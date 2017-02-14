package com.tencent.mm.sdk.openapi;

import android.content.Intent;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;

public interface IWXAPI {
    void detach();

    int getWXAppSupportAPI();

    boolean handleIntent(Intent intent, IWXAPIEventHandler iWXAPIEventHandler);

    boolean isWXAppInstalled();

    boolean isWXAppSupportAPI();

    boolean openWXApp();

    boolean registerApp(String str);

    boolean sendReq(BaseReq baseReq);

    boolean sendResp(BaseResp baseResp);

    void unregisterApp();
}
