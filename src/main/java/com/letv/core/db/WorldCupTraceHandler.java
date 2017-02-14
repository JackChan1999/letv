package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.android.client.worldcup.util.Constants;
import com.letv.component.upgrade.core.service.DownloadManager;
import com.letv.core.bean.DownloadDBListBean;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.utils.DownloadUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StoreUtils;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class WorldCupTraceHandler {
    private Context context;

    public boolean isInFinish(java.lang.String r12) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
        /*
        r11 = this;
        r8 = 1;
        r9 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r11.context;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r1 = com.letv.core.db.LetvContentProvider.URI_WORLDCUPTRACE;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r2 = 0;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r3 = "episodeid=? AND finish=?";	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r4 = 2;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r5 = 0;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r4[r5] = r12;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r5 = 1;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r10 = "4";	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r4[r5] = r10;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r5 = 0;	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        r0 = r6.getCount();	 Catch:{ Exception -> 0x002c, all -> 0x0032 }
        if (r0 <= 0) goto L_0x002a;
    L_0x0025:
        r0 = r8;
    L_0x0026:
        com.letv.core.utils.LetvTools.closeCursor(r6);
    L_0x0029:
        return r0;
    L_0x002a:
        r0 = r9;
        goto L_0x0026;
    L_0x002c:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        r0 = r9;
        goto L_0x0029;
    L_0x0032:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.db.WorldCupTraceHandler.isInFinish(java.lang.String):boolean");
    }

    public WorldCupTraceHandler(Context context) {
        this.context = context;
    }

    public int getWorldCupTraceNumByStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish=?", new String[]{status + ""}, "timestamp ASC");
            int count = cursor.getCount();
            return count;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public int getWorldCupTraceNumExceptStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish!=?", new String[]{status + ""}, "timestamp ASC");
            int count = cursor.getCount();
            return count;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBListBean getLoadingDownloadInfo() {
        DownloadDBListBean list = new DownloadDBListBean();
        boolean flag = false;
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish<>?", new String[]{"4"}, "timestamp ASC");
            if (!PreferencesManager.getInstance().hasUpdateMulti()) {
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int episodeId = cursor.getInt(cursor.getColumnIndex("episodeid"));
                        if (!DownloadManager.getInstance(this.context).isDownloadExist(episodeId + "")) {
                            delete(episodeId);
                            flag = true;
                        }
                    }
                }
                PreferencesManager.getInstance().updateMulti();
            }
            if (flag) {
                cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish<>?", new String[]{"4"}, "timestamp ASC");
            } else {
                cursor.moveToPosition(-1);
            }
            while (cursor.moveToNext()) {
                list.add(createDownloadDBBean(cursor));
            }
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        } finally {
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
            }
        }
        return list;
    }

    public DownloadDBListBean getDownloadExceptFinishTrace() {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish!=?", new String[]{"4"}, "timestamp ASC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            while (cursor.moveToNext()) {
                arrayList.add(createDownloadDBBean(cursor));
            }
            return arrayList;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBListBean getAllFinishTrace() {
        Cursor cursor = null;
        ArrayList<Integer> deList = new ArrayList();
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish=?", new String[]{"4"}, "timestamp DESC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            while (cursor != null && cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = createDownloadDBBean(cursor);
                if (isFileExist(mDownloadDBBean.vid)) {
                    arrayList.add(mDownloadDBBean);
                    LogInfo.log("ljnalex", "checkFileExist exist");
                } else {
                    LogInfo.log("ljnalex", "checkFileExist not exist");
                    deList.add(Integer.valueOf(mDownloadDBBean.vid));
                }
            }
            for (int i = 0; i < deList.size(); i++) {
                delete(((Integer) deList.get(i)).intValue());
            }
            return arrayList;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public boolean isFileExist(int episodeId) {
        File file = getDownloadFileByEpisodeId((long) episodeId);
        return file != null && file.exists();
    }

    public File getDownloadFileByEpisodeId(long episodeid) {
        if (!StoreUtils.isSdcardAvailable()) {
            return null;
        }
        File downloadDir = null;
        DownloadDBBean info = getInFinish(episodeid);
        if (!(info == null || info.filePath == null)) {
            downloadDir = new File(info.filePath);
        }
        return new File(downloadDir, DownloadUtils.createFileName(episodeid));
    }

    public DownloadDBListBean getAllFinishTraceByAlbumId(String albumId) {
        DownloadDBListBean mDownloadDBBeanList = new DownloadDBListBean();
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "albumId=? AND finish=?", new String[]{albumId + "", "4"}, "ord ASC");
            while (cursor.moveToNext()) {
                mDownloadDBBeanList.add(createDownloadDBBean(cursor));
            }
            return mDownloadDBBeanList;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBListBean getAllDownLoadTrace() {
        try {
            Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, null, null, "timestamp ASC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            while (cursor != null && cursor.moveToNext()) {
                arrayList.add(createDownloadDBBean(cursor));
            }
            LetvTools.closeCursor(cursor);
            return arrayList;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    public DownloadDBBean getDownloadDBBeanPosition(int position) {
        DownloadDBBean mDownloadDBBean = null;
        try {
            Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "finish<>?", new String[]{"4"}, "timestamp ASC");
            if (cursor.move(position + 1)) {
                mDownloadDBBean = createDownloadDBBean(cursor);
            }
            LetvTools.closeCursor(cursor);
            return mDownloadDBBean;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    public DownloadDBBean has(long episodeId) {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "episodeid=?", new String[]{episodeId + ""}, null);
            if (cursor.moveToFirst()) {
                DownloadDBBean createDownloadDBBean = createDownloadDBBean(cursor);
                return createDownloadDBBean;
            }
            LetvTools.closeCursor(cursor);
            return null;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public List<Integer> getAllDownloadEpisodeId() {
        List<Integer> ids = new ArrayList();
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, new String[]{"episodeid"}, null, null, null);
            while (cursor.moveToNext()) {
                ids.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("episodeid"))));
            }
            return ids;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public List<Long> getAllDownloadAlbumId() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, new String[]{PageJumpUtil.IN_TO_ALBUM_PID}, "1 = 1 group by albumId", null, "timestamp ASC");
            List<Long> albumIds = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    albumIds.add(Long.valueOf(cursor.getLong(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID))));
                } catch (Throwable th2) {
                    th = th2;
                    List<Long> list = albumIds;
                }
            }
            LetvTools.closeCursor(cursor);
            return albumIds;
        } catch (Throwable th3) {
            th = th3;
            LetvTools.closeCursor(cursor);
            throw th;
        }
    }

    public LinkedHashMap<String, List<DownloadDBBean>> getAllDownLoadItems() {
        List<Long> albumIds = getAllDownloadAlbumId();
        DownloadDBListBean allDownLoadTrace = getAllDownLoadTrace();
        LinkedHashMap<String, List<DownloadDBBean>> mAllDownLoadItemsMap = new LinkedHashMap();
        if (albumIds != null && albumIds.size() > 0 && allDownLoadTrace != null && allDownLoadTrace.size() > 0) {
            for (Long albumId : albumIds) {
                LogInfo.log(Constants.DEBUG_TAG, "---albumId =" + albumId);
                List<DownloadDBBean> mDownloadDBBeanList = new ArrayList();
                Iterator it = allDownLoadTrace.iterator();
                while (it.hasNext()) {
                    DownloadDBBean downloadDBBean = (DownloadDBBean) it.next();
                    if (((long) downloadDBBean.aid) == albumId.longValue()) {
                        mDownloadDBBeanList.add(downloadDBBean);
                        LogInfo.log(Constants.DEBUG_TAG, "---albumId =" + albumId + " downloadDBBean=" + downloadDBBean.toString());
                    }
                }
                LogInfo.log(Constants.DEBUG_TAG, "---mDownloadDBBeanList.size()=" + mDownloadDBBeanList.size());
                if (mDownloadDBBeanList.size() > 0) {
                    mAllDownLoadItemsMap.put(albumId + "", mDownloadDBBeanList);
                }
            }
        }
        return mAllDownLoadItemsMap;
    }

    public DownloadDBBean getTitleInFinish(int albumId, float order) {
        DownloadDBBean mDownloadDBBean;
        synchronized (this.context) {
            mDownloadDBBean = null;
            try {
                Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "albumId=? AND ord=? AND finish=?", new String[]{albumId + "", order + "", "4"}, null);
                if (cursor.moveToFirst()) {
                    mDownloadDBBean = createDownloadDBBean(cursor);
                }
                LetvTools.closeCursor(cursor);
            } catch (Throwable th) {
                LetvTools.closeCursor(null);
            }
        }
        return mDownloadDBBean;
    }

    public DownloadDBBean getInFinish(long episodeId) {
        DownloadDBBean mDownloadDBBean;
        synchronized (this.context) {
            mDownloadDBBean = null;
            try {
                Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "episodeid=? AND finish=?", new String[]{episodeId + "", "4"}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    mDownloadDBBean = createDownloadDBBean(cursor);
                }
                LetvTools.closeCursor(cursor);
            } catch (Exception e) {
                e.printStackTrace();
                LetvTools.closeCursor(null);
                return null;
            } catch (Throwable th) {
                LetvTools.closeCursor(null);
            }
        }
        return mDownloadDBBean;
    }

    public DownloadDBBean getTitleInFinishByVid(int albumId, int vid) {
        DownloadDBBean mDownloadDBBean;
        synchronized (this.context) {
            mDownloadDBBean = null;
            try {
                Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "albumId=? AND episodeid=? AND finish=?", new String[]{albumId + "", vid + "", "4"}, null);
                if (cursor.moveToFirst()) {
                    mDownloadDBBean = createDownloadDBBean(cursor);
                }
                LetvTools.closeCursor(cursor);
            } catch (Throwable th) {
                LetvTools.closeCursor(null);
            }
        }
        return mDownloadDBBean;
    }

    public void delete(int episodeId) {
        this.context.getContentResolver().delete(LetvContentProvider.URI_WORLDCUPTRACE, "episodeid=?", new String[]{episodeId + ""});
    }

    public boolean finish(int episodeId, long totalSize) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(totalSize));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(totalSize));
            cv.put("finish", "4");
            this.context.getContentResolver().update(LetvContentProvider.URI_WORLDCUPTRACE, cv, "episodeid=?", new String[]{episodeId + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDownloadSize(String episodeId, long totalSize, long downLoadSize, int status) {
        try {
            ContentValues cv = new ContentValues();
            LogInfo.log(Constants.DEBUG_TAG, "status=" + status + "  updateDownloadSize episodeId =" + episodeId + "  totalSize=" + totalSize + "--downLoadSize=" + downLoadSize);
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(totalSize));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(downLoadSize));
            cv.put("finish", Integer.valueOf(status));
            this.context.getContentResolver().update(LetvContentProvider.URI_WORLDCUPTRACE, cv, "episodeid=?", new String[]{episodeId});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DownloadDBBean> getAllDownloadInfoWithAid(long albumId) {
        Cursor cursor = null;
        List<DownloadDBBean> list = new ArrayList();
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "albumId=?", new String[]{albumId + ""}, "timestamp ASC");
            while (cursor.moveToNext()) {
                list.add(createDownloadDBBean(cursor));
            }
            return list;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public boolean hasOldVersionItem(long albumId) {
        try {
            Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_WORLDCUPTRACE, null, "albumId=?", new String[]{albumId + ""}, null);
            boolean isNew = false;
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW)) == 0) {
                    isNew = true;
                    break;
                }
            }
            LetvTools.closeCursor(cursor);
            return isNew;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    private DownloadDBBean createDownloadDBBean(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        DownloadDBBean mDownloadDBBean = new DownloadDBBean();
        try {
            mDownloadDBBean.vid = cursor.getInt(cursor.getColumnIndex("episodeid"));
            mDownloadDBBean.aid = cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID));
            mDownloadDBBean.icon = cursor.getString(cursor.getColumnIndex(SettingsJsonConstants.APP_ICON_KEY));
            mDownloadDBBean.type = cursor.getInt(cursor.getColumnIndex("type"));
            mDownloadDBBean.ord = (float) cursor.getInt(cursor.getColumnIndex("ord"));
            mDownloadDBBean.cid = cursor.getInt(cursor.getColumnIndex("cid"));
            mDownloadDBBean.episodetitle = cursor.getString(cursor.getColumnIndex("episodetitle"));
            mDownloadDBBean.episodeIcon = cursor.getString(cursor.getColumnIndex("episodeicon"));
            mDownloadDBBean.albumtitle = cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE));
            mDownloadDBBean.totalsize = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_TOTALSIZE));
            mDownloadDBBean.finish = cursor.getInt(cursor.getColumnIndex("finish"));
            mDownloadDBBean.timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
            mDownloadDBBean.length = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_LENGTH));
            mDownloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
            mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
            mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
            mDownloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
            return mDownloadDBBean;
        } catch (Exception e) {
            e.printStackTrace();
            return mDownloadDBBean;
        }
    }
}
