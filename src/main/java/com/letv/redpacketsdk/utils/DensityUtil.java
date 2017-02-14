package com.letv.redpacketsdk.utils;

import android.content.Context;

public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        try {
            return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception e) {
            return (int) dpValue;
        }
    }

    public static int px2dip(Context context, float pxValue) {
        try {
            return (int) ((pxValue * context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception e) {
            return (int) pxValue;
        }
    }
}
