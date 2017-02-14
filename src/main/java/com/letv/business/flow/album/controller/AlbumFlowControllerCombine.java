package com.letv.business.flow.album.controller;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.utils.PlayConstantUtils.PFConstant;
import com.letv.android.client.business.R;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.CombileResultBean;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.RealPlayUrlInfoBean;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyRequest.RequestPriority;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.VolleyResult;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDbCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.CombileResultParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.PlayUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.List;

public class AlbumFlowControllerCombine extends AlbumFlowRequestUrlController {
    private CombileResultBean mCombileResult;
    private String mMidRollAdUrls;
    private long mMidTime;
    private String mPreRollAdUrls;

    public AlbumFlowControllerCombine(Context context, AlbumPlayFlow flow) {
        super(context, flow);
        this.mIsCombine = true;
        this.mCombileResult = null;
    }

    private void resetAdInfo() {
        this.mPreRollAdUrls = null;
        this.mMidRollAdUrls = null;
        this.mMidTime = 0;
    }

    protected void doRequest(boolean isRetry) {
        if (!isRetry) {
            resetAdInfo();
            if (!this.mFlow.getFrontAdNormal(false)) {
                if (this.mCdeEnable) {
                    getRealUrlFromCde();
                } else {
                    getRealUrlFromNet();
                }
            }
        } else if ((!TextUtils.isEmpty(this.mPreRollAdUrls) && !this.mFlow.mIsFrontAdFinished) || !TextUtils.isEmpty(this.mMidRollAdUrls)) {
            if (this.mFlow.mIsFrontAdFinished) {
                this.mPreRollAdUrls = null;
            }
            requestCombinesUrl(this.mPreRollAdUrls, this.mMidRollAdUrls, this.mMidTime, this.mFlow.getLinkShellUrl());
        } else if (this.mCdeEnable) {
            getRealUrlFromCde();
        } else {
            getRealUrlFromNet();
        }
    }

    protected boolean onAfterFetchRealUrlFromCde(RealPlayUrlInfoBean result, CacheResponseState state) {
        if (!super.onAfterFetchRealUrlFromCde(result, state)) {
            if (state == CacheResponseState.SUCCESS) {
                onAfterFetchRealUrl(result.realUrl, VideoPlayChannel.CDE);
            } else {
                this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                showError(true, LetvErrorCode.REQUEST_REAL_URL_FROM_CDE_ERROR);
            }
        }
        return true;
    }

    protected void onAfterFetchRealUrlFromNet(RealPlayUrlInfoBean result) {
        onAfterFetchRealUrl(result.realUrl, VideoPlayChannel.CDN);
    }

    public void handleADUrlAcquireDone(List<AdElementMime> preRollAdUrls, List<AdElementMime> midRollAdUrls, long midTime) {
        if (!BaseTypeUtils.isListEmpty(preRollAdUrls) || !BaseTypeUtils.isListEmpty(midRollAdUrls)) {
            this.mFlow.mPlayInfo.mType20 = System.currentTimeMillis();
            String preUrl = "";
            String midUrl = "";
            String videoUrl = this.mFlow.getLinkShellUrl();
            if (!BaseTypeUtils.isListEmpty(preRollAdUrls)) {
                for (AdElementMime pre : preRollAdUrls) {
                    preUrl = preUrl + PlayUtils.replaceM3v(pre.mediaFileUrl) + "&tss=ios|";
                }
                if (preUrl.length() > 0) {
                    preUrl = preUrl.substring(0, preUrl.length() - 1);
                }
            }
            if (!BaseTypeUtils.isListEmpty(midRollAdUrls)) {
                for (AdElementMime middle : midRollAdUrls) {
                    midUrl = midUrl + PlayUtils.replaceM3v(middle.mediaFileUrl) + "&tss=ios|";
                }
                if (midUrl.length() > 0) {
                    midUrl = midUrl.substring(0, midUrl.length() - 1);
                }
            }
            requestCombinesUrl(preUrl, midUrl, midTime, videoUrl);
        } else if (this.mCdeEnable) {
            getRealUrlFromCde();
        } else {
            getRealUrlFromNet();
        }
    }

    private void requestCombinesUrl(String preRollAdUrls, String midRollAdUrls, long midTime, String videoUrl) {
        final String str = preRollAdUrls;
        final String str2 = videoUrl;
        final String str3 = midRollAdUrls;
        final long j = midTime;
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setPriority(RequestPriority.IMMEDIATE).setCache(new VolleyDbCache<LetvBaseBean>() {
            public LetvBaseBean get(VolleyRequest<?> volleyRequest) {
                AlbumFlowControllerCombine.this.mFlow.mPlayInfo.mType20 = System.currentTimeMillis() - AlbumFlowControllerCombine.this.mFlow.mPlayInfo.mType20;
                AlbumFlowControllerCombine.this.doRequestCombinesUrl(str, str2, str3, j);
                return null;
            }

            public void add(VolleyRequest<?> volleyRequest, LetvBaseBean response) {
            }
        }).setCallback(new SimpleResponse()).add();
    }

    private void doRequestCombinesUrl(String preRollAdUrls, String videoUrl, String midRollAdUrls, long midTime) {
        this.mFlow.addPlayInfo("请求拼接接口开始", "前贴:" + preRollAdUrls);
        this.mFlow.addPlayInfo("请求拼接接口开始", "中贴:" + midRollAdUrls);
        this.mFlow.addPlayInfo("请求拼接接口开始", "正片:" + videoUrl);
        this.mPreRollAdUrls = preRollAdUrls;
        this.mMidRollAdUrls = midRollAdUrls;
        this.mMidTime = midTime;
        VolleyRequest<CombileResultBean> request = new LetvRequest().setUrl(LetvUrlMaker.getCombinesUrl()).setRequestType(RequestManner.NETWORK_ONLY).addPostParam("ahl", preRollAdUrls).addPostParam("vl", videoUrl).addPostParam(SettingsJsonConstants.ICON_HASH_KEY, "1").addPostParam("bks", "2").addPostParam("aml", midRollAdUrls).addPostParam("amp", midTime + "").setCache(new VolleyNoCache()).setParser(new CombileResultParser()).setTag(AlbumPlayFlow.REQUEST_COMBINES).setShowTag(true);
        if (PreferencesManager.getInstance().getUseCombineM3u8()) {
            request.addPostParam("once", "2");
        }
        VolleyResult<CombileResultBean> result = request.syncFetch();
        if (!request.isCanceled()) {
            this.mFlow.mPlayInfo.type8 = request.getRequestNetConsumeTime();
            this.mFlow.mPlayInfo.type8_1 = request.getClientConsumeTime();
            this.mFlow.addPlayInfo("拼接接口耗时", "接口耗时：" + this.mFlow.mPlayInfo.type8 + ";客户端耗时：" + this.mFlow.mPlayInfo.type8_1);
            if (result.networkState == NetworkResponseState.SUCCESS && (result.result instanceof CombileResultBean)) {
                this.mCombileResult = (CombileResultBean) result.result;
                if (checkCombileResult(this.mCombileResult, result)) {
                    if (result.dataHull != null) {
                        LogInfo.log("zhuqiao_realurl", "拼接结果:" + result.dataHull.sourceData);
                        sendCombineCompleteToAd(result.dataHull.sourceData);
                    }
                    if (!TextUtils.isEmpty(preRollAdUrls) && this.mFlow.mPlayInfo.frontAdDuration == 0) {
                        this.mPreRollAdUrls = null;
                    }
                    if (!TextUtils.isEmpty(midRollAdUrls) && this.mFlow.mPlayInfo.midAdPlayTime == 0) {
                        this.mMidRollAdUrls = null;
                        this.mMidTime = 0;
                    }
                    this.mFlow.addPlayInfo("拼接接口请求结束：成功", "");
                    this.mCombileResult.muri = PlayUtils.addStatisticsInfoToUrl(this.mCombileResult.muri, this.mFlow.mPlayInfo.mUuidTimp, this.mFlow.mVid + "");
                    if (this.mCdeEnable) {
                        RealPlayUrlInfoBean cdeRealPlayUrl = null;
                        if (!TextUtils.isEmpty(this.mCombileResult.m3u8)) {
                            cdeRealPlayUrl = doGetRealUrlFromCde(this.mCombileResult.m3u8, videoUrl, "");
                        } else if (!TextUtils.isEmpty(this.mCombileResult.muri)) {
                            cdeRealPlayUrl = doGetRealUrlFromCde("", "", this.mCombileResult.muri);
                        }
                        final RealPlayUrlInfoBean cdeRealPlayUrlTemp = cdeRealPlayUrl;
                        if (cdeRealPlayUrlTemp != null || this.mFlow.mOverloadProtectionState == OverloadProtectionState.CUTOUT) {
                            this.mHandler.postAtFrontOfQueue(new Runnable() {
                                public void run() {
                                    AlbumFlowControllerCombine.this.onAfterFetchRealUrlFromCde(cdeRealPlayUrlTemp, cdeRealPlayUrlTemp == null ? CacheResponseState.ERROR : CacheResponseState.SUCCESS);
                                }
                            });
                            return;
                        }
                    }
                    this.mHandler.postAtFrontOfQueue(new Runnable() {
                        public void run() {
                            AlbumFlowControllerCombine.this.onAfterFetchRealUrl(AlbumFlowControllerCombine.this.mCombileResult.muri, VideoPlayChannel.CDN);
                        }
                    });
                    return;
                }
                doIfCombineError(false);
                return;
            }
            showError(false, LetvErrorCode.REQUEST_AD_COMBINE_ERROR);
            this.mFlow.addPlayInfo("拼接接口请求结束：失败", "");
            doIfCombineError(true);
        }
    }

    private void doIfCombineError(boolean isAdCombineError) {
        if (this.mFlow.mDdUrlsResult == null || BaseTypeUtils.isQueueEmpty(this.mFlow.mDdUrlsResult.streamQueue) || isAdCombineError) {
            resetAdInfo();
            sendCombineCompleteToAd("");
        }
        if (this.mCdeEnable) {
            final RealPlayUrlInfoBean cdeRealPlayUrl = doGetRealUrlFromCde("", "", "");
            if (cdeRealPlayUrl != null || this.mFlow.mOverloadProtectionState == OverloadProtectionState.CUTOUT) {
                this.mHandler.postAtFrontOfQueue(new Runnable() {
                    public void run() {
                        AlbumFlowControllerCombine.this.onAfterFetchRealUrlFromCde(cdeRealPlayUrl, cdeRealPlayUrl == null ? CacheResponseState.ERROR : CacheResponseState.SUCCESS);
                    }
                });
                return;
            }
        }
        this.mHandler.postAtFrontOfQueue(new Runnable() {
            public void run() {
                AlbumFlowControllerCombine.this.getRealUrlFromNet();
            }
        });
    }

    private boolean checkCombileResult(CombileResultBean result, VolleyResult volleyResult) {
        this.mFlow.mPlayInfo.frontAdDuration = 0;
        this.mFlow.mPlayInfo.midDuration = 0;
        if (result.vs < 0) {
            showError(true, LetvErrorCode.REQUEST_AD_COMBINE_ERROR);
            if (volleyResult == null || volleyResult.dataHull == null || TextUtils.isEmpty(volleyResult.dataHull.sourceData)) {
                this.mFlow.addPlayInfo("拼接接口请求结束：正片拼接失败", "");
                return false;
            }
            this.mFlow.addPlayInfo("拼接接口请求结束：正片拼接失败", volleyResult.dataHull.sourceData);
            return false;
        }
        int i;
        if (result.ahtArr != null && result.ahtArr.length > 0) {
            for (i = 0; i < result.ahtArr.length; i++) {
                if (BaseTypeUtils.getElementFromIntArray(result.ahsArr, i) < 0) {
                    this.mFlow.addPlayInfo("前贴拼接失败", "第" + i + "段");
                } else {
                    float time = result.ahtArr[i];
                    AlbumPlayInfo albumPlayInfo = this.mFlow.mPlayInfo;
                    albumPlayInfo.frontAdDuration = (long) (((float) albumPlayInfo.frontAdDuration) + (time * 1000.0f));
                    LogInfo.log("zhuqiao_realurl", "前贴拼接广告时长:" + (time * 1000.0f));
                }
            }
        }
        if (result.amtArr != null && result.amtArr.length > 0) {
            for (i = 0; i < result.amtArr.length; i++) {
                if (BaseTypeUtils.getElementFromIntArray(result.amsArr, i) < 0) {
                    this.mFlow.addPlayInfo("中贴拼接失败", "第" + i + "段");
                } else {
                    time = result.amtArr[i];
                    albumPlayInfo = this.mFlow.mPlayInfo;
                    albumPlayInfo.midDuration = (long) (((float) albumPlayInfo.midDuration) + (time * 1000.0f));
                    LogInfo.log("zhuqiao_realurl", "中贴拼接广告时长:" + (time * 1000.0f));
                }
            }
        }
        this.mFlow.addPlayInfo("前贴拼接广告总时长", this.mFlow.mPlayInfo.frontAdDuration + "");
        this.mFlow.addPlayInfo("中贴拼接广告总时长", this.mFlow.mPlayInfo.midDuration + "");
        if (result.ampArr != null && result.ampArr.length > 0) {
            this.mFlow.mPlayInfo.midAdPlayTime = (long) (result.ampArr[0] * 1000.0f);
            this.mFlow.addPlayInfo("中贴插入时间点", this.mFlow.mPlayInfo.midAdPlayTime + "");
        }
        return true;
    }

    private void sendCombineCompleteToAd(final String data) {
        this.mHandler.post(new Runnable() {
            public void run() {
                Message msg = new Message();
                msg.what = 5;
                Bundle bundle = new Bundle();
                bundle.putString(PFConstant.KEY_AD_COMBINE_RESULT, data);
                msg.setData(bundle);
                AlbumFlowControllerCombine.this.mFlow.mAdListener.notifyADEvent(msg);
            }
        });
    }
}
