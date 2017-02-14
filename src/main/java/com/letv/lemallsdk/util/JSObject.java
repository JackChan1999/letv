package com.letv.lemallsdk.util;

import android.content.Context;
import android.webkit.JavascriptInterface;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.api.PayHttpTask;
import com.letv.lemallsdk.view.LogonManager;
import com.letv.lemallsdk.view.ShareManager;
import io.fabric.sdk.android.services.common.CommonUtils;

public class JSObject {
    private Context context;

    public JSObject(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void jsCallAndroidLePay(String orderId) {
        PayHttpTask.getOrderDetail(orderId, this.context);
        EALogger.i("js", "JS调用android的打开支付SDK，订单id：" + orderId);
    }

    @JavascriptInterface
    public void jsCallAndroidLoginPage() {
        EALogger.i(CommonUtils.SDK, "JS调用android的打开登录页面");
        if (LemallPlatform.getInstance().getmAppInfo().isInlay()) {
            new LogonManager(this.context).showLogon4Inlay();
        } else {
            LemallPlatform.getInstance().getCallbackListener().openLoginPage();
        }
    }

    @JavascriptInterface
    public void jsCallAndroidShare(String shareData) {
        ShareManager.getInstance(this.context).setShareData(shareData);
        EALogger.i(CommonUtils.SDK, "JS调用android的分享，设置分享内容" + shareData);
    }
}
