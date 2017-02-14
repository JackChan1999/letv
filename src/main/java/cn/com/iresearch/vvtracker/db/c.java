package cn.com.iresearch.vvtracker.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

final class c extends SQLiteOpenHelper {
    public c(Context context, String str, int i) {
        super(context, str, null, i);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", null);
        if (rawQuery != null) {
            while (rawQuery.moveToNext()) {
                sQLiteDatabase.execSQL("DROP TABLE " + rawQuery.getString(0));
            }
        }
        if (rawQuery != null) {
            rawQuery.close();
        }
    }
}
