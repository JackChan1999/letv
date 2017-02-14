package com.coolcloud.uac.android.common;

import com.coolcloud.uac.android.common.util.FLOG;
import com.coolcloud.uac.android.common.util.TextUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Config {
    public static Set<String> BANAPPID = new HashSet();
    public static Map<String, String> CURRENTAPP = new HashMap();
    private static final String TAG = "Config";
    private static String UACBRAND = "qiku";

    public static String getUacBrand() {
        return UACBRAND;
    }

    public static void setUacBrand(String uacBrand) {
        if (TextUtils.equal(uacBrand, "coolpad")) {
            UACBRAND = "coolpad";
        } else if (TextUtils.equal(uacBrand, "qiku")) {
            UACBRAND = "qiku";
        } else {
            FLOG.e(TAG, "setUacBrand error (unknown Brand)");
        }
    }
}
