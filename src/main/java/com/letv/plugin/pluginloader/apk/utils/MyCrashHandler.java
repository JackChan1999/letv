package com.letv.plugin.pluginloader.apk.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Process;
import android.support.v4.os.EnvironmentCompat;
import com.letv.plugin.pluginloader.apk.compat.SystemPropertiesCompat;
import java.io.File;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCrashHandler implements UncaughtExceptionHandler {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String TAG = "MyCrashHandler";
    private static final MyCrashHandler sMyCrashHandler = new MyCrashHandler();
    private Context mContext;
    private UncaughtExceptionHandler mOldHandler;

    public static MyCrashHandler getInstance() {
        return sMyCrashHandler;
    }

    public void register(Context context) {
        if (context != null) {
            this.mOldHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (this.mOldHandler != this) {
                Thread.setDefaultUncaughtExceptionHandler(this);
            }
            this.mContext = context;
        }
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Throwable e;
        Throwable th;
        Log.e(TAG, "uncaughtException", ex, new Object[0]);
        PrintWriter writer = null;
        try {
            Date date = new Date();
            String dateStr = SIMPLE_DATE_FORMAT1.format(date);
            File file = new File(Environment.getExternalStorageDirectory(), String.format("PluginLog/CrashLog/CrashLog_%s_%s.log", new Object[]{dateStr, Integer.valueOf(Process.myPid())}));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            PrintWriter writer2 = new PrintWriter(file);
            try {
                writer2.println("Date:" + SIMPLE_DATE_FORMAT.format(date));
                writer2.println("----------------------------------------System Infomation-----------------------------------");
                String packageName = this.mContext.getPackageName();
                writer2.println("AppPkgName:" + packageName);
                try {
                    PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(packageName, 0);
                    writer2.println("VersionCode:" + packageInfo.versionCode);
                    writer2.println("VersionName:" + packageInfo.versionName);
                    writer2.println("Debug:" + ((packageInfo.applicationInfo.flags & 2) != 0));
                } catch (Exception e2) {
                    writer2.println("VersionCode:-1");
                    writer2.println("VersionName:null");
                    writer2.println("Debug:Unkown");
                }
                writer2.println("PName:" + getProcessName());
                try {
                    writer2.println("imei:" + getIMEI(this.mContext));
                } catch (Exception e3) {
                }
                writer2.println("Board:" + SystemPropertiesCompat.get("ro.product.board", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.bootloader:" + SystemPropertiesCompat.get("ro.bootloader", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.brand:" + SystemPropertiesCompat.get("ro.product.brand", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.cpu.abi:" + SystemPropertiesCompat.get("ro.product.cpu.abi", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.cpu.abi2:" + SystemPropertiesCompat.get("ro.product.cpu.abi2", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.device:" + SystemPropertiesCompat.get("ro.product.device", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.display.id:" + SystemPropertiesCompat.get("ro.build.display.id", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.fingerprint:" + SystemPropertiesCompat.get("ro.build.fingerprint", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.hardware:" + SystemPropertiesCompat.get("ro.hardware", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.host:" + SystemPropertiesCompat.get("ro.build.host", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.id:" + SystemPropertiesCompat.get("ro.build.id", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.manufacturer:" + SystemPropertiesCompat.get("ro.product.manufacturer", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.model:" + SystemPropertiesCompat.get("ro.product.model", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.product.name:" + SystemPropertiesCompat.get("ro.product.name", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("gsm.version.baseband:" + SystemPropertiesCompat.get("gsm.version.baseband", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.tags:" + SystemPropertiesCompat.get("ro.build.tags", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.type:" + SystemPropertiesCompat.get("ro.build.type", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.user:" + SystemPropertiesCompat.get("ro.build.user", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.version.codename:" + SystemPropertiesCompat.get("ro.build.version.codename", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.version.incremental:" + SystemPropertiesCompat.get("ro.build.version.incremental", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.version.release:" + SystemPropertiesCompat.get("ro.build.version.release", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("ro.build.version.sdk:" + SystemPropertiesCompat.get("ro.build.version.sdk", EnvironmentCompat.MEDIA_UNKNOWN));
                writer2.println("\n\n\n----------------------------------Exception---------------------------------------\n\n");
                writer2.println("----------------------------Exception message:" + ex.getLocalizedMessage() + "\n");
                writer2.println("----------------------------Exception StackTrace:");
                ex.printStackTrace(writer2);
                if (writer2 != null) {
                    try {
                        writer2.flush();
                        writer2.close();
                    } catch (Exception e4) {
                    }
                }
                if (this.mOldHandler != null) {
                    this.mOldHandler.uncaughtException(thread, ex);
                    writer = writer2;
                    return;
                }
                writer = writer2;
            } catch (Throwable th2) {
                th = th2;
                writer = writer2;
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
                if (this.mOldHandler != null) {
                    this.mOldHandler.uncaughtException(thread, ex);
                }
                throw th;
            }
        } catch (Throwable th3) {
            e = th3;
            Log.e(TAG, "记录uncaughtException", e, new Object[0]);
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            if (this.mOldHandler != null) {
                this.mOldHandler.uncaughtException(thread, ex);
            }
        }
    }

    private String getIMEI(Context mContext) {
        return "test";
    }

    public String getProcessName() {
        for (RunningAppProcessInfo info : ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses()) {
            if (info.pid == Process.myPid()) {
                return info.processName;
            }
        }
        return null;
    }
}
