package com.coolcloud.uac.android.common.util;

import android.content.ComponentName;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class TextUtils {
    public static boolean isNumberic(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static boolean isAscii(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '\u0000' || c > '') {
                return false;
            }
        }
        return true;
    }

    public static boolean equal(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 != null && str2 == null) {
            return false;
        }
        if (str1 != null || str2 == null) {
            return str1.equals(str2);
        }
        return false;
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 != null && str2 == null) {
            return false;
        }
        if (str1 != null || str2 == null) {
            return str1.equalsIgnoreCase(str2);
        }
        return false;
    }

    public static boolean isPhone(String phone) {
        if (isEmpty(phone) || phone.length() < 11 || phone.contains("@")) {
            return false;
        }
        return true;
    }

    public static boolean isEmail(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String mode = "";
        if (str.indexOf(64) == 1) {
            mode = "^[a-z0-9A-Z]+\\@[a-z0-9A-Z]+[.]{1}[a-z0-9A-Z]+\\w*[.]*\\w*[a-zA-Z]+$";
        } else {
            mode = "^[a-z0-9A-Z]+[-+._a-z0-9A-Z]*[a-z0-9A-Z]+\\@[a-z0-9A-Z]+[.]{1}[a-z0-9A-Z]+\\w*[.]*\\w*[a-zA-Z]+$";
        }
        return Pattern.compile(mode).matcher(str).find();
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() <= 0;
    }

    public static boolean isEmpty(String... s) {
        for (String isEmpty : s) {
            if (isEmpty(isEmpty)) {
                return true;
            }
        }
        return false;
    }

    public static boolean empty(String s) {
        return s == null || s.length() <= 0;
    }

    public static String nowTime() {
        String pattern = "yyyyMMddHHmmss";
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }

    public static String a2s(String[] texts) {
        if (texts == null || texts.length <= 0) {
            return "";
        }
        boolean first = true;
        StringBuffer sb = new StringBuffer();
        for (String text : texts) {
            if (first) {
                first = false;
            } else {
                sb.append(";");
            }
            sb.append(text);
        }
        return sb.toString();
    }

    public static boolean contains(String s, String sub) {
        if (isEmpty(s)) {
            return false;
        }
        return s.contains(sub);
    }

    public static int compare(String s1, String s2) {
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }
        return s1.compareTo(s2);
    }

    public static String trim(String s) {
        return !empty(s) ? s.trim() : s;
    }

    public static String getShortClassName(Intent intent) {
        if (intent == null) {
            return "";
        }
        ComponentName mComponentName = intent.getComponent();
        if (mComponentName == null) {
            String mString = intent.getAction();
            if (isEmpty(mString)) {
                return "";
            }
            return mString;
        }
        String mPackageClassName = mComponentName.getClassName();
        if (isEmpty(mPackageClassName)) {
            return "";
        }
        return mPackageClassName.substring(mPackageClassName.lastIndexOf(".") + 1);
    }
}
