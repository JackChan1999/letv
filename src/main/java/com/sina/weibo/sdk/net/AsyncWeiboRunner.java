package com.sina.weibo.sdk.net;

import android.content.Context;
import com.sina.weibo.sdk.cmd.WbAppActivator;
import com.sina.weibo.sdk.exception.WeiboException;

public class AsyncWeiboRunner {
    private Context mContext;

    public AsyncWeiboRunner(Context context) {
        this.mContext = context;
    }

    @Deprecated
    public void requestByThread(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        new 1(this, url, httpMethod, params, listener).start();
    }

    public String request(String url, WeiboParameters params, String httpMethod) throws WeiboException {
        WbAppActivator.getInstance(this.mContext, params.getAppKey()).activateApp();
        return HttpManager.openUrl(this.mContext, url, httpMethod, params);
    }

    public void requestAsync(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        WbAppActivator.getInstance(this.mContext, params.getAppKey()).activateApp();
        new RequestRunner(this.mContext, url, params, httpMethod, listener).execute(new Void[1]);
    }
}
