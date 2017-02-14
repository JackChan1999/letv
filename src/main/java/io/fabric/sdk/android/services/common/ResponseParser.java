package io.fabric.sdk.android.services.common;

import com.letv.core.messagebus.config.LeMessageIds;

public class ResponseParser {
    public static final int ResponseActionDiscard = 0;
    public static final int ResponseActionRetry = 1;

    public static int parse(int statusCode) {
        if (statusCode >= 200 && statusCode <= 299) {
            return 0;
        }
        if (statusCode >= 300 && statusCode <= 399) {
            return 1;
        }
        if (statusCode >= 400 && statusCode <= 499) {
            return 0;
        }
        if (statusCode >= LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA) {
            return 1;
        }
        return 1;
    }
}
