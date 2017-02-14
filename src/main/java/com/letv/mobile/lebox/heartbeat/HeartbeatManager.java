package com.letv.mobile.lebox.heartbeat;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.letv.core.db.PreferencesManager;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.heartbeat.HeartbeatService.HeartbeatServiceBinder;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;

public class HeartbeatManager {
    private static final String TAG = HeartbeatManager.class.getSimpleName();
    public static boolean isWifiAPAutoConnect = true;
    private static HeartbeatManager mHeartbeatManager;
    private boolean flag;
    private Application mApplication;
    private final Context mContext = LeBoxApp.getApplication();
    HeartbeatObservable mHeartbeatObservable = new HeartbeatObservable();
    private HeartbeatService mHeartbeatService;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
            HeartbeatManager.this.flag = false;
            Logger.d(HeartbeatManager.TAG, "---------onServiceConnected------------heartbeat service unbind ------------");
            HeartbeatManager.this.notifyStateChange(7);
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            HeartbeatManager.this.mHeartbeatService = ((HeartbeatServiceBinder) service).getService();
            HeartbeatManager.this.flag = true;
            Logger.d(HeartbeatManager.TAG, "---------onServiceConnected------------heartbeat service bind success------------");
            HeartbeatManager.this.notifyStateChange(7);
        }
    };

    private HeartbeatManager() {
        bindService();
    }

    public static synchronized HeartbeatManager getInstance() {
        HeartbeatManager heartbeatManager;
        synchronized (HeartbeatManager.class) {
            if (mHeartbeatManager == null) {
                mHeartbeatManager = new HeartbeatManager();
            }
            heartbeatManager = mHeartbeatManager;
        }
        return heartbeatManager;
    }

    public void release() {
        synchronized (HeartbeatManager.class) {
            mHeartbeatManager = null;
        }
    }

    private void bindService() {
        if (!LeboxQrCodeBean.getSsid().isEmpty() && PreferencesManager.getInstance().isLeboxEnable()) {
            this.mContext.bindService(new Intent(this.mContext, HeartbeatService.class), this.mServiceConnection, 1);
        }
    }

    private void unBind() {
        if (this.flag) {
            this.mContext.unbindService(this.mServiceConnection);
            this.flag = false;
        }
    }

    public void onStartService() {
        bindService();
    }

    public void onStopService() {
        unBind();
    }

    public void regustHeartbeatObserver(HeartbeatObserver observer) {
        this.mHeartbeatObservable.addObserver(observer);
    }

    public void unRegustHeartbeatObserver(HeartbeatObserver observer) {
        this.mHeartbeatObservable.deleteObserver(observer);
    }

    public void notifyStateChange(int type) {
        this.mHeartbeatObservable.notifyObserversHasChanged(type);
    }

    public void cleanHeartbeatObserver() {
        this.mHeartbeatObservable.deleteObservers();
    }

    public void onStartHeartbeat() {
        if (this.mHeartbeatService == null) {
            bindService();
        } else if (!this.mHeartbeatService.isRunHeartbeat()) {
            this.mHeartbeatService.startHeartbeat();
        }
    }

    public void onStopHeartbeat() {
        if (this.mHeartbeatService != null) {
            this.mHeartbeatService.stopHeartbeat();
        } else {
            bindService();
        }
    }

    public void onStartNetworkConnectionWatcher() {
        if (this.mHeartbeatService == null) {
            bindService();
        } else if (!this.mHeartbeatService.isNeedWatcher()) {
            this.mHeartbeatService.startNetworkConnectionWatcher();
        }
    }

    public void onStopNetworkConnectionWatcher() {
        if (this.mHeartbeatService != null) {
            this.mHeartbeatService.stopNetworkConnectionWatcher();
        } else {
            bindService();
        }
    }

    public String getDeviceMode() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getDeviceMode();
        }
        bindService();
        return null;
    }

    public String getSsid() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getSsid();
        }
        bindService();
        return null;
    }

    public boolean getHasInternet() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getHasInternet();
        }
        bindService();
        return false;
    }

    public String getUpdateState() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getUpdateState();
        }
        bindService();
        return null;
    }

    public String getDownloadingTaskVid() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getDownloadingTaskVid();
        }
        bindService();
        return null;
    }

    public String getDownloadingTaskPr() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getDownloadingTaskPr();
        }
        bindService();
        return null;
    }

    public String getCompletedVersion() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getCompletedVersion();
        }
        bindService();
        return null;
    }

    public String getUnFinishedVersion() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getUnFinishedVersion();
        }
        bindService();
        return null;
    }

    public int getCurrentHeartbeatTime() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getCurrentHeartbeatTime();
        }
        bindService();
        return 0;
    }

    public void setCurrentHeartbeatTime(int currentHeartbeatTime) {
        if (this.mHeartbeatService == null) {
            bindService();
        } else {
            this.mHeartbeatService.setCurrentHeartbeatTime(currentHeartbeatTime);
        }
    }

    public boolean isRunHeartbeat() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.isRunHeartbeat();
        }
        bindService();
        return false;
    }

    public boolean isNeedWatcher() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.isNeedWatcher();
        }
        bindService();
        return false;
    }

    public boolean isLeboxHasInternet() {
        if (this.mHeartbeatService != null) {
            return this.mHeartbeatService.getHasInternet();
        }
        bindService();
        return false;
    }

    public Application getApplication() {
        if (this.mApplication != null || this.mHeartbeatService == null) {
            return this.mApplication;
        }
        Application application = this.mHeartbeatService.getApplication();
        this.mApplication = application;
        return application;
    }

    public void setApplication(Application mApplication) {
        this.mApplication = mApplication;
    }

    public boolean onStartConnectLebox() {
        if (this.mHeartbeatService == null) {
            bindService();
            return false;
        }
        this.mHeartbeatService.checkNetworkConnection();
        return true;
    }
}
