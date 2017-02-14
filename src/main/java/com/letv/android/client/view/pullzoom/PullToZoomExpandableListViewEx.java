package com.letv.android.client.view.pullzoom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class PullToZoomExpandableListViewEx extends PullToZoomBase<ExpandableListView> implements OnScrollListener {
    private static final String TAG = PullToZoomExpandableListViewEx.class.getSimpleName();
    private static final Interpolator sInterpolator = new 1();
    private FrameLayout mHeaderContainer;
    private int mHeaderHeight;
    private ScalingRunnable mScalingRunnable;

    public PullToZoomExpandableListViewEx(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public PullToZoomExpandableListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((ExpandableListView) this.mRootView).setOnScrollListener(this);
        this.mScalingRunnable = new ScalingRunnable(this);
        setEventDispatch();
    }

    public void setHideHeader(boolean isHideHeader) {
        if (isHideHeader != isHideHeader()) {
            super.setHideHeader(isHideHeader);
            if (isHideHeader) {
                removeHeaderView();
            } else {
                updateHeaderView();
            }
        }
    }

    public void setHeaderView(View headerView) {
        if (headerView != null) {
            this.mHeaderView = headerView;
            updateHeaderView();
        }
    }

    public void setZoomView(View zoomView) {
        if (zoomView != null) {
            this.mZoomView = zoomView;
            updateHeaderView();
        }
    }

    private void removeHeaderView() {
        if (this.mHeaderContainer != null) {
            ((ExpandableListView) this.mRootView).removeHeaderView(this.mHeaderContainer);
        }
    }

    private void updateHeaderView() {
        if (this.mHeaderContainer != null) {
            ((ExpandableListView) this.mRootView).removeHeaderView(this.mHeaderContainer);
            this.mHeaderContainer.removeAllViews();
            if (this.mZoomView != null) {
                this.mHeaderContainer.addView(this.mZoomView);
            }
            if (this.mHeaderView != null) {
                this.mHeaderContainer.addView(this.mHeaderView);
            }
            this.mHeaderHeight = this.mHeaderContainer.getHeight();
            ((ExpandableListView) this.mRootView).addHeaderView(this.mHeaderContainer);
        }
    }

    public void setAdapter(ExpandableListAdapter adapter) {
        ((ExpandableListView) this.mRootView).setAdapter(adapter);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        ((ExpandableListView) this.mRootView).setOnItemClickListener(listener);
    }

    protected ExpandableListView createRootView(Context context, AttributeSet attrs) {
        return new ExpandableListView(context, attrs);
    }

    protected void smoothScrollToTop() {
        Log.d(TAG, "smoothScrollToTop --> ");
        this.mScalingRunnable.startAnimation(200);
    }

    protected void pullHeaderToZoom(int newScrollValue) {
        Log.d(TAG, "pullHeaderToZoom --> newScrollValue = " + newScrollValue);
        Log.d(TAG, "pullHeaderToZoom --> mHeaderHeight = " + this.mHeaderHeight);
        if (!(this.mScalingRunnable == null || this.mScalingRunnable.isFinished())) {
            this.mScalingRunnable.abortAnimation();
        }
        LayoutParams localLayoutParams = this.mHeaderContainer.getLayoutParams();
        localLayoutParams.height = Math.abs(newScrollValue) + this.mHeaderHeight;
        this.mHeaderContainer.setLayoutParams(localLayoutParams);
    }

    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    private boolean isFirstItemVisible() {
        Adapter adapter = ((ExpandableListView) this.mRootView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        if (((ExpandableListView) this.mRootView).getFirstVisiblePosition() <= 1) {
            View firstVisibleChild = ((ExpandableListView) this.mRootView).getChildAt(0);
            if (firstVisibleChild != null) {
                return firstVisibleChild.getTop() >= ((ExpandableListView) this.mRootView).getTop();
            }
        }
        return false;
    }

    public void handleStyledAttributes(TypedArray a) {
        this.mHeaderContainer = new FrameLayout(getContext());
        if (this.mZoomView != null) {
            this.mHeaderContainer.addView(this.mZoomView);
        }
        if (this.mHeaderView != null) {
            this.mHeaderContainer.addView(this.mHeaderView);
        }
        ((ExpandableListView) this.mRootView).addHeaderView(this.mHeaderContainer);
    }

    public void setHeaderViewSize(int width, int height) {
        if (this.mHeaderContainer != null) {
            LayoutParams layoutParams = this.mHeaderContainer.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new AbsListView.LayoutParams(width, height);
            }
            layoutParams.width = width;
            layoutParams.height = height;
            this.mHeaderContainer.setLayoutParams(layoutParams);
            this.mHeaderHeight = height;
        }
    }

    public void setHeaderLayoutParams(AbsListView.LayoutParams layoutParams) {
        if (this.mHeaderContainer != null) {
            this.mHeaderContainer.setLayoutParams(layoutParams);
            this.mHeaderHeight = layoutParams.height;
        }
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if (this.mHeaderHeight == 0 && this.mHeaderContainer != null) {
            this.mHeaderHeight = this.mHeaderContainer.getHeight();
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mZoomView != null && !isHideHeader() && isPullToZoomEnabled()) {
            float f = (float) (this.mHeaderHeight - this.mHeaderContainer.getBottom());
            if (!isParallax()) {
                return;
            }
            if (f > 0.0f && f < ((float) this.mHeaderHeight)) {
                this.mHeaderContainer.scrollTo(0, -((int) (0.65d * ((double) f))));
            } else if (this.mHeaderContainer.getScrollY() != 0) {
                this.mHeaderContainer.scrollTo(0, 0);
            }
        }
    }

    public void setEventDispatch() {
        ((ExpandableListView) this.mRootView).setOnTouchListener(new 2(this));
    }
}
