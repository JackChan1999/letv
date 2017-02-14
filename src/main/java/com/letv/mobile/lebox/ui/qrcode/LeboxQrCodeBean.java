package com.letv.mobile.lebox.ui.qrcode;

import com.letv.mobile.http.utils.StringUtils;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.SharedPreferencesUtil;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class LeboxQrCodeBean {
    public static final String KEY_CODE = "CODE";
    public static final String KEY_MAC = "MAC";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_SCHEME = "SCHEME";
    public static final String KEY_SSID = "SSID";
    public static final String KEY_VERSION = "VERSION";
    public static final String TAG = "LeboxQrCodeModel";
    private static String code;
    private static String mac;
    private static String password;
    private static String scheme;
    private static String ssid;
    private static String version;

    private LeboxQrCodeBean() {
    }

    public static boolean isWifiText(String wifiText) {
        if (wifiText == null) {
            return false;
        }
        try {
            String code;
            JSONObject jsonObj = new JSONObject(wifiText);
            String scheme = jsonObj.getString(KEY_SCHEME);
            String version = jsonObj.getString(KEY_VERSION);
            String mac = jsonObj.getString(KEY_MAC);
            String ssid = jsonObj.getString(KEY_SSID);
            String password = jsonObj.getString(KEY_PASSWORD);
            if (jsonObj.has(KEY_CODE)) {
                code = jsonObj.getString(KEY_CODE);
            } else {
                code = getCode();
            }
            if (StringUtils.equalsNull(scheme) || StringUtils.equalsNull(version) || StringUtils.equalsNull(mac) || StringUtils.equalsNull(ssid) || StringUtils.equalsNull(password) || StringUtils.equalsNull(code)) {
                return false;
            }
            setScheme(scheme);
            setVersion(version);
            setMac(mac);
            setSsid(ssid);
            setPassword(password);
            setCode(code);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getScheme() {
        if (StringUtils.equalsNull(scheme)) {
            scheme = SharedPreferencesUtil.readData(KEY_SCHEME, "");
        }
        return scheme;
    }

    public static String getVersion() {
        if (StringUtils.equalsNull(version)) {
            version = SharedPreferencesUtil.readData(KEY_VERSION, "");
        }
        return version;
    }

    public static String getMac() {
        if (StringUtils.equalsNull(mac)) {
            mac = SharedPreferencesUtil.readData(KEY_MAC, "");
        }
        return mac;
    }

    public static String getSsid() {
        if (StringUtils.equalsNull(ssid)) {
            ssid = SharedPreferencesUtil.readData(KEY_SSID, "");
        }
        return ssid;
    }

    public static String getDirectSsid() {
        if (StringUtils.equalsNull(getSsid())) {
            return "";
        }
        return "DIRECT-" + getSsid();
    }

    public static String getPassword() {
        if (StringUtils.equalsNull(password)) {
            password = SharedPreferencesUtil.readData(KEY_PASSWORD, "");
        }
        return password;
    }

    public static String getCode() {
        if (StringUtils.equalsNull(code)) {
            code = SharedPreferencesUtil.readData(KEY_CODE, "");
            if (StringUtils.equalsNull(code)) {
                setCode(UUID.randomUUID().toString());
            }
        }
        Logger.d(TAG, "get code = " + code);
        return code;
    }

    public static void setScheme(String scheme) {
        scheme = scheme;
        SharedPreferencesUtil.writeData(KEY_SCHEME, scheme);
    }

    public static void setVersion(String version) {
        version = version;
        SharedPreferencesUtil.writeData(KEY_VERSION, version);
    }

    public static void setMac(String mac) {
        mac = mac;
        SharedPreferencesUtil.writeData(KEY_MAC, mac);
    }

    public static void setSsid(String ssid) {
        ssid = ssid;
        SharedPreferencesUtil.writeData(KEY_SSID, ssid);
    }

    public static void setPassword(String password) {
        password = password;
        SharedPreferencesUtil.writeData(KEY_PASSWORD, password);
    }

    public static void setCode(String code) {
        code = code;
        SharedPreferencesUtil.writeData(KEY_CODE, code);
    }

    public static void removeScheme() {
        scheme = "";
        SharedPreferencesUtil.removeData(KEY_SCHEME);
    }

    public static void removeVersion() {
        version = "";
        SharedPreferencesUtil.removeData(KEY_VERSION);
    }

    public static void removeMac() {
        mac = "";
        SharedPreferencesUtil.removeData(KEY_MAC);
    }

    public static void removeSsid() {
        ssid = "";
        SharedPreferencesUtil.removeData(KEY_SSID);
    }

    public static void removePassword() {
        password = "";
        SharedPreferencesUtil.removeData(KEY_PASSWORD);
    }

    public static boolean isAllKeyValueNotNull() {
        if (StringUtils.equalsNull(getScheme()) || StringUtils.equalsNull(getVersion()) || StringUtils.equalsNull(getMac()) || StringUtils.equalsNull(getSsid()) || StringUtils.equalsNull(getPassword()) || StringUtils.equalsNull(getCode())) {
            return false;
        }
        return true;
    }

    public static void cleanAllInfo() {
        removeScheme();
        removeVersion();
        removeMac();
        removeSsid();
        removePassword();
    }
}
