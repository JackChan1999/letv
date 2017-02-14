package com.google.gson.jpush.a;

import com.google.gson.jpush.internal.b;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class a<T> {
    private static final String z;
    final Class<? super T> a;
    final Type b;
    final int c;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r6 = 69;
        r0 = "\b)6z>+'e}.5%ey67!(l# 2k";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0029;
    L_0x000d:
        r3 = r0;
        r4 = r2;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0012:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0037;
            case 1: goto L_0x0039;
            case 2: goto L_0x003c;
            case 3: goto L_0x003e;
            default: goto L_0x0019;
        };
    L_0x0019:
        r5 = 87;
    L_0x001b:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0027;
    L_0x0023:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0012;
    L_0x0027:
        r1 = r0;
        r0 = r3;
    L_0x0029:
        if (r1 > r2) goto L_0x000d;
    L_0x002b:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0037:
        r5 = r6;
        goto L_0x001b;
    L_0x0039:
        r5 = 64;
        goto L_0x001b;
    L_0x003c:
        r5 = r6;
        goto L_0x001b;
    L_0x003e:
        r5 = 9;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.a.a.<clinit>():void");
    }

    protected a() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException(z);
        }
        this.b = b.a(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        this.a = b.b(this.b);
        this.c = this.b.hashCode();
    }

    private a(Type type) {
        this.b = b.a((Type) com.google.gson.jpush.internal.a.a((Object) type));
        this.a = b.b(this.b);
        this.c = this.b.hashCode();
    }

    public static <T> a<T> a(Class<T> cls) {
        return new a(cls);
    }

    public static a<?> a(Type type) {
        return new a(type);
    }

    public final Class<? super T> a() {
        return this.a;
    }

    public final Type b() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof a) && b.a(this.b, ((a) obj).b);
    }

    public final int hashCode() {
        return this.c;
    }

    public final String toString() {
        return b.c(this.b);
    }
}
