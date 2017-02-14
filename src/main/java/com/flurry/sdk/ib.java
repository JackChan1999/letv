package com.flurry.sdk;

import android.text.TextUtils;
import android.util.Log;

public final class ib {
    private static int a = 4000;
    private static boolean b = false;
    private static int c = 5;
    private static boolean d = false;

    public static void a() {
        b = true;
    }

    public static void b() {
        b = false;
    }

    public static int c() {
        return c;
    }

    public static boolean d() {
        return d;
    }

    public static void a(int i) {
        c = i;
    }

    public static void a(boolean z) {
        d = z;
    }

    public static void a(String str, String str2) {
        b(3, str, str2);
    }

    public static void a(String str, String str2, Throwable th) {
        b(6, str, str2, th);
    }

    public static void b(String str, String str2) {
        b(6, str, str2);
    }

    public static void c(String str, String str2) {
        b(4, str, str2);
    }

    public static void d(String str, String str2) {
        b(2, str, str2);
    }

    public static void e(String str, String str2) {
        b(5, str, str2);
    }

    public static void a(int i, String str, String str2, Throwable th) {
        c(i, str, str2, th);
    }

    public static void a(int i, String str, String str2) {
        c(i, str, str2);
    }

    private static void b(int i, String str, String str2, Throwable th) {
        b(i, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    private static void b(int i, String str, String str2) {
        if (!b && c <= i) {
            d(i, str, str2);
        }
    }

    private static void c(int i, String str, String str2, Throwable th) {
        c(i, str, str2 + '\n' + Log.getStackTraceString(th));
    }

    private static void c(int i, String str, String str2) {
        if (d) {
            d(i, str, str2);
        }
    }

    private static void d(int i, String str, String str2) {
        if (!d) {
            str = "FlurryAgent";
        }
        int length = TextUtils.isEmpty(str2) ? 0 : str2.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = a > length - i2 ? length : a + i2;
            if (Log.println(i, str, str2.substring(i2, i3)) > 0) {
                i2 = i3;
            } else {
                return;
            }
        }
    }
}
