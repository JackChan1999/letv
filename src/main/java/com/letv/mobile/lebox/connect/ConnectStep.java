package com.letv.mobile.lebox.connect;

import android.os.Message;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatObserver;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Logger;

public class ConnectStep extends BaseStep {
    private static final String TAG = ConnectStep.class.getSimpleName();
    HeartbeatObserver mHeartbeatObserver;

    public void startStep(StepsQueue stepsQueue) {
        this.timeOut = 20000;
        this.mStepsQueue = stepsQueue;
        if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable()) {
            Logger.d(TAG, "---------is lebox already connected-------------------");
            LeboxConnectManager.getInstance().notifyProgress(21);
            onResult(stepsQueue, true, true);
            return;
        }
        LeboxConnectManager.getInstance().notifyProgress(19);
        this.mHeartbeatObserver = new 1(this, stepsQueue);
        HeartbeatManager.getInstance().regustHeartbeatObserver(this.mHeartbeatObserver);
        HeartbeatManager.getInstance().onStartConnectLebox();
        stepsQueue.getHandler().sendEmptyMessageDelayed(0, (long) this.timeOut);
    }

    protected void loopCheck(Message msg) {
        if (this.mStepsQueue != null) {
            this.mStepsQueue.getHandler().removeMessages(0);
        }
        if (this.mHeartbeatObserver != null) {
            HeartbeatManager.getInstance().unRegustHeartbeatObserver(this.mHeartbeatObserver);
        }
        if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable()) {
            Logger.d(TAG, "----on time out-----is lebox  connected  success-------------------");
            LeboxConnectManager.getInstance().notifyProgress(21);
            onResult(this.mStepsQueue, true, true);
            return;
        }
        Logger.d(TAG, "----on time out-----is  lebox connected fail-------------------");
        LeboxConnectManager.getInstance().notifyProgress(20);
        onResult(this.mStepsQueue, false, false);
    }
}
