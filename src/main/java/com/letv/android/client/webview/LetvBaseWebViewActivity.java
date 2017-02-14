package com.letv.android.client.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.sdk.authjs.a;
import com.letv.android.client.activity.SearchMainActivity;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.messagemodel.WebViewInviteShareProtocol;
import com.letv.android.client.commonlib.messagemodel.WebViewShareProtocol;
import com.letv.android.client.commonlib.messagemodel.WebViewShareProtocol.OnWebViewRefreshListener;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.webapp.LetvWebAppManager;
import com.letv.android.client.webview.SyncUserStateUtil.GetUserloginStateListener;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.UIsUtils;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.json.JSONObject;

public class LetvBaseWebViewActivity extends WrapActivity implements OnClickListener {
    public static final int AUTO_PLAY = 1;
    public static final int LOADURL = 0;
    public static final int Login_OK = 12345;
    public static final int PIC_COMPRESS = 3;
    public static final int RES_CODE_CAMEIA_PIC_FORJS = 10003;
    public static final int RES_CODE_PHOTO_ALBUM_FORJS = 10002;
    public static final int WEBVIEWREQUESTCODE = 1;
    public static final int WEBVIEWRESULTCODE = 2;
    public static final int goToLoginPageForJS = 1000;
    public static final int goToLoginPageForMatchUrl = 1001;
    public static int netType = -1;
    protected String autoPlayJs = "";
    protected ImageView back_iv;
    protected String baseUrl;
    protected ImageView close;
    protected boolean flag = true;
    protected ImageView getMoreImg;
    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case 0:
                        LetvBaseWebViewActivity.this.loadUrlBySync(msg.getData().getString("url"));
                        return;
                    case 1:
                        if (msg.obj instanceof String) {
                            LetvBaseWebViewActivity.this.autoPlayJs = msg.obj.toString();
                            if (!LetvBaseWebViewActivity.this.mIsLoading) {
                                LetvBaseWebViewActivity.this.mWebView.loadUrl(LetvBaseWebViewActivity.this.autoPlayJs);
                                return;
                            }
                            return;
                        }
                        return;
                    case 3:
                        LetvBaseWebViewActivity.this.mPicCompressOptions = ((Integer) msg.obj).intValue();
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            LetvBaseWebViewActivity.this.mJsGetPicCallback = bundle.getString(a.c);
                            LetvBaseWebViewActivity.this.mJsGetPicCallbackid = bundle.getString("callback_id");
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    };
    protected boolean isFinish;
    private boolean isNeedStatistics;
    volatile boolean loadFinish = false;
    protected String loadType;
    protected Activity mActivity = this;
    protected boolean mFlagErrorpagebycookie = true;
    WebViewInviteShareProtocol mInviteShareProtocol = null;
    protected boolean mIsLoading = true;
    public boolean mIsLoadingRefresh = false;
    private String mJsGetPicCallback;
    private String mJsGetPicCallbackid;
    private NetWorkBroadcastReceiver mNetWorkBroadcastReceiver = null;
    private int mPicCompressOptions = 0;
    private String mShareDefaultDesc = "";
    String mShareDefaultIcon = "";
    String mShareDefaultTitle = "";
    WebViewShareProtocol mShareProtocol = null;
    protected WebView mWebView;
    protected ProgressBar progressBar;
    protected TextView pullDownUrlText;
    protected PublicLoadLayout root;
    protected TextView titleView;
    private View topBar;
    private LetvWebViewChromeClient webChromeClient;

    final class GetShareDesc {
        GetShareDesc() {
        }

        @JavascriptInterface
        public void getShareDesc(String result) {
            LetvBaseWebViewActivity.this.handler.post(new 1(this, result));
        }
    }

    public void setNeedStatistics(boolean isNeedStatistics) {
        this.isNeedStatistics = isNeedStatistics;
    }

    protected WebView setMyWebView() {
        return null;
    }

    @TargetApi(11)
    protected void onCreate(Bundle savedInstanceState) {
        LogInfo.log("wlx", "LetvBaseWebViewActivity onCreate");
        super.onCreate(savedInstanceState);
        setRedPacketFrom(new RedPacketFrom(0));
        netType = NetworkUtils.getNetworkType();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.mNetWorkBroadcastReceiver = new NetWorkBroadcastReceiver();
        registerReceiver(this.mNetWorkBroadcastReceiver, intentFilter);
        setContentView(R.layout.letv_webview_new);
        getWindow().addFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
        if (!TextUtils.isEmpty(getIntent().getStringExtra(LetvWebViewActivityConfig.LOAD_TYPE))) {
            this.loadType = getIntent().getStringExtra(LetvWebViewActivityConfig.LOAD_TYPE);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("url"))) {
            this.baseUrl = getIntent().getStringExtra("url");
        }
        String tempUrl = setBaseUrl();
        if (!TextUtils.isEmpty(tempUrl)) {
            this.baseUrl = tempUrl;
        }
        LogInfo.log("ZSM isFromNative " + getIntent().getBooleanExtra(LetvWebViewActivityConfig.IS_FROM_NATIVE, false));
        if (getIntent().getBooleanExtra(LetvWebViewActivityConfig.IS_FROM_NATIVE, false)) {
            NativeWebViewUtils.getInstance().setIsFromNative(true);
            LetvWebAppManager.setBasePath(this.mContext);
        } else {
            NativeWebViewUtils.getInstance().setIsFromNative(false);
            this.baseUrl = getValidUrl(this.baseUrl);
            if (TextUtils.isEmpty(this.baseUrl)) {
                return;
            }
        }
        LogInfo.log("wlx", "baseUrl：" + this.baseUrl);
        findView();
        if (!getIntent().getBooleanExtra(LetvWebViewActivityConfig.IS_SHOW_SHARE, true)) {
            this.getMoreImg.setVisibility(4);
        }
        initWebView();
        if (!getIntent().getBooleanExtra(LetvWebViewActivityConfig.IS_RED_ENVELOPE, false)) {
            statisticsIfNeed();
        }
        initView();
        this.flag = true;
        setCookie();
        loadUrl();
    }

    private void loadUrl() {
        if (isNeedLogin()) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this).create(1001, 1001)));
        } else {
            loadUrlOrSyncUserState();
        }
    }

    private boolean isNeedLogin() {
        return (this.baseUrl.startsWith("http://sso.letv.com/user/mLoginMini") || this.baseUrl.startsWith("http://sso.letv.com/user/mloginHome")) && !PreferencesManager.getInstance().isLogin();
    }

    private void setCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        LetvUtils.setCookies(this, this.baseUrl);
        LogInfo.log("wlx", "setCookies之后  baseCookie: " + cookieManager.getCookie(this.baseUrl));
    }

    private void initView() {
        this.pullDownUrlText.setText(getString(R.string.supplied_by, new Object[]{getUrlTitle(this.baseUrl)}));
        this.getMoreImg.setOnClickListener(this);
        this.close.setOnClickListener(this);
        this.back_iv.setOnClickListener(this);
        this.titleView.setText(this.loadType);
        this.root.setRefreshData(new RefreshData() {
            public void refreshData() {
                LetvBaseWebViewActivity.this.refresh();
            }
        });
    }

    public View getTopBar() {
        return this.topBar;
    }

    protected void syncUserState() {
        this.flag = true;
        SyncUserStateUtil.getUserStateUtil(new GetUserloginStateListener() {
            public void onSyncSuccessed() {
                new Handler(LetvBaseWebViewActivity.this.getMainLooper()).post(new 1(this));
            }

            public void onSyncFailed() {
                new Handler(LetvBaseWebViewActivity.this.getMainLooper()).post(new 2(this));
            }
        }).syncUserStateByAsync(this);
        this.progressBar.setProgress(10);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    protected String setBaseUrl() {
        return "";
    }

    protected void onStart() {
        super.onStart();
    }

    private String getValidUrl(String baseUrl) {
        String validUrl = baseUrl;
        if (TextUtils.isEmpty(baseUrl) || baseUrl.startsWith("http://") || baseUrl.startsWith("https://")) {
            return validUrl;
        }
        return "http://" + baseUrl;
    }

    protected void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 || requestCode == 1001) {
            SyncUserStateUtil.getUserStateUtil(new GetUserloginStateListener() {
                public void onSyncSuccessed() {
                    LogInfo.log("wlx", "同步成功！");
                    if (requestCode == 1000) {
                        JSBridgeUtil.getInstance().loginCallBack(true);
                    } else if (requestCode != 1001) {
                    } else {
                        if (resultCode == LetvBaseWebViewActivity.Login_OK) {
                            LetvBaseWebViewActivity.this.loadUrlBySync(LetvBaseWebViewActivity.this.baseUrl);
                        } else {
                            LetvBaseWebViewActivity.this.finish();
                        }
                    }
                }

                public void onSyncFailed() {
                    LogInfo.log("wlx", "同步失败，显示错误页面！");
                    LetvBaseWebViewActivity.this.mFlagErrorpagebycookie = true;
                    LetvBaseWebViewActivity.this.showErrorPage();
                }
            }).syncUserStateByAsync(this);
        }
        if ((requestCode == 10003 || requestCode == 10002) && data != null) {
            Bitmap picBitmap = null;
            Uri uri = data.getData();
            if (uri == null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    picBitmap = (Bitmap) bundle.get(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                }
            }
            jsCallbackPic(uri, picBitmap);
        }
        if (this.mInviteShareProtocol != null) {
            this.mInviteShareProtocol.activityResult(requestCode, resultCode, data);
        }
        if (this.mShareProtocol != null) {
            this.mShareProtocol.activityResult(requestCode, resultCode, data);
        }
    }

    protected WebView getWebView() {
        return this.mWebView;
    }

    protected void initWebView() {
        WebView childWebView = setMyWebView();
        if (childWebView != null) {
            this.mWebView = childWebView;
        }
        initSetting(this.mWebView, this.mActivity);
        if (this.webChromeClient == null) {
            this.webChromeClient = new LetvWebViewChromeClient(this);
        }
        setAcceptThirdPartyCookies();
        this.mWebView.setWebChromeClient(this.webChromeClient);
        JSBridgeUtil.getInstance().setJSBridge(this, this.mWebView, this.handler, this.root);
    }

    @SuppressLint({"JavascriptInterface"})
    public void initSetting(WebView webView, Context context) {
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUserAgentString(LetvUtils.createUA(webView.getSettings().getUserAgentString()));
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new GetShareDesc(), "getShareDesc");
    }

    @TargetApi(21)
    private void setAcceptThirdPartyCookies() {
        if (VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.mWebView, true);
        }
    }

    protected void getDesc() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("function(){\n");
        buffer.append("  var m = document.getElementsByTagName('meta');\n");
        buffer.append("  for(var i in m) { \n");
        buffer.append("    if(m[i].name == 'description') {\n");
        buffer.append("      return m[i].content;\n");
        buffer.append("    }\n");
        buffer.append("  }\n");
        buffer.append("  return '';\n");
        buffer.append("}()");
        this.mWebView.loadUrl("javascript:window.getShareDesc.getShareDesc(" + buffer.toString() + ")");
    }

    protected String getUrlTitle(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.contains("http://")) {
            String redirectFlag = "to=http%3A%2F%2F";
            if (url.contains(redirectFlag)) {
                url = url.substring(url.indexOf(redirectFlag) + redirectFlag.length());
                if (url.contains("%2F")) {
                    url = url.substring(0, url.indexOf("%2F"));
                }
            } else {
                url = url.replace("http://", "");
                if (url.contains("/")) {
                    url = (String) url.subSequence(0, url.indexOf("/"));
                } else if (url.contains("?")) {
                    url = (String) url.subSequence(0, url.indexOf("?"));
                }
            }
        } else if (url.length() > 15) {
            url = url.substring(0, 15);
        }
        return url;
    }

    private void findView() {
        this.getMoreImg = (ImageView) findViewById(R.id.get_more);
        this.titleView = (TextView) findViewById(R.id.letv_webview_title);
        this.close = (ImageView) findViewById(R.id.close_iv);
        this.back_iv = (ImageView) findViewById(R.id.back_iv);
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        this.root = new PublicLoadLayout(this);
        this.root.addContent(R.layout.letv_webview_only);
        rootLayout.addView(this.root, new LayoutParams(-1, -1));
        this.mWebView = (WebView) this.root.findViewById(R.id.webView);
        this.progressBar = (ProgressBar) findViewById(R.id.loading_progress);
        this.pullDownUrlText = (TextView) findViewById(R.id.pulldown_title_url);
        this.topBar = findViewById(R.id.web_view_top_layout);
    }

    protected synchronized void loadUrlBySync(String url) {
        LogInfo.log("wlx", "loadUrlBySync url: " + url);
        if (this.mWebView != null) {
            if (TextUtils.isEmpty(url) || !url.contains("next_action=")) {
                LogInfo.log("wlx", "mWebView.loadUrl(url)");
                this.progressBar.setVisibility(0);
                this.progressBar.setProgress(10);
                if (getIntent().getBooleanExtra(LetvWebViewActivityConfig.IS_RED_ENVELOPE, false)) {
                    LogInfo.log("wlx", "红包链接");
                    this.mWebView.loadUrl(getFullRedPacketListUrl(url));
                } else {
                    this.mWebView.loadUrl(url);
                }
            } else {
                try {
                    this.baseUrl = url.substring(url.indexOf("next_action=") + "next_action=".length());
                    String decode_baseUrl = URLDecoder.decode(this.baseUrl, "UTF-8");
                    this.mWebView.loadUrl(decode_baseUrl);
                    this.baseUrl = decode_baseUrl;
                    LogInfo.log("wlx", "next_action_url: " + this.baseUrl);
                    LogInfo.log("wlx", "decoder next_action_url: " + decode_baseUrl);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    showErrorPage();
                }
            }
        }
    }

    private String getFullRedPacketListUrl(String url) {
        String fullUrl = "";
        String token = SyncUserStateUtil.getCookieSsoToken(url);
        String deviceId = LetvUtil.getDeviceID(this);
        String time = System.currentTimeMillis() + "";
        LogInfo.log("RedPacket", "token=" + token);
        fullUrl = url + "deviceId/" + deviceId + "/time/" + time + "/auth/" + LetvUtil.MD5Helper(deviceId + token + time + "springPackage") + "/appId/letv01";
        LogInfo.log("RedPacket", "跳转到红包list页面：url=" + fullUrl);
        return fullUrl;
    }

    private void statisticsIfNeed() {
        if (!this.isNeedStatistics) {
            return;
        }
        if (this.baseUrl != null && this.baseUrl.contains(LetvUtils.WEB_INNER_FLAG) && !this.baseUrl.contains("ref=") && this.baseUrl.contains("?")) {
            this.baseUrl += SearchMainActivity.SEARCH_H5_WEB_URL_REF;
        } else if (this.baseUrl != null && this.baseUrl.contains(LetvUtils.WEB_INNER_FLAG) && !this.baseUrl.contains("ref=") && !this.baseUrl.contains("?")) {
            this.baseUrl += "?ref=0101";
        }
    }

    private void showErrorPage() {
        this.flag = false;
        this.root.netError(false);
        this.progressBar.setVisibility(8);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_iv) {
            finish();
        } else if (id == R.id.back_iv) {
            if (this.mWebView.canGoBack()) {
                LogInfo.log("+-->", "按下返回值之前的url：" + this.mWebView.getUrl());
                LogInfo.log("+-->", "LetvBaseWebView---- cangoback");
                LogInfo.log("+-->", "按下返回值之前的url：" + this.mWebView.getUrl());
                if (this.mWebView.copyBackForwardList().getCurrentIndex() <= 0) {
                    this.pullDownUrlText.setText(getString(R.string.supplied_by, new Object[]{getUrlTitle(list.getItemAtIndex(0).getUrl())}));
                } else {
                    this.pullDownUrlText.setText(getString(R.string.supplied_by, new Object[]{getUrlTitle(list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl())}));
                }
                this.mWebView.goBack();
                if (!this.close.isShown()) {
                    this.close.setVisibility(0);
                }
            } else if (getIntent().getIntExtra("from", -1) == 20) {
                ActivityUtils.getInstance().removeAll();
            } else {
                finish();
            }
        } else if (id != R.id.get_more) {
        } else {
            if (LetvUtils.isInHongKong()) {
                UIsUtils.showToast(R.string.share_copyright_disable);
                return;
            }
            getDesc();
            onShareDialogShow(null);
        }
    }

    public void finish() {
        super.finish();
        this.autoPlayJs = null;
    }

    @TargetApi(11)
    protected void onDestroy() {
        super.onDestroy();
        JSBridgeUtil.getInstance().unRegisterAllObservers();
        unregisterReceiver(this.mNetWorkBroadcastReceiver);
        try {
            getWindow().clearFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
            this.isFinish = true;
            if (this.mWebView != null) {
                ViewGroup vg = (ViewGroup) this.mWebView.getParent();
                if (vg != null) {
                    vg.removeView(this.mWebView);
                }
                this.mWebView.stopLoading();
                this.mWebView.setVisibility(8);
                this.mWebView.removeAllViews();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        new Handler(LetvBaseWebViewActivity.this.getMainLooper()).post(new 1(this));
                    }
                }, ViewConfiguration.getZoomControlsTimeout());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getActivityName() {
        return getClass().getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }

    protected void onResume() {
        super.onResume();
        this.mWebView.clearCache(true);
        this.mWebView.destroyDrawingCache();
        this.mWebView.setWillNotCacheDrawing(true);
        this.isFinish = false;
        callHiddenWebViewMethod("onResume");
    }

    protected void onRestart() {
        super.onRestart();
        if (getIntent().getBooleanExtra(LetvWebViewActivityConfig.IS_FROM_NATIVE, false)) {
            this.mWebView.loadUrl(JavaScriptinterface.buildupJSCallString("LetvJSBridge.fireEvent", "onWindowBack", "{}"));
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        HashMap<String, Object> onOrientationChangeMap = new HashMap();
        if (newConfig.orientation == 2) {
            onOrientationChangeMap.put("direction", "landscape");
        } else if (newConfig.orientation == 1) {
            onOrientationChangeMap.put("direction", "portrait");
        }
        String callString = "javascript:LetvJSBridge.fireEvent('onOrientationChange','" + new JSONObject(onOrientationChangeMap).toString() + "')";
        LogInfo.log("wangtao", "onOrientationChange callString: " + callString);
        this.mWebView.loadUrl(callString);
    }

    protected void onPause() {
        if (!(this.mInviteShareProtocol == null || this.mInviteShareProtocol.getFragment() == null || !this.mInviteShareProtocol.getFragment().isAdded())) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.remove(this.mInviteShareProtocol.getFragment());
            tx.commit();
        }
        if (!(this.mShareProtocol == null || this.mShareProtocol.getFragment() == null || !this.mShareProtocol.getFragment().isAdded())) {
            tx = getSupportFragmentManager().beginTransaction();
            tx.remove(this.mShareProtocol.getFragment());
            tx.commit();
        }
        getSupportFragmentManager().executePendingTransactions();
        super.onPause();
        callHiddenWebViewMethod("onPause");
    }

    private void callHiddenWebViewMethod(String name) {
        if (this.mWebView != null) {
            try {
                WebView.class.getMethod(name, new Class[0]).invoke(this.mWebView, new Object[0]);
            } catch (Exception e) {
                LogInfo.log("wlx", e.getMessage());
            }
        }
    }

    public void onJsShareDialogShow(String jsonString) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (this.mInviteShareProtocol == null) {
                LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_SHARE_INVITE_WEBVIEW_INIT));
                if (LeResponseMessage.checkResponseMessageValidity(response, WebViewInviteShareProtocol.class)) {
                    this.mInviteShareProtocol = (WebViewInviteShareProtocol) response.getData();
                }
            } else {
                ft.remove(this.mInviteShareProtocol.getFragment());
            }
            if (this.mInviteShareProtocol != null) {
                this.mInviteShareProtocol.setShareText(jsonString);
                ft.add(this.mInviteShareProtocol.getFragment(), "shareDialogs");
                ft.commitAllowingStateLoss();
            }
        }
    }

    public void onShareDialogShow(String jsonString) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (this.mShareProtocol == null) {
                LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_SHARE_WEBVIEW_INIT, new OnWebViewRefreshListener() {
                    public void onRefresh() {
                        LetvBaseWebViewActivity.this.refresh();
                    }
                }));
                if (LeResponseMessage.checkResponseMessageValidity(response, WebViewShareProtocol.class)) {
                    this.mShareProtocol = (WebViewShareProtocol) response.getData();
                }
            } else {
                ft.remove(this.mShareProtocol.getFragment());
            }
            if (this.mShareProtocol != null) {
                this.mShareProtocol.setDefaultShareText(this.mShareDefaultTitle, this.mShareDefaultDesc, getBaseUrl(), this.mShareDefaultIcon, this.baseUrl);
                ft.add(this.mShareProtocol.getFragment(), "shareDialog");
                this.mShareProtocol.setIsLoadComplete(this.loadFinish);
                this.mShareProtocol.notifyShareLayout();
            }
            ft.commitAllowingStateLoss();
        }
    }

    private void loadUrlOrSyncUserState() {
        if (!NetworkUtils.isNetworkAvailable() && NativeWebViewUtils.getInstance().getFromNative()) {
            loadUrlBySync(this.baseUrl);
        } else if (!NetworkUtils.isNetworkAvailable()) {
            showErrorPage();
        } else if (SyncUserStateUtil.isNeedSyncUserState()) {
            syncUserState();
        } else {
            loadUrlBySync(this.baseUrl);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.mShareProtocol != null && this.mShareProtocol.getFragment() != null && this.mShareProtocol.getFragment().getShowsDialog()) {
            try {
                this.mShareProtocol.getFragment().dismissAllowingStateLoss();
            } catch (Exception e) {
            }
            finish();
        }
    }

    public void parseShareJson(String jsonString) {
        if (this.mShareProtocol == null) {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_SHARE_WEBVIEW_INIT, new OnWebViewRefreshListener() {
                public void onRefresh() {
                    LetvBaseWebViewActivity.this.refresh();
                }
            }));
            if (LeResponseMessage.checkResponseMessageValidity(response, WebViewShareProtocol.class)) {
                this.mShareProtocol = (WebViewShareProtocol) response.getData();
            }
        }
        if (this.mShareProtocol != null) {
            this.mShareProtocol.setJsonShareText(jsonString);
        }
    }

    private void refresh() {
        LogInfo.log("wlx", "刷新");
        this.mIsLoadingRefresh = isLoading();
        if (SyncUserStateUtil.isSyncUserStateSuccessWithH5() || !this.mFlagErrorpagebycookie) {
            this.flag = true;
            if (TextUtils.isEmpty(this.mWebView.getUrl())) {
                this.mWebView.loadUrl(this.baseUrl);
                return;
            } else {
                this.mWebView.reload();
                return;
            }
        }
        this.progressBar.setVisibility(8);
        this.progressBar.setVisibility(0);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                LetvBaseWebViewActivity.this.progressBar.setProgress(10);
            }
        }, 50);
        LogInfo.log("wlx", "错误页面刷新加载url");
        syncUserState();
    }

    public boolean isLoading() {
        return true;
    }

    private void jsCallbackPic(Uri uri, Bitmap bitmap) {
        if (uri != null) {
            try {
                bitmap = compressImage(FileUtils.getBitmapByPath(getPicPathFromUri(uri), 1800, 1800));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (bitmap != null) {
            bitmap = compressImage(bitmap);
        }
        this.mWebView.loadUrl(JavaScriptinterface.buildupJSCallString(this.mJsGetPicCallback, this.mJsGetPicCallbackid, "{\"image\":\"" + imgToBase64(bitmap) + "\"}"));
    }

    @TargetApi(19)
    private String getPicPathFromUri(Uri uri) {
        boolean isKitKat;
        if (VERSION.SDK_INT >= 19) {
            isKitKat = true;
        } else {
            isKitKat = false;
        }
        if (isKitKat && DocumentsContract.isDocumentUri(this, uri)) {
            String[] split;
            if (isExternalStorageDocument(uri)) {
                split = DocumentsContract.getDocumentId(uri).split(com.letv.pp.utils.NetworkUtils.DELIMITER_COLON);
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                return null;
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(this, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (!isMediaDocument(uri)) {
                return null;
            } else {
                String type = DocumentsContract.getDocumentId(uri).split(com.letv.pp.utils.NetworkUtils.DELIMITER_COLON)[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                return getDataColumn(this, contentUri, "_id=?", new String[]{split[1]});
            }
        } else if (WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(this, uri, null, null);
        } else if (IDataSource.SCHEME_FILE_TAG.equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {
            return null;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);
        int options = 10;
        if (this.mPicCompressOptions != 0) {
            options = this.mPicCompressOptions;
        }
        image.compress(CompressFormat.JPEG, options, baos);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, null);
    }

    public static String imgToBase64(Bitmap bitmap) {
        Throwable th;
        String str = null;
        if (bitmap != null) {
            ByteArrayOutputStream out = null;
            try {
                ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                try {
                    bitmap.compress(CompressFormat.JPEG, 100, out2);
                    out2.flush();
                    out2.close();
                    str = Base64.encodeToString(out2.toByteArray(), 0);
                    try {
                        out2.flush();
                        out2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    out = out2;
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    out = out2;
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e32) {
                        e32.printStackTrace();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                out.flush();
                out.close();
                return str;
            } catch (Throwable th3) {
                th = th3;
                out.flush();
                out.close();
                throw th;
            }
        }
        return str;
    }
}
