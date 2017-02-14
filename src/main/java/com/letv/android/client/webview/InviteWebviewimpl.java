package com.letv.android.client.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.letv.android.client.commonlib.config.InviteWebviewimplConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.messagemodel.WebViewInviteShareProtocol;
import com.letv.android.client.commonlib.popdialog.ApkDownloadAsyncTask;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.util.DataConstant.PAGE;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.pp.utils.NetworkUtils;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.HashMap;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class InviteWebviewimpl extends LetvBaseWebViewActivity {
    private static final int lOGIN_REQUEST_CODE = 61440;
    private WebView _mWebView;
    private String baseUrl = "";
    private String from = "mobile_tv";
    private String loadUrl;
    private WebView mWebView;
    private WebViewInviteShareProtocol shareDialogFragment;
    private SyncUserStateUtil syncUserStateUtil;
    private String web_flag = "";
    private String web_url = "";

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogInfo.log("+->", "shouldOverrideUrlLoading--》》url" + url);
            UIsUtils.hideSoftkeyboard(InviteWebviewimpl.this);
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            InviteWebviewimpl.this.flag = false;
            InviteWebviewimpl.this.root.netError(false);
            InviteWebviewimpl.this.progressBar.setVisibility(8);
            InviteWebviewimpl.this.mWebView.setNetworkAvailable(false);
        }
    }

    private void initParam() {
        Intent intent = getIntent();
        this.web_flag = intent.getStringExtra(InviteWebviewimplConfig.INVITE_FLAG);
        this.web_url = intent.getStringExtra(InviteWebviewimplConfig.FLOATBALLACTIVEURL);
        getURL();
        setBaseUrl(this.baseUrl);
    }

    protected void onCreate(Bundle savedInstanceState) {
        LogInfo.log("lxx", "inviteWebviewiml");
        initParam();
        super.onCreate(savedInstanceState);
        findView();
    }

    protected String setBaseUrl() {
        return this.baseUrl;
    }

    private void setJSBridgeByVersion() {
        LogInfo.log("lxx", "versoinName: " + LetvUtils.getOSVersionName());
        LogInfo.log("lxx", "versionCode: " + LetvUtils.getOSVersionCode());
        if (LetvUtils.getOSVersionCode() < 10) {
            UIsUtils.showToast(R.string.toast_not_support_js);
            this.mWebView.getSettings().setJavaScriptEnabled(false);
        } else if (LetvUtils.getOSVersionCode() == 10) {
            if ("2.3.3".equals(LetvUtils.getOSVersionName())) {
                UIsUtils.showToast(R.string.toast_not_support_js);
                this.mWebView.getSettings().setJavaScriptEnabled(false);
                return;
            }
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            JSBridgeUtil.getInstance().setJSBridge(this, this.mWebView, null, null);
        } else {
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            JSBridgeUtil.getInstance().setJSBridge(this, this.mWebView, null, null);
        }
    }

    private void getURL() {
        if ("check".equals(this.web_flag)) {
            this.loadUrl = getRightUrl();
        } else if (ControlAction.ACTION_KEY_LEFT.equals(this.web_flag)) {
            this.loadUrl = getLeftUrl();
        } else if ("floatBallActive".equals(this.web_flag)) {
            String str = this.web_url;
            this.baseUrl = str;
            this.loadUrl = str;
            LogInfo.log("LM", ".....web_url..." + this.web_url);
        }
        if (!TextUtils.isEmpty(this.loadUrl) && !"floatBallActive".equals(this.web_flag)) {
            this.baseUrl = this.loadUrl;
            String ssoTk = PreferencesManager.getInstance().getSso_tk();
            if (TextUtils.isEmpty(ssoTk)) {
                if ("check".equals(this.web_flag)) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this).create(4)));
                } else if (ControlAction.ACTION_KEY_LEFT.equals(this.web_flag)) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(this).create(5)));
                }
            }
            if (!TextUtils.isEmpty(ssoTk)) {
                this.loadUrl = "http://sso.letv.com/user/setUserStatus?tk=" + ssoTk + "&from=mobile_tv&next_action=" + URLEncoder.encode(this.loadUrl);
            }
        }
    }

    private String getRightUrl() {
        return myEncodeUrl(new String[]{"84", "80", "80", "75", "57", "82", "82", "84", "61", "70", "78", "64", "70", "66", "87", "80", "69", "70", "71", "79", "78", "82", "75", "86", "79", "78", "79", "80", "87", "82", "83", "84", "85", "86", "87"});
    }

    private String getLeftUrl() {
        String s = new BigInteger("95472").shiftLeft(67).shiftLeft(23).toString();
        String[] a = new String[]{"84", "80", "80", "75", "57", "82", "82", "84", "61", "70", "78", "64", "70", "66", "87", "80", "69", "70", "71", "79", "78", "82", "75", "86", "79", "78", "79", "80", "87", "82", "83", VType.FLV_1080P6M_3D, "32", "78", VType.H265_FLV_800, "80", "36", "61", "87", "69", VType.H265_FLV_800, "61", VType.H265_FLV_1080P, "51", "83", "45", "83", VType.H265_FLV_800, VType.H265_FLV_1300, VType.H265_FLV_720P, VType.H265_FLV_1080P, "51", "83"};
        String devid = Global.DEVICEID;
        return String.format(myEncodeUrl(a), new Object[]{devid, MD5.toMd5(Global.DEVICEID + s)});
    }

    private String myEncodeUrl(String[] arrs) {
        HashMap<String, String> m = new HashMap();
        m.put("84", "h");
        m.put("80", "t");
        m.put("75", "p");
        m.put("57", NetworkUtils.DELIMITER_COLON);
        m.put("82", "/");
        m.put("61", "d");
        m.put("70", ".");
        m.put("78", PAGE.MYLETV);
        m.put("64", "y");
        m.put("66", PAGE.LIVE);
        m.put("87", "e");
        m.put("69", PAGE.MYVIDEO);
        m.put("71", "c");
        m.put("79", "o");
        m.put("86", "r");
        m.put("83", "s");
        m.put(VType.FLV_1080P6M_3D, "u");
        m.put("32", PAGE.MYSHARE);
        m.put(VType.H265_FLV_800, "i");
        m.put("36", "?");
        m.put(VType.H265_FLV_1080P, SearchCriteria.EQ);
        m.put("51", "%");
        m.put("45", "&");
        m.put(VType.H265_FLV_1300, "g");
        m.put(VType.H265_FLV_720P, "n");
        m.put("85", "a");
        StringBuilder b = new StringBuilder();
        for (String tmp : arrs) {
            b.append((String) m.get(tmp));
        }
        return b.toString();
    }

    @SuppressLint({"AddJavascriptInterface"})
    private void findView() {
        this.mWebView = getWebView();
        this.mWebView.getSettings().setCacheMode(2);
        this.mWebView.addJavascriptInterface(new JavaScriptinterface(this, this.mWebView, null), "LetvJSBridge_For_Android");
        this.mWebView.setWebViewClient(new LetvWebViewClient());
        this.mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                ApkDownloadAsyncTask.downloadApk(InviteWebviewimpl.this, url, "Invite");
                InviteWebviewimpl.this.finish();
            }
        });
        LogInfo.log("+->", "loadURL--->>>" + this.loadUrl);
        LogInfo.log("+->", "baseUrl--->>>" + this.baseUrl);
    }

    public void onShareDialogShow(String text) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            if (this.shareDialogFragment == null) {
                LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_SHARE_INVITE_WEBVIEW_INIT));
                if (LeResponseMessage.checkResponseMessageValidity(response, WebViewInviteShareProtocol.class)) {
                    this.mInviteShareProtocol = (WebViewInviteShareProtocol) response.getData();
                }
            }
            if (!(this.shareDialogFragment == null || this.shareDialogFragment.getFragment() == null)) {
                this.shareDialogFragment.setShareText(text);
                if (!this.shareDialogFragment.getFragment().isAdded()) {
                    this.shareDialogFragment.getFragment().show(ft, "shareDialog");
                }
            }
        }
        this.web_flag = "";
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_iv) {
            finish();
        } else if (id == R.id.back_iv) {
            if (this.mWebView.canGoBack()) {
                LogInfo.log("+->", "mWebView-->>back" + this.mWebView.getUrl());
                WebBackForwardList list = this.mWebView.copyBackForwardList();
                if (list.getCurrentIndex() <= 0) {
                    this.pullDownUrlText.setText(String.format(getString(R.string.supplied_by), new Object[]{getUrlTitle(list.getItemAtIndex(0).getUrl())}));
                    this.titleView.setText(list.getItemAtIndex(0).getTitle());
                } else {
                    this.pullDownUrlText.setText(String.format(getString(R.string.supplied_by), new Object[]{getUrlTitle(list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl())}));
                    this.titleView.setText(list.getItemAtIndex(list.getCurrentIndex() - 1).getTitle());
                }
                this.mWebView.goBack();
                if (!this.close.isShown()) {
                    this.close.setVisibility(0);
                }
            } else if ("floatBallActive".equals(this.web_flag)) {
                finish();
            } else {
                finish();
            }
        }
        UIsUtils.hideSoftkeyboard(this);
    }

    private void _backEvent() {
        if (this.mWebView != null && this.mWebView.canGoBack()) {
            this.mWebView.stopLoading();
            LogInfo.log("+->", "mWebView-->>back" + this.mWebView.getUrl());
            WebBackForwardList list = this.mWebView.copyBackForwardList();
            if (list.getCurrentIndex() <= 0) {
                this.pullDownUrlText.setText(String.format(getString(R.string.supplied_by), new Object[]{getUrlTitle(list.getItemAtIndex(0).getUrl())}));
            } else {
                this.pullDownUrlText.setText(String.format(getString(R.string.supplied_by), new Object[]{getUrlTitle(list.getItemAtIndex(list.getCurrentIndex() - 1).getUrl())}));
                this.titleView.setText(list.getItemAtIndex(list.getCurrentIndex() - 1).getTitle());
            }
            this.mWebView.goBack();
            if (!this.close.isShown()) {
                this.close.setVisibility(0);
            }
        } else if ("floatBallActive".equals(this.web_flag)) {
            finish();
        } else {
            finish();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            getWindow().clearFlags(ViewCompat.MEASURED_STATE_TOO_SMALL);
            if (this.mWebView != null) {
                ViewGroup vg = (ViewGroup) this.mWebView.getParent();
                if (vg != null) {
                    vg.removeView(this.mWebView);
                }
                this.mWebView.stopLoading();
                this.mWebView.setVisibility(8);
                this.mWebView.removeAllViews();
                this.mWebView.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == 4) {
            _backEvent();
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
        if (!(this.shareDialogFragment == null || this.shareDialogFragment.getFragment() == null || !this.shareDialogFragment.getFragment().isAdded())) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.remove(this.shareDialogFragment.getFragment());
            tx.commit();
        }
        getSupportFragmentManager().executePendingTransactions();
        callHiddenWebViewMethod("onPause");
    }

    protected void onResume() {
        super.onResume();
        callHiddenWebViewMethod("onResume");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!TextUtils.isEmpty(this.web_flag) && "floatBallActive".equals(this.web_flag)) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    InviteWebviewimpl.this.mWebView.reload();
                }
            }, 1000);
        }
    }

    private void callHiddenWebViewMethod(String name) {
        if (this.mWebView != null) {
            try {
                WebView.class.getMethod(name, new Class[0]).invoke(this.mWebView, new Object[0]);
            } catch (Exception e) {
            }
        }
    }
}
