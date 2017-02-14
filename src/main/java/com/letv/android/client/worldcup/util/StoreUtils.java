package com.letv.android.client.worldcup.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import java.io.File;

public class StoreUtils {
    public static final long DEFAULT_SDCARD_SIZE = 10485760;

    public static boolean isSdcardAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static long getSdcardAvailableSpace(Context context) {
        Exception e;
        if (!isSdcardAvailable()) {
            return -1;
        }
        String sdcardPath = LetvServiceConfiguration.getDownload_path(context);
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

    public static long getSdcardTotalSpace(Context context) {
        Exception e;
        if (!isSdcardAvailable()) {
            return -1;
        }
        String sdcardPath = LetvServiceConfiguration.getDownload_path(context);
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
}
