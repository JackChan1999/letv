package com.letv.android.client.webview;

import android.text.TextUtils;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.letv.core.utils.LogInfo;

public class LetvWebViewChromeClient extends WebChromeClient {
    LetvBaseWebViewActivity mBaseWebViewActivity;

    public LetvWebViewChromeClient(LetvBaseWebViewActivity webViewActivity) {
        this.mBaseWebViewActivity = webViewActivity;
    }

    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        LogInfo.log("wlx", "url: " + url + ", message: " + message + ", result: " + result);
        return super.onJsAlert(view, url, message, result);
    }

    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        LogInfo.log("wlx", "url: " + url + ", message: " + message + ", defaultValue: " + defaultValue + ", result: " + result);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    public void onProgressChanged(WebView view, int newProgress) {
        boolean z = true;
        if (!this.mBaseWebViewActivity.isLoading()) {
            this.mBaseWebViewActivity.progressBar.setVisibility(8);
            if (newProgress == 100) {
                LogInfo.log("wlx", "progress=100");
                this.mBaseWebViewActivity.loadFinish = true;
                if (this.mBaseWebViewActivity.mShareProtocol != null) {
                    this.mBaseWebViewActivity.mShareProtocol.setIsLoadComplete(this.mBaseWebViewActivity.loadFinish);
                    this.mBaseWebViewActivity.mShareProtocol.notifyShareLayout();
                }
            }
        } else if (this.mBaseWebViewActivity.getBaseUrl().startsWith("http://v.ifeng.com")) {
            this.mBaseWebViewActivity.progressBar.setVisibility(8);
        } else {
            if (this.mBaseWebViewActivity.progressBar.getVisibility() != 0) {
                this.mBaseWebViewActivity.progressBar.setVisibility(0);
            }
            if (newProgress > 10 && newProgress < 100) {
                this.mBaseWebViewActivity.progressBar.setProgress(newProgress);
                this.mBaseWebViewActivity.loadFinish = false;
            } else if (newProgress == 100) {
                LogInfo.log("wlx", "progress=100");
                if (this.mBaseWebViewActivity.flag) {
                    this.mBaseWebViewActivity.root.finish();
                } else if (this.mBaseWebViewActivity.root.getVisibility() != 0) {
                    this.mBaseWebViewActivity.root.setVisibility(0);
                }
                this.mBaseWebViewActivity.progressBar.setVisibility(8);
                LetvBaseWebViewActivity letvBaseWebViewActivity = this.mBaseWebViewActivity;
                if (this.mBaseWebViewActivity.mIsLoadingRefresh) {
                    z = false;
                }
                letvBaseWebViewActivity.loadFinish = z;
                this.mBaseWebViewActivity.mIsLoadingRefresh = false;
                if (this.mBaseWebViewActivity.mShareProtocol != null) {
                    this.mBaseWebViewActivity.mShareProtocol.setIsLoadComplete(this.mBaseWebViewActivity.loadFinish);
                    this.mBaseWebViewActivity.mShareProtocol.notifyShareLayout();
                }
            }
        }
    }

    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        LogInfo.log("wlx", message + " -- From line " + lineNumber + " of " + sourceID);
    }

    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
        LogInfo.log("wlx", "触发 WebChromeClient 的onCloseWindow 回调");
    }

    public void onReceivedTitle(WebView view, String title) {
        if (TextUtils.isEmpty(this.mBaseWebViewActivity.loadType)) {
            this.mBaseWebViewActivity.titleView.setText(title);
        } else {
            super.onReceivedTitle(view, title);
        }
        this.mBaseWebViewActivity.mShareDefaultTitle = title;
    }

    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        this.mBaseWebViewActivity.mShareDefaultIcon = url;
        LogInfo.log("wlx", "url =" + url);
    }

    public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
}
