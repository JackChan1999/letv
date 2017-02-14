package com.google.gson.jpush;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.internal.ag;
import java.io.Reader;
import java.io.StringReader;

public final class ab {
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
        r3 = "WD|(;u\u0005e%,bL{#~[vZ\n~bJ`6=t\u001f5";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
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
            case 0: goto L_0x0055;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 94;
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
        r0 = "1Qzd\u0014bJ{";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "ULqd0~Q5'1V`);1Q}!~tKa-,t\u0005q+=dHp**?";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0055:
        r11 = 17;
        goto L_0x0021;
    L_0x0058:
        r11 = 37;
        goto L_0x0021;
    L_0x005b:
        r11 = 21;
        goto L_0x0021;
    L_0x005e:
        r11 = 68;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.ab.<clinit>():void");
    }

    private static w a(a aVar) {
        boolean p = aVar.p();
        aVar.a(true);
        try {
            w a = ag.a(aVar);
            aVar.a(p);
            return a;
        } catch (Throwable e) {
            throw new aa(new StringBuilder(z[0]).append(aVar).append(z[1]).toString(), e);
        } catch (Throwable e2) {
            throw new aa(new StringBuilder(z[0]).append(aVar).append(z[1]).toString(), e2);
        } catch (Throwable th) {
            aVar.a(p);
        }
    }

    private w a(Reader reader) {
        try {
            a aVar = new a(reader);
            w a = a(aVar);
            if ((a instanceof y) || aVar.f() == c.j) {
                return a;
            }
            throw new af(z[2]);
        } catch (Throwable e) {
            throw new af(e);
        } catch (Throwable e2) {
            throw new x(e2);
        } catch (Throwable e22) {
            throw new af(e22);
        }
    }

    public final w a(String str) {
        return a(new StringReader(str));
    }
}
