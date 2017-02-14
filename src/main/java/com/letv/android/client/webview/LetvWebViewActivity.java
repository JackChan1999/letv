package com.letv.android.client.webview;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.PayFailedActivityConfig;
import com.letv.android.client.commonlib.config.PaySucceedActivityConfig;
import com.letv.android.client.commonlib.popdialog.ApkDownloadAsyncTask;
import com.letv.android.client.commonlib.view.LoadingDialog;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.IWoFlowManager.ORDER_STATE;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.TopicDetailInfoListBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.VipProductContant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AlbumInfoParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.lemallsdk.util.Constants;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.net.URISyntaxException;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class LetvWebViewActivity extends LetvBaseWebViewActivity implements OnClickListener {
    private static String titles = "";
    private boolean firstLoad = true;
    private LoadingDialog mLoadingDialog;
    private WebView mWebView;

    private class LetvWebViewClient extends WebViewClient {
        private LetvWebViewClient() {
        }

        @TargetApi(21)
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            LogInfo.log("ZSM sFromNative shouldInterceptRequest == " + NativeWebViewUtils.getInstance().getFromNative() + " request == " + request.getUrl().toString());
            if (!NativeWebViewUtils.getInstance().getFromNative()) {
                return super.shouldInterceptRequest(view, request);
            }
            super.shouldInterceptRequest(view, request);
            return NativeWebViewUtils.getInstance().getResource(request.getUrl().toString());
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogInfo.log("ZSM sFromNative shouldOverrideUrlLoading  == " + NativeWebViewUtils.getInstance().getFromNative());
            if (NativeWebViewUtils.getInstance().getFromNative()) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            LogInfo.log("ZSM letvWebview shouldOverrideUrlLoading url == " + url);
            if (url.contains(VipProductContant.ACTION_HONGKONG_VIP_PAY)) {
                if (url.contains(Constants.CALLBACK_SUCCESS)) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new PaySucceedActivityConfig(LetvWebViewActivity.this.mActivity).create(url)));
                    LetvWebViewActivity.this.mActivity.finish();
                } else {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new PayFailedActivityConfig(LetvWebViewActivity.this.mActivity).create()));
                    LetvWebViewActivity.this.mActivity.finish();
                }
                return super.shouldOverrideUrlLoading(view, url);
            } else if (url.startsWith("letvclient://")) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent it = new Intent();
                    it.setData(uri);
                    LetvWebViewActivity.this.startActivity(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                int jumpType = 0;
                if (TextUtils.equals(LetvWebViewActivity.this.baseUrl, url) || TextUtils.equals(LetvWebViewActivity.this.baseUrl.replace(".letv.com", ".le.com"), url.replace(".letv.com", ".le.com"))) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                if (url.startsWith("intent://")) {
                    try {
                        LetvWebViewActivity.this.startActivity(Intent.parseUri(url, 1));
                    } catch (URISyntaxException e2) {
                        e2.printStackTrace();
                        LogInfo.log("wlx", e2.getMessage());
                    } catch (ActivityNotFoundException e3) {
                        e3.printStackTrace();
                        LogInfo.log("wlx", e3.getMessage());
                    }
                    return true;
                }
                LetvWebViewActivity.this.pullDownUrlText.setText(LetvWebViewActivity.this.getString(R.string.supplied_by, new Object[]{LetvWebViewActivity.this.getUrlTitle(url)}));
                LogInfo.log("+-->", "LetvWebViewActivity---------------->>>>>>>>>>>>>");
                String last_should_url = "";
                try {
                    if (LetvWebViewActivity.this.firstLoad) {
                        LetvWebViewActivity.this.firstLoad = false;
                    }
                    boolean isPay = url.toLowerCase().contains("bosshaspay=pay");
                    LogInfo.log("haitian", "topicWeburl = " + url);
                    String u;
                    if (!url.toLowerCase().contains("vplay_")) {
                        int index = url.indexOf("?");
                        if (index > 0 || !LetvUtils.judgeInnerUrl(url)) {
                            u = null;
                            if (url.contains("vtype=mp4")) {
                                jumpType = 0;
                            } else if (url.contains("zid=")) {
                                jumpType = 1;
                            }
                            if (index > 0) {
                                if (jumpType == 1) {
                                    u = url.substring(url.indexOf("?") + 1);
                                } else {
                                    u = url.substring(0, url.indexOf("?"));
                                }
                            }
                            if (!(TextUtils.isEmpty(u) && LetvUtils.judgeInnerUrl(url))) {
                                LogInfo.log("clf", "jumpType == 0");
                                if (jumpType == 0) {
                                    String temp_url;
                                    ORDER_STATE state;
                                    if (index > 0) {
                                        if (".mp4".equals(u.substring(u.lastIndexOf("."), u.length())) && url.contains("vtype=mp4")) {
                                            view.stopLoading();
                                            if (!LetvWebViewActivity.this.isFinish) {
                                                temp_url = url;
                                                state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(LetvWebViewActivity.this, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(LetvWebViewActivity.this);
                                                if (NetworkUtils.isUnicom3G(false) && (state == ORDER_STATE.ORDER || state == ORDER_STATE.UNORDER)) {
                                                    UnicomWoFlowDialogUtils.woWebViewNotPlayDialog(LetvWebViewActivity.this, new 1(this, temp_url));
                                                } else {
                                                    Intent intent = new Intent("android.intent.action.VIEW");
                                                    intent.setDataAndType(Uri.parse(temp_url), "video/mp4");
                                                    intent.putExtra("android.intent.extra.screenOrientation", 0);
                                                    LetvWebViewActivity.this.startActivity(intent);
                                                }
                                            }
                                            LogInfo.log("lxx", "5555");
                                            last_should_url = url;
                                            return false;
                                        }
                                    }
                                    last_should_url = LetvWebViewActivity.this.mWebView.copyBackForwardList().getCurrentItem().getUrl();
                                    LogInfo.log("clf", "....judgeInnerUrl(url)=" + LetvUtils.judgeInnerUrl(url));
                                    LogInfo.log("clf", "....last_should_url=" + last_should_url);
                                    LogInfo.log("clf", "....judgeInnerUrl(last_should_url)=" + LetvUtils.judgeInnerUrl(last_should_url));
                                    if ((LetvUtils.judgeOutSideUrl(url) || !(LetvUtils.judgeInnerUrl(url) || !LetvUtils.judgeInnerUrl(last_should_url) || LetvUtils.judgeOutSideUrl(last_should_url))) && !LetvWebViewActivity.this.isFinish) {
                                        temp_url = url;
                                        state = ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(LetvWebViewActivity.this, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).getUserOrderInfo(LetvWebViewActivity.this);
                                        if (NetworkUtils.isUnicom3G(false) && (state == ORDER_STATE.ORDER || state == ORDER_STATE.UNORDER)) {
                                            view.stopLoading();
                                            UnicomWoFlowDialogUtils.woWebViewNotPlayDialog(LetvWebViewActivity.this, new 2(this, view, temp_url));
                                            LogInfo.log("lxx", "5555");
                                            last_should_url = url;
                                            return false;
                                        }
                                    }
                                } else if (jumpType == 1) {
                                    view.stopLoading();
                                    u = u.toLowerCase();
                                    if (u.contains("zid=")) {
                                        requestTopicPlay(u);
                                        LogInfo.log("lxx", "5555");
                                        last_should_url = url;
                                        return true;
                                    }
                                }
                            }
                        }
                    } else if (url.lastIndexOf("/") > 0) {
                        u = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                        if (u.contains("vplay_")) {
                            String vplayId = u.substring(u.lastIndexOf(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) + 1, u.length());
                            if (!(LetvWebViewActivity.this.isFinish || TextUtils.isEmpty(vplayId))) {
                                view.stopLoading();
                                LetvWebViewActivity.requestAlbum(vplayId, LetvWebViewActivity.this.mActivity, isPay);
                                return true;
                            }
                        }
                    }
                    LogInfo.log("lxx", "5555");
                    last_should_url = url;
                } catch (Exception e4) {
                    e4.printStackTrace();
                } finally {
                    LogInfo.log("lxx", "5555");
                    last_should_url = url;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        private void requestTopicPlay(String userInfo) {
            String vid = null;
            String pid = null;
            String zid = null;
            for (String item : userInfo.split("&")) {
                if (item.contains(SearchCriteria.EQ)) {
                    if (item.contains(PlayConstant.ZID)) {
                        zid = item.substring(item.indexOf(SearchCriteria.EQ) + 1);
                    } else if (item.contains("pid")) {
                        pid = item.substring(item.indexOf(SearchCriteria.EQ) + 1);
                    } else if (item.contains("vid")) {
                        vid = item.substring(item.indexOf(SearchCriteria.EQ) + 1);
                    }
                }
            }
            if (TextUtils.isEmpty(zid)) {
                ToastUtils.showToast(LetvWebViewActivity.this.mLoadingDialog.getWindow().getDecorView().getContext(), R.string.topic_player_info);
                return;
            }
            new LetvRequest(TopicDetailInfoListBean.class).setOnPreExecuteListener(new 4(this)).setUrl(MediaAssetApi.getInstance().getTopicDeatil(zid, null)).setCache(new VolleyNoCache()).setCallback(new 3(this, zid, pid, vid)).add();
        }

        private void toTopic(TopicDetailInfoListBean topicInfo, String zid, String pid, String vid) {
            cancelDialog();
            if (topicInfo != null && topicInfo.subject != null) {
                LogInfo.log(LetvWebViewActivity.this.getActivityName() + "||wlx", "type = " + topicInfo.subject.getType() + " zid =" + Long.parseLong(zid) + " pid =" + Long.parseLong(pid) + " vid =" + Long.parseLong(vid));
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(LetvWebViewActivity.this.mActivity).createTopic(BaseTypeUtils.stol(zid), (long) BaseTypeUtils.stoi(pid), (long) BaseTypeUtils.stoi(vid), 14)));
            }
        }

        private void showDialog() {
            LogInfo.log(LetvWebViewActivity.this.getActivityName() + "||wlx", "showDialog");
            if (LetvWebViewActivity.this.mLoadingDialog != null && LetvWebViewActivity.this.mLoadingDialog.isShowing()) {
                return;
            }
            if (!LetvWebViewActivity.this.mActivity.isFinishing() || LetvWebViewActivity.this.mActivity.isRestricted()) {
                try {
                    LetvWebViewActivity.this.mLoadingDialog = new LoadingDialog(LetvWebViewActivity.this.mActivity, R.string.dialog_loading);
                    LetvWebViewActivity.this.mLoadingDialog.setCancelable(true);
                    LetvWebViewActivity.this.mLoadingDialog.show();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        private void cancelDialog() {
            if (LetvWebViewActivity.this.mLoadingDialog != null && LetvWebViewActivity.this.mLoadingDialog.isShowing()) {
                try {
                    LetvWebViewActivity.this.mLoadingDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            LogInfo.log("LM", "onReceivedSslError");
            if (LetvWebViewActivity.this.baseUrl.contains(LetvUtils.WEB_INNER_FLAG) || !LetvConfig.getPcode().equals("010110016")) {
                handler.proceed();
            } else {
                handler.cancel();
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LetvWebViewActivity.this.mIsLoading = true;
            LogInfo.log("wlx", "onPageStart");
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LetvWebViewActivity.this.mIsLoading = false;
            if (!TextUtils.isEmpty(LetvWebViewActivity.this.autoPlayJs)) {
                view.loadUrl(LetvWebViewActivity.this.autoPlayJs);
            }
            LogInfo.log("wlx", "onPageFinished");
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            LogInfo.log("lxx", "errorCode: " + errorCode + ",description: " + description + ",failingUrl: " + failingUrl);
            if (errorCode != -10) {
                LetvWebViewActivity.this.flag = false;
                LetvWebViewActivity.this.root.netError(false);
                LetvWebViewActivity.this.progressBar.setVisibility(8);
                LetvWebViewActivity.this.mWebView.setNetworkAvailable(true);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        LogInfo.log("lxx", "LetvWebViewActivity onCreate");
        setNeedStatistics(true);
        super.onCreate(savedInstanceState);
        this.mWebView = getWebView();
        if (TextUtils.isEmpty(this.loadType) || !this.loadType.equals(LetvUtils.getString(R.string.letv_protol_name))) {
            this.mWebView.getSettings().setJavaScriptEnabled(true);
        } else {
            this.mWebView.getSettings().setJavaScriptEnabled(false);
        }
        this.mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                LogInfo.log("LXF", LetvWebViewActivity.this.loadType + "<<-----------download url------------>>" + url);
                LogInfo.log("lxx", "DownloadListener,url: " + url + ",userAgent: " + userAgent + ",mimetype: " + mimetype + ",contentDisposition: " + contentDisposition + ",contentLength: " + contentLength);
                ApkDownloadAsyncTask.downloadApk(LetvWebViewActivity.this, url, LetvWebViewActivity.this.loadType);
                LetvWebViewActivity.this.finish();
            }
        });
        this.mWebView.setWebViewClient(new LetvWebViewClient());
    }

    public static boolean launchWebJudgeUrl(Context context, String url) {
        if (TextUtils.isEmpty(url) || !url.toLowerCase().contains("vplay_") || url.lastIndexOf("/") <= 0) {
            return false;
        }
        String u = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        if (!u.contains("vplay_")) {
            return false;
        }
        String vplayId = u.substring(u.lastIndexOf(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) + 1, u.length());
        if (TextUtils.isEmpty(vplayId)) {
            return false;
        }
        requestAlbum(vplayId, context, false);
        return true;
    }

    protected String setBaseUrl() {
        return null;
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.back_iv) {
            if (!TextUtils.isEmpty(this.loadType) && this.loadType.equals(getResources().getString(R.string.letv_search_name))) {
                StringBuilder sb = new StringBuilder();
                sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.searchResultPage).append("&fl=d21&wz=1");
                DataStatistics.getInstance().sendActionInfo(this, "0", "0", LetvUtils.getPcode(), "0", sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
            }
            UIsUtils.hideSoftkeyboard(this);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!TextUtils.isEmpty(titles) && titles.equals(LetvUtils.getString(R.string.qiangdui))) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    LetvWebViewActivity.this.mWebView.reload();
                }
            }, 1000);
        }
    }

    private static void requestAlbum(final String vid, final Context context, final boolean isPay) {
        new LetvRequest(AlbumInfo.class).setUrl(MediaAssetApi.getInstance().requestGetAlbumByIdUrl(vid)).setParser(new AlbumInfoParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<AlbumInfo>() {
            public void onNetworkResponse(VolleyRequest<AlbumInfo> volleyRequest, AlbumInfo result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS) {
                    LogInfo.log("LetvWebviewActivity||wlx", "播放视频");
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).createForWebPay(result.type == 1 ? result.pid : 0, BaseTypeUtils.stol(vid), 0, isPay)));
                    return;
                }
                LetvWebViewActivity.onError(state, context);
            }
        }).add();
    }

    private static void onError(NetworkResponseState state, Context context) {
        switch (state) {
            case NETWORK_NOT_AVAILABLE:
            case NETWORK_ERROR:
                ToastUtils.showToast(context, R.string.net_no);
                return;
            case RESULT_ERROR:
                ToastUtils.showToast(context, R.string.get_data_error);
                return;
            default:
                return;
        }
    }
}
