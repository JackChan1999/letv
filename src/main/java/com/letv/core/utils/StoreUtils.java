package com.letv.core.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import com.letv.core.db.PreferencesManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StoreUtils {
    private static final String DATAINFO = "Letv/storage/relevant_data";
    public static final int DEFAULT_SDCARD_SIZE = 104857600;
    public static final String DOWLOAD_LOCATION = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letv/storage/download");
    public static final String DOWNLOAD = "Letv/storage/download";
    private static final String DOWNLOAD_EPISODE_INFO = "Letv/storage/download/info";
    public static final String PATH = "Letv/storage/";
    private static String SDCARD2_EMULATED = "/storage/sdcard1";
    private static String SDCARD2_PATH = "/mnt/sdcard2";
    private static String SDCARD_EX = "/storage/extSdCard";
    private static String SDCARD_EXT = "/mnt/sdcard-ext";
    private static String SDCARD_EX_BIND = "/mnt/extrasd_bind";
    public static boolean SDCARD_MOUNTED = true;
    private static String SDCARD_PATH = "/mnt/sdcard";
    public static final String SHARE_LIVE_PIC_SAVE_PATH = "Letv/share/live/";
    public static final String SHARE_PATH = "Letv/share/";
    public static final String VIDEOSHOT_PIC_HEAD_PATH = "Letv/share/head/";
    public static String VIDEOSHOT_PIC_SAVE_PATH = "";
    public static final String VIDEOSHOT_PIC_TEMP_PATH = "Letv/share/temp/";
    public static final String VIDEOSHOT_PIC_WATERMARK_PATH = "Letv/share/watermark/";

    public static File getDownloadEpisodeInfoFile(Context context, int albumId, float order) {
        if (!isSdcardAvailable()) {
            return null;
        }
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), DOWNLOAD_EPISODE_INFO);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            return new File(dir, albumId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearDownloadEpisodeInfo(Activity activity) {
        int i = 0;
        File dir = activity.getDir(DOWNLOAD_EPISODE_INFO, 0);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            int length = files.length;
            while (i < length) {
                files[i].delete();
                i++;
            }
        }
    }

    public static void deleteDownloadEpisodeInfo(File file) {
        new Thread(new 1(file)).start();
    }

    public static String setDefaultDownloadPath(Context context) {
        String defaultPath = getExternalMemoryPath();
        File dir;
        if (TextUtils.isEmpty(defaultPath)) {
            defaultPath = getSdcardMemoryPath();
            if (TextUtils.isEmpty(defaultPath)) {
                defaultPath = isSdcardAvailable() ? getExternalStorage() : null;
                if (TextUtils.isEmpty(defaultPath)) {
                    PreferencesManager.getInstance().setDownloadLocation("", false);
                } else {
                    defaultPath = defaultPath + "/Letv/storage/download";
                    PreferencesManager.getInstance().setDownloadLocation(defaultPath, true);
                    if (!TextUtils.isEmpty(defaultPath)) {
                        dir = new File(defaultPath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                    }
                }
            } else {
                defaultPath = defaultPath + "/Letv/storage/download";
                if (VERSION.SDK_INT >= 19 && context != null) {
                    defaultPath = getVer4_4DownloadPath(context);
                }
                if (!TextUtils.isEmpty(defaultPath)) {
                    dir = new File(defaultPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                }
                PreferencesManager.getInstance().setDownloadLocation(defaultPath, false);
            }
        } else {
            defaultPath = defaultPath + "/Letv/storage/download";
            PreferencesManager.getInstance().setDownloadLocation(defaultPath, true);
            if (!TextUtils.isEmpty(defaultPath)) {
                dir = new File(defaultPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }
            }
        }
        return defaultPath;
    }

    public static String getLocalRestorePath(Context context, String subDir) {
        String defaultPath = getExternalMemoryPath();
        File dir;
        if (TextUtils.isEmpty(defaultPath)) {
            defaultPath = getSdcardMemoryPath();
            if (TextUtils.isEmpty(defaultPath)) {
                defaultPath = isSdcardAvailable() ? getExternalStorage() : null;
                if (!TextUtils.isEmpty(defaultPath)) {
                    defaultPath = defaultPath + "/" + subDir;
                    if (!TextUtils.isEmpty(defaultPath)) {
                        dir = new File(defaultPath);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                    }
                }
            } else {
                defaultPath = defaultPath + "/" + subDir;
                if (VERSION.SDK_INT >= 19 && context != null) {
                    defaultPath = getVer4_4DownloadPath(context);
                }
                if (!TextUtils.isEmpty(defaultPath)) {
                    dir = new File(defaultPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }
            }
        } else {
            defaultPath = defaultPath + "/" + subDir;
            if (!TextUtils.isEmpty(defaultPath)) {
                dir = new File(defaultPath);
                if (!dir.exists() && dir.mkdirs()) {
                    LogInfo.log("fornia", "getLocalRestorePath: 2020创建目录成功defaultPath:" + defaultPath);
                }
            }
        }
        return defaultPath;
    }

    public static String getLocalRestoreNomediaPath(Context context, String subDir) {
        String defaultPath = getExternalMemoryPath();
        File dir;
        if (TextUtils.isEmpty(defaultPath)) {
            defaultPath = getSdcardMemoryPath();
            if (TextUtils.isEmpty(defaultPath)) {
                defaultPath = isSdcardAvailable() ? getExternalStorage() : null;
                if (!TextUtils.isEmpty(defaultPath)) {
                    defaultPath = defaultPath + "/" + subDir;
                    if (!TextUtils.isEmpty(defaultPath)) {
                        dir = new File(defaultPath);
                        if (dir.exists()) {
                            try {
                                if (new File(dir, ".nomedia").createNewFile()) {
                                    LogInfo.log("fornia", "getLocalRestorePath: 2020创建nomedia成功1111");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (dir.mkdirs()) {
                            try {
                                if (new File(dir, ".nomedia").createNewFile()) {
                                    LogInfo.log("fornia", "getLocalRestorePath: 2020创建nomedia成功1111");
                                }
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                defaultPath = defaultPath + "/" + subDir;
                if (VERSION.SDK_INT >= 19 && context != null) {
                    defaultPath = getVer4_4DownloadPath(context);
                }
                if (!TextUtils.isEmpty(defaultPath)) {
                    dir = new File(defaultPath);
                    if (dir.exists()) {
                        try {
                            if (new File(dir, ".nomedia").createNewFile()) {
                                LogInfo.log("fornia", "getLocalRestorePath: 2323创建nomedia成功1111");
                            }
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    } else if (dir.mkdirs()) {
                        try {
                            if (new File(dir, ".nomedia").createNewFile()) {
                                LogInfo.log("fornia", "getLocalRestorePath: 2021创建nomedia成功1111");
                            }
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                }
            }
        } else {
            defaultPath = defaultPath + "/" + subDir;
            if (!TextUtils.isEmpty(defaultPath)) {
                dir = new File(defaultPath);
                if (dir.exists()) {
                    try {
                        if (new File(dir, ".nomedia").createNewFile()) {
                            LogInfo.log("fornia", "getLocalRestorePath: 2020创建nomedia成功1111");
                        }
                    } catch (IOException e2222) {
                        e2222.printStackTrace();
                    }
                } else if (dir.mkdirs()) {
                    try {
                        if (new File(dir, ".nomedia").createNewFile()) {
                            LogInfo.log("fornia", "getLocalRestorePath: 2020创建nomedia成功0000");
                        }
                    } catch (IOException e22222) {
                        e22222.printStackTrace();
                    }
                }
            }
        }
        return defaultPath;
    }

    public static boolean hasExternalMemoryPath() {
        return !TextUtils.isEmpty(getExternalMemoryPath());
    }

    public static boolean hasSdcardMemoryPath() {
        String memPath = getExternalMemoryPath();
        String sdcardPath = getSdcardMemoryPath();
        return (TextUtils.isEmpty(sdcardPath) || sdcardPath.equalsIgnoreCase(memPath)) ? false : true;
    }

    public static String getExternalStorage() {
        File file;
        try {
            file = Environment.getExternalStorageDirectory();
        } catch (Exception e) {
            file = null;
        }
        if (file == null) {
            return "";
        }
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    @TargetApi(9)
    public static String getExternalMemoryPathForSetting() {
        LogInfo.log("ljnalex", "Environment.isExternalStorageRemovable():" + Environment.isExternalStorageRemovable());
        String externalStorageDir = getExternalStorage();
        return ((!isSdcardAvailable() || Environment.isExternalStorageRemovable()) && TextUtils.isEmpty(externalStorageDir)) ? null : externalStorageDir;
    }

    @TargetApi(9)
    public static String getExternalMemoryPath() {
        LogInfo.log("ljnalex", "Environment.isExternalStorageRemovable():" + Environment.isExternalStorageRemovable());
        return (!isSdcardAvailable() || Environment.isExternalStorageRemovable()) ? null : getExternalStorage();
    }

    @TargetApi(9)
    public static String getSdcardMemoryPath() {
        if (VERSION.SDK_INT > 10 || !Environment.isExternalStorageRemovable()) {
            String path = SDCARD2_PATH;
            File file = new File(path);
            if (!file.exists()) {
                path = SDCARD_EX;
                file = new File(path);
                if (!file.exists()) {
                    path = SDCARD2_EMULATED;
                    file = new File(path);
                    if (!file.exists()) {
                        path = SDCARD_EX_BIND;
                        file = new File(path);
                        if (!file.exists()) {
                            path = SDCARD_EXT;
                            file = new File(path);
                            if (!file.exists()) {
                                path = SDCARD_PATH;
                                file = new File(path);
                                if (!(file.exists() && Environment.isExternalStorageRemovable())) {
                                    path = null;
                                }
                            }
                        }
                    }
                }
            }
            if (!file.exists()) {
                return path;
            }
            if (file.getTotalSpace() != 0 && file.canRead() && file.canWrite()) {
                return path;
            }
            return null;
        } else if (isSdcardAvailable()) {
            return getExternalStorage();
        } else {
            return null;
        }
    }

    public static boolean isSdcardAvailable() {
        boolean exist = "mounted".equals(Environment.getExternalStorageState());
        LogInfo.log("ljnalex", "Environment.isSdcardAvailable():" + exist);
        return exist;
    }

    public static long getAvailableSpaceByPath(String path) {
        StatFs statFs;
        Exception e;
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        long block = 0;
        long size = 0;
        try {
            StatFs statFs2 = new StatFs(path);
            try {
                block = (long) statFs2.getAvailableBlocks();
                size = (long) statFs2.getBlockSize();
                statFs = statFs2;
            } catch (Exception e2) {
                e = e2;
                statFs = statFs2;
                e.printStackTrace();
                return size * block;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return size * block;
        }
        return size * block;
    }

    public static long getSdcardAvailableSpace() {
        Exception e;
        if (!isSdcardAvailable()) {
            return -1;
        }
        String sdcardPath = getDownloadDir().getAbsolutePath();
        LogInfo.log("king", "========sdcardPath:" + sdcardPath);
        File dir = new File(sdcardPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long block = 0;
        long size = 0;
        try {
            StatFs statFs = new StatFs(sdcardPath);
            StatFs statFs2;
            try {
                block = (long) statFs.getAvailableBlocks();
                size = (long) statFs.getBlockSize();
                statFs2 = statFs;
            } catch (Exception e2) {
                e = e2;
                statFs2 = statFs;
                LogInfo.log("king", "========e:" + e.getMessage());
                e.printStackTrace();
                return size * block;
            }
        } catch (Exception e3) {
            e = e3;
            LogInfo.log("king", "========e:" + e.getMessage());
            e.printStackTrace();
            return size * block;
        }
        return size * block;
    }

    public static File getDownloadDir() {
        String path = PreferencesManager.getInstance().getDownloadLocation();
        if (TextUtils.isEmpty(path)) {
            path = DOWLOAD_LOCATION;
        }
        File downloadDir = new File(path);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }

    public static long getTotalSpaceByPath(String path) {
        Exception e;
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        long block = 0;
        long size = 0;
        try {
            StatFs statFs = new StatFs(path);
            StatFs statFs2;
            try {
                block = (long) statFs.getBlockCount();
                size = (long) statFs.getBlockSize();
                statFs2 = statFs;
            } catch (Exception e2) {
                e = e2;
                statFs2 = statFs;
                e.printStackTrace();
                return size * block;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return size * block;
        }
        return size * block;
    }

    public static long getSdcardTotalSpace() {
        Exception e;
        if (!isSdcardAvailable()) {
            return -1;
        }
        String sdcardPath = getDownloadDir().getAbsolutePath();
        File dir = new File(sdcardPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long block = 0;
        long size = 0;
        try {
            StatFs statFs = new StatFs(sdcardPath);
            StatFs statFs2;
            try {
                block = (long) statFs.getBlockCount();
                size = (long) statFs.getBlockSize();
                statFs2 = statFs;
            } catch (Exception e2) {
                e = e2;
                statFs2 = statFs;
                e.printStackTrace();
                return size * block;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return size * block;
        }
        return size * block;
    }

    public static File getSdcardRootDirectory() {
        if (isSdcardAvailable()) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    public static String getVer4_4DownloadPath(Context context) {
        File[] dirs = ContextCompat.getExternalFilesDirs(context, null);
        LogInfo.log("ljnalex", "dirs.length:" + dirs.length);
        if (dirs == null || dirs.length == 0) {
            return "";
        }
        if (isSdcardAvailable() && dirs.length >= 2 && dirs[1] != null) {
            return dirs[1].getAbsolutePath();
        }
        if (isSdcardAvailable() || dirs[0] == null) {
            return "";
        }
        return dirs[0].getAbsolutePath();
    }

    private static void writeRelevantData(Context context, String data, File file) {
        new 2(file, data).execute(new Void[0]);
    }

    private static String readRelevantData(File file) {
        Exception e;
        Throwable th;
        ObjectInputStream in = null;
        try {
            ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(file));
            try {
                String str = (String) in2.readObject();
                if (in2 != null) {
                    try {
                        in2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                in = in2;
                return str;
            } catch (Exception e3) {
                e = e3;
                in = in2;
                try {
                    e.printStackTrace();
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                in = in2;
                if (in != null) {
                    in.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (in != null) {
                in.close();
            }
            return null;
        }
    }

    public static File getRelevantFile(Context context, String filename) {
        if (!isSdcardAvailable()) {
            return null;
        }
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), DATAINFO);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            File file = new File(dir, filename);
            if (file.exists()) {
                return file;
            }
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveRelevantData(Context context, String filename, String data) {
        File file = getRelevantFile(context, filename);
        if (file != null && !TextUtils.isEmpty(data)) {
            writeRelevantData(context, data, file);
        }
    }

    public static String getRelevantData(Context context, String filename) {
        File file = getRelevantFile(context, filename);
        if (file != null) {
            return readRelevantData(file);
        }
        return null;
    }
}
