package com.letv.business.flow.main;

import android.content.Context;
import android.text.TextUtils;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.component.upgrade.utils.UpgradeHttpApi;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.DataStatusInfoBean;
import com.letv.core.bean.FindListDataBean;
import com.letv.core.bean.FloatBallBeanList;
import com.letv.core.bean.GeoBean;
import com.letv.core.bean.LiveDateInfo;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.TipMapBean;
import com.letv.core.bean.switchinfo.Defaultbr;
import com.letv.core.bean.switchinfo.LogoInfo;
import com.letv.core.bean.switchinfo.SearchWordsInfo;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.BrName;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyRequestQueue.RequestFilter;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.listener.OnEntryResponse;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.DataStatusInfoParser;
import com.letv.core.parser.FloatBallBeanListParser;
import com.letv.core.parser.GeoCodeParser;
import com.letv.core.parser.TipBeanListParser;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.util.DataConstant.ACTION.LE123.CHANNEL;
import com.tencent.connect.common.Constants;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class MainFlow {
    private String TAG = MainFlow.class.getName();
    private MainFlowCallback mCallback;
    private Context mContext;

    public MainFlow(Context context, MainFlowCallback callback) {
        if (context == null || callback == null) {
            throw new NullPointerException("main flow param is null!");
        }
        this.mContext = context;
        this.mCallback = callback;
    }

    public void start() {
        requestDataStatusInfo(new Runnable() {
            public void run() {
                MainFlow.this.requestTipInfo();
            }
        });
        PreferencesManager.getInstance().setShow3gDialog(true);
        PreferencesManager.getInstance().setBritness(0.0f);
    }

    public void requestTipInfo() {
        new LetvRequest().setParser(new TipBeanListParser()).setCache(new VolleyDiskCache(TipMapBean.TIP_CACHE_KEY)).setCallback(new SimpleResponse<TipMapBean>() {
            public void onNetworkResponse(VolleyRequest<TipMapBean> volleyRequest, TipMapBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("zhuqiao", "requestTipInfo net state " + state);
                if (state == NetworkResponseState.SUCCESS) {
                    MainFlow.this.mCallback.onTipCallback(result);
                    AdsManagerProxy.getInstance(MainFlow.this.mContext).setAdTipsMessage(result.titleMap, result.messageMap);
                }
            }

            public void onCacheResponse(VolleyRequest<TipMapBean> request, TipMapBean result, DataHull hull, CacheResponseState state) {
                LogInfo.log("zhuqiao", "requestTipInfo cache state:" + state);
                request.setUrl(LetvUrlMaker.getDialogMsgInfoUrl(hull.markId));
                if (state == CacheResponseState.SUCCESS) {
                    MainFlow.this.mCallback.onTipCallback(result);
                    AdsManagerProxy.getInstance(MainFlow.this.mContext).setAdTipsMessage(result.titleMap, result.messageMap);
                }
            }

            public void onErrorReport(VolleyRequest<TipMapBean> volleyRequest, String errorInfo) {
            }
        }).add();
    }

    private void requestDataStatusInfo(final Runnable runnable) {
        AdsManagerProxy.getInstance(this.mContext).setShowAd(true);
        String tag = this.TAG + "requestDataStatus";
        Volley.getQueue().cancelWithTag(tag);
        new LetvRequest().setUrl(LetvUrlMaker.getDataStatusInfoUrl()).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setTag(tag).setParser(new DataStatusInfoParser()).setCallback(new SimpleResponse<DataStatusInfoBean>() {
            public void onNetworkResponse(VolleyRequest<DataStatusInfoBean> volleyRequest, DataStatusInfoBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("zhuqiao", "requestDataStatusInfo state:" + state);
                LogInfo.log("zhuqiao", "url =" + LetvUrlMaker.getDataStatusInfoUrl());
                if (state == NetworkResponseState.SUCCESS) {
                    if (result.mThemeDataBean != null) {
                        result.mThemeDataBean.mServerTime = hull.mServerTime;
                    }
                    MainFlow.this.setDataStatusInfo(result);
                    MainFlow.this.mCallback.checkUpdate(true);
                    MainFlow.this.mCallback.checkUninstallEnable(true);
                    MainFlow.this.mCallback.checkRedPacket();
                    MainFlow.this.mCallback.dexPatch();
                    MainFlow.this.mCallback.updateUI();
                    MainFlow.this.mCallback.homePageLoad();
                    runnable.run();
                    return;
                }
                if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE || state == NetworkResponseState.NETWORK_ERROR || state == NetworkResponseState.RESULT_ERROR) {
                    MainFlow.this.mCallback.checkUpdate(true);
                    MainFlow.this.mCallback.checkAd(false);
                    MainFlow.this.mCallback.showChannelRecommend(PreferencesManager.getInstance().getChannelRecommendSwitch());
                    MainFlow.this.mCallback.checkUninstallEnable(false);
                    MainFlow.this.mCallback.updateUI();
                    MainFlow.this.mCallback.homePageLoad();
                    PreferencesManager.getInstance().setSearchWordsInfo(new SearchWordsInfo());
                }
                runnable.run();
                DataStatistics.getInstance().sendErrorInfo(BaseApplication.getInstance(), "0", "0", LetvErrorCode.LTURLModule_ApiStatus, null, hull.reportErrorString, null, null, null, null);
            }
        }).add();
    }

    public void requestLocationMessage() {
        new LetvRequest().setUrl(LetvUrlMaker.getGeoUrl(PreferencesManager.getInstance().getLocationLongitude(), PreferencesManager.getInstance().getLocationLatitude())).setParser(new GeoCodeParser()).setCallback(new OnEntryResponse<GeoBean>() {
            public void onNetworkResponse(VolleyRequest<GeoBean> volleyRequest, GeoBean result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS && result != null && !TextUtils.isEmpty(result.country)) {
                    if (!PreferencesManager.getInstance().isTestApi() || PreferencesManager.getInstance().getCitySelect() == 0) {
                        if (!TextUtils.equals(result.country, PreferencesManager.getInstance().getCountryCode())) {
                            PreferencesManager.getInstance().setCountryCode(result.country);
                            if (MainFlow.this.mCallback != null) {
                                MainFlow.this.mCallback.locationChange();
                            }
                        }
                        String geoCode = result.country + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + result.provinceid + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + result.districtid + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + result.citylevel;
                        PreferencesManager.getInstance().setGeoCode(geoCode);
                        geoCode = geoCode + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + result.location;
                        if (!TextUtils.equals(geoCode, PreferencesManager.getInstance().getLocationCode())) {
                            PreferencesManager.getInstance().setLocationCode(geoCode);
                            LetvUtils.resetLoacationMessage();
                        }
                        PreferencesManager.getInstance().setStatisticsLocation(result.country + "|" + result.location);
                    }
                }
            }

            public void onErrorReport(VolleyRequest<GeoBean> volleyRequest, String errorInfo) {
            }

            public void onCacheResponse(VolleyRequest<GeoBean> volleyRequest, GeoBean result, DataHull hull, CacheResponseState state) {
            }
        }).add();
    }

    public void requestFindTask() {
        String tag = this.TAG + "requestFind";
        Volley.getQueue().cancelWithTag(tag);
        new LetvRequest(FindListDataBean.class).setTag(tag).setCache(new VolleyDiskCache(FindListDataBean.CACHE_KEY)).setCallback(new SimpleResponse<FindListDataBean>() {
            public void onNetworkResponse(VolleyRequest<FindListDataBean> volleyRequest, FindListDataBean result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS && MainFlow.this.mCallback != null) {
                    MainFlow.this.mCallback.beanCallBack(result);
                }
            }

            public void onCacheResponse(VolleyRequest<FindListDataBean> request, FindListDataBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS && MainFlow.this.mCallback != null) {
                    MainFlow.this.mCallback.beanCallBack(result);
                }
                request.setUrl(MediaAssetApi.getInstance().getFindUrl(hull.markId));
            }

            public void onErrorReport(VolleyRequest<FindListDataBean> volleyRequest, String errorInfo) {
            }
        }).add();
    }

    public void requestFloatTask() {
        String tag = this.TAG + "requestFloat";
        Volley.getQueue().cancelWithTag(tag);
        new LetvRequest(FloatBallBeanList.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().requestFloatBallUrl()).setTag(tag).setParser(new FloatBallBeanListParser()).setCallback(new SimpleResponse<FloatBallBeanList>() {
            public void onErrorReport(VolleyRequest<FloatBallBeanList> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }

            public void onNetworkResponse(VolleyRequest<FloatBallBeanList> request, FloatBallBeanList result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS && MainFlow.this.mCallback != null) {
                    MainFlow.this.mCallback.beanCallBack(result);
                }
                super.onNetworkResponse(request, result, hull, state);
            }

            public void onCacheResponse(VolleyRequest<FloatBallBeanList> request, FloatBallBeanList result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS && MainFlow.this.mCallback != null) {
                    MainFlow.this.mCallback.beanCallBack(result);
                }
                super.onCacheResponse(request, result, hull, state);
            }
        }).add();
    }

    private void setDataStatusInfo(DataStatusInfoBean result) {
        boolean z;
        boolean z2 = true;
        if (result.tm > 0) {
            TimestampBean.getTm().updateTimestamp(result.tm);
        }
        if (result.blockNum != null) {
            PreferencesManager.getInstance().setDataNum(result.blockNum);
        }
        PreferencesManager.getInstance().setGameShow(result.game == 1);
        PreferencesManager.getInstance().setShareWords(result.shareWords);
        PreferencesManager instance = PreferencesManager.getInstance();
        if (result.channelMember == 1) {
            z = true;
        } else {
            z = false;
        }
        instance.setChannelShow(z);
        instance = PreferencesManager.getInstance();
        if (result.lemall == 1) {
            z = true;
        } else {
            z = false;
        }
        instance.setLeMallShow(z);
        PreferencesManager.getInstance().setBottomRecommendSwitch(result.bottomRecommendSwitch);
        PreferencesManager.getInstance().setChannelRecommendSwitch(result.channelRecommendSwitch);
        PreferencesManager.getInstance().setPopRecommendSwitch(result.popRecommendSwitch);
        PreferencesManager.getInstance().setAdOfflineSwitch(result.adOffline);
        AdsManagerProxy.getInstance(this.mContext).setShowOfflineAd(result.adOffline);
        PreferencesManager.getInstance().setChinaUnicomSwitch(result.chinaUnicom);
        PreferencesManager.getInstance().setLinkShellSwitch(result.isLinkShell);
        PreferencesManager.getInstance().setMP4UtpSwitch(result.isMp4Utp);
        PreferencesManager.getInstance().setM3v(result.m3v);
        PreferencesManager.getInstance().setSupportCombine(result.combine == 1);
        instance = PreferencesManager.getInstance();
        if (result.combineUseM3u8 == 1) {
            z = true;
        } else {
            z = false;
        }
        instance.setUseCombineM3u8(z);
        PreferencesManager.getInstance().setCopyright(result.copyright);
        PreferencesManager.getInstance().setDlna(result.dlna);
        PreferencesManager.getInstance().setProtoBuf(result.protobuf);
        PreferencesManager.getInstance().setRedPackageSDK(result.redPacketSdk);
        PreferencesManager.getInstance().setReactNativeEnable(result.reactNative);
        PreferencesManager.getInstance().setSwitchStreamEnable(result.switchStream);
        if (this.mCallback != null) {
            this.mCallback.showChannelRecommend(PreferencesManager.getInstance().getChannelRecommendSwitch());
        }
        if (result.mChannelWorldCupInfo != null) {
            PreferencesManager.getInstance().setChannelWorldCupSwitch(result.mChannelWorldCupInfo.getChannelStatus());
        }
        if (result.mApiInfo == null || !"2".equals(result.mApiInfo.getApistatus())) {
            PreferencesManager.getInstance().setTestApi(false);
            UpgradeHttpApi.SET_TEST_API_DOMAIN(this.mContext, false);
        } else {
            PreferencesManager.getInstance().setTestApi(true);
            UpgradeHttpApi.SET_TEST_API_DOMAIN(this.mContext, true);
        }
        LetvConfig.SLOW_TIME_OUT = result.mTimeOutInfo != null ? result.mTimeOutInfo.getTimeValue() : 1.5d;
        if (result.mAdsInfo == null) {
            AdsManagerProxy.getInstance(this.mContext).setShowAd(false);
            if (this.mCallback != null) {
                this.mCallback.checkAd(false);
            }
        } else if ("1".equals(result.mAdsInfo.getValue())) {
            AdsManagerProxy.getInstance(this.mContext).setShowAd(true);
            if (this.mCallback != null) {
                this.mCallback.checkAd(true);
            }
            BaseApplication instance2 = BaseApplication.getInstance();
            z = "1".equals(result.mAdsInfo.getPinValue()) || "".equals(result.mAdsInfo.getPinValue());
            instance2.setAdsPinjie(z);
        } else {
            AdsManagerProxy.getInstance(this.mContext).setShowAd(false);
            if (this.mCallback != null) {
                this.mCallback.checkAd(false);
            }
        }
        if (result.downloadDefaultbr != null) {
            Defaultbr dd = result.downloadDefaultbr;
            if (!PreferencesManager.getInstance().downloadBrControlIsClose()) {
                boolean flag;
                if (BaseApplication.getInstance().getSuppportTssLevel() == 0) {
                    flag = true;
                } else {
                    flag = false;
                }
                if (flag) {
                    if (CHANNEL.SERIAL_SPECIAL.equals(dd.getLow())) {
                        PreferencesManager.getInstance().setIsDownloadHd(false);
                    } else {
                        PreferencesManager.getInstance().setIsDownloadHd(true);
                    }
                } else {
                    if (Constants.DEFAULT_UIN.equals(dd.getNormal())) {
                        PreferencesManager.getInstance().setIsDownloadHd(true);
                    } else {
                        PreferencesManager.getInstance().setIsDownloadHd(false);
                    }
                }
                PreferencesManager.getInstance().closeDownloadBrControl();
            }
            PreferencesManager.getInstance().setDownloadLow_zh(dd.getLow_zh());
            if (!TextUtils.isEmpty(dd.getLow_zh())) {
                BrName.downloadLowName = dd.getLow_zh();
            }
            PreferencesManager.getInstance().setDownloadNormal_zh(dd.getNormal_zh());
            if (!TextUtils.isEmpty(dd.getNormal_zh())) {
                BrName.downloadNormalName = dd.getNormal_zh();
            }
            PreferencesManager.getInstance().setDownloadHigh_zh(dd.getHigh_zh());
            if (!TextUtils.isEmpty(dd.getHigh_zh())) {
                BrName.downloadHighName = dd.getHigh_zh();
            }
        }
        if (result.playDefaultbr != null) {
            Defaultbr pd = result.downloadDefaultbr;
            if (!PreferencesManager.getInstance().playBrControlIsClose()) {
                PreferencesManager.getInstance().setPlayLevel(BaseApplication.getInstance().getDefaultLevel());
                PreferencesManager.getInstance().closePlayBrControl();
            }
            PreferencesManager.getInstance().setPlayLow_zh(pd.getLow_zh());
            if (!TextUtils.isEmpty(pd.getLow_zh())) {
                BrName.playLowName = pd.getLow_zh();
            }
            PreferencesManager.getInstance().setPlayNormal_zh(pd.getNormal_zh());
            if (!TextUtils.isEmpty(pd.getNormal_zh())) {
                BrName.playNormalName = pd.getNormal_zh();
            }
            PreferencesManager.getInstance().setPlayHigh_zh(pd.getHigh_zh());
            if (!TextUtils.isEmpty(pd.getHigh_zh())) {
                BrName.playHighName = pd.getHigh_zh();
            }
        }
        if (result.mLogoInfo != null) {
            LogoInfo mLogoInfo = result.mLogoInfo;
            if (!"1".equals(mLogoInfo.getStatus()) || TextUtils.isEmpty(mLogoInfo.getIcon())) {
                PreferencesManager.getInstance().setLogoInfo(false);
            } else {
                PreferencesManager.getInstance().setLogoInfo(true);
            }
        } else {
            PreferencesManager.getInstance().setLogoInfo(false);
        }
        if (result.mUtpInfo != null) {
            if ("1".equals(result.mUtpInfo.getStatus())) {
                LogInfo.log("zhuqiao", "----------setUtp true");
                PreferencesManager.getInstance().setUtp(true);
            } else {
                LogInfo.log("zhuqiao", "----------setUtp false");
                PreferencesManager.getInstance().setUtp(false);
            }
        }
        requestDate();
        if (result.mAdsConfig != null) {
            AdsManagerProxy.getInstance(this.mContext).initRemoteConfig(result.mAdsConfig.getAdsConfig());
        } else {
            AdsManagerProxy.getInstance(this.mContext).initRemoteConfig(null);
        }
        if (result.mPhonePayInfo != null) {
            PreferencesManager.getInstance().setShowPhonePay(result.mPhonePayInfo.getData().equals("1"));
        }
        instance = PreferencesManager.getInstance();
        if (result.uninstallEnable == 1) {
            z = true;
        } else {
            z = false;
        }
        instance.setUninstallEnable(z);
        instance = PreferencesManager.getInstance();
        if (result.game == 1) {
            z = true;
        } else {
            z = false;
        }
        instance.setGameShow(z);
        PreferencesManager.getInstance().setPushTm(result.pushTm);
        PreferencesManager.getInstance().setDexPatchEnable(result.dexpatch.equals("1"));
        PreferencesManager instance3 = PreferencesManager.getInstance();
        if (result.lebox != 1) {
            z2 = false;
        }
        instance3.setLeboxEnable(z2);
        if (result.mThemeDataBean != null) {
            FileUtils.saveObjectToFile(result.mThemeDataBean, "themedata");
        }
        if (result.mSearchWordsInfo != null) {
            PreferencesManager.getInstance().setSearchWordsInfo(result.mSearchWordsInfo);
        } else {
            PreferencesManager.getInstance().setSearchWordsInfo(new SearchWordsInfo());
        }
    }

    private void requestDate() {
        new LetvRequest(LiveDateInfo.class).setUrl(MediaAssetApi.getInstance().getDateUrl()).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveDateInfo>() {
            public void onNetworkResponse(VolleyRequest<LiveDateInfo> volleyRequest, LiveDateInfo result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS) {
                    BaseApplication.getInstance().setLiveDateInfo(result);
                }
            }
        }).add();
    }

    public void destroy() {
        Volley.getQueue().cancelAll(new RequestFilter() {
            public boolean apply(VolleyRequest<?> request) {
                return (request == null || TextUtils.isEmpty(request.getTag()) || !request.getTag().startsWith(MainFlow.this.TAG)) ? false : true;
            }
        });
    }
}
