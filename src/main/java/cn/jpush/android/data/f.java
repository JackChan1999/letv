package cn.jpush.android.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.jpush.android.util.z;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public final class f {
    public static final String[] a;
    private static final String[] z;
    private Context b;
    private g c;
    private SQLiteDatabase d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 17;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "QEV[\u0019QEV[nL\\GT[RPwTM_WRF\\\u001eSR\\U\u001eP\t";
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
            case 0: goto L_0x00e4;
            case 1: goto L_0x00e8;
            case 2: goto L_0x00ec;
            case 3: goto L_0x00f0;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 57;
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
            case 16: goto L_0x00f4;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "Jj_ZZ_Y]ZMWSZVXJ\\\\[";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "R[l\\]";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "R[lG\\SZEP";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "R[lAKWRTPKaAZX\\";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "R[lVVK[G";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "R[l\\]\u0003";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "R[lT]ZjG\\T[";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "R[lA@NP";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "R[lPAJGR";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "R[lVVK[G\u000b\t";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "QEV[\u0019lPRQX\\YVqXJTqTJ[\u0015UTPR\u0015V\u000f";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u001eT]Q\u0019R[lAKWRTPKaAZX\\";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "R[lVVK[G\b";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\u0003\u0005";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "\u001eT]Q\u0019R[lA@NP";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "R[lVVK[G\u000b\t\u001eT]Q\u0019R[lAKWRTPKaAZX\\\u0002";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        z = r4;
        r0 = 8;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "a\\W";
        r0 = 16;
        r4 = r3;
        goto L_0x0009;
    L_0x00e4:
        r9 = 62;
        goto L_0x0020;
    L_0x00e8:
        r9 = 53;
        goto L_0x0020;
    L_0x00ec:
        r9 = 51;
        goto L_0x0020;
    L_0x00f0:
        r9 = 53;
        goto L_0x0020;
    L_0x00f4:
        r3[r2] = r1;
        r0 = 1;
        r1 = z;
        r2 = 2;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 2;
        r1 = z;
        r2 = 5;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 3;
        r1 = z;
        r2 = 3;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 4;
        r1 = z;
        r2 = 8;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 5;
        r1 = z;
        r2 = 9;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 6;
        r1 = z;
        r2 = 4;
        r1 = r1[r2];
        r4[r0] = r1;
        r0 = 7;
        r1 = z;
        r2 = 7;
        r1 = r1[r2];
        r4[r0] = r1;
        a = r4;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.f.<clinit>():void");
    }

    public f(Context context) {
        this.b = context;
        this.c = new g(context);
    }

    private synchronized boolean c() {
        boolean z = false;
        synchronized (this) {
            try {
                this.d = this.c.getWritableDatabase();
                z = true;
            } catch (Exception e) {
                new StringBuilder(z[0]).append(e.getMessage());
                z.e();
            }
        }
        return z;
    }

    private synchronized boolean d() {
        boolean z;
        z = false;
        try {
            this.d = this.c.getReadableDatabase();
            z = true;
        } catch (Exception e) {
            new StringBuilder(z[11]).append(e.getMessage());
            z.e();
        }
        return z;
    }

    public final synchronized long a(long j, int i, int i2, int i3, String str, long j2, long j3) {
        long j4;
        ContentValues contentValues = new ContentValues();
        contentValues.put(z[2], Long.valueOf(j));
        contentValues.put(z[5], Integer.valueOf(1));
        contentValues.put(z[3], Integer.valueOf(0));
        contentValues.put(z[8], Integer.valueOf(0));
        contentValues.put(z[9], str);
        contentValues.put(z[4], Long.valueOf(j2));
        contentValues.put(z[7], Long.valueOf(j3));
        j4 = 0;
        try {
            j4 = this.d.insert(z[1], null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j4;
    }

    public final synchronized Cursor a(int i, long j) {
        Cursor query;
        Exception e;
        try {
            query = this.d.query(true, z[1], a, new StringBuilder(z[13]).append(1).append(z[12]).append(SearchCriteria.LT).append(j).toString(), null, null, null, null, null);
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

    public final synchronized Cursor a(long j, int i) {
        Cursor query;
        try {
            query = this.d.query(true, z[1], a, new StringBuilder(z[6]).append(j).append(z[15]).append(z[14]).toString(), null, null, null, null, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            query = null;
        } catch (Throwable th) {
        }
        return query;
    }

    public final synchronized Cursor a(long j, long j2) {
        Cursor query;
        try {
            query = this.d.query(true, z[1], a, new StringBuilder(z[16]).append(300000 + j).append(z[12]).append(SearchCriteria.GT).append(j).toString(), null, null, null, null, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            query = null;
        }
        return query;
    }

    public final synchronized void a() {
        if (this.d != null) {
            this.d.close();
        }
    }

    public final synchronized void a(Cursor cursor, h hVar) {
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                if (hVar == null) {
                    hVar = new h();
                }
                try {
                    hVar.a(cursor.getLong(1));
                    hVar.a(cursor.getInt(2));
                    hVar.b(cursor.getInt(3));
                    hVar.c(cursor.getInt(4));
                    hVar.a(cursor.getString(5));
                    hVar.c(cursor.getLong(6));
                    hVar.b(cursor.getLong(7));
                } catch (Exception e) {
                    e.getStackTrace();
                }
                hVar.toString();
                z.c();
            }
        }
        z.b();
    }

    public final synchronized boolean a(boolean z) {
        return z ? c() : d();
    }

    public final synchronized boolean b() {
        boolean z = true;
        synchronized (this) {
            try {
                String str = z[10];
                ContentValues contentValues = new ContentValues();
                contentValues.put(z[5], Integer.valueOf(0));
                contentValues.put(z[3], Integer.valueOf(1));
                contentValues.put(z[8], Integer.valueOf(0));
                if (this.d.update(z[1], contentValues, str, null) <= 0) {
                    z = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                z = false;
            }
        }
        return z;
    }

    public final synchronized boolean b(long j, int i, int i2, int i3, String str, long j2, long j3) {
        boolean z;
        try {
            String stringBuilder = new StringBuilder(z[6]).append(j).toString();
            ContentValues contentValues = new ContentValues();
            contentValues.put(z[2], Long.valueOf(j));
            contentValues.put(z[5], Integer.valueOf(i));
            contentValues.put(z[3], Integer.valueOf(i2));
            contentValues.put(z[8], Integer.valueOf(0));
            contentValues.put(z[9], str);
            contentValues.put(z[4], Long.valueOf(j2));
            contentValues.put(z[7], Long.valueOf(j3));
            z = this.d.update(z[1], contentValues, stringBuilder, null) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        return z;
    }
}
