package com.letv.pp.listener;

public interface OnStartStatusChangeListener {
    public static final int STATUS_CODE_AIDL_RETURN_NULL = -6;
    public static final int STATUS_CODE_CDE_START_FAILED = -4;
    public static final int STATUS_CODE_CDE_START_SUCCESS = 0;
    public static final int STATUS_CODE_CONFIG_FILE_NOT_FOUND = -1;
    public static final int STATUS_CODE_LIBRARY_LOAD_FAILED = -3;
    public static final int STATUS_CODE_LIBRARY_PULL_FAILED = -2;
    public static final int STATUS_CODE_LINKSHELL_START_FAILED = -4;
    public static final int STATUS_CODE_LINKSHELL_START_SUCCESS = 0;
    public static final int STATUS_CODE_SERVICE_BIND_FAILED = -5;

    void onCdeServiceDisconnected();

    void onCdeStartComplete(int i);

    void onLinkShellStartComplete(int i);
}
