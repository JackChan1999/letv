package com.letv.android.client.worldcup.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import java.io.IOException;
import java.util.Properties;

public class DownloadContentProvider extends ContentProvider {
    public static String AUTHORITY = null;
    public static final Uri DOWNLOAD_TASK_URI = Uri.parse("content://" + AUTHORITY + "/" + "worldcup_download_info");
    public static final Uri DOWNLOAD_THREAD_URI = Uri.parse("content://" + AUTHORITY + "/" + "worldcup_downlod_thread_info");
    private static final int URI_DOWNLOAD_DIR = 100;
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
    private static final int URI_THREAD_DIR = 101;
    private SQLiteDataBase sqliteDataBase;

    static {
        try {
            Properties properties = new Properties();
            properties.load(DownloadContentProvider.class.getClassLoader().getResourceAsStream("letv.properties"));
            AUTHORITY = properties.getProperty("worldcup.DownloadContentProvider.authorities");
            if (AUTHORITY == null) {
                AUTHORITY = "com.letv.android.client.worldcup.db.DownloadContentProvider";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI_MATCHER.addURI(AUTHORITY, "worldcup_download_info", 100);
        URI_MATCHER.addURI(AUTHORITY, "worldcup_downlod_thread_info", 101);
    }

    public boolean onCreate() {
        this.sqliteDataBase = new SQLiteDataBase(getContext());
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case 100:
                qb.setTables("worldcup_download_info");
                break;
            case 101:
                qb.setTables("worldcup_downlod_thread_info");
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());
        }
        Cursor c = qb.query(this.sqliteDataBase.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues _values) {
        ContentValues values;
        String table;
        String nullColumn;
        Uri contentUri;
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        if (_values != null) {
            values = new ContentValues(_values);
        } else {
            values = new ContentValues();
        }
        switch (URI_MATCHER.match(uri)) {
            case 100:
                table = "worldcup_download_info";
                nullColumn = "id";
                contentUri = DOWNLOAD_TASK_URI;
                break;
            case 101:
                table = "worldcup_downlod_thread_info";
                nullColumn = "_id";
                contentUri = DOWNLOAD_THREAD_URI;
                break;
            default:
                throw new UnsupportedOperationException("Invalid URI " + uri);
        }
        long rowId = db.insert(table, nullColumn, values);
        if (rowId > 0) {
            Uri newUri = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return newUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table;
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case 100:
                table = "worldcup_download_info";
                break;
            case 101:
                table = "worldcup_downlod_thread_info";
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return db.delete(table, selection, selectionArgs);
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = "";
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case 100:
                tableName = "worldcup_download_info";
                break;
            case 101:
                tableName = "worldcup_downlod_thread_info";
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());
        }
        return db.update(tableName, values, selection, selectionArgs);
    }
}
