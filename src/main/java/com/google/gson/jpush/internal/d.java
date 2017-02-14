package com.google.gson.jpush.internal;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

final class d implements Serializable, ParameterizedType {
    private static final String z;
    private final Type a;
    private final Type b;
    private final Type[] c;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "uz";
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
        r5 = 98;
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
        r5 = 89;
        goto L_0x001a;
    L_0x0039:
        r5 = 90;
        goto L_0x001a;
    L_0x003c:
        r5 = 75;
        goto L_0x001a;
    L_0x003f:
        r5 = 51;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.d.<clinit>():void");
    }

    public d(Type type, Type type2, Type... typeArr) {
        int i = 0;
        if (type2 instanceof Class) {
            Class cls = (Class) type2;
            int i2 = (Modifier.isStatic(cls.getModifiers()) || cls.getEnclosingClass() == null) ? 1 : 0;
            boolean z = (type == null && i2 == 0) ? false : true;
            a.a(z);
        }
        this.a = type == null ? null : b.a(type);
        this.b = b.a(type2);
        this.c = (Type[]) typeArr.clone();
        while (i < this.c.length) {
            a.a(this.c[i]);
            b.e(this.c[i]);
            this.c[i] = b.a(this.c[i]);
            i++;
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof ParameterizedType) && b.a((Type) this, (ParameterizedType) obj);
    }

    public final Type[] getActualTypeArguments() {
        return (Type[]) this.c.clone();
    }

    public final Type getOwnerType() {
        return this.a;
    }

    public final Type getRawType() {
        return this.b;
    }

    public final int hashCode() {
        return (Arrays.hashCode(this.c) ^ this.b.hashCode()) ^ b.a(this.a);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder((this.c.length + 1) * 30);
        stringBuilder.append(b.c(this.b));
        if (this.c.length == 0) {
            return stringBuilder.toString();
        }
        stringBuilder.append(SearchCriteria.LT).append(b.c(this.c[0]));
        for (int i = 1; i < this.c.length; i++) {
            stringBuilder.append(z).append(b.c(this.c[i]));
        }
        return stringBuilder.append(SearchCriteria.GT).toString();
    }
}
