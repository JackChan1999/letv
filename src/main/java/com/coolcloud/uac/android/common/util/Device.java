package com.coolcloud.uac.android.common.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Device {
    private static final String DEFAULT_IMEI = "360_DEFAULT_IMEI";
    private static final String KEY_DEVICE_ID = "persist.coolcloud.devid";
    private static final String TAG = "Device";
    private static String m2;
    private static String sDeviceId = null;

    public static String getDeviceType() {
        return AbstractSpiCall.ANDROID_CLIENT_TYPE;
    }

    public static String getDeviceModel() {
        String deviceModel;
        if (Build.MODEL.startsWith(Build.BRAND)) {
            deviceModel = Build.MODEL;
        } else {
            deviceModel = Build.BRAND + Build.MODEL;
        }
        if (empty(deviceModel)) {
            return deviceModel;
        }
        return deviceModel.replaceAll(" ", "");
    }

    public static String readAndSyncDeviceId(Context context) {
        return null;
    }

    public static boolean replaceSettingsDeviceId(Context context, String nativeDeviceId, String settingsDeviceId) {
        FLOG.d(TAG, "[nativeDeviceId:" + nativeDeviceId + "][settingsDeviceId:" + settingsDeviceId + "] replace Settings DeviceId...");
        if (TextUtils.equal(nativeDeviceId, settingsDeviceId)) {
            LOG.d(TAG, "equal return");
            return true;
        } else if (valid(nativeDeviceId)) {
            FLOG.d(TAG, "not equal nativeDeviceId valid sync to Settings");
            return writeToSettings(context, nativeDeviceId);
        } else {
            FLOG.d(TAG, "not equal nativeDeviceId invalid ignore");
            return true;
        }
    }

    public static String getNativeDeviceId(Context context) {
        boolean ok = false;
        String deviceId0 = "";
        String deviceId1 = "";
        try {
            Class<?> clazz = Class.forName("com.yulong.android.telephony.CPTelephonyManager");
            Method getDefault = clazz.getMethod("getDefault", new Class[0]);
            Method getDualDeviceId = clazz.getMethod("getDualDeviceId", new Class[]{Integer.TYPE});
            Object receiver = getDefault.invoke(null, new Object[0]);
            deviceId0 = (String) getDualDeviceId.invoke(receiver, new Object[]{Integer.valueOf(0)});
            deviceId1 = (String) getDualDeviceId.invoke(receiver, new Object[]{Integer.valueOf(1)});
            ok = true;
        } catch (ClassNotFoundException e) {
            LOG.w(TAG, "invoke getDualDeviceId failed(ClassNotFoundException): " + e.getMessage());
        } catch (NoClassDefFoundError e2) {
            LOG.w(TAG, "invoke getDualDeviceId failed(NoClassDefFoundError): " + e2.getMessage());
        } catch (Throwable e3) {
            LOG.w(TAG, "invoke getDualDeviceId failed(Throwable): " + e3.getMessage());
        }
        if (!ok) {
            try {
                clazz = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
                getDefault = clazz.getMethod("getDefault", new Class[0]);
                Method getDeviceId = clazz.getMethod("getDeviceId", new Class[]{Integer.TYPE});
                receiver = getDefault.invoke(null, new Object[0]);
                deviceId0 = (String) getDeviceId.invoke(receiver, new Object[]{Integer.valueOf(0)});
                deviceId1 = (String) getDeviceId.invoke(receiver, new Object[]{Integer.valueOf(1)});
            } catch (ClassNotFoundException e4) {
                LOG.w(TAG, "invoke getDeviceId failed(ClassNotFoundException): " + e4.getMessage());
            } catch (NoClassDefFoundError e22) {
                LOG.w(TAG, "invoke getDeviceId failed(NoClassDefFoundError): " + e22.getMessage());
            } catch (Throwable e32) {
                LOG.w(TAG, "invoke getDeviceId failed(Throwable): " + e32.getMessage());
            }
        }
        return getSystemDeviceId(context, deviceId0, deviceId1);
    }

    private static String getSystemDeviceId(Context context, String deviceId0, String deviceId1) {
        String defaultValue = "000000000000000";
        if (!valid(deviceId0)) {
            deviceId0 = deviceId1;
            if (!valid(deviceId0)) {
                deviceId0 = getNativeDeviceId0(context);
                deviceId1 = getNativeDeviceId1(context);
                if (!valid(deviceId0)) {
                    deviceId0 = deviceId1;
                    if (!valid(deviceId0)) {
                        deviceId0 = getSystemDeviceID(context);
                        if (valid(deviceId0)) {
                        }
                    }
                }
            }
        }
        if (!valid(deviceId1) || TextUtils.equal(deviceId1, deviceId0)) {
            deviceId1 = "000000000000000";
        }
        String deviceId = "";
        if (valid(deviceId0)) {
            deviceId = deviceId0 + NetworkUtils.DELIMITER_LINE + deviceId1;
        }
        LOG.i(TAG, "[deviceId:" + deviceId + "] get System deviceId done ...");
        return deviceId;
    }

    private static String getNativeDeviceId0(Context context) {
        String deviceId0 = "";
        try {
            Class<?> clazz = Class.forName("android.telephony.TelephonyManager");
            Method getDefault = clazz.getMethod("getDefault", new Class[0]);
            return (String) clazz.getMethod("getDeviceId", new Class[]{Integer.TYPE}).invoke(getDefault.invoke(null, new Object[0]), new Object[]{Integer.valueOf(0)});
        } catch (ClassNotFoundException e) {
            FLOG.w(TAG, "invoke getNativeDeviceId0 failed(ClassNotFoundException): " + e.getMessage());
            return deviceId0;
        } catch (NoClassDefFoundError e2) {
            FLOG.w(TAG, "invoke getNativeDeviceId0 failed(NoClassDefFoundError): " + e2.getMessage());
            return deviceId0;
        } catch (Throwable e3) {
            FLOG.w(TAG, "invoke getNativeDeviceId0 failed(Throwable): " + e3.getMessage());
            return deviceId0;
        }
    }

    private static String getNativeDeviceId1(Context context) {
        String deviceId1 = "";
        try {
            Class<?> clazz = Class.forName("android.telephony.TelephonyManager");
            Method getDefault = clazz.getMethod("getDefault", new Class[0]);
            return (String) clazz.getMethod("getDeviceId", new Class[]{Integer.TYPE}).invoke(getDefault.invoke(null, new Object[0]), new Object[]{Integer.valueOf(1)});
        } catch (ClassNotFoundException e) {
            FLOG.w(TAG, "invoke getNativeDeviceId1 failed(ClassNotFoundException): " + e.getMessage());
            return deviceId1;
        } catch (NoClassDefFoundError e2) {
            FLOG.w(TAG, "invoke getNativeDeviceId1 failed(NoClassDefFoundError): " + e2.getMessage());
            return deviceId1;
        } catch (Throwable e3) {
            FLOG.w(TAG, "invoke getNativeDeviceId1 failed(Throwable): " + e3.getMessage());
            return deviceId1;
        }
    }

    public static synchronized String getDeviceId(Context context) {
        String deviceId;
        synchronized (Device.class) {
            deviceId = null;
            if (context != null) {
                deviceId = getNativeDeviceId(context);
            }
            if (valid(deviceId)) {
                LOG.i(TAG, "get device from  Native succeed");
            } else {
                deviceId = getCustomAndSync(context);
            }
        }
        return deviceId;
    }

    private static synchronized String getCustomAndSync(Context context) {
        String str;
        synchronized (Device.class) {
            if (!valid(sDeviceId)) {
                String deviceId = readFromSettings(context, null);
                if (!valid(deviceId)) {
                    deviceId = generateDeviceId(context);
                    if (valid(deviceId)) {
                        writeToSettings(context, deviceId);
                    }
                }
                if (valid(deviceId)) {
                    sDeviceId = deviceId;
                }
            }
            str = sDeviceId;
        }
        return str;
    }

    protected static synchronized String getSettingsDeviceId(Context context) {
        String deviceId;
        synchronized (Device.class) {
            deviceId = readFromSettings(context, null);
        }
        return deviceId;
    }

    private static String generateDeviceId(Context context) {
        String defaultValue = "000000000000000";
        String deviceId0 = "";
        String deviceId1 = "";
        deviceId0 = getMACAddress(context);
        if (!valid(deviceId0)) {
            deviceId0 = getAndroidId(context);
            if (valid(deviceId0)) {
                deviceId1 = nowMillis();
            } else {
                deviceId0 = getSerial();
                if (valid(deviceId0)) {
                    deviceId1 = nowMillis();
                } else {
                    deviceId0 = getDefault();
                    deviceId1 = nowMillis();
                }
            }
        }
        if (!valid(deviceId0)) {
            deviceId0 = getDefault();
        }
        if (!valid(deviceId1)) {
            deviceId1 = "000000000000000";
        }
        String deviceId = deviceId0 + NetworkUtils.DELIMITER_LINE + deviceId1;
        if (context != null) {
            writeToLog(context, deviceId);
        }
        FLOG.i(TAG, "generate custom deviceId: " + deviceId);
        return deviceId;
    }

    private static void writeToLog(Context context, String deviceId) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String packageName = context.getPackageName();
            int versionCode = packageInfo.versionCode;
            FLOG.i(TAG, "[packageName:" + packageName + "][versionCode:" + versionCode + "][versionName" + packageInfo.versionName + "][deviceId" + deviceId + "]");
        } catch (Exception e) {
            FLOG.i(TAG, "write device Id to String log: (Exception)" + e);
        } catch (Throwable e2) {
            FLOG.i(TAG, "write device Id to String log: (Throwable)" + e2);
        }
    }

    @TargetApi(9)
    private static String getSerial() {
        try {
            return Build.SERIAL;
        } catch (Throwable e) {
            FLOG.w(TAG, "get serial failed(Throwable): " + e.getMessage());
            return null;
        }
    }

    private static String getDefault() {
        return replace(getDeviceModel(), "[^0-9a-zA-Z]", "") + nowMillis();
    }

    protected static String getMACAddress(Context context) {
        try {
            WifiManager manager = (WifiManager) context.getSystemService("wifi");
            if (manager != null) {
                return replace(manager.getConnectionInfo().getMacAddress(), NetworkUtils.DELIMITER_COLON, "");
            }
        } catch (Throwable t) {
            FLOG.e(TAG, "get mac address failed(Throwable)", t);
        }
        return null;
    }

    private static String getAndroidId(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable e) {
            FLOG.w(TAG, "get android ID failed(Throwable): " + e.getMessage());
            return "";
        }
    }

    private static String getSystemDeviceID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm != null) {
            return tm.getDeviceId();
        }
        FLOG.e(TAG, "get telephony service failed while getting deviceId");
        return null;
    }

    public static boolean valid(String id) {
        return (empty(id) || id.matches("[0]+")) ? false : true;
    }

    private static String replace(String s, String regular, String replace) {
        if (empty(s)) {
            return s;
        }
        return s.replaceAll(regular, replace);
    }

    private static boolean empty(String s) {
        return s == null || s.length() <= 0;
    }

    private static String nowMillis() {
        String pattern = "yyyyMMddHHmmssSSS";
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date());
    }

    private static boolean writeToSettings(Context context, String deviceId) {
        boolean result = false;
        try {
            result = putSettingsString(context, deviceId);
            FLOG.i(TAG, "[deviceId:" + deviceId + "] write to settings(" + result + ")");
            return result;
        } catch (Throwable e) {
            FLOG.w(TAG, "[deviceId:" + deviceId + "] write to settings failed(Throwable): " + e.getMessage());
            return result;
        }
    }

    private static String readFromSettings(Context context, String defaultValue) {
        String deviceId = defaultValue;
        try {
            String value = getSettingsString(context);
            if (empty(value)) {
                deviceId = defaultValue;
            } else {
                deviceId = value;
            }
            FLOG.i(TAG, "[default:" + defaultValue + "] read from settings(" + deviceId + ")");
        } catch (Throwable e) {
            FLOG.w(TAG, "[defaultDeviceId:" + defaultValue + "] read from settings failed(Throwable): " + e.getMessage());
        }
        return deviceId;
    }

    private static boolean putSettingsString(Context context, String deviceId) {
        ContentResolver resolver = context.getContentResolver();
        boolean ok = System.putString(resolver, KEY_DEVICE_ID, deviceId);
        System.putString(resolver, "persit.coolcloud.devid", deviceId);
        return ok;
    }

    private static String getSettingsString(Context context) {
        ContentResolver resolver = context.getContentResolver();
        String value = System.getString(resolver, KEY_DEVICE_ID);
        return empty(value) ? System.getString(resolver, "persit.coolcloud.devid") : value;
    }

    public static String getFreeCallDeviceId(Context context) {
        if (!TextUtils.isEmpty(m2)) {
            return m2;
        }
        String imei = getIMEI(context);
        String AndroidID = System.getString(context.getContentResolver(), "android_id");
        String _m2 = getMD5("" + imei + AndroidID + getDeviceSerialForMid2());
        m2 = _m2;
        return _m2;
    }

    public static String getIMEI(Context mContext) {
        PreferenceUtil.getInstance().init(mContext);
        String imei = PreferenceUtil.getInstance().getString(DEFAULT_IMEI, "");
        if (TextUtils.isEmpty(imei)) {
            imei = ((TelephonyManager) mContext.getSystemService("phone")).getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                PreferenceUtil.getInstance().putString(DEFAULT_IMEI, imei);
            }
        }
        if (TextUtils.isEmpty(imei)) {
            return DEFAULT_IMEI;
        }
        return imei;
    }

    public static String getMD5(String input) {
        return getMD5(input.getBytes());
    }

    public static String getMD5(byte[] input) {
        return bytesToHexString(MD5(input));
    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        String table = "0123456789abcdef";
        char[] cchars = new char[(bytes.length * 2)];
        int i = 0;
        int k = 0;
        while (i < bytes.length) {
            cchars[k] = table.charAt((bytes[i] >> 4) & 15);
            k++;
            cchars[k] = table.charAt(bytes[i] & 15);
            i++;
            k++;
        }
        return String.valueOf(cchars);
    }

    public static byte[] MD5(byte[] input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md == null) {
            return null;
        }
        md.update(input);
        return md.digest();
    }

    private static String getDeviceSerialForMid2() {
        String serial = "";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            return (String) c.getMethod("get", new Class[]{String.class}).invoke(c, new Object[]{"ro.serialno"});
        } catch (Exception e) {
            return serial;
        }
    }

    public static String getMeidId(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
    }
}
