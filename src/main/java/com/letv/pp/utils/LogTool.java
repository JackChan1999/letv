package com.letv.pp.utils;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LogTool {
    private static final int DEBUG = 3;
    private static final int ERROR = 6;
    public static final int ERROR_LEVEL = 8;
    public static final int FATAL_LEVEL = 16;
    private static final int INFO = 4;
    public static final int INFO_LEVEL = 2;
    private static final String ROOT_TAG = "cdeapi";
    private static final String TAG = "LogTool";
    public static final int TRACE_LEVEL = 1;
    private static final int VERBOSE = 2;
    private static final int WARN = 5;
    public static final int WARN_LEVEL = 4;
    private static int sLogLevel = 30;

    public static final void setLogLevel(int level) {
        sLogLevel = level;
    }

    public static final void v(String tag, String msg) {
        if ((sLogLevel & 1) != 0) {
            print(2, ROOT_TAG, buildLog(tag, msg, null));
        }
    }

    public static final void v(String tag, String msg, Throwable tr) {
        if ((sLogLevel & 1) != 0) {
            print(2, ROOT_TAG, buildLog(tag, msg, tr));
        }
    }

    public static final void v(String tag, String format, Object... args) {
        v(tag, null, format, args);
    }

    public static final void v(String tag, Throwable tr, String format, Object... args) {
        if ((sLogLevel & 1) != 0) {
            print(2, ROOT_TAG, buildLog(tag, formatString(format, args), tr));
        }
    }

    public static final void d(String tag, String msg) {
        if ((sLogLevel & 1) != 0) {
            print(3, ROOT_TAG, buildLog(tag, msg, null));
        }
    }

    public static final void d(String tag, String msg, Throwable tr) {
        if ((sLogLevel & 1) != 0) {
            print(3, ROOT_TAG, buildLog(tag, msg, tr));
        }
    }

    public static final void d(String tag, String format, Object... args) {
        d(tag, null, format, args);
    }

    public static final void d(String tag, Throwable tr, String format, Object... args) {
        if ((sLogLevel & 1) != 0) {
            print(3, ROOT_TAG, buildLog(tag, formatString(format, args), tr));
        }
    }

    public static final void i(String tag, String msg) {
        if ((sLogLevel & 2) != 0) {
            print(4, ROOT_TAG, buildLog(tag, msg, null));
        }
    }

    public static final void i(String tag, String msg, Throwable tr) {
        if ((sLogLevel & 2) != 0) {
            print(4, ROOT_TAG, buildLog(tag, msg, tr));
        }
    }

    public static final void i(String tag, String format, Object... args) {
        i(tag, null, format, args);
    }

    public static final void i(String tag, Throwable tr, String format, Object... args) {
        if ((sLogLevel & 2) != 0) {
            print(4, ROOT_TAG, buildLog(tag, formatString(format, args), tr));
        }
    }

    public static final void w(String tag, String msg) {
        if ((sLogLevel & 4) != 0) {
            print(5, ROOT_TAG, buildLog(tag, msg, null));
        }
    }

    public static final void w(String tag, String msg, Throwable tr) {
        if ((sLogLevel & 4) != 0) {
            print(5, ROOT_TAG, buildLog(tag, msg, tr));
        }
    }

    public static final void w(String tag, String format, Object... args) {
        w(tag, null, format, args);
    }

    public static final void w(String tag, Throwable tr, String format, Object... args) {
        if ((sLogLevel & 4) != 0) {
            print(5, ROOT_TAG, buildLog(tag, formatString(format, args), tr));
        }
    }

    public static final void e(String tag, String msg) {
        if ((sLogLevel & 8) != 0) {
            print(6, ROOT_TAG, buildLog(tag, msg, null));
        }
    }

    public static final void e(String tag, String msg, Throwable tr) {
        if ((sLogLevel & 8) != 0) {
            print(6, ROOT_TAG, buildLog(tag, msg, tr));
        }
    }

    public static final void e(String tag, String format, Object... args) {
        e(tag, null, format, args);
    }

    public static final void e(String tag, Throwable tr, String format, Object... args) {
        if ((sLogLevel & 8) != 0) {
            print(6, ROOT_TAG, buildLog(tag, formatString(format, args), tr));
        }
    }

    private static final void print(int priority, String tag, String msg) {
        Log.println(priority, tag, msg);
    }

    private static final String buildLog(String tag, String msg, Throwable tr) {
        if (tr != null) {
            if (StringUtils.isEmpty(msg)) {
                msg = getStackTraceString(tr);
            } else {
                msg = new StringBuilder(String.valueOf(msg)).append('\n').append(getStackTraceString(tr)).toString();
            }
        }
        return new StringBuffer().append("[").append(tag).append("] ").append(msg).toString();
    }

    private static String formatString(String format, Object... args) {
        try {
            format = String.format(format.replaceAll("%d", "%s").replaceAll("%f", "%s"), args);
        } catch (Exception e) {
            e(TAG, "formatString failed. " + e.toString());
            if (args != null) {
                for (Object arg : args) {
                    format = new StringBuilder(String.valueOf(format)).append(", ").append(arg).toString();
                }
            }
        }
        return format;
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        for (Throwable t = tr; t != null; t = t.getCause()) {
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, false);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
