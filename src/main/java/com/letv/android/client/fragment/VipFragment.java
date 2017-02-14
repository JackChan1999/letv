package com.letv.android.client.fragment;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.android.client.adapter.MineListViewAdapter;
import com.letv.android.client.adapter.channel.ChannelDetailExpandableListAdapter;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.fragment.HomeBaseFragment.CurrentPage;
import com.letv.android.client.task.ChannelDetailTask;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.view.FootSearchView;
import com.letv.android.client.view.RoundImageView;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.UserBean;
import com.letv.core.bean.channel.ChannelFilterTypes;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.bean.channel.TopList;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class VipFragment extends ChannelBaseFragment implements OnClickListener {
    float dowmY;
    private boolean isShowable;
    private boolean mCanShowFilterBar;
    private View mFloatHead;
    private FootSearchView mFootSearchView;
    private boolean mHasStatisticsPageVisible;
    private View mHead;
    private Button mHeadButton;
    private boolean mIsFirstCreate;
    private ChannelDetailExpandableListAdapter mListAdapter;
    private ChannelNavigation mNavigation;
    private RoundImageView mRoundHead;
    private OnScrollListener mScroolEvent;
    private TextView mSubTitle;
    private TextView mTitle;
    private UserBean mUser;
    private ImageView mVipTag;
    OnTouchListener onTouchListener;

    public VipFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mCanShowFilterBar = false;
        this.mHasStatisticsPageVisible = false;
        this.mUser = null;
        this.mIsFirstCreate = true;
        this.mScroolEvent = new OnScrollListener(this) {
            final /* synthetic */ VipFragment this$0;

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
                    this.this$0.isShowable = false;
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
            final /* synthetic */ VipFragment this$0;

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
                                    this.this$0.dowmY = event.getY();
                                }
                                if (event.getY() - this.this$0.dowmY < ((float) (-UIsUtils.dipToPx(38.0f)))) {
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
        this.mIsFirstCreate = false;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.mPullView != null) {
            reloadPage(isVisibleToUser);
        }
        if (this.mListAdapter == null || !isVisibleToUser) {
            this.mHasStatisticsPageVisible = false;
        } else {
            statisticsBottomVipExposure();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        reloadPage(!hidden);
        if (this.mListAdapter == null || hidden) {
            this.mHasStatisticsPageVisible = false;
            return;
        }
        LogInfo.log("jc666", "firstpage onHiddenChanged=" + hidden);
        statisticsBottomVipExposure();
    }

    public void reloadPage(boolean isVisible) {
        if (!isVisible) {
            return;
        }
        if (this.mChannelHomeBean == null || BaseTypeUtils.isListEmpty(this.mChannelHomeBean.block)) {
            requestData(false);
        }
    }

    public void onResume() {
        super.onResume();
        isLogin();
        if (!(this.mChannelHomeBean == null || !this.mChannelHomeBean.isShowTextMark || getVipTipsView() == null)) {
            getVipTipsView().showVipText();
        }
        if (getUserVisibleHint() && !this.mIsHidden && !TextUtils.equals(PageIdConstant.pushPage, StatisticsUtils.getPageId())) {
            statisticsBottomVipExposure();
        }
    }

    private void statisticsBottomVipExposure() {
        if (!this.mHasStatisticsPageVisible) {
            this.mHasStatisticsPageVisible = true;
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.vipCategoryPage, "19", null, null, -1, null);
            if (this.mListAdapter != null) {
                this.mListAdapter.setVisibleToUser(true);
                this.mListAdapter.initStatisticsStatus();
            }
        }
    }

    private void init() {
        this.mHead = LayoutInflater.from(this.mContext).inflate(R.layout.vip_channel_head_layout, null);
        this.mRoot.addView(this.mHead);
        this.mFloatHead = this.mHead.findViewById(R.id.vip_head_login);
        this.mRoundHead = (RoundImageView) this.mHead.findViewById(R.id.vip_pic);
        this.mHeadButton = (Button) this.mHead.findViewById(R.id.login_button);
        this.mTitle = (TextView) this.mHead.findViewById(R.id.head_login_title);
        this.mSubTitle = (TextView) this.mHead.findViewById(R.id.head_login_subtitle);
        this.mVipTag = (ImageView) this.mHead.findViewById(R.id.vip_tag);
        this.mFloatHead.setVisibility(0);
        this.mRoundHead.setOnClickListener(this);
        this.mHeadButton.setOnClickListener(this);
        ((LayoutParams) this.mPullView.getLayoutParams()).topMargin = (int) ((37.0f * getResources().getDisplayMetrics().density) + 0.5f);
        this.mPullView.setParams(param);
        this.mCanShowFilterBar = false;
        this.mListAdapter = new ChannelDetailExpandableListAdapter(this.mContext);
        this.mListAdapter.isFomVipPage(true);
        this.mListAdapter.initMorecallBack(this);
        this.mListAdapter.setStatisticsInfo(-1, "1003322235");
        ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mListAdapter);
        this.mFootSearchView = new FootSearchView(this.mContext, 0);
        this.mPullView.setOnScrollListener(this.mScroolEvent);
        ((ExpandableListView) this.mPullView.getRefreshableView()).setOnTouchListener(this.onTouchListener);
        this.mChanelDetailtask = new ChannelDetailTask(BaseApplication.getInstance(), 0, null);
        this.mChanelDetailtask.initCallBack(this);
        requestData(false);
        this.mRoot.loading(false);
    }

    void loadData(boolean isPullToRefresh) {
        if (this.mChanelDetailtask != null && this.mPageCardList != null) {
            this.mChanelDetailtask.requestChannelDetailList(isPullToRefresh, false, 1, this.mPageCardList);
            if (this.mListAdapter != null) {
                this.mListAdapter.setPageCardList(this.mPageCardList);
            }
            getFocusAd(String.valueOf(1));
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
            if (!BaseTypeUtils.isListEmpty(this.mChannelHomeBean.searchWords)) {
                initFooterSearchView(this.mChannelHomeBean.searchWords);
            }
            setFocusView(this.mChannelHomeBean.focus);
            if (this.mChannelHomeBean.isShowTextMark) {
                setVipTipsView();
            }
            initTabView(result);
            finishLoading();
            this.mPullView.setPullToRefreshEnabled(true);
            ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mListAdapter);
            this.mListAdapter.setDataList((ExpandableListView) this.mPullView.getRefreshableView(), this.mChannelHomeBean, isFromNet);
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
            if (isShow) {
                TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, (float) (-UIsUtils.dipToPx(38.0f)), 0.0f);
                animation.setDuration(200);
                animation.setFillAfter(true);
                animation.setAnimationListener(new AnimationListener(this) {
                    final /* synthetic */ VipFragment this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
            if (!isShow) {
                TranslateAnimation animation1 = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-UIsUtils.dipToPx(38.0f)));
                animation1.setDuration(200);
                animation1.setFillAfter(true);
                animation1.setAnimationListener(new AnimationListener(this) {
                    final /* synthetic */ VipFragment this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
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

    private void clear() {
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
        removeTabView();
        removeVipTipView();
        removeTabView();
        this.mPageCardList = null;
        this.mChannelHomeBean = null;
    }

    public void onDestroy() {
        clear();
        super.onDestroy();
        if (this.mListAdapter != null) {
            this.mListAdapter.destroyData();
        }
    }

    public void onDetach() {
        super.onDetach();
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    protected <T extends View> T getViewById(View parent, int id) {
        return parent.findViewById(id);
    }

    CurrentPage getCurrentPage() {
        return CurrentPage.CHANNEL;
    }

    private void onHeadIconClick(View view, boolean headFlag) {
        if (!NetworkUtils.isNetworkAvailable() && this.mUser == null && PreferencesManager.getInstance().isLogin()) {
            ToastUtils.showToast(this.mContext, 2131100493);
            return;
        }
        String name = "";
        String vip = "vip=0";
        if (!PreferencesManager.getInstance().isLogin() || this.mUser == null) {
            name = "立即登录";
            LetvLoginActivity.launch(getActivity());
        } else {
            if (PreferencesManager.getInstance().isVip()) {
                name = getActivity().getString(2131100552);
                vip = "vip=1";
            } else {
                name = "开通会员";
            }
            new LetvWebViewActivityConfig(this.mContext).launch(MineListViewAdapter.USER_CENTER_URL, this.mContext.getString(2131100489));
        }
        StatisticsUtils.statisticsActionInfo(this.mContext, "010", "0", "vp08", name, 4, vip);
    }

    private void onVipBtnClick(View view, boolean headFlag) {
        if (NetworkUtils.isNetworkAvailable() || this.mUser != null) {
            String name = "";
            String vip = "vip=0";
            if (!PreferencesManager.getInstance().isLogin() || this.mUser == null) {
                name = "开通会员";
                LetvVipActivity.launch(this.mContext, "");
            } else if (PreferencesManager.getInstance().isVip()) {
                name = getActivity().getString(2131100552);
                vip = "vip=1";
                LetvVipActivity.launch(this.mContext, "");
            } else {
                name = "开通会员";
                LetvVipActivity.launch(this.mContext, "");
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, "010", "0", "vp08", name, 3, vip);
            return;
        }
        ToastUtils.showToast(this.mContext, 2131100493);
    }

    private void isLogin() {
        if (PreferencesManager.getInstance().isLogin()) {
            RequestUserByTokenTask.getUserByTokenTask(getActivity(), PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
                final /* synthetic */ VipFragment this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                    if (state == CacheResponseState.SUCCESS && result != null) {
                        this.this$0.mUser = result;
                        this.this$0.updateHeaderUI();
                    }
                }
            });
        } else {
            showNonLoginStatus();
        }
    }

    public void refreshHead() {
        if (!this.mIsFirstCreate) {
            isLogin();
        }
        if (this.mChannelHomeBean != null && this.mChannelHomeBean.isShowTextMark && getVipTipsView() != null) {
            getVipTipsView().showVipText();
        }
    }

    private void updateHeaderUI() {
        if (!PreferencesManager.getInstance().isLogin() || this.mUser == null) {
            showNonLoginStatus();
            return;
        }
        if (!PreferencesManager.getInstance().isVip() || this.mUser.vipInfo == null) {
            if (VERSION.SDK_INT >= 16) {
                this.mVipTag.setBackground(this.mContext.getResources().getDrawable(R.drawable.vip_tag_dark));
            } else {
                this.mVipTag.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.vip_tag_dark));
            }
            this.mVipTag.setVisibility(0);
            this.mSubTitle.setVisibility(8);
            this.mTitle.setText(TipUtils.getTipMessage("2000020"));
            this.mHeadButton.setText(2131100535);
        } else {
            if (VERSION.SDK_INT >= 16) {
                this.mVipTag.setBackground(this.mContext.getResources().getDrawable(R.drawable.vip_tag_light));
            } else {
                this.mVipTag.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.drawable.vip_tag_light));
            }
            this.mVipTag.setVisibility(0);
            int days = 16;
            if (this.mUser.vipInfo != null) {
                days = (int) (this.mUser.vipInfo.lastdays / LetvConstant.VIP_OVERDUE_TIME);
            }
            if (days <= 15) {
                this.mSubTitle.setVisibility(8);
                String tmpText;
                String clipedString;
                if (days == 0) {
                    tmpText = TipUtils.getTipMessage("2000017");
                    if (tmpText == null || tmpText.length() <= 15) {
                        clipedString = tmpText;
                    } else {
                        clipedString = tmpText.substring(0, 15);
                    }
                    this.mTitle.setText(clipedString);
                } else {
                    tmpText = TipUtils.getTipMessage("2000019").replaceAll("％d|%d|%|％", String.valueOf(days));
                    if (tmpText == null || tmpText.length() <= 15) {
                        clipedString = tmpText;
                    } else {
                        clipedString = tmpText.substring(0, 15);
                    }
                    this.mTitle.setText(clipedString);
                }
            } else {
                try {
                    if (this.mUser.vipInfo != null) {
                        long lateEndTime;
                        if (this.mUser.vipInfo.vipType == 2) {
                            lateEndTime = this.mUser.vipInfo.seniorcanceltime;
                            this.mTitle.setText(this.mContext.getString(2131101134));
                            this.mSubTitle.setVisibility(0);
                            this.mSubTitle.setText("  " + StringUtils.timeString(lateEndTime));
                        } else {
                            lateEndTime = this.mUser.vipInfo.canceltime;
                            this.mTitle.setText(this.mContext.getString(2131101101));
                            this.mSubTitle.setVisibility(0);
                            this.mSubTitle.setText("  " + StringUtils.timeString(lateEndTime));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mHeadButton.setText(2131100552);
        }
        String icon = this.mUser.picture;
        String tag = (String) this.mRoundHead.getTag();
        if (!TextUtils.isEmpty(tag) && TextUtils.isEmpty(icon) && tag.equalsIgnoreCase(icon)) {
            this.mRoundHead.setImageDrawable(this.mContext.getResources().getDrawable(2130837633));
            return;
        }
        PreferencesManager.getInstance().setPicture(this.mUser.picture);
        ImageDownloader.getInstance().download(this.mRoundHead, icon);
        this.mRoundHead.setTag(icon);
    }

    private void showNonLoginStatus() {
        this.mTitle.setText(TipUtils.getTipMessage("2000020"));
        this.mSubTitle.setVisibility(8);
        this.mVipTag.setVisibility(8);
        this.mHeadButton.setText(2131100535);
        this.mRoundHead.setImageDrawable(this.mContext.getResources().getDrawable(2130837633));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip_pic /*2131364459*/:
                onHeadIconClick(v, true);
                Log.i("hzz", "lastdays =" + PreferencesManager.getInstance().getLastdays() + " checkday = " + PreferencesManager.getInstance().getLastdays());
                return;
            case R.id.login_button /*2131364462*/:
                onVipBtnClick(v, false);
                return;
            default:
                return;
        }
    }

    ChannelDetailExpandableListAdapter getAdapter() {
        return null;
    }
}
