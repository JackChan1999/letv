package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.MobilePayResultBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.MobilePayResultParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class PayCommondActivity extends WrapActivity implements OnClickListener {
    private View backView;
    private String commond;
    private String company;
    private TextView pay_commond;
    private Button pay_send_message;
    private String phoneNum;
    private String price;
    private Button query_pay_result;
    private String serviceNum;
    private String svip;
    private TextView telecomBottomText;
    private View telecom_pay_title;
    private TextView viewPhoneNum;

    public PayCommondActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.svip = "1";
    }

    public static void launch(Context context, String phoneNum, String price, String commond, String serviceNum, String svip) {
        Intent intent = new Intent();
        intent.setClass(context, PayCommondActivity.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("price", price);
        intent.putExtra("commond", commond);
        intent.putExtra("serviceNum", serviceNum);
        intent.putExtra("svip", svip);
        ((Activity) context).startActivityForResult(intent, 17);
    }

    private void readParams() {
        this.phoneNum = getIntent().getStringExtra("phoneNum");
        this.price = getIntent().getStringExtra("price");
        this.commond = getIntent().getStringExtra("commond");
        this.serviceNum = getIntent().getStringExtra("serviceNum");
        this.svip = getIntent().getStringExtra("svip");
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.pay_commond_layout);
        readParams();
        findView();
        setText();
    }

    private void findView() {
        this.backView = findViewById(R.id.phone_back_btn);
        this.telecom_pay_title = findViewById(R.id.telecom_pay_title);
        this.viewPhoneNum = (TextView) findViewById(R.id.pay_phonenum_price);
        this.pay_commond = (TextView) findViewById(R.id.pay_commond);
        this.pay_send_message = (Button) findViewById(R.id.pay_send_message);
        this.query_pay_result = (Button) findViewById(R.id.query_pay_result);
        this.telecomBottomText = (TextView) findViewById(R.id.telecom_bottom_text);
        this.query_pay_result.setOnClickListener(this);
        this.pay_send_message.setOnClickListener(this);
        this.backView.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_back_btn /*2131363961*/:
            case R.id.telecom_pay_title /*2131363962*/:
                finish();
                return;
            case R.id.pay_send_message /*2131363965*/:
                Intent it = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + this.serviceNum));
                it.putExtra("sms_body", this.commond);
                startActivity(it);
                return;
            case R.id.query_pay_result /*2131363967*/:
                queryPayResult();
                return;
            default:
                return;
        }
    }

    private void setText() {
        this.viewPhoneNum.setText(Html.fromHtml("<html><head><title></title></head><body><font color=\"#393939\">" + getString(2131100932) + " " + this.phoneNum + ":  " + "<font color=\"#00a0e9\">" + this.price + "<font color=\"#393939\">" + LetvUtils.getString(2131099881) + "</body></html>"));
        this.pay_commond.setText(Html.fromHtml("<html><head><title></title></head><body><font color=\"#393939\">" + getString(2131100570) + "<font color=\"#f9a038\">" + this.commond + "<font color=\"#393939\">" + getString(2131100571) + "<font color=\"#fff9a038\">" + this.serviceNum + "</body></html>"));
        this.telecomBottomText.setText(LetvUtils.ToDBC(getResources().getString(2131100164)));
    }

    private void queryPayResult() {
        new LetvRequest(MobilePayResultBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().queryPayResult(0, PreferencesManager.getInstance().getUserId(), this.phoneNum)).setCache(new VolleyNoCache()).setParser(new MobilePayResultParser()).setCallback(new SimpleResponse<MobilePayResultBean>(this) {
            final /* synthetic */ PayCommondActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<MobilePayResultBean> volleyRequest, MobilePayResultBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "qq login requestUserInfoFromToken onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        if (result == null) {
                            return;
                        }
                        if (result.status.equals("1")) {
                            PaySucceedActivity.launch(this.this$0, this.this$0.getResources().getString(2131099770), this.this$0.getResources().getString(2131099771), this.this$0.price, this.this$0.getResources().getString(2131099772), result.ordernumber);
                            return;
                        } else {
                            ToastUtils.showToast(this.this$0, this.this$0.getString(2131099773));
                            return;
                        }
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<MobilePayResultBean> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 17) {
            setResult(resultCode);
        }
        finish();
    }

    public String getActivityName() {
        return PayCommondActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
