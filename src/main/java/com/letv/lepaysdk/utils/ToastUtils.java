package com.letv.lepaysdk.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {
    private static Toast toast;

    public static void makeText(Context context, String text) {
        if (!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            Toast.makeText(context, text, 0).show();
        }
    }
}
