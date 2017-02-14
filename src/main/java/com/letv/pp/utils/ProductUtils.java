package com.letv.pp.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;

@SuppressLint({"DefaultLocale"})
public class ProductUtils {
    public static final String C1 = "C1";
    public static final String C1A = "C1A";
    public static final String C1B = "C1B";
    public static final String C1S = "C1S";
    public static final String MAX70 = "MAX70";
    private static final String PROPERTY_PRODUCT_NAME = "ro.letv.product.name";
    private static final String PROPERTY_PRODUCT_VARIANT = "ro.letv.product.variant";
    public static final String S250 = "S250";
    public static final String S40 = "S40";
    public static final String S50 = "S50";
    public static final String X60 = "X60";
    private static String sProductName;

    public static String getProductName() {
        if (!TextUtils.isEmpty(sProductName)) {
            return sProductName;
        }
        try {
            sProductName = SystemProperties.get(PROPERTY_PRODUCT_NAME, "");
            if (TextUtils.isEmpty(sProductName)) {
                sProductName = SystemProperties.get("persist.product.letv.name", "");
            }
        } catch (Exception e) {
        } catch (Error e2) {
        }
        if (TextUtils.isEmpty(sProductName)) {
            if (Build.MODEL.toUpperCase().contains(Build.BRAND.toUpperCase())) {
                sProductName = Build.MODEL;
            } else {
                sProductName = Build.BRAND + " " + Build.MODEL;
            }
        } else if (isLetv()) {
            sProductName = "LETV " + sProductName;
        }
        sProductName = sProductName.toUpperCase();
        return sProductName;
    }

    public static boolean isMAX70() {
        return MAX70.equals(getProductName());
    }

    public static boolean isX60() {
        return X60.equals(getProductName());
    }

    public static boolean isS50() {
        return S50.equals(getProductName());
    }

    public static boolean isS40() {
        return S40.equals(getProductName());
    }

    public static boolean isC1() {
        return C1.equals(getProductName());
    }

    public static boolean isC1A() {
        return C1A.equals(getProductName());
    }

    public static boolean isC1B() {
        return C1B.equals(getProductName());
    }

    public static boolean isC1S() {
        return C1S.equals(getProductName());
    }

    public static boolean isC1Series() {
        String name = getProductName();
        return C1.equals(name) || C1A.equals(name) || C1B.equals(name) || C1S.equals(name);
    }

    public static boolean isS250() {
        String productName = getProductName();
        if (TextUtils.isEmpty(productName)) {
            return false;
        }
        if (productName.contains(S250) || productName.contains("S2-50F")) {
            return true;
        }
        return false;
    }

    public static String getProductVariant() {
        String result = "";
        try {
            result = SystemProperties.get(PROPERTY_PRODUCT_VARIANT, null).toUpperCase();
        } catch (Exception e) {
        } catch (Error e2) {
        }
        return result;
    }

    public static boolean is3DCapable() {
        String result = getProductVariant();
        return !TextUtils.isEmpty(result) && result.contains(".3D");
    }

    public static boolean isYunOS() {
        String hw = "";
        try {
            hw = SystemProperties.get("ro.yunos.hardware", null).toLowerCase();
        } catch (Exception e) {
        } catch (Error e2) {
        }
        return !TextUtils.isEmpty(hw) && hw.equals("yunos");
    }

    public static boolean isWobo() {
        return !TextUtils.isEmpty(Build.MODEL) && Build.MODEL.equals("wobo");
    }

    public static boolean ishaisi() {
        return !TextUtils.isEmpty(Build.MODEL) && Build.MODEL.equals("Hi3716CV200");
    }

    public static boolean isLetv() {
        String productName = getProductName();
        if (TextUtils.isEmpty(productName)) {
            return false;
        }
        if (productName.equals(C1) || productName.equals(C1A) || productName.equals(C1B) || productName.equals(C1S) || productName.equals(X60) || productName.equals(S50) || productName.equals(S40) || productName.equals(MAX70) || productName.contains(S250)) {
            return true;
        }
        return false;
    }
}
