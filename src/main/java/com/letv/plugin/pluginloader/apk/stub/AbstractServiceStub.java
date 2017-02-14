package com.letv.plugin.pluginloader.apk.stub;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import com.letv.plugin.pluginloader.apk.utils.Log;

public abstract class AbstractServiceStub extends Service {
    private static final String TAG = "AbstractServiceStub";
    private static ServcesManager mCreator = ServcesManager.getDefault();
    private boolean isRunning = false;
    private Object sLock = new Object();

    public void onCreate() {
        super.onCreate();
        this.isRunning = true;
    }

    public void onDestroy() {
        try {
            mCreator.onDestroy();
        } catch (Exception e) {
            handleException(e);
        }
        super.onDestroy();
        this.isRunning = false;
        try {
            synchronized (this.sLock) {
                this.sLock.notifyAll();
            }
        } catch (Exception e2) {
        }
    }

    public static void startKillService(Context context, Intent service) {
        service.putExtra("ActionKillSelf", true);
        context.startService(service);
    }

    public void onStart(Intent intent, int startId) {
        if (intent != null) {
            try {
                if (intent.getBooleanExtra("ActionKillSelf", false)) {
                    startKillSelf();
                    if (ServcesManager.getDefault().hasServiceRunning()) {
                        Log.i(TAG, "doGc Kill Process(pid=%s,uid=%s has exit) for %s onStart intent=%s skip,has service running", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid()), getClass().getSimpleName(), intent);
                    } else {
                        stopSelf(startId);
                        boolean stopService = getApplication().stopService(intent);
                        Log.i(TAG, "doGc Kill Process(pid=%s,uid=%s has exit) for %s onStart=%s intent=%s", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid()), getClass().getSimpleName(), Boolean.valueOf(stopService), intent);
                    }
                } else {
                    mCreator.onStart(this, intent, 0, startId);
                }
            } catch (Exception e) {
                handleException(e);
            }
        }
        super.onStart(intent, startId);
    }

    private void startKillSelf() {
        if (this.isRunning) {
            try {
                new Thread() {
                    public void run() {
                        synchronized (AbstractServiceStub.this.sLock) {
                            try {
                                AbstractServiceStub.this.sLock.wait();
                            } catch (Exception e) {
                            }
                        }
                        Log.i(AbstractServiceStub.TAG, "doGc Kill Process(pid=%s,uid=%s has exit) for %s 2", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid()), getClass().getSimpleName());
                        Process.killProcess(Process.myPid());
                    }
                }.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleException(Exception e) {
        Log.e(TAG, "handleException", e, new Object[0]);
    }

    public void onTaskRemoved(Intent rootIntent) {
        if (rootIntent != null) {
            try {
                mCreator.onTaskRemoved(this, rootIntent);
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        if (intent != null) {
            try {
                return mCreator.onBind(this, intent);
            } catch (Exception e) {
                handleException(e);
            }
        }
        return null;
    }

    public void onRebind(Intent intent) {
        if (intent != null) {
            try {
                mCreator.onRebind(this, intent);
            } catch (Exception e) {
                handleException(e);
            }
        }
        super.onRebind(intent);
    }

    public boolean onUnbind(Intent intent) {
        if (intent != null) {
            try {
                return mCreator.onUnbind(intent);
            } catch (Exception e) {
                handleException(e);
            }
        }
        return false;
    }
}
