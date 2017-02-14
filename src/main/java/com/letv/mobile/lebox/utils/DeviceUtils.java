package com.letv.mobile.lebox.utils;

import android.os.Build;
import android.telephony.TelephonyManager;
import com.letv.mobile.http.utils.ContextProvider;
import com.letv.mobile.http.utils.MD5Util;
import com.letv.mobile.http.utils.StringUtils;
import com.letv.mobile.lebox.LeBoxApp;

public class DeviceUtils {
    private static final String LETV_BRAND_NAME = "Letv";

    public static boolean isOtherDevice() {
        return !isLetvDevice();
    }

    public static boolean isLetvDevice() {
        return LETV_BRAND_NAME.equalsIgnoreCase(Build.BRAND);
    }

    public static String getDeviceId() {
        String imeiId = getDeviceIMEI();
        if (!StringUtils.equalsNull(imeiId)) {
            return imeiId;
        }
        String mac = SystemUtil.getMacAddress();
        if (StringUtils.equalsNull(mac)) {
            return "";
        }
        return MD5Util.MD5(mac);
    }

    public static String getDevId() {
        String imei = getDeviceIMEI();
        StringBuilder sb = new StringBuilder();
        sb.append("&").append("devid=");
        if (StringUtils.equalsNull(imei)) {
            String mac = SystemUtil.getMacAddress();
            if (StringUtils.equalsNull(mac)) {
                return sb.toString();
            }
            sb.append("mac");
            sb.append(mac);
            return sb.toString();
        }
        sb.append("imei").append(imei);
        return sb.toString();
    }

    public static String getDeviceIMEI() {
        if (ContextProvider.getApplicationContext() == null) {
            ContextProvider.initIfNotInited(LeBoxApp.getApplication());
        }
        if (ContextProvider.getApplicationContext() == null) {
            return "";
        }
        String deviceId = ((TelephonyManager) ContextProvider.getApplicationContext().getSystemService("phone")).getDeviceId();
        return (deviceId == null || deviceId.length() <= 0) ? "" : deviceId;
    }
}
