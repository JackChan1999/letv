package com.immersion.aws.pm;

import android.content.SharedPreferences;
import com.immersion.Log;
import rrrrrr.crcccr;
import rrrrrr.crccrr;

public class NativePolicyManager implements crcccr, crccrr {
    public static int b0444фффф0444 = 1;
    private static final String bннн043Dн043D = "NativePolicyManager";
    public static int bф0444ффф0444 = 2;
    public static int bфф0444фф0444 = 0;
    public static int bффффф0444 = 96;
    private long b043Dнн043Dн043D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NativePolicyManager(java.lang.String r5, com.immersion.aws.pm.PolicyPathInfo r6, android.content.SharedPreferences r7) throws java.lang.OutOfMemoryError, java.lang.UnsatisfiedLinkError {
        /*
        r4 = this;
        r4.<init>();
        r0 = com.immersion.content.EndpointWarp.loadSharedLibrary();
        if (r0 != 0) goto L_0x0011;
    L_0x0009:
        r0 = new java.lang.UnsatisfiedLinkError;
        r1 = "Failed to load libImmEndpointWarpJ.so";
        r0.<init>(r1);
        throw r0;
    L_0x0011:
        r0 = r4.bщ0449щщ04490449(r5, r6, r7);
    L_0x0015:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x001e;
            case 1: goto L_0x0015;
            default: goto L_0x0019;
        };
    L_0x0019:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0015;
            case 1: goto L_0x001e;
            default: goto L_0x001d;
        };
    L_0x001d:
        goto L_0x0019;
    L_0x001e:
        r2 = bффффф0444;
        r3 = b0444фффф0444;
        r3 = r3 + r2;
        r2 = r2 * r3;
        r3 = bф0444ффф0444;
        r2 = r2 % r3;
        switch(r2) {
            case 0: goto L_0x0032;
            default: goto L_0x002a;
        };
    L_0x002a:
        r2 = 64;
        bффффф0444 = r2;
        r2 = 73;
        b0444фффф0444 = r2;
    L_0x0032:
        r4.b043Dнн043Dн043D = r0;
        r0 = "NativePolicyManager";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = r4.toString();
        r1 = r1.append(r2);
        r2 = "init: ";
        r1 = r1.append(r2);
        r2 = r4.b043Dнн043Dн043D;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = r4.b043Dнн043Dн043D;
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 != 0) goto L_0x0064;
    L_0x005e:
        r0 = new java.lang.OutOfMemoryError;
        r0.<init>();
        throw r0;
    L_0x0064:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.NativePolicyManager.<init>(java.lang.String, com.immersion.aws.pm.PolicyPathInfo, android.content.SharedPreferences):void");
    }

    public static int b04440444ффф0444() {
        return 70;
    }

    public static int b0444ф0444фф0444() {
        return 1;
    }

    public static native void naDispose(long j);

    public static native long naInit(String str, String str2, String str3, SharedPreferences sharedPreferences);

    public static native void naPrepareContent(long j, String str);

    public static native void naSignal(long j, String str);

    public long b0449щщщ04490449() {
        if (((bффффф0444 + b0444фффф0444) * bффффф0444) % bф0444ффф0444 != bфф0444фф0444) {
            bффффф0444 = b04440444ффф0444();
            bфф0444фф0444 = b04440444ффф0444();
        }
        return this.b043Dнн043Dн043D;
    }

    public long bщ0449щщ04490449(String str, PolicyPathInfo policyPathInfo, SharedPreferences sharedPreferences) {
        int i = bффффф0444;
        switch ((i * (b0444фффф0444 + i)) % bф0444ффф0444) {
            case 0:
                break;
            default:
                bффффф0444 = b04440444ффф0444();
                bфф0444фф0444 = 16;
                break;
        }
        try {
            try {
                return naInit(str, policyPathInfo.getUniqueId(), policyPathInfo.getGracePeriodExtStoragePath(), sharedPreferences);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void dispose() {
        try {
            naDispose(this.b043Dнн043Dн043D);
            this.b043Dнн043Dн043D = 0;
            String str = bннн043Dн043D;
            try {
                int i = bффффф0444;
                switch ((i * (b0444фффф0444 + i)) % bф0444ффф0444) {
                    case 0:
                        break;
                    default:
                        bффффф0444 = 9;
                        b0444фффф0444 = b04440444ффф0444();
                        break;
                }
                Log.d(str, toString() + " dispose! " + this.b043Dнн043Dн043D);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void onPolicyFileFailure() {
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPolicyFileSuccess(java.lang.String r5) {
        /*
        r4 = this;
        r0 = "NativePolicyManager";
        r1 = new java.lang.StringBuilder;
        r2 = b04440444ффф0444();
        r3 = b0444ф0444фф0444();
        r3 = r3 + r2;
        r2 = r2 * r3;
        r3 = bф0444ффф0444;
        r2 = r2 % r3;
        switch(r2) {
            case 0: goto L_0x001e;
            default: goto L_0x0014;
        };
    L_0x0014:
        r2 = 72;
        bффффф0444 = r2;
        r2 = b04440444ффф0444();
        bфф0444фф0444 = r2;
    L_0x001e:
        r1.<init>();
        r2 = "onPolicyFileSuccess. ptr: ";
        r1 = r1.append(r2);
        r2 = r4.b043Dнн043Dн043D;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = r4.b043Dнн043Dн043D;
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 == 0) goto L_0x004a;
    L_0x003c:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x003c;
            case 1: goto L_0x0045;
            default: goto L_0x0040;
        };
    L_0x0040:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0045;
            case 1: goto L_0x003c;
            default: goto L_0x0044;
        };
    L_0x0044:
        goto L_0x0040;
    L_0x0045:
        r0 = r4.b043Dнн043Dн043D;
        naSignal(r0, r5);
    L_0x004a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.NativePolicyManager.onPolicyFileSuccess(java.lang.String):void");
    }

    public void onPrepareContent(String str) {
        String str2 = bннн043Dн043D;
        StringBuilder append = new StringBuilder().append("onPrepareContent. ptr: ");
        int i = bффффф0444;
        switch ((i * (b0444фффф0444 + i)) % bф0444ффф0444) {
            case 0:
                break;
            default:
                bффффф0444 = b04440444ффф0444();
                bфф0444фф0444 = 67;
                break;
        }
        Log.d(str2, append.append(this.b043Dнн043Dн043D).toString());
        if (this.b043Dнн043Dн043D != 0) {
            naPrepareContent(this.b043Dнн043Dн043D, str);
        }
    }
}
