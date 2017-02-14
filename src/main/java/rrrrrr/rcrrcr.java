package rrrrrr;

import com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;

public class rcrrcr implements Runnable {
    public static int b04270427Ч0427Ч0427 = 0;
    public static int b0427Ч04270427Ч0427 = 2;
    public static int bЧ0427Ч0427Ч0427 = 7;
    private final int b041B041BЛЛ041B041B;
    public final /* synthetic */ HapticPlaybackThread b041BЛ041BЛ041B041B;
    private final long b041BЛЛЛ041B041B;
    private final long bЛ041BЛЛ041B041B;
    private final long bЛЛ041BЛ041B041B;
    private final byte[] bЛЛЛЛ041B041B;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public rcrrcr(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3, long r4, long r6, byte[] r8, int r9, long r10) {
        /*
        r2 = this;
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r2.b041BЛ041BЛ041B041B = r3;
        r2.<init>();
        r2.bЛЛЛЛ041B041B = r8;
        r2.b041BЛЛЛ041B041B = r4;
        r0 = bЧ0427Ч0427Ч0427;
        r1 = bЧЧ04270427Ч0427();
        r0 = r0 + r1;
        r1 = bЧ0427Ч0427Ч0427;
        r0 = r0 * r1;
        r1 = b0427Ч04270427Ч0427;
        r0 = r0 % r1;
        r1 = b04270427Ч0427Ч0427;
        if (r0 == r1) goto L_0x002b;
    L_0x0023:
        r0 = 18;
        bЧ0427Ч0427Ч0427 = r0;
        r0 = 64;
        b04270427Ч0427Ч0427 = r0;
    L_0x002b:
        r2.bЛ041BЛЛ041B041B = r6;
        r2.b041B041BЛЛ041B041B = r9;
        r2.bЛЛ041BЛ041B041B = r10;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcrrcr.<init>(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, long, long, byte[], int, long):void");
    }

    public static int bЧЧ04270427Ч0427() {
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r8 = this;
        r0 = r8.b041BЛ041BЛ041B041B;
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bйййй04390439(r0);
        if (r0 == 0) goto L_0x007f;
    L_0x0008:
        r0 = r8.b041BЛ041BЛ041B041B;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439ййй04390439(r0);
    L_0x000e:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0017;
            case 1: goto L_0x000e;
            default: goto L_0x0012;
        };
    L_0x0012:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0017;
            default: goto L_0x0016;
        };
    L_0x0016:
        goto L_0x0012;
    L_0x0017:
        monitor-enter(r1);
        r0 = r8.b041BЛ041BЛ041B041B;	 Catch:{ all -> 0x0080 }
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439йй04390439(r0);	 Catch:{ all -> 0x0080 }
        r0.remove(r8);	 Catch:{ all -> 0x0080 }
        monitor-exit(r1);	 Catch:{ all -> 0x0080 }
        r0 = r8.b041BЛЛЛ041B041B;
        r2 = r8.bЛ041BЛЛ041B041B;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 < 0) goto L_0x0070;
    L_0x002a:
        r0 = r8.b041BЛ041BЛ041B041B;
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04390439йй04390439(r0);
        r0 = r0.areHapticsEnabled();
        if (r0 == 0) goto L_0x0049;
    L_0x0036:
        r0 = r8.b041BЛ041BЛ041B041B;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bйй0439й04390439(r0);
        r2 = r8.bЛЛЛЛ041B041B;
        r0 = r8.bЛЛЛЛ041B041B;
        r3 = r0.length;
        r4 = r8.bЛЛ041BЛ041B041B;
        r0 = r8.b041B041BЛЛ041B041B;
        r6 = (long) r0;
        r1.update(r2, r3, r4, r6);
    L_0x0049:
        r0 = r8.b041BЛ041BЛ041B041B;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439й0439й04390439(r0);
        monitor-enter(r1);
        r0 = r8.b041BЛ041BЛ041B041B;	 Catch:{ all -> 0x0083 }
        r2 = r8.b041BЛ041BЛ041B041B;	 Catch:{ all -> 0x0083 }
        r2 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439йй0439й0439(r2);	 Catch:{ all -> 0x0083 }
        com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй04390439й04390439(r0, r2);	 Catch:{ all -> 0x0083 }
        r0 = r8.b041BЛ041BЛ041B041B;	 Catch:{ all -> 0x0083 }
        r2 = r8.b041BЛ041BЛ041B041B;	 Catch:{ all -> 0x0083 }
        r2 = r2.b044Eю044E044Eю044E;	 Catch:{ all -> 0x0083 }
        com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b043904390439й04390439(r0, r2);	 Catch:{ all -> 0x0083 }
        r0 = r8.b041BЛ041BЛ041B041B;	 Catch:{ all -> 0x0083 }
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x0083 }
        com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439йй043904390439(r0, r2);	 Catch:{ all -> 0x0083 }
        monitor-exit(r1);	 Catch:{ all -> 0x0083 }
    L_0x0070:
        r0 = r8.b041BЛ041BЛ041B041B;
        r0 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439043904390439й(r0);
        r1 = r8.b041BЛ041BЛ041B041B;
        r1 = com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439й043904390439(r1);
        r0.post(r1);
    L_0x007f:
        return;
    L_0x0080:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0080 }
        throw r0;
    L_0x0083:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0083 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rcrrcr.run():void");
    }
}
