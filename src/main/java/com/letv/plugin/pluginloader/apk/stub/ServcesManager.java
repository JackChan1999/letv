package com.letv.plugin.pluginloader.apk.stub;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.IBinder;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import com.letv.plugin.pluginloader.apk.compat.ActivityThreadCompat;
import com.letv.plugin.pluginloader.apk.compat.CompatibilityInfoCompat;
import com.letv.plugin.pluginloader.apk.compat.QueuedWorkCompat;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.pm.PluginProcessManager;
import com.letv.plugin.pluginloader.apk.utils.FieldUtils;
import com.letv.plugin.pluginloader.apk.utils.MethodUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ServcesManager {
    private static ServcesManager sServcesManager;
    private Map<String, Service> mNameService = new HashMap();
    private Map<Object, Integer> mServiceTaskIds = new HashMap();
    private Map<Object, Service> mTokenServices = new HashMap();

    private ServcesManager() {
    }

    public static ServcesManager getDefault() {
        synchronized (ServcesManager.class) {
            if (sServcesManager == null) {
                sServcesManager = new ServcesManager();
            }
        }
        return sServcesManager;
    }

    public boolean hasServiceRunning() {
        return this.mTokenServices.size() > 0 && this.mNameService.size() > 0;
    }

    private Object findTokenByService(Service service) {
        for (Object s : this.mTokenServices.keySet()) {
            if (this.mTokenServices.get(s) == service) {
                return s;
            }
        }
        return null;
    }

    private ClassLoader getClassLoader(ApplicationInfo pluginApplicationInfo) throws Exception {
        Object object = ActivityThreadCompat.currentActivityThread();
        if (object == null) {
            return null;
        }
        Object obj;
        if (VERSION.SDK_INT >= 11) {
            obj = MethodUtils.invokeMethod(object, "getPackageInfoNoCheck", pluginApplicationInfo, CompatibilityInfoCompat.DEFAULT_COMPATIBILITY_INFO());
        } else {
            obj = MethodUtils.invokeMethod(object, "getPackageInfoNoCheck", pluginApplicationInfo);
        }
        return (ClassLoader) MethodUtils.invokeMethod(obj, "getClassLoader", new Object[0]);
    }

    private void handleCreateServiceOne(Context hostContext, Intent stubIntent, ServiceInfo info) throws Exception {
        ResolveInfo resolveInfo = hostContext.getPackageManager().resolveService(stubIntent, 0);
        ServiceInfo stubInfo = resolveInfo != null ? resolveInfo.serviceInfo : null;
        ApkManager.getInstance().reportMyProcessName(stubInfo.processName, info.processName, info.packageName);
        PluginProcessManager.preLoadApk(hostContext, info);
        Object activityThread = ActivityThreadCompat.currentActivityThread();
        Object fakeToken = new MyFakeIBinder();
        Constructor init = Class.forName(ActivityThreadCompat.activityThreadClass().getName() + "$CreateServiceData").getDeclaredConstructor(new Class[0]);
        if (!init.isAccessible()) {
            init.setAccessible(true);
        }
        Object data = init.newInstance(new Object[0]);
        FieldUtils.writeField(data, UserInfoDb.TOKEN, fakeToken);
        FieldUtils.writeField(data, "info", (Object) info);
        if (VERSION.SDK_INT >= 11) {
            FieldUtils.writeField(data, "compatInfo", CompatibilityInfoCompat.DEFAULT_COMPATIBILITY_INFO());
        }
        Method method = activityThread.getClass().getDeclaredMethod("handleCreateService", new Class[]{CreateServiceData});
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        method.invoke(activityThread, new Object[]{data});
        Object mService = FieldUtils.readField(activityThread, "mServices");
        Service service = (Service) MethodUtils.invokeMethod(mService, "get", fakeToken);
        MethodUtils.invokeMethod(mService, "remove", fakeToken);
        this.mTokenServices.put(fakeToken, service);
        this.mNameService.put(info.name, service);
        if (stubInfo != null) {
            ApkManager.getInstance().onServiceCreated(stubInfo, info);
        }
    }

    private void handleOnStartOne(Intent intent, int flags, int startIds) throws Exception {
        ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(intent, 0);
        if (info != null) {
            Service service = (Service) this.mNameService.get(info.name);
            if (service != null) {
                intent.setExtrasClassLoader(getClassLoader(info.applicationInfo));
                Object token = findTokenByService(service);
                Integer integer = (Integer) this.mServiceTaskIds.get(token);
                if (integer == null) {
                    integer = Integer.valueOf(-1);
                }
                int startId = integer.intValue() + 1;
                this.mServiceTaskIds.put(token, Integer.valueOf(startId));
                int res = service.onStartCommand(intent, flags, startId);
                QueuedWorkCompat.waitToFinish();
            }
        }
    }

    private void handleOnTaskRemovedOne(Intent intent) throws Exception {
        if (VERSION.SDK_INT >= 14) {
            ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(intent, 0);
            if (info != null) {
                Service service = (Service) this.mNameService.get(info.name);
                if (service != null) {
                    intent.setExtrasClassLoader(getClassLoader(info.applicationInfo));
                    service.onTaskRemoved(intent);
                    QueuedWorkCompat.waitToFinish();
                }
                QueuedWorkCompat.waitToFinish();
            }
        }
    }

    private void handleOnDestroyOne(ServiceInfo targetInfo) {
        Service service = (Service) this.mNameService.get(targetInfo.name);
        if (service != null) {
            service.onDestroy();
            this.mNameService.remove(targetInfo.name);
            Object token = findTokenByService(service);
            this.mTokenServices.remove(token);
            this.mServiceTaskIds.remove(token);
            QueuedWorkCompat.waitToFinish();
            ApkManager.getInstance().onServiceDestory(null, targetInfo);
        }
        QueuedWorkCompat.waitToFinish();
    }

    private IBinder handleOnBindOne(Intent intent) throws Exception {
        ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(intent, 0);
        if (info != null) {
            Service service = (Service) this.mNameService.get(info.name);
            if (service != null) {
                intent.setExtrasClassLoader(getClassLoader(info.applicationInfo));
                return service.onBind(intent);
            }
        }
        return null;
    }

    private void handleOnRebindOne(Intent intent) throws Exception {
        ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(intent, 0);
        if (info != null) {
            Service service = (Service) this.mNameService.get(info.name);
            if (service != null) {
                intent.setExtrasClassLoader(getClassLoader(info.applicationInfo));
                service.onRebind(intent);
            }
        }
    }

    private boolean handleOnUnbindOne(Intent intent) throws Exception {
        ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(intent, 0);
        if (info == null) {
            return false;
        }
        Service service = (Service) this.mNameService.get(info.name);
        if (service == null) {
            return false;
        }
        intent.setExtrasClassLoader(getClassLoader(info.applicationInfo));
        return service.onUnbind(intent);
    }

    public int onStart(Context context, Intent intent, int flags, int startId) throws Exception {
        Intent targetIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
        if (targetIntent != null) {
            ServiceInfo targetInfo = ApkManager.getInstance().resolveServiceInfo(targetIntent, 0);
            if (targetInfo != null) {
                if (((Service) this.mNameService.get(targetInfo.name)) == null) {
                    handleCreateServiceOne(context, intent, targetInfo);
                }
                handleOnStartOne(targetIntent, flags, startId);
            }
        }
        return -1;
    }

    public void onTaskRemoved(Context context, Intent intent) throws Exception {
        Intent targetIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
        if (targetIntent != null) {
            ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(targetIntent, 0);
            if (((Service) this.mNameService.get(info.name)) == null) {
                handleCreateServiceOne(context, intent, info);
            }
            handleOnTaskRemovedOne(targetIntent);
        }
    }

    public IBinder onBind(Context context, Intent intent) throws Exception {
        Intent targetIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
        if (targetIntent == null) {
            return null;
        }
        ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(targetIntent, 0);
        if (((Service) this.mNameService.get(info.name)) == null) {
            handleCreateServiceOne(context, intent, info);
        }
        return handleOnBindOne(targetIntent);
    }

    public void onRebind(Context context, Intent intent) throws Exception {
        Intent targetIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
        if (targetIntent != null) {
            ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(targetIntent, 0);
            if (((Service) this.mNameService.get(info.name)) == null) {
                handleCreateServiceOne(context, intent, info);
            }
            handleOnRebindOne(targetIntent);
        }
    }

    public boolean onUnbind(Intent intent) throws Exception {
        Intent targetIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
        if (targetIntent == null) {
            return false;
        }
        if (((Service) this.mNameService.get(ApkManager.getInstance().resolveServiceInfo(targetIntent, 0).name)) != null) {
            return handleOnUnbindOne(targetIntent);
        }
        return false;
    }

    public int stopService(Context context, Intent intent) throws Exception {
        ServiceInfo targetInfo = ApkManager.getInstance().resolveServiceInfo(intent, 0);
        if (targetInfo == null) {
            return 0;
        }
        handleOnUnbindOne(intent);
        handleOnDestroyOne(targetInfo);
        return 1;
    }

    public boolean stopServiceToken(ComponentName cn, IBinder token, int startId) throws Exception {
        if (((Service) this.mTokenServices.get(token)) == null) {
            return false;
        }
        Integer lastId = (Integer) this.mServiceTaskIds.get(token);
        if (lastId == null || startId != lastId.intValue()) {
            return false;
        }
        Intent intent = new Intent();
        intent.setComponent(cn);
        ServiceInfo info = ApkManager.getInstance().resolveServiceInfo(intent, 0);
        if (info == null) {
            return false;
        }
        handleOnUnbindOne(intent);
        handleOnDestroyOne(info);
        return true;
    }

    public void onDestroy() {
        for (Service service : this.mTokenServices.values()) {
            service.onDestroy();
        }
        this.mTokenServices.clear();
        this.mServiceTaskIds.clear();
        this.mNameService.clear();
        QueuedWorkCompat.waitToFinish();
    }
}
