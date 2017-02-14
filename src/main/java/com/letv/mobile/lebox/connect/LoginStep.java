package com.letv.mobile.lebox.connect;

import android.os.Message;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Logger;

public class LoginStep extends BaseStep {
    private static final String TAG = LoginStep.class.getSimpleName();

    public void startStep(StepsQueue stepsQueue) {
        if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable() && HttpCacheAssistant.getInstanced().isLogin()) {
            LeboxConnectManager.getInstance().notifyProgress(26);
            Logger.d(TAG, "---------is  lebox already register -------------------");
            onResult(stepsQueue, true, true);
            return;
        }
        LeboxConnectManager.getInstance().notifyProgress(23);
        HttpRequesetManager.getInstance().getUserPermission(new 1(this, stepsQueue));
    }

    protected void loopCheck(Message msg) {
    }
}
