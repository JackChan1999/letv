package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.letv.core.bean.DownloadDBListBean;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.constant.DownloadConstant;
import com.letv.core.utils.DownloadUtils;
import com.letv.core.utils.LetvTools;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executors;

public class DownloadTraceHandler {
    AsyDataBaseHandler asyDataBaseHandler;
    private Context mContext;

    public boolean isInFinish(java.lang.String r11) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
        /*
        r10 = this;
        r8 = 0;
        r6 = 0;
        r7 = 0;
        r0 = r10.mContext;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r1 = com.letv.core.db.LetvContentProvider.URI_DOWNLOADTRACE;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r2 = 1;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r3 = 0;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r4 = "episodetitle";	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r3 = "episodeid=? AND finish=?";	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r4 = 2;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r5 = 0;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r4[r5] = r11;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r5 = 1;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r9 = "4";	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r4[r5] = r9;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r5 = 0;	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        r0 = r6.getCount();	 Catch:{ Exception -> 0x0030, all -> 0x0036 }
        if (r0 <= 0) goto L_0x002c;
    L_0x002b:
        r7 = 1;
    L_0x002c:
        com.letv.core.utils.LetvTools.closeCursor(r6);
    L_0x002f:
        return r7;
    L_0x0030:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        r7 = r8;
        goto L_0x002f;
    L_0x0036:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.db.DownloadTraceHandler.isInFinish(java.lang.String):boolean");
    }

    public DownloadTraceHandler(Context context) {
        this.mContext = context;
    }

    public boolean saveDownloadTrace(DownloadDBBean bean) {
        if (has((long) bean.vid) != null) {
            return updateByEpisodeId(bean);
        }
        ContentValues cv = new ContentValues();
        cv.put(DownloadVideoTable.COLUMN_DURATION, Long.valueOf(bean.duration));
        cv.put("episodeid", Integer.valueOf(bean.vid));
        cv.put(PageJumpUtil.IN_TO_ALBUM_PID, Integer.valueOf(bean.aid));
        cv.put(SettingsJsonConstants.APP_ICON_KEY, bean.icon);
        cv.put("type", Integer.valueOf(bean.type));
        cv.put("ord", Float.valueOf(bean.ord));
        cv.put("cid", Integer.valueOf(bean.cid));
        cv.put("episodetitle", bean.episodetitle);
        cv.put("episodeicon", bean.episodeIcon);
        cv.put(DownloadAlbumTable.COLUMN_ALBUMTITLE, bean.albumtitle);
        cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(bean.totalsize));
        cv.put("finish", Integer.valueOf(bean.finish));
        cv.put("timestamp", Long.valueOf(System.currentTimeMillis()));
        cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(bean.length));
        cv.put("file_path", TextUtils.isEmpty(bean.filePath) ? DownloadConstant.DOWNLOAD_LOCATION_DIR : bean.filePath);
        cv.put("isHd", Integer.valueOf(bean.isHd));
        cv.put(DownloadVideoTable.COLUMN_ISNEW, Integer.valueOf(bean.isNew));
        cv.put(DownloadVideoTable.COLUMN_BTIME, Long.valueOf(bean.btime));
        cv.put(DownloadVideoTable.COLUMN_ETIME, Long.valueOf(bean.etime));
        cv.put("isWatch", Integer.valueOf(bean.isWatch));
        cv.put(DownloadVideoTable.COLUMN_DURATION, Long.valueOf(bean.duration));
        this.mContext.getContentResolver().insert(LetvContentProvider.URI_DOWNLOADTRACE, cv);
        return true;
    }

    private boolean updateByEpisodeId(DownloadDBBean mDownloadDBBean) {
        if (mDownloadDBBean == null) {
            return false;
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put("episodeid", Integer.valueOf(mDownloadDBBean.vid));
            cv.put(PageJumpUtil.IN_TO_ALBUM_PID, Integer.valueOf(mDownloadDBBean.aid));
            cv.put(SettingsJsonConstants.APP_ICON_KEY, mDownloadDBBean.icon);
            cv.put("type", Integer.valueOf(mDownloadDBBean.type));
            cv.put("ord", Float.valueOf(mDownloadDBBean.ord));
            cv.put("cid", Integer.valueOf(mDownloadDBBean.cid));
            cv.put("episodetitle", mDownloadDBBean.episodetitle);
            cv.put("episodeicon", mDownloadDBBean.episodeIcon);
            cv.put(DownloadAlbumTable.COLUMN_ALBUMTITLE, mDownloadDBBean.albumtitle);
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(mDownloadDBBean.totalsize));
            cv.put("finish", Integer.valueOf(mDownloadDBBean.finish));
            cv.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(mDownloadDBBean.length));
            cv.put("file_path", TextUtils.isEmpty(mDownloadDBBean.filePath) ? DownloadConstant.DOWNLOAD_LOCATION_DIR : mDownloadDBBean.filePath);
            cv.put("isHd", Integer.valueOf(mDownloadDBBean.isHd));
            cv.put(DownloadVideoTable.COLUMN_ISNEW, Integer.valueOf(mDownloadDBBean.isNew));
            cv.put(DownloadVideoTable.COLUMN_BTIME, Long.valueOf(mDownloadDBBean.btime));
            cv.put(DownloadVideoTable.COLUMN_ETIME, Long.valueOf(mDownloadDBBean.etime));
            cv.put("isWatch", Integer.valueOf(mDownloadDBBean.isWatch));
            this.mContext.getContentResolver().update(LetvContentProvider.URI_DOWNLOADTRACE, cv, "episodeid=?", new String[]{mDownloadDBBean.vid + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateStateByEpisodeId(DownloadDBBean mDownloadDBBean) {
        if (mDownloadDBBean == null) {
            return false;
        }
        try {
            ContentValues cv = new ContentValues();
            cv.put("episodeid", Integer.valueOf(mDownloadDBBean.vid));
            cv.put(PageJumpUtil.IN_TO_ALBUM_PID, Integer.valueOf(mDownloadDBBean.aid));
            cv.put(SettingsJsonConstants.APP_ICON_KEY, mDownloadDBBean.icon);
            cv.put("type", Integer.valueOf(mDownloadDBBean.type));
            cv.put("ord", Float.valueOf(mDownloadDBBean.ord));
            cv.put("cid", Integer.valueOf(mDownloadDBBean.cid));
            cv.put("episodetitle", mDownloadDBBean.episodetitle);
            cv.put("episodeicon", mDownloadDBBean.episodeIcon);
            cv.put(DownloadAlbumTable.COLUMN_ALBUMTITLE, mDownloadDBBean.albumtitle);
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(mDownloadDBBean.totalsize));
            cv.put("finish", Integer.valueOf(mDownloadDBBean.finish));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(mDownloadDBBean.length));
            cv.put("file_path", TextUtils.isEmpty(mDownloadDBBean.filePath) ? DownloadConstant.DOWNLOAD_LOCATION_DIR : mDownloadDBBean.filePath);
            cv.put("isHd", Integer.valueOf(mDownloadDBBean.isHd));
            cv.put(DownloadVideoTable.COLUMN_ISNEW, Integer.valueOf(mDownloadDBBean.isNew));
            cv.put(DownloadVideoTable.COLUMN_BTIME, Long.valueOf(mDownloadDBBean.btime));
            cv.put(DownloadVideoTable.COLUMN_ETIME, Long.valueOf(mDownloadDBBean.etime));
            cv.put("isWatch", Integer.valueOf(mDownloadDBBean.isWatch));
            this.mContext.getContentResolver().update(LetvContentProvider.URI_DOWNLOADTRACE, cv, "episodeid=?", new String[]{mDownloadDBBean.vid + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getDownloadTraceNumByStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish=?", new String[]{status + ""}, "timestamp ASC");
            int count = cursor.getCount();
            return count;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBListBean getDownloadTraceByStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish=?", new String[]{status + ""}, "timestamp ASC");
            DownloadDBListBean list = new DownloadDBListBean();
            while (cursor.moveToNext()) {
                DownloadDBBean bean = new DownloadDBBean();
                bean.vid = cursor.getInt(cursor.getColumnIndex("episodeid"));
                bean.aid = cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID));
                bean.icon = cursor.getString(cursor.getColumnIndex(SettingsJsonConstants.APP_ICON_KEY));
                bean.type = cursor.getInt(cursor.getColumnIndex("type"));
                bean.ord = (float) cursor.getInt(cursor.getColumnIndex("ord"));
                bean.cid = cursor.getInt(cursor.getColumnIndex("cid"));
                bean.episodetitle = cursor.getString(cursor.getColumnIndex("episodetitle"));
                bean.episodeIcon = cursor.getString(cursor.getColumnIndex("episodeicon"));
                bean.albumtitle = cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE));
                bean.totalsize = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_TOTALSIZE));
                bean.finish = cursor.getInt(cursor.getColumnIndex("finish"));
                bean.timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                bean.length = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_LENGTH));
                bean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                bean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                bean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                bean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                bean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                list.add(bean);
            }
            return list;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public int getDownloadTraceNumExceptStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish!=?", new String[]{status + ""}, "timestamp ASC");
            int count = cursor.getCount();
            return count;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBBean getCurrentDownloadingDBBean() {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish==", new String[]{"1"}, "timestamp ASC");
            DownloadDBBean downloadDBBean = null;
            while (cursor.moveToNext()) {
                downloadDBBean = new DownloadDBBean();
                downloadDBBean.vid = cursor.getInt(cursor.getColumnIndex("episodeid"));
                downloadDBBean.aid = cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID));
                downloadDBBean.icon = cursor.getString(cursor.getColumnIndex(SettingsJsonConstants.APP_ICON_KEY));
                downloadDBBean.type = cursor.getInt(cursor.getColumnIndex("type"));
                downloadDBBean.ord = (float) cursor.getInt(cursor.getColumnIndex("ord"));
                downloadDBBean.cid = cursor.getInt(cursor.getColumnIndex("cid"));
                downloadDBBean.episodetitle = cursor.getString(cursor.getColumnIndex("episodetitle"));
                downloadDBBean.episodeIcon = cursor.getString(cursor.getColumnIndex("episodeicon"));
                downloadDBBean.albumtitle = cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE));
                downloadDBBean.totalsize = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_TOTALSIZE));
                downloadDBBean.finish = cursor.getInt(cursor.getColumnIndex("finish"));
                downloadDBBean.timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                downloadDBBean.length = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_LENGTH));
                downloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                downloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                downloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                downloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                downloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                downloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
            }
            return downloadDBBean;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public boolean isDownloading() {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish= ?", new String[]{"1"}, "timestamp ASC");
            if (cursor == null) {
                return false;
            }
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public DownloadDBListBean getLoadingDownloadInfo() {
        DownloadDBListBean list = new DownloadDBListBean();
        Cursor cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish<>?", new String[]{"4"}, "timestamp ASC");
        if (cursor != null) {
            if (!PreferencesManager.getInstance().hasUpdateMulti()) {
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        cursor.getInt(cursor.getColumnIndex("episodeid"));
                    }
                }
                PreferencesManager.getInstance().updateMulti();
            }
            if (false) {
                cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish<>?", new String[]{"4"}, "timestamp ASC");
            } else {
                cursor.moveToPosition(-1);
            }
            while (cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
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
                mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                list.add(mDownloadDBBean);
            }
            cursor.close();
        }
        return list;
    }

    public DownloadDBListBean getDownloadExceptFinishTrace() {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish!=?", new String[]{"4"}, "timestamp ASC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            while (cursor != null && cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
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
                mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                arrayList.add(mDownloadDBBean);
            }
            LetvTools.closeCursor(cursor);
            return arrayList;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    public DownloadDBListBean getAllFinishTrace() {
        ArrayList<Integer> deList = new ArrayList();
        try {
            Cursor cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish=?", new String[]{"4"}, "timestamp DESC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            while (cursor != null && cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
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
                mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                mDownloadDBBean.isWatch = cursor.getInt(cursor.getColumnIndex("isWatch"));
                mDownloadDBBean.duration = (long) cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                arrayList.add(mDownloadDBBean);
            }
            LetvTools.closeCursor(cursor);
            return arrayList;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    public DownloadDBListBean getAllFinishLatestThreeDownLoadTrace() {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish=?", new String[]{"4"}, "timestamp DESC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            ArrayList<Integer> albumIdList = new ArrayList();
            int count = 0;
            while (cursor.moveToNext() && count < 3) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
                int albumId = cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID));
                mDownloadDBBean.aid = albumId;
                mDownloadDBBean.episodetitle = cursor.getString(cursor.getColumnIndex("episodetitle"));
                mDownloadDBBean.albumtitle = cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE));
                if (!albumIdList.contains(Integer.valueOf(albumId))) {
                    albumIdList.add(Integer.valueOf(albumId));
                    arrayList.add(mDownloadDBBean);
                    count++;
                }
            }
            LetvTools.closeCursor(cursor);
            return arrayList;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    public boolean checkFileExist(int albumId, int episodeId, float ordId, boolean isNew) {
        File file;
        if (isNew) {
            file = DownloadUtils.getDownloadFile((long) episodeId);
        } else {
            file = DownloadUtils.getDownloadFile(albumId, ordId);
        }
        return file != null && file.exists();
    }

    public LinkedHashMap<String, DownloadDBListBean> getAllFinishTraceByAlbumId() {
        DownloadDBListBean allDownLoadTrace = getAllFinishTrace();
        return new LinkedHashMap();
    }

    public DownloadDBListBean getAllFinishTraceByAlbumId(String albumId) {
        DownloadDBListBean mDownloadDBBeanList = new DownloadDBListBean();
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "albumId=? AND finish=?", new String[]{albumId + "", "4"}, "ord ASC");
            while (cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
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
                mDownloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                mDownloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                mDownloadDBBean.isWatch = cursor.getInt(cursor.getColumnIndex("isWatch"));
                mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                mDownloadDBBeanList.add(mDownloadDBBean);
            }
            return mDownloadDBBeanList;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBListBean getAllDownLoadTrace() {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, null, null, "timestamp ASC");
            DownloadDBListBean arrayList = new DownloadDBListBean();
            while (cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
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
                mDownloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                mDownloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                arrayList.add(mDownloadDBBean);
            }
            return arrayList;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public DownloadDBBean getDownloadDBBeanPosition(int position) {
        Throwable th;
        Cursor cursor = null;
        DownloadDBBean mDownloadDBBean = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "finish<>?", new String[]{"4"}, "timestamp ASC");
            if (cursor.move(position + 1)) {
                DownloadDBBean mDownloadDBBean2 = new DownloadDBBean();
                try {
                    mDownloadDBBean2.vid = cursor.getInt(cursor.getColumnIndex("episodeid"));
                    mDownloadDBBean2.aid = cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID));
                    mDownloadDBBean2.icon = cursor.getString(cursor.getColumnIndex(SettingsJsonConstants.APP_ICON_KEY));
                    mDownloadDBBean2.type = cursor.getInt(cursor.getColumnIndex("type"));
                    mDownloadDBBean2.ord = (float) cursor.getInt(cursor.getColumnIndex("ord"));
                    mDownloadDBBean2.cid = cursor.getInt(cursor.getColumnIndex("cid"));
                    mDownloadDBBean2.episodetitle = cursor.getString(cursor.getColumnIndex("episodetitle"));
                    mDownloadDBBean2.episodeIcon = cursor.getString(cursor.getColumnIndex("episodeicon"));
                    mDownloadDBBean2.albumtitle = cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE));
                    mDownloadDBBean2.totalsize = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_TOTALSIZE));
                    mDownloadDBBean2.finish = cursor.getInt(cursor.getColumnIndex("finish"));
                    mDownloadDBBean2.timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                    mDownloadDBBean2.length = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_LENGTH));
                    mDownloadDBBean2.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                    mDownloadDBBean2.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                    mDownloadDBBean2.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                    mDownloadDBBean2.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                    mDownloadDBBean2.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                    mDownloadDBBean = mDownloadDBBean2;
                } catch (Throwable th2) {
                    th = th2;
                    mDownloadDBBean = mDownloadDBBean2;
                    LetvTools.closeCursor(cursor);
                    throw th;
                }
            }
            LetvTools.closeCursor(cursor);
            return mDownloadDBBean;
        } catch (Throwable th3) {
            th = th3;
            LetvTools.closeCursor(cursor);
            throw th;
        }
    }

    public DownloadDBBean has(long episodeId) {
        DownloadDBBean downloadDBBean;
        Throwable th;
        DownloadDBBean mDownloadDBBean = null;
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "episodeid=?", new String[]{episodeId + ""}, null);
            if (cursor.moveToFirst()) {
                mDownloadDBBean = new DownloadDBBean();
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
                    mDownloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                    mDownloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                    mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                    mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                    mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                    mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                    LetvTools.closeCursor(cursor);
                    downloadDBBean = mDownloadDBBean;
                } catch (Throwable th2) {
                    th = th2;
                    downloadDBBean = mDownloadDBBean;
                    LetvTools.closeCursor(cursor);
                    throw th;
                }
            }
            LetvTools.closeCursor(cursor);
            return mDownloadDBBean;
        } catch (Throwable th3) {
            th = th3;
            LetvTools.closeCursor(cursor);
            throw th;
        }
    }

    public List<Integer> getAllDownloadEpisodeId() {
        List<Integer> ids = new ArrayList();
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, new String[]{"episodeid"}, null, null, null);
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
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, new String[]{PageJumpUtil.IN_TO_ALBUM_PID}, "1 = 1 group by albumId", null, "timestamp ASC");
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
                List<DownloadDBBean> mDownloadDBBeanList = new ArrayList();
                Iterator it = allDownLoadTrace.iterator();
                while (it.hasNext()) {
                    DownloadDBBean downloadDBBean = (DownloadDBBean) it.next();
                    if (((long) downloadDBBean.aid) == albumId.longValue()) {
                        mDownloadDBBeanList.add(downloadDBBean);
                    }
                }
                if (mDownloadDBBeanList.size() > 0) {
                    mAllDownLoadItemsMap.put(albumId + "", mDownloadDBBeanList);
                }
            }
        }
        return mAllDownLoadItemsMap;
    }

    public synchronized DownloadDBBean getTitleInFinish(long episodeId) {
        DownloadDBBean downloadDBBean;
        Throwable th;
        Cursor cursor = null;
        downloadDBBean = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "episodeid=? AND finish=?", new String[]{episodeId + "", "4"}, null);
            if (cursor != null && cursor.moveToFirst()) {
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
                    mDownloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                    mDownloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                    mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                    mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                    mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                    mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                    downloadDBBean = mDownloadDBBean;
                } catch (Exception e) {
                    downloadDBBean = mDownloadDBBean;
                    LetvTools.closeCursor(cursor);
                    downloadDBBean = null;
                    return downloadDBBean;
                } catch (Throwable th2) {
                    th = th2;
                    downloadDBBean = mDownloadDBBean;
                    LetvTools.closeCursor(cursor);
                    throw th;
                }
            }
            LetvTools.closeCursor(cursor);
        } catch (Exception e2) {
            LetvTools.closeCursor(cursor);
            downloadDBBean = null;
            return downloadDBBean;
        } catch (Throwable th3) {
            th = th3;
            LetvTools.closeCursor(cursor);
            throw th;
        }
        return downloadDBBean;
    }

    public DownloadDBBean getTitleInFinishByVid(int albumId, int vid) {
        DownloadDBBean mDownloadDBBean;
        synchronized (this.mContext) {
            Cursor cursor = null;
            mDownloadDBBean = null;
            try {
                cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "albumId=? AND episodeid=? AND finish=?", new String[]{albumId + "", vid + "", "4"}, null);
                if (cursor.moveToFirst()) {
                    DownloadDBBean mDownloadDBBean2 = new DownloadDBBean();
                    try {
                        mDownloadDBBean2.vid = cursor.getInt(cursor.getColumnIndex("episodeid"));
                        mDownloadDBBean2.aid = cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID));
                        mDownloadDBBean2.icon = cursor.getString(cursor.getColumnIndex(SettingsJsonConstants.APP_ICON_KEY));
                        mDownloadDBBean2.type = cursor.getInt(cursor.getColumnIndex("type"));
                        mDownloadDBBean2.ord = (float) cursor.getInt(cursor.getColumnIndex("ord"));
                        mDownloadDBBean2.cid = cursor.getInt(cursor.getColumnIndex("cid"));
                        mDownloadDBBean2.episodetitle = cursor.getString(cursor.getColumnIndex("episodetitle"));
                        mDownloadDBBean2.episodeIcon = cursor.getString(cursor.getColumnIndex("episodeicon"));
                        mDownloadDBBean2.albumtitle = cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE));
                        mDownloadDBBean2.totalsize = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_TOTALSIZE));
                        mDownloadDBBean2.finish = cursor.getInt(cursor.getColumnIndex("finish"));
                        mDownloadDBBean2.timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                        mDownloadDBBean2.length = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_LENGTH));
                        mDownloadDBBean2.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                        mDownloadDBBean2.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                        mDownloadDBBean2.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                        mDownloadDBBean2.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                        mDownloadDBBean2.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                        mDownloadDBBean2.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                        mDownloadDBBean = mDownloadDBBean2;
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        mDownloadDBBean = mDownloadDBBean2;
                        LetvTools.closeCursor(cursor);
                        throw th2;
                    }
                }
                LetvTools.closeCursor(cursor);
            } catch (Throwable th3) {
                th2 = th3;
                LetvTools.closeCursor(cursor);
                throw th2;
            }
        }
        return mDownloadDBBean;
    }

    public void delete(int episodeId) {
        Executors.newSingleThreadExecutor().execute(new 1(this, episodeId));
    }

    public boolean finish(int episodeId, long totalSize) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(totalSize));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(totalSize));
            cv.put("finish", "4");
            this.mContext.getContentResolver().update(LetvContentProvider.URI_DOWNLOADTRACE, cv, "episodeid=?", new String[]{episodeId + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private AsyDataBaseHandler getAsyDataBaseHandler() {
        if (this.asyDataBaseHandler == null) {
            this.asyDataBaseHandler = new AsyDataBaseHandler(this.mContext.getContentResolver());
        }
        return this.asyDataBaseHandler;
    }

    public boolean updateDownloadSize(String episodeId, long totalSize, long downLoadSize, int status) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(totalSize));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(downLoadSize));
            cv.put("finish", Integer.valueOf(status));
            getAsyDataBaseHandler().startUpdate(0, null, LetvContentProvider.URI_DOWNLOADTRACE, cv, "episodeid=?", new String[]{episodeId});
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
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "albumId=?", new String[]{albumId + ""}, "timestamp ASC");
            while (cursor.moveToNext()) {
                DownloadDBBean mDownloadDBBean = new DownloadDBBean();
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
                mDownloadDBBean.filePath = cursor.getString(cursor.getColumnIndex("file_path"));
                mDownloadDBBean.isHd = cursor.getInt(cursor.getColumnIndex("isHd"));
                mDownloadDBBean.isNew = cursor.getInt(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ISNEW));
                mDownloadDBBean.btime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME));
                mDownloadDBBean.etime = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME));
                mDownloadDBBean.duration = cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_DURATION));
                list.add(mDownloadDBBean);
            }
            return list;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public boolean hasOldVersionItem(long albumId) {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_DOWNLOADTRACE, null, "albumId=?", new String[]{albumId + ""}, null);
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

    public void updateWatchStateByEpisodeid(int watchState, long episodeid) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("isWatch", Integer.valueOf(watchState));
            this.mContext.getContentResolver().update(LetvContentProvider.URI_DOWNLOADTRACE, cv, "episodeid=?", new String[]{episodeid + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeUserStatus(DownloadDBBean downloadDBBean) {
        new Thread(new 2(this, downloadDBBean)).start();
    }
}
