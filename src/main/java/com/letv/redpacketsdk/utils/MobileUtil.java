package com.letv.redpacketsdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.letv.pp.utils.NetworkUtils;
import com.letv.redpacketsdk.RedPacketSdkManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class MobileUtil {
    public static String getIMEI(Context context) {
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

    public static String generateDeviceId(Context context) {
        return RedPacketSdkManager.getInstance().getDeviceId();
    }

    public static String getDeviceName() {
        return BaseTypeUtils.ensureStringValidate(Build.MODEL);
    }

    public static String getBrandName() {
        String brand = BaseTypeUtils.ensureStringValidate(Build.BRAND);
        if (TextUtils.isEmpty(brand)) {
            return "";
        }
        return getData(brand);
    }

    public static String getData(String data) {
        if (data == null || data.length() <= 0) {
            return NetworkUtils.DELIMITER_LINE;
        }
        return data.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    public static String getUUID(Context context) {
        return generateDeviceId(context) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + System.currentTimeMillis();
    }

    public static String getNetType(Context context) {
        String type = "network";
        if (context == null) {
            return type;
        }
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isAvailable()) {
                return "nont";
            }
            if (1 == networkInfo.getType()) {
                return "wifi";
            }
            switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                case 1:
                case 2:
                case 4:
                    return "2g";
                case 13:
                    return "4g";
                default:
                    return "3g";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return type;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context == null) {
            return false;
        }
        try {
            NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
