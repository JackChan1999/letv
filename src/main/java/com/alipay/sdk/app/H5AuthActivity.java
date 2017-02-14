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

public class H5AuthActivity extends Activity {
    private WebView a;
    private com.alipay.sdk.widget.a b;
    private Handler c;
    private boolean d;
    private boolean e;
    private Runnable f = new b(this);

    private class a extends WebViewClient {
        final /* synthetic */ H5AuthActivity a;

        private a(H5AuthActivity h5AuthActivity) {
            this.a = h5AuthActivity;
        }

        public final void onPageFinished(WebView webView, String str) {
            H5AuthActivity.f(this.a);
            this.a.c.removeCallbacks(this.a.f);
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            H5AuthActivity.c(this.a);
            this.a.c.postDelayed(this.a.f, 30000);
            super.onPageStarted(webView, str, bitmap);
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            this.a.e = true;
            super.onReceivedError(webView, i, str, str2);
        }

        public final void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (this.a.d) {
                sslErrorHandler.proceed();
                this.a.d = false;
                return;
            }
            this.a.runOnUiThread(new c(this, sslErrorHandler));
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (TextUtils.equals(str, "sdklite://h5quit") || TextUtils.equals(str, "http://m.alipay.com/?action=h5quit")) {
                l.a = l.a();
                this.a.finish();
            } else if (str.startsWith("sdklite://h5quit?result=")) {
                try {
                    String substring = str.substring(str.indexOf("sdklite://h5quit?result=") + 24);
                    int parseInt = Integer.parseInt(substring.substring(substring.lastIndexOf("&end_code=") + 10));
                    m a;
                    if (parseInt == m.SUCCEEDED.a()) {
                        String decode = URLDecoder.decode(str);
                        decode = decode.substring(decode.indexOf("sdklite://h5quit?result=") + 24, decode.lastIndexOf("&end_code="));
                        a = m.a(parseInt);
                        l.a = l.a(a.a(), a.b(), decode);
                    } else {
                        a = m.a(m.FAILED.a());
                        l.a = l.a(a.a(), a.b(), "");
                    }
                } catch (Exception e) {
                    l.a = l.b();
                }
                this.a.runOnUiThread(new f(this));
            } else {
                webView.loadUrl(str);
            }
            return true;
        }
    }

    private static void a() {
        Object obj = AuthTask.a;
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
        try {
            this.b.b();
        } catch (Exception e) {
            this.b = null;
        }
    }

    private void c() {
        if (this.b != null && this.b.a()) {
            this.b.c();
        }
        this.b = null;
    }

    static /* synthetic */ void c(H5AuthActivity h5AuthActivity) {
        if (h5AuthActivity.b == null) {
            h5AuthActivity.b = new com.alipay.sdk.widget.a(h5AuthActivity);
        }
        try {
            h5AuthActivity.b.b();
        } catch (Exception e) {
            h5AuthActivity.b = null;
        }
    }

    static /* synthetic */ void f(H5AuthActivity h5AuthActivity) {
        if (h5AuthActivity.b != null && h5AuthActivity.b.a()) {
            h5AuthActivity.b.c();
        }
        h5AuthActivity.b = null;
    }

    public void finish() {
        Object obj = AuthTask.a;
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
        } else if (this.e) {
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
                    this.a.setDownloadListener(new a(this));
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
