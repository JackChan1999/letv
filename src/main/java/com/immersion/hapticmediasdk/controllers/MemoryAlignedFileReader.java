package com.immersion.hapticmediasdk.controllers;

import com.immersion.content.HapticHeaderUtils;
import com.immersion.content.HeaderUtils;
import com.immersion.hapticmediasdk.models.HapticFileInformation;
import com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Profiler;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import rrrrrr.ccrcrr;
import rrrrrr.ccrrrr;
import rrrrrr.crccrc;

public class MemoryAlignedFileReader implements IHapticFileReader {
    private static final int b042DЭ042D042D042DЭ = 3072;
    private static final int b042DЭ042DЭЭ042D = 16;
    private static int b042DЭЭ042D042DЭ = 80;
    public static int b044604460446ц04460446 = bц04460446ц04460446();
    public static int b04460446цц04460446 = bц04460446ц04460446();
    private static int bЭ042DЭ042D042DЭ = 0;
    private static final String bЭ042DЭЭ042DЭ = "MemoryAlignedFileReader";
    private static final int bЭЭ042D042D042DЭ = 1024;
    public static int bцц0446ц04460446 = 1;
    public static int bццц044604460446 = 2;
    private FileManager b042D042D042D042D042DЭ;
    private int b042D042D042DЭ042DЭ;
    private crccrc b042D042D042DЭЭ042D;
    private HapticFileInformation b042D042DЭ042D042DЭ;
    private File b042D042DЭЭ042DЭ;
    private int b042D042DЭЭЭ042D;
    private ccrcrr b042DЭ042DЭ042DЭ;
    private byte[] b042DЭЭЭЭ042D;
    private String bЭ042D042D042D042DЭ;
    private ccrcrr bЭ042D042DЭ042DЭ;
    private ccrrrr bЭ042D042DЭЭ042D;
    private final Profiler bЭ042DЭЭЭ042D;
    private FileChannel bЭЭ042DЭ042DЭ;
    private int bЭЭ042DЭЭ042D;
    private int bЭЭЭ042D042DЭ;
    private ArrayList bЭЭЭ042DЭ042D;
    private HeaderUtils bЭЭЭЭЭ042D;

    static {
        try {
            if (((b04460446цц04460446 + bцц0446ц04460446) * b04460446цц04460446) % bццц044604460446 != b044604460446ц04460446) {
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public MemoryAlignedFileReader(String str, HeaderUtils headerUtils) {
        this.b042D042D042DЭ042DЭ = 0;
        if (((b04460446цц04460446 + bцц0446ц04460446) * b04460446цц04460446) % bццц044604460446 != b044604460446ц04460446) {
            b04460446цц04460446 = 1;
            b044604460446ц04460446 = 42;
        }
        this.bЭ042D042D042D042DЭ = null;
        this.b042D042D042D042D042DЭ = null;
        this.b042DЭЭЭЭ042D = null;
        this.bЭ042DЭЭЭ042D = new Profiler();
        this.bЭ042D042D042D042DЭ = str;
        this.bЭЭЭЭЭ042D = headerUtils;
    }

    public MemoryAlignedFileReader(String str, FileManager fileManager, int i) {
        try {
            this.b042D042D042DЭ042DЭ = 0;
            this.bЭ042D042D042D042DЭ = null;
            this.b042D042D042D042D042DЭ = null;
            this.b042DЭЭЭЭ042D = null;
            int i2 = b04460446цц04460446;
            switch ((i2 * (bцц0446ц04460446 + i2)) % b0446ц0446ц04460446()) {
                case 0:
                    break;
                default:
                    b04460446цц04460446 = bц04460446ц04460446();
                    bцц0446ц04460446 = 68;
                    break;
            }
            this.bЭ042DЭЭЭ042D = new Profiler();
            this.bЭ042D042D042D042DЭ = str;
            try {
                this.b042D042D042D042D042DЭ = fileManager;
                this.bЭЭЭЭЭ042D = new HapticHeaderUtils();
                this.bЭ042D042DЭЭ042D = new ccrrrr(this);
                this.b042D042D042DЭЭ042D = new crccrc(this);
                this.bЭЭЭ042DЭ042D = new ArrayList();
                this.b042D042D042DЭ042DЭ = i;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b0446ц0446ц04460446() {
        return 2;
    }

    public static int b0446цц044604460446() {
        return 1;
    }

    private static boolean b04490449щщщщ(ccrcrr rrrrrr_ccrcrr, int i) {
        if (!b0449щщщщщ(rrrrrr_ccrcrr, i)) {
            int i2 = b04460446цц04460446;
            switch ((i2 * (bцц0446ц04460446 + i2)) % bццц044604460446) {
                case 0:
                    break;
                default:
                    b04460446цц04460446 = 81;
                    b044604460446ц04460446 = 47;
                    break;
            }
            if (!bщ0449щщщщ(rrrrrr_ccrcrr, i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b0449щ0449щщщ() {
        /*
        r5 = this;
        r0 = "MemoryAlignedFileReader";
        r1 = "%%%%%%%%%%% logBufferState %%%%%%%%%%%";
        com.immersion.Log.d(r0, r1);
        r0 = r5.b042DЭ042DЭ042DЭ;
        if (r0 == 0) goto L_0x0183;
    L_0x000b:
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW capacity = ";
        r1 = r1.append(r2);
        r2 = r5.b042DЭ042DЭ042DЭ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.capacity();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW position = ";
        r1 = r1.append(r2);
        r2 = r5.b042DЭ042DЭ042DЭ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r3 = b04460446цц04460446;
        r4 = bцц0446ц04460446;
        r4 = r4 + r3;
        r3 = r3 * r4;
        r4 = b0446ц0446ц04460446();
        r3 = r3 % r4;
        switch(r3) {
            case 0: goto L_0x005a;
            default: goto L_0x004e;
        };
    L_0x004e:
        r3 = bц04460446ц04460446();
        b04460446цц04460446 = r3;
        r3 = bц04460446ц04460446();
        b044604460446ц04460446 = r3;
    L_0x005a:
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW remaining = ";
        r1 = r1.append(r2);
        r2 = r5.b042DЭ042DЭ042DЭ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.remaining();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW mHapticDataOffset = ";
        r1 = r1.append(r2);
        r2 = r5.b042DЭ042DЭ042DЭ;
        r2 = r2.mHapticDataOffset;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW mHapticDataOffset + position = ";
        r1 = r1.append(r2);
        r2 = r5.b042DЭ042DЭ042DЭ;
        r2 = r2.mHapticDataOffset;
        r3 = r5.b042DЭ042DЭ042DЭ;
        r3 = r3.mMappedByteBuffer;
        r3 = r3.position();
        r2 = r2 + r3;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
    L_0x00c6:
        r0 = "MemoryAlignedFileReader";
        r1 = "--------------------------------------";
        com.immersion.Log.d(r0, r1);
        r0 = r5.bЭ042D042DЭ042DЭ;
        if (r0 == 0) goto L_0x018c;
    L_0x00d1:
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW capacity = ";
        r1 = r1.append(r2);
        r2 = r5.bЭ042D042DЭ042DЭ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.capacity();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW position = ";
        r1 = r1.append(r2);
        r2 = r5.bЭ042D042DЭ042DЭ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
    L_0x0115:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x011e;
            case 1: goto L_0x0115;
            default: goto L_0x0119;
        };
    L_0x0119:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0115;
            case 1: goto L_0x011e;
            default: goto L_0x011d;
        };
    L_0x011d:
        goto L_0x0119;
    L_0x011e:
        r1.<init>();
        r2 = "mNextMMW remaining = ";
        r1 = r1.append(r2);
        r2 = r5.bЭ042D042DЭ042DЭ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.remaining();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW mHapticDataOffset = ";
        r1 = r1.append(r2);
        r2 = r5.bЭ042D042DЭ042DЭ;
        r2 = r2.mHapticDataOffset;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
        r0 = "MemoryAlignedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW mHapticDataOffset + position = ";
        r1 = r1.append(r2);
        r2 = r5.bЭ042D042DЭ042DЭ;
        r2 = r2.mHapticDataOffset;
        r3 = r5.bЭ042D042DЭ042DЭ;
        r3 = r3.mMappedByteBuffer;
        r3 = r3.position();
        r2 = r2 + r3;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.Log.d(r0, r1);
    L_0x017b:
        r0 = "MemoryAlignedFileReader";
        r1 = "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
        com.immersion.Log.d(r0, r1);
        return;
    L_0x0183:
        r0 = "MemoryAlignedFileReader";
        r1 = "mCurrentMMW is null";
        com.immersion.Log.d(r0, r1);
        goto L_0x00c6;
    L_0x018c:
        r0 = "MemoryAlignedFileReader";
        r1 = "mNextMMW is null";
        com.immersion.Log.d(r0, r1);
        goto L_0x017b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.b0449щ0449щщщ():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b0449щщщщщ(rrrrrr.ccrcrr r3, int r4) {
        /*
        r0 = 1;
        r1 = 0;
        r2 = r3.mHapticDataOffset;
        if (r4 >= r2) goto L_0x0007;
    L_0x0006:
        return r0;
    L_0x0007:
        switch(r1) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0007;
            default: goto L_0x000a;
        };
    L_0x000a:
        switch(r0) {
            case 0: goto L_0x0007;
            case 1: goto L_0x000e;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x000a;
    L_0x000e:
        r0 = b04460446цц04460446;
        r2 = bцц0446ц04460446;
        r0 = r0 + r2;
        r2 = b04460446цц04460446;
        r0 = r0 * r2;
        r2 = bццц044604460446;
        r0 = r0 % r2;
        r2 = b044604460446ц04460446;
        if (r0 == r2) goto L_0x0029;
    L_0x001d:
        r0 = 88;
        b04460446цц04460446 = r0;
        r0 = bц04460446ц04460446();
        b044604460446ц04460446 = r0;
        r0 = r1;
        goto L_0x0006;
    L_0x0029:
        r0 = r1;
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.b0449щщщщщ(rrrrrr.ccrcrr, int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b044C044C044Cь044C044C() {
        /*
        r5 = this;
        r0 = 0;
        r2 = 0;
        r1 = r5.b042D042DЭ042D042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        if (r1 == 0) goto L_0x0008;
    L_0x0006:
        r0 = 1;
    L_0x0007:
        return r0;
    L_0x0008:
        r1 = r5.b042D042DЭЭ042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        if (r1 != 0) goto L_0x001a;
    L_0x000c:
        r1 = r5.b042D042D042D042D042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        if (r1 == 0) goto L_0x0037;
    L_0x0010:
        r1 = r5.b042D042D042D042D042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r3 = r5.bЭ042D042D042D042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r1 = r1.getHapticStorageFile(r3);	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r5.b042D042DЭЭ042DЭ = r1;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
    L_0x001a:
        r1 = r5.bЭЭ042DЭ042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        if (r1 != 0) goto L_0x007c;
    L_0x001e:
        r1 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r3 = r5.b042D042DЭЭ042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r4 = "r";
        r1.<init>(r3, r4);	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r2 = r1.getChannel();	 Catch:{ FileNotFoundException -> 0x007a, Exception -> 0x0075 }
        r5.bЭЭ042DЭ042DЭ = r2;	 Catch:{ FileNotFoundException -> 0x007a, Exception -> 0x0075 }
    L_0x002e:
        r2 = r5.bЭЭ042DЭ042DЭ;	 Catch:{ FileNotFoundException -> 0x007a, Exception -> 0x0075 }
        if (r2 == 0) goto L_0x0007;
    L_0x0032:
        r0 = r5.bььь044C044C044C();	 Catch:{ FileNotFoundException -> 0x007a, Exception -> 0x0075 }
        goto L_0x0007;
    L_0x0037:
        r1 = r5.bЭ042D042D042D042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        if (r1 == 0) goto L_0x0007;
    L_0x003b:
        r1 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r3 = r5.bЭ042D042D042D042DЭ;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r1.<init>(r3);	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        r5.b042D042DЭЭ042DЭ = r1;	 Catch:{ FileNotFoundException -> 0x0045, Exception -> 0x0075 }
        goto L_0x001a;
    L_0x0045:
        r1 = move-exception;
        r1 = r2;
    L_0x0047:
        r2 = "MemoryAlignedFileReader";
        r3 = "FileNotFoundException";
        com.immersion.Log.e(r2, r3);	 Catch:{ Exception -> 0x0073 }
        r2 = r5.b042D042D042D042D042DЭ;	 Catch:{ Exception -> 0x0073 }
        r2.closeCloseable(r1);	 Catch:{ Exception -> 0x0073 }
        r1 = r5.b042D042D042D042D042DЭ;	 Catch:{ Exception -> 0x0073 }
        r2 = b04460446цц04460446;
        r3 = bцц0446ц04460446;
        r2 = r2 + r3;
        r3 = b04460446цц04460446;
        r2 = r2 * r3;
        r3 = bццц044604460446;
        r2 = r2 % r3;
        r3 = b044604460446ц04460446;
        if (r2 == r3) goto L_0x006d;
    L_0x0064:
        r2 = 5;
        b04460446цц04460446 = r2;
        r2 = bц04460446ц04460446();
        b044604460446ц04460446 = r2;
    L_0x006d:
        r2 = r5.bЭЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0073 }
        r1.closeCloseable(r2);	 Catch:{ Exception -> 0x0073 }
        goto L_0x0007;
    L_0x0073:
        r0 = move-exception;
        throw r0;
    L_0x0075:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x0073 }
        goto L_0x0007;
    L_0x007a:
        r2 = move-exception;
        goto L_0x0047;
    L_0x007c:
        r1 = r2;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.b044C044C044Cь044C044C():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b044C044Cь044C044C044C() {
        /*
        r3 = this;
        r0 = 0;
        r1 = 0;
        r2 = r3.bЭЭЭЭЭ042D;
        if (r2 == 0) goto L_0x001f;
    L_0x0006:
        r1.length();	 Catch:{ Exception -> 0x000a }
        goto L_0x0006;
    L_0x000a:
        r1 = move-exception;
        r1 = bц04460446ц04460446();
        b04460446цц04460446 = r1;
    L_0x0011:
        switch(r0) {
            case 0: goto L_0x0019;
            case 1: goto L_0x0011;
            default: goto L_0x0014;
        };
    L_0x0014:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0011;
            case 1: goto L_0x0019;
            default: goto L_0x0018;
        };
    L_0x0018:
        goto L_0x0014;
    L_0x0019:
        r0 = r3.bЭЭЭЭЭ042D;
        r0 = r0.getNumChannels();
    L_0x001f:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.b044C044Cь044C044C044C():int");
    }

    private int b044Cь044C044C044C044C(int i) {
        int i2 = this.bЭЭ042DЭЭ042D;
        if (((b04460446цц04460446 + bцц0446ц04460446) * b04460446цц04460446) % bццц044604460446 != b044604460446ц04460446) {
            b04460446цц04460446 = 55;
            b044604460446ц04460446 = 56;
        }
        return i2 + bь044Cь044C044C044C(i);
    }

    private void b044Cьь044C044C044C() throws NotEnoughHapticBytesAvailableException, IOException {
        try {
            int i = this.bЭ042D042DЭ042DЭ.mHapticDataOffset + 1024;
            this.b042DЭ042DЭ042DЭ = this.bЭ042D042DЭ042DЭ;
            try {
                i -= bЭ042DЭ042D042DЭ / 2;
                if (((b04460446цц04460446 + bцц0446ц04460446) * b04460446цц04460446) % bццц044604460446 != b044604460446ц04460446) {
                    b04460446цц04460446 = bц04460446ц04460446();
                    b044604460446ц04460446 = 50;
                }
                this.bЭ042D042DЭ042DЭ = bь044C044C044C044C044C(i);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int bц04460446ц04460446() {
        return 72;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int bщ04490449щщщ() {
        /*
        r3 = this;
        r1 = 1;
        r0 = 0;
    L_0x0002:
        switch(r1) {
            case 0: goto L_0x0002;
            case 1: goto L_0x0009;
            default: goto L_0x0005;
        };
    L_0x0005:
        switch(r1) {
            case 0: goto L_0x0002;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0005;
    L_0x0009:
        r1 = r0 + 1024;
        r2 = bЭ042DЭ042D042DЭ;
        r2 = r2 / 2;
        r1 = r1 % r2;
        if (r1 == 0) goto L_0x002b;
    L_0x0012:
        r1 = b04460446цц04460446;
        r2 = bцц0446ц04460446;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = b0446ц0446ц04460446();
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0028;
            default: goto L_0x0020;
        };
    L_0x0020:
        r1 = 92;
        b04460446цц04460446 = r1;
        r1 = 63;
        b044604460446ц04460446 = r1;
    L_0x0028:
        r0 = r0 + 16;
        goto L_0x0009;
    L_0x002b:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.bщ04490449щщщ():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean bщ0449щщщщ(rrrrrr.ccrcrr r5, int r6) {
        /*
        r0 = 1;
        r1 = 0;
        r2 = r5.mHapticDataOffset;
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000b;
            default: goto L_0x0007;
        };
    L_0x0007:
        switch(r1) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0004;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0007;
    L_0x000b:
        r3 = r5.mMappedByteBuffer;
        r3 = r3.capacity();
        r2 = r2 + r3;
        r3 = bц04460446ц04460446();
        r4 = bцц0446ц04460446;
        r3 = r3 + r4;
        r4 = bц04460446ц04460446();
        r3 = r3 * r4;
        r4 = bццц044604460446;
        r3 = r3 % r4;
        r4 = b044604460446ц04460446;
        if (r3 == r4) goto L_0x0031;
    L_0x0025:
        r3 = bц04460446ц04460446();
        b04460446цц04460446 = r3;
        r3 = bц04460446ц04460446();
        b044604460446ц04460446 = r3;
    L_0x0031:
        if (r6 < r2) goto L_0x0034;
    L_0x0033:
        return r0;
    L_0x0034:
        r0 = r1;
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.bщ0449щщщщ(rrrrrr.ccrcrr, int):boolean");
    }

    private static boolean bщщ0449щщщ(ccrcrr rrrrrr_ccrcrr, int i) {
        return bщ0449щщщщ(rrrrrr_ccrcrr, bЭ042DЭ042D042DЭ + i);
    }

    private ccrcrr bь044C044C044C044C044C(int i) throws IOException, NotEnoughHapticBytesAvailableException {
        try {
            this.bЭ042DЭЭЭ042D.startTiming();
            if (i < this.b042D042DЭЭЭ042D) {
                int i2 = this.bЭЭ042DЭЭ042D + i;
                int bщ04490449щщщ = bщ04490449щщщ();
                int i3 = (i + 1024) + bщ04490449щщщ <= this.b042D042DЭЭЭ042D ? bщ04490449щщщ + 1024 : this.b042D042DЭЭЭ042D - i;
                try {
                    if (i + i3 > this.bЭЭЭ042D042DЭ) {
                        throw new NotEnoughHapticBytesAvailableException("Not enough bytes available yet.");
                    } else {
                        MappedByteBuffer map = this.bЭЭ042DЭ042DЭ.map(MapMode.READ_ONLY, (long) i2, (long) i3);
                        if (map != null) {
                            map.order(ByteOrder.BIG_ENDIAN);
                            ccrcrr rrrrrr_ccrcrr = new ccrcrr();
                            rrrrrr_ccrcrr.mMappedByteBuffer = map;
                            if (((bц04460446ц04460446() + bцц0446ц04460446) * bц04460446ц04460446()) % bццц044604460446 != b044604460446ц04460446) {
                                b04460446цц04460446 = bц04460446ц04460446();
                                b044604460446ц04460446 = 10;
                            }
                            rrrrrr_ccrcrr.mHapticDataOffset = i;
                            return rrrrrr_ccrcrr;
                        }
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            return null;
        } catch (Exception e2) {
            throw e2;
        }
    }

    private boolean bь044C044Cь044C044C(int i) {
        try {
            if (this.bЭЭЭ042D042DЭ >= i) {
                return true;
            }
            if (((b04460446цц04460446 + b0446цц044604460446()) * b04460446цц04460446) % bццц044604460446 == b044604460446ц04460446) {
                return false;
            }
            b04460446цц04460446 = bц04460446ц04460446();
            b044604460446ц04460446 = bц04460446ц04460446();
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    private int bь044Cь044C044C044C(int i) {
        try {
            if (this.bЭЭЭЭЭ042D == null) {
                return 0;
            }
            if (((bц04460446ц04460446() + bцц0446ц04460446) * bц04460446ц04460446()) % bццц044604460446 != b044604460446ц04460446) {
                b04460446цц04460446 = bц04460446ц04460446();
                b044604460446ц04460446 = bц04460446ц04460446();
            }
            try {
                return this.bЭЭЭЭЭ042D.calculateByteOffsetIntoHapticData(i);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int bьь044C044C044C044C(rrrrrr.ccrcrr r5, int r6) {
        /*
        r4 = this;
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = r5.mHapticDataOffset;
        r0 = r6 - r0;
        r1 = r5.mMappedByteBuffer;
        r2 = b04460446цц04460446;
        r3 = bцц0446ц04460446;
        r2 = r2 + r3;
        r3 = b04460446цц04460446;
        r2 = r2 * r3;
        r3 = bццц044604460446;
        r2 = r2 % r3;
        r3 = b044604460446ц04460446;
        if (r2 == r3) goto L_0x002a;
    L_0x001e:
        r2 = bц04460446ц04460446();
        b04460446цц04460446 = r2;
        r2 = bц04460446ц04460446();
        b044604460446ц04460446 = r2;
    L_0x002a:
        r1 = r1.capacity();
        r0 = r0 % r1;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.bьь044C044C044C044C(rrrrrr.ccrcrr, int):int");
    }

    private boolean bььь044C044C044C() {
        try {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.position(0);
            if (this.bЭЭ042DЭ042DЭ.read(allocate, 16) != 4) {
                return false;
            }
            allocate.flip();
            int i = allocate.getInt();
            int i2 = i + 28;
            ByteBuffer allocate2 = ByteBuffer.allocate(i2);
            allocate2.order(ByteOrder.LITTLE_ENDIAN);
            if (this.bЭЭ042DЭ042DЭ.read(allocate2, 0) != i2) {
                return false;
            }
            allocate2.position(4);
            this.b042D042DЭЭЭ042D = (allocate2.getInt() + 8) - i2;
            this.bЭ042D042DЭЭ042D.setHapticDataSize(this.b042D042DЭЭЭ042D);
            this.bЭЭ042DЭЭ042D = i2;
            allocate2.position(20);
            this.b042DЭЭЭЭ042D = new byte[i];
            allocate2.duplicate().get(this.b042DЭЭЭЭ042D, 0, i);
            this.bЭЭЭЭЭ042D.setEncryptedHSI(allocate2, i);
            this.b042D042D042DЭЭ042D.setContentId(this.bЭЭЭЭЭ042D.getContentUUID());
            i = this.bЭЭЭЭЭ042D.calculateBlockSize();
            if (i <= 0) {
                return false;
            }
            bЭ042DЭ042D042DЭ = i * 2;
            this.bЭ042D042DЭЭ042D.setHapticBlockSize(bЭ042DЭ042D042DЭ);
            i = this.bЭЭЭЭЭ042D.calculateBlockRate();
            if (i <= 0) {
                return false;
            }
            b042DЭЭ042D042DЭ = i;
            int i3 = b04460446цц04460446;
            switch ((i3 * (bцц0446ц04460446 + i3)) % bццц044604460446) {
                case 0:
                    break;
                default:
                    b04460446цц04460446 = 24;
                    b044604460446ц04460446 = 81;
                    break;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean bufferAtPlaybackPosition(int r9) {
        /*
        r8 = this;
        r1 = 1;
        r0 = 0;
        r2 = r8.b044C044C044Cь044C044C();	 Catch:{ Exception -> 0x0092 }
        if (r2 != 0) goto L_0x0072;
    L_0x0008:
        return r0;
    L_0x0009:
        r3 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0094 }
        if (r3 == 0) goto L_0x0015;
    L_0x000d:
        r3 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0094 }
        r3 = b04490449щщщщ(r3, r2);	 Catch:{ Exception -> 0x0094 }
        if (r3 == 0) goto L_0x007e;
    L_0x0015:
        r3 = r8.bЭ042D042DЭ042DЭ;	 Catch:{  }
        if (r3 == 0) goto L_0x0029;
    L_0x0019:
        r3 = r8.bЭ042D042DЭ042DЭ;	 Catch:{  }
        r3 = b04490449щщщщ(r3, r2);	 Catch:{  }
        if (r3 != 0) goto L_0x0029;
    L_0x0021:
        r3 = r8.bЭ042D042DЭ042DЭ;	 Catch:{  }
        r3 = bщщ0449щщщ(r3, r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        if (r3 == 0) goto L_0x007b;
    L_0x0029:
        r3 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        if (r3 == 0) goto L_0x0033;
    L_0x002d:
        r3 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        r3 = r3.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        if (r3 == r2) goto L_0x0039;
    L_0x0033:
        r3 = r8.bь044C044C044C044C044C(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        r8.b042DЭ042DЭ042DЭ = r3;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
    L_0x0039:
        r3 = r8.bЭ042D042DЭ042DЭ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        if (r3 == 0) goto L_0x0063;
    L_0x003d:
        r3 = r8.bЭ042D042DЭ042DЭ;	 Catch:{  }
        r3 = r3.mHapticDataOffset;	 Catch:{  }
        r4 = r2 + 1024;
        r5 = bЭ042DЭ042D042DЭ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        r6 = b04460446цц04460446;
        r7 = bцц0446ц04460446;
        r6 = r6 + r7;
        r7 = b04460446цц04460446;
        r6 = r6 * r7;
        r7 = bццц044604460446;
        r6 = r6 % r7;
        r7 = b044604460446ц04460446;
        if (r6 == r7) goto L_0x005e;
    L_0x0054:
        r6 = bц04460446ц04460446();
        b04460446цц04460446 = r6;
        r6 = 53;
        b044604460446ц04460446 = r6;
    L_0x005e:
        r5 = r5 / 2;
        r4 = r4 - r5;
        if (r3 == r4) goto L_0x0070;
    L_0x0063:
        r2 = r2 + 1024;
        r3 = bЭ042DЭ042D042DЭ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        r3 = r3 / 2;
        r2 = r2 - r3;
        r2 = r8.bь044C044C044C044C044C(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
        r8.bЭ042D042DЭ042DЭ = r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
    L_0x0070:
        r0 = r1;
        goto L_0x0008;
    L_0x0072:
        r2 = r8.bь044Cь044C044C044C(r9);	 Catch:{ Exception -> 0x0092 }
        r3 = r8.b042D042DЭЭЭ042D;	 Catch:{ Exception -> 0x0094 }
        if (r2 < r3) goto L_0x0009;
    L_0x007a:
        goto L_0x0008;
    L_0x007b:
        r8.b044Cьь044C044C044C();	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0099, IOException -> 0x0096 }
    L_0x007e:
        r0 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0092 }
        if (r0 == 0) goto L_0x008f;
    L_0x0082:
        r0 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0092 }
        r0 = r0.mMappedByteBuffer;	 Catch:{ Exception -> 0x0094 }
        r3 = r8.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0094 }
        r2 = r8.bьь044C044C044C044C(r3, r2);	 Catch:{ Exception -> 0x0094 }
        r0.position(r2);	 Catch:{ Exception -> 0x0094 }
    L_0x008f:
        r0 = r1;
        goto L_0x0008;
    L_0x0092:
        r0 = move-exception;
        throw r0;
    L_0x0094:
        r0 = move-exception;
        throw r0;
    L_0x0096:
        r1 = move-exception;
        goto L_0x0008;
    L_0x0099:
        r1 = move-exception;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.bufferAtPlaybackPosition(int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
        r4 = this;
        r0 = 0;
        r1 = 0;
        r2 = 0;
    L_0x0003:
        switch(r0) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0003;
            default: goto L_0x0006;
        };
    L_0x0006:
        r3 = 1;
        switch(r3) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0006;
    L_0x000b:
        r2.length();	 Catch:{ Exception -> 0x000f }
        goto L_0x000b;
    L_0x000f:
        r2 = move-exception;
        r2 = 87;
        b04460446цц04460446 = r2;
    L_0x0014:
        r0 = r0 / r1;
        goto L_0x0014;
    L_0x0016:
        r0 = move-exception;
        r0 = bц04460446ц04460446();
        b04460446цц04460446 = r0;
        r0 = r4.b042D042D042D042D042DЭ;
        r1 = r4.bЭЭ042DЭ042DЭ;
        r0.closeCloseable(r1);
        r0 = r4.bЭЭЭЭЭ042D;
        r0.dispose();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.close():void");
    }

    public long getBlockOffset(long j) {
        try {
            long j2 = j % ((long) b042DЭЭ042D042DЭ);
            if (((b04460446цц04460446 + bцц0446ц04460446) * b04460446цц04460446) % bццц044604460446 != b044604460446ц04460446) {
                b04460446цц04460446 = 21;
                b044604460446ц04460446 = bц04460446ц04460446();
            }
            try {
                return (j2 * 16) / ((long) b042DЭЭ042D042DЭ);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public int getBlockSizeMS() {
        try {
            return b042DЭЭ042D042DЭ;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getBufferForPlaybackPosition(int r8) throws com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException {
        /*
        r7 = this;
        r2 = 0;
        r0 = 1;
        r1 = 0;
        r3 = r7.b042DЭ042DЭ042DЭ;
        if (r3 != 0) goto L_0x0009;
    L_0x0007:
        r0 = r2;
    L_0x0008:
        return r0;
    L_0x0009:
        r4 = r7.bь044Cь044C044C044C(r8);
        r3 = r7.bЭ042D042DЭЭ042D;
        r3.setLastPlayedBlockPosition(r4);
        r3 = r7.b042D042DЭЭЭ042D;
        r5 = bЭ042DЭ042D042DЭ;
        r5 = r5 / 2;
        r3 = r3 - r5;
        if (r4 <= r3) goto L_0x0097;
    L_0x001b:
        r0 = r2;
        goto L_0x0008;
    L_0x001d:
        r7.b044Cьь044C044C044C();	 Catch:{ Exception -> 0x0090 }
        r0 = r1;
    L_0x0021:
        if (r0 == 0) goto L_0x00b1;
    L_0x0023:
        r3 = bЭ042DЭ042D042DЭ;	 Catch:{ Exception -> 0x0090 }
        r3 = r3 / 2;
        r3 = new byte[r3];	 Catch:{ Exception -> 0x0090 }
        r5 = b04460446цц04460446;
        r6 = bцц0446ц04460446;
        r6 = r6 + r5;
        r5 = r5 * r6;
        r6 = bццц044604460446;
        r5 = r5 % r6;
        switch(r5) {
            case 0: goto L_0x003f;
            default: goto L_0x0035;
        };
    L_0x0035:
        r5 = bц04460446ц04460446();
        b04460446цц04460446 = r5;
        r5 = 19;
        b044604460446ц04460446 = r5;
    L_0x003f:
        r5 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r5 = r5.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r5 = r5.position();	 Catch:{ Exception -> 0x0090 }
        r6 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r6 = r6.mHapticDataOffset;	 Catch:{ Exception -> 0x0090 }
        r5 = r5 + r6;
        if (r5 < r4) goto L_0x0050;
    L_0x004e:
        if (r5 <= r4) goto L_0x0063;
    L_0x0050:
        r4 = r4 - r5;
        r5 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r5 = r5.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r5 = r5.position();	 Catch:{ Exception -> 0x0090 }
        r4 = r4 + r5;
        if (r4 >= 0) goto L_0x00b6;
    L_0x005c:
        r4 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r4.position(r1);	 Catch:{ Exception -> 0x0090 }
    L_0x0063:
        r1 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.remaining();	 Catch:{ Exception -> 0x0090 }
        r4 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r4 = r4.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r5 = 0;
        r6 = bЭ042DЭ042D042DЭ;	 Catch:{ Exception -> 0x0090 }
        if (r1 >= r6) goto L_0x00ae;
    L_0x0074:
        r4.get(r3, r5, r1);	 Catch:{ Exception -> 0x0090 }
        if (r0 != 0) goto L_0x008d;
    L_0x0079:
        r0 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r0 = r0.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r1 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.position();	 Catch:{ Exception -> 0x0090 }
        r4 = bЭ042DЭ042D042DЭ;	 Catch:{ Exception -> 0x0090 }
        r4 = r4 / 2;
        r1 = r1 - r4;
        r0.position(r1);	 Catch:{ Exception -> 0x0090 }
    L_0x008d:
        r0 = r3;
        goto L_0x0008;
    L_0x0090:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r2;
        goto L_0x0008;
    L_0x0097:
        r3 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r3 = r3.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r3 = r3.remaining();	 Catch:{ Exception -> 0x0090 }
        r5 = bЭ042DЭ042D042DЭ;	 Catch:{ Exception -> 0x0090 }
        if (r3 >= r5) goto L_0x00cd;
    L_0x00a3:
        r3 = r7.bЭ042D042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        if (r3 == 0) goto L_0x0021;
    L_0x00a7:
        switch(r0) {
            case 0: goto L_0x00a7;
            case 1: goto L_0x001d;
            default: goto L_0x00aa;
        };	 Catch:{ Exception -> 0x0090 }
    L_0x00aa:
        switch(r0) {
            case 0: goto L_0x00a7;
            case 1: goto L_0x001d;
            default: goto L_0x00ad;
        };	 Catch:{ Exception -> 0x0090 }
    L_0x00ad:
        goto L_0x00aa;
    L_0x00ae:
        r1 = bЭ042DЭ042D042DЭ;	 Catch:{ Exception -> 0x0090 }
        goto L_0x0074;
    L_0x00b1:
        r3 = bЭ042DЭ042D042DЭ;	 Catch:{ Exception -> 0x0090 }
        r3 = new byte[r3];	 Catch:{ Exception -> 0x0090 }
        goto L_0x003f;
    L_0x00b6:
        r1 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.limit();	 Catch:{ Exception -> 0x0090 }
        if (r1 >= r4) goto L_0x00cb;
    L_0x00c0:
        r1 = r7.b042DЭ042DЭ042DЭ;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.mMappedByteBuffer;	 Catch:{ Exception -> 0x0090 }
        r1 = r1.limit();	 Catch:{ Exception -> 0x0090 }
        r1 = r1 + -1;
        goto L_0x005c;
    L_0x00cb:
        r1 = r4;
        goto L_0x005c;
    L_0x00cd:
        r0 = r1;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.getBufferForPlaybackPosition(int):byte[]");
    }

    public List getCollectors() {
        this.bЭЭЭ042DЭ042D.clear();
        this.bЭЭЭ042DЭ042D.add(this.bЭ042D042DЭЭ042D);
        this.bЭЭЭ042DЭ042D.add(this.b042D042D042DЭЭ042D);
        return this.bЭЭЭ042DЭ042D;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getEncryptedHapticHeader() {
        /*
        r2 = this;
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = b04460446цц04460446;
        r1 = bцц0446ц04460446;
        r0 = r0 + r1;
        r1 = b04460446цц04460446;
        r0 = r0 * r1;
        r1 = bццц044604460446;
        r0 = r0 % r1;
        r1 = b044604460446ц04460446;
        if (r0 == r1) goto L_0x0022;
    L_0x0018:
        r0 = 89;
        b04460446цц04460446 = r0;
        r0 = bц04460446ц04460446();
        b044604460446ц04460446 = r0;
    L_0x0022:
        r0 = r2.b042DЭЭЭЭ042D;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader.getEncryptedHapticHeader():byte[]");
    }

    public int getHapticBlockIndex(long j) {
        int i = b04460446цц04460446;
        switch ((i * (bцц0446ц04460446 + i)) % bццц044604460446) {
            case 0:
                break;
            default:
                b04460446цц04460446 = 77;
                b044604460446ц04460446 = 45;
                break;
        }
        try {
            i = bь044Cь044C044C044C((int) j);
            if (this.b042D042D042DЭ042DЭ == 2) {
                return i / 16;
            }
            try {
                return this.b042D042D042DЭ042DЭ >= 3 ? i / (b044C044Cь044C044C044C() * 16) : 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public HapticFileInformation getHapticFileInformation() {
        return this.b042D042DЭ042D042DЭ;
    }

    public void setBlockSizeMS(int i) {
        try {
            b042DЭЭ042D042DЭ = i;
            int bц04460446ц04460446 = bц04460446ц04460446();
            switch ((bц04460446ц04460446 * (bцц0446ц04460446 + bц04460446ц04460446)) % bццц044604460446) {
                case 0:
                    return;
                default:
                    b04460446цц04460446 = 51;
                    b044604460446ц04460446 = 60;
                    return;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void setBytesAvailable(int i) {
        if (((bц04460446ц04460446() + bцц0446ц04460446) * bц04460446ц04460446()) % bццц044604460446 != b044604460446ц04460446) {
            b04460446цц04460446 = 53;
            b044604460446ц04460446 = 9;
        }
        try {
            this.bЭЭЭ042D042DЭ = i;
            if (this.bЭЭЭ042D042DЭ <= 0) {
                this.bЭЭЭ042D042DЭ = i;
                try {
                    b044C044C044Cь044C044C();
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
