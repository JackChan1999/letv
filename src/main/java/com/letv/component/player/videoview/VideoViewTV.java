package com.letv.component.player.videoview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.media.Metadata;
import android.net.Uri;
import android.support.v4.media.TransportMediator;
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
import com.letv.component.player.http.HttpRequestManager;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.Tools;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnCacheListener;
import com.media.ffmpeg.FFMpegPlayer.OnFirstPlayLitener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import java.io.IOException;
import java.util.Map;

public class VideoViewTV extends SurfaceView implements LetvMediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private final int FORWARD_TIME;
    private final int REWIND_TIME;
    private int duration;
    protected int lastSeekWhenDestoryed;
    private final OnBufferingUpdateListener mBufferingUpdateListener;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private final OnCompletionListener mCompletionListener;
    protected Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private final OnErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    OnInfoListener mInfoListener;
    private MediaPlayer mMediaPlayer;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnMediaStateTimeListener mOnMediaStateTimeListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    OnPreparedListener mPreparedListener;
    private int mRatioType;
    Callback mSHCallback;
    OnSeekCompleteListener mSeekCompleteListener;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private int mTargetState;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;
    private MediaController mediaController;

    public VideoViewTV(Context context) {
        super(context);
        this.FORWARD_TIME = 20000;
        this.REWIND_TIME = 20000;
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.lastSeekWhenDestoryed = 0;
        this.mRatioType = -1;
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                VideoViewTV.this.mSurfaceWidth = w;
                VideoViewTV.this.mSurfaceHeight = h;
                LogTag.i("VideoViewTV", "surfaceChanged(), mSurfaceWidth=" + VideoViewTV.this.mSurfaceWidth + ", mSurfaceHeight=" + VideoViewTV.this.mSurfaceHeight);
                boolean isValidState;
                if (VideoViewTV.this.mTargetState == 3) {
                    isValidState = true;
                } else {
                    isValidState = false;
                }
                boolean hasValidSize;
                if (VideoViewTV.this.mVideoWidth == w && VideoViewTV.this.mVideoHeight == h) {
                    hasValidSize = true;
                } else {
                    hasValidSize = false;
                }
                if (VideoViewTV.this.mMediaPlayer != null && isValidState && hasValidSize) {
                    if (VideoViewTV.this.mSeekWhenPrepared != 0) {
                        VideoViewTV.this.seekTo(VideoViewTV.this.mSeekWhenPrepared);
                    }
                    VideoViewTV.this.start();
                }
            }

            public void surfaceCreated(SurfaceHolder holder) {
                LogTag.i("VideoViewTV", "surfaceCreated()");
                VideoViewTV.this.mSurfaceHolder = holder;
                VideoViewTV.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                LogTag.i("VideoViewTV", "surfaceDestroyed()");
                VideoViewTV.this.mSurfaceHolder = null;
                if (VideoViewTV.this.mediaController != null) {
                    VideoViewTV.this.mediaController.hide();
                }
                VideoViewTV.this.lastSeekWhenDestoryed = VideoViewTV.this.getCurrentPosition();
                VideoViewTV.this.release(true);
            }
        };
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                VideoViewTV.this.mVideoWidth = mp.getVideoWidth();
                VideoViewTV.this.mVideoHeight = mp.getVideoHeight();
                LogTag.i("VideoViewTV", "onVideoSizeChanged(), mVideoWidth=" + VideoViewTV.this.mVideoWidth + ", mVideoHeight=" + VideoViewTV.this.mVideoHeight);
                if (VideoViewTV.this.mVideoWidth != 0 && VideoViewTV.this.mVideoHeight != 0) {
                    VideoViewTV.this.getHolder().setFixedSize(VideoViewTV.this.mVideoWidth, VideoViewTV.this.mVideoHeight);
                }
            }
        };
        this.mInfoListener = new OnInfoListener() {
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (VideoViewTV.this.mOnInfoListener == null || !VideoViewTV.this.mOnInfoListener.onInfo(mp, what, extra)) {
                    return false;
                }
                return true;
            }
        };
        this.mSeekCompleteListener = new OnSeekCompleteListener() {
            public void onSeekComplete(MediaPlayer mp) {
                if (VideoViewTV.this.mOnSeekCompleteListener != null) {
                    VideoViewTV.this.mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewTv(乐视电视videoview)  onPrepared()");
                if (VideoViewTV.this.mOnMediaStateTimeListener != null) {
                    VideoViewTV.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.PREPARED, currentDate);
                }
                VideoViewTV.this.mCurrentState = 2;
                try {
                    Metadata data = (Metadata) Class.forName(MediaPlayer.class.getName()).getMethod("getMetadata", new Class[]{Boolean.TYPE, Boolean.TYPE}).invoke(mp, new Object[]{Boolean.valueOf(false), Boolean.valueOf(false)});
                    VideoViewTV videoViewTV;
                    if (data != null) {
                        videoViewTV = VideoViewTV.this;
                        boolean z = !data.has(29) || data.getBoolean(29);
                        videoViewTV.mCanPause = z;
                        videoViewTV = VideoViewTV.this;
                        z = !data.has(30) || data.getBoolean(30);
                        videoViewTV.mCanSeekBack = z;
                        videoViewTV = VideoViewTV.this;
                        z = !data.has(31) || data.getBoolean(31);
                        videoViewTV.mCanSeekForward = z;
                    } else {
                        VideoViewTV videoViewTV2 = VideoViewTV.this;
                        videoViewTV = VideoViewTV.this;
                        VideoViewTV.this.mCanSeekForward = true;
                        videoViewTV.mCanSeekBack = true;
                        videoViewTV2.mCanPause = true;
                    }
                    if (VideoViewTV.this.mOnPreparedListener != null && VideoViewTV.this.isInPlaybackState()) {
                        VideoViewTV.this.mOnPreparedListener.onPrepared(VideoViewTV.this.mMediaPlayer);
                    }
                    if (VideoViewTV.this.mediaController != null) {
                        VideoViewTV.this.mediaController.setEnabled(true);
                    }
                    VideoViewTV.this.mVideoWidth = mp.getVideoWidth();
                    VideoViewTV.this.mVideoHeight = mp.getVideoHeight();
                    int seekToPosition = VideoViewTV.this.mSeekWhenPrepared;
                    if (seekToPosition != 0) {
                        VideoViewTV.this.seekTo(seekToPosition);
                    }
                    if (VideoViewTV.this.mVideoWidth != 0 && VideoViewTV.this.mVideoHeight != 0) {
                        VideoViewTV.this.getHolder().setFixedSize(VideoViewTV.this.mVideoWidth, VideoViewTV.this.mVideoHeight);
                        if (VideoViewTV.this.mSurfaceWidth != VideoViewTV.this.mVideoWidth || VideoViewTV.this.mSurfaceHeight != VideoViewTV.this.mVideoHeight) {
                            return;
                        }
                        if (VideoViewTV.this.mTargetState == 3) {
                            VideoViewTV.this.start();
                            if (VideoViewTV.this.mediaController != null) {
                                VideoViewTV.this.mediaController.show();
                            }
                        } else if (!VideoViewTV.this.isPlaying()) {
                            if ((seekToPosition != 0 || VideoViewTV.this.getCurrentPosition() > 0) && VideoViewTV.this.mediaController != null) {
                                VideoViewTV.this.mediaController.show(0);
                            }
                        }
                    } else if (VideoViewTV.this.mTargetState == 3) {
                        VideoViewTV.this.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                VideoViewTV.this.mCurrentState = 5;
                VideoViewTV.this.mTargetState = 5;
                if (VideoViewTV.this.mediaController != null) {
                    VideoViewTV.this.mediaController.hide();
                }
                if (VideoViewTV.this.mOnCompletionListener != null) {
                    VideoViewTV.this.mOnCompletionListener.onCompletion(VideoViewTV.this.mMediaPlayer);
                }
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
                VideoViewTV.this.mCurrentState = -1;
                VideoViewTV.this.mTargetState = -1;
                if (VideoViewTV.this.mediaController != null) {
                    VideoViewTV.this.mediaController.hide();
                }
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + "VideoViewTV(乐视电视videoview) error, framework_err=" + framework_err + ", impl_err=" + impl_err);
                if (VideoViewTV.this.mOnMediaStateTimeListener != null) {
                    VideoViewTV.this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.ERROR, currentDate);
                }
                return (VideoViewTV.this.mOnErrorListener == null || VideoViewTV.this.mOnErrorListener.onError(VideoViewTV.this.mMediaPlayer, framework_err, impl_err)) ? true : true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                VideoViewTV.this.mCurrentBufferPercentage = percent;
                if (VideoViewTV.this.mOnBufferingUpdateListener != null) {
                    VideoViewTV.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mContext = context;
        initVideoView();
        setBackgroundColor(Color.argb(101, 99, 22, 0));
    }

    public VideoViewTV(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initVideoView();
    }

    public VideoViewTV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.FORWARD_TIME = 20000;
        this.REWIND_TIME = 20000;
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.lastSeekWhenDestoryed = 0;
        this.mRatioType = -1;
        this.mSHCallback = /* anonymous class already generated */;
        this.mSizeChangedListener = /* anonymous class already generated */;
        this.mInfoListener = /* anonymous class already generated */;
        this.mSeekCompleteListener = /* anonymous class already generated */;
        this.mPreparedListener = /* anonymous class already generated */;
        this.mCompletionListener = /* anonymous class already generated */;
        this.mErrorListener = /* anonymous class already generated */;
        this.mBufferingUpdateListener = /* anonymous class already generated */;
        this.mContext = context;
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
        this.mTargetState = 0;
    }

    public boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    public boolean extIsInPlaybackState() {
        return isInPlaybackState();
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

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewTV release()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.RELEASE, currentDateRelease);
            }
            this.mMediaPlayer.release();
            this.mCurrentState = 0;
            this.mTargetState = 0;
        }
        this.mMediaPlayer = null;
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
        LogTag.i("VideoViewTV", "onMeasure(), width=" + width + ", height=" + height + ", mVideoWidth=" + this.mVideoWidth + ", mVideoHeight=" + this.mVideoHeight);
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

    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoPath(String path, Map<String, String> headers) {
        setVideoURI(Uri.parse(path), headers);
    }

    public void setVideoURI(Uri uri) {
        setVideoURI(uri, null);
    }

    public void setVideoURI(Uri uri, Map<String, String> headers) {
        String currentDate = Tools.getCurrentDate();
        LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewTV(乐视电视videoview)  setVideoURI(), url=" + (uri != null ? uri.toString() : "null"), true);
        if (this.mOnMediaStateTimeListener != null) {
            this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.INITPATH, currentDate);
        }
        this.mUri = uri;
        this.mHeaders = headers;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    private void initListener() {
        this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
        this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
        this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
        this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
        this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
        this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
        this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
    }

    private void deadListener(MediaPlayer mMediaPlayer) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnSeekCompleteListener(null);
            mMediaPlayer.setOnInfoListener(null);
            mMediaPlayer.setOnPreparedListener(null);
            mMediaPlayer.setOnVideoSizeChangedListener(null);
            mMediaPlayer.setOnCompletionListener(null);
            mMediaPlayer.setOnErrorListener(null);
            mMediaPlayer.setOnBufferingUpdateListener(null);
        }
    }

    @SuppressLint({"NewApi"})
    private void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            Intent i = new Intent("com.android.music.musicservicecommand");
            i.putExtra("command", "pause");
            this.mContext.sendBroadcast(i);
            release(false);
            try {
                String currentDate = Tools.getCurrentDate();
                LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDate + " VideoViewH264mp4(乐视电视videoview)  创建MediaPlayer对象");
                if (this.mOnMediaStateTimeListener != null) {
                    this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.CREATE, currentDate);
                }
                this.mMediaPlayer = new MediaPlayer();
                initListener();
                this.duration = -1;
                this.mCurrentBufferPercentage = 0;
                this.mMediaPlayer.setDataSource(this.mContext, this.mUri, this.mHeaders);
                this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setScreenOnWhilePlaying(true);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
                attachMediaController();
            } catch (IOException e) {
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (IllegalArgumentException e2) {
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (Exception e3) {
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            }
        }
    }

    public void setMediaController(MediaController controller) {
        if (this.mediaController != null) {
            this.mediaController.hide();
        }
        this.mediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mediaController != null) {
            View anchorView;
            this.mediaController.setMediaPlayer(this);
            if (getParent() instanceof View) {
                anchorView = (View) getParent();
            } else {
                anchorView = this;
            }
            this.mediaController.setAnchorView(anchorView);
            this.mediaController.setEnabled(isInPlaybackState());
        }
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            String currentDateRelease = Tools.getCurrentDate();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + currentDateRelease + "VideoViewTV release()");
            if (this.mOnMediaStateTimeListener != null) {
                this.mOnMediaStateTimeListener.onMediaStateTime(MeidaStateType.RELEASE, currentDateRelease);
            }
            this.mMediaPlayer.release();
            deadListener(this.mMediaPlayer);
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (cleartargetstate) {
                this.mTargetState = 0;
            }
        }
    }

    public void reset() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported;
        if (keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 164 || keyCode == 82 || keyCode == 5 || keyCode == 6) {
            isKeyCodeSupported = false;
        } else {
            isKeyCodeSupported = true;
        }
        if (isInPlaybackState() && isKeyCodeSupported && this.mediaController != null) {
            if (keyCode == 79 || keyCode == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mediaController.show();
                    return true;
                }
                start();
                this.mediaController.hide();
                return true;
            } else if (keyCode == TransportMediator.KEYCODE_MEDIA_PLAY) {
                if (this.mMediaPlayer.isPlaying()) {
                    return true;
                }
                start();
                this.mediaController.hide();
                return true;
            } else if (keyCode != 86 && keyCode != TransportMediator.KEYCODE_MEDIA_PAUSE) {
                toggleMediaControlsVisiblity();
            } else if (!this.mMediaPlayer.isPlaying()) {
                return true;
            } else {
                pause();
                this.mediaController.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public int getLastSeekWhenDestoryed() {
        return this.lastSeekWhenDestoryed;
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mediaController.isShowing()) {
            this.mediaController.hide();
        } else {
            this.mediaController.show();
        }
    }

    public void start() {
        HttpRequestManager.getInstance(this.mContext).requestCapability();
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + Tools.getCurrentDate() + " VideoViewTV(乐视电视videoview)  start()");
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void start(MediaPlayer mMediaPlayer) {
        if (isInPlaybackState()) {
            mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void pause() {
        if (isInPlaybackState()) {
            try {
                if (this.mMediaPlayer != null && this.mMediaPlayer.isPlaying()) {
                    this.mMediaPlayer.pause();
                    LetvMediaPlayerManager.getInstance().writePlayLog("系统当前时间:  " + Tools.getCurrentDate() + " VideoViewTV(乐视电视videoview)  pause()");
                    this.mCurrentState = 4;
                }
            } catch (Exception e) {
            }
        }
        this.mTargetState = 4;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openVideo();
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (!isInPlaybackState()) {
            this.duration = -1;
            return this.duration;
        } else if (this.duration > 0) {
            return this.duration;
        } else {
            this.duration = this.mMediaPlayer.getDuration();
            return this.duration;
        }
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
        seekTo(getCurrentPosition() + 20000);
    }

    public void rewind() {
        seekTo(getCurrentPosition() - 20000);
    }

    public boolean isPlaying() {
        try {
            return isInPlaybackState() && this.mMediaPlayer.isPlaying();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaused() {
        return this.mCurrentState == 4;
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    protected int[] getVideoSize() {
        if (this.mMediaPlayer == null) {
            return null;
        }
        int width = this.mMediaPlayer.getVideoWidth();
        int height = this.mMediaPlayer.getVideoHeight();
        int[] screenSize = new int[2];
        float ratios = Math.max(((float) width) / ((float) this.mSurfaceWidth), ((float) height) / ((float) this.mSurfaceHeight));
        screenSize[0] = (int) Math.ceil((double) (((float) width) / ratios));
        screenSize[1] = (int) Math.ceil((double) (((float) height) / ratios));
        return screenSize;
    }

    public View getView() {
        return this;
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnInfoListener(OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
        this.mOnBufferingUpdateListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener l) {
        this.mOnVideoSizeChangedListener = l;
    }

    public OnSeekCompleteListener getOnSeekCompleteListener() {
        return this.mOnSeekCompleteListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener mOnSeekCompleteListener) {
        this.mOnSeekCompleteListener = mOnSeekCompleteListener;
    }

    public void setVideoViewStateChangeListener(OnVideoViewStateChangeListener videoViewStateChangeListener) {
    }

    public boolean isEnforcementWait() {
        return false;
    }

    public void setEnforcementWait(boolean enforcementWait) {
    }

    public boolean isEnforcementPause() {
        return false;
    }

    public void setEnforcementPause(boolean enforcementPause) {
    }

    public void setVideoPlayUrl(PlayUrl url) {
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
