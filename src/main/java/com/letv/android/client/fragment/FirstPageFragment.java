package com.letv.android.client.fragment;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.FirstPageCustomActivity;
import com.letv.android.client.adapter.TopHomeListViewAdapter;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.fragment.HomeBaseFragment.CurrentPage;
import com.letv.android.client.task.FirstPageTask;
import com.letv.android.client.task.FirstPageTask$FirstPageCallBack;
import com.letv.android.client.utils.CardSortManager;
import com.letv.android.client.view.channel.RecommendView;
import com.letv.android.client.view.home.HomeServiceView;
import com.letv.core.BaseApplication;
import com.letv.core.bean.HomeBottomRecommendBean;
import com.letv.core.bean.HomePageBean;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.PlayRecord;
import com.letv.core.bean.PlayRecordList;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class FirstPageFragment extends HomeBaseFragment implements FirstPageTask$FirstPageCallBack, OnClickListener {
    public static boolean sHasShowPlayRecord = false;
    private boolean mCanShowPlayRecord;
    private TextView mCardManager;
    private View mCardManagerLayout;
    private boolean mHasAddFootView;
    private boolean mHasStatisticsPageVisible;
    private TopHomeListViewAdapter mHomePageAdapter;
    private HomePageBean mHomePageBean;
    private View mHomeRecordContainer;
    private TextView mHomeRecordContent;
    private boolean mIsLogin;
    private boolean mIsRequestingAd;
    private ImageView mLetvImageView;
    private PlayRecordList mListRecord;
    private RecommendView mRecommendView;
    private ImageView mRecordClose;
    private OnScrollListener mScroolEvent;
    private HomeServiceView mServiceView;
    private CountDownTimer mTimer;
    private FirstPageTask mfirstPageTask;

    public FirstPageFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mLetvImageView = null;
        this.mIsLogin = false;
        this.mCanShowPlayRecord = false;
        this.mHasStatisticsPageVisible = false;
        this.mScroolEvent = new 4(this);
    }

    public String getTagName() {
        return FirstPageFragment.class.getName();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mIsLogin = PreferencesManager.getInstance().isLogin();
        findView();
        createSearchIcon();
        init();
        requestData(false);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            updataCardChange();
        }
        if (this.mRoot != null) {
            reloadPage(isVisibleToUser);
        }
        if (this.mHomePageBean == null || this.mHomePageAdapter == null || !isVisibleToUser) {
            this.mHasStatisticsPageVisible = false;
        } else {
            statisticsBottomVipExposure();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        reloadPage(hidden);
        if (!hidden) {
            updataCardChange();
        }
        if (this.mHomePageBean == null || this.mHomePageAdapter == null || hidden) {
            this.mHasStatisticsPageVisible = false;
        } else {
            statisticsBottomVipExposure();
        }
    }

    private void statisticsBottomVipExposure() {
        if (!this.mHasStatisticsPageVisible) {
            this.mHasStatisticsPageVisible = true;
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.index, "19", null, null, -1, null);
            if (this.mHomePageBean != null && this.mHomePageAdapter != null) {
                this.mHomePageAdapter.setCanStatistics(true, true);
                this.mHomePageAdapter.initStatisticsStatus();
                if (this.mHomePageBean.mServiceBlock != null && this.mServiceView != null) {
                    this.mServiceView.statisticsViewExposure(this.mHomePageBean.mServiceBlock);
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        this.mPullView.setParams(Boolean.valueOf(true), "TopHomeFragment");
        updataCardChange();
        if (getUserVisibleHint() && !this.mIsHidden) {
            statisticsBottomVipExposure();
        }
    }

    private void findView() {
        this.mListView = (ExpandableListView) this.mPullView.getRefreshableView();
        this.mPullView.setOnScrollListener(this.mScroolEvent);
        this.mHomeRecordContainer = this.mRoot.findViewById(R.id.my_home_record_container);
        this.mHomeRecordContainer.bringToFront();
        this.mHomeRecordContainer.setOnClickListener(this);
        this.mHomeRecordContent = (TextView) this.mHomeRecordContainer.findViewById(R.id.home_record_content);
        this.mHomeRecordContent.setOnClickListener(this);
        this.mRecordClose = (ImageView) this.mHomeRecordContainer.findViewById(R.id.home_close_button);
        this.mRecordClose.setOnClickListener(this);
        this.mCardManagerLayout = LayoutInflater.from(this.mContext).inflate(R.layout.first_page_custom_table, null);
        this.mCardManager = (TextView) this.mCardManagerLayout.findViewById(R.id.card_manager);
        this.mCardManager.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700092, 2131100369));
        this.mCardManager.setOnClickListener(this);
    }

    private void init() {
        this.mfirstPageTask = new FirstPageTask(BaseApplication.getInstance());
        this.mfirstPageTask.initFirstPageCallBack(this);
        this.mHomePageAdapter = new TopHomeListViewAdapter(getActivity());
        this.mListView.setAdapter(this.mHomePageAdapter);
    }

    private void updataCardChange() {
        if (PreferencesManager.getInstance().getHasEditCard() && this.mHomePageBean != null) {
            refrenshView(CardSortManager.getHomePageBean(this.mHomePageBean), false);
            PreferencesManager.getInstance().setHasEditCard(false);
        }
    }

    void loadData(boolean isRefresh) {
        getFocusAd("");
        if (isRefresh) {
            this.mfirstPageTask.requestHomePageData(this.mPageCardList, isRefresh);
            this.mfirstPageTask.requestHomeLive(isRefresh);
            if (PreferencesManager.getInstance().getBottomRecommendSwitch()) {
                this.mfirstPageTask.requestBottomRecommend(isRefresh);
            }
        } else if (this.mPageCardList != null && this.mHomePageAdapter != null) {
            this.mHomePageAdapter.setPageCardList(this.mPageCardList);
            initRequest();
        }
    }

    private void initRequest() {
        loading();
        this.mfirstPageTask.requestHomePageData(this.mPageCardList, false);
        this.mfirstPageTask.requestHomeLive(false);
        if (PreferencesManager.getInstance().getBottomRecommendSwitch()) {
            this.mfirstPageTask.requestBottomRecommend(false);
        }
        if (this.mIsLogin) {
            this.mfirstPageTask.requestCustomerLevel();
            if (!sHasShowPlayRecord) {
                this.mfirstPageTask.requestPlayRecord();
            }
        } else if (!sHasShowPlayRecord) {
            this.mListRecord = DBManager.getInstance().getPlayTrace().getLastPlayTrace(1);
        }
    }

    public void reloadPage(boolean isVisible) {
        if (!isVisible) {
            return;
        }
        if (this.mHomePageBean == null || BaseTypeUtils.isListEmpty(this.mHomePageBean.block)) {
            requestData(false);
        }
    }

    public void fetchDataResult(LetvBaseBean result, boolean isNet) {
        if (result == null && this.mHomePageBean == null) {
            showErrorView(isNet);
        } else if (result == null) {
            refreshComplete();
            finishLoading();
        } else if (result instanceof HomePageBean) {
            if (!this.mHasAddFootView) {
                this.mHasAddFootView = true;
                addFootView();
            }
            refrenshView((HomePageBean) result, isNet);
            setRecordContent((PlayRecord) BaseTypeUtils.getElementFromList(this.mListRecord, 0));
        } else if (result instanceof LiveRemenListBean) {
            doLiveData((LiveRemenListBean) result, isNet);
        } else if (result instanceof HomeBottomRecommendBean) {
            doRecommendView((HomeBottomRecommendBean) result);
        } else if (result instanceof PlayRecordList) {
            this.mListRecord = (PlayRecordList) result;
            setRecordContent((PlayRecord) BaseTypeUtils.getElementFromList(this.mListRecord, 0));
        }
    }

    private void showErrorView(boolean isNet) {
        if (isNet) {
            showNetError();
        } else {
            showDataError();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_manager /*2131362588*/:
                FirstPageCustomActivity.launch(this.mContext);
                StatisticsUtils.statisticsActionInfo(this.mContext, DataConstant.P3, "0", "1a", getString(2131100132), 2, null);
                return;
            case R.id.my_home_record_container /*2131362712*/:
                setHomeRecordVisible(false);
                doPLayRecordClick();
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "1c", null, 1, null, PageIdConstant.index, null, null, null, null, null);
                return;
            case R.id.home_close_button /*2131362822*/:
                setHomeRecordVisible(false);
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "1c", null, 2, null, PageIdConstant.index, null, null, null, null, null);
                return;
            case R.id.home_record_content /*2131362823*/:
                setHomeRecordVisible(false);
                doPLayRecordClick();
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "1c", null, 1, null, PageIdConstant.index, null, null, null, null, null);
                return;
            default:
                return;
        }
    }

    private void doRecommendView(HomeBottomRecommendBean bean) {
        if (bean != null) {
            if (this.mRecommendView == null) {
                this.mRecommendView = new RecommendView(this.mContext);
            }
            this.mRecommendView.addFooterRecommendView(bean);
        }
    }

    private void addFootView() {
        if (this.mListView != null && this.mListView.getFooterViewsCount() == 0) {
            if (PreferencesManager.getInstance().getBottomRecommendSwitch()) {
                if (this.mRecommendView == null) {
                    this.mRecommendView = new RecommendView(this.mContext);
                }
                this.mListView.addFooterView(this.mRecommendView.getView());
            }
            if (!(this.mCardManagerLayout == null || LetvUtils.isInHongKong())) {
                this.mListView.addFooterView(this.mCardManagerLayout);
            }
            addFootLetvImageView();
        }
    }

    private void createSearchIcon() {
        if (PreferencesManager.getInstance().getSearchShortcut()) {
            PreferencesManager.getInstance().setSearchShortcut(false);
        }
    }

    public void onPause() {
        super.onPause();
        this.mHasStatisticsPageVisible = false;
        setHomeRecordVisible(false);
    }

    private void refrenshView(HomePageBean result, boolean isNet) {
        refreshComplete();
        finishLoading();
        if (result != null) {
            this.mHomePageBean = result;
            setFocusView(result.focus);
            setServiceView(isNet);
            if (!(this.mHomePageBean.mADPosition == -1 || this.mHomePageBean.adInfo == null || this.mIsRequestingAd)) {
                getADBannerForBlock();
            }
            if (this.mHomePageAdapter != null) {
                this.mListView.setAdapter(this.mHomePageAdapter);
                this.mHomePageAdapter.setSearchWords(this.mHomePageBean.searchWords);
                this.mHomePageAdapter.setCanStatistics(isNet, getUserVisibleHint());
                this.mHomePageAdapter.setList(this.mHomePageBean);
                setListView();
            }
        }
    }

    private void getADBannerForBlock() {
        new Thread(new 1(this)).start();
    }

    private void setListView() {
        this.mHomePageAdapter.notifyDataSetChanged();
        for (int i = 0; i < this.mHomePageAdapter.getGroupCount(); i++) {
            this.mListView.expandGroup(i);
        }
        this.mListView.setGroupIndicator(null);
        this.mListView.setOnGroupClickListener(new 2(this));
    }

    private void doLiveData(LiveRemenListBean result, boolean isNet) {
        LogInfo.log("zhaoxiang", "--------->doLiveData");
        if (!BaseTypeUtils.isListEmpty(result.mRemenList) && this.mHomePageAdapter != null) {
            this.mHomePageAdapter.setLiveList(result.mRemenList, null, isNet);
            this.mHomePageAdapter.notifyDataSetChanged();
        }
    }

    private void addFootLetvImageView() {
        if (this.mLetvImageView == null) {
            this.mLetvImageView = new ImageView(getActivity());
        }
        this.mLetvImageView.setLayoutParams(new LayoutParams(-1, -2));
        this.mLetvImageView.setPadding(0, UIsUtils.zoomWidth(10), 0, UIsUtils.zoomWidth(16));
        this.mLetvImageView.setImageResource(2130838157);
        this.mLetvImageView.setScaleType(ScaleType.CENTER);
        if (this.mListView != null) {
            this.mListView.addFooterView(this.mLetvImageView);
        }
    }

    private void setServiceView(boolean isNet) {
        if (!LetvUtils.isGooglePlay()) {
            if (this.mHomePageBean != null && this.mHomePageBean.mServiceBlock != null) {
                try {
                    if (this.mServiceView == null) {
                        this.mServiceView = new HomeServiceView(this.mContext);
                    }
                    this.mServiceView.setData(this.mHomePageBean.mServiceBlock);
                    if (getUserVisibleHint() && isNet) {
                        this.mServiceView.statisticsViewExposure(this.mHomePageBean.mServiceBlock);
                    }
                    if (this.mListView.getHeaderViewsCount() > 1 && VERSION.SDK_INT >= 14) {
                        this.mListView.removeHeaderView(this.mServiceView.getView());
                    }
                    this.mListView.addHeaderView(this.mServiceView.getView());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (this.mListView.getHeaderViewsCount() > 1 && this.mServiceView != null) {
                this.mListView.removeHeaderView(this.mServiceView.getView());
                this.mServiceView = null;
            }
        }
    }

    public void onDestroy() {
        LetvApplication.getInstance().setPlayerFavouriteClick(false);
        super.onDestroy();
        sHasShowPlayRecord = true;
        if (this.mfirstPageTask != null) {
            this.mfirstPageTask.onDestroy();
        }
        LogInfo.log("zhaoxiang", "onDestroy");
        this.mHomePageAdapter = null;
        this.mHasAddFootView = false;
        if (this.mHomePageBean != null) {
            this.mHomePageBean = null;
        }
        this.mHomeRecordContainer = null;
    }

    private void doPLayRecordClick() {
        if (BaseTypeUtils.getElementFromList(this.mListRecord, 0) != null) {
            PlayRecord trace = (PlayRecord) this.mListRecord.get(0);
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(getActivity()).create(trace.type == 1 ? (long) trace.albumId : 0, (long) trace.videoId, 4)));
            this.mRoot.removeView(this.mHomeRecordContainer);
        }
    }

    private void setRecordContent(PlayRecord trace) {
        if (trace != null && this.mHomePageBean != null && this.mCanShowPlayRecord && this.mHomeRecordContent != null) {
            try {
                if (!sHasShowPlayRecord && !TextUtils.isEmpty(trace.title)) {
                    String arg1 = LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70011, this.mContext.getResources().getString(2131100173));
                    sHasShowPlayRecord = true;
                    String arg2 = trace.title;
                    this.mHomeRecordContent.setText(String.format(this.mContext.getResources().getString(2131100170), new Object[]{arg1, arg2}));
                    setHomeRecordVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeRecommendView() {
        try {
            if (this.mListView != null && this.mRecommendView != null && this.mListView.getHeaderViewsCount() > 1 && VERSION.SDK_INT >= 14) {
                this.mListView.removeHeaderView(this.mRecommendView.getView());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHomeRecord(boolean show) {
        this.mCanShowPlayRecord = show;
        if (show && BaseTypeUtils.getElementFromList(this.mListRecord, 0) != null) {
            setRecordContent((PlayRecord) this.mListRecord.get(0));
        }
    }

    public void setHomeRecordVisible(boolean show) {
        if (this.mHomeRecordContainer != null) {
            try {
                if (LetvApplication.getInstance().getShowRecordFlag() && show && this.mHomeRecordContainer.getVisibility() != 0) {
                    this.mHomeRecordContainer.bringToFront();
                    this.mHomeRecordContainer.setVisibility(0);
                    this.mTimer = new 3(this, 10000, 10000);
                    this.mTimer.start();
                } else if (!show) {
                    if (this.mTimer != null) {
                        this.mTimer.cancel();
                    }
                    this.mHomeRecordContainer.setVisibility(8);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    CurrentPage getCurrentPage() {
        return CurrentPage.HOME;
    }
}
