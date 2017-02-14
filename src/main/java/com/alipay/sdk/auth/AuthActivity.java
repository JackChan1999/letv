package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.alipay.sdk.authjs.c;
import com.alipay.sdk.util.k;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends Activity {
    static final String a = "params";
    static final String b = "redirectUri";
    private WebView c;
    private String d;
    private com.alipay.sdk.widget.a e;
    private Handler f;
    private boolean g;
    private boolean h;
    private Runnable i = new d(this);

    private class a extends WebChromeClient {
        final /* synthetic */ AuthActivity a;

        private a(AuthActivity authActivity) {
            this.a = authActivity;
        }

        public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            String message = consoleMessage.message();
            if (TextUtils.isEmpty(message)) {
                return super.onConsoleMessage(consoleMessage);
            }
            Object obj = null;
            if (message.startsWith("h5container.message: ")) {
                obj = message.replaceFirst("h5container.message: ", "");
            }
            if (TextUtils.isEmpty(obj)) {
                return super.onConsoleMessage(consoleMessage);
            }
            AuthActivity.b(this.a, obj);
            return super.onConsoleMessage(consoleMessage);
        }
    }

    private class b extends WebViewClient {
        final /* synthetic */ AuthActivity a;

        private b(AuthActivity authActivity) {
            this.a = authActivity;
        }

        public final void onPageFinished(WebView webView, String str) {
            AuthActivity.g(this.a);
            this.a.f.removeCallbacks(this.a.i);
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            AuthActivity.d(this.a);
            this.a.f.postDelayed(this.a.i, 30000);
            super.onPageStarted(webView, str, bitmap);
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            this.a.h = true;
            super.onReceivedError(webView, i, str, str2);
        }

        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (this.a.g) {
                sslErrorHandler.proceed();
                this.a.g = false;
                return;
            }
            this.a.runOnUiThread(new e(this, sslErrorHandler));
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!AuthActivity.a(this.a, str)) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            webView.stopLoading();
            return true;
        }
    }

    private void a() {
        try {
            if (this.e == null) {
                this.e = new com.alipay.sdk.widget.a(this);
            }
            this.e.b();
        } catch (Exception e) {
            this.e = null;
        }
    }

    static /* synthetic */ void a(AuthActivity authActivity, com.alipay.sdk.authjs.a aVar) {
        if (authActivity.c != null && aVar != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(com.alipay.sdk.authjs.a.e, aVar.i);
                jSONObject.put(com.alipay.sdk.authjs.a.g, aVar.k);
                jSONObject.put(com.alipay.sdk.authjs.a.f, aVar.m);
                jSONObject.put(com.alipay.sdk.authjs.a.h, aVar.l);
                authActivity.runOnUiThread(new c(authActivity, String.format("AlipayJSBridge._invokeJS(%s)", new Object[]{jSONObject.toString()})));
            } catch (JSONException e) {
            }
        }
    }

    private void a(com.alipay.sdk.authjs.a aVar) {
        if (this.c != null && aVar != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(com.alipay.sdk.authjs.a.e, aVar.i);
                jSONObject.put(com.alipay.sdk.authjs.a.g, aVar.k);
                jSONObject.put(com.alipay.sdk.authjs.a.f, aVar.m);
                jSONObject.put(com.alipay.sdk.authjs.a.h, aVar.l);
                runOnUiThread(new c(this, String.format("AlipayJSBridge._invokeJS(%s)", new Object[]{jSONObject.toString()})));
            } catch (JSONException e) {
            }
        }
    }

    static /* synthetic */ boolean a(AuthActivity authActivity, String str) {
        if (TextUtils.isEmpty(str) || str.startsWith("http://") || str.startsWith("https://")) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, authActivity.d)) {
                str = str + "?resultCode=150";
            }
            h.a((Activity) authActivity, str);
        }
        authActivity.finish();
        return true;
    }

    private boolean a(String str) {
        if (TextUtils.isEmpty(str) || str.startsWith("http://") || str.startsWith("https://")) {
            return false;
        }
        if (!"SDKLite://h5quit".equalsIgnoreCase(str)) {
            if (TextUtils.equals(str, this.d)) {
                str = str + "?resultCode=150";
            }
            h.a((Activity) this, str);
        }
        finish();
        return true;
    }

    private void b() {
        if (this.e != null && this.e.a()) {
            this.e.c();
        }
        this.e = null;
    }

    static /* synthetic */ void b(AuthActivity authActivity, String str) {
        String str2;
        c cVar = new c(authActivity.getApplicationContext(), new b(authActivity));
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(com.alipay.sdk.authjs.a.e);
            try {
                if (!TextUtils.isEmpty(string)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(com.alipay.sdk.authjs.a.f);
                    jSONObject2 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                    String string2 = jSONObject.getString(com.alipay.sdk.authjs.a.g);
                    String string3 = jSONObject.getString(com.alipay.sdk.authjs.a.d);
                    com.alipay.sdk.authjs.a aVar = new com.alipay.sdk.authjs.a("call");
                    aVar.j = string3;
                    aVar.k = string2;
                    aVar.m = jSONObject2;
                    aVar.i = string;
                    cVar.a(aVar);
                }
            } catch (Exception e) {
                str2 = string;
                if (!TextUtils.isEmpty(str2)) {
                    try {
                        cVar.a(str2, com.alipay.sdk.authjs.a.a.RUNTIME_ERROR);
                    } catch (JSONException e2) {
                    }
                }
            }
        } catch (Exception e3) {
            str2 = null;
            if (!TextUtils.isEmpty(str2)) {
                cVar.a(str2, com.alipay.sdk.authjs.a.a.RUNTIME_ERROR);
            }
        }
    }

    private void b(String str) {
        String str2;
        c cVar = new c(getApplicationContext(), new b(this));
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(com.alipay.sdk.authjs.a.e);
            try {
                if (!TextUtils.isEmpty(string)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(com.alipay.sdk.authjs.a.f);
                    jSONObject2 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                    String string2 = jSONObject.getString(com.alipay.sdk.authjs.a.g);
                    String string3 = jSONObject.getString(com.alipay.sdk.authjs.a.d);
                    com.alipay.sdk.authjs.a aVar = new com.alipay.sdk.authjs.a("call");
                    aVar.j = string3;
                    aVar.k = string2;
                    aVar.m = jSONObject2;
                    aVar.i = string;
                    cVar.a(aVar);
                }
            } catch (Exception e) {
                str2 = string;
                if (!TextUtils.isEmpty(str2)) {
                    try {
                        cVar.a(str2, com.alipay.sdk.authjs.a.a.RUNTIME_ERROR);
                    } catch (JSONException e2) {
                    }
                }
            }
        } catch (Exception e3) {
            str2 = null;
            if (!TextUtils.isEmpty(str2)) {
                cVar.a(str2, com.alipay.sdk.authjs.a.a.RUNTIME_ERROR);
            }
        }
    }

    static /* synthetic */ void d(AuthActivity authActivity) {
        try {
            if (authActivity.e == null) {
                authActivity.e = new com.alipay.sdk.widget.a(authActivity);
            }
            authActivity.e.b();
        } catch (Exception e) {
            authActivity.e = null;
        }
    }

    static /* synthetic */ void g(AuthActivity authActivity) {
        if (authActivity.e != null && authActivity.e.a()) {
            authActivity.e.c();
        }
        authActivity.e = null;
    }

    public void onBackPressed() {
        if (!this.c.canGoBack()) {
            h.a((Activity) this, this.d + "?resultCode=150");
            finish();
        } else if (this.h) {
            h.a((Activity) this, this.d + "?resultCode=150");
            finish();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                finish();
                return;
            }
            try {
                this.d = extras.getString(b);
                String string = extras.getString("params");
                if (k.a(string)) {
                    Method method;
                    super.requestWindowFeature(1);
                    this.f = new Handler(getMainLooper());
                    View linearLayout = new LinearLayout(getApplicationContext());
                    LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                    linearLayout.setOrientation(1);
                    setContentView(linearLayout, layoutParams);
                    this.c = new WebView(getApplicationContext());
                    layoutParams.weight = 1.0f;
                    this.c.setVisibility(0);
                    linearLayout.addView(this.c, layoutParams);
                    WebSettings settings = this.c.getSettings();
                    settings.setUserAgentString(settings.getUserAgentString() + k.c(getApplicationContext()));
                    settings.setRenderPriority(RenderPriority.HIGH);
                    settings.setSupportMultipleWindows(true);
                    settings.setJavaScriptEnabled(true);
                    settings.setSavePassword(false);
                    settings.setJavaScriptCanOpenWindowsAutomatically(true);
                    settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
                    settings.setAllowFileAccess(false);
                    settings.setTextSize(TextSize.NORMAL);
                    this.c.setVerticalScrollbarOverlay(true);
                    this.c.setWebViewClient(new b());
                    this.c.setWebChromeClient(new a());
                    this.c.setDownloadListener(new a(this));
                    this.c.loadUrl(string);
                    if (VERSION.SDK_INT >= 7) {
                        try {
                            method = this.c.getSettings().getClass().getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
                            if (method != null) {
                                method.invoke(this.c.getSettings(), new Object[]{Boolean.valueOf(true)});
                            }
                        } catch (Exception e) {
                        }
                    }
                    try {
                        method = this.c.getClass().getMethod("removeJavascriptInterface", new Class[0]);
                        if (method != null) {
                            method.invoke(this.c, new Object[]{"searchBoxJavaBridge_"});
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        return;
                    }
                }
                finish();
            } catch (Exception e3) {
                finish();
            }
        } catch (Exception e4) {
            finish();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.c != null) {
            this.c.removeAllViews();
            this.c.destroy();
            this.c = null;
        }
    }
}
