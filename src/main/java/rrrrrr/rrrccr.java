package rrrrrr;

import com.immersion.aws.tvm.Token;

public class rrrccr extends rrcrcc {
    public static int b044Aъ044A044Aъъ = 1;
    public static int bъ044A044A044Aъъ = 2;
    public static int bъъ044A044Aъъ = 37;
    public static int bъъъъ044Aъ;
    private Token b042DЭЭ042D042D042D;
    private Token bЭЭЭ042D042D042D;

    public rrrccr(int i, String str) {
        int i2 = bъъ044A044Aъъ;
        switch ((i2 * (b044Aъ044A044Aъъ + i2)) % bъ044A044A044Aъъ) {
            case 0:
                break;
            default:
                bъъ044A044Aъъ = b044A044A044A044Aъъ();
                b044Aъ044A044Aъъ = 28;
                break;
        }
        try {
            super(i, str);
        } catch (Exception e) {
            throw e;
        }
    }

    public rrrccr(Token token, Token token2) {
        super(200, null);
        this.bЭЭЭ042D042D042D = token;
        this.b042DЭЭ042D042D042D = token2;
        if (((bъъ044A044Aъъ + b044Aъъъ044Aъ()) * bъъ044A044Aъъ) % bъ044A044A044Aъъ != bъъъъ044Aъ) {
            bъъ044A044Aъъ = b044A044A044A044Aъъ();
            bъъъъ044Aъ = 66;
        }
    }

    public static int b044A044A044A044Aъъ() {
        return 96;
    }

    public static int b044Aъъъ044Aъ() {
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.immersion.aws.tvm.Token getLongTermToken() {
        /*
        r3 = this;
        r1 = 1;
        r0 = r3.bЭЭЭ042D042D042D;
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
        r1 = bъъ044A044Aъъ;
        r2 = b044Aъ044A044Aъъ;
        r1 = r1 + r2;
        r2 = bъъ044A044Aъъ;
        r1 = r1 * r2;
        r2 = bъ044A044A044Aъъ;
        r1 = r1 % r2;
        r2 = bъъъъ044Aъ;
        if (r1 == r2) goto L_0x0020;
    L_0x0019:
        r1 = 2;
        bъъ044A044Aъъ = r1;
        r1 = 93;
        bъъъъ044Aъ = r1;
    L_0x0020:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.rrrccr.getLongTermToken():com.immersion.aws.tvm.Token");
    }

    public Token getShortTermToken() {
        if (((bъъ044A044Aъъ + b044Aъ044A044Aъъ) * bъъ044A044Aъъ) % bъ044A044A044Aъъ != bъъъъ044Aъ) {
            bъъ044A044Aъъ = b044A044A044A044Aъъ();
            bъъъъ044Aъ = b044A044A044A044Aъъ();
        }
        return this.b042DЭЭ042D042D042D;
    }
}
