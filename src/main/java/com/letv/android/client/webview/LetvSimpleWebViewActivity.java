package com.letv.android.client.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;

public class LetvSimpleWebViewActivity extends Activity implements OnClickListener {
    CookieManager cookieManager = CookieManager.getInstance();
    private ImageView mBackView;
    private ImageView mCloseView;
    private ImageView mRefreshView;
    String mServerCookie = "";
    private TextView mTitleView;
    private String mUrl;
    String mUrlCookie = "";
    private WebView mWebView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simply_web_view);
        if (getIntent() != null) {
            this.mUrl = getIntent().getStringExtra(LetvLoginActivityConfig.AWARDURL);
        }
        initView();
        initListener();
        initContent();
    }

    private void initView() {
        this.mTitleView = (TextView) findViewById(R.id.letv_webview_title);
        this.mCloseView = (ImageView) findViewById(R.id.close_iv);
        this.mBackView = (ImageView) findViewById(R.id.back_iv);
        this.mRefreshView = (ImageView) findViewById(R.id.refresh_iv);
        this.mWebView = (WebView) findViewById(R.id.wv_layout);
        this.mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LetvSimpleWebViewActivity.this.mWebView.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LetvSimpleWebViewActivity.this.mServerCookie = LetvSimpleWebViewActivity.this.cookieManager.getCookie(url);
                LogInfo.log("fornia", "LetvSimpleWebViewActivity CookieStr:" + LetvSimpleWebViewActivity.this.mServerCookie);
                if (TextUtils.isEmpty(LetvSimpleWebViewActivity.this.mUrlCookie) && !TextUtils.isEmpty(LetvSimpleWebViewActivity.this.mServerCookie)) {
                    LetvSimpleWebViewActivity.this.mUrlCookie = LetvSimpleWebViewActivity.this.mServerCookie;
                    LetvSimpleWebViewActivity.this.cookieManager.setCookie(LetvSimpleWebViewActivity.this.mUrl, LetvSimpleWebViewActivity.this.mUrlCookie);
                    LetvSimpleWebViewActivity.this.mWebView.loadUrl(LetvSimpleWebViewActivity.this.mUrl);
                }
            }
        });
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setSupportZoom(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        LogInfo.log("fornia", "LetvSimpleWebViewActivity useragent LetvSimpleWebViewActivity获取");
        this.mWebView.getSettings().setUserAgentString(LetvUtils.createUA(this.mWebView.getSettings().getUserAgentString(), this));
    }

    private void initListener() {
        this.mCloseView.setOnClickListener(this);
        this.mBackView.setOnClickListener(this);
        this.mRefreshView.setOnClickListener(this);
    }

    private void initContent() {
        if (TextUtils.isEmpty(this.mUrl)) {
            ToastUtils.showToast((Context) this, "网址为空");
            finish();
            return;
        }
        this.mUrlCookie = this.cookieManager.getCookie(this.mUrl);
        LogInfo.log("fornia", "LetvSimpleWebViewActivity LetvSimpleWebViewActivity获取mUrl：" + this.mUrl + "|CookieUrl:" + this.mUrlCookie);
        this.mWebView.loadUrl(this.mUrl);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.close_iv || id == R.id.back_iv) {
            finish();
        } else if (id == R.id.refresh_iv) {
            if (TextUtils.isEmpty(this.mUrlCookie)) {
                this.mUrlCookie = this.mServerCookie;
            }
            this.cookieManager.setCookie(this.mUrl, this.mUrlCookie);
            this.mWebView.reload();
        }
    }
}
