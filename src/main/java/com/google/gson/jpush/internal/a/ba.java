package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class ba extends al<Boolean> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "\u001fz^W";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0026;
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
            case 0: goto L_0x0034;
            case 1: goto L_0x0037;
            case 2: goto L_0x003a;
            case 3: goto L_0x003d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 4;
    L_0x0018:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0024;
    L_0x0020:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0024:
        r1 = r0;
        r0 = r3;
    L_0x0026:
        if (r1 > r2) goto L_0x000b;
    L_0x0028:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0034:
        r5 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0018;
    L_0x0037:
        r5 = 15;
        goto L_0x0018;
    L_0x003a:
        r5 = 50;
        goto L_0x0018;
    L_0x003d:
        r5 = 59;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.ba.<clinit>():void");
    }

    ba() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return Boolean.valueOf(aVar.h());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Boolean bool = (Boolean) obj;
        dVar.b(bool == null ? z : bool.toString());
    }
}
