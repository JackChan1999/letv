package cn.jpush.android.util;

import android.content.Context;
import android.util.Log;
import cn.jpush.android.e;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JLogger {
    private static final SimpleDateFormat a = new SimpleDateFormat(z);
    private static t b = new t();
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r6 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        r0 = "z\u0004\u001c=PN0H \u0019#5-~\u0010nG\u00167";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0029;
    L_0x000e:
        r3 = r0;
        r4 = r2;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0013:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0049;
            case 2: goto L_0x004b;
            case 3: goto L_0x004e;
            default: goto L_0x001a;
        };
    L_0x001a:
        r5 = r6;
    L_0x001b:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0027;
    L_0x0023:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0013;
    L_0x0027:
        r1 = r0;
        r0 = r3;
    L_0x0029:
        if (r1 > r2) goto L_0x000e;
    L_0x002b:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        r0 = new java.text.SimpleDateFormat;
        r1 = z;
        r0.<init>(r1);
        a = r0;
        r0 = new cn.jpush.android.util.t;
        r0.<init>();
        b = r0;
        return;
    L_0x0047:
        r5 = 3;
        goto L_0x001b;
    L_0x0049:
        r5 = r6;
        goto L_0x001b;
    L_0x004b:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x001b;
    L_0x004e:
        r5 = 68;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.JLogger.<clinit>():void");
    }

    private static void a(int i, String str, String str2) {
        String str3 = "V";
        switch (i) {
            case 1:
                if (e.a) {
                    Log.v(str, str2);
                }
                str3 = "V";
                break;
            case 2:
                if (e.a) {
                    Log.d(str, str2);
                }
                str3 = "D";
                break;
            case 4:
                if (e.a) {
                    Log.i(str, str2);
                }
                str3 = "I";
                break;
            case 8:
                if (e.a) {
                    Log.w(str, str2);
                }
                str3 = "W";
                break;
            case 16:
                if (e.a) {
                    Log.e(str, str2);
                }
                str3 = "E";
                break;
        }
        if (b != null && b.b && (b.a & i) != 0) {
            cn.jpush.android.data.e eVar = new cn.jpush.android.data.e(i, str3, str, str2, a.format(new Date()));
            if (b != null) {
                b.a(eVar);
            }
        }
    }

    public static void d(String str, String str2) {
        a(2, str, str2);
    }

    public static void e(String str, String str2) {
        a(16, str, str2);
    }

    public static void i(String str, String str2) {
        a(4, str, str2);
    }

    public static void parseModalJson(String str, Context context) {
        if (context != null && b != null) {
            b.a(context, str);
        }
    }

    public static void reportByHeartbeats() {
        if (b != null) {
            b.a();
        }
    }

    public static void v(String str, String str2) {
        a(1, str, str2);
    }

    public static void w(String str, String str2) {
        a(8, str, str2);
    }
}
