package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.ac;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.v;
import com.google.gson.jpush.t;
import com.google.gson.jpush.w;
import com.google.gson.jpush.y;
import com.google.gson.jpush.z;
import java.util.Iterator;
import java.util.Map.Entry;

final class as extends al<w> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "?\u0001+\u0014s\u0012I*X`\u000e\u0007*\u001d7";
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
        r5 = 23;
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
        r5 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        goto L_0x0019;
    L_0x0038:
        r5 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        goto L_0x0019;
    L_0x003b:
        r5 = 94;
        goto L_0x0019;
    L_0x003e:
        r5 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.as.<clinit>():void");
    }

    as() {
    }

    private void a(d dVar, w wVar) {
        if (wVar == null || (wVar instanceof y)) {
            dVar.f();
        } else if (wVar instanceof ac) {
            ac j = wVar.j();
            if (j.k()) {
                dVar.a(j.b());
            } else if (j.a()) {
                dVar.a(j.g());
            } else {
                dVar.b(j.c());
            }
        } else if (wVar instanceof t) {
            dVar.b();
            Iterator it = wVar.i().iterator();
            while (it.hasNext()) {
                a(dVar, (w) it.next());
            }
            dVar.c();
        } else if (wVar instanceof z) {
            dVar.d();
            for (Entry entry : wVar.h().a()) {
                dVar.a((String) entry.getKey());
                a(dVar, (w) entry.getValue());
            }
            dVar.e();
        } else {
            throw new IllegalArgumentException(new StringBuilder(z).append(wVar.getClass()).toString());
        }
    }

    private w b(a aVar) {
        w tVar;
        switch (az.a[aVar.f().ordinal()]) {
            case 1:
                return new ac(new v(aVar.h()));
            case 2:
                return new ac(Boolean.valueOf(aVar.i()));
            case 3:
                return new ac(aVar.h());
            case 4:
                aVar.j();
                return y.a;
            case 5:
                tVar = new t();
                aVar.a();
                while (aVar.e()) {
                    tVar.a(b(aVar));
                }
                aVar.b();
                return tVar;
            case 6:
                tVar = new z();
                aVar.c();
                while (aVar.e()) {
                    tVar.a(aVar.g(), b(aVar));
                }
                aVar.d();
                return tVar;
            default:
                throw new IllegalArgumentException();
        }
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }
}
