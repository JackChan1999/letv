package com.letv.ads.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ads.db";
    private static final int DATABASE_VERSION = 2;

    public SQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db.execSQL("drop table if exists ads_table");
            createTable(db);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != 2) {
        }
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ads_table(AD TEXT PRIMARY KEY,Content TEXT);");
    }
}
