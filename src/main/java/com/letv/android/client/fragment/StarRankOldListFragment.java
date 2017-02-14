package com.letv.android.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.StarRankOldListAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.parser.StarRankOldListParser;
import com.letv.android.client.utils.FootViewUtil;
import com.letv.android.client.view.PullToRefreshBase.OnLastItemVisibleListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.StarRankOldList;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class StarRankOldListFragment extends LetvBaseFragment {
    private final int PAGE_SIZE;
    private int mCurrentPage;
    private FootViewUtil mFootViewUtil;
    private boolean mIsLoadMore;
    private boolean mIsRefernce;
    private PullToRefreshListView mPullView;
    private PublicLoadLayout mRootView;
    private StarRankOldListAdapter mStarRankingAdapter;
    private OnLastItemVisibleListener onLastItemVisibleListener;

    public StarRankOldListFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.PAGE_SIZE = 12;
        this.mCurrentPage = 1;
        this.mIsLoadMore = false;
        this.mIsRefernce = false;
        this.onLastItemVisibleListener = new 4(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = PublicLoadLayout.createPage(this.mContext, (int) R.layout.fragment_star_rank_old_list, true);
        return this.mRootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPullView = (PullToRefreshListView) view.findViewById(R.id.star_rank_list_view);
        init();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStarRankOldList(false);
    }

    public void onResume() {
        super.onResume();
    }

    private void init() {
        this.mPullView.setParams(Boolean.valueOf(true), "OldStarRankingFragment");
        this.mStarRankingAdapter = new StarRankOldListAdapter(this.mContext);
        ((ListView) this.mPullView.getRefreshableView()).setAdapter(this.mStarRankingAdapter);
        this.mPullView.setOnLastItemVisibleListener(this.onLastItemVisibleListener);
        this.mFootViewUtil = new FootViewUtil(this.mPullView);
        this.mPullView.setOnRefreshListener(new 1(this));
        this.mRootView.setRefreshData(new 2(this));
    }

    private void getStarRankOldList(boolean isPullToRefresh) {
        if (!isPullToRefresh) {
            this.mRootView.loading(false);
        }
        Volley.getQueue().cancelWithTag(getVolleyTag());
        new LetvRequest(StarRankOldList.class).setCache(new VolleyDiskCache(getCacheFileName())).setParser(new StarRankOldListParser()).setCallback(new 3(this, isPullToRefresh)).add();
    }

    private void refreshView(boolean isPullToRefresh, StarRankOldList result) {
        this.mRootView.finish();
        if (isPullToRefresh) {
            this.mPullView.onRefreshComplete();
        }
        if (result != null && result.mList != null) {
            if (this.mStarRankingAdapter == null) {
                this.mStarRankingAdapter = new StarRankOldListAdapter(this.mContext);
            }
            ((ListView) this.mPullView.getRefreshableView()).setAdapter(this.mStarRankingAdapter);
            this.mStarRankingAdapter.setList(result.mList);
        }
    }

    private String getCacheFileName() {
        return "StarRankOldListFragment_CacheFileName";
    }

    private String getVolleyTag() {
        return "StarRankOldListFragment_VolleyTag";
    }

    public int getContainerId() {
        return 0;
    }

    public String getTagName() {
        return null;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        Volley.getQueue().cancelWithTag(getVolleyTag());
        this.mPullView.setOnScrollListener(null);
        super.onDestroy();
    }
}
