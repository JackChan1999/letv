package com.letv.android.client.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.letv.android.client.activity.PayFailedActivity;
import com.letv.android.client.module.LetvAlipayManager;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    public WXPayEntryActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.api = WXAPIFactory.createWXAPI(this, LetvAlipayManager.WX_PAY_APP_ID);
        LogInfo.log("weixin", "WXPayEntryActivity appId = " + LetvAlipayManager.WX_PAY_APP_ID);
        this.api.handleIntent(getIntent(), this);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    public void onReq(BaseReq arg0) {
    }

    public void onResp(BaseResp resp) {
        LogInfo.log("weixin", "onPayFinish, errCode = " + resp.errCode);
        finish();
        if (resp.getType() == 5) {
            if (resp.errCode == 0) {
                LetvAlipayManager.getInstance().startPaySucceedActivity("");
            } else if (resp.errCode != -2) {
                PayFailedActivity.launch(this);
            }
        }
        LetvAlipayManager.getInstance().finishWxPay();
    }
}
