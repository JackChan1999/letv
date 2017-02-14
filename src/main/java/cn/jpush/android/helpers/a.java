package cn.jpush.android.helpers;

import cn.jpush.android.util.z;
import java.net.InetAddress;

final class a extends Thread {
    private static final String[] z;
    private String a = null;
    private InetAddress b = null;

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
        r4 = 3;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 5;
        r6 = new java.lang.String[r0];
        r5 = "~\u000f]\u0015JZ\u000fJZbb9\u000e\u001cGE\u0006\u000e\u001cTC\u0007\u000e\u0012I_\u001e\u0014Z";
        r0 = -1;
        r7 = r6;
        r8 = r6;
        r6 = r1;
    L_0x000e:
        r5 = r5.toCharArray();
        r9 = r5.length;
        if (r9 > r2) goto L_0x0078;
    L_0x0015:
        r10 = r1;
    L_0x0016:
        r11 = r5;
        r12 = r10;
        r15 = r9;
        r9 = r5;
        r5 = r15;
    L_0x001b:
        r14 = r9[r10];
        r13 = r12 % 5;
        switch(r13) {
            case 0: goto L_0x006c;
            case 1: goto L_0x006f;
            case 2: goto L_0x0072;
            case 3: goto L_0x0075;
            default: goto L_0x0022;
        };
    L_0x0022:
        r13 = 38;
    L_0x0024:
        r13 = r13 ^ r14;
        r13 = (char) r13;
        r9[r10] = r13;
        r10 = r12 + 1;
        if (r5 != 0) goto L_0x0030;
    L_0x002c:
        r9 = r11;
        r12 = r10;
        r10 = r5;
        goto L_0x001b;
    L_0x0030:
        r9 = r5;
        r5 = r11;
    L_0x0032:
        if (r9 > r10) goto L_0x0016;
    L_0x0034:
        r9 = new java.lang.String;
        r9.<init>(r5);
        r5 = r9.intern();
        switch(r0) {
            case 0: goto L_0x0049;
            case 1: goto L_0x0053;
            case 2: goto L_0x005c;
            case 3: goto L_0x0067;
            default: goto L_0x0040;
        };
    L_0x0040:
        r7[r6] = r5;
        r0 = "o\u0005@\u0014CO\u001eG\u0014Ad\u000fB\nC^";
        r5 = r0;
        r6 = r2;
        r7 = r8;
        r0 = r1;
        goto L_0x000e;
    L_0x0049:
        r7[r6] = r5;
        r0 = "x\u0002KZ@M\u0003B\u000fTIJO\nVI\u000b\\\t\u0006X\u0005\u000e\u0012GZ\u000f\u000e\u0018CI\u0004\u000e\u001b\u0006@\u000bM\u0011\u0006C\f\u000e3hx/|4cxJ\u000f";
        r5 = r0;
        r6 = r3;
        r7 = r8;
        r0 = r2;
        goto L_0x000e;
    L_0x0053:
        r7[r6] = r5;
        r0 = "^\u000f]\u0015JZ\u000fJZbb9\u000eW\u0006D\u0005]\u000e\u001c";
        r5 = r0;
        r6 = r4;
        r7 = r8;
        r0 = r3;
        goto L_0x000e;
    L_0x005c:
        r7[r6] = r5;
        r5 = 4;
        r0 = "y\u0004E\u0014I[\u0004\u000e\u0012I_\u001e\u000e\u001f^O\u000f^\u000eOC\u0004\u000f";
        r6 = r5;
        r7 = r8;
        r5 = r0;
        r0 = r4;
        goto L_0x000e;
    L_0x0067:
        r7[r6] = r5;
        z = r8;
        return;
    L_0x006c:
        r13 = 44;
        goto L_0x0024;
    L_0x006f:
        r13 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        goto L_0x0024;
    L_0x0072:
        r13 = 46;
        goto L_0x0024;
    L_0x0075:
        r13 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        goto L_0x0024;
    L_0x0078:
        r10 = r1;
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.a.<clinit>():void");
    }

    public a(String str) {
        this.a = str;
    }

    public final synchronized InetAddress a() {
        InetAddress inetAddress;
        if (this.b != null) {
            inetAddress = this.b;
        } else {
            new StringBuilder(z[0]).append(this.a);
            z.d();
            inetAddress = null;
        }
        return inetAddress;
    }

    public final void run() {
        try {
            new StringBuilder(z[3]).append(this.a);
            z.c();
            this.b = InetAddress.getByName(this.a);
        } catch (Throwable e) {
            z.a(z[1], z[4], e);
        } catch (Throwable e2) {
            z.a(z[1], z[2], e2);
        }
    }
}
