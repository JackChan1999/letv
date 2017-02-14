package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import io.fabric.sdk.android.services.concurrency.PriorityRunnable;

public class SafeToast extends Toast {
    public SafeToast(Context context) {
        super(context);
    }

    public void show() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.show();
        } else {
            new Handler(Looper.getMainLooper()).post(new PriorityRunnable() {
                public void run() {
                    super.show();
                }
            });
        }
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast origToast = Toast.makeText(context, text, duration);
        SafeToast safeToast = new SafeToast(context);
        safeToast.setView(origToast.getView());
        safeToast.setDuration(origToast.getDuration());
        return safeToast;
    }

    public static Toast makeText(Context context, int resId, int duration) throws NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }
}
