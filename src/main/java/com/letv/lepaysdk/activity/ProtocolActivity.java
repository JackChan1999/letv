package com.letv.lepaysdk.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.letv.lepaysdk.utils.LOG;
import com.letv.lepaysdk.utils.ResourceUtil;
import com.letv.lepaysdk.view.LePayActionBar;
import com.letv.lepaysdk.view.MProgressDialog;

public class ProtocolActivity extends Activity {
    private LePayActionBar mActionBar;
    private WebView mWebView;
    private String url = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ResourceUtil.getLayoutResource(this, "lepay_protocol_activity"));
        initView();
        setListeners();
    }

    private void initView() {
        this.mActionBar = (LePayActionBar) findViewById(ResourceUtil.getIdResource(this, "lepay_actionbar"));
        this.mActionBar.setTitle(getString(ResourceUtil.getStringResource(this, "lepay_activity_protocol_title")));
        this.mActionBar.setRightButtonVisable(8);
        this.mWebView = (WebView) findViewById(ResourceUtil.getIdResource(this, "lepay_webview"));
        this.url = getIntent().getStringExtra("protocol");
        if (this.url != null) {
            this.mWebView.loadUrl(this.url);
        } else {
            LOG.logE("url 为空");
        }
    }

    private void setListeners() {
        this.mActionBar.setLeftButtonOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProtocolActivity.this.onBackPressed();
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                MProgressDialog.showProgressDialog(ProtocolActivity.this);
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                MProgressDialog.dismissProgressDialog();
                super.onPageFinished(view, url);
            }
        });
    }
}
