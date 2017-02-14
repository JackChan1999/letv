package com.letv.lepaysdk.wxpay;

import com.tencent.mm.sdk.modelbase.BaseResp;

public interface WXPayCallback {
    void wxPayCallback(BaseResp baseResp);
}
