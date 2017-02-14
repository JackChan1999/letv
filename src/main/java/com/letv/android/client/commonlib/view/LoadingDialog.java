package com.letv.android.client.commonlib.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.TextView;
import com.letv.android.client.commonlib.R;

public class LoadingDialog extends ProgressDialog {
    private int msgId;
    private String msgText;

    public LoadingDialog(Context context, int msgId) {
        super(context);
        this.msgId = msgId;
    }

    public LoadingDialog(Context context, String msgText) {
        super(context);
        this.msgText = msgText;
    }

    public LoadingDialog(Context context, int msgId, boolean isFull) {
        super(context);
        this.msgId = msgId;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingdialog);
        TextView msg = (TextView) findViewById(R.id.msg);
        if (this.msgId != 0 || TextUtils.isEmpty(this.msgText)) {
            msg.setText(this.msgId);
        } else {
            msg.setText(this.msgText);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
