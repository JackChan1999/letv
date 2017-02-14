package com.letv.lemallsdk.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.R;
import com.letv.lemallsdk.api.HttpTask;
import com.letv.lemallsdk.model.ILetvBridge;
import com.letv.lemallsdk.model.MenuEntity;
import com.letv.lemallsdk.model.PageEntity;
import com.letv.lemallsdk.model.TitleStatus;
import com.letv.lemallsdk.util.Constants;
import com.letv.lemallsdk.util.EALogger;
import com.letv.lemallsdk.util.JSObject;
import com.letv.lemallsdk.view.CustomWebView;
import com.letv.lemallsdk.view.IWebviewListener;
import com.letv.lemallsdk.view.LogonManager;
import com.letv.lemallsdk.view.ShareManager;
import com.letv.lemallsdk.view.TitlePopupMenu;
import com.letv.lemallsdk.view.TitlePopupMenu.OnItemOnClickListener;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.Map;

@SuppressLint({"SetJavaScriptEnabled"})
public class LemallSdkMainActivity extends Activity implements OnClickListener, OnItemOnClickListener, IWebviewListener {
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    public static int Horizontal = 2;
    private String NEED_GOBACK = "need_goback=1";
    private String PAGE_FLAG = "page_flag=";
    private ImageButton back_btn;
    private int barHeight = 8;
    private boolean buyByWatching = false;
    private boolean hasSetTitleStatus = false;
    private boolean isAdd = false;
    private boolean isUploadImg = false;
    private JSObject jsObject;
    private RelativeLayout lemallsdk_title_bar;
    private Context mContext;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private CustomWebView mWebView;
    private ImageButton more_btn;
    private boolean needGoBack = false;
    private String pageFlag;
    private ProgressBar progressBar = null;
    private int progressStyle = Horizontal;
    private Button retry_btn;
    private RelativeLayout retry_layout;
    private ImageButton share_btn;
    private String ssoToken;
    private TitleStatus titleInfo;
    private TitlePopupMenu titlePopup;
    private TextView title_tv;
    private int uiStyle = 0;
    private String value;
    private String webviewUrl;
    private RelativeLayout webview_layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lemallsdk_main_activity);
        initData();
        initView();
    }

    private void initData() {
        this.mContext = this;
        this.jsObject = new JSObject(this.mContext);
        SharedPreferences sharedPreferences = getSharedPreferences("lemallsdk", 0);
        LemallPlatform.getInstance().uuid = sharedPreferences.getString("lemall_sdk_uuid", "");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.buyByWatching = extras.getBoolean(Constants.BUY_BY_WATCHING);
            this.pageFlag = extras.getString(Constants.PAGE_FLAG);
            this.value = extras.getString(Constants.VALUE_ID);
            String in_uuid = extras.getString(Constants.UUID);
            if (!TextUtils.isEmpty(this.pageFlag)) {
                if (this.buyByWatching) {
                    if (!in_uuid.equals(LemallPlatform.getInstance().uuid)) {
                        LemallPlatform.getInstance().uuid = in_uuid;
                    }
                    Editor editor = sharedPreferences.edit();
                    editor.putString("lemall_sdk_uuid", LemallPlatform.getInstance().uuid);
                    editor.commit();
                }
                if (!"other".equals(this.pageFlag)) {
                    getWebUrl();
                }
            }
        }
    }

    @SuppressLint({"InflateParams", "NewApi"})
    private void initView() {
        this.uiStyle = LemallPlatform.getInstance().getmAppInfo().getUiStyle();
        this.lemallsdk_title_bar = (RelativeLayout) findViewById(R.id.lemallsdk_title_bar);
        this.title_tv = (TextView) findViewById(R.id.lemallsdk_title_tv);
        this.back_btn = (ImageButton) findViewById(R.id.lemallsdk_back_btn);
        this.share_btn = (ImageButton) findViewById(R.id.lemallsdk_share_btn);
        this.more_btn = (ImageButton) findViewById(R.id.lemallsdk_more_btn);
        this.retry_layout = (RelativeLayout) findViewById(R.id.lemallsdk_webview_retry_layout);
        this.retry_btn = (Button) findViewById(R.id.lemallsdk_webview_retry_btn);
        if (this.uiStyle == 1) {
            this.lemallsdk_title_bar.setBackgroundColor(getResources().getColor(R.color.white_title_1));
            this.title_tv.setTextColor(getResources().getColor(R.color.title_color_1));
            this.back_btn.setBackground(getResources().getDrawable(R.drawable.lemallsdk_back_btn_selector_1));
            this.share_btn.setBackground(getResources().getDrawable(R.drawable.lemallsdk_share_btn_selector_1));
            this.more_btn.setBackground(getResources().getDrawable(R.drawable.lemallsdk_more_btn_selector_1));
        }
        this.titlePopup = new TitlePopupMenu(this, -2, -2);
        this.titlePopup.setItemOnClickListener(this);
        setTitleStatus(this.pageFlag);
        this.webview_layout = (RelativeLayout) findViewById(R.id.lemallsdk_webview_layout);
        this.progressBar = (ProgressBar) LayoutInflater.from(this.mContext).inflate(R.layout.lemallsdk_progressbar_webview, null);
        this.mWebView = new CustomWebView((Context) this, this.progressBar);
        this.mWebView.setLayoutParams(new LayoutParams(-1, -1));
        this.webview_layout.addView(this.mWebView);
        this.mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                EALogger.i(CommonUtils.SDK, "shouldOverrideUrlLoading 方法获取到的URL地址为：" + url);
                try {
                    if (!url.startsWith("tel://")) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.DIAL");
                    intent.setData(Uri.parse("tel:" + url.substring(6)));
                    LemallSdkMainActivity.this.startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return true;
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                EALogger.i(CommonUtils.SDK, "onPageStarted 方法获取到的URL地址为：" + url);
                if (TextUtils.isEmpty(url)) {
                    LemallSdkMainActivity.this.hasSetTitleStatus = false;
                } else {
                    if (url.contains(LemallSdkMainActivity.this.PAGE_FLAG)) {
                        int p_pageflag = url.indexOf(LemallSdkMainActivity.this.PAGE_FLAG);
                        int p_and = url.indexOf("&", p_pageflag);
                        String urlpageflag = "";
                        if (p_pageflag < p_and) {
                            urlpageflag = url.substring(LemallSdkMainActivity.this.PAGE_FLAG.length() + p_pageflag, p_and);
                        } else {
                            urlpageflag = url.substring(LemallSdkMainActivity.this.PAGE_FLAG.length() + p_pageflag);
                        }
                        LemallSdkMainActivity.this.setTitleStatus(urlpageflag);
                        EALogger.i(CommonUtils.SDK, "从页面URL中截取到的页面标记为：" + urlpageflag);
                    } else {
                        LemallSdkMainActivity.this.hasSetTitleStatus = false;
                    }
                    if (url.contains(LemallSdkMainActivity.this.NEED_GOBACK)) {
                        LemallSdkMainActivity.this.needGoBack = true;
                    } else {
                        LemallSdkMainActivity.this.needGoBack = false;
                    }
                    EALogger.i(CommonUtils.SDK, "从页面URL中截取到的返回上一页标记为：" + LemallSdkMainActivity.this.needGoBack);
                }
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                EALogger.i(CommonUtils.SDK, "onPageFinished 方法获取到的URL地址为：" + url);
                if (!LemallSdkMainActivity.this.hasSetTitleStatus) {
                    LemallSdkMainActivity.this.setTitleStatus("other");
                    LemallSdkMainActivity.this.title_tv.setText(view.getTitle());
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                EALogger.i(CommonUtils.SDK, "onReceivedError 方法获取到的URL地址为：" + failingUrl);
                view.stopLoading();
                view.clearView();
                LemallSdkMainActivity.this.retry_layout.setVisibility(0);
                view.loadUrl("javascript:document.body.innerHTML=\"\"");
            }
        });
        this.mWebView.setWebChromeClient(new WebChromeClient() {
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
            }

            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                EALogger.i(CommonUtils.SDK, "onCreateWindow");
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                EALogger.i(CommonUtils.SDK, "onJsAlert");
                Builder builder = new Builder(LemallSdkMainActivity.this.mContext);
                builder.setTitle("提示").setMessage(message).setPositiveButton(17039370, null);
                builder.setCancelable(false);
                builder.create().show();
                result.confirm();
                return true;
            }

            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                EALogger.i(CommonUtils.SDK, "onJsConfirm");
                new Builder(LemallSdkMainActivity.this.mContext).setTitle("提示").setMessage(message).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                }).create().show();
                return true;
            }

            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                EALogger.i(CommonUtils.SDK, "onJsPrompt");
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                LemallSdkMainActivity.this.mUploadMessage = uploadMsg;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                LemallSdkMainActivity.this.startActivityForResult(Intent.createChooser(intent, LemallSdkMainActivity.this.getString(R.string.lemall_complete_the_operation_need_to_use)), 1);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                LemallSdkMainActivity.this.mUploadMessageForAndroid5 = uploadMsg;
                Intent contentSelectionIntent = new Intent("android.intent.action.GET_CONTENT");
                contentSelectionIntent.addCategory("android.intent.category.OPENABLE");
                contentSelectionIntent.setType("image/*");
                Intent chooserIntent = new Intent("android.intent.action.CHOOSER");
                chooserIntent.putExtra("android.intent.extra.INTENT", contentSelectionIntent);
                chooserIntent.putExtra("android.intent.extra.TITLE", "Image Chooser");
                LemallSdkMainActivity.this.startActivityForResult(chooserIntent, 2);
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                LemallSdkMainActivity.this.mUploadMessage = uploadMsg;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                LemallSdkMainActivity.this.startActivityForResult(Intent.createChooser(intent, LemallSdkMainActivity.this.getString(R.string.lemall_complete_the_operation_need_to_use)), 1);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                LemallSdkMainActivity.this.mUploadMessage = uploadMsg;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                LemallSdkMainActivity.this.startActivityForResult(Intent.createChooser(intent, LemallSdkMainActivity.this.getString(R.string.lemall_complete_the_operation_need_to_use)), 1);
            }

            public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    LemallSdkMainActivity.this.progressBar.setVisibility(8);
                    return;
                }
                LemallSdkMainActivity.this.progressBar.setVisibility(0);
                if (!LemallSdkMainActivity.this.isAdd) {
                    if (LemallSdkMainActivity.this.progressStyle == LemallSdkMainActivity.Horizontal) {
                        LemallSdkMainActivity.this.progressBar.setMax(100);
                        LemallSdkMainActivity.this.progressBar.setProgress(0);
                        LemallSdkMainActivity.this.mWebView.addView(LemallSdkMainActivity.this.progressBar, -1, LemallSdkMainActivity.this.barHeight);
                    }
                    LemallSdkMainActivity.this.isAdd = true;
                }
                if (LemallSdkMainActivity.this.progressStyle == LemallSdkMainActivity.Horizontal) {
                    LemallSdkMainActivity.this.progressBar.setVisibility(0);
                    LemallSdkMainActivity.this.progressBar.setProgress(newProgress);
                }
            }
        });
        this.back_btn.setOnClickListener(this);
        this.share_btn.setOnClickListener(this);
        this.more_btn.setOnClickListener(this);
        this.retry_btn.setOnClickListener(this);
        if ("other".equals(this.pageFlag)) {
            this.webviewUrl = this.value;
            this.mWebView.loadUrl(this.webviewUrl);
            this.mWebView.addJavascriptInterface(this.jsObject, "js2sdk");
        }
    }

    private void setTitleStatus(String pageFlag) {
        boolean hasMore = true;
        this.hasSetTitleStatus = true;
        this.titleInfo = LemallPlatform.getInstance().getTitleInfo(pageFlag);
        if (this.titleInfo != null) {
            if (!"other".equals(pageFlag)) {
                this.title_tv.setText(this.titleInfo.getTitle());
            }
            if (!TextUtils.isEmpty(this.titleInfo.getHasShare())) {
                if ("1".equals(this.titleInfo.getHasShare())) {
                    this.share_btn.setVisibility(0);
                } else {
                    this.share_btn.setVisibility(8);
                }
            }
            if (!TextUtils.isEmpty(this.titleInfo.getHasMore())) {
                if (!"1".equals(this.titleInfo.getHasMore())) {
                    hasMore = false;
                }
                if (hasMore) {
                    this.more_btn.setVisibility(0);
                } else {
                    this.more_btn.setVisibility(8);
                }
            }
            if (this.titleInfo.getMenuList() != null && this.titleInfo.getMenuList().size() > 0) {
                this.titlePopup.cleanItem();
                for (int i = 0; i < this.titleInfo.getMenuList().size(); i++) {
                    this.titlePopup.addItem((MenuEntity) this.titleInfo.getMenuList().get(i));
                }
                return;
            }
            return;
        }
        this.title_tv.setText("乐视商城");
        this.share_btn.setVisibility(8);
        this.more_btn.setVisibility(8);
    }

    protected void onResume() {
        this.ssoToken = LemallPlatform.getInstance().getSsoToken();
        EALogger.i(CommonUtils.SDK, "从乐视网登录页面传回来的ssoToken值为：" + this.ssoToken);
        if (LemallPlatform.getInstance().paySuccess.booleanValue()) {
            EALogger.i("onResume", "paySuccess支付成功页支付成功页支付成功页支付成功页");
            this.webviewUrl = "http://m.lemall.com/orders/defray.html?merchant_no=" + LemallPlatform.getInstance().payOrderId + "&price=" + LemallPlatform.getInstance().payOrderPrice + "&trade_result=success";
            EALogger.i("onResume", "webviewUrl===>" + this.webviewUrl);
            this.mWebView.loadUrl(this.webviewUrl);
            this.mWebView.addJavascriptInterface(this.jsObject, "js2sdk");
            EALogger.i("onResume", "loadloadloadloadloadloadloadloadloadloadloadload");
            LemallPlatform.getInstance().paySuccess = Boolean.valueOf(false);
        } else if (!this.isUploadImg) {
            LogonManager logon = new LogonManager(this);
            if (TextUtils.isEmpty(this.ssoToken)) {
                logon.syncCookieByGuest(this);
                if (this.needGoBack) {
                    onBackPressed();
                }
            } else {
                logon.syncToken4Logon(this.ssoToken, this);
            }
        }
        super.onResume();
    }

    public void reLoadWebUrl() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (LemallSdkMainActivity.this.mWebView != null) {
                    LemallSdkMainActivity.this.mWebView.reload();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Uri result;
        if (requestCode == 1) {
            this.isUploadImg = true;
            if (this.mUploadMessage != null) {
                result = (intent == null || resultCode != -1) ? null : intent.getData();
                this.mUploadMessage.onReceiveValue(result);
                this.mUploadMessage = null;
            } else {
                return;
            }
        } else if (requestCode == 2) {
            this.isUploadImg = true;
            if (this.mUploadMessageForAndroid5 != null) {
                result = (intent == null || resultCode != -1) ? null : intent.getData();
                if (result != null) {
                    this.mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                } else {
                    this.mUploadMessageForAndroid5.onReceiveValue(new Uri[0]);
                }
                this.mUploadMessageForAndroid5 = null;
            } else {
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mWebView.removeAllViews();
        this.mWebView.destroy();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.lemallsdk_back_btn) {
            onBackPressed();
        } else if (id == R.id.lemallsdk_share_btn) {
            ShareManager.getInstance(this).showLayout();
        } else if (id == R.id.lemallsdk_more_btn) {
            try {
                this.titlePopup.show(v);
            } catch (Exception e) {
            }
        } else if (id == R.id.lemallsdk_webview_retry_btn) {
            this.retry_layout.setVisibility(8);
            if (TextUtils.isEmpty(this.webviewUrl)) {
                getWebUrl();
            } else {
                reLoadWebUrl();
            }
            if (!TextUtils.isEmpty(this.ssoToken)) {
                new LogonManager(this).syncToken4Logon(this.ssoToken, this);
            }
        }
    }

    public void onItemClick(MenuEntity item, int position) {
        if (Constants.PAGE_FLAG_CLOSE.equals(item.getPageFlag())) {
            finish();
            return;
        }
        this.webviewUrl = item.getUrl();
        addUuidToUrl(LemallPlatform.getInstance().uuid);
        EALogger.i(CommonUtils.SDK, "菜单点击项的URL地址为：" + this.webviewUrl);
        if (this.retry_layout.getVisibility() == 0) {
            this.retry_layout.setVisibility(8);
        }
        this.mWebView.loadUrl(this.webviewUrl);
        this.mWebView.addJavascriptInterface(this.jsObject, "js2sdk");
    }

    private void getWebUrl() {
        Map<String, String> bodyMap = HttpTask.getEncryBodyMap();
        bodyMap.put("appid", String.valueOf(LemallPlatform.getInstance().getmAppInfo().getId()));
        bodyMap.put(Constants.PAGE_FLAG, this.pageFlag);
        if (!TextUtils.isEmpty(this.value) && this.pageFlag.equals(Constants.PAGE_FLAG_PRODUCTDETAIL)) {
            bodyMap.put("productId", this.value);
        }
        if (!TextUtils.isEmpty(this.value) && this.pageFlag.equals(Constants.PAGE_FLAG_ORDERDETAIL)) {
            bodyMap.put("orderId", this.value);
        }
        HttpTask.getPage(new ILetvBridge() {
            public void shitData(Object failObj) {
                LemallSdkMainActivity.this.retry_layout.setVisibility(0);
                super.shitData(failObj);
            }

            public void goldData(Object successObj) {
                LemallSdkMainActivity.this.retry_layout.setVisibility(8);
                PageEntity page = (PageEntity) successObj;
                if (page != null) {
                    if (Constants.PAGE_FLAG_PAYSUCCESS.equals(LemallSdkMainActivity.this.pageFlag)) {
                        LemallSdkMainActivity.this.webviewUrl = page.getUrl() + LemallPlatform.getInstance().orderId + ".html";
                    } else {
                        LemallSdkMainActivity.this.webviewUrl = page.getUrl();
                    }
                    LemallSdkMainActivity.this.addUuidToUrl(LemallPlatform.getInstance().uuid);
                    EALogger.i(CommonUtils.SDK, "获取到的URL地址为：" + LemallSdkMainActivity.this.webviewUrl);
                    LemallSdkMainActivity.this.mWebView.loadUrl(LemallSdkMainActivity.this.webviewUrl);
                    LemallSdkMainActivity.this.mWebView.addJavascriptInterface(LemallSdkMainActivity.this.jsObject, "js2sdk");
                }
            }
        });
    }

    public void onBackPressed() {
        if (this.mWebView.canGoBack()) {
            this.mWebView.goBack();
        } else {
            finish();
        }
    }

    private void addUuidToUrl(String uuid) {
        if (!TextUtils.isEmpty(uuid)) {
            this.webviewUrl += (this.webviewUrl.contains("?") ? "&" : "?") + "uuid=" + uuid;
        }
    }
}
