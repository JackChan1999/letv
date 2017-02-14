package master.flame.danmaku.danmaku.model;

import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;

public abstract class AbsDisplayer<T, F> implements IDisplayer {
    public abstract void clearTextHeightCache();

    public abstract void drawDanmaku(BaseDanmaku baseDanmaku, T t, float f, float f2, boolean z);

    public abstract BaseCacheStuffer getCacheStuffer();

    public abstract T getExtraData();

    public abstract void setCacheStuffer(BaseCacheStuffer baseCacheStuffer);

    public abstract void setExtraData(T t);

    public abstract void setFakeBoldText(boolean z);

    public abstract void setScaleTextSizeFactor(float f);

    public abstract void setTransparency(int i);

    public abstract void setTypeFace(F f);

    public boolean isHardwareAccelerated() {
        return false;
    }
}
