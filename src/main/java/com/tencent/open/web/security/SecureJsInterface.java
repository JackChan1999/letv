package com.tencent.open.web.security;

import com.tencent.open.a.b;
import com.tencent.open.a.f;

/* compiled from: ProGuard */
public class SecureJsInterface extends b {
    private static final String a = (f.d + ".SI");
    public static boolean isPWDEdit = false;
    private String b;

    public boolean customCallback() {
        return true;
    }

    public void curPosFromJS(String str) {
        f.c(a, "-->curPosFromJS: " + str);
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            f.e(a, "-->curPosFromJS number format exception.");
        }
        if (i < 0) {
            throw new RuntimeException("position is illegal.");
        }
        if (a.c) {
        }
        if (!a.b) {
            this.b = a.a;
            JniInterface.insetTextToArray(i, this.b, this.b.length());
            f.b(a, "mKey: " + this.b);
        } else if (Boolean.valueOf(JniInterface.BackSpaceChar(a.b, i)).booleanValue()) {
            a.b = false;
        }
    }

    public void isPasswordEdit(String str) {
        f.c(a, "-->is pswd edit, flag: " + str);
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            f.e(a, "-->is pswd edit exception: " + e.getMessage());
        }
        if (i != 0 && i != 1) {
            throw new RuntimeException("is pswd edit flag is illegal.");
        } else if (i == 0) {
            isPWDEdit = false;
        } else if (i == 1) {
            isPWDEdit = true;
        }
    }

    public void clearAllEdit() {
        f.c(a, "-->clear all edit.");
        try {
            JniInterface.clearAllPWD();
        } catch (Throwable e) {
            f.e(a, "-->clear all edit exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getMD5FromNative() {
        f.c(a, "-->get md5 form native");
        String str = "";
        try {
            str = JniInterface.getPWDKeyToMD5(null);
            f.b(a, "-->getMD5FromNative, MD5= " + str);
            return str;
        } catch (Throwable e) {
            f.e(a, "-->get md5 form native exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
