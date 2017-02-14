package com.immersion.aws.pm;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {
    public static int b044404440444ф0444ф = 2;
    public static int b0444ф0444ф0444ф = 0;
    public static int bф04440444ф0444ф = 1;
    public static int bфф0444ф0444ф = 48;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Utilities() {
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
        r2.<init>();
        r0 = bфф0444ф0444ф;
        r1 = bф04440444ф0444ф;
        r0 = r0 + r1;
        r1 = bфф0444ф0444ф;
        r0 = r0 * r1;
        r1 = b044404440444ф0444ф;
        r0 = r0 % r1;
        r1 = b0444ф0444ф0444ф;
        if (r0 == r1) goto L_0x0023;
    L_0x001b:
        r0 = 8;
        bфф0444ф0444ф = r0;
        r0 = 97;
        b0444ф0444ф0444ф = r0;
    L_0x0023:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.Utilities.<init>():void");
    }

    public static int b0444фф04440444ф() {
        return 79;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b0449044904490449щ0449(java.lang.String r3) {
        /*
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
        r0 = "MD5";
        r0 = java.security.MessageDigest.getInstance(r0);	 Catch:{ NoSuchAlgorithmException -> 0x0036 }
        r1 = r3.getBytes();
        r0.update(r1);
        r1 = bфф0444ф0444ф;
        r2 = bф04440444ф0444ф;
        r1 = r1 + r2;
        r2 = bфф0444ф0444ф;
        r1 = r1 * r2;
        r2 = b044404440444ф0444ф;
        r1 = r1 % r2;
        r2 = b0444ф0444ф0444ф;
        if (r1 == r2) goto L_0x002d;
    L_0x0025:
        r1 = 37;
        bфф0444ф0444ф = r1;
        r1 = 44;
        b0444ф0444ф0444ф = r1;
    L_0x002d:
        r0 = r0.digest();
        r0 = bщ044904490449щ0449(r0);
    L_0x0035:
        return r0;
    L_0x0036:
        r0 = move-exception;
        r0 = "";
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.aws.pm.Utilities.b0449044904490449щ0449(java.lang.String):java.lang.String");
    }

    public static int bффф04440444ф() {
        return 0;
    }

    public static String bщ044904490449щ0449(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            if (((bфф0444ф0444ф + bф04440444ф0444ф) * bфф0444ф0444ф) % b044404440444ф0444ф != bффф04440444ф()) {
                bфф0444ф0444ф = b0444фф04440444ф();
                b0444ф0444ф0444ф = 36;
            }
            stringBuilder.append(String.format("%02x", new Object[]{Integer.valueOf(b & 255)}));
        }
        return stringBuilder.toString();
    }

    public static String bщщщщ04490449(File file) {
        String str = "";
        byte[] bArr = new byte[1024];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                instance.update(bArr, 0, read);
            }
            str = bщ044904490449щ0449(instance.digest());
            fileInputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        } catch (NoSuchAlgorithmException e3) {
        }
        return str;
    }
}
