package com.letv.android.client.worldcup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "letv_worldcup_download.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TAG = SQLiteDataBase.class.getSimpleName();

    SQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table worldcup_download_info(_id integer primary key autoincrement, id text not null, url text not null, directory text not null, name text not null, created text not null, elapsed_time text not null, downloaded text,total text,threads int,state int, mmsid text, ishd text, pcode text, version text);");
        db.execSQL("create table worldcup_downlod_thread_info(_id integer primary key autoincrement, first_byte text not null, last_byte text not null, downloaded text not null, download_id integer not null, FOREIGN KEY(download_id) REFERENCES worldcup_download_info(_id));");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS worldcup_download_info");
        db.execSQL("DROP TABLE IF EXISTS worldcup_downlod_thread_info");
        onCreate(db);
    }
}
