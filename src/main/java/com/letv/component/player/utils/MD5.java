package com.letv.component.player.utils;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String toMd5(String md5Str) {
        String result = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            algorithm.reset();
            algorithm.update(md5Str.getBytes("utf-8"));
            result = toHexString(algorithm.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return result;
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (int b : bytes) {
            int b2;
            if (b2 < 0) {
                b2 += 256;
            }
            if (b2 < 16) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(b2));
        }
        return hexString.toString();
    }
}
