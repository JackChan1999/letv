package com.letv.download.manager;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.utils.LogInfo;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.util.L;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.util.ArrayList;

public class VideoFileManager {
    private static final String DOWNLOAD_EPISODE_INFO = "LetvDownload/storage/download/info";
    private static final String TAG = VideoFileManager.class.getSimpleName();
    private static Context sContext = BaseApplication.instance;

    public static void delDownloadedAlbum(long aid) {
        ArrayList<DownloadVideo> arrayVideo = DownloadDBDao.getInstance(sContext).getDownloadVideoFinishByAid(aid);
        if (arrayVideo == null) {
            DownloadDBDao.getInstance(sContext).deleteByID(DownloadAlbumTable.CONTENT_URI, "aid = " + aid, null);
            return;
        }
        for (int i = 0; i < arrayVideo.size(); i++) {
            DownloadVideo downloadVideo = (DownloadVideo) arrayVideo.get(i);
            delDownloadedVideo(downloadVideo.vid, downloadVideo.aid, downloadVideo.ord, downloadVideo.isNew ? 1 : 0, downloadVideo.filePath);
        }
    }

    public static void delDownloadedVideo(DownloadVideo downloadVideo) {
        delDownloadedVideo(downloadVideo.vid, downloadVideo.aid, downloadVideo.ord, downloadVideo.isNew ? 1 : 0, downloadVideo.filePath);
    }

    public static void delDownloadedVideo(long vid, long aid, int order, int isNew, String filePath) {
        File infoFile = getDownloadEpisodeInfoFile(aid, order);
        if (infoFile != null) {
            infoFile.delete();
        }
        File file = null;
        if (isNew == 1) {
            file = getDownloadFile(vid, filePath);
        } else if (isNew == 0) {
            file = getDownloadFile(aid, order, filePath);
        }
        if (file != null) {
            LogInfo.log(TAG, "delDownloadedVideo res " + file.delete() + " exists : " + file.exists() + " getAbsolutePath : " + file.getAbsolutePath());
        }
        DownloadDBDao.getInstance(sContext).removeDownloadVideoed(vid, aid);
    }

    public static void delDownloadingVideo(long vid) {
        DownloadManager.synRemoveDownload(vid);
    }

    private static File getDownloadFile(long vid, String filePath) {
        if (!StoreManager.isStoreMounted()) {
            return null;
        }
        File downloadDir;
        if (TextUtils.isEmpty(filePath)) {
            downloadDir = new File(Environment.getExternalStorageDirectory(), StoreManager.DOWNLOAD);
        } else {
            downloadDir = new File(filePath);
        }
        return new File(downloadDir, createFileName(vid));
    }

    public static File getVideoFileFile(String filePath, boolean isNew, long vid, long aid, int ord) {
        File file;
        if (isNew) {
            file = new File(filePath, createFileName(vid));
        } else {
            file = new File(filePath, createFileName(aid, (float) ord));
        }
        L.v(TAG, "getVideoFileFile getAbsolutePath : " + file.getAbsolutePath() + " exists : " + file.exists());
        return file;
    }

    public static boolean isVideoFileExists(DownloadVideo downloadVideo) {
        L.v(TAG, "isVideoFileExists downloadVideo state :" + downloadVideo.state);
        if (downloadVideo.state != 4) {
            return false;
        }
        return isVideoFileExists(downloadVideo.filePath, downloadVideo.isNew, downloadVideo.vid, downloadVideo.aid, downloadVideo.ord);
    }

    public static boolean isCompatVideoFileExists(DownloadVideo downloadVideo) {
        L.v(TAG, "isCompatVideoFileExists downloaded : " + downloadVideo.downloaded);
        if (downloadVideo.downloaded == 0) {
            return true;
        }
        boolean exist = isVideoFileExists(downloadVideo.filePath, downloadVideo.isNew, downloadVideo.vid, downloadVideo.aid, downloadVideo.ord);
        L.v(TAG, "isCompatVideoFileExists exist : " + exist);
        return exist;
    }

    public static boolean isVideoFileExists(String filePath, boolean isNew, long vid, long aid, int ord) {
        return getVideoFileFile(filePath, isNew, vid, aid, ord).exists();
    }

    public static String createFileName(long episodeid) {
        return (episodeid + "").trim() + ".mp4";
    }

    private static File getDownloadFile(long aid, int order, String filePath) {
        if (!StoreManager.isStoreMounted()) {
            return null;
        }
        File downloadDir;
        if (TextUtils.isEmpty(filePath)) {
            downloadDir = new File(Environment.getExternalStorageDirectory(), StoreManager.DOWNLOAD);
        } else {
            downloadDir = new File(filePath);
        }
        return new File(downloadDir, createFileName(aid, (float) order));
    }

    private static String createFileName(long albumId, float order) {
        return (albumId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + order).trim() + ".mp4";
    }

    private static File getDownloadEpisodeInfoFile(long aid, int order) {
        if (!StoreManager.isStoreMounted()) {
            return null;
        }
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), DOWNLOAD_EPISODE_INFO);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            return new File(dir, aid + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
