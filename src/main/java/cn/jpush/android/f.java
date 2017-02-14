package cn.jpush.android;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import cn.jpush.android.util.z;

final class f implements ServiceConnection {
    private static final String[] z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "L\u0000\u001f,\bCCFe\bC0\u000e7\u0011D\u0000\u000e\u0001\u000e^\u0000\u0004+\tH\u0000\u001f \u0003\u0001C(*\n]\f\u0005 \tY-\n(\u0002\u0017";
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
        r11 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
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
        r0 = "L\u0000\u001f,\bCCFe\bC0\u000e7\u0011D\u0000\u000e\u0006\bC\r\u000e&\u0013H\u0007Ge$B\u000e\u001b*\tH\r\u001f\u000b\u0006@\u0006Q";
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
        r11 = 45;
        goto L_0x0021;
    L_0x004e:
        r11 = 99;
        goto L_0x0021;
    L_0x0051:
        r11 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x0021;
    L_0x0054:
        r11 = 69;
        goto L_0x0021;
    L_0x0057:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.f.<clinit>():void");
    }

    f() {
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        new StringBuilder(z[1]).append(componentName.toString());
        z.b();
        z.c();
        e.o = c.a(iBinder);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        new StringBuilder(z[0]).append(componentName.toString());
        z.b();
        e.o = null;
    }
}
