package com.immersion.content;

import java.nio.ByteBuffer;

public abstract class HeaderUtils {
    public static int b0427ЧЧЧЧЧ = 1;
    public static int bЕ04150415041504150415 = 70;
    public static int bЧ0427ЧЧЧЧ = 2;

    public HeaderUtils() {
        int i = bЕ04150415041504150415;
        switch ((i * (b0427ЧЧЧЧЧ + i)) % bЧ0427ЧЧЧЧ) {
            case 0:
                break;
            default:
                bЕ04150415041504150415 = 54;
                b0427ЧЧЧЧЧ = b04270427ЧЧЧЧ();
                break;
        }
    }

    public static int b04270427ЧЧЧЧ() {
        return 57;
    }

    public abstract int calculateBlockRate();

    public abstract int calculateBlockSize();

    public abstract int calculateByteOffsetIntoHapticData(int i);

    public abstract void dispose();

    public abstract String getContentUUID();

    public abstract int getEncryption();

    public abstract int getMajorVersionNumber();

    public abstract int getMinorVersionNumber();

    public abstract int getNumChannels();

    public abstract void setEncryptedHSI(ByteBuffer byteBuffer, int i);
}
