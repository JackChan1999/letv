package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.k;

final class av implements am {
    private static final String[] z;
    final /* synthetic */ Class a;
    final /* synthetic */ Class b;
    final /* synthetic */ al c;

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
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "\u0018\u0017?\rv,\u000f\u0007\r`.\u0013a";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0058;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004c;
            case 1: goto L_0x004f;
            case 2: goto L_0x0052;
            case 3: goto L_0x0055;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 25;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0018;
    L_0x002d:
        r7 = r2;
        r2 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r3] = r2;
        r0 = "r\u00178\u0018i*\u0013.D";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0047:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004c:
        r11 = 94;
        goto L_0x0021;
    L_0x004f:
        r11 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x0021;
    L_0x0052:
        r11 = 92;
        goto L_0x0021;
    L_0x0055:
        r11 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x0021;
    L_0x0058:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.av.<clinit>():void");
    }

    av(Class cls, Class cls2, al alVar) {
        this.a = cls;
        this.b = cls2;
        this.c = alVar;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        Class a = aVar.a();
        return (a == this.a || a == this.b) ? this.c : null;
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.b.getName()).append("+").append(this.a.getName()).append(z[1]).append(this.c).append("]").toString();
    }
}
