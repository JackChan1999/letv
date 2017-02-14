package com.letv.android.client.album.controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.letv.ads.ex.utils.PlayConstantUtils.ClientCPConstant;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.view.AlbumPlayFragment.BackgroundVideoViewSubscription;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;

public class AlbumMidAdController {
    private static final int MID_AD_DIFF = 500;
    private static final int TIP_DURATION = 6000;
    private AlbumPlayActivity mActivity;
    private View mCancleView;
    private Handler mHandler = new Handler();
    private boolean mHasShowed;
    private boolean mIsShowing = false;
    private TextView mTipMsgView;
    private TextView mTipTitleView;
    private View mTipView;

    public AlbumMidAdController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        this.mTipView = this.mActivity.getViewById(R.id.change_stream_tip_frame);
        this.mTipTitleView = (TextView) this.mActivity.getViewById(R.id.change_stream_tip_text);
        this.mTipMsgView = (TextView) this.mActivity.getViewById(R.id.change_stream_tip_stream);
        this.mCancleView = this.mActivity.getViewById(R.id.change_stream_tip_cancel);
    }

    private void show() {
        if (!this.mIsShowing && !this.mHasShowed) {
            this.mIsShowing = true;
            this.mHasShowed = true;
            this.mHandler.post(new 1(this));
        }
    }

    private void hide() {
        if (this.mIsShowing) {
            this.mHandler.post(new 2(this));
        }
        this.mIsShowing = false;
    }

    public void reSet() {
        hide();
        this.mHasShowed = false;
    }

    public boolean skipMidAd(long time) {
        AlbumPlayInfo playInfo = this.mActivity.getFlow().mPlayInfo;
        return time >= playInfo.midAdPlayTime - 1000 && time <= (playInfo.midAdPlayTime + playInfo.midDuration) - 500;
    }

    public void checkMidAdPlay() {
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (flow != null) {
            AlbumPlayInfo playInfo = flow.mPlayInfo;
            if (playInfo.midAdPlayTime != -1 && playInfo.midDuration != 0 && playInfo.currTime != 0) {
                if (!flow.mIsMidAdPlayed) {
                    long midEndTime = playInfo.midAdPlayTime + playInfo.midDuration;
                    if (playInfo.midAdPlayTime - playInfo.currTime > 6000 || playInfo.midAdPlayTime <= playInfo.currTime) {
                        this.mActivity.getVideoController().setEnableSeek(true);
                        hide();
                    } else {
                        if (playInfo.midAdPlayTime > playInfo.currTime + 500) {
                            show();
                        } else {
                            hide();
                        }
                        this.mActivity.getVideoController().setEnableSeek(false);
                    }
                    if (playInfo.currTime + 1500 < playInfo.midAdPlayTime) {
                        flow.mIsMidAdFinished = true;
                    } else if (Math.abs(playInfo.midAdPlayTime - playInfo.currTime) <= 1500 && flow.mIsMidAdFinished) {
                        reSet();
                        LogInfo.log("zhuqiao", "中贴开始播放");
                        this.mHandler.post(new 4(this));
                    } else if (!flow.mIsMidAdFinished && (Math.abs(midEndTime - playInfo.currTime) <= 500 || playInfo.currTime > midEndTime)) {
                        flow.mIsMidAdFinished = true;
                        flow.mIsMidAdPlayed = true;
                        reSet();
                        LogInfo.log("zhuqiao", "中贴结束播放");
                        this.mHandler.post(new 5(this));
                        if (flow.isUseDoublePlayerAndChangeStream()) {
                            RxBus.getInstance().send(new BackgroundVideoViewSubscription());
                        }
                    }
                    if (!flow.mIsMidAdFinished) {
                        this.mHandler.post(new 6(this, flow));
                    }
                } else if (skipMidAd(playInfo.currTime)) {
                    LogInfo.log("zhuqiao", "已播完，seek到了中贴片中间，跳过中贴片");
                    this.mHandler.post(new 3(this, playInfo));
                }
            }
        }
    }

    private void setVisibleForBarrage(boolean show) {
        if (this.mActivity.getBarrageProtocol() != null) {
            this.mActivity.getBarrageProtocol().changeVisibity(show);
        }
    }

    public void notifyMidAdStartToAdSdk() {
        if (this.mActivity.getFlow() != null && this.mActivity.getFlow().mIsMidAdFinished) {
            LogInfo.log("zhaosumin", "中贴广告开始");
            if (this.mActivity.getHalfFragment() != null) {
                this.mActivity.getVideoController().setVisibilityForMore(false);
                this.mActivity.getHalfFragment().closeExpand();
            }
            this.mActivity.getFlow().mIsMidAdFinished = false;
            reSet();
            this.mActivity.getVideoController().setControllerVisibility(8, true);
            setVisibleForBarrage(false);
            Message msg = new Message();
            msg.what = 10;
            Bundle bundle = new Bundle();
            bundle.putInt(ClientCPConstant.KEY_AD_TYPE, 2);
            msg.setData(bundle);
            this.mActivity.getPlayAdListener().notifyADEvent(msg);
            this.mActivity.getAlbumPlayFragment().setVisibityForWaterMark(false);
        }
    }

    public long getBeginTime(long timeMS) {
        if (this.mActivity.getFlow() == null) {
            return timeMS;
        }
        AlbumPlayInfo info = this.mActivity.getFlow().mPlayInfo;
        if (info.midDuration <= 0 || (info.currTime - info.midAdPlayTime) - info.midDuration <= -500) {
            return timeMS;
        }
        return timeMS - info.midDuration;
    }
}
