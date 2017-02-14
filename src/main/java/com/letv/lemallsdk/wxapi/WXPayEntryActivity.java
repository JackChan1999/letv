package com.letv.lemallsdk.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.letv.lepaysdk.activity.CashierAcitivity;
import com.letv.lepaysdk.utils.LOG;
import com.letv.lepaysdk.wxpay.WXPay;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String APP_ID = "wxecf7ff1c1503e73d";
    private IWXAPI api;
    private CashierAcitivity mCashierAcitivity;
    private WXPay wxPay;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.wxPay = WXPay.getInstance(this);
        this.api = WXAPIFactory.createWXAPI(this, APP_ID);
        this.api.handleIntent(getIntent(), this);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    public void onReq(BaseReq req) {
    }

    public void onResp(BaseResp resp) {
        this.wxPay.setResp(resp);
        LOG.logE("resp:" + resp.errCode);
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
