package com.letv.android.uninstall;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

public class UninstalledObserver {
    private static final String ANDROID = "android";
    private static final String MODEL = "model";
    private static final String OS = "os";
    private static final String TAG = "UninstalledObserver";
    private static final String URL = "url";
    private static final String VERSION = "version";
    private static final String VERSION_NAME = "versionName";

    private static native int init(String str, String str2, String str3);

    public static int init(String url, Context context) {
        if (TextUtils.isEmpty(url) || context == null) {
            Log.e(TAG, "init fail: url or context is null");
            return -1;
        }
        SharedPreferences setting = context.getSharedPreferences(TAG, 0);
        int mObserverProcessPid = setting.getInt("ObserverProcessPid", -1);
        if (!setting.getBoolean("ObserverEnable", true)) {
            return -1;
        }
        if (mObserverProcessPid != -1) {
            return mObserverProcessPid;
        }
        String urladdParamer = getUrlAddParamer(url, context);
        try {
            if (VERSION.SDK_INT < 17) {
                mObserverProcessPid = init(urladdParamer, context.getFilesDir().getParent(), null);
            } else {
                mObserverProcessPid = init(urladdParamer, context.getFilesDir().getParent(), getUserSerial(context));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, urladdParamer + "\n" + mObserverProcessPid + "\n" + context.getFilesDir().getParent());
        Editor edit = setting.edit();
        edit.putInt("ObserverProcessPid", mObserverProcessPid);
        edit.commit();
        return mObserverProcessPid;
    }

    private static String getUrlAddParamer(String url, Context context) {
        SharedPreferences setting = context.getSharedPreferences(TAG, 0);
        if (!setting.getBoolean("isInit", false)) {
            try {
                Editor edit = setting.edit();
                edit.putString(VERSION_NAME, context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
                edit.putString(MODEL, Build.MODEL);
                edit.putString("version", VERSION.RELEASE);
                edit.putString("url", url);
                edit.putBoolean("isInit", true);
                edit.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        StringBuilder builder = new StringBuilder(url);
        builder.append("&model=" + setting.getString(MODEL, ""));
        builder.append("&os=android" + setting.getString("version", ""));
        builder.append("&version=" + setting.getString(VERSION_NAME, ""));
        return builder.toString();
    }

    public static String getUrl(Context context) {
        SharedPreferences setting = context.getSharedPreferences(TAG, 0);
        return setting.getString("url", setting.getString("url", ""));
    }

    private static String getUserSerial(Context context) {
        Object userManager = context.getSystemService("user");
        if (userManager == null) {
            Log.e(TAG, "userManager not exsit !!!");
            return null;
        }
        try {
            Object myUserHandle = Process.class.getMethod("myUserHandle", (Class[]) null).invoke(Process.class, (Object[]) null);
            return String.valueOf(((Long) userManager.getClass().getMethod("getSerialNumberForUser", new Class[]{myUserHandle.getClass()}).invoke(userManager, new Object[]{myUserHandle})).longValue());
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "", e);
            return null;
        } catch (IllegalArgumentException e2) {
            Log.e(TAG, "", e2);
            return null;
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "", e3);
            return null;
        } catch (InvocationTargetException e4) {
            Log.e(TAG, "", e4);
            return null;
        }
    }

    public static void stop(Context context) {
        SharedPreferences setting = context.getSharedPreferences(TAG, 0);
        int mObserverProcessPid = setting.getInt("ObserverProcessPid", -1);
        if (mObserverProcessPid != -1) {
            Process.killProcess(mObserverProcessPid);
            Editor edit = setting.edit();
            edit.putInt("ObserverProcessPid", -1);
            edit.commit();
        }
    }

    public static void resetPid(Context context) {
        Editor edit = context.getSharedPreferences(TAG, 0).edit();
        edit.putInt("ObserverProcessPid", -1);
        edit.commit();
    }

    public static void setEnable(boolean b, Context context) {
        Editor edit = context.getSharedPreferences(TAG, 0).edit();
        edit.putBoolean("ObserverEnable", b);
        edit.commit();
        if (!b) {
            stop(context);
        }
    }

    public static boolean isEnable(Context context) {
        return context.getSharedPreferences(TAG, 0).getBoolean("ObserverEnable", true);
    }

    static {
        Log.d(TAG, "load lib --> uninstalled_observer");
        System.loadLibrary("uninstalled_observer");
    }
}
