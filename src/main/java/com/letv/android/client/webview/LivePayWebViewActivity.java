package com.letv.android.client.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.letv.android.client.webview.JavaScriptinterface.PayCallBack;
import com.letv.core.utils.LogInfo;

public class LivePayWebViewActivity extends LetvBaseWebViewActivity {

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LivePayWebViewActivity.this.getWebView().loadUrl(url);
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSBridgeUtil.getInstance().getJavaScriptinterface().setPayCallBack(new PayCallBack() {
            public void onPaySuccessed() {
                LivePayWebViewActivity.this.setResult(257);
                LogInfo.log("pay_", "pay successed finish web page");
                LivePayWebViewActivity.this.finish();
            }

            public void onPayFailed() {
                LogInfo.log("pay_", "pay failed finish web page");
                LivePayWebViewActivity.this.setResult(258);
            }
        });
        getWebView().setWebViewClient(new LetvWebViewClient());
        getWebView().loadUrl(getIntent().getStringExtra("url"));
    }
}
