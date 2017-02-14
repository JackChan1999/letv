package com.letv.mobile.lebox.http.lebox;

public final class LeboxTaskDownloadStatus {
    public static final String DOWNLOAD_STATUS_ADDRESS_ERROR = "7";
    public static final String DOWNLOAD_STATUS_COMPLETE = "2";
    public static final String DOWNLOAD_STATUS_DOWDLOADING = "1";
    public static final String DOWNLOAD_STATUS_NETWORK_ERROR = "5";
    public static final String DOWNLOAD_STATUS_NETWORK_RETRY = "8";
    public static final String DOWNLOAD_STATUS_PAUSE = "3";
    @Deprecated
    public static final String DOWNLOAD_STATUS_READY_DOWNLOAD = "9";
    @Deprecated
    public static final String DOWNLOAD_STATUS_RETRY_FAILURE = "10";
    @Deprecated
    public static final String DOWNLOAD_STATUS_STORE_UN_WRITE = "4";
    public static final String DOWNLOAD_STATUS_THE_SERVER_ERROR = "6";
    public static final String DOWNLOAD_STATUS_WAITTING = "0";

    private LeboxTaskDownloadStatus() {
    }
}
