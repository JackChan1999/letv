package master.flame.danmaku.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
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

public class DanmakuView extends View implements IDanmakuView, IDanmakuViewController {
    private static final int MAX_RECORD_SIZE = 50;
    private static final int ONE_SECOND = 1000;
    public static final String TAG = "DanmakuView";
    private DrawHandler handler;
    private boolean isSurfaceCreated;
    private Callback mCallback;
    private boolean mClearFlag;
    private boolean mDanmakuVisible = true;
    private boolean mDrawFinished = false;
    private Object mDrawMonitor = new Object();
    private LinkedList<Long> mDrawTimes;
    protected int mDrawingThreadType = 0;
    private boolean mEnableDanmakuDrwaingCache = true;
    private HandlerThread mHandlerThread;
    private OnDanmakuClickListener mOnDanmakuClickListener;
    private boolean mRequestRender = false;
    private Runnable mResumeRunnable = new Runnable() {
        public void run() {
            if (DanmakuView.this.handler != null) {
                DanmakuView.this.mResumeTryCount = DanmakuView.this.mResumeTryCount + 1;
                if (DanmakuView.this.mResumeTryCount > 4 || super.isShown()) {
                    DanmakuView.this.handler.resume();
                } else {
                    DanmakuView.this.handler.postDelayed(this, (long) (DanmakuView.this.mResumeTryCount * 100));
                }
            }
        }
    };
    private int mResumeTryCount = 0;
    private boolean mShowFps;
    private DanmakuTouchHelper mTouchHelper;
    private long mUiThreadId;

    public DanmakuView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.mUiThreadId = Thread.currentThread().getId();
        setBackgroundColor(0);
        setDrawingCacheBackgroundColor(0);
        DrawHelper.useDrawColorToClearCanvas(true, false);
        this.mTouchHelper = DanmakuTouchHelper.instance(this);
    }

    public DanmakuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DanmakuView(Context context, AttributeSet attrs, int defStyle) {
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
        DrawHandler handler = this.handler;
        this.handler = null;
        unlockCanvasAndPost();
        if (handler != null) {
            handler.quit();
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

    public long drawDanmakus() {
        if (!this.isSurfaceCreated) {
            return 0;
        }
        if (!isShown()) {
            return -1;
        }
        long stime = SystemClock.uptimeMillis();
        lockCanvas();
        return SystemClock.uptimeMillis() - stime;
    }

    @SuppressLint({"NewApi"})
    private void postInvalidateCompat() {
        this.mRequestRender = true;
        if (VERSION.SDK_INT >= 16) {
            postInvalidateOnAnimation();
        } else {
            postInvalidate();
        }
    }

    private void lockCanvas() {
        if (this.mDanmakuVisible) {
            postInvalidateCompat();
            synchronized (this.mDrawMonitor) {
                while (!this.mDrawFinished && this.handler != null) {
                    try {
                        this.mDrawMonitor.wait(200);
                    } catch (InterruptedException e) {
                        if (!this.mDanmakuVisible || this.handler == null || this.handler.isStop()) {
                            break;
                        }
                        Thread.currentThread().interrupt();
                    }
                }
                this.mDrawFinished = false;
            }
        }
    }

    private void lockCanvasAndClear() {
        this.mClearFlag = true;
        lockCanvas();
    }

    private void unlockCanvasAndPost() {
        synchronized (this.mDrawMonitor) {
            this.mDrawFinished = true;
            this.mDrawMonitor.notifyAll();
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDanmakuVisible || this.mRequestRender) {
            if (this.mClearFlag) {
                DrawHelper.clearCanvas(canvas);
                this.mClearFlag = false;
            } else if (this.handler != null) {
                RenderingState rs = this.handler.draw(canvas);
                if (this.mShowFps) {
                    if (this.mDrawTimes == null) {
                        this.mDrawTimes = new LinkedList();
                    }
                    DrawHelper.drawFPS(canvas, String.format(Locale.getDefault(), "fps %.2f,time:%d s,cache:%d,miss:%d", new Object[]{Float.valueOf(fps()), Long.valueOf(getCurrentTime() / 1000), Long.valueOf(rs.cacheHitCount), Long.valueOf(rs.cacheMissCount)}));
                }
            }
            this.mRequestRender = false;
            unlockCanvasAndPost();
            return;
        }
        super.onDraw(canvas);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.handler != null) {
            this.handler.notifyDispSizeChanged(right - left, bottom - top);
        }
        this.isSurfaceCreated = true;
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
            this.mResumeTryCount = 0;
            this.handler.postDelayed(this.mResumeRunnable, 100);
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
        this.mClearFlag = false;
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

    public void clear() {
        if (!isViewReady()) {
            return;
        }
        if (!this.mDanmakuVisible || Thread.currentThread().getId() == this.mUiThreadId) {
            this.mClearFlag = true;
            postInvalidateCompat();
            return;
        }
        lockCanvasAndClear();
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

    @SuppressLint({"NewApi"})
    public boolean isHardwareAccelerated() {
        if (VERSION.SDK_INT >= 11) {
            return super.isHardwareAccelerated();
        }
        return false;
    }

    public void clearDanmakusOnScreen() {
        if (this.handler != null) {
            this.handler.clearDanmakusOnScreen();
        }
    }

    public void setOnDanmakuClickListener(OnDanmakuClickListener listener) {
        this.mOnDanmakuClickListener = listener;
        setClickable(listener != null);
    }

    public OnDanmakuClickListener getOnDanmakuClickListener() {
        return this.mOnDanmakuClickListener;
    }

    public long getLastDanmakuTimer() {
        if (this.handler == null) {
            return 0;
        }
        BaseDanmaku baseDanmaku = this.handler.getLastDanmaku();
        if (baseDanmaku != null) {
            return baseDanmaku.time;
        }
        return 0;
    }

    public boolean isClickDanmukuRange(MotionEvent event) {
        IDanmakus clickDanmakus = this.mTouchHelper.touchHitDanmaku(event.getX(), event.getY());
        return (clickDanmakus == null || clickDanmakus.isEmpty()) ? false : true;
    }
}
