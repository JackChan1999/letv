package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.MyFollowAdapter;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.utils.FootViewUtil;
import com.letv.android.client.view.PullToRefreshBase.OnLastItemVisibleListener;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.MyFollowBean;
import com.letv.core.bean.MyFollowItemBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyFollowActivity extends WrapActivity implements OnClickListener, OnRefreshListener, OnLastItemVisibleListener, RefreshData, OnItemClickListener {
    private TextView mEmptyTip;
    private TextView mFoot;
    private FootViewUtil mFootViewUtil;
    private boolean mIsLoadingMore;
    private MyFollowAdapter mMyFollowAdapter;
    private PublicLoadLayout mMyFollowContent;
    private int mPage;
    private int mPageSize;
    private PullToRefreshListView mPullToRefreshListView;
    private int mTotalPage;
    private int total;

    public MyFollowActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mPage = 0;
        this.mTotalPage = 0;
        this.total = 0;
        this.mIsLoadingMore = false;
    }

    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyFollowActivity.class);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        findView();
    }

    protected void onResume() {
        super.onResume();
        if (PreferencesManager.getInstance().isLogin()) {
            this.mPullToRefreshListView.setPullToRefreshEnabled(true);
            requestData(true, false);
        } else {
            if (this.mPullToRefreshListView != null) {
                this.mPullToRefreshListView.setPullToRefreshEnabled(false);
            }
            showNoDataTip();
        }
        StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.myAttention, "19", null, null, -1, null);
    }

    private void findView() {
        ((ImageView) findViewById(R.id.back_btn)).setOnClickListener(this);
        this.mMyFollowContent = (PublicLoadLayout) findViewById(R.id.my_follow_content);
        this.mMyFollowContent.addContent(R.layout.my_follow_content);
        this.mMyFollowContent.setRefreshData(this);
        this.mPullToRefreshListView = (PullToRefreshListView) this.mMyFollowContent.findViewById(R.id.list);
        this.mPullToRefreshListView.setOnLastItemVisibleListener(this);
        this.mPullToRefreshListView.setOnRefreshListener(this);
        ((ListView) this.mPullToRefreshListView.getRefreshableView()).setOnItemClickListener(this);
        this.mMyFollowAdapter = new MyFollowAdapter(this);
        ((ListView) this.mPullToRefreshListView.getRefreshableView()).setAdapter(this.mMyFollowAdapter);
        this.mEmptyTip = (TextView) this.mMyFollowContent.findViewById(2131362695);
        this.mFoot = (TextView) this.mMyFollowContent.findViewById(R.id.foot);
        this.mFoot.setOnClickListener(this);
    }

    private void request(int page, final boolean isMore) {
        LogInfo.log(getActivityName() + "||wlx", "request my follow  = " + page);
        new LetvRequest(MyFollowBean.class).setUrl(MediaAssetApi.getInstance().getMyFollow(page)).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<MyFollowBean>(this) {
            final /* synthetic */ MyFollowActivity this$0;

            public void onNetworkResponse(VolleyRequest<MyFollowBean> volleyRequest, MyFollowBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", " network state=" + state);
                if (state == NetworkResponseState.SUCCESS) {
                    LogInfo.log(this.this$0.getActivityName() + "||wlx", " hull = " + hull.sourceData);
                    if (isMore) {
                        this.this$0.loadMoreRefreshView(result);
                    } else {
                        this.this$0.refreshView(result);
                    }
                } else if (!isMore) {
                    this.this$0.error(state);
                }
            }
        }).add();
    }

    private void refreshView(MyFollowBean result) {
        if (result.data == null) {
            showNoDataTip();
            this.mMyFollowAdapter.setData(null);
            this.mTotalPage = 0;
            this.total = 0;
            refreshComplete();
            return;
        }
        this.mTotalPage = result.data.page;
        this.mPageSize = result.data.pagecount;
        this.total = result.data.count;
        if (BaseTypeUtils.isListEmpty(result.data.getList())) {
            this.mMyFollowAdapter.setData(null);
            showNoDataTip();
        } else {
            this.mPullToRefreshListView.setPullToRefreshEnabled(true);
            hideNoDataTip();
            this.mMyFollowAdapter.setData(result.data.getList());
        }
        refreshComplete();
        hideNoMoreTip();
    }

    private void loadMoreRefreshView(MyFollowBean result) {
        this.mIsLoadingMore = false;
        if (result.data == null) {
            refreshComplete();
            return;
        }
        this.mTotalPage = result.data.page;
        this.mMyFollowAdapter.addData(result.data.getList());
        refreshComplete();
    }

    private void hideNoDataTip() {
        this.mEmptyTip.setVisibility(8);
        this.mFoot.setVisibility(8);
    }

    private void showNoDataTip() {
        boolean isLogin = PreferencesManager.getInstance().isLogin();
        this.mEmptyTip.setVisibility(0);
        this.mFoot.setVisibility(0);
        if (isLogin) {
            this.mEmptyTip.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700048, getString(2131101576)));
            this.mFoot.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700049, getString(2131101578)));
            return;
        }
        this.mEmptyTip.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700050, getString(2131101580)));
        this.mFoot.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700051, getString(2131101577)));
    }

    private void refreshComplete() {
        if (this.mMyFollowContent != null) {
            this.mMyFollowContent.finish();
        }
        if (this.mPullToRefreshListView != null) {
            this.mPullToRefreshListView.onRefreshComplete();
        }
        if (!isHasMore() && this.total > this.mPageSize) {
            showNoMoreTip();
        }
    }

    public void showNoMoreTip() {
        if (this.mFootViewUtil == null) {
            this.mFootViewUtil = new FootViewUtil(this.mPullToRefreshListView);
        }
        this.mFootViewUtil.showFootNoMore();
    }

    public void showMoreLoading() {
        if (this.mFootViewUtil == null) {
            this.mFootViewUtil = new FootViewUtil(this.mPullToRefreshListView);
        }
        this.mFootViewUtil.showLoadingView();
    }

    public void hideNoMoreTip() {
        if (this.mFootViewUtil == null) {
            this.mFootViewUtil = new FootViewUtil(this.mPullToRefreshListView);
        }
        this.mFootViewUtil.removeFootView();
    }

    private void error(NetworkResponseState state) {
        if (this.mMyFollowContent != null) {
            this.mMyFollowContent.netError(false);
        }
    }

    public boolean isHasMore() {
        return this.mTotalPage > this.mPage + 1;
    }

    public String getActivityName() {
        return getClass().getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn /*2131361912*/:
                finish();
                return;
            case R.id.foot /*2131363912*/:
                if (PreferencesManager.getInstance().isLogin()) {
                    StarRankActivity.launch(this);
                    return;
                } else {
                    LetvLoginActivity.launch((Activity) this);
                    return;
                }
            default:
                return;
        }
    }

    public void onLastItemVisible() {
        if (NetworkUtils.isNetworkAvailable() && isHasMore() && !this.mIsLoadingMore) {
            this.mIsLoadingMore = true;
            requestData(false, true);
        }
    }

    public void onRefresh() {
        this.mPage = 0;
        request(this.mPage, false);
    }

    public void refreshData() {
        if (NetworkUtils.isNetworkAvailable()) {
            requestData(true, false);
        } else {
            ToastUtils.showToast(getActivity(), 2131100332);
        }
    }

    private void requestData(boolean showLoading, boolean isMore) {
        if (this.mMyFollowContent != null && showLoading) {
            this.mMyFollowContent.loading(false);
        }
        if (isMore) {
            this.mPage++;
            showMoreLoading();
            request(this.mPage, true);
            return;
        }
        onRefresh();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (NetworkUtils.isNetworkAvailable()) {
            MyFollowItemBean bean = (MyFollowItemBean) this.mMyFollowAdapter.getItem(position - 1);
            StarActivity.launch(this, bean.follow_id, bean.nickname, PageIdConstant.myAttention);
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.myAttention, "0", "s01", bean.nickname, -1, null);
            return;
        }
        ToastUtils.showToast(getActivity(), 2131100332);
    }
}
