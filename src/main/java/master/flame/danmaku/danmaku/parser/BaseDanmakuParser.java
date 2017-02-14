package master.flame.danmaku.danmaku.parser;

import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;

public abstract class BaseDanmakuParser {
    protected DanmakuContext mContext;
    private IDanmakus mDanmakus;
    protected IDataSource<?> mDataSource;
    protected IDisplayer mDisp;
    protected float mDispDensity;
    protected int mDispHeight;
    protected int mDispWidth;
    protected float mScaledDensity;
    protected DanmakuTimer mTimer;

    protected abstract IDanmakus parse();

    public BaseDanmakuParser setDisplayer(IDisplayer disp) {
        this.mDisp = disp;
        this.mDispWidth = disp.getWidth();
        this.mDispHeight = disp.getHeight();
        this.mDispDensity = disp.getDensity();
        this.mScaledDensity = disp.getScaledDensity();
        this.mContext.mDanmakuFactory.updateViewportState((float) this.mDispWidth, (float) this.mDispHeight, getViewportSizeFactor());
        this.mContext.mDanmakuFactory.updateMaxDanmakuDuration();
        return this;
    }

    protected float getViewportSizeFactor() {
        return 1.0f / (this.mDispDensity - 0.6f);
    }

    public IDisplayer getDisplayer() {
        return this.mDisp;
    }

    public BaseDanmakuParser load(IDataSource<?> source) {
        this.mDataSource = source;
        return this;
    }

    public BaseDanmakuParser setTimer(DanmakuTimer timer) {
        this.mTimer = timer;
        return this;
    }

    public DanmakuTimer getTimer() {
        return this.mTimer;
    }

    public IDanmakus getDanmakus() {
        if (this.mDanmakus != null) {
            return this.mDanmakus;
        }
        this.mContext.mDanmakuFactory.resetDurationsData();
        this.mDanmakus = parse();
        releaseDataSource();
        this.mContext.mDanmakuFactory.updateMaxDanmakuDuration();
        return this.mDanmakus;
    }

    protected void releaseDataSource() {
        if (this.mDataSource != null) {
            this.mDataSource.release();
        }
        this.mDataSource = null;
    }

    public void release() {
        releaseDataSource();
    }

    public BaseDanmakuParser setConfig(DanmakuContext config) {
        this.mContext = config;
        return this;
    }
}
