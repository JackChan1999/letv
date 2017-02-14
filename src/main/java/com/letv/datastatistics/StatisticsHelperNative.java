package com.letv.datastatistics;

public class StatisticsHelperNative {
    private static StatisticsHelperNative instance = new StatisticsHelperNative();

    public native String encrypt_deviceinfo();

    public static StatisticsHelperNative getInstance() {
        return instance;
    }

    static {
        System.loadLibrary("StatisticsHelper");
    }
}
