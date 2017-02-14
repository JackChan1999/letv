package com.google.gson.jpush.internal;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

final class c implements Serializable, GenericArrayType {
    private static final String z;
    private final Type a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "{\f";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0028;
    L_0x000c:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0011:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0036;
            case 1: goto L_0x0039;
            case 2: goto L_0x003c;
            case 3: goto L_0x003f;
            default: goto L_0x0018;
        };
    L_0x0018:
        r5 = 86;
    L_0x001a:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0026;
    L_0x0022:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0011;
    L_0x0026:
        r1 = r0;
        r0 = r3;
    L_0x0028:
        if (r1 > r2) goto L_0x000c;
    L_0x002a:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0036:
        r5 = 32;
        goto L_0x001a;
    L_0x0039:
        r5 = 81;
        goto L_0x001a;
    L_0x003c:
        r5 = 14;
        goto L_0x001a;
    L_0x003f:
        r5 = 7;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.c.<clinit>():void");
    }

    public c(Type type) {
        this.a = b.a(type);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof GenericArrayType) && b.a((Type) this, (GenericArrayType) obj);
    }

    public final Type getGenericComponentType() {
        return this.a;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return b.c(this.a) + z;
    }
}
