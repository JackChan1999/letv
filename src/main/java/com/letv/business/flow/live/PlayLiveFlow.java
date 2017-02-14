package com.letv.business.flow.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import cn.com.iresearch.vvtracker.IRVideo;
import com.letv.ads.ex.client.ADListener;
import com.letv.ads.ex.client.ClientFunction;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.android.client.business.R;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.live.LiveFlowCallback.AsyncCallback;
import com.letv.business.flow.live.LiveFlowCallback.CheckPayCallback;
import com.letv.business.flow.live.LiveFlowCallback.LivePlayCallback;
import com.letv.business.flow.live.LiveFlowCallback.RequestUrlByChannelIdCallback;
import com.letv.business.flow.statistics.LivePlayStatisticsHelper;
import com.letv.business.flow.statistics.StatisticsInfo;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils.UnicomDialogClickListener;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.LiveApi;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.CurrentProgram;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannelProgramList;
import com.letv.core.bean.LiveCanPlay;
import com.letv.core.bean.LiveDateInfo;
import com.letv.core.bean.LiveLunboProgramListBean;
import com.letv.core.bean.LiveMiGuUrlInfo;
import com.letv.core.bean.LivePriceBean;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveResultInfo;
import com.letv.core.bean.LiveStreamBean;
import com.letv.core.bean.LiveStreamBean.StreamType;
import com.letv.core.bean.LiveUrlInfo;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.bean.RealLink;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.TimestampBean.FetchServerTimeListener;
import com.letv.core.bean.YingchaoJianquanResult;
import com.letv.core.bean.YingchaoTicketConsumeResult;
import com.letv.core.bean.YingchaoTicketInfo;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LiveDateInfoParser;
import com.letv.core.parser.LiveLunboProgramListParser;
import com.letv.core.parser.LiveLunboWeishiChannelProgramListParser;
import com.letv.core.parser.LiveMiGuUrlParser;
import com.letv.core.parser.LiveMusicPriceParser;
import com.letv.core.parser.LivePriceParser;
import com.letv.core.parser.LiveRemenBaseBeanParser;
import com.letv.core.parser.LiveResultParser;
import com.letv.core.parser.LiveRoomHalfPlayerDataParser;
import com.letv.core.parser.YingchaoJianquanParser;
import com.letv.core.parser.YingchaoTicketConsumeParser;
import com.letv.core.parser.YingchaoTicketInfoParser;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.util.DataUtils;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.pp.func.CdeHelper;
import com.letv.pp.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.List;
import org.cybergarage.upnp.Device;

public class PlayLiveFlow extends BasePlayLiveFlow {
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_NONE = 0;
    public static final int DIRECTION_RIGHT = 1;
    public final int REFRESHEPGLIST = 1000;
    public final long REFRESHEPGLIST_TIME = 120000;
    private final int UPDATE_STATICICS_TIME = 257;
    public final int WAIT_ADFRAGMENT_COMMIT = 1001;
    private ADListener mADListener = new ADListener() {
        public void handleADFinish(boolean isFinishByHand) {
            PlayLiveFlow.LogAddInfo("广告结束回调", "isFinishByHand=" + isFinishByHand);
            if (PlayLiveFlow.this.mPlayAdFragment != null) {
                PlayLiveFlow.this.mPlayAdFragment.setADPause(false);
            }
            PlayLiveFlow.this.mAdsFinished = true;
            if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mIsPlayingAds = false;
            }
            PlayLiveFlow.LogAddInfo("广告结束回调", "是否有广告=" + (PlayLiveFlow.this.mPlayAdFragment != null ? Boolean.valueOf(PlayLiveFlow.this.mPlayAdFragment.isHaveFrontAds()) : "playAdFragment is null") + ",mLivePlayCallback.isEnforcementPause()=" + PlayLiveFlow.this.mLivePlayCallback.isEnforcementPause());
            if (PlayLiveFlow.this.mLivePlayCallback.isEnforcementPause()) {
                PlayLiveFlow.this.mLivePlayCallback.setEnforcementWait(false);
                return;
            }
            if (!(PlayLiveFlow.this.mPlayAdFragment == null || PlayLiveFlow.this.mPlayAdFragment.isPlaying() || PlayLiveFlow.this.mPlayInterupted || PlayLiveFlow.this.isOverLoadCutOut())) {
                PlayLiveFlow.this.mLivePlayCallback.loading(false);
            }
            PlayLiveFlow.LogAddInfo("广告结束回调", "mPlayInterupted=" + PlayLiveFlow.this.mPlayInterupted + ",isOverLoadCutOut()=" + PlayLiveFlow.this.isOverLoadCutOut());
            if (!(PlayLiveFlow.this.mPlayInterupted || PlayLiveFlow.this.isOverLoadCutOut())) {
                PlayLiveFlow.LogAddInfo("广告结束开始播放", "");
                PlayLiveFlow.this.mLivePlayCallback.playOnAdsFinish();
            }
            PlayLiveFlow.this.mLivePlayCallback.setEnforcementWait(false);
            if (PlayLiveFlow.this.isOverLoadCutOut()) {
                String tx = TipUtils.getTipMessage(DialogMsgConstantId.OVERLOAD_CUTOUT, PlayLiveFlow.this.mContext.getString(R.string.overload_protection_cutoff));
                PlayLiveFlow.LogAddInfo("广告结束回调 过载断流", "tx=" + tx);
                PlayLiveFlow.this.mLivePlayCallback.notPlay(tx);
            }
        }

        public void handleADStart(long time) {
            LogInfo.log("jc666", "ads start play! time=" + time);
            if (PlayLiveFlow.this.mStatisticsHelper != null && PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo != null) {
                PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mRequestTimeInfo.mAdsPlayFirstFrameTime = time;
                PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mIsPlayingAds = true;
            }
        }
    };
    private ClientFunction mAdsCallback = new ClientFunction() {
        public void skipAd() {
            PlayLiveFlow.LogAddInfo("广告回调 点击跳过广告按钮", "islogined=" + PreferencesManager.getInstance().isLogin() + ",isVip=" + PreferencesManager.getInstance().isVip());
            if (PreferencesManager.getInstance().isLogin() && PreferencesManager.getInstance().isVip()) {
                PlayLiveFlow.this.mPlayAdFragment.setADPause(true);
                PlayLiveFlow.this.getFrontAd(PlayLiveFlow.this.mLiveStreamBean.getLiveUrl(), PlayLiveFlow.this.mLaunchMode == 101 ? "2" : "1");
                return;
            }
            PlayLiveFlow.this.mLivePlayCallback.jumpToVipProducts(0, 0);
        }

        public void setHalfOrFullScreen(boolean isHalf, boolean isBtnHalf) {
            if (isHalf) {
                PlayLiveFlow.this.mLivePlayCallback.full();
            } else if (PlayLiveFlow.this.mOnlyFull) {
                PlayLiveFlow.this.mLivePlayCallback.back();
            } else {
                PlayLiveFlow.this.mLivePlayCallback.half();
            }
        }

        public void resumeVideo() {
            PlayLiveFlow.this.mLivePlayCallback.resumeVideo();
        }

        public void pauseVideo() {
            PlayLiveFlow.this.mLivePlayCallback.pauseVideo();
        }

        public void onHalfBackDown() {
            PlayLiveFlow.this.mLivePlayCallback.back();
        }

        public long getVideoCurrentTime() {
            try {
                if (PlayLiveFlow.this.mLivePlayCallback.isFirstPlay()) {
                    return 0;
                }
                return (long) PlayLiveFlow.this.mLivePlayCallback.getCurrentPlayPosition();
            } catch (Exception e) {
                return 0;
            }
        }

        public Rect getPlayerRect() {
            return null;
        }

        public void onAdFullProcessTimeout(boolean needSkipAd) {
        }
    };
    private boolean mAdsFinished = false;
    private AsyncCallback mCanPlayCallback = new AsyncCallback() {
        public void onNetworkResponse(NetworkResponseState state, LetvBaseBean result, long time) {
            if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mRequestTimeInfo.mTimeRequestCanplay = time;
            }
            PlayLiveFlow.LogAddInfo("开始请求是否可以播放 返回结果", "state=" + state + ",result=" + result);
            switch (state) {
                case SUCCESS:
                    if (result != null) {
                        boolean isGslb = false;
                        LiveCanPlay liveCanPlay = (LiveCanPlay) result;
                        PlayLiveFlow.LogAddInfo("开始请求是否可以播放 返回结果", "liveCanPlay.canPlay=" + liveCanPlay.canPlay);
                        if ("0".equals(liveCanPlay.canPlay)) {
                            PlayLiveFlow.this.mLivePlayCallback.cannotPlayError();
                        } else {
                            PlayLiveFlow.LogAddInfo("开始请求是否可以播放 开始请求真实地址", "");
                            isGslb = true;
                            PlayLiveFlow.this.playAd(PlayLiveFlow.this.mLiveStreamBean);
                            PlayLiveFlow.this.requestRealLink(PlayLiveFlow.this.mLiveStreamBean, PlayLiveFlow.this.mContext);
                        }
                        if (PlayLiveFlow.this.mStatisticsHelper != null && PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo != null) {
                            PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mIsGslb = isGslb;
                            return;
                        }
                        return;
                    }
                    return;
                case NETWORK_ERROR:
                    PlayLiveFlow.LogAddInfo("开始请求是否可以播放 请求结果", "网络连接失败");
                    PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                    return;
                default:
                    return;
            }
        }
    };
    private String mFrontAdty = "";
    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    PlayLiveFlow.this.requestData(false, false);
                    PlayLiveFlow.this.mHandler.sendEmptyMessageDelayed(1000, 120000);
                    return;
                case 1001:
                    PlayLiveFlow.LogAddInfo("开始等待广告WAIT_ADFRAGMENT_COMMIT", "");
                    if (PlayLiveFlow.this.mPlayAdFragment == null || PlayLiveFlow.this.mPlayAdFragment.getActivity() != null) {
                        if (PlayLiveFlow.this.mUserControllLevel) {
                            PlayLiveFlow.this.setStreamTypeFromUser(PlayLiveFlow.this.mUserStreamType);
                        } else {
                            PlayLiveFlow.this.setStreamTypeFromCpu(PlayLiveFlow.this.mLiveStreamBean, PlayLiveFlow.this.mDefaultLow);
                        }
                        PlayLiveFlow.this.mLivePlayCallback.updateHdButton(PlayLiveFlow.this.mLiveStreamBean);
                        PlayLiveFlow.LogAddInfo("开始广告结束，开始请求播放地址WAIT_ADFRAGMENT_COMMIT", "");
                        PlayLiveFlow.this.playUrl(PlayLiveFlow.this.mLiveStreamBean);
                        return;
                    }
                    sendEmptyMessageDelayed(1001, 50);
                    return;
                default:
                    return;
            }
        }
    };
    private LiveLunboProgramListBean mLunboData;
    protected AdPlayFragmentProxy mPlayAdFragment;
    private boolean mPlayInterupted;
    private AsyncCallback mRealLinkCallback = new AsyncCallback() {
        public void onNetworkResponse(NetworkResponseState state, LetvBaseBean result, long time) {
            if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mRequestTimeInfo.mTimeRequestRealUrl = time;
            }
            PlayLiveFlow.LogAddInfo("请求真实地址回调", "state=" + state + ",result=" + result);
            if (result == null) {
                PlayLiveFlow.this.mLivePlayCallback.notPlay(null);
                return;
            }
            switch (state) {
                case SUCCESS:
                    RealLink realLink = (RealLink) result;
                    PlayLiveFlow.LogAddInfo("请求真实地址回调 过载保护状态", "realLink.overloadProtectionState=" + realLink.overloadProtectionState);
                    if (realLink.overloadProtectionState == OverloadProtectionState.CUTOUT) {
                        PlayLiveFlow.LogAddInfo("请求真实地址回调 过载保护", "mIsPlayedAd=" + PlayLiveFlow.this.mIsPlayedAd + ",mAdsFinished=" + PlayLiveFlow.this.mAdsFinished);
                        if (!PlayLiveFlow.this.mIsPlayedAd || PlayLiveFlow.this.mAdsFinished) {
                            String tx = TipUtils.getTipMessage(DialogMsgConstantId.OVERLOAD_CUTOUT, PlayLiveFlow.this.mContext.getString(R.string.overload_protection_cutoff));
                            PlayLiveFlow.LogAddInfo("请求真实地址回调 过载保护提示", "tx=" + tx);
                            PlayLiveFlow.this.mLivePlayCallback.notPlay(tx);
                        }
                        PlayLiveFlow.this.mOverloadState = realLink.overloadProtectionState;
                        return;
                    }
                    if (realLink.overloadProtectionState == OverloadProtectionState.DOWNLOAD_STREAM) {
                        PlayLiveFlow.LogAddInfo("请求真实地址回调 过载降码流", "");
                        PlayLiveFlow.this.mLivePlayCallback.setDownStreamTipVisible(true);
                    } else if (realLink.overloadProtectionState == OverloadProtectionState.NORMAL) {
                        PlayLiveFlow.LogAddInfo("请求真实地址回调 未过载", "");
                        PlayLiveFlow.this.mLivePlayCallback.setDownStreamTipVisible(false);
                    }
                    PlayLiveFlow.this.mOverloadState = realLink.overloadProtectionState;
                    PlayLiveFlow.LogAddInfo("请求真实地址回调 状态码", "realLink.ercode=" + realLink.ercode + ",mIsWo3GUser=" + PlayLiveFlow.this.mIsWo3GUser);
                    if (!RealLink.CORRECT_ERCODE.equals(realLink.ercode)) {
                        PlayLiveFlow.this.mLivePlayCallback.notPlay(null);
                        PlayLiveFlow.LogAddInfo("请求真实地址回调 请求真实地址出错", "realLink.ercode=" + realLink.ercode);
                        return;
                    } else if (PlayLiveFlow.this.mIsWo3GUser) {
                        PlayLiveFlow.LogAddInfo("请求真实地址回调 开始请求免流量地址", "");
                        ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(PlayLiveFlow.this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).identifyWoVideoSDK(PlayLiveFlow.this.mContext, realLink.location, 0, new LetvWoFlowListener() {
                            public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                                if (PlayLiveFlow.this.mContext != null) {
                                    PlayLiveFlow.LogAddInfo("请求真实地址回调 请求免流量地址返回", "freeUrl=" + freeUrl);
                                    if (freeUrl == null || freeUrl.equals("")) {
                                        PlayLiveFlow.LogAddInfo("请求真实地址回调 免流量地址为空", "freeUrl=" + freeUrl);
                                        new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                PlayLiveFlow.this.mLivePlayCallback.notPlay(null);
                                            }
                                        });
                                        return;
                                    }
                                    PlayLiveFlow.this.mRealUrl = freeUrl;
                                    PlayLiveFlow.this.mIsPlayFreeUrl = true;
                                    new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                        public void run() {
                                            LogInfo.log("king", "****************Live Toast111***************");
                                            PlayLiveFlow.LogAddInfo("请求真实地址回调 开始免流量播放", "");
                                            UnicomWoFlowDialogUtils.showWoFreeActivatedToast(PlayLiveFlow.this.mContext);
                                            PlayLiveFlow.this.play(PlayLiveFlow.this.mRealUrl);
                                        }
                                    });
                                }
                            }
                        });
                        return;
                    } else {
                        PlayLiveFlow.LogAddInfo("未订购  走直播原来地址", "realLink.location=" + realLink.location);
                        PlayLiveFlow.this.play(realLink.location);
                        return;
                    }
                case NETWORK_NOT_AVAILABLE:
                case NETWORK_ERROR:
                case RESULT_ERROR:
                    PlayLiveFlow.LogAddInfo("请求真实地址 失败", "");
                    PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mReqhasAd = false;
    private RequestRealLink mRequestRealLink;
    private RequestUrlByChannelIdCallback mRequestUrlByChannelIdCallback = new RequestUrlByChannelIdCallback() {
        public void onNetworkResponse(NetworkResponseState state, LiveUrlInfo result, boolean isPay, boolean isToPlay) {
            PlayLiveFlow.LogAddInfo("根据ChannelId请求直播码流，返回结果", "state=" + state + ",isPay=" + isPay + ",isToPlay=" + isToPlay);
            switch (state) {
                case SUCCESS:
                    if (result == null) {
                        PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                        return;
                    }
                    PlayLiveFlow.this.updateStreamIdLiveUrl(PlayLiveFlow.this.mLiveStreamBean, result);
                    if (PlayLiveFlow.this.mUserControllLevel) {
                        PlayLiveFlow.this.setStreamTypeFromUser(PlayLiveFlow.this.mUserStreamType);
                    } else {
                        PlayLiveFlow.this.setStreamTypeFromCpu(PlayLiveFlow.this.mLiveStreamBean, PlayLiveFlow.this.mDefaultLow);
                    }
                    if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                        PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mVt = PlayLiveFlow.this.mLiveStreamBean.getCode();
                    }
                    PlayLiveFlow.LogAddInfo("根据ChannelId请求直播码流，处理码流后", "mLiveStreamBean.getLiveUrl()=" + PlayLiveFlow.this.mLiveStreamBean.getLiveUrl());
                    if (TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.getLiveUrl())) {
                        PlayLiveFlow.this.mLivePlayCallback.notPlay(null);
                        return;
                    }
                    PlayLiveFlow.this.mLivePlayCallback.onLiveUrlPost(PlayLiveFlow.this.mLiveStreamBean, isToPlay);
                    if (isToPlay) {
                        PlayLiveFlow.this.checkPay(new CheckPayCallback() {
                            public void payCallback() {
                                PlayLiveFlow.this.mHandler.sendEmptyMessageDelayed(1001, 30);
                            }

                            public void freeCallback() {
                                PlayLiveFlow.this.playUrl(PlayLiveFlow.this.mLiveStreamBean);
                            }
                        });
                        return;
                    }
                    return;
                case NETWORK_NOT_AVAILABLE:
                    PlayLiveFlow.LogAddInfo("根据ChannelId请求直播码流，请求结果", "无网");
                    PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                    return;
                case NETWORK_ERROR:
                    PlayLiveFlow.LogAddInfo("根据ChannelId请求直播码流，请求结果", "网络连接失败");
                    PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                    return;
                case RESULT_ERROR:
                    PlayLiveFlow.LogAddInfo("根据ChannelId请求直播码流，请求结果", "数据解析失败");
                    PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                    return;
                default:
                    return;
            }
        }
    };
    public LivePlayStatisticsHelper mStatisticsHelper;

    private class ConsumeLiveTicket {
        private ConsumeLiveTicket() {
        }

        public void start() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_CONSUME_LIVE_TICKET);
            new LetvRequest().setUrl(PayCenterApi.getInstance().requestYingchaoTicketConsume(PlayLiveFlow.this.mLiveid, PreferencesManager.getInstance().getUserId())).setCache(new VolleyNoCache()).setTag(BasePlayLiveFlow.REQUEST_CONSUME_LIVE_TICKET).setParser(new YingchaoTicketConsumeParser()).setCallback(new SimpleResponse<YingchaoTicketConsumeResult>() {
                public void onNetworkResponse(VolleyRequest<YingchaoTicketConsumeResult> volleyRequest, YingchaoTicketConsumeResult result, DataHull hull, NetworkResponseState state) {
                    switch (state) {
                        case SUCCESS:
                            if (result != null && result.status.equals("1")) {
                                if (LetvConfig.isDebug()) {
                                    Toast.makeText(PlayLiveFlow.this.mContext, "消费券成功 -- 测试消息", 0).show();
                                }
                                PlayLiveFlow.this.checkPermission();
                                return;
                            } else if (!TextUtils.isEmpty(result.error)) {
                                PlayLiveFlow.this.checkPermission();
                                return;
                            } else {
                                return;
                            }
                        case NETWORK_NOT_AVAILABLE:
                        case NETWORK_ERROR:
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }

                public void onErrorReport(VolleyRequest<YingchaoTicketConsumeResult> volleyRequest, String errorInfo) {
                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1407", null, errorInfo, null, null, null, null, "pl", uuid);
                }
            }).add();
        }
    }

    private class QueryLivePrice {
        private QueryLivePrice() {
        }

        public void start() {
            if (!TextUtils.isEmpty(PlayLiveFlow.this.mLiveid) || TextUtils.isEmpty(PlayLiveFlow.this.mUniqueId)) {
                requestPrice();
                return;
            }
            new LetvRequest().setUrl(LiveApi.getInstance().getLiveDataById(PlayLiveFlow.this.mUniqueId)).setCache(new VolleyNoCache()).setParser(new LiveRemenBaseBeanParser()).setCallback(new SimpleResponse<LiveRemenBaseBean>() {
                public void onNetworkResponse(VolleyRequest<LiveRemenBaseBean> volleyRequest, LiveRemenBaseBean result, DataHull hull, NetworkResponseState state) {
                    if (result != null) {
                        LogInfo.log("live", "!!!!!QueryLivePricemLiveid =" + PlayLiveFlow.this.mLiveid);
                        PlayLiveFlow.this.mLiveid = result.screenings;
                        PlayLiveFlow.this.mPlayTime = result.getBeginTime();
                    }
                    QueryLivePrice.this.requestPrice();
                }
            }).add();
        }

        private void requestPrice() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_QUERY_LIVE_PRICE);
            VolleyRequest<LivePriceBean> request = new LetvRequest().setUrl(PayCenterApi.getInstance().requestYingchaoQueryPrice(PlayLiveFlow.this.mLiveid)).setCache(new VolleyNoCache()).setTag(BasePlayLiveFlow.REQUEST_QUERY_LIVE_PRICE);
            if (PlayLiveFlow.this.mLaunchMode == 105) {
                request.setParser(new LiveMusicPriceParser(LetvUtils.getTurnFromLiveid(PlayLiveFlow.this.mLiveid), LetvUtils.getGameFromLiveid(PlayLiveFlow.this.mLiveid)));
            } else {
                request.setParser(new LivePriceParser());
            }
            request.setCallback(new SimpleResponse<LivePriceBean>() {
                public void onNetworkResponse(VolleyRequest<LivePriceBean> volleyRequest, LivePriceBean result, DataHull hull, NetworkResponseState state) {
                    switch (state) {
                        case SUCCESS:
                            if (result != null) {
                                PlayLiveFlow.this.mLivePriceBean = result;
                                LogInfo.log("clf", "showPrice...mLivePriceBean=" + PlayLiveFlow.this.mLivePriceBean);
                                if (PlayLiveFlow.this.mLivePlayCallback != null) {
                                    LogInfo.log("clf", "showPrice...mLivePriceBean1");
                                    PlayLiveFlow.this.mLivePlayCallback.showPayPrice(result);
                                    return;
                                }
                                return;
                            }
                            return;
                        case NETWORK_NOT_AVAILABLE:
                        case NETWORK_ERROR:
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }

                public void onErrorReport(VolleyRequest<LivePriceBean> volleyRequest, String errorInfo) {
                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1409", null, errorInfo, null, null, null, null, "pl", uuid);
                }
            });
            request.add();
        }
    }

    private class QueryLiveTicket {
        private QueryLiveTicket() {
        }

        public void start() {
            new LetvRequest().setUrl(LiveApi.getInstance().getDate()).setCache(new VolleyNoCache()).setParser(new LiveDateInfoParser()).setCallback(new SimpleResponse<LiveDateInfo>() {
                public void onNetworkResponse(VolleyRequest<LiveDateInfo> volleyRequest, LiveDateInfo result, DataHull hull, NetworkResponseState state) {
                    if (result != null) {
                        BaseApplication.getInstance().setLiveDateInfo(result);
                    }
                    QueryLiveTicket.this.requestLiveTicket();
                }

                public void onErrorReport(VolleyRequest<LiveDateInfo> volleyRequest, String errorInfo) {
                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1410", null, errorInfo, null, null, null, null, "pl", uuid);
                }
            }).add();
        }

        private void requestLiveTicket() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_LIVE_TICKET);
            new LetvRequest().setUrl(PayCenterApi.getInstance().requestYingchaoTicketInfo(PlayLiveFlow.this.mLiveid, PreferencesManager.getInstance().getUserId())).setTag(BasePlayLiveFlow.REQUEST_LIVE_TICKET).setCache(new VolleyNoCache()).setParser(new YingchaoTicketInfoParser()).setCallback(new SimpleResponse<YingchaoTicketInfo>() {
                public void onNetworkResponse(VolleyRequest<YingchaoTicketInfo> volleyRequest, YingchaoTicketInfo result, DataHull hull, NetworkResponseState state) {
                    switch (state) {
                        case SUCCESS:
                            PlayLiveFlow.this.mLivePlayCallback.hidePayLoading();
                            if (result == null || !result.status.equals("1")) {
                                PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1002);
                                return;
                            } else if (TextUtils.isEmpty(result.count)) {
                                PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1002);
                                return;
                            } else {
                                int count = 0;
                                try {
                                    count = Integer.parseInt(result.count);
                                    PlayLiveFlow.this.mTicketCount = count;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (count <= 0 || !result.ticketStatus.equals("1")) {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1002);
                                    return;
                                } else if (PlayLiveFlow.this.compareTime(PlayLiveFlow.this.formatTime(BaseApplication.getInstance().getLiveDateInfo().date), PlayLiveFlow.this.formatTime(PlayLiveFlow.this.mPlayTime)) < 0) {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID);
                                    return;
                                } else {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID);
                                    return;
                                }
                            }
                        case NETWORK_NOT_AVAILABLE:
                        case NETWORK_ERROR:
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }

                public void onErrorReport(VolleyRequest<YingchaoTicketInfo> volleyRequest, String errorInfo) {
                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1409", null, errorInfo, null, null, null, null, "pl", uuid);
                }
            }).add();
        }
    }

    private class RequestAddBookLive {
        private String channel_code = this.live.liveType;
        private String channel_name = this.live.channelName;
        private LiveRemenBaseBean live;
        private String p_name = this.live.title;
        private String play_time = (this.live.getFullPlayDate() + " " + this.live.getPlayTime());
        private int position;

        public RequestAddBookLive(Context context, LiveRemenBaseBean program) {
            this.live = program;
        }

        public void start() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_ADD_BOOK_LIVE);
            new LetvRequest().setUrl(LiveApi.getInstance().getAddBookLive(Global.DEVICEID, this.play_time, this.p_name, this.channel_code, this.channel_name, this.live.id)).setTag(BasePlayLiveFlow.REQUEST_ADD_BOOK_LIVE).setCache(new VolleyNoCache()).setParser(new LiveResultParser()).setCallback(new SimpleResponse<LiveResultInfo>() {
                public void onNetworkResponse(VolleyRequest<LiveResultInfo> volleyRequest, LiveResultInfo result, DataHull hull, NetworkResponseState state) {
                    if (result != null && "1".equals(result.result)) {
                        PlayLiveFlow.this.mLivePlayCallback.bookLiveProgram(RequestAddBookLive.this.p_name, RequestAddBookLive.this.channel_name, RequestAddBookLive.this.channel_code, RequestAddBookLive.this.play_time, RequestAddBookLive.this.live.id);
                    }
                }
            }).add();
        }
    }

    private class RequestLiveJianQuan {
        public RequestLiveJianQuan() {
            PlayLiveFlow.this.mLivePlayCallback.showPayLoading();
            PlayLiveFlow.LogAddInfo("开始付费视频鉴权", "");
        }

        public void start() {
            PlayLiveFlow.LogAddInfo("开始付费视频鉴权", "mLiveid=" + PlayLiveFlow.this.mLiveid + ",mPlayTime=" + PlayLiveFlow.this.mPlayTime + ",mUniqueId=" + PlayLiveFlow.this.mUniqueId);
            if (!TextUtils.isEmpty(PlayLiveFlow.this.mLiveid) && !TextUtils.isEmpty(PlayLiveFlow.this.mPlayTime) && (!PlayLiveFlow.this.mPlayTime.contains(NetworkUtils.DELIMITER_LINE) || PlayLiveFlow.this.mPlayTime.indexOf(NetworkUtils.DELIMITER_LINE) != PlayLiveFlow.this.mPlayTime.lastIndexOf(NetworkUtils.DELIMITER_LINE))) {
                requestPrice();
            } else if (!TextUtils.isEmpty(PlayLiveFlow.this.mUniqueId)) {
                String live_url = LiveApi.getInstance().getLiveDataById(PlayLiveFlow.this.mUniqueId);
                PlayLiveFlow.LogAddInfo("鉴权时开始请求直播信息", "live_url=" + live_url);
                new LetvRequest().setUrl(live_url).setCache(new VolleyNoCache()).setParser(new LiveRemenBaseBeanParser()).setCallback(new SimpleResponse<LiveRemenBaseBean>() {
                    public void onNetworkResponse(VolleyRequest<LiveRemenBaseBean> volleyRequest, LiveRemenBaseBean result, DataHull hull, NetworkResponseState state) {
                        PlayLiveFlow.LogAddInfo("鉴权时请求直播信息返回", "result=" + result);
                        if (result != null) {
                            PlayLiveFlow.this.mLiveid = result.screenings;
                            PlayLiveFlow.this.mPlayTime = result.getBeginTime();
                            PlayLiveFlow.LogAddInfo("鉴权时请求直播信息返回", "mLiveid=" + PlayLiveFlow.this.mLiveid + ",mPlayTime=" + PlayLiveFlow.this.mPlayTime);
                        }
                        RequestLiveJianQuan.this.requestPrice();
                    }
                }).add();
            }
        }

        private void requestPrice() {
            String price_url = PayCenterApi.getInstance().requestYingchaoQueryPrice(PlayLiveFlow.this.mLiveid);
            PlayLiveFlow.LogAddInfo("请求价格", "price_url=" + price_url);
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_LIVE_JIANQUAN);
            VolleyRequest<LivePriceBean> request = new LetvRequest().setUrl(price_url).setCache(new VolleyNoCache()).setTag(BasePlayLiveFlow.REQUEST_LIVE_JIANQUAN);
            if (PlayLiveFlow.this.mLaunchMode == 105) {
                request.setParser(new LiveMusicPriceParser(LetvUtils.getTurnFromLiveid(PlayLiveFlow.this.mLiveid), LetvUtils.getGameFromLiveid(PlayLiveFlow.this.mLiveid)));
            } else {
                request.setParser(new LivePriceParser());
            }
            request.setCallback(new SimpleResponse<LivePriceBean>() {
                public void onNetworkResponse(VolleyRequest<LivePriceBean> volleyRequest, LivePriceBean result, DataHull hull, NetworkResponseState state) {
                    PlayLiveFlow.LogAddInfo("请求价格返回", "result=" + result);
                    PlayLiveFlow.this.mLivePriceBean = result;
                    RequestLiveJianQuan.this.requestJianquan();
                }

                public void onErrorReport(VolleyRequest<LivePriceBean> volleyRequest, String errorInfo) {
                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1409", null, errorInfo, null, null, null, null, "pl", uuid);
                }
            });
            request.add();
        }

        private void requestJianquan() {
            String jianquan_url = PayCenterApi.getInstance().requestYingchaoJianquan(PlayLiveFlow.this.mUniqueId, PlayLiveFlow.this.mLiveid, PlayLiveFlow.this.mLiveStreamBean.getStreamId(), PreferencesManager.getInstance().getUserId());
            PlayLiveFlow.LogAddInfo("开始鉴权", "jianquan_url=" + jianquan_url);
            new LetvRequest().setUrl(jianquan_url).setCache(new VolleyNoCache()).setParser(new YingchaoJianquanParser()).setCallback(new SimpleResponse<YingchaoJianquanResult>() {
                public void onNetworkResponse(VolleyRequest<YingchaoJianquanResult> volleyRequest, YingchaoJianquanResult result, DataHull hull, NetworkResponseState state) {
                    PlayLiveFlow.LogAddInfo("请求鉴权返回", "state=" + state + ",result=" + result);
                    switch (state) {
                        case SUCCESS:
                            if (result != null) {
                                PlayLiveFlow.LogAddInfo("鉴权状态", "result.status=" + result.status);
                                if (result.status.equals("1")) {
                                    PlayLiveFlow.this.mLivePlayCallback.hidePayLoading();
                                    PlayLiveFlow.this.mIsPayed = true;
                                    PlayLiveFlow.LogAddInfo("鉴权成功", "");
                                    PlayLiveFlow.this.mLivePlayCallback.removeLivePayLayout();
                                    PlayLiveFlow.this.mToken = result.token;
                                    PlayLiveFlow.this.mLiveStreamBean = PlayLiveFlow.this.mLiveStreamBean.addPayTokenUid(result.token);
                                    PlayLiveFlow.this.mHandler.sendEmptyMessage(1001);
                                    return;
                                }
                                PlayLiveFlow.LogAddInfo("鉴权失败", "result.status=" + result.status);
                                if (!result.code.equals("1004")) {
                                    PlayLiveFlow.this.mLivePlayCallback.hidePayLoading();
                                }
                                if (result.code.equals("1004")) {
                                    new QueryLiveTicket().start();
                                    return;
                                } else if (result.code.equals("1011")) {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1004);
                                    return;
                                } else if (result.code.equals("1012")) {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1004);
                                    return;
                                } else if (result.code.equals("1013")) {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1004);
                                    return;
                                } else if (PreferencesManager.getInstance().isLogin()) {
                                    PlayLiveFlow.this.mLivePlayCallback.showPayLayout(1002);
                                    return;
                                } else {
                                    return;
                                }
                            } else if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                                return;
                            } else {
                                return;
                            }
                        case NETWORK_NOT_AVAILABLE:
                        case NETWORK_ERROR:
                            PlayLiveFlow.LogAddInfo("请求鉴权", "网络错误");
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            PlayLiveFlow.LogAddInfo("请求鉴权", "数据错误");
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }

                public void onErrorReport(VolleyRequest<YingchaoJianquanResult> volleyRequest, String errorInfo) {
                    PlayLiveFlow.LogAddInfo("请求鉴权,请求错误", "errorInfo=" + errorInfo);
                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1406", null, errorInfo, null, null, null, null, "pl", uuid);
                }
            }).add();
        }
    }

    private class RequestLiveLunboProgramList {
        private int direction;
        private boolean hasToPlayResult;
        private boolean isShowListLoading;
        private long time1 = System.currentTimeMillis();
        private long time2;
        private String url;

        public RequestLiveLunboProgramList(int direction, boolean isShowListLoading, boolean hasToPlayResult) {
            this.direction = direction;
            this.isShowListLoading = isShowListLoading;
            this.hasToPlayResult = hasToPlayResult;
            this.url = LiveApi.getInstance().getLunboWeishiData(PlayLiveFlow.this.mChannelId, LetvUtils.getLunboOrWeishiType(PlayLiveFlow.this.mLaunchMode));
            LogInfo.log("clf", "RequestLiveLunboProgramList1...isShowListLoading=" + isShowListLoading);
            PlayLiveFlow.LogAddInfo("processLiveRoomResult", "----------hasToPlayResult=" + hasToPlayResult);
            LogInfo.log("live", "RequestLiveLunboProgramList.....url=" + this.url);
        }

        public void start() {
            if (this.hasToPlayResult) {
                PlayLiveFlow.this.requestUrlByChannelId(false, true, PlayLiveFlow.this.mChannelId);
            }
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_LIVE_LUNBO_PROGRAMLIST);
            new LetvRequest().setUrl(this.url).setTag(BasePlayLiveFlow.REQUEST_LIVE_LUNBO_PROGRAMLIST).setParser(new LiveLunboWeishiChannelProgramListParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveBeanLeChannelProgramList>() {
                public void onNetworkResponse(VolleyRequest<LiveBeanLeChannelProgramList> volleyRequest, LiveBeanLeChannelProgramList result, DataHull hull, NetworkResponseState state) {
                    if (result != null && result.mLiveLunboProgramListBean.size() > 0) {
                        PlayLiveFlow.this.mPlayingProgram = (ProgramEntity) ((LiveBeanLeChannelProgramList.LiveLunboProgramListBean) result.mLiveLunboProgramListBean.get(0)).programs.get(0);
                        if (PlayLiveFlow.this.mPlayingProgram != null) {
                            PlayLiveFlow.this.mCurrentProgram = new CurrentProgram(PlayLiveFlow.this.mPlayingProgram.playTime, PlayLiveFlow.this.mPlayingProgram.id, PlayLiveFlow.this.mChannelId);
                            PlayLiveFlow.this.mProgramName = PlayLiveFlow.this.mPlayingProgram.title;
                            PlayLiveFlow.this.mLivePlayCallback.setmProgramName(PlayLiveFlow.this.mPlayingProgram.title);
                            LogInfo.log("clf", "轮播卫视RequestLiveLunboProgramList当前正在播放节目：" + PlayLiveFlow.this.mPlayingProgram.title);
                            PlayLiveFlow.this.mLivePlayCallback.onProgramChange(PlayLiveFlow.this.mCurrentProgram);
                        }
                    }
                    RequestLiveLunboProgramList.this.requestPrograms();
                    if (state != NetworkResponseState.SUCCESS) {
                        String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                        DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", LetvErrorCode.REQUEST_LUNBOWEISHI_ERROR, null, StatisticsUtils.getSubErroCode(state, hull), null, null, null, null, "pl", uuid);
                    }
                }
            }).add();
        }

        private void requestPrograms() {
            String programsUrl = "";
            if (this.direction == 0) {
                programsUrl = LiveApi.getInstance().getLiveNearPrograms(PlayLiveFlow.this.mChannelId);
            } else if (PlayLiveFlow.this.mLunboData != null) {
                List<ProgramEntity> programs = PlayLiveFlow.this.mLunboData.programs;
                if (programs != null && programs.size() > 0) {
                    String pgid = "0";
                    if (this.direction == 2) {
                        pgid = ((ProgramEntity) programs.get(0)).id;
                    } else if (programs.size() > 1) {
                        pgid = ((ProgramEntity) programs.get(programs.size() - 1)).id;
                    }
                    LogInfo.log("clf", "请求轮播卫视节目单: RequestLiveLunboProgramList" + pgid + " , channelId = " + PlayLiveFlow.this.mChannelId);
                    programsUrl = LiveApi.getInstance().getLiveProgramsInc(this.direction, pgid);
                }
            }
            new LetvRequest().setCache(new VolleyNoCache()).setParser(new LiveLunboProgramListParser()).setUrl(programsUrl).setCallback(new SimpleResponse<LiveLunboProgramListBean>() {
                public void onNetworkResponse(VolleyRequest<LiveLunboProgramListBean> volleyRequest, LiveLunboProgramListBean result, DataHull hull, NetworkResponseState state) {
                    RequestLiveLunboProgramList.this.time2 = System.currentTimeMillis();
                    if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                        PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mRequestTimeInfo.mTimeRequestProgramList = RequestLiveLunboProgramList.this.time2 - RequestLiveLunboProgramList.this.time1;
                    }
                    LogInfo.log("clf", "RequestLiveLunboProgramList...state=" + state);
                    switch (state) {
                        case NETWORK_NOT_AVAILABLE:
                            if (RequestLiveLunboProgramList.this.isShowListLoading) {
                                PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(2);
                                return;
                            }
                            return;
                        case NETWORK_ERROR:
                            if (RequestLiveLunboProgramList.this.isShowListLoading) {
                                PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(3);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            if (RequestLiveLunboProgramList.this.isShowListLoading) {
                                PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(4);
                                return;
                            }
                            return;
                        default:
                            LogInfo.log("clf", "RequestLiveLunboProgramList...result=" + result);
                            if (result != null) {
                                if (RequestLiveLunboProgramList.this.direction == 0) {
                                    PlayLiveFlow.this.mLunboData = result;
                                }
                                List<ProgramEntity> programs = PlayLiveFlow.this.mLunboData.programs;
                                if (programs != null && programs.size() > 0) {
                                    ArrayList<ProgramEntity> pgs = result.programs;
                                    if (pgs != null && pgs.size() != 0) {
                                        if (RequestLiveLunboProgramList.this.direction != 0) {
                                            programs.addAll(pgs);
                                        }
                                        LogInfo.log("clf", "RequestLiveLunboProgramList 节目单数量:" + PlayLiveFlow.this.mLunboData.programs.size());
                                    } else if (RequestLiveLunboProgramList.this.direction != 0) {
                                        PlayLiveFlow.this.mLivePlayCallback.toastNoMore();
                                    }
                                }
                                LogInfo.log("clf", "RequestLiveLunboProgramList2...isShowListLoading=" + RequestLiveLunboProgramList.this.isShowListLoading);
                                if (programs != null && programs.size() > 0) {
                                    PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(1);
                                    return;
                                } else if (RequestLiveLunboProgramList.this.isShowListLoading) {
                                    PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(4);
                                    return;
                                } else {
                                    return;
                                }
                            } else if (RequestLiveLunboProgramList.this.isShowListLoading) {
                                PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(4);
                                return;
                            } else {
                                return;
                            }
                    }
                }

                public void onErrorReport(VolleyRequest<LiveLunboProgramListBean> volleyRequest, String errorInfo) {
                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.getActivity(), "0", "0", LetvErrorCode.LTURLModule_Live_ChannelBill, null, errorInfo, null, null, null, null);
                }
            }).add();
        }
    }

    private class RequestLiveRoomList {
        private boolean hasToPlay;
        private boolean isShowListLoading;
        private long time1 = System.currentTimeMillis();
        private long time2;
        private String url = LiveApi.getInstance().getLiveRoomHalfPlayerData(LiveRoomConstant.CHANNEL_TYPE_ALL, "2");

        public RequestLiveRoomList(boolean isShowListLoading, boolean hasToPlay) {
            this.isShowListLoading = isShowListLoading;
            this.hasToPlay = hasToPlay;
            LogInfo.log("live", "RequestLiveRoomList.....");
        }

        private void start() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_LIVE_ROOMLIST);
            new LetvRequest().setUrl(this.url).setTag(BasePlayLiveFlow.REQUEST_LIVE_ROOMLIST).setParser(new LiveRoomHalfPlayerDataParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveRemenListBean>() {
                public void onNetworkResponse(VolleyRequest<LiveRemenListBean> volleyRequest, LiveRemenListBean liveRemenListBean, DataHull hull, NetworkResponseState state) {
                    if (!(state == NetworkResponseState.SUCCESS || hull == null)) {
                        String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                        DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1404-" + hull.apiState, null, null, null, null, null, null, "pl", uuid);
                    }
                    if (liveRemenListBean != null) {
                        int playPosition = PlayLiveFlow.this.findPlayItemIndex(liveRemenListBean.mRemenList);
                        LogInfo.log("clf", ">>playPosition : " + playPosition + " uniqueId : " + PlayLiveFlow.this.mUniqueId);
                        if (playPosition != -1 || TextUtils.isEmpty(PlayLiveFlow.this.mUniqueId)) {
                            PlayLiveFlow.this.processLiveRoomResult(liveRemenListBean, RequestLiveRoomList.this.hasToPlay, playPosition);
                            return;
                        }
                        final LiveRemenListBean liveRemenListBean2 = liveRemenListBean;
                        final int i = playPosition;
                        new LetvRequest().setCache(new VolleyNoCache()).setUrl(PlayLiveFlow.this.mUniqueId).setUrl(LiveApi.getInstance().getLiveDataById(PlayLiveFlow.this.mUniqueId)).setParser(new LiveRemenBaseBeanParser()).setCallback(new SimpleResponse<LiveRemenBaseBean>() {
                            public void onNetworkResponse(VolleyRequest<LiveRemenBaseBean> volleyRequest, LiveRemenBaseBean liveRemenBaseBean, DataHull hull, NetworkResponseState state) {
                                if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                                    RequestLiveRoomList.this.time2 = System.currentTimeMillis();
                                    PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mRequestTimeInfo.mTimeRequestProgramList = RequestLiveRoomList.this.time2 - RequestLiveRoomList.this.time1;
                                }
                                if (state != NetworkResponseState.SUCCESS) {
                                    String uuid = (PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null) ? "" : PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.getUuidTime(PlayLiveFlow.this.mContext);
                                    DataStatistics.getInstance().sendErrorInfo(PlayLiveFlow.this.mContext, "0", "0", "1404", null, null, null, null, null, null, "pl", uuid);
                                    switch (state) {
                                        case NETWORK_NOT_AVAILABLE:
                                            if (RequestLiveRoomList.this.isShowListLoading) {
                                                PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(2);
                                                return;
                                            }
                                            return;
                                        case NETWORK_ERROR:
                                            if (RequestLiveRoomList.this.isShowListLoading) {
                                                PlayLiveFlow.this.mLivePlayCallback.notifyHalfLivePlayFragment(3);
                                                return;
                                            }
                                            return;
                                    }
                                }
                                PlayLiveFlow.this.mLiveRemenBase = liveRemenBaseBean;
                                PlayLiveFlow.this.processLiveRoomResult(liveRemenListBean2, RequestLiveRoomList.this.hasToPlay, i);
                            }
                        }).add();
                    }
                }
            }).add();
        }
    }

    private class RequestPayInfoById {
        private boolean toPlay;
        private String zhiboId;

        public RequestPayInfoById(String id, boolean toPlay) {
            this.zhiboId = id;
            this.toPlay = toPlay;
            if (toPlay) {
                PlayLiveFlow.this.mLivePlayCallback.loading(false);
            }
            PlayLiveFlow.LogAddInfo("开始请求直播详情zhiboId", this.zhiboId + ",toPlay=" + toPlay);
        }

        public void start() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_PAYINFO_BY_ID);
            String url = LiveApi.getInstance().getLiveDataById(this.zhiboId);
            PlayLiveFlow.LogAddInfo("开始请求直播详情 url", url);
            new LetvRequest().setUrl(url).setTag(BasePlayLiveFlow.REQUEST_PAYINFO_BY_ID).setCache(new VolleyNoCache()).setParser(new LiveRemenBaseBeanParser()).setCallback(new SimpleResponse<LiveRemenBaseBean>() {
                public void onNetworkResponse(VolleyRequest<LiveRemenBaseBean> volleyRequest, LiveRemenBaseBean result, DataHull hull, NetworkResponseState state) {
                    boolean z = true;
                    PlayLiveFlow.LogAddInfo("请求直播详情，返回结果", "state=" + state + "," + result);
                    switch (state) {
                        case SUCCESS:
                            PlayLiveFlow.this.mUniqueId = RequestPayInfoById.this.zhiboId;
                            if (result != null) {
                                PlayLiveFlow.this.mLiveBean = result;
                                PlayLiveFlow.this.mLiveid = result.screenings;
                                PlayLiveFlow.this.updatePartId(result);
                                PlayLiveFlow playLiveFlow = PlayLiveFlow.this;
                                if (result.isDanmaku != 1) {
                                    z = false;
                                }
                                playLiveFlow.mIsDanmaku = z;
                                PlayLiveFlow.this.mLivePlayCallback.changeLaunchMode(result);
                                if ("sports".equals(result.liveType)) {
                                    PlayLiveFlow.this.mHomeImgUrl = result.homeImgUrl;
                                    PlayLiveFlow.this.mGuestImgUrl = result.guestImgUrl;
                                    PlayLiveFlow.this.mHomeName = result.home;
                                    PlayLiveFlow.this.mGuestName = result.guest;
                                }
                                PlayLiveFlow.this.mPlayTime = result.getBeginTime();
                                PlayLiveFlow.this.mProgramName = result.title;
                                if (!TextUtils.isEmpty(result.isPay)) {
                                    PlayLiveFlow.this.mIsPay = result.isPay.equals("1");
                                }
                                PlayLiveFlow.this.mLivePlayCallback.setmProgramName(result.title);
                                PlayLiveFlow.this.requestUrlByChannelId(PlayLiveFlow.this.mIsPay, RequestPayInfoById.this.toPlay, result.selectId);
                                return;
                            } else if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getResources().getString(R.string.data_request_error));
                                return;
                            } else {
                                return;
                            }
                        case NETWORK_NOT_AVAILABLE:
                        case NETWORK_ERROR:
                            PlayLiveFlow.LogAddInfo("请求直播详情，返回结果", "网络出错");
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            PlayLiveFlow.LogAddInfo("请求直播详情，返回结果", "数据错误");
                            if (!PlayLiveFlow.this.mLivePlayCallback.isPlaying()) {
                                PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getResources().getString(R.string.data_request_error));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }

                public void onErrorReport(VolleyRequest<LiveRemenBaseBean> request, String errorInfo) {
                    super.onErrorReport(request, errorInfo);
                    PlayLiveFlow.LogAddInfo("请求直播详情，请求错误 errorInfo", errorInfo);
                }
            }).add();
        }
    }

    private class RequestPlayingProgram {
        private String channelId;

        public RequestPlayingProgram(String channelId) {
            this.channelId = channelId;
        }

        private void start() {
            Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_PLAYING_PROGRAM);
            new LetvRequest().setUrl(LiveApi.getInstance().getLunboWeishiData(this.channelId, LetvUtils.getLunboOrWeishiType(PlayLiveFlow.this.mLaunchMode))).setTag(BasePlayLiveFlow.REQUEST_PLAYING_PROGRAM).setParser(new LiveLunboWeishiChannelProgramListParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveBeanLeChannelProgramList>() {
                public void onNetworkResponse(VolleyRequest<LiveBeanLeChannelProgramList> volleyRequest, LiveBeanLeChannelProgramList result, DataHull hull, NetworkResponseState state) {
                    if (result != null) {
                        PlayLiveFlow.this.mPlayingProgram = (ProgramEntity) ((LiveBeanLeChannelProgramList.LiveLunboProgramListBean) result.mLiveLunboProgramListBean.get(0)).programs.get(0);
                        PlayLiveFlow.this.mProgramName = PlayLiveFlow.this.mPlayingProgram.title;
                        PlayLiveFlow.this.mLivePlayCallback.setmProgramName(PlayLiveFlow.this.mPlayingProgram.title);
                        PlayLiveFlow.this.mCurrentProgram = new CurrentProgram(PlayLiveFlow.this.mPlayingProgram.playTime, PlayLiveFlow.this.mPlayingProgram.id, PlayLiveFlow.this.mChannelId);
                        PlayLiveFlow.this.mLivePlayCallback.onProgramChange(PlayLiveFlow.this.mCurrentProgram);
                    }
                }
            }).add();
        }
    }

    public PlayLiveFlow(Context context, int launchMode, boolean onlyFull, LivePlayCallback livePlayCallback) {
        this.mContext = context;
        this.mLaunchMode = launchMode;
        this.mOnlyFull = onlyFull;
        this.mLivePlayCallback = livePlayCallback;
        this.mStatisticsHelper = new LivePlayStatisticsHelper();
        initBaseData();
    }

    protected void initLiveData() {
        initLiveStreamUrl();
        initAds();
    }

    public void run() {
        LogAddInfo("启动直播播放", "run");
        LogAddInfo("uniqueId", this.mUniqueId);
        LogAddInfo("cid", this.mChannelId);
        LogAddInfo("mPushLiveId", this.mPushLiveId);
        LogAddInfo("启动模式", this.mLaunchMode + "");
        if (this.mStatisticsHelper != null) {
            this.mStatisticsHelper.mStatisticsInfo.mIsInLiveHall = false;
        }
        if (this.mLaunchMode == 113) {
            this.mHandler.sendEmptyMessage(1001);
            return;
        }
        if (LiveLunboUtils.isLunboType(this.mLaunchMode) || this.mLaunchMode == 102) {
            if (!TextUtils.isEmpty(this.mChannelId)) {
                if (TextUtils.isEmpty(this.mSignal) || !this.mSignal.equals("9")) {
                    LogAddInfo("播放轮播卫视视频mChannelId", this.mChannelId);
                    requestUrlByChannelId(false, true, this.mChannelId);
                } else {
                    LogAddInfo("播放咪咕视频mSignal", this.mSignal);
                    requestMiGuLiveURLByChannelId(this.mChannelId, 0);
                }
            }
            if (this.mOnlyFull) {
                requestPlayingProgram();
            } else {
                requestData(true, false);
            }
            startAutoRefresh();
        }
        if (!TextUtils.isEmpty(this.mPushLiveId)) {
            this.mUniqueId = this.mPushLiveId;
            if (this.mOnlyFull) {
                LogAddInfo("体育等视频全屏播放mPushLiveId", this.mPushLiveId);
                requestPayInfoById(this.mPushLiveId, true);
            } else {
                LogAddInfo("有mPushLiveId优先拉详情播放 mPushLiveId", this.mPushLiveId);
                requestPayInfoById(this.mPushLiveId, true);
                requestData(true, false);
                startAutoRefresh();
            }
            this.mWebPlayExeception = false;
        } else if (!TextUtils.isEmpty(this.mLiveStreamBean.getLiveUrl())) {
            LogAddInfo("mPushLiveId未空，码流地址不为空 mLiveStreamBean.getLiveUrl()", this.mLiveStreamBean.getLiveUrl());
            if (!this.mOnlyFull) {
                requestData(true, false);
                startAutoRefresh();
            }
            checkPay(new CheckPayCallback() {
                public void payCallback() {
                    PlayLiveFlow.this.mHandler.sendEmptyMessageDelayed(1001, 30);
                }

                public void freeCallback() {
                    PlayLiveFlow.this.mHandler.sendEmptyMessageDelayed(1001, 30);
                }
            });
        }
    }

    private void startAutoRefresh() {
        this.mHandler.removeMessages(1000);
        this.mHandler.sendEmptyMessageDelayed(1000, 120000);
    }

    public void onNetChange() {
        int currentState = com.letv.core.utils.NetworkUtils.getNetworkType();
        LogAddInfo("网络变化 当前网络状态currentState", "" + currentState);
        if (currentState != this.mOldNetState) {
            switch (currentState) {
                case 1:
                    this.mLivePlayCallback.dismisNetChangeDialog();
                    adsOnResume();
                    if (this.mIsPlayFreeUrl) {
                        this.mIsPlayFreeUrl = false;
                        this.mIsWo3GUser = false;
                        if (this.mLiveStreamBean != null) {
                            requestRealLink(this.mLiveStreamBean, getActivity());
                        }
                    }
                    LogInfo.log("clf", "onNetChange NETTYPE_WIFI mRealLink null " + TextUtils.isEmpty(this.mRealUrl));
                    if (!this.mPlayInterupted) {
                        if (!TextUtils.isEmpty(this.mRealUrl)) {
                            this.mLivePlayCallback.star();
                            break;
                        }
                        LogInfo.log("clf", "---------------3G切Wifi 开始重新请求数据");
                        run();
                        break;
                    }
                    break;
                case 2:
                case 3:
                    LogInfo.log("---NETTYPE_3G 1");
                    if (this.mLaunchMode != 102 || !isMigu()) {
                        replaceUrlToFreeurlForNetChange();
                        break;
                    } else {
                        run();
                        break;
                    }
            }
            this.mOldNetState = currentState;
            if (currentState == 0) {
                this.mLivePlayCallback.onPlayNetNull();
                this.mPlayInterupted = true;
                LogInfo.log("clf", "播放时网络断开");
            } else {
                LogInfo.log("clf", "onNetChange playInterupted=" + this.mPlayInterupted);
                if (this.mPlayInterupted) {
                    this.mLivePlayCallback.loading(false);
                    if (com.letv.core.utils.NetworkUtils.isWifi()) {
                        this.mLivePlayCallback.playNetWhileNotWait(this.mRealUrl);
                    }
                    LogInfo.log("3_g", "自动联网");
                    run();
                } else if (com.letv.core.utils.NetworkUtils.isWifi()) {
                    run();
                }
            }
            this.mLivePlayCallback.notifyControllBarNetChange();
        }
    }

    public void onRequestErr() {
        if (this.mLiveStreamBean.getLiveUrl() != null) {
            if (this.mUserControllLevel) {
                setStreamTypeFromUser(this.mUserStreamType);
            } else {
                setStreamTypeFromCpu(this.mLiveStreamBean, this.mDefaultLow);
            }
            this.mLivePlayCallback.updateHdButton(this.mLiveStreamBean);
            checkPay(new CheckPayCallback() {
                public void payCallback() {
                    PlayLiveFlow.this.playUrl(PlayLiveFlow.this.mLiveStreamBean);
                }

                public void freeCallback() {
                    PlayLiveFlow.this.playUrl(PlayLiveFlow.this.mLiveStreamBean);
                }
            });
        } else if (TextUtils.isEmpty(this.mPushLiveId)) {
            requestData(false, true);
        } else {
            run();
        }
    }

    public void onPlayFailed() {
        if (this.mLiveStreamBean.getLiveUrl() != null) {
            if (this.mUserControllLevel) {
                setStreamTypeFromUser(this.mUserStreamType);
            } else {
                setStreamTypeFromCpu(this.mLiveStreamBean, this.mDefaultLow);
            }
            this.mLivePlayCallback.updateHdButton(this.mLiveStreamBean);
            playUrl(this.mLiveStreamBean);
            return;
        }
        requestData(false, true);
    }

    public void onActiResultProcess() {
        this.mStatisticsHelper.clear();
        this.mIsPlayedAd = false;
        this.mLivePlayCallback.loading(false);
        stopAdsPlayBack();
        this.mLivePlayCallback.pause();
        this.mLivePlayCallback.stopPlayback();
        this.mLivePlayCallback.hide3gLayout();
        run();
    }

    public void loadPrograms(int direction) {
        requestLiveLunboProgramList(direction, false, false);
    }

    private void resetStream() {
        this.mLiveStreamBean = new LiveStreamBean();
    }

    public void changePlay(LiveRemenBaseBean live, boolean isPay, boolean isPlaying) {
        LogAddInfo("changePlay", "----------");
        stopAdsPlayBack();
        this.mIsPlayedAd = false;
        this.mIsPay = isPay;
        this.mAdsFinished = false;
        this.mIsPayed = false;
        this.mLiveid = "";
        this.mLiveBean = null;
        if (live != null) {
            if (TextUtils.isEmpty(this.mPushLiveId)) {
                this.mUniqueId = live.id;
            } else {
                this.mPushLiveId = live.id;
                this.mUniqueId = live.id;
            }
            this.mLiveBean = live;
            this.mPlayTime = live.getBeginTime();
            updatePartId(live);
            if (live.ch.equals(LiveRoomConstant.LIVE_TYPE_SPORT)) {
                this.mHomeImgUrl = live.homeImgUrl;
                this.mGuestImgUrl = live.guestImgUrl;
                this.mHomeName = live.home;
                this.mGuestName = live.guest;
            } else {
                this.mHomeImgUrl = null;
                this.mGuestImgUrl = null;
                this.mHomeName = "";
                this.mGuestName = "";
            }
            this.mLiveRemenBase = live;
            this.mCode = this.mLiveRemenBase.liveType;
            this.mChannelId = this.mLiveRemenBase.selectId;
            this.mChannelName = this.mLiveRemenBase.channelName;
            LogInfo.log("clf", "changePlay...mCode=" + this.mCode + ",mChannelId=" + this.mChannelId + ",mChannelName=" + this.mChannelName);
            if (!TextUtils.isEmpty(live.selectId)) {
                resetStream();
                requestUrlByChannelId(isPay, isPlaying, live.selectId);
            }
            statisticsLaunch(false);
        }
    }

    public void changeLunboChannel(String chName, String chEnName, String chId, String pName, String chNum, String signal) {
        this.mChannelName = chName;
        this.mCode = chEnName;
        this.mChannelId = chId;
        this.mProgramName = pName;
        this.mChannelNum = chNum;
        this.mSignal = signal;
        Bundle bundle = new Bundle();
        bundle.putString("code", this.mCode);
        bundle.putString(PlayConstant.LIVE_CHANNEL_NAME, this.mChannelName);
        bundle.putString(PlayConstant.LIVE_CHANNEL_ID, this.mChannelId);
        bundle.putString(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER, this.mChannelNum);
        bundle.putBoolean(PlayConstant.LIVE_FULL_ONLY, this.mOnlyFull);
        BaseApplication.getInstance().setLiveLunboBundle(bundle);
        stopAdsPlayBack();
        this.mIsPlayedAd = false;
        this.mAdsFinished = false;
        resetStream();
        run();
        statisticsLaunch(false);
    }

    public void changeMultiBranch(String channelId) {
        requestUrlByChannelId(false, true, channelId);
    }

    private void initAds() {
        if (AdsManagerProxy.isADPluginEnable()) {
            this.mPlayAdFragment = new AdPlayFragmentProxy(this.mContext);
            this.mPlayAdFragment.setClientFunction(this.mAdsCallback);
            this.mPlayAdFragment.setAdListener(this.mADListener);
            this.mLivePlayCallback.addAdsFragment(this.mPlayAdFragment);
        }
    }

    public void stopAdsPlayBack() {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.setADPause(false);
            this.mPlayAdFragment.stopPlayback(false);
        }
    }

    public void setAdsMuteViewStatus(int progress) {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.setMuteViewStatus(progress);
        }
    }

    public void lockScreenPause() {
        if (this.mPlayAdFragment != null && !this.mPlayAdFragment.isFinishAd()) {
            this.mPlayAdFragment.onPause();
        }
    }

    public void unLockSceenResume() {
        if (this.mPlayAdFragment != null && !this.mPlayAdFragment.isFinishAd()) {
            this.mPlayAdFragment.onResume();
        }
    }

    public void adsOnResume() {
        if (this.mPlayAdFragment != null && this.mReqhasAd) {
            this.mPlayAdFragment.onResume();
        }
    }

    public void closePauseAd() {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.closePauseAd();
        }
    }

    public void setPauseAd(boolean isPause) {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.setADPause(isPause);
        }
    }

    public boolean isAdsPlaying() {
        if (this.mPlayAdFragment != null) {
            return this.mPlayAdFragment.isPlaying();
        }
        return false;
    }

    public boolean isAdsFinished() {
        return this.mAdsFinished;
    }

    public void setAdsFinished(boolean isFinished) {
        this.mAdsFinished = isFinished;
    }

    public boolean isPauseAd() {
        if (this.mPlayAdFragment != null) {
            return this.mPlayAdFragment.isPauseAd();
        }
        return false;
    }

    public boolean isHaveFrontAds() {
        if (this.mPlayAdFragment != null) {
            return this.mPlayAdFragment.isHaveFrontAds();
        }
        return false;
    }

    public static void LogAddInfo(String key, String value) {
        String tempInfo = key + " : " + value + "-->";
        LetvLogApiTool.getInstance().saveExceptionInfo(key + " : " + value + "-->");
        LogInfo.log("live", key + "--->" + value);
    }

    private void initLiveStreamUrl() {
        Intent intent = ((Activity) this.mContext).getIntent();
        if (intent != null) {
            this.mLiveStreamBean = new LiveStreamBean();
            String url350 = intent.getStringExtra(PlayConstant.LIVE_URL_350);
            String url1000 = intent.getStringExtra(PlayConstant.LIVE_URL_1000);
            String url1300 = intent.getStringExtra(PlayConstant.LIVE_URL_1300);
            String url720 = intent.getStringExtra(PlayConstant.LIVE_URL_720);
            String urlUnknown = intent.getStringExtra("url");
            this.mLiveStreamBean.liveUrl350 = url350;
            this.mLiveStreamBean.streamId350 = intent.getStringExtra(PlayConstant.LIVE_STREAMID_350);
            this.mLiveStreamBean.liveUrl1000 = url1000;
            this.mLiveStreamBean.streamId1000 = intent.getStringExtra(PlayConstant.LIVE_STREAMID_1000);
            this.mLiveStreamBean.liveUrl1300 = url1300;
            this.mLiveStreamBean.streamId1300 = intent.getStringExtra(PlayConstant.LIVE_STREAMID_1300);
            this.mLiveStreamBean.liveUrl720p = url720;
            this.mLiveStreamBean.streamId720p = intent.getStringExtra(PlayConstant.LIVE_STREAMID_720);
            if (!TextUtils.isEmpty(urlUnknown) && urlUnknown.contains("\\")) {
                urlUnknown = urlUnknown.replace("\\", "");
            }
            this.mLiveStreamBean.liveUrlUnknown = urlUnknown;
            this.mLiveStreamBean.streamIdUnknown = intent.getStringExtra(PlayConstant.LIVE_STREAMID);
            this.mDefaultLow = intent.getBooleanExtra(PlayConstant.LIVE_IS_LOW, false);
            setStreamTypeFromCpu(this.mLiveStreamBean, this.mDefaultLow);
            if (this.mLaunchMode != 113) {
                this.mLivePlayCallback.updateHdButton(this.mLiveStreamBean);
            }
        }
    }

    private void setStreamTypeFromUser(StreamType type) {
        this.mLiveStreamBean.streamType = type;
        if (TextUtils.isEmpty(this.mLiveStreamBean.getLiveUrl())) {
            setStreamTypeFromCpu(this.mLiveStreamBean, this.mDefaultLow);
        }
    }

    private void setStreamTypeFromCpu(LiveStreamBean bean, boolean defaultLow) {
        String url350 = bean.liveUrl350;
        String url1000 = bean.liveUrl1000;
        String url1300 = bean.liveUrl1300;
        String url720p = bean.liveUrl720p;
        String urlUnknown = bean.liveUrlUnknown;
        LogInfo.log("clf", "url350=" + url350);
        LogInfo.log("clf", "url1000=" + url1000);
        LogInfo.log("clf", "urlUnknown=" + urlUnknown);
        switch (PreferencesManager.getInstance().getPlayLevel()) {
            case 1:
                setUserStreamType(StreamType.STREAM_350);
                break;
            case 2:
                setUserStreamType(StreamType.STREAM_1000);
                break;
            case 3:
                setUserStreamType(StreamType.STREAM_1300);
                break;
            case 4:
                setUserStreamType(StreamType.STREAM_720p);
                break;
        }
        if (TextUtils.isEmpty(this.mLiveStreamBean.getLiveUrl())) {
            LogInfo.log("clf", "BaseApplication.getInstance().getSuppportTssLevel()=" + BaseApplication.getInstance().getSuppportTssLevel());
            switch (BaseApplication.getInstance().getSuppportTssLevel()) {
                case 0:
                    if (TextUtils.isEmpty(url350)) {
                        if (TextUtils.isEmpty(url1000)) {
                            if (!TextUtils.isEmpty(urlUnknown)) {
                                bean.streamType = StreamType.STREAM_UNKNOWN;
                                break;
                            }
                        }
                        bean.streamType = StreamType.STREAM_1000;
                        break;
                    }
                    bean.streamType = StreamType.STREAM_350;
                    break;
                    break;
                case 1:
                    if (TextUtils.isEmpty(url350)) {
                        if (TextUtils.isEmpty(url1000)) {
                            if (!TextUtils.isEmpty(urlUnknown)) {
                                bean.streamType = StreamType.STREAM_UNKNOWN;
                                break;
                            }
                        }
                        bean.streamType = StreamType.STREAM_1000;
                        break;
                    }
                    bean.streamType = StreamType.STREAM_350;
                    break;
                    break;
                case 3:
                    if (TextUtils.isEmpty(url1000)) {
                        if (TextUtils.isEmpty(url350)) {
                            if (!TextUtils.isEmpty(urlUnknown)) {
                                bean.streamType = StreamType.STREAM_UNKNOWN;
                                break;
                            }
                        }
                        bean.streamType = StreamType.STREAM_350;
                        break;
                    }
                    bean.streamType = StreamType.STREAM_1000;
                    break;
                    break;
                case 4:
                    if (TextUtils.isEmpty(url1300)) {
                        if (TextUtils.isEmpty(url1000)) {
                            if (TextUtils.isEmpty(url350)) {
                                if (!TextUtils.isEmpty(urlUnknown)) {
                                    bean.streamType = StreamType.STREAM_UNKNOWN;
                                    break;
                                }
                            }
                            bean.streamType = StreamType.STREAM_350;
                            break;
                        }
                        bean.streamType = StreamType.STREAM_1000;
                        break;
                    }
                    bean.streamType = StreamType.STREAM_1300;
                    break;
                    break;
                case 6:
                    if (TextUtils.isEmpty(url720p)) {
                        if (TextUtils.isEmpty(url1300)) {
                            if (TextUtils.isEmpty(url1000)) {
                                if (TextUtils.isEmpty(url350)) {
                                    if (!TextUtils.isEmpty(urlUnknown)) {
                                        bean.streamType = StreamType.STREAM_UNKNOWN;
                                        break;
                                    }
                                }
                                bean.streamType = StreamType.STREAM_350;
                                break;
                            }
                            bean.streamType = StreamType.STREAM_1000;
                            break;
                        }
                        bean.streamType = StreamType.STREAM_1300;
                        break;
                    }
                    bean.streamType = StreamType.STREAM_720p;
                    break;
                    break;
            }
        }
        if (!defaultLow) {
            return;
        }
        if (!TextUtils.isEmpty(url350)) {
            bean.streamType = StreamType.STREAM_350;
        } else if (!TextUtils.isEmpty(urlUnknown)) {
            bean.streamType = StreamType.STREAM_UNKNOWN;
        } else if (!TextUtils.isEmpty(url1000)) {
            bean.streamType = StreamType.STREAM_1000;
        }
    }

    private void updateStreamIdLiveUrl(LiveStreamBean bean, LiveUrlInfo info) {
        bean.liveUrl350 = info.liveUrl_350;
        bean.streamId350 = info.streamId_350;
        bean.tm350 = info.tm_350;
        bean.code350 = info.code_350;
        bean.liveUrl1000 = info.liveUrl_1000;
        bean.streamId1000 = info.streamId_1000;
        bean.tm1000 = info.tm_1000;
        bean.code1000 = info.code_1000;
        bean.liveUrlUnknown = "";
        LogAddInfo("更新LiveStreamBean", "BaseApplication.getInstance().getSuppportTssLevel()=" + BaseApplication.getInstance().getSuppportTssLevel());
        switch (BaseApplication.getInstance().getSuppportTssLevel()) {
            case 4:
                bean.liveUrl1300 = info.liveUrl_1300;
                bean.streamId1300 = info.streamId_1300;
                bean.tm1300 = info.tm_1300;
                bean.code1300 = info.code_1300;
                return;
            case 6:
                bean.liveUrl1300 = info.liveUrl_1300;
                bean.streamId1300 = info.streamId_1300;
                bean.tm1300 = info.tm_1300;
                bean.code1300 = info.code_1300;
                bean.liveUrl720p = info.liveUrl_720p;
                bean.streamId720p = info.streamId_720p;
                bean.tm720p = info.tm_720p;
                bean.code720p = info.code_720p;
                return;
            default:
                return;
        }
    }

    private void updateMiGuStreamIdLiveUrl(LiveStreamBean bean, int quality, LiveMiGuUrlInfo urlInfo) {
        if (quality == 0) {
            this.mLiveStreamBean.liveUrl350 = urlInfo.url;
        }
        if (quality == 1) {
            this.mLiveStreamBean.liveUrl1000 = urlInfo.url;
        }
        LogAddInfo("咪咕码流设置", "BaseApplication.getInstance().getSuppportTssLevel()=" + BaseApplication.getInstance().getSuppportTssLevel());
        switch (BaseApplication.getInstance().getSuppportTssLevel()) {
            case 4:
                if (quality == 2) {
                    this.mLiveStreamBean.liveUrl1300 = urlInfo.url;
                    return;
                }
                return;
            case 6:
                if (quality == 2) {
                    this.mLiveStreamBean.liveUrl1300 = urlInfo.url;
                }
                if (quality == 3) {
                    this.mLiveStreamBean.liveUrl720p = urlInfo.url;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void stopCde() {
        CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
        if (this.mIsP2PMode && cdeHelper != null && this.mRequestRealLink != null && !TextUtils.isEmpty(this.mRequestRealLink.getP2PUrl())) {
            cdeHelper.stopPlay(this.mRequestRealLink.getP2PUrl());
            LogInfo.log("clf", "停止cde url=" + this.mRequestRealLink.getP2PUrl());
        }
    }

    public void pauseCde() {
        CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
        if (this.mIsP2PMode && cdeHelper != null && this.mRequestRealLink != null && !TextUtils.isEmpty(this.mRequestRealLink.getP2PUrl())) {
            cdeHelper.pausePlay(this.mRequestRealLink.getP2PUrl());
            LogInfo.log("clf", "暂停cde url=" + this.mRequestRealLink.getP2PUrl());
        }
    }

    public void resumeCde() {
        CdeHelper cdeHelper = BaseApplication.getInstance().getCdeHelper();
        if (this.mIsP2PMode && cdeHelper != null && this.mRequestRealLink != null && !TextUtils.isEmpty(this.mRequestRealLink.getP2PUrl())) {
            cdeHelper.resumePlay(this.mRequestRealLink.getP2PUrl());
            LogInfo.log("clf", "恢复cde url=" + this.mRequestRealLink.getP2PUrl());
        }
    }

    public ProgramEntity getPlayingProgram() {
        return this.mPlayingProgram;
    }

    public void requestData(boolean isShowListLoading, boolean hasToPlayResult) {
        LogInfo.log("clf", "RequestLiveLunboProgramList...isShowListLoading=" + isShowListLoading);
        if (isShowListLoading) {
            this.mLivePlayCallback.notifyHalfLivePlayFragment(0);
        }
        if (LetvUtils.isLiveSingleChannel(this.mLaunchMode)) {
            requestLiveRoomList(isShowListLoading, hasToPlayResult);
        } else {
            requestLiveLunboProgramList(0, isShowListLoading, hasToPlayResult);
        }
    }

    private void requestPayInfoById(String id, boolean toPlay) {
        new RequestPayInfoById(id, toPlay).start();
    }

    private void requestLiveRoomList(final boolean isShowListLoading, final boolean hasToPlay) {
        if (TimestampBean.getTm().mHasRecodeServerTime) {
            new RequestLiveRoomList(isShowListLoading, hasToPlay).start();
        } else {
            TimestampBean.getTm().getServerTimestamp(new FetchServerTimeListener() {
                public void afterFetch() {
                    new RequestLiveRoomList(isShowListLoading, hasToPlay).start();
                }
            });
        }
    }

    private void requestUrlByChannelId(boolean isPay, boolean isToPlay, String channelId) {
        if (isToPlay) {
            this.mLivePlayCallback.loading(false);
        }
        LogAddInfo("根据channelId请求播放地址", "isToPlay=" + isToPlay + ",channelId=" + channelId);
        String station = this.mLiveStreamBean.getStreamId();
        if (LetvUtils.isLunboOrWeishi(this.mLaunchMode)) {
            station = this.mCode;
        }
        String liveId = "";
        if (!(LiveLunboUtils.isLunboType(this.mLaunchMode) || this.mLaunchMode == 102)) {
            liveId = this.mUniqueId;
        }
        String uuid = "";
        if (!(this.mStatisticsHelper == null || this.mStatisticsHelper.mStatisticsInfo == null)) {
            this.mStatisticsHelper.mStatisticsInfo.mLiveId = liveId;
            this.mStatisticsHelper.mStatisticsInfo.mStation = station;
            uuid = this.mStatisticsHelper.mStatisticsInfo.getUuidTime(this.mContext);
        }
        new RequestUrlByChannelId(this.mContext, channelId, isPay, isToPlay, uuid, station, liveId, this.mRequestUrlByChannelIdCallback).start();
    }

    public void requestMiGuLiveURLByChannelId(final String channelId, final int quality) {
        String url = LetvUrlMaker.getMiGuLiveUrl(channelId, String.valueOf(quality));
        LogAddInfo("请求咪咕播放地址", "url=" + url);
        if (quality == 0) {
            resetStream();
        }
        new LetvRequest(LiveMiGuUrlInfo.class).setUrl(url).setParser(new LiveMiGuUrlParser()).setTag(BasePlayLiveFlow.REQUEST_MIGU_URL_BY_CHANNELID).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveMiGuUrlInfo>() {
            public void onNetworkResponse(VolleyRequest<LiveMiGuUrlInfo> request, LiveMiGuUrlInfo result, DataHull hull, NetworkResponseState state) {
                super.onNetworkResponse(request, result, hull, state);
                PlayLiveFlow.LogAddInfo("请求咪咕播放地址 返回结果", "state=" + state + ",result=" + result);
                switch (state) {
                    case SUCCESS:
                        if (PlayLiveFlow.this.mLiveStreamBean != null) {
                            PlayLiveFlow.this.updateMiGuStreamIdLiveUrl(PlayLiveFlow.this.mLiveStreamBean, quality, result);
                            break;
                        }
                        break;
                    case NETWORK_NOT_AVAILABLE:
                        if (quality > 3 && TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.liveUrl350) && TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.liveUrl1000)) {
                            PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                            break;
                        }
                    case NETWORK_ERROR:
                        if (quality > 3 && TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.liveUrl350) && TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.liveUrl1000)) {
                            PlayLiveFlow.this.mLivePlayCallback.requestError(null);
                            break;
                        }
                    case RESULT_ERROR:
                        if (quality > 3 && TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.liveUrl350) && TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.liveUrl1000)) {
                            PlayLiveFlow.this.mLivePlayCallback.requestError(PlayLiveFlow.this.mContext.getString(R.string.data_request_error));
                            break;
                        }
                }
                if (quality > 3) {
                    if (PlayLiveFlow.this.mUserControllLevel) {
                        PlayLiveFlow.this.setStreamTypeFromUser(PlayLiveFlow.this.mUserStreamType);
                    } else {
                        PlayLiveFlow.this.setStreamTypeFromCpu(PlayLiveFlow.this.mLiveStreamBean, PlayLiveFlow.this.mDefaultLow);
                    }
                    if (!(PlayLiveFlow.this.mStatisticsHelper == null || PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo == null)) {
                        PlayLiveFlow.this.mStatisticsHelper.mStatisticsInfo.mVt = PlayLiveFlow.this.mLiveStreamBean.getCode();
                    }
                    PlayLiveFlow.LogAddInfo("请求咪咕播放地址", "mLiveStreamBean.getLiveUrl()=" + PlayLiveFlow.this.mLiveStreamBean.getLiveUrl());
                    if (TextUtils.isEmpty(PlayLiveFlow.this.mLiveStreamBean.getLiveUrl())) {
                        PlayLiveFlow.this.mLivePlayCallback.notPlay(null);
                        return;
                    }
                    PlayLiveFlow.this.mLivePlayCallback.onLiveUrlPost(PlayLiveFlow.this.mLiveStreamBean, true);
                    PlayLiveFlow.this.playUrl(PlayLiveFlow.this.mLiveStreamBean);
                    return;
                }
                PlayLiveFlow.this.requestMiGuLiveURLByChannelId(channelId, quality + 1);
            }

            public void onCacheResponse(VolleyRequest<LiveMiGuUrlInfo> request, LiveMiGuUrlInfo result, DataHull hull, CacheResponseState state) {
                super.onCacheResponse(request, result, hull, state);
            }

            public void onErrorReport(VolleyRequest<LiveMiGuUrlInfo> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    private void requestPlayingProgram() {
        new RequestPlayingProgram(this.mChannelId).start();
    }

    private void requestLiveLunboProgramList(int direction, boolean isShowListLoading, boolean hasToPlayResult) {
        new RequestLiveLunboProgramList(direction, isShowListLoading, hasToPlayResult).start();
    }

    public void requestCanplay(LiveStreamBean liveStream) {
        new RequestCanPlay(this.mContext, liveStream.getStreamId(), this.mStatisticsHelper.mStatisticsInfo.getUuidTime(this.mContext), this.mCanPlayCallback).start();
    }

    public void requestRealLink(LiveStreamBean liveStream, Context context) {
        LogAddInfo("开始请求真实地址", "");
        if (this.mRequestRealLink != null) {
            this.mRequestRealLink = null;
        }
        this.mRequestRealLink = new RequestRealLink(this.mContext, liveStream, this.mIsWo3GUser, this.mIsP2PMode, this.mStatisticsHelper.mStatisticsInfo.getUuidTime(this.mContext), this.mRealLinkCallback);
        this.mRequestRealLink.start();
    }

    public void consumeLiveTicket() {
        new ConsumeLiveTicket().start();
    }

    private void checkPay(CheckPayCallback call) {
        LogAddInfo("检查是否是付费视频", "mIsPay=" + this.mIsPay);
        if (!this.mIsPay) {
            LogAddInfo("检查是否是付费视频", "免费直播");
            if (call != null) {
                call.freeCallback();
            }
        } else if (PreferencesManager.getInstance().isLogin()) {
            LogAddInfo("检查是否是付费视频 已登陆", "mIsPayed=" + this.mIsPayed);
            if (!this.mIsPayed) {
                LogAddInfo("未支付开始鉴权", "mIsPayed=" + this.mIsPayed);
                checkPermission();
            } else if (call != null) {
                this.mLiveStreamBean = this.mLiveStreamBean.addPayTokenUid(this.mToken);
                call.payCallback();
            }
        } else {
            LogAddInfo("检查是否是付费视频 未登陆", "");
            new QueryLivePrice().start();
        }
    }

    private void checkPermission() {
        new RequestLiveJianQuan().start();
    }

    private void setP2pByNet() {
        switch (com.letv.core.utils.NetworkUtils.getNetworkType()) {
            case 2:
            case 3:
                LogInfo.log("---NETTYPE_3G 2");
                break;
        }
        this.mIsP2PMode = PreferencesManager.getInstance().getUtp();
    }

    public void play(String mUrl) {
        this.mRealUrl = mUrl;
        LogAddInfo("拿到播放地址开始播放", "mUrl=" + mUrl);
        this.mLivePlayCallback.play(mUrl);
    }

    public void playUrl(final LiveStreamBean liveStream) {
        LogAddInfo("playUrl-拿到播放码流地址，进行处理", "liveStream.getLiveUrl()=" + liveStream.getLiveUrl());
        setP2pByNet();
        if (TextUtils.isEmpty(liveStream.getLiveUrl())) {
            LogAddInfo("playUrl", "播放码流为空");
            this.mLivePlayCallback.notPlay(null);
        } else if (UnicomWoFlowManager.getInstance().supportWoFree()) {
            UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.mContext, new LetvWoFlowListener() {
                public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                    if (PlayLiveFlow.this.mContext != null) {
                        PlayLiveFlow.LogAddInfo("playUrl-请求是否已订购联通免流量", "isOrder=" + isOrder);
                        PlayLiveFlow.this.mIsWo3GUser = isOrder;
                        PlayLiveFlow.this.mIsPlayFreeUrl = false;
                        final IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(PlayLiveFlow.this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
                        boolean isNetWo = com.letv.core.utils.NetworkUtils.isUnicom3G(false);
                        boolean isPhoneNumNull = TextUtils.isEmpty(woFlowManager.getPhoneNum(PlayLiveFlow.this.mContext));
                        PlayLiveFlow.LogAddInfo("playUrl-是否屏蔽未订购联通免流量的提醒", "UnicomWoFlowManager.DISABLE_WO=false");
                        PlayLiveFlow.LogAddInfo("playUrl-判断显示免流量对话框", "isNetWo=" + isNetWo + ",isPhoneNumNull=" + isPhoneNumNull);
                        if (isSupportProvince && !PlayLiveFlow.this.mIsWo3GUser && PlayLiveFlow.this.mIsShowOrderDialog && !isPhoneNumNull && isNetWo) {
                            PlayLiveFlow.this.mIsShowOrderDialog = false;
                            if (PreferencesManager.getInstance().getWoFlowAlert()) {
                                PlayLiveFlow.LogAddInfo("playUrl-使用流量播放", "");
                                PlayLiveFlow.this.startPlay(liveStream);
                            } else {
                                PlayLiveFlow.LogAddInfo("playUrl-提示联通免流量订购框", "");
                                new UnicomWoFlowDialogUtils().showWoMainDialog(PlayLiveFlow.this.mContext, new UnicomDialogClickListener() {
                                    public void onConfirm() {
                                        new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                PlayLiveFlow.this.mLivePlayCallback.buyWo3G();
                                            }
                                        });
                                    }

                                    public void onCancel() {
                                        PlayLiveFlow.this.startPlay(liveStream);
                                    }

                                    public void onResponse(boolean isShow) {
                                    }
                                }, PlayLiveFlow.this.mContext.getClass().getSimpleName());
                            }
                        }
                        if (!PlayLiveFlow.this.mIsWo3GUser && isPhoneNumNull && isNetWo) {
                            new UnicomWoFlowDialogUtils().showOrderConfirmEnquireDialog(PlayLiveFlow.this.mContext, new UnicomDialogClickListener() {
                                public void onConfirm() {
                                    PlayLiveFlow.LogAddInfo("playUrl-显示短信取号对话框", "");
                                    woFlowManager.showSMSVerificationDialog(PlayLiveFlow.this.mContext, new LetvWoFlowListener() {
                                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                                            PlayLiveFlow.LogAddInfo("playUrl-短信取号结果", "isSmsSuccess=" + isSmsSuccess + ",isOrder=" + isOrder);
                                            if (isSmsSuccess && isOrder) {
                                                PlayLiveFlow.LogAddInfo("playUrl-短信取号结果", "短信取号成功");
                                                PlayLiveFlow.this.playUrl(liveStream);
                                                return;
                                            }
                                            PlayLiveFlow.LogAddInfo("playUrl-短信取号结果", "短信取号失败或者用户未订购");
                                            PlayLiveFlow.this.startPlay(liveStream);
                                        }
                                    });
                                }

                                public void onCancel() {
                                    PlayLiveFlow.this.startPlay(liveStream);
                                }

                                public void onResponse(boolean isShow) {
                                }
                            });
                        } else {
                            PlayLiveFlow.this.startPlay(liveStream);
                        }
                    }
                }
            });
        } else {
            startPlay(liveStream);
        }
    }

    private void replaceUrlToFreeurlForNetChange() {
        if (UnicomWoFlowManager.getInstance().supportWoFree()) {
            UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(getActivity(), new LetvWoFlowListener() {
                public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                    if (PlayLiveFlow.this.getActivity() != null) {
                        PlayLiveFlow.this.mIsWo3GUser = isOrder;
                        PlayLiveFlow.this.mIsPlayFreeUrl = false;
                        final IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(PlayLiveFlow.this.getActivity(), JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
                        boolean isNetWo = com.letv.core.utils.NetworkUtils.isUnicom3G(false);
                        boolean isPhoneNumNull = TextUtils.isEmpty(woFlowManager.getPhoneNum(PlayLiveFlow.this.getActivity()));
                        if (!PlayLiveFlow.this.mIsWo3GUser && PlayLiveFlow.this.mIsShowOrderDialog && !isPhoneNumNull && isNetWo) {
                            PlayLiveFlow.this.mIsShowOrderDialog = false;
                            if (!PreferencesManager.getInstance().getWoFlowAlert()) {
                                new UnicomWoFlowDialogUtils().showWoMainDialog(PlayLiveFlow.this.getActivity(), new UnicomDialogClickListener() {
                                    public void onConfirm() {
                                        new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                PlayLiveFlow.this.mLivePlayCallback.buyWo3G();
                                            }
                                        });
                                    }

                                    public void onCancel() {
                                        if (PlayLiveFlow.this.mLivePlayCallback.isPlaying() && PlayLiveFlow.this.mPlayAdFragment != null && !PlayLiveFlow.this.mPlayAdFragment.isPlaying()) {
                                            new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                                public void run() {
                                                    if (PlayLiveFlow.this.mLivePlayCallback.showNetChangeDialog()) {
                                                        PlayLiveFlow.this.mLivePlayCallback.pause();
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    public void onResponse(boolean isShow) {
                                    }
                                }, PlayLiveFlow.this.getActivity().getClass().getSimpleName());
                            } else if (!(!PlayLiveFlow.this.mLivePlayCallback.isPlaying() || PlayLiveFlow.this.mPlayAdFragment == null || PlayLiveFlow.this.mPlayAdFragment.isPlaying())) {
                                new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        if (PlayLiveFlow.this.mLivePlayCallback.showNetChangeDialog()) {
                                            PlayLiveFlow.this.mLivePlayCallback.pause();
                                        }
                                    }
                                });
                            }
                        }
                        if (!isOrder && isPhoneNumNull && isNetWo) {
                            PlayLiveFlow.this.mLivePlayCallback.pause();
                            new UnicomWoFlowDialogUtils().showOrderConfirmEnquireDialog(PlayLiveFlow.this.getActivity(), new UnicomDialogClickListener() {
                                public void onConfirm() {
                                    PlayLiveFlow.this.replaceUrlToFreeurlForNetChange();
                                    LogInfo.log("wuxinrong", "PlayLiveFlow 2 显示短信取号对话框");
                                    woFlowManager.showSMSVerificationDialog(PlayLiveFlow.this.mContext, new LetvWoFlowListener() {
                                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                                            if (isSmsSuccess && isOrder) {
                                                LogInfo.log("wuxinrong", "PlayLiveFlow 2 短信取号成功");
                                                LogInfo.log("wuxinrong", "PlayLiveFlow 2 用户已订购");
                                                PlayLiveFlow.this.replaceUrlToFreeurlForNetChange();
                                                return;
                                            }
                                            LogInfo.log("wuxinrong", "PlayLiveFlow 2 短信取号失败或者用户未订购");
                                            if (PlayLiveFlow.this.mLivePlayCallback.isPlaying() && PlayLiveFlow.this.mPlayAdFragment != null && !PlayLiveFlow.this.mPlayAdFragment.isPlaying()) {
                                                new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                                    public void run() {
                                                        PlayLiveFlow.this.mLivePlayCallback.showNetChangeDialog();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }

                                public void onCancel() {
                                    if (PlayLiveFlow.this.mLivePlayCallback.isPlaying() && PlayLiveFlow.this.mPlayAdFragment != null && !PlayLiveFlow.this.mPlayAdFragment.isPlaying()) {
                                        new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                PlayLiveFlow.this.mLivePlayCallback.showNetChangeDialog();
                                            }
                                        });
                                    }
                                }

                                public void onResponse(boolean isShow) {
                                }
                            });
                        } else if (isOrder) {
                            if (PlayLiveFlow.this.mLiveStreamBean != null) {
                                PlayLiveFlow.this.requestRealLink(PlayLiveFlow.this.mLiveStreamBean, PlayLiveFlow.this.getActivity());
                            }
                        } else if (!isOrder) {
                            new Handler(PlayLiveFlow.this.getActivity().getMainLooper()).post(new Runnable() {
                                public void run() {
                                    PlayLiveFlow.this.mLivePlayCallback.showNetChangeDialog();
                                }
                            });
                        }
                    }
                }
            });
        } else {
            this.mLivePlayCallback.showNetChangeDialog();
        }
    }

    public void startPlay(LiveStreamBean liveStream) {
        LogAddInfo("开始请求播放地址", "liveStream.getStreamId()=" + liveStream.getStreamId() + ",mIsWo3GUser=" + this.mIsWo3GUser + ",isMigu()=" + isMigu());
        if (TextUtils.isEmpty(liveStream.getStreamId())) {
            playAd(liveStream);
            if (!this.mIsWo3GUser || isMigu()) {
                LogAddInfo("开始请求播放地址 未订购免流量 开始播放", "liveStream.getLiveUrl()=" + liveStream.getLiveUrl());
                this.mRealUrl = liveStream.getLiveUrl();
                this.mLivePlayCallback.play(liveStream.getLiveUrl());
            } else {
                LogAddInfo("开始请求播放地址 请求免流量地址", "");
                ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).identifyWoVideoSDK(this.mContext, liveStream.getLiveUrl(), 0, new LetvWoFlowListener() {
                    public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                        if (PlayLiveFlow.this.mContext != null) {
                            PlayLiveFlow.LogAddInfo("开始请求播放地址 请求免流量地址", "freeUrl=" + freeUrl);
                            if (freeUrl == null || freeUrl.equals("")) {
                                PlayLiveFlow.LogAddInfo("开始请求播放地址 免流量地址为空", "freeUrl=" + freeUrl);
                                new Handler(PlayLiveFlow.this.mContext.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        PlayLiveFlow.this.mLivePlayCallback.notPlay(null);
                                    }
                                });
                                return;
                            }
                            PlayLiveFlow.this.mRealUrl = freeUrl;
                            PlayLiveFlow.this.mIsPlayFreeUrl = true;
                            new Handler(PlayLiveFlow.this.mContext.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    UnicomWoFlowDialogUtils.showWoFreeActivatedToast(PlayLiveFlow.this.mContext);
                                    PlayLiveFlow.this.mLivePlayCallback.play(PlayLiveFlow.this.mRealUrl);
                                    PlayLiveFlow.LogAddInfo("开始请求播放地址 已订购获取免流量地址", "mRealUrl=" + PlayLiveFlow.this.mRealUrl);
                                }
                            });
                        }
                    }
                });
            }
            if (VERSION.SDK_INT > 8) {
                IRVideo.getInstance().newVideoPlay(this.mContext, TextUtils.isEmpty(liveStream.getTm()) ? "zhibo" : liveStream.getTm(), 0, Boolean.valueOf(true));
                return;
            }
            return;
        }
        LogAddInfo("开始请求播放地址 StreamId为空 开始请求是否可以播放", "");
        requestCanplay(liveStream);
        if (TextUtils.isEmpty(liveStream.getTm())) {
            if (VERSION.SDK_INT > 8) {
                IRVideo.getInstance().newVideoPlay(this.mContext, liveStream.getStreamId(), 0, Boolean.valueOf(true));
            }
        } else if (VERSION.SDK_INT > 8) {
            IRVideo.getInstance().newVideoPlay(this.mContext, liveStream.getTm(), 0, Boolean.valueOf(true));
        }
    }

    public void setADPause(boolean isPause) {
        if (this.mPlayAdFragment != null && this.mReqhasAd) {
            this.mPlayAdFragment.setADPause(isPause);
        }
    }

    private void playAd(LiveStreamBean liveStream) {
        statisticsPlayInit();
        LogAddInfo("开始请求广告", "mIsPlayedAd=" + this.mIsPlayedAd + ",mPlayAdFragment=" + this.mPlayAdFragment + ",mPlayAdFragment.getActivity()=" + (this.mPlayAdFragment == null ? "playAdFragment is null" : this.mPlayAdFragment.getActivity()));
        if (!this.mIsPlayedAd && this.mPlayAdFragment != null && this.mPlayAdFragment.getActivity() != null) {
            LogAddInfo("开始请求广告", "liveStream.getLiveUrl()=" + liveStream.getLiveUrl() + ",mLaunchMode=" + this.mLaunchMode);
            getFrontAdNormal(liveStream.getLiveUrl(), this.mLaunchMode == 101 ? "2" : "1");
            this.mIsPlayedAd = true;
            if (this.mOnlyFull) {
                this.mPlayAdFragment.setOnlyFullScreen();
            }
        }
    }

    public void getFrontAdNormal(String url, String ty) {
        LogAddInfo("开始请求前贴片广告", "url=" + url + ",ty=" + ty);
        this.mFrontAdty = this.mPlayAdFragment != null ? ty : "";
        LogAddInfo("开始请求前贴片广告", "NetworkUtils.getNetworkType()=" + com.letv.core.utils.NetworkUtils.getNetworkType());
        switch (com.letv.core.utils.NetworkUtils.getNetworkType()) {
            case 2:
            case 3:
                LogAddInfo("开始请求前贴片广告 2G\u0003G网络", "mIsWo3GUser" + this.mIsWo3GUser);
                if (!this.mIsWo3GUser) {
                    boolean isShow = this.mLivePlayCallback.showNetChangeDialog();
                    LogAddInfo("开始请求前贴片广告 2G\u0003G网络", "isShow" + isShow);
                    if (isShow && this.mPlayAdFragment != null) {
                        this.mPlayAdFragment.setADPause(true);
                        break;
                    }
                }
                break;
        }
        if (this.mPlayAdFragment != null) {
            if (!(BaseApplication.getInstance() == null || AdsManagerProxy.getInstance(this.mContext) == null)) {
                LogAddInfo("开始请求前贴片广告", "BaseApplication.getInstance().isPush()" + BaseApplication.getInstance().isPush());
                if (BaseApplication.getInstance().isPush()) {
                    AdsManagerProxy.getInstance(this.mContext).setFromPush(true);
                }
            }
            LogAddInfo("开始请求前贴片广告--getLiveFrontAd", "url=" + url + ",uuid" + DataUtils.getUUID(this.mContext) + "PreferencesManager.getInstance().getUserId()" + ",mIsWo3GUser=" + this.mIsWo3GUser + ",mIsPay=" + this.mIsPay + ",isSupportM3U8=" + BaseApplication.getInstance().getPinjie() + ",isUseCde=" + PreferencesManager.getInstance().getUtp() + ",isPanorama=" + this.mIsPanoramaVideo);
            this.mPlayAdFragment.getLiveFrontAd(url, DataUtils.getUUID(this.mContext), PreferencesManager.getInstance().getUserId(), "", ty, false, this.mIsWo3GUser, this.mIsPay, BaseApplication.getInstance().getPinjie(), PreferencesManager.getInstance().getUtp(), this.mIsPanoramaVideo);
            this.mLivePlayCallback.setEnforcementWait(true);
        }
    }

    public void getFrontAd(String url, String ty) {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.getLiveFrontAd(url, DataUtils.getUUID(getActivity()), PreferencesManager.getInstance().getUserId(), "", ty, false, this.mIsWo3GUser, this.mIsPay, BaseApplication.getInstance().getPinjie(), PreferencesManager.getInstance().getUtp(), this.mIsPanoramaVideo);
            this.mLivePlayCallback.setEnforcementWait(true);
        }
    }

    public void getLiveFrontAd(boolean toShowLoading) {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.getLiveFrontAd(this.mLiveStreamBean.getLiveUrl(), DataUtils.getUUID(getActivity()), PreferencesManager.getInstance().getUserId(), "", this.mFrontAdty, toShowLoading, this.mIsWo3GUser, this.mIsPay, BaseApplication.getInstance().getPinjie(), PreferencesManager.getInstance().getUtp(), this.mIsPanoramaVideo);
        }
    }

    public AdPlayFragmentProxy getPlayAdFragment() {
        return this.mPlayAdFragment;
    }

    public LetvBaseBean getCurrentLiveData() {
        if (LetvUtils.isLiveSingleChannel(this.mLaunchMode)) {
            LogInfo.log("fornia", "share--- 000000share initViews 初始化分享浮层 mLiveRemenBase：" + this.mLiveRemenBase);
            return this.mLiveRemenBase;
        }
        LogInfo.log("fornia", "share--- 000000share initViews 初始化分享浮层 mLunboData：" + this.mLunboData);
        return this.mLunboData;
    }

    public String syncGetPlayUrl(Device device) {
        if (this.mRequestRealLink == null) {
            return null;
        }
        return this.mRequestRealLink.syncGetPlayUrl(device);
    }

    public LetvBaseBean getData() {
        if (LetvUtils.isLiveSingleChannel(this.mLaunchMode)) {
            return this.mLiveRoomListBean;
        }
        return this.mLunboData;
    }

    private int findPlayItemIndex(List<LiveRemenBaseBean> list) {
        int i = 0;
        while (list != null && i < list.size()) {
            LiveRemenBaseBean live = (LiveRemenBaseBean) list.get(i);
            if (live.id != null && live.id.equals(this.mUniqueId)) {
                return i;
            }
            i++;
        }
        if (!TextUtils.isEmpty(this.mProgramName)) {
            i = 0;
            while (list != null && i < list.size()) {
                live = (LiveRemenBaseBean) list.get(i);
                if (live.title == null || !live.title.equals(this.mProgramName)) {
                    i++;
                } else {
                    this.mUniqueId = live.id;
                    return i;
                }
            }
        }
        return -1;
    }

    private void processLiveRoomResult(LiveRemenListBean result, boolean hasToPlay, int playPosition) {
        LogAddInfo("processLiveRoomResult", "----------");
        if (result == null || result.mRemenList == null) {
            this.mLivePlayCallback.notifyHalfLivePlayFragment(4);
            return;
        }
        this.mLiveRoomListBean = result;
        LiveRemenBaseBean live = playPosition != -1 ? (LiveRemenBaseBean) this.mLiveRoomListBean.mRemenList.get(playPosition) : null;
        LogInfo.log("pay_", " findPlayItemIndex = " + playPosition);
        this.mLivePlayCallback.loading(true);
        if (live != null) {
            this.mLiveRemenBase = live;
            this.mProgramName = live.title;
            this.mLivePlayCallback.setmProgramName(live.title);
            if (TextUtils.isEmpty(this.mUniqueId)) {
                this.mUniqueId = live.id;
            }
            if (hasToPlay) {
                requestUrlByChannelId("1".equals(live.isPay), true, live.selectId);
                if (!TextUtils.isEmpty(live.getBeginTime())) {
                    this.mPlayTime = live.getBeginTime();
                }
            }
        }
        if (result.mRemenList.size() == 0) {
            this.mLivePlayCallback.notifyHalfLivePlayFragment(5);
            return;
        }
        if (playPosition > 0) {
            playPosition--;
        }
        this.mLivePlayCallback.notifyHalfLivePlayFragment(1);
    }

    public void onDestroy() {
        this.mHandler.removeMessages(1000);
        this.mHandler.removeMessages(1001);
    }

    private String formatTime(String time) {
        try {
            if (!TextUtils.isEmpty(time)) {
                int dotIndex = time.indexOf(NetworkUtils.DELIMITER_COLON);
                if (dotIndex != -1) {
                    time = time.substring(0, dotIndex + 3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    private int compareTime(String time1, String time2) {
        if (TextUtils.isEmpty(time1)) {
            return 0;
        }
        return time1.compareTo(time2);
    }

    public void statisticsLaunch(boolean isTg) {
        if (this.mStatisticsHelper != null && this.mStatisticsHelper.mStatisticsInfo != null) {
            setStatisticsData();
            if (isTg) {
                this.mStatisticsHelper.statisticsPlayActions("tg");
                StatisticsInfo statisticsInfo = this.mStatisticsHelper.mStatisticsInfo;
                statisticsInfo.mInterruptNum++;
                this.mStatisticsHelper.mStatisticsInfo.mReplayType = 2;
                if (this.mLiveStreamBean != null) {
                    this.mStatisticsHelper.mStatisticsInfo.mVt = this.mLiveStreamBean.getCode();
                }
            }
            this.mStatisticsHelper.statisticsPlayActions("launch");
        }
    }

    public void statisticsPlayInit() {
        if (this.mStatisticsHelper != null && this.mStatisticsHelper.mStatisticsInfo != null) {
            setStatisticsData();
            this.mStatisticsHelper.statisticsPlayActions("init");
        }
    }

    public void statisticsPlaying() {
        if (this.mStatisticsHelper != null && this.mStatisticsHelper.mStatisticsInfo != null) {
            setStatisticsData();
            this.mStatisticsHelper.statisticsPlayActions("play");
        }
    }

    private void setStatisticsData() {
        StatisticsInfo statisticsInfo = this.mStatisticsHelper.mStatisticsInfo;
        statisticsInfo.mLiveRenmenBase = this.mLiveRemenBase;
        statisticsInfo.mLaunchMode = this.mLaunchMode;
        statisticsInfo.mIsPay = this.mIsPay;
        statisticsInfo.mPlayAdFragment = this.mPlayAdFragment;
        statisticsInfo.mCid = this.mChannelId;
        if (BaseApplication.getInstance().isPush()) {
            statisticsInfo.mLiveId = this.mPushLiveId;
        }
        if (!TextUtils.isEmpty(statisticsInfo.mStation)) {
            return;
        }
        if (!TextUtils.isEmpty(this.mCode)) {
            statisticsInfo.mStation = this.mCode;
        } else if (this.mLiveStreamBean != null) {
            statisticsInfo.mStation = this.mLiveStreamBean.getStreamId();
        }
    }
}
