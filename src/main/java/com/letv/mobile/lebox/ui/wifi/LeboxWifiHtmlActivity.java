package com.letv.mobile.lebox.ui.wifi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.view.ProgressWebView;

public class LeboxWifiHtmlActivity extends Activity implements OnClickListener {
    private static final String TAG = LeboxWifiHtmlActivity.class.getSimpleName();
    ImageView mBackImage;
    TextView mTitleTextView;
    ProgressWebView mWebView;

    private class HelloWebViewClient extends WebViewClient {
        private HelloWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initView() {
        this.mBackImage = (ImageView) findViewById(R.id.common_nav_left);
        this.mBackImage.setOnClickListener(this);
        this.mTitleTextView = (TextView) findViewById(R.id.common_nav_title);
        this.mTitleTextView.setText(R.string.btn_text_valid_wifi);
        this.mWebView = (ProgressWebView) findViewById(R.id.web_view);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        String url = HttpRequesetManager.getLeboxWifiHtmlPath();
        Logger.d(TAG, url);
        this.mWebView.loadUrl(url);
        this.mWebView.setWebViewClient(new HelloWebViewClient());
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        if (v == this.mBackImage) {
            finish();
        }
    }
}
