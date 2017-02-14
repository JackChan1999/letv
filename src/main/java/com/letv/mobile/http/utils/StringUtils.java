package com.letv.mobile.http.utils;

import android.text.TextUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsNull(String str) {
        return isBlank(str) || str.equalsIgnoreCase("null");
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String md5Helper(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            StringBuilder buf = new StringBuilder("");
            for (int i : b) {
                int i2;
                if (i2 < 0) {
                    i2 += 256;
                }
                if (i2 < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i2));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSubStr(String str, int subNu, String replace) {
        int strLength = str.length();
        if (strLength >= subNu) {
            return str.substring(strLength - subNu, strLength);
        }
        for (int i = strLength; i < subNu; i++) {
            str = new StringBuilder(String.valueOf(str)).append(replace).toString();
        }
        return str;
    }

    public static String getUUIDString(String tBrand, String tSeries, String tUnique, int subNu, String replace) {
        return md5Helper(getSubStr(tBrand, subNu, replace) + getSubStr(tSeries, subNu, replace) + getSubStr(tUnique, subNu, replace));
    }

    public static String encodeStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static boolean isDigit(String strNum) {
        return Pattern.compile("[0-9]{1,}").matcher(strNum).matches();
    }

    public static String stringChangeCapital(String s) {
        if (equalsNull(s)) {
            return "";
        }
        char[] c = s.toCharArray();
        int i = 0;
        while (i < s.length()) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                c[i] = Character.toUpperCase(c[i]);
            } else if (c[i] >= 'A' && c[i] <= 'Z') {
                c[i] = Character.toLowerCase(c[i]);
            }
            i++;
        }
        return String.valueOf(c);
    }

    public static String addComma3(String str) {
        if (equalsNull(str)) {
            return "";
        }
        str = new StringBuilder(str).reverse().toString();
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if ((i * 3) + 3 > str.length()) {
                str2 = new StringBuilder(String.valueOf(str2)).append(str.substring(i * 3, str.length())).toString();
                break;
            }
            str2 = new StringBuilder(String.valueOf(str2)).append(str.substring(i * 3, (i * 3) + 3)).append(",").toString();
        }
        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        return new StringBuilder(str2).reverse().toString();
    }

    public static String handlerStr(String str, int length) {
        if (str == null || "".equals(str)) {
            return "";
        }
        if (str.length() > 15) {
            return str.substring(0, 15) + "...";
        }
        return str;
    }

    public static String change0ToNull(String str) {
        if (equalsNull(str)) {
            return str;
        }
        try {
            if (Float.valueOf(str).floatValue() - 0.0f == 0.0f) {
                return null;
            }
            return str;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
