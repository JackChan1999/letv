package com.letv.android.client.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import cn.com.iresearch.vvtracker.IRVideo;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.BasePlayActivity;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.android.client.activity.LetvWoFlowActivity;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.BasePlayActivityConfig;
import com.letv.android.client.commonlib.messagemodel.DLNAProtocol;
import com.letv.android.client.commonlib.view.LongWatchNoticeDialog;
import com.letv.android.client.commonlib.view.PlayLoadLayout;
import com.letv.android.client.commonlib.view.PlayLoadLayout.PlayLoadLayoutCallBack;
import com.letv.android.client.controllerbars.LiveFullControllerBar;
import com.letv.android.client.controllerbars.LiveHalfControllerBar;
import com.letv.android.client.fragment.ChatFragmant;
import com.letv.android.client.listener.LiveControllerWidgetCallback;
import com.letv.android.client.listener.PlayActivityCallback;
import com.letv.android.client.utils.LetvLiveBookUtil;
import com.letv.android.client.view.LivePayLayout;
import com.letv.business.flow.live.LiveFlowCallback.CheckPayCallback;
import com.letv.business.flow.live.LiveFlowCallback.LivePlayCallback;
import com.letv.business.flow.live.LiveFragmentCallback;
import com.letv.business.flow.live.PlayLiveFlow;
import com.letv.business.flow.statistics.StatisticsInfo;
import com.letv.core.BaseApplication;
import com.letv.core.bean.BarrageBean;
import com.letv.core.bean.CurrentProgram;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LivePriceBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveStreamBean;
import com.letv.core.bean.LiveStreamBean.StreamType;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.ShareConstant;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.http.LetvHttpConstant;
import com.letv.pp.utils.NetworkUtils;
import com.letv.redpacketsdk.ui.RedPacketUI;
import java.util.Timer;
import java.util.TimerTask;
import org.cybergarage.upnp.Device;

public class PlayLiveController extends BasePlayController implements PlayLoadLayoutCallBack, LiveControllerWidgetCallback, LivePlayCallback, LiveFragmentCallback {
    private static WebViewCallBack mCallBack;
    private long LONG_WATCH_PERIOD;
    public final String SHARE_LIVE_ENTERTAIN_URL;
    public final String SHARE_LIVE_LUNBO_URL;
    public final String SHARE_LIVE_MUSIC_URL;
    public final String SHARE_LIVE_OTHER_URL;
    public final String SHARE_LIVE_SPORTS_URL;
    private int mAllowVote;
    private long mBlockStartTime;
    public boolean mCollected;
    public int mCurrentState;
    private DLNAProtocol mDlnaProtocol;
    private PlayLiveHalfTabContrller mHalfTabContrller;
    private boolean mIsFirstPlay;
    private boolean mIsFull;
    private LivePayLayout mLivePayLayout;
    private PlayLiveFlow mLiveflow;
    private PlayLoadLayout mLoadLayout;
    private boolean mPlayInterupted;
    private int mPlayLevel;
    private String mPlayOn3GText;
    private boolean mShouldBack;
    private TimerTask mTimerTask;
    private Timer mTimerWatch;

    public PlayLiveController(Context context, PlayActivityCallback playCallback) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, playCallback);
        this.mCollected = false;
        this.mTimerWatch = null;
        this.mTimerTask = null;
        this.LONG_WATCH_PERIOD = 10800000;
        this.mIsFirstPlay = true;
        this.mPlayLevel = -1;
        this.mBlockStartTime = 0;
        this.SHARE_LIVE_SPORTS_URL = "http://m.letv.com/live/list/channel/sports/";
        this.SHARE_LIVE_ENTERTAIN_URL = "http://m.letv.com/live/list/channel/ent/";
        this.SHARE_LIVE_MUSIC_URL = "http://m.letv.com/live/list/channel/music/";
        this.SHARE_LIVE_LUNBO_URL = "http://m.letv.com/live/play_%s.html";
        this.SHARE_LIVE_OTHER_URL = "http://m.letv.com/live/list/channel/zongyi/";
    }

    public void initData() {
        Intent intent = getActivity().getIntent();
        this.mShouldBack = intent.getBooleanExtra(PlayConstant.BACK, false);
        this.mAllowVote = intent.getIntExtra(PlayConstant.LIVE_ALLOW_VOTE, 0);
        this.mPlayOn3GText = TipUtils.getTipMessage("100006", 2131100656);
        TipBean mMessageBean = TipUtils.getTipBean(DialogMsgConstantId.LONGTIME_TIP);
        if (!(mMessageBean == null || TextUtils.isEmpty(mMessageBean.message))) {
            this.LONG_WATCH_PERIOD = (long) (((Float.parseFloat(mMessageBean.message) * 60.0f) * 60.0f) * 1000.0f);
        }
        this.mLiveflow = new PlayLiveFlow(getActivity(), getLaunchMode(), isOnlyFull(), this);
        if (!isOnlyFull()) {
            this.mHalfTabContrller = new PlayLiveHalfTabContrller(this.mContext, this, this);
        }
        this.mLiveBarrageSentCallback = new 1(this);
    }

    public void initViews() {
        this.mLoadLayout = new PlayLoadLayout(this.mContext);
        this.mLoadLayout.setCallBack(this);
        this.mLoadLayout.setLayoutParams(new LayoutParams(-1, -1));
        getPlayUper().addView(this.mLoadLayout);
        UIsUtils.inflate(getActivity(), R.layout.live_full_play_controller, this.mLoadLayout, true);
        if (!(isOnlyFull() || isPanoramaVideo())) {
            UIsUtils.inflate(getActivity(), R.layout.detail_half_play_top, this.mLoadLayout, true);
            UIsUtils.inflate(getActivity(), R.layout.detailplay_half_live_progress, this.mLoadLayout, true);
            UIsUtils.inflate(getActivity(), R.layout.play_live_lower, getPlayLower(), true);
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.init();
            }
            initHalfController();
        }
        initFullController();
        initFavouriteStatus();
        initRedPacketControl();
        updateRedPacketFrom();
        if (isPanoramaVideo()) {
            initPanoramaController();
        }
        registerDlnaProtocol();
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(getActivity(), new LeMessage(401));
        if (LeResponseMessage.checkResponseMessageValidity(response, DLNAProtocol.class)) {
            this.mDlnaProtocol = (DLNAProtocol) response.getData();
        }
        if (isOnlyFull() || isPanoramaVideo()) {
            full();
            return;
        }
        try {
            String cid;
            StringBuilder sb = new StringBuilder();
            sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.halpPlayPage);
            if (LiveLunboUtils.isLunboType(getLaunchMode()) || getLaunchMode() == 102) {
                cid = getChannelId();
            } else {
                cid = StatisticsUtils.getPageId();
            }
            if (!TextUtils.isEmpty(cid) && cid.equals(NetworkUtils.DELIMITER_LINE)) {
                cid = PageIdConstant.onLiveSportCtegoryPage;
            }
            DataStatistics.getInstance().sendActionInfo(getActivity(), "0", "0", LetvUtils.getPcode(), "19", sb.toString(), "0", cid, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLiveType() {
        if (this.mLiveflow == null) {
            return "";
        }
        LogInfo.log("fornia", "LiveroomplayerController getLiveType:" + this.mLiveflow.getCode() + this.mLiveflow.getChannelName());
        return this.mLiveflow.getCode();
    }

    public String syncGetPlayUrl(Device device) {
        if (this.mLiveflow == null) {
            return null;
        }
        return this.mLiveflow.syncGetPlayUrl(device);
    }

    public void startFlow() {
        this.mLiveflow.start();
    }

    private void initPanoramaController() {
        if (this.mFullController != null) {
            this.mFullController.initPanoramaController();
        }
    }

    protected void initFullController() {
        this.mFullController = new LiveFullControllerBar(getActivity(), this, isPanoramaVideo());
        this.mFullController.setData(isOnlyFull());
    }

    protected void onBarrageMsgSent(BarrageBean text) {
        LogInfo.log("fornia", "在弹幕被发送之后 playlivecon 2222onBarrageMsgSent:" + text + this.mHalfTabContrller);
        if (this.mHalfTabContrller != null && this.mHalfTabContrller.mViewPagerAdapter.getItem(0) != null && (this.mHalfTabContrller.mViewPagerAdapter.getItem(0) instanceof ChatFragmant)) {
            LogInfo.log("fornia", "在弹幕被发送之后 playlivecon 3333onBarrageMsgSent:" + text);
            ((ChatFragmant) this.mHalfTabContrller.mViewPagerAdapter.getItem(0)).sendBarrageMessage(text);
        }
    }

    protected void initHalfController() {
        this.mHalfController = new LiveHalfControllerBar(getActivity(), this);
    }

    private void initFavouriteStatus() {
        this.mCollected = DBManager.getInstance().getChannelListTrace().hasCollectChannel(getChannelId(), LiveLunboUtils.getChannelDBTypeFromLaunchMode(getLaunchMode()));
        if (this.mHalfController != null) {
            this.mHalfController.setBottomBarStatus();
        }
        if (this.mFullController != null) {
            this.mFullController.setCollectState();
        }
    }

    public void updateCollectState() {
        if (this.mHalfController != null) {
            this.mHalfController.setBottomBarStatus();
        }
        if (this.mFullController != null) {
            this.mFullController.setCollectState();
        }
    }

    public void setFloatBallVisible(boolean isVisible) {
        if (this.mPlayCallback != null) {
            this.mPlayCallback.setFloatBallVisible(isVisible);
        }
    }

    private RedPacketFrom getRedPacketFrom() {
        RedPacketFrom redPacketFrom = new RedPacketFrom(2);
        if (LetvUtils.isLunboOrWeishi(getLaunchMode())) {
            redPacketFrom.content = getChannelId();
        } else {
            redPacketFrom.content = this.mLiveflow.getPushLiveId();
        }
        return redPacketFrom;
    }

    private void updateRedPacketFrom() {
        ((LetvBaseActivity) getActivity()).setRedPacketFrom(getRedPacketFrom());
    }

    private void changeRedPacketLocation(boolean isLandspace) {
        ((LetvBaseActivity) getActivity()).setRedPacketEntryLocation(isLandspace);
    }

    private RedPacketUI getRedPacketEntry() {
        return ((LetvBaseActivity) getActivity()).getBaseRedPacket();
    }

    private void initRedPacketControl() {
        if (getRedPacketEntry() != null) {
            getRedPacketEntry().setDialogDisplayCallback(new 2(this));
        }
    }

    public void onResume() {
        RxBus.getInstance().send("rx_bus_live_live_action_update_system_ui");
        if (this.mAudioManagerUtils != null) {
            this.mAudioManagerUtils.requestFocus();
        }
        if (this.mLiveflow != null) {
            loading(false);
            this.mLiveflow.resumeCde();
            if (this.mFullController != null) {
                this.mFullController.startWatchAndBuy(this.mLiveflow.getPartId());
            }
            LogInfo.log("leiting", "onResume mPlayInterupted=" + this.mPlayInterupted);
            if (this.mPlayInterupted) {
                this.mPlayInterupted = false;
                StatisticsInfo statisticsInfo = this.mLiveflow.mStatisticsHelper.mStatisticsInfo;
                statisticsInfo.mInterruptNum++;
                this.mLiveflow.mStatisticsHelper.mStatisticsInfo.mReplayType = 3;
                this.mLiveflow.statisticsLaunch(false);
                this.mLiveflow.statisticsPlayInit();
                if (!this.mLiveflow.mStatisticsHelper.mStatisticsInfo.mIsPlayingAds) {
                    this.mLiveflow.statisticsPlaying();
                }
            } else {
                this.mLiveflow.statisticsLaunch(false);
            }
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.onResume();
            }
        }
    }

    public void onStop() {
        this.mPlayInterupted = true;
        if (this.mLiveflow != null) {
            this.mLiveflow.pauseCde();
            if (this.mLiveflow.mStatisticsHelper != null) {
                this.mLiveflow.mStatisticsHelper.stopTimeStatistics();
            }
        }
        if (this.mFullController != null) {
            this.mFullController.endWatchAndBuy();
        }
    }

    private void dispMobileNetBg(boolean isLandscape) {
        boolean isShowMiguTip = true;
        if (!this.mLiveflow.isWo3GUser() && !TextUtils.isEmpty(this.mLiveflow.getRealUrl()) && PreferencesManager.getInstance().isShow3gDialog()) {
            if (com.letv.core.utils.NetworkUtils.getNetworkType() != 2 && com.letv.core.utils.NetworkUtils.getNetworkType() != 3) {
                return;
            }
            if (isLandscape) {
                if (this.mFullController != null) {
                    if (!(this.mLiveflow.isWo3GUser() && this.mLiveflow.isMigu())) {
                        isShowMiguTip = false;
                    }
                    this.mFullController.show3gLayout(false, isLandscape, isShowMiguTip);
                }
                if (this.mHalfController != null) {
                    this.mHalfController.hide3gLayout();
                    return;
                }
                return;
            }
            if (this.mHalfController != null) {
                if (!(this.mLiveflow.isWo3GUser() && this.mLiveflow.isMigu())) {
                    isShowMiguTip = false;
                }
                this.mHalfController.show3gLayout(false, isLandscape, isShowMiguTip);
            }
            if (this.mFullController != null) {
                this.mFullController.hide3gLayout();
            }
        }
    }

    private void addLivePayViewLayout() {
        if (this.mLivePayLayout == null) {
            this.mLivePayLayout = new LivePayLayout(getActivity(), this);
            this.mLivePayLayout.setLayoutParams(new LayoutParams(-1, -1));
            this.mLivePayLayout.setTitle(StringUtils.formatLiveTitle(this.mLiveflow.getChannelNum(), this.mLiveflow.getChannelName(), this.mLiveflow.getProgramName()));
            getPlayUper().addView(this.mLivePayLayout);
            if (this.mLoadLayout != null) {
                this.mLoadLayout.finish();
            }
            if (UIsUtils.isLandscape(getActivity()) && this.mLivePayLayout != null) {
                this.mLivePayLayout.showFullScreenTitleBar();
            }
        }
    }

    private void checkPay(CheckPayCallback call) {
        if (this.mLiveflow.isPay()) {
            if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
                addLivePayViewLayout();
                this.mLivePayLayout.setCallBack(new LivePayCallbackImpl(this, null));
                if (PreferencesManager.getInstance().isLogin()) {
                    if (this.mLiveflow.isPayed()) {
                        removeLivePayLayout();
                        if (call != null) {
                            call.payCallback();
                            return;
                        }
                        return;
                    }
                    return;
                } else if (this.mLivePayLayout != null) {
                    this.mLivePayLayout.showLayout(1001);
                    return;
                } else {
                    return;
                }
            }
            removeLivePayLayout();
            this.mLoadLayout.requestError();
        } else if (call != null) {
            call.freeCallback();
        }
    }

    private void showToast3g() {
        if (!com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            return;
        }
        if (com.letv.core.utils.NetworkUtils.getNetworkType() == 2 || com.letv.core.utils.NetworkUtils.getNetworkType() == 3) {
            ToastUtils.showToast(getActivity(), this.mPlayOn3GText);
        }
    }

    public void hide3gLayout() {
        if (this.mHalfController != null) {
            this.mHalfController.hide3gLayout();
        }
        if (this.mFullController != null) {
            this.mFullController.hide3gLayout();
        }
    }

    private void show3gLayout(boolean isBlack, boolean isShowMiguTip) {
        if (!(this.mIsFull || this.mHalfController == null)) {
            this.mHalfController.show3gLayout(isBlack, this.mIsFull, isShowMiguTip);
            if (this.mFullController != null) {
                this.mFullController.hide();
            }
        }
        if (this.mIsFull && this.mFullController != null) {
            this.mFullController.show3gLayout(isBlack, this.mIsFull, isShowMiguTip);
            if (this.mHalfController != null) {
                this.mHalfController.hide();
            }
        }
    }

    public void format() {
        onDestroy();
        this.mLiveflow.setAdsFinished(false);
        removeLivePayLayout();
        if (this.mHalfTabContrller != null) {
            this.mHalfTabContrller.format();
        }
        getPlayUper().removeAllViews();
        getPlayLower().removeAllViews();
    }

    public void onLivePayLoginResult(boolean loginSuccess) {
        if (loginSuccess && this.mLiveflow != null) {
            this.mLiveflow.run();
        }
    }

    public void onShareActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mFullController != null) {
            this.mFullController.onShareActivityResult(requestCode, resultCode, data);
        }
    }

    public void onActivityResultLoginSuccess() {
        if (this.mHalfTabContrller != null) {
            this.mHalfTabContrller.onLoginSuccess();
        }
        if (PreferencesManager.getInstance().isVip()) {
            this.mLiveflow.onActiResultProcess();
        }
    }

    public void onActivityResultPaySuccess(boolean isPaySucceed) {
        if (isPaySucceed) {
            this.mLiveflow.onActiResultProcess();
        }
    }

    public void curVolume(int max, int progrees) {
    }

    public void curVolume(int max, int progrees, boolean isUp) {
        if (this.mLiveflow != null) {
            this.mLiveflow.setAdsMuteViewStatus(progrees);
        }
    }

    public void startLongWatchCountDown() {
        if (this.mTimerWatch == null || this.mTimerTask == null) {
            this.mTimerWatch = new Timer();
        } else {
            this.mTimerTask.cancel();
        }
        this.mTimerTask = new 3(this);
        try {
            this.mTimerWatch.schedule(this.mTimerTask, this.LONG_WATCH_PERIOD);
        } catch (Exception e) {
            LogInfo.log("lb", "长时间观看提醒错误startLongWatchCountDown: " + e.getMessage());
            this.mTimerWatch = null;
            this.mTimerTask = null;
        }
    }

    public void onTimeChange() {
    }

    public void onBatteryChange(int curStatus, int curPower) {
    }

    public void onNetChange() {
        if (this.mLiveflow != null) {
            this.mLiveflow.onNetChange();
        }
        if (com.letv.core.utils.NetworkUtils.getNetworkType() == 0 && this.mFullController != null) {
            this.mFullController.setWatchAndBuyViewVisbile(false);
        }
    }

    public void onHeadsetPlug() {
    }

    public void onLongPress() {
        if (this.mFullController != null) {
            this.mFullController.clickShowAndHide(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (event.getRepeatCount() != 0) {
            return true;
        }
        if (isPanoramaVideo() && PreferencesManager.getInstance().isPanoramaPlayGuideVisible() && this.mFullController != null) {
            this.mFullController.playGuideClick();
            return true;
        } else if (this.mHalfTabContrller != null && this.mHalfTabContrller.isFullChat()) {
            this.mHalfTabContrller.hideFullChat();
            return true;
        } else if (UIsUtils.isLandscape(getActivity())) {
            half();
            return true;
        } else {
            back();
            return true;
        }
    }

    public void onSingleTapUp() {
        super.onSingleTapUp();
    }

    public void onDoubleTap() {
        if (this.mPlayCallback.isPlaying()) {
            pause();
        } else {
            star();
        }
    }

    public void onMiddleSingleFingerUp() {
        if (this.mFullController != null && UIsUtils.isLandscape(getActivity())) {
            this.mFullController.singleFingerUp();
        }
    }

    public void onMiddleSingleFingerDown() {
        if (this.mFullController != null) {
            this.mFullController.singleFingerDown();
        }
    }

    public void onLandscapeScrollFinish(float incremental) {
        if (!UIsUtils.isLandscape(getActivity())) {
            return;
        }
        if (incremental > 0.15f) {
            if (this.mFullController != null) {
                this.mFullController.singleScrollRight();
            }
        } else if (incremental < -0.15f && this.mFullController != null) {
            this.mFullController.singleScrollLeft();
        }
    }

    public void changeDirection(boolean isLandscape) {
        if (isLandscape) {
            if (this.mHalfController != null) {
                this.mHalfController.hide();
                this.mHalfController.setBtnBackForeverVisible(false);
            }
            LogInfo.log("live__", "changeDirection isLandscape = true mFullController = " + this.mFullController);
            if (this.mFullController != null) {
                this.mIsFull = true;
                LogInfo.log("live__", "changeDirection mFullController.show()");
                this.mFullController.show();
                this.mFullController.setBarrageVisibility(true);
                this.mFullController.setWatchAndBuyViewVisbile(true);
            }
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.hideShareDialog();
            }
            if (this.mLivePayLayout != null) {
                this.mLivePayLayout.showFullScreenTitleBar();
                this.mLivePayLayout.setTitle(StringUtils.formatLiveTitle(this.mLiveflow.getChannelNum(), this.mLiveflow.getChannelName(), this.mLiveflow.getProgramName()));
                this.mLivePayLayout.changeScreen(false);
            }
        } else {
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.notify(0, 6);
            }
            if (this.mFullController != null) {
                this.mFullController.hide();
                this.mFullController.setBarrageVisibility(false);
                this.mFullController.setWatchAndBuyViewVisbile(false);
            }
            if (this.mHalfController != null) {
                this.mHalfController.show();
                this.mIsFull = false;
            }
            if (this.mLivePayLayout != null) {
                this.mLivePayLayout.showFullScreenTitleBar();
                this.mLivePayLayout.setTitle(StringUtils.formatLiveTitle(this.mLiveflow.getChannelNum(), this.mLiveflow.getChannelName(), this.mLiveflow.getProgramName()));
                this.mLivePayLayout.changeScreen(true);
            }
        }
        changeRedPacketLocation(isLandscape);
        updateCollectState();
        dispMobileNetBg(isLandscape);
    }

    public void lockScreenPause() {
        if (this.mLiveflow != null) {
            this.mLiveflow.lockScreenPause();
        }
    }

    public void unLockSceenResume() {
        if (this.mLiveflow != null) {
            this.mLiveflow.unLockSceenResume();
        }
    }

    public void finishPlayer() {
        if (getActivity() != null) {
            if (!(TextUtils.isEmpty(this.mLiveflow.getPushLiveId()) || mCallBack == null)) {
                if (this.mLiveflow.isWebPlayExeception()) {
                    mCallBack.onPlayFailed();
                } else {
                    mCallBack.onPlaySucceed();
                }
            }
            Bundle bundle = LetvApplication.getInstance().getLiveLunboBundle();
            if (bundle != null) {
                bundle.putInt("launchMode", getLaunchMode());
                LetvApplication.getInstance().setLiveLunboBundle(bundle);
            }
            this.mPlayCallback.finishPlayer();
            getActivity().finish();
        }
    }

    public String getShareProgramName() {
        LogInfo.log(LetvHttpConstant.LOG, "getShareProgramName getLaunchMode() = " + getLaunchMode());
        if (LetvUtils.isLunboOrWeishi(getLaunchMode())) {
            return formatTitle(this.mLiveflow.getChannelNum(), this.mLiveflow.getChannelName(), this.mLiveflow.getProgramName());
        }
        return this.mLiveflow.getProgramName();
    }

    public String formatTitle(String channel_Num, String channel_Name, String program_Name) {
        if (TextUtils.isEmpty(channel_Name)) {
            return program_Name;
        }
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(channel_Num) && channel_Num.length() == 1) {
            builder.append("0");
            builder.append(channel_Num).append("  ");
        } else if (!TextUtils.isEmpty(channel_Num)) {
            builder.append(channel_Num).append("  ");
        }
        builder.append(channel_Name);
        if (!TextUtils.isEmpty(program_Name)) {
            builder.append("：").append(program_Name);
        }
        return builder.toString();
    }

    public String getShareLiveUrl() {
        LogInfo.log(LetvHttpConstant.LOG, "getShareLiveUrl getLaunchMode() = " + getLaunchMode());
        switch (getLaunchMode()) {
            case 1:
                return ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_LUNBO.replace("{id}", getUniqueId());
            case 3:
            case 4:
            case 5:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_NORMAL.replace("{channel}", getLiveType()).replace("{id}", getUniqueId());
            case 15:
                return ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_WEISHI.replace("{id}", getUniqueId());
            case 23:
                return ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_OTHER.replace("{id}", getUniqueId());
            default:
                return "";
        }
    }

    public void onDestroy() {
        if (this.mDlnaProtocol != null) {
            this.mDlnaProtocol.protocolDestory();
            this.mDlnaProtocol = null;
        }
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_DLNA_LIVE_PROTOCOL);
        if (this.mLiveflow != null) {
            this.mLiveflow.onDestroy();
        }
        if (this.mFullController != null) {
            this.mFullController.onDestroy();
        }
        if (this.mHalfController != null) {
            this.mHalfController.onDestroy();
        }
        if (this.mAudioManagerUtils != null) {
            this.mAudioManagerUtils.abandonFocus();
        }
        if (this.mTimerWatch != null) {
            this.mTimerWatch.cancel();
        }
        if (this.mTimerTask != null) {
            this.mTimerTask.cancel();
        }
        this.mTimerWatch = null;
        this.mTimerTask = null;
        mCallBack = null;
    }

    public void destroyStatisticsInfo() {
        if (this.mLiveflow != null && this.mLiveflow.mStatisticsHelper != null) {
            this.mLiveflow.mStatisticsHelper.destroy();
        }
    }

    public void onChange(int currentState) {
        this.mCurrentState = currentState;
        LogInfo.log("clf", "onchage mCurrentState = " + this.mCurrentState);
        if (this.mCurrentState == 3) {
            LogInfo.log("clf", "播放状态onChange STATE_PLAYING");
            this.mLiveflow.setWebPlayExeception(false);
            this.mLiveflow.statisticsPlaying();
            this.mLiveflow.mStatisticsHelper.startTimeStatistics();
            this.mLoadLayout.finish();
            if (this.mHalfController != null) {
                this.mHalfController.star();
                if (LetvUtils.isSMNote() && this.mHalfController != null) {
                    this.mHalfController.setFullBtnVisible(true);
                    this.mPlayCallback.setLock(isLock());
                }
            }
            if (this.mFullController != null) {
                this.mFullController.star();
                this.mFullController.setBarrageVisibility(true);
                this.mFullController.setWatchAndBuyViewVisbile(true);
            }
            if (this.mIsFirstPlay) {
                if (UIsUtils.isLandscape(getActivity())) {
                    if (this.mFullController != null) {
                        this.mFullController.show();
                    }
                } else if (this.mHalfController != null) {
                    this.mHalfController.show();
                }
            }
            this.mPlayInterupted = false;
            this.mIsFirstPlay = false;
        } else if (this.mCurrentState == 4) {
            this.mLoadLayout.finish();
            if (this.mHalfController != null) {
                this.mHalfController.pause();
            }
            if (this.mFullController != null) {
                this.mFullController.pause();
            }
            this.mLiveflow.mStatisticsHelper.pauseTimeStatistics();
        } else if (this.mCurrentState == -1) {
            if (this.mLiveflow != null) {
                this.mLiveflow.stopCde();
                this.mLiveflow.closePauseAd();
            }
            this.mLoadLayout.requestError(getActivity().getResources().getString(2131100497), "");
            this.mLiveflow.setWebPlayExeception(true);
            this.mLiveflow.mStatisticsHelper.stopTimeStatistics();
        } else if (this.mCurrentState == 0) {
            if (this.mHalfController != null) {
                this.mHalfController.Inoperable();
            }
            if (this.mFullController != null) {
                this.mFullController.Inoperable();
            }
        } else if (this.mCurrentState == 5) {
            if (this.mLiveflow != null) {
                this.mLiveflow.stopCde();
            }
            this.mLiveflow.mStatisticsHelper.stopTimeStatistics();
        } else if (this.mCurrentState == 6) {
            LogInfo.log("clf", "!!!!!!!!stopCde....STATE_STOPBACK");
            if (this.mLiveflow != null) {
                this.mLiveflow.stopCde();
            }
            if (VERSION.SDK_INT > 8) {
                IRVideo.getInstance().videoEnd(this.mContext);
            }
            this.mLiveflow.mStatisticsHelper.stopTimeStatistics();
            this.mIsFirstPlay = true;
            LetvApplication.getInstance().setPush(false);
        } else if (this.mCurrentState == 7) {
            if (this.mPlayCallback.isEnforcementPause()) {
                this.mLoadLayout.finish();
                if (this.mHalfController != null) {
                    this.mHalfController.pause();
                }
                if (this.mFullController != null) {
                    this.mFullController.pause();
                }
                this.mLiveflow.mStatisticsHelper.pauseTimeStatistics();
            }
        } else if (this.mCurrentState != 1 && this.mCurrentState == 2) {
            LogInfo.log("播放器起播第一帧", "------------");
        }
    }

    public void setPlayBtnStatus(boolean isPlaying) {
        if (isPlaying) {
            if (this.mHalfController != null) {
                this.mHalfController.star();
            }
            if (this.mFullController != null) {
                this.mFullController.star();
                return;
            }
            return;
        }
        if (this.mHalfController != null) {
            this.mHalfController.pause();
        }
        if (this.mFullController != null) {
            this.mFullController.pause();
        }
    }

    public void onDlnaChange(boolean isDlnaPlaying) {
        if (isDlnaPlaying) {
            pause();
            setPlayBtnStatus(true);
            if (this.mFullController != null) {
                this.mFullController.onDlnaPlayStart();
                this.mFullController.setBarrageVisibility(false);
                return;
            }
            return;
        }
        star();
    }

    public void cancelLongTimeWatch() {
        LongWatchNoticeDialog.dismissDialog();
        startLongWatchCountDown();
    }

    public void blockStart() {
        if (!isDlnaPlaying()) {
            this.mLoadLayout.loadingVideo("");
            this.mBlockStartTime = System.currentTimeMillis();
            this.mLiveflow.mStatisticsHelper.setIsBlocking(true);
            this.mLiveflow.mStatisticsHelper.statisticsPlayActions("block");
        }
    }

    public void blockEnd() {
        this.mLoadLayout.finish();
        this.mLiveflow.mStatisticsHelper.setIsBlocking(false);
        this.mLiveflow.mStatisticsHelper.statisticsPlayActions("eblock", System.currentTimeMillis() - this.mBlockStartTime);
    }

    public void blockTwiceAlert() {
        if (!isDlnaPlaying() && this.mFullController != null && UIsUtils.isLandscape(getActivity())) {
            this.mFullController.blockTwiceAlert();
        }
    }

    public int getPlayLevel() {
        return 0;
    }

    public void onRequestErr() {
        this.mLiveflow.onRequestErr();
    }

    public void onNetChangeErr() {
        onRequestErr();
    }

    public void onVipErr(boolean isLogin) {
    }

    public void onJumpErr() {
    }

    public void onDemandErr() {
    }

    public void onPlayFailed() {
        this.mLiveflow.onPlayFailed();
    }

    public void calledInError() {
    }

    public void calledInFinish() {
    }

    public void closeDlna(boolean isStopDlnaPlay) {
    }

    public void commitErrorInfo() {
    }

    public void requestData(boolean isShowListLoading, boolean hasToPlayResult) {
        if (this.mLiveflow != null) {
            this.mLiveflow.requestData(isShowListLoading, hasToPlayResult);
        }
    }

    public void clickPlayOrPause() {
        if (this.mDlnaProtocol != null) {
            this.mDlnaProtocol.protocolClickPauseOrPlay();
        }
    }

    public void star() {
        if (!isDlnaPlaying()) {
            hide3gLayout();
            this.mPlayCallback.setEnforcementPause(false);
            if (this.mLiveflow.isPauseAd() && this.mLiveflow.isHaveFrontAds()) {
                this.mLiveflow.setADPause(false);
                this.mLiveflow.setPauseAd(false);
                this.mLiveflow.getLiveFrontAd(true);
                this.mPlayCallback.setEnforcementWait(true);
                LogInfo.log("clf", "------------star  1 广告由暂停，重新请求播放");
                return;
            }
            if (this.mPlayCallback.isEnforcementPause() && this.mLiveflow.isAdsPlaying()) {
                loading(false);
            }
            this.mLiveflow.setPauseAd(false);
            if (this.mHalfController != null) {
                this.mHalfController.setTitle(this.mLiveflow.getProgramName());
            }
            LogInfo.log("clf", "----播放 star() 播放器状态mCurrentState= " + this.mCurrentState);
            LogInfo.log("clf", "----播放 star() 播放器状mLiveflow.getRealUrl()= " + this.mLiveflow.getRealUrl());
            if (this.mCurrentState == 7) {
                this.mLoadLayout.loadingVideo(this.mLiveflow.getProgramName());
                this.mPlayCallback.playNet(this.mLiveflow.getRealUrl(), true, false, 0);
                return;
            }
            this.mPlayCallback.start(this.mLiveflow.getRealUrl(), this.mLiveflow.isAdsFinished());
        }
    }

    public void pause() {
        if (this.mDlnaProtocol == null || !this.mDlnaProtocol.isPlayingDlna()) {
            this.mPlayCallback.playPause();
        }
    }

    public void startDlna() {
        if (this.mDlnaProtocol != null) {
            this.mDlnaProtocol.protocolSearch();
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, "0", "c655", null, 6, null);
        }
    }

    public void closeSensor() {
        this.mPlayCallback.closeSensor();
    }

    public void openSensor() {
        this.mPlayCallback.openSensor();
    }

    public void star3G() {
        showToast3g();
        PreferencesManager.getInstance().setShow3gDialog(false);
        if (this.mLiveflow.isMigu()) {
            PreferencesManager.getInstance().setShowMiGuDialog(false);
        }
        this.mPlayCallback.setEnforcementPause(false);
        this.mLiveflow.adsOnResume();
        loading(false);
        hide3gLayout();
        this.mLiveflow.run();
    }

    public void full() {
        if (this.mLivePayLayout != null) {
            this.mLivePayLayout.showFullScreenTitleBar();
            this.mLivePayLayout.setTitle(StringUtils.formatLiveTitle(this.mLiveflow.getChannelNum(), this.mLiveflow.getChannelName(), this.mLiveflow.getProgramName()));
            this.mLivePayLayout.invalidate();
        }
        this.mPlayCallback.lockOnce(getActivity().getRequestedOrientation());
        UIsUtils.setScreenLandscape(getActivity());
    }

    public void half() {
        if (isOnlyFull() && LiveLunboUtils.isLunboType(getLaunchMode())) {
            back();
        } else if (isPanoramaVideo()) {
            back();
        } else {
            if (this.mFullController != null) {
                this.mFullController.hide();
            }
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.changeChatConnect(true);
            }
            if (isOnlyFull()) {
                finishPlayer();
            } else {
                this.mPlayCallback.lockOnce(getActivity().getRequestedOrientation());
                UIsUtils.setScreenPortrait(getActivity());
            }
            String userClickBackTime = "";
            long userClickBackStartTime = 0;
            if (userClickBackTime == null) {
                userClickBackTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
                userClickBackStartTime = System.currentTimeMillis();
            }
            StringBuilder sb = new StringBuilder();
            if (userClickBackTime == null) {
                sb.append("time=").append(NetworkUtils.DELIMITER_LINE).append("&");
            } else {
                sb.append("time=").append(userClickBackTime).append("&");
            }
            if (((double) StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - userClickBackStartTime)) == 0.0d) {
                sb.append("ut=").append(NetworkUtils.DELIMITER_LINE).append("&");
            } else {
                sb.append("ut=").append(StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - userClickBackStartTime)).append("&");
            }
            sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.fullPlayPage);
            DataStatistics.getInstance().sendActionInfo(LetvApplication.getInstance(), "0", "0", LetvUtils.getPcode(), VType.FLV_720P_3D, sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
        }
    }

    public void back() {
        LogInfo.log("glh", "onclick bacck");
        if (!isOnlyFull() && UIsUtils.isLandscape(getActivity())) {
            half();
        } else if (this.mLiveflow.getFrom() != 20 && this.mLiveflow.getFrom() != 21) {
            finishPlayer();
            if ((this.mContext instanceof BasePlayActivity) && ((BasePlayActivity) this.mContext).mIsVr) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new BasePlayActivityConfig(BaseApplication.getInstance()).createPanoramaVideo(this.mLiveflow.getPushLiveId(), this.mLiveflow.getCode())));
            }
        } else if (this.mShouldBack) {
            ActivityUtils.getInstance().removeAll();
        } else {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("video://video"));
            intent.putExtra("from_M", true);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
        try {
            String userClickBackTime = "";
            if (this.mFullController != null) {
                userClickBackTime = this.mFullController.getUserClickBackTime();
            }
            if (userClickBackTime == null && this.mHalfController != null) {
                userClickBackTime = this.mHalfController.getUserClickBackTime();
            }
            if (userClickBackTime == null) {
                userClickBackTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("time=").append(userClickBackTime).append("&");
            long backTime = BaseTypeUtils.stol(userClickBackTime, 0);
            if (((double) StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - backTime)) == 0.0d) {
                sb.append("ut=").append(NetworkUtils.DELIMITER_LINE).append("&");
            } else {
                sb.append("ut=").append(StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - backTime)).append("&");
            }
            sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.halpPlayPage);
            DataStatistics.getInstance().sendActionInfo(LetvApplication.getInstance(), "0", "0", LetvUtils.getPcode(), VType.FLV_720P_3D, sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
            if ((TextUtils.isEmpty(StatisticsUtils.getPageId()) || !StatisticsUtils.getPageId().equals(PageIdConstant.pushPage)) && this.mLiveflow.getFrom() != 2) {
                StatisticsUtils.staticticsInfoPost(LetvApplication.getInstance(), "19", StatisticsUtils.getFl(), null, -1, null, StatisticsUtils.getPageId(), StatisticsUtils.getPageId(), null, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void share() {
        if (this.mHalfTabContrller != null) {
            this.mHalfTabContrller.share();
        }
    }

    public void toPip() {
        boolean z = true;
        LiveStreamBean liveStreamBean = this.mLiveflow.getLiveStreamBean();
        if (liveStreamBean != null) {
            if (getActivity() != null) {
                getActivity().finish();
            }
            Bundle mBundle = new Bundle();
            mBundle.putBoolean("isLive", true);
            mBundle.putString(PlayConstant.LIVE_STREAMID, liveStreamBean.getStreamId());
            mBundle.putString("url", liveStreamBean.getLiveUrl());
            mBundle.putString(PlayConstant.LIVE_PROGRAM_NAME, this.mLiveflow.getProgramName());
            mBundle.putString(PlayConstant.LIVE_URL_350, liveStreamBean.liveUrl350);
            mBundle.putString(PlayConstant.LIVE_STREAMID_350, liveStreamBean.streamId350);
            mBundle.putString(PlayConstant.LIVE_URL_1000, liveStreamBean.liveUrl1000);
            mBundle.putString(PlayConstant.LIVE_STREAMID_1000, liveStreamBean.streamId1000);
            mBundle.putString(PlayConstant.LIVE_URL_1300, liveStreamBean.liveUrl1300);
            mBundle.putString(PlayConstant.LIVE_STREAMID_1300, liveStreamBean.streamId1300);
            mBundle.putString(PlayConstant.LIVE_URL_720, liveStreamBean.liveUrl720p);
            mBundle.putString(PlayConstant.LIVE_STREAMID_720, liveStreamBean.streamId720p);
            String str = PlayConstant.LIVE_IS_LOW;
            if (liveStreamBean.getLiveUrl() != liveStreamBean.liveUrl350) {
                z = false;
            }
            mBundle.putBoolean(str, z);
            mBundle.putString(LetvConstant.Intent.Bundle.VIDEO_FORMAT, LetvApplication.getInstance().getVideoFormat());
            mBundle.putString("code", this.mLiveflow.getCode());
            mBundle.putInt(PlayConstant.LIVE_MODE, getLaunchMode());
            mBundle.putBoolean(PlayConstant.LIVE_FULL_ONLY, isOnlyFull());
            mBundle.putString(PlayConstant.LIVE_ID, this.mLiveflow.getUniqueId());
            mBundle.putBoolean("isWo3GUser", this.mLiveflow.isWo3GUser());
            String pcode = Global.PCODE;
            String version = Global.VERSION;
            String deviceId = Global.DEVICEID;
            mBundle.putString("pcode", pcode);
            mBundle.putString("version", version);
            mBundle.putString(LetvConstant.Intent.Bundle.DEVICEID, deviceId);
            LeMessageManager.getInstance().dispatchMessage(this.mContext, new LeMessage(800, mBundle));
            return;
        }
        ToastUtils.showToast(getActivity(), 2131100149);
    }

    public void playHd(int level) {
        this.mPlayCallback.playPause();
        this.mPlayCallback.stopPlayback();
        this.mLiveflow.stopAdsPlayBack();
        loading(false);
        switch (level) {
            case 1:
                this.mLiveflow.setUserStreamType(StreamType.STREAM_350);
                break;
            case 2:
                this.mLiveflow.setUserStreamType(StreamType.STREAM_1000);
                break;
            case 3:
                this.mLiveflow.setUserStreamType(StreamType.STREAM_1300);
                break;
            case 4:
                this.mLiveflow.setUserStreamType(StreamType.STREAM_720p);
                break;
        }
        this.mPlayLevel = level;
        this.mLiveflow.setUserControllLevel(true);
        if (TextUtils.isEmpty(this.mLiveflow.getSignal()) || !this.mLiveflow.getSignal().equals("9")) {
            this.mLiveflow.playUrl(this.mLiveflow.getLiveStreamBean());
        } else {
            this.mLiveflow.requestMiGuLiveURLByChannelId(this.mLiveflow.getChannelId(), 0);
        }
        this.mLiveflow.mStatisticsHelper.mStatisticsInfo.resetTimeInfos();
        this.mLiveflow.statisticsLaunch(true);
    }

    public void book() {
        if (this.mHalfController != null) {
            this.mHalfController.showBookView();
        }
    }

    public void hideBookView() {
        if (this.mHalfController != null) {
            this.mHalfController.hideBookView();
        }
    }

    public void chat() {
        if (this.mHalfTabContrller != null) {
            this.mHalfTabContrller.chat();
        }
    }

    public void favourite() {
        if (this.mHalfTabContrller != null) {
            this.mHalfTabContrller.favourite();
        }
        if (this.mHalfController != null) {
            this.mHalfController.setBottomBarStatus();
        }
    }

    public String getChannelNum() {
        return this.mLiveflow != null ? this.mLiveflow.getChannelNum() : "";
    }

    public String getChannelName() {
        return this.mLiveflow != null ? this.mLiveflow.getChannelName() : "";
    }

    public String getProgramName() {
        return this.mLiveflow != null ? this.mLiveflow.getProgramName() : "";
    }

    public String getGuestImgUrl() {
        return this.mLiveflow != null ? this.mLiveflow.getGuestImgUrl() : "";
    }

    public String getHomeImgUrl() {
        return this.mLiveflow != null ? this.mLiveflow.getHomeImgUrl() : "";
    }

    public String getHomeName() {
        return this.mLiveflow != null ? this.mLiveflow.getHomeName() : "";
    }

    public String getGuestName() {
        return this.mLiveflow.getGuestName();
    }

    public LivePriceBean getLivePrice() {
        return this.mLiveflow != null ? this.mLiveflow.getLivePriceBean() : null;
    }

    public LiveStreamBean getLiveStreamBean() {
        return this.mLiveflow.getLiveStreamBean();
    }

    public LiveRemenBaseBean getLiveInfo() {
        return this.mLiveflow.getLiveInfo();
    }

    public String getPlayTime() {
        return StringUtils.formatPlayTime(this.mLiveflow.getPlayTime());
    }

    public PlayLiveFlow getLiveFlow() {
        LogInfo.log("fornia", "share--- 000000share initViews 初始化分享浮层 独立全屏mLiveflow：" + this.mLiveflow);
        return this.mLiveflow;
    }

    public int getTicketCount() {
        return this.mLiveflow.getTicketCount();
    }

    public void setCollected(boolean collected) {
        this.mCollected = collected;
    }

    public boolean getCollected() {
        return this.mCollected;
    }

    public CurrentProgram getCurProgram() {
        return this.mLiveflow.getCurrentProgram();
    }

    public String getChannelId() {
        return this.mLiveflow.getChannelId();
    }

    public String getCode() {
        return this.mLiveflow.getCode();
    }

    public void requestError(String msg) {
        this.mLoadLayout.requestError(msg);
        this.mLiveflow.setWebPlayExeception(true);
        removeLivePayLayout();
    }

    public void notPlay(String tx) {
        if (isPanoramaVideo()) {
            this.mLoadLayout.notPlay(getActivity().getString(2131100283));
        } else if (TextUtils.isEmpty(tx)) {
            this.mLoadLayout.notPlay();
        } else {
            this.mLoadLayout.notPlay(tx);
        }
    }

    public void setDownStreamTipVisible(boolean isVisible) {
        if (this.mFullController == null) {
            return;
        }
        if (isVisible) {
            if (UIsUtils.isLandscape(getActivity())) {
                this.mFullController.showDownStreamTip();
            }
            this.mFullController.showOverLoadStatus(true);
            return;
        }
        this.mFullController.showOverLoadStatus(false);
    }

    public void cannotPlayError() {
        this.mLoadLayout.cannotPlayError();
    }

    public void updateHdButton(LiveStreamBean liveStreamBean) {
        this.mFullController.setOnlyOneLevel(liveStreamBean);
    }

    public void onLiveUrlPost(LiveStreamBean liveStreamBean, boolean isToPlay) {
        updateHdButton(liveStreamBean);
        if (isToPlay) {
            checkPay(null);
        }
    }

    public void addAdsFragment(AdPlayFragmentProxy AdPlayFragmentProxy) {
        getActivity().getSupportFragmentManager().beginTransaction().add(2131361905, (Fragment) AdPlayFragmentProxy).commit();
    }

    public void play(String url) {
        PlayLiveFlow.LogAddInfo("开始播放，先判断是否显示非wifi提示框", "url=" + url);
        if (!showNetChangeDialog()) {
            PlayLiveFlow.LogAddInfo("开始播放", "mPlayCallback.isEnforcementWait()=" + this.mPlayCallback.isEnforcementWait());
            this.mLiveflow.mStatisticsHelper.mStatisticsInfo.mRealUrl = url;
            this.mPlayCallback.playNet(url, true, false, 0);
            if (this.mPlayLevel > 0 && !this.mLiveflow.isOverLoadDownStream()) {
                PreferencesManager.getInstance().setPlayLevel(this.mPlayLevel);
            }
        }
    }

    public boolean showNetChangeDialog() {
        if (this.mLiveflow.isWo3GUser() && !this.mLiveflow.isMigu()) {
            return false;
        }
        if (!PreferencesManager.getInstance().isShow3gDialog()) {
            if (this.mLiveflow.isMigu() && !PreferencesManager.getInstance().isShowMiGuDialog()) {
                showToast3g();
                return false;
            } else if (!this.mLiveflow.isMigu()) {
                showToast3g();
                return false;
            }
        }
        if (com.letv.core.utils.NetworkUtils.getNetworkType() != 2 && com.letv.core.utils.NetworkUtils.getNetworkType() != 3) {
            return false;
        }
        boolean isShowMiguTip;
        if (this.mLoadLayout != null) {
            this.mLoadLayout.finish();
        }
        if (this.mLiveflow.isWo3GUser() && this.mLiveflow.isMigu()) {
            isShowMiguTip = true;
        } else {
            isShowMiguTip = false;
        }
        show3gLayout(false, isShowMiguTip);
        this.mLiveflow.setADPause(true);
        this.mPlayCallback.setEnforcementPause(true);
        this.mPlayCallback.playPause();
        return true;
    }

    public void setEnforcementWait(boolean isWait) {
        this.mPlayCallback.setEnforcementWait(isWait);
    }

    public void resumeVideo() {
        this.mPlayCallback.resumeVideo();
    }

    public void pauseVideo() {
        this.mPlayCallback.pauseVideo();
    }

    public void setmProgramName(String name) {
        if (!TextUtils.isEmpty(name)) {
            this.mFullController.setTitle(name);
            if (this.mHalfController != null) {
                this.mHalfController.setTitle(name);
            }
            Bundle bundle = LetvApplication.getInstance().getLiveLunboBundle();
            if (bundle != null) {
                bundle.putString(PlayConstant.LIVE_PROGRAM_NAME, name);
                LetvApplication.getInstance().setLiveLunboBundle(bundle);
            }
            if (this.mLivePayLayout != null) {
                this.mLivePayLayout.setTitle(StringUtils.formatLiveTitle(this.mLiveflow.getChannelNum(), this.mLiveflow.getChannelName(), name));
            }
        }
    }

    public void setmSignal(String signal) {
    }

    public void updatePartId(String partID) {
        LogInfo.log("clf", "updatePartId...partID=" + partID);
        if (this.mFullController != null) {
            this.mFullController.endWatchAndBuy();
            if (!TextUtils.isEmpty(partID)) {
                this.mFullController.startWatchAndBuy(partID);
            }
        }
    }

    public void onProgramChange(CurrentProgram curProgram) {
        if (this.mFullController != null) {
            this.mFullController.onProgramChange(curProgram);
        }
    }

    public void loading(boolean checkShow) {
        if ((!checkShow || this.mLoadLayout.isShowLoading()) && !isDlnaPlaying()) {
            this.mLoadLayout.loadingVideo("");
        }
    }

    public void notifyHalfLivePlayFragment(int state) {
        if (state != 11 || this.mHalfTabContrller == null) {
            if (this.mLiveflow != null) {
                this.mLiveflow.setFlowState(state);
            }
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.notify(-1, state);
                return;
            }
            return;
        }
        this.mHalfTabContrller.notifyProgramsChanged(state);
    }

    public void toastNoMore() {
        ToastUtils.showToast(2131100511);
    }

    public boolean isEnforcementPause() {
        return this.mPlayCallback.isEnforcementPause();
    }

    public void playOnAdsFinish() {
        PlayLiveFlow.LogAddInfo("广告结束开始播放", "isPanoramaVideo=" + isPanoramaVideo() + ",PreferencesManager.getInstance().isPanoramaPlayGuideVisible()=" + PreferencesManager.getInstance().isPanoramaPlayGuideVisible());
        if (!isPanoramaVideo() || !PreferencesManager.getInstance().isPanoramaPlayGuideVisible()) {
            PlayLiveFlow.LogAddInfo("广告结束开始播放", "mLiveflow.getRealUrl()=" + this.mLiveflow.getRealUrl());
            if (!TextUtils.isEmpty(this.mLiveflow.getRealUrl()) && !showNetChangeDialog()) {
                PlayLiveFlow.LogAddInfo("广告结束开始播放", "PlayCallback.isEnforcementWait()=" + this.mPlayCallback.isEnforcementWait());
                this.mLoadLayout.loadingVideo(this.mLiveflow.getProgramName());
                if (this.mPlayCallback.isEnforcementWait()) {
                    this.mPlayCallback.start(this.mLiveflow.getRealUrl(), this.mLiveflow.isAdsFinished());
                } else {
                    this.mPlayCallback.start(this.mLiveflow.getRealUrl(), this.mLiveflow.isAdsFinished());
                }
            }
        }
    }

    public void play() {
        if (!isDlnaPlaying() && this.mLiveflow.getRealUrl() != null && !this.mPlayCallback.isPlaying()) {
            this.mLoadLayout.loadingVideo(this.mLiveflow.getProgramName());
            this.mPlayCallback.playNet(this.mLiveflow.getRealUrl(), true, false, 0);
        }
    }

    public void dismisNetChangeDialog() {
        hide3gLayout();
        ToastUtils.showToast(TipUtils.getTipMessage("100007", 2131100657));
    }

    public void notifyControllBarNetChange() {
        if (UIsUtils.isLandscape(getActivity())) {
            if (this.mFullController != null) {
                this.mFullController.onNetChange();
            }
        } else if (this.mHalfController != null) {
            this.mHalfController.onNetChange();
        }
    }

    public void onPlayNetNull() {
        this.mPlayCallback.playPause();
        this.mPlayCallback.stopPlayback();
        requestError(null);
    }

    public void playNetWhileNotWait(String realUrl) {
        if ((!isPanoramaVideo() || !PreferencesManager.getInstance().isPanoramaPlayGuideVisible()) && !this.mPlayCallback.isEnforcementWait()) {
            this.mPlayCallback.playNet(realUrl, true, false, 0);
        }
    }

    public boolean isPlaying() {
        return this.mPlayCallback.isPlaying();
    }

    public boolean isFirstPlay() {
        return this.mIsFirstPlay;
    }

    public int getFlowState() {
        return this.mLiveflow != null ? this.mLiveflow.getFlowState() : -1;
    }

    public LetvBaseBean getCurrentLiveData() {
        return this.mLiveflow != null ? this.mLiveflow.getCurrentLiveData() : null;
    }

    public ProgramEntity getCurrentLiveProgram() {
        return this.mLiveflow != null ? this.mLiveflow.getPlayingProgram() : null;
    }

    public String getUniqueId() {
        String uniqueId = this.mLiveflow != null ? this.mLiveflow.getUniqueId() : "";
        if (TextUtils.isEmpty(uniqueId)) {
            return this.mLiveflow.getPushLiveId();
        }
        return uniqueId;
    }

    public void changePlay(LiveRemenBaseBean live, boolean isPay, boolean isPlaying, int allowVote) {
        if (live != null) {
            setmProgramName(live.title);
            this.mLiveflow.setUniqueId(live.id);
            this.mLiveflow.setProgramName(live.title);
        }
        loading(false);
        this.mAllowVote = allowVote;
        this.mPlayCallback.playPause();
        this.mPlayCallback.stopPlayback();
        removeLivePayLayout();
        if (UIsUtils.isLandscape(this.mContext)) {
            if (this.mFullController != null) {
                this.mFullController.show();
            }
        } else if (this.mHalfController != null) {
            this.mHalfController.show();
        }
        if (live != null) {
            changeLaunchMode(live);
            this.mLiveflow.mStatisticsHelper.clear();
            this.mLiveflow.changePlay(live, isPay, isPlaying);
            if (this.mFullController != null) {
                this.mFullController.setBtnsVisible(false);
                this.mFullController.endWatchAndBuy();
                this.mFullController.resetWatchBuyShow();
                this.mFullController.showOverLoadStatus(false);
                this.mFullController.changeCurrentChannel(live.liveType, live.id);
                this.mFullController.initInteractFloatView();
            }
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.changePlay();
            }
            updateRedPacketFrom();
        }
        if (this.mDlnaProtocol != null && this.mDlnaProtocol.isPlayingDlna()) {
            this.mDlnaProtocol.protocolStop(true, false);
        }
    }

    public void changeMultiBranch(String channelId) {
        loading(false);
        this.mPlayCallback.playPause();
        this.mPlayCallback.stopPlayback();
        this.mLiveflow.changeMultiBranch(channelId);
    }

    public void changeLaunchMode(LiveRemenBaseBean live) {
        if (!TextUtils.isEmpty(live.liveType)) {
            setLaunchMode(LetvUtils.getLaunchMode(live.liveType));
            this.mLiveflow.setLaunchMode(getLaunchMode());
        }
    }

    public LetvBaseBean getData() {
        return this.mLiveflow != null ? this.mLiveflow.getData() : null;
    }

    public void loadPrograms(int direction) {
        if (this.mLiveflow != null) {
            this.mLiveflow.loadPrograms(direction);
        }
    }

    public boolean isPay() {
        return this.mLiveflow != null ? this.mLiveflow.isPay() : false;
    }

    public boolean isPanorama() {
        return isPanoramaVideo();
    }

    public boolean isDanmaku() {
        return this.mLiveflow.isDanmaku();
    }

    public boolean isDlnaPlaying() {
        return this.mDlnaProtocol != null && this.mDlnaProtocol.isPlayingDlna();
    }

    public boolean hasVote() {
        return this.mAllowVote == 1;
    }

    public boolean isAdShowing() {
        return this.mLiveflow.isAdsPlaying() || this.mLiveflow.isPauseAd();
    }

    public void setOrientationSensorLock(boolean isLock) {
        if (this.mPlayCallback != null) {
            this.mPlayCallback.setLock(isLock);
        }
    }

    public void setFloatControllerVisible(boolean isVisible) {
    }

    public void changeLunboChannel(String chName, String chEnName, String chId, String pName, String chNum, String signal) {
        if (this.mLiveflow != null) {
            setmProgramName(pName);
            this.mPlayCallback.playPause();
            this.mPlayCallback.stopPlayback();
            LogInfo.log("clf", "changeLunboChannel loading");
            loading(false);
            if (UIsUtils.isLandscape(this.mContext)) {
                if (this.mFullController != null) {
                    this.mFullController.show();
                }
            } else if (this.mHalfController != null) {
                this.mHalfController.show();
            }
            this.mLiveflow.mStatisticsHelper.clear();
            this.mLiveflow.changeLunboChannel(chName, chEnName, chId, pName, chNum, signal);
            if (this.mFullController != null) {
                this.mFullController.setBtnsVisible(false);
                this.mFullController.showOverLoadStatus(false);
                this.mFullController.changeCurrentChannel(chEnName, chId);
            }
            if (this.mHalfTabContrller != null) {
                this.mHalfTabContrller.refreshLivePlayData();
            }
            initFavouriteStatus();
            updateRedPacketFrom();
        }
    }

    public void setChannelName(String channelName) {
        if (this.mLiveflow != null) {
            this.mLiveflow.setChannelName(channelName);
        }
    }

    public void setChannelId(String channelId) {
        if (this.mLiveflow != null) {
            this.mLiveflow.setChannelId(channelId);
        }
    }

    public void setCode(String code) {
        if (this.mLiveflow != null) {
            this.mLiveflow.setCode(code);
        }
    }

    public void setChannelNum(String channelNum) {
        if (this.mLiveflow != null) {
            this.mLiveflow.setChannelNum(channelNum);
        }
    }

    public void showPayLoading() {
        if (this.mLoadLayout != null && this.mLivePayLayout != null) {
            loading(false);
            this.mLivePayLayout.setVisibility(4);
        }
    }

    public void hidePayLoading() {
        if (this.mLoadLayout != null && this.mLivePayLayout != null) {
            this.mLoadLayout.finish();
            this.mLivePayLayout.setVisibility(0);
        }
    }

    public void showPayLayout(int state) {
        if (this.mLivePayLayout != null) {
            this.mLivePayLayout.showLayout(state);
        }
    }

    public void removeLivePayLayout() {
        if (this.mLivePayLayout != null) {
            getPlayUper().removeView(this.mLivePayLayout);
            this.mLivePayLayout = null;
        }
    }

    public void showPayPrice(LivePriceBean result) {
        if (this.mLivePayLayout != null) {
            this.mLivePayLayout.showPrice(result);
        }
    }

    public int getCurrentPlayPosition() {
        return this.mPlayCallback.getCurrentPlayPosition();
    }

    public void jumpToVipProducts(long aid, long vid) {
        int requestCode;
        if (PreferencesManager.getInstance().isLogin()) {
            requestCode = 18;
        } else {
            requestCode = 19;
        }
        LetvVipActivity.launch(getActivity(), getActivity().getResources().getString(2131100645), requestCode);
    }

    public void bookLiveProgram(String programName, String channelName, String code, String play_time, String id) {
        LetvLiveBookUtil.bookLiveProgram(this.mContext, programName, channelName, code, play_time, id);
    }

    public void buyWo3G() {
        LetvWoFlowActivity.launchOrderActivity(this.mContext);
    }

    public void stopPlayback() {
        this.mPlayCallback.stopPlayback();
    }

    public static void setWebViewCallBack(WebViewCallBack callBack) {
        mCallBack = callBack;
    }

    public static void clearWebViewCallBack() {
        mCallBack = null;
    }

    private void registerDlnaProtocol() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_DLNA_LIVE_PROTOCOL, new 5(this, new 4(this))));
    }
}
