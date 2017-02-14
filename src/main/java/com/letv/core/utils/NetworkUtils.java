package com.letv.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.letv.core.BaseApplication;
import com.letv.core.constant.NetworkConstant;
import com.letv.core.db.PreferencesManager;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class NetworkUtils {
    public static final int OPERATOR_CHINA_MOBILE = 1;
    public static final int OPERATOR_CHINA_TELECOM = 3;
    public static final int OPERATOR_CHINA_UNICOM = 2;
    public static final int OPERATOR_UNKNOWN = 0;

    public static boolean isMobileNetwork() {
        if (!isNetworkAvailable() || isWifi()) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkAvailable() {
        return getAvailableNetworkInfo() != null;
    }

    public static boolean is3GNetwork() {
        switch (((TelephonyManager) BaseApplication.getInstance().getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
                return false;
            default:
                return true;
        }
    }

    public static NetworkInfo getAvailableNetworkInfo() {
        NetworkInfo ni = null;
        try {
            ni = ((ConnectivityManager) BaseApplication.getInstance().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ni == null || !ni.isAvailable()) ? null : ni;
    }

    public static int getNetworkType() {
        NetworkInfo networkInfo = ((ConnectivityManager) BaseApplication.getInstance().getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return 0;
        }
        if (1 == networkInfo.getType()) {
            return 1;
        }
        switch (((TelephonyManager) BaseApplication.getInstance().getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
                return 2;
            case 13:
                return 3;
            default:
                return 3;
        }
    }

    private static int getOperatorByNetworkNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return 0;
        }
        if (number.startsWith(NetworkConstant.NETWORK_NUMBER_46000) || number.startsWith(NetworkConstant.NETWORK_NUMBER_46002) || number.startsWith(NetworkConstant.NETWORK_NUMBER_46007)) {
            return 1;
        }
        if (number.startsWith(NetworkConstant.NETWORK_NUMBER_46001) || number.startsWith(NetworkConstant.NETWORK_NUMBER_46010)) {
            return 2;
        }
        if (number.startsWith(NetworkConstant.NETWORK_NUMBER_46003)) {
            return 3;
        }
        return 0;
    }

    public static boolean isUnicom3G(boolean isFirst) {
        boolean isUnicom3G = false;
        NetworkInfo ni = ((ConnectivityManager) BaseApplication.getInstance().getSystemService("connectivity")).getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()) {
            if (1 == ni.getType()) {
                return false;
            }
            TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getSystemService("phone");
            String networkNumber = tm.getSimOperator();
            if (TextUtils.isEmpty(networkNumber)) {
                networkNumber = tm.getSubscriberId();
            }
            if (networkNumber != null && 2 == getOperatorByNetworkNumber(networkNumber)) {
                String netTypeName = ni.getExtraInfo();
                if ("3gnet".equals(netTypeName) || "uninet".equals(netTypeName)) {
                    isUnicom3G = isFirst ? true : PreferencesManager.getInstance().isChinaUnicomSwitch();
                }
            }
        }
        LogInfo.log("unicom", "检测是否为联通3G isUnicom3G = " + isUnicom3G);
        return isUnicom3G;
    }

    public static boolean isWifi() {
        NetworkInfo networkInfo = getAvailableNetworkInfo();
        if (networkInfo == null || networkInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkConnected(Context context) {
        boolean isConnected = false;
        try {
            isConnected = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    public static boolean isProxy() {
        return !TextUtils.isEmpty(Proxy.getDefaultHost());
    }

    public static boolean checkIsProxyNet(Context context) {
        boolean isAboveICS;
        String proxyAddress;
        ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (VERSION.SDK_INT >= 14) {
            isAboveICS = true;
        } else {
            isAboveICS = false;
        }
        if (isAboveICS) {
            proxyAddress = System.getProperty("http.proxyHost");
        } else {
            proxyAddress = Proxy.getHost(context);
        }
        if (TextUtils.isEmpty(proxyAddress)) {
            return false;
        }
        return true;
    }

    public static String type2String(int type) {
        switch (type) {
            case 0:
                return "none";
            case 1:
                return "wifi";
            case 2:
                return "2G";
            case 3:
                return "3G";
            case 4:
                return "3G";
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static int getOperator() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        String imsi = "";
        if (networkInfo != null && networkInfo.isAvailable()) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            imsi = tm.getSimOperator();
            if (TextUtils.isEmpty(imsi)) {
                imsi = tm.getSubscriberId();
            }
        }
        if (TextUtils.isEmpty(imsi)) {
            return 0;
        }
        return getOperatorByNetworkNumber(imsi);
    }

    public static String getIp() {
        if (isNetworkAvailable()) {
            return isWifi() ? getWifiIp() : getMobileIp();
        } else {
            return "";
        }
    }

    public static String getWifiIp() {
        try {
            int ipAddress = ((WifiManager) BaseApplication.getInstance().getSystemService("wifi")).getConnectionInfo().getIpAddress();
            String ip = String.format(Locale.getDefault(), "%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
            Log.e("songhang", "获取 WIFI ip地址: " + ip);
            return ip;
        } catch (Exception ex) {
            Log.e("songhang", ex.getMessage());
            return "";
        }
    }

    public static String getMobileIp() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipaddress = inetAddress.getHostAddress().toString();
                        Log.e("songhang", "获取 GPRS ip地址: " + ipaddress);
                        return ipaddress;
                    }
                }
            }
            return "";
        } catch (SocketException ex) {
            Log.e("songhang", "Exception in getMobileIp Address: " + ex.toString());
            return "";
        }
    }
}
