package com.letv.android.client.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.ui.download.MyDownloadActivity;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class DownloadCompeleReceiver extends BroadcastReceiver {
    public DownloadCompeleReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("android.client.receiver.DownloadCompeleReceiver")) {
            Intent down_it;
            if (MainActivity.getInstance() != null) {
                LogInfo.log("onReceive", "MainActivityGroup.getInstance() != null");
                down_it = new Intent(context, MyDownloadActivity.class);
                down_it.putExtra(MyDownloadActivityConfig.PAGE, 0);
            } else {
                LogInfo.log("onReceive", "MainActivityGroup.getInstance() == null");
                down_it = new Intent(context, MainActivity.class);
                down_it.setAction("DownloadCompeleReceiver");
            }
            down_it.setFlags(268435456);
            context.startActivity(down_it);
        }
    }
}
