package com.immersion.content;

import android.content.Context;
import com.immersion.Log;

public class EndpointWarp {
    private static final String b0414Д0414Д04140414 = "EndpointWarp";
    public static int b043Bл043B043B043Bл = 5;
    public static int bл043B043B043B043Bл = 0;
    public static int bл043Bллл043B = 1;
    public static int bллллл043B = 2;
    long b041404140414Д04140414;
    private long bД04140414Д04140414;
    long bДДД041404140414;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public EndpointWarp(android.content.Context r5, long r6) {
        /*
        r4 = this;
        r2 = 0;
        r4.<init>();
        r0 = b043Bл043B043B043Bл;
        r1 = b043B043B043B043B043Bл();
        r0 = r0 + r1;
        r1 = b043Bл043B043B043Bл;
        r0 = r0 * r1;
        r1 = bллллл043B;
        r0 = r0 % r1;
        r1 = bл043B043B043B043Bл;
        if (r0 == r1) goto L_0x001f;
    L_0x0015:
        r0 = b043Bлллл043B();
        b043Bл043B043B043Bл = r0;
        r0 = 88;
        bл043B043B043B043Bл = r0;
    L_0x001f:
        switch(r2) {
            case 0: goto L_0x0026;
            case 1: goto L_0x001f;
            default: goto L_0x0022;
        };
    L_0x0022:
        switch(r2) {
            case 0: goto L_0x0026;
            case 1: goto L_0x001f;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0022;
    L_0x0026:
        r0 = r4.initRenderer(r5);
        r4.b041404140414Д04140414 = r0;
        r4.bД04140414Д04140414 = r6;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.EndpointWarp.<init>(android.content.Context, long):void");
    }

    public static int b043B043B043B043B043Bл() {
        return 1;
    }

    public static int b043Bлллл043B() {
        return 40;
    }

    private native void dispose(long j, long j2);

    private native void flushWarp(long j);

    private native long getWarpCurrentPosition(long j);

    private native long initRenderer(Context context);

    public static boolean loadSharedLibrary() {
        try {
            System.loadLibrary("ImmEndpointWarpJ");
            return true;
        } catch (UnsatisfiedLinkError e) {
            String property = System.getProperty("java.vm.name");
            if (((b043Bл043B043B043Bл + bл043Bллл043B) * b043Bл043B043B043Bл) % bллллл043B != bл043B043B043B043Bл) {
                b043Bл043B043B043Bл = 70;
                bл043B043B043B043Bл = b043Bлллл043B();
            }
            if (property.contains("Java HotSpot")) {
                return true;
            }
            Log.e(b0414Д0414Д04140414, "Unable to load libImmEndpointWarpJ.so.Please make sure this file is in the libs/armeabi folder.");
            e.printStackTrace();
            return false;
        }
    }

    private native long setMetaData(long j, long j2, byte[] bArr, int i);

    private native void startWarp(long j);

    private native void stopWarp(long j);

    private native void updateWarp(long j, byte[] bArr, int i, long j2, long j3);

    public void dispose() {
        try {
            try {
                dispose(this.bДДД041404140414, this.b041404140414Д04140414);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void flush() {
        /*
        r2 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = b043Bл043B043B043Bл;
        r1 = bл043Bллл043B;
        r0 = r0 + r1;
        r1 = b043Bл043B043B043Bл;
        r0 = r0 * r1;
        r1 = bллллл043B;
        r0 = r0 % r1;
        r1 = bл043B043B043B043Bл;
        if (r0 == r1) goto L_0x0021;
    L_0x0017:
        r0 = b043Bлллл043B();
        b043Bл043B043B043Bл = r0;
        r0 = 50;
        bл043B043B043B043Bл = r0;
    L_0x0021:
        r0 = r2.bДДД041404140414;
        r2.flushWarp(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.EndpointWarp.flush():void");
    }

    public long getCurrentPosition() {
        long j = this.bДДД041404140414;
        if (((b043Bл043B043B043Bл + bл043Bллл043B) * b043Bл043B043B043Bл) % bллллл043B != bл043B043B043B043Bл) {
            b043Bл043B043B043Bл = 31;
            bл043B043B043B043Bл = 31;
        }
        return getWarpCurrentPosition(j);
    }

    public void setMetaData(byte[] bArr, int i) {
        long j = this.b041404140414Д04140414;
        long j2 = this.bД04140414Д04140414;
        if (((b043Bл043B043B043Bл + bл043Bллл043B) * b043Bл043B043B043Bл) % bллллл043B != bл043B043B043B043Bл) {
            b043Bл043B043B043Bл = b043Bлллл043B();
            bл043B043B043B043Bл = b043Bлллл043B();
        }
        this.bДДД041404140414 = setMetaData(j, j2, bArr, i);
    }

    public void start() {
        startWarp(this.bДДД041404140414);
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                b043Bл043B043B043Bл = b043Bлллл043B();
                while (true) {
                    switch (null) {
                        case null:
                            return;
                        case 1:
                            break;
                        default:
                            while (true) {
                                switch (1) {
                                    case 0:
                                        break;
                                    case 1:
                                        return;
                                    default:
                                }
                            }
                    }
                }
            }
        }
    }

    public void stop() {
        if (((b043Bл043B043B043Bл + bл043Bллл043B) * b043Bл043B043B043Bл) % bллллл043B != bл043B043B043B043Bл) {
            b043Bл043B043B043Bл = b043Bлллл043B();
            bл043B043B043B043Bл = 34;
        }
        stopWarp(this.bДДД041404140414);
        while (true) {
            switch (null) {
                case null:
                    return;
                case 1:
                    break;
                default:
                    while (true) {
                        switch (null) {
                            case null:
                                return;
                            case 1:
                                break;
                            default:
                        }
                    }
            }
        }
    }

    public void update(byte[] bArr, int i, long j, long j2) {
        try {
            long j3 = this.bДДД041404140414;
            if (((b043Bл043B043B043Bл + bл043Bллл043B) * b043Bл043B043B043Bл) % bллллл043B != bл043B043B043B043Bл) {
                b043Bл043B043B043Bл = b043Bлллл043B();
                bл043B043B043B043Bл = 41;
            }
            try {
                updateWarp(j3, bArr, i, j, j2);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
