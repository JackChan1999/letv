package com.flurry.sdk;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;

public class jl {
    public static boolean a() {
        return ((KeyguardManager) hn.a().c().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    @SuppressLint({"NewApi"})
    public static Point b() {
        Display defaultDisplay = ((WindowManager) hn.a().c().getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else if (VERSION.SDK_INT >= 14) {
            try {
                Method method = Display.class.getMethod("getRawHeight", new Class[0]);
                point.x = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                point.y = ((Integer) method.invoke(defaultDisplay, new Object[0])).intValue();
            } catch (Throwable th) {
                defaultDisplay.getSize(point);
            }
        } else if (VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
        } else {
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
        }
        return point;
    }

    public static DisplayMetrics c() {
        Display defaultDisplay = ((WindowManager) hn.a().c().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

    @SuppressLint({"NewApi"})
    public static DisplayMetrics d() {
        Display defaultDisplay = ((WindowManager) hn.a().c().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics;
        if (VERSION.SDK_INT >= 17) {
            displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            return displayMetrics;
        } else if (VERSION.SDK_INT < 14) {
            return c();
        } else {
            try {
                displayMetrics = new DisplayMetrics();
                Display.class.getMethod("getRealMetrics", new Class[0]).invoke(defaultDisplay, new Object[]{displayMetrics});
                return displayMetrics;
            } catch (Exception e) {
                return c();
            }
        }
    }

    public static float e() {
        return d().density;
    }

    public static int a(int i) {
        return Math.round(((float) i) / d().density);
    }

    public static int b(int i) {
        return Math.round(d().density * ((float) i));
    }

    public static int f() {
        return b().x;
    }

    public static int g() {
        return b().y;
    }

    public static int h() {
        return a(f());
    }

    public static int i() {
        return a(g());
    }

    public static int j() {
        Point b = b();
        if (b.x == b.y) {
            return 3;
        }
        if (b.x < b.y) {
            return 1;
        }
        return 2;
    }

    public static Pair<Integer, Integer> k() {
        return Pair.create(Integer.valueOf(h()), Integer.valueOf(i()));
    }

    public static Pair<Integer, Integer> c(int i) {
        int h = h();
        int i2 = i();
        switch (i) {
            case 2:
                return Pair.create(Integer.valueOf(i2), Integer.valueOf(h));
            default:
                return Pair.create(Integer.valueOf(h), Integer.valueOf(i2));
        }
    }
}
