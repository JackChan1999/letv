package com.letv.core.api;

public class LetvHttpApiConfig {
    public static final String IP_BASE_URL = "http://api.letv.com/getipgeo";
    public static String PCODE = null;
    private static final String STATIC_APP_BASE_END = ".mindex.html";
    public static String VERSION;

    public static void initialize(String pcode, String version) {
        PCODE = pcode;
        VERSION = version;
    }

    public static String getStaticEnd() {
        return STATIC_APP_BASE_END;
    }

    public static String getQQLoginUrl() {
        return "http://dynamic.app.m.letv.com/android/dynamic.php?mod=passport&ctl=index&act=appqq&pcode=" + PCODE + "&version=" + VERSION;
    }

    public static String getSinaLoginUrl() {
        return "http://dynamic.app.m.letv.com/android/dynamic.php?mod=passport&ctl=index&act=appsina&pcode=" + PCODE + "&version=" + VERSION;
    }
}
