package com.letv.component.upgrade.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.letv.component.utils.DebugLog;

public abstract class LetvIntentService extends Service {
    private static final String TAG = "LetvIntentService";
    private String mName;
    private volatile ServiceHandler mServiceHandler;
    private volatile Looper mServiceLooper;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            LetvIntentService.this.onHandleIntent((Intent) msg.obj);
        }
    }

    protected abstract void onHandleIntent(Intent intent);

    public LetvIntentService(String name) {
        this.mName = name;
    }

    public void onCreate() {
        super.onCreate();
        DebugLog.log(TAG, "=======================================Servcie onCreate!");
        HandlerThread thread = new HandlerThread("IntentService[" + this.mName + "]");
        thread.start();
        this.mServiceLooper = thread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        DebugLog.log(TAG, "=======================================Servcie onStart!");
        Message msg = this.mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        this.mServiceHandler.sendMessage(msg);
    }

    public void onDestroy() {
        this.mServiceLooper.quit();
        DebugLog.log(TAG, "=======================================Servcie OnDestory!!");
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
