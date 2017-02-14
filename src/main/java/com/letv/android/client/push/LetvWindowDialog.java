package com.letv.android.client.push;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LetvWindowDialog extends RelativeLayout {
    private TextView cancelBtn;
    private Context context;
    private TextView dialogContent;
    private TextView dialogTitle;
    private LetvWindowDialogListener listener;
    private LinearLayout mAlertDialogLayout;
    private int mAlertDialogLayoutId;
    private TextView okBtn;
    private OnClickListener onClickListener;

    public interface LetvWindowDialogListener {
        void cancelCallBack(Context context);

        void okCallBack(Context context);
    }

    public LetvWindowDialog(Context context, LetvWindowDialogListener mListener) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.onClickListener = new OnClickListener(this) {
            final /* synthetic */ LetvWindowDialog this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.w_button1 /*2131363617*/:
                        if (this.this$0.listener != null) {
                            this.this$0.listener.cancelCallBack(this.this$0.context);
                            return;
                        }
                        return;
                    case R.id.w_button2 /*2131363618*/:
                        if (this.this$0.listener != null) {
                            this.this$0.listener.okCallBack(this.this$0.context);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.listener = mListener;
        this.context = context;
        init();
    }

    protected void init() {
        inflate(this.context, R.layout.letv_window_dialog_layout, this);
        findView();
    }

    private void findView() {
        this.dialogContent = (TextView) findViewById(2131362362);
        this.dialogTitle = (TextView) findViewById(R.id.alertTitle);
        this.okBtn = (TextView) findViewById(R.id.w_button1);
        this.cancelBtn = (TextView) findViewById(R.id.w_button2);
        this.okBtn.setOnClickListener(this.onClickListener);
        this.cancelBtn.setOnClickListener(this.onClickListener);
    }

    public void setListener(LetvWindowDialogListener mListener) {
        this.listener = mListener;
    }

    public void setTitleContent(String title, String content) {
        this.dialogTitle.setText(title);
        this.dialogContent.setText(content);
    }
}
