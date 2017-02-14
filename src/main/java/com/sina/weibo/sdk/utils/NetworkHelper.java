package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.os.EnvironmentCompat;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.List;

public class NetworkHelper {
    public static boolean hasInternetPermission(Context context) {
        if (context == null || context.checkCallingOrSelfPermission("android.permission.INTERNET") == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null || !info.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean isWifiValid(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info != null && 1 == info.getType() && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static boolean isMobileNetwork(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info != null && info != null && info.getType() == 0 && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static NetworkInfo getNetworkInfo(Context context, int networkType) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(networkType);
    }

    public static int getNetworkType(Context context) {
        if (context == null) {
            return -1;
        }
        NetworkInfo info = getActiveNetworkInfo(context);
        if (info == null) {
            return -1;
        }
        return info.getType();
    }

    public static int getWifiState(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService("wifi");
        if (wifi == null) {
            return 4;
        }
        return wifi.getWifiState();
    }

    public static DetailedState getWifiConnectivityState(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context, 1);
        return networkInfo == null ? DetailedState.FAILED : networkInfo.getDetailedState();
    }

    public static boolean wifiConnection(Context context, String wifiSSID, String password) {
        WifiManager wifi = (WifiManager) context.getSystemService("wifi");
        String strQuotationSSID = "\"" + wifiSSID + "\"";
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if (wifiInfo != null && (wifiSSID.equals(wifiInfo.getSSID()) || strQuotationSSID.equals(wifiInfo.getSSID()))) {
            return true;
        }
        List<ScanResult> scanResults = wifi.getScanResults();
        if (scanResults == null || scanResults.size() == 0) {
            return false;
        }
        for (int nAllIndex = scanResults.size() - 1; nAllIndex >= 0; nAllIndex--) {
            String strScanSSID = ((ScanResult) scanResults.get(nAllIndex)).SSID;
            if (wifiSSID.equals(strScanSSID) || strQuotationSSID.equals(strScanSSID)) {
                WifiConfiguration config = new WifiConfiguration();
                config.SSID = strQuotationSSID;
                config.preSharedKey = "\"" + password + "\"";
                config.status = 2;
                return wifi.enableNetwork(wifi.addNetwork(config), false);
            }
        }
        return false;
    }

    public static void clearCookies(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    public static String generateUA(Context ctx) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Android");
        buffer.append("__");
        buffer.append("weibo");
        buffer.append("__");
        buffer.append(CommonUtils.SDK);
        buffer.append("__");
        try {
            buffer.append(ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 16).versionName.replaceAll("\\s+", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
        } catch (Exception e) {
            buffer.append(EnvironmentCompat.MEDIA_UNKNOWN);
        }
        return buffer.toString();
    }
}
