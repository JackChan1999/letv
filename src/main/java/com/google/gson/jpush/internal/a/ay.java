package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.k;

final class ay implements am {
    private static final String[] z;
    final /* synthetic */ Class a;
    final /* synthetic */ al b;

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
        r12 = 32;
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "fC9T\u0016R[\u0001T\u0000PG\u0012I\u001cRC(C\u0011Y\u001f";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000d:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0057;
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
            case 1: goto L_0x004f;
            case 2: goto L_0x0052;
            case 3: goto L_0x0055;
            default: goto L_0x0021;
        };
    L_0x0021:
        r11 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
    L_0x0023:
        r11 = r11 ^ r13;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002f;
    L_0x002b:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x001a;
    L_0x002f:
        r7 = r2;
        r2 = r9;
    L_0x0031:
        if (r7 > r8) goto L_0x0015;
    L_0x0033:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            default: goto L_0x003f;
        };
    L_0x003f:
        r5[r3] = r2;
        r0 = "\fC>A\tTG(\u001d";
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
        r11 = r12;
        goto L_0x0023;
    L_0x004f:
        r11 = 34;
        goto L_0x0023;
    L_0x0052:
        r11 = 90;
        goto L_0x0023;
    L_0x0055:
        r11 = r12;
        goto L_0x0023;
    L_0x0057:
        r8 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.ay.<clinit>():void");
    }

    ay(Class cls, al alVar) {
        this.a = cls;
        this.b = alVar;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        return this.a.isAssignableFrom(aVar.a()) ? this.b : null;
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.a.getName()).append(z[1]).append(this.b).append("]").toString();
    }
}
