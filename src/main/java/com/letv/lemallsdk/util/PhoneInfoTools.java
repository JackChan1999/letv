package com.letv.lemallsdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.letv.lemallsdk.LemallPlatform;
import java.util.Locale;
import java.util.TimeZone;

public class PhoneInfoTools {
    private Context context;

    private Context getOnlyContext() {
        if (this.context == null) {
            this.context = LemallPlatform.getInstance().getContext();
        }
        return this.context;
    }

    public String getDeviceCode() {
        String deviceId = ((TelephonyManager) getOnlyContext().getSystemService("phone")).getDeviceId();
        if ("000000000000000".equals(deviceId) || deviceId == null) {
            return "ThisIsaEmulator";
        }
        return deviceId;
    }

    public String getDeviceModel() {
        return Build.MODEL;
    }

    public int getOSVersion() {
        return VERSION.SDK_INT;
    }

    public String getOSVersionSDK() {
        return VERSION.SDK;
    }

    public String getAppVersionName() {
        String mAppVersion = "";
        try {
            PackageInfo info = getOnlyContext().getPackageManager().getPackageInfo(getOnlyContext().getPackageName(), 0);
            if (info == null || TextUtils.isEmpty(info.versionName)) {
                return mAppVersion;
            }
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return mAppVersion;
        }
    }

    public String getNetType() {
        String netType = "";
        NetworkInfo info = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null) {
            return netType;
        }
        int nType = info.getType();
        if (nType == 0) {
            switch (info.getSubtype()) {
                case 1:
                    netType = "GPRS";
                    break;
                case 2:
                    netType = "EDGE";
                    break;
                case 3:
                    netType = "UMTS";
                    break;
                case 4:
                    netType = "CDMA";
                    break;
                case 5:
                    netType = "EVDO0";
                    break;
                case 6:
                    netType = "EVDOA";
                    break;
                case 8:
                    netType = "HSDPA";
                    break;
                case 9:
                    netType = "HSUPA";
                    break;
                case 10:
                    netType = "HSPA";
                    break;
                default:
                    netType = "其他网络";
                    break;
            }
        } else if (nType == 1) {
            netType = "NETTYPE_WIFI";
        }
        return netType;
    }

    public String getChannel() {
        String CHANNELID = "";
        try {
            return getOnlyContext().getPackageManager().getApplicationInfo(getOnlyContext().getPackageName(), 128).metaData.get("UMENG_CHANNEL");
        } catch (Exception e) {
            return "2000";
        }
    }

    public String getTimezone() {
        return TimeZone.getDefault().getID();
    }

    public String getCountry() {
        return Locale.getDefault().getCountry();
    }

    public String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public String getOSVersionName(int sdkInt) {
        String Name = "";
        switch (sdkInt) {
            case 11:
                return "HONEYCOMB";
            case 12:
                return "HONEYCOMB_MR1";
            case 13:
                return "HONEYCOMB_MR2";
            case 14:
                return "ICE_CREAM_SANDWICH";
            case 15:
                return "ICE_CREAM_SANDWICH_MR1";
            case 16:
                return "JELLY_BEAN";
            case 17:
                return "JELLY_BEAN_MR1";
            case 18:
                return "JELLY_BEAN_MR2";
            case 19:
                return "KITKAT";
            case 20:
                return "KITKAT_WATCH";
            case 21:
                return "LOLLIPOP";
            default:
                return "";
        }
    }
}
