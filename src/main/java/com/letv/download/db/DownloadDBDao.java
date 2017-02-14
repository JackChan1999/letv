package com.letv.download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.core.utils.LogInfo;
import com.letv.download.bean.DownloadAlbum;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.bean.PartInfoBean;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.download.db.Download.ThreadInfoTable;
import com.letv.download.util.L;
import java.util.ArrayList;

public class DownloadDBDao extends BaseDownloadDao {
    private static final String TAG = DownloadDBDao.class.getSimpleName();
    private static DownloadDBDao sDownloadDBDao;

    private DownloadDBDao(Context context) {
        this.mContext = context;
    }

    public static DownloadDBDao getInstance(Context context) {
        if (sDownloadDBDao == null) {
            sDownloadDBDao = new DownloadDBDao(context);
        }
        return sDownloadDBDao;
    }

    public long insertPartInfo(long vid, PartInfoBean partInfo) {
        partInfo.vid = vid;
        LogInfo.log("fornia", "partInfo firstByte:" + partInfo.firstByte + "partInfo lastByte:" + partInfo.lastByte);
        return Long.parseLong((String) insert(ThreadInfoTable.CONTENT_URI, ThreadInfoTable.threadInfoToContentValues(partInfo)).getPathSegments().get(1));
    }

    public void updatePartInfo(long vid, PartInfoBean partInfo) {
        update(ThreadInfoTable.CONTENT_URI, ThreadInfoTable.threadInfoToContentValues(partInfo), "_id = " + partInfo.rowId);
    }

    public void addNewDownload(DownloadVideo downloadVideo) {
        L.v(TAG, "addNewDownload downloadVideo mDownloadAlbum : " + downloadVideo.mDownloadAlbum);
        insertDownloadVideo(downloadVideo);
        if (!isHasDownloadAlbum(downloadVideo.aid)) {
            insertDownloadAlbum(downloadVideo.mDownloadAlbum);
        }
    }

    private void insertDownloadVideo(DownloadVideo downloadVideo) {
        insert(DownloadVideoTable.CONTENT_URI, DownloadVideoTable.videoToContentValues(downloadVideo));
    }

    private void insertDownloadAlbum(DownloadAlbum downloadAlbum) {
        insert(DownloadAlbumTable.CONTENT_URI, DownloadAlbumTable.albumToContentValues(downloadAlbum));
    }

    private void updateDownloadAlbumByAid(long aid, DownloadAlbum downloadAlbum) {
        update(DownloadAlbumTable.CONTENT_URI, DownloadAlbumTable.albumToContentValues2(downloadAlbum), "aid = " + downloadAlbum.aid);
    }

    public DownloadAlbum getDownloadAlbumByAid(long aid) {
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadAlbumTable.CONTENT_URI, null, "aid = " + aid, null, null);
            if (cursor == null || cursor.getCount() <= 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return null;
            }
            cursor.moveToFirst();
            DownloadAlbum cursorToDownloadAlbum = DownloadAlbumTable.cursorToDownloadAlbum(cursor);
            return cursorToDownloadAlbum;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public boolean isHasDownloadAlbum(long aid) {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            cursor = queryByID(DownloadAlbumTable.CONTENT_URI, null, "aid = " + aid, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return isHas;
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public boolean isHasDownload(long vid, boolean isFinish) {
        String selection;
        Cursor cursor = null;
        boolean isHas = false;
        if (isFinish) {
            selection = "vid = " + vid + " and " + "state" + " = " + 4;
        } else {
            try {
                selection = "vid = " + vid;
            } catch (Throwable th) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
            }
        }
        cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, selection, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            isHas = true;
        }
        if (!(cursor == null || cursor.isClosed())) {
            cursor.close();
        }
        return isHas;
    }

    public ArrayList<DownloadAlbum> getDownloadAlbumed() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadAlbumTable.CONTENT_URI, null, "albumVideoNum != 0", null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return null;
            }
            ArrayList<DownloadAlbum> arrayList;
            ArrayList<DownloadAlbum> arrayAlbum = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    arrayAlbum.add(DownloadAlbumTable.cursorToDownloadAlbum(cursor));
                } catch (Throwable th2) {
                    th = th2;
                    arrayList = arrayAlbum;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            arrayList = arrayAlbum;
            return arrayAlbum;
        } catch (Throwable th3) {
            th = th3;
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            throw th;
        }
    }

    public boolean isHasPartInfo(long vid) {
        Cursor cursor = null;
        boolean isHas = false;
        try {
            cursor = queryByID(ThreadInfoTable.CONTENT_URI, null, "download_id = " + vid, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return isHas;
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public ArrayList<DownloadAlbum> getAllDownloadAlbum() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadAlbumTable.CONTENT_URI, null, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return null;
            }
            ArrayList<DownloadAlbum> arrayList;
            ArrayList<DownloadAlbum> arrayAlbum = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    arrayAlbum.add(DownloadAlbumTable.cursorToDownloadAlbum(cursor));
                } catch (Throwable th2) {
                    th = th2;
                    arrayList = arrayAlbum;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            arrayList = arrayAlbum;
            return arrayAlbum;
        } catch (Throwable th3) {
            th = th3;
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            throw th;
        }
    }

    public ArrayList<PartInfoBean> getAllPartInfo() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = queryByID(ThreadInfoTable.CONTENT_URI, null, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return null;
            }
            ArrayList<PartInfoBean> arrayList;
            ArrayList<PartInfoBean> arrayPartInfo = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    arrayPartInfo.add(ThreadInfoTable.cursorToPartInfo(cursor));
                } catch (Throwable th2) {
                    th = th2;
                    arrayList = arrayPartInfo;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            arrayList = arrayPartInfo;
            return arrayPartInfo;
        } catch (Throwable th3) {
            th = th3;
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            throw th;
        }
    }

    public ArrayList<PartInfoBean> getAllPartInfoByVid(long vid) {
        Throwable th;
        Cursor cursor = null;
        String selection = "";
        try {
            cursor = queryByID(ThreadInfoTable.CONTENT_URI, null, "download_id = " + vid, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return null;
            }
            ArrayList<PartInfoBean> arrayList;
            ArrayList<PartInfoBean> arrayPartInfo = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    arrayPartInfo.add(ThreadInfoTable.cursorToPartInfo(cursor));
                } catch (Throwable th2) {
                    th = th2;
                    arrayList = arrayPartInfo;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            arrayList = arrayPartInfo;
            return arrayPartInfo;
        } catch (Throwable th3) {
            th = th3;
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            throw th;
        }
    }

    public ArrayList<DownloadVideo> getAllDownloadVideo() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return null;
            }
            ArrayList<DownloadVideo> arrayList;
            ArrayList<DownloadVideo> arrayDownloadVideos = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    arrayDownloadVideos.add(DownloadVideoTable.cursorToDownloadVideo(cursor));
                } catch (Throwable th2) {
                    th = th2;
                    arrayList = arrayDownloadVideos;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            arrayList = arrayDownloadVideos;
            return arrayDownloadVideos;
        } catch (Throwable th3) {
            th = th3;
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            throw th;
        }
    }

    public ArrayList<DownloadVideo> getAllDownloadVideoed() {
        ArrayList<DownloadVideo> arrayDownloadVideos = null;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "state == 4", null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return arrayDownloadVideos;
            }
            arrayDownloadVideos = new ArrayList();
            while (cursor.moveToNext()) {
                DownloadVideo downloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
                downloadVideo.mDownloadAlbum = getDownloadAlbumByAid(downloadVideo.aid);
                arrayDownloadVideos.add(downloadVideo);
            }
            return arrayDownloadVideos;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public ArrayList<DownloadVideo> getAllDownloadVideoing() {
        ArrayList<DownloadVideo> arrayDownloadVideos = null;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "state != 4", null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return arrayDownloadVideos;
            }
            arrayDownloadVideos = new ArrayList();
            while (cursor.moveToNext()) {
                DownloadVideo downloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
                downloadVideo.mDownloadAlbum = getDownloadAlbumByAid(downloadVideo.aid);
                arrayDownloadVideos.add(downloadVideo);
            }
            return arrayDownloadVideos;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public DownloadVideo getDownloadVideoingByVid(long vid) {
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "vid = " + vid + " and " + "state" + " != " + 4, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                DownloadVideo cursorToDownloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
                return cursorToDownloadVideo;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return null;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public DownloadVideo getDownloadVideoByVid(long vid) {
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "vid = " + vid, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                DownloadVideo cursorToDownloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
                return cursorToDownloadVideo;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return null;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public DownloadVideo getDownloadVideoedByVid(long vid) {
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "vid = " + vid + " and " + "state" + " = " + 4, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                DownloadVideo cursorToDownloadVideo = DownloadVideoTable.cursorToDownloadVideo(cursor);
                return cursorToDownloadVideo;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return null;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public void removeDownloadVideoing(long vid, long aid) {
        deleteByID(DownloadVideoTable.CONTENT_URI, "vid = " + vid, null);
        removePartInfo(vid);
        if (getDownloadVideoByAid(aid) == null) {
            removeDownloadAlbumed(aid);
        }
    }

    public boolean isHasDownloadingByAid(long aid) {
        boolean isHas = false;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "aid = " + aid + " and " + "state" + " != " + 4, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                isHas = true;
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return isHas;
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public void removeDownloadVideoed(long vid, long aid) {
        long j = 0;
        LogInfo.log("removeDownloadVideoedremoveDownloadVideoed");
        DownloadVideo downloadVideo = getDownloadVideoedByVid(vid);
        DownloadAlbum downloadAlbum = getDownloadAlbumByAid(aid);
        if (downloadAlbum != null) {
            if (downloadAlbum.albumVideoNum - 1 <= 0) {
                LogInfo.log("removeDownloadVideoedremoveDownloadVideoeddownloadAlbum.albumVideoNum - 1 <= 0");
                String where = "aid = " + aid;
                if (isHasDownloadingByAid(aid)) {
                    downloadAlbum.albumVideoNum = 0;
                    downloadAlbum.albumTotalSize = 0;
                    downloadAlbum.isWatch = false;
                    updateDownloadAlbumByAid(downloadAlbum.aid, downloadAlbum);
                } else {
                    deleteByID(DownloadAlbumTable.CONTENT_URI, where, null);
                }
                deleteByID(DownloadVideoTable.CONTENT_URI, "vid = " + vid, null);
                return;
            }
            LogInfo.log("removeDownloadVideoedremoveDownloadVideoeddownloadAlbum.albumVideoNum - 1 > 0");
            downloadAlbum.albumVideoNum--;
            long j2 = downloadAlbum.albumTotalSize;
            if (downloadVideo != null) {
                j = downloadVideo.totalsize;
            }
            downloadAlbum.albumTotalSize = j2 - j;
            updateDownloadAlbumByAid(downloadAlbum.aid, downloadAlbum);
            deleteByID(DownloadVideoTable.CONTENT_URI, "vid = " + vid, null);
        }
    }

    private void removePartInfo(long vid) {
        deleteByID(ThreadInfoTable.CONTENT_URI, "download_id = " + vid, null);
    }

    public void removeAllPartInfo(DownloadVideo downloadVideo) {
        if (downloadVideo.mParts != null && downloadVideo.mParts.length > 0) {
            for (PartInfoBean partInfo : downloadVideo.mParts) {
                if (partInfo != null) {
                    removePartInfo(partInfo.vid);
                }
            }
        }
    }

    public void removeDownloadAlbumed(long aid) {
        deleteByID(DownloadAlbumTable.CONTENT_URI, "aid = " + aid, null);
        deleteByID(DownloadVideoTable.CONTENT_URI, "aid = " + aid, null);
    }

    public ArrayList<DownloadVideo> getDownloadVideoFinishByAid(long aid) {
        ArrayList<DownloadVideo> arrayDownloadVideos = null;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "aid = " + aid + " and " + "state" + " = " + 4, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return arrayDownloadVideos;
            }
            arrayDownloadVideos = new ArrayList();
            while (cursor.moveToNext()) {
                arrayDownloadVideos.add(DownloadVideoTable.cursorToDownloadVideo(cursor));
            }
            return arrayDownloadVideos;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public ArrayList<DownloadVideo> getDownloadVideoByAid(long aid) {
        ArrayList<DownloadVideo> arrayDownloadVideos = null;
        Cursor cursor = null;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "aid = " + aid, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
                return arrayDownloadVideos;
            }
            arrayDownloadVideos = new ArrayList();
            while (cursor.moveToNext()) {
                arrayDownloadVideos.add(DownloadVideoTable.cursorToDownloadVideo(cursor));
            }
            return arrayDownloadVideos;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public void updateDownloadVideoWatch(long aid, long vid, boolean isWatch) {
        int i = 1;
        String where = "vid = " + vid;
        ContentValues cv = new ContentValues();
        String str = "isWatch";
        if (!isWatch) {
            i = 0;
        }
        cv.put(str, Integer.valueOf(i));
        update(DownloadVideoTable.CONTENT_URI, cv, where);
        updateDownloadAlbumWatchByAid(aid);
    }

    public void updateDownloadAlbumWatchByAid(long aid) {
        ArrayList<DownloadVideo> arrayDownloadVideos = getDownloadVideoFinishByAid(aid);
        if (arrayDownloadVideos != null) {
            boolean isNotWatch = false;
            for (int i = 0; i < arrayDownloadVideos.size(); i++) {
                if (!((DownloadVideo) arrayDownloadVideos.get(i)).isWatch) {
                    isNotWatch = true;
                }
            }
            if (!isNotWatch) {
                String where = "aid = " + aid;
                ContentValues cv = new ContentValues();
                cv.put("isWatch", Integer.valueOf(1));
                update(DownloadAlbumTable.CONTENT_URI, cv, where);
            }
        }
    }

    public int getDownloadVideoCountByAid(long aid) {
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "aid = " + aid + " and " + "state" + " = " + 4, null, null);
            if (!(cursor == null || cursor.getCount() == 0)) {
                count = cursor.getCount();
            }
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

    public long getDownloadVideoTotalSizeByAid(long aid) {
        Cursor cursor = null;
        long totalSize = 0;
        try {
            cursor = queryByID(DownloadVideoTable.CONTENT_URI, null, "aid = " + aid + " and " + "state" + " = " + 4, null, null);
            if (!(cursor == null || cursor.getCount() == 0)) {
                while (cursor.moveToNext()) {
                    totalSize += DownloadVideoTable.cursorToDownloadVideo(cursor).totalsize;
                }
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
            return totalSize;
        } catch (Throwable th) {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    public void addDownloadAlbum(long aid, DownloadAlbum downloadAlbum) {
        LogInfo.log(TAG, "addDownloadAlbum aid : " + aid + "  downloadAlbum : " + downloadAlbum);
        if (!isHasDownloadAlbum(aid) && downloadAlbum != null) {
            L.v(TAG, "addDownloadAlbum  insert album");
            insertDownloadAlbum(downloadAlbum);
        }
    }

    public void updateDownloadVideoByVid(DownloadVideo downloadVideo) {
        update(DownloadVideoTable.CONTENT_URI, DownloadVideoTable.videoToContentValues(downloadVideo), "vid = " + downloadVideo.vid);
        if (downloadVideo.mParts != null && downloadVideo.mParts.length > 0) {
            for (PartInfoBean partInfo : downloadVideo.mParts) {
                if (partInfo != null) {
                    updatePartInfo(downloadVideo.vid, partInfo);
                }
            }
        }
        if (downloadVideo.state == 4) {
            LogInfo.log(TAG, "updateDownloadVideoByVid finish>>");
            if (!(isHasDownloadAlbum(downloadVideo.aid) || downloadVideo.mDownloadAlbum == null)) {
                L.v(TAG, "updateDownloadVideoByVid again insert album");
                insertDownloadAlbum(downloadVideo.mDownloadAlbum);
            }
            removePartInfo(downloadVideo.vid);
            DownloadAlbum downloadAlbum = getDownloadAlbumByAid(downloadVideo.aid);
            if (downloadAlbum != null) {
                downloadAlbum.albumVideoNum = getDownloadVideoCountByAid(downloadVideo.aid);
                downloadAlbum.albumTotalSize = getDownloadVideoTotalSizeByAid(downloadVideo.aid);
                downloadAlbum.timestamp = downloadVideo.timestamp;
                downloadAlbum.isWatch = false;
                updateDownloadAlbumByAid(downloadVideo.aid, downloadAlbum);
            }
        }
    }
}
