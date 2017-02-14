package com.letv.hotfixlib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.X509EncodedKeySpec;
import java.util.jar.Manifest;
import javax.crypto.Cipher;

public class CheckPatch {
    private static final String MD5 = "MD5";
    private static final String PATCH_FILE = "Patch-File";
    private static final String PATCH_MF = "patch.mf";
    private static final String PATCH_VERSION = "Patch-Version";
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx4ET7nyFO5e9/Wr0lV0xgeky8Ur45EwUPbmXP\nHmraIvK1bsQLmAoU2mjVH+Uo7uzAQWXExB0OzAyqR2tBcs0OQ6oFmp30xhelnMdfHk06f+ldjdmv\nndbeWrTMzoQmH6C3PiiupyU6AmqlVVmt98zS/pUU7Qnz4U0kZMQIqQSQhwIDAQAB";
    private static final String TARGET_APK_VERSION_NAME = "Target-Apk-Version-Name";
    private File mPatchFile;

    public boolean verify(Context context, File patchPath) {
        if (!(context == null || patchPath == null)) {
            try {
                if (patchPath.isDirectory()) {
                    File patchFM = new File(patchPath, PATCH_MF);
                    if (!patchFM.exists()) {
                        return false;
                    }
                    Manifest mf = new Manifest(new FileInputStream(patchFM));
                    String targetVersion = mf.getMainAttributes().getValue(TARGET_APK_VERSION_NAME);
                    if (targetVersion == null) {
                        return false;
                    }
                    if (!targetVersion.equals(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName)) {
                        return false;
                    }
                    String patchFileString = mf.getMainAttributes().getValue(PATCH_FILE);
                    if (TextUtils.isEmpty(patchFileString)) {
                        return false;
                    }
                    File patchFile = new File(patchPath, patchFileString);
                    if (!patchFile.exists()) {
                        return false;
                    }
                    String encryptedMd5FileString = mf.getMainAttributes().getValue("MD5");
                    if (TextUtils.isEmpty(encryptedMd5FileString)) {
                        return false;
                    }
                    File encryptedMd5File = new File(patchPath, encryptedMd5FileString);
                    if (!encryptedMd5File.exists()) {
                        return false;
                    }
                    String originalMd5 = decrypt(PUBLIC_KEY, decryptBASE64(readFileData(encryptedMd5File)));
                    String patchMD5 = getMD5ForFile(patchFile);
                    this.mPatchFile = patchFile;
                    return patchMD5.equals(originalMd5);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private String readFileData(File file) {
        Exception e;
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(file);
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer);
            String res2 = new String(buffer, "UTF-8");
            try {
                fin.close();
                return res2;
            } catch (Exception e2) {
                e = e2;
                res = res2;
                e.printStackTrace();
                return res;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return res;
        }
    }

    private String getMD5ForFile(File patch) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(patch);
        byte[] dataBytes = new byte[1024];
        while (true) {
            int count = fis.read(dataBytes);
            if (count == -1) {
                break;
            }
            md.update(dataBytes, 0, count);
        }
        byte[] mdBytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte mdByte : mdBytes) {
            sb.append(Integer.toString((mdByte & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    private String decrypt(String key, byte[] src) throws Exception {
        Key publicKey = getPublicKey(key);
        if (publicKey == null || src == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(2, publicKey);
        return new String(cipher.doFinal(src), "UTF-8");
    }

    private Key getPublicKey(String publicKey) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decryptBASE64(publicKey)));
    }

    private byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, 0);
    }

    public File getPatchFile() {
        return this.mPatchFile;
    }
}
