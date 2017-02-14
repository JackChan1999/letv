package com.letv.lemallsdk.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.internal.NativeProtocol;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.utils.external.alipay.AlipayUtils.PayChannel;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.api.http.AsyncHttpClient;
import com.letv.lemallsdk.api.http.AsyncHttpResponseHandler;
import com.letv.lemallsdk.api.http.RequestParams;
import com.letv.lemallsdk.command.BaseParse;
import com.letv.lemallsdk.command.ParseCookie;
import com.letv.lemallsdk.command.ParsePageEntity;
import com.letv.lemallsdk.command.ParseTitleStatus;
import com.letv.lemallsdk.command.ParserCashier;
import com.letv.lemallsdk.command.ParserOrderDetail;
import com.letv.lemallsdk.model.ILetvBridge;
import com.letv.lemallsdk.util.Constants;
import com.letv.lemallsdk.util.EALogger;
import com.letv.lemallsdk.util.PhoneInfoTools;
import com.letv.lemallsdk.view.IWebviewListener;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpTask {
    private static final int CASHIERURL = 4;
    private static final int GETURL = 2;
    private static final int ORDERDETAILURL = 3;
    private static final int TITLEBAR = 1;
    private static Map<String, String> body4Post;
    private static BaseParse parse;
    private static PhoneInfoTools tools;

    class AnonymousClass1 extends AsyncHttpResponseHandler {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ IWebviewListener val$iLetvBrideg;

        AnonymousClass1(Context context, IWebviewListener iWebviewListener) {
            this.val$context = context;
            this.val$iLetvBrideg = iWebviewListener;
        }

        protected void sendResponseMessage(HttpResponse response) {
            super.sendResponseMessage(response);
            Header[] headers = response.getHeaders("Set-Cookie");
            if (headers != null && headers.length > 0) {
                CookieSyncManager.createInstance(this.val$context).sync();
                CookieManager instance = CookieManager.getInstance();
                instance.setAcceptCookie(true);
                instance.removeSessionCookie();
                String mm = "";
                for (Header header : headers) {
                    String[] split = header.toString().split("Set-Cookie:");
                    EALogger.i("正式登录", "split[1]===>" + split[1]);
                    instance.setCookie(Constants.THIRDLOGIN, split[1]);
                    int index = split[1].indexOf(";");
                    if (TextUtils.isEmpty(mm)) {
                        mm = split[1].substring(index + 1);
                        EALogger.i("正式登录", "mm===>" + mm);
                    }
                }
                EALogger.i("正式登录", "split[1222]===>COOKIE_DEVICE_ID=" + LemallPlatform.getInstance().uuid + ";" + mm);
                instance.setCookie(Constants.THIRDLOGIN, "COOKIE_DEVICE_ID=" + LemallPlatform.getInstance().uuid + ";" + mm);
                instance.setCookie(Constants.THIRDLOGIN, "COOKIE_APP_ID=" + LemallPlatform.getInstance().getmAppInfo().getId() + ";" + mm);
                CookieSyncManager.getInstance().sync();
                this.val$iLetvBrideg.reLoadWebUrl();
            }
        }

        public void onSuccess(int statusCode, Header[] headers, String content) {
            super.onSuccess(statusCode, headers, content);
            if (statusCode == 200) {
                EALogger.i("正式登录", "正式登录===>" + content);
                new ParseCookie().Json2Entity(content);
            }
        }

        public void onFailure(Throwable error) {
            super.onFailure(error);
        }
    }

    class AnonymousClass2 extends AsyncHttpResponseHandler {
        private final /* synthetic */ IWebviewListener val$iLetvBrideg;

        AnonymousClass2(IWebviewListener iWebviewListener) {
            this.val$iLetvBrideg = iWebviewListener;
        }

        protected void sendResponseMessage(HttpResponse response) {
            super.sendResponseMessage(response);
            Header[] headers = response.getHeaders("Set-Cookie");
            if (headers != null && headers.length > 0) {
                CookieSyncManager.createInstance(LemallPlatform.getInstance().getContext()).sync();
                CookieManager instance = CookieManager.getInstance();
                instance.setAcceptCookie(true);
                instance.removeSessionCookie();
                String mm = "";
                for (Header header : headers) {
                    String[] split = header.toString().split("Set-Cookie:");
                    EALogger.i("游客登录", "split[1]===>" + split[1]);
                    instance.setCookie(Constants.GUESTLOGIN, split[1]);
                    int index = split[1].indexOf(";");
                    if (TextUtils.isEmpty(mm)) {
                        mm = split[1].substring(index + 1);
                        EALogger.i("正式登录", "mm===>" + mm);
                    }
                }
                EALogger.i("游客登录", "split[11]===>COOKIE_DEVICE_ID=" + LemallPlatform.getInstance().uuid + ";" + mm);
                instance.setCookie(Constants.GUESTLOGIN, "COOKIE_DEVICE_ID=" + LemallPlatform.getInstance().uuid + ";" + mm);
                instance.setCookie(Constants.THIRDLOGIN, "COOKIE_APP_ID=" + LemallPlatform.getInstance().getmAppInfo().getId() + ";" + mm);
                CookieSyncManager.getInstance().sync();
                this.val$iLetvBrideg.reLoadWebUrl();
            }
        }

        public void onSuccess(int statusCode, Header[] headers, String content) {
            super.onSuccess(statusCode, headers, content);
            if (statusCode == 200) {
                EALogger.i("游客登录", "游客登录===>" + content);
                new ParseCookie().Json2Entity(content);
            }
        }

        public void onFailure(Throwable error) {
            super.onFailure(error);
        }
    }

    class AnonymousClass3 extends AsyncHttpResponseHandler {
        private final /* synthetic */ ILetvBridge val$iBribery;
        private final /* synthetic */ int val$whichWant;

        AnonymousClass3(int i, ILetvBridge iLetvBridge) {
            this.val$whichWant = i;
            this.val$iBribery = iLetvBridge;
        }

        public void onSuccess(String content) {
            super.onSuccess(content);
            HttpTask.parseData(this.val$whichWant, content, this.val$iBribery);
        }

        public void onFailure(Throwable error) {
            super.onFailure(error);
            this.val$iBribery.shitData(error);
        }
    }

    class AnonymousClass4 extends AsyncHttpResponseHandler {
        private final /* synthetic */ ILetvBridge val$iBribery;
        private final /* synthetic */ int val$whichWant;

        AnonymousClass4(int i, ILetvBridge iLetvBridge) {
            this.val$whichWant = i;
            this.val$iBribery = iLetvBridge;
        }

        public void onSuccess(String content) {
            super.onSuccess(content);
            EALogger.i(CommonUtils.SDK, "初始化数据或者获取URL数据：" + content);
            HttpTask.parseData(this.val$whichWant, content, this.val$iBribery);
        }

        public void onFailure(Throwable error) {
            super.onFailure(error);
            this.val$iBribery.shitData(error);
        }
    }

    class AnonymousClass5 extends AsyncTask<Void, Void, Object> {
        private final /* synthetic */ String val$content;
        private final /* synthetic */ ILetvBridge val$iBribery;

        AnonymousClass5(String str, ILetvBridge iLetvBridge) {
            this.val$content = str;
            this.val$iBribery = iLetvBridge;
        }

        protected Object doInBackground(Void... params) {
            return HttpTask.parse.Json2Entity(this.val$content);
        }

        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            this.val$iBribery.goldData(result);
        }
    }

    public static void getTitleStatus(ILetvBridge iBribery) {
        getPageUrl(1, Constants.HTTP_GET_TITLEBAR, iBribery);
    }

    public static void getPage(ILetvBridge iBribery) {
        getPageUrl(2, Constants.HTTP_GET_URL, iBribery);
    }

    public static void getLogon(IWebviewListener iLetvBrideg) {
        String ssoToken = LemallPlatform.getInstance().getSsoToken();
        Context context = LemallPlatform.getInstance().getContext();
        if (TextUtils.isEmpty(ssoToken)) {
            EALogger.i(CommonUtils.SDK, "无token");
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("SSO_TK", ssoToken);
        params.put("EXPIRE_TYPE", "3");
        EALogger.i("正式登录", "正式登录==SSO_TK=>" + ssoToken);
        client.get(true, Constants.THIRDLOGIN, params, new AnonymousClass1(context, iLetvBrideg));
    }

    public static void getGuestLogon(IWebviewListener iLetvBrideg) {
        new AsyncHttpClient().get(false, Constants.GUESTLOGIN, null, new AnonymousClass2(iLetvBrideg));
    }

    public static void getOrderDetail(String OrderID, ILetvBridge iBribery) {
        RequestParams params = new RequestParams();
        if (LemallPlatform.getInstance().getCookieLinkdata() != null) {
            for (String sp : LemallPlatform.getInstance().getCookieLinkdata().split("&")) {
                String[] dog = sp.split(SearchCriteria.EQ);
                if (dog.length > 1) {
                    params.put(dog[0], dog[1]);
                }
            }
        }
        params.put("order_id", OrderID);
        get(false, 3, params, Constants.HTTP_GET_ORDERDETAIL, iBribery);
    }

    public static void getCashier(String OrderID, ILetvBridge iBribery) {
        RequestParams params = new RequestParams();
        params.put("orderId", OrderID);
        params.put("appVersion", "ANDROID_04");
        get(true, 4, params, Constants.HTTP_GET_CASHIER, iBribery);
    }

    private static void get(boolean isCookieStore, int whichWant, RequestParams params, String url, ILetvBridge iBribery) {
        new AsyncHttpClient().get(isCookieStore, url, params, new AnonymousClass3(whichWant, iBribery));
    }

    private static void getPageUrl(int whichWant, String url, ILetvBridge iBribery) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(NativeProtocol.WEB_DIALOG_PARAMS, getJsonEncry());
        client.post(url, params, new AnonymousClass4(whichWant, iBribery));
    }

    private static void parseData(int whichWant, String content, ILetvBridge iBribery) {
        switch (whichWant) {
            case 1:
                parse = new ParseTitleStatus();
                break;
            case 2:
                parse = new ParsePageEntity();
                break;
            case 3:
                parse = new ParserOrderDetail();
                break;
            case 4:
                parse = new ParserCashier();
                break;
        }
        new AnonymousClass5(content, iBribery).execute(new Void[0]);
    }

    public static Map<String, String> getEncryBodyMap() {
        if (body4Post == null) {
            body4Post = new HashMap();
        }
        body4Post.clear();
        return body4Post;
    }

    private static PhoneInfoTools getPhoneTools() {
        if (tools == null) {
            tools = new PhoneInfoTools();
        }
        return tools;
    }

    private static String getJsonEncry() {
        tools = getPhoneTools();
        JSONObject jsonHead = new JSONObject();
        JSONObject system = new JSONObject();
        JSONObject aboutApp = new JSONObject();
        JSONObject other = new JSONObject();
        String encrypt = "";
        try {
            system.put("devHwVersion", tools.getDeviceModel());
            system.put("devOsName", tools.getOSVersionName(VERSION.SDK_INT));
            system.put("devOsType", "Android");
            system.put("devOsVersion", VERSION.RELEASE);
            system.put("deviceName", Build.BRAND + tools.getDeviceModel());
            system.put("uniqueId", tools.getDeviceCode());
            aboutApp.put("appVersion", "V" + tools.getAppVersionName());
            String netType = tools.getNetType();
            String str = "curNetType";
            if (!"WIFI".equals(netType)) {
                netType = "NON_WIFI";
            }
            aboutApp.put(str, netType);
            aboutApp.put("channelId", tools.getChannel());
            other.put("timeZone", tools.getTimezone());
            other.put(HOME_RECOMMEND_PARAMETERS.COUNTRY, tools.getCountry());
            other.put("language", tools.getLanguage());
            jsonHead.put("equipment", system);
            jsonHead.put(PayChannel.CLIENT, aboutApp);
            jsonHead.put("other", other);
            JSONObject jsonBody = new JSONObject();
            if (body4Post.size() > 0) {
                for (Entry<String, String> entry : body4Post.entrySet()) {
                    jsonBody.put((String) entry.getKey(), entry.getValue());
                }
            }
            JSONObject postAll = new JSONObject();
            postAll.put("mobileHead", jsonHead);
            postAll.put("mobileBody", jsonBody);
            return postAll.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return encrypt;
        }
    }
}
