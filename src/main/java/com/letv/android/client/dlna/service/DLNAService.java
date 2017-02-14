package com.letv.android.client.dlna.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.letv.android.client.dlna.engine.SearchThread;
import com.letv.core.utils.LogInfo;
import org.cybergarage.upnp.ControlPoint;

public class DLNAService extends Service {
    public static final String SEARCH_KEY = "search";
    public static final String STOP = "stop";
    private ControlPoint mControlPoint;
    private SearchThread mSearchThread;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        unInit();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || intent.getStringExtra(SEARCH_KEY) == null || !TextUtils.equals(intent.getStringExtra(SEARCH_KEY), "stop")) {
            startThread();
        } else {
            stopThread();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void unInit() {
        stopThread();
    }

    private void startThread() {
        if (this.mSearchThread != null) {
            LogInfo.log("dlna", "thread is not null");
            this.mSearchThread.setSearcTimes(0);
        } else {
            LogInfo.log("dlna", "thread is null, create a new thread");
            ControlPoint controlPoint = new ControlPoint();
            this.mControlPoint = controlPoint;
            this.mSearchThread = new SearchThread(controlPoint);
        }
        if (this.mSearchThread.isAlive()) {
            LogInfo.log("dlna", "thread is alive");
            this.mSearchThread.awake();
            return;
        }
        LogInfo.log("dlna", "start the thread");
        this.mSearchThread.start();
    }

    private void stopThread() {
        if (this.mSearchThread != null) {
            this.mSearchThread.stopThread();
            this.mSearchThread = null;
        }
        if (this.mControlPoint != null) {
            this.mControlPoint.stop();
            this.mControlPoint = null;
        }
        LogInfo.log("dlna", "stop dlna service");
    }
}
