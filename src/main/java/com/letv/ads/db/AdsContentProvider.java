package com.letv.ads.db;

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

public class AdsContentProvider extends ContentProvider {
    private static final int ADS = 100;
    public static String AUTHORITY;
    public static final Uri URI_ADS = Uri.parse("content://" + AUTHORITY + "/" + DBConstant.TABLE_NAME);
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
    private SQLiteDataBase sqliteDataBase;

    static {
        InputStream in = null;
        try {
            Properties properties = new Properties();
            in = AdsContentProvider.class.getClassLoader().getResourceAsStream("letv.properties");
            if (in != null) {
                properties.load(in);
                AUTHORITY = properties.getProperty("AdsContentProvider.authorities");
            }
            if (AUTHORITY == null) {
                AUTHORITY = "com.letv.ads.db.AdsContentProvider";
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        }
        URI_MATCHER.addURI(AUTHORITY, DBConstant.TABLE_NAME, 100);
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
                long rowId = db.insert(DBConstant.TABLE_NAME, DBConstant.AD, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_ADS, rowId);
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
                int count = db.delete(DBConstant.TABLE_NAME, selection, selectionArgs);
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
                int count = db.update(DBConstant.TABLE_NAME, values, selection, selectionArgs);
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
                Cursor cursor = db.query(DBConstant.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }
}
