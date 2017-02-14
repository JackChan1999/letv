package com.letv.android.client.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class CustomLoadingDialog extends Dialog {
    private static int default_height = 100;
    private static int default_width = 100;
    private TextView textView;

    public CustomLoadingDialog(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, default_width, default_height, R.layout.layout_dialog, 2131230820);
    }

    public CustomLoadingDialog(Context context, String message) {
        this(context, default_width, default_height, R.layout.layout_message_dialog, 2131230820);
        if (TextUtils.isEmpty(message)) {
            this.textView.setVisibility(8);
        } else {
            this.textView.setText(message);
        }
    }

    public CustomLoadingDialog(Context context, View view, int style) {
        super(context, style);
        setContentView(view);
    }

    public CustomLoadingDialog(Context context, int width, int height, int layout, int style) {
        super(context, style);
        setContentView(layout);
        this.textView = (TextView) findViewById(2131363110);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.width = UIsUtils.zoomWidth(width);
        params.height = UIsUtils.zoomWidth(height);
        params.gravity = 17;
        window.setAttributes(params);
    }

    private float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public void setWindowParams(int width, int height, int gravity) {
        int i = -1;
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        if (width <= 0) {
            width = -1;
        }
        params.width = width;
        if (height > 0) {
            i = height;
        }
        params.height = i;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    public void setTextMsg(String msg) {
        this.textView.setText(msg);
        this.textView.setVisibility(0);
    }

    public void setTextMsg(int resId) {
        this.textView.setText(resId);
        this.textView.setVisibility(0);
    }
}
