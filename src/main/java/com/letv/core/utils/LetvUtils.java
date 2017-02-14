package com.letv.core.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ResolveInfo.DisplayNameComparator;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.os.Vibrator;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.amap.api.location.AMapLocation;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.GSMInfo;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.PlayRecord;
import com.letv.core.bean.RelatedVideoList;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.channel.RedField;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.LetvConstant.SortType;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.external.gaode.AMapLocationTool;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class LetvUtils {
    public static final char CHARACTER_AND = '&';
    public static final char CHARACTER_BACKSLASH = '/';
    public static final char CHARACTER_EQUAL = '=';
    private static final String CN_BELONG_AREA = "100";
    public static final String COUNTRY_CHINA = "CN";
    public static final String COUNTRY_CODE_KEY = "region";
    public static final String COUNTRY_HONGKONG = "HK";
    public static final String COUNTRY_TAIWAN = "TW";
    public static final String COUNTRY_UNITED_KINGDOM = "UK";
    public static final String COUNTRY_UNITED_STATES = "US";
    private static final double GB = 1.073741824E9d;
    private static final String HK_BELONG_AREA = "101";
    public static final int INTERFACE_SEPERATOR_AND = 0;
    public static final int INTERFACE_SEPERATOR_BACKSLASH = 1;
    public static final String LANG_CHINA = "chs";
    public static final String LANG_HONGKONG = "cht";
    public static final String LANG_TAIWAN = "cht";
    public static final String LANG_UK = "en";
    public static final String LANG_USA = "en";
    private static final double MB = 1048576.0d;
    public static final String PARAM_LANG = "lang";
    private static final String TAG = "LetvUtils_GetDeviceId";
    public static final String URL_FLAG = "http://m.letv.com/search/url?to=";
    public static final String URL_FLAG_NEW = "http://m.le.com/search/url?to=";
    private static final String US_BELONG_AREA = "102";
    public static final String WEB_INNER_FLAG = "letv.com";
    public static final String WEB_INNER_FLAG_NEW = "le.com";
    private static String countryCode;
    public static long mCurrentClickTime = 0;
    public static long mLastClickTime = 0;
    private static String sAndLocationMessage;
    private static String sBackslashLocationMessage;
    private static String sDevId = "";
    private static String sLocationgMessage;
    private static String sMacAddress = "";
    private static String ua;
    private static int versionCode;
    private static String versionName;

    private static class CompareTools implements Comparator<LiveBeanLeChannel> {
        SortType sortType;

        public CompareTools(SortType sortType) {
            this.sortType = sortType;
        }

        public int compare(LiveBeanLeChannel lhs, LiveBeanLeChannel rhs) {
            if (this.sortType == SortType.SORT_BYNO) {
                int ihsNo = LetvUtils.stringToInt(lhs.numericKeys);
                int rhsNo = LetvUtils.stringToInt(rhs.numericKeys);
                if (ihsNo < rhsNo) {
                    return -1;
                }
                if (ihsNo > rhsNo) {
                    return 1;
                }
                return 0;
            } else if (this.sortType != SortType.SORT_BYNEWTIME) {
                return 0;
            } else {
                long ihsTime = lhs.currentmillisecond;
                long rhsTime = rhs.currentmillisecond;
                if (ihsTime > rhsTime) {
                    return -1;
                }
                if (ihsTime == rhsTime) {
                    return 0;
                }
                return 1;
            }
        }
    }

    public static String toPlayCountText(long playCount) {
        if (playCount <= 0) {
            return "";
        }
        if (playCount <= 10000) {
            return new DecimalFormat("#,###").format(playCount) + getString(R.string.times);
        } else if (playCount <= 100000000) {
            return new DecimalFormat("#,###.0").format(((double) playCount) / 10000.0d) + getString(R.string.ten_thousand_times);
        } else {
            return new DecimalFormat("#,###.0").format(((double) playCount) / 1.0E8d) + getString(R.string.hundred_million_times);
        }
    }

    public static int stringToInt(String str) {
        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String getRGBHexValue(String value, boolean hasAlpha) {
        LogInfo.log("fornia", "chatfragment getRGBHexValue value:" + value);
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return getRGBHexValue(Integer.valueOf(value), hasAlpha);
    }

    public static String getRGBHexValue(Integer value, boolean hasAlpha) {
        LogInfo.log("fornia", "chatfragment getRGBHexValue value:" + value);
        if (value == null) {
            return "";
        }
        String hex = Integer.toHexString(value.intValue());
        LogInfo.log("fornia", "chatfragment getRGBHexValue hex:" + hex);
        BigInteger bi = new BigInteger(hex, 16);
        LogInfo.log("fornia", "chatfragment getRGBHexValue bi:" + bi.toString(16));
        String color = bi.toString(16);
        if (TextUtils.isEmpty(color)) {
            return "";
        }
        if (color.length() != 8 || hasAlpha) {
            LogInfo.log("fornia", "chatfragment getRGBHexValue color:" + color);
            return color;
        }
        LogInfo.log("fornia", "chatfragment getRGBHexValue color.substring(2):" + color.substring(2));
        return color.substring(2);
    }

    public static int getMinusDaysBetweenTwoDate(long endTime, long startTime) {
        Date endDate = new Date(endTime);
        Date beginDate = new Date(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        long endMilliSec = c.getTimeInMillis();
        c.setTime(beginDate);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return (int) ((endMilliSec - c.getTimeInMillis()) / 86400000);
    }

    public static SpannableStringBuilder formatVideoTitlePrefix(int color, String str, int start, int end) {
        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(str);
        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(color), start, end, 34);
        return mSpannableStringBuilder;
    }

    public static int getClientVersionCode() {
        if (versionCode != 0) {
            return versionCode;
        }
        try {
            versionCode = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            versionCode = PreferencesManager.getInstance().getSettingVersionCode();
        }
        return versionCode;
    }

    public static String getClientVersionName() {
        if (!TextUtils.isEmpty(versionName)) {
            return versionName;
        }
        try {
            versionName = BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getOSVersionName() {
        return VERSION.RELEASE;
    }

    public static int getSDKVersion() {
        return VERSION.SDK_INT;
    }

    public static boolean is60() {
        return getSDKVersion() >= 23;
    }

    public static String getUa() {
        return ua;
    }

    public static void setUa(Context context) {
        if (TextUtils.isEmpty(ua)) {
            ua = PreferencesManager.getInstance().getUA();
            if (!TextUtils.isEmpty(ua)) {
                ua = "LetvGphoneClient/" + getClientVersionName() + " " + ua;
            } else if (context instanceof Activity) {
                WebView webView = new WebView(context);
                webView.setVisibility(8);
                ua = webView.getSettings().getUserAgentString();
                PreferencesManager.getInstance().setUA(ua);
                ua = "LetvGphoneClient/" + getClientVersionName() + " " + ua;
            }
        }
    }

    public static String generateDeviceId(Context context) {
        LogInfo.log(TAG, "*****generateDeviceId start*****");
        if (TextUtils.isEmpty(sDevId)) {
            String deviceId = PreferencesManager.getInstance().getDeviceId(context);
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = FileUtils.getRelevantData(context, "device_info");
                if (TextUtils.isEmpty(deviceId)) {
                    String synthesize = getIMEI() + getIMSI() + getDeviceName() + getBrandName() + getMacAddress();
                    deviceId = MD5.MD5Encode(synthesize);
                    LogInfo.log(TAG, "***combine deviceid***deviceid:" + deviceId + ", before_md5:" + synthesize);
                    PreferencesManager.getInstance().setDeviceId(context, deviceId);
                    FileUtils.saveRelevantData(context, "device_info", deviceId);
                } else {
                    PreferencesManager.getInstance().setDeviceId(context, deviceId);
                }
            } else {
                LogInfo.log(TAG, "from sp deviceid:" + deviceId);
            }
            sDevId = deviceId;
            LogInfo.log(TAG, "*****generateDeviceId end*****deviceid:" + deviceId);
            return deviceId;
        }
        LogInfo.log(TAG, "*****generateDeviceId end*****deviceid:" + sDevId);
        return sDevId;
    }

    private static String generate_DeviceId() {
        LogInfo.log(TAG, "***combine imsi start***");
        String str = getIMEI() + getDeviceName() + getBrandName() + getMacAddress();
        String imsi = MD5.MD5Encode(str);
        LogInfo.log(TAG, "***combine imsi end***imsi:" + imsi + ", before_md5:" + str);
        return imsi;
    }

    public static String getUUID(Context context) {
        return generateDeviceId(context) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + System.currentTimeMillis();
    }

    public static String getIMEI() {
        try {
            String deviceId = ((TelephonyManager) BaseApplication.getInstance().getSystemService("phone")).getDeviceId();
            LogInfo.log(TAG, "get imei:" + deviceId);
            if (deviceId == null || deviceId.length() <= 0) {
                return "";
            }
            return deviceId.replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIMSI() {
        String subscriberId = ((TelephonyManager) BaseApplication.getInstance().getSystemService("phone")).getSubscriberId();
        LogInfo.log(TAG, "get imsi:" + subscriberId);
        if (subscriberId == null || subscriberId.length() <= 0) {
            return generate_DeviceId();
        }
        subscriberId.replace(" ", "");
        if (TextUtils.isEmpty(subscriberId)) {
            return generate_DeviceId();
        }
        return subscriberId;
    }

    public static String getMacAddress() {
        if (TextUtils.isEmpty(sMacAddress)) {
            WifiInfo wifiInfo = ((WifiManager) BaseApplication.getInstance().getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo == null) {
                return "";
            }
            sMacAddress = BaseTypeUtils.ensureStringValidate(wifiInfo.getMacAddress());
            LogInfo.log(TAG, "get macaddress:" + sMacAddress);
            return sMacAddress;
        }
        LogInfo.log(TAG, "sMacAddress:" + sMacAddress);
        return sMacAddress;
    }

    public static String getMacAddressForLogin() {
        String macAdd = "";
        if (VERSION.SDK_INT < 23) {
            return getMacAddress();
        }
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (interfaces != null) {
            while (interfaces.hasMoreElements()) {
                NetworkInterface iF = (NetworkInterface) interfaces.nextElement();
                byte[] addr = new byte[0];
                try {
                    addr = iF.getHardwareAddress();
                } catch (SocketException e2) {
                    e2.printStackTrace();
                }
                if (!(addr == null || addr.length == 0)) {
                    StringBuilder buf = new StringBuilder();
                    int length = addr.length;
                    for (int i = 0; i < length; i++) {
                        buf.append(String.format("%02X:", new Object[]{Byte.valueOf(addr[i])}));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    String mac = buf.toString();
                    if ("wlan0".equals(iF.getName())) {
                        macAdd = mac;
                    }
                    Log.e("wdm", "interfaceName=" + iF.getName() + "\n, mac=" + mac);
                }
            }
        }
        return macAdd.toLowerCase();
    }

    public static String getDeviceName() {
        String deviceName = BaseTypeUtils.ensureStringValidate(Build.MODEL);
        LogInfo.log(TAG, "get deviceName:" + deviceName);
        return deviceName;
    }

    public static String getBrandName() {
        String brand = BaseTypeUtils.ensureStringValidate(Build.BRAND);
        LogInfo.log(TAG, "get brandName:" + brand);
        if (TextUtils.isEmpty(brand)) {
            return "";
        }
        return getData(brand);
    }

    public static String getModelName() {
        String model = Build.MODEL;
        if (model == null || model.length() <= 0) {
            return "";
        }
        return getData(model);
    }

    public static String getData(String data) {
        if (data == null || data.length() <= 0) {
            return NetworkUtils.DELIMITER_LINE;
        }
        return data.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    public static String getMIUIVersion() {
        String version;
        Throwable th;
        BufferedReader input = null;
        try {
            BufferedReader input2 = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.miui.ui.version.name").getInputStream()), 1024);
            try {
                version = input2.readLine();
                input2.close();
                if (input2 != null) {
                    try {
                        input2.close();
                    } catch (IOException e) {
                        Log.e("wuxinrong", "Exception while closing InputStream", e);
                    }
                }
                input = input2;
            } catch (IOException e2) {
                input = input2;
                version = null;
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e3) {
                        Log.e("wuxinrong", "Exception while closing InputStream", e3);
                    }
                }
                return version;
            } catch (Throwable th2) {
                th = th2;
                input = input2;
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e32) {
                        Log.e("wuxinrong", "Exception while closing InputStream", e32);
                    }
                }
                throw th;
            }
        } catch (IOException e4) {
            version = null;
            if (input != null) {
                input.close();
            }
            return version;
        } catch (Throwable th3) {
            th = th3;
            if (input != null) {
                input.close();
            }
            throw th;
        }
        return version;
    }

    public static boolean isSMNote() {
        return "GT-N7100".equals(getDeviceName());
    }

    public boolean isSViP() {
        return PreferencesManager.getInstance().getVipLevel() == 2;
    }

    public static String getPcode() {
        return LetvConfig.getPcode();
    }

    public static boolean isRealMainProcess(Context context) {
        String processName = getProcessName(context, Process.myPid());
        if (TextUtils.isEmpty(processName)) {
            return false;
        }
        if (processName.equals("com.letv.android.client")) {
            return true;
        }
        return processName.contains("pip_Service");
    }

    public static String getProcessName(Context cxt, int pid) {
        List<RunningAppProcessInfo> runningApps = ((ActivityManager) cxt.getSystemService("activity")).getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static String getUID() {
        if (PreferencesManager.getInstance().isLogin()) {
            return PreferencesManager.getInstance().getUserId();
        }
        return "";
    }

    public static String getGBNumber(long size, int bit) {
        if (size <= 0) {
            return "0B";
        }
        if (size < 1000) {
            return size + "B";
        }
        if (size < 1024000) {
            return dealDotZero(new BigDecimal((double) ((((float) size) * 1.0f) / 1024.0f)).setScale(0, 4)) + "K";
        } else if (size < 1048576000) {
            return dealDotZero(new BigDecimal((double) ((((float) size) * 1.0f) / 1048576.0f)).setScale(bit, 4)) + "M";
        } else {
            return dealDotZero(new BigDecimal((double) ((((float) size) * 1.0f) / 1.07374182E9f)).setScale(bit, 4)) + "G";
        }
    }

    public static String getMBDecimal(long size) {
        String mS = new Double(new DecimalFormat(".0").format((double) (size / 1048576))).toString();
        int start = mS.lastIndexOf(".");
        if (start <= 0) {
            return mS + ".0M";
        }
        if (mS.substring(start + 1).length() == 1) {
            return mS + "0M";
        }
        return mS + "M";
    }

    public static String getDecimalsVal(double val, int bit) {
        return new BigDecimal(val).setScale(bit, 4).toString() + "";
    }

    public static long getLongMB(long size, int bit) {
        return getLongVal(((double) size) / MB, bit);
    }

    public static long getLongVal(double val, int bit) {
        return new BigDecimal(val).setScale(bit, 4).longValue();
    }

    private static String dealDotZero(BigDecimal bd) {
        String origString = bd + "";
        Log.i("fornia", "check size origString:" + origString);
        String result = origString;
        if (origString.indexOf(".") > 0) {
            return origString.replaceAll("0+?$", "").replaceAll("[.]$", "");
        }
        return result;
    }

    public static boolean checkClickEvent() {
        return checkClickEvent(1000);
    }

    public static boolean checkClickEvent(long interval) {
        mCurrentClickTime = System.currentTimeMillis();
        if (mCurrentClickTime - mLastClickTime > interval) {
            mLastClickTime = mCurrentClickTime;
            return true;
        }
        mLastClickTime = mCurrentClickTime;
        return false;
    }

    public static boolean reflectScreenState() {
        boolean isScreenOn = true;
        boolean flag = false;
        try {
            isScreenOn = ((PowerManager) BaseApplication.getInstance().getSystemService("power")).isScreenOn();
            flag = ((KeyguardManager) BaseApplication.getInstance().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isScreenOn && !flag;
    }

    public static String getChannelName(Context context, int channelId) {
        switch (channelId) {
            case 1:
                return context.getString(R.string.channel_movie);
            case 2:
                return context.getString(R.string.channel_tv);
            case 3:
                return context.getString(R.string.channel_joy);
            case 4:
                return context.getString(R.string.channel_pe);
            case 5:
                return context.getString(R.string.channel_cartoon);
            case 6:
                return context.getString(R.string.channel_information);
            case 7:
                return context.getString(R.string.channel_original);
            case 8:
                return context.getString(R.string.channel_others);
            case 9:
                return context.getString(R.string.channel_music);
            case 10:
                return context.getString(R.string.channel_funny);
            case 11:
                return context.getString(R.string.channel_tvshow);
            case 12:
                return context.getString(R.string.channel_science);
            case 13:
                return context.getString(R.string.channel_life);
            case 14:
                return context.getString(R.string.channel_car);
            case 15:
                return context.getString(R.string.channel_tvprogram);
            case 16:
                return context.getString(R.string.channel_document_film);
            case 17:
                return context.getString(R.string.channel_open_class);
            case 19:
                return context.getString(R.string.channel_letv_make);
            case 20:
                return context.getString(R.string.channel_fashion);
            case 22:
                return context.getString(R.string.channel_financial);
            case 23:
                return context.getString(R.string.channel_tourism);
            case 34:
                return context.getString(R.string.channel_paternity);
            case 1021:
                return context.getString(R.string.channel_education);
            default:
                return "";
        }
    }

    public static boolean canDownload3g(Activity activity) {
        if (activity == null) {
            LogInfo.log("", " canDownload3g  activity == null ");
            return true;
        } else if (NetworkUtils.isNetworkConnected(activity) || NetworkUtils.isWifi() || PreferencesManager.getInstance().isAllowMobileNetwork()) {
            return true;
        } else {
            DialogUtil.call(activity, activity.getResources().getString(R.string.dialog_3g_download), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            return false;
        }
    }

    public static String createUA(String oldUA) {
        if (TextUtils.isEmpty(oldUA)) {
            String ua = "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1" + " LetvMobileClient_" + getClientVersionCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getSystemName();
            LogInfo.log("fornia", "useragent 1111newUA:" + ua);
            return ua;
        }
        LogInfo.log("fornia", "useragent 0000newUA:" + oldUA + " " + "LetvMobileClient_" + getClientVersionCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getSystemName());
        return oldUA + " " + "LetvMobileClient_" + getClientVersionCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getSystemName();
    }

    public static String getSystemName() {
        return AbstractSpiCall.ANDROID_CLIENT_TYPE;
    }

    public static void setCookies(Context context, String url) {
        String cookie1 = "letvclient_sig=" + MD5.toMd5(Global.DEVICEID + LetvConstant.MIYUE_ATTENDANCE);
        String cookie2 = "letvclient_did=" + Global.DEVICEID;
        CookieSyncManager syncManger = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        if (CookieStr == null || !CookieStr.contains("letvclient_did") || !CookieStr.contains("letvclient_sig")) {
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url, cookie1);
            cookieManager.setCookie(url, cookie2);
            syncManger.sync();
        }
    }

    public static GSMInfo getGSMInfo(Context context) {
        try {
            GSMInfo info = new GSMInfo();
            TelephonyManager manager = (TelephonyManager) context.getSystemService("phone");
            if (manager != null) {
                CellLocation cellLocation = manager.getCellLocation();
                int lac = 0;
                int cellid = 0;
                if (cellLocation != null) {
                    if (cellLocation instanceof GsmCellLocation) {
                        lac = ((GsmCellLocation) cellLocation).getLac();
                        cellid = ((GsmCellLocation) cellLocation).getCid();
                    } else if (cellLocation instanceof CdmaCellLocation) {
                        cellid = ((CdmaCellLocation) cellLocation).getNetworkId();
                        lac = ((CdmaCellLocation) cellLocation).getBaseStationId();
                    }
                }
                info.lac = lac;
                info.cid = cellid;
            }
            AMapLocation location = AMapLocationTool.getInstance().location();
            if (location != null) {
                info.latitude = location.getLatitude();
                info.longitude = location.getLongitude();
                return info;
            }
            info.latitude = Double.parseDouble(PreferencesManager.getInstance().getLocationLongitude());
            info.longitude = Double.parseDouble(PreferencesManager.getInstance().getLocationLatitude());
            return info;
        } catch (Exception e) {
            LogInfo.log("ZSM++ ==== GSM exception e == " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static boolean emailFormats(String email) {
        if (email == null) {
            return false;
        }
        return Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$").matcher(email).matches();
    }

    public static void retrievePwdBySMS(Context context, String phonenumber) {
        Intent mIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + phonenumber));
        mIntent.putExtra("sms_body", "");
        context.startActivity(mIntent);
    }

    public static boolean judgeInnerUrl(String url) {
        return !TextUtils.isEmpty(url) && (url.contains(WEB_INNER_FLAG) || url.contains(WEB_INNER_FLAG_NEW));
    }

    public static boolean judgeOutSideUrl(String url) {
        return !TextUtils.isEmpty(url) && (url.contains(URL_FLAG) || url.contains(URL_FLAG_NEW));
    }

    public static String formatDoubleNum(double price, int num) {
        if (num == 1) {
            return new DecimalFormat("#0.0").format(price);
        }
        if (num == 2) {
            return new DecimalFormat("#0.00").format(price);
        }
        return String.valueOf(price);
    }

    public static String getStartStreamText() {
        return TipUtils.getTipMessage("100037", BaseApplication.instance.getString(R.string.stream_standard));
    }

    public static String getSmoothStreamText() {
        return TipUtils.getTipMessage("100036", BaseApplication.instance.getString(R.string.stream_smooth));
    }

    public static String getHDstreamText() {
        return TipUtils.getTipMessage("100038", BaseApplication.instance.getString(R.string.stream_hd));
    }

    public static String getSpeedStreamText() {
        return TipUtils.getTipMessage("100035", BaseApplication.instance.getString(R.string.stream_low));
    }

    public static boolean openApp(Context context, String isAppPackageName) {
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        PackageManager mPackageManager = BaseApplication.getInstance().getApplicationContext().getPackageManager();
        List<ResolveInfo> mAllApps = mPackageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(mAllApps, new DisplayNameComparator(mPackageManager));
        for (ResolveInfo res : mAllApps) {
            String pkg = res.activityInfo.packageName;
            String cls = res.activityInfo.name;
            LogInfo.log("Emerson", "-------pkg = " + pkg + "--cls = " + cls);
            if (res.activityInfo.packageName.equals(isAppPackageName)) {
                ComponentName componet = new ComponentName(pkg, cls);
                Intent intent = new Intent();
                intent.setComponent(componet);
                intent.addFlags(268435456);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    public static String getNumberTime2(long time_second) {
        Formatter formatter = new Formatter(null, Locale.getDefault());
        if (time_second < 0) {
            time_second = 0;
        }
        try {
            String formatter2;
            long seconds = time_second % 60;
            if (time_second / 60 > 99) {
                formatter2 = formatter.format("%03d:%02d", new Object[]{Long.valueOf(time_second / 60), Long.valueOf(seconds)}).toString();
            } else {
                formatter2 = formatter.format("%02d:%02d", new Object[]{Long.valueOf(time_second / 60), Long.valueOf(seconds)}).toString();
                formatter.close();
            }
            return formatter2;
        } finally {
            formatter.close();
        }
    }

    public static boolean passwordFormat(String password) {
        if (password == null) {
            return false;
        }
        return Pattern.compile("^[a-zA-Z0-9!`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{6,16}$").matcher(password).matches();
    }

    public static boolean mobileNumberFormat(String mobiles) {
        if (mobiles == null) {
            return false;
        }
        return Pattern.compile("^(1)\\d{10}$").matcher(mobiles).matches();
    }

    public static String createUA(String oldUA, Context context) {
        LogInfo.log("fornia", "useragent oldUA:" + oldUA);
        if (TextUtils.isEmpty(oldUA)) {
            String ua = "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1" + " LetvMobileClient_" + LetvTools.getClientVersionCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getSystemName();
            LogInfo.log("fornia", "useragent 1111newUA:" + ua);
            return ua;
        }
        LogInfo.log("fornia", "useragent 0000newUA:" + oldUA + " " + "LetvMobileClient_" + LetvTools.getClientVersionCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getSystemName());
        return oldUA + " " + "LetvMobileClient_" + LetvTools.getClientVersionCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getSystemName();
    }

    public static String getLoginUserName() {
        if (PreferencesManager.getInstance().isLogin()) {
            return PreferencesManager.getInstance().getUserName();
        }
        return "";
    }

    public static int getMerge(String style) {
        return "1".equals(style) ? 0 : 1;
    }

    public static boolean getIsList(String style) {
        return !"1".equals(style);
    }

    public static String getOrder(int cid) {
        return (cid == 11 || cid == 3 || cid == 4) ? "1" : "-1";
    }

    public static int calculateRows(int index, int rowSize) {
        int b = index / rowSize;
        return index % rowSize == 0 ? b : b + 1;
    }

    public static int calculatePos(int index, int rowSize) {
        return index % rowSize;
    }

    public static String checkUrl(String url) {
        if (url == null) {
            return url;
        }
        url.replaceAll(" ", "");
        if (url.startsWith("http://")) {
            return url;
        }
        return "http://" + url;
    }

    public static boolean checkUrl2(String url) {
        if (url == null) {
            return false;
        }
        return url.startsWith("http:");
    }

    public static String getLunboOrWeishiType(int launchMode) {
        switch (launchMode) {
            case 101:
                return "1";
            case 102:
                return "2";
            case 115:
                return "1";
            case 116:
                return "1";
            case 117:
                return "1";
            case LiveType.PLAY_LIVE_HK_MUSIC /*118*/:
                return "1";
            case 119:
                return "1";
            default:
                return "2";
        }
    }

    public static boolean isLunboOrWeishiByChannelType(String channelType) {
        return !TextUtils.isEmpty(channelType) && (channelType.equals("2") || channelType.equals("1"));
    }

    public static String[] getVideoIdsByVideoList(RelatedVideoList relatedVideoList) {
        if (relatedVideoList == null) {
            return null;
        }
        int length;
        if (relatedVideoList.size() % 60 == 0) {
            length = relatedVideoList.size() / 60;
        } else {
            length = (relatedVideoList.size() / 60) + 1;
        }
        String[] ids = new String[length];
        for (int i = 0; i < length; i++) {
            ids[i] = "";
            for (int j = 0; j < 60; j++) {
                int k = (i * 60) + j;
                if (k >= relatedVideoList.size()) {
                    break;
                }
                ids[i] = ids[i] + ((AlbumInfo) relatedVideoList.get(k)).pid + ",";
            }
            ids[i] = ids[i].substring(0, ids[i].length() - 1);
        }
        return ids;
    }

    public static boolean isPlaying(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(startTime)) {
            return false;
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (currentTime.compareTo(startTime) < 0 || currentTime.compareTo(endTime) > 0) {
            return false;
        }
        return true;
    }

    public static boolean isNotStart(String beginTime) {
        return beginTime != null && beginTime.compareTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) > 0;
    }

    public static boolean isOver(String endTime) {
        return endTime != null && endTime.compareTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) < 0;
    }

    public static String getLunboWeishiType(int launchMode) {
        switch (launchMode) {
            case 101:
                return "lunbo";
            case 102:
                return "weishi";
            default:
                return "lunbo";
        }
    }

    public static int codeToInt(String code) {
        if (TextUtils.isEmpty(code)) {
            return 0;
        }
        code = code.toUpperCase();
        StringBuilder numString = new StringBuilder();
        HashMap<Character, Integer> hashMap = new HashMap();
        hashMap.put(Character.valueOf('A'), Integer.valueOf(1));
        hashMap.put(Character.valueOf('B'), Integer.valueOf(2));
        hashMap.put(Character.valueOf('C'), Integer.valueOf(3));
        hashMap.put(Character.valueOf('D'), Integer.valueOf(4));
        hashMap.put(Character.valueOf('E'), Integer.valueOf(5));
        hashMap.put(Character.valueOf('F'), Integer.valueOf(6));
        hashMap.put(Character.valueOf('G'), Integer.valueOf(7));
        hashMap.put(Character.valueOf('H'), Integer.valueOf(8));
        hashMap.put(Character.valueOf('I'), Integer.valueOf(9));
        hashMap.put(Character.valueOf('J'), Integer.valueOf(10));
        hashMap.put(Character.valueOf('K'), Integer.valueOf(11));
        hashMap.put(Character.valueOf('L'), Integer.valueOf(12));
        hashMap.put(Character.valueOf('M'), Integer.valueOf(13));
        hashMap.put(Character.valueOf('N'), Integer.valueOf(14));
        hashMap.put(Character.valueOf('O'), Integer.valueOf(15));
        hashMap.put(Character.valueOf('P'), Integer.valueOf(16));
        hashMap.put(Character.valueOf('Q'), Integer.valueOf(17));
        hashMap.put(Character.valueOf('R'), Integer.valueOf(18));
        hashMap.put(Character.valueOf('S'), Integer.valueOf(19));
        hashMap.put(Character.valueOf('T'), Integer.valueOf(20));
        hashMap.put(Character.valueOf('U'), Integer.valueOf(21));
        hashMap.put(Character.valueOf('V'), Integer.valueOf(22));
        hashMap.put(Character.valueOf('W'), Integer.valueOf(23));
        hashMap.put(Character.valueOf('X'), Integer.valueOf(24));
        hashMap.put(Character.valueOf('Y'), Integer.valueOf(25));
        hashMap.put(Character.valueOf('Z'), Integer.valueOf(26));
        hashMap.put(Character.valueOf('0'), Integer.valueOf(27));
        hashMap.put(Character.valueOf('1'), Integer.valueOf(28));
        hashMap.put(Character.valueOf('2'), Integer.valueOf(29));
        hashMap.put(Character.valueOf('3'), Integer.valueOf(30));
        hashMap.put(Character.valueOf('4'), Integer.valueOf(31));
        hashMap.put(Character.valueOf('5'), Integer.valueOf(32));
        hashMap.put(Character.valueOf('6'), Integer.valueOf(33));
        hashMap.put(Character.valueOf('7'), Integer.valueOf(34));
        hashMap.put(Character.valueOf('8'), Integer.valueOf(35));
        hashMap.put(Character.valueOf('9'), Integer.valueOf(36));
        if (TextUtils.isEmpty(code)) {
            return 0;
        }
        char[] cs = code.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            int a = 0;
            if (hashMap.containsKey(Character.valueOf(cs[i]))) {
                a = ((Integer) hashMap.get(Character.valueOf(cs[i]))).intValue();
            }
            numString.append(a);
        }
        try {
            return (int) Long.parseLong(numString.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static String ToDBC(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        int i = 0;
        while (i < c.length) {
            if (c[i] == '　') {
                c[i] = ' ';
            } else if (c[i] > '＀' && c[i] < '｟') {
                c[i] = (char) (c[i] - 65248);
            }
            i++;
        }
        return new String(c);
    }

    public static String ToSBC(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '　';
            } else if (c[i] < '') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    public static String getPlayRecordType(Context context, PlayRecord record, long btime) {
        if (record == null) {
            return null;
        }
        String loadingStr;
        if (record.playedDuration <= 0 || record.playedDuration == btime) {
            loadingStr = TipUtils.getTipMessage(DialogMsgConstantId.ON_LOADING, R.string.player_loading);
        } else {
            String defaultMsg = context.getString(R.string.play_record_loading_tag);
            String msg = TipUtils.getTipMessage("100072", defaultMsg);
            if (!msg.contains("%s")) {
                msg = defaultMsg;
            }
            loadingStr = String.format(msg, new Object[]{StringUtils.stringForTimeNoHour(record.playedDuration * 1000)});
        }
        return loadingStr;
    }

    public static List<LiveBeanLeChannel> sortChannelList(List<LiveBeanLeChannel> list, SortType sortType) {
        Collections.sort(list, new CompareTools(sortType));
        return list;
    }

    public static String toStringChannelType(int launchMode) {
        switch (launchMode) {
            case 101:
                return "lunbo";
            case 102:
                return "weishi";
            case 103:
                return "sports";
            case 104:
                return "ent";
            case 105:
                return "music";
            case 106:
                return "game";
            case 107:
                return "information";
            case 108:
                return "finance";
            case 109:
                return "brand";
            case LiveType.PLAY_LIVE_OTHER /*110*/:
                return "other";
            case 111:
                return "variety";
            case 114:
                return "hk_all";
            case 115:
                return "hk_movie";
            case 116:
                return "hk_tvseries";
            case 117:
                return "hk_variety";
            case LiveType.PLAY_LIVE_HK_MUSIC /*118*/:
                return "hk_music";
            case 119:
                return "hk_sports";
            default:
                return PlayConstant.CHANNEL_TYPE_VALUE_REMEN;
        }
    }

    public static String getChannelFromLiveid(String liveid) {
        if (TextUtils.isEmpty(liveid) || liveid.length() <= 1) {
            return "";
        }
        return liveid.substring(0, 2);
    }

    public static String getCategoryFromLiveid(String liveid) {
        if (TextUtils.isEmpty(liveid) || liveid.length() <= 4) {
            return "";
        }
        return liveid.substring(2, 5);
    }

    public static String getSeasonFromLiveid(String liveid) {
        if (TextUtils.isEmpty(liveid) || liveid.length() <= 8) {
            return "";
        }
        return liveid.substring(5, 9);
    }

    public static String getTurnFromLiveid(String liveid) {
        if (TextUtils.isEmpty(liveid) || liveid.length() <= 11) {
            return "";
        }
        return liveid.substring(9, 12);
    }

    public static String getGameFromLiveid(String liveid) {
        if (TextUtils.isEmpty(liveid) || liveid.length() <= 15) {
            return "";
        }
        return liveid.substring(12, 16);
    }

    public static boolean isInFinish(long id) {
        return DBManager.getInstance().getDownloadTrace().isInFinish(new StringBuilder().append(id).append("").toString()) || DBManager.getInstance().getWorldCupTrace().isInFinish(id + "");
    }

    public static int getOSVersionCode() {
        int version = -1;
        try {
            version = Integer.valueOf(VERSION.SDK_INT).intValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getClientVersionCode(Context context) {
        int i = 0;
        if (context != null) {
            try {
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService("activity");
        String packageName = context.getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public static String timeStringAll(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    public static String timeClockString(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static boolean isDownloadServiceRunning() {
        return isServiceRunning(BaseApplication.getInstance(), "com.letv.download.service.DownloadService");
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        List<RunningServiceInfo> serviceList = ((ActivityManager) mContext.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (serviceList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (((RunningServiceInfo) serviceList.get(i)).service.getClassName().equals(className)) {
                LogInfo.log("XX", "isServiceRunning name " + ((RunningServiceInfo) serviceList.get(i)).service.getClassName());
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static String getCacheSize(Context context) {
        String fileSizeString = "0.00M";
        String state = Environment.getExternalStorageState();
        if (!state.equals("mounted") || state.equals("mounted_ro")) {
            return fileSizeString;
        }
        File file = new File(FileUtils.getBitmapCachePath(context));
        if (file == null || !file.exists()) {
            return fileSizeString;
        }
        long size = 0;
        File[] flist = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size += getFileSize(flist[i]);
            } else {
                size += flist[i].length();
            }
        }
        if (size <= 0) {
            return fileSizeString;
        }
        return new DecimalFormat("#0.00").format(((double) size) / MB) + "M";
    }

    public static long getFileSize(File f) {
        long size = 0;
        File[] flist = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size += getFileSize(flist[i]);
            } else {
                size += flist[i].length();
            }
        }
        return size;
    }

    public static boolean isApplicationInBackground(Context context) {
        List<RunningTaskInfo> tasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (tasks.isEmpty() || ((RunningTaskInfo) tasks.get(0)).topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static String getLocalIP() {
        WifiManager wifiManager = (WifiManager) BaseApplication.getInstance().getSystemService("wifi");
        if (wifiManager == null) {
            return "";
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return "";
        }
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0) {
            return "";
        }
        return (ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255);
    }

    public static boolean isSpecialChannel() {
        String[] pcodes = LetvConfig.getSpecialPcodeList();
        if (pcodes == null) {
            return false;
        }
        for (String pcode : pcodes) {
            if (LetvConfig.getPcode().equals(pcode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoRetainPopupPcode() {
        return true;
    }

    public static boolean isLongWelcomePcode() {
        String[] pcodes = LetvConfig.getLongWelcomePcodeList();
        if (pcodes == null) {
            return false;
        }
        for (String pcode : pcodes) {
            if (LetvConfig.getPcode().equals(pcode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGooglePlay() {
        String[] pcodes = LetvConfig.getGooglePlaylist();
        if (pcodes == null) {
            return false;
        }
        for (String pcode : pcodes) {
            if (LetvConfig.getPcode().equals(pcode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDelayUpgrade() {
        String[] pcodes = LetvConfig.getDelayUpgradeList();
        if (pcodes == null) {
            return false;
        }
        for (String pcode : pcodes) {
            if (LetvConfig.getPcode().equals(pcode)) {
                return true;
            }
        }
        return false;
    }

    public static String getString(int id) {
        return BaseApplication.getInstance().getApplicationContext().getString(id);
    }

    public static String getString(int id, String text) {
        return BaseApplication.getInstance().getApplicationContext().getString(id, new Object[]{text});
    }

    public static String getString(int id, String preText, String postText) {
        return BaseApplication.getInstance().getApplicationContext().getString(id, new Object[]{preText, postText});
    }

    public static void Vibrate(Activity activity, long milliseconds) {
        ((Vibrator) activity.getSystemService("vibrator")).vibrate(milliseconds);
    }

    public static void Vibrate(Activity activity, long[] pattern, boolean isRepeat) {
        ((Vibrator) activity.getSystemService("vibrator")).vibrate(pattern, isRepeat ? 1 : -1);
    }

    public static String getCountry() {
        String country = BaseApplication.getInstance().getResources().getConfiguration().locale.getCountry();
        if (COUNTRY_HONGKONG.equals(country)) {
            return COUNTRY_TAIWAN;
        }
        return country;
    }

    public static String genLangResRequestUrl(String url, int seperator) {
        return genLangResRequestUrl(url, seperator, true);
    }

    public static String genLangResRequestUrl(String url, int seperator, boolean needLocation) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        String locationMessage = getLocationMessage(seperator);
        if (!TextUtils.isEmpty(locationMessage) && needLocation) {
            sb.append(locationMessage);
        }
        String code = getCountryCode();
        if (seperator == 0) {
            sb.append(CHARACTER_AND).append(COUNTRY_CODE_KEY).append(CHARACTER_EQUAL).append(code).append(CHARACTER_AND).append(PARAM_LANG).append(CHARACTER_EQUAL);
        } else if (1 == seperator) {
            sb.append(CHARACTER_BACKSLASH).append(COUNTRY_CODE_KEY).append(CHARACTER_BACKSLASH).append(code).append(CHARACTER_BACKSLASH).append(PARAM_LANG).append(CHARACTER_BACKSLASH);
        } else {
            LogInfo.log("international", "接口分隔符参数类型非法");
            return url;
        }
        String country = getCountry();
        String lang = "";
        if (COUNTRY_HONGKONG.equals(country)) {
            lang = "cht";
        } else if (COUNTRY_TAIWAN.equals(country)) {
            lang = "cht";
        } else if (COUNTRY_UNITED_STATES.equals(country) || COUNTRY_UNITED_KINGDOM.equals(country)) {
            lang = LANG_CHINA;
        } else {
            lang = LANG_CHINA;
        }
        sb.append(lang);
        return sb.toString();
    }

    public static void sendBroadcast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            context.sendBroadcast(new Intent(msg));
        }
    }

    public static int getLaunchMode(String channelType) {
        if (TextUtils.isEmpty(channelType)) {
            return 101;
        }
        if ("sports".equals(channelType)) {
            return 103;
        }
        if ("ent".equals(channelType) || LiveRoomConstant.CHANNEL_TYPE_ENTERTAINMENT.equals(channelType)) {
            return 104;
        }
        if ("music".equals(channelType)) {
            return 105;
        }
        if ("game".equals(channelType)) {
            return 106;
        }
        if ("information".equals(channelType)) {
            return 107;
        }
        if ("finance".equals(channelType)) {
            return 108;
        }
        if ("brand".equals(channelType)) {
            return 109;
        }
        if ("other".equals(channelType)) {
            return LiveType.PLAY_LIVE_OTHER;
        }
        if ("variety".equals(channelType)) {
            return 111;
        }
        if ("lunbo".equals(channelType)) {
            return 101;
        }
        if ("weishi".equals(channelType)) {
            return 102;
        }
        if ("hk_all".equals(channelType)) {
            return 114;
        }
        if ("hk_movie".equals(channelType)) {
            return 115;
        }
        if ("hk_tvseries".equals(channelType)) {
            return 116;
        }
        if ("hk_variety".equals(channelType)) {
            return 117;
        }
        if ("hk_music".equals(channelType)) {
            return LiveType.PLAY_LIVE_HK_MUSIC;
        }
        return "hk_sports".equals(channelType) ? 119 : 103;
    }

    public static boolean isLunboOrWeishi(int launchMode) {
        return launchMode == 101 || launchMode == 102 || launchMode == 115 || launchMode == 116 || launchMode == 117 || launchMode == 119 || launchMode == LiveType.PLAY_LIVE_HK_MUSIC;
    }

    public static boolean isLiveSingleChannel(int launchMode) {
        switch (launchMode) {
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case LiveType.PLAY_LIVE_OTHER /*110*/:
            case 111:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLbOrWsByChannelType(String channelType) {
        return "lunbo".equals(channelType) || "weishi".equals(channelType) || "hk_movie".equals(channelType) || "hk_tvseries".equals(channelType) || "hk_variety".equals(channelType) || "hk_sports".equals(channelType) || "hk_music".equals(channelType);
    }

    public static int getConstantPrefix(int constant) {
        return (constant / 100) * 100;
    }

    public static boolean isNewUser() {
        if (getTimeDifference(BaseApplication.getInstance().getSharedPreferences("LetvActivity", 32768).getString("firstRunAppTime", "2015-11-30  17:18:56"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))) <= 6) {
            return true;
        }
        return false;
    }

    public static int getTimeDifference(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return (int) ((df.parse(endTime).getTime() - df.parse(startTime).getTime()) / 86400000);
        } catch (Exception e) {
            e.printStackTrace();
            return 8;
        } catch (Throwable th) {
            return 8;
        }
    }

    public static String getCountryCode() {
        if (TextUtils.isEmpty(countryCode)) {
            countryCode = PreferencesManager.getInstance().getCountryCode();
        }
        if (TextUtils.isEmpty(countryCode)) {
            countryCode = COUNTRY_CHINA;
        }
        if (PreferencesManager.getInstance().isTestApi() && !TextUtils.isEmpty(PreferencesManager.getInstance().getLocationCode()) && PreferencesManager.getInstance().getLocationCode().contains(COUNTRY_HONGKONG)) {
            return COUNTRY_HONGKONG;
        }
        return countryCode;
    }

    public static ArrayList<SiftKVP> getStringSKfList(ArrayList<RedField> redField) {
        if (redField == null) {
            return null;
        }
        ArrayList<SiftKVP> list = new ArrayList();
        int i = 0;
        while (i < redField.size()) {
            if (BaseTypeUtils.getElementFromList(redField, i) != null) {
                StringBuffer sb = new StringBuffer();
                if (TextUtils.equals("pt", ((RedField) redField.get(i)).redFieldTypeList) && TextUtils.equals(LetvConstant.MOBILE_SYSTEM_PHONE_PAY, ((RedField) redField.get(i)).redFieldDetailList)) {
                    sb.append("ispay/1");
                } else {
                    sb.append(((RedField) redField.get(i)).redFieldTypeList).append("/");
                    sb.append(((RedField) redField.get(i)).redFieldDetailList);
                }
                SiftKVP sift = new SiftKVP();
                sift.filterKey = sb.toString();
                list.add(sift);
            }
            i++;
        }
        return list;
    }

    public static String getLocationMessage(int seperator) {
        if (TextUtils.isEmpty(sLocationgMessage)) {
            sLocationgMessage = PreferencesManager.getInstance().getLocationCode();
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(sLocationgMessage)) {
            String[] arrGeoCode = sLocationgMessage.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            try {
                if (BaseTypeUtils.getElementFromArray(arrGeoCode, 4) != null) {
                    if (seperator == 0) {
                        if (TextUtils.isEmpty(sAndLocationMessage)) {
                            sb.append(CHARACTER_AND).append(HOME_RECOMMEND_PARAMETERS.COUNTRY).append(CHARACTER_EQUAL).append(URLEncoder.encode(arrGeoCode[0], "UTF-8")).append(CHARACTER_AND).append(HOME_RECOMMEND_PARAMETERS.PROVINCE_ID).append(CHARACTER_EQUAL).append(URLEncoder.encode(arrGeoCode[1], "UTF-8")).append(CHARACTER_AND).append(HOME_RECOMMEND_PARAMETERS.DISTRICT_ID).append(CHARACTER_EQUAL).append(URLEncoder.encode(arrGeoCode[2], "UTF-8")).append(CHARACTER_AND).append(HOME_RECOMMEND_PARAMETERS.CITY_LEVEL).append(CHARACTER_EQUAL).append(URLEncoder.encode(arrGeoCode[3], "UTF-8")).append(CHARACTER_AND).append(HOME_RECOMMEND_PARAMETERS.LOCATION).append(CHARACTER_EQUAL).append(URLEncoder.encode(arrGeoCode[4], "UTF-8"));
                            sAndLocationMessage = sb.toString();
                        }
                        return sAndLocationMessage;
                    } else if (1 == seperator) {
                        if (TextUtils.isEmpty(sBackslashLocationMessage)) {
                            sb.append(CHARACTER_BACKSLASH).append(HOME_RECOMMEND_PARAMETERS.COUNTRY).append(CHARACTER_BACKSLASH).append(URLEncoder.encode(arrGeoCode[0], "UTF-8")).append(CHARACTER_BACKSLASH).append(HOME_RECOMMEND_PARAMETERS.PROVINCE_ID).append(CHARACTER_BACKSLASH).append(URLEncoder.encode(arrGeoCode[1], "UTF-8")).append(CHARACTER_BACKSLASH).append(HOME_RECOMMEND_PARAMETERS.DISTRICT_ID).append(CHARACTER_BACKSLASH).append(URLEncoder.encode(arrGeoCode[2], "UTF-8")).append(CHARACTER_BACKSLASH).append(HOME_RECOMMEND_PARAMETERS.CITY_LEVEL).append(CHARACTER_BACKSLASH).append(URLEncoder.encode(arrGeoCode[3], "UTF-8")).append(CHARACTER_BACKSLASH).append(HOME_RECOMMEND_PARAMETERS.LOCATION).append(CHARACTER_BACKSLASH).append(URLEncoder.encode(arrGeoCode[4], "UTF-8"));
                            sBackslashLocationMessage = sb.toString();
                        }
                        return sBackslashLocationMessage;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean isInHongKong() {
        return TextUtils.equals(getCountryCode(), COUNTRY_HONGKONG);
    }

    public static void resetLoacationMessage() {
        countryCode = "";
        sLocationgMessage = "";
        sAndLocationMessage = "";
        sBackslashLocationMessage = "";
    }

    public static boolean isTopRunning(Context context) {
        List<RunningTaskInfo> tasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = ((RunningTaskInfo) tasks.get(0)).topActivity;
            LogInfo.log("plugin", "topActivity = " + topActivity.toString());
            LogInfo.log("plugin", "topActivity.getPackageName() = " + topActivity.getPackageName());
            LogInfo.log("plugin", "context.getPackageName() = " + context.getPackageName());
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static String getBelongArea() {
        return isInHongKong() ? HK_BELONG_AREA : CN_BELONG_AREA;
    }
}
