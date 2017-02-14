package com.letv.android.client.worldcup.util;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;

public class DownloadUtil {
    public static File getDownloadFile(Context context, long episodeid, String path) {
        if (!StoreUtils.isSdcardAvailable()) {
            return null;
        }
        File downloadDir;
        if (TextUtils.isEmpty(path)) {
            downloadDir = new File(LetvServiceConfiguration.getDownload_path(context));
        } else {
            downloadDir = new File(path);
        }
        return new File(downloadDir, createFileName(episodeid));
    }

    public static String createFileName(long episodeid) {
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(episodeid)).toString().trim())).append(".mp4").toString();
    }

    public static File getDownloadDir(Context context) {
        File downloadDir = new File(LetvServiceConfiguration.getDownload_path(context));
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }

    public static File getCurrentDownloadFile(Context context, long episodeid) {
        if (!StoreUtils.isSdcardAvailable()) {
            return null;
        }
        File downloadDir = getDownloadDir(context);
        if (downloadDir != null) {
            return new File(downloadDir, createFileName(episodeid));
        }
        return null;
    }
}
