package com.letv.android.client.album;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import cn.com.iresearch.vvtracker.IRVideo;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.album.controller.AlbumBlockController;
import com.letv.android.client.album.controller.AlbumCacheController;
import com.letv.android.client.album.controller.AlbumCollectController;
import com.letv.android.client.album.controller.AlbumController;
import com.letv.android.client.album.controller.AlbumErrorTopController;
import com.letv.android.client.album.controller.AlbumGestureController;
import com.letv.android.client.album.controller.AlbumLoadController;
import com.letv.android.client.album.controller.AlbumMidAdController;
import com.letv.android.client.album.controller.AlbumNetChangeController;
import com.letv.android.client.album.controller.AlbumPlayAdController;
import com.letv.android.client.album.controller.AlbumPlayGeneralTrailController;
import com.letv.android.client.album.controller.AlbumPlayNextController;
import com.letv.android.client.album.controller.AlbumPlayingHandler;
import com.letv.android.client.album.controller.AlbumRecommendController;
import com.letv.android.client.album.controller.AlbumRedPacketController;
import com.letv.android.client.album.controller.AlbumShareController;
import com.letv.android.client.album.controller.LongWatchController;
import com.letv.android.client.album.controller.ScreenProjectionController;
import com.letv.android.client.album.half.AlbumHalfFragment;
import com.letv.android.client.album.half.controller.AlbumHalfController;
import com.letv.android.client.album.observable.AlbumGestureObservable;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.android.client.album.service.SimplePluginDownloadService;
import com.letv.android.client.album.service.SimplePluginDownloadService.PluginInstalled;
import com.letv.android.client.album.utils.AlbumLaunchUtils;
import com.letv.android.client.album.view.AlbumBaseControllerFragment;
import com.letv.android.client.album.view.AlbumPlayContainView;
import com.letv.android.client.album.view.AlbumPlayFragment;
import com.letv.android.client.album.view.AlbumVideoController;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.FloatBallConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.messagemodel.BarrageAlbumProtocol;
import com.letv.android.client.commonlib.messagemodel.ShareWindowProtocol;
import com.letv.android.client.commonlib.view.LongWatchNoticeDialog;
import com.letv.business.flow.album.AlbumFlowUtils;
import com.letv.business.flow.album.AlbumPlayBaseFlow;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.PlayObservable;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.business.flow.live.LiveWatchAndBuyFlow.AddToCartResultStatus;
import com.letv.core.BaseApplication;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.PlayerType;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequestQueue.RequestFilter;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.common.Constant;
import master.flame.danmaku.danmaku.parser.IDataSource;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class AlbumPlayActivity extends LetvBaseActivity {
    public static final int LANDSCAPE_EXPAND_WIDTH = UIsUtils.dipToPx(283.0f);
    public static final String NOT_SHOW_NONCOPYRIGHT_WEBVIEW_URL = "-1";
    public static boolean sIsBlockPause = false;
    public static boolean sIsFromCollection = false;
    public static boolean sIsShowingLongwatch = false;
    public static long sStartPlay;
    private AlbumPlayFragment mAlbumPlayFragment;
    private ImageView mBackgroundView;
    private BarrageAlbumProtocol mBarrageProtocol;
    private AlbumBlockController mBlockController;
    public FrameLayout mBottomFrame;
    private AlbumCacheController mCacheController;
    private AlbumCollectController mCollectController;
    private AlbumController mController;
    private FloatBallConfig mFloatBallConfig;
    public FrameLayout mFloatFrame;
    private AlbumPlayFlowObservable mFlowObservable = new AlbumPlayFlowObservable();
    private AlbumGestureController mGestureController;
    private AlbumGestureObservable mGestureObservable = new AlbumGestureObservable();
    private AlbumHalfController mHalfController;
    private AlbumHalfFragment mHalfFragment;
    private int mHalfScreenHeight;
    private int mHalfVideoHeight;
    private Handler mHandler = new Handler();
    public String mHaptUrl;
    private boolean mHasApplyPermissionsBefore;
    public boolean mIs4dVideo;
    public boolean mIsDolbyVideo;
    private boolean mIsFirstOnResume = true;
    private boolean mIsForceFull;
    public boolean mIsLandspace;
    public boolean mIsLebox;
    public boolean mIsPanoramaVideo;
    public boolean mIsPlayingDlna;
    public boolean mIsPlayingNonCopyright;
    public boolean mIsVR;
    public int mLaunchMode;
    private AlbumLoadController mLoadController;
    private LongWatchController mLongWatchController;
    private AlbumMidAdController mMidAdTipController;
    private AlbumNetChangeController mNetChangeController;
    public String mNonCopyrightUrl;
    private AlbumPlayAdController mPlayAdController;
    private AlbumPlayFlow mPlayAlbumFlow;
    private AlbumPlayNextController mPlayNextController;
    private PlayObservable mPlayObservable;
    public AlbumPlayingHandler mPlayingHandler;
    private AlbumRecommendController mRecommendController;
    private AlbumRedPacketController mRedPacketController;
    private ScreenObservable mScreenObservable = new ScreenObservable();
    private ScreenProjectionController mScreenProjectionController;
    private AlbumShareController mShareController;
    private ShareWindowProtocol mShareWindowProtocol;
    public boolean mShouldWaitDrmPluginInstall;
    private Subscription mSubscription;
    private AlbumTaskRegister mTask;
    private AlbumErrorTopController mTopController;
    private AlbumVideoController mVideoController;
    private AlbumPlayGeneralTrailController mVipTrailController;
    private WebView mWebView;
    private AlbumPlayContainView rootView;

    protected void onCreate(Bundle savedInstanceState) {
        this.mNeedApplyPermissions = true;
        this.mHasApplyPermissionsBefore = hasApplyPermissions();
        super.onCreate(savedInstanceState);
    }

    protected void _setContentView() {
    }

    protected void onApplyPermissionsSuccess() {
        super.onApplyPermissionsSuccess();
        if (this.mHasApplyPermissionsBefore) {
            LogInfo.log("zhuqiao_time", "****************点击图片到oncreat:" + (System.currentTimeMillis() - StatisticsUtils.mClickImageForPlayTime) + "毫秒****************");
            sStartPlay = System.currentTimeMillis();
            LogInfo.log("zhuqiao_time", "****************setContentView:" + (System.currentTimeMillis() - sStartPlay) + "毫秒****************");
            setContentView(R.layout.activity_play_album);
            StatisticsUtils.sLoginRef = MainActivity.THIRD_PARTY_LETV;
            sStartPlay = System.currentTimeMillis();
            initMParams();
            initDataFromIntent(getIntent());
            init();
            initTask();
            LogInfo.log("zhuqiao_time", "****************init:" + (System.currentTimeMillis() - sStartPlay) + "毫秒****************");
            sStartPlay = System.currentTimeMillis();
            initObservables();
            LogInfo.log("zhuqiao_time", "****************initObservables:" + (System.currentTimeMillis() - sStartPlay) + "毫秒****************");
            startFlow(getIntent(), false);
            LogInfo.log("zhuqiao_time", "****************点击图片到启动流程消耗:" + (System.currentTimeMillis() - StatisticsUtils.mClickImageForPlayTime) + "毫秒****************");
            this.mRedPacketController.initRedPacketView();
        } else if (getIntent() == null) {
            finish();
        } else {
            Uri uri = getIntent().getData();
            Bundle bundle = getIntent().getExtras();
            finish();
            try {
                Intent tempIntent = new Intent(BaseApplication.getInstance(), AlbumPlayActivity.class);
                if (uri != null) {
                    tempIntent.setData(uri);
                }
                if (bundle != null) {
                    tempIntent.putExtras(bundle);
                }
                tempIntent.addFlags(268435456);
                BaseApplication.getInstance().startActivity(tempIntent);
            } catch (Exception e) {
            }
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            boolean shouldLaunchNewActivity;
            if (!(!this.mIsPlayingDlna || this.mVideoController == null || this.mVideoController.mDlnaProtocol == null)) {
                this.mVideoController.mDlnaProtocol.protocolStop(true, false);
            }
            if (PreferencesManager.getInstance().getCopyright() == 0) {
                shouldLaunchNewActivity = false;
            } else if (this.mIsPlayingNonCopyright) {
                boolean nonCopyright = intent.getBooleanExtra(PlayConstant.NO_COPYRIGHT, false);
                String url = intent.getStringExtra(PlayConstant.NO_COPYRIGHT_URL);
                if (nonCopyright && TextUtils.equals(url, "-1")) {
                    shouldLaunchNewActivity = false;
                } else {
                    shouldLaunchNewActivity = true;
                }
            } else {
                shouldLaunchNewActivity = intent.getBooleanExtra(PlayConstant.NO_COPYRIGHT, false);
            }
            if (shouldLaunchNewActivity) {
                finish();
                Intent tempIntent = new Intent(BaseApplication.getInstance(), AlbumPlayActivity.class);
                tempIntent.putExtras(intent.getExtras());
                tempIntent.addFlags(268435456);
                BaseApplication.getInstance().startActivity(tempIntent);
                return;
            }
            System.out.println("onNewIntent");
            this.mIsFirstOnResume = true;
            initDataFromIntent(intent);
            initWithIntent(intent);
            initWindow();
            if (this.mPlayAlbumFlow != null) {
                this.mPlayAlbumFlow.format(false);
            }
            if (this.mAlbumPlayFragment != null) {
                this.mAlbumPlayFragment.destoryCde();
            }
            AlbumBaseControllerFragment.sSwitchToStream1080p = false;
            startFlow(intent, true);
            this.mRedPacketController.initRedPacketView();
        }
    }

    protected void onStart() {
        boolean hasFinishAd = true;
        super.onStart();
        if (hasApplyPermissions()) {
            this.mHalfFragment.onStart();
            UIsUtils.enableScreenAlwaysOn(this);
            boolean isLaunchFromSelf = TextUtils.equals(MainActivity.THIRD_PARTY_LETV, StatisticsUtils.sLoginRef);
            if (this.mPlayAlbumFlow != null && mHomeKeyEventReceiver != null && mHomeKeyEventReceiver.isHomeClicked() && isLaunchFromSelf) {
                this.mPlayAlbumFlow.mPlayInfo.mReplayType = 3;
                this.mPlayAlbumFlow.mPlayInfo.mIsAfterHomeClicked = true;
                if (this.mPlayAdController == null || !this.mPlayAdController.getAdFragment().isFinishAd()) {
                    hasFinishAd = false;
                }
                this.mPlayAlbumFlow.statisticsLaunch(hasFinishAd);
                this.mPlayAlbumFlow.updatePlayDataStatistics("init", -1);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        if (hasApplyPermissions()) {
            if (this.mPlayAlbumFlow != null) {
                this.mPlayAlbumFlow.addPlayInfo("进入播放页", "");
            }
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    AlbumPlayActivity.this.mHalfFragment.onResume();
                    if (!AlbumPlayActivity.this.mIsFirstOnResume) {
                        if (AlbumPlayActivity.this.mHalfFragment.mOpenCommentDetail) {
                            AlbumPlayActivity.this.mHalfFragment.mOpenCommentDetail = false;
                        } else {
                            AlbumPlayActivity.this.mHalfFragment.refreshView(false, false);
                        }
                        AlbumPlayActivity.this.mAlbumPlayFragment.onResume();
                    }
                    AlbumPlayActivity.this.mAlbumPlayFragment.callAdsPlayInterface(8);
                    AlbumPlayActivity.this.mGestureController.onResume();
                    AlbumPlayActivity.this.mController.openSensor();
                    AlbumPlayActivity.this.mIsFirstOnResume = false;
                    if (!(AlbumPlayActivity.this.mHalfFragment == null || AlbumPlayActivity.this.mHalfFragment.getCommentController() == null)) {
                        AlbumPlayActivity.this.mHalfFragment.getCommentController().updateUserHeadPhoto();
                    }
                    AlbumPlayActivity.this.mRedPacketController.onResume();
                }
            }, this.mIsFirstOnResume ? 100 : 300);
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.mHasApplyPermissionsBefore) {
            this.mHalfFragment.onPause();
            if (this.mFloatBallConfig != null) {
                this.mFloatBallConfig.onPause();
            }
            this.mPlayAdController.setADPause(true);
            this.mAlbumPlayFragment.onPause();
            this.mAlbumPlayFragment.callAdsPlayInterface(7);
            this.mPlayAdController.clearAdFullProcessTimeout();
        }
    }

    protected void onStop() {
        super.onStop();
        this.mController.leaveAlbumPlayActivity();
        BaseApplication.getInstance().mIsAlbumActivityAlive = false;
        if (this.mHasApplyPermissionsBefore) {
            this.mHalfFragment.onStop();
            UIsUtils.disableScreenAlwaysOn(this);
            this.mPlayAdController.setADPause(true);
            this.mController.onStop();
            this.mController.closeSensor();
            if (this.mPlayAlbumFlow == null || this.mPlayingHandler == null || mHomeKeyEventReceiver == null || !mHomeKeyEventReceiver.isHomeClicked()) {
                this.mAlbumPlayFragment.onStop(false);
            } else {
                AlbumPlayInfo albumPlayInfo = this.mPlayAlbumFlow.mPlayInfo;
                albumPlayInfo.mGlsbNum++;
                if (!(this.mPlayAdController.getAdFragment() == null || this.mPlayAlbumFlow.mIsCombineAd || !this.mPlayAlbumFlow.mPlayInfo.mIsPlayingAds)) {
                    this.mPlayAlbumFlow.mPlayInfo.mAdsPlayFirstFrameTime = this.mPlayAdController.getAdFragment().getAdsPlayFirstFrameTime();
                    this.mPlayAlbumFlow.mPlayInfo.mAdsRequestTime = this.mPlayAdController.getAdFragment().getAdsLoadingTime();
                    LogInfo.log("jc666", "home 退出时广告首次播放时间:" + this.mPlayAlbumFlow.mPlayInfo.mAdsPlayFirstFrameTime);
                    LogInfo.log("jc666", "home 退出时本次广告请求所耗时间:" + this.mPlayAlbumFlow.mPlayInfo.mAdsRequestTime);
                }
                this.mAlbumPlayFragment.onStop(true);
            }
            if (this.mPlayAlbumFlow != null) {
                this.mPlayAlbumFlow.addPlayInfo("离开播放页", "");
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.dispatchTouchEvent(ev);
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            LogInfo.log("dispatchTouchEvent", "Caught unhandled dispatchTouchEvent exception");
            return true;
        }
    }

    public int getHalfScreenHeight() {
        return this.mHalfScreenHeight;
    }

    public int getHalfVideoHeight() {
        return this.mHalfVideoHeight;
    }

    private void initMParams() {
        StatisticsUtils.sMStartType = "";
        AlbumLaunchUtils.initMParams(this, getIntent());
    }

    private void initDataFromIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            boolean z;
            if (intent.getBooleanExtra(PlayConstant.NO_COPYRIGHT, false) && PreferencesManager.getInstance().getCopyright() == 1) {
                z = true;
            } else {
                z = false;
            }
            this.mIsPlayingNonCopyright = z;
            this.mNonCopyrightUrl = intent.getStringExtra(PlayConstant.NO_COPYRIGHT_URL);
            this.mHaptUrl = intent.getStringExtra(PlayConstant.HAPT_URL);
            this.mLaunchMode = intent.getIntExtra("launchMode", 0);
            if (TextUtils.isEmpty(getIntent().getStringExtra(PlayConstant.HAPT_URL))) {
                z = false;
            } else {
                z = true;
            }
            this.mIs4dVideo = z;
            if (getIntent().getSerializableExtra(PlayConstant.VIDEO_TYPE) == VideoType.Panorama) {
                z = true;
            } else {
                z = false;
            }
            this.mIsPanoramaVideo = z;
            if (getIntent().getSerializableExtra(PlayConstant.VIDEO_TYPE) == VideoType.Dolby) {
                z = true;
            } else {
                z = false;
            }
            this.mIsDolbyVideo = z;
            this.mIsForceFull = getIntent().getBooleanExtra(PlayConstant.FORCE_FULL, false);
            if (this.mLaunchMode == 7) {
                z = true;
            } else {
                z = false;
            }
            this.mIsLebox = z;
            int from = intent.getIntExtra("from", 0);
            String ref = intent.getStringExtra(PlayConstant.REF);
            if (!TextUtils.isEmpty(ref)) {
                StatisticsUtils.sLoginRef = ref;
                StatisticsUtils.sFrom = ref;
            }
            if (from != 18) {
                BaseApplication.getInstance().setLiveLunboBundle(null);
            }
            if (this.mLaunchMode == 0) {
                String path = null;
                if (!(intent == null || intent.getData() == null)) {
                    Uri uriPath = intent.getData();
                    String scheme = uriPath.getScheme();
                    path = (scheme == null || scheme.equals(IDataSource.SCHEME_FILE_TAG)) ? uriPath.getPath() : uriPath.toString();
                }
                this.mLaunchMode = 1;
                intent.putExtra("launchMode", 1);
                intent.putExtra(PlayConstant.URI, path);
                intent.putExtra("seek", 0);
                intent.putExtra(PlayConstant.PLAY_MODE, 1);
            }
        }
    }

    private void init() {
        int i = 8;
        this.mTask = new AlbumTaskRegister(this);
        this.mBackgroundView = (ImageView) getViewById(R.id.album_loading_bg);
        this.mBackgroundView.setTag(R.id.scale_type, ScaleType.CENTER_CROP);
        this.mBottomFrame = (FrameLayout) getViewById(R.id.play_album_bottom_frame);
        this.mFloatFrame = (FrameLayout) getViewById(R.id.layout_album_float_frame);
        this.mAlbumPlayFragment = new AlbumPlayFragment(this);
        this.mVideoController = new AlbumVideoController(this, getViewById(R.id.player_full_controller_contain));
        this.mHalfFragment = new AlbumHalfFragment(this);
        this.mHalfFragment.onCreate(null);
        LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(801));
        this.mPlayObservable = new PlayObservable(this);
        this.mGestureController = new AlbumGestureController(this, this.mAlbumPlayFragment, this.mGestureObservable);
        this.mPlayAdController = new AlbumPlayAdController(this);
        this.mVipTrailController = new AlbumPlayGeneralTrailController(this);
        this.mPlayNextController = new AlbumPlayNextController(this, PlayerType.MAIN);
        this.mRedPacketController = new AlbumRedPacketController(this);
        this.mBlockController = new AlbumBlockController();
        this.mController = new AlbumController(this);
        this.mTopController = new AlbumErrorTopController(this);
        this.mNetChangeController = new AlbumNetChangeController(this);
        this.mRecommendController = new AlbumRecommendController(this);
        this.mLongWatchController = new LongWatchController(this);
        this.mLoadController = new AlbumLoadController(this, this.mBottomFrame);
        this.mMidAdTipController = new AlbumMidAdController(this);
        this.rootView = (AlbumPlayContainView) getViewById(R.id.play_album_parent_view);
        this.rootView.setLongWatchController(this.mLongWatchController);
        getSupportFragmentManager().beginTransaction().add(R.id.play_album_ad_contain, this.mPlayAdController.getAdFragment()).commitAllowingStateLoss();
        getViewById(R.id.play_album_half_frame).setVisibility(this.mIsPlayingNonCopyright ? 8 : 0);
        View viewById = getViewById(R.id.play_album_toolbar);
        if (!this.mIsPlayingNonCopyright) {
            i = 0;
        }
        viewById.setVisibility(i);
        if (this.mIsPlayingNonCopyright) {
            initWebView();
        }
        checkDrmPlugin(false);
    }

    public void checkDrmPlugin(boolean forceCheck) {
        boolean z = true;
        if (!forceCheck && (!LetvUtils.isInHongKong() || (ApkManager.getInstance().getPluginInstallState(Constant.DRM_LIBWASABIJNI) == 1 && BaseApplication.getInstance().mHasLoadDrmSo))) {
            z = false;
        }
        this.mShouldWaitDrmPluginInstall = z;
        if (this.mShouldWaitDrmPluginInstall && NetworkUtils.isNetworkAvailable()) {
            this.mLoadController.loading(R.string.plugin_drm_downloading);
            SimplePluginDownloadService.launchSimplePluginDownloadService(this, UIsUtils.isLandscape(this) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage);
        }
    }

    private void initWebView() {
        if (this.mWebView == null) {
            this.mWebView = new WebView(this);
            this.mWebView.setBackgroundColor(-1);
            this.rootView.addView(this.mWebView, new LayoutParams(-1, -1));
            this.mWebView.getSettings().setUseWideViewPort(true);
            this.mWebView.getSettings().setSupportZoom(true);
            this.mWebView.getSettings().setBuiltInZoomControls(true);
            this.mWebView.setVerticalScrollBarEnabled(true);
            this.mWebView.setHorizontalScrollBarEnabled(true);
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.getSettings().setDomStorageEnabled(true);
            this.mWebView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    AlbumPlayActivity.this.mWebView.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            this.mWebView.setClickable(false);
        }
    }

    private void initTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(116, new TaskRunnable() {
            public LeResponseMessage run(LeMessage message) {
                if (message == null || !(message.getContext() instanceof AlbumPlayActivity)) {
                    return null;
                }
                return new LeResponseMessage(116, Boolean.valueOf(AlbumPlayActivity.this.mIsPlayingNonCopyright));
            }
        }));
    }

    private void initObservables() {
        addObserverToScreenObservable();
        addObserverToFlowObservable();
        addObserverToPlayObservable();
        addObserverToGestureObservable();
        initWithIntent(getIntent());
        initWindow();
        registerEventBus();
    }

    private void registerEventBus() {
        if (this.mSubscription == null || this.mSubscription.isUnsubscribed()) {
            this.mSubscription = RxBus.getInstance().toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
                public void call(Object o) {
                    if (o instanceof PluginInstalled) {
                        if (((PluginInstalled) o).success) {
                            AlbumPlayActivity.this.mShouldWaitDrmPluginInstall = false;
                            if (AlbumPlayActivity.this.mPlayAlbumFlow != null) {
                                AlbumPlayActivity.this.mPlayAlbumFlow.mShouldWaitDrmPluginInstall = false;
                                AlbumPlayActivity.this.mPlayAlbumFlow.start();
                                return;
                            }
                            return;
                        }
                        AlbumPlayActivity.this.mLoadController.requestError(AlbumPlayActivity.this.getString(R.string.plugin_drm_download_error), AddToCartResultStatus.NO_NETWORK, AlbumPlayActivity.this.getString(R.string.plugin_drm_retry_download));
                        StatisticsUtils.statisticsActionInfo(AlbumPlayActivity.this.mContext, UIsUtils.isLandscape(AlbumPlayActivity.this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, "19", "drm03", null, 1, null);
                    } else if ((o instanceof Integer) && ((Integer) o).intValue() == 114) {
                        AlbumPlayActivity.this.finish();
                    }
                }
            });
        }
    }

    private void unRegisterEventBus() {
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
        if (this.mTask != null) {
            this.mTask.onDestory();
        }
        LeMessageManager.getInstance().unRegister(104);
        LeMessageManager.getInstance().unRegister(106);
        LeMessageManager.getInstance().unRegister(321, 326);
        LeMessageManager.getInstance().unRegister(3);
        LeMessageManager.getInstance().unRegister(116);
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(901));
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(113));
    }

    private void initWithIntent(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            this.mPlayAdController.initState();
            this.mGestureController.setIsPanorama(this.mIsPanoramaVideo);
            this.mGestureController.setIsVr(this.mIsVR);
            this.mGestureController.setGestureUseful(!this.mIsVR);
            this.mVideoController.initFullState();
            this.mController.openSensor();
            if (isForceFull() && !this.mIsPlayingNonCopyright) {
                this.mController.fullLock();
            } else if (this.mIsPlayingNonCopyright && TextUtils.equals(this.mNonCopyrightUrl, "-1")) {
                this.mController.fullLock();
            }
            if (this.mIsDolbyVideo || this.mIsPanoramaVideo) {
                this.mTopController.setVisibilityForSwitchView(8);
            }
            initFloatBall();
        }
    }

    private void initFloatBall() {
        if (TipUtils.getTipTitle(DialogMsgConstantId.CONSTANT_80003, "0").equals("1")) {
            if (this.mFloatBallConfig == null) {
                LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_FLOAT_BALL_INIT, this));
                if (LeResponseMessage.checkResponseMessageValidity(response, FloatBallConfig.class)) {
                    this.mFloatBallConfig = (FloatBallConfig) response.getData();
                }
            }
            if (this.mFloatBallConfig == null) {
                return;
            }
            if (!NetworkUtils.isNetworkAvailable() || this.mIsLandspace) {
                this.mFloatBallConfig.hideFloat();
            } else {
                this.mFloatBallConfig.showFloat("9");
            }
        }
    }

    private RedPacketFrom getRedPacketCurrentType() {
        LogInfo.log("RedPacket", "RedPacket+getRedPacketCurrentType");
        RedPacketFrom redPackFrom = new RedPacketFrom();
        redPackFrom.from = 3;
        if (!(this.mPlayAlbumFlow == null || this.mPlayAlbumFlow.mCurrentPlayingVideo == null)) {
            redPackFrom.pid = this.mPlayAlbumFlow.mCurrentPlayingVideo.pid + "";
            redPackFrom.cid = this.mPlayAlbumFlow.mCurrentPlayingVideo.cid + "";
            redPackFrom.zid = this.mPlayAlbumFlow.mCurrentPlayingVideo.zid;
            LogInfo.log("RedPacket", "RedPacket+albumPlayActivity: pid=" + redPackFrom.pid + ";cid=" + redPackFrom.cid + ";zid=" + redPackFrom.zid);
        }
        redPackFrom.cid = "2";
        return redPackFrom;
    }

    private void addObserverToScreenObservable() {
        this.mScreenObservable.addObserver(this.mAlbumPlayFragment);
        this.mScreenObservable.addObserver(this.mVideoController);
        this.mScreenObservable.addObserver(this.mRecommendController);
        this.mScreenObservable.addObserver(this.mTopController);
        this.mScreenObservable.addObserver(this.mHalfFragment);
        this.mScreenObservable.addObserver(this.mPlayNextController);
    }

    private void addObserverToFlowObservable() {
        this.mFlowObservable.addObserver(this.mAlbumPlayFragment);
        this.mFlowObservable.addObserver(this.mVideoController);
        this.mFlowObservable.addObserver(this.mGestureController);
        this.mFlowObservable.addObserver(this.mNetChangeController);
        this.mFlowObservable.addObserver(this.mTopController);
        this.mFlowObservable.addObserver(this.mHalfFragment);
    }

    private void addObserverToPlayObservable() {
        this.mPlayObservable.addObserver(this.mAlbumPlayFragment);
        this.mPlayObservable.addObserver(this.mVideoController);
        this.mPlayObservable.addObserver(this.mHalfFragment);
        this.mPlayObservable.addObserver(this.mGestureController);
        this.mPlayObservable.addObserver(this.mNetChangeController);
        this.mPlayObservable.addObserver(this.mTopController);
        this.mPlayObservable.addObserver(this.mPlayNextController);
    }

    private void addObserverToGestureObservable() {
        this.mGestureObservable.addObserver(this.mAlbumPlayFragment);
        this.mGestureObservable.addObserver(this.mVideoController);
        this.mGestureObservable.addObserver(this.mGestureController);
    }

    private void initWindow() {
        this.mIsLandspace = UIsUtils.isLandscape(this);
        if (!(getHalfFragment() == null || getHalfFragment().getExpendFragment() == null)) {
            getHalfFragment().getExpendFragment().setIsFull(this.mIsLandspace);
        }
        if (this.mIsLandspace) {
            InputMethodManager manager = (InputMethodManager) getSystemService("input_method");
            if (!(getCurrentFocus() == null || getCurrentFocus().getWindowToken() == null)) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
            }
            UIsUtils.zoomViewFull(this.mBottomFrame);
            UIsUtils.zoomViewFull(this.mFloatFrame);
        } else {
            UIsUtils.zoomView(320, 180, this.mBottomFrame);
            UIsUtils.zoomView(320, 180, this.mFloatFrame);
            this.mHalfVideoHeight = this.mBottomFrame.getLayoutParams().height;
            this.mHalfScreenHeight = (UIsUtils.getScreenHeightWithoutStatusBar(this) - this.mHalfVideoHeight) - UIsUtils.dipToPx(40.0f);
        }
        this.mScreenObservable.notifyObservers(ScreenObservable.ON_CONFIG_CHANGE);
        if (this.mIsLandspace) {
            UIsUtils.fullScreen(this);
        } else {
            UIsUtils.cancelFullScreen(this);
        }
        if (this.mFloatBallConfig != null) {
            if (this.mIsLandspace) {
                this.mFloatBallConfig.hideFloat(true);
            } else {
                this.mFloatBallConfig.showFloat();
            }
        }
        this.mRedPacketController.changeRedPacketLocation(this.mIsLandspace);
        LeMessageManager.getInstance().sendMessageByRx(104);
    }

    public void startFlow(Intent intent, boolean onNewIntent) {
        if (intent != null && intent.getExtras() != null) {
            this.mPlayAlbumFlow = AlbumFlowUtils.getPlayFlow(this, this.mLaunchMode, intent.getExtras());
            if (this.mPlayAlbumFlow != null) {
                if (!onNewIntent) {
                    this.mPlayAlbumFlow.mPlayInfo.type14 = System.currentTimeMillis() - StatisticsUtils.mClickImageForPlayTime;
                }
                this.mPlayAlbumFlow.mIsNonCopyright = this.mIsPlayingNonCopyright;
                this.mPlayAlbumFlow.mShouldWaitDrmPluginInstall = this.mShouldWaitDrmPluginInstall;
                this.mPlayObservable.addObserver(this.mPlayAlbumFlow);
                this.mPlayAlbumFlow.setObservable(this.mFlowObservable);
                this.mPlayAlbumFlow.setLoadListener(this.mLoadController);
                this.mPlayAlbumFlow.setAdListener(this.mPlayAdController);
                this.mPlayAlbumFlow.setVideoListener(this.mAlbumPlayFragment);
                this.mPlayAlbumFlow.setAlbumVipTrailListener(this.mVipTrailController);
                if (!this.mIsPlayingNonCopyright || TextUtils.equals(this.mNonCopyrightUrl, "-1")) {
                    if (intent.getBooleanExtra(PlayConstant.IS_PAY, false) && PreferencesManager.getInstance().isLogin()) {
                        this.mController.syncUserInfo();
                    } else if (!this.mController.checkPanoramaTip() && !this.mShouldWaitDrmPluginInstall) {
                        this.mPlayAlbumFlow.start();
                    } else if (this.mShouldWaitDrmPluginInstall && !NetworkUtils.isNetworkAvailable()) {
                        this.mPlayAlbumFlow.showNoNet();
                    }
                    LogInfo.log("zhaosumin", "AlbumPlayAcitivity pid == " + this.mPlayAlbumFlow.mAid + " vid = " + this.mPlayAlbumFlow.mVid);
                    downloadBackground();
                } else if (!LetvUtils.isGooglePlay()) {
                    this.mWebView.setVisibility(0);
                    if (!TextUtils.isEmpty(this.mNonCopyrightUrl)) {
                        String str;
                        if (this.mNonCopyrightUrl.startsWith(IDataSource.SCHEME_HTTP_TAG)) {
                            str = this.mNonCopyrightUrl;
                        } else {
                            str = "http://" + this.mNonCopyrightUrl;
                        }
                        this.mNonCopyrightUrl = str;
                        this.mWebView.loadUrl(this.mNonCopyrightUrl);
                    }
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            AlbumPlayActivity.this.mWebView.setVisibility(8);
                            AlbumPlayActivity.this.mController.fullLock();
                            if (!AlbumPlayActivity.this.mShouldWaitDrmPluginInstall) {
                                AlbumPlayActivity.this.mPlayAlbumFlow.start();
                            } else if (!NetworkUtils.isNetworkAvailable()) {
                                AlbumPlayActivity.this.mPlayAlbumFlow.showNoNet();
                            }
                        }
                    }, 1000);
                }
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogInfo.log("zhuqiao", "***********onConfigurationChanged");
        initWindow();
    }

    public AlbumPlayFragment getAlbumPlayFragment() {
        return this.mAlbumPlayFragment;
    }

    public AlbumVideoController getVideoController() {
        return this.mVideoController;
    }

    public AlbumPlayFlow getFlow() {
        return this.mPlayAlbumFlow;
    }

    public PlayObservable getPlayObservable() {
        return this.mPlayObservable;
    }

    public AlbumGestureObservable getGestureObservable() {
        return this.mGestureObservable;
    }

    public AlbumLoadController getLoadListener() {
        return this.mLoadController;
    }

    public AlbumPlayGeneralTrailController getVipTrailListener() {
        return this.mVipTrailController;
    }

    public AlbumGestureController getGestureController() {
        return this.mGestureController;
    }

    public AlbumController getController() {
        if (this.mController == null) {
            this.mController = new AlbumController(this);
        }
        return this.mController;
    }

    public AlbumNetChangeController getNetChangeController() {
        return this.mNetChangeController;
    }

    public AlbumErrorTopController getErrorTopController() {
        return this.mTopController;
    }

    public AlbumRecommendController getRecommendController() {
        return this.mRecommendController;
    }

    public LongWatchController getLongWatchController() {
        return this.mLongWatchController;
    }

    public FloatBallConfig getFloatBall() {
        return this.mFloatBallConfig;
    }

    public ShareWindowProtocol getShareWindowProtocol() {
        if (this.mShareWindowProtocol == null) {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(109));
            if (LeResponseMessage.checkResponseMessageValidity(response, ShareWindowProtocol.class)) {
                this.mShareWindowProtocol = (ShareWindowProtocol) response.getData();
            }
        }
        return this.mShareWindowProtocol;
    }

    public BarrageAlbumProtocol getBarrageProtocol() {
        if (this.mBarrageProtocol == null) {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(300));
            if (LeResponseMessage.checkResponseMessageValidity(response, BarrageAlbumProtocol.class)) {
                this.mBarrageProtocol = (BarrageAlbumProtocol) response.getData();
            }
        }
        return this.mBarrageProtocol;
    }

    public ScreenProjectionController getScreenProjectionController() {
        return this.mScreenProjectionController;
    }

    public void setScreenProjectionController(ScreenProjectionController controller) {
        this.mScreenProjectionController = controller;
    }

    public AlbumCollectController getCollectController() {
        if (this.mCollectController == null) {
            this.mCollectController = new AlbumCollectController(this);
        }
        return this.mCollectController;
    }

    public AlbumCacheController getCacheController() {
        if (this.mCacheController == null) {
            this.mCacheController = new AlbumCacheController(this);
        }
        return this.mCacheController;
    }

    public AlbumShareController getShareController() {
        if (this.mShareController == null) {
            this.mShareController = new AlbumShareController(this);
        }
        return this.mShareController;
    }

    public AlbumPlayNextController getPlayNextController() {
        return this.mPlayNextController;
    }

    public AlbumRedPacketController getRedPacketController() {
        return this.mRedPacketController;
    }

    public AlbumHalfController getHalfController() {
        if (this.mHalfController == null) {
            this.mHalfController = new AlbumHalfController(this);
        }
        return this.mHalfController;
    }

    public AlbumMidAdController getMidAdController() {
        return this.mMidAdTipController;
    }

    public AlbumBlockController getBlockController() {
        return this.mBlockController;
    }

    public AlbumHalfFragment getHalfFragment() {
        return this.mHalfFragment;
    }

    public AlbumPlayAdController getPlayAdListener() {
        return this.mPlayAdController;
    }

    private void clearObservables() {
        if (this.mScreenObservable != null) {
            this.mScreenObservable.deleteObservers();
        }
        if (this.mFlowObservable != null) {
            this.mFlowObservable.deleteObservers();
        }
        if (this.mPlayObservable != null) {
            this.mPlayObservable.deleteObservers();
        }
        if (this.mGestureObservable != null) {
            this.mGestureObservable.deleteObservers();
        }
    }

    public View getRootView() {
        return this.rootView;
    }

    public boolean isForceFull() {
        return this.mLaunchMode == 3 || this.mIs4dVideo || this.mLaunchMode == 1 || this.mIsPlayingNonCopyright || this.mIsPanoramaVideo || this.mIsDolbyVideo || this.mIsForceFull || this.mIsLebox;
    }

    public void coverBlackOnVideoView(boolean cover) {
        int i = 0;
        if (this.mIsPlayingDlna) {
            this.mBackgroundView.setVisibility(8);
            findViewById(R.id.album_loading_cover_bg).setVisibility(8);
            return;
        }
        int i2;
        ImageView imageView = this.mBackgroundView;
        if (cover) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        View findViewById = findViewById(R.id.album_loading_cover_bg);
        if (!cover) {
            i = 8;
        }
        findViewById.setVisibility(i);
    }

    public void downloadBackground() {
        ImageDownloader.getInstance().download(this.mBackgroundView, PreferencesManager.getInstance().getLoadingBackground(this.mPlayAlbumFlow.mAid + "", this.mPlayAlbumFlow.mVid + ""));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0) {
            boolean flag = super.onKeyDown(keyCode, event);
            this.mController.onKeyDown(keyCode, event);
            return flag;
        } else if (this.mVideoController == null) {
            finish();
            return true;
        } else if (this.mVideoController.clickBack()) {
            return true;
        } else {
            if (isForceFull()) {
                this.mController.back();
                return true;
            } else if (UIsUtils.isLandscape(this)) {
                this.mController.half();
                return true;
            } else {
                this.mController.back();
                return true;
            }
        }
    }

    public void finish() {
        super.finish();
        LogInfo.log("zhuqiao", "finish 清除请求");
        if (this.mVideoController != null) {
            this.mVideoController.onFinish();
        }
        Volley.getQueue().cancelAll(new RequestFilter() {
            public boolean apply(VolleyRequest<?> request) {
                return (request == null || TextUtils.isEmpty(request.getTag()) || (!request.getTag().startsWith(AlbumPlayFlow.ALBUM_FLOW_TAG) && !request.getTag().startsWith("half_tag_"))) ? false : true;
            }
        });
        unRegisterEventBus();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mHasApplyPermissionsBefore) {
            this.mHalfFragment.onDestroy();
            LongWatchNoticeDialog.onDestory();
            format();
            AlbumVideoController.sSwitchToStream1080p = false;
            AlbumVideoController.mIsSwitch = false;
            AlbumPlayBaseFlow.sRef = "";
            sIsBlockPause = false;
            sIsFromCollection = false;
            FileUtils.clearPicsAfterChangeVideo(getApplicationContext());
            if (VERSION.SDK_INT > 8) {
                IRVideo.getInstance().videoEnd(this.mContext);
            }
            StatisticsUtils.sFrom = "";
            StatisticsUtils.sPlayFromCard = false;
            StatisticsUtils.setActionProperty(com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, -1, com.letv.pp.utils.NetworkUtils.DELIMITER_LINE);
        }
    }

    private void format() {
        if (this.mFloatBallConfig != null) {
            this.mFloatBallConfig.onDestory();
            this.mFloatBallConfig = null;
        }
        if (this.mLongWatchController != null) {
            this.mLongWatchController.onDestory();
        }
        if (this.mBlockController != null) {
            this.mBlockController.destory();
        }
        if (this.mShareWindowProtocol != null) {
            this.mShareWindowProtocol.hideShareDialog();
        }
        this.mShareWindowProtocol = null;
        this.mAlbumPlayFragment.onDestroy();
        this.mAlbumPlayFragment.callAdsPlayInterface(9);
        this.mVideoController.onDestroy();
        this.mGestureController.onDestroy();
        this.mController.closeSensor();
        clearObservables();
        sIsShowingLongwatch = false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogInfo.log("ZSM == AlbumPlayActivity onActivityResult");
        if (requestCode == 10 && resultCode == 1 && data != null) {
            new LetvWebViewActivityConfig(this).launch(data.getStringExtra(LetvLoginActivityConfig.AWARDURL), false, false, 28);
        }
        if (this.mController != null) {
            this.mController.onActivityResult(requestCode, resultCode, data);
        }
        if (this.mVideoController != null) {
            this.mVideoController.onShareActivityResult(requestCode, resultCode, data);
        }
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.ALBUM_FRAGMENT_TAG_ARRAY;
    }

    public String getActivityName() {
        return AlbumPlayActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public void sendCommet(Bundle bundle) {
        this.mHalfFragment.getCommentController().addComment(bundle);
    }
}
