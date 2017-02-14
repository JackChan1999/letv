package com.letv.mobile.http;

public final class HttpGlobalConfig {
    private static boolean sIsDebug = true;
    private static boolean sIsNeedCache;
    private static boolean sIsNeedIpLooping;
    private static String sLetvDynamicDomain;
    private static String[] sLetvDynamicLoopingIps;
    private static String sLetvReportDomain;
    private static String sLetvStaticDomain;
    private static String[] sLetvStaticLoopingIps;

    public static void init(String dynamicDomain, String staticDomain, String reportDomain, String[] dynamicLoopingIps, String[] staticLoopingIps, boolean isNeedIpPolling, boolean isDebug, boolean isNeedCache) {
        sLetvDynamicDomain = dynamicDomain;
        sLetvStaticDomain = staticDomain;
        sLetvReportDomain = reportDomain;
        sLetvDynamicLoopingIps = dynamicLoopingIps;
        sLetvStaticLoopingIps = staticLoopingIps;
        sIsNeedIpLooping = isNeedIpPolling;
        sIsDebug = isDebug;
        sIsNeedCache = isNeedCache;
    }

    private HttpGlobalConfig() {
    }

    public static String getDynamicDomain() {
        return sLetvDynamicDomain;
    }

    public static String getStaticDomain() {
        return sLetvStaticDomain;
    }

    public static String getReportDomain() {
        return sLetvReportDomain;
    }

    public static String[] getCommonLoopingIps() {
        return sLetvDynamicLoopingIps;
    }

    public static String[] getStaticLoopingIps() {
        return sLetvStaticLoopingIps;
    }

    public static boolean isNeedIpLooping() {
        return sIsNeedIpLooping;
    }

    public static boolean isDebug() {
        return sIsDebug;
    }

    public static boolean isNeedCache() {
        return sIsNeedCache;
    }
}
