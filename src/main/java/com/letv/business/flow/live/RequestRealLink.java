package com.letv.business.flow.live;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.letv.business.flow.live.LiveFlowCallback.AsyncCallback;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.ExpireTimeBean;
import com.letv.core.bean.LiveStreamBean;
import com.letv.core.bean.RealLink;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.VolleyResult;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.ExpireTimeParser;
import com.letv.core.parser.LiveRealParser;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.PlayUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.pp.func.CdeHelper;
import com.letv.pp.url.PlayUrl;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestRealLink {
    private long etime;
    private AsyncCallback mCallback;
    private Context mContext;
    private Handler mHandler;
    private boolean mHasError = false;
    private boolean mIsP2PMode;
    private boolean mIsWo3GUser;
    private String mLinkShellUrl = "";
    private LiveStreamBean mLiveStream;
    private String mP2PUrl;
    private String mUUid;
    private long stime;

    public RequestRealLink(Context context, LiveStreamBean liveStream, boolean isWo3GUser, boolean isP2PMode, String uuid, AsyncCallback callback) {
        this.mContext = context;
        this.mLiveStream = liveStream;
        this.mIsWo3GUser = isWo3GUser;
        this.mIsP2PMode = isP2PMode;
        this.mCallback = callback;
        this.mUUid = uuid;
        this.mHandler = new Handler();
        PlayLiveFlow.LogAddInfo("开始请求真实地址", "mIsWo3GUser=" + this.mIsWo3GUser + ",mIsP2PMode=" + this.mIsP2PMode + ",mUUid=" + this.mUUid);
    }

    public void start() {
        PlayLiveFlow.LogAddInfo("开始请求真实地址", "ExpireTimeBean.getTm().hasInit()=" + ExpireTimeBean.getTm().hasInit());
        if (ExpireTimeBean.getTm().hasInit()) {
            requestCdeOverLoad();
            return;
        }
        String time_url = LetvUrlMaker.getExpireTimeUrl();
        PlayLiveFlow.LogAddInfo("开始请求真实地址 请求过期时间", "time_url=" + time_url);
        new LetvRequest(ExpireTimeBean.class).setUrl(time_url).setCache(new VolleyNoCache()).setParser(new ExpireTimeParser()).setCallback(new SimpleResponse<ExpireTimeBean>() {
            public void onNetworkResponse(VolleyRequest<ExpireTimeBean> volleyRequest, ExpireTimeBean result, DataHull hull, NetworkResponseState state) {
                RequestRealLink.this.requestCdeOverLoad();
            }

            public void onErrorReport(VolleyRequest<ExpireTimeBean> volleyRequest, String errorInfo) {
                PlayLiveFlow.LogAddInfo("开始请求真实地址 请求过期时间失败", "errorInfo=" + errorInfo);
                String str = errorInfo;
                String str2 = null;
                String str3 = null;
                String str4 = null;
                String str5 = null;
                DataStatistics.getInstance().sendErrorInfo(RequestRealLink.this.mContext, "0", "0", LetvErrorCode.GET_LIVE_EXPIRE_TIME_ERROR, null, str, str2, str3, str4, str5, "pl", RequestRealLink.this.mUUid);
            }
        }).add();
    }

    public String syncGetPlayUrl(Device device) {
        String encryptUrl = getEncryptUrl();
        if (TextUtils.isEmpty(encryptUrl)) {
            return null;
        }
        if (PreferencesManager.getInstance().isLinkShellSwitch()) {
            String linkShellUrl = PlayUtils.getURLFromLinkShell(encryptUrl, this.mUUid);
            if (!TextUtils.isEmpty(linkShellUrl)) {
                encryptUrl = linkShellUrl;
            }
        }
        VolleyResult<RealLink> result = new LetvRequest().setUrl(encryptUrl).setCache(new VolleyNoCache()).setTag(BasePlayLiveFlow.REQUEST_REAL_LINK_SYNC).setParser(new LiveRealParser()).setMaxRetries(2).setRequestType(RequestManner.NETWORK_ONLY).syncFetch();
        if (result == null || result.networkState != NetworkResponseState.SUCCESS) {
            return null;
        }
        RealLink realLink = result.result;
        if (realLink == null || TextUtils.isEmpty(realLink.location)) {
            return null;
        }
        return realLink.location;
    }

    private void requestCdeOverLoad() {
        PlayLiveFlow.LogAddInfo("开始请求cde过载保护", "");
        new Thread(new Runnable() {
            public void run() {
                String encryptUrl = RequestRealLink.this.getEncryptUrl();
                if (!TextUtils.isEmpty(encryptUrl)) {
                    int errCode = -1;
                    if (!RequestRealLink.this.mIsWo3GUser && RequestRealLink.this.mIsP2PMode) {
                        String str = null;
                        CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
                        if (!(cdeHelper == null || NetworkUtils.checkIsProxyNet(RequestRealLink.this.mContext))) {
                            String json = cdeHelper.getPlayUrlSync(encryptUrl);
                            PlayLiveFlow.LogAddInfo("开始请求cde过载保护 请求结果", "json=" + json);
                            if (!TextUtils.isEmpty(json)) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    str = object.getString("playUrl");
                                    errCode = object.getInt("errCode");
                                } catch (JSONException e) {
                                }
                                PlayLiveFlow.LogAddInfo("开始请求cde过载保护 请求结果", "p2pUrl=" + str);
                                if (TextUtils.isEmpty(str)) {
                                    str = RequestRealLink.this.getP2pUrl(encryptUrl);
                                }
                                if (!TextUtils.isEmpty(str)) {
                                    RequestRealLink.this.mP2PUrl = str;
                                    final RealLink p2pDataEntity = new RealLink();
                                    RequestRealLink.this.setOverLoadStaus(p2pDataEntity, errCode);
                                    p2pDataEntity.location = str;
                                    PlayLiveFlow.LogAddInfo("走P2P播放逻辑", "mP2PUrl=" + RequestRealLink.this.mP2PUrl);
                                    RequestRealLink.this.mHandler.post(new Runnable() {
                                        public void run() {
                                            RequestRealLink.this.mCallback.onNetworkResponse(NetworkResponseState.SUCCESS, p2pDataEntity, 0);
                                        }
                                    });
                                    return;
                                }
                            }
                        }
                    }
                    RequestRealLink.this.requestReaUrl(encryptUrl, errCode);
                }
            }
        }).start();
    }

    private void requestReaUrl(String encryptUrl, final int errorCode) {
        PlayLiveFlow.LogAddInfo("开始请求真实地址", "encryptUrl=" + encryptUrl);
        if (PreferencesManager.getInstance().isLinkShellSwitch()) {
            String linkShellUrl = PlayUtils.getURLFromLinkShell(encryptUrl, this.mUUid);
            if (!TextUtils.isEmpty(linkShellUrl)) {
                encryptUrl = linkShellUrl;
                PlayLiveFlow.LogAddInfo("开始请求真实地址 LinkShell返回", "encryptUrl=" + encryptUrl);
            }
        }
        this.stime = System.currentTimeMillis();
        Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_REAL_LINK);
        new LetvRequest().setUrl(encryptUrl).setCache(new VolleyNoCache()).setTag(BasePlayLiveFlow.REQUEST_REAL_LINK).setParser(new LiveRealParser()).setCallback(new SimpleResponse<RealLink>() {
            public void onNetworkResponse(VolleyRequest<RealLink> volleyRequest, final RealLink result, DataHull hull, final NetworkResponseState state) {
                PlayLiveFlow.LogAddInfo("开始请求真实地址 请求结果", "errorCode=" + errorCode);
                RequestRealLink.this.etime = System.currentTimeMillis();
                RequestRealLink.this.setOverLoadStaus(result, errorCode);
                RequestRealLink.this.mHandler.post(new Runnable() {
                    public void run() {
                        RequestRealLink.this.mCallback.onNetworkResponse(state, result, RequestRealLink.this.etime - RequestRealLink.this.stime);
                    }
                });
            }

            public void onErrorReport(VolleyRequest<RealLink> volleyRequest, String errorInfo) {
                PlayLiveFlow.LogAddInfo("开始请求真实地址 请求失败", "errorInfo=" + errorInfo);
                String str = errorInfo;
                String str2 = null;
                String str3 = null;
                String str4 = null;
                String str5 = null;
                DataStatistics.getInstance().sendErrorInfo(RequestRealLink.this.mContext, "0", "0", LetvErrorCode.REQUEST_REAL_LINK_ERROR, null, str, str2, str3, str4, str5, "pl", RequestRealLink.this.mUUid);
            }
        }).add();
    }

    private String getEncryptUrl() {
        String tm = null;
        if (ExpireTimeBean.getTm().hasInit()) {
            tm = String.valueOf(ExpireTimeBean.getTm().getCurServerTime());
        } else {
            this.mHasError = true;
        }
        if (TextUtils.isEmpty(tm) || this.mLiveStream == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(replaceTm(tm, this.mLiveStream.getLiveUrl()) + "&key=" + LetvTools.generateLiveEncryptKey(this.mLiveStream.getStreamId(), tm));
        builder.append("&");
        builder.append("expect");
        builder.append(SearchCriteria.EQ);
        builder.append("3");
        builder.append("&");
        builder.append("format");
        builder.append(SearchCriteria.EQ);
        builder.append("1");
        LogInfo.log("live", "请求真实地址 参数 = " + builder.toString());
        return builder.toString();
    }

    private void setOverLoadStaus(RealLink realLink, int errCode) {
        if (realLink != null) {
            if (errCode == PlayConstant.OVERLOAD_PROTECTION_OVERLOAD) {
                realLink.overloadProtectionState = OverloadProtectionState.CUTOUT;
            } else if (errCode == PlayConstant.OVERLOAD_PROTECTION_DOWNLOAD_STREAM) {
                realLink.overloadProtectionState = OverloadProtectionState.DOWNLOAD_STREAM;
            }
        }
    }

    public String replaceTm(String tm, String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.indexOf("tm=") == -1) {
            return url + "&tm=" + tm;
        }
        int startPos = 0;
        while (startPos >= 0 && startPos < url.length()) {
            int posT = url.indexOf("tm=", startPos);
            if (posT == -1) {
                break;
            }
            int posE = url.indexOf("&", posT) == -1 ? url.length() : url.indexOf("&", posT);
            url = url.replace(url.substring(posT, posE), "tm=" + tm);
            startPos = posE;
        }
        return url;
    }

    private String getP2pUrl(String ddUrl) {
        CdeHelper mCdeHelper = BaseApplication.getInstance().getCdeHelper();
        if (this.mIsWo3GUser || !this.mIsP2PMode || mCdeHelper == null) {
            return null;
        }
        PlayUrl cc = new PlayUrl(mCdeHelper.getServicePort(), p2pFormat(ddUrl), "", "");
        PlayLiveFlow.LogAddInfo("获取p2purl", "p2pUrl=" + cc.getPlay());
        return cc.getPlay();
    }

    private String p2pFormat(String ddUrl) {
        LogInfo.log("haitian", "live requestRealLink unLinkShellUrl=" + ddUrl);
        if (!PreferencesManager.getInstance().isLinkShellSwitch()) {
            return ddUrl;
        }
        this.mLinkShellUrl = PlayUtils.getURLFromLinkShell(ddUrl, this.mUUid);
        if (TextUtils.isEmpty(this.mLinkShellUrl)) {
            return ddUrl;
        }
        ddUrl = this.mLinkShellUrl;
        LogInfo.log("clf", "live requestRealLink LinkShellUrl=" + ddUrl);
        return ddUrl;
    }

    public String getP2PUrl() {
        return this.mP2PUrl;
    }

    public String getLinkShellUrl() {
        return this.mLinkShellUrl;
    }
}
