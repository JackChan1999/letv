package com.google.gson.jpush.internal;

import com.google.gson.jpush.x;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;

final class n implements ae<T> {
    private static final String z;
    final /* synthetic */ Type a;
    final /* synthetic */ f b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "\u001bG3m<;MeI>'D\u0016i$r]<|5h\t";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 80;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 82;
        goto L_0x0019;
    L_0x0038:
        r5 = 41;
        goto L_0x0019;
    L_0x003b:
        r5 = 69;
        goto L_0x0019;
    L_0x003e:
        r5 = 12;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.n.<clinit>():void");
    }

    n(f fVar, Type type) {
        this.b = fVar;
        this.a = type;
    }

    public final T a() {
        if (this.a instanceof ParameterizedType) {
            Type type = ((ParameterizedType) this.a).getActualTypeArguments()[0];
            if (type instanceof Class) {
                return EnumSet.noneOf((Class) type);
            }
            throw new x(new StringBuilder(z).append(this.a.toString()).toString());
        }
        throw new x(new StringBuilder(z).append(this.a.toString()).toString());
    }
}
