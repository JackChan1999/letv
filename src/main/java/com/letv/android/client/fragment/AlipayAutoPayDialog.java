package com.letv.android.client.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.view.CustomBaseDialog;
import com.letv.android.client.listener.AlipayConfirmCallback;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class AlipayAutoPayDialog extends DialogFragment {
    private static String CHANGE_ALL_SCREEN_ALIPAY_AUTO_PAY = "100107";
    private static String CHANGE_MOBILE_ALIPAY_AUTO_PAY = "100106";
    private static String OPEN_ALL_SCREEN_ALIPAY_AUTO_PAY = "700059";
    private static String OPEN_MOBILE_ALIPAY_AUTO_PAY = "700053";
    private static String RESUME_ALL_SCREEN_ALIPAY_AUTO_PAY = "700060";
    private static String RESUME_MOBILE_ALIPAY_AUTO_PAY = "700054";
    private boolean isAgreeAutoPayFlag;
    private TextView mAgree;
    private TextView mAgreement;
    private AlipayConfirmCallback mAlipayConfirmPayCallback;
    private TextView mCancelBtn;
    private TextView mConfirmBtn;
    private TextView mContent;
    private CustomBaseDialog mDialog;
    private boolean mIsMobileVipFlag;
    private boolean mIsNotOpenContinusMonthly;
    private String mTipsMessage;
    private String mTipsTitle;
    private TextView mTitle;
    private View root;

    public AlipayAutoPayDialog() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isAgreeAutoPayFlag = true;
        this.mIsNotOpenContinusMonthly = false;
        this.mIsMobileVipFlag = true;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.root = LayoutInflater.from(getActivity()).inflate(R.layout.alipay_auto_pay_open_resume_dialog, null);
        findViews();
        setListeners();
        getTipsMessages();
        this.mDialog = new CustomBaseDialog(getActivity(), this.root, R.style.letv_like_dialog, 17);
        this.mDialog.setCanceledOnTouchOutside(false);
        return this.mDialog;
    }

    private void getTipsMessages() {
        this.mIsNotOpenContinusMonthly = getArguments().getBoolean(AlipayConstant.NOT_OPEN_CONTINUE_MONTHLY, true);
        this.mIsMobileVipFlag = getArguments().getBoolean(AlipayConstant.IS_MOBILE_VIP_FLAG, true);
        if (PreferencesManager.getInstance().getAlipayAutoOpenStatus()) {
            if (this.mIsMobileVipFlag) {
                this.mTipsTitle = TipUtils.getTipTitle(CHANGE_MOBILE_ALIPAY_AUTO_PAY, getString(2131099722));
                this.mTipsMessage = TipUtils.getTipMessage(CHANGE_MOBILE_ALIPAY_AUTO_PAY, getString(2131099721));
            } else {
                this.mTipsTitle = TipUtils.getTipTitle(CHANGE_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099720));
                this.mTipsMessage = TipUtils.getTipMessage(CHANGE_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099719));
            }
        } else if (this.mIsNotOpenContinusMonthly) {
            if (this.mIsMobileVipFlag) {
                this.mTipsTitle = TipUtils.getTipTitle(OPEN_MOBILE_ALIPAY_AUTO_PAY, getString(2131099741));
                this.mTipsMessage = TipUtils.getTipMessage(OPEN_MOBILE_ALIPAY_AUTO_PAY, getString(2131099740));
            } else {
                this.mTipsTitle = TipUtils.getTipTitle(OPEN_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099741));
                this.mTipsMessage = TipUtils.getTipMessage(OPEN_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099740));
            }
        } else if (this.mIsMobileVipFlag) {
            this.mTipsTitle = TipUtils.getTipTitle(RESUME_MOBILE_ALIPAY_AUTO_PAY, getString(2131099743));
            this.mTipsMessage = TipUtils.getTipMessage(RESUME_MOBILE_ALIPAY_AUTO_PAY, getString(2131099742));
        } else {
            this.mTipsTitle = TipUtils.getTipTitle(RESUME_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099743));
            this.mTipsMessage = TipUtils.getTipMessage(RESUME_ALL_SCREEN_ALIPAY_AUTO_PAY, getString(2131099742));
        }
        this.mTipsMessage = this.mTipsMessage.replace("\\n", "<br>");
        this.mTipsMessage = this.mTipsMessage.replace("\n", "<br>");
        this.mTitle.setText(this.mTipsTitle);
        this.mContent.setText(Html.fromHtml(this.mTipsMessage));
    }

    private void findViews() {
        this.mTitle = (TextView) this.root.findViewById(R.id.alipay_auto_pay_title);
        this.mContent = (TextView) this.root.findViewById(R.id.alipay_auto_pay_content);
        this.mAgree = (TextView) this.root.findViewById(R.id.alipay_auto_pay_agree);
        this.mAgreement = (TextView) this.root.findViewById(R.id.alipay_auto_pay_agreement);
        this.mCancelBtn = (TextView) this.root.findViewById(R.id.alipay_auto_pay_cancel);
        this.mConfirmBtn = (TextView) this.root.findViewById(R.id.alipay_auto_pay_confirm);
        this.mAgreement.setPaintFlags(this.mAgreement.getPaintFlags() | 8);
    }

    private void setListeners() {
        this.mAgreement.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayDialog this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                new LetvWebViewActivityConfig(this.this$0.getActivity()).launch(AlipayConstant.ALIAPY_AUTO_PAY_AGREEMENT_URL, this.this$0.getString(2131099731));
            }
        });
        this.mCancelBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayDialog this$0;

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
        this.mConfirmBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayDialog this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LogInfo.log("CRL 支付宝支付开始2 弹出第一个dialog ");
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

    public void setTitle(String text) {
        this.mTitle.setText(text);
    }

    public void setContent(String text) {
        this.mContent.setText(text);
    }
}
