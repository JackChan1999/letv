package com.letv.android.client.album.half.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.letv.android.client.album.R;
import com.letv.core.utils.UIsUtils;

public class LetvToastPlayerLibs extends Toast {
    private ImageView iconView = ((ImageView) this.view.findViewById(R.id.tosat_icon));
    private TextView msgView = ((TextView) this.view.findViewById(R.id.tosat_msg));
    private View view;

    public LetvToastPlayerLibs(Context context) {
        super(context);
        this.view = UIsUtils.inflate(context, R.layout.ok_toast_layout_playerlibs, null, false);
        setGravity(16, 0, 0);
        setView(this.view);
    }

    public void setMsg(String msg) {
        this.msgView.setText(msg);
    }

    public void setMsg(int msg) {
        this.msgView.setText(msg);
    }

    public void setErr(boolean isErr) {
        if (isErr) {
            this.iconView.setImageResource(R.drawable.err_toast_icon);
        } else {
            this.iconView.setImageResource(R.drawable.tosat_icon);
        }
    }

    public void show() {
        super.show();
    }
}
