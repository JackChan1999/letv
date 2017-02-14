package cn.jpush.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import cn.jpush.android.e;

public final class af {
    private static SharedPreferences a = null;
    private static final String[] z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "\u00158@b \u0003%\u0006&1\u00182\u001cg9\u0012x\u001b{5\u0004x\u001ez?\u0010?\u0002m";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0064;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0058;
            case 1: goto L_0x005b;
            case 2: goto L_0x005e;
            case 3: goto L_0x0061;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 80;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "\u00159\u0000|5\u000e\"Na#V8\u001bd<Zv\u0007fp%>\u000fz5&$\u000bn5\u00043\u0000k5&$\u0001k5\u0005%@a>\u001f\"";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "%>\u000fz5&$\u000bn5\u00043\u0000k5&$\u0001k5\u0005%";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        r0 = 0;
        a = r0;
        return;
    L_0x0058:
        r11 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x0021;
    L_0x005b:
        r11 = 86;
        goto L_0x0021;
    L_0x005e:
        r11 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x0021;
    L_0x0061:
        r11 = 8;
        goto L_0x0021;
    L_0x0064:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.af.<clinit>():void");
    }

    public static void a(Context context, String str, int i) {
        if (a(context)) {
            b(context);
            Editor edit = a.edit();
            edit.putInt(str, i);
            edit.apply();
            return;
        }
        z.d();
    }

    public static void a(Context context, String str, long j) {
        if (a(context)) {
            b(context);
            Editor edit = a.edit();
            edit.putLong(str, j);
            edit.apply();
            return;
        }
        z.d();
    }

    public static void a(Context context, String str, String str2) {
        if (a(context)) {
            b(context);
            Editor edit = a.edit();
            edit.putString(str, str2);
            edit.apply();
            return;
        }
        z.d();
    }

    public static void a(Context context, String str, boolean z) {
        if (a(context)) {
            b(context);
            Editor edit = a.edit();
            edit.putBoolean(str, z);
            edit.apply();
            return;
        }
        z.d();
    }

    private static boolean a(Context context) {
        if (context != null) {
            return true;
        }
        z.d(z[2], z[1]);
        return false;
    }

    public static int b(Context context, String str, int i) {
        if (a(context)) {
            b(context);
            return a.getInt(str, i);
        }
        z.d();
        return i;
    }

    public static long b(Context context, String str, long j) {
        if (a(context)) {
            b(context);
            return a.getLong(str, j);
        }
        z.d();
        return j;
    }

    public static String b(Context context, String str, String str2) {
        if (a(context)) {
            b(context);
            return a.getString(str, str2);
        }
        z.d();
        return str2;
    }

    private static void b(Context context) {
        if (a == null) {
            a = context.getSharedPreferences(z[0], 4);
        } else if (e.o == null && !e.l) {
            a = context.getSharedPreferences(z[0], 4);
        }
    }

    public static boolean b(Context context, String str, boolean z) {
        if (a(context)) {
            b(context);
            return a.getBoolean(str, z);
        }
        z.d();
        return z;
    }
}
