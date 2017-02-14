package cn.jpush.android.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import cn.jpush.android.util.z;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;

final class e extends BroadcastReceiver {
    private static final String[] z;
    final /* synthetic */ d a;

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
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "5e6%\u0014=o|9\u001e %%>\u001d=%\u0001\u0014:\u001aT\u0000\u0012(\u0001G\u0006\u0004";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0055;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "5e6%\u0014=o|9\u001e %%>\u001d=%\u0005\u001e=\u001dT\u0001\u0003:\u0000N\r\u00143\u0015E\u0015\u0012?";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "#b4>$'3#\u001e";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0055:
        r11 = 84;
        goto L_0x0021;
    L_0x0058:
        r11 = 11;
        goto L_0x0021;
    L_0x005b:
        r11 = 82;
        goto L_0x0021;
    L_0x005e:
        r11 = 87;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.e.<clinit>():void");
    }

    private e(d dVar) {
        this.a = dVar;
    }

    public final void onReceive(Context context, Intent intent) {
        boolean z = false;
        if (this.a.x && d.a(this.a.j)) {
            if (this.a.p == 2) {
                if (z[0].equals(intent.getAction())) {
                    this.a.l.removeMessages(5);
                    if (System.currentTimeMillis() - this.a.o > DanmakuFactory.MIN_DANMAKU_DURATION) {
                        this.a.l.sendEmptyMessageDelayed(5, DanmakuFactory.MIN_DANMAKU_DURATION);
                    } else {
                        this.a.l.sendEmptyMessage(5);
                    }
                } else if (this.a.r) {
                    if (z[1].equals(intent.getAction()) && intent.getIntExtra(z[2], 4) == 2) {
                        this.a.d = true;
                        this.a.m = false;
                        z.b();
                    }
                }
            }
        } else if (intent.getIntExtra(z[2], 4) == 3) {
            WifiManager b = this.a.s.b();
            if (b != null) {
                z = b.startScan();
            }
            if (z) {
                try {
                    this.a.A = this.a.s.c();
                    this.a.j.unregisterReceiver(this.a.n);
                    if (!this.a.x) {
                        this.a.d();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
