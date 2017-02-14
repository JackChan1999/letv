package com.letv.android.client.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.letv.android.client.R;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class PullToRefreshBase<T extends View> extends LinearLayout {
    static final float FRICTION = 2.0f;
    static final int MANUAL_REFRESHING = 3;
    public static final int MODE_BOTH = 3;
    public static final int MODE_PULL_DOWN_TO_REFRESH = 1;
    public static final int MODE_PULL_UP_TO_REFRESH = 2;
    static final int PULL_TO_REFRESH = 0;
    static final int REFRESHING = 2;
    static final int RELEASE_TO_REFRESH = 1;
    private int mCurrentMode;
    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private boolean mDisableScrollingWhileRefreshing;
    private PullToRefreshHeaderView mFooterLayout;
    private final Handler mHandler;
    private int mHeaderHeight;
    private PullToRefreshHeaderView mHeaderLayout;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    public boolean mIsFromMessagePage;
    private boolean mIsPullToRefreshEnabled;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mMode;
    private OnRefreshListener mOnRefreshListener;
    T mRefreshableView;
    private int mState;
    private int mTouchSlop;
    protected Object[] objs;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLastItemVisibleListener {
        void onLastItemVisible();
    }

    final class SmoothScrollRunnable implements Runnable {
        static final int ANIMATION_DURATION_MS = 190;
        static final int ANIMATION_FPS = 16;
        private boolean mContinueRunning;
        private int mCurrentY;
        private final Handler mHandler;
        private final Interpolator mInterpolator;
        private final int mScrollFromY;
        private final int mScrollToY;
        private long mStartTime;
        final /* synthetic */ PullToRefreshBase this$0;

        public SmoothScrollRunnable(PullToRefreshBase this$0, Handler handler, int fromY, int toY) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            this.mContinueRunning = true;
            this.mStartTime = -1;
            this.mCurrentY = -1;
            this.mHandler = handler;
            this.mScrollFromY = fromY;
            this.mScrollToY = toY;
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        }

        public void run() {
            if (this.mStartTime == -1) {
                this.mStartTime = System.currentTimeMillis();
            } else {
                this.mCurrentY = this.mScrollFromY - Math.round(((float) (this.mScrollFromY - this.mScrollToY)) * this.mInterpolator.getInterpolation(((float) Math.max(Math.min(((System.currentTimeMillis() - this.mStartTime) * 1000) / 190, 1000), 0)) / 1000.0f));
                this.this$0.setHeaderScroll(this.mCurrentY);
            }
            if (this.mContinueRunning && this.mScrollToY != this.mCurrentY) {
                this.mHandler.postDelayed(this, 16);
            } else if (Math.abs(this.mScrollToY - this.mCurrentY) < 10 && this.this$0.mState == 2 && this.this$0.mOnRefreshListener != null) {
                this.this$0.mOnRefreshListener.onRefresh();
            }
        }

        public void stop() {
            this.mContinueRunning = false;
            this.mHandler.removeCallbacks(this);
        }
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attributeSet);

    protected abstract boolean isReadyForPullDown();

    protected abstract boolean isReadyForPullUp();

    public PullToRefreshBase(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mIsBeingDragged = false;
        this.mState = 0;
        this.mMode = 1;
        this.mDisableScrollingWhileRefreshing = true;
        this.mIsPullToRefreshEnabled = true;
        this.mHandler = new Handler();
        this.mIsFromMessagePage = false;
        init(context, null);
    }

    public PullToRefreshBase(Context context, int mode) {
        super(context);
        this.mIsBeingDragged = false;
        this.mState = 0;
        this.mMode = 1;
        this.mDisableScrollingWhileRefreshing = true;
        this.mIsPullToRefreshEnabled = true;
        this.mHandler = new Handler();
        this.mIsFromMessagePage = false;
        this.mMode = mode;
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsBeingDragged = false;
        this.mState = 0;
        this.mMode = 1;
        this.mDisableScrollingWhileRefreshing = true;
        this.mIsPullToRefreshEnabled = true;
        this.mHandler = new Handler();
        this.mIsFromMessagePage = false;
        init(context, attrs);
    }

    public final T getAdapterView() {
        return this.mRefreshableView;
    }

    public final T getRefreshableView() {
        return this.mRefreshableView;
    }

    public final boolean isPullToRefreshEnabled() {
        return this.mIsPullToRefreshEnabled;
    }

    public final boolean isDisableScrollingWhileRefreshing() {
        return this.mDisableScrollingWhileRefreshing;
    }

    public final boolean isRefreshing() {
        return this.mState == 2 || this.mState == 3;
    }

    public final void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
        this.mDisableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
    }

    public final void onRefreshComplete() {
        if (this.mState != 0) {
            if (!(this.mHeaderLayout == null || this.objs == null)) {
                this.mHeaderLayout.setParams(this.objs);
            }
            resetHeader(false);
        }
    }

    public final void onRefreshCompleteImmediately() {
        if (this.mState != 0) {
            if (!(this.mHeaderLayout == null || this.objs == null)) {
                this.mHeaderLayout.setParams(this.objs);
            }
            resetHeader(true);
        }
    }

    public final void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public final void setPullToRefreshEnabled(boolean enable) {
        this.mIsPullToRefreshEnabled = enable;
    }

    public void setReleaseLabel(String releaseLabel) {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.setReleaseLabel(releaseLabel);
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setReleaseLabel(releaseLabel);
        }
    }

    public void setPullLabel(String pullLabel) {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.setPullLabel(pullLabel);
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setPullLabel(pullLabel);
        }
    }

    public void setRefreshingLabel(String refreshingLabel) {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.setRefreshingLabel(refreshingLabel);
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setRefreshingLabel(refreshingLabel);
        }
    }

    public final void setRefreshing() {
        setRefreshing(true);
    }

    public final void setRefreshing(boolean doScroll) {
        if (!isRefreshing()) {
            setRefreshingInternal(doScroll);
            this.mState = 3;
        }
    }

    public final boolean hasPullFromTop() {
        return this.mCurrentMode != 2;
    }

    public final boolean onTouchEvent(MotionEvent event) {
        if (!this.mIsPullToRefreshEnabled) {
            return false;
        }
        if (isRefreshing() && this.mDisableScrollingWhileRefreshing) {
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
                return true;
            case 1:
            case 3:
                if (!this.mIsBeingDragged) {
                    return false;
                }
                this.mIsBeingDragged = false;
                if (this.mState != 1 || this.mOnRefreshListener == null) {
                    this.mHeaderLayout.reset3();
                    smoothScrollTo(0);
                } else {
                    setRefreshingInternal(true);
                }
                return true;
            case 2:
                if (!(this.mHeaderLayout == null || this.mHeaderLayout.refreshFlag)) {
                    this.mHeaderLayout.refreshFlag = true;
                }
                if (!this.mIsBeingDragged) {
                    return false;
                }
                this.mLastMotionY = event.getY();
                pullEvent();
                return true;
            default:
                return false;
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.mIsPullToRefreshEnabled) {
            return false;
        }
        if (isRefreshing() && this.mDisableScrollingWhileRefreshing) {
            return true;
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
                        this.mLastMotionX = event.getX();
                        this.mIsBeingDragged = false;
                        break;
                    }
                    break;
                case 2:
                    if (isReadyForPull()) {
                        float y2 = event.getY();
                        float dy = y2 - this.mLastMotionY;
                        float yDiff = Math.abs(dy);
                        float xDiff = Math.abs(event.getX() - this.mLastMotionX);
                        if (yDiff > ((float) this.mTouchSlop) && yDiff > xDiff) {
                            if ((this.mMode != 1 && this.mMode != 3) || dy < 1.0E-4f || !isReadyForPullDown()) {
                                if ((this.mMode == 2 || this.mMode == 3) && dy <= 1.0E-4f && isReadyForPullUp()) {
                                    this.mLastMotionY = y2;
                                    this.mIsBeingDragged = true;
                                    if (this.mMode == 3) {
                                        this.mCurrentMode = 2;
                                        break;
                                    }
                                }
                            }
                            this.mLastMotionY = y2;
                            this.mIsBeingDragged = true;
                            if (this.mMode == 3) {
                                this.mCurrentMode = 1;
                                break;
                            }
                        }
                    }
                    break;
            }
            return this.mIsBeingDragged;
        }
    }

    protected void addRefreshableView(Context context, T refreshableView) {
        addView(refreshableView, new LayoutParams(-1, 0, 1.0f));
    }

    protected final int getCurrentMode() {
        return this.mCurrentMode;
    }

    protected final PullToRefreshHeaderView getFooterLayout() {
        return this.mFooterLayout;
    }

    protected final PullToRefreshHeaderView getHeaderLayout() {
        return this.mHeaderLayout;
    }

    protected final int getHeaderHeight() {
        return this.mHeaderHeight;
    }

    protected final int getMode() {
        return this.mMode;
    }

    protected void resetHeader(boolean immediately) {
        this.mState = 0;
        this.mIsBeingDragged = false;
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.reset2();
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.reset2();
        }
        if (immediately) {
            if (this.mFooterLayout != null) {
                this.mHeaderLayout.reset();
            }
            scrollTo(0, 0);
            return;
        }
        this.mHandler.postDelayed(new Runnable(this) {
            final /* synthetic */ PullToRefreshBase this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                if (this.this$0.mFooterLayout != null) {
                    this.this$0.mHeaderLayout.reset();
                }
            }
        }, 300);
        smoothScrollTo(0);
    }

    protected void setRefreshingInternal(boolean doScroll) {
        this.mState = 2;
        LogInfo.log("zhaoxiang", "-----setRefreshingInternal" + this.mState);
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.refreshing();
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.refreshing();
        }
        if (doScroll) {
            smoothScrollTo(this.mCurrentMode == 1 ? -this.mHeaderHeight : this.mHeaderHeight);
        }
    }

    protected final void setHeaderScroll(int y) {
        scrollTo(0, y);
    }

    protected final void smoothScrollTo(int y) {
        if (this.mCurrentSmoothScrollRunnable != null) {
            this.mCurrentSmoothScrollRunnable.stop();
            this.mCurrentSmoothScrollRunnable = null;
        }
        if (getScrollY() != y) {
            this.mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(this, this.mHandler, getScrollY(), y);
            this.mHandler.post(this.mCurrentSmoothScrollRunnable);
        }
    }

    public void setParams(Object... objs) {
        this.mHeaderLayout.setParams(objs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(1);
        this.mTouchSlop = ViewConfiguration.getTouchSlop();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);
        if (a.hasValue(22)) {
            this.mMode = a.getInteger(22, 1);
        }
        this.mRefreshableView = createRefreshableView(context, attrs);
        addRefreshableView(context, this.mRefreshableView);
        String pullLabel = context.getString(2131100695);
        String refreshingLabel = context.getString(2131100699);
        String releaseLabel = context.getString(2131100700);
        if (this.mMode == 1 || this.mMode == 3) {
            this.mHeaderLayout = new PullToRefreshHeaderView(context, 1, releaseLabel, pullLabel, refreshingLabel, this.objs);
            addView(this.mHeaderLayout, 0, new LayoutParams(-1, -2));
            measureView(this.mHeaderLayout);
            this.mHeaderHeight = this.mHeaderLayout.getMeasuredHeight();
        }
        if (this.mMode == 2 || this.mMode == 3) {
            this.mFooterLayout = new PullToRefreshHeaderView(context, 2, releaseLabel, pullLabel, refreshingLabel, this.objs);
            addView(this.mFooterLayout, new LayoutParams(-1, -2));
            measureView(this.mFooterLayout);
            this.mHeaderHeight = this.mFooterLayout.getMeasuredHeight();
        }
        if (a.hasValue(21)) {
            int color = a.getColor(21, -16777216);
            if (this.mHeaderLayout != null) {
                this.mHeaderLayout.setTextColor(color);
            }
            if (this.mFooterLayout != null) {
                this.mFooterLayout.setTextColor(color);
            }
        }
        if (a.hasValue(20)) {
            setBackgroundResource(a.getResourceId(20, 0));
        }
        if (a.hasValue(19)) {
            this.mRefreshableView.setBackgroundResource(a.getResourceId(19, 0));
        }
        a.recycle();
        switch (this.mMode) {
            case 2:
                setPadding(0, 0, 0, -this.mHeaderHeight);
                break;
            case 3:
                setPadding(0, -this.mHeaderHeight, 0, -this.mHeaderHeight);
                break;
            default:
                setPadding(0, -this.mHeaderHeight, 0, 0);
                break;
        }
        if (this.mMode != 3) {
            this.mCurrentMode = this.mMode;
        }
    }

    private void measureView(View child) {
        int childHeightSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private boolean pullEvent() {
        int newHeight;
        int oldHeight = getScrollY();
        switch (this.mCurrentMode) {
            case 2:
                newHeight = Math.round(Math.max(this.mInitialMotionY - this.mLastMotionY, 0.0f) / FRICTION);
                break;
            default:
                newHeight = Math.round(Math.min(this.mInitialMotionY - this.mLastMotionY, 0.0f) / FRICTION);
                break;
        }
        if (newHeight != 0) {
            setHeaderScroll(newHeight);
            if (this.mState == 0 && this.mHeaderHeight < Math.abs(newHeight)) {
                this.mState = 1;
                switch (this.mCurrentMode) {
                    case 1:
                        this.mHeaderLayout.setMessagePageFlag(this.mIsFromMessagePage);
                        this.mHeaderLayout.releaseToRefresh();
                        return true;
                    case 2:
                        this.mFooterLayout.releaseToRefresh();
                        return true;
                    default:
                        return true;
                }
            } else if (this.mState == 1 && this.mHeaderHeight >= Math.abs(newHeight)) {
                this.mState = 0;
                switch (this.mCurrentMode) {
                    case 1:
                        this.mHeaderLayout.setMessagePageFlag(getMessagePageFlag());
                        this.mHeaderLayout.pullToRefresh();
                        return true;
                    case 2:
                        this.mFooterLayout.pullToRefresh();
                        return true;
                    default:
                        return true;
                }
            }
        }
        if (oldHeight == newHeight) {
            return false;
        }
        return true;
    }

    public boolean getMessagePageFlag() {
        return this.mIsFromMessagePage;
    }

    public void setMessagePageFlag(boolean isFromMessagePage) {
        this.mIsFromMessagePage = isFromMessagePage;
    }

    private boolean isReadyForPull() {
        switch (this.mMode) {
            case 1:
                return isReadyForPullDown();
            case 2:
                return isReadyForPullUp();
            case 3:
                return isReadyForPullUp() || isReadyForPullDown();
            default:
                return false;
        }
    }

    public void setLongClickable(boolean longClickable) {
        getRefreshableView().setLongClickable(longClickable);
    }

    public void hideTimeText() {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.setHideTime(true);
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.setHideTime(true);
        }
    }

    public void reset() {
        if (this.mHeaderLayout != null) {
            this.mHeaderLayout.reset();
        }
        if (this.mFooterLayout != null) {
            this.mFooterLayout.reset();
        }
    }
}
