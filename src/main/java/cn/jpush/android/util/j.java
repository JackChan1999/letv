package cn.jpush.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class j {
    public static String a;
    private static final String z;

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
        r1 = 0;
        r2 = "V\u000eDSIb:\u0010N\u0000p?u\u0010\tBMNY";
        r0 = -1;
    L_0x0004:
        r2 = r2.toCharArray();
        r3 = r2.length;
        r4 = 1;
        if (r3 > r4) goto L_0x004d;
    L_0x000c:
        r4 = r1;
    L_0x000d:
        r5 = r2;
        r6 = r4;
        r9 = r3;
        r3 = r2;
        r2 = r9;
    L_0x0012:
        r8 = r3[r4];
        r7 = r6 % 5;
        switch(r7) {
            case 0: goto L_0x003e;
            case 1: goto L_0x0041;
            case 2: goto L_0x0044;
            case 3: goto L_0x0047;
            default: goto L_0x0019;
        };
    L_0x0019:
        r7 = 100;
    L_0x001b:
        r7 = r7 ^ r8;
        r7 = (char) r7;
        r3[r4] = r7;
        r4 = r6 + 1;
        if (r2 != 0) goto L_0x0027;
    L_0x0023:
        r3 = r5;
        r6 = r4;
        r4 = r2;
        goto L_0x0012;
    L_0x0027:
        r3 = r2;
        r2 = r5;
    L_0x0029:
        if (r3 > r4) goto L_0x000d;
    L_0x002b:
        r3 = new java.lang.String;
        r3.<init>(r2);
        r2 = r3.intern();
        switch(r0) {
            case 0: goto L_0x004a;
            default: goto L_0x0037;
        };
    L_0x0037:
        z = r2;
        r0 = "V\u000eDS)b\u0013Yu,g\u001aP";
        r2 = r0;
        r0 = r1;
        goto L_0x0004;
    L_0x003e:
        r7 = 47;
        goto L_0x001b;
    L_0x0041:
        r7 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x001b;
    L_0x0044:
        r7 = 61;
        goto L_0x001b;
    L_0x0047:
        r7 = 42;
        goto L_0x001b;
    L_0x004a:
        a = r2;
        return;
    L_0x004d:
        r4 = r1;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.j.<clinit>():void");
    }

    public static String a() {
        return new SimpleDateFormat(z).format(new Date());
    }
}
