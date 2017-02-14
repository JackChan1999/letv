package com.letv.core.contentprovider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class UserInfoTools {
    public static final String LOGINOUT_ACTION = "com.letv.android.client.logout";
    public static final String LOGIN_ACTION = "com.letv.android.client.login";

    public static void copyUpgradeUserInfo(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r0 = r9.getContentResolver();
        r1 = "fornia";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "更新用户数据 copyUpgradeUserInfo:";
        r2 = r2.append(r3);
        r2 = r2.append(r10);
        r2 = r2.append(r11);
        r2 = r2.append(r12);
        r2 = r2.append(r13);
        r2 = r2.toString();
        com.letv.core.utils.LogInfo.log(r1, r2);
        r8 = new android.content.ContentValues;
        r8.<init>();
        r1 = "userId";
        r2 = android.text.TextUtils.isEmpty(r10);
        if (r2 == 0) goto L_0x0039;
    L_0x0037:
        r10 = "";
    L_0x0039:
        r8.put(r1, r10);
        r1 = "token";
        r2 = android.text.TextUtils.isEmpty(r11);
        if (r2 == 0) goto L_0x0047;
    L_0x0045:
        r11 = "";
    L_0x0047:
        r8.put(r1, r11);
        r1 = "shareUserId";
        r2 = android.text.TextUtils.isEmpty(r12);
        if (r2 == 0) goto L_0x0055;
    L_0x0053:
        r12 = "";
    L_0x0055:
        r8.put(r1, r12);
        r1 = "shareToken";
        r2 = android.text.TextUtils.isEmpty(r13);
        if (r2 == 0) goto L_0x0063;
    L_0x0061:
        r13 = "";
    L_0x0063:
        r8.put(r1, r13);
        r6 = 0;
        r1 = com.letv.core.contentprovider.UserInfoContentProvider.URI_USERINFO;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r2 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r3 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r4 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r5 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r1 = r6.getCount();	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        if (r1 != 0) goto L_0x0082;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
    L_0x0077:
        r1 = com.letv.core.contentprovider.UserInfoContentProvider.URI_USERINFO;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r0.insert(r1, r8);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
    L_0x007c:
        if (r6 == 0) goto L_0x0081;
    L_0x007e:
        r6.close();
    L_0x0081:
        return;
    L_0x0082:
        r1 = com.letv.core.contentprovider.UserInfoContentProvider.URI_USERINFO;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r2 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r3 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        r0.update(r1, r8, r2, r3);	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        goto L_0x007c;
    L_0x008a:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ Exception -> 0x008a, all -> 0x0094 }
        if (r6 == 0) goto L_0x0081;
    L_0x0090:
        r6.close();
        goto L_0x0081;
    L_0x0094:
        r1 = move-exception;
        if (r6 == 0) goto L_0x009a;
    L_0x0097:
        r6.close();
    L_0x009a:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.contentprovider.UserInfoTools.copyUpgradeUserInfo(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void login(android.content.Context r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r0 = r10.getContentResolver();
        r9 = new android.content.ContentValues;
        r9.<init>();
        r1 = "userId";
        r9.put(r1, r11);
        r1 = "token";
        r9.put(r1, r12);
        r1 = "shareUserId";
        r9.put(r1, r13);
        r1 = "shareToken";
        r9.put(r1, r14);
        r6 = 0;
        r1 = com.letv.core.contentprovider.UserInfoContentProvider.URI_USERINFO;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r2 = 0;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r3 = 0;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r4 = 0;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r5 = 0;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r6 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = r6.getCount();	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        if (r1 != 0) goto L_0x0068;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
    L_0x0032:
        r1 = com.letv.core.contentprovider.UserInfoContentProvider.URI_USERINFO;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r0.insert(r1, r9);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "zhuqiao";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r2 = "插入登录用户数据";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        com.letv.core.utils.LogInfo.log(r1, r2);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
    L_0x0040:
        r8 = new android.content.Intent;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "com.letv.android.client.login";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r8.<init>(r1);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "userId";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r8.putExtra(r1, r11);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "token";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r8.putExtra(r1, r12);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "shareUserId";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r8.putExtra(r1, r13);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "shareToken";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r8.putExtra(r1, r14);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r10.sendBroadcast(r8);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        if (r6 == 0) goto L_0x0067;
    L_0x0064:
        r6.close();
    L_0x0067:
        return;
    L_0x0068:
        r1 = com.letv.core.contentprovider.UserInfoContentProvider.URI_USERINFO;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r2 = 0;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r3 = 0;	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r0.update(r1, r9, r2, r3);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r1 = "zhuqiao";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        r2 = "更新登录用户数据";	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        com.letv.core.utils.LogInfo.log(r1, r2);	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        goto L_0x0040;
    L_0x0079:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ Exception -> 0x0079, all -> 0x0083 }
        if (r6 == 0) goto L_0x0067;
    L_0x007f:
        r6.close();
        goto L_0x0067;
    L_0x0083:
        r1 = move-exception;
        if (r6 == 0) goto L_0x0089;
    L_0x0086:
        r6.close();
    L_0x0089:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.contentprovider.UserInfoTools.login(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void logout(Context context) {
        try {
            context.getContentResolver().delete(UserInfoContentProvider.URI_USERINFO, null, null);
            context.sendBroadcast(new Intent(LOGINOUT_ACTION));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean isUserExist(Context context) {
        boolean z;
        synchronized (UserInfoTools.class) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(UserInfoContentProvider.URI_USERINFO, null, null, null, null);
                z = cursor != null && cursor.getCount() > 0;
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
                z = false;
            } catch (Throwable th) {
                if (!(cursor == null || cursor.isClosed())) {
                    cursor.close();
                }
            }
        }
        return z;
    }
}
