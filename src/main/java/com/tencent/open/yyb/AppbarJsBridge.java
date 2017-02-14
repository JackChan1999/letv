package com.tencent.open.yyb;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.alipay.sdk.authjs.a;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.tencent.open.a.f;
import com.tencent.open.utils.Util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AppbarJsBridge {
    public static final int AUTHORIZE_FAIL = -5;
    public static final String BUTTON_CLICK_CALLBACK_FUNCTION_NAME = "clickCallback";
    public static final String CALLBACK_LOGIN = "loginCallback";
    public static final String CALLBACK_SHARE = "shareCallback";
    public static final int Code_Java_Exception = -3;
    public static final int Code_None = -2;
    public static final int JSBRIDGE_VERSION = 1;
    public static final String JS_BRIDGE_SCHEME = "jsb://";
    public static final String READY_CALLBACK_FUNCTION_NAME = "readyCallback";
    public static final int Result_Fail = -1;
    public static final int Result_OK = 0;
    public static final int SHARE_QQ = 1;
    public static final int SHARE_QZ = 2;
    public static final int SHARE_TIMELINE = 4;
    public static final int SHARE_WX = 3;
    private WebView a;
    private Activity b;

    public AppbarJsBridge(Activity activity, WebView webView) {
        this.b = activity;
        this.a = webView;
    }

    public void closeWebView(Uri uri, int i, String str, String str2) {
        f.b(f.d, "-->closeWebView : url = " + uri);
        this.b.finish();
    }

    public void pageControl(Uri uri, int i, String str, String str2) {
        f.b(f.d, "-->pageControl : url = " + uri);
        int parseIntValue = Util.parseIntValue(uri.getQueryParameter("type"));
        if (this.a != null) {
            if (parseIntValue == 1) {
                this.a.goBack();
            } else if (parseIntValue == 2) {
                this.a.goForward();
            } else {
                this.a.reload();
            }
        }
        response(str2, i, str, "");
    }

    public void share(Uri uri, int i, String str, String str2) {
        f.b(f.d, "-->share : url = " + uri);
        String queryParameter = uri.getQueryParameter("title");
        String queryParameter2 = uri.getQueryParameter("summary");
        String queryParameter3 = uri.getQueryParameter("iconUrl");
        if (TextUtils.isEmpty(queryParameter3)) {
            queryParameter3 = "http://qzs.qq.com/open/mobile/jsbridge/demo.htm";
        }
        String queryParameter4 = uri.getQueryParameter("jumpUrl");
        f.b(f.d, "-->share : title = " + queryParameter);
        f.b(f.d, "-->share : summary = " + queryParameter2);
        f.b(f.d, "-->share : iconUrl = " + queryParameter3);
        f.b(f.d, "-->share : jumpUrl = " + queryParameter4);
        ShareModel shareModel = new ShareModel();
        shareModel.a = queryParameter;
        shareModel.b = queryParameter2;
        shareModel.c = queryParameter3;
        shareModel.d = queryParameter4;
        ((AppbarActivity) this.b).setShareModel(shareModel);
        switch (Util.parseIntValue(uri.getQueryParameter("type"), 0)) {
            case 1:
                ((AppbarActivity) this.b).shareToQQ();
                return;
            case 2:
                ((AppbarActivity) this.b).shareToQzone();
                return;
            case 3:
                ((AppbarActivity) this.b).shareToWX();
                return;
            case 4:
                ((AppbarActivity) this.b).shareToTimeline();
                return;
            default:
                ((AppbarActivity) this.b).showFloatingDialog();
                return;
        }
    }

    public void getAppInfo(Uri uri, int i, String str, String str2) {
        Object queryParameter = uri.getQueryParameter("packagenames");
        f.b(f.d, "-->getAppInfo : packageNames = " + queryParameter);
        if (!TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(str2)) {
            String[] split = queryParameter.split(",");
            if (split != null && split.length != 0) {
                JSONObject jSONObject = new JSONObject();
                for (String trim : split) {
                    String trim2 = trim.trim();
                    PackageInfo packageInfo = null;
                    try {
                        packageInfo = this.b.getPackageManager().getPackageInfo(trim2, 0);
                    } catch (NameNotFoundException e) {
                        f.b(f.d, "-->getAppInfo : NameNotFoundException e1");
                    }
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        if (packageInfo != null) {
                            jSONObject2.put("install", 1);
                            jSONObject2.put("appName", packageInfo.applicationInfo.name);
                            jSONObject2.put("verCode", packageInfo.versionCode);
                            jSONObject2.put("verName", packageInfo.versionName);
                        } else {
                            jSONObject2.put("install", 0);
                        }
                        jSONObject.put(trim2, jSONObject2);
                    } catch (Exception e2) {
                        responseFail(str2, i, str, -3);
                    }
                }
                f.b(f.d, "-->getAppInfo : result = " + jSONObject.toString());
                response(str2, i, str, jSONObject.toString());
            }
        }
    }

    public void clickCallback() {
        response(BUTTON_CLICK_CALLBACK_FUNCTION_NAME, 0, null, "");
    }

    public void openNewWindow(Uri uri, int i, String str, String str2) {
        String queryParameter = uri.getQueryParameter("url");
        try {
            Intent intent = new Intent(this.b, AppbarActivity.class);
            intent.putExtra("url", queryParameter);
            this.b.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWebView(Uri uri, int i, String str, String str2) {
        boolean z = true;
        try {
            Object queryParameter = uri.getQueryParameter("title");
            int parseIntValue = Util.parseIntValue(uri.getQueryParameter("buttonVisible"), 0);
            if (!TextUtils.isEmpty(queryParameter)) {
                ((AppbarActivity) this.b).setAppbarTitle(queryParameter);
            }
            AppbarActivity appbarActivity = (AppbarActivity) this.b;
            if (parseIntValue != 1) {
                z = false;
            }
            appbarActivity.setShareVisibility(z);
            f.b(f.d, "-->setWebView : url = " + uri + " -- buttonVisiable = " + parseIntValue);
            response(str2, i, str, "");
        } catch (Exception e) {
            responseFail(str2, i, str, -3);
        }
    }

    public void openLoginActivity(Uri uri, int i, String str, String str2) {
        f.b(f.d, "-->openLoginActivity : url = " + uri);
        ((AppbarActivity) this.b).login();
    }

    public int getVersion() {
        return 1;
    }

    public void invoke(String str) {
        f.b(f.d, "-->invoke : url = " + str);
        Uri parse = Uri.parse(str);
        String host = parse.getHost();
        f.b(f.d, "-->invoke : hostAsMethodName = " + host);
        if (!TextUtils.isEmpty(host)) {
            int i;
            List pathSegments = parse.getPathSegments();
            String str2 = null;
            if (pathSegments == null || pathSegments.size() <= 0) {
                i = 0;
            } else {
                i = Util.parseIntValue((String) pathSegments.get(0));
                if (pathSegments.size() > 1) {
                    str2 = (String) pathSegments.get(1);
                }
            }
            f.b(f.d, "-->invoke : seqid = " + i + " callbackName = " + str2);
            if (host.equals("callBatch")) {
                try {
                    JSONArray jSONArray = new JSONArray(parse.getQueryParameter(a.f));
                    int length = jSONArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i2);
                        String string = jSONObject.getString("method");
                        int i3 = jSONObject.getInt("seqid");
                        String optString = jSONObject.optString(a.c);
                        JSONObject jSONObject2 = jSONObject.getJSONObject("args");
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(JS_BRIDGE_SCHEME).append(string).append("/").append(i3).append("/").append(!TextUtils.isEmpty(optString) ? optString : "").append("?");
                        if (jSONObject2 != null) {
                            Iterator keys = jSONObject2.keys();
                            while (keys.hasNext()) {
                                String str3 = (String) keys.next();
                                stringBuilder.append(str3).append(SearchCriteria.EQ).append(Uri.encode(Uri.decode(jSONObject2.getString(str3)))).append("&");
                            }
                        }
                        a(Uri.parse(stringBuilder.toString()), string, i3, optString);
                    }
                    return;
                } catch (Exception e) {
                    if (!TextUtils.isEmpty(str2)) {
                        responseFail(str2, i, host, -5);
                        return;
                    }
                    return;
                }
            }
            a(parse, host, i, str2);
        }
    }

    private void a(Uri uri, String str, int i, String str2) {
        f.b(f.d, "-->callAMethod : uri = " + uri);
        if (a(str)) {
            try {
                AppbarJsBridge.class.getDeclaredMethod(str, new Class[]{Uri.class, Integer.TYPE, String.class, String.class}).invoke(this, new Object[]{uri, Integer.valueOf(i), str, str2});
            } catch (Exception e) {
                f.b(f.d, "-->callAMethod : Exception = " + e.getMessage());
                e.printStackTrace();
                if (!TextUtils.isEmpty(str2)) {
                    responseFail(str2, i, str, -3);
                }
            }
        } else if (!TextUtils.isEmpty(str2)) {
            responseFail(str2, i, str, -5);
        }
    }

    private boolean a(String str) {
        return true;
    }

    public void ready() {
        response(READY_CALLBACK_FUNCTION_NAME, 1, null, "true");
    }

    public void responseShare(int i) {
        Map hashMap = new HashMap();
        hashMap.put("type", i + "");
        response(CALLBACK_SHARE, 0, null, "0", hashMap);
    }

    public void responseShareFail(int i) {
        Map hashMap = new HashMap();
        hashMap.put("type", i + "");
        response(CALLBACK_SHARE, 0, null, "1", hashMap);
    }

    public void response(String str, int i, String str2, String str3) {
        response(str, i, str2, str3, null);
    }

    public void response(String str, int i, String str2, String str3, Map<String, String> map) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("result", 0);
                jSONObject.put(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, str3);
                if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put("method", str2);
                }
                jSONObject.put("seqid", i);
                if (map != null) {
                    for (String str4 : map.keySet()) {
                        jSONObject.put(str4, map.get(str4));
                    }
                }
                callback(str, jSONObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void responseFail(String str, int i, String str2, int i2) {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("result", -1);
                jSONObject.put("code", i2);
                jSONObject.put("method", str2);
                jSONObject.put("seqid", i);
                callback(str, jSONObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void callback(String str, String str2) {
        if (this.a != null) {
            StringBuffer stringBuffer = new StringBuffer("javascript:");
            stringBuffer.append("if(!!").append(str).append("){");
            stringBuffer.append(str);
            stringBuffer.append("(");
            stringBuffer.append(str2);
            stringBuffer.append(")}");
            this.a.loadUrl(stringBuffer.toString());
        }
    }
}
