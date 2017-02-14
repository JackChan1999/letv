package com.sina.weibo.sdk.component;

import android.webkit.WebViewClient;

abstract class WeiboWebViewClient extends WebViewClient {
    protected BrowserRequestCallBack mCallBack;

    WeiboWebViewClient() {
    }

    public void setBrowserRequestCallBack(BrowserRequestCallBack callback) {
        this.mCallBack = callback;
    }
}
