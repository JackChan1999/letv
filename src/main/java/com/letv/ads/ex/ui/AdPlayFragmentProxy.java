package com.letv.ads.ex.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.adlib.sdk.utils.LogInfo;
import com.letv.ads.ex.client.ADListener;
import com.letv.ads.ex.client.ClientFunction;
import com.letv.ads.ex.client.IClientADEventInformer;
import com.letv.ads.ex.client.IVideoStatusInformer;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.ads.ex.utils.ReflectionUtils;
import com.letv.http.LetvLogApiTool;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.fragment.ProxyFragment;
import java.util.HashMap;

public class AdPlayFragmentProxy extends ProxyFragment {
    private static final String TAG = "AdPlayFragmentProxy";
    private ADListener mAdListener;
    private boolean mIsADPluginEnable = false;

    public interface OnPauseADListener {
        void onPauseAdVisible(boolean z);
    }

    public AdPlayFragmentProxy(Context context) {
        boolean isADPluginEnable = AdsManagerProxy.isADPluginEnable();
        LogInfo.log(TAG, "AdPlayFragmentProxy() isADPluginEnable=" + isADPluginEnable);
        LetvLogApiTool.createPlayLogInfo("创建广告fragment", "", "广告插件是否可用:" + isADPluginEnable);
        if (isADPluginEnable) {
            try {
                Bundle args = new Bundle();
                args.putString("extra.packagename", JarConstant.LETV_ADS_PACKAGENAME);
                args.putString("extra.class", JarConstant.LETV_ADS_PLAYFRAGMENT_CLASS);
                args.putString("extra.jarname", "Letv_Ads.apk");
                setArguments(args);
                createRemoteFragment(context);
            } catch (Exception e) {
                LogInfo.log(TAG, "AdPlayFragmentProxy() e=" + e);
                LetvLogApiTool.createPlayLogInfo("创建广告fragment异常", "", "异常信息:" + e);
            }
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setClientFunction(ClientFunction clientFunction) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setClientFunction", new Class[]{ClientFunction.class}, clientFunction);
        }
    }

    public void setAdListener(ADListener adListener) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setAdListener", new Class[]{ADListener.class}, adListener);
        }
        this.mAdListener = adListener;
    }

    public void setPauseAdsListener(OnPauseADListener pauseAdsListener) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setPauseAdsListener", new Class[]{OnPauseADListener.class}, pauseAdsListener);
        }
    }

    public void setIVideoStatusInformer(IVideoStatusInformer iVideoStatusInformer) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setIVideoStatusInformer", new Class[]{IVideoStatusInformer.class}, iVideoStatusInformer);
        }
    }

    public void setADPause(boolean pauseAd) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setADPause", new Class[]{Boolean.TYPE}, Boolean.valueOf(pauseAd));
        }
    }

    public void setOnlyFullScreen() {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setOnlyFullScreen", null, new Object[0]);
        }
    }

    public void setAdsViewHalfFullBtnVisible(boolean isVisible) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setAdsViewHalfFullBtnVisible", new Class[]{Boolean.TYPE}, Boolean.valueOf(isVisible));
        }
    }

    public void setMuteViewStatus(int cur) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "setMuteViewStatus", new Class[]{Integer.TYPE}, Integer.valueOf(cur));
        }
    }

    public boolean isPauseAd() {
        Boolean isPauseAd = Boolean.valueOf(false);
        if (this.mRemoteFragment != null) {
            isPauseAd = ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "isPauseAd", null, new Object[0]);
        }
        return isPauseAd.booleanValue();
    }

    public boolean isPlaying() {
        Boolean isPlaying = Boolean.valueOf(false);
        if (this.mRemoteFragment != null) {
            isPlaying = ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "isPlaying", null, new Object[0]);
        }
        return isPlaying.booleanValue();
    }

    public boolean isHaveFrontAds() {
        Boolean isHaveFrontAds = Boolean.valueOf(false);
        if (this.mRemoteFragment != null) {
            isHaveFrontAds = ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "isHaveFrontAds", null, new Object[0]);
        }
        return isHaveFrontAds.booleanValue();
    }

    public void cancelRequestFrontAdTask() {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "cancelRequestFrontAdTask", null, new Object[0]);
        }
    }

    public void closePauseAd() {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "closePauseAd", null, new Object[0]);
        }
    }

    public void stopPlayback(boolean notifyOnFinish) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "stopPlayback", new Class[]{Boolean.TYPE}, Boolean.valueOf(notifyOnFinish));
        }
    }

    public void stopPlayback(boolean notifyOnFinish, boolean isBreakRetry) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "stopPlayback", new Class[]{Boolean.TYPE, Boolean.TYPE}, Boolean.valueOf(notifyOnFinish), Boolean.valueOf(isBreakRetry));
        }
    }

    public HashMap<String, String> getVODFrontADParameter(String uuid, String uid, String py, String ty, boolean isSupportM3U8, boolean disableAvd, boolean toShowLoading, boolean isWoOrderUser, boolean isOpenCde, boolean isPanorama, boolean isSupportFullCombine, boolean isRequestCacheAD, boolean isPush, boolean isNeedProllAd, boolean isNeedMidProllAd) {
        if (this.mRemoteFragment != null) {
            return (HashMap) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getVODFrontADParameter", new Class[]{String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, uuid, uid, py, ty, Boolean.valueOf(isSupportM3U8), Boolean.valueOf(disableAvd), Boolean.valueOf(toShowLoading), Boolean.valueOf(isWoOrderUser), Boolean.valueOf(isOpenCde), Boolean.valueOf(isPanorama), Boolean.valueOf(isSupportFullCombine), Boolean.valueOf(isRequestCacheAD), Boolean.valueOf(isPush), Boolean.valueOf(isNeedProllAd), Boolean.valueOf(isNeedMidProllAd));
        } else if (this.mAdListener == null) {
            return null;
        } else {
            this.mAdListener.handleADUrlAcquireDone(null, null, 0, false);
            this.mAdListener.handleADFinish(false);
            return null;
        }
    }

    public HashMap<String, String> getVODFrontADParameter(String uuid, String uid, String py, String ty, boolean isSupportM3U8, boolean disableAvd, boolean toShowLoading, boolean isWoOrderUser, boolean isOpenCde, boolean isPanorama, boolean isRequestCacheAD, boolean isPush, boolean isNeedProllAd, boolean isNeedMidProllAd) {
        if (this.mRemoteFragment != null) {
            return (HashMap) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getVODFrontADParameter", new Class[]{String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, uuid, uid, py, ty, Boolean.valueOf(isSupportM3U8), Boolean.valueOf(disableAvd), Boolean.valueOf(toShowLoading), Boolean.valueOf(isWoOrderUser), Boolean.valueOf(isOpenCde), Boolean.valueOf(isPanorama), Boolean.valueOf(isRequestCacheAD), Boolean.valueOf(isPush), Boolean.valueOf(isNeedProllAd), Boolean.valueOf(isNeedMidProllAd));
        } else if (this.mAdListener == null) {
            return null;
        } else {
            this.mAdListener.handleADUrlAcquireDone(null, null, 0, false);
            this.mAdListener.handleADFinish(false);
            return null;
        }
    }

    public synchronized void getOfflineFrontAd(int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty, boolean isVipVideo, boolean disableAvd, boolean toShowLoading, boolean isOfflineAds) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getDemandFrontAd", new Class[]{Integer.TYPE, Long.TYPE, Long.TYPE, String.class, String.class, String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, Integer.valueOf(cid), Long.valueOf(aid), Long.valueOf(vid), mmsid, uuid, uid, vlen, py, ty, Boolean.valueOf(false), Boolean.valueOf(isVipVideo), Boolean.valueOf(disableAvd), Boolean.valueOf(toShowLoading), Boolean.valueOf(false), Boolean.valueOf(isOfflineAds), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false));
        } else if (this.mAdListener != null) {
            this.mAdListener.handleADUrlAcquireDone(null, null, 0, false);
            this.mAdListener.handleADFinish(false);
        }
    }

    public synchronized void getOfflineFrontAd(int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty, boolean isVipVideo, boolean disableAvd, boolean toShowLoading, boolean isOfflineAds, boolean isRequestCacheAD) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getDemandFrontAd", new Class[]{Integer.TYPE, Long.TYPE, Long.TYPE, String.class, String.class, String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, Integer.valueOf(cid), Long.valueOf(aid), Long.valueOf(vid), mmsid, uuid, uid, vlen, py, ty, Boolean.valueOf(false), Boolean.valueOf(isVipVideo), Boolean.valueOf(disableAvd), Boolean.valueOf(toShowLoading), Boolean.valueOf(false), Boolean.valueOf(isOfflineAds), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(isRequestCacheAD));
        } else if (this.mAdListener != null) {
            this.mAdListener.handleADUrlAcquireDone(null, null, 0, false);
            this.mAdListener.handleADFinish(false);
        }
    }

    public synchronized void getDemandFrontAdForHot(Context context, int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty, boolean isSupportM3U8, boolean isNeedPay, boolean disableAvd, boolean toShowLoading) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getDemandFrontAdForHot", new Class[]{Context.class, Integer.TYPE, Long.TYPE, Long.TYPE, String.class, String.class, String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, context, Integer.valueOf(cid), Long.valueOf(aid), Long.valueOf(vid), mmsid, uuid, uid, vlen, py, ty, Boolean.valueOf(isSupportM3U8), Boolean.valueOf(isNeedPay), Boolean.valueOf(disableAvd), Boolean.valueOf(toShowLoading));
        } else if (this.mAdListener != null) {
            this.mAdListener.handleADUrlAcquireDone(null, null, 0, false);
            this.mAdListener.handleADFinish(false);
        }
    }

    public synchronized void getLiveFrontAd(String streamUrl, String uuid, String uid, String py, String ty, boolean toShowLoading, boolean isWoOrderUser, boolean isNeedPay, boolean isSupportM3U8, boolean isUseCde, boolean isPanorama) {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getLiveFrontAd", new Class[]{String.class, String.class, String.class, String.class, String.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE}, streamUrl, uuid, uid, py, ty, Boolean.valueOf(toShowLoading), Boolean.valueOf(isWoOrderUser), Boolean.valueOf(isNeedPay), Boolean.valueOf(isSupportM3U8), Boolean.valueOf(isUseCde), Boolean.valueOf(isPanorama));
        } else if (this.mAdListener != null) {
            this.mAdListener.handleADUrlAcquireDone(null, null, 0, false);
            this.mAdListener.handleADFinish(false);
        }
    }

    public synchronized void getDemandPauseAd() {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getDemandPauseAd", null, new Object[0]);
        }
    }

    public IVideoStatusInformer getIVideoStatusInformer() {
        if (this.mRemoteFragment != null) {
            return (IVideoStatusInformer) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getIVideoStatusInformer", null, new Object[0]);
        }
        return null;
    }

    public IClientADEventInformer getIADEventInformer() {
        if (this.mRemoteFragment != null) {
            return (IClientADEventInformer) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getIADEventInformer", null, new Object[0]);
        }
        return null;
    }

    public int getAdsVideoTotalTime() {
        if (this.mRemoteFragment != null) {
            return ((Integer) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsVideoTotalTime", null, new Object[0])).intValue();
        }
        return 0;
    }

    public long getAdsLoadingTime() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsLoadingTime", null, new Object[0])).longValue();
        }
        return 0;
    }

    public long getAdsCombineCostTime() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsCombineCostTime", null, new Object[0])).longValue();
        }
        return 0;
    }

    public long getAdsPlayLoadTime() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsPlayLoadTime", null, new Object[0])).longValue();
        }
        return 0;
    }

    public long getAdsPlayFirstFrameTime() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsPlayFirstFrameTime", null, new Object[0])).longValue();
        }
        return 0;
    }

    public long getAdsPlayCompleteTime() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsPlayCompleteTime", null, new Object[0])).longValue();
        }
        return 0;
    }

    public long getAdsCostPlayTimeInFact() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsCostPlayTimeInFact", null, new Object[0])).longValue();
        }
        return 0;
    }

    public long getAdsInteractiveTimeInFact() {
        if (this.mRemoteFragment != null) {
            return ((Long) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "getAdsInteractiveTimeInFact", null, new Object[0])).longValue();
        }
        return 0;
    }

    public boolean isFinishAd() {
        if (this.mRemoteFragment != null) {
            return ((Boolean) ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "isFinishAd", null, new Object[0])).booleanValue();
        }
        return true;
    }

    public void clearAdFullProcessTimeout() {
        if (this.mRemoteFragment != null) {
            ReflectionUtils.callADObjectMethod(this.mRemoteFragment, this.mRemoteFragment.getClass(), "clearAdFullProcessTimeout", null, new Object[0]);
        }
    }
}
