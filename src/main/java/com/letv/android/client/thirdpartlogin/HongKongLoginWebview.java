package com.letv.android.client.thirdpartlogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
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
import com.letv.android.client.R;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.controller.RedPacketSdkController;
import com.letv.android.client.receiver.TokenLoseReceiver;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.UserBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LoginConstant;
import com.letv.core.constant.VipProductContant;
import com.letv.core.contentprovider.UserInfoTools;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.tencent.open.SocialConstants;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class HongKongLoginWebview extends Activity implements OnClickListener {
    private TextView address;
    private String baseUrl;
    private ImageView mBackImageView;
    private String mRedPacketAwardUrl;
    private PublicLoadLayout mRootLayout;
    private WebView mWebView;
    private int mforWhat;
    private ProgressBar progressBar;
    private ImageView refresh;

    class Handler {
        final /* synthetic */ HongKongLoginWebview this$0;

        Handler(HongKongLoginWebview this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        @JavascriptInterface
        public void show(String data) {
            this.this$0.mRootLayout.post(new 1(this));
            String callBack = data.substring(data.indexOf("(") + 1, data.indexOf(")"));
            LogInfo.log("ZSM onPageFinished Handler show == " + callBack);
            if (callBack.contains("status")) {
                ToastUtils.showToast(2131100346);
                return;
            }
            try {
                JSONObject object = new JSONObject(callBack);
                PreferencesManager.getInstance().setUserName(object.getString("nickname"));
                PreferencesManager.getInstance().setUserId(object.getString("uid"));
                PreferencesManager.getInstance().setNickName(object.getString("nickname"));
                PreferencesManager.getInstance().setPicture(object.getString(SocialConstants.PARAM_AVATAR_URI));
                this.this$0.mRequestLoginTaskCallBack();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LetvWebViewChromeClient extends WebChromeClient {
        private LetvWebViewChromeClient() {
        }

        /* synthetic */ LetvWebViewChromeClient(HongKongLoginWebview x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (HongKongLoginWebview.this.progressBar.getVisibility() != 0) {
                HongKongLoginWebview.this.progressBar.setVisibility(0);
            }
            HongKongLoginWebview.this.progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                HongKongLoginWebview.this.progressBar.setVisibility(8);
            }
        }

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogInfo.log("HongkongLoginWebview title == " + title);
            HongKongLoginWebview.this.address.setText(title);
        }
    }

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        /* synthetic */ LetvWebViewClient(HongKongLoginWebview x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            HongKongLoginWebview.this.progressBar.setVisibility(0);
            HongKongLoginWebview.this.progressBar.setProgress(0);
        }

        public void onPageFinished(WebView view, String url) {
            try {
                String path = new URL(url).getPath();
                LogInfo.log("ZSM onPageFinished path = " + path);
                if (path.contains("checklogin")) {
                    LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_WEBVIEW_COOKIE_SSO_TOKEN, url));
                    if (LeResponseMessage.checkResponseMessageValidity(response, String.class)) {
                        PreferencesManager.getInstance().setSso_tk((String) response.getData());
                    }
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
            if (HongKongLoginWebview.this.baseUrl.contains(LetvUtils.WEB_INNER_FLAG) || !LetvConfig.getPcode().equals("010110016")) {
                handler.proceed();
            } else {
                handler.cancel();
            }
        }
    }

    public HongKongLoginWebview() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mforWhat = 0;
        this.mRedPacketAwardUrl = "";
    }

    public static void launch(Activity context) {
        if (NetworkUtils.isNetworkAvailable()) {
            context.startActivityForResult(new Intent(context, HongKongLoginWebview.class), 16);
        } else {
            ToastUtils.showToast((Context) context, 2131100493);
        }
    }

    public static void launch(Activity context, int forWhat) {
        if (NetworkUtils.isNetworkAvailable()) {
            Intent intent = new Intent(context, HongKongLoginWebview.class);
            intent.putExtra(LetvLoginActivityConfig.FOR_WHAT, forWhat);
            context.startActivityForResult(intent, 0);
            return;
        }
        ToastUtils.showToast((Context) context, 2131100493);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRootLayout = PublicLoadLayout.createPage((Context) this, (int) R.layout.hongkong_login_webview);
        setContentView(this.mRootLayout);
        getWindow().addFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
        this.baseUrl = "https://sso.le.com/userGlobal/mlogin?next_action=http%3A%2F%2Fsso.le.com%2Fopen%2Fchecklogin%3Fjsonp%3Djxb_hk&isApp=1&language=zh-HK";
        LogInfo.log("ZSM hongkong login baseUrl == " + this.baseUrl);
        findView();
    }

    @SuppressLint({"AddJavascriptInterface"})
    private void findView() {
        if (!(getIntent() == null || getIntent().getExtras() == null)) {
            this.mforWhat = getIntent().getIntExtra(LetvLoginActivityConfig.FOR_WHAT, 0);
            this.mRedPacketAwardUrl = getIntent().getStringExtra(LetvLoginActivityConfig.AWARDURL);
        }
        this.refresh = (ImageView) findViewById(R.id.refresh);
        this.address = (TextView) findViewById(2131362037);
        this.progressBar = (ProgressBar) findViewById(2131362841);
        this.mBackImageView = (ImageView) findViewById(R.id.hongkong_login_back_btn);
        this.mBackImageView.setOnClickListener(this);
        this.mWebView = (WebView) findViewById(2131362842);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setSupportZoom(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setUserAgentString(LetvUtils.createUA(this.mWebView.getSettings().getUserAgentString(), this));
        LogInfo.log("ZSM UA == " + LetvUtils.createUA(this.mWebView.getSettings().getUserAgentString()));
        this.mWebView.setVerticalScrollBarEnabled(true);
        this.mWebView.setHorizontalScrollBarEnabled(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.addJavascriptInterface(new Handler(this), "handler");
        this.mWebView.setWebViewClient(new LetvWebViewClient());
        this.mWebView.setWebChromeClient(new LetvWebViewChromeClient());
        LetvUtils.setCookies(this, this.baseUrl);
        this.mWebView.loadUrl(this.baseUrl);
        this.refresh.setOnClickListener(this);
        this.address.setOnClickListener(this);
        this.mWebView.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ HongKongLoginWebview this$0;

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
            case R.id.hongkong_login_back_btn /*2131362838*/:
                if (this.mWebView.canGoBack()) {
                    this.mWebView.goBack();
                    return;
                } else {
                    finish();
                    return;
                }
            case R.id.refresh /*2131362839*/:
                if (NetworkUtils.isNetworkAvailable()) {
                    this.mWebView.reload();
                    return;
                } else {
                    ToastUtils.showToast((Context) this, 2131100493);
                    return;
                }
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
        RequestUserByTokenTask.getUserByTokenTask(this, PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ HongKongLoginWebview this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.setResult(1);
                    this.this$0.loginSuccess();
                    this.this$0.finish();
                }
            }
        });
    }

    private void loginSuccess() {
        LogInfo.log("ZSM", "登陆成功后的数据统一处理");
        TokenLoseReceiver.sTokenTostShow = false;
        if (TextUtils.isEmpty(this.mRedPacketAwardUrl)) {
            setResult(1);
        } else {
            Intent redPacketIntent = new Intent();
            redPacketIntent.putExtra(LetvLoginActivityConfig.AWARDURL, this.mRedPacketAwardUrl);
            setResult(1, redPacketIntent);
        }
        if (PreferencesManager.getInstance().isVip()) {
            LetvUtils.sendBroadcast(this, VipProductContant.ACTION_VIP_AUTH_PASS);
        }
        try {
            LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(LeMessageIds.MSG_WEBVIEW_SYNC_LOGIN));
            UserInfoTools.login(this, PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getSso_tk(), PreferencesManager.getInstance().getShareUserId(), PreferencesManager.getInstance().getShareToken());
            StatisticsUtils.statisticsLoginAndEnv(this, 2, true);
            StatisticsUtils.statisticsLoginAndEnv(this, 2, false);
        } catch (Exception e) {
        }
        RedPacketSdkManager.getInstance().setUid(PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : "");
        RedPacketSdkManager.getInstance().setToken(PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getSso_tk() : "");
        try {
            LemallPlatform.getInstance().setSsoToken(PreferencesManager.getInstance().getSso_tk());
        } catch (Exception e2) {
        }
        if (8 != this.mforWhat) {
            DBManager.getInstance().getFavoriteTrace().requestPostFavouriteThenDeleteDbBean();
        }
        if (3 == this.mforWhat) {
            MainLaunchUtils.jump2My(this);
        }
        if (LoginConstant.LOGIN_FROM_RED_PACKET == this.mforWhat) {
            RedPacketSdkController.jumpRedPacketList(this);
        }
    }
}
