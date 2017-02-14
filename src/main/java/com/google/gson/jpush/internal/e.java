package com.google.gson.jpush.internal;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

final class e implements Serializable, WildcardType {
    private static final String[] z;
    private final Type a;
    private final Type b;

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
        r2 = ":\u001f,f\u0015`Q-mA";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0056;
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
            case 1: goto L_0x004d;
            case 2: goto L_0x0050;
            case 3: goto L_0x0053;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 97;
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
        r0 = ":\u001f:k\u0011`Mi";
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
        r11 = 5;
        goto L_0x0021;
    L_0x004d:
        r11 = 63;
        goto L_0x0021;
    L_0x0050:
        r11 = 73;
        goto L_0x0021;
    L_0x0053:
        r11 = 30;
        goto L_0x0021;
    L_0x0056:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.e.<clinit>():void");
    }

    public e(Type[] typeArr, Type[] typeArr2) {
        boolean z = true;
        a.a(typeArr2.length <= 1);
        a.a(typeArr.length == 1);
        if (typeArr2.length == 1) {
            a.a(typeArr2[0]);
            b.e(typeArr2[0]);
            if (typeArr[0] != Object.class) {
                z = false;
            }
            a.a(z);
            this.b = b.a(typeArr2[0]);
            this.a = Object.class;
            return;
        }
        a.a(typeArr[0]);
        b.e(typeArr[0]);
        this.b = null;
        this.a = b.a(typeArr[0]);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof WildcardType) && b.a((Type) this, (WildcardType) obj);
    }

    public final Type[] getLowerBounds() {
        if (this.b == null) {
            return b.a;
        }
        return new Type[]{this.b};
    }

    public final Type[] getUpperBounds() {
        return new Type[]{this.a};
    }

    public final int hashCode() {
        return (this.b != null ? this.b.hashCode() + 31 : 1) ^ (this.a.hashCode() + 31);
    }

    public final String toString() {
        return this.b != null ? new StringBuilder(z[1]).append(b.c(this.b)).toString() : this.a == Object.class ? "?" : new StringBuilder(z[0]).append(b.c(this.a)).toString();
    }
}
