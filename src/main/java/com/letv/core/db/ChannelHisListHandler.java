package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveBeanLeChannelList;
import com.letv.core.constant.DatabaseConstant.ChannelHisListTrace.Field;
import com.letv.core.constant.DatabaseConstant.ChannelListTrace.ChannelStatus;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;

public class ChannelHisListHandler {
    private Context mContext;

    public boolean hasHisTableData(java.lang.String r12) {
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
        r8 = 1;
        r9 = 0;
        r6 = 0;
        r0 = r11.mContext;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r1 = com.letv.core.db.LetvContentProvider.URI_CHANNELHISLISTTRACE;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r2 = 0;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r3 = "isRecord= ? and channel_type=?";	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r4 = 2;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r5 = 0;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r10 = "1";	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r4[r5] = r10;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r5 = 1;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r4[r5] = r12;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r5 = 0;	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        r0 = r6.getCount();	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        if (r0 <= 0) goto L_0x0029;
    L_0x0024:
        r0 = r8;
    L_0x0025:
        com.letv.core.utils.LetvTools.closeCursor(r6);
    L_0x0028:
        return r0;
    L_0x0029:
        r0 = r9;
        goto L_0x0025;
    L_0x002b:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ Exception -> 0x002b, all -> 0x0034 }
        com.letv.core.utils.LetvTools.closeCursor(r6);
        r0 = r9;
        goto L_0x0028;
    L_0x0034:
        r0 = move-exception;
        com.letv.core.utils.LetvTools.closeCursor(r6);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.db.ChannelHisListHandler.hasHisTableData(java.lang.String):boolean");
    }

    public ChannelHisListHandler(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized boolean updateByChannelId(LiveBeanLeChannel liveLunboWeishi, String type) {
        boolean z = true;
        synchronized (this) {
            if (liveLunboWeishi != null) {
                try {
                    ContentValues values = new ContentValues();
                    values.put("channelid", liveLunboWeishi.channelId);
                    values.put("numericKeys", liveLunboWeishi.numericKeys);
                    values.put("name", liveLunboWeishi.channelName);
                    values.put("ename", liveLunboWeishi.channelEname);
                    values.put("signal", liveLunboWeishi.signal);
                    values.put("channelIcon", liveLunboWeishi.channelIcon);
                    values.put(Field.ISRECORD, Integer.valueOf(liveLunboWeishi.isRecord));
                    values.put(Field.SYSTEMILLISECOND, Long.valueOf(liveLunboWeishi.currentmillisecond));
                    this.mContext.getContentResolver().update(LetvContentProvider.URI_CHANNELHISLISTTRACE, values, "channelid=? and channel_type=?", new String[]{liveLunboWeishi.channelId + "", type});
                } catch (Exception e) {
                    e.printStackTrace();
                    z = false;
                }
            } else {
                z = false;
            }
        }
        return z;
    }

    public synchronized boolean updateByNumberkeys(LiveBeanLeChannel liveLunboWeishi, String channelStatus, String type) {
        boolean z = true;
        synchronized (this) {
            if (liveLunboWeishi != null) {
                try {
                    ContentValues values = new ContentValues();
                    values.put("channelid", liveLunboWeishi.channelId);
                    values.put("numericKeys", liveLunboWeishi.numericKeys);
                    values.put("name", liveLunboWeishi.channelName);
                    values.put("ename", liveLunboWeishi.channelEname);
                    values.put("signal", liveLunboWeishi.signal);
                    values.put("channelIcon", liveLunboWeishi.channelIcon);
                    values.put(Field.ISRECORD, Integer.valueOf(liveLunboWeishi.isRecord));
                    values.put(Field.SYSTEMILLISECOND, Long.valueOf(liveLunboWeishi.currentmillisecond));
                    values.put("channelstatus", channelStatus);
                    this.mContext.getContentResolver().update(LetvContentProvider.URI_CHANNELHISLISTTRACE, values, "numericKeys=? and channel_type=?", new String[]{liveLunboWeishi.numericKeys + "", type});
                } catch (Exception e) {
                    e.printStackTrace();
                    z = false;
                }
            } else {
                z = false;
            }
        }
        return z;
    }

    public synchronized boolean updateChannelStatus(LiveBeanLeChannel channel, String channelStatus, String type) {
        boolean z = true;
        synchronized (this) {
            ContentValues values = new ContentValues();
            values.put("channelid", channel.channelId);
            values.put("numericKeys", channel.numericKeys);
            values.put("name", channel.channelName);
            values.put("ename", channel.channelEname);
            values.put("channelstatus", channelStatus);
            values.put("channel_type", type);
            values.put("signal", channel.signal);
            values.put("channelIcon", channel.channelIcon);
            try {
                if (this.mContext.getContentResolver().update(LetvContentProvider.URI_CHANNELHISLISTTRACE, values, "channelid=? and channel_type=?", new String[]{channel.channelId, type}) != 1) {
                    z = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                z = false;
            }
        }
        return z;
    }

    public synchronized boolean delete(LiveBeanLeChannel channel, String type) {
        boolean z = true;
        synchronized (this) {
            try {
                if (this.mContext.getContentResolver().delete(LetvContentProvider.URI_CHANNELHISLISTTRACE, "channelid=? and channel_type=?", new String[]{channel.channelId, type}) <= 0) {
                    z = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                z = false;
            }
        }
        return z;
    }

    public boolean updateHisChannel(String channelId, String type) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Field.ISRECORD, Integer.valueOf(1));
            cv.put(Field.SYSTEMILLISECOND, Long.valueOf(System.currentTimeMillis()));
            this.mContext.getContentResolver().update(LetvContentProvider.URI_CHANNELHISLISTTRACE, cv, "channelid=? and channel_type=?", new String[]{channelId, type});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LiveBeanLeChannel getLastHisChannel(String type) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "channel_type=?", new String[]{type}, "systemmillisecond desc");
            if (cursor == null || !cursor.moveToFirst()) {
                LetvTools.closeCursor(cursor);
                return null;
            }
            LiveBeanLeChannel livelunboweishi = new LiveBeanLeChannel();
            livelunboweishi.channelId = cursor.getString(cursor.getColumnIndexOrThrow("channelid"));
            livelunboweishi.numericKeys = cursor.getString(cursor.getColumnIndexOrThrow("numericKeys"));
            livelunboweishi.channelName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            livelunboweishi.channelEname = cursor.getString(cursor.getColumnIndexOrThrow("ename"));
            livelunboweishi.signal = cursor.getString(cursor.getColumnIndexOrThrow("signal"));
            livelunboweishi.channelIcon = cursor.getString(cursor.getColumnIndexOrThrow("channelIcon"));
            livelunboweishi.currentmillisecond = cursor.getLong(cursor.getColumnIndexOrThrow(Field.SYSTEMILLISECOND));
            return livelunboweishi;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public LiveBeanLeChannelList getHisChannelList(String type) {
        Exception e;
        Throwable th;
        Cursor cursor = null;
        LiveBeanLeChannelList lunboWeishiList = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "isRecord= ?  and channel_type=?", new String[]{"1", type}, null);
            LiveBeanLeChannelList lunboWeishiList2 = new LiveBeanLeChannelList();
            while (cursor.moveToNext()) {
                try {
                    LiveBeanLeChannel livelunboweishi = new LiveBeanLeChannel();
                    livelunboweishi.channelId = cursor.getString(cursor.getColumnIndexOrThrow("channelid"));
                    if ("weishi".equals(type)) {
                        livelunboweishi.numericKeys = "";
                    } else {
                        livelunboweishi.numericKeys = cursor.getString(cursor.getColumnIndexOrThrow("numericKeys"));
                    }
                    livelunboweishi.channelName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    livelunboweishi.channelEname = cursor.getString(cursor.getColumnIndexOrThrow("ename"));
                    livelunboweishi.signal = cursor.getString(cursor.getColumnIndexOrThrow("signal"));
                    livelunboweishi.channelIcon = cursor.getString(cursor.getColumnIndexOrThrow("channelIcon"));
                    livelunboweishi.currentmillisecond = cursor.getLong(cursor.getColumnIndexOrThrow(Field.SYSTEMILLISECOND));
                    lunboWeishiList2.add(livelunboweishi);
                } catch (Exception e2) {
                    e = e2;
                    lunboWeishiList = lunboWeishiList2;
                } catch (Throwable th2) {
                    th = th2;
                    lunboWeishiList = lunboWeishiList2;
                }
            }
            LetvTools.closeCursor(cursor);
            return lunboWeishiList2;
        } catch (Exception e3) {
            e = e3;
            try {
                e.printStackTrace();
                LetvTools.closeCursor(cursor);
                return lunboWeishiList;
            } catch (Throwable th3) {
                th = th3;
                LetvTools.closeCursor(cursor);
                throw th;
            }
        }
    }

    public LiveBeanLeChannelList getAllChannelList(String type) {
        Exception e;
        Throwable th;
        Cursor cursor = null;
        LiveBeanLeChannelList lunboWeishiList = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "channelstatus= ? and channel_type=?", new String[]{ChannelStatus.NORMAL, type}, null);
            LiveBeanLeChannelList lunboWeishiList2 = new LiveBeanLeChannelList();
            while (cursor.moveToNext()) {
                try {
                    LiveBeanLeChannel livelunboweishi = new LiveBeanLeChannel();
                    livelunboweishi.channelId = cursor.getString(cursor.getColumnIndexOrThrow("channelid"));
                    livelunboweishi.numericKeys = cursor.getString(cursor.getColumnIndexOrThrow("numericKeys"));
                    livelunboweishi.channelName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    livelunboweishi.channelEname = cursor.getString(cursor.getColumnIndexOrThrow("ename"));
                    livelunboweishi.signal = cursor.getString(cursor.getColumnIndexOrThrow("signal"));
                    livelunboweishi.channelIcon = cursor.getString(cursor.getColumnIndexOrThrow("channelIcon"));
                    livelunboweishi.currentmillisecond = cursor.getLong(cursor.getColumnIndexOrThrow(Field.SYSTEMILLISECOND));
                    lunboWeishiList2.add(livelunboweishi);
                } catch (Exception e2) {
                    e = e2;
                    lunboWeishiList = lunboWeishiList2;
                } catch (Throwable th2) {
                    th = th2;
                    lunboWeishiList = lunboWeishiList2;
                }
            }
            LetvTools.closeCursor(cursor);
            return lunboWeishiList2;
        } catch (Exception e3) {
            e = e3;
            try {
                e.printStackTrace();
                LetvTools.closeCursor(cursor);
                return lunboWeishiList;
            } catch (Throwable th3) {
                th = th3;
                LetvTools.closeCursor(cursor);
                throw th;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean addToChannelList(com.letv.core.bean.LiveBeanLeChannel r6, java.lang.String r7) {
        /*
        r5 = this;
        r2 = 1;
        monitor-enter(r5);
        if (r6 == 0) goto L_0x000e;
    L_0x0004:
        r3 = r6.channelId;	 Catch:{ Exception -> 0x005a }
        r3 = r5.isExist(r3, r7);	 Catch:{ Exception -> 0x005a }
        if (r3 == 0) goto L_0x000e;
    L_0x000c:
        monitor-exit(r5);
        return r2;
    L_0x000e:
        r1 = new android.content.ContentValues;	 Catch:{ Exception -> 0x005a }
        r1.<init>();	 Catch:{ Exception -> 0x005a }
        r3 = "channelid";
        r4 = r6.channelId;	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = "numericKeys";
        r4 = r6.numericKeys;	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = "name";
        r4 = r6.channelName;	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = "ename";
        r4 = r6.channelEname;	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = "channel_type";
        r1.put(r3, r7);	 Catch:{ Exception -> 0x005a }
        r3 = "signal";
        r4 = r6.signal;	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = "channelIcon";
        r4 = r6.channelIcon;	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = "isRecord";
        r4 = r6.isRecord;	 Catch:{ Exception -> 0x005a }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Exception -> 0x005a }
        r1.put(r3, r4);	 Catch:{ Exception -> 0x005a }
        r3 = r5.mContext;	 Catch:{ Exception -> 0x005a }
        r3 = r3.getContentResolver();	 Catch:{ Exception -> 0x005a }
        r4 = com.letv.core.db.LetvContentProvider.URI_CHANNELHISLISTTRACE;	 Catch:{ Exception -> 0x005a }
        r3.insert(r4, r1);	 Catch:{ Exception -> 0x005a }
        goto L_0x000c;
    L_0x005a:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0060 }
        r2 = 0;
        goto L_0x000c;
    L_0x0060:
        r2 = move-exception;
        monitor-exit(r5);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.db.ChannelHisListHandler.addToChannelList(com.letv.core.bean.LiveBeanLeChannel, java.lang.String):boolean");
    }

    public boolean isExist(String channelId, String type) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "channelid=? and channel_type=?", new String[]{channelId + "", type}, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
            LetvTools.closeCursor(cursor);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public boolean isExistByNumberKeysAndStatus(String numberKeys, String type) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "numericKeys=? and channelstatus= ? and channel_type=?", new String[]{numberKeys + "", ChannelStatus.DELETE, type}, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
            LetvTools.closeCursor(cursor);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public int getFirstChannelPosition(String type) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "channelstatus= ? and channel_type=?", new String[]{ChannelStatus.NORMAL, type}, null);
            if (cursor == null || cursor.getCount() <= 0) {
                LetvTools.closeCursor(cursor);
                return 0;
            }
            cursor.moveToNext();
            int i = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public int getCurrentChannelPosition(String curChannelName, String type) {
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "name= ? and channel_type=?", new String[]{curChannelName, type}, null);
            if (cursor == null || cursor.getCount() <= 0) {
                LetvTools.closeCursor(cursor);
                return -1;
            }
            cursor.moveToNext();
            int i = cursor.getInt(0);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
    }

    public LiveBeanLeChannel getNextChannel(String curChannelName, String type) {
        Cursor cursor = null;
        try {
            int position = getCurrentChannelPosition(curChannelName, type);
            LogInfo.log("ljnalex", "getNextChannel position = " + position);
            if (position != -1) {
                while (true) {
                    cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "_id = ? and channel_type=?", new String[]{(position + 1) + "", type}, null);
                    cursor.moveToNext();
                    if (!cursor.getString(cursor.getColumnIndexOrThrow("channelstatus")).equals(ChannelStatus.DELETE)) {
                        break;
                    }
                }
                if (cursor != null && cursor.getCount() > 0) {
                    LiveBeanLeChannel createChannel = createChannel(cursor);
                    return createChannel;
                }
            }
            LetvTools.closeCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
        return null;
    }

    public LiveBeanLeChannel getPreChannel(String curChannelName, String type) {
        Cursor cursor = null;
        try {
            int position = getCurrentChannelPosition(curChannelName, type);
            LogInfo.log("ljnalex", "getPreChannel position = " + position);
            if (position != -1) {
                while (true) {
                    cursor = this.mContext.getContentResolver().query(LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "_id = ? and channel_type=?", new String[]{(position - 1) + "", type}, null);
                    cursor.moveToLast();
                    if (!cursor.getString(cursor.getColumnIndexOrThrow("channelstatus")).equals(ChannelStatus.DELETE)) {
                        break;
                    }
                }
                if (cursor != null && cursor.getCount() > 0) {
                    LiveBeanLeChannel createChannel = createChannel(cursor);
                    return createChannel;
                }
            }
            LetvTools.closeCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
        return null;
    }

    private LiveBeanLeChannel createChannel(Cursor cursor) {
        LiveBeanLeChannel livelunboweishi = new LiveBeanLeChannel();
        try {
            livelunboweishi.channelId = cursor.getString(cursor.getColumnIndexOrThrow("channelid"));
            livelunboweishi.numericKeys = cursor.getString(cursor.getColumnIndexOrThrow("numericKeys"));
            livelunboweishi.channelName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            livelunboweishi.channelEname = cursor.getString(cursor.getColumnIndexOrThrow("ename"));
            livelunboweishi.signal = cursor.getString(cursor.getColumnIndexOrThrow("signal"));
            livelunboweishi.channelIcon = cursor.getString(cursor.getColumnIndexOrThrow("channelIcon"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LetvTools.closeCursor(cursor);
        }
        return livelunboweishi;
    }
}
