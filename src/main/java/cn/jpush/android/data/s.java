package cn.jpush.android.data;

import android.content.Context;
import cn.jpush.android.api.m;
import cn.jpush.android.helpers.g;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import org.json.JSONObject;

public final class s extends c {
    private static final String[] J;
    public String E;
    public String F;
    public String G;
    public String H;
    public String I;
    public int a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "USs\u0002b";
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
            case 0: goto L_0x0069;
            case 1: goto L_0x006c;
            case 2: goto L_0x006f;
            case 3: goto L_0x0072;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 87;
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
            case 4: goto L_0x0064;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "USw\b1L";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "USk\u0014;";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "US{\u0013%O";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "USj\u001f'F";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "vbu\b8Tb>\u0010>GiqF#Z|{Fz\u0003";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0064:
        r3[r2] = r1;
        J = r4;
        return;
    L_0x0069:
        r9 = 35;
        goto L_0x001f;
    L_0x006c:
        r9 = 12;
        goto L_0x001f;
    L_0x006f:
        r9 = 30;
        goto L_0x001f;
    L_0x0072:
        r9 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.s.<clinit>():void");
    }

    public s() {
        this.o = 2;
    }

    public final void a(Context context) {
        z.a();
        g.a(this.c, 995, context);
        if (this.a == 0) {
            if (a.b(context)) {
                ServiceInterface.a(context, (c) this);
            }
        } else if (this.a == 1) {
            m.a(context, (c) this);
        } else {
            new StringBuilder(J[5]).append(this.a);
            z.b();
        }
    }

    public final boolean a(Context context, JSONObject jSONObject) {
        z.a();
        this.a = jSONObject.optInt(J[4], 0);
        this.E = jSONObject.optString(J[2], "");
        this.F = jSONObject.optString(J[3], "");
        this.H = jSONObject.optString(J[0], "");
        this.G = jSONObject.optString(J[1], "");
        return true;
    }
}
