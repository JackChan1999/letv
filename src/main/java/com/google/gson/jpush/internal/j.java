package com.google.gson.jpush.internal;

import java.lang.reflect.Type;

final class j implements ae<T> {
    private static final String[] z;
    final /* synthetic */ Class a;
    final /* synthetic */ Type b;
    final /* synthetic */ f c;
    private final UnsafeAllocator d = UnsafeAllocator.create();

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
        r2 = "jgD\u0011\u0019-4b\u0011\fd&xT7*4b\u0015\u0010'\"U\u0006\u001b%3y\u0006^3.b\u001c^\u00034y\u001a^\"(dT\n,.eT\n=7sT\u0013%>6\u0012\u0017<gb\u001c\u00177gf\u0006\u0011&+s\u0019P";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0057;
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
            case 0: goto L_0x004b;
            case 1: goto L_0x004e;
            case 2: goto L_0x0051;
            case 3: goto L_0x0054;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
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
            case 0: goto L_0x0046;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r3] = r2;
        r0 = "\u0011)w\u0016\u0012!gb\u001b^-)`\u001b\u0015!gx\u001bS%5q\u0007^'(x\u0007\n62u\u0000\u00116gp\u001b\fd";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004b:
        r11 = 68;
        goto L_0x0021;
    L_0x004e:
        r11 = 71;
        goto L_0x0021;
    L_0x0051:
        r11 = 22;
        goto L_0x0021;
    L_0x0054:
        r11 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        goto L_0x0021;
    L_0x0057:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.j.<clinit>():void");
    }

    j(f fVar, Class cls, Type type) {
        this.c = fVar;
        this.a = cls;
        this.b = type;
    }

    public final T a() {
        try {
            return this.d.newInstance(this.a);
        } catch (Throwable e) {
            throw new RuntimeException(new StringBuilder(z[1]).append(this.b).append(z[0]).toString(), e);
        }
    }
}
