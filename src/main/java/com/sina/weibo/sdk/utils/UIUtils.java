package com.sina.weibo.sdk.utils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.widget.Toast;

public class UIUtils {
    public static void showAlert(Context context, String title, String text) {
        if (context != null) {
            new Builder(context).setTitle(title).setMessage(text).create().show();
        }
    }

    public static void showAlert(Context context, int titleId, int textId) {
        if (context != null) {
            showAlert(context, context.getString(titleId), context.getString(textId));
        }
    }

    public static void showToast(Context context, int resId, int duration) {
        if (context != null) {
            Toast.makeText(context, resId, duration).show();
        }
    }

    public static void showToast(Context context, CharSequence text, int duration) {
        if (context != null) {
            Toast.makeText(context, text, duration).show();
        }
    }

    public static void showToastInCenter(Context context, int resId, int duration) {
        if (context != null) {
            Toast toast = Toast.makeText(context, resId, duration);
            toast.setGravity(17, 0, 0);
            toast.show();
        }
    }
}
