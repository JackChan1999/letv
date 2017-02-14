package com.letv.mobile.lebox.view.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.utils.Util;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout implements IPullToRefresh<T> {
    static final boolean DEBUG = true;
    static final int DEMO_SCROLL_INTERVAL = 225;
    static final float FRICTION = 2.0f;
    static final String LOG_TAG = "PullToRefresh";
    public static final int SMOOTH_SCROLL_DURATION_MS = 200;
    public static final int SMOOTH_SCROLL_LONG_DURATION_MS = 325;
    static final String STATE_CURRENT_MODE = "ptr_current_mode";
    static final String STATE_MODE = "ptr_mode";
    static final String STATE_SCROLLING_REFRESHING_ENABLED = "ptr_disable_scrolling";
    static final String STATE_SHOW_REFRESHING_VIEW = "ptr_show_refreshing_view";
    static final String STATE_STATE = "ptr_state";
    static final String STATE_SUPER = "ptr_super";
    static final boolean USE_HW_LAYERS = false;
    private Mode mCurrentMode;
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private boolean mFilterTouchEvents = true;
    private LoadingLayout mFooterLayout;
    private LoadingLayout mHeaderLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged = false;
    private float mLastMotionX;
    private float mLastMotionY;
    private boolean mLayoutVisibilityChangesEnabled = true;
    private AnimationStyle mLoadingAnimationStyle = AnimationStyle.getDefault();
    private Mode mMode = Mode.getDefault();
    private OnPullEventListener<T> mOnPullEventListener;
    private OnRefreshListener<T> mOnRefreshListener;
    private OnRefreshListener2<T> mOnRefreshListener2;
    private boolean mOverScrollEnabled = true;
    T mRefreshableView;
    private FrameLayout mRefreshableViewWrapper;
    private Interpolator mScrollAnimationInterpolator;
    private boolean mScrollingWhileRefreshingEnabled = false;
    private boolean mShowViewWhileRefreshing = true;
    private State mState = State.RESET;
    private int mTouchSlop;

    public interface OnRefreshListener<V extends View> {
        void onRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    public enum AnimationStyle {
        ROTATE,
        FLIP;

        static AnimationStyle getDefault() {
            return ROTATE;
        }

        static AnimationStyle mapIntToValue(int modeInt) {
            switch (modeInt) {
                case 1:
                    return FLIP;
                default:
                    return ROTATE;
            }
        }

        LoadingLayout createLoadingLayout(Context context, Mode mode, Orientation scrollDirection, TypedArray attrs) {
            switch (this) {
                case FLIP:
                    return new FlipLoadingLayout(context, mode, scrollDirection, attrs);
                default:
                    return new RotateLoadingLayout(context, mode, scrollDirection, attrs);
            }
        }
    }

    public enum Mode {
        DISABLED(0),
        PULL_FROM_START(1),
        PULL_FROM_END(2),
        BOTH(3),
        MANUAL_REFRESH_ONLY(4);
        
        @Deprecated
        public static Mode PULL_DOWN_TO_REFRESH;
        @Deprecated
        public static Mode PULL_UP_TO_REFRESH;
        private int mIntValue;

        static {
            PULL_DOWN_TO_REFRESH = PULL_FROM_START;
            PULL_UP_TO_REFRESH = PULL_FROM_END;
        }

        static Mode mapIntToValue(int modeInt) {
            for (Mode value : values()) {
                if (modeInt == value.getIntValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        static Mode getDefault() {
            return PULL_FROM_START;
        }

        private Mode(int modeInt) {
            this.mIntValue = modeInt;
        }

        boolean permitsPullToRefresh() {
            return (this == DISABLED || this == MANUAL_REFRESH_ONLY) ? false : true;
        }

        public boolean showHeaderLoadingLayout() {
            return this == PULL_FROM_START || this == BOTH;
        }

        public boolean showFooterLoadingLayout() {
            return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    public interface OnLastItemVisibleListener {
        void onLastItemVisible();
    }

    public interface OnPullEventListener<V extends View> {
        void onPullEvent(PullToRefreshBase<V> pullToRefreshBase, State state, Mode mode);
    }

    public interface OnRefreshListener2<V extends View> {
        void onPullDownToRefresh(PullToRefreshBase<V> pullToRefreshBase);

        void onPullUpToRefresh(PullToRefreshBase<V> pullToRefreshBase);
    }

    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }

    final class SmoothScrollRunnable implements Runnable {
        private boolean mContinueRunning = true;
        private int mCurrentY = -1;
        private final long mDuration;
        private final Interpolator mInterpolator;
        private final OnSmoothScrollFinishedListener mListener;
        private final int mScrollFromY;
        private final int mScrollToY;
        private long mStartTime = -1;

        public SmoothScrollRunnable(int fromY, int toY, long duration, OnSmoothScrollFinishedListener listener) {
            this.mScrollFromY = fromY;
            this.mScrollToY = toY;
            this.mInterpolator = PullToRefreshBase.this.mScrollAnimationInterpolator;
            this.mDuration = duration;
            this.mListener = listener;
        }

        public void run() {
            if (this.mStartTime == -1) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / this.mDuration, 1000), 0)) / 1000.0f));
                PullToRefreshBase.this.setHeaderScroll(this.mCurrentY);
            }
            if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
                ViewCompat.postOnAnimation(PullToRefreshBase.this, this);
            } else if (this.mListener != null) {
                this.mListener.onSmoothScrollFinished();
            }
        }

        public void stop() {
            this.mContinueRunning = false;
            PullToRefreshBase.this.removeCallbacks(this);
        }
    }

    public enum State {
        RESET(0),
        PULL_TO_REFRESH(1),
        RELEASE_TO_REFRESH(2),
        REFRESHING(8),
        MANUAL_REFRESHING(9),
        OVERSCROLLING(16);
        
        private int mIntValue;

        static State mapIntToValue(int stateInt) {
            for (State value : values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return RESET;
        }

        private State(int intValue) {
            this.mIntValue = intValue;
        }

        int getIntValue() {
            return this.mIntValue;
        }
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attributeSet);

    public abstract Orientation getPullToRefreshScrollDirection();

    protected abstract boolean isReadyForPullEnd();

    protected abstract boolean isReadyForPullStart();

    public PullToRefreshBase(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullToRefreshBase(Context context, Mode mode) {
        super(context);
        this.mMode = mode;
        init(context, null);
    }

    public PullToRefreshBase(Context context, Mode mode, AnimationStyle animStyle) {
        super(context);
        this.mMode = mode;
        this.mLoadingAnimationStyle = animStyle;
        init(context, null);
    }

    public void addView(View child, int index, LayoutParams params) {
        Log.d(LOG_TAG, "addView: " + child.getClass().getSimpleName());
        T refreshableView = getRefreshableView();
        if (refreshableView instanceof ViewGroup) {
            ((ViewGroup) refreshableView).addView(child, index, params);
            return;
        }
        throw new UnsupportedOperationException("Refreshable View is not a ViewGroup so can't addView");
    }

    public final boolean demo() {
        if (this.mMode.showHeaderLoadingLayout() && isReadyForPullStart()) {
            smoothScrollToAndBack((-getHeaderSize()) * 2);
            return true;
        } else if (!this.mMode.showFooterLoadingLayout() || !isReadyForPullEnd()) {
            return false;
        } else {
            smoothScrollToAndBack(getFooterSize() * 2);
            return true;
        }
    }

    public final Mode getCurrentMode() {
        return this.mCurrentMode;
    }

    public final boolean getFilterTouchEvents() {
        return this.mFilterTouchEvents;
    }

    public final ILoadingLayout getLoadingLayoutProxy() {
        return getLoadingLayoutProxy(true, true);
    }

    public final ILoadingLayout getLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        return createLoadingLayoutProxy(includeStart, includeEnd);
    }

    public final Mode getMode() {
        return this.mMode;
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public final boolean getShowViewWhileRefreshing() {
        return this.mShowViewWhileRefreshing;
    }

    public final State getState() {
        return this.mState;
    }

    @Deprecated
    public final boolean isDisableScrollingWhileRefreshing() {
        return !isScrollingWhileRefreshingEnabled();
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mMode.permitsPullToRefresh();
    }

    public final boolean isPullToRefreshOverScrollEnabled() {
        return VERSION.SDK_INT >= 9 && this.mOverScrollEnabled && OverscrollHelper.isAndroidOverScrollEnabled(this.mRefreshableView);
    }

    public final boolean isRefreshing() {
        return this.mState == State.REFRESHING || this.mState == State.MANUAL_REFRESHING;
    }

    public final boolean isScrollingWhileRefreshingEnabled() {
        return this.mScrollingWhileRefreshingEnabled;
    }

    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isPullToRefreshEnabled()) {
            return false;
        }
        int action = event.getAction();
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            return false;
        } else if (action != 0 && this.mIsBeingDragged) {
            return true;
        } else {
            switch (action) {
                case 0:
                    if (isReadyForPull()) {
                        float y = event.getY();
                        this.mInitialMotionY = y;
                        this.mLastMotionY = y;
                        y = event.getX();
                        this.mInitialMotionX = y;
                        this.mLastMotionX = y;
                        this.mIsBeingDragged = false;
                        break;
                    }
                    break;
                case 2:
                    if (this.mScrollingWhileRefreshingEnabled || !isRefreshing()) {
                        if (isReadyForPull()) {
                            float diff;
                            float y2 = event.getY();
                            float x = event.getX();
                            float oppositeDiff;
                            switch (getPullToRefreshScrollDirection()) {
                                case HORIZONTAL:
                                    diff = x - this.mLastMotionX;
                                    oppositeDiff = y2 - this.mLastMotionY;
                                    break;
                                default:
                                    diff = y2 - this.mLastMotionY;
                                    oppositeDiff = x - this.mLastMotionX;
                                    break;
                            }
                            float absDiff = Math.abs(diff);
                            if (absDiff > ((float) this.mTouchSlop) && (!this.mFilterTouchEvents || absDiff > Math.abs(oppositeDiff))) {
                                if (!this.mMode.showHeaderLoadingLayout() || diff < 1.0f || !isReadyForPullStart()) {
                                    if (this.mMode.showFooterLoadingLayout() && diff <= -1.0f && isReadyForPullEnd()) {
                                        this.mLastMotionY = y2;
                                        this.mLastMotionX = x;
                                        this.mIsBeingDragged = true;
                                        if (this.mMode == Mode.BOTH) {
                                            this.mCurrentMode = Mode.PULL_FROM_END;
                                            break;
                                        }
                                    }
                                }
                                this.mLastMotionY = y2;
                                this.mLastMotionX = x;
                                this.mIsBeingDragged = true;
                                if (this.mMode == Mode.BOTH) {
                                    this.mCurrentMode = Mode.PULL_FROM_START;
                                    break;
                                }
                            }
                        }
                    }
                    return true;
                    break;
            }
            return this.mIsBeingDragged;
        }
    }

    public final void onRefreshComplete() {
        if (isRefreshing()) {
            setState(State.RESET, new boolean[0]);
        }
    }

    public final boolean onTouchEvent(MotionEvent event) {
        if (!isPullToRefreshEnabled()) {
            return false;
        }
        if (!this.mScrollingWhileRefreshingEnabled && isRefreshing()) {
            return true;
        }
        if (event.getAction() == 0 && event.getEdgeFlags() != 0) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                if (!isReadyForPull()) {
                    return false;
                }
                float y = event.getY();
                this.mInitialMotionY = y;
                this.mLastMotionY = y;
                y = event.getX();
                this.mInitialMotionX = y;
                this.mLastMotionX = y;
                return true;
            case 1:
            case 3:
                if (!this.mIsBeingDragged) {
                    return false;
                }
                this.mIsBeingDragged = false;
                if (this.mState == State.RELEASE_TO_REFRESH && (this.mOnRefreshListener != null || this.mOnRefreshListener2 != null)) {
                    setState(State.REFRESHING, true);
                    return true;
                } else if (isRefreshing()) {
                    smoothScrollTo(0);
                    return true;
                } else {
                    setState(State.RESET, new boolean[0]);
                    return true;
                }
            case 2:
                if (!this.mIsBeingDragged) {
                    return false;
                }
                this.mLastMotionY = event.getY();
                this.mLastMotionX = event.getX();
                pullEvent();
                return true;
            default:
                return false;
        }
    }

    public final void setScrollingWhileRefreshingEnabled(boolean allowScrollingWhileRefreshing) {
        this.mScrollingWhileRefreshingEnabled = allowScrollingWhileRefreshing;
    }

    @Deprecated
    public void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
        setScrollingWhileRefreshingEnabled(!disableScrollingWhileRefreshing);
    }

    public final void setFilterTouchEvents(boolean filterEvents) {
        this.mFilterTouchEvents = filterEvents;
    }

    @Deprecated
    public void setLastUpdatedLabel(CharSequence label) {
        getLoadingLayoutProxy().setLastUpdatedLabel(label);
    }

    @Deprecated
    public void setLoadingDrawable(Drawable drawable) {
        getLoadingLayoutProxy().setLoadingDrawable(drawable);
    }

    @Deprecated
    public void setLoadingDrawable(Drawable drawable, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setLoadingDrawable(drawable);
    }

    public void setLongClickable(boolean longClickable) {
        getRefreshableView().setLongClickable(longClickable);
    }

    public final void setMode(Mode mode) {
        if (mode != this.mMode) {
            Log.d(LOG_TAG, "Setting mode to: " + mode);
            this.mMode = mode;
            updateUIForMode();
        }
    }

    public void setOnPullEventListener(OnPullEventListener<T> listener) {
        this.mOnPullEventListener = listener;
    }

    public final void setOnRefreshListener(OnRefreshListener<T> listener) {
        this.mOnRefreshListener = listener;
        this.mOnRefreshListener2 = null;
    }

    public final void setOnRefreshListener(OnRefreshListener2<T> listener) {
        this.mOnRefreshListener2 = listener;
        this.mOnRefreshListener = null;
    }

    @Deprecated
    public void setPullLabel(CharSequence pullLabel) {
        getLoadingLayoutProxy().setPullLabel(pullLabel);
    }

    @Deprecated
    public void setPullLabel(CharSequence pullLabel, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setPullLabel(pullLabel);
    }

    @Deprecated
    public final void setPullToRefreshEnabled(boolean enable) {
        setMode(enable ? Mode.getDefault() : Mode.DISABLED);
    }

    public final void setPullToRefreshOverScrollEnabled(boolean enabled) {
        this.mOverScrollEnabled = enabled;
    }

    public final void setRefreshing() {
        setRefreshing(true);
    }

    public final void setRefreshing(boolean doScroll) {
        if (!isRefreshing()) {
            setState(State.MANUAL_REFRESHING, doScroll);
        }
    }

    @Deprecated
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        getLoadingLayoutProxy().setRefreshingLabel(refreshingLabel);
    }

    @Deprecated
    public void setRefreshingLabel(CharSequence refreshingLabel, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setRefreshingLabel(refreshingLabel);
    }

    @Deprecated
    public void setReleaseLabel(CharSequence releaseLabel) {
        setReleaseLabel(releaseLabel, Mode.BOTH);
    }

    @Deprecated
    public void setReleaseLabel(CharSequence releaseLabel, Mode mode) {
        getLoadingLayoutProxy(mode.showHeaderLoadingLayout(), mode.showFooterLoadingLayout()).setReleaseLabel(releaseLabel);
    }

    public void setScrollAnimationInterpolator(Interpolator interpolator) {
        this.mScrollAnimationInterpolator = interpolator;
    }

    public final void setShowViewWhileRefreshing(boolean showView) {
        this.mShowViewWhileRefreshing = showView;
    }

    final void setState(State state, boolean... params) {
        this.mState = state;
        Log.d(LOG_TAG, "State: " + this.mState.name());
        switch (this.mState) {
            case RESET:
                onReset();
                break;
            case PULL_TO_REFRESH:
                onPullToRefresh();
                break;
            case RELEASE_TO_REFRESH:
                onReleaseToRefresh();
                break;
            case REFRESHING:
            case MANUAL_REFRESHING:
                onRefreshing(params[0]);
                break;
        }
        if (this.mOnPullEventListener != null) {
            this.mOnPullEventListener.onPullEvent(this, this.mState, this.mCurrentMode);
        }
    }

    protected final void addViewInternal(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
    }

    protected final void addViewInternal(View child, LayoutParams params) {
        super.addView(child, -1, params);
    }

    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        LoadingLayout layout = this.mLoadingAnimationStyle.createLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(4);
        return layout;
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean includeStart, boolean includeEnd) {
        LoadingLayoutProxy proxy = new LoadingLayoutProxy();
        if (includeStart && this.mMode.showHeaderLoadingLayout()) {
            proxy.addLayout(this.mHeaderLayout);
        }
        if (includeEnd && this.mMode.showFooterLoadingLayout()) {
            proxy.addLayout(this.mFooterLayout);
        }
        return proxy;
    }

    protected final void disableLoadingLayoutVisibilityChanges() {
        this.mLayoutVisibilityChangesEnabled = false;
    }

    protected final LoadingLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    protected final int getFooterSize() {
        return this.mFooterLayout.getContentSize();
    }

    protected final LoadingLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    protected final int getHeaderSize() {
        return this.mHeaderLayout.getContentSize();
    }

    protected int getPullToRefreshScrollDuration() {
        return 200;
    }

    protected int getPullToRefreshScrollDurationLonger() {
        return 325;
    }

    protected FrameLayout getRefreshableViewWrapper() {
        return this.mRefreshableViewWrapper;
    }

    protected void handleStyledAttributes(TypedArray a) {
    }

    protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
    }

    protected void onPtrSaveInstanceState(Bundle saveState) {
    }

    protected void onPullToRefresh() {
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                this.mFooterLayout.pullToRefresh();
                return;
            case PULL_FROM_START:
                this.mHeaderLayout.pullToRefresh();
                return;
            default:
                return;
        }
    }

    protected void onRefreshing(boolean doScroll) {
        if (this.mMode.showHeaderLoadingLayout()) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mMode.showFooterLoadingLayout()) {
            this.mFooterLayout.refreshing();
        }
        if (!doScroll) {
            callRefreshListener();
        } else if (this.mShowViewWhileRefreshing) {
            OnSmoothScrollFinishedListener listener = new OnSmoothScrollFinishedListener() {
                public void onSmoothScrollFinished() {
                    PullToRefreshBase.this.callRefreshListener();
                }
            };
            switch (this.mCurrentMode) {
                case PULL_FROM_END:
                case MANUAL_REFRESH_ONLY:
                    smoothScrollTo(getFooterSize(), listener);
                    return;
                default:
                    smoothScrollTo(-getHeaderSize(), listener);
                    return;
            }
        } else {
            smoothScrollTo(0);
        }
    }

    protected void onReleaseToRefresh() {
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                this.mFooterLayout.releaseToRefresh();
                return;
            case PULL_FROM_START:
                this.mHeaderLayout.releaseToRefresh();
                return;
            default:
                return;
        }
    }

    protected void onReset() {
        this.mIsBeingDragged = false;
        this.mLayoutVisibilityChangesEnabled = true;
        this.mHeaderLayout.reset();
        this.mFooterLayout.reset();
        smoothScrollTo(0);
    }

    protected final void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setMode(Mode.mapIntToValue(bundle.getInt(STATE_MODE, 0)));
            this.mCurrentMode = Mode.mapIntToValue(bundle.getInt(STATE_CURRENT_MODE, 0));
            this.mScrollingWhileRefreshingEnabled = bundle.getBoolean(STATE_SCROLLING_REFRESHING_ENABLED, false);
            this.mShowViewWhileRefreshing = bundle.getBoolean(STATE_SHOW_REFRESHING_VIEW, true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            State viewState = State.mapIntToValue(bundle.getInt(STATE_STATE, 0));
            if (viewState == State.REFRESHING || viewState == State.MANUAL_REFRESHING) {
                setState(viewState, true);
            }
            onPtrRestoreInstanceState(bundle);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    protected final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        onPtrSaveInstanceState(bundle);
        bundle.putInt(STATE_STATE, this.mState.getIntValue());
        bundle.putInt(STATE_MODE, this.mMode.getIntValue());
        bundle.putInt(STATE_CURRENT_MODE, this.mCurrentMode.getIntValue());
        bundle.putBoolean(STATE_SCROLLING_REFRESHING_ENABLED, this.mScrollingWhileRefreshingEnabled);
        bundle.putBoolean(STATE_SHOW_REFRESHING_VIEW, this.mShowViewWhileRefreshing);
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        return bundle;
    }

    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(LOG_TAG, String.format("onSizeChanged. W: %d, H: %d", new Object[]{Integer.valueOf(w), Integer.valueOf(h)}));
        super.onSizeChanged(w, h, oldw, oldh);
        refreshLoadingViewsSize();
        refreshRefreshableViewSize(w, h);
        post(new Runnable() {
            public void run() {
                PullToRefreshBase.this.requestLayout();
            }
        });
    }

    protected final void refreshLoadingViewsSize() {
        int maximumPullScroll = (int) (((float) getMaximumPullScroll()) * 1.2f);
        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setWidth(maximumPullScroll);
                    pLeft = -maximumPullScroll;
                } else {
                    pLeft = 0;
                }
                if (!this.mMode.showFooterLoadingLayout()) {
                    pRight = 0;
                    break;
                }
                this.mFooterLayout.setWidth(maximumPullScroll);
                pRight = -maximumPullScroll;
                break;
            case VERTICAL:
                if (this.mMode.showHeaderLoadingLayout()) {
                    this.mHeaderLayout.setHeight(maximumPullScroll);
                    pTop = -maximumPullScroll;
                } else {
                    pTop = 0;
                }
                if (!this.mMode.showFooterLoadingLayout()) {
                    pBottom = 0;
                    break;
                }
                this.mFooterLayout.setHeight(maximumPullScroll);
                pBottom = -maximumPullScroll;
                break;
        }
        Log.d(LOG_TAG, String.format("Setting Padding. L: %d, T: %d, R: %d, B: %d", new Object[]{Integer.valueOf(pLeft), Integer.valueOf(pTop), Integer.valueOf(pRight), Integer.valueOf(pBottom)}));
        setPadding(pLeft, pTop, pRight, pBottom);
    }

    protected final void refreshRefreshableViewSize(int width, int height) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.mRefreshableViewWrapper.getLayoutParams();
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                if (lp.width != width) {
                    lp.width = width;
                    this.mRefreshableViewWrapper.requestLayout();
                    return;
                }
                return;
            case VERTICAL:
                if (lp.height != height) {
                    lp.height = height;
                    this.mRefreshableViewWrapper.requestLayout();
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected final void setHeaderScroll(int value) {
        Log.d(LOG_TAG, "setHeaderScroll: " + value);
        int maximumPullScroll = getMaximumPullScroll();
        value = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, value));
        if (this.mLayoutVisibilityChangesEnabled) {
            if (value < 0) {
                this.mHeaderLayout.setVisibility(0);
            } else if (value > 0) {
                this.mFooterLayout.setVisibility(0);
            } else {
                this.mHeaderLayout.setVisibility(4);
                this.mFooterLayout.setVisibility(4);
            }
        }
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                scrollTo(value, 0);
                return;
            case VERTICAL:
                scrollTo(0, value);
                return;
            default:
                return;
        }
    }

    protected final void smoothScrollTo(int scrollValue) {
        smoothScrollTo(scrollValue, (long) getPullToRefreshScrollDuration());
    }

    protected final void smoothScrollTo(int scrollValue, OnSmoothScrollFinishedListener listener) {
        smoothScrollTo(scrollValue, (long) getPullToRefreshScrollDuration(), 0, listener);
    }

    protected final void smoothScrollToLonger(int scrollValue) {
        smoothScrollTo(scrollValue, (long) getPullToRefreshScrollDurationLonger());
    }

    protected void updateUIForMode() {
        LinearLayout.LayoutParams lp = getLoadingLayoutLayoutParams();
        if (this == this.mHeaderLayout.getParent()) {
            removeView(this.mHeaderLayout);
        }
        if (this.mMode.showHeaderLoadingLayout()) {
            addViewInternal(this.mHeaderLayout, 0, lp);
        }
        if (this == this.mFooterLayout.getParent()) {
            removeView(this.mFooterLayout);
        }
        if (this.mMode.showFooterLoadingLayout()) {
            addViewInternal(this.mFooterLayout, lp);
        }
        refreshLoadingViewsSize();
        this.mCurrentMode = this.mMode != Mode.BOTH ? this.mMode : Mode.PULL_FROM_START;
    }

    private void addRefreshableView(Context context, T refreshableView) {
        this.mRefreshableViewWrapper = new FrameLayout(context);
        this.mRefreshableViewWrapper.addView(refreshableView, -1, -1);
        addViewInternal(this.mRefreshableViewWrapper, new LinearLayout.LayoutParams(-1, -1));
    }

    private void callRefreshListener() {
        if (this.mOnRefreshListener != null) {
            this.mOnRefreshListener.onRefresh(this);
        } else if (this.mOnRefreshListener2 == null) {
        } else {
            if (this.mCurrentMode == Mode.PULL_FROM_START) {
                this.mOnRefreshListener2.onPullDownToRefresh(this);
            } else if (this.mCurrentMode == Mode.PULL_FROM_END) {
                this.mOnRefreshListener2.onPullUpToRefresh(this);
            }
        }
    }

    private void init(Context context, AttributeSet attrs) {
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                setOrientation(0);
                break;
            default:
                setOrientation(1);
                break;
        }
        setGravity(17);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
        if (a.hasValue(R.styleable.PullToRefresh_ptrMode)) {
            this.mMode = Mode.mapIntToValue(a.getInteger(R.styleable.PullToRefresh_ptrMode, 0));
        }
        if (a.hasValue(R.styleable.PullToRefresh_ptrAnimationStyle)) {
            this.mLoadingAnimationStyle = AnimationStyle.mapIntToValue(a.getInteger(R.styleable.PullToRefresh_ptrAnimationStyle, 0));
        }
        this.mRefreshableView = createRefreshableView(context, attrs);
        addRefreshableView(context, this.mRefreshableView);
        this.mHeaderLayout = createLoadingLayout(context, Mode.PULL_FROM_START, a);
        this.mFooterLayout = createLoadingLayout(context, Mode.PULL_FROM_END, a);
        Drawable background;
        if (a.hasValue(R.styleable.PullToRefresh_ptrRefreshableViewBackground)) {
            background = a.getDrawable(R.styleable.PullToRefresh_ptrRefreshableViewBackground);
            if (background != null) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        } else if (a.hasValue(R.styleable.PullToRefresh_ptrAdapterViewBackground)) {
            Util.warnDeprecation("ptrAdapterViewBackground", "ptrRefreshableViewBackground");
            background = a.getDrawable(R.styleable.PullToRefresh_ptrAdapterViewBackground);
            if (background != null) {
                this.mRefreshableView.setBackgroundDrawable(background);
            }
        }
        if (a.hasValue(R.styleable.PullToRefresh_ptrOverScroll)) {
            this.mOverScrollEnabled = a.getBoolean(R.styleable.PullToRefresh_ptrOverScroll, true);
        }
        if (a.hasValue(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
            this.mScrollingWhileRefreshingEnabled = a.getBoolean(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled, false);
        }
        handleStyledAttributes(a);
        a.recycle();
        updateUIForMode();
    }

    private boolean isReadyForPull() {
        switch (this.mMode) {
            case PULL_FROM_END:
                return isReadyForPullEnd();
            case PULL_FROM_START:
                return isReadyForPullStart();
            case BOTH:
                return isReadyForPullEnd() || isReadyForPullStart();
            default:
                return false;
        }
    }

    private void pullEvent() {
        float initialMotionValue;
        float lastMotionValue;
        int newScrollValue;
        int itemDimension;
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                initialMotionValue = this.mInitialMotionX;
                lastMotionValue = this.mLastMotionX;
                break;
            default:
                initialMotionValue = this.mInitialMotionY;
                lastMotionValue = this.mLastMotionY;
                break;
        }
        switch (this.mCurrentMode) {
            case PULL_FROM_END:
                newScrollValue = Math.round(Math.max(initialMotionValue - lastMotionValue, 0.0f) / FRICTION);
                itemDimension = getFooterSize();
                break;
            default:
                newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0.0f) / FRICTION);
                itemDimension = getHeaderSize();
                break;
        }
        setHeaderScroll(newScrollValue);
        if (newScrollValue != 0 && !isRefreshing()) {
            float scale = ((float) Math.abs(newScrollValue)) / ((float) itemDimension);
            switch (this.mCurrentMode) {
                case PULL_FROM_END:
                    this.mFooterLayout.onPull(scale);
                    break;
                default:
                    this.mHeaderLayout.onPull(scale);
                    break;
            }
            if (this.mState != State.PULL_TO_REFRESH && itemDimension >= Math.abs(newScrollValue)) {
                setState(State.PULL_TO_REFRESH, new boolean[0]);
            } else if (this.mState == State.PULL_TO_REFRESH && itemDimension < Math.abs(newScrollValue)) {
                setState(State.RELEASE_TO_REFRESH, new boolean[0]);
            }
        }
    }

    private LinearLayout.LayoutParams getLoadingLayoutLayoutParams() {
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                return new LinearLayout.LayoutParams(-2, -1);
            default:
                return new LinearLayout.LayoutParams(-1, -2);
        }
    }

    private int getMaximumPullScroll() {
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                return Math.round(((float) getWidth()) / FRICTION);
            default:
                return Math.round(((float) getHeight()) / FRICTION);
        }
    }

    private final void smoothScrollTo(int scrollValue, long duration) {
        smoothScrollTo(scrollValue, duration, 0, null);
    }

    private final void smoothScrollTo(int newScrollValue, long duration, long delayMillis, OnSmoothScrollFinishedListener listener) {
        int oldScrollValue;
        if (this.mCurrentSmoothScrollRunnable != null) {
            this.mCurrentSmoothScrollRunnable.stop();
        }
        switch (getPullToRefreshScrollDirection()) {
            case HORIZONTAL:
                oldScrollValue = getScrollX();
                break;
            default:
                oldScrollValue = getScrollY();
                break;
        }
        if (oldScrollValue != newScrollValue) {
            if (this.mScrollAnimationInterpolator == null) {
                this.mScrollAnimationInterpolator = new DecelerateInterpolator();
            }
            this.mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(oldScrollValue, newScrollValue, duration, listener);
            if (delayMillis > 0) {
                postDelayed(this.mCurrentSmoothScrollRunnable, delayMillis);
            } else {
                post(this.mCurrentSmoothScrollRunnable);
            }
        }
    }

    private final void smoothScrollToAndBack(int y) {
        smoothScrollTo(y, 200, 0, new OnSmoothScrollFinishedListener() {
            public void onSmoothScrollFinished() {
                PullToRefreshBase.this.smoothScrollTo(0, 200, 225, null);
            }
        });
    }
}
