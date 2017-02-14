package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class aa extends al<Class> {
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
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "~^\u001e\u0002OO\u001fOKd\n\b\nMbK\u0017\u0006En\n\u0011\u000eIj\u0004\u0017\u000eQl\u00048\u0003^xYAO";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000c:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0063;
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
            case 0: goto L_0x0057;
            case 1: goto L_0x005a;
            case 2: goto L_0x005d;
            case 3: goto L_0x0060;
            default: goto L_0x0020;
        };
    L_0x0020:
        r11 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
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
            case 1: goto L_0x0052;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r4] = r3;
        r0 = "\u0011+l\u0014\u001dXd^[\u001bP+X\u001e\bVx^\u001e\u001d\u001fj\n\u000f\u0016On\n\u001a\u000b^{^\u001e\u001d\u0000";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r5[r4] = r3;
        r3 = 2;
        r0 = "~^\u001e\u0002OO\u001fOKd\n\u001f\nLnX\u0012\u000eSbP\u001eO^+@\u001a\u0019^%F\u001a\u0001X%i\u0017\u000eLx\u0004[)PyM\u0014\u001b\u001fE[\u001dZlC\b\u001bZy\n\u001aOKrZ\u001eO^oK\u000b\u001bZy\u0015";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000c;
    L_0x0052:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0057:
        r11 = 63;
        goto L_0x0022;
    L_0x005a:
        r11 = 11;
        goto L_0x0022;
    L_0x005d:
        r11 = 42;
        goto L_0x0022;
    L_0x0060:
        r11 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        goto L_0x0022;
    L_0x0063:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.aa.<clinit>():void");
    }

    aa() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        throw new UnsupportedOperationException(z[2]);
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Class cls = (Class) obj;
        if (cls == null) {
            dVar.f();
            return;
        }
        throw new UnsupportedOperationException(new StringBuilder(z[0]).append(cls.getName()).append(z[1]).toString());
    }
}
