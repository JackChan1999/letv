package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.x;
import java.net.URI;

final class ak extends al<URI> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "\u0005Z2%";
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
        r5 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x0019;
    L_0x0038:
        r5 = 47;
        goto L_0x0019;
    L_0x003b:
        r5 = 94;
        goto L_0x0019;
    L_0x003e:
        r5 = 73;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.ak.<clinit>():void");
    }

    ak() {
    }

    private static URI b(a aVar) {
        URI uri = null;
        if (aVar.f() == c.i) {
            aVar.j();
        } else {
            try {
                String h = aVar.h();
                if (!z.equals(h)) {
                    uri = new URI(h);
                }
            } catch (Throwable e) {
                throw new x(e);
            }
        }
        return uri;
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        URI uri = (URI) obj;
        dVar.b(uri == null ? null : uri.toASCIIString());
    }
}
