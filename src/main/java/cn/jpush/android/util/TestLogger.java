package cn.jpush.android.util;

public class TestLogger implements q {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r12 = 36;
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "\u0004\u0004Z9";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000d:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0058;
    L_0x0014:
        r8 = r1;
    L_0x0015:
        r9 = r2;
        r10 = r8;
        r14 = r7;
        r7 = r2;
        r2 = r14;
    L_0x001a:
        r13 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004d;
            case 1: goto L_0x0050;
            case 2: goto L_0x0052;
            case 3: goto L_0x0055;
            default: goto L_0x0021;
        };
    L_0x0021:
        r11 = r12;
    L_0x0022:
        r11 = r11 ^ r13;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002e;
    L_0x002a:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x001a;
    L_0x002e:
        r7 = r2;
        r2 = r9;
    L_0x0030:
        if (r7 > r8) goto L_0x0015;
    L_0x0032:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r3] = r2;
        r0 = "y\tW";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000d;
    L_0x0048:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004d:
        r11 = 89;
        goto L_0x0022;
    L_0x0050:
        r11 = r12;
        goto L_0x0022;
    L_0x0052:
        r11 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x0022;
    L_0x0055:
        r11 = 25;
        goto L_0x0022;
    L_0x0058:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.TestLogger.<clinit>():void");
    }

    public static void setTestLogger() {
        z.a(new TestLogger());
    }

    public void d(String str, String str2) {
        System.out.println("[" + str + z[0] + str2);
    }

    public void d(String str, String str2, Throwable th) {
        System.out.println("[" + str + z[0] + str2 + z[1] + th.getMessage());
    }

    public void e(String str, String str2) {
        System.out.println("[" + str + z[0] + str2);
    }

    public void e(String str, String str2, Throwable th) {
        System.out.println("[" + str + z[0] + str2 + z[1] + th.getMessage());
    }

    public void i(String str, String str2) {
        System.out.println("[" + str + z[0] + str2);
    }

    public void i(String str, String str2, Throwable th) {
        System.out.println("[" + str + z[0] + str2 + z[1] + th.getMessage());
    }

    public void v(String str, String str2) {
        System.out.println("[" + str + z[0] + str2);
    }

    public void v(String str, String str2, Throwable th) {
        System.out.println("[" + str + z[0] + str2 + z[1] + th.getMessage());
    }

    public void w(String str, String str2) {
        System.out.println("[" + str + z[0] + str2);
    }

    public void w(String str, String str2, Throwable th) {
        System.out.println("[" + str + z[0] + str2 + z[1] + th.getMessage());
    }
}
