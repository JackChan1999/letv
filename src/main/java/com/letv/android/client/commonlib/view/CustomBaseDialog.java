package com.letv.android.client.commonlib.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class CustomBaseDialog extends Dialog {
    public CustomBaseDialog(Context context, View view, int style, int gravity) {
        super(context, style);
        if (gravity == 80) {
            getWindow().setSoftInputMode(16);
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            LayoutParams params = getWindow().getAttributes();
            params.width = -1;
            params.height = -2;
            params.gravity = gravity;
            getWindow().setAttributes(params);
        }
        setContentView(view);
    }

    public void setWindowParams(int width) {
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        if (width <= 0) {
            width = -1;
        }
        params.width = width;
        params.height = -1;
        params.gravity = 80;
        window.setAttributes(params);
    }
}
