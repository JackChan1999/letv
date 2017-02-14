package cn.jpush.android.util;

import cn.jpush.android.e;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public final class z {
    private static q a = new k();
    private static final SimpleDateFormat b = new SimpleDateFormat(z[2]);
    private static ArrayList<String> c = new ArrayList();
    private static boolean d = false;
    private static boolean e = false;
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
        r5 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "\u0019rH\u001e";
        r0 = -1;
        r6 = r4;
        r7 = r4;
        r4 = r1;
    L_0x000d:
        r3 = r3.toCharArray();
        r8 = r3.length;
        if (r8 > r2) goto L_0x007f;
    L_0x0014:
        r9 = r1;
    L_0x0015:
        r10 = r3;
        r11 = r9;
        r14 = r8;
        r8 = r3;
        r3 = r14;
    L_0x001a:
        r13 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x0074;
            case 1: goto L_0x0077;
            case 2: goto L_0x007a;
            case 3: goto L_0x007c;
            default: goto L_0x0021;
        };
    L_0x0021:
        r12 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
    L_0x0023:
        r12 = r12 ^ r13;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r3 != 0) goto L_0x002f;
    L_0x002b:
        r8 = r10;
        r11 = r9;
        r9 = r3;
        goto L_0x001a;
    L_0x002f:
        r8 = r3;
        r3 = r10;
    L_0x0031:
        if (r8 > r9) goto L_0x0015;
    L_0x0033:
        r8 = new java.lang.String;
        r8.<init>(r3);
        r3 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            case 1: goto L_0x0052;
            default: goto L_0x003f;
        };
    L_0x003f:
        r6[r4] = r3;
        r0 = "hi";
        r3 = r0;
        r4 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000d;
    L_0x0048:
        r6[r4] = r3;
        r0 = "x\u0004)_\u0012j\u0001O\u0001\u001bXstH)f\u001aT";
        r3 = r0;
        r4 = r5;
        r6 = r7;
        r0 = r2;
        goto L_0x000d;
    L_0x0052:
        r6[r4] = r3;
        z = r7;
        r0 = new cn.jpush.android.util.k;
        r0.<init>();
        a = r0;
        r0 = new java.text.SimpleDateFormat;
        r2 = z;
        r2 = r2[r5];
        r0.<init>(r2);
        b = r0;
        r0 = new java.util.ArrayList;
        r0.<init>();
        c = r0;
        d = r1;
        e = r1;
        return;
    L_0x0074:
        r12 = 53;
        goto L_0x0023;
    L_0x0077:
        r12 = 73;
        goto L_0x0023;
    L_0x007a:
        r12 = 7;
        goto L_0x0023;
    L_0x007c:
        r12 = 59;
        goto L_0x0023;
    L_0x007f:
        r9 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.z.<clinit>():void");
    }

    public static void a() {
    }

    static void a(q qVar) {
        a = qVar;
    }

    public static void a(String str, String str2) {
        if (e.a && a(2)) {
            a.v(z[0], "[" + str + z[1] + str2);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (e.a && a(5)) {
            a.w(z[0], "[" + str + z[1] + str2, th);
        }
    }

    private static boolean a(int i) {
        return i >= 3;
    }

    public static void b() {
    }

    public static void b(String str, String str2) {
        if (e.a && a(3)) {
            a.d(z[0], "[" + str + z[1] + str2);
        }
    }

    public static void b(String str, String str2, Throwable th) {
        if (a(6)) {
            a.e(z[0], "[" + str + z[1] + str2, th);
        }
    }

    public static void c() {
    }

    public static void c(String str, String str2) {
        if (e.a && a(4)) {
            a.i(z[0], "[" + str + z[1] + str2);
        }
    }

    public static void d() {
    }

    public static void d(String str, String str2) {
        if (a(5)) {
            a.w(z[0], "[" + str + z[1] + str2);
        }
    }

    public static void e() {
    }

    public static void e(String str, String str2) {
        if (a(6)) {
            a.e(z[0], "[" + str + z[1] + str2);
        }
    }

    public static void f() {
    }

    public static void g() {
    }

    public static void h() {
    }

    public static void i() {
    }
}
