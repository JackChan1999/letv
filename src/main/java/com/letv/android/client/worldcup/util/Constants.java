package com.letv.android.client.worldcup.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.letv.pp.utils.NetworkUtils;
import java.io.File;

public class Constants {
    public static final String DEBUG_TAG = "debug_tag";
    public static int DOWNLOAD_JOB_NUM_LIMIT = 1;
    public static int DOWNLOAD_JOB_THREAD_LIMIT = 2;
    public static final String DYNAMIC_APP_BASE_URL = "http://dynamic.app.m.letv.com/android/dynamic.php";
    public static final String NOTIFY_DOWNLOAD_KEY_EPISODEID = "episodeId";
    public static final String NOTIFY_DOWNLOAD_KEY_PROGRESS = "progress";
    public static final String NOTIFY_DOWNLOAD_KEY_STATUS = "status";
    public static final String NOTIFY_DOWNLOAD_KEY_TYPE = "type";
    public static final String NOTIFY_INTENT_ACTION = "com.letv.android.client.worldcup.download";
    public static final String NOTIFY_INTENT_ACTION_TO_CLIENT = "com.letv.android.client.worldcup.download.action_update";
    public static final String TEST_BASE_URL = "http://test2.m.letv.com/android/dynamic.php";
    public static boolean isDebug = true;

    public static boolean isConnect(Context mContext) {
        NetworkInfo ni = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static void setDebug(boolean flag) {
        isDebug = flag;
    }

    public static void debug(String msg, String fileName) {
        debug(msg);
        if (isDebug) {
            LetvLogTool.log(new StringBuilder(String.valueOf(fileName)).append(NetworkUtils.DELIMITER_COLON).append(msg).toString(), fileName);
        }
    }

    public static void debug(String msg) {
        debug(6, msg);
    }

    public static void debug(int level, String msg) {
        if (isDebug) {
            switch (level) {
                case 5:
                    Log.w(DEBUG_TAG, msg);
                    return;
                case 6:
                    Log.e(DEBUG_TAG, msg);
                    return;
                default:
                    Log.i(DEBUG_TAG, msg);
                    return;
            }
        }
    }

    public static void deleteFile(File file) {
        new Thread(new 1(file)).start();
    }
}
