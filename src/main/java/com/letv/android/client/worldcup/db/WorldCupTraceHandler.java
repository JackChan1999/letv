package com.letv.android.client.worldcup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.letv.android.client.worldcup.bean.DownloadDBBean;
import com.letv.android.client.worldcup.bean.DownloadDBBeanList;
import com.letv.android.client.worldcup.bean.DownloadStatus;
import com.letv.android.client.worldcup.download.WorldCupDownloadManager;
import com.letv.android.client.worldcup.util.Constants;
import com.letv.android.client.worldcup.util.DownloadUtil;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration;
import com.letv.core.BaseApplication;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldCupTraceHandler {
    private Uri content_provider_uri = LetvServiceConfiguration.getContent_provider_uri(this.context);
    private Context context = BaseApplication.instance;

    public com.letv.android.client.worldcup.bean.DownloadDBBeanList getAllTrace() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
*/
        /*
        r10 = this;
        r9 = 0;
        r7 = 0;
        r0 = r10.context;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r1 = r10.content_provider_uri;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r2 = 0;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r3 = 0;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r4 = 0;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r5 = "timestamp ASC";	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r7 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r6 = new com.letv.android.client.worldcup.bean.DownloadDBBeanList;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r6.<init>();	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
    L_0x0019:
        if (r7 == 0) goto L_0x0021;	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
    L_0x001b:
        r0 = r7.moveToNext();	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r10.closeCursor(r7);
    L_0x0024:
        return r6;
    L_0x0025:
        r0 = r10.createDownloadDBBean(r7);	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r6.add(r0);	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        goto L_0x0019;
    L_0x002d:
        r8 = move-exception;
        r8.printStackTrace();	 Catch:{ Exception -> 0x002d, all -> 0x0036 }
        r10.closeCursor(r7);
        r6 = r9;
        goto L_0x0024;
    L_0x0036:
        r0 = move-exception;
        r10.closeCursor(r7);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.worldcup.db.WorldCupTraceHandler.getAllTrace():com.letv.android.client.worldcup.bean.DownloadDBBeanList");
    }

    public WorldCupTraceHandler(Context context) {
    }

    public boolean save(DownloadDBBean mDownloadDBBean) {
        if (has((long) mDownloadDBBean.getEpisodeid()) != null) {
            return update(mDownloadDBBean);
        }
        String download_path_default;
        ContentValues cv = new ContentValues();
        cv.put("episodeid", Integer.valueOf(mDownloadDBBean.getEpisodeid()));
        cv.put(PageJumpUtil.IN_TO_ALBUM_PID, Long.valueOf(mDownloadDBBean.getAlbumId()));
        cv.put(SettingsJsonConstants.APP_ICON_KEY, mDownloadDBBean.getIcon());
        cv.put("type", Integer.valueOf(mDownloadDBBean.getType()));
        cv.put("cid", Integer.valueOf(mDownloadDBBean.getCid()));
        cv.put("episodetitle", mDownloadDBBean.getEpisodetitle());
        cv.put("episodeicon", mDownloadDBBean.getEpisodeIcon());
        cv.put(DownloadAlbumTable.COLUMN_ALBUMTITLE, mDownloadDBBean.getAlbumtitle());
        cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(mDownloadDBBean.getTotalsize()));
        cv.put("finish", Integer.valueOf(mDownloadDBBean.getFinish()));
        cv.put("timestamp", Long.valueOf(System.currentTimeMillis()));
        cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(mDownloadDBBean.getLength()));
        String str = "file_path";
        if (TextUtils.isEmpty(mDownloadDBBean.getFilePath())) {
            download_path_default = LetvServiceConfiguration.getDownload_path_default(this.context);
        } else {
            download_path_default = mDownloadDBBean.getFilePath();
        }
        cv.put(str, download_path_default);
        cv.put("isHd", Integer.valueOf(mDownloadDBBean.getIsHd()));
        cv.put(DownloadVideoTable.COLUMN_BTIME, Long.valueOf(mDownloadDBBean.getBtime()));
        cv.put(DownloadVideoTable.COLUMN_ETIME, Long.valueOf(mDownloadDBBean.getEtime()));
        this.context.getContentResolver().insert(this.content_provider_uri, cv);
        return true;
    }

    private boolean update(DownloadDBBean mDownloadDBBean) {
        if (mDownloadDBBean == null) {
            return false;
        }
        try {
            String download_path_default;
            ContentValues cv = new ContentValues();
            cv.put("episodeid", Integer.valueOf(mDownloadDBBean.getEpisodeid()));
            cv.put(PageJumpUtil.IN_TO_ALBUM_PID, Long.valueOf(mDownloadDBBean.getAlbumId()));
            cv.put(SettingsJsonConstants.APP_ICON_KEY, mDownloadDBBean.getIcon());
            cv.put("type", Integer.valueOf(mDownloadDBBean.getType()));
            cv.put("cid", Integer.valueOf(mDownloadDBBean.getCid()));
            cv.put("episodetitle", mDownloadDBBean.getEpisodetitle());
            cv.put("episodeicon", mDownloadDBBean.getEpisodeIcon());
            cv.put(DownloadAlbumTable.COLUMN_ALBUMTITLE, mDownloadDBBean.getAlbumtitle());
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(mDownloadDBBean.getTotalsize()));
            cv.put("finish", Integer.valueOf(mDownloadDBBean.getFinish()));
            cv.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(mDownloadDBBean.getLength()));
            String str = "file_path";
            if (TextUtils.isEmpty(mDownloadDBBean.getFilePath())) {
                download_path_default = LetvServiceConfiguration.getDownload_path_default(this.context);
            } else {
                download_path_default = mDownloadDBBean.getFilePath();
            }
            cv.put(str, download_path_default);
            cv.put("isHd", Integer.valueOf(mDownloadDBBean.getIsHd()));
            cv.put(DownloadVideoTable.COLUMN_BTIME, Long.valueOf(mDownloadDBBean.getBtime()));
            cv.put(DownloadVideoTable.COLUMN_ETIME, Long.valueOf(mDownloadDBBean.getEtime()));
            this.context.getContentResolver().update(this.content_provider_uri, cv, "episodeid=?", new String[]{new StringBuilder(String.valueOf(mDownloadDBBean.getEpisodeid())).toString()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNumInStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "finish=?", new String[]{new StringBuilder(String.valueOf(status)).toString()}, "timestamp ASC");
            int count = cursor.getCount();
            return count;
        } finally {
            closeCursor(cursor);
        }
    }

    public int getNumOutStatus(int status) {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "finish!=?", new String[]{new StringBuilder(String.valueOf(status)).toString()}, "timestamp ASC");
            int count = cursor.getCount();
            return count;
        } finally {
            closeCursor(cursor);
        }
    }

    public DownloadDBBeanList getLoadingDownloadInfo() {
        DownloadDBBeanList list = new DownloadDBBeanList();
        Cursor cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "finish<>?", new String[]{DownloadStatus.FINISHED.toString()}, "timestamp ASC");
        while (cursor.moveToNext()) {
            list.add(createDownloadDBBean(cursor));
        }
        cursor.close();
        return list;
    }

    public DownloadDBBeanList getOutFinishTrace() {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "finish!=?", new String[]{DownloadStatus.FINISHED.toString()}, "timestamp ASC");
            DownloadDBBeanList arrayList = new DownloadDBBeanList();
            while (cursor.moveToNext()) {
                arrayList.add(createDownloadDBBean(cursor));
            }
            return arrayList;
        } finally {
            closeCursor(cursor);
        }
    }

    public DownloadDBBean has(long episodeId) {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "episodeid=?", new String[]{new StringBuilder(String.valueOf(episodeId)).toString()}, null);
            if (cursor == null || !cursor.moveToFirst()) {
                closeCursor(cursor);
                return null;
            }
            DownloadDBBean createDownloadDBBean = createDownloadDBBean(cursor);
            return createDownloadDBBean;
        } finally {
            closeCursor(cursor);
        }
    }

    public List<Integer> getAllEpisodeId() {
        List<Integer> ids = new ArrayList();
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, new String[]{"episodeid"}, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                ids.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("episodeid"))));
            }
            closeCursor(cursor);
            return ids;
        } catch (Throwable th) {
            closeCursor(cursor);
        }
    }

    @Deprecated
    public List<Long> getAllAlbumId() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, new String[]{PageJumpUtil.IN_TO_ALBUM_PID}, "1 = 1 group by albumId", null, "timestamp ASC");
            List<Long> albumIds = new ArrayList();
            while (cursor != null) {
                try {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                    albumIds.add(Long.valueOf(cursor.getLong(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID))));
                } catch (Throwable th2) {
                    th = th2;
                    List<Long> list = albumIds;
                }
            }
            closeCursor(cursor);
            return albumIds;
        } catch (Throwable th3) {
            th = th3;
            closeCursor(cursor);
            throw th;
        }
    }

    public DownloadDBBean getInFinish(long episodeId) {
        DownloadDBBean mDownloadDBBean;
        synchronized (this.context) {
            mDownloadDBBean = null;
            try {
                Cursor cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "episodeid=? AND finish=?", new String[]{new StringBuilder(String.valueOf(episodeId)).toString(), DownloadStatus.FINISHED.toString()}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    mDownloadDBBean = createDownloadDBBean(cursor);
                }
                closeCursor(cursor);
            } catch (Throwable th) {
                closeCursor(null);
            }
        }
        return mDownloadDBBean;
    }

    public boolean isInFinish(String episodeId) {
        boolean isIn = false;
        try {
            Cursor cursor = this.context.getContentResolver().query(this.content_provider_uri, new String[]{"episodetitle"}, "episodeid=? AND finish=?", new String[]{episodeId, DownloadStatus.FINISHED.toString()}, null);
            if (cursor != null && cursor.getCount() > 0) {
                isIn = true;
            }
            closeCursor(cursor);
            return isIn;
        } catch (Throwable th) {
            closeCursor(null);
        }
    }

    public void delete(int episodeId, String path) {
        File file = DownloadUtil.getDownloadFile(this.context, (long) episodeId, path);
        if (file != null) {
            file.delete();
        }
    }

    public void deleteAll(Context context) {
        Iterator it = getAllTrace().iterator();
        while (it.hasNext()) {
            DownloadDBBean bean = (DownloadDBBean) it.next();
            if (bean.getFinish() == DownloadStatus.FINISHED.toInt()) {
                delete(bean.getEpisodeid(), bean.getFilePath());
            } else {
                WorldCupDownloadManager.getInstance(context).removeDownload(new StringBuilder(String.valueOf(bean.getEpisodeid())).toString());
            }
            context.getContentResolver().delete(this.content_provider_uri, "episodeid=?", new String[]{new StringBuilder(String.valueOf(bean.getEpisodeid())).toString()});
        }
    }

    public void deleteDownloadById(Context context, String episodeId) {
        context.getContentResolver().delete(this.content_provider_uri, "episodeid=?", new String[]{episodeId});
    }

    public boolean finish(int episodeId, long totalSize) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(totalSize));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(totalSize));
            cv.put("finish", DownloadStatus.FINISHED.toString());
            this.context.getContentResolver().update(this.content_provider_uri, cv, "episodeid=?", new String[]{new StringBuilder(String.valueOf(episodeId)).toString()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSize(String episodeId, long totalSize, long downLoadSize, int status) {
        try {
            ContentValues cv = new ContentValues();
            Constants.debug("status=" + status + "  updateDownloadSize episodeId =" + episodeId + "  totalSize=" + totalSize + "--downLoadSize=" + downLoadSize);
            cv.put(DownloadVideoTable.COLUMN_TOTALSIZE, Long.valueOf(totalSize));
            cv.put(DownloadVideoTable.COLUMN_LENGTH, Long.valueOf(downLoadSize));
            cv.put("finish", Integer.valueOf(status));
            this.context.getContentResolver().update(this.content_provider_uri, cv, "episodeid=?", new String[]{episodeId});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public List<DownloadDBBean> getAllInfoWithAid(long albumId) {
        Cursor cursor = null;
        List<DownloadDBBean> list = new ArrayList();
        try {
            cursor = this.context.getContentResolver().query(this.content_provider_uri, null, "albumId=?", new String[]{new StringBuilder(String.valueOf(albumId)).toString()}, "timestamp ASC");
            while (cursor != null && cursor.moveToNext()) {
                list.add(createDownloadDBBean(cursor));
            }
            closeCursor(cursor);
            return list;
        } catch (Throwable th) {
            closeCursor(cursor);
        }
    }

    private DownloadDBBean createDownloadDBBean(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        DownloadDBBean mDownloadDBBean = new DownloadDBBean();
        mDownloadDBBean.setEpisodeid(cursor.getInt(cursor.getColumnIndex("episodeid")));
        mDownloadDBBean.setAlbumId((long) cursor.getInt(cursor.getColumnIndex(PageJumpUtil.IN_TO_ALBUM_PID)));
        mDownloadDBBean.setIcon(cursor.getString(cursor.getColumnIndex(SettingsJsonConstants.APP_ICON_KEY)));
        mDownloadDBBean.setType(cursor.getInt(cursor.getColumnIndex("type")));
        mDownloadDBBean.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
        mDownloadDBBean.setEpisodetitle(cursor.getString(cursor.getColumnIndex("episodetitle")));
        mDownloadDBBean.setEpisodeIcon(cursor.getString(cursor.getColumnIndex("episodeicon")));
        mDownloadDBBean.setAlbumtitle(cursor.getString(cursor.getColumnIndex(DownloadAlbumTable.COLUMN_ALBUMTITLE)));
        mDownloadDBBean.setTotalsize(cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_TOTALSIZE)));
        mDownloadDBBean.setFinish(cursor.getInt(cursor.getColumnIndex("finish")));
        mDownloadDBBean.setTimestamp(cursor.getLong(cursor.getColumnIndex("timestamp")));
        mDownloadDBBean.setLength(cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_LENGTH)));
        mDownloadDBBean.setIsHd(cursor.getInt(cursor.getColumnIndex("isHd")));
        mDownloadDBBean.setBtime(cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_BTIME)));
        mDownloadDBBean.setEtime(cursor.getLong(cursor.getColumnIndex(DownloadVideoTable.COLUMN_ETIME)));
        return mDownloadDBBean;
    }

    public void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
