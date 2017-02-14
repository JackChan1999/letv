package com.letv.mobile.lebox.config;

import android.text.TextUtils;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;

public class LeBoxAppConfig {
    private static final String APP_NAME = "appName";
    private static final String INVALID_LEBOX_DOMAIN = "http://0.0.0.0";
    private static final String TAG = "LeBoxAppConfig";
    private static LeBoxAppConfig sAppConfig;
    private final String mAppName = "leBox";
    private String mDynamicDomain = "";

    private static LeBoxAppConfig getInstance() {
        if (sAppConfig != null) {
            return sAppConfig;
        }
        LeBoxAppConfig leBoxAppConfig;
        synchronized (LeBoxAppConfig.class) {
            if (sAppConfig == null) {
                sAppConfig = new LeBoxAppConfig();
            }
            leBoxAppConfig = sAppConfig;
        }
        return leBoxAppConfig;
    }

    private LeBoxAppConfig() {
        init();
    }

    public void init() {
        this.mDynamicDomain = "";
    }

    public static String getAppName() {
        getInstance().getClass();
        return "leBox";
    }

    public static String getDynamicDomain() {
        if (TextUtils.isEmpty(getInstance().mDynamicDomain.trim())) {
            if (LeBoxNetworkManager.getInstance().isLeboxWifi()) {
                getInstance().mDynamicDomain = "http://" + LeBoxNetworkManager.getInstance().getWifiGateway();
            } else if (!TextUtils.isEmpty(LeBoxNetworkManager.getInstance().getP2pDeviceGateway())) {
                getInstance().mDynamicDomain = "http://" + LeBoxNetworkManager.getInstance().getP2pDeviceGateway();
            }
        }
        if (INVALID_LEBOX_DOMAIN.equals(getInstance().mDynamicDomain)) {
            getInstance().mDynamicDomain = "";
        }
        return getInstance().mDynamicDomain;
    }

    public static void setDynamicDomain(String dynamicDomain) {
        if (!TextUtils.isEmpty(dynamicDomain) && !LeBoxNetworkManager.INVALID_LEBOX_IP.equals(dynamicDomain)) {
            if (dynamicDomain.startsWith("http://")) {
                getInstance().mDynamicDomain = dynamicDomain;
                return;
            }
            getInstance().mDynamicDomain = "http://" + dynamicDomain;
        }
    }

    public static String getStaticDomain() {
        return "";
    }

    public static String getReportDomain() {
        return "";
    }

    public static String[] getDynamicLoopingIps() {
        return null;
    }

    public static String[] getStaticLoopingIps() {
        return null;
    }

    public static boolean isHttpDebug() {
        return true;
    }

    public static boolean isNeedHttpCache() {
        return false;
    }

    public static boolean isNeedIpLooping() {
        return false;
    }
}
