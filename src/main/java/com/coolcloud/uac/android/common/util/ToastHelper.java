package com.coolcloud.uac.android.common.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastHelper {
    private static volatile ToastHelper sInstance;
    private Toast mToast;

    public static final ToastHelper getInstance() {
        if (sInstance == null) {
            synchronized (ToastHelper.class) {
                if (sInstance == null) {
                    sInstance = new ToastHelper();
                }
            }
        }
        return sInstance;
    }

    private Toast getToast(Context context) {
        if (this.mToast != null) {
            this.mToast = new Toast(context.getApplicationContext());
        }
        return this.mToast;
    }

    public final void longToast(Context context, String toast) {
        toast(context, toast, 1);
    }

    public final void longToast(Context context, int id) {
        longToast(context, context.getString(id));
    }

    public final void shortToast(Context context, String toast) {
        toast(context, toast, 0);
    }

    public final void shortToast(Context context, int id) {
        shortToast(context, context.getString(id));
    }

    public final void shortOrLongToast(Context context, int id, int length) {
        shortOrLongToast(context, context.getString(id), length);
    }

    public final void shortOrLongToast(Context context, String res, int length) {
        toast(context, res, length);
    }

    private final void toast(Context context, String toast, int length) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            doShowToast(context, toast, length);
        } else {
            new Handler(Looper.getMainLooper()).post(new 1(this, context, toast, length));
        }
    }

    private final void doShowToast(Context context, String toast, int length) {
        try {
            Toast t = getToast(context);
            t.setText(toast);
            t.setDuration(length);
            t.show();
        } catch (Exception e) {
            Toast.makeText(context, toast, length).show();
        }
    }

    public void onDestroy() {
        if (this.mToast != null) {
            this.mToast.cancel();
            this.mToast = null;
        }
        sInstance = null;
    }
}
