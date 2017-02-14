package com.letv.ads.ex.utils;

import android.content.Context;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.adlib.sdk.utils.LogInfo;
import com.letv.ads.ex.client.SearchKeyWordCallBack;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.loader.JarResources;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdsManagerProxy {
    private static final String TAG = "AdsManagerProxy";
    private static AdsManagerProxy instance;
    private static Object realSubject;

    public interface VipCallBack {
        boolean isVip();
    }

    public static synchronized AdsManagerProxy getInstance(Context context) {
        AdsManagerProxy adsManagerProxy;
        synchronized (AdsManagerProxy.class) {
            if (instance == null) {
                instance = new AdsManagerProxy();
                Class<?> realClass = JarLoader.loadClass(context, "Letv_Ads.apk", JarConstant.LETV_ADS_PACKAGENAME, "utils.AdsManager");
                realSubject = ReflectionUtils.callADObjectMethod(null, realClass, "getInstance", new Class[]{Context.class}, context);
                LogInfo.log(TAG, "getInstance realClass=" + realClass + "  realSubject=" + realSubject + "  jres=" + JarResources.getResourceByCl(JarLoader.getJarClassLoader(context, "Letv_Ads.apk", JarConstant.LETV_ADS_PACKAGENAME), context));
            }
            adsManagerProxy = instance;
        }
        return adsManagerProxy;
    }

    public void initAd(Context context, int supportLevel, String deviceId, String kv, String platform, String version, String pcode, IAdJSBridge iAdJSBridge, boolean useTestServer, boolean showLog, String apprunid, String wmac, String im, String imsi) {
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "initAd", new Class[]{Context.class, Integer.TYPE, String.class, String.class, String.class, String.class, String.class, IAdJSBridge.class, Boolean.TYPE, Boolean.TYPE, String.class, String.class, String.class, String.class}, context, Integer.valueOf(supportLevel), deviceId, kv, platform, version, pcode, iAdJSBridge, Boolean.valueOf(useTestServer), Boolean.valueOf(showLog), apprunid, wmac, im, imsi);
        }
    }

    public void initRemoteConfig(String config) {
        LogInfo.log(TAG, "deprecated  method initRemoteConfig");
    }

    public AdElementMime getBeginAdInfo() {
        if (realSubject != null) {
            return (AdElementMime) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "getBeginAdInfo", null, new Object[0]);
        }
        return null;
    }

    public void updateBeginAdInfo() {
        LogInfo.log(TAG, "updateBeginAdInfo ");
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "updateBeginAdInfo", null, new Object[0]);
        }
    }

    public ArrayList<AdElementMime> getFocusAdInfo() {
        if (realSubject != null) {
            return (ArrayList) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "getFocusAdInfo", null, new Object[0]);
        }
        return null;
    }

    public ArrayList<AdElementMime> getHomePageBannerAd() {
        if (realSubject != null) {
            return (ArrayList) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "getHomePageBannerAd", null, new Object[0]);
        }
        return null;
    }

    public ArrayList<AdElementMime> getPlayPageBannerAd(HashMap<String, String> reqParamMap) {
        if (realSubject == null) {
            return null;
        }
        return (ArrayList) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "getPlayPageBannerAd", new Class[]{HashMap.class}, reqParamMap);
    }

    public ArrayList<AdElementMime> getChannelFocusAdInfo(String cid) {
        if (realSubject == null) {
            return null;
        }
        return (ArrayList) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "getChannelFocusAdInfo", new Class[]{String.class}, cid);
    }

    public ArrayList<AdElementMime> getFeatureAdInfo() {
        if (realSubject != null) {
            return (ArrayList) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "getFeatureAdInfo", null, new Object[0]);
        }
        return null;
    }

    public void requestSearchKeyWord(Context context, SearchKeyWordCallBack searchKeyWordCallBack) {
        LogInfo.log(TAG, "requestSearchKeyWord ");
    }

    public void cancellSearchKeyWord() {
        LogInfo.log(TAG, "cancellSearchKeyWord");
    }

    public void notifyNetStatusChange(Context context, int state) {
        LogInfo.log(TAG, "notifyNetStatusChange deprecated");
    }

    public boolean isShowAd() {
        LogInfo.log(TAG, "isShowAd");
        if (realSubject != null) {
            return ((Boolean) ReflectionUtils.callADObjectMethod(realSubject, realSubject.getClass(), "isShowAd", null, new Object[0])).booleanValue();
        }
        return false;
    }

    public void setShowAd(boolean showAd) {
        LogInfo.log(TAG, "setShowAd showAd=" + showAd);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setShowAd", new Class[]{Boolean.TYPE}, Boolean.valueOf(showAd));
        }
    }

    public void setVipCallBack(VipCallBack vipCallBack) {
        LogInfo.log(TAG, "setVipCallBack vipCallBack=" + vipCallBack);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setVipCallBack", new Class[]{VipCallBack.class}, vipCallBack);
        }
    }

    public void setShowOfflineAd(boolean showOfflineAd) {
        LogInfo.log(TAG, "setShowOfflineAd showOfflineAd=" + showOfflineAd);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setShowOfflineAd", new Class[]{Boolean.TYPE}, Boolean.valueOf(showOfflineAd));
        }
    }

    public void setFromPush(boolean isFromPush) {
        LogInfo.log(TAG, "setFromPush isFromPush=" + isFromPush);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setFromPush", new Class[]{Boolean.TYPE}, Boolean.valueOf(isFromPush));
        }
    }

    public void setFromQRCode(boolean isFromQRCode) {
        LogInfo.log(TAG, "setFromQRCode isFromQRCode=" + isFromQRCode);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setFromQRCode", new Class[]{Boolean.TYPE}, Boolean.valueOf(isFromQRCode));
        }
    }

    public void setIsQRCodeVideoTime(long isQRCodeVideoTime) {
        LogInfo.log(TAG, "setIsQRCodeVideoTime isQRCodeVideoTime=" + isQRCodeVideoTime);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setIsQRCodeVideoTime", new Class[]{Long.TYPE}, Long.valueOf(isQRCodeVideoTime));
        }
    }

    public void setLetvQRCodeUrl(String letvQRCodeUrl) {
        LogInfo.log(TAG, "setLetvQRCodeUrl letvQRCodeUrl=" + letvQRCodeUrl);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setLetvQRCodeUrl", new Class[]{String.class}, letvQRCodeUrl);
        }
    }

    public void sendFeedbackToAd(String uniqueId) {
        LogInfo.log(TAG, "sendFeedbackToAd uniqueId=" + uniqueId);
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "sendFeedbackToAd", new Class[]{String.class}, uniqueId);
        }
    }

    public static boolean isADPluginEnable() {
        return realSubject != null;
    }

    public void installFirst() {
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "installFirst", null, new Object[0]);
        }
    }

    public void getOfflineCachedVideoAd(Context context, int cid, long aid, long vid, String mmsid, String uid, String vlen, boolean isSupportM3U8, boolean isVipVideo, boolean disableAvd, boolean isWoOrderUser) {
        LogInfo.log(TAG, "getOfflineCachedVideoAd ");
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "getOfflineCachedVideoAd", new Class[]{Context.class, Integer.TYPE, Long.TYPE, Long.TYPE, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, context, Integer.valueOf(cid), Long.valueOf(aid), Long.valueOf(vid), mmsid, uid, vlen, Boolean.valueOf(isSupportM3U8), Boolean.valueOf(isVipVideo), Boolean.valueOf(disableAvd), Boolean.valueOf(isWoOrderUser));
        }
    }

    public void setAdTipsMessage(Map<String, String> titleMapper, Map<String, String> messageMapper) {
        LogInfo.log(TAG, "setAdTipsMessage ");
        if (realSubject != null) {
            ReflectionUtils.callADVoidMethod(realSubject, realSubject.getClass(), "setAdTipsMessage", new Class[]{Map.class, Map.class}, titleMapper, messageMapper);
        }
    }
}
