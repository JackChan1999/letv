package com.coolcloud.uac.android.common.util;

import android.content.Context;
import android.webkit.URLUtil;
import com.coolcloud.uac.android.common.Config;
import java.io.File;

public abstract class ValidUtils {
    private static final String TAG = "ValidUtils";

    public static boolean isAuthCodeValid(String authCode) {
        return TextUtils.isNumberic(authCode);
    }

    public static boolean isAccountValid(String account) {
        if (TextUtils.isEmpty(account)) {
            return false;
        }
        boolean isPhone = TextUtils.isPhone(account);
        boolean isEmail = TextUtils.isEmail(account);
        boolean isName = account.matches("^[a-zA-Z0-9_@]{1,32}$");
        if (isEmail || isPhone || isName) {
            return true;
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        return !TextUtils.empty(password) && password.length() >= 6 && password.length() <= 20;
    }

    public static boolean isOverMaxlenth(String password) {
        return password.length() <= 16;
    }

    public static boolean isCodeValid(String code) {
        return TextUtils.isNumberic(code);
    }

    public static boolean isUrlValid(String url) {
        return !TextUtils.empty(url) && (URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url) || url.startsWith("file:"));
    }

    public static boolean isTemporaryAccount(String user) {
        return !TextUtils.isEmpty(user) && user.startsWith("CT");
    }

    public static boolean isLoaclFileExists(String localPth) {
        try {
            File f = new File(localPth);
            if (f != null && f.exists() && f.isFile() && f.canRead()) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            LOG.e(TAG, "isFileExists error" + t);
            return false;
        }
    }

    public static String checkBrand(Context mContext) {
        String uacbrand = "coolpad";
        Config.setUacBrand(uacbrand);
        return uacbrand;
    }
}
