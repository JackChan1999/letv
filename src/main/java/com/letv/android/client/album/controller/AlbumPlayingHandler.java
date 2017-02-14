package com.letv.android.client.album.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.view.AlbumBaseControllerFragment;
import com.letv.android.client.album.view.AlbumPlayFragment;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.business.flow.statistics.PlayStatisticsUtils;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.core.BaseApplication;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.mobile.lebox.LeboxApiManager;
import java.util.Timer;

public class AlbumPlayingHandler {
    private static final int PLAY_ERROR = 3;
    public static final int UPDATE_PROGRESS = 0;
    public static final int UPDATE_STATICICS_TIME = 1;
    private boolean isKadun = false;
    private long kadunTime;
    private AlbumPlayActivity mActivity;
    private Timer mBufferTimer;
    private Context mContext;
    private boolean mFirstProcess = true;
    public Handler mHandler = new 1(this);
    private boolean mHasBlockFiveSecond;
    private boolean mHasCalledPlayNext = false;
    private boolean mIsStoped = true;
    private AlbumPlayFragment mPlayFragment;
    private AlbumPlayInfo mPlayInfo;
    private Timer mProgressTimer;
    private boolean mShouldCheckBlock;
    private boolean mStartComputeBlock = true;
    private boolean mTimeStatisticsStart = false;

    public AlbumPlayingHandler(Context context, AlbumPlayFragment playFragment) {
        this.mContext = context;
        this.mPlayFragment = playFragment;
        if (this.mPlayFragment == null) {
            throw new NullPointerException("AlbumPlayingHandler param is null!");
        } else if (this.mContext instanceof AlbumPlayActivity) {
            this.mActivity = (AlbumPlayActivity) this.mContext;
        }
    }

    public void init() {
        LetvMediaPlayerControl videoView = this.mPlayFragment.getVideoView();
        if (this.mActivity != null && videoView != null && this.mActivity.getFlow() != null) {
            this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
            this.mPlayInfo.currTime = Math.max(0, ((long) videoView.getCurrentPosition()) - this.mPlayInfo.frontAdDuration);
            this.mPlayInfo.videoTotalTime = (((long) videoView.getDuration()) - this.mPlayInfo.frontAdDuration) - this.mPlayInfo.midDuration;
            this.mPlayInfo.combineTotalTime = (long) videoView.getDuration();
            this.mActivity.getVideoController().initProcess(((int) this.mPlayInfo.videoTotalTime) / 1000, ((int) this.mPlayInfo.currTime) / 1000, 0);
        }
    }

    public void startTimer() {
        if (this.mIsStoped) {
            stopTimer();
            LogInfo.log("zhuqiao", "开启timer");
            this.mIsStoped = false;
            this.mHasCalledPlayNext = false;
            this.mTimeStatisticsStart = false;
            this.mShouldCheckBlock = false;
            if (!(!this.mPlayFragment.mIsSeekByUser || this.mActivity == null || this.mActivity.getFlow() == null)) {
                this.mActivity.getFlow().mPlayInfo.mBlockType = "drag";
            }
            this.mProgressTimer = initTimer();
            this.mProgressTimer.schedule(new 2(this), 0, 1000);
        }
    }

    private Timer initTimer() {
        int i = 0;
        while (i < 3) {
            try {
                return new Timer();
            } catch (OutOfMemoryError e) {
                if (i < 3) {
                    BaseApplication.getInstance().onAppMemoryLow();
                }
                i++;
            }
        }
        return new Timer();
    }

    public void stopTimer() {
        this.mIsStoped = true;
        this.mTimeStatisticsStart = false;
        this.isKadun = false;
        if (this.mProgressTimer != null) {
            this.mProgressTimer.cancel();
            this.mProgressTimer.purge();
            this.mProgressTimer = null;
        }
        this.mHandler.removeMessages(1);
    }

    private void onProgressHandler(boolean checkBlock) {
        LetvMediaPlayerControl videoView = this.mPlayFragment.getVideoView();
        if (this.mActivity != null && videoView != null && this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
            if (!flow.mIsMidAdFinished && this.mTimeStatisticsStart) {
                this.mTimeStatisticsStart = false;
                this.mHandler.removeMessages(1);
            }
            if (this.mFirstProcess) {
                this.mFirstProcess = false;
            } else {
                this.mPlayInfo.mBlockType = "nature";
            }
            long oldTime = this.mPlayInfo.currRealTime;
            this.mPlayInfo.currRealTime = (long) videoView.getCurrentPosition();
            this.mPlayInfo.currTime = Math.max(0, this.mPlayFragment.getCurrTime());
            AlbumPlayGeneralTrailController vipListener = this.mActivity.getVipTrailListener();
            if (vipListener == null || !vipListener.isVipVideo() || !((AlbumPlayActivity) this.mContext).getVipTrailListener().checkVipTrailIsStateEnd()) {
                if (this.mActivity.getVideoController().mShouldDoChangeStreamWhenPlayed) {
                    this.mHandler.post(new 3(this));
                    this.mActivity.getVideoController().mShouldDoChangeStreamWhenPlayed = false;
                }
                checkCombinAdHasFinish();
                this.mActivity.getMidAdController().checkMidAdPlay();
                if ((flow.mIsCombineAd && flow.mIsFrontAdFinished && flow.mIsMidAdFinished) || (!flow.mIsCombineAd && flow.mIsSeparateAdFinished)) {
                    AlbumPlayInfo albumPlayInfo = this.mPlayInfo;
                    albumPlayInfo.timeElapsed++;
                    if (!this.mTimeStatisticsStart) {
                        this.mTimeStatisticsStart = true;
                        this.mHandler.sendEmptyMessageDelayed(1, 15000);
                    }
                }
                if (flow.mLaunchMode == 0) {
                    flow.mLocalSeek = this.mPlayInfo.currTime / 1000;
                }
                long timespan = this.mPlayInfo.currRealTime - oldTime;
                if (checkBlock && timespan >= 0 && timespan <= 500 && flow.mIsFrontAdFinished && this.mStartComputeBlock) {
                    logBuffer();
                } else {
                    if (this.isKadun) {
                        LogInfo.log("kadun", "关闭圈圈");
                        this.isKadun = false;
                        if (this.kadunTime > 0) {
                            int blockTime = (int) ((System.currentTimeMillis() / 1000) - this.kadunTime);
                            if (blockTime >= 2 && blockTime < 5) {
                                RxBus.getInstance().send(AlbumBlockController.BLOCK_TWO_SECOND);
                            }
                            flow.addPlayInfo("卡顿结束", blockTime + "s");
                        } else {
                            flow.addPlayInfo("卡顿结束", "");
                        }
                        flow.updateBlockDataStatistics(false, this.kadunTime * 1000, true, this.mPlayInfo.mBlockType);
                        this.kadunTime = 0;
                    }
                    this.mHasBlockFiveSecond = false;
                    if (this.mActivity.getVideoController() != null) {
                        this.mActivity.getVideoController().setBlockBtnVisibile(false);
                    }
                    if (this.mPlayInfo.mHasBuffered) {
                        reportWhenBuffer();
                    }
                    this.mPlayInfo.mIsBuffered = false;
                    if (!this.mPlayInfo.mIsSlipSeekBar) {
                        this.mPlayInfo.mIsUserClickSeekBar = false;
                        this.mPlayInfo.mIsAutoClickSeekBar = true;
                        this.mPlayInfo.mIsAutoClickSeekBarCount = false;
                        this.mPlayInfo.mIsUserClickSeekBarCount = false;
                    }
                    if (this.mActivity.getLoadListener() != null && this.mActivity.getLoadListener().isLoadingShow()) {
                        this.mHandler.post(new 4(this));
                    }
                    if (AlbumBaseControllerFragment.mIsSwitch && !this.mActivity.getFlow().enableDoublePlayer()) {
                        this.mHandler.post(new 5(this));
                    }
                    if (AlbumBaseControllerFragment.mIsSwitchAudioTrack) {
                        this.mHandler.post(new 6(this));
                    }
                    if (flow.mPlayRecord != null) {
                        flow.mPlayRecord.playedDuration = this.mPlayInfo.currTime / 1000;
                    }
                    if (flow.mIsFirstPlay) {
                        flow.mIsFirstPlay = false;
                    }
                }
                if (flow.mIsFrontAdFinished && this.mPlayFragment.isPlaying() && this.mActivity.getVideoController() != null && this.mActivity.getVideoController().isVisible()) {
                    this.mHandler.sendEmptyMessage(0);
                }
                long endTime = this.mPlayInfo.endTime + (this.mPlayInfo.midDuration / 1000);
                if (!flow.mIsSkip || this.mPlayInfo.endTime <= 0) {
                    showPlayRecommendTip(this.mPlayInfo.currTime, this.mPlayInfo.videoTotalTime / 1000);
                } else {
                    if ((this.mPlayInfo.currTime / 1000) + 15 >= endTime && this.mPlayInfo.mIsShowSkipEnd) {
                        this.mPlayInfo.mIsShowSkipEnd = false;
                    }
                    if (this.mPlayInfo.currTime / 1000 >= endTime) {
                        LogInfo.log("zhuqiao", "currTime:" + (this.mPlayInfo.currTime / 1000) + ";;endTime:" + endTime);
                        if (flow.mPlayRecord != null) {
                            flow.mPlayRecord.playedDuration = -1;
                        }
                        LogInfo.log("zhuqiao", "handler skip called play next");
                        this.mHandler.post(new 7(this));
                        return;
                    }
                    showPlayRecommendTip(this.mPlayInfo.currTime, endTime);
                }
                if ((this.mPlayInfo.currTime / 1000) + 60 >= this.mPlayInfo.videoTotalTime / 1000 && !this.mPlayInfo.mIsStatisticsFinish && this.mPlayInfo.videoTotalTime != 0) {
                    this.mActivity.getFlow().updatePlayDataStatistics("finish", (this.mPlayInfo.currTime / 1000) - (this.mPlayInfo.videoTotalTime / 1000));
                    this.mPlayInfo.mIsStatisticsFinish = true;
                }
            }
        }
    }

    private boolean checkCombinAdHasFinish() {
        if (!this.mPlayFragment.shouldCombineAdPlay() || this.mPlayInfo.currRealTime + 500 < this.mPlayFragment.getFrontAdDuration()) {
            if (this.mPlayFragment.shouldCombineAdPlay()) {
                this.mActivity.getFlow().mIsFrontAdFinished = false;
                this.mPlayInfo.mIsPlayingAds = true;
                this.mHandler.post(new 9(this));
            } else {
                this.mActivity.getFlow().mIsFrontAdFinished = true;
            }
            if (this.mActivity.getFlow().mIsFrontAdFinished) {
                return false;
            }
            return true;
        }
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().mIsFrontAdFinished = true;
            this.mPlayInfo.mIsPlayingAds = false;
            if (!this.mPlayInfo.mIsStatisticsPlay) {
                this.mActivity.getFlow().updatePlayDataStatistics("play", -1);
            }
        }
        this.mHandler.post(new 8(this));
        return true;
    }

    public void adCompleted() {
        Message msg = new Message();
        msg.what = 3;
        this.mActivity.getPlayAdListener().notifyADEvent(msg);
        this.mActivity.getVideoController().setControllerVisibility(0, true);
        this.mPlayFragment.callAdsPlayInterface(1);
        if (this.mPlayFragment.mForegroundVideoView.mSeek > 0) {
            this.mPlayFragment.onSeekFinish((int) (this.mPlayFragment.mForegroundVideoView.mSeek / 1000));
            this.mPlayFragment.start();
        }
        if (UIsUtils.isLandscape(this.mContext)) {
            this.mActivity.getVideoController().initInteract();
        }
        if (!this.mActivity.getFlow().isPlayingAd()) {
            this.mPlayFragment.setVisibityForWaterMark(true);
        }
        if (!this.mActivity.getFlow().shouldShowNetChangeDialog()) {
            this.mPlayFragment.onVideoFirstPlay();
        }
    }

    private void logBuffer() {
        if (this.mActivity.getFlow() != null && this.mPlayFragment.getVideoView() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            this.mPlayInfo = flow.mPlayInfo;
            if (flow.mVideoType == VideoType.Dolby || this.mPlayFragment.getVideoView().isPlaying() || this.mPlayFragment.isEnforcementPause() || (flow.isLebox() && !flow.mHasAd)) {
                this.mHandler.sendEmptyMessage(3);
            }
            flow.mHasAd = false;
            if (!flow.mIsFirstPlay) {
                AlbumPlayInfo albumPlayInfo;
                if (!this.mPlayInfo.mIsBuffered) {
                    albumPlayInfo = this.mPlayInfo;
                    albumPlayInfo.bufferNum++;
                    this.mPlayInfo.mIsBuffered = true;
                }
                albumPlayInfo = this.mPlayInfo;
                albumPlayInfo.bufferTime++;
                if (this.mPlayInfo.mIsUserClickSeekBar) {
                    logBufferWhenUserClick();
                } else {
                    logBufferWhenAuto();
                }
            }
        }
    }

    private void playError() {
        if (this.mActivity.getFlow() != null && this.mPlayFragment.getVideoView() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            this.mPlayInfo = flow.mPlayInfo;
            if (!flow.isLebox()) {
                boolean isBufferPlayOver;
                if (this.mPlayFragment.getVideoView().getCurrentPosition() >= (getBufferPercentage() * this.mPlayFragment.getVideoView().getDuration()) / 100) {
                    isBufferPlayOver = true;
                } else {
                    isBufferPlayOver = false;
                }
                if (this.mPlayInfo.currTime != 0 && isBufferPlayOver && NetworkUtils.getNetworkType() == 0) {
                    if (!flow.isLocalFile()) {
                        if (!(flow.mLaunchMode == 0 || this.mActivity.getLoadListener() == null)) {
                            this.mActivity.getLoadListener().requestError("", "", "");
                        }
                        if (((AlbumPlayActivity) this.mContext).getVipTrailListener() != null) {
                            ((AlbumPlayActivity) this.mContext).getVipTrailListener().hide();
                        }
                        if (this.mActivity.getErrorTopController() != null) {
                            this.mActivity.getErrorTopController().setPlayErrorCode(LetvErrorCode.NO_NET, false);
                        }
                        this.mActivity.getVideoController().setEnable(false);
                        stopTimer();
                    }
                } else if (this.mActivity.getLoadListener() != null && !flow.mIsFirstPlay) {
                    if (!this.isKadun) {
                        LogInfo.log("kadun", "显示圈圈");
                        this.isKadun = true;
                        this.kadunTime = System.currentTimeMillis() / 1000;
                        if (this.mPlayInfo.mIsFromUser) {
                            flow.addPlayInfo("卡顿开始", "手动卡顿");
                        } else {
                            flow.addPlayInfo("卡顿开始", "自动卡顿");
                        }
                    }
                    if (!this.mHasBlockFiveSecond && this.kadunTime > 0 && (System.currentTimeMillis() / 1000) - this.kadunTime > 5) {
                        this.mHasBlockFiveSecond = true;
                        RxBus.getInstance().send(AlbumBlockController.BLOCK_FIVE_SECOND);
                    }
                    this.mActivity.getLoadListener().loading();
                }
            } else if (LeboxApiManager.getInstance().isLeboxConnection()) {
                this.mActivity.getLoadListener().loading();
            } else {
                flow.connectLeboxFailed();
                stopTimer();
            }
        }
    }

    private void logBufferWhenGlsb() {
        this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
        this.mPlayInfo.mIsFromGlsb = true;
        this.mPlayInfo.mHasBuffered = true;
        AlbumPlayInfo albumPlayInfo = this.mPlayInfo;
        albumPlayInfo.glsbBfTime++;
        albumPlayInfo = this.mPlayInfo;
        albumPlayInfo.blockTime++;
    }

    private void logBufferWhenUserClick() {
        AlbumPlayInfo albumPlayInfo;
        this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
        buffTimeScheduleCancel();
        if (!this.mPlayInfo.mIsUserClickSeekBarCount) {
            albumPlayInfo = this.mPlayInfo;
            albumPlayInfo.userBfCount++;
            this.mPlayInfo.mIsUserClickSeekBarCount = true;
        }
        if (this.mPlayInfo.userBfCount == 1) {
            this.mPlayInfo.userFirstBfTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
        }
        albumPlayInfo = this.mPlayInfo;
        albumPlayInfo.userBfTime++;
        albumPlayInfo = this.mPlayInfo;
        albumPlayInfo.userBfTimeTotal++;
        this.mPlayInfo.mHasBuffered = true;
        this.mPlayInfo.mIsFromUser = true;
        albumPlayInfo = this.mPlayInfo;
        albumPlayInfo.blockTime++;
        if (this.mPlayInfo.userBfTime != 1) {
        }
    }

    private void logBufferWhenAuto() {
        this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
        if (!this.mPlayInfo.mIsAutoClickSeekBarCount) {
            this.mPlayInfo.mIsAutoClickSeekBarCount = true;
            AlbumPlayInfo albumPlayInfo = this.mPlayInfo;
            albumPlayInfo.autofCount++;
        }
        if (this.mPlayInfo.mIsAutoClickSeekBar && this.mPlayInfo.mIsAutoClickSeekBarCount) {
            if (this.mPlayInfo.autofCount == 1) {
                this.mPlayInfo.autoFirstBfTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss:SSS");
            }
            albumPlayInfo = this.mPlayInfo;
            albumPlayInfo.autoBfTime++;
            albumPlayInfo = this.mPlayInfo;
            albumPlayInfo.autoBfTimeTotal++;
            this.mPlayInfo.mHasBuffered = true;
            this.mPlayInfo.mIsFromAuto = true;
            albumPlayInfo = this.mPlayInfo;
            albumPlayInfo.blockTime++;
        }
        if (this.mPlayInfo.autoBfTime != 1) {
        }
    }

    private void reportWhenBuffer() {
        this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
        if (this.mPlayInfo.mIsFromUser) {
            LogInfo.log("zhuqiao", "手动卡顿...userBfTime=" + this.mPlayInfo.userBfTime);
            this.mPlayInfo.mHasBuffered = false;
            this.mPlayInfo.mIsFromUser = false;
            this.mPlayInfo.userBfTime = 0;
        } else if (this.mPlayInfo.mIsFromAuto) {
            LogInfo.log("zhuqiao", "自动卡顿...mAutoBfTime=" + this.mPlayInfo.autoBfTime);
            this.mPlayInfo.mHasBuffered = false;
            this.mPlayInfo.mIsFromAuto = false;
            this.mPlayInfo.autoBfTime = 0;
        } else if (this.mPlayInfo.mIsFromGlsb) {
            LogInfo.log("zhuqiao", "切换码流卡顿...mGlsbBfTime=" + this.mPlayInfo.glsbBfTime);
            this.mPlayInfo.mHasBuffered = false;
            this.mPlayInfo.mIsFromGlsb = false;
            this.mPlayInfo.glsbBfTime = 0;
            if (this.mBufferTimer != null) {
                this.mBufferTimer.cancel();
            }
            this.mBufferTimer = null;
        }
    }

    public int getBufferPercentage() {
        if (this.mActivity.getFlow() == null || this.mPlayFragment == null || this.mPlayFragment.getDuration() == 0) {
            return 0;
        }
        return (int) ((this.mActivity.getFlow().mPlayInfo.currDuration * 100) / this.mPlayFragment.getDuration());
    }

    public void buffTimeSchedule() {
        buffTimeScheduleCancel();
        System.out.println("timer schedule start");
        this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
        this.mBufferTimer = new Timer();
        this.mPlayInfo.mIsUserClickSeekBar = true;
        this.mPlayInfo.mIsAutoClickSeekBar = false;
        this.mPlayInfo.mIsSlipSeekBar = true;
        this.mBufferTimer.schedule(new 10(this), 5000);
    }

    public void buffTimeScheduleCancel() {
        if (this.mBufferTimer != null) {
            this.mBufferTimer.cancel();
            this.mPlayInfo.mIsSlipSeekBar = false;
        }
        this.mBufferTimer = null;
    }

    private void updateStaticicsTime() {
        this.mPlayInfo = this.mActivity.getFlow().mPlayInfo;
        if (this.mPlayInfo.mIsStatisticsPlay) {
            long elapsedDiff = this.mPlayInfo.timeElapsed - this.mPlayInfo.lastTimeElapsed;
            if (this.mPlayInfo.mUpdateCount == 0) {
                if (elapsedDiff < 15) {
                    this.mPlayInfo.mUpdateCount = 0;
                    this.mHandler.sendEmptyMessageDelayed(1, (15 - elapsedDiff) * 1000);
                    return;
                }
                this.mActivity.getFlow().updatePlayDataStatistics("time", 15);
                this.mPlayInfo.lastTimeElapsed = this.mPlayInfo.timeElapsed;
                this.mPlayInfo.mUpdateCount = 1;
                this.mHandler.sendEmptyMessageDelayed(1, 60000);
                if (this.mPlayInfo.mTotalConsumeTime != 0) {
                    PlayStatisticsUtils.staticticsLoadTimeInfo(this.mActivity, this.mActivity.getFlow(), this.mActivity.getPlayAdListener().getAdFragment());
                }
            } else if (this.mPlayInfo.mUpdateCount == 1) {
                if (elapsedDiff < 60) {
                    this.mPlayInfo.mUpdateCount = 1;
                    this.mHandler.sendEmptyMessageDelayed(1, (60 - elapsedDiff) * 1000);
                    return;
                }
                this.mActivity.getFlow().updatePlayDataStatistics("time", 60);
                this.mPlayInfo.lastTimeElapsed = this.mPlayInfo.timeElapsed;
                this.mPlayInfo.mUpdateCount = 2;
                this.mHandler.sendEmptyMessageDelayed(1, 180000);
                if (this.mPlayInfo.mTotalConsumeTime != 0) {
                    PlayStatisticsUtils.staticticsLoadTimeInfo(this.mActivity, this.mActivity.getFlow(), this.mActivity.getPlayAdListener().getAdFragment());
                }
            } else if (this.mPlayInfo.mUpdateCount != 2) {
            } else {
                if (elapsedDiff < 180) {
                    this.mPlayInfo.mUpdateCount = 2;
                    this.mHandler.sendEmptyMessageDelayed(1, (180 - elapsedDiff) * 1000);
                    return;
                }
                if (this.mPlayInfo.mTotalConsumeTime != 0) {
                    PlayStatisticsUtils.staticticsLoadTimeInfo(this.mActivity, this.mActivity.getFlow(), this.mActivity.getPlayAdListener().getAdFragment());
                }
                this.mActivity.getFlow().updatePlayDataStatistics("time", 180);
                this.mPlayInfo.lastTimeElapsed = this.mPlayInfo.timeElapsed;
                this.mPlayInfo.mUpdateCount = 2;
                this.mHandler.sendEmptyMessageDelayed(1, 180000);
            }
        }
    }

    public void onStopBack(boolean isDoublePlayerChangeStream) {
        if (this.mActivity != null && this.mActivity.getFlow() != null) {
            AlbumPlayInfo playInfo = isDoublePlayerChangeStream ? this.mActivity.getFlow().mLastPlayInfo : this.mActivity.getFlow().mPlayInfo;
            if (playInfo != null) {
                boolean isHomeClicked = StatisticsUtils.mIsHomeClicked;
                boolean needStatisticsTimeToPlay = playInfo.mIsStatisticsPlay || ((!playInfo.mIsCombineAd && playInfo.mIsPlayingAds) || (playInfo.mIsCombineAd && playInfo.mAdCount > 0));
                if (!(!needStatisticsTimeToPlay || playInfo.mIsStatisticsLoadTime || playInfo.mTotalConsumeTime == 0)) {
                    if (!(playInfo.mIsCombineAd || !playInfo.mIsPlayingAds || playInfo.mHasCollectTimeToPlay)) {
                        playInfo.mTotalConsumeTime = playInfo.mAdsPlayFirstFrameTime - playInfo.mTotalConsumeTime;
                        playInfo.mHasCollectTimeToPlay = true;
                        LogInfo.log("jc666", "非拼接流程home键退出且广告正在播出时起播时长：" + playInfo.mTotalConsumeTime);
                    }
                    PlayStatisticsUtils.staticticsLoadTimeInfo(this.mActivity, this.mActivity.getFlow(), this.mActivity.getPlayAdListener().getAdFragment());
                }
                if (playInfo.mIsStatisticsPlay) {
                    long dif = playInfo.timeElapsed - playInfo.lastTimeElapsed;
                    if (isHomeClicked && dif > 1) {
                        dif--;
                    }
                    this.mActivity.getFlow().updatePlayDataStatistics("time", dif, null, isDoublePlayerChangeStream);
                    if (isHomeClicked) {
                        this.mActivity.getFlow().resetTime(playInfo);
                    } else {
                        this.mActivity.getFlow().updatePlayDataStatistics("end", -1, null, isDoublePlayerChangeStream);
                    }
                }
            }
        }
    }

    public void showPlayRecommendTip(long curTime, long endTime) {
        if (this.mActivity != null && this.mActivity.getRecommendController() != null && !this.mActivity.getFlow().isPlayingAd()) {
            this.mActivity.getRecommendController().showPlayRecommendTip(curTime, endTime);
        }
    }

    public synchronized void playNext() {
        if (!(this.mActivity.getHalfController() == null || this.mHasCalledPlayNext)) {
            LogInfo.log("zhuqiao", "**********playnext**********");
            if (!this.mActivity.isForceFull() || this.mActivity.mIsPlayingNonCopyright || this.mActivity.mIsLebox) {
                this.mHasCalledPlayNext = true;
                stopTimer();
                this.mActivity.getHalfController().playNext();
            } else {
                this.mActivity.getController().finishPlayer();
            }
        }
    }

    public void setStartComputeBlock(boolean startComputeBlock) {
        this.mStartComputeBlock = startComputeBlock;
    }

    public boolean isStoped() {
        return this.mIsStoped;
    }
}
