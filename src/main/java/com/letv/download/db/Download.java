package com.letv.download.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import com.letv.download.bean.DownloadAlbum;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.bean.PartInfoBean;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Download {
    public static final String AUTHORITY = "com.letv.download.db";
    private static final String COMMA = ",";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    public static final String DATABASE_NAME = "letv_download.db";
    public static final int DATABASE_VERSION = 5;
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ");";

    public interface DownloadBaseColumns extends BaseColumns {
        public static final String COLUMN_AID = "aid";
        public static final String COLUMN_ISWATCH = "isWatch";
        public static final String COLUMN_PIC = "pic";
    }

    public static class DownloadAlbumTable implements DownloadBaseColumns {
        public static final String COLUMN_ALBUMTITLE = "albumtitle";
        public static final String COLUMN_ALBUMTOTALSIZE = "albumTotalSize";
        public static final String COLUMN_ALBUM_FROM_RECOM = "albumFromRecom";
        public static final String COLUMN_ALBUM_TIMESTAMP = "timestamp";
        public static final String COLUMN_ALBUM_VERSION = "albumVersion";
        public static final String COLUMN_ALBUM_VIDEONORAML = "albumVideoNormal";
        public static final String COLUMN_ALBUM_VIDEONUM = "albumVideoNum";
        public static final Uri CONTENT_URI = Uri.parse("content://com.letv.download.db/download_album");
        public static final String TABLE_CREATE = Download.genTableCreationSql(TABLE_NAME, getColumHashMap());
        public static final String TABLE_NAME = "download_album";

        private static HashMap<String, String> getColumHashMap() {
            LinkedHashMap<String, String> map = new LinkedHashMap();
            map.put("_id", " integer primary key autoincrement");
            map.put("aid", "int not null unique ");
            map.put(DownloadBaseColumns.COLUMN_PIC, "text");
            map.put(COLUMN_ALBUMTITLE, "text");
            map.put(COLUMN_ALBUMTOTALSIZE, "int");
            map.put("isWatch", "int");
            map.put("albumVideoNum", "int");
            map.put("timestamp", "int");
            map.put(COLUMN_ALBUM_VERSION, "int");
            map.put(COLUMN_ALBUM_VIDEONORAML, "int default 1");
            map.put(COLUMN_ALBUM_FROM_RECOM, "int");
            return map;
        }

        public static ContentValues albumToContentValues2(DownloadAlbum downloadAlbum) {
            int i = 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put("aid", Long.valueOf(downloadAlbum.aid));
            contentValues.put(COLUMN_ALBUMTOTALSIZE, Long.valueOf(downloadAlbum.albumTotalSize));
            contentValues.put("albumVideoNum", Integer.valueOf(downloadAlbum.albumVideoNum));
            contentValues.put("isWatch", Boolean.valueOf(downloadAlbum.isWatch));
            contentValues.put(COLUMN_ALBUM_VERSION, Integer.valueOf(downloadAlbum.getAlbumVersion()));
            contentValues.put(COLUMN_ALBUM_VIDEONORAML, Integer.valueOf(downloadAlbum.isVideoNormal ? 1 : 0));
            String str = COLUMN_ALBUM_FROM_RECOM;
            if (!downloadAlbum.isFrommRecom) {
                i = 0;
            }
            contentValues.put(str, Integer.valueOf(i));
            if (downloadAlbum.timestamp != 0) {
                contentValues.put("timestamp", Long.valueOf(downloadAlbum.timestamp));
            } else {
                contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            }
            return contentValues;
        }

        public static ContentValues albumToContentValues(DownloadAlbum downloadAlbum) {
            int i = 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put("aid", Long.valueOf(downloadAlbum.aid));
            contentValues.put(DownloadBaseColumns.COLUMN_PIC, downloadAlbum.picUrl);
            contentValues.put(COLUMN_ALBUMTITLE, downloadAlbum.albumTitle);
            contentValues.put(COLUMN_ALBUMTOTALSIZE, Long.valueOf(downloadAlbum.albumTotalSize));
            contentValues.put("albumVideoNum", Integer.valueOf(downloadAlbum.albumVideoNum));
            contentValues.put("isWatch", Boolean.valueOf(downloadAlbum.isWatch));
            contentValues.put(COLUMN_ALBUM_VERSION, Integer.valueOf(downloadAlbum.getAlbumVersion()));
            contentValues.put(COLUMN_ALBUM_VIDEONORAML, Integer.valueOf(downloadAlbum.isVideoNormal ? 1 : 0));
            String str = COLUMN_ALBUM_FROM_RECOM;
            if (!downloadAlbum.isFrommRecom) {
                i = 0;
            }
            contentValues.put(str, Integer.valueOf(i));
            if (downloadAlbum.timestamp != 0) {
                contentValues.put("timestamp", Long.valueOf(downloadAlbum.timestamp));
            } else {
                contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            }
            return contentValues;
        }

        public static DownloadAlbum cursorToDownloadAlbum(Cursor cursor) {
            boolean z = true;
            if (cursor == null || cursor.isClosed() || cursor.getCount() <= 0) {
                return null;
            }
            boolean z2;
            DownloadAlbum downloadAlbum = new DownloadAlbum();
            downloadAlbum.aid = cursor.getLong(cursor.getColumnIndex("aid"));
            downloadAlbum.picUrl = cursor.getString(cursor.getColumnIndex(DownloadBaseColumns.COLUMN_PIC));
            downloadAlbum.albumTitle = cursor.getString(cursor.getColumnIndex(COLUMN_ALBUMTITLE));
            downloadAlbum.albumTotalSize = cursor.getLong(cursor.getColumnIndex(COLUMN_ALBUMTOTALSIZE));
            downloadAlbum.albumVideoNum = cursor.getInt(cursor.getColumnIndex("albumVideoNum"));
            downloadAlbum.isWatch = cursor.getInt(cursor.getColumnIndex("isWatch")) == 1;
            downloadAlbum.timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
            downloadAlbum.setAlbumVersion(cursor.getInt(cursor.getColumnIndex(COLUMN_ALBUM_VERSION)));
            if (cursor.getInt(cursor.getColumnIndex(COLUMN_ALBUM_VIDEONORAML)) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            downloadAlbum.isVideoNormal = z2;
            if (cursor.getInt(cursor.getColumnIndex(COLUMN_ALBUM_FROM_RECOM)) != 1) {
                z = false;
            }
            downloadAlbum.isFrommRecom = z;
            return downloadAlbum;
        }
    }

    public static class DownloadVideoTable implements DownloadBaseColumns {
        public static final String COLUMN_BTIME = "btime";
        public static final String COLUMN_CID = "cid";
        public static final String COLUMN_DOWNLOADED = "downloaded";
        public static final String COLUMN_DOWNLOADURL = "downloadUrl";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_ETIME = "etime";
        public static final String COLUMN_FILEPATH = "filePath";
        public static final String COLUMN_HAS_SUBTITLE = "hasSubtitle";
        public static final String COLUMN_ISNEW = "isNew";
        public static final String COLUMN_ISVIP_DOWNLOAD = "isVipDownload";
        public static final String COLUMN_ISWATCH = "isWatch";
        public static final String COLUMN_IS_MULTIPLE_AUDIO = "isMultipleAudio";
        public static final String COLUMN_LENGTH = "length";
        public static final String COLUMN_MMSID = "mmsid";
        public static final String COLUMN_MULTIPLE_AUDIO_CODE = "multipleAudioCode";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ORD = "ord";
        public static final String COLUMN_PCODE = "pcode";
        public static final String COLUMN_PID = "pid";
        public static final String COLUMN_SPEED = "speed";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_STOREPATH = "storePath";
        public static final String COLUMN_STREAMTYPE = "streamType";
        public static final String COLUMN_SUBTITLE_CODE = "subtitleCode";
        public static final String COLUMN_SUBTITLE_URL = "subtitleUrl";
        public static final String COLUMN_THREADNUM = "threadNum";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_TOTALSIZE = "totalsize";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_VERSION = "version";
        public static final String COLUMN_VID = "vid";
        public static final String COLUMN_VIDEONORMAL = "videonormal";
        public static final String COLUMN_VIDEOTYPEKEY = "videoTypeKey";
        public static final Uri CONTENT_URI = Uri.parse("content://com.letv.download.db/download_video");
        public static final String TABLE_CREATE = Download.genTableCreationSql(TABLE_NAME, getColumHashMap());
        public static final String TABLE_NAME = "download_video";

        public static ContentValues videoToContentValues(DownloadVideo downloadVideo) {
            int i = 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put("aid", Long.valueOf(downloadVideo.aid));
            contentValues.put(DownloadBaseColumns.COLUMN_PIC, downloadVideo.picUrl);
            contentValues.put("cid", Long.valueOf(downloadVideo.cid));
            contentValues.put("ord", Integer.valueOf(downloadVideo.ord));
            contentValues.put("type", Integer.valueOf(downloadVideo.type));
            contentValues.put("vid", Long.valueOf(downloadVideo.vid));
            contentValues.put("name", downloadVideo.name);
            contentValues.put(COLUMN_TOTALSIZE, Long.valueOf(downloadVideo.totalsize));
            contentValues.put(COLUMN_LENGTH, Long.valueOf(downloadVideo.length));
            contentValues.put(COLUMN_VIDEOTYPEKEY, downloadVideo.videoTypeKey);
            contentValues.put(COLUMN_STREAMTYPE, Integer.valueOf(downloadVideo.streamType));
            contentValues.put(COLUMN_ISNEW, Boolean.valueOf(downloadVideo.isNew));
            contentValues.put(COLUMN_BTIME, Long.valueOf(downloadVideo.btime));
            contentValues.put(COLUMN_ETIME, Long.valueOf(downloadVideo.etime));
            contentValues.put("isWatch", Boolean.valueOf(downloadVideo.isWatch));
            contentValues.put(COLUMN_DOWNLOADURL, downloadVideo.downloadUrl);
            contentValues.put("downloaded", Long.valueOf(downloadVideo.downloaded));
            contentValues.put(COLUMN_THREADNUM, Integer.valueOf(downloadVideo.threadNum));
            contentValues.put("state", Integer.valueOf(downloadVideo.state));
            contentValues.put("mmsid", downloadVideo.mmsid);
            contentValues.put("pcode", downloadVideo.pcode);
            contentValues.put("version", downloadVideo.version);
            contentValues.put(COLUMN_FILEPATH, downloadVideo.filePath);
            contentValues.put(COLUMN_DURATION, Long.valueOf(downloadVideo.duration));
            contentValues.put("speed", downloadVideo.speed);
            contentValues.put(COLUMN_FILEPATH, downloadVideo.filePath);
            contentValues.put(COLUMN_STOREPATH, downloadVideo.storePath);
            contentValues.put("pid", Long.valueOf(downloadVideo.pid));
            String str = COLUMN_VIDEONORMAL;
            if (!downloadVideo.isVideoNormal) {
                i = 0;
            }
            contentValues.put(str, Integer.valueOf(i));
            if (downloadVideo.timestamp != 0) {
                contentValues.put("timestamp", Long.valueOf(downloadVideo.timestamp));
            } else {
                contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            }
            contentValues.put(COLUMN_ISVIP_DOWNLOAD, Boolean.valueOf(downloadVideo.isVipDownload));
            contentValues.put(COLUMN_HAS_SUBTITLE, Boolean.valueOf(downloadVideo.hasSubtitle));
            contentValues.put(COLUMN_SUBTITLE_URL, downloadVideo.subtitleUrl);
            contentValues.put(COLUMN_SUBTITLE_CODE, downloadVideo.subtitleCode);
            contentValues.put(COLUMN_IS_MULTIPLE_AUDIO, Boolean.valueOf(downloadVideo.isMultipleAudio));
            contentValues.put(COLUMN_MULTIPLE_AUDIO_CODE, downloadVideo.multipleAudioCode);
            return contentValues;
        }

        public static DownloadVideo cursorToDownloadVideo(Cursor cursor) {
            boolean z = true;
            if (cursor == null || cursor.isClosed() || cursor.getCount() <= 0) {
                return null;
            }
            boolean z2;
            DownloadVideo downloadVideo = new DownloadVideo();
            downloadVideo.aid = cursor.getLong(cursor.getColumnIndexOrThrow("aid"));
            downloadVideo.picUrl = cursor.getString(cursor.getColumnIndexOrThrow(DownloadBaseColumns.COLUMN_PIC));
            downloadVideo.cid = cursor.getLong(cursor.getColumnIndexOrThrow("cid"));
            downloadVideo.ord = cursor.getInt(cursor.getColumnIndexOrThrow("ord"));
            downloadVideo.type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
            downloadVideo.vid = cursor.getLong(cursor.getColumnIndexOrThrow("vid"));
            downloadVideo.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            downloadVideo.totalsize = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TOTALSIZE));
            downloadVideo.length = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LENGTH));
            downloadVideo.videoTypeKey = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIDEOTYPEKEY));
            downloadVideo.streamType = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STREAMTYPE));
            downloadVideo.isNew = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ISNEW)) == 1;
            downloadVideo.btime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_BTIME));
            downloadVideo.etime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ETIME));
            if (cursor.getInt(cursor.getColumnIndex("isWatch")) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            downloadVideo.isWatch = z2;
            downloadVideo.downloadUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOADURL));
            downloadVideo.downloaded = cursor.getLong(cursor.getColumnIndexOrThrow("downloaded"));
            downloadVideo.threadNum = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_THREADNUM));
            downloadVideo.state = cursor.getInt(cursor.getColumnIndexOrThrow("state"));
            downloadVideo.mmsid = cursor.getString(cursor.getColumnIndexOrThrow("mmsid"));
            downloadVideo.pcode = cursor.getString(cursor.getColumnIndexOrThrow("pcode"));
            downloadVideo.version = cursor.getString(cursor.getColumnIndexOrThrow("version"));
            downloadVideo.speed = cursor.getString(cursor.getColumnIndexOrThrow("speed"));
            downloadVideo.filePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILEPATH));
            downloadVideo.timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));
            downloadVideo.storePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STOREPATH));
            downloadVideo.duration = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DURATION));
            downloadVideo.pid = cursor.getLong(cursor.getColumnIndexOrThrow("pid"));
            downloadVideo.isVideoNormal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIDEONORMAL)) == 1;
            if (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ISVIP_DOWNLOAD)) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            downloadVideo.isVipDownload = z2;
            if (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HAS_SUBTITLE)) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            downloadVideo.hasSubtitle = z2;
            downloadVideo.subtitleUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBTITLE_URL));
            downloadVideo.subtitleCode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBTITLE_CODE));
            if (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_MULTIPLE_AUDIO)) != 1) {
                z = false;
            }
            downloadVideo.isMultipleAudio = z;
            downloadVideo.multipleAudioCode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MULTIPLE_AUDIO_CODE));
            return downloadVideo;
        }

        private static HashMap<String, String> getColumHashMap() {
            LinkedHashMap<String, String> map = new LinkedHashMap();
            map.put("_id", " integer primary key autoincrement");
            map.put("aid", "int");
            map.put("pid", "int");
            map.put("vid", "int not null unique ");
            map.put(DownloadBaseColumns.COLUMN_PIC, "text");
            map.put("cid", "int");
            map.put("ord", "int");
            map.put("name", "text");
            map.put(COLUMN_TOTALSIZE, "int");
            map.put(COLUMN_LENGTH, "int");
            map.put(COLUMN_VIDEOTYPEKEY, "text");
            map.put(COLUMN_FILEPATH, "text");
            map.put(COLUMN_STREAMTYPE, "int");
            map.put(COLUMN_ISNEW, "int");
            map.put(COLUMN_BTIME, "int");
            map.put(COLUMN_ETIME, "int");
            map.put("isWatch", "int");
            map.put(COLUMN_DURATION, "int");
            map.put(COLUMN_DOWNLOADURL, "text");
            map.put("downloaded", "int");
            map.put(COLUMN_THREADNUM, "int");
            map.put("state", "int");
            map.put("mmsid", "text");
            map.put("pcode", "text");
            map.put("version", "text");
            map.put("type", "int");
            map.put("speed", "text");
            map.put("timestamp", "int");
            map.put(COLUMN_STOREPATH, "text");
            map.put(COLUMN_VIDEONORMAL, "int default 1");
            map.put(COLUMN_ISVIP_DOWNLOAD, "int default 0");
            map.put(COLUMN_HAS_SUBTITLE, "int default 0");
            map.put(COLUMN_SUBTITLE_URL, "text");
            map.put(COLUMN_SUBTITLE_CODE, "text");
            map.put(COLUMN_IS_MULTIPLE_AUDIO, "int default 0");
            map.put(COLUMN_MULTIPLE_AUDIO_CODE, "text");
            return map;
        }
    }

    public static class ThreadInfoTable implements DownloadBaseColumns {
        public static final String COLUMN_DOWNLOADED = "downloaded";
        public static final String COLUMN_DOWNLOAD_PARTINDEX = "part_index";
        public static final String COLUMN_DOWNLOAD_ROWID = "download_id";
        public static final String COLUMN_FIRST_BYTE = "first_byte";
        public static final String COLUMN_LAST_BYTE = "last_byte";
        public static final Uri CONTENT_URI = Uri.parse("content://com.letv.download.db/download_thread_info");
        public static final String TABLE_CREATE = Download.genTableCreationSql(TABLE_NAME, getColumHashMap());
        public static final String TABLE_NAME = "download_thread_info";

        private static HashMap<String, String> getColumHashMap() {
            LinkedHashMap<String, String> map = new LinkedHashMap();
            map.put("_id", " integer primary key autoincrement");
            map.put(COLUMN_FIRST_BYTE, "int");
            map.put(COLUMN_LAST_BYTE, "int");
            map.put("downloaded", "int");
            map.put(COLUMN_DOWNLOAD_ROWID, "int");
            return map;
        }

        public static ContentValues threadInfoToContentValues(PartInfoBean partInfo) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_FIRST_BYTE, Long.valueOf(partInfo.firstByte));
            contentValues.put(COLUMN_LAST_BYTE, Long.valueOf(partInfo.lastByte));
            contentValues.put("downloaded", Long.valueOf(partInfo.downloaded));
            contentValues.put(COLUMN_DOWNLOAD_ROWID, Long.valueOf(partInfo.vid));
            return contentValues;
        }

        public static PartInfoBean cursorToPartInfo(Cursor cursor) {
            PartInfoBean partInfo = new PartInfoBean();
            partInfo.rowId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            partInfo.downloaded = cursor.getLong(cursor.getColumnIndexOrThrow("downloaded"));
            partInfo.firstByte = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_FIRST_BYTE));
            partInfo.lastByte = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_LAST_BYTE));
            partInfo.vid = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DOWNLOAD_ROWID));
            return partInfo;
        }
    }

    public static String genTableCreationSql(String tableName, HashMap<String, String> mapTable) {
        StringBuffer sqlStringBuffer = new StringBuffer();
        int i = 0;
        for (String key : mapTable.keySet()) {
            if (i == 0) {
                sqlStringBuffer.append(CREATE_TABLE).append(tableName).append(LEFT_BRACKET);
            }
            sqlStringBuffer.append(key).append(" ").append((String) mapTable.get(key));
            if (i == mapTable.size() - 1) {
                sqlStringBuffer.append(RIGHT_BRACKET);
            } else {
                sqlStringBuffer.append(COMMA);
            }
            i++;
        }
        return sqlStringBuffer.toString();
    }
}
