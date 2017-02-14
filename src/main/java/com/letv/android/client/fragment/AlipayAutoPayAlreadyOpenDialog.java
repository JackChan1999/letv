package com.letv.android.client.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.view.CustomBaseDialog;
import com.letv.android.client.listener.AlipayConfirmCallback;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class AlipayAutoPayAlreadyOpenDialog extends DialogFragment {
    private static String ALREADY_OPEN_ALIPAY_AUTO_PAY = "700057";
    private AlipayConfirmCallback mAlipayConfirmPayCallback;
    private TextView mContent;
    private CustomBaseDialog mDialog;
    private String mTipsMessage;
    private String mTipsTitle;
    private TextView mTitle;
    private TextView mYesBtn;
    private View root;

    public AlipayAutoPayAlreadyOpenDialog() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.root = LayoutInflater.from(getActivity()).inflate(R.layout.alipay_auto_pay_already_open_dialog, null);
        findViews();
        setListeners();
        getTipsMessages();
        this.mDialog = new CustomBaseDialog(getActivity(), this.root, R.style.letv_like_dialog, 17);
        this.mDialog.setCanceledOnTouchOutside(false);
        return this.mDialog;
    }

    private void getTipsMessages() {
        this.mTipsTitle = TipUtils.getTipTitle(ALREADY_OPEN_ALIPAY_AUTO_PAY, 2131099718);
        this.mTipsMessage = TipUtils.getTipMessage(ALREADY_OPEN_ALIPAY_AUTO_PAY, 2131099717);
        this.mTitle.setText(this.mTipsTitle);
        this.mContent.setText(this.mTipsMessage);
    }

    private void findViews() {
        this.mTitle = (TextView) this.root.findViewById(R.id.alipay_auto_pay_title);
        this.mContent = (TextView) this.root.findViewById(R.id.alipay_auto_pay_content);
        this.mYesBtn = (TextView) this.root.findViewById(R.id.alipay_auto_pay_confirm);
    }

    private void setListeners() {
        this.mYesBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayAlreadyOpenDialog this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.mAlipayConfirmPayCallback != null) {
                    this.this$0.mAlipayConfirmPayCallback.onAlipayConfirm(false);
                }
                this.this$0.dismiss();
            }
        });
    }

    public void setAlipayConfirmCallback(AlipayConfirmCallback alipayConfirmPayCallback) {
        this.mAlipayConfirmPayCallback = alipayConfirmPayCallback;
    }
}
