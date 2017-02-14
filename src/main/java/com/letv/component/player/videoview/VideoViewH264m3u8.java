package com.letv.component.player.videoview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
import com.letv.component.player.Interface.OnMediaStateTimeListener;
import com.letv.component.player.Interface.OnMediaStateTimeListener.MeidaStateType;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.core.PlayUrl;
import com.letv.component.player.core.PlayUrl.StreamType;
import com.letv.component.player.http.HttpRequestManager;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.PanoramaUtils;
import com.letv.component.player.utils.PointF;
import com.letv.component.player.utils.Tools;
import com.letv.core.constant.LetvConstant;
import com.media.ffmpeg.FFMpegPlayer;
import com.media.ffmpeg.FFMpegPlayer.GLRenderControler;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnCacheListener;
import com.media.ffmpeg.FFMpegPlayer.OnDisplayListener;
import com.media.ffmpeg.FFMpegPlayer.OnFirstPlayLitener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import com.media.ffmpeg.FFMpegPlayer.OnInitGLListener;
import com.media.ffmpeg.FFMpegPlayer.OnSuccessListener;
import com.tencent.open.yyb.TitleBar;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoViewH264m3u8 extends GLSurfaceView implements LetvMediaPlayerControl {
    private static final int AUDIO_SIZE = 1600;
    private static final int PICTURE_SIZE = 90;
    private static final int RELEASE_GL_STATE_ING = 1;
    private static final int RELEASE_GL_STATE_INIT = 0;
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
    public static final int SURFACE_CHANGED_ING = 2;
    public static final int SURFACE_CHANGED_INIT = 1;
    private static final String TAG = "VideoViewH264m3u8";
    private static final int VIDEO_SIZE = 400;
    private final int FORWARD_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private final int RELEASE_BLOCK_TIME = 1000;
    private final int REWIND_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private int bufferTime = 0;
    private boolean enforcementPause = false;
    private boolean enforcementWait = false;
    protected int lastSeekWhenDestoryed = 0;
    private OnAdNumberListener mAdNumberListener = new OnAdNumberListener() {
        public void onAdNumber(FFMpegPlayer mediaPlayer, int number) {
            if (VideoViewH264m3u8.this.mOnAdNumberListener != null) {
                VideoViewH264m3u8.this.mOnAdNumberListener.onAdNumber(mediaPlayer, number);
            }
        }
    };
    private OnBlockListener mBlockListener = new OnBlockListener() {
        public void onBlock(FFMpegPlayer mediaPlayer, int blockinfo) {
            if (VideoViewH264m3u8.this.mOnBlockListener != null) {
                VideoViewH264m3u8.this.mOnBlockListener.onBlock(mediaPlayer, blockinfo);
            }
            if (blockinfo == 10001) {
            }
        }
    };
    private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            VideoViewH264m3u8.this.mCurrentBufferPercentage = percent;
            if (VideoViewH264m3u8.this.mOnBufferingUpdateListener != null) {
                VideoViewH264m3u8.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };
    private OnCacheListener mCacheListener = new OnCacheListener() {
        public void onCache(FFMpegPlayer mediaPlayer, int arg, int percent, long cacherate) {
            if (VideoViewH264m3u8.this.mOnCacheListener != null) {
                VideoViewH264m3u8.this.mOnCacheListener.onCache(mediaPlayer, arg, percent, cacherate);
            }
        }
    };
    private CacheSize mCacheSize;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            LogTag.i("onCompletion()");
            VideoViewH264m3u8.this.mCurrentState = 5;
            VideoViewH264m3u8.this.StateChange(VideoViewH264m3u8.this.mCurrentState);
            VideoViewH264m3u8.this.mTargetState = 5;
            if (VideoViewH264m3u8.this.mMediaController != null) {
                VideoViewH264m3u8.this.mMediaController.hide();
            }
            if (VideoViewH264m3u8.this.mOnCompletionListener != null) {
                VideoViewH264m3u8.this.mOnCompletionListener.onCompletion(VideoViewH264m3u8.this.mMediaPlayer);
            }
            VideoViewH264m3u8.this.pause();
            VideoViewH264m3u8.this.releaseGL(true, 1);
        }
    };
    private WeakReference<Context> mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState = 0;
    private OnDisplayListener mDisplayListener = new OnDisplayListener() {
        public void onDisplay(FFMpegPlayer mediaPlayer) {
            LogTag.i("软解onDisplay()");
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264m3u8(软解m3u8)  第一帧画面时间  onDisplay()");
            if (VideoViewH264m3u8.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.DIAPLAY, currentDate);
            }
            VideoViewH264m3u8.this.mCurrentState = 3;
            VideoViewH264m3u8.this.StateChange(VideoViewH264m3u8.this.mCurrentState);
        }
    };
    private int mDuration;
    private OnErrorListener mErrorListener = new OnErrorListener() {
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            LogTag.i("onError(): framework_err=" + framework_err + ", impl_err=" + impl_err);
            VideoViewH264m3u8.this.mCurrentState = -1;
            VideoViewH264m3u8.this.StateChange(VideoViewH264m3u8.this.mCurrentState);
            VideoViewH264m3u8.this.mTargetState = -1;
            if (VideoViewH264m3u8.this.mMediaController != null) {
                VideoViewH264m3u8.this.mMediaController.hide();
            }
            if (VideoViewH264m3u8.this.mOnErrorListener != null) {
                VideoViewH264m3u8.this.mOnErrorListener.onError(VideoViewH264m3u8.this.mMediaPlayer, framework_err, impl_err);
            }
            String currentDate = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + "VideoViewH264m3u8(软解m3u8) 播放出错error, framework_err=" + framework_err + ", impl_err=" + impl_err);
            if (VideoViewH264m3u8.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.ERROR, currentDate);
            }
            LogTag.i("软解失败");
            return true;
        }
    };
    private OnFirstPlayLitener mFirstPlayLitener = new OnFirstPlayLitener() {
        public void onFirstPlay(FFMpegPlayer mediaPlayer, long startcosttime) {
            LogTag.i(VideoViewH264m3u8.TAG, "mFirstPlayLitener->onFirstPlay() is running  ");
            if (VideoViewH264m3u8.this.mOnFirstPlayLitener != null) {
                LogTag.i(VideoViewH264m3u8.TAG, "mFirstPlayLitener->onFirstPlay()->mOnFirstPlayLitener.onFirstPlay() is called  " + startcosttime);
                VideoViewH264m3u8.this.mOnFirstPlayLitener.onFirstPlay(mediaPlayer, startcosttime);
            }
        }
    };
    private int mFisrtSetFixedSize = 0;
    private GLRenderControler mGLRenderControler = new GLRenderControler() {
        public void setGLStartRenderMode() {
            VideoViewH264m3u8.this.setRenderMode(1);
        }

        public void setGLStopRenderMode() {
            VideoViewH264m3u8.this.setRenderMode(0);
        }
    };
    private OnInfoListener mInfoListener = new OnInfoListener() {
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (VideoViewH264m3u8.this.mOnInfoListener != null) {
                VideoViewH264m3u8.this.mOnInfoListener.onInfo(mp, what, extra);
            }
            return false;
        }
    };
    private OnInitGLListener mInitGLListener = new OnInitGLListener() {
        public void initGL(int w, int h, int type, int flag, String cmdStr) {
            VideoViewH264m3u8.this.initGL(w, h, type, flag, cmdStr);
        }
    };
    private int mInitPostion = 0;
    private String mLastUrl;
    private MediaController mMediaController;
    private FFMpegPlayer mMediaPlayer = null;
    private MyRenderer mMyRenderer;
    private OnAdNumberListener mOnAdNumberListener;
    private OnBlockListener mOnBlockListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCacheListener mOnCacheListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnFirstPlayLitener mOnFirstPlayLitener;
    private OnInfoListener mOnInfoListener;
    private OnMediaStateTimeListener mOnMediaStateTimeListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnSuccessListener mOnSuccessListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private float mOriginal_gravity_z = 0.0f;
    private PlayUrl mPlayerUrl;
    OnPreparedListener mPreparedListener = new OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            LogTag.i("onPrepared()");
            String currentDate = Tools.getCurrentDate();
            if (((FFMpegPlayer) mp) != VideoViewH264m3u8.this.mMediaPlayer || VideoViewH264m3u8.this.mMediaPlayer == null) {
                LogTag.i(VideoViewH264m3u8.TAG, "The  mMediaPlayer is released already while onPrepared() callbak is running!");
                return;
            }
            if (VideoViewH264m3u8.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.PREPARED, currentDate);
            }
            VideoViewH264m3u8.this.mCurrentState = 2;
            VideoViewH264m3u8.this.StateChange(VideoViewH264m3u8.this.mCurrentState);
            VideoViewH264m3u8 videoViewH264m3u8 = VideoViewH264m3u8.this;
            VideoViewH264m3u8 videoViewH264m3u82 = VideoViewH264m3u8.this;
            VideoViewH264m3u8.this.mCanSeekForward = true;
            videoViewH264m3u82.mCanSeekBack = true;
            videoViewH264m3u8.mCanPause = true;
            if (VideoViewH264m3u8.this.mOnPreparedListener != null) {
                VideoViewH264m3u8.this.mOnPreparedListener.onPrepared(VideoViewH264m3u8.this.mMediaPlayer);
            }
            VideoViewH264m3u8.this.mLastUrl = ((FFMpegPlayer) mp).getLastUrl();
            VideoViewH264m3u8.this.mVersion = ((FFMpegPlayer) mp).getVersion();
            LogTag.i(".so verison=" + VideoViewH264m3u8.this.mVersion);
            if (VideoViewH264m3u8.this.mMediaController != null) {
                VideoViewH264m3u8.this.mMediaController.setEnabled(true);
            }
            int seekToPosition = VideoViewH264m3u8.this.mSeekWhenPrepared;
            if (seekToPosition != 0) {
                VideoViewH264m3u8.this.seekTo(seekToPosition);
            }
            VideoViewH264m3u8.this.mVideoWidth = mp.getVideoWidth();
            VideoViewH264m3u8.this.mVideoHeight = mp.getVideoHeight();
            if (VideoViewH264m3u8.this.mVideoWidth == 0 || VideoViewH264m3u8.this.mVideoHeight == 0) {
                if (VideoViewH264m3u8.this.mTargetState == 3) {
                    VideoViewH264m3u8.this.start();
                }
            } else if (VideoViewH264m3u8.this.mSurfaceWidth == VideoViewH264m3u8.this.mVideoWidth && VideoViewH264m3u8.this.mSurfaceHeight == VideoViewH264m3u8.this.mVideoHeight) {
                if (VideoViewH264m3u8.this.mTargetState == 3) {
                    VideoViewH264m3u8.this.start();
                    if (VideoViewH264m3u8.this.mMediaController != null) {
                        VideoViewH264m3u8.this.mMediaController.show();
                    }
                } else if (!VideoViewH264m3u8.this.isPlaying() && ((seekToPosition != 0 || VideoViewH264m3u8.this.getCurrentPosition() > 0) && VideoViewH264m3u8.this.mMediaController != null)) {
                    VideoViewH264m3u8.this.mMediaController.show(0);
                }
                VideoViewH264m3u8.this.getHolder().setFixedSize(VideoViewH264m3u8.this.mVideoWidth, VideoViewH264m3u8.this.mVideoHeight);
                VideoViewH264m3u8.this.mFisrtSetFixedSize = 1;
                LogTag.i("onPrepared(1)->setFixedSize(), mVideoWidth=" + VideoViewH264m3u8.this.mVideoWidth + ", mVideoHeight=" + VideoViewH264m3u8.this.mVideoHeight);
            } else {
                VideoViewH264m3u8.this.getHolder().setFixedSize(VideoViewH264m3u8.this.mVideoWidth, VideoViewH264m3u8.this.mVideoHeight);
                VideoViewH264m3u8.this.mFisrtSetFixedSize = 1;
                LogTag.i("onPrepared(2)->setFixedSize(), mVideoWidth=" + VideoViewH264m3u8.this.mVideoWidth + ", mVideoHeight=" + VideoViewH264m3u8.this.mVideoHeight);
            }
        }
    };
    private int mRatioType = -1;
    private int mReleaseGLState = 0;
    private Handler mReleaseMediaPlayerHandler = new Handler();
    Callback mSHCallback = new Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            LogTag.i(VideoViewH264m3u8.TAG, "mSHCallback:surfaceChanged(), w=" + w + ", h=" + h);
            VideoViewH264m3u8.this.mSurfaceWidth = w;
            VideoViewH264m3u8.this.mSurfaceHeight = h;
            boolean isValidState;
            if (VideoViewH264m3u8.this.mTargetState == 3) {
                isValidState = true;
            } else {
                isValidState = false;
            }
            boolean hasValidSize;
            if (VideoViewH264m3u8.this.mVideoWidth == w && VideoViewH264m3u8.this.mVideoHeight == h) {
                hasValidSize = true;
            } else {
                hasValidSize = false;
            }
            if (VideoViewH264m3u8.this.mMediaPlayer != null && isValidState && hasValidSize) {
                if (VideoViewH264m3u8.this.mSeekWhenPrepared != 0) {
                    VideoViewH264m3u8.this.seekTo(VideoViewH264m3u8.this.mSeekWhenPrepared);
                }
                VideoViewH264m3u8.this.start();
                if (VideoViewH264m3u8.this.mMediaController != null) {
                    VideoViewH264m3u8.this.mMediaController.show();
                }
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            LogTag.i(VideoViewH264m3u8.TAG, "mSHCallback:surfaceCreated()");
            if (VideoViewH264m3u8.this.mSurfaceHolder == null) {
                VideoViewH264m3u8.this.mSurfaceHolder = holder;
                LogTag.i(VideoViewH264m3u8.TAG, "mSHCallback:surfaceCreated()->openVideo()");
                VideoViewH264m3u8.this.openVideo();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            LogTag.i(VideoViewH264m3u8.TAG, "mSHCallback:surfaceDestroyed()");
            VideoViewH264m3u8.this.mSurfaceHolder = null;
            if (VideoViewH264m3u8.this.mMediaController != null) {
                VideoViewH264m3u8.this.mMediaController.hide();
            }
            VideoViewH264m3u8.this.lastSeekWhenDestoryed = VideoViewH264m3u8.this.getCurrentPosition();
            VideoViewH264m3u8.this.releaseGL(false, 1);
        }
    };
    private OnSeekCompleteListener mSeekCompleteListener = new OnSeekCompleteListener() {
        public void onSeekComplete(MediaPlayer mp) {
            LogTag.i("onSeekComplete()");
            if (VideoViewH264m3u8.this.mOnSeekCompleteListener != null) {
                VideoViewH264m3u8.this.mOnSeekCompleteListener.onSeekComplete(VideoViewH264m3u8.this.mMediaPlayer);
            }
        }
    };
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener = new OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            LogTag.i("onVideoSizeChanged(), width=" + width + ", heigth=" + height);
            VideoViewH264m3u8.this.mVideoWidth = mp.getVideoWidth();
            VideoViewH264m3u8.this.mVideoHeight = mp.getVideoHeight();
            if (!(VideoViewH264m3u8.this.mVideoWidth == 0 || VideoViewH264m3u8.this.mVideoHeight == 0)) {
                VideoViewH264m3u8.this.mFisrtSetFixedSize = 1;
                VideoViewH264m3u8.this.getHolder().setFixedSize(VideoViewH264m3u8.this.mVideoWidth, VideoViewH264m3u8.this.mVideoHeight);
                LogTag.i("onVideoSizeChanged()->setFixedSize(), mVideoWidth=" + VideoViewH264m3u8.this.mVideoWidth + ", mVideoHeight=" + VideoViewH264m3u8.this.mVideoHeight);
            }
            if (VideoViewH264m3u8.this.mOnVideoSizeChangedListener != null) {
                VideoViewH264m3u8.this.mOnVideoSizeChangedListener.onVideoSizeChanged(mp, VideoViewH264m3u8.this.mVideoWidth, VideoViewH264m3u8.this.mVideoHeight);
                LogTag.i("onVideoSizeChanged()-> run user listener, mVideoWidth=" + VideoViewH264m3u8.this.mVideoWidth + ", mVideoHeight=" + VideoViewH264m3u8.this.mVideoHeight);
            }
        }
    };
    private int mSourceType = 0;
    private OnSuccessListener mSuccessListener = new OnSuccessListener() {
        public void onSuccess() {
            LogTag.i("onSuccess()");
            if (VideoViewH264m3u8.this.mOnSuccessListener != null) {
                VideoViewH264m3u8.this.mOnSuccessListener.onSuccess();
            }
            LogTag.i("软解成功");
        }
    };
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder = null;
    private int mSurfaceWidth;
    private int mTargetState = 0;
    private Uri mUri;
    private String mVersion;
    private int mVideoHeight;
    private OnVideoViewStateChangeListener mVideoViewStateChangeListener;
    private int mVideoWidth;
    private int mVolumevalue = 1;
    private SensorEventListener mySensorListener = null;
    private SensorEventListener mySensorListenerG = null;
    private Sensor orientationSensorG = null;
    private boolean releaseOpenGLOK = false;

    private class CacheSize {
        int audio_size;
        int picture_size;
        int startpic_size;
        int video_size;

        public CacheSize(int video_size, int audio_size, int picture_size, int startpic_size) {
            this.video_size = video_size;
            this.audio_size = audio_size;
            this.picture_size = picture_size;
            this.startpic_size = startpic_size;
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
            LogTag.i("MyRenderer:onSurfaceCreated()viewW: " + VideoViewH264m3u8.this.getViewWidth() + "viewH: " + VideoViewH264m3u8.this.getViewHeight());
        }

        public void onSurfaceChanged(GL10 gl, int w, int h) {
            try {
                VideoViewH264m3u8.this.mSurfaceHeight = h;
                VideoViewH264m3u8.this.mSurfaceWidth = w;
                LogTag.i("MyRenderer:onSurfaceChanged(), w=" + w + ", h=" + h + ", lastW=" + this.lastW + ", lastH=" + this.lastH);
                if (VideoViewH264m3u8.this.mMediaPlayer != null && VideoViewH264m3u8.this.mCurrentState >= 2) {
                    if (this.lastW != w || this.lastH != h) {
                        gl.glViewport(0, 0, w, h);
                        VideoViewH264m3u8.this.mMediaPlayer.native_gl_resize(w, h);
                        this.lastW = w;
                        this.lastH = h;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onDrawFrame(GL10 gl) {
            try {
                if (VideoViewH264m3u8.this.mMediaPlayer != null) {
                    VideoViewH264m3u8.this.mMediaPlayer.native_gl_render();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ReleaseGL implements Runnable {
        private boolean mCleartargetstate;
        private int mType;

        public ReleaseGL(boolean cleartargetstate, int type) {
            this.mCleartargetstate = cleartargetstate;
            this.mType = type;
        }

        public void run() {
            LogTag.i(VideoViewH264m3u8.TAG, "ReleaseGL()->run() is running!");
            VideoViewH264m3u8.this.mMediaPlayer.opengl_es_destroy(0);
            String currentDate = Tools.getCurrentDate();
            VideoViewH264m3u8.this.releaseOpenGLOK = true;
            LogTag.i(VideoViewH264m3u8.TAG, "系统当前时间:  " + currentDate + "releaseBlock()  will end  releaseOpenGLOK:" + VideoViewH264m3u8.this.releaseOpenGLOK);
        }
    }

    public VideoViewH264m3u8(Context context) {
        super(context);
        this.mContext = new WeakReference(context);
        initVideoView();
    }

    public VideoViewH264m3u8(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = new WeakReference(context);
        initVideoView();
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setEGLContextClientVersion(2);
        this.mMyRenderer = new MyRenderer();
        setRenderer(this.mMyRenderer);
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
        LogTag.i(TAG, "setVideoPath()->setVideoURI()");
        setVideoURI(Uri.parse(videoPath));
    }

    public void setVideoPath(String path, Map<String, String> map) {
    }

    public void setVideoURI(Uri uri) {
        String currentDate = Tools.getCurrentDate();
        if (this.mOnMediaStateTimeListener != null) {
            this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.INITPATH, currentDate);
        }
        this.mTargetState = 0;
        this.mUri = uri;
        this.mSeekWhenPrepared = 0;
        LogTag.i(TAG, "setVideoURI()->openVideo()");
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
        if (this.mContext.get() != null) {
            HttpRequestManager.getInstance((Context) this.mContext.get()).requestCapability();
            if (this.enforcementWait || this.enforcementPause) {
                StateChange(7);
            } else if (isInPlaybackState()) {
                LogTag.i("软解开始播放");
                this.mMediaPlayer.start();
            }
            this.mTargetState = 3;
        }
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
        LogTag.i(TAG, "stopPlayback()");
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
        }
        releaseGL(false, 2);
    }

    public void stopPlayback(boolean isRemoveCallBack) {
        LogTag.i(TAG, "stopPlayback(boolean isRemoveCallBack)" + (isRemoveCallBack ? "true" : "false"));
        StateChange(6);
        if (this.mMediaPlayer != null) {
            LogTag.i(TAG, "stopPlayback(boolean isRemoveCallBack)" + (isRemoveCallBack ? "true" : "false") + "(mMediaPlayer != null)");
            String currentDateStop = Tools.getCurrentDate();
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            try {
                this.mMediaPlayer.stop();
            } catch (Exception e) {
                LogTag.i(TAG, "native player has already null");
            }
            if (isRemoveCallBack) {
                getHolder().removeCallback(this.mSHCallback);
            }
            String currentDateRelease = Tools.getCurrentDate();
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
                    if (this.mVideoWidth * height < this.mVideoHeight * width) {
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
        setMeasuredDimension(width + 1, height + 1);
    }

    public void adjust(int type) {
        this.mRatioType = type;
        LogTag.i(TAG, "adjust((" + type + ")");
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
                result = Math.min(desiredSize, specSize);
                LogTag.i(TAG, "resolveAdjustedSize(AT_MOST),use " + result);
                return result;
            case 0:
                result = desiredSize;
                LogTag.i(TAG, "resolveAdjustedSize(UNSPECIFIED),use " + result);
                return result;
            case 1073741824:
                result = specSize;
                LogTag.i(TAG, "resolveAdjustedSize(EXACTLY),use " + result);
                return result;
            default:
                return result;
        }
    }

    public boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    private void openVideo() {
        if (this.mUri == null || this.mSurfaceHolder == null || this.mContext.get() == null) {
            setVisibility(0);
            return;
        }
        LogTag.i(TAG, "openVideo()-> releaseGL(false,1)");
        releaseGL(false, 1);
        try {
            String currentDate = Tools.getCurrentDate();
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
            this.mMediaPlayer.setOnCacheListener(this.mCacheListener);
            this.mMediaPlayer.setOnFirstPlayListener(this.mFirstPlayLitener);
            this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
            this.mMediaPlayer.setOnAdNumberListener(this.mAdNumberListener);
            this.mMediaPlayer.setOnBlockListener(this.mBlockListener);
            this.mMediaPlayer.setOnDisplayListener(this.mDisplayListener);
            this.mMediaPlayer.setOnInitListener(this.mInitGLListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setRenderControler(this.mGLRenderControler);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setDataSource((Context) this.mContext.get(), this.mUri);
            this.mMediaPlayer.setInitPosition(this.mInitPostion);
            if (this.mCacheSize != null) {
                this.mMediaPlayer.setCacheSize(this.mCacheSize.video_size, this.mCacheSize.audio_size, this.mCacheSize.picture_size, this.mCacheSize.startpic_size);
            }
            this.mMediaPlayer.setVolume(this.mVolumevalue);
            this.mMediaPlayer.setSourceType(this.mSourceType);
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
        LogTag.i("setVideoViewScale(), width=" + width + ", heigth=" + height);
    }

    private void release(boolean cleartargetstate) {
        LogTag.i(TAG, "release(boolean)" + (cleartargetstate ? "true" : "false"));
        if (this.mMediaPlayer != null) {
            LogTag.i(TAG, "release(boolean)" + (cleartargetstate ? "true" : "false") + "(mMediaPlayer != null)");
            String currentDateStop = Tools.getCurrentDate();
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            this.mMediaPlayer.stop();
            String currentDateRelease = Tools.getCurrentDate();
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
        LogTag.i(TAG, "setVideoPlayUrl()->setVideoURI()");
        setVideoURI(Uri.parse(this.mPlayerUrl.getUrl()));
    }

    public void setCacheSize(int video_size, int audio_size, int picture_size, int startpic_size) {
        if (this.mCacheSize == null) {
            this.mCacheSize = new CacheSize(video_size, audio_size, picture_size, startpic_size);
        }
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setCacheSize(video_size, audio_size, picture_size, startpic_size);
        }
    }

    public void setOnBlockListener(OnBlockListener l) {
        this.mOnBlockListener = l;
    }

    public void setOnCacheListener(OnCacheListener l) {
        this.mOnCacheListener = l;
    }

    public void setOnMediaStateTimeListener(OnMediaStateTimeListener l) {
        this.mOnMediaStateTimeListener = l;
    }

    public void setOnHardDecodeErrorListener(OnHardDecodeErrorListner l) {
    }

    public void setOnFirstPlayListener(OnFirstPlayLitener l) {
        this.mOnFirstPlayLitener = l;
    }

    private void startReleaseTimer() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                LogTag.i(VideoViewH264m3u8.TAG, "系统当前时间:  " + Tools.getCurrentDate() + "releaseBlock()->startReleaseTimer()->TimeTask()  timer out releaseOpenGLOK:" + VideoViewH264m3u8.this.releaseOpenGLOK);
                VideoViewH264m3u8.this.releaseOpenGLOK = true;
            }
        }, 1000);
    }

    private void releaseBlock() {
        String currentDate = Tools.getCurrentDate();
        LogTag.i(TAG, "系统当前时间:  " + currentDate + "releaseBlock() start run,now  releaseOpenGLOK:" + this.releaseOpenGLOK);
        startReleaseTimer();
        while (!this.releaseOpenGLOK) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LogTag.i(TAG, "系统当前时间:  " + currentDate + "releaseBlock()  run over,now  releaseOpenGLOK:" + this.releaseOpenGLOK);
    }

    private void releaseGL(boolean cleartargetstate, int type) {
        LogTag.i(TAG, "releaseGL()" + (cleartargetstate ? "true" : "false") + type);
        if (this.mMediaPlayer == null) {
            return;
        }
        if (this.mReleaseGLState == 0) {
            this.mReleaseGLState = 1;
            LogTag.i(TAG, "releaseGL()" + (cleartargetstate ? "true" : "false") + type + "(mMediaPlayer != null) (mReleaseGLState==RELEASE_GL_STATE_INIT)");
            ReleaseGL releaseGL = new ReleaseGL(cleartargetstate, type);
            this.releaseOpenGLOK = false;
            queueEvent(releaseGL);
            releaseBlock();
            if (type == 1) {
                release(cleartargetstate);
                this.mReleaseGLState = 0;
                return;
            }
            stopPlayback(cleartargetstate);
            this.mReleaseGLState = 0;
        } else if (this.mReleaseGLState == 1) {
            LogTag.i(TAG, "releaseGL()" + (cleartargetstate ? "true" : "false") + type + "(mMediaPlayer != null) (mReleaseGLState==RELEASE_GL_STATE_ING)");
        } else {
            LogTag.i(TAG, "releaseGL()" + (cleartargetstate ? "true" : "false") + type + "(mMediaPlayer != null) (else)");
        }
    }

    private void initGL(int w, int h, int type, int flag, String cmdstr) {
        final int i = w;
        final int i2 = h;
        final int i3 = type;
        final String str = cmdstr;
        queueEvent(new Runnable() {
            public void run() {
                if (VideoViewH264m3u8.this.mMediaPlayer.mSourceType == 0) {
                    VideoViewH264m3u8.this.mMediaPlayer.opengl_es_init(i, i2, i3, 0, str);
                } else if (VideoViewH264m3u8.this.mMediaPlayer.mSourceType == 1) {
                    VideoViewH264m3u8.this.mMediaPlayer.opengl_es_init(i, i2, i3, 1, str);
                } else if (VideoViewH264m3u8.this.mMediaPlayer.mSourceType == 2) {
                    VideoViewH264m3u8.this.mMediaPlayer.opengl_es_init(i, i2, i3, 2, str);
                }
            }
        });
    }

    public void processSensorEvent(SensorEvent event) {
        float gx = event.values[0];
        float gy = event.values[1];
        float gz = event.values[2];
        if (event.sensor.getType() == 9) {
            PanoramaUtils.processGravitySensorInfo(gx, gy, gz);
            this.mOriginal_gravity_z = gz;
        } else if (event.sensor.getType() == 4 && this.mMediaPlayer != null) {
            PointF pt = PanoramaUtils.processGyroscopeInfo(gx, gy, gz);
            if (this.mSourceType == 2) {
                pt.x = ((float) ((Math.acos((double) (this.mOriginal_gravity_z / 9.807f)) * 180.0d) / 3.141592653589793d)) - 90.0f;
            }
            try {
                this.mMediaPlayer.opengl_panorama_Angle(-pt.x, -pt.y, pt.z);
            } catch (Exception e) {
                LogTag.i(TAG, "opengl_panorama_Angle=" + e.getMessage());
            }
        }
    }

    public int setSourceType(int sourceType) {
        this.mSourceType = sourceType;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setSourceType(sourceType);
        }
        return 0;
    }

    public int setMachineInfomation(float ScreenResolution) {
        Log.i(TAG, "setMachineInfomationScreenResolution" + ScreenResolution);
        return 0;
    }

    public int setOneFingertouchInfomation(float begin_x, float begin_y, float end_x, float end_y) {
        int i = 1;
        Log.i(TAG, "setOneFingertouchInfomation:begin_x=" + begin_x + "begin_y=" + begin_y + "end_x=" + end_x + "end_y=" + end_y);
        int i2 = Math.abs(begin_x - end_x) > TitleBar.SHAREBTN_RIGHT_MARGIN ? 1 : 0;
        if (Math.abs(begin_y - end_y) <= TitleBar.SHAREBTN_RIGHT_MARGIN) {
            i = 0;
        }
        if ((i2 | i) != 0) {
            PointF pt = PanoramaUtils.changeCameraDirection(begin_x, begin_y, end_x, end_y);
            if (this.mMediaPlayer == null) {
                return -1;
            }
            try {
                this.mMediaPlayer.opengl_panorama_Angle(-pt.x, -pt.y, pt.z);
                return 0;
            } catch (Exception e) {
                LogTag.i("isplaying", "isplaying=" + e.getMessage());
                return 0;
            }
        }
        Log.i(TAG, "setOneFingertouchInfomation: Do noting");
        return 0;
    }

    public int setTwoFingertouchInfomation(float begin_x0, float begin_y0, float begin_x1, float begin_y1, float end_x0, float end_y0, float end_x1, float end_y1) {
        Log.i(TAG, "setTwoFingertouchInfomation:begin_x0=" + begin_x0 + "begin_y0=" + begin_y0 + "begin_x1=" + begin_x1 + "begin_y1=" + begin_y1 + "end_x0=" + end_x0 + "end_y0=" + end_y0 + "end_x1=" + end_x1 + "end_y1=" + end_y1);
        try {
            this.mMediaPlayer.opengl_panorama_Zoom(PanoramaUtils.processDoubleTouchInfo(begin_x0, begin_y0, begin_x1, begin_y1, end_x0, end_y0, end_x1, end_y1));
        } catch (Exception e) {
            LogTag.i("isplaying", "isplaying=" + e.getMessage());
        }
        return 0;
    }

    public int setgravity_yroInfomation(float gravity_yro_x, float gravity_yro_y, float gravity_yro_z) {
        if (this.mMediaPlayer == null) {
            return -1;
        }
        PointF pt = PanoramaUtils.processGyroscopeInfo(gravity_yro_x, gravity_yro_y, gravity_yro_z);
        if (this.mSourceType == 2) {
            pt.x = ((float) ((Math.acos((double) (this.mOriginal_gravity_z / 9.807f)) * 180.0d) / 3.141592653589793d)) - 90.0f;
        }
        if (PanoramaUtils.isGravityEnable()) {
            try {
                this.mMediaPlayer.opengl_panorama_Angle(-pt.x, -pt.y, pt.z);
            } catch (Exception e) {
                LogTag.i(TAG, "opengl_panorama_Angle=" + e.getMessage());
            }
        }
        return 0;
    }

    public int setGravityInfomation(float gravity_x, float gravity_y, float gravity_z) {
        this.mOriginal_gravity_z = gravity_z;
        float angle_z = PanoramaUtils.processGravitySensorInfo(gravity_x, gravity_y, gravity_z);
        return 0;
    }

    public int setgravity_yroValidInfomation(boolean gravityValid) {
        PanoramaUtils.enableGravity(gravityValid);
        return 0;
    }

    public int setAngleInit() {
        Log.i(TAG, "setAngleInit:");
        return 0;
    }

    public int setTwoFingerZoom(float zoom) {
        if (this.mMediaPlayer == null) {
            return -1;
        }
        try {
            this.mMediaPlayer.opengl_panorama_Zoom(zoom);
        } catch (Exception e) {
            LogTag.i(TAG, "opengl_panorama_Zoom=" + e.getMessage());
        }
        return 0;
    }

    public void setInitPosition(int msec) {
        this.mInitPostion = msec;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setInitPosition(msec);
        }
    }

    public void setVolume(int volume) {
        this.mVolumevalue = volume;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(volume);
        }
    }

    public void setCacheTime(double start_cache, double block_cache) {
    }
}
