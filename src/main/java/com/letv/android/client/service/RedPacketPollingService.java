package com.letv.android.client.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.redpacketsdk.RedPacketSdk;
import com.letv.redpacketsdk.bean.PollingResult;
import com.letv.redpacketsdk.callback.RedPacketPollingCallback;

public class RedPacketPollingService extends Service {
    private static final String TAG = RedPacketPollingService.class.getSimpleName();
    private WakeLock mWakeLock;

    public RedPacketPollingService() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onCreate() {
        super.onCreate();
        LogInfo.log("RedPacket", "RedPacketPollingService");
        this.mWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(1, TAG);
        this.mWakeLock.acquire();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        RedPacketSdk.getInstance().getRedPacketInfo(new RedPacketPollingCallback(this) {
            final /* synthetic */ RedPacketPollingService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceivePollingResult(PollingResult result) {
                this.this$0.detailMessage(result);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    private void detailMessage(PollingResult result) {
        LogInfo.log("RedPacket", "RedPacket polling res = " + result.toString());
        if (result != null) {
            switch (result.rollSwitch) {
                case -1:
                    startPollingService(this, 10);
                    break;
                case 0:
                    stopPollingService(this);
                    break;
                case 1:
                    if (result.rollRate <= 0) {
                        startPollingService(this, 10);
                        break;
                    } else {
                        startPollingService(this, result.rollRate);
                        break;
                    }
                default:
                    break;
            }
        }
        startPollingService(this, 10);
        stopSelf();
    }

    public static void startPollingService(Context context, int minute) {
        if (context != null) {
            PendingIntent pending = PendingIntent.getService(context, 1521045111, new Intent(context, RedPacketPollingService.class), 268435456);
            AlarmManager alarm = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            alarm.cancel(pending);
            alarm.set(2, SystemClock.elapsedRealtime() + ((long) ((minute * 60) * 1000)), pending);
        }
    }

    public static void stopPollingService(Context context) {
        if (context != null) {
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(PendingIntent.getService(context, 1521045111, new Intent(context, RedPacketPollingService.class), 268435456));
            context.stopService(new Intent(context, RedPacketPollingService.class));
        }
    }

    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }
}
