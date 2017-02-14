package com.letv.android.client.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class FeedbackDialog extends Dialog {

    public static class Builder {
        private View mContentView;
        private Context mContext;
        private String mMessage;
        private String mNegativeButton;
        private OnClickListener mNegativeListener;

        public Builder(Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.mContext = context;
        }

        public Builder setmMessage(String mMessage) {
            this.mMessage = mMessage;
            return this;
        }

        public Builder setButton(String button) {
            this.mNegativeButton = button;
            return this;
        }

        public Builder setContentView(View v) {
            this.mContentView = v;
            return this;
        }

        public Builder setmNegativeButton(String button_content, OnClickListener listener) {
            this.mNegativeButton = button_content;
            this.mNegativeListener = listener;
            return this;
        }

        public FeedbackDialog create() {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
            FeedbackDialog feedbackDialog = new FeedbackDialog(this.mContext, R.style.feedback_dialog);
            View layout = inflater.inflate(R.layout.custom_feedback_dialog, null);
            feedbackDialog.addContentView(layout, new LayoutParams(-2, -2));
            ((TextView) layout.findViewById(2131362362)).setText(this.mMessage);
            ((Button) layout.findViewById(2131362363)).setText(this.mNegativeButton);
            layout.findViewById(2131362363).setOnClickListener(new 1(this, feedbackDialog));
            return feedbackDialog;
        }
    }

    public FeedbackDialog(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
    }

    public FeedbackDialog(Context context, int theme) {
        super(context, theme);
    }
}
