package com.coolcloud.uac.android.api.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.authjs.a;
import com.coolcloud.uac.android.api.view.basic.BasicActivity;
import com.coolcloud.uac.android.common.callback.ActivityResponse;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.SystemUtils;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.coolcloud.uac.android.common.util.TimeUtils;
import com.coolcloud.uac.android.common.util.ValidUtils;
import com.facebook.internal.NativeProtocol;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class AssistActivity extends BasicActivity {
    private static final String KEY_TITLE_RESID = "titleResId";
    public static final String KEY_URL = "url";
    private static final String TAG = "AssistActivity";
    private long mStartMillis = 0;
    private View mStatusBar = null;
    private String mUrl = null;

    public static Intent getIntent(Context context, String titleResId, String url, String appId) {
        if (ValidUtils.isUrlValid(url)) {
            try {
                Intent i = new Intent(context, AssistActivity.class);
                i.addFlags(268435456);
                KVUtils.put(i, KEY_TITLE_RESID, titleResId);
                Builder ub = Uri.parse(url).buildUpon();
                ub.appendQueryParameter("pv", "1");
                ub.appendQueryParameter("titlebar", "0");
                ub.appendQueryParameter("appid", appId);
                ub.appendQueryParameter(a.c, "http://auther.coolyun.com");
                ub.appendQueryParameter("clientype", SystemUtils.getDeviceType());
                ub.appendQueryParameter(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, SystemUtils.getDeviceId(context));
                ub.appendQueryParameter("devmodel", SystemUtils.getDeviceModel());
                ub.appendQueryParameter("netype", SystemUtils.getNetworkType(context));
                ub.appendQueryParameter("tm", TimeUtils.nowTime());
                KVUtils.put(i, "url", ub.toString());
                return i;
            } catch (Throwable t) {
                LOG.e(TAG, "[url:" + url + "][appId:" + appId + "] create intent failed(Throwable)", t);
                return null;
            }
        }
        LOG.e(TAG, "[url:" + url + "][appId:" + appId + "] url invalid");
        return null;
    }

    public static Intent getIntent(Context context, String titleSrc, String url) {
        if (ValidUtils.isUrlValid(url)) {
            try {
                Intent i = new Intent(context, AssistActivity.class);
                i.addFlags(268435456);
                KVUtils.put(i, KEY_TITLE_RESID, titleSrc);
                KVUtils.put(i, "url", url);
                return i;
            } catch (Throwable t) {
                LOG.e(TAG, "[url:" + url + "] create intent failed(Throwable)", t);
                return null;
            }
        }
        LOG.e(TAG, "[url:" + url + "] url invalid");
        return null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        int rcode;
        LOG.d(TAG, "on create ...");
        super.onCreate(savedInstanceState, "uac_sdk_assist", "umgr_assist_header", KVUtils.get(getIntent(), KEY_TITLE_RESID));
        this.mStatusBar = this.mRootView.findViewWithTag("umgr_assist_header");
        if (TextUtils.equal(KVUtils.get(getIntent(), "from", "coolcloud"), "gameassist")) {
            this.mBackLayout.setVisibility(8);
        }
        loadPrivateConfig();
        this.mUrl = KVUtils.get(getIntent(), "url");
        if (ValidUtils.isUrlValid(this.mUrl)) {
            try {
                createView(this, this.mUrl);
                rcode = 0;
            } catch (Throwable t) {
                LOG.e(TAG, "[url:" + this.mUrl + "] create view failed(Throwable)", t);
                rcode = 2;
            }
        } else {
            rcode = 4017;
        }
        if (rcode != 0) {
            callbackError(rcode, "" + rcode);
        }
        this.mStartMillis = System.currentTimeMillis();
        LOG.i(TAG, "[url:" + this.mUrl + "] on create done(" + rcode + ")");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (4 == keyCode) {
            callbackCancel();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createView(Context context, String url) {
        WebView webView = (WebView) this.mRootView.findViewWithTag("uac_assist_webview");
        setWebViewAttributes(webView);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LOG.i(AssistActivity.TAG, "[url:" + url + "][millis:" + (System.currentTimeMillis() - AssistActivity.this.mStartMillis) + "] should override url loading ...");
                if (url.startsWith("http://auther.coolyun.com")) {
                    AssistActivity.this.handleUrlCallback(url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                LOG.e(AssistActivity.TAG, "[errorCode:" + errorCode + "][description:" + description + "][failingUrl:" + failingUrl + "] on received error ...");
                AssistActivity.this.callbackError(3000, "on received error");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            public void onPageFinished(WebView view, String url) {
                LOG.d(AssistActivity.TAG, "[url:" + url + "][millis:" + (System.currentTimeMillis() - AssistActivity.this.mStartMillis) + "] on page finished ...");
                view.getSettings().setBlockNetworkImage(false);
                if (url.startsWith("https://passport.coolyun.com/sso/logout")) {
                    AssistActivity.this.handleUrlCallback(url);
                }
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    AssistActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    LOG.i(AssistActivity.TAG, "[url:" + url + "] start download ...");
                } catch (Throwable t) {
                    LOG.e(AssistActivity.TAG, "[url:" + url + "] start download failed(Throwable): " + t.getMessage());
                }
            }
        });
        LOG.i(TAG, "[url:" + url + "] load url ...");
        webView.loadUrl(url);
    }

    private void handleUrlCallback(String url) {
        callbackResult(toBundle(url));
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setWebViewAttributes(WebView wv) {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(true);
    }

    private Bundle toBundle(String url) {
        Bundle bundle = new Bundle();
        try {
            if (!TextUtils.empty(url)) {
                String query = Uri.parse(url).getQuery();
                if (!TextUtils.empty(query)) {
                    for (String parameter : query.split("&")) {
                        if (!TextUtils.empty(parameter)) {
                            String[] kv = parameter.split(SearchCriteria.EQ);
                            if (kv != null && kv.length > 1) {
                                KVUtils.put(bundle, TextUtils.trim(kv[0]), TextUtils.trim(kv[1]));
                            }
                        }
                    }
                }
            }
        } catch (Throwable t) {
            LOG.e(TAG, "[url:" + url + "] parse url failed(Throwable)", t);
        }
        return bundle;
    }

    private void callbackResult(Bundle result) {
        LOG.i(TAG, "[url:" + this.mUrl + "] callback result(" + result + ")");
        ActivityResponse response = (ActivityResponse) getIntent().getParcelableExtra("activityResponse");
        if (response != null) {
            response.onResult(result);
        }
        Intent data = new Intent();
        KVUtils.put(data, NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, 0);
        if (result != null) {
            data.putExtras(result);
        }
        setResult(-1, data);
        finish();
    }

    private void callbackError(int error, String message) {
        LOG.i(TAG, "[url:" + this.mUrl + "] callback error(" + error + "," + message + ")");
        ActivityResponse response = (ActivityResponse) getIntent().getParcelableExtra("activityResponse");
        if (response != null) {
            response.onError(error, message);
        }
        Intent data = new Intent();
        KVUtils.put(data, NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, 0);
        KVUtils.put(data, "message", message);
        setResult(-1, data);
        finish();
    }

    private void callbackCancel() {
        LOG.i(TAG, "[url:" + this.mUrl + "] callback cancel");
        ActivityResponse response = (ActivityResponse) getIntent().getParcelableExtra("activityResponse");
        if (response != null) {
            response.onCancel();
        }
        KVUtils.put(new Intent(), NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, -1);
        setResult(0);
        finish();
    }
}
