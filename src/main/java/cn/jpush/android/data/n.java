package cn.jpush.android.data;

import android.content.Context;
import cn.jpush.android.api.m;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.o;
import cn.jpush.android.util.p;
import cn.jpush.android.util.z;

final class n extends Thread {
    private static final String[] z;
    final /* synthetic */ m a;
    final /* synthetic */ Context b;
    final /* synthetic */ m c;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 10;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0017Jh|MK'mmE\u001bW\"";
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
            case 0: goto L_0x008c;
            case 1: goto L_0x008f;
            case 2: goto L_0x0092;
            case 3: goto L_0x0094;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 32;
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
            case 8: goto L_0x0087;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "n\u0004gqP^\tvlD\u0001JwgKU\u0005ug\u0000H\u0002m~\u0000\u001b\u0007mmE\u001bG\"";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "]\u0003nl\u001a\u0014E";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "Z\u0004f{OR\u000e,yEI\u0007kzSR\u0005l'wi#VL~2VLru+NVso%PHg~";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "h\u0002m~eU\u001ek}Y";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0015\u0002vdL";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "H\u0002m~eU\u001ek}Y\u001b\u001apfC^\u0019q)SO\u000bp}\u0000I\u001fl(\u0000H\u0002m~mT\u000eg)\u001d\u001b";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "R\u0007e)SI\t?+";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "i\u0003aa\rK\u001fqa\u0000U\u000fgmS\u001b\u001ejl\u0000K\u000fpdIH\u0019kfN\u001b\u0005d)wi#VL~2VLru+NVso%PHg~F\"yL^\u000bql\u0000I\u000fs|EH\u001e\"`T\u0015";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0017Jp`CS>{yE\u001bW\"";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x008c:
        r9 = 59;
        goto L_0x0020;
    L_0x008f:
        r9 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        goto L_0x0020;
    L_0x0092:
        r9 = 2;
        goto L_0x0020;
    L_0x0094:
        r9 = 9;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.n.<clinit>():void");
    }

    n(m mVar, m mVar2, Context context) {
        this.c = mVar;
        this.a = mVar2;
        this.b = context;
    }

    public final void run() {
        int i = 1;
        new StringBuilder(z[6]).append(this.c.H).append(z[9]).append(this.c.G).append(z[0]).append(this.c.F);
        z.a();
        if (this.c.H != 0) {
            new StringBuilder(z[1]).append(this.c.H);
            z.b();
            return;
        }
        String str = this.a.c;
        String str2 = this.a.a;
        if (this.a.G == 0) {
            g.a(str, 995, this.b);
            m.a(this.b, this.a);
        } else if (4 == this.a.G) {
            this.a.L = str2;
            g.a(str, 995, this.b);
            m.a(this.b, this.a);
        } else if (a.c(this.b, z[3])) {
            String str3 = null;
            for (int i2 = 0; i2 < 4; i2++) {
                str3 = p.a(str2, 5, 5000);
                if (!p.a(str3)) {
                    break;
                }
            }
            i = 0;
            String b = cn.jpush.android.util.m.b(this.b, str);
            if (i != 0) {
                String str4 = b + str + z[5];
                String substring = str2.substring(0, str2.lastIndexOf("/") + 1);
                if (this.a.I.isEmpty()) {
                    this.a.L = this.a.a;
                    m.a(this.b, this.a);
                    return;
                } else if (!c.a(this.a.I, this.b, substring, str, this.a.e())) {
                    z.b();
                    g.a(str, 1014, this.b);
                    return;
                } else if (o.a(str4, str3.replaceAll(new StringBuilder(z[7]).append(substring).toString(), new StringBuilder(z[7]).append(b).toString()), this.b)) {
                    this.a.L = new StringBuilder(z[2]).append(str4).toString();
                    g.a(str, 995, this.b);
                    m.a(this.b, this.a);
                    return;
                } else {
                    g.a(str, 1014, this.b);
                    return;
                }
            }
            z.d();
            g.a(str, 1014, this.b);
            g.a(str, 1021, a.b(this.b, str2), this.b);
        } else {
            z.e(z[4], z[8]);
            g.a(str, 1014, this.b);
        }
    }
}
