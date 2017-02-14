package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.core.bean.HomePageBean.Booting;
import com.letv.core.utils.LetvDateFormat;
import com.letv.core.utils.LetvTools;
import com.letv.download.db.Download.DownloadBaseColumns;
import java.util.ArrayList;
import java.util.List;

public class FestivalImageTraceHandler {
    private Context context;

    public FestivalImageTraceHandler(Context context) {
        this.context = context;
    }

    public boolean hasFestivalImage(String name) {
        try {
            Cursor cursor = this.context.getContentResolver().query(LetvContentProvider.URI_FESTIVALIMAGE, new String[]{"count(*)"}, "name = ?", new String[]{name}, null);
            if (cursor.moveToFirst()) {
                boolean z;
                if (cursor.getInt(0) > 0) {
                    z = true;
                } else {
                    z = false;
                }
                LetvTools.closeCursor(cursor);
                return z;
            }
            LetvTools.closeCursor(cursor);
            return false;
        } catch (Throwable th) {
            LetvTools.closeCursor(null);
        }
    }

    public void clear() {
        this.context.getContentResolver().delete(LetvContentProvider.URI_FESTIVALIMAGE, null, null);
    }

    public void insert(List<Booting> bootings) {
        if (bootings != null && bootings.size() >= 0) {
            for (Booting b : bootings) {
                insert(b);
            }
        }
    }

    public void insert(Booting booting) {
        ContentValues cv = new ContentValues();
        cv.put("name", booting.name);
        cv.put(DownloadBaseColumns.COLUMN_PIC, booting.pic);
        cv.put("starttime", booting.pushpic_starttime);
        cv.put("endtime", booting.pushpic_endtime);
        cv.put("orderk", Integer.valueOf(booting.order));
        this.context.getContentResolver().insert(LetvContentProvider.URI_FESTIVALIMAGE, cv);
    }

    public void updateTime(String name, String pic, String startTime, String endTime, int order) {
        ContentValues cv = new ContentValues();
        cv.put("starttime", startTime);
        cv.put("endtime", endTime);
        cv.put(DownloadBaseColumns.COLUMN_PIC, pic);
        cv.put("orderk", Integer.valueOf(order));
        this.context.getContentResolver().update(LetvContentProvider.URI_FESTIVALIMAGE, cv, "name = ?", new String[]{name});
    }

    public Booting getCur() {
        Cursor cursor;
        List<Booting> bootings;
        int order = PreferencesManager.getInstance().getCurBootingOrder() + 1;
        Booting booting = null;
        StringBuilder sb;
        if (order == 1) {
            try {
                sb = new StringBuilder();
                sb.append("orderk = ? ");
                cursor = this.context.getContentResolver().query(LetvContentProvider.URI_FESTIVALIMAGE, null, sb.toString(), new String[]{"1"}, null);
                bootings = build(cursor);
            } catch (Throwable th) {
                LetvTools.closeCursor(null);
            }
        } else {
            sb = new StringBuilder();
            sb.append("orderk = ? OR ");
            sb.append("orderk = ? ");
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_FESTIVALIMAGE, null, sb.toString(), new String[]{order + "", "1"}, null);
            bootings = build(cursor);
        }
        if (bootings != null) {
            if (bootings.size() == 1) {
                booting = (Booting) bootings.get(0);
                order = 1;
            } else if (bootings.size() > 1) {
                for (Booting b : bootings) {
                    if (b.order == order) {
                        booting = b;
                    }
                }
                if (booting == null) {
                    booting = (Booting) bootings.get(0);
                    order = 1;
                }
            }
        }
        if (booting != null) {
            PreferencesManager.getInstance().setCurBootingOrder(order);
        }
        LetvTools.closeCursor(cursor);
        return booting;
    }

    private List<Booting> build(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<Booting> bootings = new ArrayList();
        while (cursor.moveToNext()) {
            Booting booting = new Booting();
            booting.name = cursor.getString(cursor.getColumnIndex("name"));
            booting.pic = cursor.getString(cursor.getColumnIndex(DownloadBaseColumns.COLUMN_PIC));
            booting.pushpic_starttime = cursor.getString(cursor.getColumnIndex("starttime"));
            booting.pushpic_endtime = cursor.getString(cursor.getColumnIndex("endtime"));
            booting.order = cursor.getInt(cursor.getColumnIndex("orderk"));
            if (System.currentTimeMillis() < LetvDateFormat.getTimeInMillis(booting.pushpic_endtime) && System.currentTimeMillis() > LetvDateFormat.getTimeInMillis(booting.pushpic_starttime)) {
                bootings.add(booting);
            }
        }
        return bootings;
    }
}
