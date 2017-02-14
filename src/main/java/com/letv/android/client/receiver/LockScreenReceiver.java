package com.letv.android.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.push.LetvPushService;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LockScreenReceiver extends BroadcastReceiver {
    public LockScreenReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            LogInfo.log("zhuqiao", "LockScreenReceiver:onReceive, action = " + intent.getAction());
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction()) || "android.intent.action.SCREEN_ON".equals(intent.getAction()) || ("android.intent.action.USER_PRESENT".equals(intent.getAction()) && MainActivity.getInstance() == null)) {
                try {
                    LetvPushService.schedule(context);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
