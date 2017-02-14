package com.letv.android.client.commonlib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.android.client.activity.MainActivity;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;

public class HomeKeyEventReceiver extends BroadcastReceiver {
    private boolean mIsHomeClicked = false;

    public void onReceive(Context context, Intent intent) {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";
        if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
            String reason = intent.getStringExtra("reason");
            if (TextUtils.equals(reason, "homekey")) {
                LogInfo.LogStatistics("home click:" + System.currentTimeMillis());
                this.mIsHomeClicked = true;
                StatisticsUtils.mIsHomeClicked = true;
                StatisticsUtils.statisticsLoginAndEnv(context, 1, true);
                StatisticsUtils.sLoginRef = MainActivity.THIRD_PARTY_LETV;
            } else if (TextUtils.equals(reason, "recentapps")) {
                LogInfo.LogStatistics("home long click");
            }
        }
    }

    public boolean isHomeClicked() {
        return this.mIsHomeClicked;
    }

    public void setIsHomeClicked(boolean IsHomeClicked) {
        this.mIsHomeClicked = IsHomeClicked;
    }
}
