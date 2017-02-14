package com.letv.android.client.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import com.letv.android.client.LetvApplication;
import com.letv.cache.LetvCacheMannager;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.download.manager.DownloadManager;
import com.letv.http.LetvLogApiTool;
import com.letv.pp.utils.NetworkUtils;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimeZone;
import java.util.TreeSet;

public class CrashHandler implements UncaughtExceptionHandler {
    private static final String CRASH_REPORTER_EXTENSION = ".cr";
    public static final boolean DEBUG = true;
    private static CrashHandler INSTANCE = null;
    public static final String TAG = "CrashHandler";
    private static final String VERSION_CODE = "versionCode";
    private static final String VERSION_NAME = "versionName";
    private String CRASH_PATH = null;
    private String crashFileName = null;
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private Properties mDeviceCrashInfo = new Properties();

    private CrashHandler() {
    }

    public static synchronized CrashHandler getInstance() {
        CrashHandler crashHandler;
        synchronized (CrashHandler.class) {
            if (INSTANCE == null) {
                INSTANCE = new CrashHandler();
            }
            crashHandler = INSTANCE;
        }
        return crashHandler;
    }

    public void init(Context ctx) {
        this.mContext = ctx;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.crashFileName = "crash-" + this.formatter.format(new Date()) + NetworkUtils.DELIMITER_LINE + System.currentTimeMillis() + CRASH_REPORTER_EXTENSION;
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.CRASH_PATH = Environment.getExternalStorageDirectory().getPath() + "/Letv/crash/";
        }
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            LogInfo.log("king", "uncaughtException");
            throw new Exception(ex);
        } catch (Exception e) {
            e.printStackTrace();
            if (handleException(ex) || this.mDefaultHandler == null) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e2) {
                    LogInfo.log(TAG, "Error : " + e2);
                }
                PreferencesManager.getInstance().setCrashCount(PreferencesManager.getInstance().getCrashCount() + 1);
                if (PreferencesManager.getInstance().getDownloadFlag() != 1) {
                    ((NotificationManager) LetvApplication.getInstance().getSystemService("notification")).cancel(1000);
                }
                DownloadManager.pauseAllDownload();
                LetvCacheMannager.getInstance().destroy();
                if (LetvConfig.isUmeng()) {
                    MobclickAgent.onKillProcess(LetvApplication.getInstance());
                }
                Process.killProcess(Process.myPid());
                System.exit(1);
                return;
            }
            this.mDefaultHandler.uncaughtException(thread, ex);
            PreferencesManager.getInstance().setCrashCount(PreferencesManager.getInstance().getCrashCount() + 1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex != null) {
            new 1(this).start();
            try {
                collectCrashDeviceInfo(this.mContext);
                LetvLogApiTool.getInstance().saveExceptionInfo(getTimeStamp() + " letvApplication crashed:uuid=" + LetvUtils.generateDeviceId(this.mContext) + "&tel=-&username=" + PreferencesManager.getInstance().getUserName() + SubmitExceptionHandler.submitCrashExcrption(this.mContext, saveCrashInfo(ex)).toString() + "\n\r" + ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private String getTimeStamp() {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatTime.format(new Date(System.currentTimeMillis()));
    }

    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageInfo pi = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 1);
            if (pi != null) {
                this.mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                this.mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode + "");
            }
        } catch (NameNotFoundException e) {
            LogInfo.log(TAG, "Error while collect package info" + e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.mDeviceCrashInfo.put(field.getName(), field.get(null).toString());
                LogInfo.log(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e2) {
                LogInfo.log(TAG, "Error while collect crash info" + e2);
            }
        }
    }

    private String saveCrashInfo(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String result = info.toString() + "\t" + ex.getMessage();
        printWriter.close();
        return result;
    }

    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet();
            sortedFiles.addAll(Arrays.asList(crFiles));
            Iterator it = sortedFiles.iterator();
            while (it.hasNext()) {
                File cr = new File(ctx.getFilesDir(), (String) it.next());
                postReport(cr);
                cr.delete();
            }
        }
    }

    private String[] getCrashReportFiles(Context ctx) {
        return ctx.getFilesDir().list(new 2(this));
    }

    private void postReport(File file) {
    }

    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(this.mContext);
    }
}
