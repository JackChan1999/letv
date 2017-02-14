package com.letv.lepaysdk.alipay;

import android.app.Activity;

public class AliPay {
    private AliPayCallback mAliPayCallback;

    public void pay(Activity activity, String content) {
        new Thread(new 1(this, activity, content)).start();
    }

    public void setAliPayCallback(AliPayCallback aliPayCallback) {
        this.mAliPayCallback = aliPayCallback;
    }
}
