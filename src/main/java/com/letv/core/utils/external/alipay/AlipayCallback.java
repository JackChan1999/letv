package com.letv.core.utils.external.alipay;

public interface AlipayCallback {
    void onAlipayFail(PayResult payResult);

    void onAlipaySuccess(PayResult payResult, String str);
}
