package com.letv.android.client.album.controller;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.observable.AlbumGestureObservable;
import com.letv.android.client.album.observable.AlbumGestureObservable.ProgressRegulateNotify;
import com.letv.android.client.album.observable.AlbumGestureObservable.VolumeChangeNotify;
import com.letv.android.client.album.view.AlbumPlayFragment;
import com.letv.android.client.commonlib.messagemodel.BarrageConfig.TouchData;
import com.letv.android.client.commonlib.utils.AudioManagerUtils;
import com.letv.android.client.commonlib.view.LetvPlayGestureLayout;
import com.letv.android.client.commonlib.view.LetvPlayGestureLayout.LetvPlayGestureCallBack;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.PlayObservable;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.core.BaseApplication;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import java.util.Observable;
import java.util.Observer;

public class AlbumGestureController implements LetvPlayGestureCallBack, Observer {
    public static final int SCROLL_FULL_TIME = 180000;
    public static final int SCROLL_HALF_TIME = 120000;
    public static final int SCROLL_TIME_10 = 600000;
    private AlbumPlayActivity mActivity;
    private final AlbumPlayFragment mAlbumPlayFragment;
    private final AudioManager mAudioManager;
    private AudioManagerUtils mAudioManagerUtils;
    private View mBrightnessLayout;
    private ProgressBar mBrightnessSeekbar;
    private int mCurrentBrightness = 0;
    private boolean mEnableSeek = true;
    private AlbumGestureObservable mGestureObservable;
    private ProgressBar mGestureProgress;
    private View mInterceptView;
    private boolean mIsDisplayFloatLayer = true;
    private boolean mIsGestureUseful = true;
    protected boolean mIsLocked;
    private int mLastPos = -1;
    private int mMaxBrightness;
    private int mMaxVolume;
    private int mOldBrightness;
    private int mOldVolume;
    private LetvPlayGestureLayout mPlayGesture;
    private ImageView mProgressIcon;
    private View mProgressLayout;
    private TextView mProgressTextView;
    private TextView mTotalTextView;
    private TextView mUnslidableTextView;
    private ImageView mVolumeIcon;
    private View mVolumeLayout;
    private ProgressBar mVolumeSeekbar;

    public AlbumGestureController(AlbumPlayActivity context, AlbumPlayFragment albumPlayFragment, AlbumGestureObservable gestureObservable) {
        this.mActivity = context;
        this.mAlbumPlayFragment = albumPlayFragment;
        this.mAudioManagerUtils = new AudioManagerUtils();
        this.mAudioManager = this.mAudioManagerUtils.getAudioManager();
        this.mGestureObservable = gestureObservable;
        init();
    }

    public void onResume() {
        if (this.mAudioManagerUtils != null) {
            this.mAudioManagerUtils.requestFocus();
        }
    }

    public void onDestroy() {
        if (this.mAudioManagerUtils != null) {
            this.mAudioManagerUtils.abandonFocus();
        }
    }

    public void setGestureUseful(boolean useful) {
        this.mInterceptView.setVisibility(useful ? 8 : 0);
    }

    public void enableGestureUseful() {
        this.mIsGestureUseful = true;
    }

    public void setIsPanorama(boolean isPanorama) {
        this.mPlayGesture.setIsPanorama(isPanorama);
    }

    public void setIsVr(boolean isVr) {
        this.mPlayGesture.setIsVr(isVr);
    }

    public void setEnableSeek(boolean enable) {
        this.mEnableSeek = enable;
    }

    private void init() {
        if (this.mActivity instanceof Activity) {
            Activity activity = this.mActivity;
            this.mPlayGesture = (LetvPlayGestureLayout) activity.findViewById(R.id.play_gestrue);
            this.mBrightnessLayout = activity.findViewById(R.id.brightness_layout);
            this.mVolumeLayout = activity.findViewById(R.id.volume_layout);
            if ((this.mActivity instanceof AlbumPlayActivity) && this.mActivity.mIsPlayingNonCopyright) {
                activity.findViewById(R.id.gesture_brightness_progress).setVisibility(8);
                activity.findViewById(R.id.noncopyright_gesture_brightness_progress).setVisibility(0);
                this.mBrightnessSeekbar = (ProgressBar) activity.findViewById(R.id.noncopyright_gesture_brightness_progress);
                activity.findViewById(R.id.gesture_volume_progress).setVisibility(8);
                activity.findViewById(R.id.noncopyright_gesture_volume_progress).setVisibility(0);
                this.mVolumeSeekbar = (ProgressBar) activity.findViewById(R.id.noncopyright_gesture_volume_progress);
                this.mPlayGesture.findViewById(R.id.gesture_progress).setVisibility(8);
                this.mPlayGesture.findViewById(R.id.noncopyright_gesture_progress).setVisibility(0);
                this.mGestureProgress = (ProgressBar) this.mPlayGesture.findViewById(R.id.noncopyright_gesture_progress);
            } else {
                this.mBrightnessSeekbar = (ProgressBar) activity.findViewById(R.id.gesture_brightness_progress);
                this.mVolumeSeekbar = (ProgressBar) activity.findViewById(R.id.gesture_volume_progress);
                this.mPlayGesture.findViewById(R.id.gesture_progress).setVisibility(0);
                this.mPlayGesture.findViewById(R.id.noncopyright_gesture_progress).setVisibility(8);
                this.mGestureProgress = (ProgressBar) this.mPlayGesture.findViewById(R.id.gesture_progress);
            }
            this.mProgressLayout = this.mPlayGesture.findViewById(R.id.progress_layout);
            this.mProgressIcon = (ImageView) this.mPlayGesture.findViewById(R.id.gesture_progress_icon);
            this.mProgressTextView = (TextView) this.mPlayGesture.findViewById(R.id.progress);
            this.mTotalTextView = (TextView) this.mPlayGesture.findViewById(R.id.total);
            this.mUnslidableTextView = (TextView) this.mPlayGesture.findViewById(R.id.unslidable_text);
            this.mVolumeIcon = (ImageView) activity.findViewById(R.id.gesture_volume_icon);
            this.mInterceptView = activity.findViewById(R.id.gesture_intercept_view);
            this.mUnslidableTextView.setVisibility(8);
            this.mBrightnessLayout.setVisibility(8);
            this.mVolumeLayout.setVisibility(8);
            this.mProgressLayout.setVisibility(8);
            initVolume(getMaxSoundVolume(), getCurSoundVolume());
            this.mOldBrightness = getScreenBrightness();
            float britness = PreferencesManager.getInstance().getBritness();
            if (britness != 0.0f) {
                this.mOldBrightness = (int) (255.0f * britness);
            }
            initBrightness(getMaxBrightness(), this.mOldBrightness);
            this.mPlayGesture.initializeData(((float) getCurSoundVolume()) / ((float) getMaxSoundVolume()), ((float) this.mOldBrightness) / ((float) getMaxBrightness()));
            this.mPlayGesture.setLetvPlayGestureCallBack(this, false);
            new Handler().postDelayed(new 1(this), 200);
            float brightness = BaseApplication.getInstance().getBritness();
            if (brightness != 0.0f) {
                setBrightness(brightness);
            }
            this.mInterceptView.setOnClickListener(new 2(this));
        }
    }

    public void setVolumeLayoutVisibility(boolean isShow) {
        this.mVolumeLayout.setVisibility(isShow ? 0 : 8);
    }

    private void initVolume(int max, int cur) {
        if (this.mVolumeSeekbar != null) {
            this.mMaxVolume = max;
            int curPercent = (int) ((((float) cur) / ((float) this.mMaxVolume)) * 100.0f);
            this.mVolumeSeekbar.setProgress(curPercent);
            setVolumeIcon(curPercent);
        }
    }

    private void setVolumeIcon(int curPercent) {
        if (curPercent == 0) {
            this.mVolumeIcon.setImageResource(R.drawable.mute_normal);
        } else if (curPercent > 0 && curPercent <= 50) {
            this.mVolumeIcon.setImageResource(R.drawable.btn_volume_low_normal);
        } else if (curPercent > 50 && curPercent < 100) {
            this.mVolumeIcon.setImageResource(R.drawable.btn_volume_middle_normal);
        } else if (curPercent == 100) {
            this.mVolumeIcon.setImageResource(R.drawable.btn_volume_high_normal);
        }
    }

    public int getMaxSoundVolume() {
        if (this.mAudioManager == null) {
            return 0;
        }
        return this.mAudioManager.getStreamMaxVolume(3);
    }

    public int getCurSoundVolume() {
        if (this.mAudioManager == null) {
            return 0;
        }
        return this.mAudioManager.getStreamVolume(3);
    }

    private void initSoundState(boolean showSeerbar) {
        int currentValue = this.mAudioManager.getStreamVolume(3);
        this.mActivity.getVideoController().updateVolume(new VolumeChangeNotify(this.mAudioManager.getStreamMaxVolume(3), currentValue, VolumnChangeStyle.NONE, showSeerbar));
        this.mOldVolume = currentValue;
    }

    public int setSoundVolume(float value, boolean isShow) {
        int maxValue = this.mAudioManager.getStreamMaxVolume(3);
        if (value >= 0.0f && value <= ((float) maxValue)) {
            this.mAudioManager.setStreamVolume(3, (int) value, 0);
            volumeRegulate(isShow, value);
        }
        return maxValue;
    }

    private void volumeRegulate(boolean isShow, float pos) {
        if (isShow && this.mVolumeLayout.getVisibility() != 0) {
            this.mVolumeLayout.setVisibility(0);
            LogInfo.LogStatistics("调节声音");
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c66", "1003", 3, null, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, null, null, null, null, null);
        }
        if (this.mVolumeSeekbar != null) {
            int curPercent = (int) (((double) (pos / ((float) this.mMaxVolume))) * 100.0d);
            this.mVolumeSeekbar.setProgress(curPercent);
            setVolumeIcon(curPercent);
        }
    }

    private void initBrightness(int max, int cur) {
        if (this.mBrightnessSeekbar != null) {
            this.mMaxBrightness = max;
            this.mBrightnessSeekbar.setProgress((int) ((((float) cur) / ((float) this.mMaxBrightness)) * 100.0f));
        }
    }

    private int getMaxBrightness() {
        return 255;
    }

    private int getScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = System.getInt(this.mActivity.getContentResolver(), "screen_brightness");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return screenBrightness;
    }

    private void setBrightness(float value) {
        if (value > 1.0f) {
            value = 1.0f;
        }
        if (value < 0.1f) {
            value = 0.1f;
        }
        Activity activity = this.mActivity;
        LayoutParams wl = activity.getWindow().getAttributes();
        wl.screenBrightness = value;
        activity.getWindow().setAttributes(wl);
        BaseApplication.getInstance().setBritness(value);
    }

    private int getCurrBrightness() {
        float br = this.mActivity.getWindow().getAttributes().screenBrightness;
        if (br < 0.0f) {
            br = 0.1f;
        }
        return (int) (255.0f * br);
    }

    private void brightnessRegulate(boolean isShow, int pos) {
        if (isShow && this.mBrightnessLayout.getVisibility() != 0) {
            this.mBrightnessLayout.setVisibility(0);
            LogInfo.LogStatistics("调节亮度");
            StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c66", "1002", 2, null, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, null, null, null, null, null);
        }
        if (this.mBrightnessSeekbar != null) {
            this.mBrightnessSeekbar.setProgress((int) ((((float) pos) / ((float) this.mMaxBrightness)) * 100.0f));
        }
    }

    private void progressRegulate(int curPos, int total) {
        progressRegulate(curPos, total, false, Type.ALBUM);
    }

    private void progressRegulate(int curPos, int total, boolean fromSeekBar, Type type) {
        progressRegulate(curPos, total, fromSeekBar, true, type);
    }

    public void progressRegulate(int curPos, int total, boolean fromSeekBar, boolean isSlidable, Type type) {
        if (!(this.mProgressLayout == null || this.mProgressLayout.getVisibility() == 0 || !this.mIsDisplayFloatLayer)) {
            this.mProgressLayout.setVisibility(0);
            if (!fromSeekBar) {
                LogInfo.LogStatistics("调节进度");
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "c66", "1001", 1, null, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, null, null, null, null, null);
            }
        }
        if (type == Type.ALBUM) {
            if (this.mProgressTextView != null) {
                if (this.mActivity instanceof AlbumPlayActivity) {
                    this.mProgressTextView.setText(StringUtils.timeFormatter(this.mActivity.getMidAdController().getBeginTime((long) curPos)));
                } else {
                    this.mProgressTextView.setText(StringUtils.timeFormatter((long) curPos));
                }
            }
            if (this.mTotalTextView != null) {
                this.mTotalTextView.setText(StringUtils.timeFormatter((long) total));
            }
            this.mGestureProgress.setProgress(curPos);
        }
        this.mGestureProgress.setMax(total);
        boolean forward = false;
        if (type == Type.ALBUM) {
            forward = this.mLastPos == -1 ? this.mAlbumPlayFragment.getVideoView().getCurrentPosition() <= curPos : this.mLastPos != curPos ? this.mLastPos < curPos : this.mAlbumPlayFragment.getVideoView().getDuration() == curPos;
            this.mLastPos = curPos;
        }
        if (isSlidable) {
            this.mUnslidableTextView.setVisibility(8);
        } else {
            if (forward) {
                this.mUnslidableTextView.setText(R.string.unforward);
            } else {
                this.mUnslidableTextView.setText(R.string.unbackward);
            }
            this.mUnslidableTextView.setVisibility(0);
        }
        if (forward) {
            this.mProgressIcon.setImageResource(R.drawable.kuaijin_normal);
        } else {
            this.mProgressIcon.setImageResource(R.drawable.kuaitui_normal);
        }
        if (!fromSeekBar) {
            this.mActivity.getVideoController().updateProgressRegulate(new ProgressRegulateNotify(curPos, total, forward));
        }
    }

    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(AlbumPlayFlowObservable.ON_START_FETCHING, notify)) {
                if ((this.mActivity instanceof AlbumPlayActivity) && this.mActivity.getFlow() != null && !this.mActivity.getFlow().isUseDoublePlayerAndChangeStream()) {
                    this.mIsGestureUseful = false;
                }
            } else if (TextUtils.equals(PlayObservable.ON_HEADSET_PLUG, notify)) {
                initSoundState(true);
            }
        }
    }

    private void startScreenProjection(int type) {
        if (this.mActivity instanceof AlbumPlayActivity) {
            ScreenProjectionController controller = this.mActivity.getScreenProjectionController();
            if (controller == null) {
                controller = new ScreenProjectionController(this.mActivity);
            }
            controller.performScreenProject(type);
        }
    }

    public void onDown() {
        this.mOldVolume = getCurSoundVolume();
        this.mOldBrightness = getCurrBrightness();
    }

    public void onDoubleFingersDown() {
        LogInfo.log("wuxinrong", "双指下滑，缓存到超级电视");
        startScreenProjection(1);
    }

    public void onDoubleFingersUp() {
        LogInfo.log("wuxinrong", "双指上滑，投屏到超级电视");
        startScreenProjection(0);
    }

    public void onSingleTapUp() {
        LogInfo.log("zhuqiao", "单击");
        this.mGestureObservable.notifyObservers(AlbumGestureObservable.ON_CLICK);
    }

    public void onDoubleTap() {
        LogInfo.log("ZSM", "双击");
        this.mGestureObservable.notifyObservers(AlbumGestureObservable.DOUBLE_CLICK);
    }

    public void onRightScroll(float incremental) {
        if (this.mBrightnessLayout.isEnabled() && this.mIsGestureUseful) {
            this.mGestureObservable.notifyObservers(AlbumGestureObservable.ON_GESTURE_CHANGE);
            int max = getMaxSoundVolume();
            float newVlaue = ((float) this.mOldVolume) + (((float) max) * incremental);
            if (newVlaue < 0.0f) {
                newVlaue = 0.0f;
            }
            if (newVlaue > ((float) max)) {
                newVlaue = (float) max;
            }
            setSoundVolume(newVlaue, true);
            this.mActivity.getVideoController().updateVolume(new VolumeChangeNotify(max, (int) newVlaue, VolumnChangeStyle.NONE));
        }
    }

    public void onLeftScroll(float incremental) {
        if (this.mBrightnessLayout.isEnabled() && this.mIsGestureUseful) {
            int max = getMaxBrightness();
            if (this.mCurrentBrightness == 0) {
                this.mOldBrightness = getScreenBrightness();
                float bright = BaseApplication.getInstance().getBritness();
                this.mOldBrightness = bright == 0.0f ? this.mOldBrightness : (int) (((float) max) * bright);
                this.mCurrentBrightness = this.mOldBrightness;
            }
            int newVlaue = this.mOldBrightness + ((int) Math.floor((double) ((int) (((float) max) * incremental))));
            if (newVlaue < 0) {
                newVlaue = 0;
            }
            if (newVlaue > max) {
                newVlaue = max;
            }
            brightnessRegulate(true, newVlaue);
            setBrightness(((float) newVlaue) / ((float) max));
            this.mGestureObservable.notifyObservers(AlbumGestureObservable.ON_GESTURE_CHANGE);
        }
    }

    public void onMiddleSingleFingerUp() {
    }

    public void onMiddleSingleFingerDown() {
    }

    public void onLandscapeScroll(float incremental) {
        if (this.mIsGestureUseful && this.mEnableSeek) {
            int[] result = computeLandscapeScroll(incremental);
            if (result != null && result.length == 2) {
                progressRegulate(result[0], result[1]);
            }
            this.mGestureObservable.notifyObservers(AlbumGestureObservable.ON_GESTURE_CHANGE);
        }
    }

    public void onLandscapeScrollFinish(float incremental) {
        if (this.mIsGestureUseful && this.mEnableSeek) {
            computeLandscapeScrollFinish(incremental);
            this.mGestureObservable.notifyObservers(AlbumGestureObservable.ON_GESTURE_CHANGE);
        }
    }

    public void onTouchEventUp() {
        this.mVolumeLayout.setVisibility(8);
        this.mBrightnessLayout.setVisibility(8);
        this.mProgressLayout.setVisibility(8);
        this.mGestureObservable.notifyObservers(AlbumGestureObservable.ON_TOUCH_EVENT_UP);
        this.mLastPos = -1;
    }

    public void onLongPress() {
    }

    public void onTouch() {
    }

    private int[] computeLandscapeScroll(float incremental) {
        if (!(this.mActivity instanceof AlbumPlayActivity) || this.mActivity.getFlow() == null || this.mActivity.getAlbumPlayFragment() == null || this.mActivity.getAlbumPlayFragment().getVideoView() == null) {
            return null;
        }
        LetvMediaPlayerControl videoView = this.mActivity.getAlbumPlayFragment().getVideoView();
        if (!videoView.isInPlaybackState()) {
            return null;
        }
        this.mActivity.getAlbumPlayFragment().pause();
        this.mActivity.getVideoController().pause();
        this.mActivity.getAlbumPlayFragment().stopHandlerTime();
        int duration = videoView.getDuration();
        int total = 0;
        if (UIsUtils.isLandscape(this.mActivity)) {
            if (duration > 0) {
                total = duration > 600000 ? duration / 5 : duration;
            }
        } else if (duration > 0) {
            total = duration > 600000 ? duration / 10 : duration;
        }
        int newcur = videoView.getCurrentPosition() + ((int) (((float) total) * incremental));
        if (newcur < 0) {
            newcur = 1;
        }
        if (newcur > duration) {
            newcur = duration;
        }
        return new int[]{newcur, duration};
    }

    public void computeLandscapeScrollFinish(float incremental) {
        if (this.mActivity instanceof AlbumPlayActivity) {
            AlbumPlayActivity activity = this.mActivity;
            if (activity.getFlow() != null && activity.getAlbumPlayFragment() != null && activity.getAlbumPlayFragment().getVideoView() != null) {
                activity.getVideoController().start(false);
                if (activity.getRecommendController() != null) {
                    activity.getRecommendController().hideRecommendTipView();
                }
            }
        }
    }

    public int setOneFingertouchInfomation(float begin_x, float begin_y, float end_x, float end_y) {
        if (!(this.mActivity instanceof AlbumPlayActivity)) {
            return 0;
        }
        AlbumPlayActivity activity = this.mActivity;
        if (activity.getAlbumPlayFragment() == null || activity.getAlbumPlayFragment().getVideoView() == null) {
            return 0;
        }
        boolean isPanoramaVideo;
        if ((this.mActivity instanceof AlbumPlayActivity) && this.mActivity.mIsPanoramaVideo) {
            isPanoramaVideo = true;
        } else {
            isPanoramaVideo = false;
        }
        if (isPanoramaVideo) {
            LeMessageManager.getInstance().sendMessageByRx(new LeResponseMessage(301, new TouchData(begin_x, begin_y, end_x, end_y)));
        }
        return activity.getAlbumPlayFragment().getVideoView().setOneFingertouchInfomation(begin_x, begin_y, end_x, end_y);
    }

    public int setTwoScale(float scale) {
        if (this.mActivity instanceof AlbumPlayActivity) {
            AlbumPlayActivity activity = this.mActivity;
            if (!(activity.getAlbumPlayFragment() == null || activity.getAlbumPlayFragment().getVideoView() == null)) {
                return activity.getAlbumPlayFragment().getVideoView().setTwoFingerZoom(scale);
            }
        }
        return 0;
    }
}
