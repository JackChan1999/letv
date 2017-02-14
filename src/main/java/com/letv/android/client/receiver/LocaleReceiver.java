package com.letv.android.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LocaleReceiver extends BroadcastReceiver {
    public LocaleReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
            LogInfo.log("zhuqiao", "系统语言变化，退出应用");
            ActivityUtils.getInstance().removeAll();
        }
    }
}
