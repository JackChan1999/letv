package cn.jpush.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.jpush.android.util.z;

public final class o {
    public static final String[] a;
    private static o e;
    private static Object f = new Object();
    private static final String[] z;
    private Context b;
    private p c;
    private SQLiteDatabase d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 21;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "P\u0005h0\u0018V\u001fC\fD|@\u0007";
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
            case 0: goto L_0x011a;
            case 1: goto L_0x011e;
            case 2: goto L_0x0122;
            case 3: goto L_0x0126;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
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
            case 12: goto L_0x00b3;
            case 13: goto L_0x00be;
            case 14: goto L_0x00c9;
            case 15: goto L_0x00d4;
            case 16: goto L_0x00df;
            case 17: goto L_0x00ea;
            case 18: goto L_0x00f5;
            case 19: goto L_0x0100;
            case 20: goto L_0x012a;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "P\u0005h \u0018Q\u0005h8\u0012ZL\u0010";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "P\u0005h \u0018V\u0003T6";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "I\u0001B \u001f|\u0002C2\u0003J\u0002C:\u0014P";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "P\u0005h5\u0016J\u001dR7";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "P\u0005h0\u0018V\u001fC\fF";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "P\u0005h0\u0018M\u001fh:\u0007";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "P\u0005h=\u0012W";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "P\u0005h0\u0018V\u001fC\fF|B";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "P\u0005h \u0018Q\u0005h8\u0012Z";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "P\u0005h'\u0018W\u0010[";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "P\u0005h0\u0018V\u001fC\fF\u0013";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "P\u0005h?\u0018@\u0010[\f\u0013M\u0002";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "P\u0014[6\u0014WQ\u001ds\u0011Q\u001eZs\u001dS\u0004D;(P\u0005V'\u001eP\u0005^0\u0004\u0003\u0006_6\u0005FQD'(W\u001eC2\u001b\u0003O\u0017cWB\u001fSs\u0004W.Q2\u001eO\u0014SsJ\u0003A\u0017s\u0018Q\u0015R!WA\b\u0017 \u0003|\u0005X'\u0016OQS6\u0004@Q[:\u001aJ\u0005\u0017`";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "P\u0014[6\u0014WQt\u001c\"m%\u001f \u0003|\u0002X!\u0003|\u001aR*^\u0003\u0017E<\u001a\u0003\u001bG&\u0004K.D'\u0016W\u0018D'\u001e@\u0002\u0017$\u001fF\u0003Rs\u0004W.D<\u0005W.\\6\u000e\u001eV";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "G\u0014[6\u0003FQQ!\u0018NQ]#\u0002P\u0019h \u0003B\u0005^ \u0003J\u0012D";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "P\u0014[6\u0014WQ\u001ds\u0011Q\u001eZs\u001dS\u0004D;(P\u0005V'\u001eP\u0005^0\u0004\u0003\u0006_6\u0005FQD'(E\u0010^?\u0012GQ\tsG\u0003QX!\u0013F\u0003\u00171\u000e\u0003\u0002C\f\u0011B\u0018[6\u0013\u0003\u0015R \u0014\u0003\u001d^>\u001eWQ\u0004";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "P\u0014[6\u0014WQd\u0006:\u000b";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "\nQQ!\u0018NQ]#\u0002P\u0019h \u0003B\u0005^ \u0003J\u0012D";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "L\u0001R=Wq\u0014V7\u0016A\u001dR\u0017\u0016W\u0010u2\u0004FQQ2\u001eOQRi";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "L\u0001R=WL\u0001R= Q\u0018C2\u0015O\u0014s2\u0003B\u0013V \u0012\u0003\u0017V:\u001b\u0003\u0014\r";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        z = r4;
        r0 = 12;
        r3 = new java.lang.String[r0];
        r0 = 0;
        r1 = z;
        r2 = 9;
        r1 = r1[r2];
        r3[r0] = r1;
        r2 = 1;
        r1 = "|\u0018S";
        r0 = 20;
        r4 = r3;
        goto L_0x0009;
    L_0x011a:
        r9 = 35;
        goto L_0x0020;
    L_0x011e:
        r9 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0020;
    L_0x0122:
        r9 = 55;
        goto L_0x0020;
    L_0x0126:
        r9 = 83;
        goto L_0x0020;
    L_0x012a:
        r3[r2] = r1;
        r0 = 2;
        r1 = z;
        r2 = 7;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 3;
        r1 = z;
        r2 = 6;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 4;
        r1 = z;
        r2 = 12;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 5;
        r1 = z;
        r2 = 2;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 6;
        r1 = z;
        r2 = 4;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 7;
        r1 = z;
        r2 = 10;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 8;
        r1 = z;
        r2 = 5;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 9;
        r1 = z;
        r2 = 8;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 10;
        r1 = z;
        r2 = 0;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 11;
        r1 = z;
        r2 = 11;
        r1 = r1[r2];
        r4[r0] = r1;
        a = r4;
        r0 = new java.lang.Object;
        r0.<init>();
        f = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.o.<clinit>():void");
    }

    private o(Context context) {
        this.b = context;
        this.c = new p(context);
    }

    public static o a(Context context) {
        synchronized (f) {
            if (e == null) {
                e = new o(context);
            }
        }
        return e;
    }

    private synchronized boolean e() {
        boolean z;
        z = false;
        try {
            this.d = this.c.getWritableDatabase();
            z = true;
        } catch (Exception e) {
            new StringBuilder(z[20]).append(e.getMessage());
            z.e();
        }
        return z;
    }

    private synchronized boolean f() {
        boolean z;
        z = false;
        try {
            this.d = this.c.getReadableDatabase();
            z = true;
        } catch (Exception e) {
            new StringBuilder(z[19]).append(e.getMessage());
            z.e();
        }
        return z;
    }

    public final synchronized long a(String str, String str2, String str3, String str4, String str5, int i, int i2, int i3, int i4, int i5, int i6) {
        long j;
        j = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(z[9], str);
        contentValues.put(z[7], str2);
        contentValues.put(z[6], str3);
        contentValues.put(z[12], str4);
        contentValues.put(z[2], str5);
        contentValues.put(z[4], Integer.valueOf(i));
        contentValues.put(z[10], Integer.valueOf(1));
        contentValues.put(z[5], Integer.valueOf(i3));
        contentValues.put(z[8], Integer.valueOf(i4));
        contentValues.put(z[0], Integer.valueOf(i5));
        contentValues.put(z[11], Integer.valueOf(0));
        try {
            j = this.d.insert(z[3], null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    public final synchronized q a(Cursor cursor) {
        q qVar;
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                qVar = new q();
                try {
                    qVar.a(cursor.getString(1));
                    qVar.b(cursor.getString(2));
                    qVar.c(cursor.getString(3));
                    qVar.d(cursor.getString(4));
                    qVar.e(cursor.getString(5));
                    qVar.a(cursor.getInt(6));
                    qVar.b(cursor.getInt(7));
                    qVar.c(cursor.getInt(8));
                    qVar.d(cursor.getInt(9));
                    qVar.e(cursor.getInt(10));
                    qVar.f(cursor.getInt(11));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                qVar.toString();
                z.c();
            }
        }
        z.b();
        qVar = null;
        return qVar;
    }

    public final synchronized void a() {
        if (this.d != null) {
            this.d.close();
        }
    }

    public final synchronized boolean a(String str) {
        Cursor cursor = null;
        boolean z = false;
        synchronized (this) {
            try {
                cursor = this.d.rawQuery(new StringBuilder(z[14]).append(str).append("'").toString(), null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    if (cursor.getInt(0) != 0) {
                        z = true;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return z;
    }

    public final synchronized boolean a(boolean z) {
        return z ? e() : f();
    }

    public final synchronized int b(boolean z) {
        Cursor cursor = null;
        int i = 0;
        synchronized (this) {
            String str = z[4];
            if (z) {
                str = z[10];
            }
            try {
                cursor = this.d.rawQuery(new StringBuilder(z[17]).append(str).append(z[18]).toString(), null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    i = cursor.getInt(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    public final synchronized Cursor b(String str) {
        Cursor query;
        Exception e;
        try {
            query = this.d.query(true, z[3], a, new StringBuilder(z[1]).append(str).append("'").toString(), null, null, null, null, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return query;
                }
            }
        } catch (Exception e3) {
            e = e3;
            query = null;
            e.printStackTrace();
            return query;
        }
        return query;
    }

    public final synchronized void b() {
        try {
            this.d.execSQL(z[15]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final synchronized boolean b(String str, String str2, String str3, String str4, String str5, int i, int i2, int i3, int i4, int i5, int i6) {
        boolean z;
        String stringBuilder = new StringBuilder(z[1]).append(str).append("'").toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(z[9], str);
        contentValues.put(z[7], str2);
        contentValues.put(z[6], str3);
        contentValues.put(z[12], str4);
        contentValues.put(z[2], str5);
        contentValues.put(z[4], Integer.valueOf(i));
        contentValues.put(z[10], Integer.valueOf(i2));
        contentValues.put(z[5], Integer.valueOf(i3));
        contentValues.put(z[8], Integer.valueOf(i4));
        contentValues.put(z[0], Integer.valueOf(i5));
        contentValues.put(z[11], Integer.valueOf(i6));
        z = false;
        try {
            z = this.d.update(z[3], contentValues, stringBuilder, null) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public final synchronized Cursor c() {
        Cursor rawQuery;
        Exception e;
        try {
            rawQuery = this.d.rawQuery(z[16], null);
            if (rawQuery != null) {
                try {
                    rawQuery.moveToFirst();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return rawQuery;
                }
            }
        } catch (Exception e3) {
            Exception exception = e3;
            rawQuery = null;
            e = exception;
            e.printStackTrace();
            return rawQuery;
        }
        return rawQuery;
    }

    public final synchronized Cursor d() {
        Cursor rawQuery;
        Exception e;
        try {
            rawQuery = this.d.rawQuery(z[13], null);
            if (rawQuery != null) {
                try {
                    rawQuery.moveToFirst();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return rawQuery;
                }
            }
        } catch (Exception e3) {
            Exception exception = e3;
            rawQuery = null;
            e = exception;
            e.printStackTrace();
            return rawQuery;
        }
        return rawQuery;
    }
}
