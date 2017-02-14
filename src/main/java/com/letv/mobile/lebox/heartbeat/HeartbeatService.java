package com.letv.mobile.lebox.heartbeat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.broadcast.NetBroadcastReceiver;
import com.letv.mobile.lebox.http.lebox.bean.HeartBeatBean;
import com.letv.mobile.lebox.http.lebox.bean.TaskPro;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager.HttpCallBack;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import java.util.List;

public class HeartbeatService extends Service {
    public static final int AUTO_CONNECT_CHECK_INTERVAL_LONG = 600000;
    public static final int AUTO_CONNECT_CHECK_INTERVAL_NOMAL = 60000;
    public static final int AUTO_CONNECT_CHECK_INTERVAL_SHORT = 10000;
    public static final int HEARTBEAT_INTERVAL_LONG = 30000;
    public static final int HEARTBEAT_INTERVAL_NOMAL = 7000;
    public static final int HEARTBEAT_INTERVAL_SHORT = 3000;
    private static final int MSG_TAG_AUTO_CONNECT = 12;
    private static final int MSG_TAG_HEARTBEAT = 11;
    private static final String TAG = HeartbeatService.class.getSimpleName();
    HttpCallBack<HeartBeatBean> callBack = new HttpCallBack<HeartBeatBean>() {
        public void callback(int code, String msg, String errorCode, HeartBeatBean object) {
            if (code == 0 && "0".equals(errorCode) && object != null) {
                HeartBeatBean bean = object;
                HeartbeatService.this.mLastHeartBeatBean = HeartbeatService.this.mCurrentHeartBeatBean;
                HeartbeatService.this.mCurrentHeartBeatBean = bean;
                HeartbeatService.this.HeartBeatReader();
            } else if (1 == code && "1".equals(errorCode)) {
                HttpRequesetManager.getInstance().getUserPermission(new HttpCallBack<String>() {
                    public void callback(int code, String msg, String errorCode, String object) {
                        HeartbeatManager.getInstance().notifyStateChange(9);
                    }
                });
            } else if (code == 1 && !TextUtils.isEmpty(errorCode)) {
            }
        }
    };
    private boolean isNeedWatcher;
    private boolean isRunHeartbeat;
    private final HeartbeatServiceBinder mBinder = new HeartbeatServiceBinder();
    private HeartBeatBean mCurrentHeartBeatBean;
    private int mCurrentHeartbeatTime;
    private int mCurrentNetCheckTime;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    Logger.d(HeartbeatService.TAG, "-----Execute heartbeat message-------");
                    if (Util.isBackground(LeBoxApp.getApplication())) {
                        Logger.d(HeartbeatService.TAG, "--no--runHeartBeat---APP is background");
                    } else {
                        Logger.d(HeartbeatService.TAG, "----runHeartBeat---APP is not background");
                        HttpRequesetManager.getInstance().runHeartBeat(HeartbeatService.this.callBack);
                    }
                    removeMessages(11);
                    if (HeartbeatService.this.isRunHeartbeat) {
                        sendEmptyMessageDelayed(11, (long) HeartbeatService.this.mCurrentHeartbeatTime);
                        break;
                    }
                    break;
                case 12:
                    Logger.d(HeartbeatService.TAG, "-----Execute net link polling-------");
                    boolean isNeed = HeartbeatService.this.checkNetworkConnection();
                    removeMessages(12);
                    if (isNeed && HeartbeatService.this.isNeedWatcher) {
                        if (LeBoxNetworkManager.needScanNum > 0) {
                            HeartbeatService.this.mCurrentNetCheckTime = 10000;
                        } else {
                            HeartbeatService.this.mCurrentNetCheckTime = HeartbeatService.AUTO_CONNECT_CHECK_INTERVAL_NOMAL;
                        }
                        sendEmptyMessageDelayed(12, (long) HeartbeatService.this.mCurrentNetCheckTime);
                        break;
                    }
            }
            super.handleMessage(msg);
        }
    };
    private HeartBeatBean mLastHeartBeatBean;
    BroadcastReceiver mReceiver = new NetBroadcastReceiver();

    public class HeartbeatServiceBinder extends Binder {
        HeartbeatService getService() {
            return HeartbeatService.this;
        }
    }

    public void onCreate() {
        super.onCreate();
        this.mCurrentHeartbeatTime = HEARTBEAT_INTERVAL_NOMAL;
        this.mCurrentNetCheckTime = AUTO_CONNECT_CHECK_INTERVAL_NOMAL;
        if (!LeboxQrCodeBean.getSsid().isEmpty()) {
            initRegister();
        }
        initData();
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onDestroy() {
        UnRegister();
        super.onDestroy();
    }

    private void initRegister() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("android.net.wifi.STATE_CHANGE");
        mFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        mFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
        mFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
        mFilter.addAction("android.net.wifi.SCAN_RESULTS");
        registerReceiver(this.mReceiver, mFilter);
    }

    private void UnRegister() {
        try {
            unregisterReceiver(this.mReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        if (LeBoxNetworkManager.getInstance().isLeboxWifi()) {
            startNetworkConnectionWatcher();
            startHeartbeat();
        } else if (LeBoxNetworkManager.getInstance().isWifiEnable()) {
            startNetworkConnectionWatcher();
        }
    }

    void startHeartbeat() {
        Logger.d(TAG, "--------startHeartbeat-------------------------------------isAdmini=" + HttpCacheAssistant.getInstanced().isAdmini());
        if (HttpCacheAssistant.getInstanced().isLogin()) {
            if (HttpCacheAssistant.getInstanced().isAdmini()) {
                setCurrentHeartbeatTime(HEARTBEAT_INTERVAL_NOMAL);
            } else {
                setCurrentHeartbeatTime(30000);
            }
            this.isRunHeartbeat = true;
            this.mHandler.removeMessages(11);
            this.mHandler.sendEmptyMessage(11);
            return;
        }
        this.isRunHeartbeat = false;
    }

    void stopHeartbeat() {
        this.isRunHeartbeat = false;
        this.mHandler.removeMessages(11);
    }

    public boolean isRunHeartbeat() {
        return this.isRunHeartbeat;
    }

    public boolean isNeedWatcher() {
        return this.isNeedWatcher;
    }

    void startNetworkConnectionWatcher() {
        Logger.d(TAG, "----------------------startNetworkConnectionWatcher-------------------------------------------");
        this.isNeedWatcher = true;
        this.mHandler.removeMessages(12);
        this.mHandler.sendEmptyMessage(12);
    }

    void stopNetworkConnectionWatcher() {
        this.isNeedWatcher = false;
        this.mHandler.removeMessages(12);
    }

    int getCurrentHeartbeatTime() {
        return this.mCurrentHeartbeatTime;
    }

    void setCurrentHeartbeatTime(int currentHeartbeatTime) {
        this.mCurrentHeartbeatTime = currentHeartbeatTime;
    }

    private void HeartBeatReader() {
        if (this.mCurrentHeartBeatBean != null && this.mLastHeartBeatBean != null) {
            String completedVersion = getCompletedVersion();
            if (!(TextUtils.isEmpty(completedVersion) || completedVersion.equals(this.mLastHeartBeatBean.getCompletedVersion()))) {
                downTaskVersionChange(0);
            }
            String unFinishedVersion = getUnFinishedVersion();
            if (!(TextUtils.isEmpty(unFinishedVersion) || unFinishedVersion.equals(this.mLastHeartBeatBean.getUnFinishedVersion()))) {
                downTaskVersionChange(1);
            }
            String taskVid = getDownloadingTaskVid();
            String taskPr = getDownloadingTaskPr();
            String taskpro = getDownloadingTaskProgress();
            if (!((TextUtils.isEmpty(taskVid) || taskVid.equals(this.mLastHeartBeatBean.getDownloadingTaskVid())) && ((TextUtils.isEmpty(taskPr) || taskPr.equals(this.mLastHeartBeatBean.getDownloadingTaskPr())) && (TextUtils.isEmpty(taskpro) || taskpro.equals(this.mLastHeartBeatBean.getDownloadingTaskProgress()))))) {
                if (HttpCacheAssistant.getInstanced().checkNotCompletedListInfoHaveNull()) {
                    HttpRequesetManager.getInstance().getTaskAllBeanList(new HttpCallBack<List<TaskVideoBean>>() {
                        public void callback(int code, String msg, String errorCode, List<TaskVideoBean> list) {
                            if (HttpCacheAssistant.getInstanced().setDownloadingUpdate(HeartbeatService.this.getDownloadingTaskPro())) {
                                HeartbeatManager.getInstance().notifyStateChange(2);
                            }
                        }
                    }, "2");
                } else if (HttpCacheAssistant.getInstanced().setDownloadingUpdate(getDownloadingTaskPro())) {
                    HeartbeatManager.getInstance().notifyStateChange(2);
                }
            }
            String updateState = getUpdateState();
            if (!(TextUtils.isEmpty(updateState) || updateState.equals(this.mLastHeartBeatBean.getUpdateState()))) {
                HeartbeatManager.getInstance().notifyStateChange(3);
            }
            if (getHasInternet() != this.mLastHeartBeatBean.getHasInternet()) {
                HeartbeatManager.getInstance().notifyStateChange(14);
            }
            String ssid = getSsid();
            if ((ssid != null && !ssid.equals(this.mLastHeartBeatBean.getSsid())) || (ssid == null && this.mLastHeartBeatBean.getSsid() != null)) {
                HeartbeatManager.getInstance().notifyStateChange(15);
            }
        }
    }

    private void downTaskVersionChange(int tag) {
        switch (tag) {
            case 0:
                HttpRequesetManager.getInstance().getAllDownloadFinishTask(new HttpCallBack<List<TaskVideoBean>>() {
                    public void callback(int code, String msg, String errorCode, List<TaskVideoBean> list) {
                        HeartbeatManager.getInstance().notifyStateChange(0);
                    }
                });
                return;
            case 1:
                HttpRequesetManager.getInstance().getAllUnfinishTask(new HttpCallBack<List<TaskVideoBean>>() {
                    public void callback(int code, String msg, String errorCode, List<TaskVideoBean> list) {
                        HeartbeatManager.getInstance().notifyStateChange(1);
                    }
                });
                return;
            default:
                return;
        }
    }

    public boolean checkNetworkConnection() {
        boolean isLeBoxwifi = LeBoxNetworkManager.getInstance().isLeboxWifiAvailable();
        Logger.d(TAG, "---checkNetworkConnection----isLeBoxwifi=" + isLeBoxwifi);
        if (isLeBoxwifi) {
            return false;
        }
        boolean isHasLeboxAp = LeBoxNetworkManager.getInstance().isHasLeboxAp();
        Logger.d(TAG, "---checkNetworkConnection----isHasLeboxAp=" + isHasLeboxAp);
        if (isHasLeboxAp && HeartbeatManager.isWifiAPAutoConnect && !LeboxQrCodeBean.getSsid().isEmpty()) {
            LeBoxNetworkManager.getInstance().switchLeboxWifi();
        } else if (!(Util.isBackground(this) || LeboxQrCodeBean.getSsid().isEmpty())) {
            discoverP2pDevice();
        }
        return true;
    }

    void discoverP2pDevice() {
        LeBoxNetworkManager.getInstance().discoverP2pPeers(new ActionListener() {
            public void onSuccess() {
                Logger.d(HeartbeatService.TAG, "---discoverP2pDevice--discoverPeers--onSuccess");
            }

            public void onFailure(int reason) {
                Logger.d(HeartbeatService.TAG, "---discoverP2pDevice--discoverPeers--onFailure reason=" + reason);
            }
        });
    }

    String getDeviceMode() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getDeviceMode();
    }

    String getSsid() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getSsid();
    }

    boolean getHasInternet() {
        if (this.mCurrentHeartBeatBean == null) {
            return false;
        }
        return this.mCurrentHeartBeatBean.getHasInternet();
    }

    String getUpdateState() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getUpdateState();
    }

    String getDownloadingTaskVid() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getDownloadingTaskVid();
    }

    String getDownloadingTaskPr() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getDownloadingTaskPr();
    }

    private TaskPro getDownloadingTaskPro() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getTaskPro();
    }

    String getDownloadingTaskProgress() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getDownloadingTaskProgress();
    }

    String getCompletedVersion() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getCompletedVersion();
    }

    String getUnFinishedVersion() {
        if (this.mCurrentHeartBeatBean == null) {
            return null;
        }
        return this.mCurrentHeartBeatBean.getUnFinishedVersion();
    }
}
