package com.coolcloud.uac.android.common.stat;

import com.coolcloud.uac.android.common.util.LOG;
import java.lang.reflect.InvocationTargetException;

public class DataEyeUtils {
    public static final String TAG = "DataEyeUtils";

    public static void report(String ClssStr, String mMethodStr) {
        try {
            Class.forName(ClssStr).getMethod(mMethodStr, new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException e) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "] invoke failed(ClassNotFoundException) " + e);
        } catch (NoSuchMethodException e2) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "] invoke failed(NoSuchMethodException) " + e2);
        } catch (IllegalAccessException e3) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "] invoke failed(IllegalAccessException) " + e3);
        } catch (IllegalArgumentException e4) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "] invoke failed(IllegalArgumentException) " + e4);
        } catch (InvocationTargetException e5) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "] invoke failed(InvocationTargetException) " + e5);
        } catch (Throwable e6) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "] invoke failed(Throwable) " + e6);
        }
    }

    public static void report1Param(String ClssStr, String mMethodStr, Class<?> mParamClass, Object param) {
        try {
            Class.forName(ClssStr).getMethod(mMethodStr, new Class[]{mParamClass}).invoke(null, new Object[]{param});
        } catch (ClassNotFoundException e) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + mParamClass + "][param:" + param + "] invoke failed(ClassNotFoundException) " + e);
        } catch (NoSuchMethodException e2) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + mParamClass + "][param:" + param + "] invoke failed(NoSuchMethodException) " + e2);
        } catch (IllegalAccessException e3) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + mParamClass + "][param:" + param + "] invoke failed(IllegalAccessException) " + e3);
        } catch (IllegalArgumentException e4) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + mParamClass + "][param:" + param + "] invoke failed(IllegalArgumentException) " + e4);
        } catch (InvocationTargetException e5) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + mParamClass + "][param:" + param + "] invoke failed(InvocationTargetException) " + e5);
        } catch (Throwable e6) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + mParamClass + "][param:" + param + "] invoke failed(Throwable) " + e6);
        }
    }

    public static void report2Param(String ClssStr, String mMethodStr, Class<?> mParam1Class, Object param1, Class<?> mParam2Class, Object param2) {
        try {
            Class.forName(ClssStr).getMethod(mMethodStr, new Class[]{mParam1Class, mParam2Class}).invoke(null, new Object[]{param1, param2});
        } catch (ClassNotFoundException e) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "] invoke failed(ClassNotFoundException) " + e);
        } catch (NoSuchMethodException e2) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "] invoke failed(NoSuchMethodException) " + e2);
        } catch (IllegalAccessException e3) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "] invoke failed(IllegalAccessException) " + e3);
        } catch (IllegalArgumentException e4) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "] invoke failed(IllegalArgumentException) " + e4);
        } catch (InvocationTargetException e5) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "] invoke failed(InvocationTargetException) " + e5);
        } catch (Throwable e6) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "] invoke failed(Throwable) " + e6);
        }
    }

    public static void report3Param(String ClssStr, String mMethodStr, Class<?> mParam1Class, Object param1, Class<?> mParam2Class, Object param2, Class<?> mParam3Class, Object param3) {
        try {
            Class.forName(ClssStr).getMethod(mMethodStr, new Class[]{mParam1Class, mParam2Class, mParam3Class}).invoke(null, new Object[]{param1, param2, param3});
        } catch (ClassNotFoundException e) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "][mParam3Class:" + mParam3Class + "][param3:" + param3 + "] invoke failed(ClassNotFoundException) " + e);
        } catch (NoSuchMethodException e2) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "][mParam3Class:" + mParam3Class + "][param3:" + param3 + "] invoke failed(NoSuchMethodException) " + e2);
        } catch (IllegalAccessException e3) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "][mParam3Class:" + mParam3Class + "][param3:" + param3 + "] invoke failed(IllegalAccessException) " + e3);
        } catch (IllegalArgumentException e4) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "][mParam3Class:" + mParam3Class + "][param3:" + param3 + "] invoke failed(IllegalArgumentException) " + e4);
        } catch (InvocationTargetException e5) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "][mParam3Class:" + mParam3Class + "][param3:" + param3 + "] invoke failed(InvocationTargetException) " + e5);
        } catch (Throwable e6) {
            LOG.w(TAG, "[class:" + ClssStr + "][methods:" + mMethodStr + "][mParamClass:" + "][mParam1Class:" + mParam1Class + "][param1:" + param1 + "][mParam2Class:" + mParam2Class + "][param2:" + param2 + "][mParam3Class:" + mParam3Class + "][param3:" + param3 + "] invoke failed(Throwable) " + e6);
        }
    }

    public static Object getField(String ClssStr, String mFieldStr) {
        Object obj = null;
        try {
            obj = Class.forName(ClssStr).getField(mFieldStr).get(null);
        } catch (NoSuchFieldException e) {
            LOG.w(TAG, "[class:" + ClssStr + "][field:" + mFieldStr + "] invoke failed(NoSuchFieldException) " + e);
        } catch (ClassNotFoundException e2) {
            LOG.w(TAG, "[class:" + ClssStr + "][field:" + mFieldStr + "] invoke failed(ClassNotFoundException) " + e2);
        } catch (IllegalAccessException e3) {
            LOG.w(TAG, "[class:" + ClssStr + "][field:" + mFieldStr + "] invoke failed(IllegalAccessException) " + e3);
        } catch (IllegalArgumentException e4) {
            LOG.w(TAG, "[class:" + ClssStr + "][field:" + mFieldStr + "] invoke failed(IllegalArgumentException) " + e4);
        } catch (Throwable e5) {
            LOG.w(TAG, "[class:" + ClssStr + "][field:" + mFieldStr + "] invoke failed(Throwable) " + e5);
        }
        return obj;
    }
}
