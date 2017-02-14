package com.letv.mobile.core.log;

import android.text.TextUtils;
import android.util.Log;
import com.letv.mobile.core.utils.StringUtils;
import com.letv.mobile.core.utils.SystemUtil;

public class Logger implements LogManager {
    private static final String LOG_LEVELS = "VDIWE";
    private static final String PROPERTY_KEY_LOGLEVEL = "debug.tvclient.loglevel";
    private static int level;
    private static boolean run = false;
    private static String sLogTag = "";
    private String tag = "LETV";
    private boolean toggle = true;

    static {
        getSystemLogLevel();
    }

    private static void getSystemLogLevel() {
        String strLogLevel = SystemUtil.getSystemProperty(PROPERTY_KEY_LOGLEVEL);
        if (TextUtils.isEmpty(strLogLevel)) {
            level = 3;
            return;
        }
        int logLevelIndex = LOG_LEVELS.indexOf(Character.toUpperCase(strLogLevel.charAt(0)));
        if (logLevelIndex != -1) {
            level = logLevelIndex + 2;
        } else {
            level = 3;
        }
    }

    public Logger(String tag) {
        this.tag = tag;
    }

    public static void setLogTagByPackageName(String packageName) {
        if (StringUtils.equalsNull(sLogTag) && !StringUtils.equalsNull(packageName)) {
            sLogTag = new StringBuilder(String.valueOf(packageName)).append(" : ").toString();
        }
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isToggle() {
        return this.toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public int getLevel() {
        return level;
    }

    public void e(String msg) {
        if (this.toggle && level <= 6) {
            print(6, msg);
        }
    }

    public void w(String msg) {
        if (this.toggle && level <= 5) {
            print(5, msg);
        }
    }

    public void i(String msg) {
        if (this.toggle && level <= 4) {
            print(4, msg);
        }
    }

    public void d(String msg) {
        if (this.toggle && level <= 3) {
            print(3, msg);
        }
    }

    public void v(String msg) {
        if (this.toggle && level <= 2) {
            print(2, msg);
        }
    }

    private void print(int level, String msg) {
        if (!run) {
            Log.e("LETV_MOBILE_LEADING", LOG_RUN_NAME);
            run = true;
        }
        callSysLog(level, markTag(), msg);
    }

    private static void callSysLog(int level, String tag, String msg) {
        if (tag == null) {
            tag = "";
        }
        if (msg == null) {
            msg = "";
        }
        switch (level) {
            case 2:
                Log.v(tag, sLogTag + msg);
                return;
            case 3:
                Log.d(tag, sLogTag + msg);
                return;
            case 4:
                Log.i(tag, sLogTag + msg);
                return;
            case 5:
                Log.w(tag, sLogTag + msg);
                return;
            case 6:
                Log.e(tag, sLogTag + msg);
                return;
            default:
                return;
        }
    }

    private String markTag() {
        if (this.tag == null) {
            return "LETV_MOBILE_LEADING";
        }
        return this.tag;
    }

    private static void write(int level, String tag, String msg) {
        if (level >= level) {
            callSysLog(level, tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        write(2, tag, msg);
    }

    public static void d(String tag, String msg) {
        write(3, tag, msg);
    }

    public static void i(String tag, String msg) {
        write(4, tag, msg);
    }

    public static void w(String tag, String msg) {
        write(5, tag, msg);
    }

    public static void e(String tag, String msg) {
        write(6, tag, msg);
    }
}
