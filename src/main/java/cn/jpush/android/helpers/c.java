package cn.jpush.android.helpers;

import android.content.Context;
import cn.jpush.android.util.af;
import cn.jpush.android.util.z;

public final class c extends cn.jpush.android.c {
    private static final String[] z;
    private Context a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 9;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "(%\u0013\u0012`q`_";
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
            case 1: goto L_0x0084;
            case 2: goto L_0x0086;
            case 3: goto L_0x0089;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 12;
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
        r1 = "gj\b\u001eep%\t\u001cbc%\u0007\n,el\u0001\u001f $n\u0000\n6";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "c`\u0011Sejq\u0000\u0014iv%\u0007\n,el\u0001\u001f $n\u0000\n6";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "gj\b\u001eep%\u0007\u001cch`\u0004\u001d,f|E\u0012e`iISga|_";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "gj\b\u001eep%\f\u001dxab\u0000\u0001,f|E\u0012e`iISga|_";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "c`\u0011Snkj\t\u0016mj%\u0007\n,el\u0001\u001f $n\u0000\n6";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "c`\u0011S`kk\u0002Sn}%\u0004\u001ahh)E\u0018i}?";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "gj\b\u001eep%6\u0007~mk\u0002Sn}%\u0004\u001ahh)E\u0018i}?";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "c`\u0011S_pw\f\u001dk)a\u0004\u0007m$g\u001cSmma\t_,o`\u001cI";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0082:
        r9 = 4;
        goto L_0x0020;
    L_0x0084:
        r9 = 5;
        goto L_0x0020;
    L_0x0086:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x0020;
    L_0x0089:
        r9 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.c.<clinit>():void");
    }

    public c(Context context) {
        this.a = context;
    }

    public final int a(String str, int i) {
        int b = af.b(this.a, str, i);
        new StringBuilder(z[2]).append(str).append(z[0]).append(b);
        z.a();
        return b;
    }

    public final long a(String str, long j) {
        long b = af.b(this.a, str, j);
        new StringBuilder(z[6]).append(str).append(z[0]).append(b);
        z.a();
        return b;
    }

    public final String a(String str, String str2) {
        String b = af.b(this.a, str, str2);
        new StringBuilder(z[8]).append(str).append(z[0]).append(b);
        z.a();
        return b;
    }

    public final void a(int i, long j, boolean z, float f, double d, String str) {
    }

    public final boolean a(String str, boolean z) {
        boolean b = af.b(this.a, str, z);
        new StringBuilder(z[5]).append(str).append(z[0]).append(b);
        z.a();
        return b;
    }

    public final void b(String str, int i) {
        new StringBuilder(z[4]).append(str).append(z[0]).append(i);
        z.a();
        af.a(this.a, str, i);
    }

    public final void b(String str, long j) {
        new StringBuilder(z[1]).append(str).append(z[0]).append(j);
        z.a();
        af.a(this.a, str, j);
    }

    public final void b(String str, String str2) {
        new StringBuilder(z[7]).append(str).append(z[0]).append(str2);
        z.a();
        af.a(this.a, str, str2);
    }

    public final void b(String str, boolean z) {
        new StringBuilder(z[3]).append(str).append(z[0]).append(z);
        z.a();
        af.a(this.a, str, z);
    }
}
