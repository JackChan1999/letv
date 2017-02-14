package com.letv.android.client.hot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.hot.HotPlayVideoView.HotVideoClickCallBack;
import com.letv.android.client.hot.HotVideoAdapter.HotVideoClickListener;
import com.letv.android.client.hot.HotVideoAdapter.ViewHolder;
import com.letv.android.client.share.HotSquareShareDialog;
import com.letv.android.client.share.HotSquareShareDialog.OnShareDialogDismissListener;
import com.letv.android.client.share.LetvShareControl;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.BaseNetChangeLayer;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.album.AlbumPlayHotFlow;
import com.letv.business.flow.album.PlayObservable;
import com.letv.business.flow.album.listener.AlbumPlayHotListener;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.HotTypeListBean;
import com.letv.core.bean.HotVideoBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class LetvHotActivity extends WrapActivity implements AlbumPlayHotListener {
    private static int cpuLever = -1;
    public static boolean isClickPause = false;
    private static boolean isError = false;
    private HotVideoClickCallBack calllBack;
    private int homeClickNum;
    private boolean isScreenOn;
    private View mAttachView;
    private BaseNetChangeLayer mBaseNetChangeLayer;
    private View mConvertView;
    private int mCurrentPlayTime;
    private AlbumPlayHotFlow mFlow;
    private HotChildViewControl mHotChildViewControl;
    private HotPlayVideoView mHotPlayVideoView;
    private HotSquareShareDialog mHotSquareShareDialog;
    private HotVideoClickListener mHotVideoClickListener;
    public boolean mIsFirstInScroll;
    private boolean mIsFromPush;
    public boolean mIsHomeClicked;
    private ListView mListView;
    private OnScrollListener mOnScrollListener;
    private String mPageId;
    private PlayObservable mPlayObservable;
    private PullToRefreshListView mPullToRefreshListView;
    private PublicLoadLayout mRoot;
    private int mVid;
    private int mVisibleitemCount;
    private String uuidTime;

    public LetvHotActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mCurrentPlayTime = 0;
        this.isScreenOn = true;
        this.mVid = 0;
        this.homeClickNum = 0;
        this.mIsFirstInScroll = true;
        this.mIsHomeClicked = false;
        this.mIsFromPush = false;
        this.mOnScrollListener = new OnScrollListener(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "onScrollStateChanged visiable position = " + view.getFirstVisiblePosition() + " , first = " + view.getFirstVisiblePosition() + " , last = " + view.getLastVisiblePosition());
                if (scrollState == 0) {
                    if (!(this.this$0.mAttachView == null || isPlayViewInVisible(view, this.this$0.mAttachView))) {
                        ((RelativeLayout) this.this$0.mAttachView.findViewById(R.id.hot_play_root_view)).removeAllViews();
                        if (LetvUtils.getSDKVersion() > 17) {
                            LogInfo.log(this.this$0.getActivityName() + "||wlx", "onScroll deattatchVideoView");
                            this.this$0.deattatchVideoView();
                        }
                    }
                    LogInfo.log(this.this$0.getActivityName() + "||wlx", "onScrollStateChanged start");
                    View currentView = findFirstFullViewInScreen(view);
                    LogInfo.log(this.this$0.getActivityName() + "||wlx", "currentView== null-->>" + (currentView == null));
                    if (currentView != null) {
                        LogInfo.log(this.this$0.getActivityName() + "||wlx", "onScrollStateChanged playVideo");
                        this.this$0.mConvertView = currentView;
                        playVideo(currentView);
                    }
                }
            }

            public void playVideo(final View currentView) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "playVideo isShow3g");
                if (UnicomWoFlowManager.getInstance().supportWoFree()) {
                    UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.this$0, new LetvWoFlowListener(this) {
                        final /* synthetic */ AnonymousClass5 this$1;

                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                            LogInfo.log(this.this$1.this$0.getActivityName() + "||wlx", "播放器 request isOrder = " + isOrder);
                            AlbumPlayHotFlow.sIsWo3GUser = isOrder;
                            boolean isNetWo = NetworkUtils.isUnicom3G(false);
                            IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.this$1.this$0, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
                            boolean isPhoneNumNull = TextUtils.isEmpty(woFlowManager.getPhoneNum(this.this$1.this$0));
                            if (!isOrder && isPhoneNumNull && isNetWo) {
                                new UnicomWoFlowDialogUtils().showOrderConfirmEnquireDialog(this.this$1.this$0, new 1(this, woFlowManager));
                            } else if (isOrder) {
                                if (!LetvHotActivity.isClickPause) {
                                    AlbumPlayHotFlow.sAutoPlay = true;
                                }
                                this.this$1.this$0.initHotVideo(currentView);
                            } else {
                                this.this$1.this$0.initHotVideo(currentView);
                            }
                            this.this$1.this$0.mIsFirstInScroll = false;
                        }
                    });
                    return;
                }
                this.this$0.initHotVideo(currentView);
                this.this$0.mIsFirstInScroll = false;
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.this$0.mVisibleitemCount = visibleItemCount;
            }

            public boolean isPlayViewInVisible(AbsListView listView, View view) {
                for (int i = 0; i < this.this$0.mVisibleitemCount; i++) {
                    if (view == listView.getChildAt(i)) {
                        return true;
                    }
                }
                return false;
            }

            public View findFirstFullViewInScreen(AbsListView view) {
                View firstView = null;
                if (view.getChildCount() == 0) {
                    return null;
                }
                if (1 == view.getChildCount()) {
                    firstView = view.getChildAt(0);
                } else if (view.getChildCount() >= 2) {
                    int[] location = new int[2];
                    firstView = view.getChildAt(0);
                    firstView.getLocationInWindow(location);
                    LogInfo.log(this.this$0.getActivityName() + "||wlx", "location[1] = " + location[1]);
                    if (location[1] < UIs.dipToPx(0.0f)) {
                        firstView = view.getChildAt(1);
                    }
                    if (location[1] > 0 && firstView.getTag() == null) {
                        if (this.this$0.mHotPlayVideoView == null || !this.this$0.mHotPlayVideoView.isPlaying()) {
                            this.this$0.deattatchVideoView();
                        }
                        firstView = view.getChildAt(1);
                    }
                }
                return firstView;
            }
        };
        this.mHotVideoClickListener = new HotVideoClickListener(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void shareClick(HotVideoBean hotVideoBean, View v, int position) {
                if (LetvUtils.isInHongKong()) {
                    UIsUtils.showToast(2131100814);
                } else if (this.this$0.mFlow != null) {
                    boolean isNeedResumePlay = false;
                    if (this.this$0.mHotPlayVideoView != null && this.this$0.mHotPlayVideoView.isPlaying()) {
                        this.this$0.mFlow.hotPause();
                        isNeedResumePlay = true;
                    }
                    LetvShareControl.getInstance().setAblum_att(this.this$0.mFlow.mCurrentPlayingVideo);
                    LetvShareControl.getInstance().setmShareAlbumCid(1);
                    ShareUtils.RequestShareLink(this.this$0.mContext);
                    FragmentManager fm = this.this$0.getSupportFragmentManager();
                    if (fm != null) {
                        FragmentTransaction ft = fm.beginTransaction();
                        this.this$0.mHotSquareShareDialog = (HotSquareShareDialog) fm.findFragmentByTag("hotshareDialog");
                        if (this.this$0.mHotSquareShareDialog == null) {
                            this.this$0.mHotSquareShareDialog = new HotSquareShareDialog();
                        } else {
                            ft.remove(this.this$0.mHotSquareShareDialog);
                        }
                        ft.add(this.this$0.mHotSquareShareDialog, "hotshareDialog");
                        ft.commitAllowingStateLoss();
                        final boolean finalIsNeedResumePlay = isNeedResumePlay;
                        this.this$0.mHotSquareShareDialog.setOnShareDialogDismissListener(new OnShareDialogDismissListener(this) {
                            final /* synthetic */ AnonymousClass9 this$1;

                            public void shareDismiss(boolean isShareSuccess) {
                                LogInfo.log("share", "shareDismiss");
                                FragmentTransaction ft2 = this.this$1.this$0.getSupportFragmentManager().beginTransaction();
                                HotSquareShareDialog hd = (HotSquareShareDialog) this.this$1.this$0.getSupportFragmentManager().findFragmentByTag("hotshareDialog");
                                if (hd != null) {
                                    ft2.remove(hd).commitAllowingStateLoss();
                                }
                                this.this$1.this$0.mHotSquareShareDialog = null;
                                if (this.this$1.this$0.mHotPlayVideoView != null && finalIsNeedResumePlay) {
                                    this.this$1.this$0.mHotPlayVideoView.pauseToPlay(true);
                                    LogInfo.log(this.this$1.this$0.getActivityName() + "||wlx", "mHotSquareShareDialog hotPlay()");
                                }
                            }
                        });
                    } else {
                        ToastUtils.showToast(this.this$0, 2131101077);
                    }
                    if (this.this$0.mFlow.mCurrentPlayingVideo != null && this.this$0.mHotPlayVideoView != null) {
                        StatisticsUtils.staticticsInfoPost(this.this$0, "0", "c32", null, -1, null, PageIdConstant.hotIndexCategoryPage, this.this$0.mFlow.mCurrentPlayingVideo.cid + "", this.this$0.mFlow.mCurrentPlayingVideo.pid + "", this.this$0.mHotPlayVideoView.vid + "", null, null);
                    }
                }
            }

            public void clickPlayOrPause(HotVideoBean video, View view) {
                if (view.findViewById(R.id.hot_play_playButton) != null && view.findViewById(R.id.hot_play_playButton).getVisibility() == 0) {
                    AlbumPlayHotFlow.sAutoPlay = true;
                    AlbumPlayHotFlow.sIsClickToPlay = true;
                    this.this$0.attatchVideoView(Integer.parseInt(video.id), view);
                }
            }

            public void gotoComment(String pid, String vid) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.this$0).create(BaseTypeUtils.stol(pid), BaseTypeUtils.stol(vid), 0)));
            }
        };
        this.calllBack = new HotVideoClickCallBack(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void play3G() {
                if (this.this$0.mFlow != null && !this.this$0.showNetChangeDialog()) {
                    this.this$0.mFlow.star3g();
                }
            }

            public void refreshList() {
                this.this$0.requestHotTypeData();
            }
        };
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogInfo.log(getActivityName() + "||wlx", "LetvHotActivity onCreate");
        setContentView(R.layout.hot_spuare_view);
        findview();
        this.isScreenOn = UIsUtils.isScreenOn(this);
        if (cpuLever == -1) {
            cpuLever = LetvApplication.getInstance().getSuppportTssLevel();
        }
        if (NetworkUtils.isMobileNetwork()) {
            AlbumPlayHotFlow.sAutoPlay = false;
        }
        this.mPlayObservable = new PlayObservable(this);
        this.mPageId = getIntent().getStringExtra("pageId");
        this.mVid = getIntent().getIntExtra("vid", 0);
        this.mIsFromPush = getIntent().getBooleanExtra("from_push", false);
        requestHotTypeData();
    }

    public static void launch(Context context, String pageId, int vid) {
        launch(context, pageId, vid, false);
    }

    public static void launch(Context context, String pageId, int vid, boolean isFromPush) {
        Intent intent = new Intent(context, LetvHotActivity.class);
        if (!(context instanceof Activity)) {
            intent.setFlags(268435456);
        }
        intent.putExtra("pageId", pageId);
        intent.putExtra("vid", vid);
        intent.putExtra("from_push", isFromPush);
        context.startActivity(intent);
    }

    private void sendOnScrollStateChanged() {
        if (this.mPullToRefreshListView != null && this.mListView != null) {
            Message msg = Message.obtain();
            msg.obj = this;
            new Handler(this) {
                final /* synthetic */ LetvHotActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void handleMessage(Message msg) {
                    LogInfo.log(this.this$0.getActivityName() + "||wlx", "主动触发 onScrollStateChanged 播放视频");
                    this.this$0.mPullToRefreshListView.onScrollStateChanged(this.this$0.mListView, 0);
                }
            }.sendMessageDelayed(msg, 100);
        }
    }

    @SuppressLint({"ResourceAsColor"})
    public void findview() {
        this.mRoot = (PublicLoadLayout) findViewById(R.id.hot_spuare_content_container_layout);
        this.mRoot.addContent(R.layout.hot_spuare_pager_view);
        this.mHotChildViewControl = HotChildViewControl.createControl(this, (ViewPager) findViewById(R.id.hot_viewpager), (HotTabPageIndicator) findViewById(R.id.page_tab), this.mHotVideoClickListener);
        this.mHotChildViewControl.setListener(new IHotListChangeListener(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onListChanged(PullToRefreshListView newList, boolean isMore) {
                LogInfo.log("LetvHotActivity||wlx", "onListChanged");
                if (!isMore) {
                    if (this.this$0.mHotPlayVideoView != null) {
                        this.this$0.mFlow.hotPause();
                    }
                    this.this$0.mCurrentPlayTime = 0;
                }
                if (this.this$0.mPullToRefreshListView != null) {
                    this.this$0.mPullToRefreshListView.setOnScrollListener(null);
                }
                if (newList != null) {
                    this.this$0.mPullToRefreshListView = newList;
                    this.this$0.mListView = (ListView) newList.getRefreshableView();
                    this.this$0.mPullToRefreshListView.setOnScrollListener(this.this$0.mOnScrollListener);
                    this.this$0.sendOnScrollStateChanged();
                }
            }

            public void onListChanging(PullToRefreshListView oldList) {
            }
        });
        this.mRoot.setRefreshData(new RefreshData(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.requestHotTypeData();
            }
        });
        ((ImageView) findViewById(R.id.hot_back_btn)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.finish();
            }
        });
    }

    public void onResume() {
        PreferencesManager.getInstance().setWoFlowAlert(true);
        LogInfo.log(getActivityName() + "||wlx", "LetvHotActivity onResume");
        super.onResume();
        String model = LetvUtils.getModelName();
        if (TextUtils.isEmpty(model) || !model.toUpperCase().contains("XT") || !model.contains("910") || this.isScreenOn) {
            if (this.mIsHomeClicked) {
                this.homeClickNum++;
            }
            resume();
        }
    }

    private void attatchVideoView(final int vid, View view) {
        boolean isChangeVideo;
        LogInfo.log(getActivityName() + "||wlx", "attatchVideoView mHotPlayVideoView = " + this.mHotPlayVideoView + " , autoPlay = " + AlbumPlayHotFlow.sAutoPlay + " , view = ");
        LogInfo.log(getActivityName() + "||wlx", "vid = " + vid);
        if (this.mHotPlayVideoView != null || this.mIsHomeClicked) {
            isChangeVideo = false;
        } else {
            isChangeVideo = true;
        }
        if (!(this.mHotPlayVideoView == null || this.mHotPlayVideoView.vid == vid)) {
            LogInfo.log("LetvHotActivity||wlx", "视频更换 播放时间清零");
            this.mCurrentPlayTime = 0;
            isChangeVideo = true;
        }
        LogInfo.log("LetvHotActivity||wlx", "mCurrentPlayTime = " + this.mCurrentPlayTime);
        deattatchVideoView();
        if (isChangeVideo) {
            this.uuidTime = null;
            this.homeClickNum = 0;
        }
        if (this.mIsHomeClicked) {
            this.mIsHomeClicked = false;
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null) {
            int netType = NetworkUtils.getNetworkType();
            this.mAttachView = view;
            if (netType == 0) {
                LogInfo.log(getActivityName() + "||wlx" + "attatchVideoView netType == NetWorkTypeUtils.NETTYPE_NO");
                setErrorText(2131100176, true, viewHolder, view);
                return;
            }
            LogInfo.log(getActivityName() + "||wlx", "autoPlay::" + AlbumPlayHotFlow.sAutoPlay + "\nisClickToPlay = " + AlbumPlayHotFlow.sIsClickToPlay);
            if ((AlbumPlayHotFlow.sAutoPlay && netType == 1) || ((NetworkUtils.isUnicom3G(false) && AlbumPlayHotFlow.sIsWo3GUser && AlbumPlayHotFlow.sAutoPlay) || AlbumPlayHotFlow.sIsClickToPlay)) {
                LogInfo.log("zhuqiao", ">>>>>>>>>>>>startRequestVideo");
                this.mHotPlayVideoView = new HotPlayVideoView(this, vid, this.mAttachView, this.calllBack, cpuLever);
                this.mHotPlayVideoView.mIsFromPush = this.mIsFromPush;
                startRequestVideo(viewHolder);
                return;
            }
            LogInfo.log("zhuqiao", ">>>>>>>>>>>>pausepause");
            this.mAttachView.findViewById(R.id.hot_play_playButton).setVisibility(0);
            this.mAttachView.findViewById(R.id.hot_play_playButton).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ LetvHotActivity this$0;

                public void onClick(View view) {
                    this.this$0.clickToAddVideoView(this.this$0.mAttachView, vid);
                }
            });
            ((RelativeLayout) this.mAttachView.findViewById(R.id.hot_play_root_view)).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ LetvHotActivity this$0;

                public void onClick(View view) {
                    this.this$0.clickToAddVideoView(this.this$0.mAttachView, vid);
                }
            });
        }
    }

    public void hideOut3gLayout() {
        if (this.mBaseNetChangeLayer != null) {
            this.mBaseNetChangeLayer.hide();
            this.mBaseNetChangeLayer = null;
        }
    }

    private boolean isVideoViewAttatch() {
        return (this.mHotPlayVideoView == null || this.mHotPlayVideoView.isDestroy) ? false : true;
    }

    private void deattatchVideoView() {
        LogInfo.log(getActivityName() + "||wlx", "deattatchVideoView");
        hide3gLayout();
        if (this.mAttachView != null) {
            this.mAttachView.findViewById(R.id.hot_play_playButton).setVisibility(8);
            this.mAttachView.findViewById(R.id.hot_play_root_view).setOnClickListener(null);
        }
        if (isVideoViewAttatch()) {
            this.mHotPlayVideoView.destory();
            this.mHotPlayVideoView = null;
        }
    }

    public void clickToAddVideoView(View attachView, int vid) {
        LogInfo.log(getActivityName() + "||wlx" + "clickToAddVideoView");
        if (AlbumPlayHotFlow.sIsWo3GUser || !NetworkUtils.isMobileNetwork()) {
            AlbumPlayHotFlow.sAutoPlay = true;
            AlbumPlayHotFlow.sIsClickToPlay = true;
            attatchVideoView(vid, attachView);
        } else if (showNetChangeDialog()) {
            this.mAttachView = attachView;
            this.mVid = vid;
        } else {
            AlbumPlayHotFlow.sAutoPlay = true;
            AlbumPlayHotFlow.sIsClickToPlay = true;
            isClickPause = false;
            ToastUtils.showToast(TipUtils.getTipMessage("100006", 2131100656));
            LogInfo.log("LM", "clickToadd");
            attatchVideoView(vid, attachView);
        }
    }

    public void requestHotTypeData() {
        if (this.mRoot != null) {
            this.mRoot.loading(false);
        }
        LogInfo.log("zhaosumin", "获取分类列表 url == " + LetvUrlMaker.getHotTypeList());
        new LetvRequest(HotTypeListBean.class).setUrl(LetvUrlMaker.getHotTypeList()).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<HotTypeListBean>(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<HotTypeListBean> volleyRequest, HotTypeListBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "state=" + state);
                LogInfo.log(this.this$0.getActivityName() + "||wlx", "热点分类列表结果" + hull.sourceData);
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.refreshView(result);
                    return;
                }
                if (this.this$0.mRoot != null) {
                    this.this$0.mRoot.netError(false);
                }
                LetvHotActivity.isError = true;
            }
        }).add();
    }

    private void refreshView(HotTypeListBean result) {
        if (result != null) {
            this.mHotChildViewControl.init(result, this.mPageId, this.mVid);
            if (this.mRoot != null) {
                this.mRoot.finish();
            }
        }
    }

    public void onDestroy() {
        PreferencesManager.getInstance().setWoFlowAlert(false);
        AlbumPlayHotFlow.sAutoPlay = true;
        AlbumPlayHotFlow.sIsClickToPlay = false;
        AlbumPlayHotFlow.sIsWo3GUser = false;
        isClickPause = false;
        if (this.mPlayObservable != null) {
            this.mPlayObservable.deleteObservers();
        }
        this.mIsHomeClicked = false;
        super.onDestroy();
    }

    public String getActivityName() {
        return getClass().getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        if (this.mHotPlayVideoView != null) {
            AlbumPlayHotFlow.sIsClickToPlay = true;
        }
        this.mIsHomeClicked = StatisticsUtils.mIsHomeClicked;
        deattatchVideoView();
        super.onStop();
    }

    public void onPause() {
        if (this.mHotPlayVideoView != null) {
            this.mCurrentPlayTime = this.mHotPlayVideoView.getCurrentTime();
            if (this.mHotPlayVideoView.isPlaying()) {
                AlbumPlayHotFlow.sIsClickToPlay = true;
            }
            this.mFlow.hotPause();
        }
        super.onPause();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (HotSquareShareDialog.onFragmentResult != null) {
            HotSquareShareDialog.onFragmentResult.onFragmentResult_back(requestCode, resultCode, data);
        }
    }

    private void startRequestVideo(ViewHolder holder) {
        Bundle bundle = new Bundle();
        bundle.putLong("vid", (long) holder.vid);
        if (!(this.mFlow == null || this.mPlayObservable == null)) {
            this.mPlayObservable.deleteObserver(this.mFlow);
        }
        this.mFlow = new AlbumPlayHotFlow(this, 1, bundle);
        this.mPlayObservable.addObserver(this.mFlow);
        if (this.mHotPlayVideoView != null) {
            this.mFlow.setPlayListener(this.mHotPlayVideoView);
        }
        this.mFlow.startHot(this);
        if (this.mHotPlayVideoView != null && !this.mHotPlayVideoView.isStatisticsLaunch) {
            this.mHotPlayVideoView.updatePlayDataStatistics("launch", -1, AlbumPlayHotFlow.sAutoPlay);
            this.mFlow.setPlayUuid(getUuidTime());
            this.mHotPlayVideoView.isStatisticsLaunch = true;
        }
    }

    public void onNetError(boolean isRetry, Object holder, View view) {
        if (holder instanceof ViewHolder) {
            setErrorText(2131100176, isRetry, holder, view);
        }
    }

    public void onDataError(boolean isRetry, Object holder, View view) {
        if (holder instanceof ViewHolder) {
            setErrorText(2131100175, isRetry, holder, view);
        }
    }

    public void onIpError(boolean isRetry, Object holder, View view) {
        if (holder instanceof ViewHolder) {
            setErrorText(2131100174, isRetry, holder, view);
        }
    }

    public void dismissErrorLayout(Object holder) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (viewHolder.errorLayout.getVisibility() == 0) {
                viewHolder.errorLayout.setVisibility(8);
            }
        }
    }

    public View getAttachView() {
        return this.mAttachView;
    }

    public void show3gLayout(boolean isblack, View parentView) {
        if (this.mFlow != null) {
            this.mFlow.hotPause();
        }
        hideOut3gLayout();
        this.mBaseNetChangeLayer = new BaseNetChangeLayer(this, (ViewGroup) parentView.findViewById(R.id.hot_play_view));
        int mDefaultWidth = getWindowManager().getDefaultDisplay().getWidth() - 20;
        int mDefaultHeight = (mDefaultWidth * 3) / 4;
        this.mBaseNetChangeLayer.hideController();
        if (isblack) {
            this.mBaseNetChangeLayer.showBlack();
        } else {
            this.mBaseNetChangeLayer.show3gAlert(new OnClickListener(this) {
                final /* synthetic */ LetvHotActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(View view) {
                    if (this.this$0.mFlow != null) {
                        this.this$0.mFlow.star3g();
                    } else {
                        AlbumPlayHotFlow.sAutoPlay = true;
                        AlbumPlayHotFlow.sIsClickToPlay = true;
                        LetvHotActivity.isClickPause = false;
                        PreferencesManager.getInstance().setShow3gDialog(false);
                        ToastUtils.showToast(TipUtils.getTipMessage("100006", 2131100656));
                        LogInfo.log("LM", "clickToadd");
                        this.this$0.attatchVideoView(this.this$0.mVid, this.this$0.mAttachView);
                    }
                    this.this$0.hide3gLayout();
                }
            });
        }
        this.mBaseNetChangeLayer.setSize(mDefaultHeight, mDefaultWidth);
    }

    public Object getHotSquareShareDialog() {
        return this.mHotSquareShareDialog;
    }

    public void createPlayerForPlay(Object holder, View view) {
        if (this.mHotPlayVideoView == null) {
            if (this.mAttachView == null) {
                this.mAttachView = view;
            }
            if (this.mAttachView != null && (holder instanceof ViewHolder)) {
                this.mHotPlayVideoView = new HotPlayVideoView(this, ((ViewHolder) holder).vid, this.mAttachView, this.calllBack, cpuLever);
                this.mHotPlayVideoView.mIsFromPush = this.mIsFromPush;
            }
            if (this.mFlow != null) {
                this.mFlow.setPlayListener(this.mHotPlayVideoView);
                LogInfo.log(getActivityName() + "||wlx", "真实播放地址=" + this.mFlow.mAlbumUrl.realUrl);
            }
        }
    }

    public void setErrorText(int resId, boolean isRetry, Object holderParam, View view) {
        if (holderParam instanceof ViewHolder) {
            final ViewHolder holder = (ViewHolder) holderParam;
            LogInfo.log(getActivityName() + "||wlx", "setErrorText deattatchVideoView");
            deattatchVideoView();
            if (view != null) {
                this.mAttachView = view;
                this.mHotPlayVideoView = new HotPlayVideoView(this, holder.vid, this.mAttachView, this.calllBack, cpuLever);
                if (this.mFlow != null) {
                    this.mFlow.setPlayListener(this.mHotPlayVideoView);
                }
            }
            if (this.mAttachView != null) {
                this.mAttachView.findViewById(R.id.hot_play_errer_layout).setVisibility(0);
                this.mAttachView.findViewById(R.id.hot_play_loading).setVisibility(8);
                this.mAttachView.findViewById(R.id.hot_play_image).setVisibility(8);
                this.mAttachView.findViewById(R.id.hot_play_playButton).setVisibility(8);
                this.mAttachView.findViewById(R.id.hot_play_duration).setVisibility(8);
                this.mAttachView.findViewById(R.id.hot_play_root_view).setOnClickListener(null);
                ((TextView) this.mAttachView.findViewById(R.id.hot_play_errer_tip)).setText(resId);
                if (isRetry) {
                    this.mAttachView.findViewById(R.id.hot_play_errer_retry).setVisibility(0);
                    this.mAttachView.findViewById(R.id.hot_play_errer_retry).setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ LetvHotActivity this$0;

                        public void onClick(View arg0) {
                            LogInfo.log("clf", ".....holder is " + holder);
                            holder.errorLayout.setVisibility(8);
                            holder.play.setVisibility(8);
                            holder.loading.setVisibility(0);
                            holder.image.setVisibility(0);
                            this.this$0.startRequestVideo(holder);
                        }
                    });
                    this.mAttachView.findViewById(R.id.hot_play_root_view).setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ LetvHotActivity this$0;

                        public void onClick(View view) {
                            this.this$0.clickToAddVideoView(this.this$0.mAttachView, holder.vid);
                        }
                    });
                }
            }
        }
    }

    public int getCurrentPlayTime() {
        if (this.mCurrentPlayTime <= 0) {
            return this.mCurrentPlayTime;
        }
        LogInfo.log(getActivityName() + "||wlx", "当前播放时间已经取过 清零");
        int currentTime = this.mCurrentPlayTime;
        this.mCurrentPlayTime = 0;
        return currentTime;
    }

    public void setCurrentPlayTime(int currentPlayTime) {
        this.mCurrentPlayTime = currentPlayTime;
    }

    public Object getBaseNetChangeLayer() {
        return this.mBaseNetChangeLayer;
    }

    public View getConvertView() {
        return this.mConvertView;
    }

    public void hide3gLayout() {
        hideOut3gLayout();
    }

    public void resume() {
        UIsUtils.hideSoftkeyboard(this);
        if (isError) {
            isError = false;
            requestHotTypeData();
        } else if (this.mListView != null) {
            sendOnScrollStateChanged();
        }
    }

    public void initHotVideo(View currentView) {
        LogInfo.log(getActivityName() + "||wlx", "initHotVideo");
        ViewHolder viewHolder = (ViewHolder) currentView.getTag();
        if (viewHolder == null || (isVideoViewAttatch() && viewHolder.vid == this.mHotPlayVideoView.getVid())) {
            LogInfo.log(getActivityName() + "||wlx", "视频正在播放 返回");
            return;
        }
        Message msg = Message.obtain();
        msg.what = viewHolder.vid;
        msg.obj = currentView;
        new Handler(this) {
            final /* synthetic */ LetvHotActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                this.this$0.attatchVideoView(msg.what, (View) msg.obj);
            }
        }.sendMessage(msg);
    }

    public void setIsScreenOn(boolean isScreenOn) {
        this.isScreenOn = isScreenOn;
    }

    public boolean showNetChangeDialog() {
        if (AlbumPlayHotFlow.sIsWo3GUser || !PreferencesManager.getInstance().isShow3gDialog() || !NetworkUtils.isMobileNetwork()) {
            return false;
        }
        show3gLayout(false, this.mAttachView);
        return true;
    }

    public AlbumPlayHotFlow getFlow() {
        return this.mFlow;
    }

    public HotPlayVideoView getHotPlayVideoView() {
        return this.mHotPlayVideoView;
    }

    public int getHomeClickNum() {
        return this.homeClickNum;
    }

    public void setHomeClickNum(int num) {
        this.homeClickNum = num;
    }

    public String getUuidTime() {
        if (TextUtils.isEmpty(this.uuidTime)) {
            this.uuidTime = DataUtils.getUUID(this);
        }
        String uuid = this.uuidTime;
        if (this.homeClickNum > 0) {
            return this.uuidTime + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.homeClickNum;
        }
        return uuid;
    }

    public void setUuidTime(String uuidTime) {
        this.uuidTime = uuidTime;
    }
}
