package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.annotations.b;
import com.google.gson.jpush.internal.f;
import com.google.gson.jpush.k;

public final class g implements am {
    private static final String z;
    private final f a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "\u0013thw?\u0012Zzh%6L;n0?K~8<&Mo836\u001eOa!6y!'[i8>!\u001eOa!6y!'[i^00Jtj(sL~~4![u{4}";
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
        r5 = 81;
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
        r5 = 83;
        goto L_0x0019;
    L_0x0038:
        r5 = 62;
        goto L_0x0019;
    L_0x003b:
        r5 = 27;
        goto L_0x0019;
    L_0x003e:
        r5 = 24;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.g.<clinit>():void");
    }

    public g(f fVar) {
        this.a = fVar;
    }

    static al<?> a(f fVar, k kVar, a<?> aVar, b bVar) {
        Class a = bVar.a();
        if (al.class.isAssignableFrom(a)) {
            return (al) fVar.a(a.a(a)).a();
        }
        if (am.class.isAssignableFrom(a)) {
            return ((am) fVar.a(a.a(a)).a()).a(kVar, aVar);
        }
        throw new IllegalArgumentException(z);
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        b bVar = (b) aVar.a().getAnnotation(b.class);
        return bVar == null ? null : a(this.a, kVar, aVar, bVar);
    }
}
