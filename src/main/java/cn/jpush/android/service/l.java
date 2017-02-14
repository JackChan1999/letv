package cn.jpush.android.service;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;
import cn.jpush.android.a;
import cn.jpush.android.util.z;
import java.util.List;

final class l implements Runnable {
    private static final String[] z;
    final /* synthetic */ long a;
    final /* synthetic */ PushService b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 5;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0018yX`?@yX`9Z:\\d(\u0014-]l)\u000e";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0061;
            case 1: goto L_0x0064;
            case 2: goto L_0x0067;
            case 3: goto L_0x006a;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 76;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "X6W` `0Ydl\u0019yX`?@\u0015Ut\"W1`h!Qy\t!";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "@0YdlZ6C;";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "w8Z&8\u0014*@`>@y[u$Q+\u0014q9G1\u0014r)F/]b)GyPt#\u0014-[!?Q:As%@ \u0015";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "d,Gi\u001fQ+Bh/Q";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005c:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0061:
        r9 = 52;
        goto L_0x001f;
    L_0x0064:
        r9 = 89;
        goto L_0x001f;
    L_0x0067:
        r9 = 52;
        goto L_0x001f;
    L_0x006a:
        r9 = 1;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.l.<clinit>():void");
    }

    l(PushService pushService, long j) {
        this.b = pushService;
        this.a = j;
    }

    public final void run() {
        int i = 0;
        try {
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            long s = a.s();
            new StringBuilder(z[2]).append(currentTimeMillis).append(z[0]).append(s);
            z.b();
            if (-1 == s || Math.abs(currentTimeMillis - s) > this.a) {
                List t = cn.jpush.android.util.a.t(this.b.getApplicationContext());
                a.b(currentTimeMillis);
                int size = t != null ? t.size() : 0;
                while (i < size) {
                    Intent intent = new Intent();
                    intent.setComponent((ComponentName) t.get(i));
                    if (VERSION.SDK_INT >= 12) {
                        intent.setFlags(32);
                    }
                    this.b.startService(intent);
                    i++;
                }
                return;
            }
            new StringBuilder(z[1]).append(currentTimeMillis - s);
            z.a();
        } catch (SecurityException e) {
            z.d(z[4], z[3]);
            e.printStackTrace();
        }
    }
}
