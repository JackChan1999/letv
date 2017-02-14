package master.flame.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import com.letv.core.messagebus.config.LeMessageIds;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;

public class AndroidDisplayer extends AbsDisplayer<Canvas, Typeface> {
    public static final int BORDER_WIDTH = 4;
    private Paint ALPHA_PAINT;
    private boolean ANTI_ALIAS = this.CONFIG_ANTI_ALIAS;
    private Paint BORDER_PAINT;
    public boolean CONFIG_ANTI_ALIAS = true;
    public boolean CONFIG_HAS_PROJECTION = false;
    public boolean CONFIG_HAS_SHADOW = false;
    public boolean CONFIG_HAS_STROKE = true;
    private boolean HAS_PROJECTION = this.CONFIG_HAS_PROJECTION;
    private boolean HAS_SHADOW = this.CONFIG_HAS_SHADOW;
    private boolean HAS_STROKE = this.CONFIG_HAS_STROKE;
    public TextPaint PAINT = new TextPaint();
    public TextPaint PAINT_DUPLICATE;
    private float SHADOW_RADIUS = 4.0f;
    private float STROKE_WIDTH = 3.5f;
    public int UNDERLINE_HEIGHT = 4;
    private Paint UNDERLINE_PAINT;
    private Camera camera = new Camera();
    public Canvas canvas;
    private float density = 1.0f;
    private int densityDpi = 160;
    private int height;
    private boolean isTextScaled = false;
    private boolean isTranslucent;
    private boolean mIsHardwareAccelerated = true;
    private int mMaximumBitmapHeight = 2048;
    private int mMaximumBitmapWidth = 2048;
    private int mSlopPixel = 0;
    private Matrix matrix = new Matrix();
    private final Map<Float, Float> sCachedScaleSize = new HashMap(10);
    private float sLastScaleTextSize;
    private int sProjectionAlpha = LeMessageIds.MSG_MAIN_GO_TO_LIVE_ROOM;
    private float sProjectionOffsetX = 1.0f;
    private float sProjectionOffsetY = 1.0f;
    private BaseCacheStuffer sStuffer = new SimpleTextCacheStuffer();
    private float scaleTextSize = 1.0f;
    private float scaledDensity = 1.0f;
    private int transparency = AlphaValue.MAX;
    private int width;

    public AndroidDisplayer() {
        this.PAINT.setStrokeWidth(this.STROKE_WIDTH);
        this.PAINT_DUPLICATE = new TextPaint(this.PAINT);
        this.ALPHA_PAINT = new Paint();
        this.UNDERLINE_PAINT = new Paint();
        this.UNDERLINE_PAINT.setStrokeWidth((float) this.UNDERLINE_HEIGHT);
        this.UNDERLINE_PAINT.setStyle(Style.STROKE);
        this.BORDER_PAINT = new Paint();
        this.BORDER_PAINT.setStyle(Style.STROKE);
        this.BORDER_PAINT.setStrokeWidth(4.0f);
    }

    @SuppressLint({"NewApi"})
    private static final int getMaximumBitmapWidth(Canvas c) {
        if (VERSION.SDK_INT >= 14) {
            return c.getMaximumBitmapWidth();
        }
        return c.getWidth();
    }

    @SuppressLint({"NewApi"})
    private static final int getMaximumBitmapHeight(Canvas c) {
        if (VERSION.SDK_INT >= 14) {
            return c.getMaximumBitmapHeight();
        }
        return c.getHeight();
    }

    public void setTypeFace(Typeface font) {
        if (this.PAINT != null) {
            this.PAINT.setTypeface(font);
        }
    }

    public void setShadowRadius(float s) {
        this.SHADOW_RADIUS = s;
    }

    public void setPaintStorkeWidth(float s) {
        this.PAINT.setStrokeWidth(s);
        this.STROKE_WIDTH = s;
    }

    public void setProjectionConfig(float offsetX, float offsetY, int alpha) {
        if (this.sProjectionOffsetX != offsetX || this.sProjectionOffsetY != offsetY || this.sProjectionAlpha != alpha) {
            if (offsetX <= 1.0f) {
                offsetX = 1.0f;
            }
            this.sProjectionOffsetX = offsetX;
            if (offsetY <= 1.0f) {
                offsetY = 1.0f;
            }
            this.sProjectionOffsetY = offsetY;
            if (alpha < 0) {
                alpha = 0;
            } else if (alpha > 255) {
                alpha = 255;
            }
            this.sProjectionAlpha = alpha;
        }
    }

    public void setFakeBoldText(boolean fakeBoldText) {
        this.PAINT.setFakeBoldText(fakeBoldText);
    }

    public void setTransparency(int newTransparency) {
        this.isTranslucent = newTransparency != AlphaValue.MAX;
        this.transparency = newTransparency;
    }

    public void setScaleTextSizeFactor(float factor) {
        this.isTextScaled = factor != 1.0f;
        this.scaleTextSize = factor;
    }

    public void setCacheStuffer(BaseCacheStuffer cacheStuffer) {
        if (cacheStuffer != this.sStuffer) {
            this.sStuffer = cacheStuffer;
        }
    }

    public BaseCacheStuffer getCacheStuffer() {
        return this.sStuffer;
    }

    private void update(Canvas c) {
        this.canvas = c;
        if (c != null) {
            this.width = c.getWidth();
            this.height = c.getHeight();
            if (this.mIsHardwareAccelerated) {
                this.mMaximumBitmapWidth = getMaximumBitmapWidth(c);
                this.mMaximumBitmapHeight = getMaximumBitmapHeight(c);
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getDensity() {
        return this.density;
    }

    public int getDensityDpi() {
        return this.densityDpi;
    }

    public int draw(BaseDanmaku danmaku) {
        float top = danmaku.getTop();
        float left = danmaku.getLeft();
        if (this.canvas == null) {
            return 0;
        }
        Paint alphaPaint = null;
        boolean needRestore = false;
        if (danmaku.getType() == 7) {
            if (danmaku.getAlpha() == AlphaValue.TRANSPARENT) {
                return 0;
            }
            if (!(danmaku.rotationZ == 0.0f && danmaku.rotationY == 0.0f)) {
                saveCanvas(danmaku, this.canvas, left, top);
                needRestore = true;
            }
            if (danmaku.getAlpha() != AlphaValue.MAX) {
                alphaPaint = this.ALPHA_PAINT;
                alphaPaint.setAlpha(danmaku.getAlpha());
            }
        }
        if (alphaPaint != null && alphaPaint.getAlpha() == AlphaValue.TRANSPARENT) {
            return 0;
        }
        boolean cacheDrawn = false;
        int result = 1;
        if (danmaku.hasDrawingCache() && danmaku.cache != null) {
            DrawingCacheHolder holder = ((DrawingCache) danmaku.cache).get();
            if (holder != null) {
                cacheDrawn = holder.draw(this.canvas, left, top, alphaPaint);
            }
        }
        if (!cacheDrawn) {
            if (alphaPaint != null) {
                this.PAINT.setAlpha(alphaPaint.getAlpha());
            } else {
                resetPaintAlpha(this.PAINT);
            }
            drawDanmaku(danmaku, this.canvas, left, top, false);
            result = 2;
        }
        if (danmaku.isClickZan) {
            this.sStuffer.drawClickBg(danmaku, this.canvas, left, top);
        }
        if (needRestore) {
            restoreCanvas(this.canvas);
        }
        return result;
    }

    private void resetPaintAlpha(Paint paint) {
        if (paint.getAlpha() != AlphaValue.MAX) {
            paint.setAlpha(AlphaValue.MAX);
        }
    }

    private void restoreCanvas(Canvas canvas) {
        canvas.restore();
    }

    private int saveCanvas(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
        this.camera.save();
        this.camera.rotateY(-danmaku.rotationY);
        this.camera.rotateZ(-danmaku.rotationZ);
        this.camera.getMatrix(this.matrix);
        this.matrix.preTranslate(-left, -top);
        this.matrix.postTranslate(left, top);
        this.camera.restore();
        int count = canvas.save();
        canvas.concat(this.matrix);
        return count;
    }

    public synchronized void drawDanmaku(BaseDanmaku danmaku, Canvas canvas, float left, float top, boolean fromWorkerThread) {
        float _left = left;
        float _top = top;
        left += (float) danmaku.padding;
        top += (float) danmaku.padding;
        if (danmaku.borderColor != 0) {
            left += 4.0f;
            top += 4.0f;
        }
        this.HAS_STROKE = this.CONFIG_HAS_STROKE;
        this.HAS_SHADOW = this.CONFIG_HAS_SHADOW;
        this.HAS_PROJECTION = this.CONFIG_HAS_PROJECTION;
        boolean z = fromWorkerThread && this.CONFIG_ANTI_ALIAS;
        this.ANTI_ALIAS = z;
        TextPaint paint = getPaint(danmaku, !fromWorkerThread);
        this.sStuffer.drawBackground(danmaku, canvas, _left, _top);
        float strokeLeft;
        float strokeTop;
        if (danmaku.lines != null) {
            String[] lines = danmaku.lines;
            if (lines.length == 1) {
                if (hasStroke(danmaku)) {
                    applyPaintConfig(danmaku, paint, true);
                    strokeLeft = left;
                    strokeTop = top - paint.ascent();
                    if (this.HAS_PROJECTION) {
                        strokeLeft += this.sProjectionOffsetX;
                        strokeTop += this.sProjectionOffsetY;
                    }
                    this.sStuffer.drawStroke(danmaku, lines[0], canvas, strokeLeft, strokeTop, paint);
                }
                applyPaintConfig(danmaku, paint, false);
                this.sStuffer.drawText(danmaku, lines[0], canvas, left, top - paint.ascent(), paint, fromWorkerThread);
            } else {
                float textHeight = (danmaku.paintHeight - ((float) (danmaku.padding * 2))) / ((float) lines.length);
                int t = 0;
                while (t < lines.length) {
                    if (!(lines[t] == null || lines[t].length() == 0)) {
                        if (hasStroke(danmaku)) {
                            applyPaintConfig(danmaku, paint, true);
                            strokeLeft = left;
                            strokeTop = ((((float) t) * textHeight) + top) - paint.ascent();
                            if (this.HAS_PROJECTION) {
                                strokeLeft += this.sProjectionOffsetX;
                                strokeTop += this.sProjectionOffsetY;
                            }
                            this.sStuffer.drawStroke(danmaku, lines[t], canvas, strokeLeft, strokeTop, paint);
                        }
                        applyPaintConfig(danmaku, paint, false);
                        this.sStuffer.drawText(danmaku, lines[t], canvas, left, ((((float) t) * textHeight) + top) - paint.ascent(), paint, fromWorkerThread);
                    }
                    t++;
                }
            }
        } else {
            if (hasStroke(danmaku)) {
                applyPaintConfig(danmaku, paint, true);
                strokeLeft = left;
                strokeTop = top - paint.ascent();
                if (this.HAS_PROJECTION) {
                    strokeLeft += this.sProjectionOffsetX;
                    strokeTop += this.sProjectionOffsetY;
                }
                this.sStuffer.drawStroke(danmaku, null, canvas, strokeLeft, strokeTop, paint);
            }
            applyPaintConfig(danmaku, paint, false);
            this.sStuffer.drawText(danmaku, null, canvas, left, top - paint.ascent(), paint, fromWorkerThread);
        }
        if (danmaku.underlineColor != 0) {
            float bottom = (danmaku.paintHeight + _top) - ((float) this.UNDERLINE_HEIGHT);
            canvas.drawLine(_left, bottom, _left + danmaku.paintWidth, bottom, getUnderlinePaint(danmaku));
        }
        if (danmaku.borderColor != 0) {
            canvas.drawRect(_left, _top, _left + danmaku.paintWidth, _top + danmaku.paintHeight, getBorderPaint(danmaku));
        }
    }

    private boolean hasStroke(BaseDanmaku danmaku) {
        return (this.HAS_STROKE || this.HAS_PROJECTION) && this.STROKE_WIDTH > 0.0f && danmaku.textShadowColor != 0;
    }

    public Paint getBorderPaint(BaseDanmaku danmaku) {
        this.BORDER_PAINT.setColor(danmaku.borderColor);
        return this.BORDER_PAINT;
    }

    public Paint getUnderlinePaint(BaseDanmaku danmaku) {
        this.UNDERLINE_PAINT.setColor(danmaku.underlineColor);
        return this.UNDERLINE_PAINT;
    }

    private synchronized TextPaint getPaint(BaseDanmaku danmaku, boolean quick) {
        TextPaint paint;
        if (quick) {
            paint = this.PAINT_DUPLICATE;
            paint.set(this.PAINT);
        } else {
            paint = this.PAINT;
        }
        paint.reset();
        paint.setTextSize(danmaku.textSize);
        applyTextScaleConfig(danmaku, paint);
        if (!this.HAS_SHADOW || this.SHADOW_RADIUS <= 0.0f || danmaku.textShadowColor == 0) {
            paint.clearShadowLayer();
        } else {
            paint.setShadowLayer(this.SHADOW_RADIUS, 0.0f, 0.0f, danmaku.textShadowColor);
        }
        paint.setAntiAlias(this.ANTI_ALIAS);
        return paint;
    }

    public TextPaint getPaint(BaseDanmaku danmaku) {
        return getPaint(danmaku, false);
    }

    private void applyPaintConfig(BaseDanmaku danmaku, Paint paint, boolean stroke) {
        if (this.isTranslucent) {
            if (stroke) {
                paint.setStyle(this.HAS_PROJECTION ? Style.FILL : Style.STROKE);
                paint.setColor(danmaku.textShadowColor & ViewCompat.MEASURED_SIZE_MASK);
                paint.setAlpha(this.HAS_PROJECTION ? (int) (((float) this.sProjectionAlpha) * (((float) this.transparency) / ((float) AlphaValue.MAX))) : this.transparency);
                return;
            }
            paint.setStyle(Style.FILL);
            paint.setColor(danmaku.textColor & ViewCompat.MEASURED_SIZE_MASK);
            paint.setAlpha(this.transparency);
        } else if (stroke) {
            paint.setStyle(this.HAS_PROJECTION ? Style.FILL : Style.STROKE);
            paint.setColor(danmaku.textShadowColor & ViewCompat.MEASURED_SIZE_MASK);
            paint.setAlpha(this.HAS_PROJECTION ? this.sProjectionAlpha : AlphaValue.MAX);
        } else {
            paint.setStyle(Style.FILL);
            paint.setColor(danmaku.textColor & ViewCompat.MEASURED_SIZE_MASK);
            paint.setAlpha(AlphaValue.MAX);
        }
    }

    private void applyTextScaleConfig(BaseDanmaku danmaku, Paint paint) {
        if (this.isTextScaled) {
            Float size = (Float) this.sCachedScaleSize.get(Float.valueOf(danmaku.textSize));
            if (size == null || this.sLastScaleTextSize != this.scaleTextSize) {
                this.sLastScaleTextSize = this.scaleTextSize;
                size = Float.valueOf(danmaku.textSize * this.scaleTextSize);
                this.sCachedScaleSize.put(Float.valueOf(danmaku.textSize), size);
            }
            paint.setTextSize(size.floatValue());
        }
    }

    public void measure(BaseDanmaku danmaku, boolean fromWorkerThread) {
        TextPaint paint = getPaint(danmaku);
        if (this.HAS_STROKE) {
            applyPaintConfig(danmaku, paint, true);
        }
        calcPaintWH(danmaku, paint, fromWorkerThread);
        if (this.HAS_STROKE) {
            applyPaintConfig(danmaku, paint, false);
        }
    }

    private void calcPaintWH(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
        this.sStuffer.measure(danmaku, paint, fromWorkerThread);
        setDanmakuPaintWidthAndHeight(danmaku, danmaku.paintWidth, danmaku.paintHeight);
    }

    private void setDanmakuPaintWidthAndHeight(BaseDanmaku danmaku, float w, float h) {
        float pw = w + ((float) (danmaku.padding * 2));
        float ph = h + ((float) (danmaku.padding * 2));
        if (danmaku.borderColor != 0) {
            pw += 8.0f;
            ph += 8.0f;
        }
        danmaku.paintWidth = getStrokeWidth() + pw;
        danmaku.paintHeight = ph;
    }

    public void clearTextHeightCache() {
        this.sStuffer.clearCaches();
        this.sCachedScaleSize.clear();
    }

    public float getScaledDensity() {
        return this.scaledDensity;
    }

    public void resetSlopPixel(float factor) {
        float slop = Math.max(factor, ((float) getWidth()) / DanmakuFactory.BILI_PLAYER_WIDTH) * 25.0f;
        this.mSlopPixel = (int) slop;
        if (factor > 1.0f) {
            this.mSlopPixel = (int) (slop * factor);
        }
    }

    public int getSlopPixel() {
        return this.mSlopPixel;
    }

    public void setDensities(float density, int densityDpi, float scaledDensity) {
        this.density = density;
        this.densityDpi = densityDpi;
        this.scaledDensity = scaledDensity;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setDanmakuStyle(int style, float[] values) {
        switch (style) {
            case -1:
            case 2:
                this.CONFIG_HAS_SHADOW = false;
                this.CONFIG_HAS_STROKE = true;
                this.CONFIG_HAS_PROJECTION = false;
                setPaintStorkeWidth(values[0]);
                return;
            case 0:
                this.CONFIG_HAS_SHADOW = false;
                this.CONFIG_HAS_STROKE = false;
                this.CONFIG_HAS_PROJECTION = false;
                return;
            case 1:
                this.CONFIG_HAS_SHADOW = true;
                this.CONFIG_HAS_STROKE = false;
                this.CONFIG_HAS_PROJECTION = false;
                setShadowRadius(values[0]);
                return;
            case 3:
                this.CONFIG_HAS_SHADOW = false;
                this.CONFIG_HAS_STROKE = false;
                this.CONFIG_HAS_PROJECTION = true;
                setProjectionConfig(values[0], values[1], (int) values[2]);
                return;
            default:
                return;
        }
    }

    public void setExtraData(Canvas data) {
        update(data);
    }

    public Canvas getExtraData() {
        return this.canvas;
    }

    public float getStrokeWidth() {
        if (this.HAS_SHADOW && this.HAS_STROKE) {
            return Math.max(this.SHADOW_RADIUS, this.STROKE_WIDTH);
        }
        if (this.HAS_SHADOW) {
            return this.SHADOW_RADIUS;
        }
        if (this.HAS_STROKE) {
            return this.STROKE_WIDTH;
        }
        return 0.0f;
    }

    public void setHardwareAccelerated(boolean enable) {
        this.mIsHardwareAccelerated = enable;
    }

    public boolean isHardwareAccelerated() {
        return this.mIsHardwareAccelerated;
    }

    public int getMaximumCacheWidth() {
        return this.mMaximumBitmapWidth;
    }

    public int getMaximumCacheHeight() {
        return this.mMaximumBitmapHeight;
    }
}
