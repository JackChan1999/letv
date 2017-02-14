package tv.cjump.jni;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import com.tencent.open.yyb.TitleBar;
import java.lang.reflect.Field;

public class NativeBitmapFactory {
    static Field nativeIntField = null;
    static boolean nativeLibLoaded = false;
    static boolean notLoadAgain = false;

    private static native Bitmap createBitmap(int i, int i2, int i3, boolean z);

    private static native Bitmap createBitmap19(int i, int i2, int i3, boolean z);

    private static native boolean init();

    private static native boolean release();

    public static boolean isInNativeAlloc() {
        return VERSION.SDK_INT < 11 || (nativeLibLoaded && nativeIntField != null);
    }

    public static void loadLibs() {
        if (!notLoadAgain) {
            if (!DeviceUtils.isRealARMArch() && !DeviceUtils.isRealX86Arch()) {
                notLoadAgain = true;
                nativeLibLoaded = false;
            } else if (!nativeLibLoaded) {
                try {
                    if (VERSION.SDK_INT < 11 || VERSION.SDK_INT >= 23) {
                        notLoadAgain = true;
                        nativeLibLoaded = false;
                        if (nativeLibLoaded) {
                            if (init()) {
                                release();
                                notLoadAgain = true;
                                nativeLibLoaded = false;
                            } else {
                                initField();
                                if (!testLib()) {
                                    release();
                                    notLoadAgain = true;
                                    nativeLibLoaded = false;
                                }
                            }
                        }
                        Log.e("NativeBitmapFactory", "loaded" + nativeLibLoaded);
                    }
                    System.loadLibrary("ndkbitmap");
                    nativeLibLoaded = true;
                    if (nativeLibLoaded) {
                        if (init()) {
                            initField();
                            if (testLib()) {
                                release();
                                notLoadAgain = true;
                                nativeLibLoaded = false;
                            }
                        } else {
                            release();
                            notLoadAgain = true;
                            nativeLibLoaded = false;
                        }
                    }
                    Log.e("NativeBitmapFactory", "loaded" + nativeLibLoaded);
                } catch (Exception e) {
                    e.printStackTrace();
                    notLoadAgain = true;
                    nativeLibLoaded = false;
                } catch (Error e2) {
                    e2.printStackTrace();
                    notLoadAgain = true;
                    nativeLibLoaded = false;
                }
            }
        }
    }

    public static void releaseLibs() {
        if (nativeLibLoaded) {
            release();
        }
        nativeIntField = null;
        nativeLibLoaded = false;
    }

    static void initField() {
        try {
            nativeIntField = Config.class.getDeclaredField("nativeInt");
            nativeIntField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            nativeIntField = null;
            e.printStackTrace();
        }
    }

    @SuppressLint({"NewApi"})
    private static boolean testLib() {
        Exception e;
        Throwable th;
        boolean result = true;
        if (nativeIntField == null) {
            return false;
        }
        Bitmap bitmap = null;
        Canvas canvas;
        try {
            bitmap = createNativeBitmap(2, 2, Config.ARGB_8888, true);
            if (!(bitmap != null && bitmap.getWidth() == 2 && bitmap.getHeight() == 2)) {
                result = false;
            }
            if (result) {
                if (VERSION.SDK_INT >= 17 && !bitmap.isPremultiplied()) {
                    bitmap.setPremultiplied(true);
                }
                canvas = new Canvas(bitmap);
                try {
                    Paint paint = new Paint();
                    paint.setColor(SupportMenu.CATEGORY_MASK);
                    paint.setTextSize(TitleBar.BACKBTN_LEFT_MARGIN);
                    canvas.drawRect(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight(), paint);
                    canvas.drawText("TestLib", 0.0f, 0.0f, paint);
                    if (VERSION.SDK_INT >= 17) {
                        result = bitmap.isPremultiplied();
                    }
                } catch (Exception e2) {
                    e = e2;
                    try {
                        Log.e("NativeBitmapFactory", "exception:" + e.toString());
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        throw th;
                    }
                } catch (Error e3) {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    return false;
                }
            }
            canvas = null;
            if (bitmap == null) {
                return result;
            }
            bitmap.recycle();
            return result;
        } catch (Exception e4) {
            e = e4;
            canvas = null;
            Log.e("NativeBitmapFactory", "exception:" + e.toString());
            if (bitmap != null) {
                bitmap.recycle();
            }
            return false;
        } catch (Error e5) {
            canvas = null;
            if (bitmap != null) {
                bitmap.recycle();
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            canvas = null;
            if (bitmap != null) {
                bitmap.recycle();
            }
            throw th;
        }
    }

    public static int getNativeConfig(Config config) {
        int i = 0;
        try {
            if (nativeIntField != null) {
                i = nativeIntField.getInt(config);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return i;
    }

    public static Bitmap createBitmap(int width, int height, Config config) {
        return createBitmap(width, height, config, config.equals(Config.ARGB_8888));
    }

    public static void recycle(Bitmap bitmap) {
        bitmap.recycle();
    }

    public static Bitmap createBitmap(int width, int height, Config config, boolean hasAlpha) {
        if (!nativeLibLoaded || nativeIntField == null) {
            return Bitmap.createBitmap(width, height, config);
        }
        return createNativeBitmap(width, height, config, hasAlpha);
    }

    private static Bitmap createNativeBitmap(int width, int height, Config config, boolean hasAlpha) {
        int nativeConfig = getNativeConfig(config);
        if (VERSION.SDK_INT == 19) {
            return createBitmap19(width, height, nativeConfig, hasAlpha);
        }
        return Bitmap.createBitmap(width, height, config);
    }
}
