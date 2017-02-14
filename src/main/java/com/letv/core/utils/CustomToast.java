package com.letv.core.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {
    private ImageView iconView = ((ImageView) this.view.findViewById(R.id.tosat_icon));
    private TextView msgView = ((TextView) this.view.findViewById(R.id.tosat_msg));
    private View view;

    public CustomToast(Context context) {
        super(context);
        this.view = UIsUtils.inflate(context, R.layout.toast_layout, null, false);
        setGravity(16, 0, 0);
        setView(this.view);
    }

    public void setMessage(String msg) {
        this.msgView.setText(msg);
    }

    public void setMessageById(int msgId) {
        this.msgView.setText(msgId);
    }

    public void setErr(boolean isErr) {
        if (isErr) {
            this.iconView.setImageResource(R.drawable.err_toast_icon);
        } else {
            this.iconView.setImageResource(R.drawable.toast_icon);
        }
    }

    public void show() {
        super.show();
    }
}
