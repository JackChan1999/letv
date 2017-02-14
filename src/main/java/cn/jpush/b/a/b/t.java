package cn.jpush.b.a.b;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.jpush.a.a.af;
import cn.jpush.a.a.ao;
import cn.jpush.a.a.aq;
import cn.jpush.a.a.ar;
import cn.jpush.a.a.ba;
import cn.jpush.android.a;
import cn.jpush.android.util.z;
import cn.jpush.b.a.a.b;
import cn.jpush.b.a.a.c;
import cn.jpush.b.a.a.h;

public final class t {
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 9;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\"dP\u0007\u001c\u0012k\u0011!4\u0002iX\u00054\u0005,\u001cS<\u0012kx\u0017k";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0082;
            case 1: goto L_0x0085;
            case 2: goto L_0x0088;
            case 3: goto L_0x008b;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 81;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0074;
            case 7: goto L_0x007d;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = " oE\u001a>\u000f,\u001cS\"\u0004bU09\u0000x|\u000062u_\u0010\u0013\u0000oZS|A~X\u0017k";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\ban\u00014\u0012|^\u001d\"\u0004";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "M,T\u00054\u000fxx\u0017k";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0002b\u001f\u0019!\u0014Y]8\f\"P\u001d5\u0013cX\u0017\u0000oE\u001a>\u000f\"x>\u000e3Ib#\u001e/_t";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "(ac\u0016\"\u0011c_\u00004)i]\u00034\u0013";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "M,C\u001a5[";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = " oE\u001a>\u000f,\u001cS9\u0000bU\u001f4(ac\u0016\"\u0011c_\u00004A!\u0011\u001a<\"aUI";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = " oE\u001a>\u000f,\u001cS\"\u0004bU6'\u0004bE10\u0002g\u0011^q\u0013eUI";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0082:
        r9 = 97;
        goto L_0x0020;
    L_0x0085:
        r9 = 12;
        goto L_0x0020;
    L_0x0088:
        r9 = 49;
        goto L_0x0020;
    L_0x008b:
        r9 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.b.t.<clinit>():void");
    }

    public static void a(Context context, Handler handler, h hVar, byte[] bArr) {
        c cVar = (c) hVar;
        p a = cVar.a();
        if (a == null) {
            z.e();
            return;
        }
        int i = a.a;
        long longValue = cVar.e().a().longValue();
        z.b(z[5], new StringBuilder(z[7]).append(i).append(z[6]).append(longValue).toString());
        switch (i) {
            case 1:
                af afVar = a.f;
                if (afVar != null && afVar.d() == 0) {
                    z.b();
                    a.a(true);
                    a.m();
                    break;
                }
                z.b();
                a.a(false);
                break;
                break;
            case 2:
                if (a.f.d() != 0) {
                    z.e();
                    break;
                }
                z.b();
                a.a(false);
                cn.jpush.android.helpers.h.c(context);
                break;
            case 13:
                ba baVar = (ba) a.e;
                new StringBuilder(z[8]).append(longValue).append(z[3]).append(baVar.d());
                z.b();
                a.e = ba.t().a(baVar.d()).a(baVar.f()).b(baVar.h()).c(baVar.j()).a();
                Message.obtain(handler, 7501, new b(longValue, a)).sendToTarget();
                break;
            case 14:
                a(handler, longValue, a);
                break;
        }
        cn.jpush.android.util.a.a(context, z[4], z[2], bArr);
    }

    private static void a(Handler handler, long j, p pVar) {
        new StringBuilder(z[1]).append(j);
        z.b();
        aq aqVar = (aq) pVar.e;
        ar e = aq.e();
        for (ao aoVar : aqVar.b()) {
            new StringBuilder(z[0]).append(aoVar.h());
            z.a();
            e.a(ao.r().c(aoVar.h()).a(aoVar.j()).a(aoVar.d()).b(aoVar.f()).a());
        }
        pVar.e = e.a();
        Message.obtain(handler, 7502, new b(j, pVar)).sendToTarget();
    }
}
