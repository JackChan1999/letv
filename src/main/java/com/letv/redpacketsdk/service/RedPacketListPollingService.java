package com.letv.redpacketsdk.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.parser.RedPacketParser;
import com.letv.redpacketsdk.utils.LogInfo;
import org.json.JSONArray;

public class RedPacketListPollingService extends Service {
    private static final String LIST_POLLING_ACTION = "red_packet_list_polling_action";
    private static final int REQUEST_CODE = 101;
    private static Handler updateHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!(msg == null || msg.obj == null)) {
                RedPacketSdkManager.getInstance().setRedPacketBean((RedPacketBean) msg.obj);
            }
            RedPacketListPollingService.setAlarm(RedPacketSdkManager.getInstance().getApplicationContext());
        }
    };

    public static void start(Context context) {
        if (context != null) {
            LogInfo.log("RedPacketListPollingService", JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START);
            context.startService(new Intent(context, RedPacketListPollingService.class));
        }
    }

    public static void stop(Context context) {
        if (context != null) {
            RedPacketSdkManager.getInstance().setRedPacketList(null);
            updateHandler.removeMessages(1);
            cancelAlarm(context);
            LogInfo.log("RedPacketListPollingService", "stop");
            context.stopService(new Intent(context, RedPacketListPollingService.class));
        }
    }

    private static void setAlarm(Context context) {
        if (context != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            long triggerAtTime = SystemClock.elapsedRealtime();
            PendingIntent pendingIntent = getPollingIntent(context);
            alarmManager.cancel(pendingIntent);
            alarmManager.set(3, 3000 + triggerAtTime, pendingIntent);
        }
    }

    private static void cancelAlarm(Context context) {
        if (context != null) {
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(getPollingIntent(context));
        }
    }

    private static PendingIntent getPollingIntent(Context context) {
        Intent intent = new Intent(context, RedPacketListPollingService.class);
        intent.setAction(LIST_POLLING_ACTION);
        return PendingIntent.getService(context, 101, intent, 134217728);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        LogInfo.log("RedPacketListPollingService", "onCreate");
    }

    public void onStart(Intent intent, int startId) {
        final JSONArray rpList = RedPacketSdkManager.getInstance().getRedPacketList();
        if (rpList == null) {
            stop(getApplicationContext());
        } else if (rpList == null || rpList.length() < 0) {
            stop(getApplicationContext());
        } else {
            try {
                new Thread(new Runnable() {
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = new RedPacketParser().parserRedPacketList(rpList);
                        RedPacketListPollingService.updateHandler.sendMessage(message);
                    }
                }).start();
            } catch (Exception e) {
            } catch (Error e2) {
            }
        }
    }
}
