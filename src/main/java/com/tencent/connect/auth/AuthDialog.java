package com.tencent.connect.auth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.c;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.open.web.security.JniInterface;
import com.tencent.open.web.security.SecureJsInterface;
import com.tencent.open.web.security.b;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthDialog extends Dialog {
    private static final String a = (f.d + ".authDlg");
    private String b;
    private OnTimeListener c;
    private IUiListener d;
    private Handler e;
    private FrameLayout f;
    private LinearLayout g;
    private FrameLayout h;
    private ProgressBar i;
    private String j;
    private c k;
    private Context l;
    private b m;
    private boolean n = false;
    private int o;
    private String p;
    private String q;
    private long r = 0;
    private long s = 30000;
    private HashMap<String, Runnable> t;

    /* compiled from: ProGuard */
    private class LoginWebViewClient extends WebViewClient {
        final /* synthetic */ AuthDialog a;

        private LoginWebViewClient(AuthDialog authDialog) {
            this.a = authDialog;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            f.b(AuthDialog.a, "-->Redirect URL: " + str);
            if (str.startsWith(AuthConstants.REDIRECT_BROWSER_URI)) {
                JSONObject parseUrlToJson = Util.parseUrlToJson(str);
                this.a.n = this.a.f();
                if (!this.a.n) {
                    if (parseUrlToJson.optString("fail_cb", null) != null) {
                        this.a.callJs(parseUrlToJson.optString("fail_cb"), "");
                    } else if (parseUrlToJson.optInt("fall_to_wv") == 1) {
                        AuthDialog.a(this.a, this.a.b.indexOf("?") > -1 ? "&" : "?");
                        AuthDialog.a(this.a, (Object) "browser_error=1");
                        this.a.k.loadUrl(this.a.b);
                    } else {
                        String optString = parseUrlToJson.optString("redir", null);
                        if (optString != null) {
                            this.a.k.loadUrl(optString);
                        }
                    }
                }
                return true;
            } else if (str.startsWith(ServerSetting.DEFAULT_REDIRECT_URI)) {
                this.a.c.onComplete(Util.parseUrlToJson(str));
                this.a.dismiss();
                return true;
            } else if (str.startsWith("auth://cancel")) {
                this.a.c.onCancel();
                this.a.dismiss();
                return true;
            } else if (str.startsWith("auth://close")) {
                this.a.dismiss();
                return true;
            } else if (str.startsWith("download://")) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring("download://".length()))));
                    intent.addFlags(268435456);
                    this.a.l.startActivity(intent);
                } catch (Exception e) {
                    f.b(AuthDialog.a, "-->start download activity exception, e: " + e.getMessage());
                }
                return true;
            } else if (str.startsWith(AuthConstants.PROGRESS_URI)) {
                try {
                    r0 = Uri.parse(str).getPathSegments();
                    if (r0.isEmpty()) {
                        return true;
                    }
                    int intValue = Integer.valueOf((String) r0.get(0)).intValue();
                    if (intValue == 0) {
                        this.a.h.setVisibility(8);
                        this.a.k.setVisibility(0);
                    } else if (intValue == 1) {
                        this.a.h.setVisibility(0);
                    }
                    return true;
                } catch (Exception e2) {
                    return true;
                }
            } else if (str.startsWith(AuthConstants.ON_LOGIN_URI)) {
                try {
                    r0 = Uri.parse(str).getPathSegments();
                    if (!r0.isEmpty()) {
                        this.a.q = (String) r0.get(0);
                    }
                } catch (Exception e3) {
                }
                return true;
            } else if (this.a.m.a(this.a.k, str)) {
                return true;
            } else {
                f.c(AuthDialog.a, "-->Redirect URL: return false");
                return false;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            f.c(AuthDialog.a, "-->onReceivedError, errorCode: " + i + " | description: " + str);
            if (!Util.checkNetWork(this.a.l)) {
                this.a.c.onError(new UiError(9001, "当前网络不可用，请稍后重试！", str2));
                this.a.dismiss();
            } else if (this.a.p.startsWith(ServerSetting.DOWNLOAD_QQ_URL)) {
                this.a.c.onError(new UiError(i, str, str2));
                this.a.dismiss();
            } else {
                long elapsedRealtime = SystemClock.elapsedRealtime() - this.a.r;
                if (this.a.o >= 1 || elapsedRealtime >= this.a.s) {
                    this.a.k.loadUrl(this.a.b());
                    return;
                }
                this.a.o = this.a.o + 1;
                this.a.e.postDelayed(new Runnable(this) {
                    final /* synthetic */ LoginWebViewClient a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.a.k.loadUrl(this.a.a.p);
                    }
                }, 500);
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            f.b(AuthDialog.a, "-->onPageStarted, url: " + str);
            super.onPageStarted(webView, str, bitmap);
            this.a.h.setVisibility(0);
            this.a.r = SystemClock.elapsedRealtime();
            if (!TextUtils.isEmpty(this.a.p)) {
                this.a.e.removeCallbacks((Runnable) this.a.t.remove(this.a.p));
            }
            this.a.p = str;
            Runnable timeOutRunable = new TimeOutRunable(this.a, this.a.p);
            this.a.t.put(str, timeOutRunable);
            this.a.e.postDelayed(timeOutRunable, 120000);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            f.b(AuthDialog.a, "-->onPageFinished, url: " + str);
            this.a.h.setVisibility(8);
            if (this.a.k != null) {
                this.a.k.setVisibility(0);
            }
            if (!TextUtils.isEmpty(str)) {
                this.a.e.removeCallbacks((Runnable) this.a.t.remove(str));
            }
        }

        @TargetApi(8)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
            this.a.c.onError(new UiError(sslError.getPrimaryError(), "请求不合法，请检查手机安全设置，如系统时间、代理等。", "ssl error"));
            this.a.dismiss();
        }
    }

    /* compiled from: ProGuard */
    private class OnTimeListener implements IUiListener {
        String a;
        String b;
        final /* synthetic */ AuthDialog c;
        private String d;
        private IUiListener e;

        public OnTimeListener(AuthDialog authDialog, String str, String str2, String str3, IUiListener iUiListener) {
            this.c = authDialog;
            this.d = str;
            this.a = str2;
            this.b = str3;
            this.e = iUiListener;
        }

        private void a(String str) {
            try {
                onComplete(Util.parseJson(str));
            } catch (JSONException e) {
                e.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            g.a().a(this.d + "_H5", SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.a, false);
            if (this.e != null) {
                this.e.onComplete(jSONObject);
                this.e = null;
            }
        }

        public void onError(UiError uiError) {
            String str = uiError.errorMessage != null ? uiError.errorMessage + this.a : this.a;
            g.a().a(this.d + "_H5", SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, str, false);
            this.c.a(str);
            if (this.e != null) {
                this.e.onError(uiError);
                this.e = null;
            }
        }

        public void onCancel() {
            if (this.e != null) {
                this.e.onCancel();
                this.e = null;
            }
        }
    }

    /* compiled from: ProGuard */
    private class THandler extends Handler {
        final /* synthetic */ AuthDialog a;
        private OnTimeListener b;

        public THandler(AuthDialog authDialog, OnTimeListener onTimeListener, Looper looper) {
            this.a = authDialog;
            super(looper);
            this.b = onTimeListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.b.a((String) message.obj);
                    return;
                case 2:
                    this.b.onCancel();
                    return;
                case 3:
                    AuthDialog.b(this.a.l, (String) message.obj);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    class TimeOutRunable implements Runnable {
        String a = "";
        final /* synthetic */ AuthDialog b;

        public TimeOutRunable(AuthDialog authDialog, String str) {
            this.b = authDialog;
            this.a = str;
        }

        public void run() {
            f.b(AuthDialog.a, "-->timeoutUrl: " + this.a + " | mRetryUrl: " + this.b.p);
            if (this.a.equals(this.b.p)) {
                this.b.c.onError(new UiError(9002, "请求页面超时，请稍后重试！", this.b.p));
                this.b.dismiss();
            }
        }
    }

    static /* synthetic */ String a(AuthDialog authDialog, Object obj) {
        String str = authDialog.b + obj;
        authDialog.b = str;
        return str;
    }

    static {
        try {
            Context context = Global.getContext();
            if (context == null) {
                f.b(a, "-->load wbsafeedit lib fail, because context is null.");
            } else if (new File(context.getFilesDir().toString() + "/" + AuthAgent.SECURE_LIB_NAME).exists()) {
                System.load(context.getFilesDir().toString() + "/" + AuthAgent.SECURE_LIB_NAME);
                f.b(a, "-->load wbsafeedit lib success.");
            } else {
                f.b(a, "-->load wbsafeedit lib fail, because so is not exists.");
            }
        } catch (Throwable e) {
            f.b(a, "-->load wbsafeedit lib error.", e);
        }
    }

    public AuthDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, 16973840);
        this.l = context;
        this.b = str2;
        this.c = new OnTimeListener(this, str, str2, qQToken.getAppId(), iUiListener);
        this.e = new THandler(this, this.c, context.getMainLooper());
        this.d = iUiListener;
        this.j = str;
        this.m = new b();
        getWindow().setSoftInputMode(32);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        c();
        e();
        this.t = new HashMap();
    }

    public void onBackPressed() {
        if (!this.n) {
            this.c.onCancel();
        }
        super.onBackPressed();
    }

    protected void onStop() {
        super.onStop();
    }

    private String a(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        if (!TextUtils.isEmpty(this.q) && this.q.length() >= 4) {
            stringBuilder.append("_u_").append(this.q.substring(this.q.length() - 4));
        }
        return stringBuilder.toString();
    }

    private String b() {
        String str = ServerSetting.DOWNLOAD_QQ_URL + this.b.substring(this.b.indexOf("?") + 1);
        f.c(a, "-->generateDownloadUrl, url: http://qzs.qq.com/open/mobile/login/qzsjump.html?");
        return str;
    }

    private void c() {
        d();
        LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.k = new c(this.l);
        this.k.setLayoutParams(layoutParams);
        this.f = new FrameLayout(this.l);
        layoutParams.gravity = 17;
        this.f.setLayoutParams(layoutParams);
        this.f.addView(this.k);
        this.f.addView(this.h);
        setContentView(this.f);
    }

    private void d() {
        LayoutParams layoutParams;
        this.i = new ProgressBar(this.l);
        this.i.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.g = new LinearLayout(this.l);
        View view = null;
        if (this.j.equals(SystemUtils.ACTION_LOGIN)) {
            layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            layoutParams.leftMargin = 5;
            view = new TextView(this.l);
            if (Locale.getDefault().getLanguage().equals("zh")) {
                view.setText("登录中...");
            } else {
                view.setText("Logging in...");
            }
            view.setTextColor(Color.rgb(255, 255, 255));
            view.setTextSize(18.0f);
            view.setLayoutParams(layoutParams);
        }
        layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.g.setLayoutParams(layoutParams);
        this.g.addView(this.i);
        if (view != null) {
            this.g.addView(view);
        }
        this.h = new FrameLayout(this.l);
        LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams2.leftMargin = 80;
        layoutParams2.rightMargin = 80;
        layoutParams2.topMargin = 40;
        layoutParams2.bottomMargin = 40;
        layoutParams2.gravity = 17;
        this.h.setLayoutParams(layoutParams2);
        this.h.setBackgroundResource(17301504);
        this.h.addView(this.g);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void e() {
        this.k.setVerticalScrollBarEnabled(false);
        this.k.setHorizontalScrollBarEnabled(false);
        this.k.setWebViewClient(new LoginWebViewClient());
        this.k.setWebChromeClient(new WebChromeClient());
        this.k.clearFormData();
        this.k.clearSslPreferences();
        this.k.setOnLongClickListener(new OnLongClickListener(this) {
            final /* synthetic */ AuthDialog a;

            {
                this.a = r1;
            }

            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.k.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ AuthDialog a;

            {
                this.a = r1;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                    case 1:
                        if (!view.hasFocus()) {
                            view.requestFocus();
                            break;
                        }
                        break;
                }
                return false;
            }
        });
        WebSettings settings = this.k.getSettings();
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(this.l.getDir("databases", 0).getPath());
        settings.setDomStorageEnabled(true);
        f.b(a, "-->mUrl : " + this.b);
        this.p = this.b;
        this.k.loadUrl(this.b);
        this.k.setVisibility(4);
        this.k.getSettings().setSavePassword(false);
        this.m.a(new SecureJsInterface(), "SecureJsInterface");
        SecureJsInterface.isPWDEdit = false;
        super.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ AuthDialog a;

            {
                this.a = r1;
            }

            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    JniInterface.clearAllPWD();
                } catch (Exception e) {
                }
            }
        });
    }

    private boolean f() {
        AuthMap instance = AuthMap.getInstance();
        String makeKey = instance.makeKey();
        Auth auth = new Auth();
        auth.listener = this.d;
        auth.dialog = this;
        auth.key = makeKey;
        String str = instance.set(auth);
        String substring = this.b.substring(0, this.b.indexOf("?"));
        Bundle parseUrl = Util.parseUrl(this.b);
        parseUrl.putString("token_key", makeKey);
        parseUrl.putString("serial", str);
        parseUrl.putString("browser", "1");
        this.b = substring + "?" + Util.encodeUrl(parseUrl);
        return Util.openBrowser(this.l, this.b);
    }

    private static void b(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt("type");
            Toast.makeText(context.getApplicationContext(), parseJson.getString("msg"), i).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callJs(String str, String str2) {
        this.k.loadUrl("javascript:" + str + "(" + str2 + ");void(" + System.currentTimeMillis() + ");");
    }

    public void dismiss() {
        this.t.clear();
        this.e.removeCallbacksAndMessages(null);
        if (isShowing()) {
            super.dismiss();
        }
        if (this.k != null) {
            this.k.destroy();
            this.k = null;
        }
    }
}
