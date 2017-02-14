package com.letv.mobile.lebox;

import android.app.Application;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Process;
import com.letv.mobile.http.HttpGlobalConfig;
import com.letv.mobile.http.utils.ContextProvider;
import com.letv.mobile.http.utils.StringUtils;
import com.letv.mobile.lebox.config.LeBoxAppConfig;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.imagecache.LetvCacheMannager;
import com.letv.mobile.letvhttplib.HttpLibApp;
import java.util.UUID;

public class LeBoxApp {
    private static final int EXIT_DELAY_TIME = 500;
    private static final String TAG = "LetvApplication";
    private static Application mApplication;
    private static LeBoxApp mLeBoxApp = null;

    private LeBoxApp() {
    }

    public static synchronized LeBoxApp getInstanced() {
        LeBoxApp leBoxApp;
        synchronized (LeBoxApp.class) {
            if (mLeBoxApp == null) {
                mLeBoxApp = new LeBoxApp();
            }
            leBoxApp = mLeBoxApp;
        }
        return leBoxApp;
    }

    public void init(Application application) {
        mApplication = application;
        ContextProvider.init(application);
        LetvCacheMannager.getInstance().init(mApplication);
        HttpLibApp.initCreate(application);
        init(LeBoxAppConfig.getAppName());
        HeartbeatManager.getInstance();
        HeartbeatManager.getInstance().setApplication(application);
    }

    protected void init(String appName) {
        initGlobalConfig();
        if (StringUtils.equalsNull(LeboxQrCodeBean.getCode())) {
            LeboxQrCodeBean.setCode(UUID.randomUUID().toString());
        }
    }

    private static void initGlobalConfig() {
        Logger.i(TAG, "LetvApplication initGlobalConfig()");
        HttpGlobalConfig.init(LeBoxAppConfig.getDynamicDomain(), LeBoxAppConfig.getStaticDomain(), LeBoxAppConfig.getReportDomain(), LeBoxAppConfig.getDynamicLoopingIps(), LeBoxAppConfig.getStaticLoopingIps(), LeBoxAppConfig.isNeedIpLooping(), LeBoxAppConfig.isHttpDebug(), LeBoxAppConfig.isNeedHttpCache());
    }

    public static Application getApplication() {
        if (mApplication == null && HeartbeatManager.getInstance().getApplication() != null) {
            mApplication = HeartbeatManager.getInstance().getApplication();
        }
        return mApplication;
    }

    public void onTerminate() {
        exitApp();
    }

    public static void exitApp() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Process.killProcess(Process.myPid());
            }
        }, 500);
    }

    public Resources getResources() {
        return mApplication.getResources();
    }

    public String getString(int id) {
        return getResources().getString(id);
    }
}
