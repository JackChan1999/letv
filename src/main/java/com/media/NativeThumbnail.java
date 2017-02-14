package com.media;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;

public class NativeThumbnail {
    private int mNativeContext;
    private String mUrl;
    private int mVideoHeight = getVideoHeight();
    private int mVideoWidth = getVideoWidth();

    private native boolean getThumbnail(int i, Bitmap bitmap);

    private native void init(String str);

    private native void release();

    public native int getDuration();

    public native int getVideoHeight();

    public native int getVideoWidth();

    static {
        try {
            NativeLib nativeLib = new NativeLib();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NativeThumbnail(String url) {
        this.mUrl = url;
        init(this.mUrl);
    }

    public Bitmap getVideoThumbnail(int width, int height, int time) {
        if (this.mVideoWidth <= 0 || this.mVideoHeight <= 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(this.mVideoWidth, this.mVideoHeight, Config.RGB_565);
        getThumbnail(time, bitmap);
        if (width == 0 || height == 0) {
            return bitmap;
        }
        float sx = ((float) width) / ((float) this.mVideoWidth);
        float sy = ((float) height) / ((float) this.mVideoHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        return Bitmap.createBitmap(bitmap, 0, 0, this.mVideoWidth, this.mVideoHeight, matrix, true);
    }

    public void finalize() {
        release();
    }

    public String getResolution() {
        return getVideoWidth() + "*" + getVideoHeight();
    }
}
