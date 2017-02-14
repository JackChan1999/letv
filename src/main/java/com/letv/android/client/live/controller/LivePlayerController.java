package com.letv.android.client.live.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.barrage.widget.DanmakuSettingLinearLayout;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.messagemodel.ShareFloatProtocol;
import com.letv.android.client.commonlib.view.ScrollTextView;
import com.letv.android.client.controller.LiveBarrageController;
import com.letv.android.client.live.flow.LivePlayerFlow.GetPlayInfoEvent;
import com.letv.android.client.live.utils.LiveUtils;
import com.letv.android.client.live.view.ProgramsListFloatView;
import com.letv.android.client.live.view.ProgramsListFloatView.ProgramListType;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.android.client.utils.WatchAndBuyUtils;
import com.letv.android.client.view.ChannelFloatView;
import com.letv.android.client.view.MoreFloatView;
import com.letv.android.client.view.MultiProgramFloatView;
import com.letv.android.client.view.WatchAndBuyCartListView;
import com.letv.android.client.view.WatchAndBuyView;
import com.letv.business.flow.live.LiveWatchAndBuyFlow.AddToCartResultStatus;
import com.letv.core.BaseApplication;
import com.letv.core.bean.CurrentProgram;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveStreamBean;
import com.letv.core.bean.LiveStreamBean.StreamType;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.bean.WatchAndBuyGetNumResultBean;
import com.letv.core.bean.WatchAndBuyGoodsListBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.ChannelListHandler;
import com.letv.core.db.DBManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NavigationBarController;
import com.letv.core.utils.NavigationBarController.SystemUIListener;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class LivePlayerController extends RelativeLayout implements OnClickListener {
    public static final String TAG = "LivePlayerController";
    private boolean canShowPlayBtn;
    private boolean is1080pClick;
    private boolean is4KClick;
    private boolean isFullScreen;
    private boolean isLive;
    private boolean isShowing;
    private ImageView mBack;
    private TextView mBarrageButton;
    private int mBarrageContainId;
    private ImageView mBarrageInputBtn;
    private LiveRemenBaseBean mBaseBean;
    private View mBottomBar;
    private TextView mBtnLevel2K;
    private TextView mBtnLevel4K;
    private TextView mBtnLevelHigh;
    private TextView mBtnLevelSmooth;
    private TextView mBtnLevelStandard;
    private TextView mBtnLevelSuper;
    private TextView mBtnMultiProgram;
    private boolean mCanWatchAndBuy;
    private ImageView mCartIv;
    private RelativeLayout mCartLayout;
    private Subscription mCartShowingSubscription;
    private CartTipAsyncTask mCartTask;
    private Button mCartTipBtn;
    private RelativeLayout mCartTipLayout;
    private TextView mCartTipTv;
    private TextView mCartTv;
    private LiveBeanLeChannel mChannel;
    private TextView mChannelBtn;
    private ChannelFloatView mChannelFloatView;
    private ChatController mChatController;
    private boolean mCollected;
    private Context mContext;
    private int mCurrentSelectLevelPos;
    private ImageView mForward;
    private FragmentManager mFragmentManager;
    private ImageView mFullScreen;
    private boolean mIsDownStream;
    private boolean mIsFirstPlay;
    private boolean mIsStreamChanging;
    private View mLevelBtnLayout;
    private boolean mLevelShowed;
    private RelativeLayout mLevelTip;
    private RelativeLayout mLevelTipBtnClose;
    private TextView mLevelTipText;
    private int mLevelValue;
    public LiveBarrageController mLiveBarrageController;
    private ImageView mLock;
    private TextView mMore;
    private MoreFloatView mMoreFloatView;
    private MultiProgramFloatView mMultiProgramView;
    public NavigationBarController mNavigationBarController;
    private ImageView mPlay;
    private int mPlayLevel;
    private int mPlayerState;
    private ProgramEntity mProgram;
    private TextView mProgramList;
    private ProgramsListFloatView mProgramsView;
    private TextView mQuality;
    private View mQualityLayout;
    private RxBus mRxBus;
    private ShareFloatProtocol mShareProtocol;
    private View mShareView;
    private boolean mShowTitle;
    private CompositeSubscription mSubscription;
    private ArrayList<Integer> mSupportTypes;
    private ImageView mTVSpreadImg;
    private View mTVSpreadLayout;
    private TextView mTVSpreadTxt;
    private ScrollTextView mTitle;
    private View mTopBar;
    private View mTopRightLayout;
    private String mTvSpread1080ImgUrl;
    private String mTvSpread1080pText1;
    private String mTvSpread1080pText2;
    private String mTvSpread1080pUrl;
    private String mTvSpread4KImgUrl;
    private String mTvSpread4KText1;
    private String mTvSpread4KText2;
    private String mTvSpread4KUrl;
    ClickableSpan mTvSpreadSpan;
    private WatchAndBuyView mWacthAndBuyFloatView;
    private WatchAndBuyCartListView mWatchAndBuyCartListView;
    private SystemUIListener onSystemUIListener;
    private int pageIndex;
    private boolean qualityShowing;
    private boolean showDownloadTip;
    private Subscription showingSubscription;

    public static class FullScreenBtnClickEvent {
        public boolean isFull;

        public FullScreenBtnClickEvent(boolean isFull) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.isFull = isFull;
        }
    }

    public static class PlayBtnClickEvent {
        public PlayBtnClickEvent() {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
        }
    }

    public static class SysConfigChangeEvent {
        public SysConfigChangeEvent() {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
        }
    }

    public LivePlayerController(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mCurrentSelectLevelPos = 0;
        this.is1080pClick = false;
        this.is4KClick = false;
        this.mTvSpread1080pUrl = "http://shop.letv.com/product/product-pid-DSuN7g1Ey.html";
        this.mTvSpread1080ImgUrl = "";
        this.mTvSpread4KUrl = "http://shop.letv.com/huodong/0422.html";
        this.mTvSpread4KImgUrl = "";
        this.mLevelShowed = false;
        this.mIsDownStream = false;
        this.qualityShowing = false;
        this.isFullScreen = false;
        this.canShowPlayBtn = true;
        this.isShowing = false;
        this.mCollected = false;
        this.mCanWatchAndBuy = false;
        this.mShowTitle = true;
        this.mIsFirstPlay = true;
        this.mIsStreamChanging = false;
        this.showDownloadTip = false;
        this.onSystemUIListener = new 1(this);
        this.mTvSpreadSpan = new 7(this);
        init(context);
    }

    public LivePlayerController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCurrentSelectLevelPos = 0;
        this.is1080pClick = false;
        this.is4KClick = false;
        this.mTvSpread1080pUrl = "http://shop.letv.com/product/product-pid-DSuN7g1Ey.html";
        this.mTvSpread1080ImgUrl = "";
        this.mTvSpread4KUrl = "http://shop.letv.com/huodong/0422.html";
        this.mTvSpread4KImgUrl = "";
        this.mLevelShowed = false;
        this.mIsDownStream = false;
        this.qualityShowing = false;
        this.isFullScreen = false;
        this.canShowPlayBtn = true;
        this.isShowing = false;
        this.mCollected = false;
        this.mCanWatchAndBuy = false;
        this.mShowTitle = true;
        this.mIsFirstPlay = true;
        this.mIsStreamChanging = false;
        this.showDownloadTip = false;
        this.onSystemUIListener = new 1(this);
        this.mTvSpreadSpan = new 7(this);
        init(context);
    }

    public LivePlayerController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentSelectLevelPos = 0;
        this.is1080pClick = false;
        this.is4KClick = false;
        this.mTvSpread1080pUrl = "http://shop.letv.com/product/product-pid-DSuN7g1Ey.html";
        this.mTvSpread1080ImgUrl = "";
        this.mTvSpread4KUrl = "http://shop.letv.com/huodong/0422.html";
        this.mTvSpread4KImgUrl = "";
        this.mLevelShowed = false;
        this.mIsDownStream = false;
        this.qualityShowing = false;
        this.isFullScreen = false;
        this.canShowPlayBtn = true;
        this.isShowing = false;
        this.mCollected = false;
        this.mCanWatchAndBuy = false;
        this.mShowTitle = true;
        this.mIsFirstPlay = true;
        this.mIsStreamChanging = false;
        this.showDownloadTip = false;
        this.onSystemUIListener = new 1(this);
        this.mTvSpreadSpan = new 7(this);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mNavigationBarController = new NavigationBarController(this.mContext);
        this.mRxBus = RxBus.getInstance();
    }

    public void init(int pageIndex, FragmentManager fragmentManager, int barrageContainId) {
        this.pageIndex = pageIndex;
        this.mFragmentManager = fragmentManager;
        this.mBarrageContainId = barrageContainId;
        removeAllViews();
        inflate(this.mContext, R.layout.live_full_play_controller, this);
        initView();
        initTvUrl();
        initClick();
        initFloatView();
    }

    private void reset() {
        this.pageIndex = -1;
        this.qualityShowing = false;
        this.isFullScreen = false;
        this.canShowPlayBtn = true;
        this.isShowing = false;
        this.mCollected = false;
        this.mCanWatchAndBuy = false;
        this.mIsFirstPlay = true;
    }

    public void onResume() {
        registerRxBus();
        fullScreen(this.isFullScreen);
        if (this.mChatController != null && this.mBaseBean != null) {
            this.mChatController.chatConnect(null, this.mBaseBean.chatRoomNum);
        }
    }

    public void onPause() {
        unRegisterRxBus();
        if (this.mChatController != null) {
            this.mChatController.close();
        }
        if (this.mWatchAndBuyCartListView != null && this.mWatchAndBuyCartListView.getVisibility() == 0) {
            this.mWatchAndBuyCartListView.hide();
        }
        if (this.mWacthAndBuyFloatView != null && this.mWacthAndBuyFloatView.isShowing()) {
            this.mWacthAndBuyFloatView.hide();
        }
        this.mPlayerState = 4;
    }

    public void onDestroy() {
        if (this.mWacthAndBuyFloatView != null) {
            this.mWacthAndBuyFloatView.destroy();
            WatchAndBuyView watchAndBuyView = this.mWacthAndBuyFloatView;
            WatchAndBuyView.sAddToCartGoods = null;
        }
        if (this.mShareProtocol != null) {
            this.mShareProtocol.onDestory();
        }
        if (!(this.mLiveBarrageController == null || this.mLiveBarrageController.getBarrageControl() == null)) {
            this.mLiveBarrageController.getBarrageControl().destroyBarrage();
            this.mLiveBarrageController = null;
        }
        reset();
    }

    private void initView() {
        this.mTopBar = findViewById(2131363831);
        this.mForward = (ImageView) findViewById(R.id.play_view_crl_arrow);
        this.mTopRightLayout = findViewById(R.id.top_layout);
        this.mMore = (TextView) findViewById(R.id.play_more);
        this.mBarrageButton = (TextView) findViewById(R.id.play_barrage);
        this.mBarrageInputBtn = (ImageView) findViewById(2131362705);
        this.mTitle = (ScrollTextView) findViewById(2131363836);
        this.mBack = (ImageView) findViewById(2131362471);
        this.mLock = (ImageView) findViewById(R.id.play_lock);
        this.mChannelBtn = (TextView) findViewById(R.id.play_select_channel);
        this.mCartLayout = (RelativeLayout) findViewById(R.id.live_full_controller_cart);
        this.mCartTv = (TextView) findViewById(R.id.live_full_controller_cart_tv);
        this.mCartIv = (ImageView) findViewById(R.id.live_full_controller_cart_icon);
        this.mCartTipLayout = (RelativeLayout) findViewById(R.id.watchandbuy_cart_tip_layout);
        this.mCartTipTv = (TextView) findViewById(R.id.watchandbuy_cart_tip_tv);
        this.mCartTipBtn = (Button) findViewById(R.id.watchandbuy_cart_tip_btn);
        this.mBottomBar = findViewById(2131363710);
        this.mQuality = (TextView) findViewById(R.id.play_level);
        this.mProgramList = (TextView) findViewById(R.id.play_program_list);
        this.mPlay = (ImageView) findViewById(2131363118);
        this.mFullScreen = (ImageView) findViewById(R.id.play_half);
        this.mQualityLayout = findViewById(R.id.full_sharpness_layout);
        this.mLevelBtnLayout = findViewById(R.id.full_loworhigh);
        this.mBtnLevel4K = (TextView) findViewById(R.id.live_full_4k_text);
        this.mBtnLevel2K = (TextView) findViewById(2131363147);
        this.mBtnLevelHigh = (TextView) findViewById(2131363151);
        this.mBtnLevelStandard = (TextView) findViewById(2131363152);
        this.mBtnLevelSmooth = (TextView) findViewById(R.id.full_smooth_text);
        this.mBtnLevelSuper = (TextView) findViewById(R.id.full_super_text);
        this.mTVSpreadLayout = findViewById(R.id.tv_spread);
        this.mTVSpreadImg = (ImageView) findViewById(R.id.tv_spread_imagev);
        this.mTVSpreadTxt = (TextView) findViewById(R.id.tv_spread_textv);
        this.mLevelTip = (RelativeLayout) findViewById(R.id.level_tip_layout);
        this.mLevelTipText = (TextView) findViewById(R.id.level_tip_text);
        this.mLevelTipBtnClose = (RelativeLayout) findViewById(R.id.level_close_container);
        this.mBtnMultiProgram = (TextView) findViewById(R.id.multi_program_btn);
        if (LetvUtils.isInHongKong()) {
            this.mBtnLevel4K.setVisibility(8);
            this.mBtnLevel2K.setVisibility(8);
        }
        findViewById(R.id.live_full_root).setFitsSystemWindows(false);
    }

    private void initTvUrl() {
        TipBean tvSpread1080UrlByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100022);
        if (!(tvSpread1080UrlByMsg == null || TextUtils.isEmpty(tvSpread1080UrlByMsg.message))) {
            this.mTvSpread1080pUrl = tvSpread1080UrlByMsg.message;
        }
        TipBean tvSpread1080ImgUrlByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100026);
        if (!(tvSpread1080ImgUrlByMsg == null || TextUtils.isEmpty(tvSpread1080ImgUrlByMsg.message))) {
            this.mTvSpread1080ImgUrl = tvSpread1080ImgUrlByMsg.message;
        }
        TipBean tvSpread4KUrlByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100023);
        if (!(tvSpread4KUrlByMsg == null || TextUtils.isEmpty(tvSpread4KUrlByMsg.message))) {
            this.mTvSpread4KUrl = tvSpread4KUrlByMsg.message;
        }
        TipBean tvSpread4KImgUrlByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100027);
        if (!(tvSpread4KImgUrlByMsg == null || TextUtils.isEmpty(tvSpread4KImgUrlByMsg.message))) {
            this.mTvSpread4KImgUrl = tvSpread4KImgUrlByMsg.message;
        }
        TipBean tvSpread1080TxByMsg1 = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100093);
        if (tvSpread1080TxByMsg1 == null || TextUtils.isEmpty(tvSpread1080TxByMsg1.message)) {
            this.mTvSpread1080pText1 = this.mContext.getString(2131100908);
        } else {
            this.mTvSpread1080pText1 = tvSpread1080TxByMsg1.message;
        }
        TipBean tvSpread1080TxByMsg2 = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100095);
        if (tvSpread1080TxByMsg2 == null || TextUtils.isEmpty(tvSpread1080TxByMsg2.message)) {
            this.mTvSpread1080pText2 = this.mContext.getString(2131100910);
        } else {
            this.mTvSpread1080pText2 = tvSpread1080TxByMsg2.message;
        }
        TipBean tvSpread4KTxByMsg1 = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100094);
        if (tvSpread4KTxByMsg1 == null || TextUtils.isEmpty(tvSpread4KTxByMsg1.message)) {
            this.mTvSpread4KText1 = this.mContext.getString(2131100909);
        } else {
            this.mTvSpread4KText1 = tvSpread4KTxByMsg1.message;
        }
        TipBean tvSpread4KTxByMsg2 = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100096);
        if (tvSpread4KTxByMsg2 == null || TextUtils.isEmpty(tvSpread4KTxByMsg2.message)) {
            this.mTvSpread4KText2 = this.mContext.getString(2131100910);
        } else {
            this.mTvSpread4KText2 = tvSpread4KTxByMsg2.message;
        }
    }

    private void initClick() {
        this.mBack.setOnClickListener(this);
        this.mTitle.setOnClickListener(this);
        this.mForward.setOnClickListener(this);
        this.mMore.setOnClickListener(this);
        this.mLock.setOnClickListener(this);
        this.mChannelBtn.setOnClickListener(this);
        this.mPlay.setOnClickListener(this);
        this.mFullScreen.setOnClickListener(this);
        this.mQuality.setOnClickListener(this);
        this.mProgramList.setOnClickListener(this);
        this.mTVSpreadTxt.setOnClickListener(this);
        this.mBtnLevelSmooth.setOnClickListener(this);
        this.mBtnLevelStandard.setOnClickListener(this);
        this.mBtnLevelHigh.setOnClickListener(this);
        this.mBtnLevelSuper.setOnClickListener(this);
        this.mBtnLevel2K.setOnClickListener(this);
        this.mBtnLevel4K.setOnClickListener(this);
        this.mCartIv.setOnClickListener(this);
        this.mCartTipBtn.setOnClickListener(this);
        this.mBtnMultiProgram.setOnClickListener(this);
    }

    private void initFloatView() {
        initShareFloatView();
        initMoreFloatView();
    }

    public void initBarrage() {
        if (this.mLiveBarrageController == null) {
            this.mLiveBarrageController = new LiveBarrageController(this.mContext, new 2(this), this.mFragmentManager, this.mBarrageContainId, 3);
        }
        if (this.mBaseBean.isDanmaku == 1) {
            this.mMoreFloatView.findViewById(R.id.danmaku_setting_linearlayout).setVisibility(0);
            ((DanmakuSettingLinearLayout) this.mMoreFloatView.findViewById(R.id.danmaku_setting_linearlayout)).attach(this.mLiveBarrageController.getBarrageControl());
        } else {
            this.mMoreFloatView.findViewById(R.id.danmaku_setting_linearlayout).setVisibility(8);
        }
        this.mLiveBarrageController.setBarrageButton(this.mBarrageButton, this.mBarrageInputBtn);
        this.mBarrageButton.setOnClickListener(new 3(this));
        this.mBarrageInputBtn.setOnClickListener(new 4(this));
        this.mBarrageButton.setVisibility(this.mBaseBean.isDanmaku == 1 ? 0 : 8);
        if (this.mChatController != null) {
            this.mChatController.close();
        }
        if (this.mBaseBean.isDanmaku == 1) {
            this.mChatController = new ChatController(this.mContext, new 5(this));
            this.mChatController.loadChatRecords(this.mBaseBean.chatRoomNum, null);
            LogInfo.log("zhuqiao", "initBarrage");
        }
    }

    public void canShowTitle(boolean show) {
        this.mShowTitle = show;
    }

    private void fullScreen(boolean isFull) {
        this.isFullScreen = isFull;
        if (!(this.showingSubscription == null || this.showingSubscription.isUnsubscribed())) {
            this.showingSubscription.unsubscribe();
        }
        if (isFull) {
            this.mBack.setVisibility(0);
            this.mTopRightLayout.setVisibility(0);
            this.mTitle.setVisibility(0);
            this.mForward.setVisibility(8);
            this.mQuality.setVisibility(0);
            this.mFullScreen.setImageResource(2130838715);
            if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
                this.mProgramList.setVisibility(0);
                this.mChannelBtn.setVisibility(0);
            } else if (!(LiveLunboUtils.isLunBoWeiShiType(this.pageIndex) || getLiveInfo() == null || getLiveInfo().branches == null || getLiveInfo().branchType <= 0 || BaseTypeUtils.isListEmpty(getLiveInfo().branches))) {
                this.mBtnMultiProgram.setVisibility(0);
            }
            if (this.mCanWatchAndBuy && this.mPlayerState == 3 && this.mWacthAndBuyFloatView != null) {
                this.mWacthAndBuyFloatView.show();
            } else if (this.mWacthAndBuyFloatView != null) {
                this.mWacthAndBuyFloatView.hide();
            }
            if (this.mLiveBarrageController == null || !this.mLiveBarrageController.getBarrageControl().isOpenBarrage()) {
                this.mBarrageInputBtn.setVisibility(4);
            } else {
                this.mBarrageInputBtn.setVisibility(0);
            }
            if (BaseApplication.getInstance().hasNavigationBar()) {
                this.mNavigationBarController.fireLandscapeSystemUIListener("rx_bus_live_home_action_update_system_ui", this.onSystemUIListener);
                setPadding(0, 0, BaseApplication.getInstance().getNavigationBarLandscapeWidth(), 0);
                return;
            }
            return;
        }
        if (BaseApplication.getInstance().hasNavigationBar()) {
            this.mNavigationBarController.resetAndRemoveListener();
            setPadding(0, 0, 0, 0);
        }
        if (this.mShowTitle) {
            this.mForward.setVisibility(0);
        } else {
            this.mTitle.setVisibility(8);
            this.mForward.setVisibility(8);
        }
        this.mBack.setVisibility(8);
        this.mTopRightLayout.setVisibility(8);
        this.mBarrageInputBtn.setVisibility(4);
        this.mChannelBtn.setVisibility(8);
        this.mQuality.setVisibility(8);
        this.mProgramList.setVisibility(8);
        this.mBtnMultiProgram.setVisibility(8);
        this.mFullScreen.setImageResource(2130838126);
        if (isFloatViewVisible()) {
            hideFloatView();
        }
        if (this.mWacthAndBuyFloatView != null) {
            this.mWacthAndBuyFloatView.hide();
        }
        if (this.mWatchAndBuyCartListView != null) {
            this.mWatchAndBuyCartListView.hide();
        }
        this.mCartLayout.setVisibility(8);
    }

    private boolean isFloatViewVisible() {
        return (this.mProgramsView != null && this.mProgramsView.isVisible()) || ((this.mMultiProgramView != null && this.mMultiProgramView.isVisible()) || ((this.mShareView != null && this.mShareView.getVisibility() == 0) || ((this.mMoreFloatView != null && this.mMoreFloatView.isVisible()) || (this.mChannelFloatView != null && this.mChannelFloatView.isVisible()))));
    }

    private void showAndHide(boolean show) {
        this.isShowing = show;
        if (this.isFullScreen) {
            fullScreenClickShowAndHide(show);
        } else {
            halfScreenClickShowAndHide(show);
        }
        if (show) {
            this.showingSubscription = Observable.timer(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new 6(this));
        } else if (this.showingSubscription != null) {
            LogInfo.log(RxBus.TAG, "取消监听消失的通知");
            this.showingSubscription.unsubscribe();
        }
    }

    private void fullScreenClickShowAndHide(boolean show) {
        LogInfo.log(RxBus.TAG, "全屏控制栏显示：" + show);
        if (show) {
            this.mTopBar.setVisibility(0);
            this.mBottomBar.setVisibility(0);
            if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
                this.mChannelBtn.setVisibility(0);
            }
            if (this.mLiveBarrageController == null || !this.mLiveBarrageController.getBarrageControl().isOpenBarrage()) {
                this.mBarrageInputBtn.setVisibility(4);
            } else {
                this.mBarrageInputBtn.setVisibility(0);
            }
            if (this.mCanWatchAndBuy) {
                this.mCartLayout.setVisibility(0);
                if (this.mCartShowingSubscription != null) {
                    LogInfo.log(RxBus.TAG, "取消监听购物车按钮消失的通知");
                    this.mCartShowingSubscription.unsubscribe();
                }
            }
            if (!(LiveLunboUtils.isLunBoWeiShiType(this.pageIndex) || this.mBaseBean == null || this.mBaseBean.branchType <= 0 || this.mBaseBean.isBranch != 1 || BaseTypeUtils.isListEmpty(this.mBaseBean.branches))) {
                this.mBtnMultiProgram.setVisibility(0);
            }
        } else {
            this.mTopBar.setVisibility(8);
            this.mBottomBar.setVisibility(8);
            if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
                this.mChannelBtn.setVisibility(8);
            }
            this.mBarrageInputBtn.setVisibility(4);
            hideFloatView();
            setLevelTipVisible(false);
            if (this.mCartLayout.getVisibility() == 0 && !this.mWacthAndBuyFloatView.isShowing() && this.mWatchAndBuyCartListView.getVisibility() != 0 && ((this.mCartTask == null || this.mCartTask.getStatus() != Status.RUNNING) && this.mWatchAndBuyCartListView.getVisibility() != 0)) {
                this.mCartLayout.setVisibility(8);
            }
            this.mBtnMultiProgram.setVisibility(8);
        }
        RxBus.getInstance().send("rx_bus_live_home_action_update_system_ui");
    }

    private void halfScreenClickShowAndHide(boolean show) {
        LogInfo.log(RxBus.TAG, "半屏控制栏显示：" + show);
        if (show) {
            this.mTopBar.setVisibility(0);
            this.mBottomBar.setVisibility(0);
            if (this.canShowPlayBtn) {
                this.mPlay.setVisibility(0);
                this.mFullScreen.setVisibility(0);
                return;
            }
            this.mFullScreen.setVisibility(8);
            this.mPlay.setVisibility(8);
            return;
        }
        this.mTopBar.setVisibility(8);
        this.mBottomBar.setVisibility(8);
    }

    private void hideFloatView() {
        if (this.mQualityLayout.getVisibility() == 0) {
            qualityShow(false);
        }
        if (this.mMoreFloatView != null && this.mMoreFloatView.isVisible()) {
            this.mMoreFloatView.hide();
        }
        if (this.mMultiProgramView != null && this.mMultiProgramView.isVisible()) {
            this.mMultiProgramView.hide();
        }
        if (this.mChannelFloatView != null && this.mChannelFloatView.isVisible()) {
            this.mChannelFloatView.hide();
        }
        if (this.mProgramsView != null && this.mProgramsView.isVisible()) {
            this.mProgramsView.hide();
        }
        if (this.mShareProtocol != null && this.mShareProtocol.getView().getVisibility() == 0) {
            this.mShareProtocol.hide();
        }
    }

    private void qualityShow(boolean show) {
        this.qualityShowing = show;
        this.mTVSpreadLayout.setVisibility(4);
        if (show) {
            int[] locations = new int[2];
            this.mQuality.getLocationInWindow(locations);
            int x = locations[0];
            this.mBottomBar.getLocationOnScreen(locations);
            int y = locations[1];
            LayoutParams params = (LayoutParams) this.mQualityLayout.getLayoutParams();
            params.leftMargin = (((this.mQuality.getMeasuredWidth() >> 1) + x) - (this.mLevelBtnLayout.getMeasuredWidth() >> 1)) - this.mTVSpreadLayout.getMeasuredWidth();
            params.topMargin = (y - this.mQualityLayout.getMeasuredHeight()) - 10;
            this.mQualityLayout.setLayoutParams(params);
            this.mQualityLayout.requestLayout();
            this.mQualityLayout.setVisibility(0);
            this.mQuality.setBackgroundResource(2130837620);
            return;
        }
        this.mQualityLayout.setVisibility(8);
        this.mQuality.setBackgroundResource(2130837742);
        this.is4KClick = false;
        this.is1080pClick = false;
    }

    private void playLevelClick(int selectLevelPos) {
        if (this.mCurrentSelectLevelPos != selectLevelPos) {
            String str;
            hideShoppingAds();
            this.mLevelShowed = true;
            streamChanging();
            int wz = -1;
            switch (selectLevelPos) {
                case 0:
                    this.mQuality.setText(this.mBtnLevelSmooth.getText());
                    this.mPlayLevel = 1;
                    setBtnLevelStatus(0);
                    wz = 1;
                    break;
                case 1:
                    this.mQuality.setText(this.mBtnLevelStandard.getText());
                    this.mPlayLevel = 2;
                    setBtnLevelStatus(1);
                    wz = 2;
                    break;
                case 2:
                    this.mQuality.setText(this.mBtnLevelHigh.getText());
                    this.mPlayLevel = 3;
                    setBtnLevelStatus(2);
                    wz = 3;
                    break;
                case 3:
                    this.mQuality.setText(this.mBtnLevelSuper.getText());
                    this.mPlayLevel = 4;
                    setBtnLevelStatus(3);
                    wz = 4;
                    break;
            }
            this.mQualityLayout.setVisibility(8);
            this.mRxBus.send(new PlayLevelChangeEvent(this.mPlayLevel));
            qualityShow(false);
            LogInfo.LogStatistics("---playLevelClick---");
            Context context = this.mContext;
            if (UIsUtils.isLandscape()) {
                str = PageIdConstant.fullPlayPage;
            } else {
                str = PageIdConstant.halpPlayPage;
            }
            StatisticsUtils.statisticsActionInfo(context, str, "0", "a19", null, wz, null);
        }
    }

    private void streamChanging() {
        this.mIsStreamChanging = true;
        this.mLevelTipBtnClose.setVisibility(8);
        setLevelTipVisible(true);
        this.mLevelTipText.setText(TipUtils.getTipMessage(DialogMsgConstantId.SWITCH_STREAM, 2131100359));
    }

    private void streamChanged() {
        this.mIsStreamChanging = false;
        if (this.showDownloadTip) {
            showDownStreamTip();
        } else if (this.mLevelShowed) {
            setLevelTipVisible(true);
            this.mLevelTipBtnClose.setVisibility(8);
            int index = this.mPlayLevel - 1;
            if (index < 0) {
                index = 0;
            } else if (index >= 4) {
                index = 3;
            }
            this.mLevelTipText.setText(Html.fromHtml((this.mContext.getString(2131100360) + " ") + "<font color='#00a0e9'>" + getStreamLevelName()[index] + "</font>"));
            this.mLevelShowed = false;
        }
    }

    public void showDownStreamTip() {
        this.showDownloadTip = false;
        if (this.isFullScreen) {
            setLevelTipVisible(true);
            this.mLevelTipBtnClose.setVisibility(8);
            this.mLevelTipText.setText(TipUtils.getTipMessage(DialogMsgConstantId.OVERLOAD_CHANGE_STREAM, this.mContext.getString(2131101584)));
        }
    }

    public void showOverLoadStatus(boolean isOverLoad) {
        this.mIsDownStream = isOverLoad;
        if (isOverLoad) {
            this.mBtnLevelSmooth.setBackgroundResource(2130838836);
            this.mBtnLevelStandard.setBackgroundResource(2130838836);
            this.mBtnLevelHigh.setBackgroundResource(2130838836);
            this.mBtnLevelSuper.setBackgroundResource(2130838836);
            int minLevelBtnNum = 0;
            if (this.mBtnLevelSmooth.getVisibility() == 0) {
                minLevelBtnNum = 0;
                this.mQuality.setText(this.mBtnLevelSmooth.getText());
                this.mPlayLevel = 1;
            } else if (this.mBtnLevelStandard.getVisibility() == 0) {
                minLevelBtnNum = 1;
                this.mQuality.setText(this.mBtnLevelStandard.getText());
                this.mPlayLevel = 2;
            } else if (this.mBtnLevelHigh.getVisibility() == 0) {
                minLevelBtnNum = 2;
                this.mQuality.setText(this.mBtnLevelHigh.getText());
                this.mPlayLevel = 3;
            } else if (this.mBtnLevelSuper.getVisibility() == 0) {
                minLevelBtnNum = 3;
                this.mQuality.setText(this.mBtnLevelSuper.getText());
                this.mPlayLevel = 4;
            } else if (this.mBtnLevel2K.getVisibility() == 0) {
                minLevelBtnNum = 4;
                this.mQuality.setText(this.mBtnLevel2K.getText());
                this.mPlayLevel = 5;
            }
            setBtnLevelStatus(minLevelBtnNum);
            return;
        }
        this.mBtnLevelSmooth.setBackgroundResource(2130837774);
        this.mBtnLevelStandard.setBackgroundResource(2130837774);
        this.mBtnLevelHigh.setBackgroundResource(2130837774);
        this.mBtnLevelSuper.setBackgroundResource(2130837774);
    }

    private void setLevelTipVisible(boolean flag) {
        this.mLevelTip.setVisibility(flag ? 0 : 8);
    }

    private String[] getStreamLevelName() {
        String[] streamName = new String[4];
        try {
            streamName[0] = TipUtils.getTipMessage(DialogMsgConstantId.LIVE_STREAM_ST);
        } catch (Exception e) {
            streamName[0] = this.mContext.getString(2131100891);
        }
        try {
            streamName[1] = TipUtils.getTipMessage(DialogMsgConstantId.LIVE_STREAM_HD);
        } catch (Exception e2) {
            streamName[1] = this.mContext.getString(2131100892);
        }
        try {
            streamName[2] = TipUtils.getTipMessage(DialogMsgConstantId.LIVE_STREAM_HIGH);
        } catch (Exception e3) {
            streamName[2] = this.mContext.getString(2131100888);
        }
        try {
            streamName[3] = TipUtils.getTipMessage(DialogMsgConstantId.LIVE_STREAM_SUPERHD);
        } catch (Exception e4) {
            streamName[3] = this.mContext.getString(2131100890);
        }
        return streamName;
    }

    private void setBtnLevelStatus(int num) {
        boolean z;
        boolean z2 = true;
        this.mBtnLevelSmooth.setSelected(num == 0);
        TextView textView = this.mBtnLevelSmooth;
        if (num != 0) {
            z = true;
        } else {
            z = false;
        }
        textView.setEnabled(z);
        textView = this.mBtnLevelStandard;
        if (num == 1) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = this.mBtnLevelStandard;
        if (num != 1) {
            z = true;
        } else {
            z = false;
        }
        textView.setEnabled(z);
        textView = this.mBtnLevelHigh;
        if (num == 2) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = this.mBtnLevelHigh;
        if (num != 2) {
            z = true;
        } else {
            z = false;
        }
        textView.setEnabled(z);
        textView = this.mBtnLevelSuper;
        if (num == 3) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = this.mBtnLevelSuper;
        if (num != 3) {
            z = true;
        } else {
            z = false;
        }
        textView.setEnabled(z);
        textView = this.mBtnLevel2K;
        if (num == 4) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = this.mBtnLevel2K;
        if (num != 4) {
            z = true;
        } else {
            z = false;
        }
        textView.setEnabled(z);
        textView = this.mBtnLevel4K;
        if (num == 5) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        TextView textView2 = this.mBtnLevel4K;
        if (num == 5) {
            z2 = false;
        }
        textView2.setEnabled(z2);
        this.mCurrentSelectLevelPos = num;
    }

    private void playLevel4KClick() {
        int i = 0;
        this.is4KClick = !this.is4KClick;
        this.is1080pClick = false;
        View view = this.mTVSpreadLayout;
        if (!this.is4KClick) {
            i = 4;
        }
        view.setVisibility(i);
        this.mTVSpreadTxt.setText(StringUtils.generateTvSpreadSpanText(this.mTvSpread4KText1, this.mTvSpread4KText2, this.mTvSpreadSpan));
        if (!TextUtils.isEmpty(this.mTvSpread4KImgUrl)) {
            ImageDownloader.getInstance().download(this.mTVSpreadImg, this.mTvSpread4KImgUrl);
        }
        if (this.is4KClick) {
            LogInfo.LogStatistics("onclick--清晰度4k");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c675", "2001", 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
        }
    }

    private void playLevel1080PClick() {
        int i = 0;
        this.is1080pClick = !this.is1080pClick;
        this.is4KClick = false;
        View view = this.mTVSpreadLayout;
        if (!this.is1080pClick) {
            i = 4;
        }
        view.setVisibility(i);
        LogInfo.log("fornia", "play---liveroomplaycon playLevel1080PClick is1080pClick:" + this.is1080pClick);
        this.mTVSpreadTxt.setText(StringUtils.generateTvSpreadSpanText(this.mTvSpread1080pText1, this.mTvSpread1080pText2, this.mTvSpreadSpan));
        LogInfo.log("fornia", "play---liveroomplaycon playLevel1080PClick mTvSpread1080ImgUrl:" + this.mTvSpread1080ImgUrl);
        if (!TextUtils.isEmpty(this.mTvSpread1080ImgUrl)) {
            ImageDownloader.getInstance().download(this.mTVSpreadImg, this.mTvSpread1080ImgUrl);
        }
        if (this.is1080pClick) {
            LogInfo.log("fornia", "play---liveroomplaycon playLevel1080PClick 统计  mTvSpread1080ImgUrl:" + this.mTvSpread1080ImgUrl);
            LogInfo.log("glh", "onclick--清晰度1080p");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c675", "2002", 2, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
        }
    }

    private void clickToShopping() {
        LogInfo.log("fornia", "play---liveroomplaycon mTvSpreadSpan！！！！！！");
        String uri = "http://m.shop.letv.com/?cps_id=le_app_apprx_other_appbfy1080p_brand_h_msqgcjds0701";
        if (this.is4KClick) {
            uri = this.mTvSpread4KUrl;
            LogInfo.LogStatistics("4k--了解更多");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c6751", null, 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            LogInfo.log("fornia", "play---liveroomplaycon mTvSpreadSpan is4KClick uri:" + uri);
        } else if (this.is1080pClick) {
            uri = this.mTvSpread1080pUrl;
            LogInfo.LogStatistics("1080P--了解更多");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c6752", null, 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            LogInfo.log("fornia", "play---liveroomplaycon mTvSpreadSpan is1080pClick uri:" + uri);
        }
        LogInfo.log("fornia", "play---liveroomplaycon mTvSpreadSpan all uri:" + uri);
        hideShoppingAds();
        new LetvWebViewActivityConfig(this.mContext).launch(uri, this.mContext.getString(2131100239));
    }

    public void hideShoppingAds() {
        this.mTVSpreadLayout.setVisibility(4);
        this.is4KClick = false;
        this.is1080pClick = false;
    }

    private void collectClick() {
        int i;
        boolean z = true;
        ChannelListHandler channelListTrace = DBManager.getInstance().getChannelListTrace();
        String channelId = getChannelId();
        if (this.mCollected) {
            i = 0;
        } else {
            i = 1;
        }
        if (channelListTrace.updateToChannelList(channelId, i, LiveLunboUtils.getChannelDBType(this.pageIndex))) {
            String ok = TipUtils.getTipMessage("10062", 2131101006);
            String cancel = this.mContext.getString(2131101002);
            Context context = this.mContext;
            if (this.mCollected) {
                ok = cancel;
            }
            ToastUtils.showToast(context, ok);
            if (this.mCollected) {
                z = false;
            }
            this.mCollected = z;
            this.mMoreFloatView.updateCollectStatus(this.mCollected);
        } else {
            ToastUtils.showToast(this.mContext, !this.mCollected ? 2131101005 : 2131101003);
        }
        if (this.mCollected) {
            LogInfo.LogStatistics("直播--收藏");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "l08", null, 2, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
        }
    }

    private void initFavouriteStatus() {
        this.mCollected = DBManager.getInstance().getChannelListTrace().hasCollectChannel(getChannelId(), LiveLunboUtils.getChannelDBType(this.pageIndex));
    }

    private String getChannelId() {
        if (this.mBaseBean != null) {
            return this.mBaseBean.selectId;
        }
        if (this.mChannel != null) {
            return this.mChannel.channelId;
        }
        return "";
    }

    private LiveRemenBaseBean getLiveInfo() {
        return this.mBaseBean;
    }

    private void initShareFloatView() {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this.mContext, new LeMessage(LeMessageIds.MSG_SHARE_FLOAT_INIT));
        if (LeResponseMessage.checkResponseMessageValidity(response, ShareFloatProtocol.class)) {
            this.mShareProtocol = (ShareFloatProtocol) response.getData();
            LayoutParams lp = new LayoutParams(UIsUtils.dipToPx(283.0f), -1);
            lp.addRule(11);
            if (this.mShareView != null) {
                removeView(this.mShareView);
            }
            this.mShareView = this.mShareProtocol.getView();
            addView(this.mShareView, lp);
        }
        ShareUtils.RequestShareLink(this.mContext);
        this.mShareProtocol.initViewLive(new 8(this), null);
        this.mShareProtocol.setShareFrom(3);
    }

    private void initMoreFloatView() {
        this.mMoreFloatView = (MoreFloatView) findViewById(R.id.more_float_lt);
        this.mMoreFloatView.initLiveFullItems();
        this.mMoreFloatView.init(null, new 9(this));
        this.mMoreFloatView.removePush();
        initFavouriteStatus();
    }

    private void initMultiProgramView() {
        if (this.mMultiProgramView == null) {
            this.mMultiProgramView = (MultiProgramFloatView) findViewById(R.id.multiprogram_list);
            this.mMultiProgramView.init(new 10(this), null);
        }
    }

    private void setMultiProgramBtnTx() {
        if (this.mBaseBean != null) {
            String title = this.mContext.getString(2131100280);
            if (this.mBaseBean.branchType == 1) {
                title = this.mContext.getString(2131100280);
            } else if (this.mBaseBean.branchType == 2) {
                title = this.mContext.getString(2131100278);
            }
            this.mBtnMultiProgram.setText(title);
        }
    }

    private void initWatchAndBuyView() {
        if (this.mWacthAndBuyFloatView == null) {
            this.mWacthAndBuyFloatView = (WatchAndBuyView) findViewById(R.id.watchandbuy_float_view);
            this.mWacthAndBuyFloatView.init();
            this.mWatchAndBuyCartListView = (WatchAndBuyCartListView) findViewById(R.id.watchandbuy_cartlist_view);
            this.mWatchAndBuyCartListView.init();
            this.mWacthAndBuyFloatView.setCallback(new 11(this));
        }
    }

    private void initProgramsView() {
        if (this.mChannel != null && this.mProgram != null) {
            if (this.mProgramsView == null) {
                ProgramListType type;
                this.mProgramsView = (ProgramsListFloatView) findViewById(R.id.full_program_list_lt);
                if (LiveLunboUtils.isLunboIndex(this.pageIndex)) {
                    type = ProgramListType.LUNBO;
                } else {
                    type = ProgramListType.WEISHI;
                }
                this.mProgramsView.init(this.mChannel, type);
            }
            this.mProgramsView.setCurrentProgram(new CurrentProgram(this.mProgram.playTime, this.mProgram.id, this.mChannel.channelId));
        }
    }

    private void initChannelFloatView() {
        if (this.mChannelFloatView == null) {
            this.mChannelFloatView = (ChannelFloatView) findViewById(R.id.channelListFloatView);
            LayoutParams params = (LayoutParams) this.mChannelFloatView.getLayoutParams();
            params.width = UIsUtils.zoomWidth(180);
            this.mChannelFloatView.setLayoutParams(params);
            this.mChannelFloatView.setCallBackListener(new 12(this));
            this.mChannelFloatView.init(this.mContext, new 13(this), this.pageIndex, this.mFragmentManager);
        } else if (this.mChannel != null) {
            this.mChannelFloatView.changeCurrentChannel(this.pageIndex, this.mChannel.channelId);
        } else if (this.mBaseBean != null) {
            this.mChannelFloatView.changeCurrentChannel(this.pageIndex, this.mBaseBean.id);
        }
    }

    public void onClick(View v) {
        if (v == this.mBack) {
            this.mRxBus.send(new FullScreenBtnClickEvent(false));
        } else if (v == this.mTitle || v == this.mForward) {
            String pageId = StatisticsUtils.getLivePageId(this.pageIndex);
            StatisticsUtils.setActionProperty("l09", -1, pageId);
            StatisticsUtils.statisticsActionInfo(this.mContext, pageId, "0", "l09", null, v == this.mTitle ? 1 : 2, null);
            if (this.mChannel != null) {
                String channelName = this.mChannel.channelName;
                String channelEName = this.mChannel.channelEname;
                String channelId = this.mChannel.channelId;
                String num = this.mChannel.numericKeys;
                String signal = this.mChannel.signal;
                String programName = null;
                if (this.mProgram != null) {
                    programName = this.mProgram.title;
                }
                if (this.pageIndex == 2) {
                    LiveLaunchUtils.launchLiveWeishi(this.mContext, channelEName, false, programName, channelName, channelId, signal, false);
                } else if (LiveLunboUtils.isLunboIndex(this.pageIndex)) {
                    LiveLaunchUtils.launchLiveLunbo(this.mContext, LiveLunboUtils.getLaunchMode(this.pageIndex), channelEName, false, programName, channelName, channelId, num, false);
                } else {
                    LiveLaunchUtils.launchLiveLunbo(this.mContext, LetvUtils.isInHongKong() ? 116 : 101, channelEName, false, programName, channelName, channelId, num, false);
                }
            } else if (this.mBaseBean != null) {
                LiveLaunchUtils.launchFocusPicLive(this.mContext, 0, LiveUtils.getLiveChannelType(this.pageIndex), "", "", "1".equals(this.mBaseBean.isPay), this.mBaseBean.id, this.mBaseBean.allowVote, this.mBaseBean.partId);
            }
        } else if (v == this.mMore) {
            if (this.mMoreFloatView != null) {
                showAndHide(false);
                initFavouriteStatus();
                this.mMoreFloatView.updateCollectStatus(this.mCollected);
                this.mMoreFloatView.show();
            }
        } else if (v == this.mLock) {
        } else {
            if (v == this.mChannelBtn) {
                if (this.mChannelFloatView != null) {
                    showAndHide(false);
                    this.mChannelFloatView.show();
                }
            } else if (v == this.mPlay) {
                this.mRxBus.send(new PlayBtnClickEvent());
            } else if (v == this.mFullScreen) {
                this.mRxBus.send(new FullScreenBtnClickEvent(!this.isFullScreen));
            } else if (v == this.mQuality) {
                qualityShow(!this.qualityShowing);
            } else if (v == this.mProgramList) {
                if (this.mProgramsView != null) {
                    showAndHide(false);
                    this.mProgramsView.show();
                }
            } else if (v == this.mTVSpreadTxt) {
                clickToShopping();
            } else if (v == this.mBtnLevelSmooth) {
                playLevelClick(0);
            } else if (v == this.mBtnLevelStandard) {
                playLevelClick(1);
            } else if (v == this.mBtnLevelHigh) {
                playLevelClick(2);
            } else if (v == this.mBtnLevelSuper) {
                playLevelClick(3);
            } else if (v == this.mBtnLevel2K) {
                playLevel1080PClick();
            } else if (v == this.mBtnLevel4K) {
                playLevel4KClick();
            } else if (v == this.mCartIv) {
                if (this.mWatchAndBuyCartListView != null) {
                    this.mWatchAndBuyCartListView.show(this.mBaseBean.id);
                }
                if (!(this.mCartShowingSubscription == null || this.mCartShowingSubscription.isUnsubscribed())) {
                    this.mCartShowingSubscription.unsubscribe();
                }
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, "0", "c68", null, 12, null);
            } else if (v == this.mCartTipBtn) {
                this.mCartTipLayout.setVisibility(8);
                if (this.mCartTask != null) {
                    this.mCartTask.setCartTipVisible(false);
                }
            } else if (v == this.mBtnMultiProgram) {
                showAndHide(false);
                this.mMultiProgramView.show();
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, "0", "c67", null, 11, null);
            }
        }
    }

    private void registerRxBus() {
        LogInfo.log(RxBus.TAG, "LivePlayerController注册RxBus");
        if (this.mSubscription == null) {
            this.mSubscription = new CompositeSubscription();
        }
        if (!this.mSubscription.hasSubscriptions()) {
            LogInfo.log(RxBus.TAG, "LivePlayerController添加RxBus Event");
            this.mSubscription.add(this.mRxBus.toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new 14(this)));
        }
    }

    private void unRegisterRxBus() {
        LogInfo.log(RxBus.TAG, "LivePlayerController取消注册RxBus");
        if (this.mSubscription != null && this.mSubscription.hasSubscriptions()) {
            this.mSubscription.unsubscribe();
        }
        this.mSubscription = null;
    }

    private void handleVideoStateChangeEvent(int state) {
        this.mPlayerState = state;
        switch (state) {
            case -1:
                this.mPlay.setImageResource(2130837745);
                this.mPlay.setClickable(false);
                return;
            case 3:
                this.mPlay.setClickable(true);
                this.mPlay.setImageResource(2130837739);
                this.canShowPlayBtn = true;
                showAndHide(true);
                if (this.isFullScreen && this.mCanWatchAndBuy && this.mWacthAndBuyFloatView != null) {
                    this.mWacthAndBuyFloatView.show();
                }
                if (this.mIsStreamChanging) {
                    streamChanged();
                }
                if (this.mLiveBarrageController != null) {
                    if (this.mIsFirstPlay) {
                        this.mLiveBarrageController.checkBarrageOnOff();
                        if (UIsUtils.isLandscape()) {
                            this.mLiveBarrageController.doFullScreen();
                        } else {
                            this.mLiveBarrageController.doHalfScreen();
                        }
                    } else if (!(this.mLiveBarrageController == null || this.mLiveBarrageController.getBarrageControl() == null || this.mLiveBarrageController.getBarrageControl().getBarragePlayControl() == null || !this.mLiveBarrageController.getBarrageControl().isOpenBarrage())) {
                        this.mLiveBarrageController.getBarrageControl().getBarragePlayControl().resumeBarrage();
                    }
                }
                this.mIsFirstPlay = false;
                return;
            case 4:
                this.mPlay.setClickable(true);
                this.mPlay.setImageResource(2130837745);
                if (this.mLiveBarrageController != null && this.mLiveBarrageController.getBarrageControl() != null && this.mLiveBarrageController.getBarrageControl().getBarragePlayControl() != null && this.mLiveBarrageController.getBarrageControl().isOpenBarrage()) {
                    this.mLiveBarrageController.getBarrageControl().getBarragePlayControl().pauseBarrage();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void handlePlayInfoEvent(GetPlayInfoEvent event) {
        if (event.bean != null) {
            this.mBaseBean = event.bean;
            if (!(LiveLunboUtils.isLunBoWeiShiType(this.pageIndex) || this.mBaseBean == null || this.mBaseBean.branchType <= 0 || this.mBaseBean.isBranch != 1 || BaseTypeUtils.isListEmpty(this.mBaseBean.branches))) {
                initMultiProgramView();
                setMultiProgramBtnTx();
            }
            if (this.mBaseBean.buyFlag.equals("1") && !TextUtils.isEmpty(this.mBaseBean.partId)) {
                initWatchAndBuyView();
                this.mRxBus.send(new WatchAndBuyEnableEvent(true, this.mBaseBean.partId));
            }
            if (this.mMoreFloatView != null) {
                this.mMoreFloatView.removeCollect();
            }
            initBarrage();
        } else if (event.channel != null) {
            this.mChannel = event.channel;
            this.mProgram = event.program;
            if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
                initProgramsView();
                initChannelFloatView();
            }
        }
    }

    private void handleTitle() {
        if (this.mBaseBean != null) {
            this.mTitle.setText(this.mBaseBean.title);
        } else if (this.mChannel != null && this.mProgram != null) {
            StringBuilder sb = new StringBuilder();
            String channelName = this.mChannel.channelName;
            String channelNum = this.mChannel.numericKeys;
            LogInfo.log(TAG, channelNum + ": " + channelName);
            if (this.pageIndex == 2) {
                if (!TextUtils.isEmpty(channelName)) {
                    sb.append(channelName + " : ");
                }
            } else if (!(TextUtils.isEmpty(channelNum) || TextUtils.isEmpty(channelName))) {
                if (TextUtils.isEmpty(channelNum)) {
                    sb.append(channelName);
                } else if (channelNum.length() == 1) {
                    sb.append("0").append(channelNum).append("  ").append(channelName);
                } else {
                    sb.append(channelNum).append("  ").append(channelName);
                }
                if (!TextUtils.isEmpty(this.mProgram.title)) {
                    sb.append(" : ");
                }
            }
            sb.append(this.mProgram.title);
            this.mTitle.setText(sb.toString());
        }
    }

    public void setOnlyOneLevel(LiveStreamBean liveStreamBean) {
        String[] streamName = getStreamLevelName();
        String smoothName = streamName[0];
        String standardName = streamName[1];
        String hdName = streamName[2];
        String superHdName = streamName[3];
        this.mSupportTypes = liveStreamBean.getSupportStreamType();
        if (this.mSupportTypes.size() <= 0) {
            this.mBtnLevelHigh.setVisibility(8);
            this.mQuality.setText(smoothName);
            this.mPlayLevel = 1;
        } else {
            if (this.mSupportTypes.contains(Integer.valueOf(2)) && liveStreamBean.streamType == StreamType.STREAM_1000) {
                this.mQuality.setText(standardName);
                setBtnLevelStatus(1);
                this.mPlayLevel = 2;
            } else if (this.mSupportTypes.contains(Integer.valueOf(1)) && liveStreamBean.streamType == StreamType.STREAM_350) {
                this.mQuality.setText(smoothName);
                setBtnLevelStatus(0);
                this.mPlayLevel = 1;
            } else if (this.mSupportTypes.contains(Integer.valueOf(3)) && liveStreamBean.streamType == StreamType.STREAM_1300) {
                this.mQuality.setText(hdName);
                setBtnLevelStatus(2);
                this.mPlayLevel = 3;
            } else if (this.mSupportTypes.contains(Integer.valueOf(4)) && liveStreamBean.streamType == StreamType.STREAM_720p) {
                this.mQuality.setText(superHdName);
                setBtnLevelStatus(3);
                this.mPlayLevel = 4;
            }
            if (this.mSupportTypes.contains(Integer.valueOf(1))) {
                this.mBtnLevelSmooth.setVisibility(0);
            } else {
                this.mBtnLevelSmooth.setVisibility(8);
            }
            if (this.mSupportTypes.contains(Integer.valueOf(2))) {
                this.mBtnLevelStandard.setVisibility(0);
            } else {
                this.mBtnLevelStandard.setVisibility(8);
            }
            if (this.mSupportTypes.contains(Integer.valueOf(3))) {
                this.mBtnLevelHigh.setVisibility(0);
            } else {
                this.mBtnLevelHigh.setVisibility(8);
            }
            if (this.mSupportTypes.contains(Integer.valueOf(4))) {
                this.mBtnLevelSuper.setVisibility(0);
            } else {
                this.mBtnLevelSuper.setVisibility(8);
            }
        }
        this.mBottomBar.measure(0, 0);
        this.mTVSpreadLayout.measure(0, 0);
        this.mQualityLayout.measure(0, 0);
    }

    public void onGetGoodsResponse(String streamId, WatchAndBuyGoodsListBean result) {
        if (result != null && this.mWacthAndBuyFloatView != null) {
            String serverTime = result.time;
            String liveTime = this.mBaseBean.endTime;
            if (WatchAndBuyUtils.getLongFormatTime(serverTime) > WatchAndBuyUtils.getLongFormatTime(liveTime)) {
                this.mCanWatchAndBuy = false;
                this.mRxBus.send(new WatchAndBuyEnableEvent(false));
            } else if (WatchAndBuyUtils.getLongFormatTime(serverTime) >= WatchAndBuyUtils.getLongFormatTime(liveTime) || WatchAndBuyUtils.getLongFormatTime(serverTime) + 60 <= WatchAndBuyUtils.getLongFormatTime(liveTime)) {
                this.mCanWatchAndBuy = result.startTime;
                this.mRxBus.send(new WatchAndBuyEnableEvent(result.startTime));
                this.mWacthAndBuyFloatView.setGoodsListBean(result, this.mBaseBean.endTime);
                if (this.isFullScreen) {
                    if (this.mCanWatchAndBuy && this.mPlayerState == 3) {
                        this.mWacthAndBuyFloatView.show();
                    }
                    if (!this.mCanWatchAndBuy) {
                        this.mWacthAndBuyFloatView.hide();
                        if (!result.startTime) {
                            this.mWacthAndBuyFloatView.destroy();
                        }
                        showCartTip();
                    }
                }
            } else {
                this.mCanWatchAndBuy = false;
                this.mRxBus.send(new WatchAndBuyEnableEvent(false));
            }
        }
    }

    public void onAddToCartResponse(String status) {
        if (status.equals("300")) {
            ToastUtils.showToast(LetvTools.getTextFromServer("100087", this.mContext.getString(2131101148)));
        } else if (status.equals("302")) {
            ToastUtils.showToast(LetvTools.getTextFromServer("100088", this.mContext.getString(2131101150)));
        } else if (status.equals("-1")) {
            ToastUtils.showToast(LetvTools.getTextFromServer("100089", this.mContext.getString(2131101149)));
        } else if (status.equals(AddToCartResultStatus.NO_NETWORK)) {
            ToastUtils.showToast(LetvTools.getTextFromServer("100090", this.mContext.getString(2131100499)));
        } else if (!status.equals("1")) {
            ToastUtils.showToast(LetvTools.getTextFromServer("100089", this.mContext.getString(2131101149)));
        } else if (this.mWacthAndBuyFloatView != null && this.mCartIv != null) {
            this.mWacthAndBuyFloatView.onAddToCart(this.mCartIv);
        }
    }

    public void onGetCartNumResponse(WatchAndBuyGetNumResultBean result) {
        int count = result.count;
        if (count > 0) {
            if (count > 50) {
                this.mCartTv.setText("50+");
            } else {
                this.mCartTv.setText(String.valueOf(count));
            }
            this.mCartTv.setVisibility(0);
        } else {
            this.mCartTv.setText("0");
            this.mCartTv.setVisibility(8);
        }
        if (this.mWatchAndBuyCartListView != null) {
            this.mWatchAndBuyCartListView.setData(result.cartItems, this.mBaseBean.id);
        }
    }

    public void onGetAttentionCount(int count) {
        if (this.mWacthAndBuyFloatView != null) {
            this.mWacthAndBuyFloatView.setAttetion(count);
        }
    }

    private void showCartTip() {
        LogInfo.log("pjf", "showCartTip");
        if (this.isFullScreen) {
            this.mCartTipTv.setText(LetvTools.getTextFromServer("100091", this.mContext.getString(2131101153)));
            this.mCartTipLayout.setVisibility(0);
            this.mCartTask = new CartTipAsyncTask(this);
            this.mCartTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{""});
        }
    }

    private void afterAddToCart() {
        if (this.mCartTv.getVisibility() == 8) {
            this.mCartTv.setVisibility(0);
        }
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f);
        animation.setDuration(200);
        animation.setAnimationListener(new 15(this));
        animation.setFillAfter(true);
        this.mCartTv.startAnimation(animation);
    }

    private void autoHideCartLayout() {
        this.mCartShowingSubscription = Observable.timer(10, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new 16(this));
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mNavigationBarController.resetAndRemoveListener();
    }
}
