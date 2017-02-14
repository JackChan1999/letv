package com.letv.android.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.android.client.push.LetvPushService;
import com.letv.android.client.utils.LetvLiveBookUtil;
import com.letv.core.db.PreferencesManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    public BootCompletedBroadcastReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            PreferencesManager.getInstance().setLesoNotification(true);
            LetvPushService.schedule(context);
            if (PreferencesManager.getInstance().isLiveRemind()) {
                LetvLiveBookUtil.createClock(context);
            }
        }
    }
}
