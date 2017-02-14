package com.letv.android.client.fragment;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import com.letv.android.client.R;
import com.letv.android.client.adapter.channel.ChannelDetailExpandableListAdapter;
import com.letv.android.client.adapter.channel.ChannelDetailListAdapter;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.fragment.HomeBaseFragment.CurrentPage;
import com.letv.android.client.fragment.HomeFragment.ScrollStateChange;
import com.letv.android.client.task.ChannelDetailTask;
import com.letv.android.client.task.RequestSiftedOrDolbyDatas;
import com.letv.android.client.view.FootSearchView;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.channel.ChannelFilterTypes;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.bean.channel.TopList;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.Iterator;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class ChannelDetailFragment extends ChannelBaseFragment {
    private final int DELAY_TAG;
    private final int DELAY_TIME;
    private final int SLOW_DELAY_TIME;
    float dowmY;
    private boolean isShowable;
    private boolean mCanShowFilterBar;
    private boolean mDataAddToView;
    private RelativeLayout mFilterBar;
    private FootSearchView mFootSearchView;
    private Handler mHandler;
    private boolean mHasStatisticsPageVisible;
    private boolean mIsFilterBarShow;
    private boolean mIsScrolling;
    private boolean mIsVisible;
    private ChannelDetailExpandableListAdapter mListAdapter;
    private ChannelNavigation mNavigation;
    private OnScrollListener mScroolEvent;
    private CompositeSubscription mSubscription;
    OnTouchListener onTouchListener;

    public ChannelDetailFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsFilterBarShow = false;
        this.mCanShowFilterBar = false;
        this.mHasStatisticsPageVisible = false;
        this.SLOW_DELAY_TIME = 200;
        this.DELAY_TIME = 1000;
        this.DELAY_TAG = 1001;
        this.mDataAddToView = true;
        this.mHandler = new Handler(this) {
            final /* synthetic */ ChannelDetailFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1001:
                        this.this$0.requestData(false);
                        return;
                    default:
                        return;
                }
            }
        };
        this.mScroolEvent = new OnScrollListener(this) {
            final /* synthetic */ ChannelDetailFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (this.this$0.mListAdapter instanceof OnScrollListener) {
                    this.this$0.mListAdapter.onScrollStateChanged(view, scrollState);
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.this$0.mListAdapter instanceof OnScrollListener) {
                    this.this$0.mListAdapter.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (firstVisibleItem == 0) {
                    this.this$0.mFilterBar.clearAnimation();
                    this.this$0.mFilterBar.setVisibility(8);
                    this.this$0.isShowable = false;
                    this.this$0.mIsFilterBarShow = false;
                    this.this$0.focusStartMove();
                    return;
                }
                this.this$0.focusStopMove();
                this.this$0.isShowable = true;
            }
        };
        this.isShowable = false;
        this.dowmY = 0.0f;
        this.onTouchListener = new OnTouchListener(this) {
            final /* synthetic */ ChannelDetailFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                if (this.this$0.mCanShowFilterBar) {
                    if (this.this$0.isShowable) {
                        switch (event.getAction()) {
                            case 0:
                                this.this$0.dowmY = event.getY();
                                break;
                            case 1:
                                this.this$0.dowmY = 0.0f;
                                break;
                            case 2:
                                if (this.this$0.dowmY == 0.0f) {
                                    this.this$0.dowmY = event.getY();
                                }
                                if (event.getY() - this.this$0.dowmY > ((float) UIsUtils.dipToPx(38.0f))) {
                                    if (this.this$0.mFilterBar.getVisibility() == 8) {
                                        this.this$0.controllFilterView(true);
                                    }
                                    this.this$0.dowmY = event.getY();
                                }
                                if (event.getY() - this.this$0.dowmY < ((float) (-UIsUtils.dipToPx(38.0f)))) {
                                    if (this.this$0.mFilterBar.getVisibility() == 0) {
                                        this.this$0.controllFilterView(false);
                                    }
                                    this.this$0.dowmY = event.getY();
                                    break;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    this.this$0.dowmY = 0.0f;
                }
                return false;
            }
        };
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mChannel = (Channel) bundle.getSerializable("channel");
        }
        init();
        registerRxBus();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;
        if (this.mChannel == null || this.mListAdapter == null || !isVisibleToUser) {
            this.mHasStatisticsPageVisible = false;
        } else {
            statisticsBottomVipExposure();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        boolean z;
        super.onHiddenChanged(hidden);
        this.mIsVisible = !hidden;
        if (hidden) {
            z = false;
        } else {
            z = true;
        }
        reloadPage(z, true);
        if (this.mChannel == null || this.mListAdapter == null || hidden) {
            this.mHasStatisticsPageVisible = false;
        } else {
            statisticsBottomVipExposure();
        }
    }

    private void reloadPage(boolean isVisible, boolean needCheckNet) {
        if ((!isNetworkAvailable() && needCheckNet) || this.mChannel == null || this.mChannel.id == 1001 || !isVisible) {
            return;
        }
        if (this.mChannelHomeBean == null || BaseTypeUtils.isListEmpty(this.mChannelHomeBean.block)) {
            if (this.mChanelDetailtask != null) {
                this.mChanelDetailtask.onDestroy();
            }
            this.mHandler.removeMessages(1001);
            this.mHandler.sendEmptyMessageDelayed(1001, 200);
        }
    }

    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && isVisible() && this.mChannel != null && !TextUtils.equals(PageIdConstant.pushPage, StatisticsUtils.getPageId())) {
            statisticsBottomVipExposure();
        }
    }

    private void statisticsBottomVipExposure() {
        if (!this.mHasStatisticsPageVisible) {
            this.mHasStatisticsPageVisible = true;
            if (currentPidIsVip(this.mChannel.pageid)) {
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.vipCategoryPage, "19", null, null, -1, null);
            } else {
                StatisticsUtils.statisticsActionInfo(this.mContext, getPageId(), "19", null, this.mChannel.name, -1, "scid=" + (this.mChannel != null ? this.mChannel.pageid : ""), this.mChannel != null ? this.mChannel.id + "" : "", null, null, null, null);
            }
            if (this.mListAdapter != null) {
                this.mListAdapter.setVisibleToUser(true);
                this.mListAdapter.initStatisticsStatus();
            }
        }
    }

    private void init() {
        this.mCanShowFilterBar = false;
        UIsUtils.inflate(getActivity(), R.layout.channel_filter_layout, this.mRoot, true);
        this.mFilterBar = (RelativeLayout) this.mRoot.findViewById(R.id.title_main_filter);
        this.mFilterBar.setVisibility(8);
        if (this.mChannel.id == 1001) {
            this.mDolbyListAdapter = new ChannelDetailListAdapter(this.mContext, this.mChannel.id);
            ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mDolbyListAdapter);
        } else {
            this.mListAdapter = new ChannelDetailExpandableListAdapter(this.mContext);
            this.mListAdapter.initMorecallBack(this);
            this.mListAdapter.setStatisticsInfo(this.mChannel.id, this.mChannel.pageid);
            ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mListAdapter);
        }
        this.mFootSearchView = new FootSearchView(this.mContext, this.mChannel.id);
        this.mPullView.setOnScrollListener(this.mScroolEvent);
        ((ExpandableListView) this.mPullView.getRefreshableView()).setOnTouchListener(this.onTouchListener);
        this.mFilterBar.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ChannelDetailFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                UIControllerUtils.gotoChannelDetailItemActivity(this.this$0.mContext, this.this$0.mChannel, false, this.this$0.mNavigation, null, null);
                StatisticsUtils.statisticsActionInfo(this.this$0.mContext, PageIdConstant.vipCategoryPage, "0", "h13", null, 1, "scid=" + (this.this$0.mChannel != null ? this.this$0.mChannel.pageid : ""), this.this$0.mChannel != null ? this.this$0.mChannel.id + "" : "", null, null, null, null);
            }
        });
        this.mRequestSiftedOrDolbyData = new RequestSiftedOrDolbyDatas(this.mContext, this.mChannel);
        this.mRequestSiftedOrDolbyData.setRequestCallBack(this);
        if (this.mChannel != null) {
            this.mChanelDetailtask = new ChannelDetailTask(BaseApplication.getInstance(), this.mChannel.id, this.mChannel.pageid);
            this.mChanelDetailtask.initCallBack(this);
            this.mHandler.sendEmptyMessageDelayed(1001, 1000);
            this.mRoot.loading(false);
            return;
        }
        showDataError();
    }

    void loadData(boolean isPullToRefresh) {
        if (this.mChannel != null) {
            if (this.mChannel.id == 1001 && this.mPageCardList != null && this.mRequestSiftedOrDolbyData != null) {
                this.mRequestSiftedOrDolbyData.setStartPosition(0);
                this.mRequestSiftedOrDolbyData.getChannelListAfterSift(false);
                if (this.mDolbyListAdapter != null) {
                    this.mDolbyListAdapter.setPageCardList(this.mPageCardList);
                }
            } else if (this.mChanelDetailtask != null && this.mPageCardList != null) {
                this.mChanelDetailtask.requestChannelDetailList(isPullToRefresh, false, 1, this.mPageCardList);
                if (this.mListAdapter != null) {
                    this.mListAdapter.setPageCardList(this.mPageCardList);
                }
                getFocusAd(String.valueOf(this.mChannel.id));
            }
        }
    }

    private void registerRxBus() {
        if (this.mSubscription == null) {
            this.mSubscription = new CompositeSubscription();
        }
        if (!this.mSubscription.hasSubscriptions()) {
            this.mSubscription.add(RxBus.getInstance().toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>(this) {
                final /* synthetic */ ChannelDetailFragment this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void call(Object o) {
                    if (o instanceof ScrollStateChange) {
                        ScrollStateChange scrollChange = (ScrollStateChange) o;
                        if (this.this$0.mDataAddToView || !(scrollChange.idle || this.this$0.mIsVisible)) {
                            boolean z;
                            ChannelDetailFragment channelDetailFragment = this.this$0;
                            if (scrollChange.idle || this.this$0.mIsVisible) {
                                z = false;
                            } else {
                                z = true;
                            }
                            channelDetailFragment.mIsScrolling = z;
                            if (scrollChange.idle || this.this$0.mIsVisible) {
                                this.this$0.reloadPage(true, false);
                                return;
                            } else if (this.this$0.mChanelDetailtask != null) {
                                this.this$0.mChanelDetailtask.onDestroy();
                                return;
                            } else {
                                return;
                            }
                        }
                        this.this$0.mIsScrolling = false;
                        this.this$0.setRootView(this.this$0.mChannelHomeBean, true);
                    }
                }
            }));
        }
    }

    public void onChannelSuccess(ChannelHomeBean result, boolean isRefrence, boolean isLoadingMore, boolean isFromNet) {
        refreshComplete();
        if ((result == null || BaseTypeUtils.isListEmpty(result.block)) && this.mChannelHomeBean == null) {
            showDataError();
        } else if (result != null) {
            setRootView(result, isFromNet);
        }
    }

    public void onTopSuccess(TopList result) {
    }

    public void onSiftListSuccess(ChannelFilterTypes result) {
    }

    public void dataError(boolean isLoadingMore) {
        if (this.mChannelHomeBean == null) {
            showDataError();
        }
    }

    public void netError(boolean isLoadingMore) {
        if (this.mChannelHomeBean == null) {
            showNetError();
        }
    }

    public void showErrorPage(boolean isNetError, boolean isFirstPage) {
        refreshComplete();
        if (isNetError) {
            showNetError();
        } else {
            showDataError();
        }
    }

    void moreSelect(int type, ArrayList<SiftKVP> arrayList, String name) {
    }

    private void setRootView(ChannelHomeBean result, boolean isFromNet) {
        if (result != null) {
            this.mChannelHomeBean = result;
            if (this.mIsScrolling) {
                this.mDataAddToView = false;
                return;
            }
            this.mDataAddToView = true;
            if (!BaseTypeUtils.isListEmpty(this.mChannelHomeBean.searchWords)) {
                initFooterSearchView(this.mChannelHomeBean.searchWords);
            }
            setFocusView(this.mChannelHomeBean.focus);
            if ((this.mChannel.id == 1000 || currentPidIsVip(this.mChannel.pageid)) && this.mChannelHomeBean.isShowTextMark) {
                setVipTipsView();
            }
            initTabView(result);
            if (this.mChannelHomeBean.isShowLiveBlock) {
                this.mChanelDetailtask.requestLiveList(RequestManner.NETWORK_THEN_CACHE);
            } else {
                finishLoading();
            }
            setFilterView();
            this.mPullView.setPullToRefreshEnabled(true);
            ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mListAdapter);
            this.mListAdapter.setDataList((ExpandableListView) this.mPullView.getRefreshableView(), this.mChannelHomeBean, isFromNet);
        }
    }

    private void setFilterView() {
        if (this.mChannelHomeBean.tabIndex != -1 && !BaseTypeUtils.isListEmpty(this.mChannelHomeBean.block) && this.mChannelHomeBean.block.size() >= this.mChannelHomeBean.tabIndex) {
            HomeBlock block = (HomeBlock) this.mChannelHomeBean.block.get(this.mChannelHomeBean.tabIndex);
            if (!BaseTypeUtils.isListEmpty(block.tabsNavigation)) {
                Iterator it = block.tabsNavigation.iterator();
                while (it.hasNext()) {
                    ChannelNavigation mNavigation = (ChannelNavigation) it.next();
                    if (mNavigation.subTitle == 1) {
                        this.mCanShowFilterBar = true;
                        this.mNavigation = mNavigation;
                    }
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
        focusStopMove();
        this.mHasStatisticsPageVisible = false;
    }

    private boolean currentPidIsVip(String pid) {
        if (TextUtils.equals(AlbumInfo.Channel.VIP_PAGEID_TEST, pid) || TextUtils.equals(AlbumInfo.Channel.VIP_PAGEID, pid) || TextUtils.equals(AlbumInfo.Channel.VIP_PAGEID_HONGKONG, pid)) {
            return true;
        }
        return false;
    }

    private void controllFilterView(boolean isShow) {
        if (this.isShowable) {
            if (isShow && !this.mIsFilterBarShow) {
                this.mFilterBar.setVisibility(0);
                TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, (float) (-UIsUtils.dipToPx(38.0f)), 0.0f);
                animation.setDuration(200);
                animation.setFillAfter(true);
                this.mFilterBar.setAnimation(animation);
                this.mIsFilterBarShow = true;
                animation.setAnimationListener(new AnimationListener(this) {
                    final /* synthetic */ ChannelDetailFragment this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        this.this$0.mFilterBar.setTop(0);
                        this.this$0.mFilterBar.clearAnimation();
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                this.mFilterBar.startAnimation(animation);
            }
            if (!isShow && this.mIsFilterBarShow) {
                this.mFilterBar.setVisibility(8);
                TranslateAnimation animation1 = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-UIsUtils.dipToPx(38.0f)));
                animation1.setDuration(200);
                animation1.setFillAfter(true);
                this.mFilterBar.setAnimation(animation1);
                this.mIsFilterBarShow = false;
                animation1.setAnimationListener(new AnimationListener(this) {
                    final /* synthetic */ ChannelDetailFragment this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        this.this$0.mFilterBar.setTop(-UIsUtils.dipToPx(38.0f));
                        this.this$0.mFilterBar.clearAnimation();
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                this.mFilterBar.startAnimation(animation1);
            }
        }
    }

    private void initFooterSearchView(ArrayList<String> arrayList) {
        try {
            this.mFootSearchView.setList(this.mChannelHomeBean.searchWords);
            if (this.mPullView != null && ((ExpandableListView) this.mPullView.getRefreshableView()).getFooterViewsCount() >= 1 && VERSION.SDK_INT >= 14) {
                ((ExpandableListView) this.mPullView.getRefreshableView()).removeFooterView(this.mFootSearchView);
            }
            ((ExpandableListView) this.mPullView.getRefreshableView()).addFooterView(this.mFootSearchView);
        } catch (Exception e) {
            this.mFootSearchView.setVisibility(8);
            e.printStackTrace();
        }
    }

    public String getTagName() {
        return FragmentConstant.TAG_FRAGMENT_CHANNEL_DETAIL;
    }

    public int getDisappearFlag() {
        if (this.mChannel == null || !currentPidIsVip(this.mChannel.pageid)) {
            return 1;
        }
        return 0;
    }

    private void clear() {
        LogInfo.log("zhaoxiang", "----------destroy");
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
        this.mSubscription = null;
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1001);
        }
        if (this.mChanelDetailtask != null) {
            this.mChanelDetailtask.onDestroy();
            this.mChanelDetailtask = null;
        }
        this.mPullView.setOnScrollListener(null);
        if (this.mChannelHomeBean != null) {
            this.mChannelHomeBean.clear();
            this.mChannelHomeBean = null;
        }
        if (this.mRequestSiftedOrDolbyData != null) {
            this.mRequestSiftedOrDolbyData.clear();
            this.mRequestSiftedOrDolbyData = null;
        }
        if (this.mListAdapter != null) {
            this.mListAdapter.destroyData();
            this.mListAdapter = null;
        }
        removeTabView();
        removeVipTipView();
        this.mPageCardList = null;
        this.mChannelHomeBean = null;
        this.mIsScrolling = false;
        this.mDataAddToView = false;
        this.mHasInitCursor = false;
    }

    public void onDestroy() {
        clear();
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    public String getPageId() {
        int i = this.mChannel != null ? currentPidIsVip(this.mChannel.pageid) ? 1000 : this.mChannel.id : -1;
        return PageIdConstant.getPageIdByChannelId(i);
    }

    protected <T extends View> T getViewById(View parent, int id) {
        return parent.findViewById(id);
    }

    public void setData(Channel channel) {
        this.mChannel = channel;
    }

    CurrentPage getCurrentPage() {
        return CurrentPage.CHANNEL;
    }

    ChannelDetailExpandableListAdapter getAdapter() {
        return this.mListAdapter;
    }
}
