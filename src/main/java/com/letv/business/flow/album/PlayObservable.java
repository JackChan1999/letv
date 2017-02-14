package com.letv.business.flow.album;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.letv.android.client.commonlib.config.AlbumCommentDetailActivityConfig;
import com.letv.core.utils.LetvBaseObservable;
import com.letv.core.utils.LetvUtils;

public class PlayObservable extends LetvBaseObservable {
    public static final String INTENT_DOWNLOAD = "com.letv.android.client.download";
    public static final String ON_ALARM_ALERT = (TAG + 14);
    public static final String ON_BATTERY_CHANGE = (TAG + 3);
    public static final String ON_CALL_STATE_IDLE = (TAG + 13);
    public static final String ON_CALL_STATE_OFFHOOK = (TAG + 12);
    public static final String ON_CALL_STATE_RINGING = (TAG + 11);
    public static final String ON_DOWNLOAD_CHANGE = (TAG + 4);
    public static final String ON_HEADSET_PLUG = (TAG + 5);
    public static final String ON_NET_CHANGE = (TAG + 2);
    public static final String ON_SCREEN_OFF = (TAG + 6);
    public static final String ON_SCREEN_ON = (TAG + 8);
    public static final String ON_TIME_CHANGE = (TAG + 1);
    public static final String ON_USER_PRESENT = (TAG + 7);
    private static final String TAG = PlayObservable.class.getSimpleName();
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                if (TextUtils.equals("android.intent.action.TIME_TICK", action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_TIME_CHANGE);
                } else if (TextUtils.equals("android.net.conn.CONNECTIVITY_CHANGE", action)) {
                    if (LetvUtils.reflectScreenState()) {
                        PlayObservable.this.notifyObservers(PlayObservable.ON_NET_CHANGE);
                    }
                } else if (TextUtils.equals("android.intent.action.BATTERY_CHANGED", action)) {
                    int batteryStatus = intent.getIntExtra("status", 1);
                    int level = intent.getExtras().getInt(AlbumCommentDetailActivityConfig.LEVEL, 0);
                    PlayObservable.this.notifyObservers(new BatteryStatusNotify(batteryStatus, (level * 100) / intent.getExtras().getInt("scale", 100)));
                } else if (TextUtils.equals(PlayObservable.INTENT_DOWNLOAD, action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_DOWNLOAD_CHANGE);
                } else if (TextUtils.equals("android.intent.action.HEADSET_PLUG", action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_HEADSET_PLUG);
                } else if (TextUtils.equals("android.intent.action.SCREEN_OFF", action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_SCREEN_OFF);
                } else if (TextUtils.equals("android.intent.action.SCREEN_ON", action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_SCREEN_ON);
                } else if (TextUtils.equals("android.intent.action.USER_PRESENT", action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_USER_PRESENT);
                } else if (TextUtils.equals("android.intent.action.PHONE_STATE", action)) {
                    switch (((TelephonyManager) context.getSystemService("phone")).getCallState()) {
                        case 0:
                            PlayObservable.this.notifyObservers(PlayObservable.ON_CALL_STATE_IDLE);
                            return;
                        case 1:
                            PlayObservable.this.notifyObservers(PlayObservable.ON_CALL_STATE_RINGING);
                            return;
                        case 2:
                            PlayObservable.this.notifyObservers(PlayObservable.ON_CALL_STATE_OFFHOOK);
                            return;
                        default:
                            return;
                    }
                } else if (TextUtils.equals("com.android.deskclock.ALARM_ALERT", action)) {
                    PlayObservable.this.notifyObservers(PlayObservable.ON_ALARM_ALERT);
                }
            }
        }
    };
    private Context mContext;

    public static class BatteryStatusNotify {
        public final int batteryStatus;
        public final int batterycurPower;

        public BatteryStatusNotify(int batteryStatus, int batterycurPower) {
            this.batteryStatus = batteryStatus;
            this.batterycurPower = batterycurPower;
        }
    }

    public PlayObservable(Context context) {
        this.mContext = context;
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.intent.action.TIME_TICK");
            filter.addAction("android.intent.action.BATTERY_CHANGED");
            filter.addAction(INTENT_DOWNLOAD);
            filter.addAction("android.intent.action.HEADSET_PLUG");
            filter.addAction("android.intent.action.PHONE_STATE");
            filter.addAction("com.android.deskclock.ALARM_ALERT");
            context.registerReceiver(this.mBroadcastReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent("com.ammd"));
    }

    public synchronized void deleteObservers() {
        super.deleteObservers();
        if (this.mContext != null) {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        }
    }
}
