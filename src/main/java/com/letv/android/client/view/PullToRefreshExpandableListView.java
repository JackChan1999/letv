package com.letv.android.client.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class PullToRefreshExpandableListView extends PullToRefreshAdapterViewBase<ExpandableListView> {
    private PullToRefreshHeaderView mFooterLoadingView;
    private PullToRefreshHeaderView mHeaderLoadingView;
    private FrameLayout mLvFooterLoadingFrame;
    private FrameLayout mLvHeaderLoadingFrame;

    public PullToRefreshExpandableListView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshExpandableListView(Context context, int mode) {
        super(context, mode);
        setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDisableScrollingWhileRefreshing(false);
    }

    public ContextMenuInfo getContextMenuInfo() {
        return ((InternalExpandableListView) getRefreshableView()).getContextMenuInfo();
    }

    public void setReleaseLabel(String releaseLabel) {
        super.setReleaseLabel(releaseLabel);
        if (this.mHeaderLoadingView != null) {
            this.mHeaderLoadingView.setReleaseLabel(releaseLabel);
        }
        if (this.mFooterLoadingView != null) {
            this.mFooterLoadingView.setReleaseLabel(releaseLabel);
        }
    }

    public void setPullLabel(String pullLabel) {
        super.setPullLabel(pullLabel);
        if (this.mHeaderLoadingView != null) {
            this.mHeaderLoadingView.setPullLabel(pullLabel);
        }
        if (this.mFooterLoadingView != null) {
            this.mFooterLoadingView.setPullLabel(pullLabel);
        }
    }

    public void setRefreshingLabel(String refreshingLabel) {
        super.setRefreshingLabel(refreshingLabel);
        if (this.mHeaderLoadingView != null) {
            this.mHeaderLoadingView.setRefreshingLabel(refreshingLabel);
        }
        if (this.mFooterLoadingView != null) {
            this.mFooterLoadingView.setRefreshingLabel(refreshingLabel);
        }
    }

    protected final ExpandableListView createRefreshableView(Context context, AttributeSet attrs) {
        ExpandableListView lv;
        if (VERSION.SDK_INT >= 9) {
            lv = new InternalExpandableListViewSDK9(this, context, attrs);
        } else {
            lv = new InternalExpandableListView(this, context, attrs);
        }
        lv.setLayoutParams(new LayoutParams(-1, -1));
        int mode = getMode();
        String pullLabel = context.getString(2131100695);
        String refreshingLabel = context.getString(2131100699);
        String releaseLabel = context.getString(2131100700);
        if (mode == 1 || mode == 3) {
            this.mLvHeaderLoadingFrame = new FrameLayout(context);
            this.mHeaderLoadingView = new PullToRefreshHeaderView(context, 1, releaseLabel, pullLabel, refreshingLabel, new Object[0]);
            this.mLvHeaderLoadingFrame.addView(this.mHeaderLoadingView, -1, (int) ((60.0f * getResources().getDisplayMetrics().density) + 0.5f));
            this.mHeaderLoadingView.setVisibility(8);
            lv.addHeaderView(this.mLvHeaderLoadingFrame, null, false);
        }
        if (mode == 2 || mode == 3) {
            this.mLvFooterLoadingFrame = new FrameLayout(context);
            this.mFooterLoadingView = new PullToRefreshHeaderView(context, 2, releaseLabel, pullLabel, refreshingLabel, new Object[0]);
            this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, -1, -2);
            this.mFooterLoadingView.setVisibility(8);
        }
        lv.setId(16908293);
        return lv;
    }

    protected void setRefreshingInternal(boolean doScroll) {
        ListAdapter adapter = ((ExpandableListView) this.mRefreshableView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            super.setRefreshingInternal(doScroll);
            return;
        }
        PullToRefreshHeaderView originalLoadingLayout;
        PullToRefreshHeaderView listViewLoadingLayout;
        int selection;
        int scrollToY;
        super.setRefreshingInternal(false);
        switch (getCurrentMode()) {
            case 2:
                originalLoadingLayout = getFooterLayout();
                listViewLoadingLayout = this.mFooterLoadingView;
                selection = ((ExpandableListView) this.mRefreshableView).getCount() - 1;
                scrollToY = getScrollY() - getHeaderHeight();
                break;
            default:
                originalLoadingLayout = getHeaderLayout();
                listViewLoadingLayout = this.mHeaderLoadingView;
                selection = 0;
                scrollToY = getScrollY() + getHeaderHeight();
                break;
        }
        if (doScroll) {
            setHeaderScroll(scrollToY);
        }
        originalLoadingLayout.setVisibility(4);
        listViewLoadingLayout.setParams(this.objs);
        listViewLoadingLayout.setVisibility(0);
        listViewLoadingLayout.refreshing();
        if (doScroll) {
            ((ExpandableListView) this.mRefreshableView).setSelection(selection);
            smoothScrollTo(0);
        }
    }

    protected void resetHeader(boolean immediately) {
        ListAdapter adapter = ((ExpandableListView) this.mRefreshableView).getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            super.resetHeader(immediately);
            return;
        }
        PullToRefreshHeaderView originalLoadingLayout;
        PullToRefreshHeaderView listViewLoadingLayout;
        boolean doScroll;
        int scrollToHeight = getHeaderHeight();
        switch (getCurrentMode()) {
            case 2:
                originalLoadingLayout = getFooterLayout();
                listViewLoadingLayout = this.mFooterLoadingView;
                doScroll = isReadyForPullUp();
                break;
            default:
                originalLoadingLayout = getHeaderLayout();
                listViewLoadingLayout = this.mHeaderLoadingView;
                scrollToHeight *= -1;
                doScroll = isReadyForPullDown();
                break;
        }
        originalLoadingLayout.setVisibility(0);
        if (doScroll) {
            setHeaderScroll(scrollToHeight);
        }
        listViewLoadingLayout.setVisibility(8);
        super.resetHeader(immediately);
    }

    protected int getNumberInternalHeaderViews() {
        return this.mHeaderLoadingView != null ? 1 : 0;
    }

    protected int getNumberInternalFooterViews() {
        return this.mFooterLoadingView != null ? 1 : 0;
    }

    public void addHeaderLoadingView() {
        this.mHeaderLoadingView.setVisibility(8);
        ((ExpandableListView) getRefreshableView()).addHeaderView(this.mLvHeaderLoadingFrame, null, false);
    }

    public void removeHeaderLoadingView() {
        ((ExpandableListView) getRefreshableView()).removeHeaderView(this.mLvHeaderLoadingFrame);
    }

    public void setParams(Object... objs) {
        this.objs = objs;
        super.setParams(objs);
    }
}
