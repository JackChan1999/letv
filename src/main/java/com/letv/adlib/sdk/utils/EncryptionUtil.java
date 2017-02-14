package com.letv.adlib.sdk.utils;

import android.util.Log;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
    private static final String CHAR_SET = "iso-8859-1";
    private static final String SHA1_ALGORITHM = "SHA-1";

    public static String md5(String input) {
        String result = input;
        if (!(input == null || "".equals(input))) {
            try {
                MessageDigest md = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
                md.update(input.getBytes());
                result = new BigInteger(1, md.digest()).toString(16);
                while (result.length() < 32) {
                    result = "0" + result;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String SHA1(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes(CHAR_SET), 0, text.length());
            return convertToHex(md.digest());
        } catch (Exception e) {
            Log.e("Android:SHA1", "ODIN Error generating generating SHA-1: " + e);
            return null;
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 15;
            int two_halfs = 0;
            while (true) {
                if (halfbyte < 0 || halfbyte > 9) {
                    buf.append((char) ((halfbyte - 10) + 97));
                } else {
                    buf.append((char) (halfbyte + 48));
                }
                halfbyte = data[i] & 15;
                int two_halfs2 = two_halfs + 1;
                if (two_halfs >= 1) {
                    break;
                }
                two_halfs = two_halfs2;
            }
        }
        return buf.toString();
    }
}
