package com.letv.mobile.lebox.ui.buy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;

public class LeboxBuyHtmlActivity extends Activity implements OnClickListener {
    private static final String TAG = LeboxBuyHtmlActivity.class.getSimpleName();
    ImageView mBackImage;
    TextView mTitleTextView;
    WebView mWebView;

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
        this.mTitleTextView.setText(R.string.btn_text_buy_box);
        this.mWebView = (WebView) findViewById(R.id.web_view);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        String url = "http://m.lemall.com/products/view/pid-GWGZ300051.html";
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
            if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || TextUtils.isEmpty(LeboxQrCodeBean.getPassword())) {
                PageJumpUtil.jumpToIntroduce(this);
            } else {
                PageJumpUtil.jumpLeBoxMainActivity(this);
            }
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || TextUtils.isEmpty(LeboxQrCodeBean.getPassword())) {
                PageJumpUtil.jumpToIntroduce(this);
            } else {
                PageJumpUtil.jumpLeBoxMainActivity(this);
            }
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
