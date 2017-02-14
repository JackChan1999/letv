package com.tencent.mm.sdk.constants;

public interface ConstantsAPI {
    public static final String ACTION_HANDLE_APP_REGISTER = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_REGISTER";
    public static final String ACTION_HANDLE_APP_UNREGISTER = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_UNREGISTER";
    public static final String ACTION_REFRESH_WXAPP = "com.tencent.mm.plugin.openapi.Intent.ACTION_REFRESH_WXAPP";
    public static final String APP_PACKAGE = "_mmessage_appPackage";
    public static final String CHECK_SUM = "_mmessage_checksum";
    public static final int COMMAND_GETMESSAGE_FROM_WX = 3;
    public static final int COMMAND_LAUNCH_BY_WX = 6;
    public static final int COMMAND_PAY_BY_WX = 5;
    public static final int COMMAND_SENDAUTH = 1;
    public static final int COMMAND_SENDMESSAGE_TO_WX = 2;
    public static final int COMMAND_SHOWMESSAGE_FROM_WX = 4;
    public static final int COMMAND_UNKNOWN = 0;
    public static final String CONTENT = "_mmessage_content";
    public static final String SDK_VERSION = "_mmessage_sdkVersion";
}
