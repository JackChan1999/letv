package com.letv.core.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import com.letv.core.BaseApplication;

public class ScreenInfoUtils {
    private static float mDensity;
    private static float mDensityDpi;
    private static int mHeight = 0;
    private static float mScaledDensity;
    private static int mWidth = 0;
    private static float mXdpi;
    private static float mYdpi;

    private static void getDisplayMetricsInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mXdpi = dm.xdpi;
        mYdpi = dm.ydpi;
        mDensity = dm.density;
        mDensityDpi = (float) dm.densityDpi;
        mScaledDensity = dm.scaledDensity;
    }

    public static int getWidth(Context context) {
        if (mWidth == 0) {
            getDisplayMetricsInfo(context);
        }
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public float getXdpi() {
        return mXdpi;
    }

    public float getYdpi() {
        return mYdpi;
    }

    public float getDensity() {
        return mDensity;
    }

    public float getDensityDpi() {
        return mDensityDpi;
    }

    public float getScaledDensity() {
        return mScaledDensity;
    }

    public static boolean reflectScreenState() {
        boolean isScreenOn = true;
        boolean flag = false;
        try {
            isScreenOn = ((PowerManager) BaseApplication.getInstance().getSystemService("power")).isScreenOn();
            flag = ((KeyguardManager) BaseApplication.getInstance().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isScreenOn && !flag;
    }
}
