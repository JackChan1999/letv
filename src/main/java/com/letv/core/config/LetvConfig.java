package com.letv.core.config;

import com.letv.core.utils.LetvUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LetvConfig {
    public static final int NORMAL_API = 1;
    public static final int SERVER_API = 2;
    public static double SLOW_TIME_OUT = 1.5d;
    public static final int TEST_API = 0;
    private static String appKey;
    private static String clientId;
    private static boolean debug;
    private static String[] delayUpgradeList;
    private static boolean errorCatch;
    private static String flurryKey;
    private static String[] googlePlaylist;
    private static boolean haveAd;
    private static boolean isForTest;
    private static boolean jingpin;
    private static String[] longWelcomePcodeList;
    private static String[] noExitRetainPopupList;
    private static String pcode;
    private static String source;
    private static String[] specialPcodeList;
    private static boolean umeng;
    private static String umengID = "0000";

    static {
        InputStream in = null;
        try {
            Properties properties = new Properties();
            in = LetvConfig.class.getClassLoader().getResourceAsStream("letv.properties");
            if (in != null) {
                properties.load(in);
            }
            source = properties.getProperty("letv.source");
            flurryKey = properties.getProperty("letv.flurry.key");
            debug = Boolean.parseBoolean(properties.getProperty("letv.debug"));
            pcode = properties.getProperty("letv.pcode");
            appKey = properties.getProperty("letv.appkey");
            clientId = properties.getProperty("letv.clientId");
            errorCatch = Boolean.parseBoolean(properties.getProperty("letv.error.catch"));
            jingpin = Boolean.parseBoolean(properties.getProperty("letv.jingpin"));
            umeng = Boolean.parseBoolean(properties.getProperty("letv.umeng"));
            haveAd = Boolean.parseBoolean(properties.getProperty("letv.havead"));
            isForTest = Boolean.parseBoolean(properties.getProperty("letv.fortest"));
            Properties properties_special = new Properties();
            properties_special.load(LetvConfig.class.getClassLoader().getResourceAsStream("assets/channel-maps-special.properties"));
            specialPcodeList = properties_special.getProperty("pcodes", "0").split(",");
            noExitRetainPopupList = properties_special.getProperty("noRetainPopups", "0").split(",");
            longWelcomePcodeList = properties_special.getProperty("longWelcomePcode", "0").split(",");
            googlePlaylist = properties_special.getProperty("googlePlay", "0").split(",");
            delayUpgradeList = properties_special.getProperty("delayUpgrade", "0").split(",");
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        }
    }

    public static String getSource() {
        return source;
    }

    public static String getFlurryKey() {
        return flurryKey;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static String getPcode() {
        return pcode;
    }

    public static boolean isErrorCatch() {
        return errorCatch;
    }

    public static boolean isJingpin() {
        return jingpin;
    }

    public static boolean isUmeng() {
        return umeng;
    }

    public static void setIsUmeng(boolean isUmeng) {
        umeng = isUmeng;
    }

    public static boolean isHaveAd() {
        return haveAd;
    }

    public static boolean isForTest() {
        return isForTest;
    }

    public static String getAppKey() {
        return appKey;
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getHKClientID() {
        return LetvUtils.isInHongKong() ? "1060419003" : clientId;
    }

    public static void setAppKey(String appkey) {
        appKey = appkey;
    }

    public static void setPCode(String pCode) {
        pcode = pCode;
    }

    public static String[] getSpecialPcodeList() {
        return specialPcodeList;
    }

    public static String[] getNoExitRetainPopupList() {
        return noExitRetainPopupList;
    }

    public static String[] getLongWelcomePcodeList() {
        return longWelcomePcodeList;
    }

    public static String getUmengID() {
        return umengID;
    }

    public static void setUmengID(String umengID) {
        umengID = umengID;
    }

    public static String[] getGooglePlaylist() {
        return googlePlaylist;
    }

    public static String[] getDelayUpgradeList() {
        return delayUpgradeList;
    }
}
