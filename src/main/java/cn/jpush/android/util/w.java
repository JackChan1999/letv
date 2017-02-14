package cn.jpush.android.util;

import org.json.JSONException;
import org.json.JSONObject;

final class w implements Runnable {
    private static final String[] z;
    final /* synthetic */ JSONObject a;

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
        r2 = "~FyqpFpy`";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000c:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0058;
    L_0x0013:
        r8 = r1;
    L_0x0014:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0019:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004c;
            case 1: goto L_0x004f;
            case 2: goto L_0x0052;
            case 3: goto L_0x0055;
            default: goto L_0x0020;
        };
    L_0x0020:
        r11 = 31;
    L_0x0022:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002e;
    L_0x002a:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0019;
    L_0x002e:
        r7 = r2;
        r2 = r9;
    L_0x0030:
        if (r7 > r8) goto L_0x0014;
    L_0x0032:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r3] = r2;
        r0 = "Fql}m@4p}x\u000e";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004c:
        r11 = 52;
        goto L_0x0022;
    L_0x004f:
        r11 = 20;
        goto L_0x0022;
    L_0x0052:
        r11 = 28;
        goto L_0x0022;
    L_0x0055:
        r11 = 18;
        goto L_0x0022;
    L_0x0058:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.w.<clinit>():void");
    }

    w(JSONObject jSONObject) {
        this.a = jSONObject;
    }

    public final void run() {
        try {
            z.c(z[0], new StringBuilder(z[1]).append(this.a.toString(1)).toString());
        } catch (JSONException e) {
            z.c(z[0], new StringBuilder(z[1]).append(this.a.toString()).toString());
        }
    }
}
