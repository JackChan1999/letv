package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

public abstract class BaseCacheStuffer {
    protected Proxy mProxy;

    public static abstract class Proxy {
        public abstract void prepareDrawing(BaseDanmaku baseDanmaku, boolean z);

        public abstract void releaseResource(BaseDanmaku baseDanmaku);
    }

    public abstract void clearCaches();

    public abstract void drawBackground(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2);

    public abstract void drawClickBg(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2);

    public abstract void drawStroke(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f, float f2, Paint paint);

    public abstract void drawText(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f, float f2, TextPaint textPaint, boolean z);

    public abstract void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z);

    public void clearCache(BaseDanmaku danmaku) {
    }

    public void setProxy(Proxy adapter) {
        this.mProxy = adapter;
    }

    public void releaseResource(BaseDanmaku danmaku) {
        if (this.mProxy != null) {
            this.mProxy.releaseResource(danmaku);
        }
    }
}
