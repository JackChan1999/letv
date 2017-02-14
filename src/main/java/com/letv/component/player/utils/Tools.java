package com.letv.component.player.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.letv.component.utils.MD5;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_4G = 4;
    public static final int NETTYPE_NO = 0;
    public static final int NETTYPE_WIFI = 1;

    public static boolean isWiFiConnected(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService("wifi");
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        if (!mWifiManager.isWifiEnabled() || ipAddress == 0) {
            return false;
        }
        return true;
    }

    public static boolean isMobileConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
        connMgr.getActiveNetworkInfo();
        NetworkInfo networkInfo = connMgr.getNetworkInfo(0);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
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
            case 13:
                return 4;
            default:
                return 3;
        }
    }

    public static String getNetTypeName(Context context) {
        String net = "无网";
        int type = getNetType(context);
        if (type == 1) {
            return "wifi";
        }
        if (type == 4) {
            return "4G";
        }
        if (type == 3) {
            return "3G";
        }
        if (type == 2) {
            return "2G";
        }
        if (type == 0) {
            return "无网";
        }
        return net;
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDeviceName() {
        String model = Build.MODEL;
        if (model == null || model.length() <= 0) {
            return "";
        }
        return model;
    }

    public static String getBrandName() {
        String brand = Build.BRAND;
        if (brand == null || brand.length() <= 0) {
            return "";
        }
        return brand;
    }

    public static String getOSVersionName() {
        return VERSION.RELEASE;
    }

    public static String generateDeviceId(Context context) {
        return MD5.toMd5(getIMEI(context) + getIMSI(context) + getDeviceName() + getBrandName() + getMacAddress(context));
    }

    public static String getUUID(Context context) {
        return generateDeviceId(context) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + System.currentTimeMillis();
    }

    public static String getIMEI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (deviceId == null || deviceId.length() <= 0) {
                return "";
            }
            return deviceId.replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIMSI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            if (subscriberId == null || subscriberId.length() <= 0) {
                return generate_DeviceId(context);
            }
            subscriberId.replace(" ", "");
            if (TextUtils.isEmpty(subscriberId)) {
                return generate_DeviceId(context);
            }
            return subscriberId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generate_DeviceId(Context context) {
        return MD5.toMd5(getIMEI(context) + getDeviceName() + getBrandName() + getMacAddress(context));
    }

    public static String getMacAddress(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null || macAddress.length() <= 0) {
                return "";
            }
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getResolution(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels + "*" + dm.heightPixels;
    }

    public static String getCurrentDate() {
        Date currentTime = new Date();
        return new StringBuilder(String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime))).append("(").append(currentTime.getTime()).append(")").toString();
    }
}
