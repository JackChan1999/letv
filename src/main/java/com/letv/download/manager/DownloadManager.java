package com.letv.download.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.DownloadDBListBean;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.DownloadUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.bean.DownloadAlbum;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.download.db.Download.ThreadInfoTable;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.manager.AddDownloadHandler.AddDownloadBean;
import com.letv.download.service.DownloadService;
import com.letv.download.service.IDownloadService;
import com.letv.download.util.L;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {
    public static final String COLUMN_AID = "aid";
    public static final String COLUMN_ALBUM_VIDEO_NUM = "albumVideoNum";
    public static final String COLUMN_ORD = "ord";
    public static final String COLUMN_SPEED = "speed";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_VID = "vid";
    public static final Uri DOWNLOAD_ALBUM_URI = DownloadAlbumTable.CONTENT_URI;
    public static final Uri DOWNLOAD_THREAD_URI = ThreadInfoTable.CONTENT_URI;
    public static final Uri DOWNLOAD_VIDEO_URI = DownloadVideoTable.CONTENT_URI;
    public static final int STATUS_FINISHED = 4;
    public static final int STATUS_NETWORK_ERROR = 6;
    public static final int STATUS_PAUSE = 3;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_SERVER_ERROR = 7;
    public static final int STATUS_STORE_ERROR = 8;
    public static final int STATUS_UNKNOW = 5;
    public static final int STATUS_WAIT = 0;
    private static final String TAG = DownloadManager.class.getSimpleName();
    private static Class<?> mTargetClass = null;
    static ExecutorService pool;
    private static Context sConext = BaseApplication.getInstance();
    private static DownloadServiceConnection sConnection = new DownloadServiceConnection();
    private static IDownloadService sIDownloadService;

    public static boolean isServiceConnection() {
        return sIDownloadService != null;
    }

    public static void stopDownloadService() {
        LogInfo.log("fornia", "MainAcitivityNotification stopDownloadService()()()  !!!");
        submitPool(new 1());
    }

    public static ArrayList<DownloadVideo> getDownloadVideoFinishByAid(long aid) {
        return DownloadDBDao.getInstance(sConext).getDownloadVideoFinishByAid(aid);
    }

    public static int getDownloadingVideoNum() {
        Cursor cursor = null;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_VIDEO_URI);
            query.setSelection("state != 4");
            cursor = query(query);
            int count = (cursor == null || cursor.getCount() <= 0) ? 0 : cursor.getCount();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return count;
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public static void updateDownloadAlbumWatchByAid(long aid) {
        DownloadDBDao.getInstance(sConext).updateDownloadAlbumWatchByAid(aid);
    }

    public static ArrayList<DownloadVideo> getAllDownloadVideo() {
        return DownloadDBDao.getInstance(sConext).getAllDownloadVideo();
    }

    public static ArrayList<DownloadVideo> getDownloadVideoByAid(long aid) {
        return DownloadDBDao.getInstance(sConext).getDownloadVideoByAid(aid);
    }

    public static DownloadVideo getDownloadFinishVideo(long vid) {
        Cursor cursor = null;
        DownloadVideo downloadVideo = null;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_VIDEO_URI);
            query.setSelection("vid = " + vid + " and " + "state" + " = " + 4);
            cursor = query(query);
            cursor.moveToFirst();
            downloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return downloadVideo;
    }

    public static DownloadVideo getDownloadVideoData(String vid) {
        Cursor cursor = null;
        DownloadVideo downloadVideo = null;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_VIDEO_URI);
            query.setSelection("vid = " + vid);
            cursor = query(query);
            cursor.moveToFirst();
            downloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return downloadVideo;
    }

    public static void deleteAllAlbum() {
        DownloadDBDao.getInstance(sConext).deleteAll(DOWNLOAD_ALBUM_URI);
    }

    public static void deleteAllVideo() {
        DownloadDBDao.getInstance(sConext).deleteAll(DOWNLOAD_VIDEO_URI);
    }

    public static boolean isHasDownloadRunning() {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_VIDEO_URI);
            query.setSelection("state == 1");
            cursor = query(query);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return isHas;
    }

    public static boolean isHasDownloadingData() {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_VIDEO_URI);
            query.setSelection("state != 4");
            cursor = query(query);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return isHas;
    }

    public static void deleteDownloadVideoed(long aid, long vid) {
        DownloadDBDao.getInstance(sConext).removeDownloadVideoed(vid, aid);
    }

    public static boolean isHasDownloadAlbumVideo(long aid) {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_VIDEO_URI);
            query.setSelection("aid = " + aid);
            cursor = query(query);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return isHas;
    }

    public static boolean isHasDownloadedData() {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setUri(DOWNLOAD_ALBUM_URI);
            query.setAlbumVideoNum(0);
            cursor = query(query);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return isHas;
    }

    public static boolean isHasDownloadInDB(String vid) {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            Query query = new Query(sConext.getContentResolver());
            query.setVid(vid);
            cursor = query(query);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return isHas;
    }

    public static DownloadVideo getDownloadVideo(Cursor cursor) {
        return DownloadVideoTable.cursorToDownloadVideo(cursor);
    }

    public static DownloadAlbum getDownloadAlbum(Cursor cursor) {
        return DownloadAlbumTable.cursorToDownloadAlbum(cursor);
    }

    public static void pauseDownload(long vid) {
        Intent intent = new Intent(DownloadService.ACTION_PAUSE_DOWNLOAD);
        intent.putExtra("vid", vid);
        intent.setClass(sConext, DownloadService.class);
        sConext.startService(intent);
    }

    public static void updateDownloadWatched(long aid, long vid) {
        DownloadDBDao.getInstance(sConext).updateDownloadVideoWatch(aid, vid, true);
    }

    public static void updateDownloadWatched(DownloadDBBean dbBean) {
        L.v(TAG, "updateDownloadWatched DownloadDBBean : " + dbBean);
        if (dbBean != null) {
            try {
                if (dbBean.isWatch == 0) {
                    DownloadDBDao.getInstance(sConext).updateDownloadVideoWatch((long) dbBean.aid, (long) dbBean.vid, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void startDownloadService() {
        submitPool(new 2());
    }

    private static void submitPool(Runnable task) {
        if (pool == null) {
            pool = Executors.newSingleThreadExecutor();
        }
        pool.submit(task);
    }

    public static void bindDownloadService() {
        if (sIDownloadService == null) {
            sConext.bindService(new Intent(sConext, DownloadService.class), sConnection, 1);
        }
    }

    public static boolean startDownloadService(Runnable run) {
        L.v("fornia", "startDownloadService", "MainAcitivityNotification startDownloadService run>" + run);
        submitPool(new 3(run));
        return true;
    }

    public static void sendMyDownloadClass(Class<?> target) {
        if (LetvUtils.checkClickEvent()) {
            mTargetClass = target;
            Intent intent = new Intent(sConext, DownloadService.class);
            intent.setAction(DownloadService.ACTION_INIT_NOTIFICAITON);
            intent.putExtra("class", mTargetClass);
            sConext.startService(intent);
        }
    }

    public static void unBindServiceConnection() {
        L.e("fornia", "startDownloadService", "MainAcitivityNotification unBindServiceConnection");
        try {
            if (sIDownloadService != null) {
                sConext.unbindService(sConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initDownloadingData() {
        if (LetvUtils.checkClickEvent(600)) {
            Intent intent = new Intent(DownloadService.ACTION_INIT_DOWNLOAD);
            intent.setClass(sConext, DownloadService.class);
            sConext.startService(intent);
        }
    }

    public static void resumeDownload(long vid) {
        submitPool(new 4(vid));
    }

    public static void pauseVipDownloadTask() {
        LogInfo.log(TAG, " pauseVipDownloadTask >>>>>>");
        Intent intent = new Intent(DownloadService.ACTION_PAUSE_VIP_DOWNLOAD);
        intent.setClass(sConext, DownloadService.class);
        sConext.startService(intent);
    }

    public static void synRemoveDownload(long vid) {
        L.v(TAG, "synRemoveDownload", " sIDownloadService : " + sIDownloadService + " vid : " + vid);
        if (sIDownloadService == null) {
            startDownloadService(new 5(vid));
            return;
        }
        try {
            sIDownloadService.synRemoveDownload(vid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void startAllDownload() {
        submitPool(new 6());
    }

    public static void pauseAllDownload() {
        submitPool(new 7());
    }

    public static void specialPauseAllDownload() {
        if (LetvUtils.checkClickEvent(600)) {
            L.v(TAG, "pauseAllDownload start ");
            Intent intent = new Intent(DownloadService.ACTION_PAUSE_ALL_DOWNLOAD);
            intent.setClass(sConext, DownloadService.class);
            intent.putExtra(DownloadService.IS_USER_PAUSE_ARG, false);
            sConext.startService(intent);
        }
    }

    public static void addDownload(Activity activity, AlbumInfo albumInfo, VideoBean video, boolean isDolby, boolean isShowToast, boolean isFullScreen, int stream, boolean isFromPlay, boolean isVideoNormal) {
        addDownload(activity, albumInfo, video, isDolby, isShowToast, isFullScreen, stream, isFromPlay, null, false, -1);
    }

    public static void addDownload(Activity activity, AlbumInfo albumInfo, VideoBean video, boolean isDolby, boolean isShowToast, boolean isFullScreen, int stream, boolean isFromPlay) {
        addDownload(activity, albumInfo, video, isDolby, isShowToast, isFullScreen, stream, isFromPlay, null, false, -1);
    }

    private static void addDownloadReport(AddDownloadBean addDownloadBean, int position) {
        DownloadVideo downloadVideo = addDownloadBean.mDownloadVideo;
        if (downloadVideo != null) {
            StatisticsUtils.statisticsActionInfo(sConext, PageIdConstant.downloadSelectionsPage, "0", addDownloadBean.mIsFullScreen ? "a133" : "92", downloadVideo.name, position, PreferencesManager.getInstance().isVip() ? "vip=1" : "vip=0", downloadVideo.cid + "", downloadVideo.pid + "", downloadVideo.vid + "", null, null);
            StatisticsUtils.statisticsActionInfo(sConext, PageIdConstant.downloadSelectionsPage, "2", null, downloadVideo.name, -1, "time=" + LetvUtils.timeClockString("yyyyMMdd_HH:mm:ss"), downloadVideo.cid + "", downloadVideo.pid + "", downloadVideo.vid + "", null, null);
        }
    }

    public static void addDownload(Activity activity, AlbumInfo albumInfo, VideoBean video, boolean isDolby, boolean isShowToast, boolean isFullScreen, int stream, boolean isFromPlay, Runnable onAddSuccessRun, boolean isFromRecomm, int position) {
        AddDownloadHandler.startDownload(activity, albumInfo, video, isDolby, isShowToast, isFullScreen, stream, isFromPlay, new 8(position, onAddSuccessRun), isFromRecomm);
    }

    private static void addDownload(DownloadVideo downloadVideo) {
        Intent intent = new Intent(DownloadService.ACTION_ADD_DOWNLOAD);
        intent.putExtra(DownloadService.DOWNLOAD_VIDEO_ARG, downloadVideo);
        intent.setClass(sConext, DownloadService.class);
        sConext.startService(intent);
    }

    public static Cursor query(Query query) {
        return query.runQuery(query.getContentResolver(), null);
    }

    public static void removeDownloaded(long vid) {
        Intent intent = new Intent(DownloadService.ACTION_REMOVE_DOWNLOAD);
        intent.putExtra("vid", vid);
        intent.setClass(sConext, DownloadService.class);
        sConext.startService(intent);
    }

    public static DownloadDBListBean getDownloadDBBeanByAid(long aid) {
        ArrayList<DownloadVideo> list = DownloadDBDao.getInstance(sConext).getDownloadVideoByAid(aid);
        DownloadDBListBean result = new DownloadDBListBean();
        if (BaseTypeUtils.isListEmpty(list)) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            DownloadVideo video = (DownloadVideo) it.next();
            if (video != null) {
                result.add(video.convertToDownloadDbBean());
            }
        }
        return result;
    }

    public static DownloadDBBean getLocalVideoBean(long vid) {
        DownloadDBBean bean = null;
        DownloadVideo video = getDownloadFinishVideo(vid);
        if (video != null) {
            bean = video.convertToDownloadDbBean();
        }
        File file;
        if (bean == null) {
            bean = DBManager.getInstance().getWorldCupTrace().getInFinish(vid);
            if (!(bean == null || bean.filePath == null)) {
                file = new File(new File(bean.filePath), DownloadUtils.createFileName(vid));
                if (file != null && file.exists()) {
                    bean.filePath = file.getAbsolutePath();
                }
            }
        } else {
            if (bean.isNew == 0) {
                file = DownloadUtils.getDownloadFile(bean.aid, bean.ord);
            } else {
                file = DownloadUtils.getDownloadFile((long) bean.vid);
            }
            if (file != null && file.exists()) {
                bean.filePath = file.getAbsolutePath();
            }
        }
        if (bean != null && bean.isWatch == 0) {
            DBManager.getInstance().getDownloadTrace().updateWatchStateByEpisodeid(1, (long) bean.vid);
        }
        return bean;
    }
}
