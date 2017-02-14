package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.v;

final class ac extends al<Number> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "\u001cI\"\u001c!-X<\u001eb7D?\u001b'+\u001dr\u001e--\u000br";
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
        r5 = 66;
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
        r5 = 89;
        goto L_0x0019;
    L_0x0038:
        r5 = 49;
        goto L_0x0019;
    L_0x003b:
        r5 = 82;
        goto L_0x0019;
    L_0x003e:
        r5 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.ac.<clinit>():void");
    }

    ac() {
    }

    public final /* synthetic */ Object a(a aVar) {
        c f = aVar.f();
        switch (az.a[f.ordinal()]) {
            case 1:
                return new v(aVar.h());
            case 4:
                aVar.j();
                return null;
            default:
                throw new af(new StringBuilder(z).append(f).toString());
        }
    }

    public final /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
        dVar.a((Number) obj);
    }
}
