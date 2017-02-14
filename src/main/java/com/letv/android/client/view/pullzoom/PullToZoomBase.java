package com.letv.android.client.view.pullzoom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import com.letv.android.client.R;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

@SuppressLint({"NewApi"})
public abstract class PullToZoomBase<T extends View> extends LinearLayout implements IPullToZoom<T> {
    private static final float FRICTION = 2.0f;
    private boolean isHideHeader;
    private boolean isParallax;
    private boolean isZoomEnabled;
    private boolean isZooming;
    protected View mHeaderView;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private boolean mIsForcedragged;
    private float mLastMotionX;
    private float mLastMotionY;
    protected T mRootView;
    protected int mScreenHeight;
    protected int mScreenWidth;
    private int mTouchSlop;
    protected View mZoomView;
    private OnPullZoomListener onPullZoomListener;

    public interface OnPullZoomListener {
        void onPullZoomEnd();

        void onPullZooming(int i);
    }

    protected abstract T createRootView(Context context, AttributeSet attributeSet);

    protected abstract boolean isReadyForPullStart();

    protected abstract void pullHeaderToZoom(int i);

    public abstract void setHeaderView(View view);

    public abstract void setZoomView(View view);

    protected abstract void smoothScrollToTop();

    public PullToZoomBase(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    @SuppressLint({"NewApi"})
    private void init(Context context, AttributeSet attrs) {
        setGravity(17);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        this.mScreenHeight = localDisplayMetrics.heightPixels;
        this.mScreenWidth = localDisplayMetrics.widthPixels;
        this.mRootView = createRootView(context, attrs);
        if (attrs != null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PullToZoomView);
            int zoomViewResId = a.getResourceId(2, 0);
            if (zoomViewResId > 0) {
                this.mZoomView = mLayoutInflater.inflate(zoomViewResId, null, false);
            }
            int headerViewResId = a.getResourceId(0, 0);
            if (headerViewResId > 0) {
                this.mHeaderView = mLayoutInflater.inflate(headerViewResId, null, false);
            }
            this.isParallax = a.getBoolean(1, true);
            handleStyledAttributes(a);
            a.recycle();
        }
        addView(this.mRootView, -1, -1);
    }

    public PullToZoomBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isZoomEnabled = true;
        this.isParallax = true;
        this.isZooming = false;
        this.isHideHeader = false;
        this.mIsBeingDragged = false;
        this.mIsForcedragged = false;
        init(context, attrs);
    }

    public void setOnPullZoomListener(OnPullZoomListener onPullZoomListener) {
        this.onPullZoomListener = onPullZoomListener;
    }

    public T getRootView() {
        return this.mRootView;
    }

    public View getZoomView() {
        return this.mZoomView;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    public boolean isPullToZoomEnabled() {
        return this.isZoomEnabled;
    }

    public boolean isZooming() {
        return this.isZooming;
    }

    public boolean isParallax() {
        return this.isParallax;
    }

    public boolean isHideHeader() {
        return this.isHideHeader;
    }

    public void setZoomEnabled(boolean isZoomEnabled) {
        this.isZoomEnabled = isZoomEnabled;
    }

    public void setParallax(boolean isParallax) {
        this.isParallax = isParallax;
    }

    public void setHideHeader(boolean isHideHeader) {
        this.isHideHeader = isHideHeader;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isPullToZoomEnabled() || isHideHeader()) {
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
                    if (isReadyForPullStart()) {
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
                    if (isReadyForPullStart()) {
                        float y2 = event.getY();
                        float x = event.getX();
                        float diff = y2 - this.mLastMotionY;
                        float oppositeDiff = x - this.mLastMotionX;
                        float absDiff = Math.abs(diff);
                        if (absDiff > ((float) this.mTouchSlop) && absDiff > Math.abs(oppositeDiff) && diff >= 1.0f && isReadyForPullStart()) {
                            this.mLastMotionY = y2;
                            this.mLastMotionX = x;
                            this.mIsBeingDragged = true;
                            break;
                        }
                    }
                    break;
            }
            return this.mIsBeingDragged;
        }
    }

    public void setIsForceDragged(boolean isForceDragged) {
        this.mIsForcedragged = isForceDragged;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isPullToZoomEnabled() || isHideHeader()) {
            return false;
        }
        if (event.getAction() == 0 && event.getEdgeFlags() != 0) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                if (!isReadyForPullStart()) {
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
                if (!this.mIsBeingDragged && !this.mIsForcedragged) {
                    return false;
                }
                this.mIsBeingDragged = false;
                this.mIsForcedragged = false;
                if (!isZooming()) {
                    return true;
                }
                smoothScrollToTop();
                if (this.onPullZoomListener != null) {
                    this.onPullZoomListener.onPullZoomEnd();
                }
                this.isZooming = false;
                return true;
            case 2:
                if (!this.mIsBeingDragged && !this.mIsForcedragged) {
                    return false;
                }
                this.mLastMotionY = event.getY();
                this.mLastMotionX = event.getX();
                pullEvent();
                this.isZooming = true;
                return true;
            default:
                return false;
        }
    }

    private void pullEvent() {
        int newScrollValue = Math.round(Math.min(this.mInitialMotionY - this.mLastMotionY, 0.0f) / FRICTION);
        pullHeaderToZoom(newScrollValue);
        if (this.onPullZoomListener != null) {
            this.onPullZoomListener.onPullZooming(newScrollValue);
        }
    }
}
