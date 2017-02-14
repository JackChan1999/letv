package com.immersion.content;

import java.nio.ByteBuffer;

public class HapticHeaderUtils extends HeaderUtils {
    public static int b0446044604460446ц0446 = 2;
    public static int b0446ц04460446ц0446 = 67;
    private static final String bЭ042D042D042DЭЭ = "HapticHeaderUtils";
    public static int bц044604460446ц0446 = 1;
    public static int bцццц04460446;
    private byte[] b042D042D042D042DЭЭ;
    long b042DЭЭЭ042DЭ;
    private int bЭЭЭЭ042DЭ;

    public HapticHeaderUtils() {
        try {
        } catch (Exception e) {
            throw e;
        }
    }

    public static int b0446ццц04460446() {
        return 5;
    }

    public static int bц0446цц04460446() {
        return 2;
    }

    private native int calculateBlockRateNative(long j);

    private native int calculateBlockSizeNative(long j);

    private native int calculateByteOffsetIntoHapticDataNative(long j, int i);

    private native void disposeNative(long j);

    private native String getContentIdNative(long j);

    private native int getEncryptionNative(long j);

    private native int getMajorVersionNumberNative(long j);

    private native int getMinorVersionNumberNative(long j);

    private native int getNumChannelsNative(long j);

    private native long init(byte[] bArr, int i);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int calculateBlockRate() {
        /*
        r3 = this;
        r2 = 1;
        r0 = b0446ц04460446ц0446;
        r1 = bц044604460446ц0446;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b0446044604460446ц0446;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0017;
            default: goto L_0x000d;
        };
    L_0x000d:
        r0 = b0446ццц04460446();
        b0446ц04460446ц0446 = r0;
        r0 = 23;
        bцццц04460446 = r0;
    L_0x0017:
        r0 = r3.b042DЭЭЭ042DЭ;
    L_0x0019:
        switch(r2) {
            case 0: goto L_0x0019;
            case 1: goto L_0x0020;
            default: goto L_0x001c;
        };
    L_0x001c:
        switch(r2) {
            case 0: goto L_0x0019;
            case 1: goto L_0x0020;
            default: goto L_0x001f;
        };
    L_0x001f:
        goto L_0x001c;
    L_0x0020:
        r0 = r3.calculateBlockRateNative(r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.calculateBlockRate():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int calculateBlockSize() {
        /*
        r3 = this;
        r0 = r3.b042DЭЭЭ042DЭ;
        r0 = r3.calculateBlockSizeNative(r0);
    L_0x0006:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0006;
            default: goto L_0x000a;
        };
    L_0x000a:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0006;
            case 1: goto L_0x000f;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x000a;
    L_0x000f:
        r1 = b0446ц04460446ц0446;
        r2 = bц044604460446ц0446;
        r1 = r1 + r2;
        r2 = b0446ц04460446ц0446;
        r1 = r1 * r2;
        r2 = b0446044604460446ц0446;
        r1 = r1 % r2;
        r2 = bцццц04460446;
        if (r1 == r2) goto L_0x002a;
    L_0x001e:
        r1 = b0446ццц04460446();
        b0446ц04460446ц0446 = r1;
        r1 = b0446ццц04460446();
        bцццц04460446 = r1;
    L_0x002a:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.calculateBlockSize():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int calculateByteOffsetIntoHapticData(int r4) {
        /*
        r3 = this;
        r2 = 1;
        r0 = b0446ц04460446ц0446;
        r1 = bц044604460446ц0446;
        r0 = r0 + r1;
        r1 = b0446ц04460446ц0446;
        r0 = r0 * r1;
        r1 = b0446044604460446ц0446;
        r0 = r0 % r1;
        r1 = bцццц04460446;
        if (r0 == r1) goto L_0x001c;
    L_0x0010:
        r0 = b0446ццц04460446();
        b0446ц04460446ц0446 = r0;
        r0 = b0446ццц04460446();
        bцццц04460446 = r0;
    L_0x001c:
        switch(r2) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0023;
            default: goto L_0x001f;
        };
    L_0x001f:
        switch(r2) {
            case 0: goto L_0x001c;
            case 1: goto L_0x0023;
            default: goto L_0x0022;
        };
    L_0x0022:
        goto L_0x001f;
    L_0x0023:
        r0 = r3.b042DЭЭЭ042DЭ;
        r0 = r3.calculateByteOffsetIntoHapticDataNative(r0, r4);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.calculateByteOffsetIntoHapticData(int):int");
    }

    public void dispose() {
        disposeNative(this.b042DЭЭЭ042DЭ);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getContentUUID() {
        /*
        r4 = this;
        r0 = r4.b042DЭЭЭ042DЭ;
        r2 = b0446ц04460446ц0446;
        r3 = bц044604460446ц0446;
        r2 = r2 + r3;
        r3 = b0446ц04460446ц0446;
        r2 = r2 * r3;
        r3 = b0446044604460446ц0446;
        r2 = r2 % r3;
        r3 = bцццц04460446;
        if (r2 == r3) goto L_0x001d;
    L_0x0011:
        r2 = b0446ццц04460446();
        b0446ц04460446ц0446 = r2;
        r2 = b0446ццц04460446();
        bцццц04460446 = r2;
    L_0x001d:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x001d;
            case 1: goto L_0x0026;
            default: goto L_0x0021;
        };
    L_0x0021:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x0026;
            case 1: goto L_0x001d;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0021;
    L_0x0026:
        r0 = r4.getContentIdNative(r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.getContentUUID():java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getEncryption() {
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
        r0 = b0446ц04460446ц0446;
        r1 = bц044604460446ц0446;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bц0446цц04460446();
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0020;
            default: goto L_0x0016;
        };
    L_0x0016:
        r0 = b0446ццц04460446();
        b0446ц04460446ц0446 = r0;
        r0 = 40;
        bцццц04460446 = r0;
    L_0x0020:
        r0 = r2.b042DЭЭЭ042DЭ;
        r0 = r2.getEncryptionNative(r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.getEncryption():int");
    }

    public int getMajorVersionNumber() {
        long j = this.b042DЭЭЭ042DЭ;
        if (((b0446ц04460446ц0446 + bц044604460446ц0446) * b0446ц04460446ц0446) % b0446044604460446ц0446 != bцццц04460446) {
            b0446ц04460446ц0446 = 8;
            bцццц04460446 = 15;
        }
        return getMajorVersionNumberNative(j);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMinorVersionNumber() {
        /*
        r2 = this;
        r0 = b0446ц04460446ц0446;
        r1 = bц044604460446ц0446;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b0446044604460446ц0446;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0018;
            default: goto L_0x000c;
        };
    L_0x000c:
        r0 = b0446ццц04460446();
        b0446ц04460446ц0446 = r0;
        r0 = b0446ццц04460446();
        bцццц04460446 = r0;
    L_0x0018:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0018;
            case 1: goto L_0x0021;
            default: goto L_0x001c;
        };
    L_0x001c:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0021;
            case 1: goto L_0x0018;
            default: goto L_0x0020;
        };
    L_0x0020:
        goto L_0x001c;
    L_0x0021:
        r0 = r2.b042DЭЭЭ042DЭ;
        r0 = r2.getMinorVersionNumberNative(r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.content.HapticHeaderUtils.getMinorVersionNumber():int");
    }

    public int getNumChannels() {
        try {
            int numChannelsNative = getNumChannelsNative(this.b042DЭЭЭ042DЭ);
            if (((b0446ц04460446ц0446 + bц044604460446ц0446) * b0446ц04460446ц0446) % b0446044604460446ц0446 != bцццц04460446) {
                b0446ц04460446ц0446 = b0446ццц04460446();
                bцццц04460446 = b0446ццц04460446();
            }
            return numChannelsNative;
        } catch (Exception e) {
            throw e;
        }
    }

    public void setEncryptedHSI(ByteBuffer byteBuffer, int i) {
        this.bЭЭЭЭ042DЭ = i;
        this.b042D042D042D042DЭЭ = new byte[this.bЭЭЭЭ042DЭ];
        byteBuffer.get(this.b042D042D042D042DЭЭ, 0, this.bЭЭЭЭ042DЭ);
        this.b042DЭЭЭ042DЭ = init(this.b042D042D042D042DЭЭ, this.bЭЭЭЭ042DЭ);
    }
}
