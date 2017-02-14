package com.letv.android.client.dlna.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetUtil {
    public static boolean isNetAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    public static boolean isWIFIActivate(Context context) {
        return ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
    }

    public static void changeWIFIStatus(Context context, boolean status) {
        ((WifiManager) context.getSystemService("wifi")).setWifiEnabled(status);
    }
}
