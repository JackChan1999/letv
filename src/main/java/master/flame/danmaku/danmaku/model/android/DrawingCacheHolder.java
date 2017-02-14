package master.flame.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.reflect.Array;
import tv.cjump.jni.NativeBitmapFactory;

public class DrawingCacheHolder {
    public Bitmap bitmap;
    public Bitmap[][] bitmapArray;
    public Canvas canvas;
    public boolean drawn;
    public Object extra;
    public int height;
    private int mDensity;
    public int width;

    public DrawingCacheHolder(int w, int h) {
        buildCache(w, h, 0, true);
    }

    public DrawingCacheHolder(int w, int h, int density) {
        this.mDensity = density;
        buildCache(w, h, density, true);
    }

    public void buildCache(int w, int h, int density, boolean checkSizeEquals) {
        boolean reuse = true;
        if (checkSizeEquals) {
            if (!(w == this.width && h == this.height)) {
                reuse = false;
            }
        } else if (w > this.width || h > this.height) {
            reuse = false;
        }
        if (!reuse || this.bitmap == null) {
            if (this.bitmap != null) {
                recycle();
            }
            this.width = w;
            this.height = h;
            this.bitmap = NativeBitmapFactory.createBitmap(w, h, Config.ARGB_8888);
            if (density > 0) {
                this.mDensity = density;
                this.bitmap.setDensity(density);
            }
            if (this.canvas == null) {
                this.canvas = new Canvas(this.bitmap);
                this.canvas.setDensity(density);
                return;
            }
            this.canvas.setBitmap(this.bitmap);
            return;
        }
        this.canvas.setBitmap(null);
        this.bitmap.eraseColor(0);
        this.canvas.setBitmap(this.bitmap);
        recycleBitmapArray();
    }

    public void erase() {
        eraseBitmap(this.bitmap);
        eraseBitmapArray();
    }

    public synchronized void recycle() {
        this.height = 0;
        this.width = 0;
        if (this.bitmap != null) {
            this.bitmap.recycle();
            this.bitmap = null;
        }
        recycleBitmapArray();
        this.extra = null;
    }

    @SuppressLint({"NewApi"})
    public void splitWith(int dispWidth, int dispHeight, int maximumCacheWidth, int maximumCacheHeight) {
        recycleBitmapArray();
        if (this.width > 0 && this.height > 0 && this.bitmap != null) {
            if (this.width > maximumCacheWidth || this.height > maximumCacheHeight) {
                maximumCacheWidth = Math.min(maximumCacheWidth, dispWidth);
                maximumCacheHeight = Math.min(maximumCacheHeight, dispHeight);
                int xCount = (this.width / maximumCacheWidth) + (this.width % maximumCacheWidth == 0 ? 0 : 1);
                int yCount = (this.height / maximumCacheHeight) + (this.height % maximumCacheHeight == 0 ? 0 : 1);
                int averageWidth = this.width / xCount;
                int averageHeight = this.height / yCount;
                Bitmap[][] bmpArray = (Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{yCount, xCount});
                if (this.canvas == null) {
                    this.canvas = new Canvas();
                    if (this.mDensity > 0) {
                        this.canvas.setDensity(this.mDensity);
                    }
                }
                Rect rectSrc = new Rect();
                Rect rectDst = new Rect();
                for (int yIndex = 0; yIndex < yCount; yIndex++) {
                    for (int xIndex = 0; xIndex < xCount; xIndex++) {
                        Bitmap[] bitmapArr = bmpArray[yIndex];
                        Bitmap bmp = NativeBitmapFactory.createBitmap(averageWidth, averageHeight, Config.ARGB_8888);
                        bitmapArr[xIndex] = bmp;
                        if (this.mDensity > 0) {
                            bmp.setDensity(this.mDensity);
                        }
                        this.canvas.setBitmap(bmp);
                        int left = xIndex * averageWidth;
                        int top = yIndex * averageHeight;
                        rectSrc.set(left, top, left + averageWidth, top + averageHeight);
                        rectDst.set(0, 0, bmp.getWidth(), bmp.getHeight());
                        this.canvas.drawBitmap(this.bitmap, rectSrc, rectDst, null);
                    }
                }
                this.canvas.setBitmap(this.bitmap);
                this.bitmapArray = bmpArray;
            }
        }
    }

    private void eraseBitmap(Bitmap bmp) {
        if (bmp != null) {
            bmp.eraseColor(0);
        }
    }

    private void eraseBitmapArray() {
        if (this.bitmapArray != null) {
            for (int i = 0; i < this.bitmapArray.length; i++) {
                for (Bitmap eraseBitmap : this.bitmapArray[i]) {
                    eraseBitmap(eraseBitmap);
                }
            }
        }
    }

    private void recycleBitmapArray() {
        if (this.bitmapArray != null) {
            for (int i = 0; i < this.bitmapArray.length; i++) {
                for (int j = 0; j < this.bitmapArray[i].length; j++) {
                    if (this.bitmapArray[i][j] != null) {
                        this.bitmapArray[i][j].recycle();
                        this.bitmapArray[i][j] = null;
                    }
                }
            }
            this.bitmapArray = (Bitmap[][]) null;
        }
    }

    public final synchronized boolean draw(Canvas canvas, float left, float top, Paint paint) {
        boolean z = true;
        synchronized (this) {
            if (this.bitmapArray != null) {
                for (int i = 0; i < this.bitmapArray.length; i++) {
                    for (int j = 0; j < this.bitmapArray[i].length; j++) {
                        Bitmap bmp = this.bitmapArray[i][j];
                        if (bmp != null) {
                            float dleft = left + ((float) (bmp.getWidth() * j));
                            if (dleft <= ((float) canvas.getWidth()) && ((float) bmp.getWidth()) + dleft >= 0.0f) {
                                float dtop = top + ((float) (bmp.getHeight() * i));
                                if (dtop <= ((float) canvas.getHeight()) && ((float) bmp.getHeight()) + dtop >= 0.0f) {
                                    canvas.drawBitmap(bmp, dleft, dtop, paint);
                                }
                            }
                        }
                    }
                }
            } else if (this.bitmap != null) {
                canvas.drawBitmap(this.bitmap, left, top, paint);
            } else {
                z = false;
            }
        }
        return z;
    }
}
