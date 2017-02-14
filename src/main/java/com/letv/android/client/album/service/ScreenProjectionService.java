package com.letv.android.client.album.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.letv.itv.threenscreen.service.AIDLActivity;
import com.letv.itv.threenscreen.service.AIDLService.Stub;
import com.letv.itv.threenscreen.utils.ThreenScreenManager;

public class ScreenProjectionService extends Service {
    private AIDLActivity callback;
    private final Stub mBinder = new Stub() {
        public void registerCallback(AIDLActivity cb) throws RemoteException {
            ScreenProjectionService.this.callback = cb;
        }

        public void cancel() throws RemoteException {
            ThreenScreenManager.cancel();
        }

        public void initDevice(Bundle arg0) throws RemoteException {
        }

        public void queryDiviceList(Bundle arg0, int updateId) throws RemoteException {
            ThreenScreenManager.queryDeviceList(updateId, arg0, ScreenProjectionService.this.callback);
        }

        public void sendPush(Bundle arg0, String arg1) throws RemoteException {
            ThreenScreenManager.sendPush(0, arg0, arg1, ScreenProjectionService.this.callback);
        }
    };

    public void onCreate() {
    }

    public void onStart(Intent intent, int startId) {
    }

    public IBinder onBind(Intent t) {
        return this.mBinder;
    }

    public void onDestroy() {
        ThreenScreenManager.cancel();
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}
