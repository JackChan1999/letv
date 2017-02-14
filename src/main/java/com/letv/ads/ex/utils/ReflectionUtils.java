package com.letv.ads.ex.utils;

import com.letv.adlib.sdk.utils.LogInfo;

public class ReflectionUtils {
    private static final String TAG = "ReflectionUtils";

    public static void callADVoidMethod(Object receiver, Class<?> adClass, String methodName, Class[] type, Object... args) {
        LogInfo.log(TAG, "callADVoidMethod class=" + adClass + "   methodName=" + methodName);
        try {
            adClass.getMethod(methodName, type).invoke(receiver, args);
        } catch (Exception e) {
            LogInfo.log(TAG, "callADVoidMethod e=" + e);
            e.printStackTrace();
        }
    }

    public static Object callADObjectMethod(Object receiver, Class<?> adClass, String methodName, Class[] type, Object... args) {
        LogInfo.log(TAG, "callADObjectMethod class=" + adClass + "   methodName=" + methodName);
        Object result = null;
        try {
            result = adClass.getMethod(methodName, type).invoke(receiver, args);
        } catch (Exception e) {
            LogInfo.log(TAG, "callADObjectMethod e=" + e);
            e.printStackTrace();
        }
        return result;
    }
}
