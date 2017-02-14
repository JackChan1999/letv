package com.letv.android.client.worldcup;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.letv.android.client.worldcup.dao.PreferencesManager;
import com.letv.android.client.worldcup.util.Constants;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration;
import com.letv.android.client.worldcup.util.LetvUtil;
import com.letv.android.client.worldcup.util.NetWorkTypeUtils;
import com.umeng.analytics.a;

public class LetvAlarmService extends Service {
    private static final int ALARM_INTERVAL = 600;
    private static final String TAG = LetvAlarmService.class.getSimpleName();
    private static String startTime = "";
    WakeLock mWakeLock;

    public void onCreate() {
        super.onCreate();
        Constants.debug("LetvAlarmService_onCreate System time:" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup");
        this.mWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(1, TAG);
        this.mWakeLock.acquire();
        startTime = PreferencesManager.getInstance().getWorldCupStartTime(getApplicationContext());
        Constants.debug("pcode" + LetvServiceConfiguration.getPcode(getApplicationContext()), "worldcup");
        Constants.debug("version" + LetvServiceConfiguration.getVersion(getApplicationContext()), "worldcup");
        if (isTimeVaild(startTime) && NetWorkTypeUtils.isWifi(getApplicationContext())) {
            Intent i;
            if (PreferencesManager.getInstance().getWorldCupFunc(getApplicationContext())) {
                if (checkServiceIsRunning(getApplicationContext())) {
                    Constants.debug("LetvDownloadService is Running" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup action : action_request_server");
                    i = new Intent(getApplicationContext(), LetvDownloadService.class);
                    i.setAction(LetvDownloadService.ACTION_REQUEST_SERVER);
                    startService(i);
                } else {
                    Constants.debug("LetvDownloadService is not Running" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup");
                    startService(new Intent(getApplicationContext(), LetvDownloadService.class));
                }
            } else if (checkServiceIsRunning(getApplicationContext())) {
                Constants.debug("LetvDownloadService222 is Running" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup action action_stop_service");
                i = new Intent(getApplicationContext(), LetvDownloadService.class);
                i.setAction(LetvDownloadService.ACTION_STOP_SERVICE);
                startService(i);
            }
        }
        startPollingService(getApplicationContext());
        stopSelf();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Constants.debug("LetvAlarmService_onStartCommand return START_STICKY System time:" + LetvUtil.timeString(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss"), "worldcup");
        return 1;
    }

    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }

    public static void startPollingService(Context context) {
        PendingIntent pending = PendingIntent.getService(context, 1521045130, new Intent(context, LetvAlarmService.class), 268435456);
        AlarmManager alarm = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        alarm.cancel(pending);
        long now = System.currentTimeMillis();
        alarm.set(0, now + 600000, pending);
        Constants.debug("pre query push time is " + LetvUtil.timeStringAll(now) + " , interval = " + 600 + " , so after " + 10 + " minutes，at " + LetvUtil.timeStringAll(now + 600000) + " will start query service again");
    }

    public static void stopPollingService(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(PendingIntent.getService(context, 152104513, new Intent(context, LetvAlarmService.class), 268435456));
        Constants.debug("stopPollingService>><<");
        if (checkServiceIsRunning(context)) {
            LetvDownloadService.stopService(context);
        }
    }

    public static boolean checkServiceIsRunning(Context context) {
        for (RunningServiceInfo service : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if ("com.letv.android.client.worldcup.LetvDownloadService".equals(service.service.getClassName())) {
                Constants.debug("com.letv.android.client.worldcup.LetvDownloadService is Running");
                return true;
            }
        }
        return false;
    }

    private boolean isTimeVaild(String startTime) {
        if (TextUtils.isEmpty(startTime)) {
            return false;
        }
        String[] times = startTime.split("\\$");
        Constants.debug("startTime" + startTime);
        long now = System.currentTimeMillis();
        boolean isTimeVaild = false;
        if (times != null && times.length > 0) {
            for (String time : times) {
                long date = LetvUtil.getTimeInMillis(LetvUtil.timeString(now, "yyyy-MM-dd") + " " + time + ":00");
                Constants.debug("System.currentTimeMillis now : " + System.currentTimeMillis() + "; date before:" + time + "; date after:" + date, "worldcup");
                isTimeVaild = isTimeVaild || (now > date && now < a.h + date);
                Constants.debug("for isTimeVaild: " + isTimeVaild, "worldcup");
            }
        }
        Constants.debug("isTimeVaild: " + isTimeVaild, "worldcup");
        return isTimeVaild;
    }
}
