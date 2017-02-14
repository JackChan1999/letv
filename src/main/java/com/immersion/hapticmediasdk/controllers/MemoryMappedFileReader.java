package com.immersion.hapticmediasdk.controllers;

import com.immersion.hapticmediasdk.models.HapticFileInformation;
import com.immersion.hapticmediasdk.models.HapticFileInformation.Builder;
import com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Profiler;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import rrrrrr.rccrrc;

public class MemoryMappedFileReader implements IHapticFileReader {
    public static int b041504150415ЕЕ0415 = 1;
    public static int b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
    private static int b041B041B041B041BЛЛ = 0;
    private static final int b041BЛЛЛ041BЛ = 4096;
    public static int bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
    public static int bЕЕЕ0415Е0415 = 2;
    private static int bЛ041B041B041BЛЛ = 40;
    private static final int bЛ041BЛЛ041BЛ = 12288;
    private static final String bЛЛЛ041BЛЛ = "MemoryMappedFileReader";
    private rccrrc b041B041BЛ041BЛЛ;
    private String b041B041BЛЛ041BЛ;
    private int b041BЛ041B041BЛЛ;
    private FileManager b041BЛ041BЛ041BЛ;
    private File b041BЛЛ041BЛЛ;
    private FileChannel bЛ041BЛ041BЛЛ;
    private rccrrc bЛЛ041B041BЛЛ;
    private final Profiler bЛЛ041BЛ041BЛ;
    private HapticFileInformation bЛЛЛЛ041BЛ;

    static {
        int i = b0415Е0415ЕЕ0415;
        switch ((i * (b041504150415ЕЕ0415 + i)) % bЕ0415ЕЕ04150415()) {
            case 0:
                break;
            default:
                break;
        }
    }

    public MemoryMappedFileReader(String str, FileManager fileManager) {
        try {
            this.bЛЛ041BЛ041BЛ = new Profiler();
            try {
                this.b041B041BЛЛ041BЛ = str;
                if (((b0415Е0415ЕЕ0415 + b041504150415ЕЕ0415) * b0415Е0415ЕЕ0415) % bЕЕЕ0415Е0415 != bЕ04150415ЕЕ0415) {
                    b0415Е0415ЕЕ0415 = 60;
                    bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
                }
                this.b041BЛ041BЛ041BЛ = fileManager;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int b04150415Е0415Е0415() {
        return 1;
    }

    public static int b0415ЕЕ0415Е0415() {
        return 61;
    }

    private int b044C044C044C044C044Cь(int i) {
        int hapticDataStartByteOffset = this.bЛЛЛЛ041BЛ.getHapticDataStartByteOffset();
        int i2 = b0415Е0415ЕЕ0415;
        switch ((i2 * (b041504150415ЕЕ0415 + i2)) % bЕЕЕ0415Е0415) {
            case 0:
                break;
            default:
                b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
                bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
                break;
        }
        return hapticDataStartByteOffset + b044Cь044C044C044Cь(i);
    }

    private boolean b044C044Cь044C044Cь() {
        String str = null;
        try {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.position(0);
            if (this.bЛ041BЛ041BЛЛ.read(allocate, 16) != 4) {
                return false;
            }
            allocate.flip();
            int i = allocate.getInt() + 28;
            ByteBuffer allocate2 = ByteBuffer.allocate(i);
            allocate2.order(ByteOrder.LITTLE_ENDIAN);
            if (this.bЛ041BЛ041BЛЛ.read(allocate2, 0) != i) {
                return false;
            }
            allocate2.flip();
            Builder builder = new Builder();
            builder.setFilePath(this.b041BЛЛ041BЛЛ.getAbsolutePath());
            allocate2.position(4);
            builder.setTotalFileLength(allocate2.getInt() + 8);
            allocate2.position(20);
            builder.setMajorVersion(allocate2.get());
            builder.setMinorVersion(allocate2.get());
            builder.setEncoding(allocate2.get());
            allocate2.position(24);
            builder.setSampleHertz(allocate2.getInt());
            builder.setBitsPerSample(allocate2.get() | (allocate2.get() << 8));
            byte b = allocate2.get();
            builder.setNumberOfChannels(b);
            int[] iArr = new int[b];
            for (byte b2 = (byte) 0; b2 < b; b2++) {
                iArr[b2] = allocate2.get();
            }
            builder.setActuatorArray(iArr);
            builder.setCompressionScheme(allocate2.get());
            allocate2.position(allocate2.position() + 4);
            builder.setHapticDataLength(allocate2.getInt());
            builder.setHapticDataStartByteOffset(allocate2.position());
            this.bЛЛЛЛ041BЛ = builder.build();
            b041B041B041B041BЛЛ = ((((bЛ041B041B041BЛЛ * this.bЛЛЛЛ041BЛ.getSampleHertz()) / 1000) * this.bЛЛЛЛ041BЛ.getBitsPerSample()) * this.bЛЛЛЛ041BЛ.getNumberOfChannels()) / 8;
            int i2 = 5;
            while (true) {
                try {
                    i2 /= 0;
                } catch (Exception e) {
                    b0415Е0415ЕЕ0415 = 79;
                    while (true) {
                        try {
                            i2 /= 0;
                        } catch (Exception e2) {
                            b0415Е0415ЕЕ0415 = 12;
                            while (true) {
                                try {
                                    str.length();
                                } catch (Exception e3) {
                                    b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            return false;
        }
    }

    private static boolean b044C044Cььь044C(rccrrc rrrrrr_rccrrc, int i) {
        if (((b0415Е0415ЕЕ0415 + b041504150415ЕЕ0415) * b0415Е0415ЕЕ0415) % bЕЕЕ0415Е0415 != bЕ04150415ЕЕ0415) {
            b0415Е0415ЕЕ0415 = 22;
            bЕ04150415ЕЕ0415 = 47;
        }
        try {
            return b044Cьььь044C(rrrrrr_rccrrc, i) || bь044Cььь044C(rrrrrr_rccrrc, i);
        } catch (Exception e) {
            throw e;
        }
    }

    private int b044Cь044C044C044Cь(int i) {
        int sampleHertz = i / (1000 / this.bЛЛЛЛ041BЛ.getSampleHertz());
        int bitsPerSample = this.bЛЛЛЛ041BЛ.getBitsPerSample();
        int numberOfChannels = this.bЛЛЛЛ041BЛ.getNumberOfChannels();
        float f = (float) ((bitsPerSample * numberOfChannels) / 8);
        bitsPerSample = (int) f;
        if (((float) (bitsPerSample * numberOfChannels)) / 8.0f > f) {
            bitsPerSample++;
        }
        return bitsPerSample * sampleHertz;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b044Cь044Cьь044C() {
        /*
        r4 = this;
        r0 = "MemoryMappedFileReader";
        r1 = "%%%%%%%%%%% logBufferState %%%%%%%%%%%";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = r4.b041B041BЛ041BЛЛ;
        if (r0 == 0) goto L_0x0188;
    L_0x000b:
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW capacity = ";
        r1 = r1.append(r2);
        r2 = r4.b041B041BЛ041BЛЛ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.capacity();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW position = ";
        r1 = r1.append(r2);
        r2 = r4.b041B041BЛ041BЛЛ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW remaining = ";
        r1 = r1.append(r2);
        r2 = r4.b041B041BЛ041BЛЛ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.remaining();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
    L_0x006d:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0076;
            case 1: goto L_0x006d;
            default: goto L_0x0071;
        };
    L_0x0071:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x006d;
            case 1: goto L_0x0076;
            default: goto L_0x0075;
        };
    L_0x0075:
        goto L_0x0071;
    L_0x0076:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW mHapticDataOffset = ";
        r1 = r1.append(r2);
        r2 = r4.b041B041BЛ041BЛЛ;
        r2 = r2.mHapticDataOffset;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mCurrentMMW mHapticDataOffset + position = ";
        r1 = r1.append(r2);
        r2 = r4.b041B041BЛ041BЛЛ;
        r2 = r2.mHapticDataOffset;
        r3 = r4.b041B041BЛ041BЛЛ;
        r3 = r3.mMappedByteBuffer;
        r3 = r3.position();
        r2 = r2 + r3;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
    L_0x00b5:
        r0 = "MemoryMappedFileReader";
        r1 = "--------------------------------------";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = r4.bЛЛ041B041BЛЛ;
        if (r0 == 0) goto L_0x0191;
    L_0x00c0:
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW capacity = ";
        r1 = r1.append(r2);
        r2 = r4.bЛЛ041B041BЛЛ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.capacity();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW position = ";
        r1 = r1.append(r2);
        r2 = r4.bЛЛ041B041BЛЛ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.position();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = b0415ЕЕ0415Е0415();
        r1 = b041504150415ЕЕ0415;
        r0 = r0 + r1;
        r1 = b0415ЕЕ0415Е0415();
        r0 = r0 * r1;
        r1 = bЕЕЕ0415Е0415;
        r0 = r0 % r1;
        r1 = bЕ04150415ЕЕ0415;
        if (r0 == r1) goto L_0x011f;
    L_0x0113:
        r0 = b0415ЕЕ0415Е0415();
        b0415Е0415ЕЕ0415 = r0;
        r0 = b0415ЕЕ0415Е0415();
        bЕ04150415ЕЕ0415 = r0;
    L_0x011f:
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW remaining = ";
        r1 = r1.append(r2);
        r2 = r4.bЛЛ041B041BЛЛ;
        r2 = r2.mMappedByteBuffer;
        r2 = r2.remaining();
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW mHapticDataOffset = ";
        r1 = r1.append(r2);
        r2 = r4.bЛЛ041B041BЛЛ;
        r2 = r2.mHapticDataOffset;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        r0 = "MemoryMappedFileReader";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "mNextMMW mHapticDataOffset + position = ";
        r1 = r1.append(r2);
        r2 = r4.bЛЛ041B041BЛЛ;
        r2 = r2.mHapticDataOffset;
        r3 = r4.bЛЛ041B041BЛЛ;
        r3 = r3.mMappedByteBuffer;
        r3 = r3.position();
        r2 = r2 + r3;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
    L_0x0180:
        r0 = "MemoryMappedFileReader";
        r1 = "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        return;
    L_0x0188:
        r0 = "MemoryMappedFileReader";
        r1 = "mCurrentMMW is null";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        goto L_0x00b5;
    L_0x0191:
        r0 = "MemoryMappedFileReader";
        r1 = "mNextMMW is null";
        com.immersion.hapticmediasdk.utils.Log.d(r0, r1);
        goto L_0x0180;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.b044Cь044Cьь044C():void");
    }

    private boolean b044Cьь044C044Cь(int i) {
        int i2 = 2;
        while (true) {
            try {
                i2 /= 0;
            } catch (Exception e) {
                b0415Е0415ЕЕ0415 = 48;
                try {
                    return this.b041BЛ041B041BЛЛ >= i;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        }
    }

    private static boolean b044Cьььь044C(rccrrc rrrrrr_rccrrc, int i) {
        if (i >= rrrrrr_rccrrc.mHapticDataOffset) {
            return false;
        }
        if (((b0415Е0415ЕЕ0415 + b041504150415ЕЕ0415) * b0415Е0415ЕЕ0415) % bЕЕЕ0415Е0415 == bЕ04150415ЕЕ0415) {
            return true;
        }
        b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
        bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
        return true;
    }

    public static int bЕ0415Е0415Е0415() {
        return 0;
    }

    public static int bЕ0415ЕЕ04150415() {
        return 2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int bь044C044C044C044Cь(rrrrrr.rccrrc r4, int r5) {
        /*
        r3 = this;
        r2 = 0;
        r0 = b0415Е0415ЕЕ0415;
        r1 = b04150415Е0415Е0415();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЕЕЕ0415Е0415;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0019;
            default: goto L_0x000f;
        };
    L_0x000f:
        r0 = 68;
        b0415Е0415ЕЕ0415 = r0;
        r0 = b0415ЕЕ0415Е0415();
        bЕ04150415ЕЕ0415 = r0;
    L_0x0019:
        r0 = r4.mHapticDataOffset;
    L_0x001b:
        switch(r2) {
            case 0: goto L_0x0022;
            case 1: goto L_0x001b;
            default: goto L_0x001e;
        };
    L_0x001e:
        switch(r2) {
            case 0: goto L_0x0022;
            case 1: goto L_0x001b;
            default: goto L_0x0021;
        };
    L_0x0021:
        goto L_0x001e;
    L_0x0022:
        r0 = r5 - r0;
        r1 = r4.mMappedByteBuffer;
        r1 = r1.capacity();
        r0 = r0 % r1;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.bь044C044C044C044Cь(rrrrrr.rccrrc, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean bь044Cь044C044Cь() {
        /*
        r5 = this;
        r0 = 0;
        r2 = 0;
        r1 = r5.bЛЛЛЛ041BЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        if (r1 == 0) goto L_0x0008;
    L_0x0006:
        r0 = 1;
    L_0x0007:
        return r0;
    L_0x0008:
        r1 = 12288; // 0x3000 float:1.7219E-41 double:6.071E-320;
        r1 = r5.b044Cьь044C044Cь(r1);	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        if (r1 != 0) goto L_0x0007;
    L_0x0010:
        r1 = r5.b041BЛЛ041BЛЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        if (r1 != 0) goto L_0x001e;
    L_0x0014:
        r1 = r5.b041BЛ041BЛ041BЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        r3 = r5.b041B041BЛЛ041BЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        r1 = r1.getHapticStorageFile(r3);	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        r5.b041BЛЛ041BЛЛ = r1;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
    L_0x001e:
        r1 = r5.bЛ041BЛ041BЛЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        if (r1 != 0) goto L_0x0033;
    L_0x0022:
        r3 = new java.io.RandomAccessFile;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        r1 = r5.b041BЛЛ041BЛЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        r4 = "r";
        r3.<init>(r1, r4);	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        r1 = r3.getChannel();	 Catch:{ FileNotFoundException -> 0x007e, Exception -> 0x0079 }
        r5.bЛ041BЛ041BЛЛ = r1;	 Catch:{ FileNotFoundException -> 0x007e, Exception -> 0x0079 }
        r2 = r3;
    L_0x0033:
        r1 = r5.bЛ041BЛ041BЛЛ;	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        if (r1 == 0) goto L_0x0007;
    L_0x0037:
        r0 = r5.b044C044Cь044C044Cь();	 Catch:{ FileNotFoundException -> 0x003c, Exception -> 0x0079 }
        goto L_0x0007;
    L_0x003c:
        r1 = move-exception;
    L_0x003d:
        r3 = "MemoryMappedFileReader";
    L_0x003f:
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x003f;
            default: goto L_0x0042;
        };
    L_0x0042:
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x003f;
            default: goto L_0x0045;
        };
    L_0x0045:
        goto L_0x0042;
    L_0x0046:
        r1 = r1.getMessage();
        com.immersion.hapticmediasdk.utils.Log.e(r3, r1);
        r1 = r5.b041BЛ041BЛ041BЛ;
        r1.closeCloseable(r2);
        r1 = r5.b041BЛ041BЛ041BЛ;
        r2 = r5.bЛ041BЛ041BЛЛ;
        r1.closeCloseable(r2);
        r1 = b0415ЕЕ0415Е0415();
        r2 = b041504150415ЕЕ0415;
        r1 = r1 + r2;
        r2 = b0415ЕЕ0415Е0415();
        r1 = r1 * r2;
        r2 = bЕЕЕ0415Е0415;
        r1 = r1 % r2;
        r2 = bЕ04150415ЕЕ0415;
        if (r1 == r2) goto L_0x0007;
    L_0x006c:
        r1 = b0415ЕЕ0415Е0415();
        b0415Е0415ЕЕ0415 = r1;
        r1 = b0415ЕЕ0415Е0415();
        bЕ04150415ЕЕ0415 = r1;
        goto L_0x0007;
    L_0x0079:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0007;
    L_0x007e:
        r1 = move-exception;
        r2 = r3;
        goto L_0x003d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.bь044Cь044C044Cь():boolean");
    }

    private static boolean bь044Cььь044C(rccrrc rrrrrr_rccrrc, int i) {
        if (i < rrrrrr_rccrrc.mHapticDataOffset + rrrrrr_rccrrc.mMappedByteBuffer.capacity()) {
            return false;
        }
        int i2 = b0415Е0415ЕЕ0415;
        switch ((i2 * (b041504150415ЕЕ0415 + i2)) % bЕЕЕ0415Е0415) {
            case 0:
                return true;
            default:
                b0415Е0415ЕЕ0415 = 62;
                bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
                return true;
        }
    }

    private void bьь044C044C044Cь() throws NotEnoughHapticBytesAvailableException, IOException {
        if (this.bЛЛ041B041BЛЛ != null) {
            int i = this.bЛЛ041B041BЛЛ.mHapticDataOffset + 4096;
            this.b041B041BЛ041BЛЛ = this.bЛЛ041B041BЛЛ;
            this.bЛЛ041B041BЛЛ = bььььь044C(i);
        }
    }

    private static boolean bьь044Cьь044C(rccrrc rrrrrr_rccrrc, int i) {
        return bь044Cььь044C(rrrrrr_rccrrc, b041B041B041B041BЛЛ + i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private rrrrrr.rccrrc bььььь044C(int r9) throws java.io.IOException, com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException {
        /*
        r8 = this;
        r6 = 0;
        r7 = 1;
        r0 = r8.bЛЛ041BЛ041BЛ;
        r0.startTiming();
        r0 = r8.bЛЛЛЛ041BЛ;
        r0 = r0.getHapticDataLength();
        if (r9 >= r0) goto L_0x0074;
    L_0x000f:
        r0 = r8.bЛЛЛЛ041BЛ;
        r0 = r0.getHapticDataStartByteOffset();
        r2 = r0 + r9;
        r0 = r9 + 4096;
        r1 = r8.bЛЛЛЛ041BЛ;
        r1 = r1.getHapticDataLength();
        if (r0 > r1) goto L_0x006b;
    L_0x0021:
        r0 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r4 = r0;
    L_0x0024:
        r0 = r9 + r4;
        r1 = r8.b041BЛ041B041BЛЛ;
        if (r0 <= r1) goto L_0x0056;
    L_0x002a:
        r0 = new com.immersion.hapticmediasdk.models.NotEnoughHapticBytesAvailableException;
        r1 = "Not enough bytes available yet.";
        r0.<init>(r1);
        throw r0;
    L_0x0032:
        r2 = b0415Е0415ЕЕ0415;
        r3 = b041504150415ЕЕ0415;
        r2 = r2 + r3;
        r3 = b0415Е0415ЕЕ0415;
        r2 = r2 * r3;
        r3 = bЕЕЕ0415Е0415;
        r2 = r2 % r3;
        r3 = bЕ04150415ЕЕ0415;
        if (r2 == r3) goto L_0x0049;
    L_0x0041:
        r2 = 44;
        b0415Е0415ЕЕ0415 = r2;
        r2 = 52;
        bЕ04150415ЕЕ0415 = r2;
    L_0x0049:
        r1.order(r0);
        r0 = new rrrrrr.rccrrc;
        r0.<init>(r6);
        r0.mMappedByteBuffer = r1;
        r0.mHapticDataOffset = r9;
    L_0x0055:
        return r0;
    L_0x0056:
        r0 = r8.bЛ041BЛ041BЛЛ;
        r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY;
        r2 = (long) r2;
        r4 = (long) r4;
        r1 = r0.map(r1, r2, r4);
        if (r1 == 0) goto L_0x0074;
    L_0x0062:
        r0 = java.nio.ByteOrder.LITTLE_ENDIAN;
    L_0x0064:
        switch(r7) {
            case 0: goto L_0x0064;
            case 1: goto L_0x0032;
            default: goto L_0x0067;
        };
    L_0x0067:
        switch(r7) {
            case 0: goto L_0x0064;
            case 1: goto L_0x0032;
            default: goto L_0x006a;
        };
    L_0x006a:
        goto L_0x0067;
    L_0x006b:
        r0 = r8.bЛЛЛЛ041BЛ;
        r0 = r0.getHapticDataLength();
        r0 = r0 - r9;
        r4 = r0;
        goto L_0x0024;
    L_0x0074:
        r0 = r6;
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.bььььь044C(int):rrrrrr.rccrrc");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean bufferAtPlaybackPosition(int r7) {
        /*
        r6 = this;
        r1 = 1;
        r0 = 0;
        r2 = r6.bь044Cь044C044Cь();
        if (r2 != 0) goto L_0x0009;
    L_0x0008:
        return r0;
    L_0x0009:
        r2 = r6.b044Cь044C044C044Cь(r7);
        r3 = r6.b041B041BЛ041BЛЛ;
        if (r3 == 0) goto L_0x0019;
    L_0x0011:
        r3 = r6.b041B041BЛ041BЛЛ;
        r3 = b044C044Cььь044C(r3, r2);
        if (r3 == 0) goto L_0x0072;
    L_0x0019:
        r3 = r6.bЛЛ041B041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        if (r3 == 0) goto L_0x0042;
    L_0x001d:
        r3 = r6.bЛЛ041B041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r4 = b0415Е0415ЕЕ0415;
        r5 = b041504150415ЕЕ0415;
        r4 = r4 + r5;
        r5 = b0415Е0415ЕЕ0415;
        r4 = r4 * r5;
        r5 = bЕЕЕ0415Е0415;
        r4 = r4 % r5;
        r5 = bЕ04150415ЕЕ0415;
        if (r4 == r5) goto L_0x0034;
    L_0x002e:
        r4 = 27;
        b0415Е0415ЕЕ0415 = r4;
        bЕ04150415ЕЕ0415 = r1;
    L_0x0034:
        r3 = b044C044Cььь044C(r3, r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        if (r3 != 0) goto L_0x0042;
    L_0x003a:
        r3 = r6.bЛЛ041B041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r3 = bьь044Cьь044C(r3, r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        if (r3 == 0) goto L_0x006f;
    L_0x0042:
        r3 = r6.b041B041BЛ041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        if (r3 == 0) goto L_0x004c;
    L_0x0046:
        r3 = r6.b041B041BЛ041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r3 = r3.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        if (r3 == r2) goto L_0x0059;
    L_0x004c:
        switch(r1) {
            case 0: goto L_0x004c;
            case 1: goto L_0x0053;
            default: goto L_0x004f;
        };	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
    L_0x004f:
        switch(r0) {
            case 0: goto L_0x0053;
            case 1: goto L_0x004c;
            default: goto L_0x0052;
        };	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
    L_0x0052:
        goto L_0x004f;
    L_0x0053:
        r3 = r6.bььььь044C(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r6.b041B041BЛ041BЛЛ = r3;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
    L_0x0059:
        r3 = r6.bЛЛ041B041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        if (r3 == 0) goto L_0x0065;
    L_0x005d:
        r3 = r6.bЛЛ041B041BЛЛ;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r3 = r3.mHapticDataOffset;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r4 = r2 + 4096;
        if (r3 == r4) goto L_0x006d;
    L_0x0065:
        r2 = r2 + 4096;
        r2 = r6.bььььь044C(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
        r6.bЛЛ041B041BЛЛ = r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
    L_0x006d:
        r0 = r1;
        goto L_0x0008;
    L_0x006f:
        r6.bьь044C044C044Cь();	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x008b, IOException -> 0x0085 }
    L_0x0072:
        r0 = r6.b041B041BЛ041BЛЛ;
        if (r0 == 0) goto L_0x0083;
    L_0x0076:
        r0 = r6.b041B041BЛ041BЛЛ;
        r0 = r0.mMappedByteBuffer;
        r3 = r6.b041B041BЛ041BЛЛ;
        r2 = r6.bь044C044C044C044Cь(r3, r2);
        r0.position(r2);
    L_0x0083:
        r0 = r1;
        goto L_0x0008;
    L_0x0085:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0008;
    L_0x008b:
        r1 = move-exception;
        r2 = "MemoryMappedFileReader";
        r1 = r1.getMessage();
        com.immersion.hapticmediasdk.utils.Log.w(r2, r1);
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.bufferAtPlaybackPosition(int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
        r3 = this;
        r2 = 0;
        r0 = b0415ЕЕ0415Е0415();
        r1 = b041504150415ЕЕ0415;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЕЕЕ0415Е0415;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0019;
            default: goto L_0x000f;
        };
    L_0x000f:
        r0 = b0415ЕЕ0415Е0415();
        b0415Е0415ЕЕ0415 = r0;
        r0 = 33;
        bЕ04150415ЕЕ0415 = r0;
    L_0x0019:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0019;
            default: goto L_0x001c;
        };
    L_0x001c:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0019;
            default: goto L_0x001f;
        };
    L_0x001f:
        goto L_0x001c;
    L_0x0020:
        r0 = r3.b041BЛ041BЛ041BЛ;
        r1 = r3.bЛ041BЛ041BЛЛ;
        r0.closeCloseable(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.close():void");
    }

    public long getBlockOffset(long j) {
        int i = b0415Е0415ЕЕ0415;
        switch ((i * (b041504150415ЕЕ0415 + i)) % bЕЕЕ0415Е0415) {
            case 0:
                break;
            default:
                b0415Е0415ЕЕ0415 = 64;
                bЕ04150415ЕЕ0415 = 36;
                break;
        }
        return 0;
    }

    public int getBlockSizeMS() {
        try {
            int i = bЛ041B041B041BЛЛ;
            if (((b0415Е0415ЕЕ0415 + b041504150415ЕЕ0415) * b0415Е0415ЕЕ0415) % bЕЕЕ0415Е0415 != bЕ0415Е0415Е0415()) {
                b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
                bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
            }
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    public byte[] getBufferForPlaybackPosition(int i) throws NotEnoughHapticBytesAvailableException {
        try {
            if (this.b041B041BЛ041BЛЛ == null) {
                return null;
            }
            if (this.b041B041BЛ041BЛЛ.mHapticDataOffset + this.b041B041BЛ041BЛЛ.mMappedByteBuffer.position() >= this.bЛЛЛЛ041BЛ.getHapticDataLength()) {
                return null;
            }
            int i2 = b0415Е0415ЕЕ0415;
            switch ((i2 * (b041504150415ЕЕ0415 + i2)) % bЕЕЕ0415Е0415) {
                case 0:
                    break;
                default:
                    b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
                    bЕ04150415ЕЕ0415 = 92;
                    break;
            }
            try {
                byte[] bArr = new byte[b041B041B041B041BЛЛ];
                if (b041B041B041B041BЛЛ >= this.b041B041BЛ041BЛЛ.mMappedByteBuffer.remaining()) {
                    int remaining = this.b041B041BЛ041BЛЛ.mMappedByteBuffer.remaining();
                    int i3 = b041B041B041B041BЛЛ - remaining;
                    this.b041B041BЛ041BЛЛ.mMappedByteBuffer.get(bArr, 0, remaining);
                    if (i3 > 0 && this.bЛЛ041B041BЛЛ != null) {
                        if (this.bЛЛ041B041BЛЛ.mMappedByteBuffer.remaining() < i3) {
                            i3 = this.bЛЛ041B041BЛЛ.mMappedByteBuffer.remaining();
                        }
                        this.bЛЛ041B041BЛЛ.mMappedByteBuffer.get(bArr, remaining, i3);
                    }
                    bьь044C044C044Cь();
                } else {
                    this.b041B041BЛ041BЛЛ.mMappedByteBuffer.get(bArr, 0, b041B041B041B041BЛЛ);
                }
                return bArr;
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                    return null;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        } catch (Exception e22) {
            throw e22;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List getCollectors() {
        /*
        r3 = this;
    L_0x0000:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = 0;
        r1 = b0415Е0415ЕЕ0415;
        r2 = b041504150415ЕЕ0415;
        r1 = r1 + r2;
        r2 = b0415Е0415ЕЕ0415;
        r1 = r1 * r2;
        r2 = bЕЕЕ0415Е0415;
        r1 = r1 % r2;
        r2 = bЕ04150415ЕЕ0415;
        if (r1 == r2) goto L_0x0021;
    L_0x0019:
        r1 = 43;
        b0415Е0415ЕЕ0415 = r1;
        r1 = 24;
        bЕ04150415ЕЕ0415 = r1;
    L_0x0021:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.getCollectors():java.util.List");
    }

    public byte[] getEncryptedHapticHeader() {
        return new byte[0];
    }

    public int getHapticBlockIndex(long j) {
        if (((b0415Е0415ЕЕ0415 + b041504150415ЕЕ0415) * b0415Е0415ЕЕ0415) % bЕЕЕ0415Е0415 != bЕ04150415ЕЕ0415) {
            b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
            bЕ04150415ЕЕ0415 = 3;
        }
        return 0;
    }

    public HapticFileInformation getHapticFileInformation() {
        HapticFileInformation hapticFileInformation = this.bЛЛЛЛ041BЛ;
        if (((b0415Е0415ЕЕ0415 + b041504150415ЕЕ0415) * b0415Е0415ЕЕ0415) % bЕ0415ЕЕ04150415() != bЕ04150415ЕЕ0415) {
            b0415Е0415ЕЕ0415 = b0415ЕЕ0415Е0415();
            bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
        }
        return hapticFileInformation;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setBlockSizeMS(int r3) {
        /*
        r2 = this;
    L_0x0000:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = b0415Е0415ЕЕ0415;
        r1 = b041504150415ЕЕ0415;
        r0 = r0 + r1;
        r1 = b0415Е0415ЕЕ0415;
        r0 = r0 * r1;
        r1 = bЕЕЕ0415Е0415;
        r0 = r0 % r1;
        r1 = bЕ04150415ЕЕ0415;
        if (r0 == r1) goto L_0x0022;
    L_0x0018:
        r0 = 59;
        b0415Е0415ЕЕ0415 = r0;
        r0 = b0415ЕЕ0415Е0415();
        bЕ04150415ЕЕ0415 = r0;
    L_0x0022:
        bЛ041B041B041BЛЛ = r3;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MemoryMappedFileReader.setBlockSizeMS(int):void");
    }

    public void setBytesAvailable(int i) {
        this.b041BЛ041B041BЛЛ = i;
        bь044Cь044C044Cь();
        if (((b0415Е0415ЕЕ0415 + b04150415Е0415Е0415()) * b0415Е0415ЕЕ0415) % bЕЕЕ0415Е0415 != bЕ04150415ЕЕ0415) {
            b0415Е0415ЕЕ0415 = 0;
            bЕ04150415ЕЕ0415 = b0415ЕЕ0415Е0415();
        }
    }
}
