package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class ad extends al<Character> {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "C0(\u00185r!6\u001ave 9\u000f7e<=\u000fz&/7\tl&";
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
            case 1: goto L_0x0037;
            case 2: goto L_0x003a;
            case 3: goto L_0x003d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 86;
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
        r5 = 6;
        goto L_0x0019;
    L_0x0037:
        r5 = 72;
        goto L_0x0019;
    L_0x003a:
        r5 = 88;
        goto L_0x0019;
    L_0x003d:
        r5 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.ad.<clinit>():void");
    }

    ad() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        String h = aVar.h();
        if (h.length() == 1) {
            return Character.valueOf(h.charAt(0));
        }
        throw new af(new StringBuilder(z).append(h).toString());
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Character ch = (Character) obj;
        dVar.b(ch == null ? null : String.valueOf(ch));
    }
}
