package cn.jpush.android.util;

final class e extends d {
    static final /* synthetic */ boolean g = (!c.class.desiredAssertionStatus());
    private static final byte[] h = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
    private static final byte[] i = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
    int c;
    public final boolean d;
    public final boolean e;
    public final boolean f;
    private final byte[] j;
    private int k;
    private final byte[] l;

    public e(int i, byte[] bArr) {
        boolean z = true;
        this.a = null;
        this.d = (i & 1) == 0;
        this.e = (i & 2) == 0;
        if ((i & 4) == 0) {
            z = false;
        }
        this.f = z;
        this.l = (i & 8) == 0 ? h : i;
        this.j = new byte[2];
        this.c = 0;
        this.k = this.e ? 19 : -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(byte[] r11, int r12, int r13, boolean r14) {
        /*
        r10 = this;
        r6 = r10.l;
        r7 = r10.a;
        r4 = 0;
        r1 = r10.k;
        r8 = r13 + r12;
        r0 = -1;
        r2 = r10.c;
        switch(r2) {
            case 0: goto L_0x00a8;
            case 1: goto L_0x00ac;
            case 2: goto L_0x00d0;
            default: goto L_0x000f;
        };
    L_0x000f:
        r2 = r12;
        r3 = r0;
    L_0x0011:
        r0 = -1;
        if (r3 == r0) goto L_0x0212;
    L_0x0014:
        r0 = 0;
        r4 = r3 >> 18;
        r4 = r4 & 63;
        r4 = r6[r4];
        r7[r0] = r4;
        r0 = 1;
        r4 = r3 >> 12;
        r4 = r4 & 63;
        r4 = r6[r4];
        r7[r0] = r4;
        r0 = 2;
        r4 = r3 >> 6;
        r4 = r4 & 63;
        r4 = r6[r4];
        r7[r0] = r4;
        r4 = 3;
        r0 = 4;
        r3 = r3 & 63;
        r3 = r6[r3];
        r7[r4] = r3;
        r1 = r1 + -1;
        if (r1 != 0) goto L_0x020e;
    L_0x003b:
        r1 = r10.f;
        if (r1 == 0) goto L_0x0045;
    L_0x003f:
        r1 = 4;
        r0 = 5;
        r3 = 13;
        r7[r1] = r3;
    L_0x0045:
        r4 = r0 + 1;
        r1 = 10;
        r7[r0] = r1;
        r0 = 19;
        r5 = r0;
    L_0x004e:
        r0 = r2 + 3;
        if (r0 > r8) goto L_0x00f4;
    L_0x0052:
        r0 = r11[r2];
        r0 = r0 & 255;
        r0 = r0 << 16;
        r1 = r2 + 1;
        r1 = r11[r1];
        r1 = r1 & 255;
        r1 = r1 << 8;
        r0 = r0 | r1;
        r1 = r2 + 2;
        r1 = r11[r1];
        r1 = r1 & 255;
        r0 = r0 | r1;
        r1 = r0 >> 18;
        r1 = r1 & 63;
        r1 = r6[r1];
        r7[r4] = r1;
        r1 = r4 + 1;
        r3 = r0 >> 12;
        r3 = r3 & 63;
        r3 = r6[r3];
        r7[r1] = r3;
        r1 = r4 + 2;
        r3 = r0 >> 6;
        r3 = r3 & 63;
        r3 = r6[r3];
        r7[r1] = r3;
        r1 = r4 + 3;
        r0 = r0 & 63;
        r0 = r6[r0];
        r7[r1] = r0;
        r2 = r2 + 3;
        r1 = r4 + 4;
        r0 = r5 + -1;
        if (r0 != 0) goto L_0x020a;
    L_0x0094:
        r0 = r10.f;
        if (r0 == 0) goto L_0x0207;
    L_0x0098:
        r0 = r1 + 1;
        r3 = 13;
        r7[r1] = r3;
    L_0x009e:
        r4 = r0 + 1;
        r1 = 10;
        r7[r0] = r1;
        r0 = 19;
        r5 = r0;
        goto L_0x004e;
    L_0x00a8:
        r2 = r12;
        r3 = r0;
        goto L_0x0011;
    L_0x00ac:
        r2 = r12 + 2;
        if (r2 > r8) goto L_0x000f;
    L_0x00b0:
        r0 = r10.j;
        r2 = 0;
        r0 = r0[r2];
        r0 = r0 & 255;
        r0 = r0 << 16;
        r2 = r12 + 1;
        r3 = r11[r12];
        r3 = r3 & 255;
        r3 = r3 << 8;
        r0 = r0 | r3;
        r12 = r2 + 1;
        r2 = r11[r2];
        r2 = r2 & 255;
        r0 = r0 | r2;
        r2 = 0;
        r10.c = r2;
        r2 = r12;
        r3 = r0;
        goto L_0x0011;
    L_0x00d0:
        r2 = r12 + 1;
        if (r2 > r8) goto L_0x000f;
    L_0x00d4:
        r0 = r10.j;
        r2 = 0;
        r0 = r0[r2];
        r0 = r0 & 255;
        r0 = r0 << 16;
        r2 = r10.j;
        r3 = 1;
        r2 = r2[r3];
        r2 = r2 & 255;
        r2 = r2 << 8;
        r0 = r0 | r2;
        r2 = r12 + 1;
        r3 = r11[r12];
        r3 = r3 & 255;
        r0 = r0 | r3;
        r3 = 0;
        r10.c = r3;
        r3 = r0;
        goto L_0x0011;
    L_0x00f4:
        r0 = r10.c;
        r0 = r2 - r0;
        r1 = r8 + -1;
        if (r0 != r1) goto L_0x015d;
    L_0x00fc:
        r1 = 0;
        r0 = r10.c;
        if (r0 <= 0) goto L_0x0157;
    L_0x0101:
        r0 = r10.j;
        r3 = 0;
        r1 = 1;
        r0 = r0[r3];
    L_0x0107:
        r0 = r0 & 255;
        r3 = r0 << 4;
        r0 = r10.c;
        r0 = r0 - r1;
        r10.c = r0;
        r1 = r4 + 1;
        r0 = r3 >> 6;
        r0 = r0 & 63;
        r0 = r6[r0];
        r7[r4] = r0;
        r0 = r1 + 1;
        r3 = r3 & 63;
        r3 = r6[r3];
        r7[r1] = r3;
        r1 = r10.d;
        if (r1 == 0) goto L_0x0132;
    L_0x0126:
        r1 = r0 + 1;
        r3 = 61;
        r7[r0] = r3;
        r0 = r1 + 1;
        r3 = 61;
        r7[r1] = r3;
    L_0x0132:
        r1 = r10.e;
        if (r1 == 0) goto L_0x0148;
    L_0x0136:
        r1 = r10.f;
        if (r1 == 0) goto L_0x0141;
    L_0x013a:
        r1 = r0 + 1;
        r3 = 13;
        r7[r0] = r3;
        r0 = r1;
    L_0x0141:
        r1 = r0 + 1;
        r3 = 10;
        r7[r0] = r3;
        r0 = r1;
    L_0x0148:
        r4 = r0;
    L_0x0149:
        r0 = g;
        if (r0 != 0) goto L_0x01f1;
    L_0x014d:
        r0 = r10.c;
        if (r0 == 0) goto L_0x01f1;
    L_0x0151:
        r0 = new java.lang.AssertionError;
        r0.<init>();
        throw r0;
    L_0x0157:
        r3 = r2 + 1;
        r0 = r11[r2];
        r2 = r3;
        goto L_0x0107;
    L_0x015d:
        r0 = r10.c;
        r0 = r2 - r0;
        r1 = r8 + -2;
        if (r0 != r1) goto L_0x01d5;
    L_0x0165:
        r1 = 0;
        r0 = r10.c;
        r3 = 1;
        if (r0 <= r3) goto L_0x01c9;
    L_0x016b:
        r0 = r10.j;
        r3 = 0;
        r1 = 1;
        r0 = r0[r3];
    L_0x0171:
        r0 = r0 & 255;
        r9 = r0 << 10;
        r0 = r10.c;
        if (r0 <= 0) goto L_0x01cf;
    L_0x0179:
        r0 = r10.j;
        r3 = r1 + 1;
        r0 = r0[r1];
        r1 = r3;
    L_0x0180:
        r0 = r0 & 255;
        r0 = r0 << 2;
        r0 = r0 | r9;
        r3 = r10.c;
        r1 = r3 - r1;
        r10.c = r1;
        r1 = r4 + 1;
        r3 = r0 >> 12;
        r3 = r3 & 63;
        r3 = r6[r3];
        r7[r4] = r3;
        r3 = r1 + 1;
        r4 = r0 >> 6;
        r4 = r4 & 63;
        r4 = r6[r4];
        r7[r1] = r4;
        r1 = r3 + 1;
        r0 = r0 & 63;
        r0 = r6[r0];
        r7[r3] = r0;
        r0 = r10.d;
        if (r0 == 0) goto L_0x0205;
    L_0x01ab:
        r0 = r1 + 1;
        r3 = 61;
        r7[r1] = r3;
    L_0x01b1:
        r1 = r10.e;
        if (r1 == 0) goto L_0x01c7;
    L_0x01b5:
        r1 = r10.f;
        if (r1 == 0) goto L_0x01c0;
    L_0x01b9:
        r1 = r0 + 1;
        r3 = 13;
        r7[r0] = r3;
        r0 = r1;
    L_0x01c0:
        r1 = r0 + 1;
        r3 = 10;
        r7[r0] = r3;
        r0 = r1;
    L_0x01c7:
        r4 = r0;
        goto L_0x0149;
    L_0x01c9:
        r3 = r2 + 1;
        r0 = r11[r2];
        r2 = r3;
        goto L_0x0171;
    L_0x01cf:
        r3 = r2 + 1;
        r0 = r11[r2];
        r2 = r3;
        goto L_0x0180;
    L_0x01d5:
        r0 = r10.e;
        if (r0 == 0) goto L_0x0149;
    L_0x01d9:
        if (r4 <= 0) goto L_0x0149;
    L_0x01db:
        r0 = 19;
        if (r5 == r0) goto L_0x0149;
    L_0x01df:
        r0 = r10.f;
        if (r0 == 0) goto L_0x0203;
    L_0x01e3:
        r0 = r4 + 1;
        r1 = 13;
        r7[r4] = r1;
    L_0x01e9:
        r4 = r0 + 1;
        r1 = 10;
        r7[r0] = r1;
        goto L_0x0149;
    L_0x01f1:
        r0 = g;
        if (r0 != 0) goto L_0x01fd;
    L_0x01f5:
        if (r2 == r8) goto L_0x01fd;
    L_0x01f7:
        r0 = new java.lang.AssertionError;
        r0.<init>();
        throw r0;
    L_0x01fd:
        r10.b = r4;
        r10.k = r5;
        r0 = 1;
        return r0;
    L_0x0203:
        r0 = r4;
        goto L_0x01e9;
    L_0x0205:
        r0 = r1;
        goto L_0x01b1;
    L_0x0207:
        r0 = r1;
        goto L_0x009e;
    L_0x020a:
        r5 = r0;
        r4 = r1;
        goto L_0x004e;
    L_0x020e:
        r5 = r1;
        r4 = r0;
        goto L_0x004e;
    L_0x0212:
        r5 = r1;
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.e.a(byte[], int, int, boolean):boolean");
    }
}
