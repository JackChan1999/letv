package master.flame.danmaku.controller;

import android.graphics.Canvas;
import android.os.SystemClock;
import master.flame.danmaku.controller.IDrawTask.TaskListener;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.DanmakuContext.ConfigChangedCallback;
import master.flame.danmaku.danmaku.model.android.DanmakuContext.DanmakuConfigTag;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;
import master.flame.danmaku.danmaku.renderer.IRenderer.OnDanmakuShownListener;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import master.flame.danmaku.danmaku.renderer.android.DanmakuRenderer;

public class DrawTask implements IDrawTask {
    static final /* synthetic */ boolean $assertionsDisabled = (!DrawTask.class.desiredAssertionStatus());
    protected boolean clearRetainerFlag;
    protected IDanmakus danmakuList;
    private IDanmakus danmakus = new Danmakus(0);
    private ConfigChangedCallback mConfigChangedCallback = new ConfigChangedCallback() {
        public boolean onDanmakuConfigChanged(DanmakuContext config, DanmakuConfigTag tag, Object... values) {
            return DrawTask.this.onDanmakuConfigChanged(config, tag, values);
        }
    };
    protected final DanmakuContext mContext;
    protected final AbsDisplayer mDisp;
    private boolean mIsHidden;
    private long mLastBeginMills;
    private BaseDanmaku mLastDanmaku;
    private long mLastEndMills;
    protected BaseDanmakuParser mParser;
    protected boolean mReadyState;
    final IRenderer mRenderer;
    private RenderingState mRenderingState = new RenderingState();
    private long mStartRenderTime = 0;
    TaskListener mTaskListener;
    DanmakuTimer mTimer;

    public DrawTask(DanmakuTimer timer, DanmakuContext context, TaskListener taskListener) {
        boolean z = false;
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        this.mContext = context;
        this.mDisp = context.getDisplayer();
        this.mTaskListener = taskListener;
        this.mRenderer = new DanmakuRenderer(context);
        this.mRenderer.setOnDanmakuShownListener(new OnDanmakuShownListener() {
            public void onDanmakuShown(BaseDanmaku danmaku) {
                if (DrawTask.this.mTaskListener != null) {
                    DrawTask.this.mTaskListener.onDanmakuShown(danmaku);
                }
            }
        });
        IRenderer iRenderer = this.mRenderer;
        if (this.mContext.isPreventOverlappingEnabled() || this.mContext.isMaxLinesLimited()) {
            z = true;
        }
        iRenderer.setVerifierEnabled(z);
        initTimer(timer);
        Boolean enable = Boolean.valueOf(this.mContext.isDuplicateMergingEnabled());
        if (enable == null) {
            return;
        }
        if (enable.booleanValue()) {
            this.mContext.mDanmakuFilters.registerFilter(DanmakuFilters.TAG_DUPLICATE_FILTER);
        } else {
            this.mContext.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_DUPLICATE_FILTER);
        }
    }

    protected void initTimer(DanmakuTimer timer) {
        this.mTimer = timer;
    }

    public synchronized void addDanmaku(BaseDanmaku item) {
        if (this.danmakuList != null) {
            boolean added;
            if (item.isLive) {
                removeUnusedLiveDanmakusIn(10);
            }
            item.index = this.danmakuList.size();
            if (this.mLastBeginMills <= item.time && item.time <= this.mLastEndMills) {
                synchronized (this.danmakus) {
                    added = this.danmakus.addItem(item);
                }
            } else if (item.isLive) {
                this.mLastEndMills = 0;
                this.mLastBeginMills = 0;
            }
            synchronized (this.danmakuList) {
                added = this.danmakuList.addItem(item);
            }
            if (added) {
                if (this.mTaskListener != null) {
                    this.mTaskListener.onDanmakuAdd(item);
                }
            }
            if (this.mLastDanmaku == null || !(item == null || this.mLastDanmaku == null || item.time <= this.mLastDanmaku.time)) {
                this.mLastDanmaku = item;
            }
        }
    }

    public void invalidateDanmaku(BaseDanmaku item, boolean remeasure) {
        this.mContext.getDisplayer().getCacheStuffer().clearCache(item);
        if (remeasure) {
            item.paintWidth = -1.0f;
            item.paintHeight = -1.0f;
        }
    }

    public synchronized void removeAllDanmakus() {
        if (!(this.danmakuList == null || this.danmakuList.isEmpty())) {
            this.danmakuList.clear();
        }
    }

    protected void onDanmakuRemoved(BaseDanmaku danmaku) {
    }

    public synchronized void removeAllLiveDanmakus() {
        if (!(this.danmakus == null || this.danmakus.isEmpty())) {
            synchronized (this.danmakus) {
                IDanmakuIterator it = this.danmakus.iterator();
                while (it.hasNext()) {
                    BaseDanmaku danmaku = it.next();
                    if (danmaku.isLive) {
                        it.remove();
                        onDanmakuRemoved(danmaku);
                    }
                }
            }
        }
    }

    protected synchronized void removeUnusedLiveDanmakusIn(int msec) {
        if (this.danmakuList != null && !this.danmakuList.isEmpty()) {
            long startTime = SystemClock.uptimeMillis();
            IDanmakuIterator it = this.danmakuList.iterator();
            while (it.hasNext()) {
                BaseDanmaku danmaku = it.next();
                boolean isTimeout = danmaku.isTimeOut();
                if (isTimeout && danmaku.isLive) {
                    it.remove();
                    onDanmakuRemoved(danmaku);
                }
                if (isTimeout) {
                    if (SystemClock.uptimeMillis() - startTime > ((long) msec)) {
                        break;
                    }
                }
                break;
            }
        }
    }

    public IDanmakus getVisibleDanmakusOnTime(long time) {
        IDanmakus subDanmakus = this.danmakuList.subnew((time - this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) - 100, time + this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
        IDanmakus visibleDanmakus = new Danmakus();
        if (!(subDanmakus == null || subDanmakus.isEmpty())) {
            IDanmakuIterator iterator = subDanmakus.iterator();
            while (iterator.hasNext()) {
                BaseDanmaku danmaku = iterator.next();
                if (danmaku.isShown() && !danmaku.isOutside()) {
                    visibleDanmakus.addItem(danmaku);
                }
            }
        }
        return visibleDanmakus;
    }

    public synchronized RenderingState draw(AbsDisplayer displayer) {
        return drawDanmakus(displayer, this.mTimer);
    }

    public void reset() {
        if (this.danmakus != null) {
            this.danmakus.clear();
        }
        if (this.mRenderer != null) {
            this.mRenderer.clear();
        }
    }

    public void seek(long mills) {
        reset();
        this.mContext.mGlobalFlagValues.updateVisibleFlag();
        if (mills < 1000) {
            mills = 0;
        }
        this.mStartRenderTime = mills;
    }

    public void clearDanmakusOnScreen(long currMillis) {
        reset();
        this.mContext.mGlobalFlagValues.updateVisibleFlag();
        this.mStartRenderTime = currMillis;
    }

    public void start() {
        this.mContext.registerConfigChangedCallback(this.mConfigChangedCallback);
    }

    public void quit() {
        this.mContext.unregisterAllConfigChangedCallbacks();
        if (this.mRenderer != null) {
            this.mRenderer.release();
        }
    }

    public void prepare() {
        if ($assertionsDisabled || this.mParser != null) {
            loadDanmakus(this.mParser);
            if (this.mTaskListener != null) {
                this.mTaskListener.ready();
                this.mReadyState = true;
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    protected void loadDanmakus(BaseDanmakuParser parser) {
        this.danmakuList = parser.setConfig(this.mContext).setDisplayer(this.mDisp).setTimer(this.mTimer).getDanmakus();
        if (!(this.danmakuList == null || this.danmakuList.isEmpty() || this.danmakuList.first().flags != null)) {
            IDanmakuIterator it = this.danmakuList.iterator();
            while (it.hasNext()) {
                BaseDanmaku item = it.next();
                if (item != null) {
                    item.flags = this.mContext.mGlobalFlagValues;
                }
            }
        }
        this.mContext.mGlobalFlagValues.resetAll();
        if (this.danmakuList != null) {
            this.mLastDanmaku = this.danmakuList.last();
        }
    }

    public void setParser(BaseDanmakuParser parser) {
        this.mParser = parser;
        this.mReadyState = false;
    }

    protected RenderingState drawDanmakus(AbsDisplayer disp, DanmakuTimer timer) {
        if (this.clearRetainerFlag) {
            this.mRenderer.clearRetainer();
            this.clearRetainerFlag = false;
        }
        if (this.danmakuList == null) {
            return null;
        }
        DrawHelper.clearCanvas((Canvas) disp.getExtraData());
        if (this.mIsHidden) {
            return this.mRenderingState;
        }
        long beginMills = (timer.currMillisecond - this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) - 100;
        long endMills = timer.currMillisecond + this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION;
        if (this.mLastBeginMills > beginMills || timer.currMillisecond > this.mLastEndMills) {
            IDanmakus subDanmakus = this.danmakuList.sub(beginMills, endMills);
            if (subDanmakus != null) {
                this.danmakus = subDanmakus;
            } else {
                this.danmakus.clear();
            }
            this.mLastBeginMills = beginMills;
            this.mLastEndMills = endMills;
        } else {
            beginMills = this.mLastBeginMills;
            endMills = this.mLastEndMills;
        }
        if (this.danmakus == null || this.danmakus.isEmpty()) {
            this.mRenderingState.nothingRendered = true;
            this.mRenderingState.beginTime = beginMills;
            this.mRenderingState.endTime = endMills;
            return this.mRenderingState;
        }
        RenderingState renderingState = this.mRenderer.draw(this.mDisp, this.danmakus, this.mStartRenderTime);
        this.mRenderingState = renderingState;
        if (!renderingState.nothingRendered) {
            return renderingState;
        }
        if (!(this.mTaskListener == null || this.mLastDanmaku == null || !this.mLastDanmaku.isTimeOut())) {
            this.mTaskListener.onDanmakusDrawingFinished();
        }
        if (renderingState.beginTime == -1) {
            renderingState.beginTime = beginMills;
        }
        if (renderingState.endTime != -1) {
            return renderingState;
        }
        renderingState.endTime = endMills;
        return renderingState;
    }

    public void requestClear() {
        this.mLastEndMills = 0;
        this.mLastBeginMills = 0;
        this.mIsHidden = false;
    }

    public void requestClearRetainer() {
        this.clearRetainerFlag = true;
    }

    public boolean onDanmakuConfigChanged(DanmakuContext config, DanmakuConfigTag tag, Object... values) {
        boolean handled = handleOnDanmakuConfigChanged(config, tag, values);
        if (this.mTaskListener != null) {
            this.mTaskListener.onDanmakuConfigChanged();
        }
        return handled;
    }

    protected boolean handleOnDanmakuConfigChanged(DanmakuContext config, DanmakuConfigTag tag, Object[] values) {
        boolean z = false;
        if (tag == null || DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN.equals(tag)) {
            return true;
        }
        if (DanmakuConfigTag.DUPLICATE_MERGING_ENABLED.equals(tag)) {
            Boolean enable = values[0];
            if (enable == null) {
                return false;
            }
            if (enable.booleanValue()) {
                this.mContext.mDanmakuFilters.registerFilter(DanmakuFilters.TAG_DUPLICATE_FILTER);
            } else {
                this.mContext.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_DUPLICATE_FILTER);
            }
            return true;
        } else if (DanmakuConfigTag.SCALE_TEXTSIZE.equals(tag) || DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(tag)) {
            requestClearRetainer();
            return false;
        } else if (!DanmakuConfigTag.MAXIMUN_LINES.equals(tag) && !DanmakuConfigTag.OVERLAPPING_ENABLE.equals(tag)) {
            return false;
        } else {
            if (this.mRenderer != null) {
                IRenderer iRenderer = this.mRenderer;
                if (this.mContext.isPreventOverlappingEnabled() || this.mContext.isMaxLinesLimited()) {
                    z = true;
                }
                iRenderer.setVerifierEnabled(z);
            }
            return true;
        }
    }

    public void requestHide() {
        this.mIsHidden = true;
    }

    public BaseDanmaku getLastDanmaku() {
        if (this.danmakuList != null) {
            return this.danmakuList.last();
        }
        return null;
    }
}
