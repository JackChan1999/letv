package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.EmailBackBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class FindLetvAccountPasswordActivity extends PimBaseActivity implements OnClickListener {
    private EditText mEmailAddEt;
    private TextView mPhoneNumTv;

    public FindLetvAccountPasswordActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public int getContentView() {
        return R.layout.find_letvaccount_password;
    }

    public static void launch(Activity context) {
        context.startActivity(new Intent(context, FindLetvAccountPasswordActivity.class));
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        setTitle(2131100125);
        StringBuilder sb = new StringBuilder();
        sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.findPasswordPage).append("&fl=c93&wz=1");
        DataStatistics.getInstance().sendActionInfo(this, "0", "0", LetvUtils.getPcode(), "19", sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
    }

    public void initUI() {
        super.initUI();
        findViewById(R.id.msgFindPassButton).setOnClickListener(this);
        findViewById(R.id.emailFindPassButton).setOnClickListener(this);
        this.mEmailAddEt = (EditText) findViewById(R.id.emailAdd);
        this.mPhoneNumTv = (TextView) findViewById(R.id.textv_phone_num);
        this.mPhoneNumTv.setText(getString(2131100800, new Object[]{LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_90002, LetvConstant.retrievePwdPhoneNum)}));
    }

    private boolean checkEmailFormat() {
        if (TextUtils.isEmpty(this.mEmailAddEt.getText().toString())) {
            ToastUtils.showToast(this.mEmailAddEt.getContext(), 2131100192);
            this.mEmailAddEt.requestFocus();
            return false;
        } else if (LetvUtils.emailFormats(this.mEmailAddEt.getText().toString())) {
            return true;
        } else {
            ToastUtils.showToast(this.mEmailAddEt.getContext(), TipUtils.getTipMessage("70003", getString(2131100195)));
            this.mEmailAddEt.requestFocus();
            return false;
        }
    }

    public String getActivityName() {
        return FindLetvAccountPasswordActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgFindPassButton /*2131362583*/:
                LetvUtils.retrievePwdBySMS(this, LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_90002, LetvConstant.retrievePwdPhoneNum));
                finish();
                LogInfo.LogStatistics("msg find password button");
                StatisticsUtils.staticticsInfoPost((Context) this, "b2", null, 0, -1, null, null, null, null, null);
                StatisticsUtils.staticticsInfoPost(this, "0", "c91", null, 1, null, PageIdConstant.findPasswordPage, null, null, null, null, null);
                return;
            case R.id.emailFindPassButton /*2131362586*/:
                if (checkEmailFormat()) {
                    getRequestEmailBackTask();
                    LogInfo.LogStatistics("email find password button");
                    StatisticsUtils.staticticsInfoPost(this, "0", "c92", null, 1, null, PageIdConstant.findPasswordPage, null, null, null, null, null);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void getRequestEmailBackTask() {
        new LetvRequest(EmailBackBean.class).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setUrl(PlayRecordApi.getInstance().sendBackPwdEmail(0, this.mEmailAddEt.getText().toString())).setCallback(new SimpleResponse<EmailBackBean>(this) {
            final /* synthetic */ FindLetvAccountPasswordActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<EmailBackBean> volleyRequest, EmailBackBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        if ("0".equals(result.errorCode) || "".equals(result.message) || TextUtils.isEmpty(result.message)) {
                            ToastUtils.showToast(this.this$0.getActivity(), TipUtils.getTipMessage("70002", this.this$0.getString(2131100122)));
                            return;
                        } else if ("1006".equals(result.errorCode)) {
                            UIsUtils.call(this.this$0, LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_70005, this.this$0.getResources().getString(2131100129)), null);
                            return;
                        } else {
                            ToastUtils.showToast(this.this$0.getActivity(), 2131100121);
                            return;
                        }
                    case RESULT_ERROR:
                        ToastUtils.showToast(this.this$0.getActivity(), TextUtils.isEmpty(hull.message) ? this.this$0.getString(2131100121) : hull.message);
                        return;
                    case NETWORK_ERROR:
                        ToastUtils.showToast(this.this$0.getActivity(), 2131101012);
                        return;
                    default:
                        ToastUtils.showToast(this.this$0.getActivity(), 2131100121);
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<EmailBackBean> volleyRequest, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_SendBackPwdEmail, null, errorInfo, null, null, null, null);
            }
        }).add();
    }
}
