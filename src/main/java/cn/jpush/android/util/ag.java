package cn.jpush.android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ag {
    private static ag c;
    private static final String z;
    private final String a = z;
    private SharedPreferences b = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "E\u0005W\u0013\u001f\\\u0014}#\u0018a3K\u0007";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 15;
        goto L_0x0019;
    L_0x0038:
        r5 = 85;
        goto L_0x0019;
    L_0x003b:
        r5 = 34;
        goto L_0x0019;
    L_0x003e:
        r5 = 96;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.ag.<clinit>():void");
    }

    private SharedPreferences a(Context context) {
        if (this.b == null) {
            synchronized (ag.class) {
                this.b = context.getSharedPreferences(z, 0);
            }
        }
        return this.b;
    }

    public static ag a() {
        if (c == null) {
            c = new ag();
        }
        return c;
    }

    public final long a(Context context, String str, long j) {
        return a(context).getLong(str, j);
    }

    public final String a(Context context, String str, String str2) {
        return a(context).getString(str, null);
    }

    public final void b(Context context, String str, long j) {
        a(context).edit().putLong(str, j).commit();
    }

    public final void b(Context context, String str, String str2) {
        a(context).edit().putString(str, str2).commit();
    }
}
