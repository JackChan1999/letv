package com.media.ffmpeg;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioTrack;
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
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.letv.component.player.hardwaredecode.MediaHardwareDecoder;
import com.letv.component.player.utils.LogTag;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class FFMpegPlayer extends MediaPlayer {
    public static final int DECODE_CODECOPEN_ERROR = 1028;
    public static final int DECODE_NODECODER_ERROR = 1027;
    public static final int DECODE_VMALLOC_ERROR = 1026;
    public static final int HARDWARE_DECODE = 1;
    private static final int HW_NO_ERROR = 1536;
    public static final int HW_SPSPPS_ERROR = 1537;
    public static final int HW_SPSPPS_FILLDATA_ERROR = 1540;
    public static final int HW_SPSPPS_MALLOC1_ERROR = 1538;
    public static final int HW_SPSPPS_MALLOC2_ERROR = 1539;
    private static final int MEDIA_AD_NUMBER = 50;
    private static final int MEDIA_BLOCK = 5;
    public static final int MEDIA_BLOCK_END = 10002;
    public static final int MEDIA_BLOCK_START = 10001;
    private static final int MEDIA_BUFFERING_UPDATE = 2;
    public static final int MEDIA_CACHE_END = 10004;
    private static final int MEDIA_CACHE_END_FROMC = 8;
    public static final int MEDIA_CACHE_START = 10003;
    private static final int MEDIA_CACHE_START_FROMC = 9;
    private static final int MEDIA_DECODE_SUCESS = 1024;
    private static final int MEDIA_END_BLOCK = 6;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_HARDDECODE_DURATION_FAIL_SWITCH_TO_SOFTDECODE = 1793;
    public static final int MEDIA_HARDDECODE_START_FAIL_SWITCH_TO_SOFTDECODE = 1537;
    private static final int MEDIA_HARDWARE = 1536;
    private static final int MEDIA_INFO = 200;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_FRAMERATE_AUDIO = 901;
    public static final int MEDIA_INFO_FRAMERATE_VIDEO = 900;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_PLAYBACK_COMPLETE = 1;
    private static final int MEDIA_PREPARED = 256;
    public static final int MEDIA_PREPARED_FAIL = 257;
    private static final int MEDIA_SEEK_COMPLETE = 3;
    private static final int MEDIA_SETDATASOURCE = 512;
    public static final int MEDIA_SETDATASOURCE_FAIL = 513;
    private static final int MEDIA_SET_VIDEO_SIZE = 4;
    private static final int MEDIA_SET_VIDEO_SIZE_INIT = 11;
    public static final int MEDIA_SOFTDECODE_FAIL = 1025;
    public static final int MEDIA_SOURCE_TYPE_NORMAL = 0;
    public static final int MEDIA_SOURCE_TYPE_PANORAMA = 1;
    public static final int MEDIA_SOURCE_TYPE_PANORAMA_VR = 2;
    private static final int MEDIA_START = 768;
    private static final int MEDIA_START_DISPLAY = 7;
    public static final int PREPARE_AUDIO_CODECOPEN_ERROR = 264;
    public static final int PREPARE_AUDIO_NODECODER_ERROR = 263;
    public static final int PREPARE_AUDIO_SAMPLERATE_ERROR = 265;
    public static final int PREPARE_FIND_INFO_ERROR = 258;
    private static final int PREPARE_NO_ERROR = 256;
    public static final int PREPARE_OPEN_FILE_ERROR = 257;
    public static final int PREPARE_VIDEO_CODECOPEN_ERROR = 261;
    public static final int PREPARE_VIDEO_MALLOC_ERROR = 262;
    public static final int PREPARE_VIDEO_NODECODER_ERROR = 260;
    public static final int PREPARE_VIDEO_NOSTREAM_ERROR = 259;
    public static final int SETDATASOURCE_ALLOCCONTEXT_ERROR = 514;
    private static final int SETDATASOURCE_NO_ERROR = 512;
    public static final int SETDATASOURCE_URLTOOLONG_ERROR = 513;
    public static final int SOFTWARE_DECODE = 0;
    private static final int START_NO_ERROR = 768;
    public static final int START_STATE_ERROR = 769;
    private static final String TAG = "FFMpegPlayer";
    private Context mContext;
    private EventHandler mEventHandler;
    private GLRenderControler mGlRenderControler;
    private int mNativeContext;
    private int mNativeData;
    private OnAdNumberListener mOnAdNumberListener;
    private OnBlockListener mOnBlockListener;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCacheListener mOnCacheListener;
    private OnCompletionListener mOnCompletionListener;
    private OnDisplayListener mOnDisplayListener;
    private OnErrorListener mOnErrorListener;
    private OnFirstPlayLitener mOnFirstPlayLitener;
    private OnHardDecodeErrorListner mOnHardDecodeErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnInitGLListener mOnInitGLListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnSuccessListener mOnSuccessListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private boolean mScreenOnWhilePlaying;
    public int mSourceType;
    private int mStartPlayCounts;
    private boolean mStayAwake;
    private Surface mSurface;
    private SurfaceHolder mSurfaceHolder;
    private AudioTrack mTrack;
    private MediaDecoder mVideoDecoder;
    private WakeLock mWakeLock;

    public interface OnBlockListener {
        void onBlock(FFMpegPlayer fFMpegPlayer, int i);
    }

    public interface OnDisplayListener {
        void onDisplay(FFMpegPlayer fFMpegPlayer);
    }

    public interface OnHardDecodeErrorListner {
        void onError(FFMpegPlayer fFMpegPlayer, int i, int i2);
    }

    public interface OnSuccessListener {
        void onSuccess();
    }

    public interface GLRenderControler {
        void setGLStartRenderMode();

        void setGLStopRenderMode();
    }

    public interface OnAdNumberListener {
        void onAdNumber(FFMpegPlayer fFMpegPlayer, int i);
    }

    public interface OnCacheListener {
        void onCache(FFMpegPlayer fFMpegPlayer, int i, int i2, long j);
    }

    public interface OnInitGLListener {
        void initGL(int i, int i2, int i3, int i4, String str);
    }

    public interface OnFirstPlayLitener {
        void onFirstPlay(FFMpegPlayer fFMpegPlayer, long j);
    }

    private class EventHandler extends Handler {
        private FFMpegPlayer mMediaPlayer;

        public EventHandler(FFMpegPlayer ffmpegPlayer, Looper looper) {
            super(looper);
            this.mMediaPlayer = ffmpegPlayer;
        }

        public void handleMessage(Message msg) {
            if (this.mMediaPlayer.mNativeContext != 0) {
                switch (msg.what) {
                    case 0:
                        return;
                    case 1:
                        if (FFMpegPlayer.this.mOnCompletionListener != null) {
                            FFMpegPlayer.this.mOnCompletionListener.onCompletion(this.mMediaPlayer);
                        }
                        FFMpegPlayer.this.stayAwake(false);
                        return;
                    case 2:
                        if (FFMpegPlayer.this.mOnBufferingUpdateListener != null) {
                            FFMpegPlayer.this.mOnBufferingUpdateListener.onBufferingUpdate(this.mMediaPlayer, msg.arg1);
                            return;
                        }
                        return;
                    case 3:
                        break;
                    case 4:
                        if (FFMpegPlayer.this.mOnVideoSizeChangedListener != null) {
                            FFMpegPlayer.this.mOnVideoSizeChangedListener.onVideoSizeChanged(this.mMediaPlayer, msg.arg1, msg.arg2);
                            return;
                        }
                        return;
                    case 5:
                        if (FFMpegPlayer.this.mOnBlockListener != null) {
                            FFMpegPlayer.this.mOnBlockListener.onBlock(this.mMediaPlayer, 10001);
                            return;
                        }
                        return;
                    case 6:
                        if (FFMpegPlayer.this.mOnBlockListener != null) {
                            FFMpegPlayer.this.mOnBlockListener.onBlock(this.mMediaPlayer, 10002);
                            return;
                        }
                        return;
                    case 7:
                        if (FFMpegPlayer.this.mOnDisplayListener != null) {
                            FFMpegPlayer.this.mOnDisplayListener.onDisplay(this.mMediaPlayer);
                        }
                        FFMpegPlayer fFMpegPlayer = FFMpegPlayer.this;
                        fFMpegPlayer.mStartPlayCounts = fFMpegPlayer.mStartPlayCounts + 1;
                        if (FFMpegPlayer.this.mStartPlayCounts <= 1 && FFMpegPlayer.this.mOnFirstPlayLitener != null) {
                            FFMpegPlayer.this.mOnFirstPlayLitener.onFirstPlay(this.mMediaPlayer, (long) (msg.arg1 / 1000));
                            return;
                        }
                        return;
                    case 8:
                        if (FFMpegPlayer.this.mOnCacheListener != null) {
                            FFMpegPlayer.this.mOnCacheListener.onCache(this.mMediaPlayer, FFMpegPlayer.MEDIA_CACHE_END, msg.arg1, (long) msg.arg2);
                            return;
                        }
                        return;
                    case 9:
                        if (FFMpegPlayer.this.mOnCacheListener != null) {
                            FFMpegPlayer.this.mOnCacheListener.onCache(this.mMediaPlayer, 10003, msg.arg1, (long) msg.arg2);
                            return;
                        }
                        return;
                    case 11:
                        if (FFMpegPlayer.this.mOnInitGLListener != null) {
                            FFMpegPlayer.this.mOnInitGLListener.initGL(msg.arg1, msg.arg2, 0, 0, "");
                            break;
                        }
                        break;
                    case 50:
                        if (FFMpegPlayer.this.mOnAdNumberListener != null) {
                            FFMpegPlayer.this.mOnAdNumberListener.onAdNumber(this.mMediaPlayer, msg.arg1);
                            return;
                        }
                        return;
                    case 200:
                        if (FFMpegPlayer.this.mOnInfoListener != null) {
                            FFMpegPlayer.this.mOnInfoListener.onInfo(this.mMediaPlayer, msg.arg1, msg.arg2);
                            return;
                        }
                        return;
                    case 256:
                        if (FFMpegPlayer.this.mOnPreparedListener != null) {
                            FFMpegPlayer.this.mOnPreparedListener.onPrepared(this.mMediaPlayer);
                            return;
                        }
                        return;
                    case 257:
                    case 513:
                    case 1025:
                        boolean error_was_handled = false;
                        if (FFMpegPlayer.this.mOnErrorListener != null) {
                            error_was_handled = FFMpegPlayer.this.mOnErrorListener.onError(this.mMediaPlayer, msg.what, msg.arg1);
                        }
                        if (!(FFMpegPlayer.this.mOnCompletionListener == null || error_was_handled)) {
                            FFMpegPlayer.this.mOnCompletionListener.onCompletion(this.mMediaPlayer);
                        }
                        FFMpegPlayer.this.stayAwake(false);
                        return;
                    case 1024:
                        if (FFMpegPlayer.this.mOnSuccessListener != null) {
                            FFMpegPlayer.this.mOnSuccessListener.onSuccess();
                            return;
                        }
                        return;
                    case 1537:
                    case FFMpegPlayer.MEDIA_HARDDECODE_DURATION_FAIL_SWITCH_TO_SOFTDECODE /*1793*/:
                        if (FFMpegPlayer.this.mOnHardDecodeErrorListener != null) {
                            FFMpegPlayer.this.mOnHardDecodeErrorListener.onError(this.mMediaPlayer, msg.what, msg.arg1);
                            return;
                        }
                        return;
                    default:
                        return;
                }
                if (FFMpegPlayer.this.mOnSeekCompleteListener != null) {
                    FFMpegPlayer.this.mOnSeekCompleteListener.onSeekComplete(this.mMediaPlayer);
                }
            }
        }
    }

    private static native void _nativeDraw(Canvas canvas);

    private native void _pause() throws IllegalStateException;

    private native void _release();

    private native void _reset();

    private native void _setAudioTrack(AudioTrack audioTrack) throws IOException;

    private native void _setVideoSurface(Surface surface) throws IOException;

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    private final native void native_finalize();

    private static final native void native_init() throws RuntimeException;

    private final native int native_setup(Object obj);

    private native int native_suspend_resume(boolean z);

    public native int _native_sync(long j) throws IOException;

    public native int getCurrentPosition();

    public native int getDuration();

    public native String getLastUrl();

    public native String getVersion();

    public native int getVideoHeight();

    public native int getVideoRotate(String str) throws IOException, IllegalArgumentException, IllegalStateException;

    public native int getVideoWidth();

    public native boolean isPlaying();

    public native void native_gl_render();

    public native void native_gl_resize(int i, int i2);

    public native void opengl_es_destroy(int i);

    public native void opengl_es_init(int i, int i2, int i3, int i4, String str);

    public native void opengl_panorama_Angle(float f, float f2, float f3);

    public native void opengl_panorama_Zoom(float f);

    public native void prepare() throws IOException, IllegalStateException;

    public native void seekTo(int i) throws IllegalStateException;

    public native void setAudioStreamType(int i);

    public native int setCacheSize(int i, int i2, int i3, int i4);

    public native int setCacheTime(double d, double d2);

    public native void setDataSource(String str) throws IOException, IllegalArgumentException, IllegalStateException;

    public native int setHardwareDecode(int i) throws IOException;

    public native void setHwCapbility(int i, int i2);

    public native void setInitPosition(int i);

    public native void setVolume(int i);

    public native void startHwRender();

    static {
        try {
            FFMpeg loadLib = new FFMpeg();
            native_init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FFMpegPlayer() {
        this.mStartPlayCounts = 0;
        this.mNativeData = 0;
        this.mWakeLock = null;
        this.mGlRenderControler = null;
        this.mSourceType = 0;
        this.mStartPlayCounts = 0;
        Looper looper = Looper.myLooper();
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            looper = Looper.getMainLooper();
            if (looper != null) {
                this.mEventHandler = new EventHandler(this, looper);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
        this.mSourceType = 0;
    }

    public FFMpegPlayer(Context context) {
        this.mStartPlayCounts = 0;
        this.mNativeData = 0;
        this.mWakeLock = null;
        this.mGlRenderControler = null;
        this.mSourceType = 0;
        this.mStartPlayCounts = 0;
        Looper looper = Looper.myLooper();
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            looper = Looper.getMainLooper();
            if (looper != null) {
                this.mEventHandler = new EventHandler(this, looper);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
        this.mContext = context;
        this.mSourceType = 0;
    }

    private static void postEventFromNative(Object mediaplayer_ref, int what, int arg1, int arg2, Object obj) {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mEventHandler != null) {
            mp.mEventHandler.sendMessage(mp.mEventHandler.obtainMessage(what, arg1, arg2, obj));
        }
    }

    public void setDisplay(SurfaceHolder sh) {
        this.mSurfaceHolder = sh;
        if (sh != null) {
            this.mSurface = sh.getSurface();
        } else {
            this.mSurface = null;
        }
        updateSurfaceScreenOn();
    }

    public void start() throws IllegalStateException {
        stayAwake(true);
        _start();
    }

    public void stop() throws IllegalStateException {
        stayAwake(false);
        _stop();
    }

    public void pause() throws IllegalStateException {
        stayAwake(false);
        _pause();
    }

    public void prepareAsync() throws IllegalStateException {
        try {
            prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(Context context, Uri uri) {
        try {
            setDataSource(uri.toString());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public int setSourceType(int sourceType) {
        switch (sourceType) {
            case 0:
                Log.i(TAG, "setSourceType:" + sourceType);
                this.mSourceType = 0;
                break;
            case 1:
                Log.i(TAG, "setSourceType:" + sourceType);
                this.mSourceType = 1;
                break;
            case 2:
                Log.i(TAG, "setSourceType:" + sourceType);
                this.mSourceType = 2;
                break;
            default:
                this.mSourceType = 0;
                Log.i(TAG, "error type setSourceTypesourceType" + sourceType);
                break;
        }
        return 0;
    }

    public void release() {
        stayAwake(false);
        updateSurfaceScreenOn();
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnVideoSizeChangedListener = null;
        _release();
    }

    public void reset() {
        stayAwake(false);
        _reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
    }

    public boolean suspend() {
        if (native_suspend_resume(true) < 0) {
            return false;
        }
        stayAwake(false);
        this.mEventHandler.removeCallbacksAndMessages(null);
        return true;
    }

    public boolean resume() {
        if (native_suspend_resume(false) < 0) {
            return false;
        }
        if (isPlaying()) {
            stayAwake(true);
        }
        return true;
    }

    public void setWakeMode(Context context, int mode) {
        boolean washeld = false;
        if (this.mWakeLock != null) {
            if (this.mWakeLock.isHeld()) {
                washeld = true;
                this.mWakeLock.release();
            }
            this.mWakeLock = null;
        }
        this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(536870912 | mode, MediaPlayer.class.getName());
        this.mWakeLock.setReferenceCounted(false);
        if (washeld) {
            this.mWakeLock.acquire();
        }
    }

    public void setScreenOnWhilePlaying(boolean screenOn) {
        if (this.mScreenOnWhilePlaying != screenOn) {
            this.mScreenOnWhilePlaying = screenOn;
            updateSurfaceScreenOn();
        }
    }

    private void stayAwake(boolean awake) {
        if (this.mWakeLock != null) {
            if (awake && !this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!awake && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = awake;
        updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        if (this.mSurfaceHolder != null) {
            SurfaceHolder surfaceHolder = this.mSurfaceHolder;
            boolean z = this.mScreenOnWhilePlaying && this.mStayAwake;
            surfaceHolder.setKeepScreenOn(z);
        }
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener mSizeChangedListener) {
        this.mOnVideoSizeChangedListener = mSizeChangedListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
    }

    public void setOnPreparedListener(OnPreparedListener listener) {
        this.mOnPreparedListener = listener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
    }

    public void setOnCompletionListener(OnCompletionListener listener) {
        this.mOnCompletionListener = listener;
    }

    public void setOnSuccessListener(OnSuccessListener listener) {
        this.mOnSuccessListener = listener;
    }

    public void setOnErrorListener(OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    public void setOnInfoListener(OnInfoListener listener) {
        this.mOnInfoListener = listener;
    }

    public void setOnAdNumberListener(OnAdNumberListener listener) {
        this.mOnAdNumberListener = listener;
    }

    public void setOnBlockListener(OnBlockListener listener) {
        this.mOnBlockListener = listener;
    }

    public void setOnDisplayListener(OnDisplayListener listener) {
        this.mOnDisplayListener = listener;
    }

    public void setOnHardDecoddErrorListener(OnHardDecodeErrorListner listener) {
        this.mOnHardDecodeErrorListener = listener;
    }

    public void setOnCacheListener(OnCacheListener listener) {
        this.mOnCacheListener = listener;
    }

    public void setOnFirstPlayListener(OnFirstPlayLitener litener) {
        this.mOnFirstPlayLitener = litener;
    }

    public void setOnInitListener(OnInitGLListener initGLListener) {
        this.mOnInitGLListener = initGLListener;
    }

    public static void initAudioTrack(Object mediaplayer_ref, int sampleRateInHz, int channelConfig) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null) {
            int bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, 2);
            try {
                mp.mTrack = new AudioTrack(3, sampleRateInHz, channelConfig, 2, bufferSizeInBytes, 1);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            try {
                if (mp.mTrack != null) {
                    mp.mTrack.play();
                }
            } catch (IllegalStateException e2) {
                LogTag.e("Error creating uninitialized AudioTrack, re-initial it");
                int tryCount = 0;
                while (mp.mTrack.getPlayState() == 0 && tryCount < 3) {
                    if (mp.mTrack != null) {
                        mp.mTrack.stop();
                        mp.mTrack.release();
                        mp.mTrack = null;
                    }
                    mp.mTrack = new AudioTrack(3, sampleRateInHz, channelConfig, 2, bufferSizeInBytes, 1);
                    tryCount++;
                }
                if (mp.mTrack != null) {
                    mp.mTrack.play();
                }
            }
        }
    }

    public static void writeAudioTrack(Object mediaplayer_ref, byte[] audioData, int size) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mTrack != null) {
            mp.mTrack.write(audioData, 0, size);
        }
    }

    public static void releaseAudioTrack(Object mediaplayer_ref) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mTrack != null) {
            mp.mTrack.stop();
            mp.mTrack.release();
            mp.mTrack = null;
        }
    }

    public static void JavaDraw(Object mediaplayer_ref) throws IOException {
        if (((FFMpegPlayer) ((WeakReference) mediaplayer_ref).get()) != null) {
        }
    }

    public void setRenderControler(GLRenderControler controler) {
        this.mGlRenderControler = controler;
    }

    public static void stopRenderMode(Object mediaplayer_ref) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mGlRenderControler != null) {
            mp.mGlRenderControler.setGLStopRenderMode();
        }
    }

    public static void startRenderMode(Object mediaplayer_ref) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mGlRenderControler != null) {
            mp.mGlRenderControler.setGLStartRenderMode();
        }
    }

    public static void initVideoDecoder(Object mediaplayer_ref, int width, int height) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mContext != null) {
            mp.mVideoDecoder = new MediaHardwareDecoder();
            mp.mVideoDecoder.setPlayer(mp);
            mp.mVideoDecoder.createDecoder(width, height, mp.mSurface);
        }
    }

    public static void stopVideoDecoder(Object mediaplayer_ref) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp != null && mp.mVideoDecoder != null) {
            LogTag.i(TAG, "stopVideoDecoder() real do stopCodec()");
            mp.mVideoDecoder.stopCodec();
            mp.mVideoDecoder = null;
        }
    }

    public static int fillInputBuffer(Object mediaplayer_ref, byte[] data, long pts, int flush) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp == null || mp.mVideoDecoder == null) {
            return -1;
        }
        return mp.mVideoDecoder.fillInputBuffer(data, pts, flush);
    }

    public static int flushCodec(Object mediaplayer_ref) throws IOException {
        FFMpegPlayer mp = (FFMpegPlayer) ((WeakReference) mediaplayer_ref).get();
        if (mp == null) {
            return -1;
        }
        if (mp.mVideoDecoder != null) {
            mp.mVideoDecoder.flushCodec();
        }
        return 0;
    }

    public void setDecoderSurface(Surface surface) {
        this.mSurface = surface;
    }
}
