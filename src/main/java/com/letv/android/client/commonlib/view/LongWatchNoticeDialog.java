package com.letv.android.client.commonlib.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.commonlib.R;
import com.letv.core.utils.TipUtils;

public class LongWatchNoticeDialog extends Dialog {
    private static LongWatchNoticeDialog dialog = null;
    private Context mContext;

    public LongWatchNoticeDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setCanceledOnTouchOutside(false);
        View root = View.inflate(getContext(), R.layout.layout_long_watch, null);
        setContentView(root, new LayoutParams(-2, -2));
        TextView iKnow = (TextView) root.findViewById(R.id.textView_i_know);
        ((TextView) root.findViewById(R.id.long_watch_tip)).setText(TipUtils.getTipMessage("100080", R.string.long_watch_tip));
        iKnow.setOnClickListener(new 1(this));
    }

    public static void show(DismissCallBack callBack, Context context) {
        dialog = new LongWatchNoticeDialog(context, R.style.first_push_style);
        dialog.setOnDismissListener(new 2(callBack));
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void onDestory() {
        dismissDialog();
        dialog = null;
    }
}
