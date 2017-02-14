package com.letv.lemallsdk.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.letv.lemallsdk.util.OtherUtil;

public class CustomWebView extends WebView {
    public static int Circle = 1;
    public static int Horizontal = 2;
    private boolean isAdd = false;
    private Context mContext;
    private ProgressBar progressBar = null;
    private int progressStyle = Horizontal;

    public CustomWebView(Context context, ProgressBar progressBar) {
        super(context);
        this.mContext = context;
        this.progressBar = progressBar;
        initializeOptions();
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initializeOptions();
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    public void initializeOptions() {
        WebSettings settings = getSettings();
        if (VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        settings.setUserAgentString(OtherUtil.getUserAgent(this.mContext));
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(2);
        settings.setAllowFileAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setAppCacheEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(true);
        CookieManager.getInstance().setAcceptCookie(true);
        setLongClickable(false);
        setScrollbarFadingEnabled(true);
        setScrollBarStyle(0);
        setDrawingCacheEnabled(true);
        setClickable(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        requestFocus();
    }

    public void loadUrl(String url) {
        super.loadUrl(url);
    }
}
