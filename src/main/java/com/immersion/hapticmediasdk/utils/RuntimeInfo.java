package com.immersion.hapticmediasdk.utils;

public class RuntimeInfo {
    public static int b044A044Aъ044A044Aъ = 2;
    public static int b044A044Aъъ044Aъ = 1;
    public static int bъ044Aъ044A044Aъ = 0;
    public static int bъ044Aъъ044Aъ = 58;
    private boolean bЭ042DЭ042D042D042D = true;

    public static int b044Aъ044Aъ044Aъ() {
        return 90;
    }

    public static int bъъ044Aъ044Aъ() {
        return 2;
    }

    public synchronized boolean areHapticsEnabled() {
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                throw e;
            } catch (Exception e2) {
                bъ044Aъъ044Aъ = b044Aъ044Aъ044Aъ();
                return this.bЭ042DЭ042D042D042D;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mute() {
        /*
        r2 = this;
        r1 = 1;
        monitor-enter(r2);
        r0 = -1;
    L_0x0003:
        switch(r1) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r1) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        r1 = new int[r0];	 Catch:{ Exception -> 0x0019 }
        goto L_0x000a;
    L_0x000d:
        r0 = move-exception;
        r0 = b044Aъ044Aъ044Aъ();	 Catch:{ all -> 0x0023 }
        bъ044Aъъ044Aъ = r0;	 Catch:{ all -> 0x0023 }
        r0 = 0;
        r2.bЭ042DЭ042D042D042D = r0;	 Catch:{ all -> 0x0023 }
        monitor-exit(r2);
        return;
    L_0x0019:
        r1 = move-exception;
        r1 = b044Aъ044Aъ044Aъ();	 Catch:{ all -> 0x0023 }
        bъ044Aъъ044Aъ = r1;	 Catch:{ all -> 0x0023 }
    L_0x0020:
        r1 = new int[r0];	 Catch:{ Exception -> 0x000d }
        goto L_0x0020;
    L_0x0023:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.RuntimeInfo.mute():void");
    }

    public synchronized void unmute() {
        if (((bъ044Aъъ044Aъ + b044A044Aъъ044Aъ) * bъ044Aъъ044Aъ) % b044A044Aъ044A044Aъ != bъ044Aъ044A044Aъ) {
            bъ044Aъъ044Aъ = b044Aъ044Aъ044Aъ();
            bъ044Aъ044A044Aъ = b044Aъ044Aъ044Aъ();
        }
        try {
            this.bЭ042DЭ042D042D042D = true;
        } catch (Exception e) {
            throw e;
        }
        return;
    }
}
