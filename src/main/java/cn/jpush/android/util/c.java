package cn.jpush.android.util;

import java.io.UnsupportedEncodingException;

public class c {
    static final /* synthetic */ boolean a;
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
        r7 = 1;
        r3 = 0;
        r0 = "\u001e{x\u001f5\ba\u001c";
        r0 = r0.toCharArray();
        r1 = r0.length;
        if (r1 > r7) goto L_0x004d;
    L_0x000b:
        r2 = r3;
    L_0x000c:
        r4 = r0;
        r5 = r2;
        r9 = r1;
        r1 = r0;
        r0 = r9;
    L_0x0011:
        r8 = r1[r2];
        r6 = r5 % 5;
        switch(r6) {
            case 0: goto L_0x0041;
            case 1: goto L_0x0044;
            case 2: goto L_0x0047;
            case 3: goto L_0x004a;
            default: goto L_0x0018;
        };
    L_0x0018:
        r6 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
    L_0x001a:
        r6 = r6 ^ r8;
        r6 = (char) r6;
        r1[r2] = r6;
        r2 = r5 + 1;
        if (r0 != 0) goto L_0x0026;
    L_0x0022:
        r1 = r4;
        r5 = r2;
        r2 = r0;
        goto L_0x0011;
    L_0x0026:
        r1 = r0;
        r0 = r4;
    L_0x0028:
        if (r1 > r2) goto L_0x000c;
    L_0x002a:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        r0 = cn.jpush.android.util.c.class;
        r0 = r0.desiredAssertionStatus();
        if (r0 != 0) goto L_0x003e;
    L_0x003d:
        r3 = r7;
    L_0x003e:
        a = r3;
        return;
    L_0x0041:
        r6 = 75;
        goto L_0x001a;
    L_0x0044:
        r6 = 40;
        goto L_0x001a;
    L_0x0047:
        r6 = 85;
        goto L_0x001a;
    L_0x004a:
        r6 = 94;
        goto L_0x001a;
    L_0x004d:
        r2 = r3;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.c.<clinit>():void");
    }

    private c() {
    }

    public static String a(byte[] bArr, int i) {
        int i2 = 1;
        try {
            int length = bArr.length;
            e eVar = new e(10, null);
            int i3 = (length / 3) * 4;
            if (!eVar.d) {
                switch (length % 3) {
                    case 0:
                        break;
                    case 1:
                        i3 += 2;
                        break;
                    case 2:
                        i3 += 3;
                        break;
                    default:
                        break;
                }
            } else if (length % 3 > 0) {
                i3 += 4;
            }
            if (!eVar.e || length <= 0) {
                i2 = i3;
            } else {
                int i4 = ((length - 1) / 57) + 1;
                if (eVar.f) {
                    i2 = 2;
                }
                i2 = (i2 * i4) + i3;
            }
            eVar.a = new byte[i2];
            eVar.a(bArr, 0, length, true);
            if (a || eVar.b == i2) {
                return new String(eVar.a, z);
            }
            throw new AssertionError();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
