package com.letv.plugin.pluginloader.apk.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import com.letv.plugin.pluginloader.apk.hook.HookFactory;
import com.letv.plugin.pluginloader.apk.hook.proxy.LibCoreHook;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.cybergarage.upnp.std.av.server.object.ContentNode;

public class Log {
    private static final String TAG = "Log";
    private static boolean sDebug;
    private static final File sDir = new File(Environment.getExternalStorageDirectory(), "360Log/Plugin/");
    private static boolean sFileLog;
    private static final SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final SimpleDateFormat sFormat1 = new SimpleDateFormat("yyyyMMdd");
    private static Handler sHandler = new Handler(sHandlerThread.getLooper());
    private static HandlerThread sHandlerThread = new HandlerThread("FileLogThread");

    static {
        boolean z = true;
        sDebug = true;
        sFileLog = false;
        if (!(sDir.exists() && sDir.isDirectory())) {
            z = false;
        }
        sFileLog = z;
        sDebug = sFileLog;
        sHandlerThread.start();
    }

    private Log() {
    }

    public static boolean isDebug() {
        return sDebug;
    }

    private static boolean isFileLog() {
        return sFileLog;
    }

    public static boolean isLoggable(int i) {
        return isDebug();
    }

    public static boolean isLoggable() {
        return isDebug();
    }

    private static String levelToStr(int level) {
        switch (level) {
            case 2:
                return "V";
            case 3:
                return "D";
            case 4:
                return "I";
            case 5:
                return "W";
            case 6:
                return "E";
            case 7:
                return "A";
            default:
                return ContentNode.UNKNOWN;
        }
    }

    private static File getLogFile() {
        File file = new File(Environment.getExternalStorageDirectory(), String.format("360Log/Plugin/Log_%s_%s.log", new Object[]{sFormat1.format(new Date()), Integer.valueOf(Process.myPid())}));
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return file;
    }

    private static void logToFile(int level, String tag, String format, Object[] args, Throwable tr) {
        sHandler.post(new 1(level, tag, format, args, tr));
    }

    private static void logToFileInner(int level, String tag, String format, Object[] args, Throwable tr) {
        Throwable e;
        Throwable th;
        PrintWriter writer = null;
        try {
            if (isFileLog()) {
                HookFactory.getInstance().setHookEnable(LibCoreHook.class, false);
                PrintWriter writer2 = new PrintWriter(new FileWriter(getLogFile(), true));
                try {
                    String msg = String.format(format, args);
                    writer2.println(String.format("%s %s-%s/%s %s/%s %s", new Object[]{sFormat.format(new Date()), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid()), getProcessName(), levelToStr(level), tag, msg}));
                    if (tr != null) {
                        tr.printStackTrace(writer2);
                        writer2.println();
                    }
                    if (writer2 != null) {
                        try {
                            writer2.close();
                        } catch (Throwable th2) {
                        }
                    }
                    HookFactory.getInstance().setHookEnable(LibCoreHook.class, true);
                    writer = writer2;
                    return;
                } catch (Throwable th3) {
                    th = th3;
                    writer = writer2;
                    if (writer != null) {
                        writer.close();
                    }
                    HookFactory.getInstance().setHookEnable(LibCoreHook.class, true);
                    throw th;
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (Throwable th4) {
                }
            }
            HookFactory.getInstance().setHookEnable(LibCoreHook.class, true);
        } catch (Throwable th5) {
            e = th5;
            e.printStackTrace();
            if (writer != null) {
                writer.close();
            }
            HookFactory.getInstance().setHookEnable(LibCoreHook.class, true);
        }
    }

    private static String getProcessName() {
        return "?";
    }

    private static void logToFileWtf(String tag, String format, Object[] args, Throwable tr) {
        logToFile(-1, tag, format, args, tr);
    }

    public static void v(String tag, String format, Object... args) {
        v(tag, format, null, args);
    }

    public static void v(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(2)) {
            logToFile(2, tag, format, args, tr);
            if (tr == null) {
                android.util.Log.v(tag, String.format(format, args));
            } else {
                android.util.Log.v(tag, String.format(format, args), tr);
            }
        }
    }

    public static void d(String tag, String format, Object... args) {
        d(tag, format, null, args);
    }

    public static void d(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(3)) {
            logToFile(3, tag, format, args, tr);
            if (tr == null) {
                android.util.Log.d(tag, String.format(format, args));
            } else {
                android.util.Log.d(tag, String.format(format, args), tr);
            }
        }
    }

    public static void i(String tag, String format, Object... args) {
        i(tag, format, null, args);
    }

    public static void i(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(4)) {
            logToFile(4, tag, format, args, tr);
            if (tr == null) {
                android.util.Log.i(tag, String.format(format, args));
            } else {
                android.util.Log.i(tag, String.format(format, args), tr);
            }
        }
    }

    public static void w(String tag, String format, Object... args) {
        w(tag, format, null, args);
    }

    public static void w(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(5)) {
            logToFile(5, tag, format, args, tr);
            if (tr == null) {
                android.util.Log.w(tag, String.format(format, args));
            } else {
                android.util.Log.w(tag, String.format(format, args), tr);
            }
        }
    }

    public static void w(String tag, Throwable tr) {
        w(tag, "Log.warn", tr, new Object[0]);
    }

    public static void e(String tag, String format, Object... args) {
        e(tag, format, null, args);
    }

    public static void e(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(6)) {
            logToFile(6, tag, format, args, tr);
            if (tr == null) {
                android.util.Log.e(tag, String.format(format, args));
            } else {
                android.util.Log.e(tag, String.format(format, args), tr);
            }
        }
    }

    public static void wtf(String tag, String format, Object... args) {
        wtf(tag, format, null, args);
    }

    public static void wtf(String tag, Throwable tr) {
        wtf(tag, "wtf", tr, new Object[0]);
    }

    public static void wtf(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable()) {
            logToFileWtf(tag, format, args, tr);
            if (tr == null) {
                android.util.Log.wtf(tag, String.format(format, args));
            } else {
                android.util.Log.wtf(tag, String.format(format, args), tr);
            }
        }
    }
}
