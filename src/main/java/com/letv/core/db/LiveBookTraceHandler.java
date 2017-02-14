package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.core.bean.PushBookLive;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import java.util.ArrayList;

public class LiveBookTraceHandler {
    private Context context;

    public boolean hasLiveBookTrace(java.lang.String r12) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r11 = this;
        r7 = 1;
        r8 = 0;
        r6 = 0;
        r0 = r11.context;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r1 = com.letv.core.db.LetvContentProvider.URI_LIVEBOOKTRACE;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r2 = 0;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r3 = "md5=?";	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r4 = 1;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r5 = 0;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r9.<init>();	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r9 = r9.append(r12);	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r10 = "";	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r9 = r9.toString();	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r4[r5] = r9;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r5 = 0;	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        r0 = r6.getCount();	 Catch:{ Exception -> 0x0039, all -> 0x003f }
        if (r0 <= 0) goto L_0x0037;
    L_0x0032:
        r0 = r7;
    L_0x0033:
        com.letv.core.utils.LetvTools.closeCursor(r6);
    L_0x0036:
        return r0;
    L_0x0037:
        r0 = r8;
        goto L_0x0033;
    L_0x0039:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        r0 = r8;
        goto L_0x0036;
    L_0x003f:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.db.LiveBookTraceHandler.hasLiveBookTrace(java.lang.String):boolean");
    }

    public LiveBookTraceHandler(Context context) {
        this.context = context;
    }

    public void saveLiveBookTrace(String programName, String channelName, String code, String play_time, long play_time_long, String id, int launchMode) {
        String md5_id = MD5.toMd5(programName + code + play_time);
        if (hasLiveBookTrace(md5_id)) {
            LogInfo.log("zlb", "数据库已有：" + channelName + " , " + programName + " , 不添加");
            LetvTools.logBook("数据库已有：" + channelName + " , " + programName + " , 不添加", getClass());
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(Field.CHANNELNAME, channelName);
        contentValues.put(Field.PROGRAMNAME, programName);
        contentValues.put("code", code);
        contentValues.put("play_time", play_time);
        contentValues.put(Field.PLAY_TIME_LONG, Long.valueOf(play_time_long));
        contentValues.put(Field.MD5_ID, md5_id);
        contentValues.put(Field.IS_NOTIFY, Integer.valueOf(0));
        contentValues.put(Field.LIVE_ID, id);
        contentValues.put("launch_mode", Integer.valueOf(launchMode));
        this.context.getContentResolver().insert(LetvContentProvider.URI_LIVEBOOKTRACE, contentValues);
        LogInfo.log("zlb", "数据库添加成功：" + channelName + " , " + programName + " , launchMode = " + launchMode);
        LetvTools.logBook("数据库添加成功：" + channelName + " , " + programName + " , launchMode = " + launchMode, getClass());
    }

    public long getNearestTrace() {
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_LIVEBOOKTRACE, null, "play_time_long>? AND is_notify<>?", new String[]{(System.currentTimeMillis() - 600000) + "", "1"}, Field.PLAY_TIME_LONG);
            if (cursor == null || cursor.getCount() <= 0) {
                LetvTools.closeCursor(cursor);
                return -1;
            }
            cursor.moveToFirst();
            long j = cursor.getLong(cursor.getColumnIndexOrThrow(Field.PLAY_TIME_LONG));
            return j;
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public ArrayList<PushBookLive> getCurrentTrace() {
        Throwable th;
        Cursor cursor = null;
        long currentTime = System.currentTimeMillis();
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_LIVEBOOKTRACE, null, "play_time_long > ? AND play_time_long < ? AND is_notify<>?", new String[]{(currentTime - 600000) + "", (600000 + currentTime) + "", "1"}, Field.PLAY_TIME_LONG);
            ArrayList<PushBookLive> list = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    PushBookLive bookLive = new PushBookLive();
                    bookLive.channelName = cursor.getString(cursor.getColumnIndexOrThrow(Field.CHANNELNAME));
                    bookLive.programName = cursor.getString(cursor.getColumnIndexOrThrow(Field.PROGRAMNAME));
                    bookLive.code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                    bookLive.play_time = cursor.getString(cursor.getColumnIndexOrThrow("play_time"));
                    bookLive.launchMode = cursor.getString(cursor.getColumnIndexOrThrow("launch_mode"));
                    bookLive.id = cursor.getString(cursor.getColumnIndexOrThrow(Field.LIVE_ID));
                    list.add(bookLive);
                } catch (Throwable th2) {
                    th = th2;
                    ArrayList<PushBookLive> arrayList = list;
                }
            }
            LetvTools.closeCursor(cursor);
            return list;
        } catch (Throwable th3) {
            th = th3;
            LetvTools.closeCursor(cursor);
            throw th;
        }
    }

    public ArrayList<PushBookLive> getAllTrace() {
        Throwable th;
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_LIVEBOOKTRACE, null, null, null, null);
            ArrayList<PushBookLive> list = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    PushBookLive bookLive = new PushBookLive();
                    bookLive.md5_id = cursor.getString(cursor.getColumnIndexOrThrow(Field.MD5_ID));
                    bookLive.channelName = cursor.getString(cursor.getColumnIndexOrThrow(Field.CHANNELNAME));
                    bookLive.programName = cursor.getString(cursor.getColumnIndexOrThrow(Field.PROGRAMNAME));
                    bookLive.code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                    bookLive.play_time = cursor.getString(cursor.getColumnIndexOrThrow("play_time"));
                    bookLive.launchMode = cursor.getString(cursor.getColumnIndexOrThrow("launch_mode"));
                    bookLive.id = cursor.getString(cursor.getColumnIndexOrThrow(Field.LIVE_ID));
                    list.add(bookLive);
                } catch (Throwable th2) {
                    th = th2;
                    ArrayList<PushBookLive> arrayList = list;
                }
            }
            LetvTools.closeCursor(cursor);
            return list;
        } catch (Throwable th3) {
            th = th3;
            LetvTools.closeCursor(cursor);
            throw th;
        }
    }

    public void clearAll() {
        this.context.getContentResolver().delete(LetvContentProvider.URI_LIVEBOOKTRACE, null, null);
    }

    public void remove(String programName, String channelName, String code, String play_time) {
        String md5_id = MD5.toMd5(programName + code + play_time);
        this.context.getContentResolver().delete(LetvContentProvider.URI_LIVEBOOKTRACE, "md5=?", new String[]{md5_id + ""});
    }

    public void remove(String md5_id) {
        this.context.getContentResolver().delete(LetvContentProvider.URI_LIVEBOOKTRACE, "md5=?", new String[]{md5_id + ""});
    }

    public void update(String programName, String channelName, String code, String play_time, boolean isNotify) {
        int in;
        if (isNotify) {
            in = 1;
        } else {
            in = 0;
        }
        String md5_id = MD5.toMd5(programName + code + play_time);
        ContentValues cv = new ContentValues();
        cv.put(Field.IS_NOTIFY, Integer.valueOf(in));
        this.context.getContentResolver().update(LetvContentProvider.URI_LIVEBOOKTRACE, cv, "md5=?", new String[]{md5_id + ""});
    }
}
