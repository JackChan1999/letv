package com.letv.mobile.lebox.connect;

import android.os.Message;
import com.letv.core.constant.LetvConstant;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatObserver;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Logger;

public class SearchLeboxStep extends BaseStep {
    private static final String TAG = SearchLeboxStep.class.getSimpleName();
    HeartbeatObserver mHeartbeatObserver;

    public void startStep(StepsQueue stepsQueue) {
        this.timeOut = LetvConstant.WIDGET_UPDATE_UI_TIME;
        this.mStepsQueue = stepsQueue;
        if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable()) {
            Logger.d(TAG, "---------is lebox already connected-------------------");
            LeboxConnectManager.getInstance().notifyProgress(21);
            onResult(stepsQueue, true, true);
            return;
        }
        LeBoxNetworkManager.needScanNum++;
        LeBoxNetworkManager.getInstance().discoverP2pPeers(null);
        if (LeBoxNetworkManager.getInstance().isHasLeboxAp() || LeBoxNetworkManager.getInstance().isHasLeboxDirectSsid()) {
            LeboxConnectManager.getInstance().notifyProgress(17);
            Logger.d(TAG, "---------is lebox already found-------------------");
            onResult(stepsQueue, true, true);
            return;
        }
        LeboxConnectManager.getInstance().notifyProgress(15);
        this.mHeartbeatObserver = new 1(this, stepsQueue);
        HeartbeatManager.getInstance().regustHeartbeatObserver(this.mHeartbeatObserver);
        stepsQueue.getHandler().sendEmptyMessageDelayed(0, (long) this.timeOut);
    }

    protected void loopCheck(Message msg) {
        if (this.mStepsQueue != null) {
            this.mStepsQueue.getHandler().removeMessages(0);
        }
        if (this.mHeartbeatObserver != null) {
            HeartbeatManager.getInstance().unRegustHeartbeatObserver(this.mHeartbeatObserver);
        }
        if (LeBoxNetworkManager.getInstance().isHasLeboxAp() || LeBoxNetworkManager.getInstance().isHasLeboxDirectSsid()) {
            Logger.d(TAG, "----on time out-----is  lebox found success-------------------");
            LeboxConnectManager.getInstance().notifyProgress(17);
            onResult(this.mStepsQueue, true, true);
            return;
        }
        Logger.d(TAG, "----on time out-----is  lebox found fail-------------------");
        LeboxConnectManager.getInstance().notifyProgress(16);
        onResult(this.mStepsQueue, false, false);
    }
}
