package com.letv.core.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class UserInfoContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.letv.android.client.provider.userinfoprovider";
    public static final int CODE_USERINFO = 1000;
    public static final Uri URI_USERINFO = Uri.parse("content://com.letv.android.client.provider.userinfoprovider/userinfo");
    private static UriMatcher matcher = new UriMatcher(-1);
    private UserInfoDb userInfoDb;

    static {
        matcher.addURI(AUTHORITY, UserInfoDb.TABLE_NAME, 1000);
    }

    public boolean onCreate() {
        this.userInfoDb = new UserInfoDb(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = matcher.match(uri);
        SQLiteDatabase db = this.userInfoDb.getWritableDatabase();
        switch (match) {
            case 1000:
                Cursor cursor = db.query(UserInfoDb.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                return null;
        }
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        int match = matcher.match(uri);
        SQLiteDatabase db = this.userInfoDb.getWritableDatabase();
        switch (match) {
            case 1000:
                long rowId = db.insert(UserInfoDb.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_USERINFO, rowId);
                    break;
                }
                break;
        }
        if (newUri != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return newUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = matcher.match(uri);
        SQLiteDatabase db = this.userInfoDb.getWritableDatabase();
        switch (match) {
            case 1000:
                int count = db.delete(UserInfoDb.TABLE_NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = matcher.match(uri);
        SQLiteDatabase db = this.userInfoDb.getWritableDatabase();
        switch (match) {
            case 1000:
                int count = db.update(UserInfoDb.TABLE_NAME, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
    }
}
