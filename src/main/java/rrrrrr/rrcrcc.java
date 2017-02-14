package rrrrrr;

import com.letv.core.messagebus.config.LeMessageIds;

public class rrcrcc {
    public static int b043B043Bл043B043Bл = 2;
    public static int b043Bлл043B043Bл = 0;
    public static int bл043Bл043B043Bл = 1;
    public static int bллл043B043Bл = 76;
    private final int b04140414ДД04140414;
    private final String bДД0414Д04140414;

    public rrcrcc(int i, String str) {
        this.b04140414ДД04140414 = i;
        this.bДД0414Д04140414 = str;
    }

    public static int bлл043B043B043Bл() {
        return 33;
    }

    public int getResponseCode() {
        if (((bллл043B043Bл + bл043Bл043B043Bл) * bллл043B043Bл) % b043B043Bл043B043Bл != b043Bлл043B043Bл) {
            bллл043B043Bл = bлл043B043B043Bл();
            b043Bлл043B043Bл = 82;
        }
        try {
            return this.b04140414ДД04140414;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getResponseMessage() {
        int bлл043B043B043Bл = bлл043B043B043Bл();
        switch ((bлл043B043B043Bл * (bл043Bл043B043Bл + bлл043B043B043Bл)) % b043B043Bл043B043Bл) {
            case 0:
                break;
            default:
                bллл043B043Bл = 98;
                b043Bлл043B043Bл = bлл043B043B043Bл();
                break;
        }
        try {
            return this.bДД0414Д04140414;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean requestTimedOut() {
        try {
            if (getResponseCode() == 408 || getResponseCode() == LeMessageIds.MSG_DLNA_LIVE_PROTOCOL) {
                return true;
            }
            int bлл043B043B043Bл = bлл043B043B043Bл();
            switch ((bлл043B043B043Bл * (bл043Bл043B043Bл + bлл043B043B043Bл)) % b043B043Bл043B043Bл) {
                case 0:
                    return false;
                default:
                    bллл043B043Bл = 43;
                    b043Bлл043B043Bл = bлл043B043B043Bл();
                    return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean requestUnauthorized() {
        return getResponseCode() == 401;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean requestWasSuccessful() {
        /*
        r4 = this;
        r0 = 1;
        r1 = 0;
        r2 = r4.getResponseCode();
    L_0x0006:
        switch(r0) {
            case 0: goto L_0x0006;
            case 1: goto L_0x000d;
            default: goto L_0x0009;
        };
    L_0x0009:
        switch(r1) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0006;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0009;
    L_0x000d:
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 != r3) goto L_0x0029;
    L_0x0011:
        r1 = bллл043B043Bл;
        r2 = bл043Bл043B043Bл;
        r1 = r1 + r2;
        r2 = bллл043B043Bл;
        r1 = r1 * r2;
        r2 = b043B043Bл043B043Bл;
        r1 = r1 % r2;
        r2 = b043Bлл043B043Bл;
        if (r1 == r2) goto L_0x0028;
    L_0x0020:
        r1 = 14;
        bллл043B043Bл = r1;
        r1 = 70;
        b043Bлл043B043Bл = r1;
    L_0x0028:
        return r0;
    L_0x0029:
        r0 = r1;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrcrcc.requestWasSuccessful():boolean");
    }
}
