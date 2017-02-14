package com.letv.plugin.pluginloader.apk.compat;

import com.letv.plugin.pluginloader.apk.utils.MethodUtils;

public class QueuedWorkCompat {
    private static Class sClass;

    private static Class Class() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.app.QueuedWork");
        }
        return sClass;
    }

    public static void waitToFinish() {
        try {
            MethodUtils.invokeStaticMethod(Class(), "waitToFinish", new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
