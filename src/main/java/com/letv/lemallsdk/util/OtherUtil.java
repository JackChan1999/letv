package com.letv.lemallsdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class OtherUtil {
    private static final String UNKNOWN_VERSION = "versionUnknown";
    private static String mAppVersion = UNKNOWN_VERSION;
    private static Context mContext = null;
    static Pattern pat = Pattern.compile(regEx);
    static String regEx = "[一-龥]";

    public static String getUserAgent(Context context) {
        String appVersionName = getAppVersionName(context);
        String deviceModel = getDeviceModel();
        return "LetvShopSdk;" + appVersionName + ";" + deviceModel + ";android-phone;" + getOSVersion() + ";zh_CN";
    }

    private static String getAppVersionName(Context context) {
        mContext = context;
        setVersionNameFromPackage();
        return mAppVersion;
    }

    private static void setVersionNameFromPackage() {
        if (isNeedToSetVersionNumber()) {
            try {
                PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
                if (info != null && !isEmpty(info.versionName)) {
                    mAppVersion = info.versionName;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mAppVersion = UNKNOWN_VERSION;
            }
        }
    }

    private static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str) || (str != null && "".equals(str.trim()))) {
            return true;
        }
        return false;
    }

    private static boolean isNeedToSetVersionNumber() {
        return UNKNOWN_VERSION.equals(mAppVersion) && mContext != null;
    }

    private static String getDeviceModel() {
        return Build.MODEL;
    }

    private static int getOSVersion() {
        return VERSION.SDK_INT;
    }

    public static boolean compareWithUrl(String url1, String url2) {
        if (TextUtils.isEmpty(url1) || TextUtils.isEmpty(url2)) {
            if (TextUtils.isEmpty(url1) && TextUtils.isEmpty(url2)) {
                return true;
            }
            return false;
        } else if (url1.length() >= url2.length()) {
            return url1.substring(0, url2.length()).equals(url2);
        } else {
            if (url1.length() < url2.length()) {
                return url2.substring(0, url1.length()).equals(url1);
            }
            return false;
        }
    }

    public static String MatchImgUrl(String url) {
        if (url.endsWith(".jpg")) {
            return new StringBuilder(Constants.IMAGE_URID).append(url).toString();
        }
        if (url.contains("http:")) {
            return url;
        }
        return new StringBuilder(Constants.IMAGE_URI).append(url).toString();
    }

    public static String doubleStrFormat(String number) {
        return new DecimalFormat("0.00").format((double) Float.valueOf(number).floatValue());
    }

    public static boolean isContainsChinese(String str) {
        if (pat.matcher(str).find()) {
            return true;
        }
        return false;
    }
}
