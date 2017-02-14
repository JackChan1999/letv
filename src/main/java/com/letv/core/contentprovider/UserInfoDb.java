package com.letv.core.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoDb extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userinfoshare.db";
    private static final int DATABASE_VERSION = 1;
    public static final String SHARE_TOKEN = "shareToken";
    public static final String SHARE_USER_ID = "shareUserId";
    public static final String TABLE_NAME = "userinfo";
    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";

    public UserInfoDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userinfo");
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE userinfo(userId TEXT PRIMARY KEY,token TEXT,shareUserId TEXT,shareToken TEXT);");
    }
}
