package com.letv.ads.ex.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.adlib.sdk.types.AdElement.AdCommonType;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.adlib.sdk.utils.LogInfo;
import com.letv.ads.ex.client.AdPlayStateListener;
import com.letv.ads.ex.ui.IAdView.AdMaterialLoadListener;
import com.letv.ads.ex.ui.IAdView.AdViewOnclickListenr;
import com.letv.ads.ex.utils.ReflectionUtils;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.application.JarApplication;
import com.letv.plugin.pluginloader.loader.JarClassLoader;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.loader.JarResources;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class AdViewProxy extends RelativeLayout {
    private static final String TAG = "AdViewProxy";
    private static Class mLoadClass;
    private JarClassLoader mJarClassLoader;
    private JarResources mJarResources;
    private RelativeLayout mRemoteView;

    public interface ClientListener {
        boolean handleADClick(AdElementMime adElementMime);
    }

    public AdViewProxy(Context context) {
        super(context);
        init(context, null);
    }

    public AdViewProxy(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            this.mJarClassLoader = JarLoader.getJarClassLoader(context, "Letv_Ads.apk", JarConstant.LETV_ADS_PACKAGENAME);
            this.mJarResources = JarResources.getResourceByCl(this.mJarClassLoader, context);
            mLoadClass = JarLoader.loadClass(context, "Letv_Ads.apk", JarConstant.LETV_ADS_PACKAGENAME, JarConstant.LETV_ADS_VIEW_CLASS);
            Constructor<?> localConstructor = mLoadClass.getConstructor(new Class[]{Context.class, AttributeSet.class, Resources.class});
            if (this.mJarResources != null) {
                updateConfiguration(this.mJarResources.getResources());
                this.mRemoteView = (RelativeLayout) localConstructor.newInstance(new Object[]{context, attrs, this.mJarResources.getResources()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogInfo.log(TAG, "Exception e=" + e.toString());
        }
        if (this.mRemoteView != null) {
            LogInfo.log(TAG, "addView mRemoteView=" + this.mRemoteView);
            addView(this.mRemoteView, new LayoutParams(-1, -1));
        }
    }

    private void updateConfiguration(Resources resources) {
        LogInfo.log(TAG, "updateConfiguration resources=" + resources);
        if (JarApplication.getInstance() != null && JarApplication.getInstance().getResources() != null) {
            Resources superRes = JarApplication.getInstance().getResources();
            Configuration configuration = superRes.getConfiguration();
            LogInfo.log(TAG, "updateConfiguration configuration=" + configuration);
            DisplayMetrics displayMetrics = superRes.getDisplayMetrics();
            if (resources != null && configuration != null && displayMetrics != null) {
                resources.updateConfiguration(configuration, displayMetrics);
            }
        }
    }

    public void setCanClose(boolean canClose) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setCanClose", new Class[]{Boolean.TYPE}, Boolean.valueOf(canClose));
        }
    }

    public void setBootView(View bootMask, View contentView) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setBootView", new Class[]{View.class, View.class}, bootMask, contentView);
        }
    }

    public void setAdType(AdCommonType adType) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setAdType", new Class[]{AdCommonType.class}, adType);
        }
    }

    public void showHomePageBanner(ArrayList<AdElementMime> adsList) {
        LogInfo.log(TAG, "showHomePageBanner adsList=" + adsList + "  mRemoteView=" + this.mRemoteView);
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "showHomePageBanner", new Class[]{ArrayList.class}, adsList);
        }
    }

    public void showAD(AdElementMime adInfo) {
        Log.d(TAG, "showAD adInfo=" + adInfo + "  mRemoteView=" + this.mRemoteView);
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "showAD", new Class[]{AdElementMime.class}, adInfo);
        }
    }

    public void onPause() {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "onPause", null, new Object[0]);
        }
    }

    public void onStop() {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "onStop", null, new Object[0]);
        }
    }

    public void closeAD() {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "closeAD", null, new Object[0]);
        }
    }

    public void setAdOnClickListener(AdViewOnclickListenr adViewOnclickListenr) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setAdOnClickListener", new Class[]{AdViewOnclickListenr.class}, adViewOnclickListenr);
        }
    }

    public void setAdMaterialLoadListener(AdMaterialLoadListener adMaterialLoadListener) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setAdMaterialLoadListener", new Class[]{AdMaterialLoadListener.class}, adMaterialLoadListener);
        }
    }

    public void setAdPlayStateListener(AdPlayStateListener adPlayStateListener) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setAdPlayStateListener", new Class[]{AdPlayStateListener.class}, adPlayStateListener);
        }
    }

    public void setClientListener(ClientListener listener) {
        if (this.mRemoteView != null) {
            ReflectionUtils.callADVoidMethod(this.mRemoteView, this.mRemoteView.getClass(), "setClientListener", new Class[]{ClientListener.class}, listener);
        }
    }

    public static void sendExposure(AdElementMime adInfo) {
        ReflectionUtils.callADVoidMethod(null, mLoadClass, "sendExposure", new Class[]{AdElementMime.class}, adInfo);
    }
}
