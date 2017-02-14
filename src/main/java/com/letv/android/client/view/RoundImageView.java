package com.letv.android.client.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.letv.android.client.R;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.ByteArrayOutputStream;

public class RoundImageView extends LetvImageView {
    private Bitmap bitmapFg;
    private Bitmap bitmapSrc;
    private int diameter;
    private Paint paint;
    private int screenW;
    private int viewH;
    private int viewW;
    private int x;
    private int y;

    public RoundImageView(Context context, AttributeSet attrs) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            this.diameter = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView).getDimensionPixelSize(0, 0);
        }
        this.paint = new Paint();
        this.paint.setFilterBitmap(false);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(2.0f);
        this.paint.setStyle(Style.STROKE);
        this.screenW = UIsUtils.getScreenWidth();
        this.diameter = UIsUtils.zoomWidth(this.diameter);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        this.viewW = getWidth();
        this.viewH = getHeight();
        int minValue = this.viewW < this.viewH ? this.viewW : this.viewH;
        if (minValue > this.diameter) {
            this.x = (minValue - this.diameter) >> 1;
            this.y = (minValue - this.diameter) >> 1;
        } else {
            this.diameter = minValue;
        }
        Drawable drawable = getDrawable();
        if (drawable != null) {
            this.bitmapSrc = ((BitmapDrawable) drawable).getBitmap();
        }
        if (this.diameter == 0 || this.bitmapSrc == null) {
            super.onDraw(canvas);
            return;
        }
        this.bitmapFg = loadBitmap(this.bitmapSrc, this.diameter);
        canvas.drawBitmap(getCircleBitmap(this.bitmapFg), (float) this.x, (float) this.y, this.paint);
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        LogInfo.log("RoundImageView", "bitmap_width:" + bitmap.getWidth() + "; bitmap_height:" + bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(2131493356));
        int radius = bitmap.getWidth() >> 1;
        canvas.drawCircle((float) radius, (float) radius, (float) radius, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private Bitmap loadBitmap(Bitmap src, int w) {
        int minValue;
        int width = src.getWidth();
        int height = src.getHeight();
        int offSetValue = 0;
        if (width < height) {
            minValue = width;
        } else {
            minValue = height;
        }
        if (minValue >= 200) {
            offSetValue = 20;
        }
        LogInfo.log("RoundImageView", "src_width:" + width + "; src_height:" + height);
        float scaleWidth = ((float) w) / ((float) (width - offSetValue));
        float scaleHeight = ((float) w) / ((float) (height - offSetValue));
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        this.bitmapSrc = Bitmap.createBitmap(src, offSetValue / 2, offSetValue / 2, width - offSetValue, height - offSetValue, matrix, true);
        return this.bitmapSrc;
    }

    public Bitmap decodeAbtoBm(byte[] b, int actualSize) {
        System.gc();
        Runtime.getRuntime().gc();
        Options oo = new Options();
        oo.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(b, 0, b.length, oo);
        int scale = 1;
        while ((oo.outWidth / scale) / 2 >= actualSize && (oo.outHeight / scale) / 2 >= actualSize) {
            scale *= 2;
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        o2.inPurgeable = true;
        o2.inInputShareable = true;
        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length, o2);
        System.gc();
        Runtime.getRuntime().gc();
        return bm;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
