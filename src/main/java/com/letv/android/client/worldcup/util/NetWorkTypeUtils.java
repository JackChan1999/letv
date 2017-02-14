package com.letv.android.client.worldcup.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkTypeUtils {
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_NO = 0;
    public static final int NETTYPE_WIFI = 1;

    public static boolean isNetAvailable(Context context) {
        return getAvailableNetWorkInfo(context) == null;
    }

    public static boolean isThirdGeneration(Context context) {
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
                return false;
            default:
                return true;
        }
    }

    public static boolean isWifi(Context context) {
        NetworkInfo networkInfo = getAvailableNetWorkInfo(context);
        if (networkInfo == null || networkInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static NetworkInfo getAvailableNetWorkInfo(Context context) {
        try {
            NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
                return null;
            }
            return activeNetInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNetWorkType(Context context) {
        String netWorkType = "";
        NetworkInfo netWorkInfo = getAvailableNetWorkInfo(context);
        if (netWorkInfo == null) {
            return netWorkType;
        }
        if (netWorkInfo.getType() == 1) {
            return "1";
        }
        if (netWorkInfo.getType() != 0) {
            return netWorkType;
        }
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
            case 4:
                return "8";
            case 5:
                return "9";
            case 6:
                return "10";
            case 7:
                netWorkType = "11";
                break;
        }
        return "-1";
    }

    public static int getNetType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return 0;
        }
        if (1 == networkInfo.getType()) {
            return 1;
        }
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
                return 2;
            default:
                return 3;
        }
    }
}
