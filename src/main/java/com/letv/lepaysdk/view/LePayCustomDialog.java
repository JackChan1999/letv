package com.letv.lepaysdk.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letv.lepaysdk.utils.ResourceUtil;
import com.sina.weibo.sdk.component.WidgetRequestParam;

public class LePayCustomDialog extends Dialog {

    public static class Builder {
        private OnClickListener closeICONClickListener;
        int closeIconResId;
        private View contentView;
        private Context context;
        private int iconResId;
        private String message;
        private OnClickListener negativeButtonClickListener;
        private String negativeButtonText;
        OnCloseListener onCloseListener;
        private OnClickListener positiveButtonClickListener;
        private String positiveButtonText;
        private String title;
        private TextView tv_Title;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) this.context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) this.context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIconRes(int iconResId) {
            this.iconResId = iconResId;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) this.context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) this.context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCloseICON(int iconResId, OnClickListener listener) {
            this.closeIconResId = iconResId;
            this.closeICONClickListener = listener;
            return this;
        }

        public TextView getTv_Title() {
            return this.tv_Title;
        }

        public void setTv_Title(TextView tv_Title) {
            this.tv_Title = tv_Title;
        }

        public LePayCustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            LePayCustomDialog dialog = new LePayCustomDialog(this.context, ResourceUtil.getStyleResource(this.context, "lepay_customDialog.Theme"));
            View layout = inflater.inflate(ResourceUtil.getLayoutResource(this.context, "lepay_custom_dialog"), null);
            dialog.addContentView(layout, new LayoutParams(-1, -2));
            this.tv_Title = (TextView) layout.findViewById(ResourceUtil.getIdResource(this.context, "title"));
            this.tv_Title.setText(this.title);
            Button bt_positive = (Button) layout.findViewById(ResourceUtil.getIdResource(this.context, "positiveButton"));
            if (this.positiveButtonText != null) {
                bt_positive.setText(this.positiveButtonText);
                if (this.positiveButtonClickListener != null) {
                    bt_positive.setOnClickListener(new 1(this, dialog));
                }
                bt_positive.setVisibility(0);
            } else {
                bt_positive.setVisibility(8);
            }
            Button bt_negative = (Button) layout.findViewById(ResourceUtil.getIdResource(this.context, "negativeButton"));
            if (this.negativeButtonText != null) {
                bt_negative.setText(this.negativeButtonText);
                if (this.negativeButtonClickListener != null) {
                    bt_negative.setOnClickListener(new 2(this, dialog));
                }
                bt_negative.setVisibility(0);
            } else {
                bt_negative.setVisibility(8);
            }
            if (this.message != null) {
                TextView tv_message = (TextView) layout.findViewById(ResourceUtil.getIdResource(this.context, "message"));
                tv_message.setText(this.message);
                tv_message.setVisibility(0);
            } else if (this.iconResId > 0) {
                ImageView lepay_iv_icon = (ImageView) layout.findViewById(ResourceUtil.getIdResource(this.context, "lepay_iv_icon"));
                lepay_iv_icon.setVisibility(0);
                lepay_iv_icon.setImageResource(this.iconResId);
            } else if (this.contentView != null) {
                ((LinearLayout) layout.findViewById(ResourceUtil.getIdResource(this.context, WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT))).removeAllViews();
                ((LinearLayout) layout.findViewById(ResourceUtil.getIdResource(this.context, WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT))).addView(this.contentView, new LayoutParams(-2, -2));
            }
            ((ImageView) layout.findViewById(ResourceUtil.getIdResource(this.context, "btn_close"))).setOnClickListener(new 3(this, dialog));
            dialog.setContentView(layout);
            return dialog;
        }

        public OnCloseListener getOnCloseListener() {
            return this.onCloseListener;
        }

        public void setOnCloseListener(OnCloseListener onCloseListener) {
            this.onCloseListener = onCloseListener;
        }

        void doClick() {
            if (this.onCloseListener != null) {
                this.onCloseListener.onClick();
            }
        }
    }

    public LePayCustomDialog(Context context) {
        super(context);
    }

    public LePayCustomDialog(Context context, int theme) {
        super(context, theme);
    }
}
