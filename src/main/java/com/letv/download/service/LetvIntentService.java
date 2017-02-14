package com.letv.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.letv.download.util.L;

public abstract class LetvIntentService extends Service {
    private static final String TAG = LetvIntentService.class.getSimpleName();
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
        L.v(TAG, "Servcie onCreate!!");
        HandlerThread thread = new HandlerThread("IntentService[" + this.mName + "]");
        thread.start();
        this.mServiceLooper = thread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        L.v(TAG, "Servcie onStartCommand!!");
        Message msg = this.mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        this.mServiceHandler.sendMessage(msg);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        this.mServiceLooper.quit();
        L.v(TAG, "Servcie onDestroy!!");
    }
}
