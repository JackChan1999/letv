package com.letv.component.player.videoview;

import android.content.Context;
import android.hardware.SensorEvent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.MediaController;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.component.player.Interface.OnMediaStateTimeListener;
import com.letv.component.player.Interface.OnMediaStateTimeListener.MeidaStateType;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.core.PlayUrl;
import com.letv.component.player.core.PlayUrl.StreamType;
import com.letv.component.player.hardwaredecode.CodecWrapper;
import com.letv.component.player.http.HttpRequestManager;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.Tools;
import com.letv.core.constant.LetvConstant;
import com.media.ffmpeg.FFMpegPlayer;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnCacheListener;
import com.media.ffmpeg.FFMpegPlayer.OnDisplayListener;
import com.media.ffmpeg.FFMpegPlayer.OnFirstPlayLitener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import com.media.ffmpeg.FFMpegPlayer.OnSuccessListener;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

public class VideoViewH264m3u8Hw extends SurfaceView implements LetvMediaPlayerControl {
    private static final int AUDIO_SIZE = 1600;
    private static final int PICTURE_SIZE = 90;
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
    private static final String TAG = "VideoViewH264m3u8Hw";
    private static final int VIDEO_SIZE = 400;
    private final int FORWARD_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private final int REWIND_TIME = LetvConstant.WIDGET_UPDATE_UI_TIME;
    private int bufferTime = 0;
    private boolean enforcementPause = false;
    private boolean enforcementWait = false;
    protected int lastSeekWhenDestoryed = 0;
    private OnAdNumberListener mAdNumberListener = new OnAdNumberListener() {
        public void onAdNumber(FFMpegPlayer mediaPlayer, int number) {
            if (VideoViewH264m3u8Hw.this.mOnAdNumberListener != null) {
                VideoViewH264m3u8Hw.this.mOnAdNumberListener.onAdNumber(mediaPlayer, number);
            }
        }
    };
    private OnBlockListener mBlockListener = new OnBlockListener() {
        public void onBlock(FFMpegPlayer mediaPlayer, int blockinfo) {
            if (VideoViewH264m3u8Hw.this.mOnBlockListener != null) {
                VideoViewH264m3u8Hw.this.mOnBlockListener.onBlock(mediaPlayer, blockinfo);
            }
            if (blockinfo == 10001) {
            }
        }
    };
    private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            VideoViewH264m3u8Hw.this.mCurrentBufferPercentage = percent;
            if (VideoViewH264m3u8Hw.this.mOnBufferingUpdateListener != null) {
                VideoViewH264m3u8Hw.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
        }
    };
    private OnCacheListener mCacheListener = new OnCacheListener() {
        public void onCache(FFMpegPlayer mediaPlayer, int arg, int percent, long cacherate) {
            if (VideoViewH264m3u8Hw.this.mOnCacheListener != null) {
                VideoViewH264m3u8Hw.this.mOnCacheListener.onCache(mediaPlayer, arg, percent, cacherate);
            }
        }
    };
    private CacheTime mCacheTime;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private OnCompletionListener mCompletionListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            VideoViewH264m3u8Hw.this.mCurrentState = 5;
            VideoViewH264m3u8Hw.this.StateChange(VideoViewH264m3u8Hw.this.mCurrentState);
            VideoViewH264m3u8Hw.this.mTargetState = 5;
            if (VideoViewH264m3u8Hw.this.mMediaController != null) {
                VideoViewH264m3u8Hw.this.mMediaController.hide();
            }
            if (VideoViewH264m3u8Hw.this.mOnCompletionListener != null) {
                VideoViewH264m3u8Hw.this.mOnCompletionListener.onCompletion(VideoViewH264m3u8Hw.this.mMediaPlayer);
            }
            VideoViewH264m3u8Hw.this.pause();
            VideoViewH264m3u8Hw.this.release(true);
        }
    };
    private WeakReference<Context> mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState = 0;
    private OnDisplayListener mDisplayListener = new OnDisplayListener() {
        public void onDisplay(FFMpegPlayer mediaPlayer) {
            LogTag.i("硬解onDisplay()");
            String currentDate = Tools.getCurrentDate();
            if (VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.DIAPLAY, currentDate);
            }
            VideoViewH264m3u8Hw.this.mCurrentState = 3;
            VideoViewH264m3u8Hw.this.StateChange(VideoViewH264m3u8Hw.this.mCurrentState);
        }
    };
    private int mDuration;
    private OnErrorListener mErrorListener = new OnErrorListener() {
        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            VideoViewH264m3u8Hw.this.mCurrentState = -1;
            VideoViewH264m3u8Hw.this.StateChange(VideoViewH264m3u8Hw.this.mCurrentState);
            VideoViewH264m3u8Hw.this.mTargetState = -1;
            if (VideoViewH264m3u8Hw.this.mMediaController != null) {
                VideoViewH264m3u8Hw.this.mMediaController.hide();
            }
            if (VideoViewH264m3u8Hw.this.mOnErrorListener != null) {
                VideoViewH264m3u8Hw.this.mOnErrorListener.onError(VideoViewH264m3u8Hw.this.mMediaPlayer, framework_err, impl_err);
            }
            String currentDate = Tools.getCurrentDate();
            if (VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.ERROR, currentDate);
            }
            LogTag.i("硬解失败");
            LogTag.i("framework_err:" + framework_err);
            return true;
        }
    };
    private OnFirstPlayLitener mFirstPlayLitener = new OnFirstPlayLitener() {
        public void onFirstPlay(FFMpegPlayer mediaPlayer, long startcosttime) {
            if (VideoViewH264m3u8Hw.this.mOnFirstPlayLitener != null) {
                VideoViewH264m3u8Hw.this.mOnFirstPlayLitener.onFirstPlay(mediaPlayer, startcosttime);
            }
        }
    };
    private OnHardDecodeErrorListner mHardDecodeErrorListner = new OnHardDecodeErrorListner() {
        public void onError(FFMpegPlayer mediaPlayer, int arg1, int arg2) {
            LogTag.i("OnHardDecodeErrorListner.onError(): framework_err=" + arg1 + ", impl_err=" + arg2);
            String currentDate = Tools.getCurrentDate();
            if (VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.HARD_ERROR, currentDate);
            }
            if (VideoViewH264m3u8Hw.this.mOnHardDecodeErrorListner != null) {
                VideoViewH264m3u8Hw.this.mOnHardDecodeErrorListner.onError(mediaPlayer, arg1, arg2);
            }
            if (VideoViewH264m3u8Hw.this.mOnErrorListener != null) {
                VideoViewH264m3u8Hw.this.mOnErrorListener.onError(mediaPlayer, arg1, arg2);
            }
            if (VideoViewH264m3u8Hw.this.mContext.get() != null) {
                HttpRequestManager.getInstance((Context) VideoViewH264m3u8Hw.this.mContext.get()).hardDecodeReport(VideoViewH264m3u8Hw.this.mPlayerUrl.mVid, VideoViewH264m3u8Hw.this.mUri.toString(), 0, arg1, VideoViewH264m3u8Hw.this.mPlayerUrl.mStreamType, 1);
            }
        }
    };
    private OnInfoListener mInfoListener = new OnInfoListener() {
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            LogTag.i("onInfo+++" + what + "extra+++" + extra);
            if (VideoViewH264m3u8Hw.this.mOnInfoListener != null) {
                VideoViewH264m3u8Hw.this.mOnInfoListener.onInfo(mp, what, extra);
            }
            return false;
        }
    };
    private int mInitPostion = 0;
    private String mLastUrl;
    private MediaController mMediaController;
    private FFMpegPlayer mMediaPlayer = null;
    private OnAdNumberListener mOnAdNumberListener;
    private OnBlockListener mOnBlockListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCacheListener mOnCacheListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnFirstPlayLitener mOnFirstPlayLitener;
    private OnHardDecodeErrorListner mOnHardDecodeErrorListner;
    private OnInfoListener mOnInfoListener;
    private OnMediaStateTimeListener mOnMediaStateTimeListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnSuccessListener mOnSuccessListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private PlayUrl mPlayerUrl;
    OnPreparedListener mPreparedListener = new OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            LogTag.i("onPrepared()");
            String currentDate = Tools.getCurrentDate();
            if (VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener != null) {
                VideoViewH264m3u8Hw.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.PREPARED, currentDate);
            }
            VideoViewH264m3u8Hw.this.mCurrentState = 2;
            VideoViewH264m3u8Hw.this.StateChange(VideoViewH264m3u8Hw.this.mCurrentState);
            VideoViewH264m3u8Hw videoViewH264m3u8Hw = VideoViewH264m3u8Hw.this;
            VideoViewH264m3u8Hw videoViewH264m3u8Hw2 = VideoViewH264m3u8Hw.this;
            VideoViewH264m3u8Hw.this.mCanSeekForward = true;
            videoViewH264m3u8Hw2.mCanSeekBack = true;
            videoViewH264m3u8Hw.mCanPause = true;
            if (VideoViewH264m3u8Hw.this.mOnPreparedListener != null) {
                VideoViewH264m3u8Hw.this.mOnPreparedListener.onPrepared(VideoViewH264m3u8Hw.this.mMediaPlayer);
            }
            VideoViewH264m3u8Hw.this.mLastUrl = ((FFMpegPlayer) mp).getLastUrl();
            VideoViewH264m3u8Hw.this.mVersion = ((FFMpegPlayer) mp).getVersion();
            if (VideoViewH264m3u8Hw.this.mMediaController != null) {
                VideoViewH264m3u8Hw.this.mMediaController.setEnabled(true);
            }
            int seekToPosition = VideoViewH264m3u8Hw.this.mSeekWhenPrepared;
            if (seekToPosition != 0) {
                VideoViewH264m3u8Hw.this.seekTo(seekToPosition);
            }
            VideoViewH264m3u8Hw.this.mVideoWidth = mp.getVideoWidth();
            VideoViewH264m3u8Hw.this.mVideoHeight = mp.getVideoHeight();
            if (VideoViewH264m3u8Hw.this.mVideoWidth == 0 || VideoViewH264m3u8Hw.this.mVideoHeight == 0) {
                if (VideoViewH264m3u8Hw.this.mTargetState == 3) {
                    VideoViewH264m3u8Hw.this.start();
                }
            } else if (VideoViewH264m3u8Hw.this.mSurfaceWidth != VideoViewH264m3u8Hw.this.mVideoWidth || VideoViewH264m3u8Hw.this.mSurfaceHeight != VideoViewH264m3u8Hw.this.mVideoHeight) {
                VideoViewH264m3u8Hw.this.getHolder().setFixedSize(VideoViewH264m3u8Hw.this.mVideoWidth, VideoViewH264m3u8Hw.this.mVideoHeight);
            } else if (VideoViewH264m3u8Hw.this.mTargetState == 3) {
                VideoViewH264m3u8Hw.this.start();
                if (VideoViewH264m3u8Hw.this.mMediaController != null) {
                    VideoViewH264m3u8Hw.this.mMediaController.show();
                }
            } else if (!VideoViewH264m3u8Hw.this.isPlaying()) {
                if ((seekToPosition != 0 || VideoViewH264m3u8Hw.this.getCurrentPosition() > 0) && VideoViewH264m3u8Hw.this.mMediaController != null) {
                    VideoViewH264m3u8Hw.this.mMediaController.show(0);
                }
            }
        }
    };
    private int mRatioType = -1;
    Callback mSHCallback = new Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            LogTag.i(VideoViewH264m3u8Hw.TAG, "surfaceChanged(), w=" + w + " h=" + h);
            VideoViewH264m3u8Hw.this.mSurfaceWidth = w;
            VideoViewH264m3u8Hw.this.mSurfaceHeight = h;
            boolean isValidState;
            if (VideoViewH264m3u8Hw.this.mTargetState == 3) {
                isValidState = true;
            } else {
                isValidState = false;
            }
            boolean hasValidSize;
            if (VideoViewH264m3u8Hw.this.mVideoWidth == w && VideoViewH264m3u8Hw.this.mVideoHeight == h) {
                hasValidSize = true;
            } else {
                hasValidSize = false;
            }
            if (VideoViewH264m3u8Hw.this.mMediaPlayer != null && isValidState && hasValidSize) {
                if (VideoViewH264m3u8Hw.this.mSeekWhenPrepared != 0) {
                    VideoViewH264m3u8Hw.this.seekTo(VideoViewH264m3u8Hw.this.mSeekWhenPrepared);
                }
                VideoViewH264m3u8Hw.this.start();
                if (VideoViewH264m3u8Hw.this.mMediaController != null) {
                    VideoViewH264m3u8Hw.this.mMediaController.show();
                }
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            if (VideoViewH264m3u8Hw.this.mSurfaceHolder == null) {
                VideoViewH264m3u8Hw.this.mSurfaceHolder = holder;
                LogTag.i(VideoViewH264m3u8Hw.TAG, "mSHCallback:surfaceCreated()->openVideo()");
                VideoViewH264m3u8Hw.this.openVideo();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            LogTag.i(VideoViewH264m3u8Hw.TAG, "mSHCallback:surfaceDestroyed()");
            VideoViewH264m3u8Hw.this.mSurfaceHolder = null;
            if (VideoViewH264m3u8Hw.this.mMediaController != null) {
                VideoViewH264m3u8Hw.this.mMediaController.hide();
            }
            VideoViewH264m3u8Hw.this.lastSeekWhenDestoryed = VideoViewH264m3u8Hw.this.getCurrentPosition();
            VideoViewH264m3u8Hw.this.release(false);
        }
    };
    private OnSeekCompleteListener mSeekCompleteListener = new OnSeekCompleteListener() {
        public void onSeekComplete(MediaPlayer mp) {
            if (VideoViewH264m3u8Hw.this.mOnSeekCompleteListener != null) {
                VideoViewH264m3u8Hw.this.mOnSeekCompleteListener.onSeekComplete(VideoViewH264m3u8Hw.this.mMediaPlayer);
            }
        }
    };
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener = new OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            VideoViewH264m3u8Hw.this.mVideoWidth = mp.getVideoWidth();
            VideoViewH264m3u8Hw.this.mVideoHeight = mp.getVideoHeight();
            if (!(VideoViewH264m3u8Hw.this.mVideoWidth == 0 || VideoViewH264m3u8Hw.this.mVideoHeight == 0)) {
                VideoViewH264m3u8Hw.this.getHolder().setFixedSize(VideoViewH264m3u8Hw.this.mVideoWidth, VideoViewH264m3u8Hw.this.mVideoHeight);
            }
            if (VideoViewH264m3u8Hw.this.mOnVideoSizeChangedListener != null) {
                VideoViewH264m3u8Hw.this.mOnVideoSizeChangedListener.onVideoSizeChanged(mp, VideoViewH264m3u8Hw.this.mVideoWidth, VideoViewH264m3u8Hw.this.mVideoHeight);
            }
        }
    };
    private int mSourceType = 0;
    private OnSuccessListener mSuccessListener = new OnSuccessListener() {
        public void onSuccess() {
            if (VideoViewH264m3u8Hw.this.mOnSuccessListener != null) {
                VideoViewH264m3u8Hw.this.mOnSuccessListener.onSuccess();
            }
            LogTag.i("硬解成功");
            if (VideoViewH264m3u8Hw.this.mContext.get() != null) {
                HttpRequestManager.getInstance((Context) VideoViewH264m3u8Hw.this.mContext.get()).hardDecodeReport(VideoViewH264m3u8Hw.this.mPlayerUrl.mVid, VideoViewH264m3u8Hw.this.mUri.toString(), 1, -1, VideoViewH264m3u8Hw.this.mPlayerUrl.mStreamType, 1);
            }
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

    private class CacheTime {
        double block_cache;
        double start_cache;

        public CacheTime(double start_cache, double block_cache) {
            this.start_cache = start_cache;
            this.block_cache = block_cache;
        }
    }

    public VideoViewH264m3u8Hw(Context context) {
        super(context);
        this.mContext = new WeakReference(context);
        initVideoView();
    }

    public VideoViewH264m3u8Hw(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = new WeakReference(context);
        initVideoView();
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

    public void setVideoPath(String videoPath) {
        this.mPlayerUrl = new PlayUrl();
        this.mPlayerUrl.setVid(-1);
        this.mPlayerUrl.setUrl(videoPath);
        this.mPlayerUrl.setStreamType(StreamType.STREAM_TYPE_UNKNOWN);
        setVideoURI(Uri.parse(videoPath));
    }

    public void setVideoPath(String path, Map<String, String> map) {
    }

    public void setVideoURI(Uri uri) {
        String currentDate = Tools.getCurrentDate();
        if (this.mOnMediaStateTimeListener != null) {
            this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.INITPATH, currentDate);
        }
        this.mUri = uri;
        this.mSeekWhenPrepared = 0;
        LogTag.i(TAG, "setVideoURI()->openVideo()");
        openVideo();
        requestLayout();
        invalidate();
        LogTag.i(TAG, "uri=" + this.mUri.toString());
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
            return this.mDuration;
        } else if (this.mDuration > 0) {
            return this.mDuration;
        } else {
            this.mDuration = this.mMediaPlayer.getDuration();
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
                LogTag.i("硬解开始播放");
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
        stopPlayback(false);
    }

    public void stopPlayback(boolean isRemoveCallBack) {
        StateChange(6);
        if (this.mMediaPlayer != null) {
            String currentDateStop = Tools.getCurrentDate();
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            try {
                this.mMediaPlayer.stop();
            } catch (Exception e) {
                LogTag.i(TAG, "hard decode native player has already null");
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
                LogTag.i(TAG, "hard decode native player has already null");
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
        if (this.mUri == null || this.mSurfaceHolder == null || this.mContext.get() == null) {
            setVisibility(0);
            return;
        }
        LogTag.i(TAG, "openVideo()-> release(false)");
        release(false);
        try {
            String currentDate = Tools.getCurrentDate();
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.CREATE, currentDate);
            }
            this.mMediaPlayer = new FFMpegPlayer((Context) this.mContext.get());
            this.mMediaPlayer.setHardwareDecode(1);
            this.mMediaPlayer.setHwCapbility(CodecWrapper.getProfile(), CodecWrapper.getAVCLevel());
            this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
            this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
            this.mDuration = -1;
            this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnSuccessListener(this.mSuccessListener);
            this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
            this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
            this.mMediaPlayer.setOnAdNumberListener(this.mAdNumberListener);
            this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
            this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
            this.mMediaPlayer.setOnBlockListener(this.mBlockListener);
            this.mMediaPlayer.setOnCacheListener(this.mCacheListener);
            this.mMediaPlayer.setOnFirstPlayListener(this.mFirstPlayLitener);
            this.mMediaPlayer.setOnDisplayListener(this.mDisplayListener);
            this.mMediaPlayer.setOnHardDecoddErrorListener(this.mHardDecodeErrorListner);
            this.mCurrentBufferPercentage = 0;
            this.mMediaPlayer.setDataSource((Context) this.mContext.get(), this.mUri);
            this.mMediaPlayer.setVolume(this.mVolumevalue);
            this.mMediaPlayer.setInitPosition(this.mInitPostion);
            if (this.mCacheTime != null) {
                this.mMediaPlayer.setCacheTime(this.mCacheTime.start_cache, this.mCacheTime.block_cache);
            }
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
        } catch (IllegalArgumentException e) {
            this.mCurrentState = -1;
            StateChange(this.mCurrentState);
            this.mTargetState = -1;
            this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
        } catch (IOException e2) {
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

    public void setOnCacheListener(OnCacheListener l) {
        this.mOnCacheListener = l;
    }

    public void setOnMediaStateTimeListener(OnMediaStateTimeListener l) {
        this.mOnMediaStateTimeListener = l;
    }

    public void setOnHardDecodeErrorListener(OnHardDecodeErrorListner l) {
        this.mOnHardDecodeErrorListner = l;
    }

    public void setOnFirstPlayListener(OnFirstPlayLitener l) {
        this.mOnFirstPlayLitener = l;
    }

    public int setSourceType(int sourceType) {
        return 0;
    }

    public int setMachineInfomation(float ScreenResolution) {
        return 0;
    }

    public int setOneFingertouchInfomation(float begin_x, float begin_y, float end_x, float end_y) {
        return 0;
    }

    public int setTwoFingertouchInfomation(float begin_x0, float begin_y0, float begin_x1, float begin_y1, float end_x0, float end_y0, float end_x1, float end_y1) {
        return 0;
    }

    public int setgravity_yroInfomation(float gravity_yro_x, float gravity_yro_y, float gravity_yro_z) {
        return 0;
    }

    public int setGravityInfomation(float gravity_x, float gravity_y, float gravity_z) {
        return 0;
    }

    public int setgravity_yroValidInfomation(boolean gravityValid) {
        return 0;
    }

    public int setAngleInit() {
        return 0;
    }

    public int setTwoFingerZoom(float zoom) {
        return 0;
    }

    public void setCacheTime(double start_cache, double block_cache) {
        if (this.mCacheTime == null) {
            this.mCacheTime = new CacheTime(start_cache, block_cache);
        }
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setCacheTime(start_cache, block_cache);
        }
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

    public void processSensorEvent(SensorEvent sensorEvent) {
    }
}
