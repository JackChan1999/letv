package com.tencent.map.b;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public final class j {
    public static String a(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            String str2 = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(Integer.toHexString(b & 255)).append(str2);
            }
            str = stringBuilder.toString();
        } catch (Exception e) {
        }
        return str;
    }

    public static byte[] a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
        try {
            deflaterOutputStream.write(bArr, 0, bArr.length);
            deflaterOutputStream.finish();
            deflaterOutputStream.flush();
            deflaterOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] b(byte[] bArr) {
        int i = 0;
        if (bArr == null) {
            return null;
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
        Object obj = new byte[0];
        Object obj2 = new byte[1024];
        while (true) {
            try {
                Object obj3;
                int read = inflaterInputStream.read(obj2);
                if (read > 0) {
                    i += read;
                    obj3 = new byte[i];
                    System.arraycopy(obj, 0, obj3, 0, obj.length);
                    System.arraycopy(obj2, 0, obj3, obj.length, read);
                } else {
                    obj3 = obj;
                }
                if (read <= 0) {
                    try {
                        byteArrayInputStream.close();
                        inflaterInputStream.close();
                        return obj3;
                    } catch (IOException e) {
                        return null;
                    }
                }
                obj = obj3;
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
