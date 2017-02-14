package com.sina.weibo.sdk.component;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.component.WidgetRequestParam.WidgetRequestCallback;
import com.sina.weibo.sdk.utils.Utility;

class WidgetWeiboWebViewClient extends WeiboWebViewClient {
    private Activity mAct;
    private WeiboAuthListener mListener;
    private WidgetRequestCallback mWidgetCallback;
    private WidgetRequestParam mWidgetRequestParam;

    public WidgetWeiboWebViewClient(Activity activity, WidgetRequestParam requestParam) {
        this.mAct = activity;
        this.mWidgetRequestParam = requestParam;
        this.mWidgetCallback = requestParam.getWidgetRequestCallback();
        this.mListener = requestParam.getAuthListener();
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.mCallBack != null) {
            this.mCallBack.onPageStartedCallBack(view, url, favicon);
        }
        super.onPageStarted(view, url, favicon);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (this.mCallBack != null) {
            this.mCallBack.shouldOverrideUrlLoadingCallBack(view, url);
        }
        boolean needClose = url.startsWith(WeiboSdkBrowser.BROWSER_CLOSE_SCHEME);
        if (!url.startsWith(WeiboSdkBrowser.BROWSER_CLOSE_SCHEME) && !url.startsWith(WeiboSdkBrowser.BROWSER_WIDGET_SCHEME)) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        Bundle bundle = Utility.parseUri(url);
        if (!(bundle.isEmpty() || this.mListener == null)) {
            this.mListener.onComplete(bundle);
        }
        if (this.mWidgetCallback != null) {
            this.mWidgetCallback.onWebViewResult(url);
        }
        if (needClose) {
            WeiboSdkBrowser.closeBrowser(this.mAct, this.mWidgetRequestParam.getAuthListenerKey(), this.mWidgetRequestParam.getWidgetRequestCallbackKey());
        }
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        if (this.mCallBack != null) {
            this.mCallBack.onPageFinishedCallBack(view, url);
        }
        super.onPageFinished(view, url);
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (this.mCallBack != null) {
            this.mCallBack.onReceivedErrorCallBack(view, errorCode, description, failingUrl);
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.mCallBack != null) {
            this.mCallBack.onReceivedSslErrorCallBack(view, handler, error);
        }
        super.onReceivedSslError(view, handler, error);
    }
}
