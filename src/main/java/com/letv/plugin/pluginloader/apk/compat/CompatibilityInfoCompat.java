package com.letv.plugin.pluginloader.apk.compat;

import com.letv.plugin.pluginloader.apk.utils.FieldUtils;

public class CompatibilityInfoCompat {
    private static Class sClass;
    private static Object sDefaultCompatibilityInfo;

    private static Class getMyClass() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.content.res.CompatibilityInfo");
        }
        return sClass;
    }

    public static Object DEFAULT_COMPATIBILITY_INFO() throws IllegalAccessException, ClassNotFoundException {
        if (sDefaultCompatibilityInfo == null) {
            sDefaultCompatibilityInfo = FieldUtils.readStaticField(getMyClass(), "DEFAULT_COMPATIBILITY_INFO");
        }
        return sDefaultCompatibilityInfo;
    }
}
