package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.net.URL;

final class aj extends al<URL> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "sN0y";
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
        r5 = 50;
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
        r5 = 29;
        goto L_0x001a;
    L_0x0039:
        r5 = 59;
        goto L_0x001a;
    L_0x003c:
        r5 = 92;
        goto L_0x001a;
    L_0x003f:
        r5 = 21;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.aj.<clinit>():void");
    }

    aj() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        String h = aVar.h();
        return !z.equals(h) ? new URL(h) : null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        URL url = (URL) obj;
        dVar.b(url == null ? null : url.toExternalForm());
    }
}
