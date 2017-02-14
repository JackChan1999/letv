package com.alipay.sdk.app;

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
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.alipay.sdk.util.k;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public class H5PayActivity extends Activity {
    private WebView a;
    private com.alipay.sdk.widget.a b;
    private Handler c;
    private boolean d;
    private boolean e;
    private Runnable f = new g(this);

    private class a extends WebViewClient {
        final /* synthetic */ H5PayActivity a;

        private a(H5PayActivity h5PayActivity) {
            this.a = h5PayActivity;
        }

        public final void onLoadResource(WebView webView, String str) {
        }

        public final void onPageFinished(WebView webView, String str) {
            H5PayActivity.f(this.a);
            this.a.c.removeCallbacks(this.a.f);
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            H5PayActivity.c(this.a);
            this.a.c.postDelayed(this.a.f, 30000);
            super.onPageStarted(webView, str, bitmap);
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            this.a.d = true;
            super.onReceivedError(webView, i, str, str2);
        }

        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (this.a.e) {
                sslErrorHandler.proceed();
                this.a.e = false;
                return;
            }
            this.a.runOnUiThread(new h(this, sslErrorHandler));
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("alipays://platformapi/startApp?")) {
                return false;
            }
            if (TextUtils.equals(str, "sdklite://h5quit") || TextUtils.equals(str, "http://m.alipay.com/?action=h5quit")) {
                l.a = l.a();
                this.a.finish();
                return true;
            } else if (str.startsWith("sdklite://h5quit?result=")) {
                try {
                    String substring = str.substring(str.indexOf("sdklite://h5quit?result=") + 24);
                    int parseInt = Integer.parseInt(substring.substring(substring.lastIndexOf("&end_code=") + 10));
                    if (parseInt == m.SUCCEEDED.a() || parseInt == m.PAY_WAITTING.a()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        substring = URLDecoder.decode(str);
                        substring = substring.substring(substring.indexOf("sdklite://h5quit?result=") + 24, substring.lastIndexOf("&end_code="));
                        if (substring.contains("&return_url=\"")) {
                            int indexOf = substring.indexOf("&return_url=\"") + 13;
                            stringBuilder.append(substring.split("&return_url=\"")[0]).append("&return_url=\"").append(substring.substring(indexOf, substring.indexOf("\"&", indexOf))).append(substring.substring(substring.indexOf("\"&", indexOf)));
                            substring = stringBuilder.toString();
                        }
                        m a = m.a(parseInt);
                        l.a = l.a(a.a(), a.b(), substring);
                        this.a.runOnUiThread(new k(this));
                        return true;
                    }
                    m a2 = m.a(m.FAILED.a());
                    l.a = l.a(a2.a(), a2.b(), "");
                    this.a.runOnUiThread(new k(this));
                    return true;
                } catch (Exception e) {
                    l.a = l.b();
                }
            } else {
                webView.loadUrl(str);
                return true;
            }
        }
    }

    private static void a() {
        Object obj = PayTask.a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception e) {
            }
        }
    }

    private void b() {
        if (this.b == null) {
            this.b = new com.alipay.sdk.widget.a(this);
        }
        this.b.b();
    }

    private void c() {
        if (this.b != null && this.b.a()) {
            this.b.c();
        }
        this.b = null;
    }

    static /* synthetic */ void c(H5PayActivity h5PayActivity) {
        if (h5PayActivity.b == null) {
            h5PayActivity.b = new com.alipay.sdk.widget.a(h5PayActivity);
        }
        h5PayActivity.b.b();
    }

    static /* synthetic */ void f(H5PayActivity h5PayActivity) {
        if (h5PayActivity.b != null && h5PayActivity.b.a()) {
            h5PayActivity.b.c();
        }
        h5PayActivity.b = null;
    }

    public void finish() {
        Object obj = PayTask.a;
        synchronized (obj) {
            try {
                obj.notify();
            } catch (Exception e) {
            }
        }
        super.finish();
    }

    public void onBackPressed() {
        if (!this.a.canGoBack()) {
            l.a = l.a();
            finish();
        } else if (this.d) {
            m a = m.a(m.NETWORK_ERROR.a());
            l.a = l.a(a.a(), a.b(), "");
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
                String string = extras.getString("url");
                if (k.a(string)) {
                    Method method;
                    super.requestWindowFeature(1);
                    this.c = new Handler(getMainLooper());
                    Object string2 = extras.getString("cookie");
                    if (!TextUtils.isEmpty(string2)) {
                        CookieSyncManager.createInstance(getApplicationContext()).sync();
                        CookieManager.getInstance().setCookie(string, string2);
                        CookieSyncManager.getInstance().sync();
                    }
                    View linearLayout = new LinearLayout(getApplicationContext());
                    LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                    linearLayout.setOrientation(1);
                    setContentView(linearLayout, layoutParams);
                    this.a = new WebView(getApplicationContext());
                    layoutParams.weight = 1.0f;
                    this.a.setVisibility(0);
                    linearLayout.addView(this.a, layoutParams);
                    WebSettings settings = this.a.getSettings();
                    settings.setUserAgentString(settings.getUserAgentString() + k.c(getApplicationContext()));
                    settings.setRenderPriority(RenderPriority.HIGH);
                    settings.setSupportMultipleWindows(true);
                    settings.setJavaScriptEnabled(true);
                    settings.setSavePassword(false);
                    settings.setJavaScriptCanOpenWindowsAutomatically(true);
                    settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
                    settings.setAllowFileAccess(false);
                    settings.setTextSize(TextSize.NORMAL);
                    this.a.setVerticalScrollbarOverlay(true);
                    this.a.setWebViewClient(new a());
                    this.a.loadUrl(string);
                    if (VERSION.SDK_INT >= 7) {
                        try {
                            method = this.a.getSettings().getClass().getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
                            if (method != null) {
                                method.invoke(this.a.getSettings(), new Object[]{Boolean.valueOf(true)});
                            }
                        } catch (Exception e) {
                        }
                    }
                    try {
                        method = this.a.getClass().getMethod("removeJavascriptInterface", new Class[0]);
                        if (method != null) {
                            method.invoke(this.a, new Object[]{"searchBoxJavaBridge_"});
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
        if (this.a != null) {
            this.a.removeAllViews();
            this.a.destroy();
            this.a = null;
        }
    }
}
