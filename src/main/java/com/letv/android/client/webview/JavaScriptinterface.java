package com.letv.android.client.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.alipay.sdk.authjs.a;
import com.amap.api.location.AMapLocation;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.StarActivityConfig;
import com.letv.android.client.commonlib.config.StarRankActivityConfig;
import com.letv.android.client.commonlib.share.ShareResultObserver;
import com.letv.android.client.commonlib.share.WXShareResultObserver;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.api.PlayRecordApi.MODIFYPWD_PARAMETERS;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlertArgument;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.LetvConstant.Intent;
import com.letv.core.constant.PlayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.listener.OnRelevantStateChangeListener;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AlbumInfoParser;
import com.letv.core.parser.VideoPlayerParser;
import com.letv.core.utils.EncryptUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.core.utils.VideoStreamHandler;
import com.letv.core.utils.external.gaode.AMapLocationTool;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.StoreManager;
import com.letv.download.manager.StoreManager.StoreDeviceInfo;
import com.letv.lemallsdk.util.Constants;
import com.letv.zxing.ex.ParseResultEntity;
import com.letv.zxing.ex.URIResultListener;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class JavaScriptinterface implements ShareResultObserver, WXShareResultObserver, OnRelevantStateChangeListener, NetWorkChangeObserver, URIResultListener {
    private static final String RESULT_CANNELED = "{\"result\": 201}";
    private static final String RESULT_FAIL = "{\"result\": 400}";
    private static final String RESULT_SUCCEED = "{\"result\": 200}";
    private static final String TAG = "JavaScriptinterface";
    private static JavaScriptinterface javaScriptinterface;
    private static JSInBean jsInBean;
    private static WebView mWebView;
    private static List<String> methods = new ArrayList();
    private Handler activityHandler;
    private Handler handler;
    private Activity mActivity;
    private PayCallBack payCallBack;
    private View root;
    private WebViewSensorEventListener webSensorEventListener;

    public interface PayCallBack {
        void onPayFailed();

        void onPaySuccessed();
    }

    public PayCallBack getPayCallBack() {
        return this.payCallBack;
    }

    public void setPayCallBack(PayCallBack payCallBack) {
        this.payCallBack = payCallBack;
    }

    public JavaScriptinterface(Activity activity, WebView webView, View root) {
        this.mActivity = activity;
        mWebView = webView;
        addAllMethods();
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1100, this));
        NetWorkBroadcastReceiver.registerObserver(this);
        this.handler = new Handler(this.mActivity.getMainLooper());
        this.root = root;
    }

    public void removeAllObservers() {
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_JS_INTERFACE_OBSERVER_REMOVE, this));
        NetWorkBroadcastReceiver.unRegisterObserver(this);
        if (this.webSensorEventListener != null) {
            this.webSensorEventListener.stop();
        }
    }

    public void setHandler(Handler handler) {
        this.activityHandler = handler;
    }

    private static void addAllMethods() {
        methods.add("core.hasFeature");
        methods.add("fun.callShare");
        methods.add("fun.setShare");
        methods.add("fun.callLogin");
        methods.add("fun.callLogout");
        methods.add("fun.playVideo");
        methods.add("fun.callWebview");
        methods.add("core.getPcode");
        methods.add("core.getVersion");
        methods.add("fun.setStatus");
        methods.add("fun.callAlert");
        methods.add("core.getUserInfo");
        methods.add("core.getDeviceInfo");
        methods.add("core.getNetworkState");
        methods.add("core.getPowerLevel");
        methods.add("fun.callBrowser");
        methods.add("core.getOnlineDevice");
        methods.add("fun.callScanCode");
        methods.add("fun.saveVideo");
        methods.add("fun.openStartWindow");
        methods.add("fun.openStartRankWindow");
        methods.add("fun.getParams");
        methods.add("fun.isTestApi");
        methods.add("fun.setStorage");
        methods.add("fun.getStorage");
        methods.add("fun.getTK");
        methods.add("fun.autoPlay");
        methods.add("fun.report");
        methods.add("fun.openImage");
        methods.add("fun.openCamera");
        methods.add("core.getSpaceSize");
        methods.add("fun.getGeolocation");
        methods.add("fun.enableShake");
    }

    public void getContentOfHtml(String htmlContent) {
        LogInfo.log("lxx", "htmlContent: " + htmlContent);
    }

    @JavascriptInterface
    public void core_hasFeature(String jsonString) {
        LogInfo.log("lxx", "core_hasFeature jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            String featureName = jsInBean.getName();
            boolean hasFeature = false;
            int i = 0;
            while (i < methods.size()) {
                if (featureName != null && featureName.length() > 0 && featureName.equals(methods.get(i))) {
                    hasFeature = true;
                    break;
                }
                i++;
            }
            Map<String, Object> result = new HashMap();
            result.put("featureName", featureName);
            result.put("result", Boolean.valueOf(hasFeature));
            jsCallBack(jsInBean, new JSONObject(result).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void fun_playVideo(String jsonString) {
        LogInfo.log("lxx", "fun_playVideo jsonString: " + jsonString);
        try {
            jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
            JSONObject jo = new JSONObject(jsInBean.getName());
            String type = jo.getString("type");
            String screen = jo.getString("screen");
            this.handler.post(new 1(this, type, jo));
        } catch (JSONException e) {
            e.printStackTrace();
            LogInfo.log("lxx", "playVideo的name参数异常");
        }
    }

    @JavascriptInterface
    public void fun_callShare(String jsonString) {
        LogInfo.log("lxx", "fun_callShare jsonString: " + jsonString);
        jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        if (this.mActivity instanceof LetvBaseWebViewActivity) {
            ((LetvBaseWebViewActivity) this.mActivity).onJsShareDialogShow(jsonString);
        }
    }

    @JavascriptInterface
    public void fun_setShare(String jsonString) {
        LogInfo.log("lxx", "fun_setShare jsonString: " + jsonString);
        jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        if (this.mActivity instanceof LetvBaseWebViewActivity) {
            ((LetvBaseWebViewActivity) this.mActivity).parseShareJson(jsonString);
        }
        jsCallBack(jsInBean, RESULT_SUCCEED);
    }

    @JavascriptInterface
    public void fun_callLogin(String jsonString) {
        LogInfo.log("lxx", "fun_callLogin jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        Map<String, Integer> map = new HashMap();
        map.put("result", Integer.valueOf(200));
        try {
            String callString = buildupJSCallString(jsInBean.getCallback(), jsInBean.getCallback_id(), new JSONObject(map).toString());
            LogInfo.log("lxx", "fun_callLogin 第一次callback callString: " + callString);
            new Handler(Looper.getMainLooper()).post(new 2(this, callString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (LetvUtils.checkClickEvent(SPConstant.DELAY_BUFFER_DURATION)) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this.mActivity).create(1000)));
        } else {
            LogInfo.log("pay_zlb", "login return.");
        }
    }

    @JavascriptInterface
    public void fun_callLogout(String jsonString) {
        LogInfo.log("lxx", "jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        Map<String, Integer> map = new HashMap();
        map.put("result", Integer.valueOf(200));
        JSONObject logoutResultJson = new JSONObject(map);
        try {
            String callString = buildupJSCallString(jsInBean.getCallback(), jsInBean.getCallback_id(), logoutResultJson.toString());
            LogInfo.log("lxx", "fun_callLogout callString: " + callString);
            new Handler(Looper.getMainLooper()).post(new 3(this, callString));
            PreferencesManager.getInstance().logoutUser();
            String s = "javascript:LetvJSBridge.fireEvent('onlogout','" + logoutResultJson.toString() + "')";
            LogInfo.log("lxx", "fun_callLogout callString: " + s);
            new Handler(Looper.getMainLooper()).post(new 4(this, s));
            CookieManager.getInstance().removeAllCookie();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void fun_callWebview(String jsonString) {
        LogInfo.log("lxx", "fun_callWebview jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            Map<String, Integer> map = new HashMap();
            map.put("result", Integer.valueOf(200));
            String succeedcallback = buildupJSCallString(jsInBean.getCallback(), jsInBean.getCallback_id(), new JSONObject(map).toString());
            map.put("result", Integer.valueOf(400));
            String failCallback = buildupJSCallString(jsInBean.getCallback(), jsInBean.getCallback_id(), new JSONObject(map).toString());
            LogInfo.log("lxx", "fun_callWebview succeedcallback: " + succeedcallback + ", failCallback: " + failCallback);
            String url = new JSONObject(jsInBean.getName()).getString("url");
            if (!TextUtils.isEmpty(url)) {
                UIsUtils.hideSoftkeyboard(this.mActivity);
                if (url.startsWith("http:\\/\\/") || url.startsWith("http:\\/\\/") || url.startsWith("http://") || url.startsWith("http://")) {
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    message.setData(bundle);
                    message.what = 0;
                    this.activityHandler.sendMessage(message);
                    return;
                }
                jumpToClientPage(url, succeedcallback, failCallback);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jumpToClientPage(String url, String succeedCallback, String failCallback) {
        if (TextUtils.isEmpty(url)) {
            LogInfo.log("JavaScriptinterface||wlx", "url 是空的");
            return;
        }
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(LeMessageIds.MSG_JS_INTERFACE_TRANFER_PAGE, url));
        if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
            this.mActivity.finish();
            mWebView.loadUrl(succeedCallback);
            return;
        }
        mWebView.loadUrl(failCallback);
    }

    @JavascriptInterface
    public void core_getPcode(String jsonString) {
        LogInfo.log("lxx", "fun_getPcode jsonString: " + jsonString);
        String pcode = LetvConfig.getPcode();
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        Map<String, Object> result = new HashMap();
        result.put("pcode", pcode);
        jsCallBack(jsInBean, new JSONObject(result).toString());
    }

    @JavascriptInterface
    public void core_getVersion(String jsonString) {
        LogInfo.log("lxx", "core_getVersion jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        Map<String, Object> result = new HashMap();
        result.put("version", LetvUtils.getClientVersionName());
        result.put("name", this.mActivity.getResources().getString(R.string.app_name));
        result.put("pcode", LetvConfig.getPcode());
        result.put("osversionname", LetvUtils.getOSVersionName());
        jsCallBack(jsInBean, new JSONObject(result).toString());
    }

    @JavascriptInterface
    public void fun_setStatus(String jsonString) {
        LogInfo.log("lxx", "fun_setStatus jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            String payResultCode = new JSONObject(jsInBean.getName()).getString("code");
            if (!TextUtils.isEmpty(payResultCode)) {
                if (DataConstant.P3.equals(payResultCode)) {
                    callBackForJS(jsInBean, true);
                    if (this.payCallBack != null) {
                        this.payCallBack.onPaySuccessed();
                    }
                } else if ("002".equals(payResultCode)) {
                    callBackForJS(jsInBean, false);
                    if (this.payCallBack != null) {
                        this.payCallBack.onPayFailed();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callBackForJS(jsInBean, false);
            LogInfo.log("lxx", "name参数有误");
        }
    }

    @JavascriptInterface
    public void core_getUserInfo(String jsonString) {
        LogInfo.log("lxx", "fun_setStatus jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
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
            jsCallBack(jsInBean, new JSONObject(map).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void fun_callAlert(String jsonString) {
        LogInfo.log("lxx", "fun_callAlert jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        AlertArgument alertArgument = parseAlertArguments(jsInBean.getName());
        if (alertArgument == null) {
            callBackForJS(jsInBean, false);
        } else if ("alert".equals(alertArgument.getType())) {
            if (TextUtils.isEmpty(alertArgument.getText())) {
                callBackForJS(jsInBean, false);
                return;
            }
            LogInfo.log("lxx", "弹taost");
            ToastUtils.showToast(this.root.getContext(), alertArgument.getText());
            callBackForJS(jsInBean, true);
        } else if (!"remind".equals(alertArgument.getType())) {
        } else {
            if (TextUtils.isEmpty(alertArgument.getText()) || TextUtils.isEmpty(alertArgument.getTarget())) {
                callBackForJS(jsInBean, false);
                return;
            }
            LogInfo.log("lxx", "推送");
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(LeMessageIds.MSG_JS_INTERFACE_ADD_QD_REMIND, alertArgument));
            if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class)) {
                callBackForJS(jsInBean, ((Boolean) response.getData()).booleanValue());
            }
        }
    }

    private void callBackForJS(JSInBean jsInBean, boolean isSuccess) {
        if (isSuccess) {
            jsCallBack(jsInBean, RESULT_SUCCEED);
        } else {
            jsCallBack(jsInBean, RESULT_FAIL);
        }
    }

    private AlertArgument parseAlertArguments(String name) {
        try {
            JSONObject jo = new JSONObject(name);
            AlertArgument alertArgument = new AlertArgument();
            if ("alert".equalsIgnoreCase(jo.getString("type").trim())) {
                alertArgument.setType(jo.getString("type").trim());
                alertArgument.setText(jo.getString("text").trim());
            } else if ("remind".equalsIgnoreCase(jo.getString("type").trim())) {
                alertArgument.setType(jo.getString("type").trim());
                alertArgument.setText(jo.getString("text").trim());
                alertArgument.setImage(jo.getString("image").trim());
                alertArgument.setStartTime(Long.valueOf(jo.getLong("startTime")));
                alertArgument.setTarget(jo.getString("target").trim());
            }
            LogInfo.log("lxx", "alertArgument：" + alertArgument);
            return alertArgument;
        } catch (JSONException e) {
            e.printStackTrace();
            LogInfo.log("lxx", "解析AlertArguments参数出错！");
            return null;
        }
    }

    public static String buildupJSCallString(String funcName, String callbackId, String result) {
        StringBuilder builder = new StringBuilder();
        builder.append("javascript:");
        builder.append(funcName);
        builder.append("(");
        builder.append("'" + callbackId + "'");
        builder.append(",");
        builder.append("'" + result + "'");
        builder.append(");");
        return builder.toString();
    }

    public void onShareSucceed() {
        jsCallBack(jsInBean, RESULT_SUCCEED);
    }

    public void onShareFail() {
        jsCallBack(jsInBean, RESULT_FAIL);
    }

    public void onCanneled() {
        jsCallBack(jsInBean, RESULT_CANNELED);
    }

    public void onWXShareSucceed() {
        LogInfo.log("lxx", "onWXShareSucceed jsInBean: " + jsInBean);
        jsCallBack(jsInBean, RESULT_SUCCEED);
    }

    public void onWXShareFail() {
        jsCallBack(jsInBean, RESULT_FAIL);
    }

    public void onWXCanneled() {
        jsCallBack(jsInBean, RESULT_CANNELED);
    }

    @JavascriptInterface
    public void sendPlayVedioResultToJS(int playState) {
        Map<String, Object> map = new HashMap();
        map.put("result", Integer.valueOf(playState));
        map.put("log", "");
        jsCallBack(jsInBean, new JSONObject(map).toString());
    }

    @JavascriptInterface
    public void core_getDeviceInfo(String jsonString) {
        LogInfo.log("lxx", "core_getDeviceInfo jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        HashMap<String, Object> deviceInfo = new HashMap();
        deviceInfo.put(Intent.Bundle.DEVICEID, Global.DEVICEID);
        deviceInfo.put("name", LetvUtils.getDeviceName());
        deviceInfo.put("type", "Phone");
        deviceInfo.put("version", Global.VERSION);
        HashMap<String, Integer> screen = new HashMap();
        screen.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.valueOf(Global.displayMetrics.widthPixels));
        screen.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.valueOf(Global.displayMetrics.heightPixels));
        deviceInfo.put("screen", new JSONObject(screen));
        jsCallBack(jsInBean, new JSONObject(deviceInfo).toString());
    }

    @JavascriptInterface
    public void core_getNetworkState(String jsonString) {
        LogInfo.log("lxx", "core_getDeviceInfo jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        HashMap<String, Object> wifiInfo = new HashMap();
        wifiInfo.put("type", NetworkUtils.type2String(NetworkUtils.getNetworkType()));
        wifiInfo.put("operator", Integer.valueOf(NetworkUtils.getOperator()));
        jsCallBack(jsInBean, new JSONObject(wifiInfo).toString());
    }

    public void onTimeChange() {
    }

    public void onNetChange() {
        LogInfo.log("lxx", "onNetChange");
    }

    public void onBatteryChange(int curStatus, int curPower) {
        LogInfo.log("lxx", "onBatteryChange, curStatus: " + curStatus + ", curPower: " + curPower);
    }

    public void onDownloadStateChange() {
    }

    public void onHeadsetPlug() {
    }

    @JavascriptInterface
    public void core_getPowerLevel(String jsonString) {
        LogInfo.log("lxx", "core_getDeviceInfo jsonString: " + jsonString);
        this.mActivity.registerReceiver(new BatteryBroadcastReceiver(JSBridgeUtil.getInstance().parseJsonArray(jsonString), mWebView, this.mActivity), new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    @JavascriptInterface
    public void fun_callBrowser(String jsonString) {
        LogInfo.log("lxx", "fun_callBrowser jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            String url = new JSONObject(jsInBean.getName()).getString("url");
            android.content.Intent intent = new android.content.Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            new Handler(Looper.getMainLooper()).post(new 5(this, intent));
            jsCallBack(jsInBean, RESULT_SUCCEED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsCallBack(JSInBean jsInBean, String result) {
        if (jsInBean == null) {
            LogInfo.log("wlx", "jsInBean is null");
            return;
        }
        String callString = buildupJSCallString(jsInBean.getCallback(), jsInBean.getCallback_id(), result);
        LogInfo.log("wlx", jsInBean.getFunc() + com.letv.pp.utils.NetworkUtils.DELIMITER_COLON + callString);
        mWebView.post(new 6(this, callString));
    }

    @JavascriptInterface
    public void core_getOnlineDevice(String jsonString) {
        LogInfo.log("lxx", "core_getOnlineDevice jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        List<HashMap<String, Object>> deviceInfoList = new ArrayList();
        HashMap<String, Object> deviceInfo = new HashMap();
        deviceInfo.put(Intent.Bundle.DEVICEID, Global.DEVICEID);
        deviceInfo.put("name", LetvUtils.getDeviceName());
        deviceInfo.put("type", "Phone");
        deviceInfo.put("version", Global.VERSION);
        HashMap<String, Integer> screen = new HashMap();
        screen.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.valueOf(Global.displayMetrics.widthPixels));
        screen.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.valueOf(Global.displayMetrics.heightPixels));
        deviceInfo.put("screen", new JSONObject(screen));
        HashMap<String, Object> deviceInfo2 = new HashMap();
        deviceInfo2.put(Intent.Bundle.DEVICEID, Global.DEVICEID);
        deviceInfo2.put("name", LetvUtils.getDeviceName());
        deviceInfo2.put("type", "Phone");
        deviceInfo2.put("version", Global.VERSION);
        HashMap<String, Integer> screen2 = new HashMap();
        screen2.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.valueOf(Global.displayMetrics.widthPixels));
        screen2.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.valueOf(Global.displayMetrics.heightPixels));
        deviceInfo2.put("screen", new JSONObject(screen2));
        deviceInfoList.add(deviceInfo);
        deviceInfoList.add(deviceInfo2);
        jsCallBack(jsInBean, new JSONArray(deviceInfoList).toString());
    }

    @JavascriptInterface
    public void fun_callScanCode(String jsonString) {
        LogInfo.log("lxx", "fun_callScanCode jsonString: " + jsonString);
        jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        LeMessageManager.getInstance().dispatchMessage(this.mActivity, new LeMessage(LeMessageIds.MSG_JS_INTERFACE_START_CAPTURE));
    }

    public void handleResult(Activity activity, ParseResultEntity resultEntity) {
        LogInfo.log("lxx", "text: " + resultEntity.getText());
        HashMap<String, String> qrCodeResult = new HashMap();
        qrCodeResult.put("string", resultEntity.getText());
        try {
            jsCallBack(jsInBean, new JSONObject(qrCodeResult).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            activity.finish();
        }
    }

    @JavascriptInterface
    public void fun_saveVideo(String jsonString) {
        LogInfo.log("lxx", "fun_callScanCode jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            JSONObject name = new JSONObject(jsInBean.getName());
            String vid = name.getString("vid");
            String definition = name.getString("definition");
            String pid = name.getString("pid");
            if (DownloadManager.isHasDownloadInDB(vid)) {
                ToastUtils.showToast(this.root.getContext(), this.mActivity.getString(R.string.this_video_already_exist));
                jsCallBack(jsInBean, RESULT_FAIL);
                return;
            }
            requestVideo(this.mActivity, pid, Long.valueOf(Long.parseLong(vid)), VideoStreamHandler.getCurrentStream(definition));
            jsCallBack(jsInBean, RESULT_SUCCEED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestVideo(Context context, String pid, Long vid, int stream) {
        new LetvRequest().setUrl(MediaAssetApi.getInstance().getVideoPlayUrl("", pid + "", null, vid + "", PreferencesManager.getInstance().getUserId(), BaseApplication.getInstance().getVideoFormat(), "0", String.valueOf(TimestampBean.getTm().getCurServerTime()), "", null)).setCache(new VolleyNoCache()).setParser(new VideoPlayerParser()).setCallback(new 7(this, pid, stream)).add();
    }

    private void requestAlbum(String aid, VideoBean videoBean, int stream) {
        new LetvRequest(AlbumInfo.class).setUrl(LetvUrlMaker.getAlbumVideoInfoUrl(aid)).setParser(new AlbumInfoParser()).setCache(new VolleyNoCache()).setCallback(new 8(this, videoBean, stream)).add();
    }

    private void onError(NetworkResponseState state) {
        switch (9.$SwitchMap$com$letv$core$network$volley$VolleyResponse$NetworkResponseState[state.ordinal()]) {
            case 1:
            case 2:
                ToastUtils.showToast(this.root.getContext(), R.string.net_no);
                return;
            case 3:
                ToastUtils.showToast(this.root.getContext(), R.string.get_data_error);
                return;
            default:
                return;
        }
    }

    @JavascriptInterface
    public static void setCookieByJS(WebView webView) {
        HashMap<String, String> cookieMap = new HashMap();
        cookieMap.put(MODIFYPWD_PARAMETERS.TK_KEY, PreferencesManager.getInstance().getSso_tk());
        cookieMap.put("from", "mobile_tv");
        String callString = "javascript:LetvJSBridge.fireEvent('onsetcookie','" + new JSONObject(cookieMap).toString() + "')";
        LogInfo.log("lxx", "setCookieByJS callString: " + callString);
        webView.loadUrl(callString);
    }

    public void onNetTypeChange(HashMap<String, Object> netMap) {
        String callString = "javascript:LetvJSBridge.fireEvent('onNetworkChange','" + new JSONObject(netMap).toString() + "')";
        LogInfo.log("lxx", "onNetTypeChange callString: " + callString);
        mWebView.loadUrl(callString);
    }

    @JavascriptInterface
    public void fun_getParams(String jsonString) {
        LogInfo.log("ZSM webview js fun_getParms jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            JSONArray name = new JSONArray(jsInBean.getName());
            Map<String, Object> map = new HashMap();
            for (int i = 0; i < name.length(); i++) {
                String object = name.getString(i);
                if (TextUtils.equals(object, "pcode")) {
                    map.put("pcode", LetvConfig.getPcode());
                }
                if (TextUtils.equals(object, "version")) {
                    map.put("version", LetvUtils.getClientVersionName());
                }
                if (TextUtils.equals(object, HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY)) {
                    map.put(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID);
                }
                if (TextUtils.equals(object, "ssotk")) {
                    map.put("ssotk", PreferencesManager.getInstance().getSso_tk());
                }
            }
            jsCallBack(jsInBean, new JSONObject(map).toString());
        } catch (JSONException e) {
            e.printStackTrace();
            jsCallBack(jsInBean, RESULT_FAIL);
        }
    }

    @JavascriptInterface
    public void fun_openStartWindow(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            JSONObject name = new JSONObject(jsInBean.getName());
            String follow_id = name.getString("follow_id");
            String nickname = name.getString("nickname");
            LogInfo.log("ZSM webview js fun_openStartWindow follow_id: " + follow_id + "  nickname == " + nickname);
            if (TextUtils.isEmpty(follow_id) || TextUtils.isEmpty(nickname)) {
                jsCallBack(jsInBean, RESULT_FAIL);
            } else {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new StarActivityConfig(this.mActivity).create(follow_id, nickname, PageIdConstant.myAttention)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            jsCallBack(jsInBean, RESULT_FAIL);
            LogInfo.log("lxx", "name参数有误");
        }
    }

    @JavascriptInterface
    public void fun_openStartRankWindow(String jsonString) {
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new StarRankActivityConfig(this.mActivity)));
    }

    @JavascriptInterface
    public void fun_setStorage(String jsonString) {
        LogInfo.log("ZSM webview js fun_setStorage jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            JSONArray name = new JSONArray(jsInBean.getName());
            for (int i = 0; i < name.length(); i++) {
                JSONObject object = name.getJSONObject(i);
                String key = object.getString("key");
                String value = object.getString(Constants.VALUE_ID);
                LogInfo.log("ZSM webview js fun_setStorage key: " + key + "  value == " + value);
                FileUtils.saveApiFileCache(BaseApplication.getInstance(), key, value);
            }
            jsCallBack(jsInBean, RESULT_SUCCEED);
        } catch (JSONException e) {
            e.printStackTrace();
            jsCallBack(jsInBean, RESULT_FAIL);
        }
    }

    @JavascriptInterface
    public void fun_getStorage(String jsonString) {
        LogInfo.log("ZSM webview js fun_getStorage jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            JSONArray name = new JSONArray(jsInBean.getName());
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (int i = 0; i < name.length(); i++) {
                String key = name.getString(i);
                builder.append("\"" + key + "\":");
                builder.append(FileUtils.getApiFileCache(BaseApplication.getInstance(), key));
                if (i != name.length() - 1) {
                    builder.append(",");
                }
            }
            builder.append("}");
            LogInfo.log("ZSM webview js fun_getStorage result: " + builder.toString());
            jsCallBack(jsInBean, builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            jsCallBack(jsInBean, RESULT_FAIL);
        }
    }

    @JavascriptInterface
    public void fun_isTestApi(String jsonString) {
        LogInfo.log("ZSM webview js fun_getStorage jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        boolean result = PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest();
        jsCallBack(jsInBean, result ? "true" : "false");
    }

    @JavascriptInterface
    public void fun_report(String jsonString) {
        LogInfo.log("ZSM webview js fun_report jsonString: " + jsonString);
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            JSONObject nameObjece = new JSONObject(jsInBean.getName());
            String acode = nameObjece.getString("acode");
            String pageid = nameObjece.getString(PlayConstant.PAGE_ID);
            String fi = nameObjece.getString("fi");
            int wz = nameObjece.getInt("wz");
            String scid = nameObjece.getString("scid");
            String fragid = nameObjece.getString("fragid");
            StatisticsUtils.statisticsActionInfo(this.root.getContext(), pageid, acode, fi, nameObjece.getString("name"), wz, null);
            jsCallBack(jsInBean, RESULT_SUCCEED);
        } catch (JSONException e) {
            e.printStackTrace();
            jsCallBack(jsInBean, RESULT_FAIL);
        }
    }

    @JavascriptInterface
    public void fun_getTK(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            String url = new JSONObject(jsInBean.getName()).getString("url");
            LogInfo.log("ZSM webview js fun_getTk url == " + url);
            jsCallBack(jsInBean, EncryptUtils.letvEncrypt(((long) TimestampBean.getTm().getCurServerTime()) * 1, url));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void fun_autoPlay(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        if (jsInBean != null) {
            try {
                String domId = new JSONObject(jsInBean.getName()).getString("domId");
                LogInfo.log("ZSM webview js fun_autoPlay domId == " + domId);
                String js = "javascript:(function() { try {var dom = document.getElementById('" + domId + "').getElementsByTagName('video')[0];dom.play();}catch(e){} })()";
                if (this.activityHandler != null) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = js;
                    this.activityHandler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @JavascriptInterface
    public void fun_openImage(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            int options = (int) (new JSONObject(jsInBean.getName()).getDouble("scale") * 100.0d);
            if (this.activityHandler != null) {
                Message message = new Message();
                message.what = 3;
                message.obj = Integer.valueOf(options);
                Bundle bundle = new Bundle();
                bundle.putString(a.c, jsInBean.getCallback());
                bundle.putString("callback_id", jsInBean.getCallback_id());
                message.setData(bundle);
                this.activityHandler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.PICK");
        intent.setType("image/*");
        this.mActivity.startActivityForResult(intent, 10002);
    }

    @JavascriptInterface
    public void fun_openCamera(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        try {
            int options = (int) (new JSONObject(jsInBean.getName()).getDouble("scale") * 100.0d);
            if (this.activityHandler != null) {
                Message message = new Message();
                message.what = 3;
                message.obj = Integer.valueOf(options);
                Bundle bundle = new Bundle();
                bundle.putString(a.c, jsInBean.getCallback());
                bundle.putString("callback_id", jsInBean.getCallback_id());
                message.setData(bundle);
                this.activityHandler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mActivity.startActivityForResult(new android.content.Intent("android.media.action.IMAGE_CAPTURE"), 10003);
    }

    @JavascriptInterface
    public void core_getSpaceSize(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        StoreDeviceInfo storeDeviceInfo = StoreManager.getDefaultDownloadDeviceInfo();
        long availableSize = storeDeviceInfo.mAvailable;
        long totalSize = storeDeviceInfo.mTotalSpace;
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"spaceSize\":" + availableSize + ",");
        builder.append("\"totalSize\":" + totalSize);
        builder.append("}");
        LogInfo.log("ZSM webview js core_getSpaceSize result: " + builder.toString());
        jsCallBack(jsInBean, builder.toString());
    }

    @JavascriptInterface
    public void fun_enableShake(String jsonString) {
        this.webSensorEventListener = new WebViewSensorEventListener(this.mActivity, mWebView);
        this.webSensorEventListener.start();
    }

    @JavascriptInterface
    public void fun_getGeolocation(String jsonString) {
        JSInBean jsInBean = JSBridgeUtil.getInstance().parseJsonArray(jsonString);
        StringBuilder builder = new StringBuilder();
        AMapLocation location = AMapLocationTool.getInstance().location();
        builder.append("{");
        builder.append("\"permission\":" + (this.mActivity.getPackageManager().checkPermission("android.permission.ACCESS_COARSE_LOCATION", BaseApplication.getInstance().getPackageName()) == 0 ? "1" : "0") + ",");
        builder.append("\"longitude\":" + location.getLongitude() + ",");
        builder.append("\"latitude\":" + location.getLatitude() + ",");
        builder.append("\"horizontalAccuracy\":0,");
        builder.append("\"verticalAccuracy\":0,");
        builder.append("\"altitude\":" + location.getAltitude() + ",");
        builder.append("\"altitudeAccuracy\":0");
        builder.append("}");
        jsCallBack(jsInBean, builder.toString());
    }
}
