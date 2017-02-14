package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import java.lang.ref.SoftReference;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

public class SpannedCacheStuffer extends SimpleTextCacheStuffer {
    public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
        if (danmaku.text instanceof Spanned) {
            if (this.mProxy != null) {
                this.mProxy.prepareDrawing(danmaku, fromWorkerThread);
            }
            CharSequence text = danmaku.text;
            if (text != null) {
                StaticLayout staticLayout = new StaticLayout(text, paint, (int) StaticLayout.getDesiredWidth(danmaku.text, paint), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                danmaku.paintWidth = (float) staticLayout.getWidth();
                danmaku.paintHeight = (float) staticLayout.getHeight();
                danmaku.obj = new SoftReference(staticLayout);
                return;
            }
        }
        super.measure(danmaku, paint, fromWorkerThread);
    }

    public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
        if (danmaku.obj == null) {
            super.drawStroke(danmaku, lineText, canvas, left, top, paint);
        }
    }

    public void drawText(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, TextPaint paint, boolean fromWorkerThread) {
        if (danmaku.obj == null) {
            super.drawText(danmaku, lineText, canvas, left, top, paint, fromWorkerThread);
            return;
        }
        StaticLayout staticLayout = (StaticLayout) danmaku.obj.get();
        boolean requestRemeasure = (danmaku.requestFlags & 1) != 0;
        boolean requestInvalidate = (danmaku.requestFlags & 2) != 0;
        if (requestInvalidate || staticLayout == null) {
            if (requestInvalidate) {
                danmaku.requestFlags &= -3;
            } else if (this.mProxy != null) {
                this.mProxy.prepareDrawing(danmaku, fromWorkerThread);
            }
            CharSequence text = danmaku.text;
            if (text != null) {
                if (requestRemeasure) {
                    staticLayout = new StaticLayout(text, paint, (int) StaticLayout.getDesiredWidth(danmaku.text, paint), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                    danmaku.paintWidth = (float) staticLayout.getWidth();
                    danmaku.paintHeight = (float) staticLayout.getHeight();
                    danmaku.requestFlags &= -2;
                } else {
                    staticLayout = new StaticLayout(text, paint, (int) danmaku.paintWidth, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                }
                danmaku.obj = new SoftReference(staticLayout);
            } else {
                return;
            }
        }
        boolean needRestore = false;
        if (!(left == 0.0f || top == 0.0f)) {
            canvas.save();
            canvas.translate(left, paint.ascent() + top);
            needRestore = true;
        }
        staticLayout.draw(canvas);
        if (needRestore) {
            canvas.restore();
        }
    }

    public void clearCaches() {
        super.clearCaches();
        System.gc();
    }

    public void clearCache(BaseDanmaku danmaku) {
        super.clearCache(danmaku);
        if (danmaku.obj instanceof SoftReference) {
            ((SoftReference) danmaku.obj).clear();
        }
    }

    public void releaseResource(BaseDanmaku danmaku) {
        clearCache(danmaku);
        super.releaseResource(danmaku);
    }
}
