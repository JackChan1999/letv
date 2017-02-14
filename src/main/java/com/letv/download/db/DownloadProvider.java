package com.letv.download.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.download.db.Download.ThreadInfoTable;

public class DownloadProvider extends ContentProvider {
    private static final int URI_DOWNLOAD_ALBUM_DIR = 100;
    private static final int URI_DOWNLOAD_THREAD_DIR = 102;
    private static final int URI_DOWNLOAD_VIDEO_DIR = 101;
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
    DownloadDBHelper mDownloadDBHelper;

    static {
        URI_MATCHER.addURI(Download.AUTHORITY, DownloadAlbumTable.TABLE_NAME, 100);
        URI_MATCHER.addURI(Download.AUTHORITY, DownloadVideoTable.TABLE_NAME, 101);
        URI_MATCHER.addURI(Download.AUTHORITY, ThreadInfoTable.TABLE_NAME, 102);
    }

    public boolean onCreate() {
        this.mDownloadDBHelper = new DownloadDBHelper(getContext());
        return false;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case 100:
                qb.setTables(DownloadAlbumTable.TABLE_NAME);
                break;
            case 101:
                qb.setTables(DownloadVideoTable.TABLE_NAME);
                break;
            case 102:
                qb.setTables(ThreadInfoTable.TABLE_NAME);
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());
        }
        Cursor c = qb.query(this.mDownloadDBHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Uri contentUri;
        long rowId = 0;
        SQLiteDatabase db = this.mDownloadDBHelper.getWritableDatabase();
        String table;
        switch (URI_MATCHER.match(uri)) {
            case 100:
                table = DownloadAlbumTable.TABLE_NAME;
                contentUri = DownloadAlbumTable.CONTENT_URI;
                rowId = db.insert(table, null, values);
                break;
            case 101:
                table = DownloadVideoTable.TABLE_NAME;
                contentUri = DownloadVideoTable.CONTENT_URI;
                rowId = db.insert(table, null, values);
                break;
            case 102:
                table = ThreadInfoTable.TABLE_NAME;
                contentUri = ThreadInfoTable.CONTENT_URI;
                try {
                    rowId = db.insert(table, null, values);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            default:
                throw new UnsupportedOperationException("Invalid URI " + uri);
        }
        if (rowId <= 0) {
            return null;
        }
        Uri newUri = ContentUris.withAppendedId(contentUri, rowId);
        getContext().getContentResolver().notifyChange(uri, null);
        return newUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table;
        SQLiteDatabase db = this.mDownloadDBHelper.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case 100:
                table = DownloadAlbumTable.TABLE_NAME;
                break;
            case 101:
                table = DownloadVideoTable.TABLE_NAME;
                break;
            case 102:
                table = ThreadInfoTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        int count = db.delete(table, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = "";
        SQLiteDatabase db = this.mDownloadDBHelper.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case 100:
                tableName = DownloadAlbumTable.TABLE_NAME;
                break;
            case 101:
                tableName = DownloadVideoTable.TABLE_NAME;
                break;
            case 102:
                tableName = ThreadInfoTable.TABLE_NAME;
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());
        }
        int count = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
