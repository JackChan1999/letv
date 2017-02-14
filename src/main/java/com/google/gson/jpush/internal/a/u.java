package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class u extends al<Date> {
    public static final am a = new v();
    private static final String z;
    private final DateFormat b = new SimpleDateFormat(z);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "~:eeb\u001fWQ<J";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
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
            case 0: goto L_0x003c;
            case 1: goto L_0x003f;
            case 2: goto L_0x0042;
            case 3: goto L_0x0045;
            default: goto L_0x0018;
        };
    L_0x0018:
        r5 = 6;
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
        goto L_0x0011;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000c;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        r0 = new com.google.gson.jpush.internal.a.v;
        r0.<init>();
        a = r0;
        return;
    L_0x003c:
        r5 = 51;
        goto L_0x0019;
    L_0x003f:
        r5 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x0019;
    L_0x0042:
        r5 = 40;
        goto L_0x0019;
    L_0x0045:
        r5 = 69;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.u.<clinit>():void");
    }

    private synchronized void a(d dVar, Date date) {
        dVar.b(date == null ? null : this.b.format(date));
    }

    private synchronized Date b(a aVar) {
        Date date;
        if (aVar.f() == c.i) {
            aVar.j();
            date = null;
        } else {
            try {
                date = new Date(this.b.parse(aVar.h()).getTime());
            } catch (Throwable e) {
                throw new af(e);
            }
        }
        return date;
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }
}
