package com.letv.datastatistics.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DEs {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private byte[] desKey;

    public static String encode(String key, byte[] data) {
        try {
            Key secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key.getBytes()));
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(1, secretKey, new IvParameterSpec("12345678".getBytes()));
            return byteToString(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decode(String encryptText, String key) {
        try {
            SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key.getBytes()));
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(2, secretKey, new IvParameterSpec("12345678".getBytes()));
            return new String(cipher.doFinal(byte2hex(encryptText.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String byteToString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        int n = 0;
        while (b != null && n < b.length) {
            String stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
            n++;
        }
        return hs.toString().toUpperCase(Locale.CHINA);
    }

    private static byte[] byte2hex(byte[] b) {
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        byte[] b2 = new byte[(b.length / 2)];
        for (int n = 0; n < b.length; n += 2) {
            b2[n / 2] = (byte) Integer.parseInt(new String(b, n, 2), 16);
        }
        return b2;
    }

    public DEs(String desKey) {
        this.desKey = desKey.getBytes();
    }

    public byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(this.desKey));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, key, sr);
        return cipher.doFinal(plainText);
    }

    public byte[] desDecrypt(byte[] encryptText) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(this.desKey));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key, sr);
        return cipher.doFinal(encryptText);
    }

    public String encrypt(String input) throws Exception {
        return LetvBase64.encode(desEncrypt(input.getBytes()));
    }

    public String decrypt(String input) throws Exception {
        return new String(desDecrypt(LetvBase64.decode(input)));
    }
}
