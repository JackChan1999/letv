package com.letv.android.client.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.thirdpartlogin.HongKongLoginWebview;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PointsWeb extends WrapActivity implements OnClickListener {
    private ImageView back;
    private String baseUrl;
    private ImageView close;
    private ImageView forward;
    private String from;
    private final int goToLoginPageIfSatisfyConditions;
    private boolean isFinish;
    private int jumpType;
    private String loadType;
    private WebView mWebView;
    private String nextAction;
    private ProgressBar progressBar;
    private ImageView refresh;
    private TextView titleView;
    private String url;
    private TextView urlTitleView;

    private class LetvWebViewChromeClient extends WebChromeClient {
        private LetvWebViewChromeClient() {
        }

        /* synthetic */ LetvWebViewChromeClient(PointsWeb x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (PointsWeb.this.progressBar.getVisibility() != 0) {
                PointsWeb.this.progressBar.setVisibility(0);
            }
            PointsWeb.this.progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                PointsWeb.this.progressBar.setVisibility(8);
                PointsWeb.this.urlTitleView.setVisibility(8);
            }
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        /* synthetic */ LetvWebViewClient(PointsWeb x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (url.indexOf("?") > 0) {
                    String u = url.substring(0, url.indexOf("?"));
                    if (TextUtils.isEmpty(u)) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    if (PointsWeb.this.jumpType == 0) {
                        if (!".mp4".equals(u.substring(u.lastIndexOf("."), u.length())) || !url.contains("vtype=mp4")) {
                            return super.shouldOverrideUrlLoading(view, url);
                        }
                        view.stopLoading();
                        if (!PointsWeb.this.isFinish) {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setDataAndType(Uri.parse(url), "video/mp4");
                            intent.putExtra("android.intent.extra.screenOrientation", 0);
                            PointsWeb.this.startActivity(intent);
                        }
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public PointsWeb() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.baseUrl = "http://sso.letv.com/user/setUserStatus";
        this.from = "mobile_tv";
        this.url = this.baseUrl;
        this.jumpType = 0;
        this.goToLoginPageIfSatisfyConditions = 123;
    }

    public static void synCookies(Context context, String url) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        LogInfo.log("lxx", "PointsWeb onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letv_webview);
        getWindow().addFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
        String sso = PreferencesManager.getInstance().getSso_tk();
        this.url += "?tk=" + sso + "&from=" + this.from + "&next_action=" + getIntent().getStringExtra("url");
        findView();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void findView() {
        this.close = (ImageView) findViewById(2131362036);
        this.refresh = (ImageView) findViewById(R.id.refresh);
        this.progressBar = (ProgressBar) findViewById(2131362841);
        this.titleView = (TextView) findViewById(2131362037);
        this.urlTitleView = (TextView) findViewById(R.id.webview_title_url);
        this.back = (ImageView) findViewById(2131363604);
        this.forward = (ImageView) findViewById(2131363603);
        this.titleView.setText(getIntent().getStringExtra("title"));
        ((ImageView) findViewById(2131362035)).setOnClickListener(this);
        this.mWebView = (WebView) findViewById(2131362842);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setSupportZoom(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setUserAgentString(LetvUtils.createUA(this.mWebView.getSettings().getUserAgentString(), this));
        this.mWebView.setVerticalScrollBarEnabled(true);
        this.mWebView.setHorizontalScrollBarEnabled(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setWebViewClient(new LetvWebViewClient());
        this.mWebView.setWebChromeClient(new LetvWebViewChromeClient());
        LogInfo.log("lxx", "访问的url: " + this.url);
        goToLoginPageIfSatisfyConditions(this.url);
        LetvUtils.setCookies(this, this.url);
        this.mWebView.loadUrl(this.url);
        if (this.baseUrl != null) {
            this.urlTitleView.setText(this.baseUrl);
        }
        this.back.setOnClickListener(this);
        this.forward.setOnClickListener(this);
        this.refresh.setOnClickListener(this);
        this.close.setOnClickListener(this);
        this.close.setVisibility(8);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362035:
                if (this.mWebView.canGoBack()) {
                    LogInfo.log("lxx", "按下返回值之前的url：" + this.mWebView.getUrl());
                    this.mWebView.goBack();
                    LogInfo.log("lxx", "按下返回值之后的url：" + this.mWebView.getUrl());
                    return;
                }
                finish();
                return;
            case 2131362036:
                finish();
                return;
            case R.id.refresh /*2131362839*/:
                this.mWebView.reload();
                return;
            case 2131363603:
                if (this.mWebView.canGoForward()) {
                    this.mWebView.goForward();
                    return;
                }
                return;
            case 2131363604:
                if (this.mWebView.canGoBack()) {
                    this.mWebView.goBack();
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        gcWebView();
    }

    private void gcWebView() {
        try {
            getWindow().clearFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
            this.isFinish = true;
            if (this.mWebView != null) {
                ViewGroup vg = (ViewGroup) this.mWebView.getParent();
                if (vg != null) {
                    vg.removeView(this.mWebView);
                }
                this.mWebView.stopLoading();
                this.mWebView.setVisibility(8);
                this.mWebView.removeAllViews();
                this.mWebView.destroy();
            }
        } catch (Exception e) {
        }
    }

    protected void onPause() {
        super.onPause();
        callHiddenWebViewMethod("onPause");
    }

    protected void onResume() {
        super.onResume();
        callHiddenWebViewMethod("onResume");
    }

    private void callHiddenWebViewMethod(String name) {
        if (this.mWebView != null) {
            try {
                WebView.class.getMethod(name, new Class[0]).invoke(this.mWebView, new Object[0]);
            } catch (Exception e) {
            }
        }
    }

    protected boolean goToLoginPageIfSatisfyConditions(String url) {
        if (!url.startsWith("http://sso.letv.com/user/mLoginMini") && !url.startsWith("http://sso.letv.com/user/mloginHome")) {
            return false;
        }
        this.nextAction = url.substring(url.indexOf("next_action=") + "next_action=".length(), url.length());
        if (LetvUtils.isInHongKong()) {
            HongKongLoginWebview.launch(this);
        } else {
            startActivityForResult(new Intent(this, LetvLoginActivity.class), 123);
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && PreferencesManager.getInstance().isLogin()) {
            String sso = PreferencesManager.getInstance().getSso_tk();
            String temp = this.nextAction + "?did=" + Global.DEVICEID + "&sig=" + MD5.toMd5(Global.DEVICEID + LetvConstant.MIYUE_ATTENDANCE);
            String loadUrl = "";
            try {
                if (!TextUtils.isEmpty(this.nextAction)) {
                    loadUrl = this.baseUrl + "?tk=" + sso + "&from=" + this.from + "&next_action=" + URLEncoder.encode(temp, "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            LogInfo.log("lxx", "onActivityResult loadUrl = " + loadUrl);
            this.mWebView.loadUrl(loadUrl);
        }
    }

    public String getActivityName() {
        return PointsActivtiy.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
