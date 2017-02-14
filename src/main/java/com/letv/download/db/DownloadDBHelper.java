package com.letv.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.letv.core.utils.LogInfo;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.download.db.Download.ThreadInfoTable;

public class DownloadDBHelper extends SQLiteOpenHelper {
    private Context mContext;

    public DownloadDBHelper(Context context) {
        super(context, Download.DATABASE_NAME, null, 5);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DownloadAlbumTable.TABLE_CREATE);
        db.execSQL(DownloadVideoTable.TABLE_CREATE);
        db.execSQL(ThreadInfoTable.TABLE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogInfo.log("DownloadDBHelper", " onUpgrade oldVersion : " + oldVersion + " newVersion : " + newVersion);
        switch (oldVersion) {
            case 1:
                onCreate(db);
                return;
            case 2:
                db.execSQL("ALTER TABLE download_video ADD COLUMN videonormal int default 1;");
                db.execSQL("ALTER TABLE download_album ADD COLUMN albumVersion int ;");
                db.execSQL("ALTER TABLE download_album ADD COLUMN albumVideoNormal int default 1;");
                db.execSQL("ALTER TABLE download_album ADD COLUMN albumFromRecom int ;");
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                return;
        }
        db.execSQL("ALTER TABLE download_video ADD COLUMN isVipDownload int default 0;");
        db.execSQL("ALTER TABLE download_video ADD COLUMN hasSubtitle int default 0;");
        db.execSQL("ALTER TABLE download_video ADD COLUMN subtitleUrl text ;");
        db.execSQL("ALTER TABLE download_video ADD COLUMN subtitleCode text ;");
        db.execSQL("ALTER TABLE download_video ADD COLUMN isMultipleAudio int default 0;");
        db.execSQL("ALTER TABLE download_video ADD COLUMN multipleAudioCode text ;");
    }
}
