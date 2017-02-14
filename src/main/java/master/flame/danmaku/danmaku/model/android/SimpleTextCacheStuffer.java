package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextPaint;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

public class SimpleTextCacheStuffer extends BaseCacheStuffer {
    private static final Map<Float, Float> sTextHeightCache = new HashMap();

    protected Float getCacheHeight(BaseDanmaku danmaku, Paint paint) {
        Float textSize = Float.valueOf(paint.getTextSize());
        Float textHeight = (Float) sTextHeightCache.get(textSize);
        if (textHeight != null) {
            return textHeight;
        }
        FontMetrics fontMetrics = paint.getFontMetrics();
        textHeight = Float.valueOf((fontMetrics.descent - fontMetrics.ascent) + fontMetrics.leading);
        sTextHeightCache.put(textSize, textHeight);
        return textHeight;
    }

    public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
        if (this.mProxy != null) {
            this.mProxy.prepareDrawing(danmaku, fromWorkerThread);
        }
        float w = 0.0f;
        Float textHeight = Float.valueOf(0.0f);
        if (danmaku.lines == null) {
            if (danmaku.text == null) {
                w = 0.0f;
            } else {
                w = paint.measureText(danmaku.text.toString());
                textHeight = getCacheHeight(danmaku, paint);
            }
            danmaku.paintWidth = w;
            danmaku.paintHeight = textHeight.floatValue();
            return;
        }
        textHeight = getCacheHeight(danmaku, paint);
        for (String tempStr : danmaku.lines) {
            if (tempStr.length() > 0) {
                w = Math.max(paint.measureText(tempStr), w);
            }
        }
        danmaku.paintWidth = w;
        danmaku.paintHeight = ((float) danmaku.lines.length) * textHeight.floatValue();
    }

    public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
        if (lineText != null) {
            canvas.drawText(lineText, left, top, paint);
        } else {
            canvas.drawText(danmaku.text.toString(), left, top, paint);
        }
    }

    public void drawText(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, TextPaint paint, boolean fromWorkerThread) {
        if (lineText != null) {
            canvas.drawText(lineText, left, top, paint);
        } else {
            canvas.drawText(danmaku.text.toString(), left, top, paint);
        }
    }

    public void clearCaches() {
        sTextHeightCache.clear();
    }

    public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
    }

    public void drawClickBg(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
    }
}
