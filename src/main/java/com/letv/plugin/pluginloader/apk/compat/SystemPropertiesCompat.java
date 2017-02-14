package com.letv.plugin.pluginloader.apk.compat;

import com.letv.plugin.pluginloader.apk.utils.MethodUtils;
import java.lang.reflect.InvocationTargetException;

public class SystemPropertiesCompat {
    private static Class<?> sClass;

    private static Class getMyClass() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.os.SystemProperties");
        }
        return sClass;
    }

    private static String getInner(String key, String defaultValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return (String) MethodUtils.invokeStaticMethod(getMyClass(), "get", key, defaultValue);
    }

    public static String get(String key, String defaultValue) {
        try {
            defaultValue = getInner(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
