package cn.jpush.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cn.jpush.android.util.z;

public final class b extends SQLiteOpenHelper {
    public static final String[] a;
    public static final String[] b;
    private static b c;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 13;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Y\u0013s\nE\\";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x00b7;
            case 1: goto L_0x00bb;
            case 2: goto L_0x00bf;
            case 3: goto L_0x00c2;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 44;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0074;
            case 7: goto L_0x007d;
            case 8: goto L_0x0087;
            case 9: goto L_0x0092;
            case 10: goto L_0x009d;
            case 11: goto L_0x00a8;
            case 12: goto L_0x00c6;
            case 13: goto L_0x00d8;
            case 14: goto L_0x00e2;
            case 15: goto L_0x00ec;
            case 16: goto L_0x00f6;
            case 17: goto L_0x0105;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "U\u0010d\nE\\";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "W\u0015f'^Q\u0007f\nE\\";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "U\u0002j;sQ\u0007";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "Y\u0013s\nE\\^<";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "M\u0013o<_L";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "[\u0011f4X]Cw4NT\u0006# \\T\np!\u0004g\nguEV\u0017f2IJCs'EU\u0002q,\fS\u0006zuMM\u0017l<B[\u0011f8IV\u0017/8__<j1\fL\u0006{!\u0000Y\u0013s\nE\\Cw0TLOn4EV<j1\fL\u0006{!\u0000\u0018\fu0^J\ng0sQ\u0007#!I@\u0017*";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "[\u0011f4X]Cw4NT\u0006#1CO\ro<_LK\\<H\u0018\nm!I_\u0006qu\\J\nn4^ACh0U\u0018\u0002v!CQ\r`'IU\u0006m!\u0000U\u0010d\nE\\Cw0TLOq0\\]\u0002w\nBM\u000e#<BL\u0006d0^\u0014\u0010w4^L<s:_\u0018\nm!I_\u0006qyIV\u0007\\%CKCj;X]\u0004f'\u0000[\fm!IV\u0017#!I@\u0017*";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "K\u0006q#E[\u0006-1N";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\\\u0011l%\fL\u0002a9I\u0018\u0007l\"BT\np!";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "l\u000bfuCT\u0007U0^K\nl;\fQ\u00109u";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\\\u0011l%\fL\u0002a9I\u0018\u0016s9EK\u0017";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0018\u0017k0\fV\u0006t\u0003IJ\u0010j:B\u0018\npu\u0016\u0018";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        z = r4;
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "g\ng";
        r0 = 12;
        r4 = r3;
        goto L_0x0009;
    L_0x00b7:
        r9 = 56;
        goto L_0x0020;
    L_0x00bb:
        r9 = 99;
        goto L_0x0020;
    L_0x00bf:
        r9 = 3;
        goto L_0x0020;
    L_0x00c2:
        r9 = 85;
        goto L_0x0020;
    L_0x00c6:
        r3[r2] = r1;
        r0 = 1;
        r1 = z;
        r2 = 1;
        r1 = r1[r2];
        r4[r0] = r1;
        r2 = 2;
        r1 = "J\u0006s0ML<m A";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00d8:
        r3[r2] = r1;
        r2 = 3;
        r1 = "K\u0017b'Xg\u0013l&";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00e2:
        r3[r2] = r1;
        r2 = 4;
        r1 = "]\rg\n\\W\u0010";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00ec:
        r3[r2] = r1;
        r2 = 5;
        r1 = "[\fm!IV\u0017";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00f6:
        r3[r2] = r1;
        a = r4;
        r0 = 4;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "g\ng";
        r0 = 17;
        r4 = r3;
        goto L_0x0009;
    L_0x0105:
        r3[r2] = r1;
        r0 = 1;
        r1 = z;
        r2 = 1;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 2;
        r1 = z;
        r2 = 0;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 3;
        r1 = z;
        r2 = 3;
        r1 = r1[r2];
        r4[r0] = r1;
        b = r4;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.b.<clinit>():void");
    }

    private b(Context context) {
        super(context, z[8], null, 3);
    }

    private static b a(Context context) {
        if (c == null) {
            c = new b(context);
        }
        return c;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String a(android.content.Context r16, java.lang.String r17) {
        /*
        r14 = cn.jpush.android.data.b.class;
        monitor-enter(r14);
        r10 = "";
        r13 = "";
        r12 = "";
        r11 = "";
        r9 = 0;
        r1 = a(r16);	 Catch:{ Exception -> 0x00ae, all -> 0x00ba }
        r1 = r1.getWritableDatabase();	 Catch:{ Exception -> 0x00ae, all -> 0x00ba }
        r2 = z;	 Catch:{ Exception -> 0x00ae, all -> 0x00ba }
        r3 = 5;
        r2 = r2[r3];	 Catch:{ Exception -> 0x00ae, all -> 0x00ba }
        r3 = b;	 Catch:{ Exception -> 0x00ae, all -> 0x00ba }
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r2 = r1.query(r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x00ae, all -> 0x00ba }
        if (r2 == 0) goto L_0x00ca;
    L_0x0026:
        r3 = r2.getCount();	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        if (r3 < 0) goto L_0x00ca;
    L_0x002c:
        r3 = r2.moveToNext();	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        if (r3 == 0) goto L_0x00ca;
    L_0x0032:
        r3 = z;	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r4 = 0;
        r3 = r3[r4];	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = r2.getColumnIndex(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r4 = r2.getString(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = z;	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r5 = 1;
        r3 = r3[r5];	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = r2.getColumnIndex(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r6 = r2.getString(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = z;	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r5 = 3;
        r3 = r3[r5];	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = r2.getColumnIndex(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r5 = r2.getString(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = z;	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r7 = 2;
        r3 = r3[r7];	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = r2.getColumnIndex(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r3 = r2.getString(r3);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        r0 = r17;
        r7 = r0.endsWith(r4);	 Catch:{ Exception -> 0x00c4, all -> 0x00c2 }
        if (r7 == 0) goto L_0x002c;
    L_0x006e:
        r15 = r3;
        r3 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r15;
    L_0x0073:
        r7 = z;	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r8 = 5;
        r7 = r7[r8];	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r8 = z;	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r9 = 4;
        r8 = r8[r9];	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r9 = 1;
        r9 = new java.lang.String[r9];	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r10 = 0;
        r9[r10] = r5;	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1.delete(r7, r8, r9);	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1.<init>();	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r5 = ",";
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1 = r1.append(r6);	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r5 = ",";
        r1 = r1.append(r5);	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00c7, all -> 0x00c2 }
        if (r2 == 0) goto L_0x00ac;
    L_0x00a9:
        r2.close();	 Catch:{ all -> 0x00b7 }
    L_0x00ac:
        monitor-exit(r14);
        return r1;
    L_0x00ae:
        r1 = move-exception;
        r2 = r9;
        r1 = r10;
    L_0x00b1:
        if (r2 == 0) goto L_0x00ac;
    L_0x00b3:
        r2.close();	 Catch:{ all -> 0x00b7 }
        goto L_0x00ac;
    L_0x00b7:
        r1 = move-exception;
        monitor-exit(r14);
        throw r1;
    L_0x00ba:
        r1 = move-exception;
        r2 = r9;
    L_0x00bc:
        if (r2 == 0) goto L_0x00c1;
    L_0x00be:
        r2.close();	 Catch:{ all -> 0x00b7 }
    L_0x00c1:
        throw r1;	 Catch:{ all -> 0x00b7 }
    L_0x00c2:
        r1 = move-exception;
        goto L_0x00bc;
    L_0x00c4:
        r1 = move-exception;
        r1 = r10;
        goto L_0x00b1;
    L_0x00c7:
        r1 = move-exception;
        r1 = r3;
        goto L_0x00b1;
    L_0x00ca:
        r4 = r11;
        r5 = r12;
        r6 = r13;
        r3 = r10;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.b.a(android.content.Context, java.lang.String):java.lang.String");
    }

    public static synchronized void a(Context context, c cVar, String str, String str2) {
        synchronized (b.class) {
            a(context, cVar.c, cVar.d, str, str2);
        }
    }

    private static synchronized void a(Context context, String str, String str2, String str3, String str4) {
        Cursor query;
        Cursor cursor;
        Throwable th;
        synchronized (b.class) {
            try {
                ContentValues contentValues;
                SQLiteDatabase writableDatabase = a(context).getWritableDatabase();
                query = writableDatabase.query(z[5], b, z[4], new String[]{str3}, null, null, null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0) {
                            query.moveToFirst();
                            contentValues = new ContentValues();
                            contentValues.put(z[1], str);
                            contentValues.put(z[3], str4);
                            contentValues.put(z[2], str2);
                            writableDatabase.update(z[5], contentValues, z[4], new String[]{str3});
                            if (query != null) {
                                query.close();
                            }
                        }
                    } catch (Exception e) {
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                }
                contentValues = new ContentValues();
                contentValues.put(z[1], str);
                contentValues.put(z[0], str3);
                contentValues.put(z[3], str4);
                contentValues.put(z[2], str2);
                writableDatabase.insert(z[5], null, contentValues);
                if (query != null) {
                    query.close();
                }
            } catch (Exception e2) {
                cursor = null;
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th3) {
                th = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        z.b();
        sQLiteDatabase.execSQL(z[7]);
        sQLiteDatabase.execSQL(z[6]);
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        new StringBuilder(z[10]).append(i).append(z[12]).append(i2);
        z.b();
        sQLiteDatabase.execSQL(z[9]);
        sQLiteDatabase.execSQL(z[11]);
        onCreate(sQLiteDatabase);
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        new StringBuilder(z[10]).append(i).append(z[12]).append(i2);
        z.b();
        sQLiteDatabase.execSQL(z[9]);
        sQLiteDatabase.execSQL(z[11]);
        onCreate(sQLiteDatabase);
    }
}
