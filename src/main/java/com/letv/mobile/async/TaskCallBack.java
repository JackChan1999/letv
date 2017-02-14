package com.letv.mobile.async;

public interface TaskCallBack {
    public static final int CODE_ERROR_DATA = 1;
    public static final int CODE_ERROR_NETWORK_CONNECT = 2;
    public static final int CODE_ERROR_NETWORK_NO = 3;
    public static final int CODE_ERROR_UNKNOWN = 4;
    public static final int CODE_OK = 0;
    public static final int CODE_UPDATE_OK = 1000;
    public static final int LETV_ERROR = 0;
    public static final int LETV_OK = 1;

    void callback(int i, String str, String str2, Object obj);
}
