package com.letv.datastatistics.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.letv.datastatistics.DataStatistics;
import com.letv.lemallsdk.util.Constants;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public final class DataUtils {
    private static final String TAG = "DataUtils_GetDeviceId";

    private DataUtils() {
    }

    public static String getData(String data) {
        if (data == null || data.length() <= 0) {
            return NetworkUtils.DELIMITER_LINE;
        }
        return removeQuote(data.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
    }

    public static String getTrimData(String data) {
        return TextUtils.isEmpty(data) ? NetworkUtils.DELIMITER_LINE : data.trim();
    }

    public static String getDataEmpty(String data) {
        if (data == null || data.length() <= 0) {
            return "";
        }
        return data.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    public static String getData(long data) {
        return data <= 0 ? NetworkUtils.DELIMITER_LINE : String.valueOf(data);
    }

    public static String removeQuote(String data) {
        if (TextUtils.isEmpty(data) || !data.contains("\"")) {
            return data;
        }
        return data.replace("\"", "");
    }

    public static NetworkInfo getAvailableNetWorkInfo(Context context) {
        if (context == null) {
            return null;
        }
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return null;
        }
        return activeNetInfo;
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

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "";
    }

    public static String generateDeviceId(Context context) {
        log(TAG, "*****generateDeviceId start*****");
        String devicesID = getIMEI(context) + getIMSI(context) + getDeviceName() + getBrandName() + getMacAddress(context);
        String encodeDevId = MD5Helper(devicesID);
        log(TAG, "***combine deviceid***deviceid:" + encodeDevId + ", before_md5:" + devicesID);
        log(TAG, "*****generateDeviceId end*****deviceid:" + encodeDevId);
        return encodeDevId;
    }

    private static String generate_DeviceId(Context context) {
        log(TAG, "***combine imsi start***");
        String str = getIMEI(context) + getDeviceName() + getBrandName() + getMacAddress(context);
        String imsi = MD5Helper(str);
        log(TAG, "***combine imsi end***imsi:" + imsi + ", before_md5:" + str);
        return imsi;
    }

    public static String getUUID(Context context) {
        return generateDeviceId(context) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + System.currentTimeMillis();
    }

    public static String MD5Helper(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(byteArray[i] & 255).length() == 1) {
                    sb.append("0").append(Integer.toHexString(byteArray[i] & 255));
                } else {
                    sb.append(Integer.toHexString(byteArray[i] & 255));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("no device Id");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            throw new RuntimeException("no device Id");
        }
    }

    public static String getIMEI(Context context) {
        String deviceId = "";
        if (context == null) {
            return deviceId;
        }
        try {
            deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            deviceId = TextUtils.isEmpty(deviceId) ? "" : deviceId.replace(" ", "");
            log(TAG, "get imei:" + deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    public static String getEncryptIMEI(Context context, String key) {
        String imei = getIMEI(context);
        return TextUtils.isEmpty(imei) ? NetworkUtils.DELIMITER_LINE : DEs.encode(key, imei.getBytes());
    }

    public static String getEncryptIMSI(Context context, String key) {
        String subscriberId = NetworkUtils.DELIMITER_LINE;
        if (context != null) {
            try {
                subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
                return TextUtils.isEmpty(subscriberId) ? NetworkUtils.DELIMITER_LINE : DEs.encode(key, subscriberId.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return subscriberId;
    }

    public static String getIMSI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            log(TAG, "get imsi:" + subscriberId);
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

    public static String getResolution(Context context) {
        if (context == null) {
            return "";
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels + "*" + dm.heightPixels;
    }

    public static String getNewResolution(Context context) {
        if (context == null) {
            return "";
        }
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + dm.heightPixels;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDensity(Context context) {
        if (context == null) {
            return "";
        }
        return String.valueOf(context.getResources().getDisplayMetrics().density);
    }

    public static String getDeviceName() {
        String model = Build.MODEL;
        log(TAG, "get deviceName:" + model);
        if (model == null || model.length() <= 0) {
            return "";
        }
        return model;
    }

    public static String getBrandName() {
        String brand = Build.BRAND;
        log(TAG, "get brandName:" + brand);
        if (brand == null || brand.length() <= 0) {
            return "";
        }
        return brand;
    }

    public static String getClientVersionName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSystemName() {
        return AbstractSpiCall.ANDROID_CLIENT_TYPE;
    }

    public static String getOSVersionName() {
        return VERSION.RELEASE;
    }

    public static String getExtraStr(String... params) {
        StringBuilder sb = new StringBuilder();
        for (String s : params) {
            sb.append(s).append(";");
        }
        String retStr = sb.toString();
        if (retStr.length() > 1) {
            return retStr.substring(0, retStr.length() - 1);
        }
        return retStr;
    }

    public static String getExtraStr(List<String> list) {
        return getExtraStr((String[]) list.toArray(new String[list.size()]));
    }

    public static String getIds(String pid, String vid) {
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(pid)) {
            pid = NetworkUtils.DELIMITER_LINE;
        }
        builder.append(pid);
        builder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        if (TextUtils.isEmpty(vid)) {
            vid = NetworkUtils.DELIMITER_LINE;
        }
        builder.append(vid);
        return builder.toString();
    }

    public static String getErrorMessage(String pcode, String did, String version, String actionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(did).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(System.currentTimeMillis()).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(version).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(actionId).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(pcode);
        return sb.toString();
    }

    public static String timeClockString(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static int getCpuFrequency(int type) {
        FileNotFoundException e;
        Throwable th;
        IOException e2;
        String path = "/sys/devices/system/cpu/cpu0/cpufreq/";
        if (type == 1) {
            path = path + "cpuinfo_max_freq";
        } else if (type == 2) {
            path = path + "cpuinfo_min_freq";
        } else {
            path = path + "scaling_cur_freq";
        }
        int result = 0;
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(path));
            try {
                result = Integer.parseInt(br2.readLine().trim()) / 1000;
                if (br2 != null) {
                    try {
                        br2.close();
                        br = br2;
                    } catch (IOException e3) {
                        br = br2;
                    }
                }
            } catch (FileNotFoundException e4) {
                e = e4;
                br = br2;
                try {
                    e.printStackTrace();
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e5) {
                        }
                    }
                    return result;
                } catch (Throwable th2) {
                    th = th2;
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e6) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e7) {
                e2 = e7;
                br = br2;
                e2.printStackTrace();
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e8) {
                    }
                }
                return result;
            } catch (Throwable th3) {
                th = th3;
                br = br2;
                if (br != null) {
                    br.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e9) {
            e = e9;
            e.printStackTrace();
            if (br != null) {
                br.close();
            }
            return result;
        } catch (IOException e10) {
            e2 = e10;
            e2.printStackTrace();
            if (br != null) {
                br.close();
            }
            return result;
        }
        return result;
    }

    public static String getConnectWifiSsid(Context context) {
        if (context == null) {
            return "";
        }
        String ssid = "";
        try {
            return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getSSID();
        } catch (Exception e) {
            return ssid;
        }
    }

    public static String getMacAddress(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            log(TAG, "get macaddress:" + macAddress);
            if (macAddress == null || macAddress.length() <= 0) {
                return "";
            }
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getNoColonMacAddress(Context context) {
        String macAddress = getMacAddress(context);
        return TextUtils.isEmpty(macAddress) ? "" : macAddress.replace(NetworkUtils.DELIMITER_COLON, "");
    }

    public static String getWifiMacAddress(Context context) {
        WifiInfo info = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        String ipString = NetworkUtils.DELIMITER_LINE;
        if (info == null) {
            return ipString;
        }
        int ipAddress = info.getIpAddress();
        if (ipAddress > 0) {
            return (ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255);
        }
        return ipString;
    }

    public static String getUnEmptyData(String data) {
        return TextUtils.isEmpty(data) ? NetworkUtils.DELIMITER_LINE : data;
    }

    public static int getCpuNumCores() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static long getMemoryTotalSize() {
        Exception e;
        Throwable th;
        long mTotal = 0;
        String content = null;
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader("/proc/meminfo"), 8);
            try {
                String line = br2.readLine();
                if (line != null) {
                    content = line;
                }
                if (!TextUtils.isEmpty(content)) {
                    mTotal = (long) (Integer.parseInt(content.substring(content.indexOf(58) + 1, content.indexOf(107)).trim()) / 1024);
                }
                if (br2 != null) {
                    try {
                        br2.close();
                        br = br2;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        br = br2;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                br = br2;
                try {
                    e.printStackTrace();
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    return mTotal;
                } catch (Throwable th2) {
                    th = th2;
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                br = br2;
                if (br != null) {
                    br.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (br != null) {
                br.close();
            }
            return mTotal;
        }
        return mTotal;
    }

    public static void setUuidToLocal(Context context, String uuid) {
        Editor editor = context.getSharedPreferences("statistics", 4).edit();
        editor.putString(Constants.UUID, uuid);
        editor.commit();
    }

    public static String getLocalUuid(Context context) {
        return context.getSharedPreferences("statistics", 4).getString(Constants.UUID, "");
    }

    public static String getCloudUUID() {
        String uuid = getSystemProperty("ro.aliyun.clouduuid");
        if (TextUtils.isEmpty(uuid)) {
            return getSystemProperty("ro.sys.aliyun.clouduuid");
        }
        return uuid;
    }

    private static String getSystemProperty(String key) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{key, ""});
        } catch (Exception e) {
            return "";
        }
    }

    private static void log(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg) && DataStatistics.getInstance().isDebug()) {
            Log.e(tag, msg);
        }
    }
}
