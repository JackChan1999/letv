package com.letv.component.player.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtil {
    private static final String DECODE_CAPABILITY = "decode_capability";
    private static final String ERROR_CODE = "error_code";
    private static final String FIRST_DECODE = "first_deocode";
    private static final String LOCAL_CAPCITY = "local_capcity";
    private static final String REQUEST_PARAMS = "request_params";
    private static final String REQUEST_RESULT = "request_result";
    private static final String SDK_VERSION_CODE = "sdk_version_code";

    public static SharedPreferences getDefault(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Editor getEditor(Context context) {
        return getDefault(context).edit();
    }

    public static boolean isFirsSoftDecode(Context context, String type) {
        return getDefault(context).getBoolean(type, true);
    }

    public static void setFirsSoftDecode(Context context, String type) {
        getEditor(context).putBoolean(type, false).commit();
    }

    public static int getFirsHardDecode(Context context) {
        return getDefault(context).getInt(FIRST_DECODE, -1);
    }

    public static void setFirsHardDecode(Context context, int type) {
        getEditor(context).putInt(FIRST_DECODE, type).commit();
    }

    public static void removeFirsHardDecode(Context context) {
        getEditor(context).remove(FIRST_DECODE).commit();
    }

    public static void setDecodeCapability(Context context, int capability, int adaptered) {
        getEditor(context).putString(DECODE_CAPABILITY, new StringBuilder(String.valueOf(capability)).append(",").append(adaptered).toString()).commit();
    }

    public static String getDecodeCapability(Context context) {
        return getDefault(context).getString(DECODE_CAPABILITY, "");
    }

    public static void setVersionCode(Context context, float versionCode) {
        getEditor(context).putFloat(SDK_VERSION_CODE, versionCode).commit();
    }

    public static float getVersionCode(Context context) {
        return getDefault(context).getFloat(SDK_VERSION_CODE, 0.0f);
    }

    public static void setErrorCode(Context context, String errorCode) {
        getEditor(context).putString("error_code", errorCode).commit();
    }

    public static String getErrorCode(Context context) {
        return getDefault(context).getString("error_code", "");
    }

    public static void setQuestParams(Context context, String params) {
        getEditor(context).putString(REQUEST_PARAMS, params).commit();
    }

    public static String getQuestParams(Context context) {
        return getDefault(context).getString(REQUEST_PARAMS, "");
    }

    public static void setQuestResult(Context context, String result) {
        getEditor(context).putString(REQUEST_RESULT, result).commit();
    }

    public static String getQuestResult(Context context) {
        return getDefault(context).getString(REQUEST_RESULT, "");
    }

    public static void setLocalCapcity(Context context, String localCapcity) {
        getEditor(context).putString(LOCAL_CAPCITY, localCapcity).commit();
    }

    public static String getLocalCapcity(Context context) {
        return getDefault(context).getString(LOCAL_CAPCITY, "");
    }
}
