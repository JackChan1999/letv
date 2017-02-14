package com.letv.android.client.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.letv.core.BaseApplication;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncUserStateUtil {
    private static final String USER_STATUS_URL = "http://sso.letv.com/user/setUserStatus";
    private static boolean mIsSyncUserStateSuccessWithH5 = false;
    private GetUserloginStateListener mListener;

    public interface GetUserloginStateListener {
        void onSyncFailed();

        void onSyncSuccessed();
    }

    class JSHandler {
        JSHandler() {
        }

        @JavascriptInterface
        public void show(String data) {
            LogInfo.log("wlx", "data: " + data);
            try {
                if ("0".equals(new JSONObject(data).getString("errorCode"))) {
                    if (SyncUserStateUtil.this.mListener != null) {
                        SyncUserStateUtil.this.mListener.onSyncSuccessed();
                    }
                    LogInfo.log("wlx", "同步状态成功");
                    if (PreferencesManager.getInstance().isLogin()) {
                        String token = SyncUserStateUtil.getCookieSsoToken(SyncUserStateUtil.getSyncUserStateUrl());
                        LogInfo.log("wlx", "token = " + token);
                        if (!TextUtils.isEmpty(token)) {
                            SyncUserStateUtil.setGameTokenCookie(BaseApplication.getInstance(), token);
                        }
                    }
                    SyncUserStateUtil.mIsSyncUserStateSuccessWithH5 = true;
                    return;
                }
                if (SyncUserStateUtil.this.mListener != null) {
                    SyncUserStateUtil.this.mListener.onSyncFailed();
                }
                LogInfo.log("wlx", "同步状态失败");
                SyncUserStateUtil.mIsSyncUserStateSuccessWithH5 = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public SyncUserStateUtil(GetUserloginStateListener listener) {
        this.mListener = listener;
    }

    public static SyncUserStateUtil getUserStateUtil() {
        return new SyncUserStateUtil(null);
    }

    public static SyncUserStateUtil getUserStateUtil(GetUserloginStateListener listener) {
        return new SyncUserStateUtil(listener);
    }

    public static boolean isNeedSyncUserState() {
        return !mIsSyncUserStateSuccessWithH5;
    }

    public static String getSyncUserStateUrl() {
        String webSsoUrl = "";
        if (!PreferencesManager.getInstance().isLogin()) {
            return "http://sso.letv.com/user/setUserStatus?tk=&from=mobile_tv";
        }
        String ssoTk = PreferencesManager.getInstance().getSso_tk();
        if (TextUtils.isEmpty(ssoTk)) {
            return webSsoUrl;
        }
        return "http://sso.letv.com/user/setUserStatus?tk=" + ssoTk + "&from=mobile_tv";
    }

    @SuppressLint({"AddJavascriptInterface"})
    public synchronized void syncUserStateByAsync(Context context) {
        LogInfo.log("wlx", "通知WebView客户端登录登出状态");
        mIsSyncUserStateSuccessWithH5 = false;
        WebView webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSHandler(), "jshandler");
        webView.setVisibility(8);
        webView.loadUrl(getSyncUserStateUrl());
        webView.setWebViewClient(new 1(this));
    }

    public static void setGameTokenCookie(Context context, String token) {
        CookieSyncManager syncManger = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("http://api.game.letvstore.com", "sso_tk=" + token);
        syncManger.sync();
        LogInfo.log("wlx", "game cookie =  " + cookieManager.getCookie("http://api.game.letvstore.com"));
    }

    public static String getCookieSsoToken(String url) {
        String cookies = CookieManager.getInstance().getCookie(url);
        String sso_tk = "";
        if (!TextUtils.isEmpty(cookies)) {
            for (String v : cookies.split(";")) {
                if (v.contains("sso_tk=")) {
                    sso_tk = v.trim().replace("sso_tk=", "");
                    break;
                }
            }
        }
        LogInfo.log("wlx", "cookie sso_tk =" + sso_tk);
        return sso_tk;
    }

    public static boolean isSyncUserStateSuccessWithH5() {
        return mIsSyncUserStateSuccessWithH5;
    }
}
