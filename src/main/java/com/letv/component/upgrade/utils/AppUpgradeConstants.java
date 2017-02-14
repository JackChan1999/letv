package com.letv.component.upgrade.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.text.TextUtils;
import com.letv.pp.utils.NetworkUtils;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class AppUpgradeConstants {
    public static final int AUTO_CHECK = 1;
    public static final int CANCEL_UPGRADE = 201;
    public static final String CM_WAP_PROXY = "10.0.0.172";
    public static final int CONFIRM_UPGRADE = 200;
    public static final int CONNECT_TIMEOUT = 10000;
    public static final String CT_WAP_PROXY = "10.0.0.200";
    public static final String CURRENT_APP_NAME = "letv_android_client.apk";
    public static final int DEFAULT_SDCARD_SIZE = 104857600;
    public static final String DOWLOAD_LOCATION = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath())).append(File.separator).append(PATH_DOWNLOAD).toString();
    public static final int DOWNLOAD_JOB_NUM_LIMIT = 1;
    public static final int DOWNLOAD_RESOURCE_THREAD_COUNT = 1;
    public static final String FILENAME_NULL = "22402";
    public static final int FORCE_UPGRADE_FAIL = 400;
    public static final int FORCE_UPGRADE_SUCCESS = 200;
    public static final int FROM_SILENT = 1;
    public static final int GET_UPGRADE_INFO_ERROR = 402;
    public static final String INTENT_REMOTE_SERVICE = "com.letv.appupgrade.SERVICE";
    public static final int MANUAL_CHECK = 2;
    public static final int NEED_UPGRADE = 200;
    public static final String NINE_ZERO_TWO_CONSTANT = "902";
    public static final String NOTIFY_INTENT_ACTION = "com.letv.android.client.appupgrade.download.list";
    public static final int NOT_FROM_SILENT = 2;
    public static final int NOT_NEED_UPGRADE = 401;
    public static final String PATH_DOWNLOAD = "letv_appdownload/storage/download";
    public static final int READ_TIMEOUT = 10000;
    public static final int REJECT_MAX_NUM = 2;
    public static final String ROOT_SU = "ltsysu";
    public static String RemoteBroadcastAction = "com.letv.remote_letv_down_finish";
    public static final String SERVICE_KILLED = "22403";
    public static final String START_DL_FAIL = "22404";
    public static final String START_DL_SUCCESS = "22100";
    public static final String TWENTYTWO_ZERO_ONE_CONSTANT = "2201";
    public static final String URL_NULL = "22401";
    public static String UpgradeLogVer = "2.0";
    public static final int WAP_PROXY_PORT = 80;
    public static ApplicationInfo appInfo;
    public static String sdkVersion = "1.2";

    public static String[] getChannelInfo(Context context) {
        String[] channelInfo = new String[2];
        try {
            String pchannel = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("UMENG_CHANNEL");
            InputStream in = context.getAssets().open("channel-maps.properties");
            if (pchannel == null || in == null) {
                return channelInfo;
            }
            Properties p = new Properties();
            p.load(in);
            String value = (String) p.get(pchannel);
            if (pchannel.contains(" ")) {
                value = (String) p.get(pchannel.split(" ")[0]);
                if (!TextUtils.isEmpty(value) && value.contains(NetworkUtils.DELIMITER_COLON)) {
                    value = value.substring(value.indexOf(58) + 1);
                }
            }
            if (TextUtils.isEmpty(value) || !value.contains("#")) {
                return channelInfo;
            }
            String[] data = value.split("#");
            if (data == null || data.length != 2) {
                return channelInfo;
            }
            String appeky = data[1];
            channelInfo[0] = data[0];
            channelInfo[1] = appeky;
            return channelInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
