package com.letv.mobile.core.config;

import android.annotation.SuppressLint;
import android.os.Environment;
import com.letv.android.client.activity.MainActivity;
import com.letv.mobile.core.utils.ContextProvider;
import com.letv.mobile.core.utils.FileUtils;
import com.letv.mobile.core.utils.Utils;
import java.io.File;

public final class LeTVConfig {
    private static final String GLOBAL_DOWNLOAD_PATH = "/ledown/";
    public static final String IMAGE_CACHE_DIR = "/.image/";
    public static final int LETV_BASE_LOG_LEVEL = 3;
    @Deprecated
    private static final String LETV_DOWNLOAD_PATH = "/download/";
    public static final String LETV_ERROR_PATH = "/errorLog/";
    public static final String LETV_FILE_LOGGER_PATH = "/log/";
    private static final String LETV_NOT_CHECK_MEDIA_FILE = "/.nomedia";
    private static final String LETV_PLAYER_LOG_PATH = "/player_log/";
    static final String TEMP_PATH = "shareTemp.png";
    private static String sAppName = MainActivity.THIRD_PARTY_LETV;
    private static String sWorkingPath = null;

    public static void init(String appName) {
        if (!Utils.isStringEmpty(appName)) {
            sAppName = appName;
            initAllDir();
        }
    }

    private static void initAllDir() {
        int i = 0;
        FileUtils.createFile(getNoMediaFilePath());
        String[] allDir = new String[]{getImageCachePath(), getErrorLogPath(), getPlayerLogPath(), getDownloadPath()};
        int length = allDir.length;
        while (i < length) {
            FileUtils.createDir(allDir[i]);
            i++;
        }
        FileUtils.createFile(getDownloadNoMediaFilePath());
    }

    public static String getImageCachePath() {
        return getGlobalWorkingPath() + sAppName + IMAGE_CACHE_DIR;
    }

    public static String getErrorLogPath() {
        return getGlobalWorkingPath() + sAppName + LETV_ERROR_PATH;
    }

    private static String getNoMediaFilePath() {
        return getGlobalWorkingPath() + sAppName + LETV_NOT_CHECK_MEDIA_FILE;
    }

    public static String getDownloadNoMediaFilePath() {
        return getDownloadPath() + LETV_NOT_CHECK_MEDIA_FILE;
    }

    public static String getAppRoot() {
        return getGlobalWorkingPath() + sAppName;
    }

    public static String getDownloadPath() {
        return getGlobalWorkingPath() + GLOBAL_DOWNLOAD_PATH;
    }

    public static String getPlayerLogPath() {
        return getGlobalWorkingPath() + sAppName + LETV_PLAYER_LOG_PATH;
    }

    public static String getGlobalWorkingPath() {
        if (sWorkingPath == null) {
            String str = getSDPath();
            if (str == null) {
                return getNoSdCardPath();
            }
            if (!new File(str).canWrite()) {
                return getNoSdCardPath();
            }
            sWorkingPath = str;
        }
        return sWorkingPath;
    }

    public static String getSDPath() {
        File sdDir = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir == null ? null : sdDir.toString() + "/";
    }

    @SuppressLint({"SdCardPath"})
    public static String getNoSdCardPath() {
        return new StringBuilder(String.valueOf(ContextProvider.getApplicationContext().getFilesDir().toString())).append(File.separator).toString();
    }

    public static File getImgCacheSDCardFileInstance() {
        return ImgCacheFileHolder.access$0();
    }

    public static File getImgCacheMemoryFileInstance() {
        return ImgCacheFileHolder.access$1();
    }

    public static String getsAppName() {
        return sAppName;
    }

    public static String getShareTempPath() {
        return getImageCachePath() + TEMP_PATH;
    }

    private LeTVConfig() {
    }
}
