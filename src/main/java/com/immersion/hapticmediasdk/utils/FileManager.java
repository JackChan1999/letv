package com.immersion.hapticmediasdk.utils;

import android.content.Context;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

public class FileManager {
    public static final String HAPTIC_STORAGE_FILENAME = "dat.hapt";
    public static final String TAG = "FileManager";
    public static int b04250425042504250425Х = 1;
    public static int b0425Х042504250425Х = 98;
    public static int bХ0425042504250425Х = 0;
    public static int bХХХХХ0425 = 2;
    private int b043Dн043Dн043D043D;
    Context bн043D043Dн043D043D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public FileManager(android.content.Context r3) {
        /*
        r2 = this;
        r0 = 1;
        r2.<init>();
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000b;
            default: goto L_0x0007;
        };
    L_0x0007:
        switch(r0) {
            case 0: goto L_0x0004;
            case 1: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0007;
    L_0x000b:
        r0 = 0;
        r2.b043Dн043Dн043D043D = r0;
        r0 = b0425Х042504250425Х;
        r1 = b04250425042504250425Х;
        r0 = r0 + r1;
        r1 = b0425Х042504250425Х;
        r0 = r0 * r1;
        r1 = bХХХХХ0425;
        r0 = r0 % r1;
        r1 = bХ0425042504250425Х;
        if (r0 == r1) goto L_0x0027;
    L_0x001d:
        r0 = b0425ХХХХ0425();
        b0425Х042504250425Х = r0;
        r0 = 8;
        bХ0425042504250425Х = r0;
    L_0x0027:
        r2.bн043D043Dн043D043D = r3;
        r0 = android.os.Process.myTid();
        r2.b043Dн043Dн043D043D = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.FileManager.<init>(android.content.Context):void");
    }

    public static int b04250425ХХХ0425() {
        return 2;
    }

    public static int b0425ХХХХ0425() {
        return 36;
    }

    public static int bХ0425ХХХ0425() {
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeCloseable(java.io.Closeable r4) {
        /*
        r3 = this;
        r2 = 0;
        if (r4 == 0) goto L_0x001a;
    L_0x0003:
        r4.close();	 Catch:{ SocketTimeoutException -> 0x0031, IOException -> 0x001b }
        r0 = b0425Х042504250425Х;
        r1 = b04250425042504250425Х;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bХХХХХ0425;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001a;
            default: goto L_0x0012;
        };
    L_0x0012:
        r0 = 11;
        b0425Х042504250425Х = r0;
        r0 = 75;
        bХ0425042504250425Х = r0;
    L_0x001a:
        return;
    L_0x001b:
        r0 = move-exception;
        r1 = "FileManager";
    L_0x001e:
        switch(r2) {
            case 0: goto L_0x0025;
            case 1: goto L_0x001e;
            default: goto L_0x0021;
        };
    L_0x0021:
        switch(r2) {
            case 0: goto L_0x0025;
            case 1: goto L_0x001e;
            default: goto L_0x0024;
        };
    L_0x0024:
        goto L_0x0021;
    L_0x0025:
        r2 = r0.getMessage();
        if (r2 != 0) goto L_0x003a;
    L_0x002b:
        r0 = "Fail to close closable.";
    L_0x002d:
        com.immersion.hapticmediasdk.utils.Log.e(r1, r0);
        goto L_0x001a;
    L_0x0031:
        r0 = move-exception;
        r0 = "FileManager";
        r1 = "Connection Timed Out.";
        com.immersion.hapticmediasdk.utils.Log.e(r0, r1);
        goto L_0x001a;
    L_0x003a:
        r0 = r0.getMessage();
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.FileManager.closeCloseable(java.io.Closeable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void deleteHapticStorage() {
        /*
        r9 = this;
        r1 = 0;
        r0 = new java.io.File;
        r2 = r9.getInternalHapticPath();
        r0.<init>(r2);
        r2 = r0.listFiles();
        if (r2 == 0) goto L_0x005a;
    L_0x0010:
        r3 = r2.length;
        r0 = r1;
    L_0x0012:
        if (r0 >= r3) goto L_0x005a;
    L_0x0014:
        r4 = r2[r0];
        r5 = r4.getName();
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = r9.b043Dн043Dн043D043D;
    L_0x0021:
        switch(r1) {
            case 0: goto L_0x0029;
            case 1: goto L_0x0021;
            default: goto L_0x0024;
        };
    L_0x0024:
        r8 = 1;
        switch(r8) {
            case 0: goto L_0x0021;
            case 1: goto L_0x0029;
            default: goto L_0x0028;
        };
    L_0x0028:
        goto L_0x0024;
    L_0x0029:
        r6 = r6.append(r7);
        r7 = "dat.hapt";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r5 = r5.endsWith(r6);
        if (r5 == 0) goto L_0x0057;
    L_0x003d:
        r5 = b0425Х042504250425Х;
        r6 = b04250425042504250425Х;
        r5 = r5 + r6;
        r6 = b0425Х042504250425Х;
        r5 = r5 * r6;
        r6 = bХХХХХ0425;
        r5 = r5 % r6;
        r6 = bХ0425042504250425Х;
        if (r5 == r6) goto L_0x0054;
    L_0x004c:
        r5 = 76;
        b0425Х042504250425Х = r5;
        r5 = 64;
        bХ0425042504250425Х = r5;
    L_0x0054:
        r4.delete();
    L_0x0057:
        r0 = r0 + 1;
        goto L_0x0012;
    L_0x005a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.FileManager.deleteHapticStorage():void");
    }

    public File getHapticStorageFile(String str) {
        try {
            String path = this.bн043D043Dн043D043D.getFilesDir().getPath();
            try {
                StringBuilder append = new StringBuilder().append(getUniqueFileName(str)).append(HAPTIC_STORAGE_FILENAME);
                int i = b0425Х042504250425Х;
                switch ((i * (b04250425042504250425Х + i)) % b04250425ХХХ0425()) {
                    case 0:
                        break;
                    default:
                        b0425Х042504250425Х = b0425ХХХХ0425();
                        bХ0425042504250425Х = 18;
                        break;
                }
                return new File(path, append.toString());
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public String getInternalHapticPath() {
        int i = b0425Х042504250425Х;
        switch ((i * (b04250425042504250425Х + i)) % bХХХХХ0425) {
            case 0:
                break;
            default:
                b0425Х042504250425Х = 81;
                bХ0425042504250425Х = 61;
                break;
        }
        try {
            try {
                return this.bн043D043Dн043D043D.getFilesDir().getAbsolutePath();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getUniqueFileName(java.lang.String r9) {
        /*
        r8 = this;
        r7 = 1;
        r6 = 0;
        r0 = "MD5";
        r0 = java.security.MessageDigest.getInstance(r0);	 Catch:{ Exception -> 0x005b }
        r0.reset();	 Catch:{ Exception -> 0x005b }
        r1 = r9.getBytes();	 Catch:{ Exception -> 0x005b }
        r2 = 0;
        r3 = r9.length();	 Catch:{ Exception -> 0x005b }
        r0.update(r1, r2, r3);	 Catch:{ Exception -> 0x005b }
        r1 = "%032x_%d";
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x005b }
        r3 = 0;
        r4 = new java.math.BigInteger;	 Catch:{ Exception -> 0x005b }
        r5 = 1;
        r0 = r0.digest();	 Catch:{ Exception -> 0x005b }
        r4.<init>(r5, r0);	 Catch:{ Exception -> 0x005b }
        r2[r3] = r4;	 Catch:{ Exception -> 0x005b }
        r0 = 1;
        r3 = b0425ХХХХ0425();
        r4 = b04250425042504250425Х;
        r3 = r3 + r4;
        r4 = b0425ХХХХ0425();
        r3 = r3 * r4;
        r4 = bХХХХХ0425;
        r3 = r3 % r4;
        r4 = bХ0425042504250425Х;
        if (r3 == r4) goto L_0x0047;
    L_0x003d:
        r3 = b0425ХХХХ0425();
        b0425Х042504250425Х = r3;
        r3 = 76;
        bХ0425042504250425Х = r3;
    L_0x0047:
        r3 = r8.b043Dн043Dн043D043D;	 Catch:{ Exception -> 0x005b }
    L_0x0049:
        switch(r7) {
            case 0: goto L_0x0049;
            case 1: goto L_0x0050;
            default: goto L_0x004c;
        };	 Catch:{ Exception -> 0x005b }
    L_0x004c:
        switch(r6) {
            case 0: goto L_0x0050;
            case 1: goto L_0x0049;
            default: goto L_0x004f;
        };	 Catch:{ Exception -> 0x005b }
    L_0x004f:
        goto L_0x004c;
    L_0x0050:
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x005b }
        r2[r0] = r3;	 Catch:{ Exception -> 0x005b }
        r0 = java.lang.String.format(r1, r2);	 Catch:{ Exception -> 0x005b }
    L_0x005a:
        return r0;
    L_0x005b:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = 0;
        goto L_0x005a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.utils.FileManager.getUniqueFileName(java.lang.String):java.lang.String");
    }

    public BufferedOutputStream makeOutputStream(String str) {
        OutputStream openFileOutput;
        Exception e;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            int available;
            byte[] bArr = new byte[1024];
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                openFileOutput = this.bн043D043Dн043D043D.openFileOutput(new String(getUniqueFileName(str) + HAPTIC_STORAGE_FILENAME), 0);
                try {
                    for (available = fileInputStream.available(); available > 0; available = fileInputStream.available()) {
                        openFileOutput.write(bArr, 0, fileInputStream.read(bArr));
                    }
                    fileInputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    try {
                        e.printStackTrace();
                        if (openFileOutput != null) {
                            bufferedOutputStream = new BufferedOutputStream(openFileOutput);
                        }
                        available = b0425Х042504250425Х;
                        switch ((available * (b04250425042504250425Х + available)) % bХХХХХ0425) {
                            case 0:
                                break;
                            default:
                                b0425Х042504250425Х = b0425ХХХХ0425();
                                bХ0425042504250425Х = 34;
                                break;
                        }
                        return bufferedOutputStream;
                    } catch (Exception e3) {
                        throw e3;
                    }
                }
            } catch (Exception e4) {
                e = e4;
                openFileOutput = null;
                e.printStackTrace();
                if (openFileOutput != null) {
                    bufferedOutputStream = new BufferedOutputStream(openFileOutput);
                }
                available = b0425Х042504250425Х;
                switch ((available * (b04250425042504250425Х + available)) % bХХХХХ0425) {
                    case 0:
                        break;
                    default:
                        b0425Х042504250425Х = b0425ХХХХ0425();
                        bХ0425042504250425Х = 34;
                        break;
                }
                return bufferedOutputStream;
            }
            if (openFileOutput != null) {
                bufferedOutputStream = new BufferedOutputStream(openFileOutput);
            }
            available = b0425Х042504250425Х;
            switch ((available * (b04250425042504250425Х + available)) % bХХХХХ0425) {
                case 0:
                    break;
                default:
                    b0425Х042504250425Х = b0425ХХХХ0425();
                    bХ0425042504250425Х = 34;
                    break;
            }
            return bufferedOutputStream;
        } catch (Exception e32) {
            throw e32;
        }
    }

    public BufferedOutputStream makeOutputStreamForStreaming(String str) {
        try {
            try {
                try {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.bн043D043Dн043D043D.openFileOutput(getUniqueFileName(str) + HAPTIC_STORAGE_FILENAME, 0));
                    if (((b0425Х042504250425Х + b04250425042504250425Х) * b0425Х042504250425Х) % bХХХХХ0425 == bХ0425ХХХ0425()) {
                        return bufferedOutputStream;
                    }
                    b0425Х042504250425Х = b0425ХХХХ0425();
                    bХ0425042504250425Х = 15;
                    return bufferedOutputStream;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e2) {
                throw e2;
            }
        } catch (Exception e22) {
            throw e22;
        }
    }
}
