package com.letv.component.player.http;

public class HttpServerConfig {
    public static final String FEEDBACK_INIT_URL = "http://api.feedback.platform.letv.com/fb/init";
    public static final String FEEDBACK_INIT_URL_TEST = "http://test.push.platform.letv.com/fb/init";
    public static final String FEEDBACK_UPLOADLOG_URL = "http://api.feedback.platform.letv.com/fb/logUpload?fbid=%s";
    public static final String FEEDBACK_UPLOADLOG_URL_TEST = "http://test.push.platform.letv.com/fb/logUpload?fbid=%s";
    public static final String HARD_SOFT_DECODE_CAPABILITY = "dc/status";
    public static String HARD_SOFT_DECODE_REPORT = "dc/rpt";
    private static final String WHITE_BLACK_URL = "http://endecoding.go.letv.com/";
    private static final String WHITE_BLACK_URL_TEST = "http://117.121.2.70/";
    private static boolean sIsDebug = false;

    public static String getServerUrl() {
        if (sIsDebug) {
            return WHITE_BLACK_URL_TEST;
        }
        return WHITE_BLACK_URL;
    }

    public static String getFeedbackInitUrl() {
        if (sIsDebug) {
            return FEEDBACK_INIT_URL_TEST;
        }
        return FEEDBACK_INIT_URL;
    }

    public static String getFeedbackUploadlogUrl() {
        if (sIsDebug) {
            return FEEDBACK_UPLOADLOG_URL_TEST;
        }
        return FEEDBACK_UPLOADLOG_URL;
    }

    public static void setDebugMode(boolean isDebug) {
        sIsDebug = isDebug;
    }
}
