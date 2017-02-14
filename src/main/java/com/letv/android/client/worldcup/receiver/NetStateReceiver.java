package com.letv.android.client.worldcup.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.android.client.worldcup.download.WorldCupDownloadManager;
import com.letv.android.client.worldcup.util.NetWorkTypeUtils;

public class NetStateReceiver extends BroadcastReceiver {
    public static final int NOTIFY_ID = 1;
    Context context;

    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (NetWorkTypeUtils.getAvailableNetWorkInfo(context) != null) {
            if (!(!NetWorkTypeUtils.isWifi(context))) {
                WorldCupDownloadManager.getInstance(context).refresh();
                return;
            } else if (WorldCupDownloadManager.getInstance(context).getErrorDownloadNum() > 0) {
                WorldCupDownloadManager.getInstance(context).pauseAll();
                return;
            } else {
                return;
            }
        }
        WorldCupDownloadManager.getInstance(context).pauseAll();
    }
}
