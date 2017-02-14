package com.letv.android.client.commonlib.view;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ImageView.ScaleType;
import java.util.HashSet;
import java.util.Set;

public class RoundedPagerDrawable extends Drawable {
    public static final int DEFAULT_BORDER_COLOR = -16777216;
    public static final String TAG = "RoundedDrawable";
    private final Bitmap mBitmap;
    private final int mBitmapHeight;
    private final Paint mBitmapPaint;
    private final RectF mBitmapRect = new RectF();
    private final int mBitmapWidth;
    private ColorStateList mBorderColor = ColorStateList.valueOf(-16777216);
    private final Paint mBorderPaint;
    private final RectF mBorderRect = new RectF();
    private float mBorderWidth = 0.0f;
    private final RectF mBounds = new RectF();
    private float mCornerRadius = 0.0f;
    private final boolean[] mCornersRounded = new boolean[]{true, true, true, true};
    private final RectF mDrawableRect = new RectF();
    private boolean mOval = false;
    private boolean mRebuildShader = true;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private final Matrix mShaderMatrix = new Matrix();
    private final RectF mSquareCornersRect = new RectF();
    private TileMode mTileModeX = TileMode.CLAMP;
    private TileMode mTileModeY = TileMode.CLAMP;

    public RoundedPagerDrawable(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.mBitmapWidth = bitmap.getWidth();
        this.mBitmapHeight = bitmap.getHeight();
        this.mBitmapRect.set(0.0f, 0.0f, (float) this.mBitmapWidth, (float) this.mBitmapHeight);
        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setStyle(Style.FILL);
        this.mBitmapPaint.setAntiAlias(true);
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
    }

    public static RoundedPagerDrawable fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new RoundedPagerDrawable(bitmap);
        }
        return null;
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable == null || (drawable instanceof RoundedPagerDrawable)) {
            return drawable;
        }
        if (drawable instanceof LayerDrawable) {
            Drawable ld = (LayerDrawable) drawable;
            int num = ld.getNumberOfLayers();
            for (int i = 0; i < num; i++) {
                ld.setDrawableByLayerId(ld.getId(i), fromDrawable(ld.getDrawable(i)));
            }
            return ld;
        }
        Bitmap bm = drawableToBitmap(drawable);
        if (bm != null) {
            return new RoundedPagerDrawable(bm);
        }
        return drawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Failed to create bitmap from drawable!");
            return null;
        }
    }

    public Bitmap getSourceBitmap() {
        return this.mBitmap;
    }

    public boolean isStateful() {
        return this.mBorderColor.isStateful();
    }

    protected boolean onStateChange(int[] state) {
        int newColor = this.mBorderColor.getColorForState(state, 0);
        if (this.mBorderPaint.getColor() == newColor) {
            return super.onStateChange(state);
        }
        this.mBorderPaint.setColor(newColor);
        return true;
    }

    private void updateShaderMatrix() {
        float dx;
        float dy;
        float scale;
        switch (1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
            case 1:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setTranslate((float) ((int) (((this.mBorderRect.width() - ((float) this.mBitmapWidth)) * 0.5f) + 0.5f)), (float) ((int) (((this.mBorderRect.height() - ((float) this.mBitmapHeight)) * 0.5f) + 0.5f)));
                break;
            case 2:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.reset();
                dx = 0.0f;
                dy = 0.0f;
                if (((float) this.mBitmapWidth) * this.mBorderRect.height() > this.mBorderRect.width() * ((float) this.mBitmapHeight)) {
                    scale = this.mBorderRect.height() / ((float) this.mBitmapHeight);
                    dx = (this.mBorderRect.width() - (((float) this.mBitmapWidth) * scale)) * 0.5f;
                } else {
                    scale = this.mBorderRect.width() / ((float) this.mBitmapWidth);
                    dy = (this.mBorderRect.height() - (((float) this.mBitmapHeight) * scale)) * 0.5f;
                }
                this.mShaderMatrix.setScale(scale, scale);
                this.mShaderMatrix.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (dy + 0.5f)));
                break;
            case 3:
                this.mShaderMatrix.reset();
                if (((float) this.mBitmapWidth) > this.mBounds.width() || ((float) this.mBitmapHeight) > this.mBounds.height()) {
                    scale = Math.min(this.mBounds.width() / ((float) this.mBitmapWidth), this.mBounds.height() / ((float) this.mBitmapHeight));
                } else {
                    scale = 1.0f;
                }
                dx = (float) ((int) (((this.mBounds.width() - (((float) this.mBitmapWidth) * scale)) * 0.5f) + 0.5f));
                dy = (float) ((int) (((this.mBounds.height() - (((float) this.mBitmapHeight) * scale)) * 0.5f) + 0.5f));
                this.mShaderMatrix.setScale(scale, scale);
                this.mShaderMatrix.postTranslate(dx, dy);
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            case 5:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.END);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            case 6:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.START);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            case 7:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            default:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.CENTER);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
        }
        this.mDrawableRect.set(this.mBorderRect);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mBounds.set(bounds);
        updateShaderMatrix();
    }

    public void draw(Canvas canvas) {
        if (this.mRebuildShader) {
            BitmapShader bitmapShader = new BitmapShader(this.mBitmap, this.mTileModeX, this.mTileModeY);
            if (this.mTileModeX == TileMode.CLAMP && this.mTileModeY == TileMode.CLAMP) {
                bitmapShader.setLocalMatrix(this.mShaderMatrix);
            }
            this.mBitmapPaint.setShader(bitmapShader);
            this.mRebuildShader = false;
        }
        if (this.mOval) {
            if (this.mBorderWidth > 0.0f) {
                canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
                canvas.drawOval(this.mBorderRect, this.mBorderPaint);
                return;
            }
            canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
        } else if (any(this.mCornersRounded)) {
            float radius = this.mCornerRadius;
            if (this.mBorderWidth > 0.0f) {
                canvas.drawRoundRect(this.mDrawableRect, radius, radius, this.mBitmapPaint);
                canvas.drawRoundRect(this.mBorderRect, radius, radius, this.mBorderPaint);
                redrawBitmapForSquareCorners(canvas);
                redrawBorderForSquareCorners(canvas);
                return;
            }
            canvas.drawRoundRect(this.mDrawableRect, radius, radius, this.mBitmapPaint);
            redrawBitmapForSquareCorners(canvas);
        } else {
            canvas.drawRect(this.mDrawableRect, this.mBitmapPaint);
            if (this.mBorderWidth > 0.0f) {
                canvas.drawRect(this.mBorderRect, this.mBorderPaint);
            }
        }
    }

    private void redrawBitmapForSquareCorners(Canvas canvas) {
        if (!all(this.mCornersRounded) && this.mCornerRadius != 0.0f) {
            float left = this.mDrawableRect.left;
            float top = this.mDrawableRect.top;
            float right = left + this.mDrawableRect.width();
            float bottom = top + this.mDrawableRect.height();
            float radius = this.mCornerRadius;
            if (!this.mCornersRounded[0]) {
                this.mSquareCornersRect.set(left, top, left + radius, top + radius);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
            if (!this.mCornersRounded[1]) {
                this.mSquareCornersRect.set(right - radius, top, right, radius);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
            if (!this.mCornersRounded[2]) {
                this.mSquareCornersRect.set(right - radius, bottom - radius, right, bottom);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
            if (!this.mCornersRounded[3]) {
                this.mSquareCornersRect.set(left, bottom - radius, left + radius, bottom);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
        }
    }

    private void redrawBorderForSquareCorners(Canvas canvas) {
        if (!all(this.mCornersRounded) && this.mCornerRadius != 0.0f) {
            float left = this.mDrawableRect.left;
            float top = this.mDrawableRect.top;
            float right = left + this.mDrawableRect.width();
            float bottom = top + this.mDrawableRect.height();
            float radius = this.mCornerRadius;
            float offset = this.mBorderWidth / 2.0f;
            if (!this.mCornersRounded[0]) {
                canvas.drawLine(left - offset, top, left + radius, top, this.mBorderPaint);
                canvas.drawLine(left, top - offset, left, top + radius, this.mBorderPaint);
            }
            if (!this.mCornersRounded[1]) {
                canvas.drawLine((right - radius) - offset, top, right, top, this.mBorderPaint);
                canvas.drawLine(right, top - offset, right, top + radius, this.mBorderPaint);
            }
            if (!this.mCornersRounded[2]) {
                canvas.drawLine((right - radius) - offset, bottom, right + offset, bottom, this.mBorderPaint);
                canvas.drawLine(right, bottom - radius, right, bottom, this.mBorderPaint);
            }
            if (!this.mCornersRounded[3]) {
                canvas.drawLine(left - offset, bottom, left + radius, bottom, this.mBorderPaint);
                canvas.drawLine(left, bottom - radius, left, bottom, this.mBorderPaint);
            }
        }
    }

    public int getOpacity() {
        return -3;
    }

    public int getAlpha() {
        return this.mBitmapPaint.getAlpha();
    }

    public void setAlpha(int alpha) {
        this.mBitmapPaint.setAlpha(alpha);
        invalidateSelf();
    }

    public ColorFilter getColorFilter() {
        return this.mBitmapPaint.getColorFilter();
    }

    public void setColorFilter(ColorFilter cf) {
        this.mBitmapPaint.setColorFilter(cf);
        invalidateSelf();
    }

    public void setDither(boolean dither) {
        this.mBitmapPaint.setDither(dither);
        invalidateSelf();
    }

    public void setFilterBitmap(boolean filter) {
        this.mBitmapPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public float getCornerRadius(@Corner int corner) {
        return this.mCornersRounded[corner] ? this.mCornerRadius : 0.0f;
    }

    public RoundedPagerDrawable setCornerRadius(float radius) {
        setCornerRadius(radius, radius, radius, radius);
        return this;
    }

    public RoundedPagerDrawable setCornerRadius(@Corner int corner, float radius) {
        if (radius == 0.0f || this.mCornerRadius == 0.0f || this.mCornerRadius == radius) {
            if (radius == 0.0f) {
                if (only(corner, this.mCornersRounded)) {
                    this.mCornerRadius = 0.0f;
                }
                this.mCornersRounded[corner] = false;
            } else {
                if (this.mCornerRadius == 0.0f) {
                    this.mCornerRadius = radius;
                }
                this.mCornersRounded[corner] = true;
            }
            return this;
        }
        throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
    }

    public RoundedPagerDrawable setCornerRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        boolean z = true;
        Set<Float> radiusSet = new HashSet(4);
        radiusSet.add(Float.valueOf(topLeft));
        radiusSet.add(Float.valueOf(topRight));
        radiusSet.add(Float.valueOf(bottomRight));
        radiusSet.add(Float.valueOf(bottomLeft));
        radiusSet.remove(Float.valueOf(0.0f));
        if (radiusSet.size() > 1) {
            throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
        }
        boolean z2;
        if (radiusSet.isEmpty()) {
            this.mCornerRadius = 0.0f;
        } else {
            float radius = ((Float) radiusSet.iterator().next()).floatValue();
            if (Float.isInfinite(radius) || Float.isNaN(radius) || radius < 0.0f) {
                throw new IllegalArgumentException("Invalid radius value: " + radius);
            }
            this.mCornerRadius = radius;
        }
        boolean[] zArr = this.mCornersRounded;
        if (topLeft > 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        zArr[0] = z2;
        zArr = this.mCornersRounded;
        if (topRight > 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        zArr[1] = z2;
        zArr = this.mCornersRounded;
        if (bottomRight > 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        zArr[2] = z2;
        boolean[] zArr2 = this.mCornersRounded;
        if (bottomLeft <= 0.0f) {
            z = false;
        }
        zArr2[3] = z;
        return this;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public RoundedPagerDrawable setBorderWidth(float width) {
        this.mBorderWidth = width;
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
        return this;
    }

    public int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public RoundedPagerDrawable setBorderColor(int color) {
        return setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    public RoundedPagerDrawable setBorderColor(ColorStateList colors) {
        if (colors == null) {
            colors = ColorStateList.valueOf(0);
        }
        this.mBorderColor = colors;
        this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
        return this;
    }

    public boolean isOval() {
        return this.mOval;
    }

    public RoundedPagerDrawable setOval(boolean oval) {
        this.mOval = oval;
        return this;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public RoundedPagerDrawable setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ScaleType.FIT_CENTER;
        }
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            updateShaderMatrix();
        }
        return this;
    }

    public TileMode getTileModeX() {
        return this.mTileModeX;
    }

    public RoundedPagerDrawable setTileModeX(TileMode tileModeX) {
        if (this.mTileModeX != tileModeX) {
            this.mTileModeX = tileModeX;
            this.mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    public TileMode getTileModeY() {
        return this.mTileModeY;
    }

    public RoundedPagerDrawable setTileModeY(TileMode tileModeY) {
        if (this.mTileModeY != tileModeY) {
            this.mTileModeY = tileModeY;
            this.mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    private static boolean only(int index, boolean[] booleans) {
        int len = booleans.length;
        for (int i = 0; i < len; i++) {
            boolean z;
            boolean z2 = booleans[i];
            if (i == index) {
                z = true;
            } else {
                z = false;
            }
            if (z2 != z) {
                return false;
            }
        }
        return true;
    }

    private static boolean any(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    private static boolean all(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return false;
            }
        }
        return true;
    }

    public Bitmap toBitmap() {
        return drawableToBitmap(this);
    }
}
