package com.letv.plugin.pluginloader.apk.pm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.letv.plugin.pluginloader.apk.hook.handle.IActivityManagerHookHandle.getIntentSender;

public class PluginManagerService extends Service {
    private static final String TAG = PluginManagerService.class.getSimpleName();
    private IApkManagerImpl mPluginPackageManager;

    public void onCreate() {
        super.onCreate();
        this.mPluginPackageManager = new IApkManagerImpl(this);
        this.mPluginPackageManager.onCreate();
    }

    public void onDestroy() {
        try {
            this.mPluginPackageManager.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return this.mPluginPackageManager;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        getIntentSender.handlePendingIntent(this, intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
