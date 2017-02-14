package master.flame.danmaku.danmaku.renderer.android;

import android.os.SystemClock;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.ICacheManager;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.renderer.IRenderer.OnDanmakuShownListener;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import master.flame.danmaku.danmaku.renderer.Renderer;
import master.flame.danmaku.danmaku.renderer.android.DanmakusRetainer.Verifier;

public class DanmakuRenderer extends Renderer {
    private ICacheManager mCacheManager;
    private final DanmakuContext mContext;
    private final DanmakusRetainer mDanmakusRetainer;
    private OnDanmakuShownListener mOnDanmakuShownListener;
    private final RenderingState mRenderingState = new RenderingState();
    private final DanmakuTimer mStartTimer = new DanmakuTimer();
    private Verifier mVerifier;
    private final Verifier verifier = new Verifier() {
        public boolean skipLayout(BaseDanmaku danmaku, float fixedTop, int lines, boolean willHit) {
            boolean isFilter = DanmakuRenderer.this.mContext.mDanmakuFilters.filterSecondary(danmaku, lines, 0, DanmakuRenderer.this.mStartTimer, willHit, DanmakuRenderer.this.mContext);
            if ((danmaku.priority != (byte) 0 || !isFilter) && (!isFilter || !danmaku.isDanmakuTypeFiltered)) {
                return false;
            }
            danmaku.setVisibility(false);
            return true;
        }
    };

    public DanmakuRenderer(DanmakuContext config) {
        this.mContext = config;
        this.mDanmakusRetainer = new DanmakusRetainer();
    }

    public void clear() {
        clearRetainer();
        this.mContext.mDanmakuFilters.clear();
    }

    public void clearRetainer() {
        this.mDanmakusRetainer.clear();
    }

    public void release() {
        this.mDanmakusRetainer.release();
        this.mContext.mDanmakuFilters.clear();
    }

    public void setVerifierEnabled(boolean enabled) {
        this.mVerifier = enabled ? this.verifier : null;
    }

    public RenderingState draw(IDisplayer disp, IDanmakus danmakus, long startRenderTime) {
        int lastTotalDanmakuCount = this.mRenderingState.totalDanmakuCount;
        this.mRenderingState.reset();
        IDanmakuIterator itr = danmakus.iterator();
        int orderInScreen = 0;
        this.mStartTimer.update(SystemClock.uptimeMillis());
        int sizeInScreen = danmakus.size();
        BaseDanmaku drawItem = null;
        while (itr.hasNext()) {
            drawItem = itr.next();
            if (!drawItem.hasPassedFilter()) {
                this.mContext.mDanmakuFilters.filter(drawItem, orderInScreen, sizeInScreen, this.mStartTimer, false, this.mContext);
            }
            if (drawItem.time >= startRenderTime && (!(drawItem.priority == (byte) 0 && drawItem.isFiltered()) && drawItem.time >= startRenderTime)) {
                if (!drawItem.isDanmakuTypeFiltered || !drawItem.isFiltered()) {
                    if (drawItem.isLate()) {
                        if (this.mCacheManager == null || drawItem.hasDrawingCache()) {
                            break;
                        }
                        this.mCacheManager.addDanmaku(drawItem);
                    } else {
                        orderInScreen++;
                        if (!drawItem.isMeasured()) {
                            drawItem.measure(disp, false);
                        }
                        this.mDanmakusRetainer.fix(drawItem, disp, this.mVerifier);
                        if (!drawItem.isOutside() && drawItem.isShown()) {
                            if (drawItem.lines != null || drawItem.getBottom() <= ((float) disp.getHeight())) {
                                int renderingType = drawItem.draw(disp);
                                RenderingState renderingState;
                                if (renderingType == 1) {
                                    renderingState = this.mRenderingState;
                                    renderingState.cacheHitCount++;
                                } else if (renderingType == 2) {
                                    renderingState = this.mRenderingState;
                                    renderingState.cacheMissCount++;
                                    if (this.mCacheManager != null) {
                                        this.mCacheManager.addDanmaku(drawItem);
                                    }
                                }
                                this.mRenderingState.addCount(drawItem.getType(), 1);
                                this.mRenderingState.addTotalCount(1);
                                if (!(this.mOnDanmakuShownListener == null || drawItem.firstShownFlag == this.mContext.mGlobalFlagValues.FIRST_SHOWN_RESET_FLAG)) {
                                    drawItem.firstShownFlag = this.mContext.mGlobalFlagValues.FIRST_SHOWN_RESET_FLAG;
                                    this.mOnDanmakuShownListener.onDanmakuShown(drawItem);
                                }
                            }
                        }
                    }
                }
            }
        }
        this.mRenderingState.nothingRendered = this.mRenderingState.totalDanmakuCount == 0;
        this.mRenderingState.endTime = drawItem != null ? drawItem.time : -1;
        if (this.mRenderingState.nothingRendered) {
            this.mRenderingState.beginTime = -1;
        }
        this.mRenderingState.incrementCount = this.mRenderingState.totalDanmakuCount - lastTotalDanmakuCount;
        this.mRenderingState.consumingTime = this.mStartTimer.update(SystemClock.uptimeMillis());
        return this.mRenderingState;
    }

    public void setCacheManager(ICacheManager cacheManager) {
        this.mCacheManager = cacheManager;
    }

    public void setOnDanmakuShownListener(OnDanmakuShownListener onDanmakuShownListener) {
        this.mOnDanmakuShownListener = onDanmakuShownListener;
    }

    public void removeOnDanmakuShownListener() {
        this.mOnDanmakuShownListener = null;
    }
}
