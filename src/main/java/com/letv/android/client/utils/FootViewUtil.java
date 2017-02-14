package com.letv.android.client.utils;

import android.text.TextUtils;
import android.widget.ExpandableListView;
import android.widget.ListView;
import com.letv.android.client.commonlib.view.ChannelListFootView;
import com.letv.android.client.commonlib.view.ChannelListFootView.State;
import com.letv.android.client.view.PullToRefreshExpandableListView;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.BaseApplication;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class FootViewUtil {
    private ChannelListFootView mChannelListFootView;
    private PullToRefreshExpandableListView mExpandableListView;
    private PullToRefreshListView mListView;
    private String mTipMessage;

    public FootViewUtil(PullToRefreshListView listView) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mChannelListFootView = new ChannelListFootView(BaseApplication.getInstance());
        this.mListView = listView;
    }

    public FootViewUtil(PullToRefreshListView listView, String tipMessage) {
        this.mChannelListFootView = new ChannelListFootView(BaseApplication.getInstance());
        this.mListView = listView;
        this.mTipMessage = tipMessage;
    }

    public FootViewUtil(PullToRefreshExpandableListView expandableListViewListView) {
        this.mChannelListFootView = new ChannelListFootView(BaseApplication.getInstance());
        this.mExpandableListView = expandableListViewListView;
    }

    public FootViewUtil(PullToRefreshExpandableListView expandableListViewListView, String tipMessage) {
        this.mChannelListFootView = new ChannelListFootView(BaseApplication.getInstance());
        this.mExpandableListView = expandableListViewListView;
        this.mTipMessage = tipMessage;
    }

    public ChannelListFootView getChannelListFootView() {
        return this.mChannelListFootView;
    }

    public void showFootNoMore() {
        showFootView();
        this.mChannelListFootView.showNoMore(TextUtils.isEmpty(this.mTipMessage) ? TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700071, 2131100511) : this.mTipMessage);
    }

    public void showLoadingView() {
        showFootView();
        this.mChannelListFootView.showLoading();
    }

    public void showFootError() {
        showFootView();
        this.mChannelListFootView.showError(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700085, 2131100007));
    }

    public void reLoading() {
        if (this.mChannelListFootView.state == State.NOMORE || this.mChannelListFootView.state == State.ERROR) {
            showLoadingView();
        }
    }

    public void removeFootView() {
        if (this.mListView != null && ((ListView) this.mListView.getRefreshableView()).getFooterViewsCount() > 0) {
            ((ListView) this.mListView.getRefreshableView()).removeFooterView(this.mChannelListFootView);
        } else if (this.mExpandableListView != null && ((ExpandableListView) this.mExpandableListView.getRefreshableView()).getFooterViewsCount() > 0) {
            ((ExpandableListView) this.mExpandableListView.getRefreshableView()).removeFooterView(this.mChannelListFootView);
        }
    }

    public void showFootView() {
        if (this.mListView != null && ((ListView) this.mListView.getRefreshableView()).getFooterViewsCount() == 0) {
            ((ListView) this.mListView.getRefreshableView()).addFooterView(this.mChannelListFootView);
        } else if (this.mExpandableListView != null && ((ExpandableListView) this.mExpandableListView.getRefreshableView()).getFooterViewsCount() == 0) {
            ((ExpandableListView) this.mExpandableListView.getRefreshableView()).addFooterView(this.mChannelListFootView);
        }
        if (this.mChannelListFootView.getVisibility() == 8) {
            this.mChannelListFootView.setVisibility(0);
        }
    }
}
