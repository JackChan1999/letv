package com.letv.android.client.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.http.LetvSimpleAsyncTask;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.view.HomeFocusViewPager;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshExpandableListView;
import com.letv.android.client.view.home.HomeFocusView;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.PageCardListBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.pagecard.PageCardFetcher;
import com.letv.core.pagecard.PageCardFetcher.PageCardCallback;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

public abstract class HomeBaseFragment extends LetvBaseFragment implements OnRefreshListener {
    protected ArrayList<AdElementMime> mAdInfo;
    protected TextView mDoblyTextView;
    protected HomeFocusView mFocusView;
    protected HomeFocusViewPager mFocusViewpager;
    private boolean mIsVisibleToUser;
    protected ExpandableListView mListView;
    protected PageCardListBean mPageCardList;
    protected PullToRefreshExpandableListView mPullView;
    protected RequestFocusAd mRequestFocusAd;
    protected PublicLoadLayout mRoot;

    public enum CurrentPage {
        NORMAL,
        HOME,
        CHANNEL
    }

    protected class RequestFocusAd extends LetvSimpleAsyncTask<ArrayList<AdElementMime>> {
        String cid;
        final /* synthetic */ HomeBaseFragment this$0;

        public RequestFocusAd(HomeBaseFragment this$0, Context context, String cid) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            super(context);
            this.cid = "0";
            this.cid = cid;
        }

        public ArrayList<AdElementMime> doInBackground() {
            if (this.this$0.getCurrentPage() == CurrentPage.HOME) {
                return AdsManagerProxy.getInstance(this.this$0.getActivity()).getFocusAdInfo();
            }
            return AdsManagerProxy.getInstance(this.this$0.getActivity()).getChannelFocusAdInfo(this.cid);
        }

        public void onPostExecute(ArrayList<AdElementMime> result) {
            this.this$0.mAdInfo = result;
            this.this$0.addAdToFocus(result, true);
        }

        public void onPreExecute() {
        }
    }

    abstract CurrentPage getCurrentPage();

    abstract void loadData(boolean z);

    public HomeBaseFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsVisibleToUser = true;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.mIsVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            focusStartMove();
        } else {
            focusStopMove();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.mIsVisibleToUser = !hidden;
        if (hidden) {
            focusStopMove();
        } else {
            focusStartMove();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRoot = PublicLoadLayout.createPage(this.mContext, (int) R.layout.fragment_top_home, true);
        initView();
        return this.mRoot;
    }

    private void initView() {
        this.mPullView = (PullToRefreshExpandableListView) this.mRoot.findViewById(R.id.pull_list);
        this.mDoblyTextView = (TextView) this.mRoot.findViewById(R.id.channel_detail_dobly_tag);
        this.mListView = (ExpandableListView) this.mPullView.getRefreshableView();
        this.mListView.setScrollingCacheEnabled(false);
        this.mPullView.setParams(Boolean.valueOf(true), FragmentConstant.TAG_FRAGMENT_HOME);
        this.mPullView.setOnRefreshListener(new OnRefreshListener(this) {
            final /* synthetic */ HomeBaseFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onRefresh() {
                if (this.this$0.isNetworkAvailable()) {
                    this.this$0.requestData(true);
                } else {
                    this.this$0.mPullView.onRefreshComplete();
                }
            }
        });
        this.mRoot.setRefreshData(new RefreshData(this) {
            final /* synthetic */ HomeBaseFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.requestData(false);
            }
        });
    }

    protected void requestData(boolean isRefresh) {
        if (!isRefresh) {
            loading();
        }
        fetchPageCardData(isRefresh);
    }

    protected void loading() {
        if (this.mRoot != null) {
            this.mRoot.loading(false);
        }
    }

    private void fetchPageCardData(final boolean isRefresh) {
        if (this.mPageCardList != null) {
            loadData(isRefresh);
        } else {
            PageCardFetcher.fetchPageCard(this.mContext, new PageCardCallback(this) {
                final /* synthetic */ HomeBaseFragment this$0;

                public void onFetch(PageCardListBean pageCardList) {
                    this.this$0.mPageCardList = pageCardList;
                    this.this$0.loadData(isRefresh);
                }
            });
        }
    }

    public void onRefresh() {
        if (isNetworkAvailable()) {
            requestData(true);
        } else {
            refreshComplete();
        }
    }

    public void refreshComplete() {
        if (this.mPullView != null) {
            this.mPullView.onRefreshComplete();
        }
    }

    protected void finishLoading() {
        if (this.mRoot != null) {
            this.mRoot.finish();
        }
    }

    protected boolean isNetworkAvailable() {
        if (NetworkUtils.isNetworkAvailable()) {
            return true;
        }
        ToastUtils.showToast(getActivity(), 2131100332);
        return false;
    }

    protected void showNetError() {
        if (this.mRoot != null) {
            this.mRoot.netError(false);
        }
    }

    protected void showDataError() {
        if (this.mRoot != null) {
            this.mRoot.dataError(false);
        }
    }

    protected void showDataError(boolean isFilter, boolean filterTypeNull) {
        if (this.mRoot != null) {
            this.mRoot.dataFilterError(false, isFilter, filterTypeNull);
        }
    }

    protected void setFocusView(List<HomeMetaData> focusBlocks) {
        if (BaseTypeUtils.isListEmpty(focusBlocks)) {
            removeFocusView();
            return;
        }
        try {
            if (this.mFocusView == null) {
                this.mFocusView = new HomeFocusView(this.mContext);
                this.mFocusViewpager = this.mFocusView.getViewPager();
                this.mFocusViewpager.setCurrentPage(getCurrentPage());
                this.mListView.addHeaderView(this.mFocusView.getFocusView());
            }
            this.mFocusView.setList(focusBlocks);
            addAdToFocus(this.mAdInfo, false);
            if (this.mContext instanceof MainActivity) {
                this.mFocusViewpager.setParentView(((MainActivity) this.mContext).getHomeFragment().getViewPager(), this.mPullView);
            }
            focusStartMove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void removeFocusView() {
        if (this.mFocusView != null && ((ExpandableListView) this.mPullView.getRefreshableView()).getHeaderViewsCount() > 0) {
            ((ExpandableListView) this.mPullView.getRefreshableView()).removeHeaderView(this.mFocusView.getFocusView());
            this.mFocusView.destroy();
            this.mFocusView = null;
        }
    }

    protected void focusStartMove() {
        if (this.mFocusViewpager != null && this.mIsVisibleToUser) {
            this.mFocusViewpager.startMove();
        }
    }

    protected void focusStopMove() {
        if (this.mFocusViewpager != null) {
            this.mFocusViewpager.stopMove();
        }
    }

    public void onResume() {
        focusStartMove();
        super.onResume();
    }

    public void onPause() {
        focusStopMove();
        super.onPause();
    }

    public void onDestroy() {
        removeFocusView();
        if (this.mPullView != null) {
            this.mPullView.removeAllViews();
            this.mListView = null;
        }
        if (this.mRequestFocusAd != null) {
            this.mRequestFocusAd.cancel(true);
            this.mRequestFocusAd = null;
        }
        if (this.mRoot != null) {
            this.mRoot.removeAllViews();
            this.mRoot = null;
        }
        cleanAd();
        super.onDestroy();
    }

    public boolean scrollToTop() {
        if (this.mListView == null) {
            return false;
        }
        if (this.mListView.getFirstVisiblePosition() == 0) {
            return true;
        }
        this.mListView.setSelection(1);
        this.mListView.smoothScrollToPosition(1);
        return false;
    }

    protected void getFocusAd(String cid) {
        this.mRequestFocusAd = new RequestFocusAd(this, getActivity(), cid);
        if (AdsManagerProxy.getInstance(getActivity()).isShowAd() && this.mRequestFocusAd != null) {
            try {
                this.mRequestFocusAd.start();
            } catch (Exception e) {
            }
        }
    }

    protected void addAdToFocus(ArrayList<AdElementMime> adInfo, boolean isNet) {
        if (this.mFocusView != null) {
            if (isNet || !BaseTypeUtils.isListEmpty(adInfo)) {
                this.mFocusView.setAdList(adInfo);
                cleanAd();
            }
        }
    }

    private void cleanAd() {
        if (this.mAdInfo != null) {
            this.mAdInfo.clear();
            this.mAdInfo = null;
        }
    }
}
