package com.letv.business.flow.album.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.android.client.business.R;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.album.AlbumPlayBaseFlow.PlayErrorState;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlow.FlowStyle;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.AlbumPlayFlowObservable.PlayErrorCodeNotify;
import com.letv.business.flow.album.model.AlbumUrl;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.DDUrlsResultBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.RealPlayUrlInfoBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyRequest.RequestPriority;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDbCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.RealPlayUrlInfoParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.PlayUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.marlindrm.bean.DrmResultBean;
import com.letv.marlindrm.constants.DrmResultCodeEnum;
import com.letv.marlindrm.intf.DrmDealCallBackInf;
import com.letv.marlindrm.manager.DrmManager;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.pp.func.CdeHelper;
import com.letv.pp.utils.LibraryHelper;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AlbumFlowRequestUrlController {
    protected boolean mCdeEnable = true;
    protected final Context mContext;
    protected final AlbumPlayFlow mFlow;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected boolean mIsCombine = false;

    protected enum VideoPlayChannel {
        CDE,
        CDN,
        DRM
    }

    public AlbumFlowRequestUrlController(Context context, AlbumPlayFlow flow) {
        this.mContext = context;
        this.mFlow = flow;
        if (this.mFlow == null) {
            throw new NullPointerException("AlbumFlowController param is null!");
        }
    }

    public void startRequestRealPlayUrl() {
        boolean z = true;
        if (this.mFlow.mCurrentPlayingVideo != null) {
            if (!onPreRequestRealPlayUrl()) {
                this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", true, "0002-8"));
                this.mFlow.addPlayInfo("onPreRequestRealPlayUrl", "失败");
            } else if (this.mFlow.mDdUrlsResult == null) {
                this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", true, "0002-8"));
                this.mFlow.addPlayInfo("调度地址为空", "");
            } else {
                boolean z2 = !this.mFlow.mIsWo3GUser && NetworkUtils.getNetworkType() == 1 && PreferencesManager.getInstance().getUtp() && this.mFlow.mVideoType == VideoType.Normal && BaseApplication.getInstance().getCdeHelper() != null && !NetworkUtils.checkIsProxyNet(this.mContext) && PreferencesManager.getInstance().isLinkShellSwitch() && TextUtils.equals(BaseApplication.getInstance().getVideoFormat(), "ios") && !this.mFlow.mIsDownloadFile;
                this.mCdeEnable = z2;
                this.mFlow.mIsUseCde = this.mCdeEnable;
                AlbumPlayFlow albumPlayFlow = this.mFlow;
                StringBuilder append = new StringBuilder().append("isWo3GUser:").append(this.mFlow.mIsWo3GUser).append(";isWifi:");
                if (NetworkUtils.getNetworkType() == 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                append = append.append(z2).append(";upt open:").append(PreferencesManager.getInstance().getUtp()).append(";video type is normal:");
                if (this.mFlow.mVideoType == VideoType.Normal) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                StringBuilder append2 = append.append(z2).append(";cdeIsNull:");
                if (BaseApplication.getInstance().getCdeHelper() != null) {
                    z = false;
                }
                albumPlayFlow.addPlayInfo(append2.append(z).append(";proxy:").append(NetworkUtils.checkIsProxyNet(this.mContext)).append(";kaiguan:").append(PreferencesManager.getInstance().isLinkShellSwitch()).append(";format:").append(BaseApplication.getInstance().getVideoFormat()).toString(), ";isDownloadFile:" + this.mFlow.mIsDownloadFile);
                fetchDispatchUrl(false);
            }
        }
    }

    public boolean fetchDispatchUrl(boolean isRetry) {
        if (this.mFlow == null || this.mFlow.mDdUrlsResult == null) {
            return false;
        }
        String[] arr = this.mFlow.mDdUrlsResult.poll();
        if (arr == null) {
            if (isRetry) {
                this.mFlow.addPlayInfo("码流重试结束", "无可用码流");
            } else {
                this.mFlow.addPlayInfo("调度地址为空", "");
            }
            this.mFlow.mPlayInfo.mType18 = System.currentTimeMillis() - this.mFlow.mPlayInfo.mType18;
            return false;
        }
        this.mFlow.mAlbumUrl.dispatchName = arr[0];
        this.mFlow.mAlbumUrl.dispatchUrl = arr[1];
        if (isRetry) {
            this.mFlow.addPlayInfo("码流重试", arr[0] + "|" + arr[1]);
        } else {
            this.mFlow.addPlayInfo("码流", arr[0] + "|" + arr[1]);
        }
        if (!BaseApplication.getInstance().isCdeStarting()) {
            AlbumPlayFlow albumPlayFlow = this.mFlow;
            this.mCdeEnable = false;
            albumPlayFlow.mIsUseCde = false;
            this.mFlow.addPlayInfo(LibraryHelper.LIB_NAME_CDE, "服务不可用,重启");
            BaseApplication.getInstance().startCde();
        }
        this.mFlow.mPlayInfo.mType18 = System.currentTimeMillis() - this.mFlow.mPlayInfo.mType18;
        doRequest(isRetry);
        return true;
    }

    protected void doRequest(boolean isRetry) {
    }

    protected boolean onPreRequestRealPlayUrl() {
        VideoFileBean videoFile = this.mFlow.mVideoFile;
        if (videoFile == null) {
            return false;
        }
        boolean z;
        int playLevel = PreferencesManager.getInstance().getPlayLevel();
        int currentState = NetworkUtils.getNetworkType();
        if (this.mFlow.mFlowStyle == FlowStyle.Album) {
            this.mFlow.mShouldDeclineStream = videoFile.isDeclineStream;
            int memoryPlayLevel = BaseApplication.getInstance().getMemoryPlayLevel();
            if (this.mFlow.mShouldDeclineStream && !BaseApplication.getInstance().isSettingPlayLevel()) {
                playLevel = memoryPlayLevel == -1 ? videoFile.getDeclineStreamLevel() : memoryPlayLevel;
            }
        }
        if (BaseApplication.sIsChangeStream && this.mFlow.mVideoType == VideoType.Normal && (currentState == 2 || currentState == 3)) {
            LogInfo.log("zhuqiao", "第一次进入客户端，非wifi网络需要降码流");
            int supportLevel = BaseApplication.getInstance().getSuppportTssLevel();
            if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 4) != null && supportLevel >= 6) {
                playLevel = 4;
            }
            if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 3) != null && supportLevel >= 4) {
                playLevel = 3;
            }
            if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 2) != null && supportLevel >= 3) {
                playLevel = 2;
            }
            if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 1) != null) {
                playLevel = 1;
            }
            if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 0) != null && supportLevel >= 0) {
                playLevel = 0;
            }
        }
        LogInfo.log("zhuqiao", "curr playLevel:" + playLevel);
        if (this.mFlow.mCurrentPlayingVideo.pay == 1) {
            z = true;
        } else {
            z = false;
        }
        DDUrlsResultBean ddUrlsResultBean = PlayUtils.getDDUrls(videoFile, playLevel, z, this.mFlow.mVideoType);
        if (ddUrlsResultBean != null) {
            this.mFlow.mDdUrlsResult = ddUrlsResultBean;
            this.mFlow.mPlayLevel = this.mFlow.mDdUrlsResult.playLevel;
            this.mFlow.mStreamSupporter.init(this.mFlow.mDdUrlsResult);
            LogInfo.log("zhuqiao", "----LetvMediaPlayerManager.getInstance().isSupportHardDecode()" + LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel());
            this.mFlow.mStreamLevel = this.mFlow.mDdUrlsResult.streamLevel;
            this.mFlow.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_STREAM_INIT);
            this.mFlow.mPlayInfo.mIsGslb = true;
            return true;
        }
        this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), LetvErrorCode.DOWNLOAD_VIDEOFILE_FAILED, "");
        this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify(LetvErrorCode.DOWNLOAD_VIDEOFILE_FAILED, true));
        this.mFlow.mPlayInfo.mIsGslb = false;
        return false;
    }

    protected void getRealUrlFromCde() {
        Volley.getQueue().cancelWithTag(AlbumPlayFlow.REQUEST_REAL_URL_BY_CDE);
        new LetvRequest().setTag(AlbumPlayFlow.REQUEST_REAL_URL_BY_CDE).setPriority(RequestPriority.IMMEDIATE).setRequestType(RequestManner.CACHE_ONLY).setCache(new VolleyDbCache<RealPlayUrlInfoBean>() {
            public RealPlayUrlInfoBean get(VolleyRequest<?> volleyRequest) {
                return AlbumFlowRequestUrlController.this.doGetRealUrlFromCde("", "", "");
            }

            public void add(VolleyRequest<?> volleyRequest, RealPlayUrlInfoBean response) {
            }
        }).setCallback(new SimpleResponse<RealPlayUrlInfoBean>() {
            public void onCacheResponse(VolleyRequest<RealPlayUrlInfoBean> volleyRequest, RealPlayUrlInfoBean result, DataHull hull, CacheResponseState state) {
                AlbumFlowRequestUrlController.this.onAfterFetchRealUrlFromCde(result, state);
            }
        }).add();
    }

    protected RealPlayUrlInfoBean doGetRealUrlFromCde(String m3u8, String videoUrl, String muri) {
        this.mFlow.addPlayInfo("从cde获取播放地址开始", "");
        if (this.mFlow.mDdUrlsResult == null) {
            return null;
        }
        CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
        if (cdeHelper == null) {
            this.mFlow.addPlayInfo("从cde获取播放地址结束：失败", "cdeHelper为空");
            return null;
        }
        long time = System.currentTimeMillis();
        this.mFlow.mPlayInfo.type9 = System.currentTimeMillis();
        String linkShell = "";
        if (!TextUtils.isEmpty(m3u8) && !TextUtils.isEmpty(videoUrl)) {
            linkShell = cdeHelper.getCacheUrlWithData(m3u8, "m3u8", videoUrl, "");
            LogInfo.log("zhuqiao_realurl", "cache url:" + linkShell);
            this.mFlow.addPlayInfo("从cde获取播放地址,cacheUrl", linkShell);
        } else if (TextUtils.isEmpty(muri)) {
            linkShell = this.mFlow.getLinkShellUrl();
            this.mFlow.addPlayInfo("从cde获取播放地址,linkShell", linkShell);
        } else {
            linkShell = muri;
            this.mFlow.addPlayInfo("从cde获取播放地址,muri", muri);
        }
        AlbumUrl albumUrl = this.mFlow.mAlbumUrl;
        this.mFlow.mAlbumUrl.p2pUrl = linkShell;
        albumUrl.linkShellUrl = linkShell;
        String str = null;
        int errCode = -1;
        this.mFlow.addPlayInfo("cde getPlayUrlSync 开始", "");
        String json = cdeHelper.getPlayUrlSync(linkShell);
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject object = new JSONObject(json);
                str = object.getString("playUrl");
                errCode = object.getInt("errCode");
                this.mFlow.addPlayInfo("cde getPlayUrlSync 结束，errorCode", errCode + ";playurl is null = " + TextUtils.isEmpty(str));
            } catch (JSONException e) {
                this.mFlow.addPlayInfo("cde getPlayUrlSync 结束，失败", e.toString());
            }
        }
        if (errCode == PlayConstant.OVERLOAD_PROTECTION_OVERLOAD) {
            this.mFlow.mOverloadProtectionState = OverloadProtectionState.CUTOUT;
            this.mFlow.mPlayInfo.type9 = System.currentTimeMillis() - this.mFlow.mPlayInfo.type9;
            return null;
        }
        if (!(errCode == 0 || errCode == PlayConstant.OVERLOAD_PROTECTION_DOWNLOAD_STREAM)) {
            this.mFlow.mOverloadProtectionState = OverloadProtectionState.OTHER_ERROR;
            str = null;
        }
        if (errCode == PlayConstant.OVERLOAD_PROTECTION_DOWNLOAD_STREAM) {
            this.mFlow.mOverloadProtectionState = OverloadProtectionState.DOWNLOAD_STREAM;
        }
        if (TextUtils.isEmpty(str)) {
            this.mFlow.mAlbumUrl.realUrl = cdeHelper.getPlayUrl(this.mFlow.mAlbumUrl.p2pUrl, "", "");
        } else {
            this.mFlow.mAlbumUrl.realUrl = str;
        }
        this.mFlow.mPlayInfo.mVideoSend = "vsend=P2P";
        if (TextUtils.isEmpty(this.mFlow.mAlbumUrl.realUrl)) {
            this.mFlow.addPlayInfo("从cde获取真实播放地址耗时", "" + (System.currentTimeMillis() - time));
            this.mFlow.mPlayInfo.type9 = System.currentTimeMillis() - this.mFlow.mPlayInfo.type9;
            return null;
        }
        this.mFlow.addPlayInfo("从cde获取播放地址结束：成功", this.mFlow.mAlbumUrl.realUrl);
        RealPlayUrlInfoBean bean = new RealPlayUrlInfoBean();
        bean.realUrl = this.mFlow.mAlbumUrl.realUrl;
        this.mFlow.mPlayInfo.mTimeForRequestRealUrlFromCde = System.currentTimeMillis() - time;
        this.mFlow.addPlayInfo("从cde获取真实播放地址耗时", "" + (System.currentTimeMillis() - time));
        this.mFlow.mPlayInfo.type9 = System.currentTimeMillis() - this.mFlow.mPlayInfo.type9;
        return bean;
    }

    protected boolean onAfterFetchRealUrlFromCde(RealPlayUrlInfoBean result, CacheResponseState state) {
        LogInfo.log("zhuqiao_realurl", state == CacheResponseState.SUCCESS ? "从cde获取真实地址成功!" : "从cde获取真实地址失败!");
        OverloadProtectionState protectionState = this.mFlow.mOverloadProtectionState;
        if (protectionState == OverloadProtectionState.NORMAL || protectionState == OverloadProtectionState.DOWNLOAD_STREAM) {
            return false;
        }
        this.mFlow.mTrailListener.hide();
        this.mFlow.mErrorState = PlayErrorState.DATA_ERROR;
        if (this.mFlow.mOverloadProtectionState != OverloadProtectionState.CUTOUT) {
            return false;
        }
        this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage(DialogMsgConstantId.OVERLOAD_CUTOUT, R.string.overload_protection_cutoff), "0208", "");
        this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("0208", true));
        return true;
    }

    protected void getRealUrlFromNet() {
        final String url = this.mFlow.getLinkShellUrl();
        if (!TextUtils.isEmpty(url)) {
            this.mFlow.addPlayInfo("从网络获取播放地址开始", url);
            this.mFlow.mPlayInfo.type9 = System.currentTimeMillis();
            Volley.getQueue().cancelWithTag(AlbumPlayFlow.REQUEST_REAL_URL);
            new LetvRequest().setUrl(url).setParser(new RealPlayUrlInfoParser()).setCache(new VolleyNoCache()).setMaxRetries(2).setPriority(RequestPriority.IMMEDIATE).setRequestType(RequestManner.NETWORK_ONLY).setTag(AlbumPlayFlow.REQUEST_REAL_URL).setShowTag(true).setCallback(new SimpleResponse<RealPlayUrlInfoBean>() {
                public void onNetworkResponse(VolleyRequest<RealPlayUrlInfoBean> request, RealPlayUrlInfoBean result, DataHull hull, NetworkResponseState state) {
                    AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mRetryNum = request.getRetryPolicy().getRetries();
                    AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mType5 = request.getRequestNetConsumeTime();
                    AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mType5_1 = request.getClientConsumeTime();
                    AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.type9 = System.currentTimeMillis() - AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.type9;
                    AlbumFlowRequestUrlController.this.mFlow.addPlayInfo("从cdn获取真实播放地址耗时", "接口耗时：" + AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mType5 + ";客户端耗时：" + AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mType5_1);
                    if (state == NetworkResponseState.SUCCESS) {
                        AlbumFlowRequestUrlController.this.mFlow.addPlayInfo("从网络获取播放地址结束：成功", url);
                        AlbumFlowRequestUrlController.this.onAfterFetchRealUrlFromNet(result);
                        return;
                    }
                    if (state != NetworkResponseState.SUCCESS) {
                        AlbumFlowRequestUrlController.this.mFlow.addPlayInfo("从网络获取播放地址结束：失败", state + "");
                    }
                    if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                        AlbumFlowRequestUrlController.this.mFlow.mLoadListener.requestError("", "", "");
                        AlbumFlowRequestUrlController.this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("1005", true));
                        AlbumFlowRequestUrlController.this.mFlow.mTrailListener.hide();
                        AlbumFlowRequestUrlController.this.mFlow.mErrorState = PlayErrorState.CND_API_ERROR;
                    } else if (!AlbumFlowRequestUrlController.this.mFlow.retryStream()) {
                        if (state == NetworkResponseState.NETWORK_ERROR) {
                            AlbumFlowRequestUrlController.this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                            AlbumFlowRequestUrlController.this.mFlow.mTrailListener.hide();
                            AlbumFlowRequestUrlController.this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("1005", true));
                            AlbumFlowRequestUrlController.this.mFlow.mErrorState = PlayErrorState.CND_API_ERROR;
                            AlbumFlowRequestUrlController.this.mFlow.staticticsErrorInfo(AlbumFlowRequestUrlController.this.mContext, "1008", "playerError", 0, -1);
                        } else if (state == NetworkResponseState.RESULT_ERROR) {
                            AlbumFlowRequestUrlController.this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "1005", "");
                            AlbumFlowRequestUrlController.this.mFlow.mTrailListener.hide();
                            AlbumFlowRequestUrlController.this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("1005", true));
                            AlbumFlowRequestUrlController.this.mFlow.mErrorState = PlayErrorState.CND_API_ERROR;
                        }
                    }
                }

                public void onErrorReport(VolleyRequest<RealPlayUrlInfoBean> volleyRequest, String errorInfo) {
                }
            }).add();
        }
    }

    protected boolean startPlayDrm() {
        this.mFlow.addPlayInfo("开始DRM流程处理", "");
        final long time = System.currentTimeMillis();
        String url = this.mFlow.mAlbumUrl.dispatchUrl;
        if (this.mFlow.mDdUrlsResult == null || TextUtils.isEmpty(url)) {
            return false;
        }
        this.mFlow.mPlayInfo.type9 = System.currentTimeMillis();
        DrmManager manager = DrmManager.getInstance();
        manager.stopDrmThread();
        if (!TextUtils.equals(this.mFlow.mCurrentPlayingVideo.drmFlag, "1")) {
            return false;
        }
        LogInfo.log("ghz DrmTest", "start drm process");
        LogInfo.log("ghz DrmTest", "playUrl: " + url);
        String drmXmlUrl = this.mFlow.mDdUrlsResult.drmToken;
        LogInfo.log("ghz DrmTest", "drmXmlUrl: " + drmXmlUrl);
        LogInfo.log("play_auto_test_DRM", "####PLAY_DRM####START vid:" + this.mFlow.mVid + " drm_token:" + drmXmlUrl);
        manager.startDrm(url, drmXmlUrl, this.mFlow.mCurrentPlayingVideo.drmVideoType, new DrmDealCallBackInf() {
            public void onDrmCallBack(int resultCode, DrmResultBean drmResultBean) {
                LogInfo.log("ghz DrmTest", "onDrmCallBack.....ressultCode:" + resultCode + ",DrmResultBean:" + drmResultBean.toString());
                AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.type9 = System.currentTimeMillis() - AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.type9;
                if (DrmResultCodeEnum.DRM_OK.getResultCode() == resultCode) {
                    String drmMediaUrl = drmResultBean.getDrmMediaUrl();
                    LogInfo.log("ghz DrmTest", "drmMediaUrl: " + drmMediaUrl);
                    if (!TextUtils.isEmpty(drmMediaUrl)) {
                        AlbumFlowRequestUrlController.this.mFlow.mAlbumUrl.realUrl = drmMediaUrl;
                        AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mTimeForRequestRealUrlFromCde = System.currentTimeMillis() - time;
                        AlbumFlowRequestUrlController.this.mFlow.addPlayInfo("获取DRM播放地址耗时", "" + (System.currentTimeMillis() - time));
                        LogInfo.log("ghz DrmTest", "mFlow.mAlbumUrl.realUrl: " + AlbumFlowRequestUrlController.this.mFlow.mAlbumUrl.realUrl);
                        AlbumFlowRequestUrlController.this.onAfterFetchRealUrl(drmMediaUrl, VideoPlayChannel.DRM);
                        return;
                    }
                    return;
                }
                AlbumFlowRequestUrlController.this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage("700097", R.string.drm_play_error), "-1", "");
            }
        });
        return true;
    }

    protected void onAfterFetchRealUrlFromNet(RealPlayUrlInfoBean result) {
    }

    public void handleADUrlAcquireDone(List<AdElementMime> list, List<AdElementMime> list2, long midTime) {
    }

    protected void onAfterFetchRealUrl(String url, VideoPlayChannel channel) {
        this.mFlow.mPlayInfo.mType17 = System.currentTimeMillis();
        if (channel == VideoPlayChannel.DRM) {
            LogInfo.log("ghz DrmTest", "startPlayNet");
            if (!this.mFlow.getFrontAdNormal(false)) {
                this.mFlow.startPlayNet();
            }
        } else if (this.mFlow.mIsWo3GUser) {
            onFetchRealUrlWithWo3GUser(url, false);
        } else {
            if (channel == VideoPlayChannel.CDE) {
                LetvLogApiTool.createPlayLogInfo("getRealPlayUrlForP2p", this.mFlow.mVid + "", "realUrl=" + this.mFlow.mAlbumUrl.realUrl + " playLevel=" + this.mFlow.mPlayLevel);
            } else if (TextUtils.isEmpty(url)) {
                this.mFlow.mLoadListener.jumpError(-1);
                this.mFlow.mTrailListener.hide();
                this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify("0204", true));
                this.mFlow.mErrorState = PlayErrorState.DATA_ERROR;
                return;
            } else {
                this.mFlow.mAlbumUrl.realUrl = url;
                LetvLogApiTool.createPlayLogInfo("getRealPlayUrlForNormalPlay", this.mFlow.mVid + "", "realUrl=" + this.mFlow.mAlbumUrl.realUrl + " playLevel=" + this.mFlow.mPlayLevel);
            }
            if (this.mFlow.mIsPlayFreeUrl) {
                startPlayNet();
            } else if ((this.mIsCombine || !this.mFlow.getFrontAdNormal(false)) && !this.mFlow.shouldShowNetChangeDialog()) {
                startPlayNet();
            }
        }
    }

    public void onFetchRealUrlWithWo3GUser(String url, final boolean isWifiRealUrl) {
        if (TextUtils.isEmpty(url)) {
            this.mFlow.mLoadListener.requestError(this.mContext.getString(R.string.net_request_error), "", "");
            this.mFlow.mTrailListener.hide();
            this.mFlow.mErrorState = PlayErrorState.DATA_ERROR;
            return;
        }
        this.mFlow.addPlayInfo("请求免流量地址开始", url);
        final long startTime = System.currentTimeMillis();
        ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).identifyWoVideoSDK(this.mContext, url, 1, new LetvWoFlowListener() {
            public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                if (AlbumFlowRequestUrlController.this.mContext != null) {
                    AlbumFlowRequestUrlController.this.mFlow.mPlayInfo.mTimeForRequestRealUrlFromUnion = System.currentTimeMillis() - startTime;
                    if (TextUtils.isEmpty(freeUrl)) {
                        AlbumFlowRequestUrlController.this.mHandler.post(new Runnable() {
                            public void run() {
                                AlbumFlowRequestUrlController.this.mFlow.addPlayInfo("请求免流量地址结束：失败", "免流量地址为空");
                                AlbumFlowRequestUrlController.this.mFlow.mLoadListener.requestError(AlbumFlowRequestUrlController.this.mContext.getString(R.string.net_request_error), "", "");
                                AlbumFlowRequestUrlController.this.mFlow.mTrailListener.hide();
                                AlbumFlowRequestUrlController.this.mFlow.mErrorState = PlayErrorState.WO_REAL_URL_API_ERROR;
                            }
                        });
                        return;
                    }
                    AlbumFlowRequestUrlController.this.mFlow.mAlbumUrl.realUrl = freeUrl;
                    AlbumFlowRequestUrlController.this.mFlow.addPlayInfo("请求免流量地址结束：成功", freeUrl);
                    AlbumFlowRequestUrlController.this.mFlow.mIsPlayFreeUrl = true;
                    AlbumFlowRequestUrlController.this.mHandler.post(new Runnable() {
                        public void run() {
                            UnicomWoFlowDialogUtils.showWoFreeActivatedToast(AlbumFlowRequestUrlController.this.mContext);
                            if (isWifiRealUrl) {
                                AlbumFlowRequestUrlController.this.startPlayNet();
                            } else if (AlbumFlowRequestUrlController.this.mIsCombine || !AlbumFlowRequestUrlController.this.mFlow.getFrontAdNormal(false)) {
                                AlbumFlowRequestUrlController.this.startPlayNet();
                            }
                        }
                    });
                }
            }
        });
    }

    private void startPlayNet() {
        if (this.mIsCombine) {
            this.mFlow.mIsSeparateAdFinished = true;
        }
        this.mFlow.startPlayNet();
    }

    protected void showError(final boolean showTip, final String errorCode) {
        this.mHandler.post(new Runnable() {
            public void run() {
                if (showTip) {
                    AlbumFlowRequestUrlController.this.mFlow.mObservable.notifyObservers(new PlayErrorCodeNotify(errorCode, false));
                }
            }
        });
    }
}
