package com.letv.android.client.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.adlib.sdk.types.AdElement.AdCommonType;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.client.AdPlayStateListener;
import com.letv.ads.ex.ui.AdViewProxy;
import com.letv.ads.ex.ui.AdViewProxy.ClientListener;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.popdialog.ContinuePayDialogHandler;
import com.letv.android.client.activity.popdialog.DownLoadApkDialogHandler;
import com.letv.android.client.activity.popdialog.InviteDialogHandler;
import com.letv.android.client.activity.popdialog.ReceiveVipDialogHandler;
import com.letv.android.client.activity.popdialog.UpgradeDialogHandler;
import com.letv.android.client.activity.popdialog.WoMainDialogHandler;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.FloatBallConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.LiveRoomConfig;
import com.letv.android.client.commonlib.config.MainActivityConfig;
import com.letv.android.client.commonlib.config.MainActivityConfig.GoToChannel;
import com.letv.android.client.commonlib.config.MainActivityConfig.GoToChannelByCid;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.fragment.ChannelDetailFragment;
import com.letv.android.client.fragment.FindFragment;
import com.letv.android.client.fragment.HomeFragment;
import com.letv.android.client.fragment.MineFragment;
import com.letv.android.client.fragment.TopRecommendFragment;
import com.letv.android.client.fragment.VipFragment;
import com.letv.android.client.hot.LetvHotActivity;
import com.letv.android.client.live.controller.LivePlayerController.SysConfigChangeEvent;
import com.letv.android.client.live.fragment.LiveFragment;
import com.letv.android.client.live.fragment.LiveFragment.COUNTRY_CHANGE_EVENT;
import com.letv.android.client.mymessage.MyMessageRequest;
import com.letv.android.client.mymessage.MyMessageRequest.MessageListener;
import com.letv.android.client.receiver.TokenLoseReceiver;
import com.letv.android.client.service.RedPacketPollingService;
import com.letv.android.client.task.RequestPraiseTask;
import com.letv.android.client.ui.download.MyDownloadActivity;
import com.letv.android.client.utils.GotoPageUtils;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.android.client.utils.PatchUtils;
import com.letv.android.client.utils.ThemeDataManager;
import com.letv.android.client.utils.ThemeDataManager.IThemeImagesDownloadCallBack;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.BottomRedPointView;
import com.letv.android.client.view.ExitRetainController;
import com.letv.android.client.view.ExitRetainController.ExitRetainCallBack;
import com.letv.android.client.view.ExitRetainPopupwindow;
import com.letv.android.client.view.MainBottomNavigationView;
import com.letv.android.client.view.MainBottomNavigationView.IBottomNavItemCheckedListener;
import com.letv.android.client.view.MainBottomNavigationView.NavigationType;
import com.letv.android.client.view.MainTopNavigationView;
import com.letv.android.uninstall.UninstalledObserver;
import com.letv.business.flow.main.MainFlow;
import com.letv.business.flow.main.MainFlowCallback;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.FindListDataBean;
import com.letv.core.bean.FloatBallBeanList;
import com.letv.core.bean.HomePageBean.Booting;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.RedirectData;
import com.letv.core.bean.TipMapBean;
import com.letv.core.bean.switchinfo.ThemeDataBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.PlayConstant;
import com.letv.core.contentprovider.UserInfoTools;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;
import com.letv.core.pagecard.AlbumPageCardFetcher;
import com.letv.core.pagecard.PageCardFetcher;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvDateUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.RetentionRateUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;
import com.letv.download.manager.CompatDataManager;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.StoreManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.redpacketsdk.RedPacketSdk;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.callback.RedPacketForecastCallback;
import com.letv.redpacketsdk.ui.RedPacketForecastView;
import com.letv.redpacketsdk.utils.DensityUtil;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends LetvBaseActivity implements MainFlowCallback, IBottomNavItemCheckedListener {
    private static final int CLOSE_TRANIT_IMAGE = 257;
    public static final String FORCELAUNCH = "forceLaunch";
    public static final String FORCEMSG = "forceMsg";
    public static final int LAUNCH_TO_LIVE_CHANNEL = 20;
    public static final int LAUNCH_TO_LIVE_PUSH = 19;
    public static String LOGIN_IN_OUT_TAG = "com.letv.android.client.ui.impl.login.out";
    public static String LOGIN_IN_OUT_TYPE = "loginOrOut";
    public static final String TAG_KEY = "tag";
    public static final String THIRD_PARTY_BAIDU = "baidu";
    public static final int THIRD_PARTY_FROM_BAIDU = 1;
    public static final String THIRD_PARTY_LESO = "leso";
    public static final String THIRD_PARTY_LETV = "letv";
    public static final String THIRD_PARTY_WANGDOUJIA = "wandoujia";
    public static final String THIRD_PARTY_YINGYONGBAO = "yingyongbao";
    private static final int TURN_PAGE_ANIM_COMMAND = 256;
    private static final int TURN_PAGE_ANIM_TIME = 11000;
    private static MainActivity instance;
    public static ExitRetainController mExitRetainController;
    private int WAIT_TIME_COUNT;
    private RelativeLayout adBootLayout;
    private boolean adIsSuccess;
    private boolean animationIsFinish;
    ClientListener clientListener;
    private boolean cmsIsSuccess;
    private ViewGroup contentView;
    private boolean currentIsFeatureOrHomepage;
    private BottomRedPointView findRedPointView;
    Handler handler;
    private View imageLayout;
    private boolean initTashIsSuccess;
    private boolean isAnimationIsFinish;
    private boolean isChoice;
    public boolean isFirstLaunchApp;
    private boolean isShowingBeiginAD;
    private boolean isTimeOutLoadImage;
    private int keyBackClickCount;
    private boolean logoOrSigner;
    private ImageView mAdBootMask;
    private AdViewProxy mAdView;
    private BroadcastReceiver mBroadcastLogInOutReceiver;
    private RelativeLayout mCardmanagerLayout;
    private ChannelDetailFragment mChannelDetailFragment;
    private Channel mChannelPageData;
    private ContinuePayDialogHandler mContinuePayDialogHandler;
    public String mCurrentPageId;
    private ExitRetainPopupwindow mExitRetainPopupwindow;
    private FindFragment mFindFragment;
    private FloatBallConfig mFloatBallConfig;
    private boolean mFromMobileSite;
    private int mFromWhere;
    private Handler mHandler;
    private HomeFragment mHomeFragment;
    private boolean mIsEnterFromThirdParty;
    private boolean mIsFloatBallRequestFinished;
    private boolean mIsForceLaunch;
    private boolean mIsFromPush;
    private boolean mIsLivePush;
    private boolean mLaunchByPush;
    private LiveFragment mLiveFragment;
    public MainBottomNavigationView mMainBottomNavigationView;
    private MainFlow mMainFlow;
    private ImageView mMainLogo;
    private boolean mMainShow;
    public MainTopNavigationView mMainTopNavigationView;
    private MineFragment mMineFragment;
    private BottomRedPointView mMineRedPointView;
    private boolean mOpenAppJustNow;
    private ReceiveVipDialogHandler mReceiveVipDialogHandler;
    private TopRecommendFragment mRecommendFragment;
    private String mRedPacketCid;
    private RedPacketForecastView mRedPacketForecastView;
    private boolean mShouldJumpToDownloadPage;
    private String mThirdPartyFrom;
    private boolean mThreeDialogShowAlready;
    private TokenLoseReceiver mTokenLoseReceiver;
    private View mTopNavigationView;
    private Typeface mTypeface;
    private UpgradeDialogHandler mUpgradeDialogHandler;
    private LinearLayout mUserGuideLayout;
    private VipFragment mVIPFragment;
    private RotateAnimation rotateAnim;
    private RotateAnimation rotateAnimDe;
    private RotateAnimation rotateAnimIn;
    private Timer timer;
    private Animation topHomePageAnim;
    private RelativeLayout topViewLayout;
    private int waitTimeCountElapsed;

    public MainActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsEnterFromThirdParty = false;
        this.mFromWhere = -1;
        this.mMineFragment = new MineFragment();
        this.mHomeFragment = new HomeFragment();
        this.mLiveFragment = new LiveFragment();
        this.mFindFragment = new FindFragment();
        this.mVIPFragment = new VipFragment();
        this.mRecommendFragment = new TopRecommendFragment();
        this.mChannelDetailFragment = new ChannelDetailFragment();
        this.mCurrentPageId = "";
        this.mExitRetainPopupwindow = new ExitRetainPopupwindow();
        this.mIsFloatBallRequestFinished = false;
        this.keyBackClickCount = 0;
        this.mThirdPartyFrom = "";
        this.mThreeDialogShowAlready = false;
        this.isShowingBeiginAD = false;
        this.mOpenAppJustNow = false;
        this.mMainShow = false;
        this.handler = new Handler();
        this.mHandler = new Handler(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                boolean z = false;
                switch (msg.what) {
                    case 1:
                        LogInfo.log("picView", "---handler----1");
                        this.this$0.animProcess();
                        return;
                    case 2:
                        LogInfo.log("picView", "---handler----2");
                        MainActivity mainActivity = this.this$0;
                        if (msg.arg1 != 1) {
                            z = true;
                        }
                        mainActivity.handlerProcess(z);
                        return;
                    case 3:
                        LogInfo.log("picView", "---handler----3");
                        this.this$0.refreshWaittingText();
                        return;
                    case 4:
                        LogInfo.log("picView", "---handler----4");
                        this.this$0.animEndProcess();
                        return;
                    case 5:
                        LogInfo.log("picView", "---handler----5");
                        if (this.this$0.mLaunchByPush && this.this$0.mIsLivePush) {
                            LogInfo.log("picView", "---isLaunchFromPush && isLivePush----5");
                            this.this$0.mLaunchByPush = false;
                            this.this$0.mIsLivePush = false;
                            return;
                        }
                        return;
                    case 256:
                        this.this$0.setAnimationImage();
                        this.this$0.mHandler.sendEmptyMessageDelayed(256, 11000);
                        return;
                    case 257:
                        this.this$0.mAdBootMask.setVisibility(8);
                        return;
                    default:
                        return;
                }
            }
        };
        this.isTimeOutLoadImage = false;
        this.timer = new Timer();
        this.cmsIsSuccess = false;
        this.adIsSuccess = false;
        this.mLaunchByPush = false;
        this.mIsLivePush = false;
        this.currentIsFeatureOrHomepage = false;
        this.WAIT_TIME_COUNT = 3;
        this.waitTimeCountElapsed = 0;
        this.logoOrSigner = false;
        this.isFirstLaunchApp = false;
        this.isAnimationIsFinish = true;
        this.initTashIsSuccess = false;
        this.animationIsFinish = false;
        this.clientListener = new ClientListener(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean handleADClick(AdElementMime adElementMime) {
                if (adElementMime == null) {
                    return false;
                }
                LogInfo.log("chengjian", "clickShowType =" + adElementMime.clickShowType);
                switch (adElementMime.clickShowType) {
                    case 3:
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.this$0.mContext).create(BaseTypeUtils.stol(adElementMime.pid), BaseTypeUtils.stol(adElementMime.vid), 0)));
                        return true;
                    case 4:
                        LiveRoomConfig.launchLives(this.this$0.mContext, null, adElementMime.sid, adElementMime.streamURL, true, 2, null);
                        return true;
                    default:
                        return false;
                }
            }
        };
        this.mIsForceLaunch = false;
        this.mBroadcastLogInOutReceiver = new BroadcastReceiver(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                if (intent != null && MainActivity.LOGIN_IN_OUT_TAG.equals(intent.getAction())) {
                    String type = intent.getStringExtra(MainActivity.LOGIN_IN_OUT_TYPE);
                    if (!"logout_success".equalsIgnoreCase(type) && "login_success".equalsIgnoreCase(type)) {
                        if (PreferencesManager.getInstance().getPraise() && PreferencesManager.getInstance().isLogin()) {
                            RequestPraiseTask requestPraiseTask = new RequestPraiseTask(context);
                        }
                        LogInfo.log("mainactivity", "mBroadcastLogInOutReceiver mThreeDialogShowAlready : " + this.this$0.mThreeDialogShowAlready + " ContinuePayDialogHandler : " + this.this$0.mContinuePayDialogHandler);
                        if (this.this$0.mContinuePayDialogHandler == null) {
                            this.this$0.mContinuePayDialogHandler = new ContinuePayDialogHandler(MainActivity.instance);
                        }
                        if (this.this$0.mReceiveVipDialogHandler == null) {
                            this.this$0.mReceiveVipDialogHandler = new ReceiveVipDialogHandler(MainActivity.instance);
                        }
                        this.this$0.mContinuePayDialogHandler.setSuccessor(this.this$0.mReceiveVipDialogHandler);
                        if (!this.this$0.mThreeDialogShowAlready && this.this$0.mContinuePayDialogHandler != null) {
                            this.this$0.mContinuePayDialogHandler.handleRequest();
                        }
                    }
                }
            }
        };
    }

    public void setThreeDialogShowAlready(boolean bShowFlag) {
        this.mThreeDialogShowAlready = bShowFlag;
    }

    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 10 && arg1 == 1 && arg2 != null) {
            MainLaunchUtils.jump2H5(this, arg2.getStringExtra(LetvLoginActivityConfig.AWARDURL), false);
        }
        if (this.mLiveFragment != null) {
            this.mLiveFragment.onActivityResult(arg0, arg1, arg2);
        }
    }

    public void setNewFindVisible(boolean b) {
        if (this.findRedPointView == null) {
            return;
        }
        if (b) {
            this.findRedPointView.show();
        } else {
            this.findRedPointView.hide();
        }
    }

    public void setMineRedPointVisible(boolean isShow) {
        LogInfo.log("wdm", "我的红点" + isShow);
        if (this.mMineRedPointView == null) {
            return;
        }
        if (isShow) {
            this.mMineRedPointView.show();
        } else {
            this.mMineRedPointView.hide();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.mNeedApplyPermissions = true;
        super.onCreate(savedInstanceState);
    }

    protected void _setContentView() {
        getWindow().setFlags(1024, 1024);
        setContentView(2130903061);
        instance = this;
        BaseApplication.getInstance().mIsMainActivityAlive = true;
    }

    protected void onApplyPermissionsSuccess() {
        super.onApplyPermissionsSuccess();
        init();
        initFloatBall();
        initDownloadConfig();
        this.mMainFlow = new MainFlow(this, this);
        this.mMainFlow.start();
        this.mMainFlow.requestFindTask();
        this.mMainFlow.requestLocationMessage();
        initExitRetain();
        MyMessageRequest.requestUnreadMessageCountTask(new MessageListener(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onMessageCount() {
                this.this$0.setMineRedPointVisible(true);
                this.this$0.mMineFragment.refreshNewMessageVisible(true);
            }
        });
        Intent intent = getIntent();
        Uri uri = intent.getData();
        String action = getIntent().getAction();
        if (uri != null) {
            String scheme = intent.getScheme();
            if (scheme == null || !SearchMainActivity.LESO_FROM.equalsIgnoreCase(scheme)) {
                this.mIsEnterFromThirdParty = false;
                initDispBegin();
            } else {
                this.mThirdPartyFrom = uri.getQueryParameter("from");
                if (isFromThirdParty(this.mThirdPartyFrom)) {
                    boolean z;
                    if (THIRD_PARTY_LETV.equalsIgnoreCase(this.mThirdPartyFrom)) {
                        z = false;
                    } else {
                        z = true;
                    }
                    this.mIsEnterFromThirdParty = z;
                    animEndProcess();
                    statisticsLaunch(0, false);
                } else {
                    this.mIsEnterFromThirdParty = false;
                    initDispBegin();
                }
            }
        } else if ("DownloadCompeleReceiver".equals(action)) {
            LogInfo.log("Emerson", "-----------------------DownloadCompeleReceiver");
            animEndProcess();
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(this).create(0)));
            getIntent().setData(null);
        } else if (intent.getBooleanExtra("isLesoIntoHomePage", false)) {
            this.mIsEnterFromThirdParty = false;
            animEndProcess();
        } else if (intent.getBooleanExtra(MainActivityConfig.IS_FACEBOOK_INTO_HOMEPAGE, false)) {
            this.mIsEnterFromThirdParty = false;
            animEndProcess();
        } else {
            initDispBegin();
        }
        UIs.createShortCut(this.mContext);
        gotoChildPage(getIntent());
        registerLogInOutReceiver();
        registerHomeKeyEventReceiver();
        parseIntentData(getIntent());
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(119));
        PreferencesManager.getInstance().saveLatestLaunchTime();
        PreferencesManager.getInstance().setShowCommentRedDot(true);
        registerTokenLoseReceiver();
        LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(LeMessageIds.MSG_WEBVIEW_UPDATE));
        if (this.mVIPFragment != null) {
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("channel", UIControllerUtils.getVipChannel(this.mContext));
            this.mVIPFragment.setArguments(mBundle);
        }
    }

    public HomeFragment getHomeFragment() {
        return this.mHomeFragment;
    }

    public boolean isHomeFragmentHidden() {
        boolean isHidden = false;
        try {
            isHidden = this.mHomeFragment.isHidden();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogInfo.log("mainactivity", "isHomeFragmentHidden isHidden : " + isHidden);
        return isHidden;
    }

    private void initPopDialog() {
        try {
            LogInfo.log("mainActivity", "initPopDialog start >>");
            DownLoadApkDialogHandler downLoadApkDialogHandler = new DownLoadApkDialogHandler(this);
            WoMainDialogHandler woMainDialogHandler = new WoMainDialogHandler(this);
            woMainDialogHandler.setSuccessor(downLoadApkDialogHandler);
            this.mContinuePayDialogHandler = new ContinuePayDialogHandler(this);
            this.mContinuePayDialogHandler.setSuccessor(woMainDialogHandler);
            InviteDialogHandler inviteDialogHandler = new InviteDialogHandler(this);
            inviteDialogHandler.setSuccessor(this.mContinuePayDialogHandler);
            this.mUpgradeDialogHandler = new UpgradeDialogHandler(this);
            this.mUpgradeDialogHandler.setSuccessor(inviteDialogHandler);
            this.mUpgradeDialogHandler.handleRequest();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initExitRetain() {
        mExitRetainController = this.mExitRetainPopupwindow.getExitRetainController();
        mExitRetainController.setExitRetainPopupwindowCallBack(new ExitRetainCallBack(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onExitAppliation() {
                LogInfo.LogStatistics("MainActivity....onExitAppliation");
                this.this$0.exitApp();
            }

            public void onShowReportData(String pageID) {
                LogInfo.LogStatistics("挽留退出框的曝光：" + pageID);
                StatisticsUtils.staticticsInfoPost(this.this$0, "19", "tc01", null, 0, null, pageID, null, null, null, null, null);
            }

            public void onClickLookBtnReportData(String pageid) {
                LogInfo.LogStatistics("点击去看看:" + pageid);
                StatisticsUtils.staticticsInfoPost(this.this$0, "0", "tc01", null, 2, null, pageid, null, null, null, null, null);
                new RetentionRateUtils().doRequest(3);
            }

            public void onClickExitBtnReportData(String pageid) {
                LogInfo.LogStatistics("点击转身离开:" + pageid);
                StatisticsUtils.staticticsInfoPost(this.this$0, "0", "tc01", null, 1, null, pageid, null, null, null, null, null);
            }

            public void onShowPopWindow() {
            }

            public void onDismissPopWindow() {
            }
        });
        mExitRetainController.getExitRetainData();
        mExitRetainController.setActivity(this);
        mExitRetainController.setCurrentPageID(PageIdConstant.index);
    }

    private RedPacketFrom getRedPacketCurrentLocation() {
        RedPacketFrom redPackFrom = new RedPacketFrom();
        if (PageIdConstant.categoryPage.equals(this.mCurrentPageId)) {
            redPackFrom.from = 4;
            redPackFrom.cid = this.mRedPacketCid;
            redPackFrom.pageid = this.mCurrentPageId;
        } else {
            redPackFrom.from = 1;
            redPackFrom.pageid = getCurrentPageId();
        }
        return redPackFrom;
    }

    private void updateHomeRedPacket() {
        if (!this.isShowingBeiginAD) {
            setRedPacketFrom(getRedPacketCurrentLocation());
            if (this.mRedPacketForecastView == null) {
                return;
            }
            if (PageIdConstant.index.equals(this.mCurrentPageId)) {
                this.mRedPacketForecastView.setVisibility(0);
            } else {
                this.mRedPacketForecastView.setVisibility(8);
            }
        }
    }

    private void getForecastRedPacket() {
        LogInfo.log("RedPacket", "get RedPacket Forecast view");
        RedPacketSdk.getInstance().getForecastView(this, new RedPacketForecastCallback(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void getCallback(Boolean has, RedPacketForecastView view) {
                LogInfo.log("RedPacket", "has forecast view = " + has);
                if (view == null) {
                    return;
                }
                if (this.this$0.getBaseRedPacket() == null || !this.this$0.getBaseRedPacket().isShown()) {
                    this.this$0.mRedPacketForecastView = view;
                    this.this$0.setRedPacketForecastView(this.this$0.mRedPacketForecastView);
                    ((ViewGroup) this.this$0.getWindow().getDecorView()).addView(this.this$0.mRedPacketForecastView);
                    LayoutParams params = (LayoutParams) this.this$0.mRedPacketForecastView.getLayoutParams();
                    params.gravity = 85;
                    params.rightMargin = DensityUtil.dip2px(this.this$0.mContext, 23.0f);
                    params.bottomMargin = DensityUtil.dip2px(this.this$0.mContext, 114.0f) / 2;
                    this.this$0.mRedPacketForecastView.setLayoutParams(params);
                    LogInfo.log("RedPacket", "set RedPacket Forecast view");
                    this.this$0.mRedPacketForecastView.setOnClickCallBack(new 1(this));
                    this.this$0.statisForcastRedPacket("19");
                    this.this$0.mHandler.postDelayed(new 2(this), 30000);
                }
            }
        });
    }

    public void checkRedPacket() {
        LogInfo.log("redPacket", "redPacket checkRedPacket =" + PreferencesManager.getInstance().getRedPackageSDK());
        if (!"0".equals(PreferencesManager.getInstance().getRedPackageSDK())) {
            if (getBaseRedPacket() == null) {
                initRedPacketView();
                getBaseRedPacket().onResume();
            }
            LetvApplication.getInstance().initRedPacketSdk();
            RedPacketPollingService.startPollingService(this, 0);
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        gotoChildPage(intent);
        parseIntentData(intent);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void gotoChildPage(Intent intent) {
        String fragmentTag = intent.getStringExtra("tag");
        if (!TextUtils.isEmpty(fragmentTag)) {
            if (TextUtils.equals(fragmentTag, FragmentConstant.TAG_FRAGMENT_CHANNEL)) {
                if (!gotoChannelByCid(intent.getStringExtra(GotoPageUtils.GOTO_CHANNEL_CID_KEY), intent.getStringExtra(GotoPageUtils.GOTO_CHANNEL_PAGE_ID_KEY), 1)) {
                }
            } else if (TextUtils.equals(fragmentTag, this.mLiveFragment.getTagName())) {
                gotoLiveFragment(intent.getStringExtra(GotoPageUtils.GOTO_CHILD_LIVE_PAGE_KAY));
            } else if (TextUtils.equals(fragmentTag, this.mHomeFragment.getTagName())) {
                this.mMainBottomNavigationView.setSelectedType(NavigationType.HOME);
            } else if (TextUtils.equals(fragmentTag, this.mChannelDetailFragment.getTagName())) {
                Bundle bundle = intent.getBundleExtra(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                if (bundle != null) {
                    Channel channel = (Channel) bundle.get("channel");
                    int from = bundle.getInt("from", -1);
                    if (channel != null) {
                        gotoChannelByCid(String.valueOf(channel.id), channel.pageid, from);
                    }
                }
            } else if (TextUtils.equals(fragmentTag, this.mMineFragment.getTagName())) {
                this.mMainBottomNavigationView.setSelectedType(NavigationType.MINE);
            } else if (TextUtils.equals(fragmentTag, this.mFindFragment.getTagName())) {
                this.mMainBottomNavigationView.setSelectedType(NavigationType.FIND);
            } else {
                LogInfo.log(getActivityName() + "||wlx", "fragmentTag 参数不对 fragmentTag =" + fragmentTag);
            }
        }
    }

    public boolean currentPidIsVip(String pid) {
        if (TextUtils.equals(AlbumInfo.Channel.VIP_PAGEID_TEST, pid) || TextUtils.equals(AlbumInfo.Channel.VIP_PAGEID, pid) || TextUtils.equals(AlbumInfo.Channel.VIP_PAGEID_HONGKONG, pid)) {
            return true;
        }
        return false;
    }

    private void initFloatBall() {
        if (this.mFloatBallConfig == null) {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_FLOAT_BALL_INIT, this));
            if (LeResponseMessage.checkResponseMessageValidity(response, FloatBallConfig.class)) {
                this.mFloatBallConfig = (FloatBallConfig) response.getData();
            }
        }
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.hideFloat();
        }
    }

    private void handleTwiceBackExit() {
        int count = this.keyBackClickCount;
        this.keyBackClickCount = count + 1;
        LogInfo.log("", "KEYCODE_BACK count " + count);
        switch (count) {
            case 0:
                UIsUtils.showToast(2131100682);
                this.mHandler.postDelayed(new Runnable(this) {
                    final /* synthetic */ MainActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void run() {
                        this.this$0.keyBackClickCount = 0;
                    }
                }, 3000);
                return;
            case 1:
                LogInfo.LogStatistics("MianActivity....back exit");
                exitApp();
                return;
            default:
                return;
        }
    }

    private void exitApp() {
        if (this.mUpgradeDialogHandler != null) {
            this.mUpgradeDialogHandler.destroyUpgrade();
        }
        LogInfo.log("fornia", "MainActivity exitApp download stop service");
        DownloadManager.stopDownloadService();
        UIsUtils.cancelToast();
        LogInfo.log("MAIN", "exitApp isNewUser " + LetvUtils.isNewUser());
        if (LetvUtils.isNewUser()) {
            mExitRetainController.clearCacheCmsID();
        }
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4:
                event.startTracking();
                return true;
            case 24:
            case 25:
                this.mLiveFragment.onKeyDown(keyCode, event);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !event.isTracking() || event.isCanceled()) {
            return super.onKeyUp(keyCode, event);
        }
        if (this.mLiveFragment.onBackPressed() && event.getRepeatCount() == 0) {
            return true;
        }
        if (!LetvUtils.isNoRetainPopupPcode() && mExitRetainController.showExitRetainPopupwindow(getWindow().getDecorView().getRootView())) {
            return true;
        }
        handleTwiceBackExit();
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mFloatBallConfig != null && this.mFloatBallConfig.dispatchTouchEvent(ev)) {
            return false;
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    protected void onPause() {
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.onPause();
        }
        if (this.isShowingBeiginAD && this.mAdView != null) {
            this.mAdView.onPause();
        }
        super.onPause();
    }

    private void initTasks() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(201, new TaskRunnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                if (LeMessage.checkMessageValidity(message, GoToChannel.class)) {
                    GoToChannel data = (GoToChannel) message.getData();
                    this.this$0.gotoChannel(data.cid, data.redirect, data.blockName, data.type);
                }
                return null;
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(202, new TaskRunnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                if (LeMessage.checkMessageValidity(message, GoToChannelByCid.class)) {
                    GoToChannelByCid data = (GoToChannelByCid) message.getData();
                    this.this$0.gotoChannelByCid(data.cid, data.pageid, data.from, data.blockName);
                }
                return null;
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_MAIN_GO_TO_LIVE_ROOM, new TaskRunnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                if (LeMessage.checkMessageValidity(message, String.class)) {
                    this.this$0.gotoLiveFragment((String) message.getData());
                }
                return null;
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_MAIN_UPDATA_INDICATOR, new TaskRunnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                if (LeMessage.checkMessageValidity(message, String.class)) {
                    this.this$0.getHomeFragment().updataIndicator((String) message.getData());
                }
                return null;
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_MAIN_GET_CURR_PAGE, new TaskRunnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public LeResponseMessage run(LeMessage message) {
                if (message == null || !(message.getContext() instanceof MainActivity)) {
                    return null;
                }
                return new LeResponseMessage(LeMessageIds.MSG_MAIN_GET_CURR_PAGE, this.this$0.getCurrentPageId());
            }
        }));
    }

    private void init() {
        initTasks();
        ImageDownloader.getInstance().download(TipUtils.getTipMessage("100121"));
        this.contentView = (ViewGroup) findViewById(R.id.main_content);
        this.mMainTopNavigationView = (MainTopNavigationView) getViewById(R.id.main_top_navigation);
        this.mMainBottomNavigationView = (MainBottomNavigationView) getViewById(R.id.main_bottom_navigation);
        this.mMainBottomNavigationView.setItemCheckedListener(this);
        this.mMainBottomNavigationView.setNavigations();
        this.findRedPointView = new BottomRedPointView(this, findViewById(R.id.main_find_red_point_target));
        this.mMineRedPointView = new BottomRedPointView(this, findViewById(R.id.main_mine_red_point_target));
        this.imageLayout = findViewById(R.id.image_layout);
        this.imageLayout.setVisibility(0);
        this.mAdBootMask = (ImageView) findViewById(R.id.ad_boot_mask);
        this.mTopNavigationView = findViewById(R.id.top_navigation);
        this.mAdView = (AdViewProxy) findViewById(R.id.ad_view);
        this.mAdView.setAdType(AdCommonType.COMMON_BEGIN);
        this.mAdView.setClientListener(this.clientListener);
        this.mAdView.setBootView(this.mAdBootMask, this.contentView);
        this.mAdView.setAdPlayStateListener(new AdPlayStateListener(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onADPlayStateChange(Bundle bundle) {
                if (bundle != null) {
                    int state = bundle.getInt("state");
                    if (state == 3) {
                        this.this$0.setRedPacketFrom(new RedPacketFrom(0));
                        this.this$0.isShowingBeiginAD = true;
                        LogInfo.log("Emerson", "state1 =" + state);
                        this.this$0.mAdBootMask.setVisibility(8);
                        this.this$0.mAdView.setVisibility(0);
                        if (this.this$0.mFloatBallConfig != null) {
                            this.this$0.mFloatBallConfig.hideFloat();
                        }
                    } else if (state == 4 || state == -1) {
                        this.this$0.isShowingBeiginAD = false;
                        LogInfo.log("Emerson", "state2 =" + state);
                        this.this$0.mAdView.setVisibility(8);
                        this.this$0.mAdBootMask.setVisibility(8);
                        this.this$0.animEndProcess();
                        this.this$0.updateHomeRedPacket();
                        this.this$0.getForecastRedPacket();
                        this.this$0.requestFloatball();
                    }
                }
            }
        });
    }

    private void animProcess() {
        this.contentView.setVisibility(0);
        this.imageLayout.startAnimation(this.topHomePageAnim);
    }

    private void initHomePageAnimation() {
        this.topHomePageAnim = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
        this.topHomePageAnim.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (!this.this$0.isFirstLaunchApp) {
                    this.this$0.animEndProcess();
                }
            }
        });
    }

    private void setAnimationImage() {
        if (this.topViewLayout != null) {
            this.logoOrSigner = !this.logoOrSigner;
            float cX = ((float) this.topViewLayout.getWidth()) / 2.0f;
            float cY = ((float) this.topViewLayout.getHeight()) / 2.0f;
            if (this.logoOrSigner) {
                this.rotateAnim = this.rotateAnimDe;
            } else {
                this.rotateAnim = this.rotateAnimIn;
            }
            if (this.rotateAnim != null) {
                this.rotateAnim.setFillAfter(true);
                this.topViewLayout.startAnimation(this.rotateAnim);
            }
        }
    }

    private void refreshWaittingText() {
        this.waitTimeCountElapsed++;
        if (this.waitTimeCountElapsed > this.WAIT_TIME_COUNT) {
            animProcess();
        } else if (this.waitTimeCountElapsed == this.WAIT_TIME_COUNT) {
            this.mHandler.sendEmptyMessageDelayed(3, 400);
        } else {
            this.mHandler.sendEmptyMessageDelayed(3, 1000);
        }
    }

    private void handlerProcess(boolean isAdSuccess) {
        if (!this.currentIsFeatureOrHomepage) {
            this.isChoice = true;
            if (isAdSuccess) {
                LogInfo.log("picView", "---isAdSuccess true----");
                return;
            }
            LogInfo.log("picView", "---isAdSuccess false----");
            refreshWaittingText();
        }
    }

    private void animEndProcess() {
        this.mMainShow = true;
        getWindow().clearFlags(1024);
        if (VERSION.SDK_INT >= 19) {
            getWindow().addFlags(67108864);
        }
        LogInfo.log("bootPic", "animate end");
        this.contentView.setVisibility(0);
        getWindow().setBackgroundDrawableResource(2131493324);
        this.animationIsFinish = true;
        this.imageLayout.setVisibility(8);
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            String scheme = intent.getScheme();
            if (scheme != null && SearchMainActivity.LESO_FROM.equalsIgnoreCase(scheme)) {
                if (!isFromThirdParty(data.getQueryParameter("from"))) {
                    return;
                }
                return;
            }
        }
        if (!this.initTashIsSuccess || this.mIsFromPush || this.mLaunchByPush || this.mIsForceLaunch) {
            getHomeFragment().setHomeRecordVisible(true);
        } else {
            initPopDialog();
        }
        this.isAnimationIsFinish = true;
    }

    public boolean isFromThirdParty(String from) {
        if (TextUtils.isEmpty(from)) {
            return false;
        }
        if (from.equalsIgnoreCase(THIRD_PARTY_BAIDU) || from.equalsIgnoreCase(THIRD_PARTY_LETV) || from.equalsIgnoreCase(THIRD_PARTY_WANGDOUJIA) || from.equalsIgnoreCase(THIRD_PARTY_LESO) || from.equalsIgnoreCase(THIRD_PARTY_YINGYONGBAO)) {
            return true;
        }
        return false;
    }

    private void showNewFeaturesDialog() {
        this.waitTimeCountElapsed = 0;
        if (PreferencesManager.getInstance().isShowNewFeaturesDialog()) {
            PreferencesManager.getInstance().notShowNewFeaturesDialog();
            this.isFirstLaunchApp = true;
            this.currentIsFeatureOrHomepage = true;
            animEndProcess();
            requestFloatball();
            statisticsLaunch(0, false);
            AdsManagerProxy.getInstance(this).installFirst();
            getForecastRedPacket();
            return;
        }
        this.isShowingBeiginAD = false;
        statisticsLaunch(this.WAIT_TIME_COUNT, false);
        AdElementMime adElementMime = AdsManagerProxy.getInstance(this).getBeginAdInfo();
        handlerDelayToMian(adElementMime);
        if (adElementMime != null) {
            this.mAdView.showAD(adElementMime);
        } else {
            timeElapsedCount();
            loadCmsImageView(true);
            requestFloatball();
            getForecastRedPacket();
        }
        this.currentIsFeatureOrHomepage = false;
    }

    private void handlerDelayToMian(AdElementMime adElementMime) {
        int delayTime = 6;
        if (adElementMime != null && adElementMime.duration > 0) {
            delayTime = adElementMime.duration + 3;
        }
        this.mHandler.postDelayed(new Runnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                if (!this.this$0.mMainShow) {
                    this.this$0.animEndProcess();
                }
            }
        }, (long) (delayTime * 1000));
    }

    private void requestFloatball() {
        if (NetworkUtils.isNetworkAvailable() && TipUtils.getTipTitle(DialogMsgConstantId.CONSTANT_80003, "1").equals("1")) {
            if (this.mMainFlow == null) {
                this.mMainFlow = new MainFlow(this, this);
            }
            this.mMainFlow.requestFloatTask();
        }
    }

    public void initUserGuide(boolean isShow) {
        if (!(this.mUserGuideLayout == null || PreferencesManager.getInstance().getHasShowUserGuide())) {
            this.mUserGuideLayout.setVisibility(isShow ? 0 : 8);
        }
        if (!PreferencesManager.getInstance().getHasShowUserGuide() && isShow) {
            this.mTopNavigationView.getLayoutParams().height = VERSION.SDK_INT >= 19 ? UIsUtils.dipToPx(44.0f) + UIsUtils.getStatusBarHeight(this.mContext) : UIsUtils.dipToPx(44.0f);
            this.mTopNavigationView.setVisibility(0);
            this.mUserGuideLayout = (LinearLayout) findViewById(R.id.user_guide);
            this.mUserGuideLayout.setVisibility(0);
            this.mUserGuideLayout.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ MainActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(View v) {
                    this.this$0.mUserGuideLayout.setVisibility(8);
                    PreferencesManager.getInstance().setHasShowUserGuide(true);
                }
            });
        }
    }

    public void initCradManagerGuide() {
        int showNum = PreferencesManager.getInstance().getHasShowCardGuide();
        if (showNum < 3 && this.mCardmanagerLayout == null) {
            this.mCardmanagerLayout = (RelativeLayout) findViewById(R.id.card_manager_entrance_guide);
            TextView tv = (TextView) this.mCardmanagerLayout.findViewById(R.id.tip_message);
            TextView clickView = (TextView) this.mCardmanagerLayout.findViewById(R.id.click_view);
            TextView topView = (TextView) this.mCardmanagerLayout.findViewById(R.id.top_view);
            SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
            builder.setSpan(new ForegroundColorSpan(getResources().getColor(2131493248)), 2, 6, 33);
            tv.setText(builder);
            final int num = showNum + 1;
            this.mCardmanagerLayout.setVisibility(0);
            this.mCardmanagerLayout.bringToFront();
            showAnimat(this.mCardmanagerLayout, true);
            topView.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ MainActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(View v) {
                }
            });
            clickView.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ MainActivity this$0;

                public void onClick(View v) {
                    this.this$0.showAnimat(this.this$0.mCardmanagerLayout, false);
                    PreferencesManager.getInstance().setHasShowCardGuide(num);
                }
            });
        }
    }

    private void showAnimat(final View view, final boolean showView) {
        AlphaAnimation animation = showView ? new AlphaAnimation(0.0f, 1.0f) : new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(300);
        animation.setFillAfter(true);
        view.setAnimation(animation);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ MainActivity this$0;

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(showView ? 0 : 8);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void timeElapsedCancel() {
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.isTimeOutLoadImage = false;
        this.timer = null;
    }

    private void loadCmsImageView(final boolean isReallyLoad) {
        Booting booting = DBManager.getInstance().getFestivalImageTrace().getCur();
        LogInfo.log("zhaoxiang", "-------------------" + (booting == null));
        if (booting != null) {
            ImageDownloader.getInstance().download(booting.pic, new ImageDownloadStateListener(this) {
                final /* synthetic */ MainActivity this$0;

                public void loading() {
                }

                public void loadSuccess(Bitmap bitmap) {
                    this.this$0.cmsIsSuccess = true;
                    if (isReallyLoad && !this.this$0.isTimeOutLoadImage) {
                        this.this$0.timeElapsedCancel();
                        this.this$0.handlerProcess(false);
                    }
                }

                public void loadSuccess(Bitmap bitmap, String localPath) {
                }

                public void loadSuccess(View view, Bitmap bitmap, String localPath) {
                }

                public void loadFailed() {
                    if (isReallyLoad && !this.this$0.isTimeOutLoadImage) {
                        this.this$0.timeElapsedCancel();
                        this.this$0.animProcess();
                    }
                }
            });
        } else if (isReallyLoad && !this.isTimeOutLoadImage) {
            timeElapsedCancel();
            animProcess();
        }
    }

    public void onBottomNavigationClick(NavigationType clickType, NavigationType checkedType) {
        LogInfo.log("jc666", "click type=" + clickType + ", checked type=" + checkedType);
        StatisticsUtils.statisticsActionInfo(this.mContext, this.mCurrentPageId, "0", "21", this.mContext.getString(clickType.mTextId), -1, null);
        LogInfo.log("Iris", this.mCurrentPageId);
        if (clickType != checkedType) {
            String title = "";
            if (clickType == NavigationType.HOME) {
                title = getString(2131100365);
                this.mCurrentPageId = PageIdConstant.index;
                showFragmentIfNeeded(this.mHomeFragment);
                if (this.mFloatBallConfig != null && this.mIsFloatBallRequestFinished) {
                    this.mFloatBallConfig.showFloat("1");
                }
            } else if (clickType == NavigationType.VIP) {
                title = getString(2131100368);
                this.mCurrentPageId = PageIdConstant.vipCategoryPage;
                showFragmentIfNeeded(this.mVIPFragment);
                if (this.mFloatBallConfig != null) {
                    this.mFloatBallConfig.showFloat("4");
                }
                this.mVIPFragment.refreshHead();
            } else if (clickType == NavigationType.LIVE) {
                title = getString(2131100366);
                this.mCurrentPageId = this.mLiveFragment.getPageId();
                LogInfo.log("jc666", "bottom checked live pageid=" + this.mCurrentPageId);
                showFragmentIfNeeded(this.mLiveFragment);
            } else if (clickType == NavigationType.FIND) {
                setNewFindVisible(false);
                this.mFindFragment.saveFindSpreadTimeStamp();
                title = getString(2131100364);
                this.mCurrentPageId = PageIdConstant.finaPage;
                showFragmentIfNeeded(this.mFindFragment);
                if (this.mFloatBallConfig != null) {
                    this.mFloatBallConfig.hideFloat();
                }
            } else if (clickType == NavigationType.MINE) {
                title = getString(2131100367);
                this.mCurrentPageId = PageIdConstant.myHomePage;
                showFragmentIfNeeded(this.mMineFragment);
                if (this.mFloatBallConfig != null) {
                    this.mFloatBallConfig.showFloat("3");
                }
            }
            if (!TextUtils.isEmpty(title)) {
                this.mMainTopNavigationView.setTitle(title);
            }
            this.mMainTopNavigationView.setImagesVisibility(clickType);
            updateHomeRedPacket();
            if (!TextUtils.isEmpty(this.mCurrentPageId) && clickType != NavigationType.VIP && clickType != NavigationType.HOME) {
                StatisticsUtils.statisticsActionInfo(this, this.mCurrentPageId, "19", null, null, -1, null);
            }
        } else if (clickType == NavigationType.HOME && this.mHomeFragment.isVisible()) {
            if (this.mHomeFragment.scrollToTop()) {
                this.mHomeFragment.updataIndicatorToFirst();
            }
        } else if (clickType == NavigationType.VIP) {
            this.mVIPFragment.scrollToTop();
        }
    }

    public void gotoHomeFragment() {
        this.mMainBottomNavigationView.setSelectedType(NavigationType.HOME);
    }

    private void initDispBegin() {
        this.mOpenAppJustNow = true;
        if (this.mLaunchByPush || this.mFromMobileSite) {
            PreferencesManager.getInstance().notShowNewFeaturesDialog();
            animEndProcess();
            return;
        }
        initHomePageAnimation();
        showNewFeaturesDialog();
    }

    private void parseIntentData(Intent intent) {
        if (intent != null) {
            this.mIsFromPush = isFromPush(intent);
            this.mIsForceLaunch = intent.getBooleanExtra("forceLaunch", false);
            this.mFromMobileSite = intent.getBooleanExtra("from_M", false);
            this.mChannelPageData = (Channel) intent.getSerializableExtra("channel");
            this.mShouldJumpToDownloadPage = intent.getBooleanExtra(MyDownloadActivityConfig.TO_DOWNLOAD, false);
            jumpFromPush(intent, intent.getIntExtra("LaunchMode", 0));
        }
    }

    private void jumpFromPush(Intent intent, int launchMode) {
        this.mIsLivePush = false;
        this.mLaunchByPush = false;
        if (launchMode == 16) {
            MainLaunchUtils.launchDemand(this, intent);
            LetvApplication.getInstance().setVipTagShow(false);
        } else if (launchMode == 18) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this).createTopic(Math.max(0, intent.getLongExtra(PlayConstant.ZID, 0)), 13)));
            LetvApplication.getInstance().setVipTagShow(false);
        } else if (launchMode == 21) {
            launchWebView(intent);
            LetvApplication.getInstance().setVipTagShow(false);
        } else if (launchMode == 17) {
            MainLaunchUtils.launchLive(this, intent);
            LetvApplication.getInstance().setVipTagShow(false);
        } else if (launchMode == 19) {
            LetvApplication.getInstance().setVipTagShow(false);
            LiveLaunchUtils.launchLiveById(this, intent.getStringExtra(PlayConstant.LIVE_LAUNCH_ID));
        } else if (launchMode == 20) {
            LetvApplication.getInstance().setVipTagShow(false);
            gotoLiveFragment(intent.getStringExtra("channel_type"));
        } else if (launchMode == 22) {
            LetvHotActivity.launch(this.mContext, intent.getStringExtra("pageId"), intent.getIntExtra("vid", 0), true);
        } else {
            LetvApplication.getInstance().setVipTagShow(true);
        }
    }

    private boolean isFromPush(Intent intent) {
        boolean z = false;
        this.mLaunchByPush = intent.getBooleanExtra(MyDownloadActivityConfig.FROM_PUSH, false);
        if (intent.getBooleanExtra("isLivePush", false) || intent.getIntExtra("LaunchMode", 0) == 19 || intent.getIntExtra("LaunchMode", 0) == 20) {
            z = true;
        }
        this.mIsLivePush = z;
        if (this.mIsLivePush) {
            BaseApplication.getInstance().setPush(true);
        }
        return this.mLaunchByPush;
    }

    private void timeElapsedCount() {
        this.timer = new Timer();
        this.isTimeOutLoadImage = false;
        this.timer.schedule(new TimerTask(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                this.this$0.isTimeOutLoadImage = true;
                this.this$0.mHandler.sendEmptyMessage(4);
                LogInfo.log("bootPic", "----timeout!");
            }
        }, 3000);
    }

    private void launchWebView(Intent intent) {
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra(LetvWebViewActivityConfig.LOAD_TYPE);
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        if (TextUtils.isEmpty(url)) {
            UIsUtils.showToast(2131101159);
        } else {
            new LetvWebViewActivityConfig(this).launch(url, title);
        }
    }

    public void gotoLiveFragment(String childId) {
        LogInfo.log("zhuqiao", "跳转至直播大厅");
        this.mLiveFragment.gotoChildPage(childId);
        this.mMainBottomNavigationView.setSelectedType(NavigationType.LIVE);
    }

    private void setFromAndFloatBall(String cid, int from) {
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.showFloat("7", cid + "");
        }
        this.mFromWhere = from;
        this.mRedPacketCid = cid;
        updateHomeRedPacket();
    }

    public boolean gotoChannelByCid(String cid, String pageid, int from) {
        gotoChannelByCid(cid, pageid, from, "");
        return true;
    }

    public boolean gotoChannelByCid(String cid, String pageid, int from, String blockName) {
        if (currentPidIsVip(pageid)) {
            this.mMainBottomNavigationView.setSelectedType(NavigationType.VIP);
        } else {
            this.mMainBottomNavigationView.setSelectedType(NavigationType.HOME);
            UIControllerUtils.gotoChannelByCid(this.mContext, cid, pageid, blockName);
        }
        setFromAndFloatBall(cid, from);
        return true;
    }

    public boolean gotoChannel(String cid, RedirectData redirect, String blockName, int type) {
        if (TextUtils.isEmpty(cid) || redirect == null) {
            return false;
        }
        UIControllerUtils.gotoChannel(this.mContext, cid, redirect, blockName, type);
        if (1 != redirect.redirectType && 2 != redirect.redirectType) {
            return true;
        }
        setFromAndFloatBall(cid, 1);
        return true;
    }

    public void registerTokenLoseReceiver() {
        this.mTokenLoseReceiver = new TokenLoseReceiver(this);
        try {
            IntentFilter intentFilter = new IntentFilter("TokenLoseReceiver1");
            intentFilter.addAction("TokenLoseReceiver2");
            registerReceiver(this.mTokenLoseReceiver, intentFilter);
        } catch (Exception e) {
            LogInfo.log("ZSM", "TAG = TokenLoseReceiver\nstack_info: " + Log.getStackTraceString(new Throwable()));
            e.printStackTrace();
        }
    }

    public void unRegisterTokenLoseReceiver() {
        try {
            if (this.mTokenLoseReceiver != null && this != null) {
                unregisterReceiver(this.mTokenLoseReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTipCallback(TipMapBean bean) {
        TipUtils.mTipMapBean = bean;
        PreferencesManager.getInstance().setInviteVisibleFlag(BaseTypeUtils.stoi(TipUtils.getTipMessage(DialogMsgConstantId.INVITE_SWITCH, "0")) != 0);
    }

    public void checkUpdate(boolean isSucceed) {
        if (!this.animationIsFinish || this.mIsFromPush || this.mLaunchByPush || this.mIsForceLaunch) {
            this.initTashIsSuccess = true;
        } else if (this.mUpgradeDialogHandler != null) {
            this.mUpgradeDialogHandler.checkUpgrade(null);
        }
        StatisticsUtils.submitLocalErrors(getActivity().getApplicationContext());
    }

    public void checkAd(boolean isSucceed) {
        if (isSucceed) {
            AdsManagerProxy.getInstance(this).updateBeginAdInfo();
        }
    }

    public void showChannelRecommend(boolean isShow) {
    }

    public void checkUninstallEnable(boolean isSucceed) {
        LogInfo.log(getActivityName() + "||wlx", "UninstallEnable=" + UninstalledObserver.isEnable(this));
        UninstalledObserver.init(LetvConstant.LETV_UNINSTALL_URL + "pcode=" + LetvConfig.getPcode() + "&devid=" + Global.DEVICEID, this);
    }

    public void updateUI() {
        if (this.mMainTopNavigationView != null) {
            this.mMainTopNavigationView.setLesoSearchContent();
        }
        LetvBaseBean letvBaseBean = FileUtils.getObjectFromFile("themedata");
        if (letvBaseBean instanceof ThemeDataBean) {
            ThemeDataBean themeDataBean = (ThemeDataBean) letvBaseBean;
            final ThemeDataManager themeDataManager = ThemeDataManager.getInstance(this);
            themeDataManager.init(themeDataBean, new IThemeImagesDownloadCallBack(this) {
                final /* synthetic */ MainActivity this$0;

                public void updateViews() {
                    LogInfo.log("jc666", "start update theme!");
                    themeDataManager.setContentTheme(this.this$0.findViewById(R.id.main_bottom_line), ThemeDataManager.NAME_BOTTOM_NAVIGATION_LINE);
                    this.this$0.mMainTopNavigationView.updateTheme(this.this$0.mMainBottomNavigationView.getCheckedNavigationType());
                    this.this$0.mMainBottomNavigationView.updateTheme();
                    if (this.this$0.mHomeFragment != null) {
                        this.this$0.mHomeFragment.updateTheme();
                    }
                }
            });
        }
    }

    public void homePageLoad() {
        if (this.mHomeFragment != null) {
            this.mHomeFragment.initNavigation(1);
        }
        if (LetvUtils.isInHongKong()) {
            LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(117, PageIdConstant.index));
        }
    }

    public void beanCallBack(LetvBaseBean bean) {
        if (bean != null) {
            if ((bean instanceof FindListDataBean) && this.mFindFragment != null) {
                this.mFindFragment.setFindListDataBean((FindListDataBean) bean);
                setNewFindVisible(((FindListDataBean) bean).hasNewSpread());
            } else if (bean instanceof FloatBallBeanList) {
                FloatBallBeanList result = (FloatBallBeanList) bean;
                if (!BaseTypeUtils.isListEmpty(result.getResult())) {
                    this.mIsFloatBallRequestFinished = true;
                    PreferencesManager.getInstance().saveTimeOfFloatInfoSuccess(StringUtils.timeString(System.currentTimeMillis()));
                    LetvApplication.getInstance().setmFloatBallBeanList(result.getResult());
                    try {
                        if (this.mFloatBallConfig != null) {
                            this.mFloatBallConfig.showFloat("1");
                            if (!TextUtils.equals(StringUtils.timeString(System.currentTimeMillis()), StringUtils.timeString(PreferencesManager.getInstance().getAttendanceCacheTime()))) {
                                this.mFloatBallConfig.openFloat();
                                PreferencesManager.getInstance().setLastAttendanceTime(System.currentTimeMillis());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void locationChange() {
        if (this.mHomeFragment != null) {
            this.mHomeFragment.initNavigation(2);
        }
        if (this.mMineFragment != null) {
            this.mMineFragment.setReload();
        }
        if (this.mFindFragment != null) {
            this.mFindFragment.setReload();
        }
        RxBus.getInstance().send(new COUNTRY_CHANGE_EVENT());
    }

    protected void onDestroy() {
        LogInfo.log("fornia", "mainactivity!!!!!!!!!!!!!!! onDestroy()");
        super.onDestroy();
        BaseApplication.getInstance().mIsMainActivityAlive = false;
        this.handler.removeCallbacksAndMessages(null);
        this.mHandler.removeCallbacksAndMessages(null);
        StatisticsUtils.statisticsActionInfo(this, this.mCurrentPageId, "0", "12", null, -1, "time=" + LetvDateUtils.timeClockString("yyyyMMdd_HH:mm:ss"));
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.onDestory();
            this.mFloatBallConfig = null;
        }
        if (this.mMainFlow != null) {
            this.mMainFlow.destroy();
        }
        if (this.mAdView != null) {
            this.mAdView.closeAD();
            this.mAdView.removeAllViews();
            this.mAdView = null;
        }
        LeMessageManager.getInstance().unRegister(201);
        LeMessageManager.getInstance().unRegister(202);
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_MAIN_GO_TO_LIVE_ROOM);
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_MAIN_UPDATA_INDICATOR);
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_MAIN_GET_CURR_PAGE);
        LetvApplication.onAppExit();
        unRegisterHomeKeyEventReceiver();
        unregisterLogInOutReceiver();
        unRegisterTokenLoseReceiver();
        PageCardFetcher.reset();
        AlbumPageCardFetcher.reset();
        ThemeDataManager.clearThemeInfos();
        StatisticsUtils.clearStatisticsInfo(this);
        instance = null;
        if (PatchUtils.isPatchDownload) {
            Process.killProcess(Process.myPid());
        }
    }

    public void dexPatch() {
        if (PreferencesManager.getInstance().isDexPatchEnable()) {
            PatchUtils.loadNewPatch();
        }
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.MAIN_FRAGMENT_TAG_ARRAY;
    }

    public String getActivityName() {
        return MainActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (this.mFloatBallConfig != null) {
            if (UIsUtils.isLandscape(this)) {
                this.mFloatBallConfig.hideFloat(true);
            } else {
                this.mFloatBallConfig.showFloat();
            }
        }
        RxBus.getInstance().send(new SysConfigChangeEvent());
        if (!(newConfig == null || newConfig.locale == null || TextUtils.equals(LetvUtils.getCountry(), newConfig.locale.getCountry()))) {
            if (this.mMainFlow == null) {
                this.mMainFlow = new MainFlow(this, this);
            }
            this.mMainFlow.requestTipInfo();
        }
        super.onConfigurationChanged(newConfig);
    }

    protected void onStop() {
        if (this.isShowingBeiginAD && this.mAdView != null) {
            this.mAdView.onStop();
        }
        this.mThreeDialogShowAlready = false;
        super.onStop();
    }

    protected void onStart() {
        super.onStart();
        if (this.mLiveFragment != null && !this.mLiveFragment.isHidden() && mHomeKeyEventReceiver != null && mHomeKeyEventReceiver.isHomeClicked()) {
            this.mLiveFragment.startFromBackground();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mOpenAppJustNow) {
            this.mOpenAppJustNow = false;
        } else if (this.mThreeDialogShowAlready) {
            this.mThreeDialogShowAlready = false;
        }
        if (!UserInfoTools.isUserExist(getApplicationContext()) && !TextUtils.isEmpty(PreferencesManager.getInstance().getUserId())) {
            UserInfoTools.copyUpgradeUserInfo(getApplicationContext(), PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getSso_tk(), PreferencesManager.getInstance().getShareUserId(), PreferencesManager.getInstance().getShareToken());
        }
    }

    public void registerLogInOutReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(LOGIN_IN_OUT_TAG);
            registerReceiver(this.mBroadcastLogInOutReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterLogInOutReceiver() {
        try {
            unregisterReceiver(this.mBroadcastLogInOutReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentPageId() {
        return this.mCurrentPageId;
    }

    public void setCurrentPageId(String currentPageId) {
        this.mCurrentPageId = currentPageId;
    }

    public LiveFragment getLiveFragment() {
        return this.mLiveFragment;
    }

    public void statisForcastRedPacket(String acode) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String str = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
        String str2 = "time=" + time + "rpid=" + DataUtils.getUnEmptyData(RedPacketSdkManager.getInstance().getForecastBean().id);
        StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.index, acode, "rpid12", str, 1, str2);
    }

    private void initDownloadConfig() {
        LogInfo.log("XX", "initDownloadConfig >>>>>>>>>");
        if (LetvUtils.isServiceRunning(getApplicationContext(), "com.letv.download.service.DownloadService")) {
            LogInfo.log("MAIN", "initDownloadConfig download service run >>");
            this.mHandler.postDelayed(new Runnable(this) {
                final /* synthetic */ MainActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void run() {
                    DownloadManager.startDownloadService(null);
                    DownloadManager.sendMyDownloadClass(MyDownloadActivity.class);
                }
            }, SPConstant.DELAY_BUFFER_DURATION);
        } else {
            DownloadManager.startDownloadService(null);
            DownloadManager.sendMyDownloadClass(MyDownloadActivity.class);
        }
        if (TextUtils.isEmpty(StoreManager.getDownloadPath())) {
            LogInfo.log("MainAcitivity", "initDownloadConfig StoreManager.getDownloadPath() inilocation !!!");
            StoreManager.initCurrentStoreLocation();
        }
        new Thread(new Runnable(this) {
            final /* synthetic */ MainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                try {
                    if (CompatDataManager.isCompatDBdata()) {
                        CompatDataManager.createCompatDBDataWorker(BaseApplication.getInstance()).doCompatOldDownloadDB();
                        DownloadManager.initDownloadingData();
                        DownloadManager.startAllDownload();
                        StoreManager.getFileDataStoreWorker().updateDownloadFileData();
                    } else if (CompatDataManager.getCompatDBFinish()) {
                        LogInfo.log("mainacitivity", "getCompatDBFinish true !!! ");
                    } else if (StoreManager.getFileDataStoreWorker().isCanDownloadRecover()) {
                        boolean isSuccess = StoreManager.getFileDataStoreWorker().downloadFileDataToDB();
                        StoreManager.getFileDataStoreWorker().setRecover();
                        LogInfo.log("mainacitivity", "downloadFileDataToDB isSuccess " + isSuccess);
                    } else if (CompatDataManager.isCompatFileData()) {
                        CompatDataManager.createCompatFileDataWorker(BaseApplication.getInstance()).doCompatOldFileData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
