package com.immersion;

public class Log {
    public static int b043B043Bлл043B043B = 2;
    public static int b043Bллл043B043B = 0;
    private static final boolean bДД0414041404140414 = false;
    public static int bл043Bлл043B043B = 1;
    public static int bлллл043B043B = 63;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Log() {
        /*
        r3 = this;
        r2 = 1;
        r0 = bлллл043B043B;
        r1 = bл043Bлл043B043B;
        r0 = r0 + r1;
        r1 = bлллл043B043B;
        r0 = r0 * r1;
        r1 = b043B043Bлл043B043B;
        r0 = r0 % r1;
        r1 = b043Bллл043B043B;
        if (r0 == r1) goto L_0x0016;
    L_0x0010:
        r0 = 65;
        bлллл043B043B = r0;
        b043Bллл043B043B = r2;
    L_0x0016:
        switch(r2) {
            case 0: goto L_0x0016;
            case 1: goto L_0x001d;
            default: goto L_0x0019;
        };
    L_0x0019:
        switch(r2) {
            case 0: goto L_0x0016;
            case 1: goto L_0x001d;
            default: goto L_0x001c;
        };
    L_0x001c:
        goto L_0x0019;
    L_0x001d:
        r3.<init>();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.Log.<init>():void");
    }

    public static int bл043B043Bл043B043B() {
        return 20;
    }

    public static void d(String str, String str2) {
        while (true) {
            switch (1) {
                case null:
                    break;
                case 1:
                    return;
                default:
                    while (true) {
                        switch (1) {
                            case null:
                                break;
                            case 1:
                                return;
                            default:
                        }
                    }
            }
        }
    }

    public static void e(String str, String str2) {
        if (((bлллл043B043B + bл043Bлл043B043B) * bлллл043B043B) % b043B043Bлл043B043B != b043Bллл043B043B) {
            bлллл043B043B = bл043B043Bл043B043B();
            b043Bллл043B043B = bл043B043Bл043B043B();
        }
        try {
            android.util.Log.e(str, str2);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void i(String str, String str2) {
        String str3 = null;
        while (true) {
            try {
                str3.length();
            } catch (Exception e) {
                bлллл043B043B = 68;
                android.util.Log.i(str, str2);
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
        }
    }

    public static void v(String str, String str2) {
    }

    public static void w(String str, String str2) {
        if (((bлллл043B043B + bл043Bлл043B043B) * bлллл043B043B) % b043B043Bлл043B043B != b043Bллл043B043B) {
            bлллл043B043B = bл043B043Bл043B043B();
            b043Bллл043B043B = 79;
        }
        try {
            android.util.Log.w(str, str2);
        } catch (Exception e) {
            throw e;
        }
    }
}
