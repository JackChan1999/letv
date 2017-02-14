package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.util.Calendar;
import java.util.GregorianCalendar;

final class aq extends al<Calendar> {
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u001b\fD@";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0068;
            case 1: goto L_0x006b;
            case 2: goto L_0x006e;
            case 3: goto L_0x0071;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 60;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            case 4: goto L_0x0063;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0011\fF]R\u0006";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u000f\u0000KGH\u0007";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\n\u0006P@s\u0004-DK";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u000f\u0006KFT";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0006\b\\}Z/\u0006KFT";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0063:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0068:
        r9 = 98;
        goto L_0x001f;
    L_0x006b:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        goto L_0x001f;
    L_0x006e:
        r9 = 37;
        goto L_0x001f;
    L_0x0071:
        r9 = 50;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.aq.<clinit>():void");
    }

    aq() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        aVar.c();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (aVar.f() != c.d) {
            String g = aVar.g();
            int m = aVar.m();
            if (z[0].equals(g)) {
                i6 = m;
            } else if (z[4].equals(g)) {
                i5 = m;
            } else if (z[5].equals(g)) {
                i4 = m;
            } else if (z[3].equals(g)) {
                i3 = m;
            } else if (z[2].equals(g)) {
                i2 = m;
            } else if (z[1].equals(g)) {
                i = m;
            }
        }
        aVar.d();
        return new GregorianCalendar(i6, i5, i4, i3, i2, i);
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Calendar calendar = (Calendar) obj;
        if (calendar == null) {
            dVar.f();
            return;
        }
        dVar.d();
        dVar.a(z[0]);
        dVar.a((long) calendar.get(1));
        dVar.a(z[4]);
        dVar.a((long) calendar.get(2));
        dVar.a(z[5]);
        dVar.a((long) calendar.get(5));
        dVar.a(z[3]);
        dVar.a((long) calendar.get(11));
        dVar.a(z[2]);
        dVar.a((long) calendar.get(12));
        dVar.a(z[1]);
        dVar.a((long) calendar.get(13));
        dVar.e();
    }
}
