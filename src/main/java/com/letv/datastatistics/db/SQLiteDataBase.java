package com.letv.datastatistics.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "datastatistics.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        createTable_StaticticsCacheTrace(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createTable_StaticticsCacheTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE staticticsCache(cacheId TEXT PRIMARY KEY,cachetime TEXT,cachedata TEXT);");
    }
}
