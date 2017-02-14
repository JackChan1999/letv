package com.letv.mobile.lebox.http.lebox;

import android.content.Context;
import com.letv.mobile.lebox.R;

public final class LeBoxHttpErrorCode {
    public static final String ERROR_CODE_AUTHENTICATION_FAILURE = "1";
    public static final String ERROR_CODE_FILE_NOT_EXIST = "8";
    public static final String ERROR_CODE_INVALID_ROUTE_ID = "7";
    public static final String ERROR_CODE_NONE = "0";
    public static final String ERROR_CODE_NO_SUPPORTED = "2";
    public static final String ERROR_CODE_OPERATION_RUNNING = "6";
    public static final String ERROR_CODE_PARAMETER_ERROR = "4";
    public static final String ERROR_CODE_TASK_ALREADY_EXIST = "9";
    public static final String ERROR_CODE_UNKNOWN_ERROR = "3";

    private LeBoxHttpErrorCode() {
    }

    public static String getErrorCodeInfo(Context context, String errorCode) {
        String errorMsg = "";
        if ("1".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_1);
        }
        if ("2".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_2);
        }
        if ("3".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_3);
        }
        if ("4".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_4);
        }
        if ("6".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_6);
        }
        if ("7".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_7);
        }
        if ("8".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_8);
        }
        if ("9".equals(errorCode)) {
            return context.getResources().getString(R.string.error_code_msg_9);
        }
        return errorMsg;
    }
}
