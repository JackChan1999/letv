package master.flame.danmaku.ui.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import java.util.LinkedList;
import java.util.Locale;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.DrawHandler.Callback;
import master.flame.danmaku.controller.DrawHelper;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.controller.IDanmakuView.OnDanmakuClickListener;
import master.flame.danmaku.controller.IDanmakuViewController;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;

@SuppressLint({"NewApi"})
public class DanmakuTextureView extends TextureView implements IDanmakuView, IDanmakuViewController, SurfaceTextureListener {
    private static final int MAX_RECORD_SIZE = 50;
    private static final int ONE_SECOND = 1000;
    public static final String TAG = "DanmakuTextureView";
    private DrawHandler handler;
    private boolean isSurfaceCreated;
    private Callback mCallback;
    private boolean mDanmakuVisible = true;
    private LinkedList<Long> mDrawTimes;
    protected int mDrawingThreadType = 0;
    private boolean mEnableDanmakuDrwaingCache = true;
    private HandlerThread mHandlerThread;
    private OnDanmakuClickListener mOnDanmakuClickListener;
    private boolean mShowFps;
    private DanmakuTouchHelper mTouchHelper;

    public DanmakuTextureView(Context context) {
        super(context);
        init();
    }

    @TargetApi(11)
    private void init() {
        setLayerType(2, null);
        setOpaque(false);
        setWillNotCacheDrawing(true);
        setDrawingCacheEnabled(false);
        setWillNotDraw(true);
        setSurfaceTextureListener(this);
        DrawHelper.useDrawColorToClearCanvas(true, true);
        this.mTouchHelper = DanmakuTouchHelper.instance(this);
    }

    public DanmakuTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DanmakuTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void addDanmaku(BaseDanmaku item) {
        if (this.handler != null) {
            this.handler.addDanmaku(item);
        }
    }

    public void invalidateDanmaku(BaseDanmaku item, boolean remeasure) {
        if (this.handler != null) {
            this.handler.invalidateDanmaku(item, remeasure);
        }
    }

    public void removeAllDanmakus() {
        if (this.handler != null) {
            this.handler.removeAllDanmakus();
        }
    }

    public void removeAllLiveDanmakus() {
        if (this.handler != null) {
            this.handler.removeAllLiveDanmakus();
        }
    }

    public IDanmakus getCurrentVisibleDanmakus() {
        if (this.handler != null) {
            return this.handler.getCurrentVisibleDanmakus();
        }
        return null;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
        if (this.handler != null) {
            this.handler.setCallback(callback);
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.isSurfaceCreated = true;
    }

    public synchronized boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.isSurfaceCreated = false;
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (this.handler != null) {
            this.handler.notifyDispSizeChanged(width, height);
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public void release() {
        stop();
        if (this.mDrawTimes != null) {
            this.mDrawTimes.clear();
        }
    }

    public void stop() {
        stopDraw();
    }

    private void stopDraw() {
        if (this.handler != null) {
            this.handler.quit();
            this.handler = null;
        }
        if (this.mHandlerThread != null) {
            HandlerThread handlerThread = this.mHandlerThread;
            this.mHandlerThread = null;
            try {
                handlerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handlerThread.quit();
        }
    }

    protected Looper getLooper(int type) {
        int priority;
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quit();
            this.mHandlerThread = null;
        }
        switch (type) {
            case 1:
                return Looper.getMainLooper();
            case 2:
                priority = -8;
                break;
            case 3:
                priority = 19;
                break;
            default:
                priority = 0;
                break;
        }
        this.mHandlerThread = new HandlerThread("DFM Handler Thread #" + priority, priority);
        this.mHandlerThread.start();
        return this.mHandlerThread.getLooper();
    }

    private void prepare() {
        if (this.handler == null) {
            this.handler = new DrawHandler(getLooper(this.mDrawingThreadType), this, this.mDanmakuVisible);
        }
    }

    public void prepare(BaseDanmakuParser parser, DanmakuContext config) {
        prepare();
        this.handler.setConfig(config);
        this.handler.setParser(parser);
        this.handler.setCallback(this.mCallback);
        this.handler.prepare();
    }

    public boolean isPrepared() {
        return this.handler != null && this.handler.isPrepared();
    }

    public DanmakuContext getConfig() {
        if (this.handler == null) {
            return null;
        }
        return this.handler.getConfig();
    }

    public void showFPS(boolean show) {
        this.mShowFps = show;
    }

    private float fps() {
        long lastTime = SystemClock.uptimeMillis();
        this.mDrawTimes.addLast(Long.valueOf(lastTime));
        float dtime = (float) (lastTime - ((Long) this.mDrawTimes.getFirst()).longValue());
        if (this.mDrawTimes.size() > 50) {
            this.mDrawTimes.removeFirst();
        }
        return dtime > 0.0f ? ((float) (this.mDrawTimes.size() * 1000)) / dtime : 0.0f;
    }

    public synchronized long drawDanmakus() {
        long dtime;
        if (this.isSurfaceCreated) {
            long stime = SystemClock.uptimeMillis();
            if (isShown()) {
                Canvas canvas = lockCanvas();
                if (canvas != null) {
                    if (this.handler != null) {
                        RenderingState rs = this.handler.draw(canvas);
                        if (this.mShowFps) {
                            if (this.mDrawTimes == null) {
                                this.mDrawTimes = new LinkedList();
                            }
                            dtime = SystemClock.uptimeMillis() - stime;
                            DrawHelper.drawFPS(canvas, String.format(Locale.getDefault(), "fps %.2f,time:%d s,cache:%d,miss:%d", new Object[]{Float.valueOf(fps()), Long.valueOf(getCurrentTime() / 1000), Long.valueOf(rs.cacheHitCount), Long.valueOf(rs.cacheMissCount)}));
                        }
                    }
                    if (this.isSurfaceCreated) {
                        unlockCanvasAndPost(canvas);
                    }
                }
                dtime = SystemClock.uptimeMillis() - stime;
            } else {
                dtime = -1;
            }
        } else {
            dtime = 0;
        }
        return dtime;
    }

    public void toggle() {
        if (!this.isSurfaceCreated) {
            return;
        }
        if (this.handler == null) {
            start();
        } else if (this.handler.isStop()) {
            resume();
        } else {
            pause();
        }
    }

    public void pause() {
        if (this.handler != null) {
            this.handler.pause();
        }
    }

    public void resume() {
        if (this.handler != null && this.handler.isPrepared()) {
            this.handler.resume();
        } else if (this.handler == null) {
            restart();
        }
    }

    public boolean isPaused() {
        if (this.handler != null) {
            return this.handler.isStop();
        }
        return false;
    }

    public void restart() {
        stop();
        start();
    }

    public void start() {
        start(0);
    }

    public void start(long postion) {
        if (this.handler == null) {
            prepare();
        } else {
            this.handler.removeCallbacksAndMessages(null);
        }
        this.handler.obtainMessage(1, Long.valueOf(postion)).sendToTarget();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mTouchHelper != null) {
            this.mTouchHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public void seekTo(Long ms) {
        if (this.handler != null) {
            this.handler.seekTo(ms);
        }
    }

    public void enableDanmakuDrawingCache(boolean enable) {
        this.mEnableDanmakuDrwaingCache = enable;
    }

    public boolean isDanmakuDrawingCacheEnabled() {
        return this.mEnableDanmakuDrwaingCache;
    }

    public boolean isViewReady() {
        return this.isSurfaceCreated;
    }

    public View getView() {
        return this;
    }

    public void show() {
        showAndResumeDrawTask(null);
    }

    public void showAndResumeDrawTask(Long position) {
        this.mDanmakuVisible = true;
        if (this.handler != null) {
            this.handler.showDanmakus(position);
        }
    }

    public void hide() {
        this.mDanmakuVisible = false;
        if (this.handler != null) {
            this.handler.hideDanmakus(false);
        }
    }

    public long hideAndPauseDrawTask() {
        this.mDanmakuVisible = false;
        if (this.handler == null) {
            return 0;
        }
        return this.handler.hideDanmakus(true);
    }

    public void setOnDanmakuClickListener(OnDanmakuClickListener listener) {
        this.mOnDanmakuClickListener = listener;
        setClickable(listener != null);
    }

    public OnDanmakuClickListener getOnDanmakuClickListener() {
        return this.mOnDanmakuClickListener;
    }

    public synchronized void clear() {
        if (isViewReady()) {
            Canvas canvas = lockCanvas();
            if (canvas != null) {
                DrawHelper.clearCanvas(canvas);
                unlockCanvasAndPost(canvas);
            }
        }
    }

    public boolean isShown() {
        return this.mDanmakuVisible && super.isShown();
    }

    public void setDrawingThreadType(int type) {
        this.mDrawingThreadType = type;
    }

    public long getCurrentTime() {
        if (this.handler != null) {
            return this.handler.getCurrentTime();
        }
        return 0;
    }

    public boolean isHardwareAccelerated() {
        return false;
    }

    public void clearDanmakusOnScreen() {
        if (this.handler != null) {
            this.handler.clearDanmakusOnScreen();
        }
    }

    public long getLastDanmakuTimer() {
        return 0;
    }

    public boolean isClickDanmukuRange(MotionEvent event) {
        return false;
    }
}
