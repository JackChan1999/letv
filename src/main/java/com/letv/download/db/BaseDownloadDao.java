package com.letv.download.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class BaseDownloadDao {
    public Context mContext;

    public ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    public Uri insert(Uri tableUri, ContentValues contentValues) {
        return getContentResolver().insert(tableUri, contentValues);
    }

    public void bulkInsert(Uri uri, ContentValues[] values) {
        try {
            getContentResolver().bulkInsert(uri, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Uri tableUri, ContentValues contentValues, String where) {
        try {
            getContentResolver().update(tableUri, contentValues, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Uri tableUri, ContentValues contentValues, String where, String[] whereArg) {
        try {
            getContentResolver().update(tableUri, contentValues, where, whereArg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByID(Uri tableUri, String where, String[] selectionArgs) {
        try {
            getContentResolver().delete(tableUri, where, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(Uri tableUri) {
        try {
            getContentResolver().delete(tableUri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor queryByID(Uri tableUri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            return getContentResolver().query(tableUri, projection, selection, selectionArgs, sortOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor queryAll(Uri tableUri) {
        try {
            return this.mContext.getContentResolver().query(tableUri, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
