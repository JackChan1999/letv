package com.letv.download.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import com.letv.core.BaseApplication;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.SharedPreferenceUtils;
import com.letv.download.util.L;
import java.io.File;

public class StoreManager {
    public static final long DEFAULT_SDCARD_SIZE = 104857600;
    public static final long DEFUALT_DOWNLOAD_MINI_SIZE = 52428800;
    public static final String DOWLOAD_LOCATION = (Environment.getExternalStorageDirectory().getAbsolutePath() + DOWNLOAD);
    public static final String DOWNLOAD = "LetvDownload/storage/download";
    public static final String DOWNLOAD2 = "letvDownload/storage/download";
    private static final String DOWNLOAD_DEVICE = "download_device";
    private static final String DOWNLOAD_LOCATION = "download_location";
    public static final int NO_SOTORE_LOCATION = 0;
    public static final String PATH = "LetvDownload/storage/";
    public static final String PATH2 = "letvDownload/storage/";
    public static final int PHONE_STORE_LOCATION = 1;
    private static final String SDCARDPATH = "sdcardpath";
    public static final int SDCARD_STORE_LOCATION = 2;
    private static final String TAG = StoreManager.class.getSimpleName();
    private static Context sContext = BaseApplication.getInstance();
    private static IStorePath sIStorePath = new VolumePath();
    private static StoreDeviceInfo sPhoneStoreDeviceInfo = null;
    private static StoreDeviceInfo sSdCardDeviceInfo = null;

    public static class StoreDeviceInfo implements Cloneable {
        public long mAvailable;
        public boolean mIsMount = true;
        public boolean mIsNotEnough = false;
        public boolean mIsPhoneStore = false;
        public boolean mIsSelect = false;
        public String mPath;
        public long mTotalSpace;

        public StoreDeviceInfo(String rootPath, String path, boolean isPhoneStore, boolean isMount) {
            initStoreDeviceInfo(rootPath, path, isPhoneStore, isMount);
        }

        public StoreDeviceInfo(String rootPath, String path, boolean isPhoneStore) {
            L.v(StoreManager.TAG, "StoreDeviceInfo path : " + path + " isPhoneStore : " + isPhoneStore);
            initStoreDeviceInfo(rootPath, path, isPhoneStore, true);
        }

        public Object clone() throws CloneNotSupportedException {
            StoreDeviceInfo storeDeviceInfo = null;
            try {
                return (StoreDeviceInfo) super.clone();
            } catch (Exception e) {
                e.printStackTrace();
                return storeDeviceInfo;
            }
        }

        public void initStoreDeviceInfo(String rootPath, String path, boolean isPhoneStore, boolean isMount) {
            boolean z;
            boolean z2 = true;
            this.mPath = path;
            LogInfo.log(StoreManager.TAG, "initStoreDeviceInfo isMount : " + isMount + " " + isPhoneStore);
            if (isMount) {
                this.mAvailable = getAvailableSpace(rootPath);
                this.mTotalSpace = getTotalSpace(rootPath);
            } else {
                this.mAvailable = 0;
                this.mTotalSpace = 0;
            }
            String currentPath = StoreManager.getDownloadPath();
            if (!TextUtils.isEmpty(path) && path.toLowerCase().equals(currentPath.toLowerCase()) && isMount) {
                z = true;
            } else {
                z = false;
            }
            this.mIsSelect = z;
            this.mIsMount = isMount;
            this.mIsPhoneStore = isPhoneStore;
            if (this.mAvailable >= StoreManager.DEFAULT_SDCARD_SIZE) {
                z2 = false;
            }
            this.mIsNotEnough = z2;
        }

        public StoreDeviceInfo(boolean isMount, boolean isPhoneStore) {
            this.mIsMount = isMount;
            this.mIsPhoneStore = isPhoneStore;
            if (!isMount) {
                this.mIsNotEnough = true;
            }
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(" mIsPhoneStore : " + this.mIsPhoneStore);
            sb.append(" mSurplusSpace : " + this.mAvailable);
            sb.append(" mTotalSpace : " + this.mTotalSpace);
            sb.append(" mPath : " + this.mPath);
            sb.append(" mIsSelect : " + this.mIsSelect);
            sb.append(" mIsMount : " + this.mIsMount);
            return sb.toString();
        }

        @TargetApi(18)
        public static long getAvailableSpace(String path) {
            Exception e;
            Log.v(StoreManager.TAG, "getAvailableSpace path : " + path);
            if (TextUtils.isEmpty(path)) {
                return 0;
            }
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            StatFs statFs = null;
            long block = 0;
            long size = 0;
            try {
                StatFs statFs2 = new StatFs(path);
                try {
                    if (VERSION.SDK_INT >= 18) {
                        block = statFs2.getAvailableBlocksLong();
                        size = statFs2.getBlockSizeLong();
                    } else {
                        block = (long) statFs2.getAvailableBlocks();
                        size = (long) statFs2.getBlockSize();
                    }
                    statFs = statFs2;
                } catch (Exception e2) {
                    e = e2;
                    statFs = statFs2;
                    LogInfo.log("king", "========e:" + e.getMessage());
                    e.printStackTrace();
                    try {
                        block = (long) statFs.getAvailableBlocks();
                        size = (long) statFs.getBlockSize();
                    } catch (Throwable e1) {
                        e1.printStackTrace();
                    }
                    return size * block;
                }
            } catch (Exception e3) {
                e = e3;
                LogInfo.log("king", "========e:" + e.getMessage());
                e.printStackTrace();
                block = (long) statFs.getAvailableBlocks();
                size = (long) statFs.getBlockSize();
                return size * block;
            }
            return size * block;
        }

        @TargetApi(18)
        private static long getTotalSpace(String path) {
            long block;
            long size;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            StatFs statFs = null;
            try {
                StatFs statFs2 = new StatFs(path);
                try {
                    if (VERSION.SDK_INT >= 18) {
                        block = statFs2.getBlockCountLong();
                        size = statFs2.getBlockSizeLong();
                    } else {
                        block = (long) statFs2.getBlockCount();
                        size = (long) statFs2.getBlockSize();
                    }
                    statFs = statFs2;
                } catch (Exception e) {
                    e = e;
                    statFs = statFs2;
                    e.printStackTrace();
                    block = (long) statFs.getBlockCount();
                    size = (long) statFs.getBlockSize();
                    return size * block;
                }
            } catch (Exception e2) {
                Exception e3;
                e3 = e2;
                e3.printStackTrace();
                block = (long) statFs.getBlockCount();
                size = (long) statFs.getBlockSize();
                return size * block;
            }
            return size * block;
        }
    }

    public static FileDataStoreWorker getFileDataStoreWorker() {
        FileDataStoreWorker.instance.mContext = sContext;
        return FileDataStoreWorker.instance;
    }

    public static boolean isStoreMounted() {
        boolean exist = "mounted".equals(Environment.getExternalStorageState());
        L.v(TAG, "isSdcardAvailable isStoreMounted " + exist);
        return exist;
    }

    public static IStorePath getsIStorePath() {
        return sIStorePath;
    }

    @TargetApi(9)
    public static boolean isSdCardPull() {
        L.v(TAG, "isSdCardPull getSdCardStorePath : " + getSdCardStorePath());
        if (TextUtils.isEmpty(getSdCardStorePath())) {
            return false;
        }
        if (!new File(getSdCardStorePath()).exists() || new File(getSdCardStorePath()).getTotalSpace() == 0) {
            return true;
        }
        return false;
    }

    public static int getCurrentStoreLocation() {
        if (!isHasPhoneStore() && !isHasSdCardStore()) {
            return 0;
        }
        if (isCurrentPhoneStore()) {
            return 1;
        }
        if (isHasSdCardStore()) {
            return 2;
        }
        return 0;
    }

    public static boolean isHasSdCardStore() {
        return !TextUtils.isEmpty(sIStorePath.getSdCardPath());
    }

    public static boolean isHasPhoneStore() {
        return !TextUtils.isEmpty(sIStorePath.getPhoneStorePath());
    }

    protected static void saveSdCardStorePath(String path) {
        SharedPreferenceUtils.put(sContext, SDCARDPATH, "path", path);
    }

    public static String getSdCardStorePath() {
        return (String) SharedPreferenceUtils.get(sContext, SDCARDPATH, "path", "");
    }

    public static boolean isCurrentPhoneStore() {
        return ((Boolean) SharedPreferenceUtils.get(sContext, DOWNLOAD_DEVICE, Boolean.valueOf(false))).booleanValue();
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

    public static boolean isSdCardMount(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return new File(path).exists();
    }

    public static void setDownloadPath(String path, boolean isPhoneStore) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            SharedPreferenceUtils.put(sContext, DOWNLOAD_DEVICE, Boolean.valueOf(isPhoneStore));
            SharedPreferenceUtils.put(sContext, "download_location", path);
        }
    }

    public static String getDownloadPath() {
        String path = (String) SharedPreferenceUtils.get(sContext, "download_location", "");
        boolean isContain = false;
        if (!TextUtils.isEmpty(path)) {
            isContain = path.contains("Letv/storage/download");
            if (!isContain) {
                isContain = path.contains("letv/storage/download");
            }
        }
        L.v(TAG, "getDownloadPath isContain : " + isContain + " path : " + path);
        if (!TextUtils.isEmpty(path) && !isContain) {
            return path;
        }
        initCurrentStoreLocation();
        return (String) SharedPreferenceUtils.get(sContext, "download_location", "");
    }

    public static StoreDeviceInfo getPhoneStoreDeviceInfo() {
        if (isStoreMounted()) {
            String phoneStorePath = sIStorePath.getPhoneStorePath();
            if (TextUtils.isEmpty(phoneStorePath)) {
                return null;
            }
            String downloadPath = phoneStorePath + "/" + DOWNLOAD;
            File file = new File(phoneStorePath);
            if (!(file.exists() || file.mkdirs())) {
                downloadPath = phoneStorePath + "/" + DOWNLOAD2;
                new File(downloadPath).mkdirs();
            }
            if (!TextUtils.isEmpty(phoneStorePath)) {
                if (sPhoneStoreDeviceInfo == null) {
                    sPhoneStoreDeviceInfo = new StoreDeviceInfo(phoneStorePath, downloadPath, true);
                } else {
                    sPhoneStoreDeviceInfo.initStoreDeviceInfo(phoneStorePath, downloadPath, true, true);
                }
            }
            return sPhoneStoreDeviceInfo;
        }
        L.e(TAG, "getPhoneStoreDeviceInfo", " not phoneStore mounted ");
        StoreDeviceInfo storeDeviceInfo = new StoreDeviceInfo(false, true);
        sPhoneStoreDeviceInfo = storeDeviceInfo;
        return storeDeviceInfo;
    }

    public static StoreDeviceInfo getDefaultDownloadDeviceInfo() {
        boolean isPhoneStore = ((Boolean) SharedPreferenceUtils.get(sContext, DOWNLOAD_DEVICE, Boolean.valueOf(false))).booleanValue();
        if (TextUtils.isEmpty(getDownloadPath())) {
            return null;
        }
        if (isPhoneStore) {
            return getPhoneStoreDeviceInfo();
        }
        return getSdCardStoreDeviceInfo();
    }

    public static StoreDeviceInfo getSdCardStoreDeviceInfo() {
        String sdCardPath = sIStorePath.getSdCardPath();
        if (!TextUtils.isEmpty(sdCardPath) && !new File(sdCardPath).exists()) {
            L.e(TAG, "getSdCardStoreDeviceInfo", " not sdCardPath exists ");
            sSdCardDeviceInfo = new StoreDeviceInfo(false, false);
            return sSdCardDeviceInfo;
        } else if (TextUtils.isEmpty(sdCardPath)) {
            return null;
        } else {
            String downloadPath = sdCardPath + "/" + DOWNLOAD;
            File file = new File(downloadPath);
            if (!(file.exists() || file.mkdirs() || !new File(sdCardPath + "/" + DOWNLOAD2).mkdirs())) {
                downloadPath = sdCardPath + "/" + DOWNLOAD2;
            }
            if (!TextUtils.isEmpty(sdCardPath)) {
                if (sSdCardDeviceInfo == null) {
                    sSdCardDeviceInfo = new StoreDeviceInfo(sdCardPath, downloadPath, false);
                } else {
                    sSdCardDeviceInfo.initStoreDeviceInfo(sdCardPath, downloadPath, false, true);
                }
            }
            return sSdCardDeviceInfo;
        }
    }

    public static void initCurrentStoreLocation() {
        String phoneStorePath = sIStorePath.getPhoneStorePath();
        String phoneStorePath0;
        File file;
        if (TextUtils.isEmpty(phoneStorePath)) {
            String sdCardPath = sIStorePath.getSdCardPath();
            if (TextUtils.isEmpty(sdCardPath)) {
                phoneStorePath0 = isStoreMounted() ? getExternalStorage() : null;
                if (TextUtils.isEmpty(phoneStorePath0)) {
                    setDownloadPath("", false);
                    return;
                }
                String phoneStorePath1 = phoneStorePath0 + "/" + DOWNLOAD;
                file = new File(phoneStorePath1);
                if (!(file.exists() || file.mkdirs())) {
                    phoneStorePath1 = phoneStorePath0 + "/" + DOWNLOAD2;
                    new File(phoneStorePath1).mkdirs();
                }
                setDownloadPath(phoneStorePath1, true);
                return;
            }
            String sdCardPath1 = sdCardPath + "/" + DOWNLOAD;
            file = new File(sdCardPath1);
            if (!(file.exists() || file.mkdirs())) {
                sdCardPath1 = sdCardPath + "/" + DOWNLOAD2;
                new File(sdCardPath1).mkdirs();
            }
            setDownloadPath(sdCardPath1, false);
            return;
        }
        phoneStorePath0 = phoneStorePath + "/" + DOWNLOAD;
        file = new File(phoneStorePath0);
        if (!(file.exists() || file.mkdirs())) {
            phoneStorePath0 = phoneStorePath + "/" + DOWNLOAD2;
            new File(phoneStorePath0).mkdirs();
        }
        setDownloadPath(phoneStorePath0, true);
    }
}
