package com.letv.lepaysdk.wxpay;

import android.content.Context;
import android.util.Log;
import com.letv.lepaysdk.model.WXModel;
import com.letv.lepaysdk.utils.LOG;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPay {
    private static WXPay mWxPay;
    private IWXAPI api;
    private Context mContext;
    private WXPayCallback mWxCallback;

    private WXPay(Context context) {
        this.mContext = context;
        this.api = WXAPIFactory.createWXAPI(context.getApplicationContext(), null);
    }

    public static WXPay getInstance(Context context) {
        if (mWxPay == null) {
            mWxPay = new WXPay(context);
        }
        return mWxPay;
    }

    public boolean isSupportWXPay() {
        return this.api.getWXAppSupportAPI() >= 570425345;
    }

    public void wxpay(String content) {
        LOG.logE("content:" + content);
        sendPayReq(content);
    }

    public void setCallback(WXPayCallback callback) {
        this.mWxCallback = callback;
    }

    public boolean isWXAppInstalled() {
        return this.api.isWXAppInstalled();
    }

    public void setResp(BaseResp baseResp) {
        if (this.mWxCallback != null) {
            this.mWxCallback.wxPayCallback(baseResp);
        }
    }

    private void sendPayReq(String result) {
        WXModel model = WXModel.jsonData(result);
        PayReq request = new PayReq();
        request.appId = model.getAppId();
        request.partnerId = model.getPartnerId();
        request.prepayId = model.getPrepayId();
        request.packageValue = model.getPackageValue();
        request.nonceStr = model.getNonceStr();
        request.timeStamp = model.getTimeStamp();
        request.sign = model.getSign();
        this.api.registerApp(model.getAppId());
        LOG.logI("[T]" + model.toString());
        Log.e("Ta", "sendReq: " + this.api.sendReq(request));
    }
}
