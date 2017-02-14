package com.letv.android.client.controller;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.BasePlayActivity;
import com.letv.android.client.barrage.live.LiveBarrageSentCallback;
import com.letv.android.client.commonlib.utils.AudioManagerUtils;
import com.letv.android.client.commonlib.view.LetvPlayGestureLayout;
import com.letv.android.client.commonlib.view.LetvPlayGestureLayout.LetvPlayGestureCallBack;
import com.letv.android.client.controllerbars.LiveFullControllerBar;
import com.letv.android.client.controllerbars.LiveHalfControllerBar;
import com.letv.android.client.listener.PlayActivityCallback;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.listener.OnRelevantStateChangeListener;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class BasePlayController implements OnRelevantStateChangeListener, OnVideoViewStateChangeListener, LetvPlayGestureCallBack {
    int currentB;
    private AudioManager mAudioManager;
    protected AudioManagerUtils mAudioManagerUtils;
    private View mBbrightnessLayout;
    private ProgressBar mBrightnessSeekbar;
    private ImageView mBtnLock;
    public Context mContext;
    protected LiveFullControllerBar mFullController;
    protected LiveHalfControllerBar mHalfController;
    private boolean mIsPanoramaVideo;
    private int mLaunchMode;
    public LiveBarrageSentCallback mLiveBarrageSentCallback;
    protected boolean mLock;
    private int mMaxBrightness;
    private int mMaxVolume;
    private int mOldBrightness;
    private int mOldVolume;
    private boolean mOnlyFull;
    protected PlayActivityCallback mPlayCallback;
    protected LetvPlayGestureLayout mPlayGestrue;
    private ViewGroup mPlayLower;
    private ViewGroup mPlayUpper;
    private View mProgressLayout;
    private ImageView mVolumeIcon;
    private View mVolumeLayout;
    private ProgressBar mVolumeSeekbar;

    public abstract void cancelLongTimeWatch();

    public abstract void changeDirection(boolean z);

    public abstract void curVolume(int i, int i2);

    public abstract void curVolume(int i, int i2, boolean z);

    public abstract void format();

    public abstract int getPlayLevel();

    public abstract void initData();

    public abstract void initViews();

    public abstract boolean isDlnaPlaying();

    public abstract void lockScreenPause();

    public abstract void onActivityResultLoginSuccess();

    public abstract void onActivityResultPaySuccess(boolean z);

    public abstract void onDestroy();

    public abstract void onResume();

    public abstract void onShareActivityResult(int i, int i2, Intent intent);

    public abstract void onStop();

    public abstract void startFlow();

    public abstract void startLongWatchCountDown();

    public abstract void unLockSceenResume();

    public BasePlayController(Context context, PlayActivityCallback playCallback) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mLaunchMode = 0;
        this.mLiveBarrageSentCallback = null;
        this.currentB = 0;
        this.mContext = context;
        this.mPlayCallback = playCallback;
    }

    public void create() {
        initData();
        initPulicViews();
        initViews();
        initSoundState();
        startFlow();
    }

    public void onSingleTapUp() {
        if (UIsUtils.isLandscape(getActivity())) {
            if (this.mFullController != null) {
                this.mFullController.clickShowAndHide();
                if (getActivity() != null && this.mPlayCallback.isPlaying()) {
                    startLongWatchCountDown();
                }
            }
        } else if (this.mHalfController != null) {
            this.mHalfController.clickShowAndHide();
        }
    }

    private void delayHide() {
        if (UIsUtils.isLandscape(getActivity())) {
            if (this.mFullController != null) {
                this.mFullController.show();
            }
        } else if (this.mHalfController != null) {
            this.mHalfController.show();
        }
        if (getActivity() != null && this.mPlayCallback.isPlaying()) {
            startLongWatchCountDown();
        }
    }

    private void initPulicViews() {
        this.mPlayUpper = (ViewGroup) getActivity().findViewById(2131361905);
        this.mPlayLower = (ViewGroup) getActivity().findViewById(R.id.play_lower);
        this.mPlayGestrue = (LetvPlayGestureLayout) getActivity().findViewById(2131363812);
        this.mBbrightnessLayout = getActivity().findViewById(2131363814);
        this.mVolumeLayout = getActivity().findViewById(2131363821);
        this.mBrightnessSeekbar = (ProgressBar) getActivity().findViewById(2131364025);
        this.mVolumeSeekbar = (ProgressBar) getActivity().findViewById(2131364029);
        this.mVolumeIcon = (ImageView) getActivity().findViewById(2131364028);
        this.mProgressLayout = getActivity().findViewById(2131362112);
        this.mBtnLock = (ImageView) getActivity().findViewById(2131364024);
        this.mBbrightnessLayout.setVisibility(8);
        this.mVolumeLayout.setVisibility(8);
        this.mProgressLayout.setVisibility(8);
        this.mAudioManagerUtils = new AudioManagerUtils();
        this.mAudioManager = this.mAudioManagerUtils.getAudioManager();
        if (this.mAudioManager.getMode() == -2) {
            this.mAudioManager.setMode(0);
        }
        initVolume(getMaxSoundVolume(), getCurSoundVolume());
        this.mOldBrightness = getScreenBrightness();
        float britness = LetvApplication.getInstance().getBritness();
        if (britness != 0.0f) {
            this.mOldBrightness = (int) (255.0f * britness);
        }
        initBrightness(getMaxBrightness(), this.mOldBrightness);
        this.mPlayGestrue.initializeData(((float) getCurSoundVolume()) / ((float) getMaxSoundVolume()), ((float) this.mOldBrightness) / ((float) getMaxBrightness()));
        this.mPlayGestrue.setLetvPlayGestureCallBack(this, true);
        this.mPlayGestrue.setIsPanorama(this.mIsPanoramaVideo);
        if ((this.mContext instanceof BasePlayActivity) && ((BasePlayActivity) this.mContext).mIsVr) {
            this.mPlayGestrue.setIsVr(true);
        }
        float britnes = LetvApplication.getInstance().getBritness();
        if (britnes != 0.0f) {
            setBrightness(britnes);
        }
        this.mBtnLock.setImageResource(this.mLock ? 2130837743 : 2130837746);
        this.mPlayCallback.setLock(this.mLock);
        this.mBtnLock.setOnClickListener(new 1(this));
    }

    public FragmentActivity getActivity() {
        return (FragmentActivity) this.mContext;
    }

    public void setOnlyFull(boolean isOnlyFull) {
        this.mOnlyFull = isOnlyFull;
    }

    public void setPanoramaVideo(boolean isPanoramaVideo) {
        this.mIsPanoramaVideo = isPanoramaVideo;
    }

    public boolean isOnlyFull() {
        return this.mOnlyFull;
    }

    public boolean isPanoramaVideo() {
        return this.mIsPanoramaVideo;
    }

    public ViewGroup getPlayUper() {
        return this.mPlayUpper;
    }

    public ViewGroup getPlayLower() {
        return this.mPlayLower;
    }

    public int getCurSoundVolume() {
        return this.mAudioManager.getStreamVolume(3);
    }

    public int getMaxSoundVolume() {
        return this.mAudioManager.getStreamMaxVolume(3);
    }

    public int getMaxBrightness() {
        return 255;
    }

    public int getBrightness() {
        float br = getActivity().getWindow().getAttributes().screenBrightness;
        LogInfo.log("clf", "getBrightness....br=" + br);
        if (br < 0.0f) {
            br = 0.1f;
        }
        return (int) (255.0f * br);
    }

    private int getScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = System.getInt(getActivity().getContentResolver(), "screen_brightness");
        } catch (SettingNotFoundException e) {
        }
        return screenBrightness;
    }

    public void initVolume(int max, int cur) {
        if (this.mVolumeSeekbar != null) {
            this.mMaxVolume = max;
            int curPercent = (int) ((((float) cur) / ((float) this.mMaxVolume)) * 100.0f);
            this.mVolumeSeekbar.setProgress(curPercent);
            setVolumeIcon(curPercent);
        }
    }

    public void initBrightness(int max, int cur) {
        if (this.mBrightnessSeekbar != null) {
            this.mMaxBrightness = max;
            this.mBrightnessSeekbar.setProgress((int) ((((float) cur) / ((float) this.mMaxBrightness)) * 100.0f));
        }
    }

    private void setVolumeIcon(int curPercent) {
        if (curPercent == 0) {
            this.mVolumeIcon.setImageResource(2130838975);
        } else if (curPercent > 0 && curPercent <= 50) {
            this.mVolumeIcon.setImageResource(2130838972);
        } else if (curPercent > 50 && curPercent < 100) {
            this.mVolumeIcon.setImageResource(2130838974);
        } else if (curPercent == 100) {
            this.mVolumeIcon.setImageResource(2130838973);
        }
    }

    public boolean changeLockStatus() {
        this.mLock = !this.mLock;
        this.mBtnLock.setImageResource(this.mLock ? 2130837743 : 2130837746);
        if (this.mLock) {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage(DialogMsgConstantId.PLAY_OPERATION_LOCK, 2131100659));
        } else {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage(DialogMsgConstantId.PLAY_OPERATION_UNLOCK, 2131100660));
        }
        this.mPlayCallback.setLock(this.mLock);
        return this.mLock;
    }

    private void initSoundState() {
        curVolume(this.mAudioManager.getStreamMaxVolume(3), this.mAudioManager.getStreamVolume(3));
    }

    public boolean isLock() {
        return this.mLock;
    }

    public void wakeLock() {
        this.mLock = false;
        this.mBtnLock.setImageResource(2130837746);
    }

    public void setBrightness(float value) {
        if (value > 1.0f) {
            value = 1.0f;
        }
        if (value < 0.1f) {
            value = 0.1f;
        }
        LogInfo.log("clf", "setBrightness....value=" + value);
        LayoutParams wl = getActivity().getWindow().getAttributes();
        wl.screenBrightness = value;
        getActivity().getWindow().setAttributes(wl);
        LetvApplication.getInstance().setBritness(value);
    }

    public int setSoundVolume(int value, boolean isShow) {
        int maxValue = this.mAudioManager.getStreamMaxVolume(3);
        if (value >= 0 && value <= maxValue) {
            this.mAudioManager.setStreamVolume(3, value, 0);
            volumeRegulate(isShow, value);
        }
        return maxValue;
    }

    public void volumeRegulate(boolean isShow, int pos) {
        if (isShow && this.mVolumeLayout.getVisibility() != 0) {
            this.mVolumeLayout.setVisibility(0);
            StatisticsUtils.statisticsActionInfo(this.mContext, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, "0", "c66", "1003", 3, null);
        }
        if (this.mVolumeSeekbar != null) {
            int curPercent = (int) ((((float) pos) / ((float) this.mMaxVolume)) * 100.0f);
            this.mVolumeSeekbar.setProgress(curPercent);
            setVolumeIcon(curPercent);
        }
    }

    public void brightnessRegulate(boolean isShow, int pos) {
        if (isShow) {
            LogInfo.log("clf", "...mBbrightnessLayout.getVisibility()=" + this.mBbrightnessLayout.getVisibility());
            if (this.mBbrightnessLayout.getVisibility() != 0) {
                this.mBbrightnessLayout.setVisibility(0);
                StatisticsUtils.statisticsActionInfo(this.mContext, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, "0", "c66", "1002", 2, null);
            }
        }
        if (this.mBrightnessSeekbar != null) {
            this.mBrightnessSeekbar.setProgress((int) ((((float) pos) / ((float) this.mMaxBrightness)) * 100.0f));
        }
    }

    public void setLaunchMode(int launchMode) {
        this.mLaunchMode = launchMode;
    }

    public int getLaunchMode() {
        return this.mLaunchMode;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void onDoubleFingersDown() {
    }

    public void onDoubleFingersUp() {
    }

    public void onDoubleTap() {
    }

    public void onRightScroll(float incremental) {
        int max = getMaxSoundVolume();
        int newVlaue = this.mOldVolume + ((int) (((float) max) * incremental));
        if (newVlaue < 0) {
            newVlaue = 0;
        }
        if (newVlaue > max) {
            newVlaue = max;
        }
        setSoundVolume(newVlaue, true);
        curVolume(max, newVlaue);
    }

    public void onLeftScroll(float incremental) {
        int max = getMaxBrightness();
        if (this.currentB == 0) {
            this.mOldBrightness = getScreenBrightness();
            float bright = LetvApplication.getInstance().getBritness();
            this.mOldBrightness = bright == 0.0f ? this.mOldBrightness : (int) (((float) max) * bright);
            this.currentB = this.mOldBrightness;
        }
        int newVlaue = this.mOldBrightness + ((int) Math.floor((double) ((int) (((float) max) * incremental))));
        LogInfo.log("clf", "onLeftScroll mOldBrightness=" + this.mOldBrightness + ",max * incremental=" + (((float) max) * incremental));
        if (newVlaue < 0) {
            newVlaue = 0;
        }
        if (newVlaue > max) {
            newVlaue = max;
        }
        LogInfo.log("clf", "onLeftScroll newVlaue=" + newVlaue);
        brightnessRegulate(true, newVlaue);
        setBrightness(((float) newVlaue) / ((float) max));
    }

    public void onTouchEventUp() {
        this.mVolumeLayout.setVisibility(8);
        this.mBbrightnessLayout.setVisibility(8);
        this.mProgressLayout.setVisibility(8);
    }

    public void onDown() {
        this.mOldVolume = getCurSoundVolume();
        this.mOldBrightness = getBrightness();
        LogInfo.log("clf", "...onDown...mOldBrightness=" + this.mOldBrightness);
    }

    public int setOneFingertouchInfomation(float begin_x, float begin_y, float end_x, float end_y) {
        if (this.mPlayCallback != null) {
            return this.mPlayCallback.setOneFingertouchInfomation(begin_x, begin_y, end_x, end_y);
        }
        return 0;
    }

    public int setTwoScale(float scale) {
        if (this.mPlayCallback != null) {
            return this.mPlayCallback.setTwoScale(scale);
        }
        return 0;
    }

    public void onLandscapeScroll(float incremental) {
    }

    public void onLandscapeScrollFinish(float incremental) {
    }

    public void onTouch() {
    }

    public void onDownloadStateChange() {
    }

    public void destroyStatisticsInfo() {
    }

    public static BasePlayController getPlayController(Context context, int launchMode, boolean isFull, boolean isPanoramaVideo, PlayActivityCallback playCallback) {
        BasePlayController controller = new PlayLiveController(context, playCallback);
        LogInfo.log("fornia", "播放器是 isFull：" + isFull);
        controller.setOnlyFull(isFull);
        controller.setPanoramaVideo(isPanoramaVideo);
        controller.setLaunchMode(launchMode);
        controller.create();
        return controller;
    }
}
