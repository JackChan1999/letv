package com.letv.android.client.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.letv.android.client.push.PushNotificationReceiver;
import com.letv.android.client.utils.LetvLiveBookUtil;
import com.letv.core.bean.PushBookLive;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.PlayConstant;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class LetvLiveReceiver extends BroadcastReceiver {
    public LetvLiveReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(Context context, Intent in) {
        if (PreferencesManager.getInstance().isLiveRemind()) {
            ArrayList<PushBookLive> list = DBManager.getInstance().getLiveBookTrace().getCurrentTrace();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    PushBookLive mPushBookLive = (PushBookLive) list.get(i);
                    Intent intent = new Intent("android.settings.SETTINGS");
                    intent.setClass(context, PushNotificationReceiver.class);
                    LogInfo.log("+-->", "--->>>context" + (context == null));
                    intent.putExtra("type", 5);
                    intent.putExtra(Field.CHANNELNAME, mPushBookLive.channelName);
                    intent.putExtra("url", mPushBookLive.url);
                    intent.putExtra(PlayConstant.LIVE_URL_350, mPushBookLive.url_350);
                    intent.putExtra("code", mPushBookLive.code);
                    intent.putExtra("play_time", mPushBookLive.play_time);
                    intent.putExtra("id", mPushBookLive.id);
                    intent.putExtra(Field.PROGRAMNAME, mPushBookLive.programName);
                    LogInfo.log("+-->", "mPushBookLive.programName--->>>=" + mPushBookLive.programName);
                    intent.putExtra("live_mode", mPushBookLive.launchMode);
                    intent.addFlags(268435456);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, LetvUtils.codeToInt(mPushBookLive.code), intent, 134217728);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                    Notification notification = new Notification();
                    if (VERSION.SDK_INT < 21) {
                        notification.icon = 2130838675;
                    } else {
                        notification.icon = 2130838670;
                    }
                    notification.tickerText = context.getString(2131100242);
                    notification.flags = 16;
                    notification.defaults |= 1;
                    notification.setLatestEventInfo(context, context.getString(2131100242), (TextUtils.isEmpty(mPushBookLive.channelName) ? "" : mPushBookLive.channelName + "  ") + mPushBookLive.programName, pendingIntent);
                    int codeToInt = LetvUtils.codeToInt(mPushBookLive.code);
                    LogInfo.log("+-->", "codeToInt--->>" + codeToInt);
                    notificationManager.notify(codeToInt, notification);
                    DBManager.getInstance().getLiveBookTrace().update(mPushBookLive.programName, mPushBookLive.channelName, mPushBookLive.code, mPushBookLive.play_time, true);
                }
            }
            LetvLiveBookUtil.createClock(context);
        }
    }
}
