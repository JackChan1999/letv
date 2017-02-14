package com.letv.android.client.album.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumPlayGeneralTrailController;
import com.letv.android.client.album.dlnacontroller.DLNAViewController;
import com.letv.android.client.album.dlnacontroller.IViewController;
import com.letv.android.client.album.dlnacontroller.NormalViewController;
import com.letv.android.client.album.listener.VideoControllerMeditor$VideoToControllerListener;
import com.letv.android.client.album.observable.AlbumGestureObservable;
import com.letv.android.client.album.observable.AlbumGestureObservable.ProgressRegulateNotify;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvVipActivityConfig;
import com.letv.android.client.commonlib.messagemodel.DLNAProtocol;
import com.letv.android.client.commonlib.messagemodel.ShareFloatProtocol;
import com.letv.android.client.commonlib.view.LetvSeekBar;
import com.letv.android.client.commonlib.view.ScrollTextView;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.AlbumPlayFlowObservable.VideoTitleNotify;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.core.BaseApplication;
import com.letv.core.audiotrack.AudioTrackManager;
import com.letv.core.bean.PlayRecord;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.subtitle.manager.SubtitleInfoManager;
import com.letv.core.subtitle.manager.SubtitleRenderManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NavigationBarController;
import com.letv.core.utils.NavigationBarController.SystemUIListener;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import java.util.Observable;
import java.util.Observer;
import rx.subscriptions.CompositeSubscription;

public abstract class AlbumBaseControllerFragment implements VideoControllerMeditor$VideoToControllerListener, Observer, OnClickListener {
    private static final int HIDE_BREATH_DELAY = 10000;
    private static final int HIDE_DELAY = 5000;
    protected static final int MSG_BREATH_END = 258;
    protected static final int MSG_BREATH_START = 257;
    protected static final int MSG_CHANGE_STREAM = 260;
    protected static final int MSG_WAIT_HIDE = 256;
    public static final int STREAM_1080_TIP_DURATION = 5000;
    public static final int SWITCHING_FINISH_TIP_DURATION = 1500;
    public static boolean mIsSwitch = false;
    public static boolean mIsSwitchAudioTrack = false;
    public static boolean mIsSwitchSubtitle = false;
    public static boolean sSwitchToStream1080p = false;
    protected AlbumPlayActivity mActivity;
    protected ImageView mBackView;
    protected View mBottomFrame;
    protected View mContentView;
    public IViewController mController;
    public DLNAProtocol mDlnaProtocol;
    private View mFlatingContainerView;
    protected int mFullSeekBarWidth;
    protected RelativeLayout mGuideRelativeActionLayout;
    protected RelativeLayout mGuideRelativeLayout;
    protected int mHalfSeekBarWidth;
    protected Handler mHandler = new 2(this);
    protected boolean mIsClickVideoShotButton = false;
    protected boolean mIsLocked;
    protected boolean mIsPlayNext;
    private boolean mIsProgress;
    protected boolean mIsVideoShotting = false;
    public long mLastProgressTime;
    protected int mLaunchMode;
    private NavigationBarController mNavigationBarController;
    public ImageView mPlayImageView;
    protected boolean mPlayVideoSuccess;
    protected View mRightCenterView;
    protected ImageView mScreenOrientationLock;
    public LetvSeekBar mSeekBar;
    protected ShareFloatProtocol mShareProtocol;
    public boolean mShouldDoChangeStreamWhenPlayed;
    protected TextView mStream1080pByVipButton;
    protected TextView mStream1080pLoginButton;
    protected TextView mStream1080pLoginTipView;
    protected View mStream1080pTipView;
    protected Button mStreamCancelView;
    protected TextView mStreamTipStreamTextView;
    protected CompositeSubscription mSubscription;
    protected TextView mSwitchingTipTextView;
    protected View mSwitchingTipView;
    protected View mTitleDot;
    protected ScrollTextView mTitleTextView;
    protected View mTopRadioGroup;
    protected View mTopRightView;
    protected View mTrailerDot;
    protected long mUserClickBackStartTime;
    protected String mUserClickBackTime;
    protected ImageView mVideoShot;
    protected TextView mVideoShotButton;
    protected ImageView mVideoShotGuideArrow;
    protected String mWaitingSwitchStreamName = "";
    private SystemUIListener onSystemUIListener = new 1(this);

    public AlbumBaseControllerFragment(Context context, View view) {
        this.mActivity = (AlbumPlayActivity) context;
        this.mNavigationBarController = new NavigationBarController(this.mActivity);
        this.mContentView = view;
        this.mController = new NormalViewController(this.mActivity, this);
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(400));
        if (LeResponseMessage.checkResponseMessageValidity(response, DLNAProtocol.class)) {
            this.mDlnaProtocol = (DLNAProtocol) response.getData();
        }
    }

    protected void initView() {
        this.mFlatingContainerView = this.mActivity.findViewById(R.id.layout_floating_container);
        this.mSwitchingTipView = this.mActivity.findViewById(R.id.change_stream_tip_frame);
        this.mSwitchingTipTextView = (TextView) this.mActivity.findViewById(R.id.change_stream_tip_text);
        this.mStreamTipStreamTextView = (TextView) this.mActivity.findViewById(R.id.change_stream_tip_stream);
        this.mStreamCancelView = (Button) this.mActivity.findViewById(R.id.change_stream_tip_cancel);
        this.mBackView = (ImageView) this.mActivity.getViewById(R.id.player_half_controller_back_forver);
        this.mStream1080pTipView = this.mActivity.findViewById(R.id.stream_1080_tip_frame);
        this.mStream1080pByVipButton = (TextView) this.mActivity.findViewById(R.id.stream_1080_tip_buy_vip_button);
        this.mStream1080pLoginButton = (TextView) this.mActivity.findViewById(R.id.stream_1080_tip_login);
        this.mStream1080pLoginTipView = (TextView) this.mActivity.findViewById(R.id.stream_1080_tip_buy_vip_text);
        this.mSeekBar.setVisibility(8);
        initLock();
        this.mScreenOrientationLock.setOnClickListener(this);
        this.mPlayImageView.setOnClickListener(this);
        this.mStreamCancelView.setOnClickListener(this);
        this.mStream1080pByVipButton.setOnClickListener(this);
        this.mStream1080pLoginButton.setOnClickListener(this);
    }

    private void initLock() {
        if (this.mActivity.getController() != null) {
            this.mIsLocked = this.mActivity.getController().isLock();
            this.mScreenOrientationLock.setImageResource(this.mIsLocked ? R.drawable.btn_play_lockscreen_selector : R.drawable.btn_play_unlockscreen_selector);
        }
    }

    public void initController() {
        if (this.mActivity.mIsPlayingDlna) {
            this.mController = new DLNAViewController(this);
            if (this.mActivity.getGestureController() != null) {
                this.mActivity.getGestureController().setGestureUseful(false);
            }
        } else {
            this.mController = new NormalViewController(this.mActivity, this);
            if (this.mActivity.getGestureController() != null) {
                this.mActivity.getGestureController().setGestureUseful(true);
            }
        }
        delayHide();
        if (this.mActivity.mIsLandspace) {
            changeToFull();
        } else {
            changeToHalf(true);
        }
    }

    protected void startBreath() {
        if (this.mVideoShotButton != null) {
            Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setInterpolator(new LinearInterpolator());
            alphaAnimation.setRepeatCount(-1);
            alphaAnimation.setRepeatMode(2);
            this.mVideoShotButton.startAnimation(alphaAnimation);
            this.mHandler.sendEmptyMessageDelayed(258, 10000);
        }
    }

    protected void stopBreath() {
        if (this.mVideoShotButton != null) {
            this.mVideoShotButton.clearAnimation();
        }
    }

    protected void updateSkipState(int seekbarW) {
        if (this.mActivity.getFlow() != null && !this.mActivity.mIsPlayingNonCopyright) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            PlayRecord record = flow.mPlayRecord;
            AlbumPlayInfo playInfo = flow.mPlayInfo;
            long beginTime = flow.mPlayInfo.beginTime;
            long endTime = flow.mPlayInfo.endTime;
            if (seekbarW == 0) {
                seekbarW = this.mSeekBar.getWidth();
                if (this.mActivity.mIsLandspace) {
                    this.mFullSeekBarWidth = seekbarW;
                } else {
                    this.mHalfSeekBarWidth = seekbarW;
                }
            }
            if (!PreferencesManager.getInstance().isSkip() || record == null) {
                this.mTitleDot.setVisibility(8);
                this.mTrailerDot.setVisibility(8);
                return;
            }
            LayoutParams params;
            if (beginTime > 0) {
                params = (LayoutParams) this.mTitleDot.getLayoutParams();
                params.leftMargin = (int) ((((double) (((long) seekbarW) * beginTime)) * 1.0d) / ((double) (record.totalDuration > 0 ? record.totalDuration : playInfo.videoTotalTime / 1000)));
                this.mTitleDot.setLayoutParams(params);
                this.mTitleDot.setVisibility(0);
            } else {
                this.mTitleDot.setVisibility(8);
            }
            if (endTime > 0) {
                params = (LayoutParams) this.mTrailerDot.getLayoutParams();
                params.rightMargin = seekbarW - ((int) ((((double) (((long) seekbarW) * endTime)) * 1.0d) / ((double) (record.totalDuration > 0 ? record.totalDuration : playInfo.videoTotalTime / 1000))));
                this.mTrailerDot.setLayoutParams(params);
                this.mTrailerDot.setVisibility(0);
                return;
            }
            this.mTrailerDot.setVisibility(8);
        }
    }

    public void update(Observable observable, Object data) {
        if (!isPlayObservableNotice(data) && !isGestureObservableNotice(data)) {
            isFlowObservableNotice(data);
        }
    }

    private boolean isPlayObservableNotice(Object data) {
        if (data instanceof String) {
            if (TextUtils.equals(ScreenObservable.ON_CONFIG_CHANGE, (String) data)) {
                if (!isVisible()) {
                    if (this.mActivity.getVipTrailListener() != null) {
                        this.mActivity.getVipTrailListener().setVisibileWithController(true);
                    }
                    if (this.mActivity.mIsLandspace || isPlayingCombineAd()) {
                        this.mBackView.setVisibility(8);
                    } else {
                        this.mBackView.setVisibility(0);
                    }
                }
                if (!BaseApplication.getInstance().hasNavigationBar()) {
                    return true;
                }
                if (UIsUtils.isLandscape(this.mActivity)) {
                    this.mNavigationBarController.fireLandscapeSystemUIListener("rx_bus_album_action_update_system_ui", this.onSystemUIListener);
                    this.mFlatingContainerView.setPadding(0, 0, BaseApplication.getInstance().getNavigationBarLandscapeWidth(), 0);
                    return true;
                }
                this.mNavigationBarController.resetAndRemoveListener();
                this.mContentView.setPadding(0, 0, 0, 0);
                this.mFlatingContainerView.setPadding(0, 0, 0, 0);
                return true;
            }
        }
        return false;
    }

    private boolean isGestureObservableNotice(Object data) {
        if (!(data instanceof String)) {
            return false;
        }
        if (!TextUtils.equals(AlbumGestureObservable.ON_TOUCH_EVENT_UP, (String) data)) {
            return false;
        }
        delayHide();
        if (this.mIsProgress) {
            this.mIsProgress = false;
            closePauseAd();
            this.mController.onStopTrackingTouch(this.mSeekBar.getSeekBar());
            this.mActivity.getAlbumPlayFragment().startHandlerTime();
            if (this.mActivity.getController() != null) {
                this.mActivity.getController().start();
            }
            start(false);
        }
        return true;
    }

    private boolean isFlowObservableNotice(Object data) {
        if (data instanceof VideoTitleNotify) {
            setTitle(((VideoTitleNotify) data).title);
            return true;
        }
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(AlbumPlayFlowObservable.ON_START_FETCHING, notify)) {
                if (this.mActivity.getFlow() != null && (!this.mActivity.getFlow().isUseDoublePlayerAndChangeStream() || this.mIsPlayNext)) {
                    this.mPlayVideoSuccess = false;
                    this.mSeekBar.setBeginTime(0);
                    if (!this.mActivity.mIsPlayingDlna) {
                        this.mSeekBar.setVisibility(8);
                        this.mPlayImageView.setVisibility(8);
                    }
                    setVisibileForBottomFrame(false);
                    if (this.mTopRadioGroup != null) {
                        this.mTopRadioGroup.setVisibility(8);
                    }
                    if (this.mTopRightView != null) {
                        this.mTopRightView.setVisibility(8);
                    }
                }
                if ((mIsSwitchAudioTrack && mIsSwitchSubtitle) || this.mActivity.getFlow().isUseDoublePlayerAndChangeStream()) {
                    return true;
                }
                setVisibileForBottomFrame(false);
                return true;
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_CONTROLLER_DISABLE, notify)) {
                disable();
                return true;
            }
        }
        return false;
    }

    public void updateProgressRegulate(ProgressRegulateNotify data) {
        if (!this.mActivity.mIsPlayingDlna) {
            this.mSeekBar.setProgress(data.curPos / 1000);
            this.mSeekBar.setMax(data.total / 1000);
            removeHideHandler();
            if (!this.mIsProgress) {
                this.mController.onStartTrackingTouch(this.mSeekBar.getSeekBar());
            }
            this.mIsProgress = true;
            this.mPlayImageView.setImageResource(data.forward ? R.drawable.kuaijin_normal : R.drawable.kuaitui_normal);
            this.mActivity.getAlbumPlayFragment().mIsSeekByUser = true;
        }
    }

    public void setTitle(String title) {
        if (this.mActivity.getFlow() == null) {
            return;
        }
        if (this.mActivity.getFlow().mIsDownloadFile) {
            this.mTitleTextView.setData(this.mActivity.getString(R.string.player_local_tip_playerlibs) + title);
        } else if (this.mActivity.mLaunchMode != 1 || this.mActivity.mIs4dVideo) {
            this.mTitleTextView.setData(title);
        } else {
            this.mTitleTextView.setData(this.mActivity.getString(R.string.player_local_default_tip) + title);
        }
    }

    public void onClick(View v) {
        if (v == this.mScreenOrientationLock) {
            clickLockBar();
        } else if (v == this.mPlayImageView) {
            clickPauseOrPlay();
        } else if (v == this.mStreamCancelView) {
            this.mSwitchingTipView.setVisibility(8);
        } else if (v == this.mStream1080pByVipButton && this.mActivity.getFlow() != null) {
            sSwitchToStream1080p = true;
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvVipActivityConfig(this.mActivity).create(this.mActivity.getString(R.string.pim_vip_recharge))));
        } else if (v == this.mStream1080pLoginButton) {
            sSwitchToStream1080p = true;
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this.mActivity).create(1, 10001)));
        }
    }

    public void setControllerVisibility(int visibility, boolean isForce) {
        removeHideHandler();
        if (visibility == 0 && !isPlayingCombineAd()) {
            setVisibility(0);
            delayHide();
        } else if (isForce) {
            setVisibility(8);
        } else {
            delayHide();
        }
    }

    protected void doVisibilityChangeAnim(boolean show) {
        int i = 8;
        if (isPlayingCombineAd() || !(show || isVisible())) {
            setControllerVisibility(8, true);
        } else if (this.mActivity.mIsPlayingNonCopyright) {
            if (show) {
                i = 0;
            }
            setControllerVisibility(i, true);
        } else {
            this.mContentView.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this.mActivity, show ? R.anim.fade_in : R.anim.alpha_out);
            animation.setAnimationListener(new 3(this, show));
            this.mContentView.startAnimation(animation);
        }
    }

    public void initProcess(int max, int progress, int buffer) {
        this.mPlayVideoSuccess = true;
        this.mIsPlayNext = false;
        this.mSeekBar.setVisibility(0);
        setVisibileForBottomFrame(true);
        this.mPlayImageView.setVisibility(0);
        if (this.mActivity.mIsLandspace && !this.mActivity.mIsPlayingDlna) {
            if (this.mTopRadioGroup != null) {
                this.mTopRadioGroup.setVisibility(0);
            }
            if (this.mTopRightView != null) {
                this.mTopRightView.setVisibility(0);
            }
            if (this.mRightCenterView != null) {
                this.mRightCenterView.setVisibility(0);
            }
        }
        this.mSeekBar.setEnable(true);
        this.mSeekBar.setMax(max);
        this.mSeekBar.setEndTime((long) (max * 1000));
        this.mSeekBar.setProgress(progress);
        this.mSeekBar.setSecondaryProgress(buffer);
        this.mSeekBar.initBeginTextView();
        this.mHandler.post(new 4(this));
        initSeekBarListener();
        if (this.mActivity.getGestureController() != null) {
            this.mActivity.getGestureController().enableGestureUseful();
        }
        if (this.mActivity.getRecommendController() != null) {
            this.mActivity.getRecommendController().reset();
        }
        setControllerVisibility(0, true);
    }

    public void setTotalTime(int time) {
        this.mSeekBar.setEnable(true);
        if (TextUtils.equals("" + time, this.mSeekBar.getTotalTimes())) {
            this.mSeekBar.setEndTime((long) time);
            this.mSeekBar.setBeginTime(0);
            this.mSeekBar.hideOrShowBeginTime(true);
        }
    }

    public void updateProgress(int progress, int buffer) {
        if (!isVisible()) {
            return;
        }
        if (buffer < 0) {
            this.mSeekBar.setProgress(progress);
            return;
        }
        this.mSeekBar.setChangeShow(false);
        this.mSeekBar.setProgress(progress);
        this.mSeekBar.setSecondaryProgress(buffer);
    }

    public void start(boolean showController) {
        this.mController.start(showController);
        if (this.mActivity.mIsPlayingDlna) {
            this.mActivity.coverBlackOnVideoView(false);
        }
    }

    public void pause() {
        this.mController.pause();
    }

    public void setEnable(boolean enabled) {
        this.mSeekBar.setEnable(enabled);
        this.mPlayImageView.setEnabled(enabled);
    }

    public void setEnableSeek(boolean enable) {
        this.mHandler.post(new 5(this, enable));
    }

    public void setBlockBtnVisibile(boolean show) {
    }

    protected void setVisibileForBottomFrame(boolean show) {
        this.mBottomFrame.setVisibility(show ? 0 : 8);
    }

    public void onStreamSwitchFinish(boolean isSuccess) {
        this.mActivity.coverBlackOnVideoView(false);
        if (this.mActivity.getGestureController() != null) {
            this.mActivity.getGestureController().enableGestureUseful();
        }
        this.mHandler.removeMessages(260);
        switchStreamFinished(isSuccess);
    }

    public void onAudioTrackSwitchFinish() {
        this.mContentView.setBackgroundColor(0);
        this.mActivity.coverBlackOnVideoView(false);
        checkStreamTipMargin();
        if (this.mActivity.getGestureController() != null) {
            this.mActivity.getGestureController().enableGestureUseful();
        }
        showEndSwitchingAudioTrack();
    }

    public void playAnotherVideo(boolean isRetry) {
        if (!(isRetry && (mIsSwitch || mIsSwitchAudioTrack || mIsSwitchSubtitle))) {
            this.mSwitchingTipView.setVisibility(8);
        }
        this.mHandler.removeMessages(260);
        this.mShouldDoChangeStreamWhenPlayed = false;
        this.mIsPlayNext = true;
    }

    public void delayHide() {
        if (this.mActivity.getErrorTopController() == null || !this.mActivity.getErrorTopController().isErrorCodeVisible()) {
            removeHideHandler();
            this.mHandler.sendEmptyMessageDelayed(256, 5000);
        }
    }

    public void removeHideHandler() {
        this.mHandler.removeMessages(256);
    }

    protected void initSeekBarListener() {
        this.mSeekBar.setOnSeekBarChangeListener(new 6(this));
        this.mSeekBar.setOnSeekBarTouchListener(new 7(this));
    }

    protected void onSeekBarTouchListener(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                this.mActivity.getAlbumPlayFragment().buffTimeSchedule();
                return;
            default:
                return;
        }
    }

    protected void onSeekProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.mController.onProgressChanged(seekBar, progress, fromUser);
    }

    private void clickLockBar() {
        this.mIsLocked = !this.mIsLocked;
        this.mScreenOrientationLock.setImageResource(this.mIsLocked ? R.drawable.btn_play_lockscreen_selector : R.drawable.btn_play_unlockscreen_selector);
        if (this.mIsLocked) {
            UIsUtils.showToast(TipUtils.getTipMessage(DialogMsgConstantId.PLAY_OPERATION_LOCK, R.string.play_operation_lock));
        } else {
            UIsUtils.showToast(TipUtils.getTipMessage(DialogMsgConstantId.PLAY_OPERATION_UNLOCK, R.string.play_operation_unlock));
        }
        if (this.mActivity.getController() != null) {
            this.mActivity.getController().setLock(this.mIsLocked);
        }
        delayHide();
        if (UIsUtils.isLandscape(this.mActivity)) {
            if (this.mIsLocked) {
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c68", "1014", 1, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            } else {
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c68", "1015", 2, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
            }
        } else if (this.mIsLocked) {
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c70", null, 1, null, PageIdConstant.halpPlayPage, null, null, null, null, null);
        } else {
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c70", null, 2, null, PageIdConstant.halpPlayPage, null, null, null, null, null);
        }
    }

    protected void clickPauseOrPlay() {
        if (this.mActivity.getFlow() != null && this.mActivity.getFlow().isUseDoublePlayerAndChangeStream() && this.mActivity.getAlbumPlayFragment().isPlaying() && this.mActivity.getAlbumPlayFragment().mForegroundVideoView != null) {
            LogInfo.log("zhuqiao", "切换过程中点击了暂停，停止后台播放器流程");
            this.mActivity.getFlow().clearRequest();
            this.mActivity.getAlbumPlayFragment().stopBackgroundVideoView();
            this.mActivity.getAlbumPlayFragment().mForegroundVideoView.mShouldChangeStreamWhenPlayed = true;
        }
        closePauseAd();
        this.mController.clickPauseOrPlay();
    }

    protected void closePauseAd() {
        if (this.mActivity.getFlow() != null && this.mActivity.getPlayAdListener() != null && this.mActivity.getFlow().mIsPauseAdIsShow) {
            this.mActivity.getPlayAdListener().closePauseAd();
        }
    }

    public void showStreamTip() {
        if (mIsSwitchSubtitle) {
            mIsSwitch = true;
        } else if (this.mActivity.getFlow().enableDoublePlayer()) {
            showStreamTipDoublePlayer();
        } else {
            this.mActivity.coverBlackOnVideoView(true);
            checkStreamTipMargin();
            mIsSwitch = true;
            this.mActivity.getViewById(R.id.change_stream_name).setVisibility(8);
            this.mSwitchingTipView.setVisibility(0);
            this.mSwitchingTipTextView.setVisibility(0);
            this.mStreamTipStreamTextView.setVisibility(8);
            this.mStreamCancelView.setVisibility(0);
            this.mStream1080pTipView.setVisibility(8);
            this.mSwitchingTipTextView.setText(TipUtils.getTipMessage(DialogMsgConstantId.SWITCH_STREAM, R.string.switch_hd_start));
        }
    }

    public void showStreamTipDoublePlayer() {
        checkStreamTipMargin();
        mIsSwitch = true;
        TextView streamNameView = (TextView) this.mActivity.getViewById(R.id.change_stream_name);
        streamNameView.setVisibility(0);
        streamNameView.setText(this.mWaitingSwitchStreamName);
        this.mSwitchingTipView.setVisibility(0);
        this.mSwitchingTipTextView.setVisibility(0);
        this.mStreamTipStreamTextView.setVisibility(0);
        this.mStreamTipStreamTextView.setTextColor(-1);
        this.mStreamCancelView.setVisibility(8);
        this.mStream1080pTipView.setVisibility(8);
        this.mSwitchingTipTextView.setText(String.format(TipUtils.getTipMessage("100114", R.string.switch_hd_start_double_player), new Object[]{""}));
        this.mHandler.sendEmptyMessage(260);
    }

    public void showBeginSwitchingAudioTrack() {
        this.mActivity.coverBlackOnVideoView(true);
        checkStreamTipMargin();
        mIsSwitchAudioTrack = true;
        this.mStreamCancelView.setVisibility(0);
        this.mSwitchingTipView.setVisibility(0);
        this.mSwitchingTipTextView.setVisibility(0);
        this.mStreamTipStreamTextView.setVisibility(8);
        this.mActivity.getViewById(R.id.change_stream_name).setVisibility(8);
        this.mSwitchingTipTextView.setText(TipUtils.getTipMessage("", R.string.switching_audio_track));
    }

    public void showBeginSwitchingSubtitle() {
        checkStreamTipMargin();
        mIsSwitchSubtitle = true;
        this.mStreamCancelView.setVisibility(0);
        this.mSwitchingTipView.setVisibility(0);
        this.mStreamTipStreamTextView.setVisibility(8);
        this.mSwitchingTipTextView.setVisibility(0);
        this.mActivity.getViewById(R.id.change_stream_name).setVisibility(8);
        this.mSwitchingTipTextView.setText(TipUtils.getTipMessage("", R.string.switching_subtitle));
    }

    private void showEndSwitchingAudioTrack() {
        if (mIsSwitchAudioTrack) {
            this.mStreamTipStreamTextView.setVisibility(0);
            this.mSwitchingTipTextView.setText(R.string.switching_audio_track_or_subtitle_complete);
            this.mStreamCancelView.setVisibility(8);
            AudioTrackManager mgr = AudioTrackManager.getInstance();
            this.mStreamTipStreamTextView.setText(mgr.obtainLanguageBy(mgr.getIndex()));
            new 8(this).sendEmptyMessageDelayed(1, 1500);
            mIsSwitchAudioTrack = false;
        }
    }

    public void showEndSwitchingSubtitle() {
        if (mIsSwitchSubtitle) {
            this.mHandler.removeMessages(260);
            this.mSwitchingTipTextView.setText(R.string.switching_audio_track_or_subtitle_complete);
            this.mStreamCancelView.setVisibility(8);
            String code = (String) SubtitleInfoManager.getInstance().getCodeList().get(SubtitleInfoManager.getInstance().getIndex());
            this.mStreamTipStreamTextView.setVisibility(0);
            this.mStreamTipStreamTextView.setText(SubtitleInfoManager.getInstance().code2language(code));
            mIsSwitchSubtitle = false;
            new 9(this).sendEmptyMessageDelayed(1, 1500);
            LogInfo.log("wuxinrong", "11111 restart()");
            SubtitleRenderManager.getInstance().restart();
        }
    }

    private void switchStreamFinished(boolean isSuccess) {
        if (mIsSwitch) {
            checkStreamTipMargin();
            ((TextView) this.mActivity.getViewById(R.id.change_stream_name)).setVisibility(8);
            this.mStream1080pTipView.setVisibility(8);
            this.mSwitchingTipView.setVisibility(0);
            this.mSwitchingTipTextView.setVisibility(0);
            String switchSuccess = String.format(TipUtils.getTipMessage("100115", R.string.switch_hd_end), new Object[]{""});
            TextView textView = this.mSwitchingTipTextView;
            if (!isSuccess) {
                switchSuccess = this.mActivity.getString(R.string.switch_hd_fail);
            }
            textView.setText(switchSuccess);
            this.mStreamTipStreamTextView.setTextColor(Color.parseColor("#00a0c9"));
            if (isSuccess) {
                this.mStreamTipStreamTextView.setVisibility(0);
                this.mStreamTipStreamTextView.setText(this.mWaitingSwitchStreamName);
                this.mStreamTipStreamTextView.setOnClickListener(null);
            } else {
                this.mStreamTipStreamTextView.setVisibility(8);
            }
            this.mStreamCancelView.setVisibility(8);
            new 10(this).sendEmptyMessageDelayed(1, 1500);
            mIsSwitch = false;
        }
    }

    protected boolean checkIfCanPlay1080p(boolean isClick) {
        int i = 8;
        if (this.mActivity.getFlow() == null || this.mActivity.getFlow().mCurrentPlayingVideo == null) {
            return false;
        }
        boolean isVipVideo;
        if (this.mActivity.getFlow().mCurrentPlayingVideo.pay == 1) {
            isVipVideo = true;
        } else {
            isVipVideo = false;
        }
        if (PreferencesManager.getInstance().isVip() || (isClick && isVipVideo)) {
            this.mStream1080pTipView.setVisibility(8);
            this.mActivity.getVipTrailListener().checkVipTrailIsStateEnd();
            return true;
        }
        checkStreamTipMargin();
        this.mSwitchingTipView.setVisibility(0);
        this.mSwitchingTipTextView.setVisibility(8);
        this.mStreamTipStreamTextView.setVisibility(8);
        this.mStreamCancelView.setVisibility(0);
        this.mStream1080pTipView.setVisibility(0);
        this.mStream1080pByVipButton.setVisibility(0);
        this.mStream1080pLoginButton.setVisibility(PreferencesManager.getInstance().isLogin() ? 8 : 0);
        TextView textView = this.mStream1080pLoginTipView;
        if (!PreferencesManager.getInstance().isLogin()) {
            i = 0;
        }
        textView.setVisibility(i);
        if (this.mActivity.getVipTrailListener() != null) {
            this.mActivity.getVipTrailListener().hide();
        }
        hide1080pTip();
        return isVipVideo;
    }

    protected void hide1080pTip() {
        this.mHandler.postDelayed(new 11(this), 5000);
    }

    public boolean is1080pStreamVisible() {
        return this.mStream1080pTipView.getVisibility() == 0;
    }

    protected void checkStreamTipMargin() {
        int height = 0;
        if (isVisible() && this.mActivity.getViewById(R.id.album_loading_bg).getVisibility() == 8) {
            height = UIsUtils.dipToPx(43.0f);
        }
        ((FrameLayout.LayoutParams) this.mSwitchingTipView.getLayoutParams()).bottomMargin = height;
    }

    private void disable() {
        this.mPlayImageView.setImageResource(R.drawable.btn_play_selector);
        this.mSeekBar.setProgress(0);
        this.mSeekBar.setEndTime(0);
        this.mSeekBar.setMax(0);
        this.mSeekBar.setSecondaryProgress(0);
        this.mSeekBar.setEnable(false);
        this.mTitleDot.setVisibility(8);
        this.mTrailerDot.setVisibility(8);
        removeHideHandler();
    }

    private void checkShowVideoRecommend(boolean show) {
        if (this.mActivity != null && this.mActivity.getRecommendController() != null && this.mActivity.getFlow() != null) {
            if (show) {
                AlbumPlayInfo playInfo = this.mActivity.getFlow().mPlayInfo;
                if (!this.mActivity.getFlow().mIsSkip || playInfo.endTime <= 0) {
                    this.mActivity.getRecommendController().showPlayRecommendTip(playInfo.currTime, playInfo.videoTotalTime / 1000);
                    return;
                } else {
                    this.mActivity.getRecommendController().showPlayRecommendTip(playInfo.currTime, playInfo.endTime + playInfo.midDuration);
                    return;
                }
            }
            this.mActivity.getRecommendController().hideRecommendTipView();
        }
    }

    protected boolean isPlayingCombineAd() {
        boolean isPlayingFrontCombineAd;
        if (this.mActivity.getFlow() == null || !this.mActivity.getFlow().mIsCombineAd || this.mActivity.getFlow().mIsFrontAdFinished) {
            isPlayingFrontCombineAd = false;
        } else {
            isPlayingFrontCombineAd = true;
        }
        boolean isPlayingMidCombineAd;
        if (this.mActivity.getFlow() == null || !this.mActivity.getFlow().mIsCombineAd || this.mActivity.getFlow().mIsMidAdFinished) {
            isPlayingMidCombineAd = false;
        } else {
            isPlayingMidCombineAd = true;
        }
        if (isPlayingFrontCombineAd || isPlayingMidCombineAd) {
            return true;
        }
        return false;
    }

    public void setVisibility(int visibility) {
        boolean z = true;
        if (isPlayingCombineAd()) {
            this.mContentView.setVisibility(4);
            onControlPanelVisible(false);
            return;
        }
        boolean hidden;
        int i;
        boolean z2;
        if (visibility != 0) {
            hidden = true;
        } else {
            hidden = false;
        }
        if (hidden) {
            ImageView imageView = this.mBackView;
            if (this.mActivity.mIsLandspace) {
                i = 8;
            } else {
                i = 0;
            }
            imageView.setVisibility(i);
            this.mBackView.setImageResource(R.drawable.back_forever_press_selecter);
        } else {
            this.mBackView.setVisibility(0);
            if (this.mActivity.mIsPlayingNonCopyright) {
                this.mBackView.setImageResource(R.drawable.noncopyright_back_selecter);
            } else {
                this.mBackView.setImageResource(R.drawable.back_white_selecter);
            }
        }
        if (!(hidden || this.mActivity.mIsPlayingDlna || this.mActivity.mPlayingHandler == null || this.mActivity.mPlayingHandler.mHandler == null)) {
            this.mActivity.mPlayingHandler.mHandler.sendEmptyMessage(0);
        }
        View view = this.mContentView;
        if (hidden) {
            i = 4;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        if (hidden) {
            z2 = false;
        } else {
            z2 = true;
        }
        onControlPanelVisible(z2);
        if (visibility == 8) {
            if (this.mShareProtocol != null) {
                this.mShareProtocol.hide();
            }
            this.mGuideRelativeLayout.setVisibility(4);
        }
        this.mUserClickBackStartTime = 0;
        this.mUserClickBackTime = "";
        initLock();
        checkStreamTipMargin();
        if (this.mActivity.getVipTrailListener() != null) {
            AlbumPlayGeneralTrailController vipTrailListener = this.mActivity.getVipTrailListener();
            if (hidden) {
                z = false;
            }
            vipTrailListener.setIsControllerVisible(z);
            this.mActivity.getVipTrailListener().setVisibileWithController(hidden);
        }
        checkShowVideoRecommend(hidden);
        RxBus.getInstance().send("rx_bus_album_action_update_system_ui");
    }

    public boolean isVisible() {
        return this.mContentView.getVisibility() == 0;
    }

    private void onControlPanelVisible(boolean visible) {
        if (this.mActivity.getPlayAdListener() != null && this.mActivity.getPlayAdListener().getAdFragment() != null && this.mActivity.getPlayAdListener().getAdFragment().getIVideoStatusInformer() != null) {
            this.mActivity.getPlayAdListener().getAdFragment().getIVideoStatusInformer().onControlPanelVisible(visible);
        }
    }

    public void hideBackForeverView() {
        if (this.mBackView.getVisibility() != 8) {
            this.mBackView.setVisibility(8);
        }
    }

    public void showBackForeverView() {
        if (this.mBackView.getVisibility() != 0 && !this.mActivity.mIsLandspace) {
            this.mBackView.setVisibility(0);
        }
    }

    protected void changeToHalf(boolean isDirectionChange) {
    }

    protected void changeToFull() {
    }

    public void onFinish() {
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    public void onDestroy() {
        this.mTitleTextView.cancel();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
        this.mNavigationBarController.resetAndRemoveListener();
    }

    public String getTitle() {
        return this.mTitleTextView.getText().toString();
    }

    public boolean isSwitchingTipViewShowing() {
        return this.mSwitchingTipView.getVisibility() == 0;
    }

    public String getUserClickBackTime() {
        return this.mUserClickBackTime;
    }

    public long getUserClickBackStartTime() {
        return this.mUserClickBackStartTime;
    }
}
