package com.letv.component.player.videoview;

import android.content.Context;
import android.content.Intent;
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
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.core.PlayUrl;
import com.letv.component.player.core.PlayUrl.StreamType;
import com.letv.component.player.http.HttpRequestManager;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.Tools;
import com.letv.core.constant.LetvConstant;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnCacheListener;
import com.media.ffmpeg.FFMpegPlayer.OnFirstPlayLitener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import java.io.IOException;
import java.util.Map;

public class VideoViewH264LeMobile extends SurfaceView implements LetvMediaPlayerControl {
    public static final int STATE_ENFORCEMENT = 7;
    public static final int STATE_ERROR = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_PAUSED = 4;
    public static final int STATE_PLAYBACK_COMPLETED = 5;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_PREPARED = 2;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_STOPBACK = 6;
    private final int FORWARD_TIME;
    private final int REWIND_TIME;
    private String TAG;
    private boolean enforcementPause;
    private boolean enforcementWait;
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
    private OnInfoListener mInfoListener;
    private MediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnMediaStateTimeListener mOnMediaStateTimeListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private OnVideoViewStateChangeListener mOnVideoViewStateChangeListener;
    private PlayUrl mPlayerUrl;
    OnPreparedListener mPreparedListener;
    private int mRatioType;
    Callback mSHCallback;
    private OnSeekCompleteListener mSeekCompleteListener;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;

    public VideoViewH264LeMobile(Context context) {
        super(context);
        this.TAG = "VideoViewLeMobile";
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
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                LogTag.i("onVideoSizeChanged(), width=" + width + ", heigth=" + height);
                VideoViewH264LeMobile.this.mVideoWidth = mp.getVideoWidth();
                VideoViewH264LeMobile.this.mVideoHeight = mp.getVideoHeight();
                if (!(VideoViewH264LeMobile.this.mVideoWidth == 0 || VideoViewH264LeMobile.this.mVideoHeight == 0)) {
                    VideoViewH264LeMobile.this.getHolder().setFixedSize(VideoViewH264LeMobile.this.mVideoWidth, VideoViewH264LeMobile.this.mVideoHeight);
                }
                if (VideoViewH264LeMobile.this.mOnVideoSizeChangedListener != null) {
                    VideoViewH264LeMobile.this.mOnVideoSizeChangedListener.onVideoSizeChanged(mp, width, height);
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                LogTag.i("onPrepared()");
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264LeMobile(乐视手机videoview)  onPrepared()");
                if (VideoViewH264LeMobile.this.mOnMediaStateTimeListener != null) {
                    VideoViewH264LeMobile.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.PREPARED, currentDate);
                }
                VideoViewH264LeMobile.this.mCurrentState = 2;
                VideoViewH264LeMobile.this.StateChange(VideoViewH264LeMobile.this.mCurrentState);
                VideoViewH264LeMobile videoViewH264LeMobile = VideoViewH264LeMobile.this;
                VideoViewH264LeMobile videoViewH264LeMobile2 = VideoViewH264LeMobile.this;
                VideoViewH264LeMobile.this.mCanSeekForward = true;
                videoViewH264LeMobile2.mCanSeekBack = true;
                videoViewH264LeMobile.mCanPause = true;
                if (VideoViewH264LeMobile.this.mMediaController != null) {
                    VideoViewH264LeMobile.this.mMediaController.setEnabled(true);
                }
                VideoViewH264LeMobile.this.mVideoWidth = mp.getVideoWidth();
                VideoViewH264LeMobile.this.mVideoHeight = mp.getVideoHeight();
                int seekToPosition = VideoViewH264LeMobile.this.mSeekWhenPrepared;
                if (seekToPosition != 0) {
                    VideoViewH264LeMobile.this.seekTo(seekToPosition);
                }
                if (VideoViewH264LeMobile.this.mVideoWidth != 0 && VideoViewH264LeMobile.this.mVideoHeight != 0) {
                    VideoViewH264LeMobile.this.getHolder().setFixedSize(VideoViewH264LeMobile.this.mVideoWidth, VideoViewH264LeMobile.this.mVideoHeight);
                    if (VideoViewH264LeMobile.this.mSurfaceWidth == VideoViewH264LeMobile.this.mVideoWidth && VideoViewH264LeMobile.this.mSurfaceHeight == VideoViewH264LeMobile.this.mVideoHeight) {
                        if (VideoViewH264LeMobile.this.mTargetState == 3) {
                            VideoViewH264LeMobile.this.start();
                            if (VideoViewH264LeMobile.this.mMediaController != null) {
                                VideoViewH264LeMobile.this.mMediaController.show();
                            }
                        } else if (!VideoViewH264LeMobile.this.isPlaying() && ((seekToPosition != 0 || VideoViewH264LeMobile.this.getCurrentPosition() > 0) && VideoViewH264LeMobile.this.mMediaController != null)) {
                            VideoViewH264LeMobile.this.mMediaController.show(0);
                        }
                    }
                } else if (VideoViewH264LeMobile.this.mTargetState == 3) {
                    VideoViewH264LeMobile.this.start();
                }
                if (VideoViewH264LeMobile.this.mOnPreparedListener != null) {
                    VideoViewH264LeMobile.this.mOnPreparedListener.onPrepared(mp);
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                LogTag.i("onCompletion()");
                VideoViewH264LeMobile.this.mCurrentState = 5;
                VideoViewH264LeMobile.this.StateChange(VideoViewH264LeMobile.this.mCurrentState);
                VideoViewH264LeMobile.this.mTargetState = 5;
                VideoViewH264LeMobile.this.mCurrentState = 6;
                VideoViewH264LeMobile.this.StateChange(VideoViewH264LeMobile.this.mCurrentState);
                if (VideoViewH264LeMobile.this.mMediaController != null) {
                    VideoViewH264LeMobile.this.mMediaController.hide();
                }
                if (VideoViewH264LeMobile.this.mOnCompletionListener != null) {
                    VideoViewH264LeMobile.this.mOnCompletionListener.onCompletion(mp);
                }
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                LogTag.i("onError(): framework_err=" + framework_err + ", impl_err=" + impl_err);
                VideoViewH264LeMobile.this.mCurrentState = -1;
                VideoViewH264LeMobile.this.StateChange(VideoViewH264LeMobile.this.mCurrentState);
                VideoViewH264LeMobile.this.mTargetState = -1;
                if (VideoViewH264LeMobile.this.mMediaController != null) {
                    VideoViewH264LeMobile.this.mMediaController.hide();
                }
                if (VideoViewH264LeMobile.this.mOnErrorListener != null) {
                    VideoViewH264LeMobile.this.mOnErrorListener.onError(mp, framework_err, impl_err);
                }
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + "VideoViewLeMobile(乐视手机videoview) error, framework_err=" + framework_err + ", impl_err=" + impl_err);
                if (VideoViewH264LeMobile.this.mOnMediaStateTimeListener != null) {
                    VideoViewH264LeMobile.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.ERROR, currentDate);
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                VideoViewH264LeMobile.this.mCurrentBufferPercentage = percent;
                if (VideoViewH264LeMobile.this.mOnBufferingUpdateListener != null) {
                    VideoViewH264LeMobile.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new OnSeekCompleteListener() {
            public void onSeekComplete(MediaPlayer mp) {
                LogTag.i("onSeekComplete()");
                if (VideoViewH264LeMobile.this.mOnSeekCompleteListener != null) {
                    VideoViewH264LeMobile.this.mOnSeekCompleteListener.onSeekComplete(VideoViewH264LeMobile.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new OnInfoListener() {
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (VideoViewH264LeMobile.this.mOnInfoListener != null) {
                    VideoViewH264LeMobile.this.mOnInfoListener.onInfo(mp, what, extra);
                }
                return false;
            }
        };
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                LogTag.i("mSHCallback:surfaceChanged(), w=" + w + ", h=" + h);
                VideoViewH264LeMobile.this.mSurfaceWidth = w;
                VideoViewH264LeMobile.this.mSurfaceHeight = h;
                boolean isValidState;
                if (VideoViewH264LeMobile.this.mTargetState == 3) {
                    isValidState = true;
                } else {
                    isValidState = false;
                }
                boolean hasValidSize;
                if (VideoViewH264LeMobile.this.mVideoWidth == w && VideoViewH264LeMobile.this.mVideoHeight == h) {
                    hasValidSize = true;
                } else {
                    hasValidSize = false;
                }
                if (VideoViewH264LeMobile.this.mMediaPlayer != null && isValidState && hasValidSize) {
                    if (VideoViewH264LeMobile.this.mSeekWhenPrepared != 0) {
                        VideoViewH264LeMobile.this.seekTo(VideoViewH264LeMobile.this.mSeekWhenPrepared);
                    }
                    VideoViewH264LeMobile.this.start();
                    if (VideoViewH264LeMobile.this.mMediaController != null) {
                        VideoViewH264LeMobile.this.mMediaController.show();
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                LogTag.i("mSHCallback:surfaceCreated()");
                VideoViewH264LeMobile.this.mSurfaceHolder = holder;
                VideoViewH264LeMobile.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                LogTag.i("mSHCallback:surfaceDestroyed()");
                VideoViewH264LeMobile.this.mSurfaceHolder = null;
                if (VideoViewH264LeMobile.this.mMediaController != null) {
                    VideoViewH264LeMobile.this.mMediaController.hide();
                }
                VideoViewH264LeMobile.this.lastSeekWhenDestoryed = VideoViewH264LeMobile.this.getCurrentPosition();
                VideoViewH264LeMobile.this.release(true);
            }
        };
        this.mContext = context;
        initVideoView();
    }

    public VideoViewH264LeMobile(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initVideoView();
    }

    public VideoViewH264LeMobile(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.TAG = "VideoViewLeMobile";
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
        this.mSizeChangedListener = /* anonymous class already generated */;
        this.mPreparedListener = /* anonymous class already generated */;
        this.mCompletionListener = /* anonymous class already generated */;
        this.mErrorListener = /* anonymous class already generated */;
        this.mBufferingUpdateListener = /* anonymous class already generated */;
        this.mSeekCompleteListener = /* anonymous class already generated */;
        this.mInfoListener = /* anonymous class already generated */;
        this.mSHCallback = /* anonymous class already generated */;
        this.mContext = context;
        initVideoView();
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
        LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264LeMobile(乐视手机videoview)  setVideoURI(), url=" + (uri != null ? uri.toString() : "null"), true);
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
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264LeMobile(乐视手机videoview)  创建MediaPlayer对象");
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
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewH264LeMobile release()");
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
        HttpRequestManager.getInstance(this.mContext).requestCapability();
        if (this.enforcementWait || this.enforcementPause) {
            StateChange(7);
        } else if (isInPlaybackState()) {
            LogTag.i("start()");
            this.mMediaPlayer.start();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + Tools.getCurrentDate() + "VideoViewH264LeMobile start()");
            this.mCurrentState = 3;
            StateChange(this.mCurrentState);
        }
        this.mTargetState = 3;
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            LogTag.i("pause()");
            this.mMediaPlayer.pause();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + Tools.getCurrentDate() + "VideoViewH264LeMobile pause()");
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
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateStop + "VideoViewH264LeMobile stop()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.STOP, currentDateStop);
            }
            this.mMediaPlayer.stop();
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewH264LeMobile release()");
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

    public int getAudioSessionId() {
        return 0;
    }

    public void setOnCacheListener(OnCacheListener l) {
    }

    public void setOnFirstPlayListener(OnFirstPlayLitener l) {
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

    public void setInitPosition(int msec) {
    }

    public void setVolume(int volume) {
    }

    public void setCacheTime(double start_cache, double block_cache) {
    }

    public void processSensorEvent(SensorEvent sensorEvent) {
    }
}
