package com.letv.mobile.lebox.connect;

import android.os.Handler;
import android.os.Message;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Logger;
import java.util.HashMap;
import java.util.Map;

public class LeboxConnectManager {
    private static final String TAG = LeboxConnectManager.class.getSimpleName();
    private static LeboxConnectManager mLeboxConnectManager;
    private static StepsQueue mQueue;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    LeboxConnectManager.this.mHandler.removeMessages(0);
                    if (LeboxConnectManager.mQueue != null) {
                        if (LeboxConnectManager.mQueue.isFinish()) {
                            Map<String, Integer> timeOutMap = new HashMap();
                            timeOutMap.put(SearchLeboxStep.class.getSimpleName(), Integer.valueOf(30000));
                            timeOutMap.put(ConnectStep.class.getSimpleName(), Integer.valueOf(30000));
                            LeboxConnectManager.this.addSteps(timeOutMap);
                        }
                        Logger.d(LeboxConnectManager.TAG, "-------startDelayConnect-----mQueue--isHasStep=" + LeboxConnectManager.mQueue.isHasStep());
                        LeboxConnectManager.mQueue.startStep();
                        break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private ConnectProgressReciver mReciver;

    public interface ConnectProgressReciver {
        void notifyProgress(int i);
    }

    private LeboxConnectManager() {
        mQueue = new StepsQueue();
    }

    public static LeboxConnectManager getInstance() {
        if (mLeboxConnectManager != null) {
            return mLeboxConnectManager;
        }
        synchronized (LeboxConnectManager.class) {
            mLeboxConnectManager = new LeboxConnectManager();
        }
        return mLeboxConnectManager;
    }

    public static void release() {
        if (mQueue == null || mQueue.isFinish()) {
            mQueue = null;
        } else {
            mQueue.cleanQueue();
            mQueue = null;
        }
        mLeboxConnectManager = null;
    }

    public synchronized void startConnect() {
        if (mQueue.isFinish()) {
            addSteps();
        }
        Logger.d(TAG, "-------startConnect-----mQueue--isHasStep=" + mQueue.isHasStep());
        mQueue.startStep();
    }

    public synchronized void startDelayConnect(int delayTime) {
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessageDelayed(0, (long) delayTime);
    }

    private synchronized void addSteps() {
        addSteps(null);
    }

    private synchronized void addSteps(Map<String, Integer> timeOutMap) {
        Logger.d(TAG, "----addSteps-----isLeboxWifi=" + LeBoxNetworkManager.getInstance().isLeboxWifi() + "--isLeboxWifiAvailable=" + LeBoxNetworkManager.getInstance().isLeboxWifiAvailable() + "--isConnectionWifiDirect=" + LeBoxNetworkManager.getInstance().isConnectionWifiDirect());
        if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable()) {
            Logger.d(TAG, "-----queue add LoginStep");
            mQueue.add(new LoginStep());
        } else {
            if (!LeBoxNetworkManager.getInstance().isWifiEnable()) {
                Logger.d(TAG, "-----queue add OpenWifiStep");
                upDateTimeOut(mQueue.add(new OpenWifiStep()), timeOutMap);
            }
            if (!LeBoxNetworkManager.getInstance().isLeboxConnected()) {
                Logger.d(TAG, "-----queue add SearchLeboxStep");
                upDateTimeOut(mQueue.add(new SearchLeboxStep()), timeOutMap);
            }
            if (!LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable()) {
                Logger.d(TAG, "-----queue add ConnectStep");
                upDateTimeOut(mQueue.add(new ConnectStep()), timeOutMap);
            }
        }
    }

    private void upDateTimeOut(BaseStep step, Map<String, Integer> timeOutMap) {
        if (timeOutMap != null && timeOutMap.size() != 0) {
            int timeout = ((Integer) timeOutMap.get(step.getClass().getSimpleName())).intValue();
            if (timeout > 0) {
                step.setTimeOut(timeout);
            }
        }
    }

    public void setConnectProgressReciver(ConnectProgressReciver reciver) {
        this.mReciver = reciver;
    }

    void notifyProgress(int p) {
        if (this.mReciver != null) {
            this.mReciver.notifyProgress(p);
        }
    }
}
