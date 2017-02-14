package com.letv.core.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public class ChangeOrientationHandler extends Handler {
    public static final int ORIENTATION_0 = 3;
    public static final int ORIENTATION_1 = 4;
    public static final int ORIENTATION_8 = 1;
    public static final int ORIENTATION_9 = 2;
    private Activity activity;

    public ChangeOrientationHandler(Activity ac) {
        this.activity = ac;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                this.activity.setRequestedOrientation(8);
                return;
            case 2:
            case 4:
                this.activity.setRequestedOrientation(1);
                return;
            case 3:
                this.activity.setRequestedOrientation(0);
                return;
            default:
                return;
        }
    }
}
