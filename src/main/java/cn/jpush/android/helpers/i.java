package cn.jpush.android.helpers;

import android.content.Context;
import cn.jpush.android.a;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.l;
import cn.jpush.android.util.z;

public final class i {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = "*\u0011V_)<\n_\\";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006b;
    L_0x0013:
        r9 = r1;
    L_0x0014:
        r10 = r4;
        r11 = r9;
        r14 = r8;
        r8 = r4;
        r4 = r14;
    L_0x0019:
        r13 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x005f;
            case 1: goto L_0x0062;
            case 2: goto L_0x0065;
            case 3: goto L_0x0068;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 90;
    L_0x0022:
        r12 = r12 ^ r13;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r4 != 0) goto L_0x002e;
    L_0x002a:
        r8 = r10;
        r11 = r9;
        r9 = r4;
        goto L_0x0019;
    L_0x002e:
        r8 = r4;
        r4 = r10;
    L_0x0030:
        if (r8 > r9) goto L_0x0014;
    L_0x0032:
        r8 = new java.lang.String;
        r8.<init>(r4);
        r4 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0050;
            case 2: goto L_0x005a;
            default: goto L_0x003e;
        };
    L_0x003e:
        r6[r5] = r4;
        r0 = "KM";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r6[r5] = r4;
        r0 = "V\f_]\f\u001f\u0011@P5\u0014Y";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0050:
        r6[r5] = r4;
        r4 = 3;
        r0 = "\t\u0007X\u0019,\u001f\u0011@P5\u0014C\u001e\u00199\u000f\u0011e\\(\t\n\\W`";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000c;
    L_0x005a:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x005f:
        r12 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        goto L_0x0022;
    L_0x0062:
        r12 = 99;
        goto L_0x0022;
    L_0x0065:
        r12 = 51;
        goto L_0x0022;
    L_0x0068:
        r12 = 57;
        goto L_0x0022;
    L_0x006b:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.i.<clinit>():void");
    }

    public static void a(Context context) {
        z.b();
        String a = ServiceInterface.a();
        String a2 = l.a(context);
        if (ai.a(a2)) {
            a2 = a.I();
        }
        new StringBuilder(z[3]).append(a).append(z[2]).append(a2);
        z.a();
        if (ai.a(a2)) {
            z.b();
        } else if (!(a.equals(a2) || a.startsWith(z[1]) || !a2.startsWith(z[1]))) {
            l.a(context, a);
            z.b();
            b(context);
            a.k(context);
        }
        a.o(a);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized boolean b(android.content.Context r14) {
        /*
        r6 = 0;
        r13 = 8;
        r4 = 0;
        r10 = cn.jpush.android.helpers.i.class;
        monitor-enter(r10);
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x005e }
        r2 = "";
        r3 = "";
        r0 = 8;
        r11 = new byte[r0];	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0058 }
        r0 = z;	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0058 }
        r1 = 0;
        r0 = r0[r1];	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0058 }
        r12 = r14.openFileInput(r0);	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0058 }
        r0 = 0;
        r1 = 8;
        r12.read(r11, r0, r1);	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0058 }
        r5 = r4;
        r0 = r6;
    L_0x0024:
        if (r5 >= r13) goto L_0x0033;
    L_0x0026:
        r8 = r0 << r13;
        r0 = r11[r5];	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        r0 = r0 & 255;
        r0 = (long) r0;	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        r8 = r8 + r0;
        r0 = r5 + 1;
        r5 = r0;
        r0 = r8;
        goto L_0x0024;
    L_0x0033:
        r5 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        r5.<init>();	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
    L_0x0038:
        r8 = r12.read();	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        r9 = -1;
        if (r8 == r9) goto L_0x0050;
    L_0x003f:
        r8 = (char) r8;	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        r5.append(r8);	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        goto L_0x0038;
    L_0x0044:
        r0 = move-exception;
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x005e }
        r0 = r6;
    L_0x0049:
        r5 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
        if (r5 != 0) goto L_0x0061;
    L_0x004d:
        r0 = r4;
    L_0x004e:
        monitor-exit(r10);
        return r0;
    L_0x0050:
        r12.close();	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        r2 = r5.toString();	 Catch:{ FileNotFoundException -> 0x0044, IOException -> 0x0081 }
        goto L_0x0049;
    L_0x0058:
        r0 = move-exception;
        r0 = r6;
    L_0x005a:
        cn.jpush.android.util.z.g();	 Catch:{ all -> 0x005e }
        goto L_0x0049;
    L_0x005e:
        r0 = move-exception;
        monitor-exit(r10);
        throw r0;
    L_0x0061:
        r5 = cn.jpush.android.util.ai.a(r3);	 Catch:{ all -> 0x005e }
        if (r5 == 0) goto L_0x006b;
    L_0x0067:
        r3 = cn.jpush.android.a.x();	 Catch:{ all -> 0x005e }
    L_0x006b:
        r5 = cn.jpush.android.util.ai.a(r3);	 Catch:{ all -> 0x005e }
        if (r5 == 0) goto L_0x0076;
    L_0x0071:
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x005e }
        r0 = r4;
        goto L_0x004e;
    L_0x0076:
        r4 = cn.jpush.android.util.a.j(r14);	 Catch:{ all -> 0x005e }
        r5 = cn.jpush.android.e.f;	 Catch:{ all -> 0x005e }
        cn.jpush.android.a.a(r0, r2, r3, r4, r5);	 Catch:{ all -> 0x005e }
        r0 = 1;
        goto L_0x004e;
    L_0x0081:
        r5 = move-exception;
        goto L_0x005a;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.i.b(android.content.Context):boolean");
    }
}
