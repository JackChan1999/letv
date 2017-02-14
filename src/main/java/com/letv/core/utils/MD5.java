package com.letv.core.utils;

import com.letv.datastatistics.util.DataConstant.PAGE;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", PAGE.MYSHARE, "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (byte byteToHexString : b) {
            resultSb.append(byteToHexString(byteToHexString));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < (byte) 0) {
            n += 256;
        }
        return hexDigits[n / 16] + hexDigits[n % 16];
    }

    public static String MD5Encode(String origin) {
        try {
            String resultString = new String(origin);
            try {
                return byteArrayToHexString(MessageDigest.getInstance(CommonUtils.MD5_INSTANCE).digest(resultString.getBytes()));
            } catch (Exception e) {
                return resultString;
            }
        } catch (Exception e2) {
            return null;
        }
    }

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
