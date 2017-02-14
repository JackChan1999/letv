package com.immersion.hapticmediasdk.utils;

import android.os.SystemClock;

public class Profiler {
    public static int b041E041EО041EОО = 1;
    public static int bО041EО041EОО = 90;
    public static int bОО041E041EОО = 2;
    public long mStartTime;
    public long mStartTimeII;

    public Profiler() {
        int i = bО041EО041EОО;
        switch ((i * (b041E041EО041EОО + i)) % bОО041E041EОО) {
            case 0:
                break;
            default:
                bО041EО041EОО = 96;
                b041E041EО041EОО = b041EО041E041EОО();
                break;
        }
    }

    public static int b041EО041E041EОО() {
        return 42;
    }

    public static int bО041E041E041EОО() {
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getDuration() {
        /*
        r6 = this;
        r0 = android.os.SystemClock.elapsedRealtime();
    L_0x0004:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000d;
            default: goto L_0x0008;
        };
    L_0x0008:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0004;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0008;
    L_0x000d:
        r2 = r6.mStartTime;
        r4 = bО041EО041EОО;
        r5 = bО041E041E041EОО();
        r5 = r5 + r4;
        r4 = r4 * r5;
        r5 = bОО041E041EОО;
        r4 = r4 % r5;
        switch(r4) {
            case 0: goto L_0x0029;
            default: goto L_0x001d;
        };
    L_0x001d:
        r4 = b041EО041E041EОО();
        bО041EО041EОО = r4;
        r4 = b041EО041E041EОО();
        b041E041EО041EОО = r4;
    L_0x0029:
        r0 = r0 - r2;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.Profiler.getDuration():long");
    }

    public long getDurationII() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i = bО041EО041EОО;
        switch ((i * (b041E041EО041EОО + i)) % bОО041E041EОО) {
            case 0:
                break;
            default:
                bО041EО041EОО = b041EО041E041EОО();
                b041E041EО041EОО = 42;
                break;
        }
        return elapsedRealtime - this.mStartTimeII;
    }

    public void startTiming() {
        this.mStartTime = SystemClock.elapsedRealtime();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startTimingII() {
        /*
        r3 = this;
        r2 = 1;
        r0 = -1;
    L_0x0002:
        r1 = new int[r0];	 Catch:{ Exception -> 0x001a }
        goto L_0x0002;
    L_0x0005:
        r0 = move-exception;
        r0 = b041EО041E041EОО();
        bО041EО041EОО = r0;
        r0 = android.os.SystemClock.elapsedRealtime();
    L_0x0010:
        switch(r2) {
            case 0: goto L_0x0010;
            case 1: goto L_0x0017;
            default: goto L_0x0013;
        };
    L_0x0013:
        switch(r2) {
            case 0: goto L_0x0010;
            case 1: goto L_0x0017;
            default: goto L_0x0016;
        };
    L_0x0016:
        goto L_0x0013;
    L_0x0017:
        r3.mStartTimeII = r0;
        return;
    L_0x001a:
        r1 = move-exception;
        r1 = b041EО041E041EОО();
        bО041EО041EОО = r1;
    L_0x0021:
        r1 = new int[r0];	 Catch:{ Exception -> 0x0005 }
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.Profiler.startTimingII():void");
    }
}
