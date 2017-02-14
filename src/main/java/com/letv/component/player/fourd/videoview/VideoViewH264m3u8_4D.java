package com.letv.component.player.fourd.videoview;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
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
import com.letv.component.player.http.HttpRequestManager;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.Tools;
import com.letv.core.constant.LetvConstant;
import com.letv.core.messagebus.config.LeMessageIds;
import com.media.ffmpeg.FFMpegPlayer;
import com.media.ffmpeg.FFMpegPlayer.GLRenderControler;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnDisplayListener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import com.media.ffmpeg.FFMpegPlayer.OnSuccessListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoViewH264m3u8_4D extends GLSurfaceView implements LetvMediaPlayerControl4D {
    private static final int AUDIO_SIZE = 1600;
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
    private static final int PICTURE_SIZE = 90;
    public static final int PLAYING = 1;
    private static int PLAY_PAUSE_FADEOUT_TIME_MS = 2000;
    private static int SEEKBAR_UPDATE_DURATION_MS = 200;
    private static final int STARTPIC_SIZE = 20;
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
    private static final String TAG = "VideoViewH264m3u8";
    public static final int UNINITIALIZED = -1;
    private static final String UNSUPPORTED_PROTOCOL = "UNSUPPORTED_PROTOCOL";
    private static final int VIDEO_SIZE = 400;
    private final int FORWARD_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private final int REWIND_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private int bufferTime = 0;
    private Runnable changeResyncInterval = new Runnable() {
        public void run() {
            if (VideoViewH264m3u8_4D.this.getCurrentPosition() > VideoViewH264m3u8_4D.FIRST_SYNC_UPDATE_TIME_MS) {
                VideoViewH264m3u8_4D.this.mResyncInterval = VideoViewH264m3u8_4D.HAPTICS_RESYNC_INTERVAL_MS;
            } else {
                VideoViewH264m3u8_4D.this.postDelayed(VideoViewH264m3u8_4D.this.changeResyncInterval, (long) VideoViewH264m3u8_4D.INITIAL_RESYNC_INTERVAL_MS);
            }
        }
    };
    private boolean downloadHapt = true;
    private boolean enforcementPause = false;
    private boolean enforcementWait = false;
    private String hapt;
    protected int lastSeekWhenDestoryed = 0;
    private OnAdNumberListener mAdNumberListener = new OnAdNumberListener() {
        public void onAdNumber(FFMpegPlayer mediaPlayer, int number) {
            if (VideoViewH264m3u8_4D.this.mOnAdNumberListener != null) {
                VideoViewH264m3u8_4D.this.mOnAdNumberListener.onAdNumber(mediaPlayer, number);
            }
        }
    };
    private OnBlockListener mBlockListener = new OnBlockListener() {
        public void onBlock(FFMpegPlayer mediaPlayer, int blockinfo) {
            if (VideoViewH264m3u8_4D.this.mOnBlockListener != null) {
                VideoViewH264m3u8_4D.this.mOnBlockListener.onBlock(mediaPlayer, blockinfo);
            }
            if (10001 == blockinfo) {
                VideoViewH264m3u8_4D.this.onPause();
            }
        }
    };
    private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            VideoViewH264m3u8_4D.this.mCurrentBufferPercentage = percent;
            if (VideoViewH264m3u8_4D.this.mOnBufferingUpdateListener != null) {
                VideoViewH264m3u8_4D.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            LogTag.i("onCompletion()");
            VideoViewH264m3u8_4D.this.mCurrentState = 5;
            VideoViewH264m3u8_4D.this.StateChange(VideoViewH264m3u8_4D.this.mCurrentState);
            VideoViewH264m3u8_4D.this.mTargetState = 5;
            VideoViewH264m3u8_4D.this.mCurrentState = 6;
            VideoViewH264m3u8_4D.this.StateChange(VideoViewH264m3u8_4D.this.mCurrentState);
            if (VideoViewH264m3u8_4D.this.mMediaController != null) {
                VideoViewH264m3u8_4D.this.mMediaController.hide();
            }
            if (VideoViewH264m3u8_4D.this.mOnCompletionListener != null) {
                VideoViewH264m3u8_4D.this.mOnCompletionListener.onCompletion(VideoViewH264m3u8_4D.this.mMediaPlayer);
            }
            VideoViewH264m3u8_4D.this.pause();
            VideoViewH264m3u8_4D.this.release(true);
        }
    };
    private Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState = 0;
    private OnDisplayListener mDisplayListener = new OnDisplayListener() {
        public void onDisplay(FFMpegPlayer mediaPlayer) {
            LogTag.i("软解onDisplay()");
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264m3u8(软解m3u8)  第一帧画面时间  onDisplay()");
            if (VideoViewH264m3u8_4D.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8_4D.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.DIAPLAY, currentDate);
            }
            VideoViewH264m3u8_4D.this.mCurrentState = 3;
            VideoViewH264m3u8_4D.this.StateChange(VideoViewH264m3u8_4D.this.mCurrentState);
        }
    };
    private int mDuration;
    private OnErrorListener mErrorListener = new OnErrorListener() {
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            LogTag.i("onError(): framework_err=" + framework_err + ", impl_err=" + impl_err);
            VideoViewH264m3u8_4D.this.mCurrentState = -1;
            VideoViewH264m3u8_4D.this.StateChange(VideoViewH264m3u8_4D.this.mCurrentState);
            VideoViewH264m3u8_4D.this.mTargetState = -1;
            if (VideoViewH264m3u8_4D.this.mMediaController != null) {
                VideoViewH264m3u8_4D.this.mMediaController.hide();
            }
            if (VideoViewH264m3u8_4D.this.mOnErrorListener != null) {
                VideoViewH264m3u8_4D.this.mOnErrorListener.onError(VideoViewH264m3u8_4D.this.mMediaPlayer, framework_err, impl_err);
            }
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + "VideoViewH264m3u8(软解m3u8) 播放出错error, framework_err=" + framework_err + ", impl_err=" + impl_err);
            if (VideoViewH264m3u8_4D.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8_4D.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.ERROR, currentDate);
            }
            LogTag.i("软解失败");
            return true;
        }
    };
    private HashMap<Integer, String> mErrorValues;
    private GLRenderControler mGLRenderControler = new GLRenderControler() {
        public void setGLStartRenderMode() {
            VideoViewH264m3u8_4D.this.onResume();
            VideoViewH264m3u8_4D.this.setRenderMode(1);
        }

        public void setGLStopRenderMode() {
            VideoViewH264m3u8_4D.this.setRenderMode(0);
        }
    };
    private String mHaptFileName = "expendables_3_hapt.hapt";
    private int mHapticState;
    private HapticContentSDK mHaptics;
    private OnInfoListener mInfoListener = new OnInfoListener() {
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (VideoViewH264m3u8_4D.this.mOnInfoListener != null) {
                VideoViewH264m3u8_4D.this.mOnInfoListener.onInfo(mp, what, extra);
            }
            return false;
        }
    };
    private boolean mIsMediaBuffering = false;
    private boolean mIsMediaPreparing = false;
    private boolean mIsSeeked = false;
    private String mLastUrl;
    private MediaController mMediaController;
    private Handler mMediaControlsHandler;
    private boolean mMediaLoaded = true;
    private FFMpegPlayer mMediaPlayer = null;
    private int mMediaState;
    private OnAdNumberListener mOnAdNumberListener;
    private OnBlockListener mOnBlockListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnMediaStateTimeListener mOnMediaStateTimeListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnSuccessListener mOnSuccessListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private String mPasssword = "immr@letv23";
    private PlayUrl mPlayerUrl;
    OnPreparedListener mPreparedListener = new OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            LogTag.i("onPrepared()");
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264m3u8(软解m3u8)  onPrepared()");
            if (VideoViewH264m3u8_4D.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8_4D.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.PREPARED, currentDate);
            }
            VideoViewH264m3u8_4D.this.mCurrentState = 2;
            VideoViewH264m3u8_4D.this.StateChange(VideoViewH264m3u8_4D.this.mCurrentState);
            VideoViewH264m3u8_4D videoViewH264m3u8_4D = VideoViewH264m3u8_4D.this;
            VideoViewH264m3u8_4D videoViewH264m3u8_4D2 = VideoViewH264m3u8_4D.this;
            VideoViewH264m3u8_4D.this.mCanSeekForward = true;
            videoViewH264m3u8_4D2.mCanSeekBack = true;
            videoViewH264m3u8_4D.mCanPause = true;
            if (VideoViewH264m3u8_4D.this.mOnPreparedListener != null) {
                VideoViewH264m3u8_4D.this.mOnPreparedListener.onPrepared(VideoViewH264m3u8_4D.this.mMediaPlayer);
            }
            VideoViewH264m3u8_4D.this.mLastUrl = ((FFMpegPlayer) mp).getLastUrl();
            VideoViewH264m3u8_4D.this.mVersion = ((FFMpegPlayer) mp).getVersion();
            LogTag.i(".so verison=" + VideoViewH264m3u8_4D.this.mVersion);
            if (VideoViewH264m3u8_4D.this.mMediaController != null) {
                VideoViewH264m3u8_4D.this.mMediaController.setEnabled(true);
            }
            int seekToPosition = VideoViewH264m3u8_4D.this.mSeekWhenPrepared;
            if (seekToPosition != 0) {
                VideoViewH264m3u8_4D.this.seekTo(seekToPosition);
            }
            VideoViewH264m3u8_4D.this.mVideoWidth = mp.getVideoWidth();
            VideoViewH264m3u8_4D.this.mVideoHeight = mp.getVideoHeight();
            if (VideoViewH264m3u8_4D.this.mVideoWidth == 0 || VideoViewH264m3u8_4D.this.mVideoHeight == 0) {
                if (VideoViewH264m3u8_4D.this.mTargetState == 3) {
                    VideoViewH264m3u8_4D.this.start();
                }
            } else if (VideoViewH264m3u8_4D.this.mSurfaceWidth != VideoViewH264m3u8_4D.this.mVideoWidth || VideoViewH264m3u8_4D.this.mSurfaceHeight != VideoViewH264m3u8_4D.this.mVideoHeight) {
                VideoViewH264m3u8_4D.this.getHolder().setFixedSize(VideoViewH264m3u8_4D.this.mVideoWidth, VideoViewH264m3u8_4D.this.mVideoHeight);
            } else if (VideoViewH264m3u8_4D.this.mTargetState == 3) {
                VideoViewH264m3u8_4D.this.start();
                if (VideoViewH264m3u8_4D.this.mMediaController != null) {
                    VideoViewH264m3u8_4D.this.mMediaController.show();
                }
            } else if (!VideoViewH264m3u8_4D.this.isPlaying() && ((seekToPosition != 0 || VideoViewH264m3u8_4D.this.getCurrentPosition() > 0) && VideoViewH264m3u8_4D.this.mMediaController != null)) {
                VideoViewH264m3u8_4D.this.mMediaController.show(0);
            }
            VideoViewH264m3u8_4D.this.palyer4dContrlHandle(seekToPosition);
        }
    };
    private int mRatioType = -1;
    private int mResyncInterval = INITIAL_RESYNC_INTERVAL_MS;
    Callback mSHCallback = new Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            LogTag.i("mSHCallback:surfaceChanged(), w=" + w + ", h=" + h);
            VideoViewH264m3u8_4D.this.mSurfaceWidth = w;
            VideoViewH264m3u8_4D.this.mSurfaceHeight = h;
            boolean isValidState;
            if (VideoViewH264m3u8_4D.this.mTargetState == 3) {
                isValidState = true;
            } else {
                isValidState = false;
            }
            boolean hasValidSize;
            if (VideoViewH264m3u8_4D.this.mVideoWidth == w && VideoViewH264m3u8_4D.this.mVideoHeight == h) {
                hasValidSize = true;
            } else {
                hasValidSize = false;
            }
            if (VideoViewH264m3u8_4D.this.mMediaPlayer != null && isValidState && hasValidSize) {
                if (VideoViewH264m3u8_4D.this.mSeekWhenPrepared != 0) {
                    VideoViewH264m3u8_4D.this.seekTo(VideoViewH264m3u8_4D.this.mSeekWhenPrepared);
                }
                VideoViewH264m3u8_4D.this.start();
                if (VideoViewH264m3u8_4D.this.mMediaController != null) {
                    VideoViewH264m3u8_4D.this.mMediaController.show();
                }
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            LogTag.i("mSHCallback:surfaceCreated()");
            if (VideoViewH264m3u8_4D.this.mSurfaceHolder == null) {
                VideoViewH264m3u8_4D.this.mSurfaceHolder = holder;
                VideoViewH264m3u8_4D.this.openVideo();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            LogTag.i("mSHCallback:surfaceDestroyed()");
            VideoViewH264m3u8_4D.this.mSurfaceHolder = null;
            if (VideoViewH264m3u8_4D.this.mMediaController != null) {
                VideoViewH264m3u8_4D.this.mMediaController.hide();
            }
            VideoViewH264m3u8_4D.this.lastSeekWhenDestoryed = VideoViewH264m3u8_4D.this.getCurrentPosition();
            VideoViewH264m3u8_4D.this.release(false);
        }
    };
    private int mSavedPos;
    private OnSeekCompleteListener mSeekCompleteListener = new OnSeekCompleteListener() {
        public void onSeekComplete(MediaPlayer mp) {
            LogTag.i("onSeekComplete()");
            if (VideoViewH264m3u8_4D.this.mOnSeekCompleteListener != null) {
                VideoViewH264m3u8_4D.this.mOnSeekCompleteListener.onSeekComplete(VideoViewH264m3u8_4D.this.mMediaPlayer);
            }
        }
    };
    private int mSeekToPosition;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener = new OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            LogTag.i("onVideoSizeChanged(), width=" + width + ", heigth=" + height);
            VideoViewH264m3u8_4D.this.mVideoWidth = mp.getVideoWidth();
            VideoViewH264m3u8_4D.this.mVideoHeight = mp.getVideoHeight();
            if (!(VideoViewH264m3u8_4D.this.mVideoWidth == 0 || VideoViewH264m3u8_4D.this.mVideoHeight == 0)) {
                VideoViewH264m3u8_4D.this.getHolder().setFixedSize(VideoViewH264m3u8_4D.this.mVideoWidth, VideoViewH264m3u8_4D.this.mVideoHeight);
            }
            if (VideoViewH264m3u8_4D.this.mOnVideoSizeChangedListener != null) {
                VideoViewH264m3u8_4D.this.mOnVideoSizeChangedListener.onVideoSizeChanged(mp, VideoViewH264m3u8_4D.this.mVideoWidth, VideoViewH264m3u8_4D.this.mVideoHeight);
            }
        }
    };
    private OnSuccessListener mSuccessListener = new OnSuccessListener() {
        public void onSuccess() {
            LogTag.i("onSuccess()");
            if (VideoViewH264m3u8_4D.this.mOnSuccessListener != null) {
                VideoViewH264m3u8_4D.this.mOnSuccessListener.onSuccess();
            }
            LogTag.i("软解成功");
        }
    };
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder = null;
    private int mSurfaceWidth;
    private int mTargetState = 0;
    private Uri mUri;
    private String mUserName = "0f3b69678cce96c0ee8f6c57e31e1273aa0f280ad5519464f52ef3b874a16b6f";
    private String mVersion;
    private int mVideoHeight;
    private OnVideoViewStateChangeListener mVideoViewStateChangeListener;
    private int mVideoWidth;
    private int mVolumeState;
    private Runnable resyncHaptics = new Runnable() {
        private int lastPos = 0;
        private boolean stalled = false;

        public synchronized void run() {
            int currPos = VideoViewH264m3u8_4D.this.getCurrentPosition();
            if (currPos == this.lastPos || currPos == VideoViewH264m3u8_4D.this.mSeekToPosition) {
                System.out.print("test: haptic sync pausing, currPos=" + currPos + "\n");
                VideoViewH264m3u8_4D.this.mHaptics.pause();
                this.stalled = true;
            } else if (this.stalled) {
                System.out.print("test: haptics sync resuming, currPos=" + currPos + "\n");
                VideoViewH264m3u8_4D.this.mHaptics.resume();
                this.stalled = false;
            }
            this.lastPos = currPos;
            if (VideoViewH264m3u8_4D.this.isHapticUsable()) {
                int returnVal = VideoViewH264m3u8_4D.this.mHaptics.update((long) currPos);
                if (returnVal != 0) {
                    Log.e(VideoViewH264m3u8_4D.TAG, "Haptics update failed with value: " + ((String) VideoViewH264m3u8_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264m3u8_4D.this.mHaptics.getSDKStatus());
                }
            }
            if (VideoViewH264m3u8_4D.this.isPlaying()) {
                VideoViewH264m3u8_4D.this.getHandler().removeCallbacks(VideoViewH264m3u8_4D.this.resyncHaptics);
                VideoViewH264m3u8_4D.this.postDelayed(VideoViewH264m3u8_4D.this.resyncHaptics, (long) VideoViewH264m3u8_4D.this.mResyncInterval);
            } else {
                this.stalled = false;
            }
        }
    };
    private long time = 0;

    private class MediaPreparer implements Runnable {
        private boolean mIsPrepared;
        private int mProgress;

        public MediaPreparer(boolean prepared, int progress) {
            this.mIsPrepared = prepared;
            this.mProgress = progress;
            VideoViewH264m3u8_4D.this.mIsMediaPreparing = true;
        }

        public void run() {
            int curProgress = VideoViewH264m3u8_4D.this.getCurrentPosition();
            int delta = curProgress - this.mProgress;
            if (VideoViewH264m3u8_4D.this.mMediaState == 3) {
                if (VideoViewH264m3u8_4D.this.isHapticUsable()) {
                    VideoViewH264m3u8_4D.this.mHaptics.pause();
                }
            } else if ((this.mIsPrepared || delta != 0) && !(this.mIsPrepared && delta == 0)) {
                int returnVal;
                if (this.mIsPrepared) {
                    if (VideoViewH264m3u8_4D.this.isHapticUsable()) {
                        if (VideoViewH264m3u8_4D.this.mMediaState != 0) {
                            returnVal = VideoViewH264m3u8_4D.this.mHaptics.seek(curProgress);
                            if (returnVal != 0) {
                                Log.e(VideoViewH264m3u8_4D.TAG, "Haptics seek failed with value: " + ((String) VideoViewH264m3u8_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264m3u8_4D.this.mHaptics.getSDKStatus());
                            }
                        }
                        System.out.println("test prepare:resume");
                        returnVal = VideoViewH264m3u8_4D.this.mHaptics.resume();
                        if (returnVal != 0) {
                            Log.e(VideoViewH264m3u8_4D.TAG, "Haptics resume failed with value: " + ((String) VideoViewH264m3u8_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264m3u8_4D.this.mHaptics.getSDKStatus());
                        }
                    } else {
                        Log.e(VideoViewH264m3u8_4D.TAG, "Haptics was not initialized yet.");
                    }
                    VideoViewH264m3u8_4D.this.post(VideoViewH264m3u8_4D.this.resyncHaptics);
                    VideoViewH264m3u8_4D.this.post(VideoViewH264m3u8_4D.this.changeResyncInterval);
                    VideoViewH264m3u8_4D.this.mMediaState = 1;
                } else {
                    if (!VideoViewH264m3u8_4D.this.isHapticUsable()) {
                        Log.e(VideoViewH264m3u8_4D.TAG, "Haptics was not initialized yet.");
                    } else if (VideoViewH264m3u8_4D.this.mMediaLoaded) {
                        returnVal = VideoViewH264m3u8_4D.this.mHaptics.play();
                        if (returnVal != 0) {
                            Log.e(VideoViewH264m3u8_4D.TAG, "Haptics play failed with value: " + ((String) VideoViewH264m3u8_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264m3u8_4D.this.mHaptics.getSDKStatus());
                        }
                    } else {
                        System.out.println("test mediaprepare else: resume");
                        returnVal = VideoViewH264m3u8_4D.this.mHaptics.resume();
                        if (returnVal != 0) {
                            Log.e(VideoViewH264m3u8_4D.TAG, "Haptics resume failed with value: " + ((String) VideoViewH264m3u8_4D.this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + VideoViewH264m3u8_4D.this.mHaptics.getSDKStatus());
                        }
                        VideoViewH264m3u8_4D.this.post(VideoViewH264m3u8_4D.this.resyncHaptics);
                        VideoViewH264m3u8_4D.this.post(VideoViewH264m3u8_4D.this.changeResyncInterval);
                    }
                    VideoViewH264m3u8_4D.this.mMediaState = 1;
                }
                VideoViewH264m3u8_4D.this.mIsSeeked = false;
                VideoViewH264m3u8_4D.this.mIsMediaPreparing = false;
            } else {
                VideoViewH264m3u8_4D.this.mMediaControlsHandler.postDelayed(this, (long) VideoViewH264m3u8_4D.MEDIA_PREPARE_INTERVAL_MS);
            }
        }
    }

    class MyRenderer implements Renderer {
        public float avgFPS = 0.0f;
        public long fpsTime = 0;
        public long frameTime = 0;
        public short framerate = (short) 0;
        public int lastH = 0;
        public int lastW = 0;
        public long time = 0;

        MyRenderer() {
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig c) {
            LogTag.i("MyRenderer:onSurfaceCreated()");
            this.lastW = 0;
            this.lastH = 0;
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
            try {
                VideoViewH264m3u8_4D.this.mSurfaceHeight = h;
                VideoViewH264m3u8_4D.this.mSurfaceWidth = w;
                LogTag.i("MyRenderer:onSurfaceChanged(), w=" + w + ", h=" + h + ", lastW=" + this.lastW + ", lastH=" + this.lastH);
                if (VideoViewH264m3u8_4D.this.mMediaPlayer == null) {
                    return;
                }
                if (this.lastW != w || this.lastH != h) {
                    gl.glViewport(0, 0, w, h);
                    VideoViewH264m3u8_4D.this.mMediaPlayer.native_gl_resize(w, h);
                    this.lastW = w;
                    this.lastH = h;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onDrawFrame(GL10 gl) {
            try {
                if (VideoViewH264m3u8_4D.this.mMediaPlayer != null && VideoViewH264m3u8_4D.this.mMediaPlayer.isPlaying()) {
                    VideoViewH264m3u8_4D.this.mMediaPlayer.native_gl_render();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public VideoViewH264m3u8_4D(Context context) {
        super(context);
        this.mContext = context;
        initVideoView();
    }

    public VideoViewH264m3u8_4D(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initVideoView();
    }

    public void setHaptUrl(String url) {
        this.hapt = url;
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setEGLConfigChooser(5, 6, 5, 0, 16, 0);
        setRenderer(new MyRenderer());
        onPause();
        getHolder().addCallback(this.mSHCallback);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        StateChange(this.mCurrentState);
        this.mTargetState = 0;
    }

    public void setVideoPath(String videoPath) {
        this.mPlayerUrl = new PlayUrl();
        this.mPlayerUrl.setVid(-1);
        this.mPlayerUrl.setStreamType(StreamType.STREAM_TYPE_UNKNOWN);
        this.mPlayerUrl.setUrl(videoPath);
        setVideoURI(Uri.parse(videoPath));
    }

    public void setVideoPath(String path, Map<String, String> map) {
    }

    public void setVideoURI(Uri uri) {
        String currentDate = Tools.getCurrentDate();
        LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264m3u8(软解m3u8)  setVideoURI(), url=" + (uri != null ? uri.toString() : "null"), true);
        if (this.mOnMediaStateTimeListener != null) {
            this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.INITPATH, currentDate);
        }
        this.mTargetState = 0;
        this.mUri = uri;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
        LogTag.i("setVideoURI(), url=" + (uri != null ? uri.toString() : "null"));
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

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
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

    public String getSkipLastURL() {
        return this.mLastUrl;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public int getViewWidth() {
        return getLayoutParams().width;
    }

    public int getViewHeight() {
        return getLayoutParams().height;
    }

    public void start() {
        HttpRequestManager.getInstance(this.mContext).requestCapability();
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
            LogTag.i("软解开始播放");
            this.mMediaPlayer.start();
            init4DMediaPreparer();
        }
        this.mTargetState = 3;
        StateChange(7);
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            LogTag.i("pause()");
            this.mMediaPlayer.pause();
            onPause();
            this.mCurrentState = 4;
            StateChange(this.mCurrentState);
        }
        this.mTargetState = 4;
    }

    public void seekTo(int mesc) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(mesc);
            this.mSeekWhenPrepared = 0;
            this.lastSeekWhenDestoryed = 0;
            return;
        }
        this.mSeekWhenPrepared = mesc;
        this.lastSeekWhenDestoryed = 0;
    }

    public void forward() {
        seekTo(getCurrentPosition() + LetvConstant.WIDGET_UPDATE_UI_TIME);
    }

    public void rewind() {
        seekTo(getCurrentPosition() - 15000);
    }

    public void stopPlayback() {
        stopPlayback(false);
    }

    public void stopPlayback(boolean isRemoveCallBack) {
        LogTag.i("stopPlayback()");
        StateChange(6);
        if (this.mMediaPlayer != null) {
            String currentDateStop = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateStop + "VideoViewH264m3u8 stop()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            try {
                this.mMediaPlayer.stop();
            } catch (Exception e) {
                LogTag.i(TAG, "native player has already null");
            }
            int returnVal = this.mHaptics.stop();
            this.mHaptics.dispose();
            if (returnVal != 0) {
                Log.e(TAG, "Haptics stop failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
            if (isRemoveCallBack) {
                getHolder().removeCallback(this.mSHCallback);
            }
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewH264m3u8 release()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.RELEASE, currentDateRelease);
            }
            try {
                this.mMediaPlayer.release();
            } catch (Exception e2) {
                LogTag.i(TAG, "native player has already null");
            }
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            StateChange(this.mCurrentState);
            this.mTargetState = 0;
            setVisibility(4);
        }
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

    public boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    private void openVideo() {
        if (this.mUri == null || this.mSurfaceHolder == null) {
            setVisibility(0);
            return;
        }
        release(false);
        try {
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264m3u8(软解m3u8)  创建FFMpegPlayer对象");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.CREATE, currentDate);
            }
            this.mMediaPlayer = new FFMpegPlayer();
            this.mMediaPlayer.setHardwareDecode(0);
            onResume();
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mDuration = -1;
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnSuccessListener(this.mSuccessListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
            this.mMediaPlayer.setOnAdNumberListener(this.mAdNumberListener);
            this.mMediaPlayer.setOnBlockListener(this.mBlockListener);
            this.mMediaPlayer.setOnDisplayListener(this.mDisplayListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setRenderControler(this.mGLRenderControler);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
            this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.setScreenOnWhilePlaying(true);
            this.mMediaPlayer.prepareAsync();
            this.mCurrentState = 1;
            attachMediaController();
        } catch (IllegalStateException ex) {
            LogTag.i("Unable to open content: " + this.mUri + " ,IllegalArgumentException=" + ex);
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
        } catch (IOException e) {
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

    private void setVideoViewScale(int width, int height) {
        LayoutParams lp = (LayoutParams) getLayoutParams();
        lp.height = height;
        lp.width = width;
        setLayoutParams(lp);
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            String currentDateStop = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateStop + "VideoViewH264m3u8 stop()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            this.mMediaPlayer.stop();
            int returnVal = this.mHaptics.stop();
            Log.e(TAG, "Haptic dispose " + this.mHaptics.getSDKStatus());
            this.mHaptics.dispose();
            if (returnVal != 0) {
                Log.e(TAG, "Haptics stop failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewH264m3u8 release()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.RELEASE, currentDateRelease);
            }
            this.mMediaPlayer.release();
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

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return this.mCurrentState == 4;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener l) {
        this.mOnSeekCompleteListener = l;
    }

    public void setOnAdNumberListener(OnAdNumberListener l) {
        this.mOnAdNumberListener = l;
    }

    public int getLastSeekWhenDestoryed() {
        return this.lastSeekWhenDestoryed;
    }

    public int getAudioSessionId() {
        return 0;
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

    public View getView() {
        return this;
    }

    private void StateChange(int mCurrentState) {
        LogTag.i("StateChange(), mCurrentState=" + mCurrentState);
        if (this.mVideoViewStateChangeListener != null) {
            this.mVideoViewStateChangeListener.onChange(mCurrentState);
        }
    }

    public void setVideoViewStateChangeListener(OnVideoViewStateChangeListener listener) {
        this.mVideoViewStateChangeListener = listener;
    }

    public void setOnInfoListener(OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener l) {
        this.mOnVideoSizeChangedListener = l;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
        this.mOnBufferingUpdateListener = l;
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnSuccessListener(OnSuccessListener l) {
        this.mOnSuccessListener = l;
    }

    public void setVideoPlayUrl(PlayUrl url) {
        this.mPlayerUrl = url;
        setVideoURI(Uri.parse(this.mPlayerUrl.getUrl()));
    }

    public void setCacheSize(int video_size, int audio_size, int picture_size, int startpic_size) {
    }

    public void setOnBlockListener(OnBlockListener l) {
        this.mOnBlockListener = l;
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
            Log.e(TAG, "Could not get new Haptic SDK Instance!");
            this.mHapticState = -1;
        } else {
            this.mHapticState = 0;
        }
        this.mSavedPos = 0;
        this.mMediaControlsHandler = new Handler();
        if (this.mHapticState == 0) {
            int returnVal = this.mHaptics.openHaptics(this.hapt);
            if (returnVal == 0) {
                this.mHapticState = 1;
                this.downloadHapt = false;
            } else {
                Log.e(TAG, "Haptics open failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
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
                    Log.e(TAG, "Haptics mute failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
                }
            }
        } else if (isHapticUsable()) {
            returnVal = this.mHaptics.unmute();
            if (returnVal == 0) {
                this.mVolumeState = 0;
            } else {
                Log.e(TAG, "Haptics unmute failed with value: " + ((String) this.mErrorValues.get(Integer.valueOf(returnVal))) + ". Current Haptic SDK state: " + this.mHaptics.getSDKStatus());
            }
        }
    }
}
