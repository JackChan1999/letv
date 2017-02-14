package com.letv.lepaysdk.utils;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LOG {
    public static boolean DEBUG = true;
    private static final String FILENAME = "lepaySDKlog.txt";
    private static final int LEVEL_D = 2;
    private static final int LEVEL_E = 4;
    private static final int LEVEL_I = 1;
    private static final int LEVEL_W = 3;
    private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void logD(String msg) {
        log(2, msg);
    }

    public static void logL(String msg) {
        if (DEBUG) {
            Log.e("LePaySDKTest", msg);
        }
    }

    public static void logI(String msg) {
        log(1, msg);
    }

    public static void logW(String msg) {
        log(3, msg);
    }

    public static void logE(String msg) {
        log(4, msg);
    }

    public static void logE(Throwable ex) {
        StackTraceElement[] elements = null;
        if (DEBUG) {
            Throwable t;
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
            }
            printWriter.close();
            String result = writer.toString();
            if (DEBUG) {
                t = new Throwable();
            } else {
                t = null;
            }
            if (t != null) {
                elements = t.getStackTrace();
            }
            String callerClassName = t != null ? elements[2].getClassName() : "N/A";
            int pos = callerClassName.lastIndexOf(46);
            if (pos >= 0) {
                callerClassName = callerClassName.substring(pos + 1);
            }
            String tag = "LePaySDKTest";
            Log.e("LePaySDKTest", result);
            try {
                write("LePaySDKTest", result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void log(int level, String msg) {
        StackTraceElement[] elements = null;
        if (DEBUG) {
            Throwable t;
            if (DEBUG) {
                t = new Throwable();
            } else {
                t = null;
            }
            if (t != null) {
                elements = t.getStackTrace();
            }
            String callerClassName = t != null ? elements[2].getClassName() : "N/A";
            String callerMethodName = t != null ? elements[2].getMethodName() : "N/A";
            int pos = callerClassName.lastIndexOf(46);
            if (pos >= 0) {
                callerClassName = callerClassName.substring(pos + 1);
            }
            String tag = callerClassName;
            StringBuffer buf = new StringBuffer();
            buf.append("[").append(callerMethodName).append("]  ").append(msg);
            switch (level) {
                case 1:
                    Log.i(tag, buf.toString());
                    break;
                case 2:
                    Log.d(tag, buf.toString());
                    break;
                case 3:
                    Log.w(tag, buf.toString());
                    break;
                case 4:
                    Log.e(tag, buf.toString());
                    break;
            }
            try {
                write(tag, buf.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void write(String tag, String log) throws IOException {
        String logger = new StringBuilder(String.valueOf(formatter.format(new Date(System.currentTimeMillis())))).append(">>>>>").append(tag).append(">>>>>").append(">>>>>").append(log).append("\n\n").toString();
        if (Environment.getExternalStorageState().equals("mounted")) {
            File file = new File(Environment.getExternalStorageDirectory(), "/lepaySDK");
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file + "/" + FILENAME, true);
            fos.write(logger.getBytes());
            fos.close();
        }
    }
}
