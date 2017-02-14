package com.letv.android.client.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.IWoFlowManager.ORDER_STATE;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils.UnicomDialogClickListener;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class LetvSearchWebViewActivity extends LetvBaseWebViewActivity implements OnClickListener {
    private boolean mIsLoading = false;
    private WebView mWebView;

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            LetvSearchWebViewActivity.this.flag = false;
            LetvSearchWebViewActivity.this.root.netError(false);
            LetvSearchWebViewActivity.this.progressBar.setVisibility(8);
            LetvSearchWebViewActivity.this.mWebView.setNetworkAvailable(true);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LetvSearchWebViewActivity.this.mIsLoading = true;
            super.onPageStarted(view, url, favicon);
            LogInfo.log("wlx||LetvWebViewClient", "onPageStarted");
        }

        public void onPageFinished(WebView view, String url) {
            LetvSearchWebViewActivity.this.mIsLoading = false;
            super.onPageFinished(view, url);
            LogInfo.log("wlx||LetvWebViewClient", "onPageFinished");
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogInfo.log("zhuqiao", "shouldOverrideUrlLoading:" + url);
            if (TextUtils.isEmpty(url)) {
                return true;
            }
            if (!url.contains("://") || url.startsWith(IDataSource.SCHEME_HTTP_TAG)) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            try {
                LetvSearchWebViewActivity.this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            } catch (Exception e) {
                return true;
            }
        }
    }

    public static void launch(final Context context, final String url, final String title) {
        if (!LetvUtils.isGooglePlay() && !TextUtils.isEmpty(url)) {
            ORDER_STATE state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(context);
            if (NetworkUtils.isUnicom3G(false) && (state == ORDER_STATE.ORDER || state == ORDER_STATE.UNORDER)) {
                UnicomWoFlowDialogUtils.woWebViewNotPlayDialog(context, new UnicomDialogClickListener() {
                    public void onConfirm() {
                        String time = LetvUtils.timeClockString("yyyyMMddHHmmss");
                        Intent intent = new Intent(context, LetvSearchWebViewActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra(LetvWebViewActivityConfig.LOAD_TYPE, title);
                        context.startActivity(intent);
                    }

                    public void onCancel() {
                    }

                    public void onResponse(boolean isShow) {
                    }
                });
                return;
            }
            Intent intent = new Intent(context, LetvSearchWebViewActivity.class);
            intent.putExtra("url", url);
            intent.putExtra(LetvWebViewActivityConfig.LOAD_TYPE, title);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        LogInfo.log("clf", "LetvSearchWebViewActivity");
        setNeedStatistics(true);
        super.onCreate(savedInstanceState);
        this.mWebView = getWebView();
        if (this.mWebView != null) {
            this.mWebView.getSettings().setUserAgentString(LetvUtils.createUA(this.mWebView.getSettings().getUserAgentString(), this));
            this.mWebView.setWebViewClient(new LetvWebViewClient());
            this.mWebView.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    try {
                        LetvSearchWebViewActivity.this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    protected String setBaseUrl() {
        return null;
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    protected void onResume() {
        super.onResume();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.back_iv) {
            if (getResources().getString(R.string.letv_search_name).equals(this.loadType)) {
                StringBuilder sb = new StringBuilder();
                sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.searchResultPage).append("&fl=d21&wz=1");
                DataStatistics.getInstance().sendActionInfo(this, "0", "0", LetvUtils.getPcode(), "0", sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
            }
            UIsUtils.hideSoftkeyboard(this);
        }
        super.onClick(v);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 1 && getTopBar() != null) {
            getTopBar().setVisibility(0);
        }
        if (newConfig.orientation == 2 && getTopBar() != null) {
            getTopBar().setVisibility(8);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
