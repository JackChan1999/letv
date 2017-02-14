package com.letv.pp.utils;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DeviceUtils {
    private static final String FILE_NAME_MEMINFO = "/proc/meminfo";
    private static final String FORMAT_NUMBER = "%s*%s";
    private static final String FORMAT_OS_VERSION = "Android %s";
    private static final String PROPERTY_RELEASE_VERSION = "ro.letv.release.version";
    private static final String TAG = "DeviceUtils";
    private static long sTotalMemory;

    static {
        Exception e;
        Throwable th;
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_NAME_MEMINFO)), 1000);
            try {
                char[] memoryInfo = reader2.readLine().trim().toCharArray();
                StringBuffer buffer = new StringBuffer();
                int i = 0;
                while (i < memoryInfo.length) {
                    if (memoryInfo[i] <= '9' && memoryInfo[i] >= '0') {
                        buffer.append(memoryInfo[i]);
                    }
                    i++;
                }
                sTotalMemory = Long.parseLong(buffer.toString());
                IOUtils.closeSilently(reader2);
                reader = reader2;
            } catch (Exception e2) {
                e = e2;
                reader = reader2;
            } catch (Throwable th2) {
                th = th2;
                reader = reader2;
            }
        } catch (Exception e3) {
            e = e3;
            try {
                LogTool.w(TAG, "staticModule. " + e.toString());
                sTotalMemory = 1;
                IOUtils.closeSilently(reader);
            } catch (Throwable th3) {
                th = th3;
                IOUtils.closeSilently(reader);
                throw th;
            }
        }
    }

    public static String getLetvRomVersion() {
        return SystemProperties.get(PROPERTY_RELEASE_VERSION, "");
    }

    public static String getOsVersion() {
        return String.format(FORMAT_OS_VERSION, new Object[]{VERSION.RELEASE});
    }

    public static long getMemorySize() {
        return sTotalMemory;
    }

    public static long getInternalStorageSize(Context context) {
        long j = 0;
        try {
            String[] paths = (String[]) StorageManager.class.getMethod("getVolumePaths", new Class[0]).invoke((StorageManager) context.getSystemService("storage"), new Object[0]);
            if (paths != null && paths.length >= 1) {
                StatFs statFs = new StatFs(paths[0]);
                j = ((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1024) / 1024;
            }
        } catch (Exception e) {
            LogTool.e(TAG, "getInternalStorageSize. " + e.toString());
        }
        return j;
    }

    public static long getExternalStorageSize(Context context) {
        long j = 0;
        try {
            String[] paths = (String[]) StorageManager.class.getMethod("getVolumePaths", new Class[0]).invoke((StorageManager) context.getSystemService("storage"), new Object[0]);
            if (paths != null && paths.length >= 2) {
                StatFs statFs = new StatFs(paths[1]);
                j = ((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1024) / 1024;
            }
        } catch (Exception e) {
            LogTool.e(TAG, "getExternalStorageSize. " + e.toString());
        }
        return j;
    }

    public static String getScreenResolution(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return "";
        }
        windowManager.getDefaultDisplay().getMetrics(new DisplayMetrics());
        return String.format(FORMAT_NUMBER, new Object[]{Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels)});
    }

    public static int getDensityDpi(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }

    public static String getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return "";
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        double width = (double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi);
        double height = (double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi);
        double num = (width * width) + (height * height);
        LogTool.i(TAG, "widthPixels(%s), heightPixels(%s), xdpi(%s), ydpi(%s)", Integer.valueOf(displayMetrics.widthPixels), Integer.valueOf(displayMetrics.heightPixels), Float.valueOf(displayMetrics.xdpi), Float.valueOf(displayMetrics.ydpi));
        LogTool.i(TAG, "getScreenSize. density(%s), scaledDensity(%s), densityDpi(%s), screenSize(%s)", Float.valueOf(displayMetrics.density), Float.valueOf(displayMetrics.scaledDensity), Integer.valueOf(displayMetrics.densityDpi), Double.valueOf(((double) ((int) ((Math.sqrt(num) + 0.05d) * 10.0d))) / 10.0d));
        return String.format(FORMAT_NUMBER, new Object[]{Float.valueOf(((float) displayMetrics.widthPixels) / displayMetrics.xdpi), Float.valueOf(((float) displayMetrics.heightPixels) / displayMetrics.ydpi)});
    }

    public static String getIMEI(Context context) {
        try {
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (deviceId != null) {
                return deviceId;
            }
            return "";
        } catch (Exception e) {
            LogTool.e(TAG, "getIMEI. " + e.toString());
            return "";
        }
    }
}
