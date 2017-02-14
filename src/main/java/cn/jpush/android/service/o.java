package cn.jpush.android.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cn.jpush.android.util.z;
import cn.jpush.b.a.a.g;
import java.lang.ref.WeakReference;

final class o extends Handler {
    private static final String[] z;
    private WeakReference<n> a;

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
        r4 = 3;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 5;
        r6 = new java.lang.String[r0];
        r5 = "\bS{@\u0015\bH|A\u001e";
        r0 = -1;
        r7 = r6;
        r8 = r6;
        r6 = r1;
    L_0x000d:
        r5 = r5.toCharArray();
        r9 = r5.length;
        if (r9 > r2) goto L_0x0075;
    L_0x0014:
        r10 = r1;
    L_0x0015:
        r11 = r5;
        r12 = r10;
        r15 = r9;
        r9 = r5;
        r5 = r15;
    L_0x001a:
        r14 = r9[r10];
        r13 = r12 % 5;
        switch(r13) {
            case 0: goto L_0x0069;
            case 1: goto L_0x006c;
            case 2: goto L_0x006f;
            case 3: goto L_0x0072;
            default: goto L_0x0021;
        };
    L_0x0021:
        r13 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
    L_0x0023:
        r13 = r13 ^ r14;
        r13 = (char) r13;
        r9[r10] = r13;
        r10 = r12 + 1;
        if (r5 != 0) goto L_0x002f;
    L_0x002b:
        r9 = r11;
        r12 = r10;
        r10 = r5;
        goto L_0x001a;
    L_0x002f:
        r9 = r5;
        r5 = r11;
    L_0x0031:
        if (r9 > r10) goto L_0x0015;
    L_0x0033:
        r9 = new java.lang.String;
        r9.<init>(r5);
        r5 = r9.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            case 1: goto L_0x0051;
            case 2: goto L_0x005a;
            case 3: goto L_0x0064;
            default: goto L_0x003f;
        };
    L_0x003f:
        r7[r6] = r5;
        r0 = "9Yd[\u0015\u0018H|@\u0017?TgK\u0011\u000f";
        r5 = r0;
        r6 = r2;
        r7 = r8;
        r0 = r1;
        goto L_0x000d;
    L_0x0048:
        r7[r6] = r5;
        r0 = "#]{J\u001c\u000e\u001cx]\u0017K\u00115Z\u0018\u0019YtJ9\u000f\u0006";
        r5 = r0;
        r6 = r3;
        r7 = r8;
        r0 = r2;
        goto L_0x000d;
    L_0x0051:
        r7[r6] = r5;
        r0 = ">R}O\u001e\u000fPpJP\u0006Or\u000e]K";
        r5 = r0;
        r6 = r4;
        r7 = r8;
        r0 = r3;
        goto L_0x000d;
    L_0x005a:
        r7[r6] = r5;
        r5 = 4;
        r0 = ">RpV\u0000\u000e_aK\u0014K\u00115Y\u0011\u0005H5Z\u001fKOp@\u0014KR`B\u001cKNp_\u0005\u000eOa\u0000";
        r6 = r5;
        r7 = r8;
        r5 = r0;
        r0 = r4;
        goto L_0x000d;
    L_0x0064:
        r7[r6] = r5;
        z = r8;
        return;
    L_0x0069:
        r13 = 107; // 0x6b float:1.5E-43 double:5.3E-322;
        goto L_0x0023;
    L_0x006c:
        r13 = 60;
        goto L_0x0023;
    L_0x006f:
        r13 = 21;
        goto L_0x0023;
    L_0x0072:
        r13 = 46;
        goto L_0x0023;
    L_0x0075:
        r10 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.o.<clinit>():void");
    }

    public o(Looper looper, n nVar) {
        super(looper);
        this.a = new WeakReference(nVar);
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        new StringBuilder(z[2]).append(Thread.currentThread().getId());
        z.a();
        n nVar = (n) this.a.get();
        if (nVar == null) {
            z.b();
            return;
        }
        p pVar;
        switch (message.what) {
            case 7401:
                if (message.obj == null) {
                    z.d(z[1], z[4]);
                    return;
                } else {
                    n.a(nVar, (g) message.obj, message.arg1);
                    return;
                }
            case 7402:
                n.a(nVar, message.getData().getLong(z[0]), message.obj);
                return;
            case 7403:
                pVar = (p) nVar.a.get((Long) message.obj);
                if (pVar == null) {
                    z.d();
                    return;
                } else {
                    nVar.a(pVar);
                    return;
                }
            case 7404:
                pVar = (p) nVar.a.get((Long) message.obj);
                if (pVar == null) {
                    z.d();
                    return;
                } else {
                    n.a(nVar, pVar);
                    return;
                }
            case 7405:
                n.b(nVar);
                return;
            default:
                new StringBuilder(z[3]).append(message.what);
                z.d();
                return;
        }
    }
}
