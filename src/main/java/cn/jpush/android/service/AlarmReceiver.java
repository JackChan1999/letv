package cn.jpush.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.jpush.android.e;
import cn.jpush.android.util.z;

public class AlarmReceiver extends BroadcastReceiver {
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
        r13 = 4;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r5 = new java.lang.String[r13];
        r4 = "Jje:\u0014@mA:W/\u000e";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006a;
    L_0x0013:
        r9 = r1;
    L_0x0014:
        r10 = r4;
        r11 = r9;
        r15 = r8;
        r8 = r4;
        r4 = r15;
    L_0x0019:
        r14 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x005f;
            case 1: goto L_0x0062;
            case 2: goto L_0x0064;
            case 3: goto L_0x0067;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
    L_0x0022:
        r12 = r12 ^ r14;
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
        r0 = "WpT\u0000\u0013@hV&";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r6[r5] = r4;
        r0 = "Fj\u00195\u0007Pw_q\u0016K`E0\u001eA*^1\u0003@jCq%qG";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0050:
        r6[r5] = r4;
        r4 = 3;
        r0 = "dhV-\u001awaT:\u001eSaE";
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
        r12 = 37;
        goto L_0x0022;
    L_0x0062:
        r12 = r13;
        goto L_0x0022;
    L_0x0064:
        r12 = 55;
        goto L_0x0022;
    L_0x0067:
        r12 = 95;
        goto L_0x0022;
    L_0x006a:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.AlarmReceiver.<clinit>():void");
    }

    public void onReceive(Context context, Intent intent) {
        z.b(z[3], z[0]);
        if (!e.a(context.getApplicationContext())) {
            return;
        }
        if (ServiceInterface.e(context)) {
            ServiceInterface.b(context, false);
            return;
        }
        try {
            Intent intent2 = new Intent(context, PushService.class);
            intent2.setAction(z[2]);
            intent2.putExtra(z[1], 0);
            context.startService(intent2);
        } catch (SecurityException e) {
            e.printStackTrace();
            z.b();
        } catch (Exception e2) {
            e2.printStackTrace();
            z.b();
        }
    }
}
