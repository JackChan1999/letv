package com.letv.mobile.lebox.ui.ripple;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.letv.mobile.lebox.R;

public class RoundImageView extends ImageView {
    private final int defaultColor = -1;
    private int defaultHeight = 0;
    private int defaultWidth = 0;
    private int mBorderInsideColor = 0;
    private int mBorderOutsideColor = 0;
    private int mBorderThickness = 0;
    private final Context mContext;

    public RoundImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setCustomAttributes(attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        setCustomAttributes(attrs);
    }

    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, R.styleable.roundedimageview);
        this.mBorderThickness = a.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
        int i = R.styleable.roundedimageview_border_outside_color;
        getClass();
        this.mBorderOutsideColor = a.getColor(i, -1);
        i = R.styleable.roundedimageview_border_inside_color;
        getClass();
        this.mBorderInsideColor = a.getColor(i, -1);
    }

    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && getWidth() != 0 && getHeight() != 0) {
            measure(0, 0);
            if (drawable.getClass() != NinePatchDrawable.class) {
                int radius;
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap().copy(Config.ARGB_8888, true);
                if (this.defaultWidth == 0) {
                    this.defaultWidth = getWidth();
                }
                if (this.defaultHeight == 0) {
                    this.defaultHeight = getHeight();
                }
                int i = this.mBorderInsideColor;
                getClass();
                if (i != -1) {
                    i = this.mBorderOutsideColor;
                    getClass();
                    if (i != -1) {
                        radius = ((this.defaultWidth < this.defaultHeight ? this.defaultWidth : this.defaultHeight) / 2) - (this.mBorderThickness * 2);
                        drawCircleBorder(canvas, (this.mBorderThickness / 2) + radius, this.mBorderInsideColor);
                        drawCircleBorder(canvas, (this.mBorderThickness + radius) + (this.mBorderThickness / 2), this.mBorderOutsideColor);
                        canvas.drawBitmap(getCroppedRoundBitmap(bitmap, radius), (float) ((this.defaultWidth / 2) - radius), (float) ((this.defaultHeight / 2) - radius), null);
                    }
                }
                i = this.mBorderInsideColor;
                getClass();
                if (i != -1) {
                    i = this.mBorderOutsideColor;
                    getClass();
                    if (i == -1) {
                        radius = ((this.defaultWidth < this.defaultHeight ? this.defaultWidth : this.defaultHeight) / 2) - this.mBorderThickness;
                        drawCircleBorder(canvas, (this.mBorderThickness / 2) + radius, this.mBorderInsideColor);
                        canvas.drawBitmap(getCroppedRoundBitmap(bitmap, radius), (float) ((this.defaultWidth / 2) - radius), (float) ((this.defaultHeight / 2) - radius), null);
                    }
                }
                i = this.mBorderInsideColor;
                getClass();
                if (i == -1) {
                    i = this.mBorderOutsideColor;
                    getClass();
                    if (i != -1) {
                        radius = ((this.defaultWidth < this.defaultHeight ? this.defaultWidth : this.defaultHeight) / 2) - this.mBorderThickness;
                        drawCircleBorder(canvas, (this.mBorderThickness / 2) + radius, this.mBorderOutsideColor);
                        canvas.drawBitmap(getCroppedRoundBitmap(bitmap, radius), (float) ((this.defaultWidth / 2) - radius), (float) ((this.defaultHeight / 2) - radius), null);
                    }
                }
                radius = (this.defaultWidth < this.defaultHeight ? this.defaultWidth : this.defaultHeight) / 2;
                canvas.drawBitmap(getCroppedRoundBitmap(bitmap, radius), (float) ((this.defaultWidth / 2) - radius), (float) ((this.defaultHeight / 2) - radius), null);
            }
        }
    }

    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap squareBitmap;
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        Bitmap bitmap;
        int i;
        if (bmpHeight > bmpWidth) {
            bitmap = bmp;
            i = (bmpHeight - bmpWidth) / 2;
            squareBitmap = Bitmap.createBitmap(bitmap, 0, i, bmpWidth, bmpWidth);
        } else if (bmpHeight < bmpWidth) {
            int x = (bmpWidth - bmpHeight) / 2;
            bitmap = bmp;
            i = 0;
            squareBitmap = Bitmap.createBitmap(bitmap, x, i, bmpHeight, bmpHeight);
        } else {
            squareBitmap = bmp;
        }
        if (squareBitmap.getWidth() == diameter && squareBitmap.getHeight() == diameter) {
            scaledSrcBmp = squareBitmap;
        } else {
            scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        }
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle((float) (scaledSrcBmp.getWidth() / 2), (float) (scaledSrcBmp.getHeight() / 2), (float) (scaledSrcBmp.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        return output;
    }

    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth((float) this.mBorderThickness);
        canvas.drawCircle((float) (this.defaultWidth / 2), (float) (this.defaultHeight / 2), (float) radius, paint);
    }
}
