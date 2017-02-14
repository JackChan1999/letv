package com.letv.core.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import com.tencent.open.yyb.TitleBar;

public class BlurUtils {
    @SuppressLint({"NewApi"})
    public static void blur(Context context, Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate((float) (-view.getLeft()), (float) (-view.getTop()));
        canvas.drawBitmap(bkg, 0.0f, 0.0f, null);
        RenderScript rs = RenderScript.create(context);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(TitleBar.BACKBTN_LEFT_MARGIN);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        if (LetvUtils.getSDKVersion() >= 16) {
            view.setBackground(new BitmapDrawable(context.getResources(), overlay));
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), overlay));
        }
        rs.destroy();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void blurAdvanced(Context context, Bitmap bkg, View view) {
        if (bkg != null) {
            Bitmap overlay = Bitmap.createBitmap(bkg.getWidth(), bkg.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(overlay);
            Paint paint = new Paint();
            paint.setFlags(2);
            canvas.drawBitmap(bkg, 0.0f, 0.0f, paint);
            overlay = FastBlur.doBlur(overlay, (int) 12.0f, true);
            if (LetvUtils.getSDKVersion() >= 16) {
                view.setBackground(new BitmapDrawable(context.getResources(), overlay));
            } else {
                view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), overlay));
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static void blur_2(Context context, Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        Display d = ((Activity) context).getWindowManager().getDefaultDisplay();
        Bitmap overlay = Bitmap.createBitmap(d.getWidth(), d.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate((float) (-view.getLeft()), (float) (-view.getTop()));
        canvas.scale(1.0f, 1.0f);
        new Paint().setFlags(2);
        canvas.drawBitmap(bkg, 0.0f, 0.0f, null);
        overlay = FastBlur.doBlur(overlay, (int) 12.0f, true);
        if (LetvUtils.getSDKVersion() >= 16) {
            view.setBackground(new BitmapDrawable(context.getResources(), overlay));
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), overlay));
        }
    }

    @SuppressLint({"NewApi"})
    public static BitmapDrawable getBlur_2(Context context, Bitmap bkg) {
        Bitmap bitmap = Bitmap.createBitmap(bkg.getWidth(), bkg.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0.0f, 0.0f, paint);
        return new BitmapDrawable(context.getResources(), FastBlur.doBlur(bitmap, (int) 1.0f, true));
    }

    @SuppressLint({"NewApi"})
    public static void blur_3(Context context, Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        Display d = ((Activity) context).getWindowManager().getDefaultDisplay();
        Bitmap overlay = Bitmap.createBitmap((int) (((float) d.getWidth()) / 1.0f), (int) (((float) d.getHeight()) / 1.0f), Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1.0f / 1.0f, 1.0f / 1.0f);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(bkg, 0.0f, 0.0f, paint);
        overlay = FastBlur.doBlur(overlay, (int) 100.0f, true);
        if (LetvUtils.getSDKVersion() >= 16) {
            view.setBackground(new BitmapDrawable(context.getResources(), overlay));
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), overlay));
        }
    }

    public static void star_blur(Context context, Bitmap bm, ImageView iv) {
        long t1 = System.currentTimeMillis();
        Bitmap overlay = Bitmap.createBitmap((int) (((float) iv.getMeasuredWidth()) / 6.0f), (int) (((float) iv.getMeasuredHeight()) / 6.0f), Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(((float) (-iv.getLeft())) / 6.0f, ((float) (-iv.getTop())) / 6.0f);
        canvas.scale(1.0f / 6.0f, 1.0f / 6.0f);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(bm, 0.0f, 0.0f, paint);
        ImageView imageView = iv;
        imageView.setImageDrawable(new BitmapDrawable(context.getResources(), FastBlur.doBlur(overlay, (int) 5.0f, true)));
        LogInfo.log("clf", "模糊处理时间 t=" + (System.currentTimeMillis() - t1));
    }
}
