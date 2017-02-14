package cn.jpush.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class f {
    private static SharedPreferences a = null;
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r6 = 1;
        r0 = ">\u0016/:\u000e(\u000bi~\u000e/\u001dg5\f8\u0016b5\rs\u000e3";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        if (r1 > r6) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0010:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0038;
            case 1: goto L_0x003b;
            case 2: goto L_0x003e;
            case 3: goto L_0x0040;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
    L_0x0019:
        r5 = r5 ^ r7;
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
        r0 = 0;
        a = r0;
        return;
    L_0x0038:
        r5 = 93;
        goto L_0x0019;
    L_0x003b:
        r5 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x0019;
    L_0x003e:
        r5 = r6;
        goto L_0x0019;
    L_0x0040:
        r5 = 80;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.f.<clinit>():void");
    }

    protected static void a(Context context, String str, int i) {
        l(context);
        Editor edit = a.edit();
        edit.putInt(str, i);
        edit.apply();
    }

    protected static void a(Context context, String str, long j) {
        l(context);
        Editor edit = a.edit();
        edit.putLong(str, j);
        edit.apply();
    }

    protected static void a(String str, int i) {
        Editor edit = a.edit();
        edit.putInt(str, i);
        edit.apply();
    }

    protected static void a(String str, long j) {
        Editor edit = a.edit();
        edit.putLong(str, j);
        edit.apply();
    }

    protected static int b(String str, int i) {
        return a.getInt(str, i);
    }

    protected static long b(String str, long j) {
        return a.getLong(str, j);
    }

    protected static void b(Context context, String str, String str2) {
        l(context);
        Editor edit = a.edit();
        edit.putString(str, str2);
        edit.apply();
    }

    protected static String c(Context context, String str, String str2) {
        l(context);
        return a.getString(str, str2);
    }

    protected static void c(String str, String str2) {
        if (a != null) {
            Editor edit = a.edit();
            edit.putString(str, str2);
            edit.apply();
        }
    }

    protected static String d(String str, String str2) {
        return a.getString(str, str2);
    }

    public static void l(Context context) {
        if (a == null) {
            a = context.getSharedPreferences(z, 0);
        }
    }

    public static void p(String str) {
        Editor edit = a.edit();
        edit.remove(str);
        edit.commit();
    }
}
