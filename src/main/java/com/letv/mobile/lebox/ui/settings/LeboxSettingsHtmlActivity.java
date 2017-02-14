package com.letv.mobile.lebox.ui.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.core.constant.LetvConstant;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.view.ProgressWebView;

public class LeboxSettingsHtmlActivity extends Activity implements OnClickListener {
    private static final int OPERATION_FOMAT = 1;
    private static final int OPERATION_SWITCH_MODE = 2;
    private static final String TAG = LeboxSettingsHtmlActivity.class.getSimpleName();
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
        this.mTitleTextView.setText(R.string.btn_text_lebox_settings);
        this.mWebView = (ProgressWebView) findViewById(R.id.web_view);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.addJavascriptInterface(this, "LetvJSBridge_For_Android");
        String url = HttpRequesetManager.getLeboxDeviceHtmlPath();
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

    @JavascriptInterface
    public void callLeboxChangeFomatState(String jsonString) {
        Logger.d(TAG, "callLeboxChangeFomatState = " + jsonString);
        execRestartTip(jsonString, 1);
    }

    @JavascriptInterface
    public void callLeboxChangeModel(String jsonString) {
        Logger.d(TAG, "callLeboxChangeModel = " + jsonString);
        execRestartTip(jsonString, 2);
    }

    private void execRestartTip(String jsonString, int operation) {
        if (!TextUtils.isEmpty(jsonString) && Boolean.valueOf(jsonString).booleanValue()) {
            Logger.d(TAG, "----isLeboxConnected=" + LeBoxNetworkManager.getInstance().isLeboxConnected());
            switch (operation) {
                case 1:
                    PageJumpUtil.jumpLeboxRipple(this, 45000, getResources().getString(R.string.ripple_page_delay_prompt_format));
                    return;
                case 2:
                    PageJumpUtil.jumpLeboxRipple(this, LetvConstant.WIDGET_UPDATE_UI_TIME, getResources().getString(R.string.ripple_page_delay_prompt_switch_mode));
                    return;
                default:
                    return;
            }
        }
    }
}
