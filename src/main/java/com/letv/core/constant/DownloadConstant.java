package com.letv.core.constant;

import android.os.Environment;

public class DownloadConstant {
    public static int DOWNLOAD_JOB_NUM_LIMIT = 1;
    public static int DOWNLOAD_JOB_THREAD_LIMIT = 3;
    public static final String DOWNLOAD_LOCATION_DIR = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letv/storage/download");
    public static final String DOWNLOAD_LOCATION_KEY = "download_location";
    public static final int DOWNLOAD_STATE_DOWNLOADING = 1;
    public static final int DOWNLOAD_STATE_FINISH = 0;
    public static final int DOWNLOAD_STATE_LOCAL = 2;
    public static final int DOWNLOAD_STATE_WORLDCUP = 3;
    public static final String DYNAMIC_APP_BASE_URL = "http://dynamic.app.m.letv.com/android/dynamic.php";
    public static final String KEY_DOWNLOAD_JOB_IS_WIFI = "iswifi";
    public static final String KEY_DOWNLOAD_JOB_WIFI_STATE = "wifistate";
    public static final String KEY_NOTIFICATION_SERVICE_ACTION = "action";
    public static final String NOTIFY_DOWNLOAD_KEY_EPISODEID = "episodeId";
    public static final String NOTIFY_DOWNLOAD_KEY_PROGRESS = "progress";
    public static final String NOTIFY_DOWNLOAD_KEY_STATUS = "status";
    public static final String NOTIFY_DOWNLOAD_KEY_TYPE = "type";
    public static final String NOTIFY_INTENT_ACTION = "com.letv.android.client.worldcup.download";
    public static final String NOTIFY_INTENT_ACTION_TO_CLIENT = "com.letv.android.client.worldcup.download.action_update";
    public static final String PATH = "Letv/storage/";
    public static final String PATH_DOWNLOAD = "Letv/storage/download";
    public static final long SIZE_GB = 1073741824;
    public static final long SIZE_KB = 1024;
    public static final long SIZE_MB = 1048576;
    public static final String TEST_BASE_URL = "http://test2.m.letv.com/android/dynamic.php";
    public static final int TYPE_DOWNLOAD_JOB_WIFI_STATE = 4;
    public static final int TYPE_NOTIFICATION_SERVICE_START = 1;
    public static final int TYPE_NOTIFICATION_SERVICE_STOP = 0;

    public class BroadcaseIntentParams {
        public static final int ADD = 6;
        public static final int COMPLETE = 1;
        public static final int CONTINUE = 5;
        public static final int DELETE = 4;
        public static final int DELETEALL = 11;
        public static final int ERROR = 9;
        public static final String KEY_DOWNLOADED = "downloaded";
        public static final String KEY_DOWNLOADED_STR = "downloaded_str";
        public static final String KEY_ID = "id";
        public static final String KEY_PROGRESS = "progress";
        public static final String KEY_SPEED = "speed";
        public static final String KEY_STATUS = "status";
        public static final String KEY_TOTAL = "total";
        public static final String KEY_TOTAL_STR = "total_str";
        public static final String KEY_TYPE = "type";
        public static final String KEY_URL = "url";
        public static final int MESSAGE_UPDATE_DOWNLOAD_PROGRESS = 1010;
        public static final int PAUSE = 3;
        public static final int PAUSE_USER = 10;
        public static final int PENDING = 8;
        public static final int PROCESS = 0;
        public static final int START = 2;
        public static final int STOP = 7;
    }
}
