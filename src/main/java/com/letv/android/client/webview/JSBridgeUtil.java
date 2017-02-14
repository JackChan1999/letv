package com.letv.android.client.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.webkit.WebView;
import com.alipay.sdk.authjs.a;
import com.letv.ads.ex.utils.IAdJSBridge;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSBridgeUtil implements IAdJSBridge {
    private static JSBridgeUtil jsBridgeUtil;
    private static WebView transparentWebView;
    private JavaScriptinterface mJavaScriptinterface;
    private WebView mWebView;

    private JSBridgeUtil() {
    }

    public JavaScriptinterface getJavaScriptinterface() {
        return this.mJavaScriptinterface;
    }

    public void setJavaScriptinterface(JavaScriptinterface javaScriptinterface) {
        this.mJavaScriptinterface = javaScriptinterface;
    }

    public static WebView getTransparentWebView(Context context) {
        if (transparentWebView == null) {
            transparentWebView = new WebView(context);
        }
        return transparentWebView;
    }

    public static JSBridgeUtil getInstance() {
        if (jsBridgeUtil == null) {
            jsBridgeUtil = new JSBridgeUtil();
        }
        return jsBridgeUtil;
    }

    @SuppressLint({"AddJavascriptInterface"})
    public void setJSBridge(Activity activity, WebView webView, Handler handler, View root) {
        this.mWebView = webView;
        JavaScriptinterface obj = new JavaScriptinterface(activity, this.mWebView, root);
        obj.setHandler(handler);
        this.mWebView.addJavascriptInterface(obj, "LetvJSBridge_For_Android");
        setJavaScriptinterface(obj);
    }

    public void unRegisterAllObservers() {
        getJavaScriptinterface().removeAllObservers();
    }

    public void loginCallBack(boolean loginResult) {
        try {
            Map<String, Object> map = new HashMap();
            map.put("username", PreferencesManager.getInstance().getUserName());
            map.put("nickname", PreferencesManager.getInstance().getNickName());
            map.put(NotificationCompat.CATEGORY_EMAIL, PreferencesManager.getInstance().getEmail());
            map.put("ssouid", PreferencesManager.getInstance().getSsouid());
            map.put("userlevel", Integer.valueOf(PreferencesManager.getInstance().getVipLevel()));
            if (1 == PreferencesManager.getInstance().getVipLevel()) {
                map.put("lasttime", Long.valueOf(PreferencesManager.getInstance().getVipCancelTime()));
            } else if (2 == PreferencesManager.getInstance().getVipLevel()) {
                map.put("lasttime", Long.valueOf(PreferencesManager.getInstance().getSeniorVipCancelTime()));
            }
            String callString = "javascript:LetvJSBridge.fireEvent('onlogin','" + new JSONObject(map).toString() + "')";
            LogInfo.log("lxx", "loginCallBack 第二次调用 callString: " + callString);
            new Handler(Looper.getMainLooper()).post(new 1(this, callString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSInBean parseJsonArray(String jsonString) {
        JSInBean jsInBean = new JSInBean();
        try {
            JSONObject jo = new JSONArray(jsonString).getJSONObject(0);
            jsInBean.setName(jo.getString("name").trim());
            jsInBean.setCallback_id(jo.getString("callback_id").trim());
            jsInBean.setCallback(jo.getString(a.c).trim());
            jsInBean.setFunc(jo.getString(a.g).trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogInfo.log("lxx", "parseJsonArray jsInBean: " + jsInBean);
        return jsInBean;
    }
}
