package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.fragment.AlipayAutoPayCloseDialog;
import com.letv.android.client.listener.AlipayConfirmCallback;
import com.letv.android.client.listener.AlipayMaintainCallback;
import com.letv.android.client.task.RequestAutoPayMaintainTask;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class AlipayAutoPayCancelActivity extends PimBaseActivity implements OnClickListener, AlipayConfirmCallback, AlipayMaintainCallback {
    private static String OPEN_ALIPAY_AUTO_PAY = "700061";
    private static String PAUSE_ALIPAY_AUTO_PAY = "700062";
    private AlipayAutoPayCloseDialog mAlipayAutoPayDialog;
    private ImageView mBackImageView;
    private TextView mDescContentView;
    private TextView mDescTitleView;
    private boolean mIsMobileVipFlag;
    private boolean mIsOpenFlag;
    private boolean mIsOpenStatus;
    private String mOpenContent;
    private String mOpenTitle;
    private String mPauseContent;
    private TextView mPauseServiceView;
    private String mPauseTitle;
    private RequestAutoPayMaintainTask mRequestAutoPayMaintainTask;

    public AlipayAutoPayCancelActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mAlipayAutoPayDialog = null;
        this.mIsMobileVipFlag = true;
        this.mIsOpenStatus = false;
        this.mRequestAutoPayMaintainTask = null;
        this.mIsOpenFlag = false;
    }

    public String getActivityName() {
        return AlipayAutoPayCancelActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    public static void launch(Activity context, boolean isCurrentOpenFlag, boolean isMobileVipFlag) {
        Intent intent = new Intent(context, AlipayAutoPayCancelActivity.class);
        intent.putExtra(AlipayConstant.CURRENT_OPEN_STATUS, isCurrentOpenFlag);
        intent.putExtra(AlipayConstant.IS_MOBILE_VIP_FLAG, isMobileVipFlag);
        context.startActivityForResult(intent, 101);
    }

    public int getContentView() {
        return R.layout.alipay_auto_pay_cancel;
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initDialogInfo();
        initUIs();
        initListeners();
    }

    private void initDialogInfo() {
        this.mOpenTitle = TipUtils.getTipTitle(OPEN_ALIPAY_AUTO_PAY, getString(2131099741));
        this.mOpenContent = TipUtils.getTipMessage(OPEN_ALIPAY_AUTO_PAY, getString(2131099740));
        this.mPauseTitle = TipUtils.getTipTitle(PAUSE_ALIPAY_AUTO_PAY, getString(2131099743));
        this.mPauseContent = TipUtils.getTipMessage(PAUSE_ALIPAY_AUTO_PAY, getString(2131099742));
        this.mOpenContent = this.mOpenContent.replace("\\n", "<br>");
        this.mOpenContent = this.mOpenContent.replace("\n", "<br>");
        this.mPauseContent = this.mPauseContent.replace("\\n", "<br>");
        this.mPauseContent = this.mPauseContent.replace("\n", "<br>");
    }

    private void initUIs() {
        this.mBackImageView = (ImageView) findViewById(R.id.back_btn);
        this.mBackImageView.setOnClickListener(this);
        this.mPauseServiceView = (TextView) findViewById(R.id.alipay_auto_pay_pause_service);
        this.mDescTitleView = (TextView) findViewById(R.id.alipay_auto_pay_desc_title);
        this.mDescContentView = (TextView) findViewById(R.id.alipay_auto_pay_desc_content);
        this.mIsMobileVipFlag = getIntent().getBooleanExtra(AlipayConstant.IS_MOBILE_VIP_FLAG, false);
        this.mIsOpenStatus = getIntent().getBooleanExtra(AlipayConstant.CURRENT_OPEN_STATUS, false);
        if (this.mIsOpenStatus) {
            this.mDescTitleView.setText(Html.fromHtml(this.mOpenTitle));
            this.mDescContentView.setText(Html.fromHtml(this.mOpenContent));
            return;
        }
        this.mDescTitleView.setText(Html.fromHtml(this.mPauseTitle));
        this.mDescContentView.setText(Html.fromHtml(this.mPauseContent));
    }

    private void initListeners() {
        this.mPauseServiceView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ AlipayAutoPayCancelActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.showAlipayCloseDialog();
            }
        });
    }

    private void showAlipayCloseDialog() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            this.mAlipayAutoPayDialog = (AlipayAutoPayCloseDialog) fm.findFragmentByTag("showmAlipayPayCloseDialog");
            if (this.mAlipayAutoPayDialog == null) {
                this.mAlipayAutoPayDialog = new AlipayAutoPayCloseDialog();
            } else {
                ft.remove(this.mAlipayAutoPayDialog);
            }
            this.mAlipayAutoPayDialog.setIsMobileVipFlag(this.mIsMobileVipFlag);
            this.mAlipayAutoPayDialog.setAlipayConfirmCallback(this);
            ft.add(this.mAlipayAutoPayDialog, "showmAlipayPayCloseDialog");
            ft.commitAllowingStateLoss();
        }
    }

    public void onAlipayConfirm(boolean isAutoPayFlag) {
        if (isAutoPayFlag) {
            if (this.mRequestAutoPayMaintainTask == null) {
                this.mRequestAutoPayMaintainTask = new RequestAutoPayMaintainTask(this, "2");
            }
            this.mRequestAutoPayMaintainTask.setAlipayMaintainCallback(this);
            this.mRequestAutoPayMaintainTask.start();
            return;
        }
        finish();
    }

    public void onAlipayMaintainSuccess(int status) {
        ToastUtils.showToast(getString(2131099728));
        Intent intent = new Intent();
        intent.putExtra(AlipayConstant.CURRENT_OPEN_STATUS, status);
        setResult(-1, intent);
        finish();
    }

    public void onAlipayMaintainFail() {
        ToastUtils.showToast(getString(2131099727));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn /*2131361912*/:
                finish();
                return;
            default:
                return;
        }
    }
}
