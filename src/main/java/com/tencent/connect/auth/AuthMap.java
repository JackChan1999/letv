package com.tencent.connect.auth;

import com.tencent.tauth.IUiListener;
import java.util.HashMap;

/* compiled from: ProGuard */
public class AuthMap {
    static final /* synthetic */ boolean a;
    private static int b = 0;
    public static AuthMap sInstance;
    public final String KEY_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public HashMap<String, Auth> authMap = new HashMap();

    /* compiled from: ProGuard */
    public static class Auth {
        public AuthDialog dialog;
        public String key;
        public IUiListener listener;
    }

    static {
        boolean z;
        if (AuthMap.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        a = z;
    }

    public static AuthMap getInstance() {
        if (sInstance == null) {
            sInstance = new AuthMap();
        }
        return sInstance;
    }

    public Auth get(String str) {
        return (Auth) this.authMap.get(str);
    }

    public static int getSerial() {
        int i = b + 1;
        b = i;
        return i;
    }

    public String set(Auth auth) {
        int serial = getSerial();
        try {
            this.authMap.put("" + serial, auth);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return "" + serial;
    }

    public void remove(String str) {
        this.authMap.remove(str);
    }

    public String makeKey() {
        int ceil = (int) Math.ceil((Math.random() * 20.0d) + 3.0d);
        char[] toCharArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = toCharArray.length;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ceil; i++) {
            stringBuffer.append(toCharArray[(int) (Math.random() * ((double) length))]);
        }
        return stringBuffer.toString();
    }

    public String decode(String str, String str2) {
        return a(str, str2);
    }

    private String a(String str, String str2) {
        int i = 0;
        if (a || str.length() % 2 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            int length = str2.length();
            int length2 = str.length() / 2;
            int i2 = 0;
            while (i < length2) {
                stringBuilder.append((char) (Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16) ^ str2.charAt(i2)));
                i2 = (i2 + 1) % length;
                i++;
            }
            return stringBuilder.toString();
        }
        throw new AssertionError();
    }
}
