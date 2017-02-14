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

public class AlipayAutoPayCloseDialog extends DialogFragment {
    private static String CLOSE_ALL_SCREEN_ALIPAY_AUTO_PAY = "700056";
    private static String CLOSE_MOBILE_ALIPAY_AUTO_PAY = "700055";
    private AlipayConfirmCallback mAlipayConfirmPayCallback;
    private TextView mContent;
    private CustomBaseDialog mDialog;
    private boolean mIsMobileVipFlag;
    private TextView mNotPauseBtn;
    private TextView mPauseBtn;
    private String mTipsMessage;
    private String mTipsTitle;
    private TextView mTitle;
    private View root;

    public AlipayAutoPayCloseDialog() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsMobileVipFlag = true;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.root = LayoutInflater.from(getActivity()).inflate(R.layout.alipay_auto_pay_close_dialog, null);
        findViews();
        setListeners();
        getTipsMessages();
        this.mDialog = new CustomBaseDialog(getActivity(), this.root, R.style.letv_like_dialog, 17);
        this.mDialog.setCanceledOnTouchOutside(false);
        return this.mDialog;
    }

    private void getTipsMessages() {
        if (this.mIsMobileVipFlag) {
            this.mTipsTitle = TipUtils.getTipTitle(CLOSE_MOBILE_ALIPAY_AUTO_PAY, getString(2131099726));
            this.mTipsMessage = TipUtils.getTipMessage(CLOSE_MOBILE_ALIPAY_AUTO_PAY, getString(2131099725));
        } else {
            this.mTipsTitle = TipUtils.getTipTitle(CLOSE_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099724));
            this.mTipsMessage = TipUtils.getTipMessage(CLOSE_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099723));
        }
        this.mTitle.setText(this.mTipsTitle);
        this.mContent.setText(this.mTipsMessage);
    }

    private void findViews() {
        this.mTitle = (TextView) this.root.findViewById(R.id.alipay_auto_pay_title);
        this.mContent = (TextView) this.root.findViewById(R.id.alipay_auto_pay_content);
        this.mNotPauseBtn = (TextView) this.root.findViewById(R.id.alipay_auto_pay_cancel);
        this.mPauseBtn = (TextView) this.root.findViewById(R.id.alipay_auto_pay_confirm);
    }

    private void setListeners() {
        this.mNotPauseBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayCloseDialog this$0;

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
        this.mPauseBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayCloseDialog this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.mAlipayConfirmPayCallback != null) {
                    this.this$0.mAlipayConfirmPayCallback.onAlipayConfirm(true);
                }
                this.this$0.dismiss();
            }
        });
    }

    public void setAlipayConfirmCallback(AlipayConfirmCallback alipayConfirmPayCallback) {
        this.mAlipayConfirmPayCallback = alipayConfirmPayCallback;
    }

    public void setIsMobileVipFlag(boolean isMobileVipFlag) {
        this.mIsMobileVipFlag = isMobileVipFlag;
    }
}
