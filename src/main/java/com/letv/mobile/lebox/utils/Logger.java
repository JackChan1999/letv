package com.letv.mobile.lebox.utils;

import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import com.letv.pp.utils.NetworkUtils;

public class Logger {
    private static Logger log;
    private final String TAG = "lebox";
    private final boolean isDebug = true;
    private Handler mHandler;
    private StringBuilder message;
    private final boolean noteDebug;
    private final boolean simplyDebug;
    private final boolean sysDebug;

    private Logger() {
        getClass();
        this.sysDebug = false;
        getClass();
        this.simplyDebug = true;
        getClass();
        this.noteDebug = false;
        if (this.noteDebug) {
            this.message = new StringBuilder();
        }
    }

    public static void i(String tag, String msg) {
        if (getInstanced().sysDebug) {
            Log.i(tag, msg);
        }
        if (getInstanced().simplyDebug) {
            String m = tag + NetworkUtils.DELIMITER_COLON + msg;
            getInstanced().getClass();
            Log.i("lebox", m);
        }
        getInstanced().debug("I", tag, msg);
    }

    public static void v(String tag, String msg) {
        if (getInstanced().sysDebug) {
            Log.v(tag, msg);
        }
        if (getInstanced().simplyDebug) {
            String m = tag + NetworkUtils.DELIMITER_COLON + msg;
            getInstanced().getClass();
            Log.v("lebox", m);
        }
        getInstanced().debug("V", tag, msg);
    }

    public static void d(String tag, String msg) {
        if (getInstanced().sysDebug) {
            Log.d(tag, msg);
        }
        if (getInstanced().simplyDebug) {
            String m = tag + NetworkUtils.DELIMITER_COLON + msg;
            getInstanced().getClass();
            Log.d("lebox", m);
        }
        getInstanced().debug("D", tag, msg);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable re) {
        if (getInstanced().sysDebug) {
            Log.w(tag, msg, re);
        }
        if (getInstanced().simplyDebug) {
            String m = tag + NetworkUtils.DELIMITER_COLON + msg;
            getInstanced().getClass();
            Log.w("lebox", m, re);
        }
        if (re != null) {
            msg = msg + '\n' + Log.getStackTraceString(re);
        }
        getInstanced().debug("W", tag, msg);
    }

    public static void e(String tag, String msg) {
        if (getInstanced().sysDebug) {
            Log.e(tag, msg);
        }
        if (getInstanced().simplyDebug) {
            String m = tag + NetworkUtils.DELIMITER_COLON + msg;
            getInstanced().getClass();
            Log.e("lebox", m);
        }
        getInstanced().debug("E", tag, msg);
    }

    private static Logger getInstanced() {
        if (log == null) {
            synchronized (Logger.class) {
                log = new Logger();
            }
        }
        return log;
    }

    private void debug(String type, String tag, String msg) {
        if (this.noteDebug && this.message != null) {
            this.message.append(type);
            this.message.append("   ");
            this.message.append(currentTimeToString());
            this.message.append(NetworkUtils.DELIMITER_COLON);
            this.message.append(tag);
            this.message.append(NetworkUtils.DELIMITER_COLON);
            this.message.append(msg);
            this.message.append("\n");
            if (this.mHandler != null && this.message != null) {
                this.mHandler.sendMessage(this.mHandler.obtainMessage(0, this.message.toString()));
            }
        }
    }

    private String currentTimeToString() {
        return DateFormat.format("HH:mm:ss", System.currentTimeMillis()).toString();
    }

    public static void logClean() {
        getInstanced().message = new StringBuilder();
    }

    public static String getLog() {
        return getInstanced().message.toString();
    }

    public static void setHandler(Handler handler) {
        getInstanced().mHandler = handler;
    }
}
