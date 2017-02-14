package com.immersion.hapticmediasdk.utils;

public class Log {
    public static int b04110411041104110411Б = 0;
    public static int b0411ББББ0411 = 2;
    public static int bБ0411041104110411Б = 94;
    public static int bБББББ0411 = 1;
    private static final boolean bД04140414Д0414Д = false;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Log() {
        /*
        r2 = this;
        r0 = 0;
        r2.<init>();
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0004;
            default: goto L_0x0007;
        };
    L_0x0007:
        switch(r0) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0004;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0007;
    L_0x000b:
        r0 = bБ0411041104110411Б;
        r1 = bБББББ0411;
        r0 = r0 + r1;
        r1 = bБ0411041104110411Б;
        r0 = r0 * r1;
        r1 = b0411ББББ0411;
        r0 = r0 % r1;
        r1 = b04110411041104110411Б;
        if (r0 == r1) goto L_0x0022;
    L_0x001a:
        r0 = 29;
        bБ0411041104110411Б = r0;
        r0 = 50;
        b04110411041104110411Б = r0;
    L_0x0022:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.Log.<init>():void");
    }

    public static int bБ04110411ББ0411() {
        return 45;
    }

    public static void d(String str, String str2) {
    }

    public static void e(String str, String str2) {
        android.util.Log.e(str, str2);
        while (true) {
            try {
                int[] iArr = new int[-1];
            } catch (Exception e) {
                bБ0411041104110411Б = 10;
                return;
            }
        }
    }

    public static void i(String str, String str2) {
        String str3 = null;
        try {
            android.util.Log.i(str, str2);
            while (true) {
                try {
                    str3.length();
                } catch (Exception e) {
                    bБ0411041104110411Б = 26;
                    while (true) {
                        try {
                            int[] iArr = new int[-1];
                        } catch (Exception e2) {
                            bБ0411041104110411Б = bБ04110411ББ0411();
                            while (true) {
                                try {
                                    str3.length();
                                } catch (Exception e3) {
                                    bБ0411041104110411Б = 57;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e4) {
            throw e4;
        }
    }

    public static void v(String str, String str2) {
    }

    public static void w(String str, String str2) {
        if (((bБ0411041104110411Б + bБББББ0411) * bБ0411041104110411Б) % b0411ББББ0411 != b04110411041104110411Б) {
            bБ0411041104110411Б = 42;
            b04110411041104110411Б = bБ04110411ББ0411();
        }
        try {
            android.util.Log.w(str, str2);
        } catch (Exception e) {
            throw e;
        }
    }
}
