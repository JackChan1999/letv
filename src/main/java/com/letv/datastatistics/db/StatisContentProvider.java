package com.letv.datastatistics.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StatisContentProvider extends ContentProvider {
    public static String AUTHORITY = null;
    private static final int STATIS = 100;
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
    public static final Uri URI_STATIS = Uri.parse("content://" + AUTHORITY + "/" + "staticticsCache");
    private SQLiteDataBase sqliteDataBase;

    static {
        try {
            Properties properties = new Properties();
            InputStream in = StatisContentProvider.class.getClassLoader().getResourceAsStream("letv.properties");
            if (in != null) {
                properties.load(in);
                AUTHORITY = properties.getProperty("StatisContentProvider.authorities");
            }
            if (AUTHORITY == null) {
                AUTHORITY = "com.letv.datastatistics.db.StatisContentProvider";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI_MATCHER.addURI(AUTHORITY, "staticticsCache", 100);
    }

    public boolean onCreate() {
        this.sqliteDataBase = new SQLiteDataBase(getContext());
        return true;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (match) {
            case 100:
                long rowId = db.insert("staticticsCache", "cacheId", values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_STATIS, rowId);
                }
                if (newUri != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return newUri;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (match) {
            case 100:
                int count = db.delete("staticticsCache", selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (match) {
            case 100:
                int count = db.update("staticticsCache", values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (match) {
            case 100:
                Cursor cursor = db.query("staticticsCache", null, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }
}
