package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.core.utils.LetvTools;
import java.util.ArrayList;

public class SearchTraceHandler {
    public static void saveSearchTrace(Context context, String name, long timeStamp) {
        if (name != null && name.length() > 0 && timeStamp > 0) {
            ContentValues contentValues;
            if (hasSearchTrace(context, name)) {
                contentValues = new ContentValues();
                contentValues.put("timestamp", Long.valueOf(timeStamp));
                context.getContentResolver().update(LetvContentProvider.URI_SEARCHTRACE, contentValues, "name=?", new String[]{name});
                return;
            }
            removeOne(context);
            contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("timestamp", Long.valueOf(timeStamp));
            context.getContentResolver().insert(LetvContentProvider.URI_SEARCHTRACE, contentValues);
        }
    }

    public static ArrayList<String> getAllSearchTrace(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(LetvContentProvider.URI_SEARCHTRACE, null, null, null, "timestamp desc");
            ArrayList<String> list = new ArrayList();
            while (cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            }
            return list;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public static void clearAll(Context context) {
        context.getContentResolver().delete(LetvContentProvider.URI_SEARCHTRACE, null, null);
    }

    private static boolean hasSearchTrace(Context context, String name) {
        int count = 0;
        try {
            boolean z;
            Cursor cursor = context.getContentResolver().query(LetvContentProvider.URI_SEARCHTRACE, null, "name=?", new String[]{name}, null);
            if (cursor != null) {
                count = cursor.getCount();
            }
            if (count > 0) {
                z = true;
            } else {
                z = false;
            }
            LetvTools.closeCursor(cursor);
            return z;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    private static void removeOne(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(LetvContentProvider.URI_SEARCHTRACE, null, null, null, "timestamp asc");
            while (cursor.getCount() >= 20) {
                cursor.moveToNext();
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                context.getContentResolver().delete(LetvContentProvider.URI_SEARCHTRACE, "name=?", new String[]{name + ""});
                cursor = context.getContentResolver().query(LetvContentProvider.URI_SEARCHTRACE, null, null, null, "timestamp asc");
            }
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }
}
