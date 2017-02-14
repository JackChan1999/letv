package com.letv.component.player.fourd.videoview;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.MediaController;
import android.widget.RelativeLayout.LayoutParams;
import com.immersion.hapticmediasdk.HapticContentSDK;
import com.immersion.hapticmediasdk.HapticContentSDKFactory;
import com.letv.component.player.Interface.OnMediaStateTimeListener;
import com.letv.component.player.Interface.OnMediaStateTimeListener.MeidaStateType;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.core.PlayUrl;
import com.letv.component.player.core.PlayUrl.StreamType;
import com.letv.component.player.fourd.LetvMediaPlayerControl4D;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.Tools;
import com.letv.core.constant.LetvConstant;
import com.letv.core.messagebus.config.LeMessageIds;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VideoViewH264mp4_4D extends SurfaceView implements LetvMediaPlayerControl4D {
    public static final int DEFAULT = 0;
    private static int FIRST_SYNC_UPDATE_TIME_MS = 200;
    private static int HAPTICS_RESYNC_INTERVAL_MS = LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA;
    public static final int HAPTIC_ERROR = -1;
    public static final int HAPTIC_INITIALIZED = 1;
    public static final int HAPTIC_UNINITIALIZED = 0;
    private static final String INACCESSIBLE_URL = "INACCESSIBLE_URL";
    private static int INITIAL_RESYNC_INTERVAL_MS = 100;
    private static final String INVALID = "INVALID";
    private static final String MALFORMED_URL = "MALFORMED_URL";
    private static int MEDIA_PREPARE_INTERVAL_MS = 20;
    public static final int MUTED = 1;
    public static final int PAUSED = 2;
    public static final int PAUSED_BUFFERRING_FINISHED = 4;
    public static final int PAUSED_DUE_TO_BUFFERING = 3;
    private static final String PERMISSION_DENIED = "PERMISSION_DENIED";
    public static final int PLAYING = 1;
    private static int PLAY_PAUSE_FADEOUT_TIME_MS = 2000;
    private static int SEEKBAR_UPDATE_DURATION_MS = 200;
    public static final int STATE_ENFORCEMENT = 7;
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PLAYBACK_COMPLETED = 5;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_STOPBACK = 6;
    public static final int STOPPED = 0;
    private static final String SUCCESS = "SUCCESS";
    public static final int UNINITIALIZED = -1;
    private static final String UNSUPPORTED_PROTOCOL = "UNSUPPORTED_PROTOCOL";
    private final int FORWARD_TIME;
    private final int REWIND_TIME;
    private String TAG;
    private Runnable changeResyncInterval;
    private boolean downloadHapt;
    private boolean enforcementPause;
    private boolean enforcementWait;
    private String hapt;
    protected int lastSeekWhenDestoryed;
    private OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener;
    private Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private int mDuration;
    private OnErrorListener mErrorListener;
    private HashMap<Integer, String> mErrorValues;
    private String mHaptFileName;
    private int mHapticState;
    private HapticContentSDK mHaptics;
    private OnInfoListener mInfoListener;
    private boolean mIsMediaBuffering;
    private boolean mIsMediaPreparing;
    private boolean mIsSeeked;
    private MediaController mMediaController;
    private Handler mMediaControlsHandler;
    private boolean mMediaLoaded;
    private MediaPlayer mMediaPlayer;
    private int mMediaState;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnMediaStateTimeListener mOnMediaStateTimeListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private OnVideoViewStateChangeListener mOnVideoViewStateChangeListener;
    private String mPasssword;
    private PlayUrl mPlayerUrl;
    OnPreparedListener mPreparedListener;
    private int mRatioType;
    private int mResyncInterval;
    Callback mSHCallback;
    private int mSavedPos;
    private OnSeekCompleteListener mSeekCompleteListener;
    private int mSeekToPosition;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private String mUserName;
    private int mVideoHeight;
    private int mVideoWidth;
    private int mVolumeState;
    private Runnable resyncHaptics;
    private long time;

    private class MediaPreparer implements Runnable {
        private boolean mIsPrepared;
        private int mProgress;

        public MediaPreparer(boolean prepared, int progress) {
            this.mIsPrepared = prepared;
            this.mProgress = progress;
            VideoViewH264mp4_4D.this.mIsMediaPreparing = true;
        }

        public void run() {
            int curProgress = VideoViewH264mp4_4D.this.getCurrentPosition();
            int delta = curProgress - this.mProgress;
            if (VideoViewH264mp4_4D.this.mMediaState == 3) {
                if (VideoViewH264mp4_4D.this.isHapticUsable()) {
                    VideoViewH264mp4_4D.this.mHaptics.pause();
                }
            } else if ((this.mIsPrepared || delta != 0) && !(this.mIsPrepared && delta == 0)) {
                int returnVal;
                if (this.mIsPrepared) {
                    if (VideoViewH264mp4_4D.this.isHapticUsable()) {
                        if (VideoViewH264mp4_4D.this.mMediaState != 0) {
                            returnVal = VideoViewH264mp4_4D.this.mHaptics.seek(curProgress);
                            if (returnVal != 0) {
                                Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics seek failed with value: " + ((String) VideoViewH264mp4_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264mp4_4D.this.mHaptics.getSDKStatus());
                            }
                        }
                        System.out.println("test prepare:resume");
                        returnVal = VideoViewH264mp4_4D.this.mHaptics.resume();
                        if (returnVal != 0) {
                            Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics resume failed with value: " + ((String) VideoViewH264mp4_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264mp4_4D.this.mHaptics.getSDKStatus());
                        }
                    } else {
                        Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics was not initialized yet.");
                    }
                    VideoViewH264mp4_4D.this.post(VideoViewH264mp4_4D.this.resyncHaptics);
                    VideoViewH264mp4_4D.this.post(VideoViewH264mp4_4D.this.changeResyncInterval);
                    VideoViewH264mp4_4D.this.mMediaState = 1;
                } else {
                    if (!VideoViewH264mp4_4D.this.isHapticUsable()) {
                        Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics was not initialized yet.");
                    } else if (VideoViewH264mp4_4D.this.mMediaLoaded) {
                        returnVal = VideoViewH264mp4_4D.this.mHaptics.play();
                        if (returnVal != 0) {
                            Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics play failed with value: " + ((String) VideoViewH264mp4_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264mp4_4D.this.mHaptics.getSDKStatus());
                        }
                    } else {
                        System.out.println("test mediaprepare else: resume");
                        returnVal = VideoViewH264mp4_4D.this.mHaptics.resume();
                        if (returnVal != 0) {
                            Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics resume failed with value: " + ((String) VideoViewH264mp4_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264mp4_4D.this.mHaptics.getSDKStatus());
                        }
                        VideoViewH264mp4_4D.this.post(VideoViewH264mp4_4D.this.resyncHaptics);
                        VideoViewH264mp4_4D.this.post(VideoViewH264mp4_4D.this.changeResyncInterval);
                    }
                    VideoViewH264mp4_4D.this.mMediaState = 1;
                }
                VideoViewH264mp4_4D.this.mIsSeeked = false;
                VideoViewH264mp4_4D.this.mIsMediaPreparing = false;
            } else {
                VideoViewH264mp4_4D.this.mMediaControlsHandler.postDelayed(this, (long) VideoViewH264mp4_4D.MEDIA_PREPARE_INTERVAL_MS);
            }
        }
    }

    public VideoViewH264mp4_4D(Context context) {
        super(context);
        this.TAG = "VideoViewH264mp4";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.FORWARD_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
        this.REWIND_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mRatioType = -1;
        this.lastSeekWhenDestoryed = 0;
        this.enforcementWait = false;
        this.enforcementPause = false;
        this.mUserName = "0f3b69678cce96c0ee8f6c57e31e1273aa0f280ad5519464f52ef3b874a16b6f";
        this.mPasssword = "immr@letv23";
        this.mHaptFileName = "expendables_3_hapt.hapt";
        this.mIsMediaPreparing = false;
        this.mIsMediaBuffering = false;
        this.time = 0;
        this.mIsSeeked = false;
        this.mMediaLoaded = true;
        this.mResyncInterval = INITIAL_RESYNC_INTERVAL_MS;
        this.downloadHapt = true;
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                LogTag.i("onVideoSizeChanged(), width=" + width + ", heigth=" + height);
                VideoViewH264mp4_4D.this.mVideoWidth = mp.getVideoWidth();
                VideoViewH264mp4_4D.this.mVideoHeight = mp.getVideoHeight();
                if (!(VideoViewH264mp4_4D.this.mVideoWidth == 0 || VideoViewH264mp4_4D.this.mVideoHeight == 0)) {
                    VideoViewH264mp4_4D.this.getHolder().setFixedSize(VideoViewH264mp4_4D.this.mVideoWidth, VideoViewH264mp4_4D.this.mVideoHeight);
                }
                if (VideoViewH264mp4_4D.this.mOnVideoSizeChangedListener != null) {
                    VideoViewH264mp4_4D.this.mOnVideoSizeChangedListener.onVideoSizeChanged(mp, width, height);
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                LogTag.i("onPrepared()");
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264mp4(普通手机videoview)  onPrepared()");
                if (VideoViewH264mp4_4D.this.mOnMediaStateTimeListener != null) {
                    VideoViewH264mp4_4D.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.PREPARED, currentDate);
                }
                VideoViewH264mp4_4D.this.mCurrentState = 2;
                VideoViewH264mp4_4D.this.StateChange(VideoViewH264mp4_4D.this.mCurrentState);
                VideoViewH264mp4_4D videoViewH264mp4_4D = VideoViewH264mp4_4D.this;
                VideoViewH264mp4_4D videoViewH264mp4_4D2 = VideoViewH264mp4_4D.this;
                VideoViewH264mp4_4D.this.mCanSeekForward = true;
                videoViewH264mp4_4D2.mCanSeekBack = true;
                videoViewH264mp4_4D.mCanPause = true;
                if (VideoViewH264mp4_4D.this.mMediaController != null) {
                    VideoViewH264mp4_4D.this.mMediaController.setEnabled(true);
                }
                VideoViewH264mp4_4D.this.mVideoWidth = mp.getVideoWidth();
                VideoViewH264mp4_4D.this.mVideoHeight = mp.getVideoHeight();
                int seekToPosition = VideoViewH264mp4_4D.this.mSeekWhenPrepared;
                if (seekToPosition != 0) {
                    VideoViewH264mp4_4D.this.seekTo(seekToPosition);
                }
                if (VideoViewH264mp4_4D.this.mVideoWidth != 0 && VideoViewH264mp4_4D.this.mVideoHeight != 0) {
                    VideoViewH264mp4_4D.this.getHolder().setFixedSize(VideoViewH264mp4_4D.this.mVideoWidth, VideoViewH264mp4_4D.this.mVideoHeight);
                    if (VideoViewH264mp4_4D.this.mSurfaceWidth == VideoViewH264mp4_4D.this.mVideoWidth && VideoViewH264mp4_4D.this.mSurfaceHeight == VideoViewH264mp4_4D.this.mVideoHeight) {
                        if (VideoViewH264mp4_4D.this.mTargetState == 3) {
                            VideoViewH264mp4_4D.this.start();
                            if (VideoViewH264mp4_4D.this.mMediaController != null) {
                                VideoViewH264mp4_4D.this.mMediaController.show();
                            }
                        } else if (!VideoViewH264mp4_4D.this.isPlaying() && ((seekToPosition != 0 || VideoViewH264mp4_4D.this.getCurrentPosition() > 0) && VideoViewH264mp4_4D.this.mMediaController != null)) {
                            VideoViewH264mp4_4D.this.mMediaController.show(0);
                        }
                    }
                } else if (VideoViewH264mp4_4D.this.mTargetState == 3) {
                    VideoViewH264mp4_4D.this.start();
                }
                if (VideoViewH264mp4_4D.this.mOnPreparedListener != null) {
                    VideoViewH264mp4_4D.this.mOnPreparedListener.onPrepared(mp);
                }
                VideoViewH264mp4_4D.this.palyer4dContrlHandle(seekToPosition);
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                LogTag.i("onCompletion()");
                VideoViewH264mp4_4D.this.mCurrentState = 5;
                VideoViewH264mp4_4D.this.StateChange(VideoViewH264mp4_4D.this.mCurrentState);
                VideoViewH264mp4_4D.this.mTargetState = 5;
                VideoViewH264mp4_4D.this.mCurrentState = 6;
                VideoViewH264mp4_4D.this.StateChange(VideoViewH264mp4_4D.this.mCurrentState);
                if (VideoViewH264mp4_4D.this.mMediaController != null) {
                    VideoViewH264mp4_4D.this.mMediaController.hide();
                }
                if (VideoViewH264mp4_4D.this.mOnCompletionListener != null) {
                    VideoViewH264mp4_4D.this.mOnCompletionListener.onCompletion(mp);
                }
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                LogTag.i("onError(): framework_err=" + framework_err + ", impl_err=" + impl_err);
                VideoViewH264mp4_4D.this.mCurrentState = -1;
                VideoViewH264mp4_4D.this.StateChange(VideoViewH264mp4_4D.this.mCurrentState);
                VideoViewH264mp4_4D.this.mTargetState = -1;
                if (VideoViewH264mp4_4D.this.mMediaController != null) {
                    VideoViewH264mp4_4D.this.mMediaController.hide();
                }
                if (VideoViewH264mp4_4D.this.mOnErrorListener != null) {
                    VideoViewH264mp4_4D.this.mOnErrorListener.onError(mp, framework_err, impl_err);
                }
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + "VideoViewH264mp4(普通手机videoview) 播放出错error, framework_err=" + framework_err + ", impl_err=" + impl_err);
                if (VideoViewH264mp4_4D.this.mOnMediaStateTimeListener != null) {
                    VideoViewH264mp4_4D.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.ERROR, currentDate);
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                VideoViewH264mp4_4D.this.mCurrentBufferPercentage = percent;
                if (VideoViewH264mp4_4D.this.mOnBufferingUpdateListener != null) {
                    VideoViewH264mp4_4D.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new OnSeekCompleteListener() {
            public void onSeekComplete(MediaPlayer mp) {
                LogTag.i("onSeekComplete()");
                if (VideoViewH264mp4_4D.this.mOnSeekCompleteListener != null) {
                    VideoViewH264mp4_4D.this.mOnSeekCompleteListener.onSeekComplete(VideoViewH264mp4_4D.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new OnInfoListener() {
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (VideoViewH264mp4_4D.this.mOnInfoListener != null) {
                    VideoViewH264mp4_4D.this.mOnInfoListener.onInfo(mp, what, extra);
                }
                return false;
            }
        };
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                LogTag.i("mSHCallback:surfaceChanged(), w=" + w + ", h=" + h);
                VideoViewH264mp4_4D.this.mSurfaceWidth = w;
                VideoViewH264mp4_4D.this.mSurfaceHeight = h;
                boolean isValidState;
                if (VideoViewH264mp4_4D.this.mTargetState == 3) {
                    isValidState = true;
                } else {
                    isValidState = false;
                }
                boolean hasValidSize;
                if (VideoViewH264mp4_4D.this.mVideoWidth == w && VideoViewH264mp4_4D.this.mVideoHeight == h) {
                    hasValidSize = true;
                } else {
                    hasValidSize = false;
                }
                if (VideoViewH264mp4_4D.this.mMediaPlayer != null && isValidState && hasValidSize) {
                    if (VideoViewH264mp4_4D.this.mSeekWhenPrepared != 0) {
                        VideoViewH264mp4_4D.this.seekTo(VideoViewH264mp4_4D.this.mSeekWhenPrepared);
                    }
                    VideoViewH264mp4_4D.this.start();
                    if (VideoViewH264mp4_4D.this.mMediaController != null) {
                        VideoViewH264mp4_4D.this.mMediaController.show();
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                LogTag.i("mSHCallback:surfaceCreated()");
                VideoViewH264mp4_4D.this.mSurfaceHolder = holder;
                VideoViewH264mp4_4D.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                LogTag.i("mSHCallback:surfaceDestroyed()");
                VideoViewH264mp4_4D.this.mSurfaceHolder = null;
                if (VideoViewH264mp4_4D.this.mMediaController != null) {
                    VideoViewH264mp4_4D.this.mMediaController.hide();
                }
                VideoViewH264mp4_4D.this.lastSeekWhenDestoryed = VideoViewH264mp4_4D.this.getCurrentPosition();
                VideoViewH264mp4_4D.this.release(true);
            }
        };
        this.resyncHaptics = new Runnable() {
            private int lastPos = 0;
            private boolean stalled = false;

            public synchronized void run() {
                int currPos = VideoViewH264mp4_4D.this.getCurrentPosition();
                if (currPos == this.lastPos || currPos == VideoViewH264mp4_4D.this.mSeekToPosition) {
                    System.out.print("test: haptic sync pausing, currPos=" + currPos + "\n");
                    VideoViewH264mp4_4D.this.mHaptics.pause();
                    this.stalled = true;
                } else if (this.stalled) {
                    System.out.print("test: haptics sync resuming, currPos=" + currPos + "\n");
                    VideoViewH264mp4_4D.this.mHaptics.resume();
                    this.stalled = false;
                }
                this.lastPos = currPos;
                if (VideoViewH264mp4_4D.this.isHapticUsable()) {
                    int returnVal = VideoViewH264mp4_4D.this.mHaptics.update((long) currPos);
                    if (returnVal != 0) {
                        Log.e(VideoViewH264mp4_4D.this.TAG, "Haptics update failed with value: " + ((String) VideoViewH264mp4_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264mp4_4D.this.mHaptics.getSDKStatus());
                    }
                }
                if (VideoViewH264mp4_4D.this.isPlaying()) {
                    VideoViewH264mp4_4D.this.getHandler().removeCallbacks(VideoViewH264mp4_4D.this.resyncHaptics);
                    VideoViewH264mp4_4D.this.postDelayed(VideoViewH264mp4_4D.this.resyncHaptics, (long) VideoViewH264mp4_4D.this.mResyncInterval);
                } else {
                    this.stalled = false;
                }
            }
        };
        this.changeResyncInterval = new Runnable() {
            public void run() {
                if (VideoViewH264mp4_4D.this.getCurrentPosition() > VideoViewH264mp4_4D.FIRST_SYNC_UPDATE_TIME_MS) {
                    VideoViewH264mp4_4D.this.mResyncInterval = VideoViewH264mp4_4D.HAPTICS_RESYNC_INTERVAL_MS;
                } else {
                    VideoViewH264mp4_4D.this.postDelayed(VideoViewH264mp4_4D.this.changeResyncInterval, (long) VideoViewH264mp4_4D.INITIAL_RESYNC_INTERVAL_MS);
                }
            }
        };
        this.mContext = context;
        initVideoView();
    }

    public VideoViewH264mp4_4D(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initVideoView();
    }

    public VideoViewH264mp4_4D(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.TAG = "VideoViewH264mp4";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.FORWARD_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
        this.REWIND_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mRatioType = -1;
        this.lastSeekWhenDestoryed = 0;
        this.enforcementWait = false;
        this.enforcementPause = false;
        this.mUserName = "0f3b69678cce96c0ee8f6c57e31e1273aa0f280ad5519464f52ef3b874a16b6f";
        this.mPasssword = "immr@letv23";
        this.mHaptFileName = "expendables_3_hapt.hapt";
        this.mIsMediaPreparing = false;
        this.mIsMediaBuffering = false;
        this.time = 0;
        this.mIsSeeked = false;
        this.mMediaLoaded = true;
        this.mResyncInterval = INITIAL_RESYNC_INTERVAL_MS;
        this.downloadHapt = true;
        this.mSizeChangedListener = /* anonymous class already generated */;
        this.mPreparedListener = /* anonymous class already generated */;
        this.mCompletionListener = /* anonymous class already generated */;
        this.mErrorListener = /* anonymous class already generated */;
        this.mBufferingUpdateListener = /* anonymous class already generated */;
        this.mSeekCompleteListener = /* anonymous class already generated */;
        this.mInfoListener = /* anonymous class already generated */;
        this.mSHCallback = /* anonymous class already generated */;
        this.resyncHaptics = /* anonymous class already generated */;
        this.changeResyncInterval = /* anonymous class already generated */;
        this.mContext = context;
        initVideoView();
    }

    public void setHaptUrl(String url) {
        this.hapt = url;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            switch (this.mRatioType) {
                case -1:
                    if (this.mVideoWidth * height <= this.mVideoHeight * width) {
                        if (this.mVideoWidth * height < this.mVideoHeight * width) {
                            width = (this.mVideoWidth * height) / this.mVideoHeight;
                            break;
                        }
                    }
                    height = (this.mVideoHeight * width) / this.mVideoWidth;
                    break;
                    break;
                case 1:
                    if (height * 4 <= width * 3) {
                        if (height * 4 < width * 3) {
                            width = (height * 4) / 3;
                            break;
                        }
                    }
                    height = (width * 3) / 4;
                    break;
                    break;
                case 2:
                    if (height * 16 <= width * 9) {
                        if (height * 16 < width * 9) {
                            width = (height * 16) / 9;
                            break;
                        }
                    }
                    height = (width * 9) / 16;
                    break;
                    break;
            }
        }
        setMeasuredDimension(width, height);
    }

    public void adjust(int type) {
        this.mRatioType = type;
        invalateScreenSize();
    }

    private void invalateScreenSize() {
        setLayoutParams((LayoutParams) getLayoutParams());
    }

    public int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
                return Math.min(desiredSize, specSize);
            case 0:
                return desiredSize;
            case 1073741824:
                return specSize;
            default:
                return result;
        }
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        StateChange(this.mCurrentState);
        this.mTargetState = 0;
    }

    public void setVideoPath(String path) {
        this.mPlayerUrl = new PlayUrl();
        this.mPlayerUrl.setVid(-1);
        this.mPlayerUrl.setUrl(path);
        this.mPlayerUrl.setStreamType(StreamType.STREAM_TYPE_UNKNOWN);
        setVideoURI(Uri.parse(path));
    }

    public void setVideoPath(String path, Map<String, String> map) {
    }

    public void setVideoURI(Uri uri) {
        String currentDate = Tools.getCurrentDate();
        LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264mp4(普通手机videoview)  setVideoURI(), url=" + (uri != null ? uri.toString() : "null"), true);
        if (this.mOnMediaStateTimeListener != null) {
            this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.INITPATH, currentDate);
        }
        this.mUri = uri;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
        LogTag.i("setVideoURI(), url=" + (uri != null ? uri.toString() : "null"));
    }

    private void openVideo() {
        if (this.mUri == null || this.mSurfaceHolder == null) {
            setVisibility(0);
            return;
        }
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        this.mContext.sendBroadcast(i);
        release(false);
        try {
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264mp4(普通手机videoview)  创建MediaPlayer对象");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.CREATE, currentDate);
            }
            this.mMediaPlayer = new MediaPlayer();
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mDuration = -1;
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
            this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            this.mMediaPlayer.prepareAsync();
            this.mCurrentState = 1;
            StateChange(this.mCurrentState);
            attachMediaController();
        } catch (IOException ex) {
            LogTag.i("Unable to open content: " + this.mUri + " ,IOException=" + ex);
            this.mCurrentState = -1;
            StateChange(this.mCurrentState);
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        } catch (IllegalArgumentException ex2) {
            LogTag.i("Unable to open content: " + this.mUri + " ,IllegalArgumentException=" + ex2);
            this.mCurrentState = -1;
            StateChange(this.mCurrentState);
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        }
    }

    public void setMediaController(MediaController controller) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            View anchorView;
            this.mMediaController.setMediaPlayer(this);
            if (getParent() instanceof View) {
                anchorView = (View) getParent();
            } else {
                anchorView = this;
            }
            this.mMediaController.setAnchorView(anchorView);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewH264mp4 release()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.RELEASE, currentDateRelease);
            }
            this.mMediaPlayer.release();
            int returnVal = this.mHaptics.stop();
            Log.e(this.TAG, "Haptic dispose " + this.mHaptics.getSDKStatus());
            this.mHaptics.dispose();
            if (returnVal != 0) {
                Log.e(this.TAG, "Haptics stop failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            StateChange(this.mCurrentState);
            if (cleartargetstate) {
                this.mTargetState = 0;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported;
        if (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 82 || keyCode == 5 || keyCode == 6) {
            isKeyCodeSupported = false;
        } else {
            isKeyCodeSupported = true;
        }
        if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
            if (keyCode == 79 || keyCode == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                    return true;
                }
                start();
                this.mMediaController.hide();
                return true;
            } else if (keyCode == 86 && this.mMediaPlayer.isPlaying()) {
                pause();
                this.mMediaController.show();
            } else {
                toggleMediaControlsVisiblity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public int getLastSeekWhenDestoryed() {
        return this.lastSeekWhenDestoryed;
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void start() {
        this.mMediaState = -1;
        this.mVolumeState = 0;
        if (this.mMediaControlsHandler == null) {
            this.mMediaControlsHandler = new Handler();
        }
        if (this.downloadHapt) {
            palyer4dInit();
            return;
        }
        if (!(this.enforcementWait || this.enforcementPause || !isInPlaybackState())) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
            StateChange(this.mCurrentState);
            init4DMediaPreparer();
        }
        this.mTargetState = 3;
        StateChange(7);
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            LogTag.i("pause()");
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
            StateChange(this.mCurrentState);
        }
        this.mTargetState = 4;
    }

    public int getDuration() {
        if (!isInPlaybackState()) {
            this.mDuration = -1;
            LogTag.i("getDuration()=" + this.mDuration);
            return this.mDuration;
        } else if (this.mDuration > 0) {
            return this.mDuration;
        } else {
            this.mDuration = this.mMediaPlayer.getDuration();
            LogTag.i("getDuration()=" + this.mDuration);
            return this.mDuration;
        }
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = 0;
            this.lastSeekWhenDestoryed = 0;
            return;
        }
        this.mSeekWhenPrepared = msec;
        this.lastSeekWhenDestoryed = 0;
    }

    public void forward() {
        seekTo(getCurrentPosition() + LetvConstant.WIDGET_UPDATE_UI_TIME);
    }

    public void rewind() {
        seekTo(getCurrentPosition() - 15000);
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return this.mCurrentState == 4;
    }

    public boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    public boolean canPause() {
        return this.mCanPause;
    }

    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    public boolean isEnforcementWait() {
        return this.enforcementWait;
    }

    public void setEnforcementWait(boolean enforcementWait) {
        this.enforcementWait = enforcementWait;
    }

    public boolean isEnforcementPause() {
        return this.enforcementPause;
    }

    public void setEnforcementPause(boolean enforcementPause) {
        this.enforcementPause = enforcementPause;
    }

    public void stopPlayback() {
        LogTag.i("stopPlayback()");
        StateChange(6);
        if (this.mMediaPlayer != null) {
            String currentDateStop = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateStop + "VideoViewH264mp4 stop()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            this.mMediaPlayer.stop();
            int returnVal = this.mHaptics.stop();
            this.mHaptics.dispose();
            if (returnVal != 0) {
                Log.e(this.TAG, "Haptics stop failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewH264mp4 release()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.RELEASE, currentDateRelease);
            }
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            StateChange(this.mCurrentState);
            this.mTargetState = 0;
            setVisibility(4);
        }
    }

    public View getView() {
        return this;
    }

    private void StateChange(int mCurrentState) {
        LogTag.i("StateChange(), mCurrentState=" + mCurrentState);
        if (this.mOnVideoViewStateChangeListener != null) {
            this.mOnVideoViewStateChangeListener.onChange(mCurrentState);
        }
    }

    public void setVideoViewStateChangeListener(OnVideoViewStateChangeListener videoViewListener) {
        this.mOnVideoViewStateChangeListener = videoViewListener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
        this.mOnBufferingUpdateListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener l) {
        this.mOnSeekCompleteListener = l;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener l) {
        this.mOnVideoSizeChangedListener = l;
    }

    public void setVideoPlayUrl(PlayUrl url) {
        this.mPlayerUrl = url;
        setVideoURI(Uri.parse(this.mPlayerUrl.getUrl()));
    }

    public void setOnAdNumberListener(OnAdNumberListener l) {
    }

    public void setCacheSize(int video_size, int audio_size, int picutureSize, int startpic_size) {
    }

    public void setOnBlockListener(OnBlockListener l) {
    }

    public void setOnMediaStateTimeListener(OnMediaStateTimeListener l) {
        this.mOnMediaStateTimeListener = l;
    }

    public void setOnHardDecodeErrorListener(OnHardDecodeErrorListner l) {
    }

    public void initHapticValues() {
        this.mErrorValues = new HashMap();
        this.mErrorValues.put(Integer.valueOf(0), SUCCESS);
        this.mErrorValues.put(Integer.valueOf(-1), INVALID);
        this.mErrorValues.put(Integer.valueOf(-2), INACCESSIBLE_URL);
        this.mErrorValues.put(Integer.valueOf(-3), PERMISSION_DENIED);
        this.mErrorValues.put(Integer.valueOf(-4), MALFORMED_URL);
        this.mErrorValues.put(Integer.valueOf(-5), UNSUPPORTED_PROTOCOL);
    }

    private boolean isHapticUsable() {
        return this.mHapticState == 1;
    }

    public void palyer4dInit() {
        this.mMediaState = -1;
        this.mVolumeState = 0;
        initHapticValues();
        this.mHaptics = HapticContentSDKFactory.GetNewSDKInstance(0, this.mContext, this.mUserName, this.mPasssword, HapticContentSDK.DEFAULT_DNS);
        if (this.mHaptics == null) {
            Log.e(this.TAG, "Could not get new Haptic SDK Instance!");
            this.mHapticState = -1;
        } else {
            this.mHapticState = 0;
        }
        this.mSavedPos = 0;
        this.mMediaControlsHandler = new Handler();
        if (this.mHapticState == 0) {
            int returnVal = this.mHaptics.openHaptics(this.hapt);
            Log.e(this.TAG, "Haptics returnVal=" + returnVal);
            if (returnVal == 0) {
                this.mHapticState = 1;
                this.downloadHapt = false;
            } else {
                Log.e(this.TAG, "Haptics open failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
        }
        if (!(this.enforcementWait || this.enforcementPause || !isInPlaybackState())) {
            this.mMediaPlayer.start();
            System.out.println("test is start");
            this.mCurrentState = 3;
            StateChange(this.mCurrentState);
            if (this.mMediaLoaded) {
                this.mMediaControlsHandler.post(new MediaPreparer(true, getCurrentPosition()));
            } else {
                this.mMediaControlsHandler.post(new MediaPreparer(false, getCurrentPosition()));
            }
        }
        this.mTargetState = 3;
        StateChange(7);
    }

    public void init4DMediaPreparer() {
        if (this.mMediaLoaded) {
            this.mMediaControlsHandler.post(new MediaPreparer(true, getCurrentPosition()));
        } else {
            this.mMediaControlsHandler.post(new MediaPreparer(false, getCurrentPosition()));
        }
    }

    public void palyer4dContrlHandle(int seekToPosition) {
        postDelayed(this.resyncHaptics, (long) INITIAL_RESYNC_INTERVAL_MS);
        postDelayed(this.changeResyncInterval, (long) INITIAL_RESYNC_INTERVAL_MS);
        this.mSavedPos = seekToPosition;
        if (this.mSavedPos == 0) {
            this.mMediaControlsHandler.post(new MediaPreparer(false, 0));
        } else {
            this.mMediaState = 2;
        }
    }

    public void setHapticMute(boolean disable) {
        int returnVal;
        if (disable) {
            if (isHapticUsable()) {
                returnVal = this.mHaptics.mute();
                if (returnVal == 0) {
                    this.mVolumeState = 1;
                } else {
                    Log.e(this.TAG, "Haptics mute failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
                }
            }
        } else if (isHapticUsable()) {
            returnVal = this.mHaptics.unmute();
            if (returnVal == 0) {
                this.mVolumeState = 0;
            } else {
                Log.e(this.TAG, "Haptics unmute failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
        }
    }
}
