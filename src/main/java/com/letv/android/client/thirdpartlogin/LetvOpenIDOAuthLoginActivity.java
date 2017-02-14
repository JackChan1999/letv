package com.letv.android.client.thirdpartlogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.UserBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.OpenUserParser;
import com.letv.core.parser.UserParser;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.net.URL;

public class LetvOpenIDOAuthLoginActivity extends WrapActivity implements OnClickListener {
    private TextView address;
    private ImageView back;
    private View back_iv;
    private String baseUrl;
    private ImageView close;
    private ImageView forward;
    private WebView mWebView;
    private ProgressBar progressBar;
    private ImageView refresh;
    private String title;

    class Handler {
        final /* synthetic */ LetvOpenIDOAuthLoginActivity this$0;

        Handler(LetvOpenIDOAuthLoginActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        @JavascriptInterface
        public void show(String data) {
            LogInfo.log("ZSM Handler show == " + data);
            try {
                UserBean result = (UserBean) new OpenUserParser().doParse(data);
                PreferencesManager.getInstance().setUserName(result.username);
                PreferencesManager.getInstance().setUserId(result.uid);
                PreferencesManager.getInstance().setSso_tk(result.tv_token);
                PreferencesManager.getInstance().setRemember_pwd(true);
                PreferencesManager.getInstance().setNickName(result.nickname);
                PreferencesManager.getInstance().setUserMobile(result.mobile);
                PreferencesManager.getInstance().setPicture(result.picture);
                this.this$0.mRequestLoginTaskCallBack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LetvWebViewChromeClient extends WebChromeClient {
        private LetvWebViewChromeClient() {
        }

        /* synthetic */ LetvWebViewChromeClient(LetvOpenIDOAuthLoginActivity x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (LetvOpenIDOAuthLoginActivity.this.progressBar.getVisibility() != 0) {
                LetvOpenIDOAuthLoginActivity.this.progressBar.setVisibility(0);
            }
            LetvOpenIDOAuthLoginActivity.this.progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                LetvOpenIDOAuthLoginActivity.this.progressBar.setVisibility(8);
            }
        }
    }

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        /* synthetic */ LetvWebViewClient(LetvOpenIDOAuthLoginActivity x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LetvOpenIDOAuthLoginActivity.this.progressBar.setVisibility(0);
            LetvOpenIDOAuthLoginActivity.this.progressBar.setProgress(0);
        }

        public void onPageFinished(WebView view, String url) {
            try {
                if (new URL(url).getPath().contains("callbackdata")) {
                    view.setVisibility(4);
                    view.loadUrl("javascript:window.handler.show(document.body.innerText);");
                    view.stopLoading();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (LetvOpenIDOAuthLoginActivity.this.baseUrl.contains(LetvUtils.WEB_INNER_FLAG) || !LetvConfig.getPcode().equals("010110016")) {
                handler.proceed();
            } else {
                handler.cancel();
            }
        }
    }

    public LetvOpenIDOAuthLoginActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Activity context, String url, String title) {
        LetvLogApiTool.getInstance().saveExceptionInfo("webview登录 Current Time :" + StringUtils.getTimeStamp() + "  url == " + url);
        Intent intent = new Intent(context, LetvOpenIDOAuthLoginActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivityForResult(intent, 16);
    }

    public static void launch(Fragment fg, String url, String title) {
        Intent intent = new Intent(fg.getActivity(), LetvOpenIDOAuthLoginActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        fg.startActivityForResult(intent, 16);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letv_webview);
        getWindow().addFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
        this.baseUrl = getIntent().getStringExtra("url");
        this.title = getIntent().getStringExtra("title");
        findView();
    }

    @SuppressLint({"AddJavascriptInterface"})
    private void findView() {
        this.back = (ImageView) findViewById(2131363604);
        this.forward = (ImageView) findViewById(2131363603);
        this.refresh = (ImageView) findViewById(R.id.refresh);
        this.address = (TextView) findViewById(2131362037);
        this.progressBar = (ProgressBar) findViewById(2131362841);
        this.back_iv = findViewById(2131362035);
        this.back_iv.setOnClickListener(this);
        this.address.setText(this.title);
        this.close = (ImageView) findViewById(2131362036);
        this.mWebView = (WebView) findViewById(2131362842);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setSupportZoom(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setUserAgentString(LetvUtils.createUA(this.mWebView.getSettings().getUserAgentString(), this));
        this.mWebView.setVerticalScrollBarEnabled(true);
        this.mWebView.setHorizontalScrollBarEnabled(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.addJavascriptInterface(new Handler(this), "handler");
        this.mWebView.setWebViewClient(new LetvWebViewClient());
        this.mWebView.setWebChromeClient(new LetvWebViewChromeClient());
        LetvUtils.setCookies(this, this.baseUrl);
        this.mWebView.loadUrl(this.baseUrl);
        this.back.setOnClickListener(this);
        this.forward.setOnClickListener(this);
        this.refresh.setOnClickListener(this);
        this.address.setOnClickListener(this);
        this.close.setVisibility(8);
        this.mWebView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ LetvOpenIDOAuthLoginActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                    case 1:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                            break;
                        }
                        break;
                }
                return false;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362035:
                if (this.mWebView.canGoBack()) {
                    this.mWebView.goBack();
                    return;
                } else {
                    finish();
                    return;
                }
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
        try {
            getWindow().clearFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
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
            e.printStackTrace();
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

    private void mRequestLoginTaskCallBack() {
        LogInfo.log("ZSM", "mRequestLoginTaskCallBack URL == " + PlayRecordApi.getInstance().openIDOAuthLoginUrl(0, PreferencesManager.getInstance().getSso_tk()));
        new LetvRequest(UserBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().openIDOAuthLoginUrl(0, PreferencesManager.getInstance().getSso_tk())).setParser(new UserParser()).setCallback(new SimpleResponse<UserBean>(this) {
            final /* synthetic */ LetvOpenIDOAuthLoginActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "mRequestLoginTaskCallBack onNetworkResponse == " + state);
                if (state == NetworkResponseState.SUCCESS) {
                    if ("1".equals(result.status) && !TextUtils.isEmpty(result.vipday)) {
                        ToastUtils.showToast(this.this$0.getActivity(), TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_10000, 2131100798));
                    }
                    this.this$0.setResult(1);
                    this.this$0.finish();
                }
            }

            public void onErrorReport(VolleyRequest<UserBean> volleyRequest, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                DataStatistics.getInstance().sendErrorInfo(LetvApplication.getInstance(), "0", "0", "10001", null, errorInfo, null, null, null, null);
            }
        }).add();
    }

    public String getActivityName() {
        return LetvOpenIDOAuthLoginActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
