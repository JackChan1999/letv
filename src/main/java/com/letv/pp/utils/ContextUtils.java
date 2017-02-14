package com.letv.pp.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import java.util.List;

public class ContextUtils {
    private static final String TAG = "ContextUtils";

    public static boolean checkPermission(Context context, String permName) {
        if (context == null || TextUtils.isEmpty(permName)) {
            return false;
        }
        try {
            if (context.getPackageManager().checkPermission(permName, context.getPackageName()) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LogTool.e(TAG, "checkPermission. " + e.toString());
            return false;
        }
    }

    public static boolean isAppVersionChange(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            SPHelper spHelper = SPHelper.getInstance(context);
            String lastAppVersionName = spHelper.getString(SPHelper.KEY_APP_VERSION_NAME, null);
            if (lastAppVersionName == null) {
                return false;
            }
            int lastAppVersionCode = spHelper.getInt(SPHelper.KEY_APP_VERSION_CODE);
            String currentAppVersionName = packageInfo.versionName;
            int currentAppVersionCode = packageInfo.versionCode;
            boolean isChange = (lastAppVersionName.equals(currentAppVersionName) && lastAppVersionCode == currentAppVersionCode) ? false : true;
            if (!isChange) {
                return isChange;
            }
            spHelper.putString(SPHelper.KEY_APP_VERSION_NAME, currentAppVersionName);
            spHelper.putInt(SPHelper.KEY_APP_VERSION_CODE, currentAppVersionCode);
            return isChange;
        } catch (Throwable e) {
            LogTool.e(TAG, "", e);
            return false;
        }
    }

    public static String getAppVersionName(Context context) {
        String str = null;
        if (context != null) {
            try {
                str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (Throwable e) {
                LogTool.e(TAG, "", e);
            }
        }
        return str;
    }

    public static int getAppVersionCode(Context context) {
        int i = 0;
        if (context != null) {
            try {
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (Throwable e) {
                LogTool.e(TAG, "", e);
            }
        }
        return i;
    }

    public static String getProcessName(Context context, Class<? extends Service> clazz) {
        try {
            return context.getPackageManager().getServiceInfo(new ComponentName(context, clazz), 4).processName;
        } catch (Throwable e) {
            LogTool.e(TAG, "", e);
            return null;
        }
    }

    public static String getProcessName(Context context, int pid) {
        List<RunningAppProcessInfo> runningApps = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static void killProcess(Context context, String processName) {
        if (context == null || TextUtils.isEmpty(processName)) {
            throw new IllegalArgumentException("parameter is null.");
        }
        List<RunningAppProcessInfo> arrayList = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (processName.equals(((RunningAppProcessInfo) arrayList.get(i)).processName)) {
                    Process.killProcess(((RunningAppProcessInfo) arrayList.get(i)).pid);
                    return;
                }
            }
        }
    }

    public static void unReceiverComponent(Context context, Class<?> clazz) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, clazz), 2, 1);
        } catch (Throwable e) {
            LogTool.e(TAG, "", e);
        }
    }

    private ContextUtils() {
    }
}
