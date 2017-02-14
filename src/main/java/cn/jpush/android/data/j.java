package cn.jpush.android.data;

import android.content.Context;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.m;
import cn.jpush.android.util.o;
import cn.jpush.android.util.p;
import cn.jpush.android.util.z;

final class j extends Thread {
    private static final String[] z;
    final /* synthetic */ i a;
    final /* synthetic */ Context b;
    final /* synthetic */ int c;
    final /* synthetic */ i d;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "&TlF\u0003";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0057;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004b;
            case 1: goto L_0x004e;
            case 2: goto L_0x0051;
            case 3: goto L_0x0054;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0018;
    L_0x002d:
        r7 = r2;
        r2 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r3] = r2;
        r0 = "nUtNU'\u0013";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004b:
        r11 = 8;
        goto L_0x0021;
    L_0x004e:
        r11 = 60;
        goto L_0x0021;
    L_0x0051:
        r11 = 24;
        goto L_0x0021;
    L_0x0054:
        r11 = 43;
        goto L_0x0021;
    L_0x0057:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.j.<clinit>():void");
    }

    j(i iVar, i iVar2, Context context, int i) {
        this.d = iVar;
        this.a = iVar2;
        this.b = context;
        this.c = i;
    }

    public final void run() {
        String str = this.a.L.j;
        String str2 = this.a.c;
        if (!d.a(str)) {
            g.a(this.a.c, 996, this.b);
        } else if (this.a.L.i) {
            String str3;
            int i;
            String str4 = null;
            for (int i2 = 0; i2 < 4; i2++) {
                str4 = p.a(str, 5, 5000);
                if (!p.a(str4)) {
                    str3 = str4;
                    i = 1;
                    break;
                }
            }
            str3 = str4;
            i = 0;
            if (i == 0) {
                g.a(str2, 1014, this.b);
                g.a(str2, 1021, a.b(this.b, str), this.b);
                z.b();
                return;
            }
            if (c.a(this.a.L.k, this.b, str.substring(0, str.lastIndexOf("/") + 1), str2, this.a.e())) {
                str4 = this.a.e() ? m.b(this.b, str2) + str2 + z[0] : m.a(this.b, str2) + str2;
                if (o.a(str4, str3, this.b)) {
                    this.a.L.n = new StringBuilder(z[1]).append(str4).toString();
                    g.a(str2, this.c, this.b);
                    i.a(this.d, this.a, this.b);
                    return;
                }
                g.a(str2, 1014, this.b);
                return;
            }
            z.b();
            g.a(str2, 1014, this.b);
        } else {
            g.a(str2, this.c, this.b);
            i.a(this.d, this.a, this.b);
        }
    }
}
