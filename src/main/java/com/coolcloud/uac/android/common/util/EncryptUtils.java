package com.coolcloud.uac.android.common.util;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class EncryptUtils {
    private static final String TAG = "EncryptUtils";
    private static Cipher cipher;
    private static MessageDigest messageDigest;

    static {
        messageDigest = null;
        cipher = null;
        try {
            messageDigest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            if (messageDigest == null) {
                LOG.e(TAG, "get message digest failed");
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.e(TAG, "get message digest failed(NoSuchAlgorithmException)", e);
            messageDigest = null;
        }
        try {
            cipher = Cipher.getInstance("DES");
            if (cipher == null) {
                LOG.e(TAG, "get cipher failed");
            }
        } catch (NoSuchPaddingException e2) {
            LOG.e(TAG, "get cipher failed(NoSuchPaddingException)", e2);
            cipher = null;
        } catch (NoSuchAlgorithmException e3) {
            LOG.e(TAG, "get cipher failed(NoSuchAlgorithmException)", e3);
            cipher = null;
        }
    }

    public static String getMD5String(String str) {
        if (TextUtils.isEmpty(str)) {
            LOG.i(TAG, "[str:" + str + "] input parameter is empty");
            return "";
        } else if (messageDigest == null) {
            LOG.e(TAG, "[str:" + str + "] the object of message digest is null");
            return "";
        } else {
            try {
                messageDigest.reset();
                messageDigest.update(str.getBytes("UTF-8"));
                byte[] bytes = messageDigest.digest();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    if (Integer.toHexString(bytes[i] & 255).length() == 1) {
                        sb.append("0").append(Integer.toHexString(bytes[i] & 255));
                    } else {
                        sb.append(Integer.toHexString(bytes[i] & 255));
                    }
                }
                return sb.toString();
            } catch (UnsupportedEncodingException e) {
                LOG.e(TAG, "[str:" + str + "] generate MD5 failed(UnsupportedEncodingException)", e);
                return "";
            }
        }
    }
}
