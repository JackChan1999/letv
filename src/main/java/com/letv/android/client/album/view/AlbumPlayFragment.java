package com.letv.android.client.album.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumPlayingHandler;
import com.letv.android.client.album.controller.AlbumWaterMarkController;
import com.letv.android.client.album.listener.VideoControllerMeditor$ControllerToVideoListener;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.android.client.album.service.SimplePluginDownloadService;
import com.letv.android.client.album.utils.AlbumVideoViewBuilder;
import com.letv.android.client.album.utils.BackgroundVideoView;
import com.letv.android.client.album.utils.ForegroundVideoView;
import com.letv.android.client.commonlib.config.LetvWoFlowActivityConfig;
import com.letv.android.client.commonlib.messagemodel.LetvPlayRecordConfig.PlayRecordFetch;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.AlbumPlayFlowObservable.LocalVideoSubtitlesPath;
import com.letv.business.flow.album.PlayObservable;
import com.letv.business.flow.album.listener.PlayVideoFragmentListener;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.cde.helper.CdeStateHelper;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.core.BaseApplication;
import com.letv.core.bean.PlayRecord;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.subtitle.manager.SubtitleInfoManager;
import com.letv.core.subtitle.manager.SubtitleRenderManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.plugin.pluginloader.common.Constant;
import com.letv.pp.func.CdeHelper;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class AlbumPlayFragment implements Observer, VideoControllerMeditor$ControllerToVideoListener, PlayVideoFragmentListener {
    public static final int ON_ACTIVITY_EXIT = 9;
    public static final int ON_ACTIVITY_PAUSE = 7;
    public static final int ON_ACTIVITY_RESUME = 8;
    public static final int ON_VIDEO_COMPLATE = 4;
    private static final int ON_VIDEO_ERROR = 5;
    private static final int ON_VIDEO_PAUSE = 2;
    public static final int ON_VIDEO_RESUME = 3;
    private static final int ON_VIDEO_SIZE = 6;
    public static final int ON_VIDEO_START = 1;
    private AlbumPlayActivity mActivity;
    public AlbumVideoViewBuilder mBackgroundBuild;
    private RelativeLayout mBackgroundPlayerFrame;
    public BackgroundVideoView mBackgroundVideoView;
    private CompositeSubscription mBackgroundVideoViewSubscription;
    private CdeStateHelper mCdeStateHelper;
    private RelativeLayout mContentView;
    public boolean mEnforcementPause = false;
    public AlbumVideoViewBuilder mForegroundBuild;
    private RelativeLayout mForegroundPlayerFrame;
    public ForegroundVideoView mForegroundVideoView;
    private Handler mHandler = new Handler();
    private boolean mHasCallAdsStart = false;
    public boolean mIsResume = false;
    public boolean mIsSeekByUser;
    private boolean mLocalVideo = false;
    private String mLocalVideoSubtitlePath = null;
    public AlbumPlayingHandler mPlayingHandler;
    private Rect mRect;
    private RelativeLayout mSubtitleCavasLayer;
    public SubtitleRenderManager mSubtitleRenderManager = SubtitleRenderManager.getInstance();
    public AlbumWaterMarkController mWaterMarkController;
    public ImageView mWaterMarkImageView;

    public AlbumPlayFragment(AlbumPlayActivity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        this.mContentView = (RelativeLayout) this.mActivity.getViewById(R.id.play_album_root);
        this.mSubtitleCavasLayer = (RelativeLayout) this.mActivity.getViewById(R.id.layer_album_subtitle_canvas);
        this.mWaterMarkImageView = (ImageView) this.mActivity.getViewById(R.id.waterMark);
        this.mWaterMarkController = new AlbumWaterMarkController(this.mActivity, this, this.mWaterMarkImageView, this.mSubtitleCavasLayer);
        AlbumPlayActivity albumPlayActivity = this.mActivity;
        AlbumPlayingHandler albumPlayingHandler = new AlbumPlayingHandler(this.mActivity, this);
        this.mPlayingHandler = albumPlayingHandler;
        albumPlayActivity.mPlayingHandler = albumPlayingHandler;
        this.mForegroundVideoView = new ForegroundVideoView(this.mActivity, this);
        registerSubscriptions();
    }

    private void registerSubscriptions() {
        if (this.mBackgroundVideoViewSubscription == null) {
            this.mBackgroundVideoViewSubscription = new CompositeSubscription();
        } else {
            this.mBackgroundVideoViewSubscription.unsubscribe();
        }
        this.mBackgroundVideoViewSubscription.add(RxBus.getInstance().toObserverable().observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribe(new 1(this)));
    }

    private void createVideoViewFrame(boolean isForegroundFrame) {
        RelativeLayout currView = isForegroundFrame ? this.mForegroundPlayerFrame : this.mBackgroundPlayerFrame;
        if (currView != null) {
            currView.removeAllViews();
            this.mContentView.removeView(currView);
        }
        currView = new RelativeLayout(this.mActivity);
        currView.setGravity(17);
        if (!isForegroundFrame) {
            currView.setVisibility(4);
        }
        this.mContentView.addView(currView, isForegroundFrame ? 0 : 1, new LayoutParams(-1, -1));
        if (isForegroundFrame) {
            this.mForegroundPlayerFrame = currView;
        } else {
            this.mBackgroundPlayerFrame = currView;
        }
        LogInfo.log("zhuqiao", "contentview child count:" + this.mContentView.getChildCount());
    }

    public void onResume() {
        if (LetvUtils.reflectScreenState()) {
            resume();
        }
    }

    public void onPause() {
        pause();
        this.mForegroundVideoView.onPause();
    }

    public void addForegroundVideoView(View view) {
        if (view != null) {
            this.mForegroundPlayerFrame.removeAllViews();
            this.mForegroundPlayerFrame.addView(view, new LayoutParams(-2, -2));
        }
    }

    public void addBackgroundVideoView(View view) {
        if (view != null) {
            this.mBackgroundPlayerFrame.removeAllViews();
            this.mBackgroundPlayerFrame.addView(view, new LayoutParams(-2, -2));
        }
    }

    private void resume() {
        if (this.mActivity.getController().onResume()) {
            LogInfo.log("zhuqiao", "albumcontroller resume");
            this.mIsResume = true;
            return;
        }
        LogInfo.log("zhuqiao", "albumplayfragment resume");
        resumeCde();
        if (this.mActivity.getFlow() != null && !this.mActivity.mIsPlayingDlna) {
            if (this.mActivity.getVipTrailListener() == null || !this.mActivity.getVipTrailListener().isTryLookPause()) {
                AlbumPlayFlow flow = this.mActivity.getFlow();
                LogInfo.log("zhuqiao", "mIsCombineAd=" + flow.mIsCombineAd + ";mIsSeparateAdFinished=" + flow.mIsSeparateAdFinished);
                if (!flow.mIsCombineAd && !flow.mIsSeparateAdFinished) {
                    flow.mPlayInfo.mIsPlayingAds = true;
                } else if (!flow.isLebox() && !NetworkUtils.isNetworkAvailable() && flow.mIsFrontAdFinished && !flow.isLocalFile()) {
                    flow.showNoNet();
                } else if (!this.mActivity.mShouldWaitDrmPluginInstall && flow.mIsStarted) {
                    this.mActivity.getLoadListener().loading();
                    this.mPlayingHandler.setStartComputeBlock(false);
                    this.mForegroundVideoView.onResume();
                    if (this.mBackgroundVideoView != null) {
                        this.mBackgroundVideoView.onResume();
                    }
                    if (flow.mPlayInfo.mVideoLoadConsumeTime == 0) {
                        flow.mPlayInfo.mVideoLoadConsumeTime = System.currentTimeMillis();
                        LogInfo.log("jc666", "resume 开始加载视频(此时无广告或者广告已经播完)");
                    }
                    if (this.mActivity.getLongWatchController() != null) {
                        this.mActivity.getLongWatchController().reCalcTime();
                    }
                    if (this.mActivity.getLoadListener() != null) {
                        this.mActivity.getLoadListener().dismissDialog();
                    }
                    callAdsPlayInterface(3);
                }
            }
        }
    }

    private void setSubtitleOwner() {
        int owner = 0;
        if (UIsUtils.isLandscape(this.mActivity)) {
            owner = 1;
        }
        SubtitleRenderManager.getInstance().setOwner(owner);
    }

    public void update(Observable observable, Object data) {
        if (data instanceof LocalVideoSubtitlesPath) {
            LogInfo.log("zhaosumin", "播放本地视频的字幕的地址是 url == " + ((LocalVideoSubtitlesPath) data).path);
            this.mLocalVideoSubtitlePath = ((LocalVideoSubtitlesPath) data).path;
            this.mLocalVideo = true;
        } else if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(ScreenObservable.ON_CONFIG_CHANGE, notify)) {
                onDirectionChange();
                this.mRect = new Rect();
                this.mHandler.postDelayed(new 2(this), 1000);
                setSubtitleOwner();
            } else if (TextUtils.equals(AlbumPlayFlowObservable.ON_START_FETCHING, notify)) {
                this.mWaterMarkImageView.setVisibility(8);
            } else if (TextUtils.equals(PlayObservable.ON_CALL_STATE_RINGING, notify) || TextUtils.equals(PlayObservable.ON_CALL_STATE_OFFHOOK, notify)) {
                LogInfo.log("A8", "接收到关于通话的广播，类型 = " + notify);
                pause();
                if (this.mPlayingHandler != null) {
                    this.mPlayingHandler.onStopBack(false);
                }
                if (this.mActivity.getFlow() != null) {
                    AlbumPlayInfo albumPlayInfo = this.mActivity.getFlow().mPlayInfo;
                    albumPlayInfo.mGlsbNum++;
                }
            } else if (TextUtils.equals(PlayObservable.ON_CALL_STATE_IDLE, notify)) {
                LogInfo.log("A8", "接收到关于通话的广播，类型 = ON_CALL_STATE_IDLE");
                if (!LetvUtils.isApplicationInBackground(this.mActivity)) {
                    LogInfo.log("A8", "通话结束，继续播放");
                    start();
                }
            } else if (TextUtils.equals(PlayObservable.ON_ALARM_ALERT, notify)) {
                pause();
            }
        }
    }

    private void onDirectionChange() {
        this.mWaterMarkController.changeWaterMarkDirection();
        this.mWaterMarkImageView.getLayoutParams().width = UIsUtils.getScreenWidth() / 18;
        this.mWaterMarkImageView.getLayoutParams().height = this.mActivity.getViewById(R.id.play_album_bottom_frame).getLayoutParams().height / 18;
    }

    public void statisticsOnPlayingOrError(AlbumPlayFlow flow) {
        if (flow != null) {
            boolean needStatisticsPlay = !flow.mIsCombineAd || (flow.mIsCombineAd && flow.mPlayInfo.mAdCount == 0);
            if (!flow.mPlayInfo.mIsStatisticsPlay && needStatisticsPlay) {
                flow.updatePlayDataStatistics("play", -1);
            }
            if (!flow.mPlayInfo.mIsStatisticsLoadTime) {
                long startTime = flow.mPlayInfo.mTotalConsumeTime;
                if (startTime != 0 && !flow.mPlayInfo.mHasCollectTimeToPlay) {
                    long endTime;
                    flow.mPlayInfo.mVideoLoadConsumeTime = System.currentTimeMillis() - flow.mPlayInfo.mVideoLoadConsumeTime;
                    if (flow.mPlayInfo.mAdCount <= 0 || flow.mIsCombineAd) {
                        endTime = System.currentTimeMillis();
                    } else {
                        endTime = flow.mPlayInfo.mAdsPlayFirstFrameTime;
                    }
                    flow.mPlayInfo.mTotalConsumeTime = endTime - startTime;
                    flow.mPlayInfo.mHasCollectTimeToPlay = true;
                    LogInfo.log("jc666", "正片加载时间:" + flow.mPlayInfo.mVideoLoadConsumeTime);
                    LogInfo.log("jc666", "起播截止时间：" + flow.mPlayInfo.mTotalConsumeTime);
                }
            }
        }
    }

    public void prepareSubtitleInfo() {
        Context context;
        if (this.mLocalVideo) {
            try {
                context = BaseApplication.getInstance().getApplicationContext();
                SubtitleInfoManager.getInstance().createLocalSubtitleInfo(this.mActivity.getFlow().mLanguageSettings);
                if (this.mForegroundVideoView.mVideoView != null) {
                    this.mSubtitleRenderManager.init(context, this.mSubtitleCavasLayer, this.mForegroundVideoView.mVideoView);
                }
                this.mSubtitleRenderManager.setSubtitleSource(1);
                this.mSubtitleRenderManager.parse(this.mLocalVideoSubtitlePath);
                this.mLocalVideo = false;
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        context = BaseApplication.getInstance().getApplicationContext();
        LogInfo.log("wuxinrong", "创建字幕信息...");
        SubtitleInfoManager.getInstance().createSubtitleInfo(context, this.mActivity.getFlow().mVideoFile, this.mActivity.getFlow().mLanguageSettings);
        if (BaseTypeUtils.isListEmpty(SubtitleInfoManager.getInstance().getCodeList())) {
            LogInfo.log("wuxinrong", "没有字幕 >>> 忽略...");
            return;
        }
        LogInfo.log("wuxinrong", "有字幕 >>> 字幕绘制控制器初始化...");
        if (this.mForegroundVideoView.mVideoView != null) {
            this.mSubtitleRenderManager.init(context, this.mSubtitleCavasLayer, this.mForegroundVideoView.mVideoView);
        }
        LogInfo.log("wuxinrong", "有字幕 >>> 字幕绘制控制器解析字幕下载url...");
        this.mSubtitleRenderManager.parse(SubtitleInfoManager.getInstance().getUri());
    }

    public void savePlayRecord(AlbumPlayFlow flow, PlayRecord playRecord) {
        VideoBean playingVideo = flow.mCurrentPlayingVideo;
        if (playingVideo != null && playRecord != null && !this.mActivity.mIsPlayingNonCopyright && this.mActivity.getFlow() != null && this.mActivity.getFlow().mVideoType != VideoType.Panorama) {
            if (!(playingVideo == null || !TextUtils.equals(playingVideo.videoTypeKey, LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN) || ((playingVideo.cid != 16 && playingVideo.cid != 2 && playingVideo.cid != 5 && playingVideo.cid != 11 && playingVideo.cid != 1021) || this.mActivity.getPlayNextController().mNextVideo == null || TextUtils.equals(LetvConstant.VIDEO_TYPE_KEY_PREVIEW, this.mActivity.getPlayNextController().mNextVideo.videoTypeKey)))) {
                playRecord.videoNextId = (int) this.mActivity.getPlayNextController().mNextVideo.vid;
            }
            long playDuration = playRecord.playedDuration;
            if (!PreferencesManager.getInstance().isSkip() || this.mActivity.getFlow().mPlayInfo.endTime == 0) {
                if (playRecord.totalDuration - playRecord.playedDuration < 10) {
                    playDuration = -1;
                }
            } else if (this.mActivity.getFlow().mPlayInfo.endTime - playRecord.playedDuration < 10) {
                playDuration = -1;
            }
            playRecord.playedDuration = playDuration;
            if (!TextUtils.isEmpty(playingVideo.videoTypeKey)) {
                playRecord.videoTypeKey = playingVideo.videoTypeKey;
            }
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(111, playRecord));
        }
    }

    public LetvMediaPlayerControl getVideoView() {
        return this.mForegroundVideoView.mVideoView;
    }

    public void onSeekFinish(int progress) {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (flow != null) {
            AlbumPlayInfo playInfo = flow.mPlayInfo;
            if (((long) progress) == playInfo.videoTotalTime / 1000) {
                LogInfo.log("zhuqiao", "onseekfinish called play next");
                this.mPlayingHandler.playNext();
                return;
            }
            long seek = (long) (progress * 1000);
            if (playInfo.midDuration > 0) {
                if (flow.mIsMidAdPlayed) {
                    if (this.mActivity.getMidAdController().skipMidAd(seek)) {
                        seek = playInfo.midAdPlayTime + playInfo.midDuration;
                        LogInfo.log("zhuqiao", "已播完，seek到了中贴片中间，跳过中贴片");
                    }
                } else if (seek >= playInfo.midAdPlayTime && seek <= playInfo.midAdPlayTime + playInfo.midDuration) {
                    seek = playInfo.midAdPlayTime;
                    this.mActivity.getMidAdController().notifyMidAdStartToAdSdk();
                    LogInfo.log("zhuqiao", "seek到了中贴片中间，从中贴片头开始播放");
                }
            }
            handSeekFinish(seek);
            if (flow.isUseDoublePlayerAndChangeStream()) {
                LogInfo.log("zhuqiao", "切换码流中seek，锁死");
                this.mActivity.getVideoController().setEnableSeek(false);
                this.mActivity.getLoadListener().loading();
            }
            this.mForegroundVideoView.seekTo(seek, true, false);
        }
    }

    public void startHandlerTime() {
        this.mPlayingHandler.startTimer();
    }

    public void stopHandlerTime() {
        this.mPlayingHandler.stopTimer();
    }

    public void buffTimeSchedule() {
        this.mPlayingHandler.buffTimeSchedule();
    }

    public void handSeekFinish(long pos) {
        if (this.mPlayingHandler != null) {
            this.mPlayingHandler.showPlayRecommendTip(pos, 0);
        }
    }

    public boolean isEnforcementPause() {
        return this.mEnforcementPause;
    }

    public void closePauseAd() {
        if (this.mActivity.getPlayAdListener() != null) {
            this.mActivity.getPlayAdListener().closePauseAd();
        }
    }

    public void initVideoView(boolean isLocal, boolean isChangeStream) {
        LogInfo.log("zhuqiao", "initVideoView isChangeStream:" + isChangeStream);
        if (!StatisticsUtils.isFirstPlay) {
            StatisticsUtils.isFirstPlay = true;
            StatisticsUtils.statisticsLoginAndEnv(this.mActivity, 4, true);
            StatisticsUtils.statisticsLoginAndEnv(this.mActivity, 4, false);
        }
        if (isChangeStream && this.mActivity.getFlow().enableDoublePlayer()) {
            createVideoViewFrame(false);
            this.mBackgroundVideoView = new BackgroundVideoView(this.mActivity, this);
            this.mBackgroundBuild = new AlbumVideoViewBuilder(this.mActivity);
            this.mBackgroundBuild.initVideoView(isLocal);
            this.mBackgroundVideoView.setVideoViewBuilder(this.mBackgroundBuild);
            return;
        }
        createVideoViewFrame(true);
        this.mForegroundBuild = new AlbumVideoViewBuilder(this.mActivity);
        this.mForegroundBuild.initVideoView(isLocal);
        this.mForegroundVideoView.setVideoViewBuilder(this.mForegroundBuild);
    }

    public void start() {
        if (this.mForegroundVideoView.mVideoView != null) {
            LogInfo.log("zhuqiao", "mVideoView.start()");
            this.mForegroundVideoView.mVideoView.start();
            this.mPlayingHandler.startTimer();
            this.mWaterMarkController.setNeedPauseWater(false);
            if (UIsUtils.isLandscape(this.mActivity) && this.mActivity.getBarrageProtocol() != null) {
                this.mActivity.getBarrageProtocol().start();
            }
        }
        callAdsPlayInterface(3);
    }

    public void pause() {
        if (this.mForegroundVideoView.mVideoView != null) {
            LogInfo.log("A8", "暂停视频播放");
            this.mForegroundVideoView.mVideoView.pause();
            this.mWaterMarkController.setNeedPauseWater(true);
            if (this.mActivity.getBarrageProtocol() != null) {
                this.mActivity.getBarrageProtocol().pause(this.mActivity.mIsPlayingDlna);
            }
        }
        callAdsPlayInterface(2);
    }

    public void startByDLNAStop() {
        start();
    }

    public void stopPlayback() {
        this.mForegroundVideoView.stopPlayback();
        this.mActivity.getMidAdController().reSet();
        this.mActivity.getVideoController().setEnableSeek(true);
        onStop(true);
    }

    public void onStop(boolean needStatistics) {
        pauseCde();
        pause();
        if (needStatistics && this.mPlayingHandler != null && this.mActivity.getFlow() != null) {
            this.mPlayingHandler.onStopBack(false);
        }
    }

    public boolean isPlaying() {
        return this.mForegroundVideoView.isPlaying();
    }

    public boolean isPaused() {
        return this.mForegroundVideoView.isPaused();
    }

    public void resetPlayFlag() {
        this.mForegroundVideoView.resetPlayFlag();
    }

    public void setEnforcementPause(boolean pause) {
        if (this.mForegroundVideoView.mVideoView != null) {
            this.mEnforcementPause = pause;
            this.mForegroundVideoView.mVideoView.setEnforcementPause(pause);
        }
    }

    public void setEnforcementWait(boolean wait) {
        if (this.mForegroundVideoView.mVideoView != null) {
            this.mForegroundVideoView.mVideoView.setEnforcementWait(wait);
        }
    }

    public void seekTo(long position, boolean shouldAddDuration) {
        this.mForegroundVideoView.seekTo(position, shouldAddDuration);
    }

    public long getCurrentPosition() {
        return this.mForegroundVideoView != null ? this.mForegroundVideoView.getCurrentPosition() : 0;
    }

    public long getDuration() {
        return this.mForegroundVideoView.getDuration();
    }

    public int getBufferPercentage() {
        return this.mPlayingHandler.getBufferPercentage();
    }

    public void startPlayNet(String url, long msec, boolean isChangeStream, boolean forceSeek) {
        if (isChangeStream && this.mActivity.getFlow().enableDoublePlayer() && this.mBackgroundVideoView != null) {
            LogInfo.log("zhuqiao", "background startPlayNet msec:" + msec + ";isChangeStream:" + isChangeStream + ";forceSeek:" + forceSeek);
            if (this.mActivity.getFlow() != null) {
                this.mBackgroundVideoView.mLinkShell = this.mActivity.getFlow().mAlbumUrl.linkShellUrl;
                this.mBackgroundVideoView.mFrontAdDuration = this.mActivity.getFlow().mPlayInfo.frontAdDuration;
            }
            this.mBackgroundVideoView.startPlayNet(url, msec, isChangeStream, forceSeek);
            return;
        }
        LogInfo.log("zhuqiao", "foreground startPlayNet msec:" + msec + ";isChangeStream:" + isChangeStream + ";forceSeek:" + forceSeek);
        if (this.mActivity.getFlow() != null) {
            this.mForegroundVideoView.mLinkShell = this.mActivity.getFlow().mAlbumUrl.linkShellUrl;
            this.mForegroundVideoView.mFrontAdDuration = this.mActivity.getFlow().mPlayInfo.frontAdDuration;
        }
        this.mForegroundVideoView.startPlayNet(url, msec, isChangeStream, forceSeek);
    }

    public void startPlayLocal(String uri, long msec, boolean isChangeStream) {
        if (isChangeStream && this.mActivity.getFlow().enableDoublePlayer() && this.mBackgroundVideoView != null) {
            this.mBackgroundVideoView.startPlayLocal(uri, msec);
        } else {
            this.mForegroundVideoView.startPlayLocal(uri, msec);
        }
    }

    public void hideRecommendTip() {
        if (this.mActivity.getRecommendController() != null) {
            this.mActivity.getRecommendController().hideRecommendTipView();
        }
    }

    public long getCurrTime() {
        if (this.mForegroundVideoView == null || this.mForegroundVideoView.mVideoView == null) {
            return this.mActivity.getFlow().mPlayInfo.currRealTime - this.mActivity.getFlow().mPlayInfo.frontAdDuration;
        }
        return ((long) this.mForegroundVideoView.mVideoView.getCurrentPosition()) - this.mForegroundVideoView.mFrontAdDuration;
    }

    public long getFrontAdDuration() {
        if (this.mForegroundVideoView != null) {
            return this.mForegroundVideoView.mFrontAdDuration;
        }
        return this.mActivity.getFlow().mPlayInfo.frontAdDuration;
    }

    public void callAdsPlayInterface(int whichStatus) {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (flow != null) {
            try {
                if (flow.mIVideoStatusInformer != null) {
                    switch (whichStatus) {
                        case 1:
                            if (!this.mHasCallAdsStart) {
                                this.mHasCallAdsStart = true;
                                flow.mIVideoStatusInformer.OnVideoStart(Boolean.valueOf(PreferencesManager.getInstance().isVip()));
                                LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~~OnVideoStart~~~~~~~~~~~~~~~~~~~");
                                return;
                            }
                            return;
                        case 2:
                            flow.mIVideoStatusInformer.OnVideoPause(PreferencesManager.getInstance().isVip());
                            return;
                        case 3:
                            flow.mIVideoStatusInformer.OnVideoResume(PreferencesManager.getInstance().isVip());
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~OnVideoResume~~~~~~~~~~~~~~~~~~~");
                            return;
                        case 4:
                            flow.mIVideoStatusInformer.OnVideoComplate();
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~OnVideoComplate~~~~~~~~~~~~~~~~~~~");
                            return;
                        case 5:
                            flow.mIVideoStatusInformer.onVideoError();
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~onVideoError~~~~~~~~~~~~~~~~~~~");
                            return;
                        case 6:
                            flow.mIVideoStatusInformer.OnVideoResize(this.mRect);
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~OnVideoResize~~~~~~~~~~~~~~~~~~~");
                            return;
                        case 7:
                            flow.mIVideoStatusInformer.OnActivityPause();
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~OnActivityPause~~~~~~~~~~~~~~~~~~~");
                            return;
                        case 8:
                            flow.mIVideoStatusInformer.OnActivityResume();
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~OnActivityResume~~~~~~~~~~~~~~~~~~~");
                            return;
                        case 9:
                            flow.mIVideoStatusInformer.OnActivityExit();
                            LogInfo.log("zhuqiao", "~~~~~~~~~~~~~~~~~~~OnActivityExit~~~~~~~~~~~~~~~~~~~");
                            return;
                        default:
                            return;
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public PlayRecord getPoint(int pid, int vid, boolean isDownload) {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(112, new PlayRecordFetch(pid, vid, isDownload)));
        if (LeResponseMessage.checkResponseMessageValidity(response, PlayRecord.class)) {
            return (PlayRecord) response.getData();
        }
        return null;
    }

    public void startCdeDownloadBuffer(String url) {
        if (this.mActivity.getFlow() != null && this.mActivity.getFlow().mIsUseCde) {
            if (this.mCdeStateHelper == null) {
                this.mCdeStateHelper = new CdeStateHelper(url, new 3(this), Looper.myLooper());
            }
            this.mCdeStateHelper.start();
        }
    }

    public void stopCdeDownloadBuffer() {
        if (this.mCdeStateHelper != null && this.mActivity.getFlow() != null && this.mActivity.getFlow().mIsUseCde) {
            this.mCdeStateHelper.stop();
            this.mCdeStateHelper = null;
        }
    }

    public void handlerFloatBall(String pageId, int cid) {
    }

    public void startOverall() {
        if (this.mActivity.getController() != null) {
            this.mActivity.getController().start();
        }
    }

    public void finishPlayer(boolean isJumpToPip) {
        if (this.mActivity.getController() != null) {
            this.mActivity.getController().finishPlayer();
        }
    }

    public void playAnotherVideo(boolean isFromInter) {
        this.mHasCallAdsStart = false;
        this.mIsSeekByUser = false;
        destoryCde();
        reset(false);
        if (this.mActivity.getBarrageProtocol() != null) {
            this.mActivity.getBarrageProtocol().end();
        }
        this.mActivity.getVideoController().playAnotherVideo(false);
    }

    public void rePlay(boolean isChangeStream) {
        if (!(isChangeStream && this.mActivity.getFlow().enableDoublePlayer())) {
            destoryCde();
            this.mActivity.getVideoController().playAnotherVideo(true);
        }
        reset(true);
    }

    private void reset(boolean isRetry) {
        stopBackgroundVideoView();
        stopCdeDownloadBuffer();
        if (this.mForegroundVideoView != null) {
            this.mForegroundVideoView.mShouldChangeStreamWhenPlayed = false;
            if (!isRetry) {
                this.mForegroundVideoView.mFrontAdDuration = 0;
            }
        }
    }

    public void stopBackgroundVideoView() {
        if (this.mBackgroundVideoView != null) {
            this.mBackgroundVideoView.cancelTime();
            this.mBackgroundVideoView = null;
            this.mBackgroundBuild = null;
        }
        if (this.mBackgroundPlayerFrame != null) {
            this.mBackgroundPlayerFrame.removeAllViews();
            this.mContentView.removeView(this.mBackgroundPlayerFrame);
            this.mBackgroundVideoView = null;
        }
    }

    public boolean shouldCombineAdPlay() {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        return flow.mIsCombineAd && flow.mPlayInfo.frontAdDuration > 0 && !flow.mIsFrontAdFinished;
    }

    public void onVideoFirstPlay() {
        LogInfo.log("zhuqiao", "---- 正片播放第一帧 ----");
        if (!(!PreferencesManager.getInstance().isVip() || PreferencesManager.getInstance().getSkipAdTipFlag() || this.mActivity == null || this.mActivity.getFlow().mVideoType == VideoType.Panorama)) {
            ToastUtils.showToast(TipUtils.getTipMessage("2000011"), 48, 0, this.mActivity.getWindowManager().getDefaultDisplay().getHeight() / 5);
            PreferencesManager.getInstance().setSkipAdTipFlag();
        }
        if (!(this.mActivity.getFlow() == null || this.mForegroundVideoView.mVideoView == null)) {
            this.mActivity.getFlow().mPlayInfo.currTime = Math.max(0, ((long) this.mForegroundVideoView.mVideoView.getCurrentPosition()) - this.mActivity.getFlow().mPlayInfo.frontAdDuration);
            this.mActivity.getFlow().addPlayInfo("播放出第一帧", "");
        }
        if (this.mActivity.getFlow() != null && this.mActivity.getFlow().mIsDownloadFile && NetworkUtils.isNetworkAvailable()) {
            if (this.mActivity.getBarrageProtocol() != null) {
                this.mActivity.getBarrageProtocol().onCacheVideoFirstPlay();
            }
        } else if (this.mActivity.getFlow() != null) {
            VideoBean videoBean = this.mActivity.getFlow().mCurrentPlayingVideo;
            boolean isDanmaku = false;
            if (videoBean != null) {
                if (videoBean.isDanmaku == 1) {
                    isDanmaku = true;
                } else {
                    isDanmaku = false;
                }
            }
            if (this.mActivity.getBarrageProtocol() != null) {
                this.mActivity.getBarrageProtocol().onStartPlay(isDanmaku);
            }
        }
    }

    private void pauseCde() {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (flow != null && flow.mIsUseCde) {
            CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
            if (cdeHelper != null && !TextUtils.isEmpty(flow.mAlbumUrl.linkShellUrl)) {
                int i = 0;
                while (i < 3) {
                    try {
                        LogInfo.log("zhuqiao", "暂停cde");
                        cdeHelper.pausePlay(flow.mAlbumUrl.linkShellUrl);
                        return;
                    } catch (OutOfMemoryError e) {
                        BaseApplication.getInstance().onAppMemoryLow();
                        i++;
                    }
                }
            }
        }
    }

    public void resumeCde() {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (flow != null && flow.mIsUseCde) {
            CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
            if (cdeHelper != null && !TextUtils.isEmpty(flow.mAlbumUrl.linkShellUrl)) {
                LogInfo.log("zhuqiao", "恢复cde");
                cdeHelper.resumePlay(flow.mAlbumUrl.linkShellUrl);
            }
        }
    }

    public void destoryCde() {
        if (this.mActivity.getFlow() != null) {
            destoryCde(this.mActivity.getFlow().mAlbumUrl.linkShellUrl);
        }
    }

    public void destoryCde(String linkshell) {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (flow != null && flow.mIsUseCde && !TextUtils.isEmpty(linkshell)) {
            CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
            if (cdeHelper != null && !TextUtils.isEmpty(linkshell)) {
                LogInfo.log("zhuqiao", "销毁cde");
                cdeHelper.stopPlay(linkshell);
            }
        }
    }

    public void onDestroy() {
        stopPlayback();
        stopCdeDownloadBuffer();
        destoryCde();
        if (this.mBackgroundVideoViewSubscription != null && this.mBackgroundVideoViewSubscription.hasSubscriptions()) {
            this.mBackgroundVideoViewSubscription.unsubscribe();
        }
        this.mBackgroundVideoViewSubscription = null;
        if (this.mPlayingHandler != null) {
            this.mPlayingHandler.mHandler.removeCallbacksAndMessages(null);
        }
        if (this.mWaterMarkController != null) {
            this.mWaterMarkController.onDestory();
        }
    }

    public void onChangeStreamSuccess() {
        LogInfo.log("zhuqiao", "切换码流成功");
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().mIsChangeingStream = false;
        }
        if (this.mPlayingHandler != null) {
            this.mPlayingHandler.onStopBack(true);
        }
        stopHandlerTime();
        if (!(this.mForegroundVideoView == null || this.mForegroundVideoView.mVideoView == null || this.mBackgroundVideoView == null || this.mBackgroundVideoView.mVideoView == null)) {
            if (this.mBackgroundPlayerFrame != null) {
                this.mBackgroundPlayerFrame.setVisibility(0);
            }
            this.mContentView.removeView(this.mForegroundPlayerFrame);
            this.mForegroundPlayerFrame = this.mBackgroundPlayerFrame;
            String oldLinkshell = this.mForegroundVideoView.mLinkShell;
            this.mForegroundVideoView.replaceVideoView(this.mBackgroundVideoView);
            start();
            destoryCde(oldLinkshell);
            this.mBackgroundVideoView = null;
            this.mBackgroundPlayerFrame = null;
        }
        this.mActivity.getVideoController().onStreamSwitchFinish(true);
        this.mActivity.getVideoController().setEnableSeek(true);
    }

    public void onChangeStreamError() {
        LogInfo.log("zhuqiao", "切换码流失败");
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().mIsChangeingStream = false;
        }
        stopBackgroundVideoView();
        this.mActivity.getVideoController().onStreamSwitchFinish(false);
    }

    public void checkDrmPlugin() {
        this.mActivity.checkDrmPlugin(true);
    }

    public void loadDrmPlugin() {
        File folder = this.mActivity.getDir(SimplePluginDownloadService.FLODER_NAME, 0);
        if (folder != null && folder.exists()) {
            System.load(folder.getAbsolutePath() + File.separator + Constant.DRM_LIBWASABIJNI);
        }
    }

    public void setVisibityForWaterMark(boolean show) {
        this.mWaterMarkImageView.setVisibility(show ? 0 : 8);
    }

    public View getContainView() {
        return this.mContentView;
    }

    public void buyWo3G() {
        if (this.mActivity.getController() != null) {
            this.mActivity.getController().setIsBuyWo3G(true);
        }
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvWoFlowActivityConfig(this.mActivity).create(false)));
    }
}
