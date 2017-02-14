package com.letv.plugin.pluginloader.apk.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.letv.plugin.pluginloader.apk.compat.ActivityThreadCompat;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.pm.PluginProcessManager;
import com.letv.plugin.pluginloader.util.JLog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PluginHelper implements ServiceConnection {
    private static final String TAG = PluginHelper.class.getSimpleName();
    private static PluginHelper mInstance = null;
    private ServiceConnection mConnection;

    private PluginHelper() {
    }

    public static final PluginHelper getInstance() {
        if (mInstance == null) {
            mInstance = new PluginHelper();
        }
        return mInstance;
    }

    public void applicationOnCreate(Context baseContext) {
        initPlugin(baseContext);
    }

    public void initApk(Context baseContext, ServiceConnection connection) {
        this.mConnection = connection;
        initPlugin(baseContext);
    }

    private void initPlugin(Context baseContext) {
        long b = System.currentTimeMillis();
        try {
            fixMiUiLbeSecurity();
        } catch (Throwable th) {
            Log.i(TAG, "Init plugin in process cost %s ms", Long.valueOf(System.currentTimeMillis() - b));
        }
        try {
            PluginProcessManager.installHook(baseContext);
        } catch (Throwable e) {
            JLog.log("apk", "初始化插件Hook出错 e=" + e);
        }
        try {
            if (PluginProcessManager.isPluginProcess(baseContext)) {
                PluginProcessManager.setHookEnable(true);
            } else {
                PluginProcessManager.setHookEnable(false);
            }
        } catch (Throwable e2) {
            Log.e(TAG, "setHookEnable has error", e2, new Object[0]);
        }
        try {
            ApkManager.getInstance().addServiceConnection(this);
            ApkManager.getInstance().init(baseContext);
        } catch (Throwable e22) {
            Log.e(TAG, "installHook has error", e22, new Object[0]);
        }
        Log.i(TAG, "Init plugin in process cost %s ms", Long.valueOf(System.currentTimeMillis() - b));
    }

    private void fixMiUiLbeSecurity() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap oldValue;
        Object applicationLoaders = MethodUtils.invokeStaticMethod(Class.forName("android.app.ApplicationLoaders"), "getDefault", new Object[0]);
        HashMap mLoaders = FieldUtils.readField(applicationLoaders, "mLoaders", true);
        if (mLoaders instanceof HashMap) {
            oldValue = mLoaders;
            if ("com.lbe.security.client.ClientContainer$MonitoredLoaderMap".equals(mLoaders.getClass().getName())) {
                Object value = new HashMap();
                value.putAll(oldValue);
                FieldUtils.writeField(applicationLoaders, "mLoaders", value, true);
            }
        }
        Object currentActivityThread = ActivityThreadCompat.currentActivityThread();
        Object mPackages = FieldUtils.readField(currentActivityThread, "mPackages", true);
        if (mPackages instanceof HashMap) {
            oldValue = (HashMap) mPackages;
            if ("com.lbe.security.client.ClientContainer$MonitoredPackageMap".equals(mPackages.getClass().getName())) {
                value = new HashMap();
                value.putAll(oldValue);
                FieldUtils.writeField(currentActivityThread, "mPackages", value, true);
            }
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            try {
                Object mMessages = FieldUtils.readField(Looper.myQueue(), "mMessages", true);
                if (mMessages instanceof Message) {
                    findLbeMessageAndRemoveIt((Message) mMessages);
                }
                Log.e(TAG, "getMainLooper MessageQueue.IdleHandler:" + mMessages, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void findLbeMessageAndRemoveIt(Message message) {
        if (message != null) {
            Runnable callback = message.getCallback();
            if (message.what == 0 && callback != null && callback.getClass().getName().indexOf("com.lbe.security.client") >= 0) {
                message.getTarget().removeCallbacks(callback);
            }
            try {
                Object nextObj = FieldUtils.readField((Object) message, "next", true);
                if (nextObj != null) {
                    findLbeMessageAndRemoveIt((Message) nextObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        PluginProcessManager.setHookEnable(true, true);
        if (this.mConnection != null) {
            this.mConnection.onServiceConnected(componentName, iBinder);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    public void applicationAttachBaseContext(Context baseContext) {
        MyCrashHandler.getInstance().register(baseContext);
    }
}
