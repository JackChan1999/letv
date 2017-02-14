package cn.jpush.b.a.a;

import cn.jpush.b.a.c.b;
import cn.jpush.b.a.c.c;
import java.nio.ByteBuffer;

public final class d {
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
        r3 = "{N\"\u0018fYNi\u0015fCM(\u0018m\u000eF&\u0004)^A;\u0005`@Gi\u001fgLO<\u0018m\u0000";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000c:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0062;
    L_0x0013:
        r8 = r1;
    L_0x0014:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0019:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0056;
            case 1: goto L_0x0059;
            case 2: goto L_0x005c;
            case 3: goto L_0x005f;
            default: goto L_0x0020;
        };
    L_0x0020:
        r11 = 9;
    L_0x0022:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002e;
    L_0x002a:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0019;
    L_0x002e:
        r7 = r3;
        r3 = r9;
    L_0x0030:
        if (r7 > r8) goto L_0x0014;
    L_0x0032:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0051;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r4] = r3;
        r0 = "ZO=\u0017e\u000eB0\u0002l]\u0000dV";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r5[r4] = r3;
        r3 = 2;
        r0 = "^A;\u0005lJ\u0000!\u0013hJ\u0000dV";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000c;
    L_0x0051:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0056:
        r11 = 46;
        goto L_0x0022;
    L_0x0059:
        r11 = 32;
        goto L_0x0022;
    L_0x005c:
        r11 = 73;
        goto L_0x0022;
    L_0x005f:
        r11 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x0022;
    L_0x0062:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.d.<clinit>():void");
    }

    public static h a(byte[] bArr) {
        new StringBuilder(z[1]).append(c.a(bArr));
        int length = bArr.length - 20;
        Object obj = new byte[20];
        System.arraycopy(bArr, 0, obj, 0, 20);
        Object obj2 = new byte[length];
        System.arraycopy(bArr, 20, obj2, 0, length);
        ByteBuffer wrap = ByteBuffer.wrap(obj2);
        e eVar = new e(false, obj);
        new StringBuilder(z[2]).append(eVar.toString());
        switch (eVar.c) {
            case 0:
                return new k(eVar, wrap);
            case 1:
                return new i(eVar, wrap);
            case 3:
                return new j(eVar, wrap);
            case 10:
                return new m(eVar, wrap);
            case 19:
                return new a(eVar, wrap);
            case 100:
                return new c(eVar, wrap);
            default:
                b.a(z[0]);
                return null;
        }
    }
}
