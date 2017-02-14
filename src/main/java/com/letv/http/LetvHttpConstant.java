package com.letv.http;

public final class LetvHttpConstant {
    public static final int CONNECT_TIMEOUT = 20000;
    public static final String LOG = "LetvHttp";
    public static final int READ_TIMEOUT = 20000;
    public static boolean isDebug = false;

    public static void setDebug(boolean isDebug) {
        isDebug = isDebug;
    }
}
