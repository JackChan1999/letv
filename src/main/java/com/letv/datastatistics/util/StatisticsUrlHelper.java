package com.letv.datastatistics.util;

public class StatisticsUrlHelper {
    private static final String BASE_URL = "http://apple.www.letv.com/";
    private static final String BASE_URL_TEST = "http://develop.bigdata.letv.com/";
    public static boolean isTest = false;
    public static String mDir = "0dz2";

    private static String getBaseUrl() {
        return isTest ? BASE_URL_TEST + mDir + "/" : BASE_URL;
    }

    public static String getStatisticsLoginUrl() {
        return getBaseUrl() + "lg/?";
    }

    public static String getStatisticsEnvUrl() {
        return getBaseUrl() + "env/?";
    }

    public static String getStatisticsActionUrl() {
        return getBaseUrl() + "op/?";
    }

    public static String getStatisticsPlayUrl() {
        return getBaseUrl() + "pl/?";
    }

    public static String getStatisticsErrorUrl() {
        return getBaseUrl() + "er/?";
    }

    public static String getStatisticsPVUrl() {
        return getBaseUrl() + "pgv/?";
    }

    public static String getStatisticsQueryUrl() {
        return getBaseUrl() + "qy/?";
    }
}
