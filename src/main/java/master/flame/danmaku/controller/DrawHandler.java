package master.flame.danmaku.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import java.util.LinkedList;
import master.flame.danmaku.controller.IDrawTask.TaskListener;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import master.flame.danmaku.danmaku.util.AndroidUtils;
import tv.cjump.jni.DeviceUtils;

public class DrawHandler extends Handler {
    private static final int CLEAR_DANMAKUS_ON_SCREEN = 13;
    private static final int HIDE_DANMAKUS = 9;
    private static final long INDEFINITE_TIME = 10000000;
    private static final int MAX_RECORD_SIZE = 500;
    private static final int NOTIFY_DISP_SIZE_CHANGED = 10;
    private static final int NOTIFY_RENDERING = 11;
    private static final int PAUSE = 7;
    public static final int PREPARE = 5;
    private static final int QUIT = 6;
    public static final int RESUME = 3;
    public static final int SEEK_POS = 4;
    private static final int SHOW_DANMAKUS = 8;
    public static final int START = 1;
    public static final int UPDATE = 2;
    private static final int UPDATE_WHEN_PAUSED = 12;
    public IDrawTask drawTask;
    private Callback mCallback;
    private DanmakuContext mContext;
    private long mCordonTime = 30;
    private long mCordonTime2 = 60;
    private IDanmakuViewController mDanmakuView;
    private boolean mDanmakusVisible = true;
    private long mDesireSeekingTime;
    private AbsDisplayer mDisp;
    private LinkedList<Long> mDrawTimes = new LinkedList();
    private long mFrameUpdateRate = 16;
    private boolean mIdleSleep;
    private boolean mInSeekingAction;
    private boolean mInSyncAction;
    private boolean mInWaitingState;
    private long mLastDeltaTime;
    private BaseDanmakuParser mParser;
    private boolean mReady;
    private long mRemainingTime;
    private final RenderingState mRenderingState = new RenderingState();
    private UpdateThread mThread;
    private long mThresholdTime;
    private long mTimeBase;
    private final boolean mUpdateInNewThread;
    private long pausedPosition = 0;
    private boolean quitFlag = true;
    private DanmakuTimer timer = new DanmakuTimer();

    public interface Callback {
        void danmakuShown(BaseDanmaku baseDanmaku);

        void drawingFinished();

        void prepared();

        void updateTimer(DanmakuTimer danmakuTimer);
    }

    public DrawHandler(Looper looper, IDanmakuViewController view, boolean danmakuVisibile) {
        boolean z = true;
        super(looper);
        this.mUpdateInNewThread = Runtime.getRuntime().availableProcessors() > 3;
        if (DeviceUtils.isProblemBoxDevice()) {
            z = false;
        }
        this.mIdleSleep = z;
        bindView(view);
        if (danmakuVisibile) {
            showDanmakus(null);
        } else {
            hideDanmakus(false);
        }
        this.mDanmakusVisible = danmakuVisibile;
    }

    private void bindView(IDanmakuViewController view) {
        this.mDanmakuView = view;
    }

    public void setConfig(DanmakuContext config) {
        this.mContext = config;
    }

    public void setParser(BaseDanmakuParser parser) {
        this.mParser = parser;
    }

    public void setCallback(Callback cb) {
        this.mCallback = cb;
    }

    public void quit() {
        sendEmptyMessage(6);
    }

    public boolean isStop() {
        return this.quitFlag;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleMessage(android.os.Message r15) {
        /*
        r14 = this;
        r7 = r15.what;
        switch(r7) {
            case 1: goto L_0x0022;
            case 2: goto L_0x00b8;
            case 3: goto L_0x002e;
            case 4: goto L_0x006f;
            case 5: goto L_0x0006;
            case 6: goto L_0x0164;
            case 7: goto L_0x0160;
            case 8: goto L_0x00e4;
            case 9: goto L_0x012d;
            case 10: goto L_0x00c6;
            case 11: goto L_0x01a9;
            case 12: goto L_0x01ae;
            case 13: goto L_0x01c5;
            default: goto L_0x0005;
        };
    L_0x0005:
        return;
    L_0x0006:
        r8 = r14.mParser;
        if (r8 == 0) goto L_0x0012;
    L_0x000a:
        r8 = r14.mDanmakuView;
        r8 = r8.isViewReady();
        if (r8 != 0) goto L_0x0019;
    L_0x0012:
        r8 = 5;
        r10 = 100;
        r14.sendEmptyMessageDelayed(r8, r10);
        goto L_0x0005;
    L_0x0019:
        r8 = new master.flame.danmaku.controller.DrawHandler$1;
        r8.<init>();
        r14.prepare(r8);
        goto L_0x0005;
    L_0x0022:
        r5 = r15.obj;
        r5 = (java.lang.Long) r5;
        if (r5 == 0) goto L_0x0063;
    L_0x0028:
        r8 = r5.longValue();
        r14.pausedPosition = r8;
    L_0x002e:
        r8 = 0;
        r14.quitFlag = r8;
        r8 = r14.mReady;
        if (r8 == 0) goto L_0x0068;
    L_0x0035:
        r8 = r14.mRenderingState;
        r8.reset();
        r8 = r14.mDrawTimes;
        r8.clear();
        r8 = android.os.SystemClock.uptimeMillis();
        r10 = r14.pausedPosition;
        r8 = r8 - r10;
        r14.mTimeBase = r8;
        r8 = r14.timer;
        r10 = r14.pausedPosition;
        r8.update(r10);
        r8 = 3;
        r14.removeMessages(r8);
        r8 = 2;
        r14.sendEmptyMessage(r8);
        r8 = r14.drawTask;
        r8.start();
        r14.notifyRendering();
        r8 = 0;
        r14.mInSeekingAction = r8;
        goto L_0x0005;
    L_0x0063:
        r8 = 0;
        r14.pausedPosition = r8;
        goto L_0x002e;
    L_0x0068:
        r8 = 3;
        r10 = 100;
        r14.sendEmptyMessageDelayed(r8, r10);
        goto L_0x0005;
    L_0x006f:
        r8 = 1;
        r14.quitFlag = r8;
        r14.quitUpdateThread();
        r2 = r15.obj;
        r2 = (java.lang.Long) r2;
        r8 = r2.longValue();
        r10 = r14.timer;
        r10 = r10.currMillisecond;
        r0 = r8 - r10;
        r8 = r14.mTimeBase;
        r8 = r8 - r0;
        r14.mTimeBase = r8;
        r8 = r14.timer;
        r10 = android.os.SystemClock.uptimeMillis();
        r12 = r14.mTimeBase;
        r10 = r10 - r12;
        r8.update(r10);
        r8 = r14.mContext;
        r8 = r8.mGlobalFlagValues;
        r8.updateMeasureFlag();
        r8 = r14.drawTask;
        if (r8 == 0) goto L_0x00a8;
    L_0x009f:
        r8 = r14.drawTask;
        r9 = r14.timer;
        r10 = r9.currMillisecond;
        r8.seek(r10);
    L_0x00a8:
        r8 = r14.timer;
        r8 = r8.currMillisecond;
        r14.pausedPosition = r8;
        r8 = 3;
        r14.removeMessages(r8);
        r8 = 3;
        r14.sendEmptyMessage(r8);
        goto L_0x0005;
    L_0x00b8:
        r8 = r14.mUpdateInNewThread;
        if (r8 == 0) goto L_0x00c1;
    L_0x00bc:
        r14.updateInNewThread();
        goto L_0x0005;
    L_0x00c1:
        r14.updateInCurrentThread();
        goto L_0x0005;
    L_0x00c6:
        r8 = r14.mContext;
        r8 = r8.mDanmakuFactory;
        r9 = r14.mContext;
        r8.notifyDispSizeChanged(r9);
        r6 = r15.obj;
        r6 = (java.lang.Boolean) r6;
        if (r6 == 0) goto L_0x0005;
    L_0x00d5:
        r8 = r6.booleanValue();
        if (r8 == 0) goto L_0x0005;
    L_0x00db:
        r8 = r14.mContext;
        r8 = r8.mGlobalFlagValues;
        r8.updateMeasureFlag();
        goto L_0x0005;
    L_0x00e4:
        r4 = r15.obj;
        r4 = (java.lang.Long) r4;
        r8 = r14.drawTask;
        if (r8 == 0) goto L_0x00fc;
    L_0x00ec:
        if (r4 != 0) goto L_0x0111;
    L_0x00ee:
        r8 = r14.timer;
        r10 = r14.getCurrentTime();
        r8.update(r10);
        r8 = r14.drawTask;
        r8.requestClear();
    L_0x00fc:
        r8 = 1;
        r14.mDanmakusVisible = r8;
        r8 = r14.quitFlag;
        if (r8 == 0) goto L_0x010c;
    L_0x0103:
        r8 = r14.mDanmakuView;
        if (r8 == 0) goto L_0x010c;
    L_0x0107:
        r8 = r14.mDanmakuView;
        r8.drawDanmakus();
    L_0x010c:
        r14.notifyRendering();
        goto L_0x0005;
    L_0x0111:
        r8 = r14.drawTask;
        r8.start();
        r8 = r14.drawTask;
        r10 = r4.longValue();
        r8.seek(r10);
        r8 = r14.drawTask;
        r8.requestClear();
        r8 = 1;
        r8 = r14.obtainMessage(r8, r4);
        r8.sendToTarget();
        goto L_0x00fc;
    L_0x012d:
        r8 = 0;
        r14.mDanmakusVisible = r8;
        r8 = r14.mDanmakuView;
        if (r8 == 0) goto L_0x0139;
    L_0x0134:
        r8 = r14.mDanmakuView;
        r8.clear();
    L_0x0139:
        r8 = r14.drawTask;
        if (r8 == 0) goto L_0x0147;
    L_0x013d:
        r8 = r14.drawTask;
        r8.requestClear();
        r8 = r14.drawTask;
        r8.requestHide();
    L_0x0147:
        r3 = r15.obj;
        r3 = (java.lang.Boolean) r3;
        r8 = r3.booleanValue();
        if (r8 == 0) goto L_0x015a;
    L_0x0151:
        r8 = r14.drawTask;
        if (r8 == 0) goto L_0x015a;
    L_0x0155:
        r8 = r14.drawTask;
        r8.quit();
    L_0x015a:
        r8 = r3.booleanValue();
        if (r8 == 0) goto L_0x0005;
    L_0x0160:
        r8 = 2;
        r14.removeMessages(r8);
    L_0x0164:
        r8 = 6;
        if (r7 != r8) goto L_0x016b;
    L_0x0167:
        r8 = 0;
        r14.removeCallbacksAndMessages(r8);
    L_0x016b:
        r8 = 1;
        r14.quitFlag = r8;
        r14.syncTimerIfNeeded();
        r8 = r14.mThread;
        if (r8 == 0) goto L_0x017b;
    L_0x0175:
        r14.notifyRendering();
        r14.quitUpdateThread();
    L_0x017b:
        r8 = r14.timer;
        r8 = r8.currMillisecond;
        r14.pausedPosition = r8;
        r8 = 6;
        if (r7 != r8) goto L_0x0005;
    L_0x0184:
        r8 = r14.drawTask;
        if (r8 == 0) goto L_0x018d;
    L_0x0188:
        r8 = r14.drawTask;
        r8.quit();
    L_0x018d:
        r8 = r14.mParser;
        if (r8 == 0) goto L_0x0196;
    L_0x0191:
        r8 = r14.mParser;
        r8.release();
    L_0x0196:
        r8 = r14.getLooper();
        r9 = android.os.Looper.getMainLooper();
        if (r8 == r9) goto L_0x0005;
    L_0x01a0:
        r8 = r14.getLooper();
        r8.quit();
        goto L_0x0005;
    L_0x01a9:
        r14.notifyRendering();
        goto L_0x0005;
    L_0x01ae:
        r8 = r14.quitFlag;
        if (r8 == 0) goto L_0x0005;
    L_0x01b2:
        r8 = r14.mDanmakuView;
        if (r8 == 0) goto L_0x0005;
    L_0x01b6:
        r8 = r14.drawTask;
        r8.requestClear();
        r8 = r14.mDanmakuView;
        r8.drawDanmakus();
        r14.notifyRendering();
        goto L_0x0005;
    L_0x01c5:
        r8 = r14.drawTask;
        if (r8 == 0) goto L_0x0005;
    L_0x01c9:
        r8 = r14.drawTask;
        r10 = r14.getCurrentTime();
        r8.clearDanmakusOnScreen(r10);
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: master.flame.danmaku.controller.DrawHandler.handleMessage(android.os.Message):void");
    }

    private void quitUpdateThread() {
        if (this.mThread != null) {
            UpdateThread thread = this.mThread;
            this.mThread = null;
            synchronized (this.drawTask) {
                this.drawTask.notifyAll();
            }
            thread.quit();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateInCurrentThread() {
        if (!this.quitFlag) {
            long d = syncTimer(SystemClock.uptimeMillis());
            if (d < 0) {
                removeMessages(2);
                sendEmptyMessageDelayed(2, 60 - d);
                return;
            }
            d = this.mDanmakuView.drawDanmakus();
            removeMessages(2);
            if (d > this.mCordonTime2) {
                this.timer.add(d);
                this.mDrawTimes.clear();
            }
            if (this.mDanmakusVisible) {
                if (this.mRenderingState.nothingRendered && this.mIdleSleep) {
                    long dTime = this.mRenderingState.endTime - this.timer.currMillisecond;
                    if (dTime > 500) {
                        waitRendering(dTime - 10);
                        return;
                    }
                }
                if (d < this.mFrameUpdateRate) {
                    sendEmptyMessageDelayed(2, this.mFrameUpdateRate - d);
                    return;
                } else {
                    sendEmptyMessage(2);
                    return;
                }
            }
            waitRendering(INDEFINITE_TIME);
        }
    }

    private void updateInNewThread() {
        if (this.mThread == null) {
            this.mThread = new UpdateThread("DFM Update") {
                public void run() {
                    long lastTime = SystemClock.uptimeMillis();
                    while (!isQuited() && !DrawHandler.this.quitFlag) {
                        long startMS = SystemClock.uptimeMillis();
                        if (DrawHandler.this.mFrameUpdateRate - (SystemClock.uptimeMillis() - lastTime) > 1) {
                            SystemClock.sleep(1);
                        } else {
                            lastTime = startMS;
                            long d = DrawHandler.this.syncTimer(startMS);
                            if (d < 0) {
                                SystemClock.sleep(60 - d);
                            } else {
                                d = DrawHandler.this.mDanmakuView.drawDanmakus();
                                if (d > DrawHandler.this.mCordonTime2) {
                                    DrawHandler.this.timer.add(d);
                                    DrawHandler.this.mDrawTimes.clear();
                                }
                                if (!DrawHandler.this.mDanmakusVisible) {
                                    DrawHandler.this.waitRendering(DrawHandler.INDEFINITE_TIME);
                                } else if (DrawHandler.this.mRenderingState.nothingRendered && DrawHandler.this.mIdleSleep) {
                                    long dTime = DrawHandler.this.mRenderingState.endTime - DrawHandler.this.timer.currMillisecond;
                                    if (dTime > 500) {
                                        DrawHandler.this.notifyRendering();
                                        DrawHandler.this.waitRendering(dTime - 10);
                                    }
                                }
                            }
                        }
                    }
                }
            };
            this.mThread.start();
        }
    }

    private final long syncTimer(long startMS) {
        if (this.mInSeekingAction || this.mInSyncAction) {
            return 0;
        }
        this.mInSyncAction = true;
        long d = 0;
        long time = startMS - this.mTimeBase;
        if (!this.mDanmakusVisible || this.mRenderingState.nothingRendered || this.mInWaitingState) {
            this.timer.update(time);
            this.mRemainingTime = 0;
        } else {
            long gapTime = time - this.timer.currMillisecond;
            long averageTime = Math.max(this.mFrameUpdateRate, getAverageRenderingTime());
            if (gapTime > SPConstant.DELAY_BUFFER_DURATION || this.mRenderingState.consumingTime > this.mCordonTime || averageTime > this.mCordonTime) {
                d = gapTime;
                gapTime = 0;
            } else {
                d = Math.min(this.mCordonTime, Math.max(this.mFrameUpdateRate, averageTime + (gapTime / this.mFrameUpdateRate)));
                long a = d - this.mLastDeltaTime;
                if (a > 3 && a < 8 && this.mLastDeltaTime >= this.mFrameUpdateRate && this.mLastDeltaTime <= this.mCordonTime) {
                    d = this.mLastDeltaTime;
                }
                gapTime -= d;
                this.mLastDeltaTime = d;
            }
            this.mRemainingTime = gapTime;
            this.timer.add(d);
        }
        if (this.mCallback != null) {
            this.mCallback.updateTimer(this.timer);
        }
        this.mInSyncAction = false;
        return d;
    }

    private void syncTimerIfNeeded() {
        if (this.mInWaitingState) {
            syncTimer(SystemClock.uptimeMillis());
        }
    }

    private void initRenderingConfigs() {
        this.mCordonTime = Math.max(33, (long) (((float) 16) * 2.5f));
        this.mCordonTime2 = (long) (((float) this.mCordonTime) * 2.5f);
        this.mFrameUpdateRate = Math.max(16, (16 / 15) * 15);
        this.mThresholdTime = this.mFrameUpdateRate + 3;
    }

    private void prepare(final Runnable runnable) {
        if (this.drawTask == null) {
            this.drawTask = createDrawTask(this.mDanmakuView.isDanmakuDrawingCacheEnabled(), this.timer, this.mDanmakuView.getContext(), this.mDanmakuView.getWidth(), this.mDanmakuView.getHeight(), this.mDanmakuView.isHardwareAccelerated(), new TaskListener() {
                public void ready() {
                    DrawHandler.this.initRenderingConfigs();
                    runnable.run();
                }

                public void onDanmakuAdd(BaseDanmaku danmaku) {
                    if (!danmaku.isTimeOut()) {
                        long delay = danmaku.time - DrawHandler.this.timer.currMillisecond;
                        if (delay > 0) {
                            DrawHandler.this.sendEmptyMessageDelayed(11, delay);
                        } else if (DrawHandler.this.mInWaitingState) {
                            DrawHandler.this.notifyRendering();
                        }
                    }
                }

                public void onDanmakuShown(BaseDanmaku danmaku) {
                    if (DrawHandler.this.mCallback != null) {
                        DrawHandler.this.mCallback.danmakuShown(danmaku);
                    }
                }

                public void onDanmakusDrawingFinished() {
                    if (DrawHandler.this.mCallback != null) {
                        DrawHandler.this.mCallback.drawingFinished();
                    }
                }

                public void onDanmakuConfigChanged() {
                    DrawHandler.this.redrawIfNeeded();
                }
            });
        } else {
            runnable.run();
        }
    }

    public boolean isPrepared() {
        return this.mReady;
    }

    private IDrawTask createDrawTask(boolean useDrwaingCache, DanmakuTimer timer, Context context, int width, int height, boolean isHardwareAccelerated, TaskListener taskListener) {
        this.mDisp = this.mContext.getDisplayer();
        this.mDisp.setSize(width, height);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisp.setDensities(displayMetrics.density, displayMetrics.densityDpi, displayMetrics.scaledDensity);
        this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
        this.mDisp.setHardwareAccelerated(isHardwareAccelerated);
        IDrawTask task = useDrwaingCache ? new CacheManagingDrawTask(timer, this.mContext, taskListener, (1048576 * AndroidUtils.getMemoryClass(context)) / 3) : new DrawTask(timer, this.mContext, taskListener);
        task.setParser(this.mParser);
        task.prepare();
        obtainMessage(10, Boolean.valueOf(false)).sendToTarget();
        return task;
    }

    public void seekTo(Long ms) {
        this.mInSeekingAction = true;
        this.mDesireSeekingTime = ms.longValue();
        removeMessages(2);
        removeMessages(3);
        removeMessages(4);
        obtainMessage(4, ms).sendToTarget();
    }

    public void addDanmaku(BaseDanmaku item) {
        if (this.drawTask != null) {
            item.flags = this.mContext.mGlobalFlagValues;
            item.setTimer(this.timer);
            this.drawTask.addDanmaku(item);
            obtainMessage(11).sendToTarget();
        }
    }

    public void invalidateDanmaku(BaseDanmaku item, boolean remeasure) {
        if (!(this.drawTask == null || item == null)) {
            this.drawTask.invalidateDanmaku(item, remeasure);
        }
        redrawIfNeeded();
    }

    public void resume() {
        sendEmptyMessage(3);
    }

    public void prepare() {
        sendEmptyMessage(5);
    }

    public void pause() {
        syncTimerIfNeeded();
        sendEmptyMessage(7);
    }

    public void showDanmakus(Long position) {
        if (!this.mDanmakusVisible) {
            this.mDanmakusVisible = true;
            removeMessages(8);
            removeMessages(9);
            obtainMessage(8, position).sendToTarget();
        }
    }

    public long hideDanmakus(boolean quitDrawTask) {
        if (!this.mDanmakusVisible) {
            return this.timer.currMillisecond;
        }
        this.mDanmakusVisible = false;
        removeMessages(8);
        removeMessages(9);
        obtainMessage(9, Boolean.valueOf(quitDrawTask)).sendToTarget();
        return this.timer.currMillisecond;
    }

    public boolean getVisibility() {
        return this.mDanmakusVisible;
    }

    public RenderingState draw(Canvas canvas) {
        if (this.drawTask == null) {
            return this.mRenderingState;
        }
        this.mDisp.setExtraData(canvas);
        this.mRenderingState.set(this.drawTask.draw(this.mDisp));
        recordRenderingTime();
        return this.mRenderingState;
    }

    private void redrawIfNeeded() {
        if (this.quitFlag && this.mDanmakusVisible) {
            obtainMessage(12).sendToTarget();
        }
    }

    private void notifyRendering() {
        if (this.mInWaitingState) {
            if (this.drawTask != null) {
                this.drawTask.requestClear();
            }
            if (this.mUpdateInNewThread) {
                synchronized (this) {
                    this.mDrawTimes.clear();
                }
                synchronized (this.drawTask) {
                    this.drawTask.notifyAll();
                }
            } else {
                this.mDrawTimes.clear();
                removeMessages(2);
                sendEmptyMessage(2);
            }
            this.mInWaitingState = false;
        }
    }

    private void waitRendering(long dTime) {
        this.mRenderingState.sysTime = SystemClock.uptimeMillis();
        this.mInWaitingState = true;
        if (this.mUpdateInNewThread) {
            if (this.mThread != null) {
                try {
                    synchronized (this.drawTask) {
                        if (dTime == INDEFINITE_TIME) {
                            this.drawTask.wait();
                        } else {
                            this.drawTask.wait(dTime);
                        }
                        sendEmptyMessage(11);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (dTime == INDEFINITE_TIME) {
            removeMessages(11);
            removeMessages(2);
        } else {
            removeMessages(11);
            removeMessages(2);
            sendEmptyMessageDelayed(11, dTime);
        }
    }

    private synchronized long getAverageRenderingTime() {
        long j;
        int frames = this.mDrawTimes.size();
        if (frames <= 0) {
            j = 0;
        } else {
            j = (((Long) this.mDrawTimes.getLast()).longValue() - ((Long) this.mDrawTimes.getFirst()).longValue()) / ((long) frames);
        }
        return j;
    }

    private synchronized void recordRenderingTime() {
        this.mDrawTimes.addLast(Long.valueOf(SystemClock.uptimeMillis()));
        if (this.mDrawTimes.size() > 500) {
            this.mDrawTimes.removeFirst();
        }
    }

    public IDisplayer getDisplayer() {
        return this.mDisp;
    }

    public void notifyDispSizeChanged(int width, int height) {
        if (this.mDisp != null) {
            if (this.mDisp.getWidth() != width || this.mDisp.getHeight() != height) {
                this.mDisp.setSize(width, height);
                obtainMessage(10, Boolean.valueOf(true)).sendToTarget();
            }
        }
    }

    public void removeAllDanmakus() {
        if (this.drawTask != null) {
            this.drawTask.removeAllDanmakus();
        }
    }

    public void removeAllLiveDanmakus() {
        if (this.drawTask != null) {
            this.drawTask.removeAllLiveDanmakus();
        }
    }

    public IDanmakus getCurrentVisibleDanmakus() {
        if (this.drawTask != null) {
            return this.drawTask.getVisibleDanmakusOnTime(getCurrentTime());
        }
        return null;
    }

    public long getCurrentTime() {
        if (!this.mReady) {
            return 0;
        }
        if (this.mInSeekingAction) {
            return this.mDesireSeekingTime;
        }
        if (this.quitFlag || !this.mInWaitingState) {
            return this.timer.currMillisecond - this.mRemainingTime;
        }
        return SystemClock.uptimeMillis() - this.mTimeBase;
    }

    public void clearDanmakusOnScreen() {
        obtainMessage(13).sendToTarget();
    }

    public DanmakuContext getConfig() {
        return this.mContext;
    }

    public BaseDanmaku getLastDanmaku() {
        if (this.drawTask != null) {
            return this.drawTask.getLastDanmaku();
        }
        return null;
    }
}
