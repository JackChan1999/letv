package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.core.messagebus.config.LeMessageIds;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import org.apache.http.util.EncodingUtils;

public class ResourceManager {
    private static final String DRAWABLE = "drawable";
    private static final String DRAWABLE_HDPI = "drawable-hdpi";
    private static final String DRAWABLE_LDPI = "drawable-ldpi";
    private static final String DRAWABLE_MDPI = "drawable-mdpi";
    private static final String DRAWABLE_XHDPI = "drawable-xhdpi";
    private static final String DRAWABLE_XXHDPI = "drawable-xxhdpi";
    private static final String[] PRE_INSTALL_DRAWBLE_PATHS = new String[]{DRAWABLE_XXHDPI, DRAWABLE_XHDPI, DRAWABLE_HDPI, DRAWABLE_MDPI, DRAWABLE_LDPI, DRAWABLE};
    private static final String TAG = ResourceManager.class.getName();

    public static String getString(Context context, String en, String cn, String tw) {
        Locale locale = getLanguage();
        if (Locale.SIMPLIFIED_CHINESE.equals(locale)) {
            return cn;
        }
        if (Locale.TRADITIONAL_CHINESE.equals(locale)) {
            return tw;
        }
        return en;
    }

    public static Drawable getDrawable(Context context, String fileName) {
        return getDrawableFromAssert(context, getAppropriatePathOfDrawable(context, fileName), false);
    }

    public static Drawable getNinePatchDrawable(Context context, String fileName) {
        return getDrawableFromAssert(context, getAppropriatePathOfDrawable(context, fileName), true);
    }

    public static Locale getLanguage() {
        Locale locale = Locale.getDefault();
        return (Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TRADITIONAL_CHINESE.equals(locale)) ? locale : Locale.ENGLISH;
    }

    private static String getAppropriatePathOfDrawable(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            LogUtil.e(TAG, "id is NOT correct!");
            return null;
        }
        int properDpi;
        String currentDpi = getCurrentDpiFolder(context);
        LogUtil.d(TAG, "find Appropriate path...");
        int existIndexLeftDpi = -1;
        int currentDpiIndex = -1;
        int existIndexRightDpi = -1;
        for (int index = 0; index < PRE_INSTALL_DRAWBLE_PATHS.length; index++) {
            if (PRE_INSTALL_DRAWBLE_PATHS[index].equals(currentDpi)) {
                currentDpiIndex = index;
            }
            String resPath = new StringBuilder(String.valueOf(PRE_INSTALL_DRAWBLE_PATHS[index])).append("/").append(fileName).toString();
            if (isFileExisted(context, resPath)) {
                if (currentDpiIndex != index) {
                    if (currentDpiIndex >= 0) {
                        existIndexRightDpi = index;
                        break;
                    }
                    existIndexLeftDpi = index;
                } else {
                    return resPath;
                }
            }
        }
        if (existIndexLeftDpi <= 0 || existIndexRightDpi <= 0) {
            if (existIndexLeftDpi > 0 && existIndexRightDpi < 0) {
                properDpi = existIndexLeftDpi;
            } else if (existIndexLeftDpi >= 0 || existIndexRightDpi <= 0) {
                properDpi = -1;
                LogUtil.e(TAG, "Not find the appropriate path for drawable");
            } else {
                properDpi = existIndexRightDpi;
            }
        } else if (Math.abs(currentDpiIndex - existIndexRightDpi) <= Math.abs(currentDpiIndex - existIndexLeftDpi)) {
            properDpi = existIndexRightDpi;
        } else {
            properDpi = existIndexLeftDpi;
        }
        if (properDpi >= 0) {
            return new StringBuilder(String.valueOf(PRE_INSTALL_DRAWBLE_PATHS[properDpi])).append("/").append(fileName).toString();
        }
        LogUtil.e(TAG, "Not find the appropriate path for drawable");
        return null;
    }

    private static Drawable getDrawableFromAssert(Context context, String relativePath, boolean isNinePatch) {
        InputStream is = null;
        try {
            Drawable rtDrawable;
            is = context.getAssets().open(relativePath);
            if (is != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                if (isNinePatch) {
                    rtDrawable = new NinePatchDrawable(new Resources(context.getAssets(), metrics, context.getResources().getConfiguration()), bitmap, bitmap.getNinePatchChunk(), new Rect(0, 0, 0, 0), null);
                } else {
                    bitmap.setDensity(metrics.densityDpi);
                    rtDrawable = new BitmapDrawable(context.getResources(), bitmap);
                }
            } else {
                rtDrawable = null;
            }
            if (is == null) {
                return rtDrawable;
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rtDrawable;
        } catch (IOException e2) {
            e2.printStackTrace();
            if (is == null) {
                return null;
            }
            try {
                is.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
            return null;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        }
    }

    private static boolean isFileExisted(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            return false;
        }
        InputStream is = null;
        try {
            is = context.getAssets().open(filePath);
            LogUtil.d(TAG, "file [" + filePath + "] existed");
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } catch (IOException e2) {
            LogUtil.d(TAG, "file [" + filePath + "] NOT existed");
            if (is == null) {
                return false;
            }
            try {
                is.close();
                return false;
            } catch (IOException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
    }

    private static String getCurrentDpiFolder(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        if (density <= LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT) {
            return DRAWABLE_LDPI;
        }
        if (density > LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT && density <= 160) {
            return DRAWABLE_MDPI;
        }
        if (density > 160 && density <= 240) {
            return DRAWABLE_HDPI;
        }
        if (density <= 240 || density > 320) {
            return DRAWABLE_XXHDPI;
        }
        return DRAWABLE_XHDPI;
    }

    private static View extractView(Context context, String fileName, ViewGroup root) throws Exception {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(context.getAssets().openXmlResourceParser(fileName), root);
    }

    private static Drawable extractDrawable(Context context, String fileName) throws Exception {
        InputStream inputStream = context.getAssets().open(fileName);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        TypedValue value = new TypedValue();
        value.density = dm.densityDpi;
        Drawable drawable = Drawable.createFromResourceStream(context.getResources(), value, inputStream, fileName);
        inputStream.close();
        return drawable;
    }

    public static int dp2px(Context context, int dp) {
        return (int) (((double) (((float) dp) * context.getResources().getDisplayMetrics().density)) + 0.5d);
    }

    public static ColorStateList createColorStateList(int normal, int pressed) {
        int[] colors = new int[]{pressed, pressed, pressed, normal};
        int[][] states = new int[4][];
        states[0] = new int[]{16842919};
        states[1] = new int[]{16842913};
        states[2] = new int[]{16842908};
        states[3] = StateSet.WILD_CARD;
        return new ColorStateList(states, colors);
    }

    public static StateListDrawable createStateListDrawable(Context context, String normalPicName, String pressedPicName) {
        Drawable normalDrawable;
        Drawable pressedDrawable;
        if (normalPicName.indexOf(".9") > -1) {
            normalDrawable = getNinePatchDrawable(context, normalPicName);
        } else {
            normalDrawable = getDrawable(context, normalPicName);
        }
        if (pressedPicName.indexOf(".9") > -1) {
            pressedDrawable = getNinePatchDrawable(context, pressedPicName);
        } else {
            pressedDrawable = getDrawable(context, pressedPicName);
        }
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{16842919}, pressedDrawable);
        drawable.addState(new int[]{16842913}, pressedDrawable);
        drawable.addState(new int[]{16842908}, pressedDrawable);
        drawable.addState(StateSet.WILD_CARD, normalDrawable);
        return drawable;
    }

    public static StateListDrawable createStateListDrawable(Context context, String normalPicName, String pressedPicName, String enabledPicName) {
        Drawable normalDrawable;
        Drawable enableDrawable;
        Drawable pressedDrawable;
        if (normalPicName.indexOf(".9") > -1) {
            normalDrawable = getNinePatchDrawable(context, normalPicName);
        } else {
            normalDrawable = getDrawable(context, normalPicName);
        }
        if (enabledPicName.indexOf(".9") > -1) {
            enableDrawable = getNinePatchDrawable(context, enabledPicName);
        } else {
            enableDrawable = getDrawable(context, enabledPicName);
        }
        if (pressedPicName.indexOf(".9") > -1) {
            pressedDrawable = getNinePatchDrawable(context, pressedPicName);
        } else {
            pressedDrawable = getDrawable(context, pressedPicName);
        }
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{16842919}, pressedDrawable);
        drawable.addState(new int[]{16842913}, pressedDrawable);
        drawable.addState(new int[]{16842908}, pressedDrawable);
        drawable.addState(new int[]{16842766}, enableDrawable);
        drawable.addState(StateSet.WILD_CARD, normalDrawable);
        return drawable;
    }

    public static String readCountryFromAsset(Context context, String assetName) {
        String content = "";
        try {
            InputStream is = context.getAssets().open(assetName);
            if (is == null) {
                return content;
            }
            DataInputStream dIs = new DataInputStream(is);
            byte[] buffer = new byte[dIs.available()];
            dIs.read(buffer);
            content = EncodingUtils.getString(buffer, "UTF-8");
            is.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return content;
        }
    }
}
