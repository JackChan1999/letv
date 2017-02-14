package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.view.DeleteButtonEditText;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.MobileCheckBean;
import com.letv.core.bean.MobilePayBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.HttpRequestMethod;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.MobileCheckParser;
import com.letv.core.parser.MobilePayParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PhonePayActivity extends WrapActivity implements OnClickListener {
    private View backView;
    private String defaultMobile;
    private ProgressBar loading;
    private String mobileNum;
    private DeleteButtonEditText phoneNumView;
    private TextView phonePayBottomText;
    private Button phoneRegisterBtn;
    private View phone_pay_title;
    private String price;
    private String sVip;

    public PhonePayActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mobileNum = "";
        this.price = "";
        this.defaultMobile = "";
        this.sVip = "1";
    }

    public static void launch(Activity context, String svip) {
        Intent intent = new Intent();
        intent.setClass(context, PhonePayActivity.class);
        intent.putExtra("svip", svip);
        context.startActivityForResult(intent, 17);
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.phone_pay_layout);
        readParams();
        findView();
        setText();
        this.phoneNumView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PhonePayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.phoneNumView.setHint("");
                this.this$0.phoneNumView.setCursorVisible(true);
                this.this$0.defaultMobile = "";
            }
        });
        this.phoneNumView.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ PhonePayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0 && TextUtils.isEmpty(this.this$0.phoneNumView.getHint())) {
                    this.this$0.phoneNumView.setHint(this.this$0.getResources().getString(2131100163));
                }
            }
        });
    }

    private void setText() {
        this.phonePayBottomText.setText(LetvUtils.ToDBC(getResources().getString(2131100601)));
        String mobile = PreferencesManager.getInstance().getUserMobile();
        LogInfo.log("vip_", getClass().getSimpleName() + " setText mobile = " + mobile + " , isLogin = " + PreferencesManager.getInstance().isLogin());
        if (PreferencesManager.getInstance().isLogin() && !TextUtils.isEmpty(mobile) && LetvUtils.mobileNumberFormat(mobile)) {
            this.phoneNumView.setHint(mobile);
            this.defaultMobile = mobile;
        }
    }

    private void readParams() {
        this.sVip = getIntent().getStringExtra("svip");
    }

    private void findView() {
        this.phoneNumView = (DeleteButtonEditText) findViewById(R.id.regist_phone_edit);
        this.phoneRegisterBtn = (Button) findViewById(R.id.regist_btnLogin_phone);
        this.loading = (ProgressBar) findViewById(2131361884);
        this.backView = findViewById(R.id.phone_back_btn);
        this.phone_pay_title = findViewById(R.id.phone_pay_title);
        this.phonePayBottomText = (TextView) findViewById(R.id.phone_pay_bottom_text);
        this.phoneRegisterBtn.setOnClickListener(this);
        this.backView.setOnClickListener(this);
        this.phone_pay_title.setOnClickListener(this);
    }

    private boolean checkPhoneFormat() {
        if (TextUtils.isEmpty(this.phoneNumView.getText().toString())) {
            if (TextUtils.isEmpty(this.defaultMobile)) {
                ToastUtils.showToast((Context) this, 2131100597);
                this.phoneNumView.requestFocus();
                return false;
            }
            this.mobileNum = this.defaultMobile;
            return true;
        } else if (LetvUtils.mobileNumberFormat(this.phoneNumView.getText().toString())) {
            this.mobileNum = this.phoneNumView.getText().toString();
            return true;
        } else {
            ToastUtils.showToast((Context) this, 2131100597);
            this.phoneNumView.requestFocus();
            return false;
        }
    }

    private void nextStep() {
        if (NetworkUtils.isNetworkAvailable()) {
            UIsUtils.hideSoftkeyboard(this);
            requestPhoneNumCheck(this.mobileNum);
            return;
        }
        ToastUtils.showToast((Context) this, 2131100495);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_back_btn /*2131363961*/:
            case R.id.phone_pay_title /*2131363999*/:
                finish();
                return;
            case R.id.regist_btnLogin_phone /*2131364004*/:
                if (checkPhoneFormat()) {
                    nextStep();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void requestPhoneNumCheck(String phoneNum) {
        this.loading.setVisibility(0);
        LogInfo.log("ZSM", "PayCenterApi.getInstance().requestPhoneNumCheck(0, phoneNum) == " + PayCenterApi.getInstance().requestPhoneNumCheck(0, phoneNum));
        new LetvRequest(MobileCheckBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestPhoneNumCheck(0, phoneNum)).setCache(new VolleyNoCache()).setParser(new MobileCheckParser()).setCallback(new SimpleResponse<MobileCheckBean>(this) {
            final /* synthetic */ PhonePayActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<MobileCheckBean> volleyRequest, MobileCheckBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "requestPhoneNumCheck onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        this.this$0.loading.setVisibility(8);
                        if (result != null) {
                            this.this$0.handleResult(result);
                            return;
                        }
                        return;
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        this.this$0.loading.setVisibility(8);
                        ToastUtils.showToast(this.this$0, 2131100332);
                        return;
                    case RESULT_ERROR:
                        this.this$0.loading.setVisibility(8);
                        ToastUtils.showToast(this.this$0, 2131100503);
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<MobileCheckBean> request, String errorInfo) {
                LogInfo.log("ZSM", "requestPhoneNumCheck onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    private void requestPayData(final MobileCheckBean checkBean) {
        this.loading.setVisibility(0);
        if ("1".equals(this.sVip)) {
            this.price = checkBean.price;
        } else {
            this.price = checkBean.gjprice;
        }
        String url = PayCenterApi.getInstance().requestMobilePayData(0, this.price, this.mobileNum, PreferencesManager.getInstance().getUserName(), PreferencesManager.getInstance().getUserId(), checkBean.payType, this.sVip);
        LogInfo.log("ZSM", "requestPayData url == " + url);
        new LetvRequest(MobilePayBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(url).setCache(new VolleyNoCache()).setParser(new MobilePayParser()).setHttpMethod(HttpRequestMethod.POST).addPostParams(PayCenterApi.getInstance().requestMobilePay(0, this.price, this.mobileNum, PreferencesManager.getInstance().getUserName(), PreferencesManager.getInstance().getUserId(), checkBean.payType, this.sVip)).setCallback(new SimpleResponse<MobilePayBean>() {
            public void onNetworkResponse(VolleyRequest<MobilePayBean> volleyRequest, MobilePayBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "requestPayData onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            LogInfo.log("ZSM", "MobilePayBean = " + result);
                            PhonePayActivity.this.gotoPay(checkBean.provider, result);
                        }
                        PhonePayActivity.this.loading.setVisibility(8);
                        return;
                    case NETWORK_NOT_AVAILABLE:
                    case NETWORK_ERROR:
                        PhonePayActivity.this.loading.setVisibility(8);
                        ToastUtils.showToast(PhonePayActivity.this, 2131100332);
                        return;
                    case RESULT_ERROR:
                        PhonePayActivity.this.loading.setVisibility(8);
                        ToastUtils.showToast(PhonePayActivity.this, 2131100503);
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<MobilePayBean> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_UC_Pay, null, errorInfo, null, null, null, null);
            }
        }).add();
    }

    private void handleResult(MobileCheckBean result) {
        if (result.code.equals("0")) {
            if (result.provider.equals("1") || result.provider.equals("2") || result.provider.equals("3")) {
                requestPayData(result);
            }
        } else if (TextUtils.isEmpty(result.msg)) {
            ToastUtils.showToast((Context) this, 2131099806);
        } else {
            try {
                ToastUtils.showToast((Context) this, URLDecoder.decode(result.msg, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void gotoPay(String provider, MobilePayBean result) {
        if (result.code.equals("1")) {
            try {
                ToastUtils.showToast((Context) this, URLDecoder.decode(result.msg, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (!result.code.equals("0")) {
        } else {
            if (provider.equals("2")) {
                payChinaUnion(result);
            } else if (provider.equals("1")) {
                payChinaMobile(result);
            } else if (provider.equals("3")) {
                payChinaTelecom(result);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 17) {
            setResult(resultCode);
        }
        finish();
    }

    private void payChinaMobile(MobilePayBean result) {
        ChinaMobileWebPayActivity.launch(this, result.posturl, getResources().getString(2131099770), getResources().getString(2131099771), this.price);
        LogInfo.log("vip_", "payChinaMobile url = " + result.posturl);
    }

    private void payChinaUnion(MobilePayBean result) {
        ChinaUinonPayActivity.launch(this, this.mobileNum, this.price, this.sVip);
    }

    private void payChinaTelecom(MobilePayBean result) {
        PayCommondActivity.launch(this, this.mobileNum, this.price, result.command, result.servicecode, this.sVip);
    }

    public String getActivityName() {
        return PhonePayActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
