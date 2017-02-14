package com.coolcloud.uac.android.api.view.basic;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

public class ButtonHandler extends Handler {
    private WeakReference<DialogInterface> mDialog;

    public ButtonHandler(DialogInterface dialog) {
        this.mDialog = new WeakReference(dialog);
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case -3:
            case -2:
            case -1:
                ((OnClickListener) msg.obj).onClick((DialogInterface) this.mDialog.get(), msg.what);
                return;
            default:
                return;
        }
    }
}
