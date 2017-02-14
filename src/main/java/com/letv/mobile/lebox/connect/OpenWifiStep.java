package com.letv.mobile.lebox.connect;

import android.os.Message;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatObserver;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Logger;

public class OpenWifiStep extends BaseStep {
    private static final String TAG = OpenWifiStep.class.getSimpleName();
    HeartbeatObserver mHeartbeatObserver;

    public void startStep(StepsQueue stepsQueue) {
        this.timeOut = 5000;
        this.mStepsQueue = stepsQueue;
        if (LeBoxNetworkManager.getInstance().isWifiEnable()) {
            Logger.d(TAG, "---------is wifi already open-------------------");
            LeboxConnectManager.getInstance().notifyProgress(14);
            onResult(stepsQueue, true, true);
            return;
        }
        this.mHeartbeatObserver = new 1(this, stepsQueue);
        HeartbeatManager.getInstance().regustHeartbeatObserver(this.mHeartbeatObserver);
        stepsQueue.getHandler().sendEmptyMessageDelayed(0, (long) this.timeOut);
        if (LeBoxNetworkManager.getInstance().openWifi()) {
            LeboxConnectManager.getInstance().notifyProgress(12);
        } else {
            LeboxConnectManager.getInstance().notifyProgress(11);
        }
    }

    protected void loopCheck(Message msg) {
        if (this.mStepsQueue != null) {
            this.mStepsQueue.getHandler().removeMessages(0);
        }
        if (this.mHeartbeatObserver != null) {
            HeartbeatManager.getInstance().unRegustHeartbeatObserver(this.mHeartbeatObserver);
        }
        if (LeBoxNetworkManager.getInstance().isWifiEnable()) {
            Logger.d(TAG, "-----on time out----is wifi  open  success-------------------");
            LeboxConnectManager.getInstance().notifyProgress(14);
            onResult(this.mStepsQueue, true, true);
            return;
        }
        Logger.d(TAG, "-----on time out----is wifi  open  fail-------------------");
        LeboxConnectManager.getInstance().notifyProgress(13);
        onResult(this.mStepsQueue, false, false);
    }
}
