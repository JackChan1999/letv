package com.letv.android.client.episode.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.letv.android.client.view.LetvImageView;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class CommentHeadImageViewPlayerLibs extends LetvImageView {
    private Bitmap bitmapSrc;
    private Paint paint;

    public CommentHeadImageViewPlayerLibs(Context context, AttributeSet attrs) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.paint = new Paint();
        this.paint.setFilterBitmap(false);
        this.paint.setAntiAlias(true);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            this.bitmapSrc = ((BitmapDrawable) drawable).getBitmap();
        }
        if (this.bitmapSrc != null) {
            this.bitmapSrc = loadBitmap(this.bitmapSrc, getWidth());
            canvas.drawBitmap(getCircleBitmap(this.bitmapSrc), 0.0f, 0.0f, this.paint);
            return;
        }
        super.onDraw(canvas);
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-12434878);
        int radius = bitmap.getWidth() >> 1;
        canvas.drawCircle((float) radius, (float) radius, (float) radius, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private Bitmap loadBitmap(Bitmap src, int w) {
        int width = src.getWidth();
        int height = src.getHeight();
        float scaleWidth = ((float) w) / ((float) width);
        float scaleHeight = ((float) w) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
    }
}
