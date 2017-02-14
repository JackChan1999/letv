package com.letv.plugin.pluginloader.apk.hook.handle;

import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import com.letv.plugin.pluginloader.apk.hook.BaseHookHandle;
import com.letv.plugin.pluginloader.apk.hook.HookedMethodHandler;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.pm.PluginManagerService;
import com.letv.plugin.pluginloader.apk.utils.Log;
import java.lang.reflect.Method;

public class IActivityManagerHookHandle extends BaseHookHandle {
    private static final String TAG = IActivityManagerHookHandle.class.getSimpleName();

    public static class getIntentSender extends HookedMethodHandler {
        public getIntentSender(Context hostContext) {
            super(hostContext);
        }

        protected boolean beforeInvoke(Object receiver, Method method, Object[] args) throws Throwable {
            if (args != null && args.length > 1 && args[1] != null && (args[1] instanceof String)) {
                String callerPackage = args[1];
                String originPackageName = this.mHostContext.getPackageName();
                if (!TextUtils.equals(callerPackage, originPackageName)) {
                    args[1] = originPackageName;
                }
            }
            boolean hasRelacedIntent = false;
            if (!(args == null || args.length <= 5 || args[5] == null)) {
                int type = ((Integer) args[0]).intValue();
                Intent replaced;
                if (args[5] instanceof Intent) {
                    replaced = replace(type, args[5]);
                    if (replaced != null) {
                        args[5] = replaced;
                        hasRelacedIntent = true;
                    }
                } else if (args[5] instanceof Intent[]) {
                    Intent[] intents = (Intent[]) args[5];
                    if (intents != null && intents.length > 0) {
                        for (int i = 0; i < intents.length; i++) {
                            replaced = replace(type, intents[i]);
                            if (replaced != null) {
                                intents[i] = replaced;
                                hasRelacedIntent = true;
                            }
                        }
                        args[5] = intents;
                    }
                }
            }
            if (hasRelacedIntent && args != null && args.length > 7) {
                if (args[7] instanceof Integer) {
                    args[7] = Integer.valueOf(134217728);
                }
                args[0] = Integer.valueOf(4);
            }
            return super.beforeInvoke(receiver, method, args);
        }

        private Intent replace(int type, Intent intent) throws RemoteException {
            Intent newIntent;
            if (type == 4) {
                ServiceInfo a = IActivityManagerHookHandle.resolveService(intent);
                if (a != null && IActivityManagerHookHandle.isPackagePlugin(a.packageName)) {
                    newIntent = new Intent(this.mHostContext, PluginManagerService.class);
                    newIntent.putExtra(ApkConstant.EXTRA_TARGET_INTENT, intent);
                    newIntent.putExtra(ApkConstant.EXTRA_TYPE, type);
                    newIntent.putExtra(ApkConstant.EXTRA_ACTION, "PendingIntent");
                    return newIntent;
                }
            } else if (type == 2) {
                ActivityInfo a2 = IActivityManagerHookHandle.resolveActivity(intent);
                if (a2 != null && IActivityManagerHookHandle.isPackagePlugin(a2.packageName)) {
                    newIntent = new Intent(this.mHostContext, PluginManagerService.class);
                    newIntent.putExtra(ApkConstant.EXTRA_TARGET_INTENT, intent);
                    newIntent.putExtra(ApkConstant.EXTRA_TYPE, type);
                    newIntent.putExtra(ApkConstant.EXTRA_ACTION, "PendingIntent");
                    return newIntent;
                }
            }
            return null;
        }

        public static void handlePendingIntent(Context context, Intent intent) {
            if (intent != null) {
                try {
                    if ("PendingIntent".equals(intent.getStringExtra(ApkConstant.EXTRA_ACTION))) {
                        int type = intent.getIntExtra(ApkConstant.EXTRA_TYPE, -1);
                        Intent actionIntent = (Intent) intent.getParcelableExtra(ApkConstant.EXTRA_TARGET_INTENT);
                        Handler handle = new Handler(Looper.getMainLooper());
                        if (type == 4 && actionIntent != null) {
                            new 2("", handle, new 1(context, actionIntent), actionIntent).start();
                        } else if (type == 2 && actionIntent != null) {
                            actionIntent.addFlags(268435456);
                            new 4("", handle, new 3(context, actionIntent), actionIntent).start();
                        }
                    }
                } catch (Exception e) {
                    Log.e(IActivityManagerHookHandle.TAG, "Exception", e, new Object[0]);
                }
            }
        }
    }

    public IActivityManagerHookHandle(Context hostContext) {
        super(hostContext);
    }

    protected void init() {
        this.sHookedMethodHandlers.put("startActivity", new startActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityAsUser", new startActivityAsUser(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityAsCaller", new startActivityAsCaller(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityAndWait", new startActivityAndWait(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityWithConfig", new startActivityWithConfig(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityIntentSender", new startActivityIntentSender(this.mHostContext));
        this.sHookedMethodHandlers.put("startVoiceActivity", new startVoiceActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("startNextMatchingActivity", new startNextMatchingActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityFromRecents", new startActivityFromRecents(this.mHostContext));
        this.sHookedMethodHandlers.put("finishActivity", new finishActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("registerReceiver", new registerReceiver(this.mHostContext));
        this.sHookedMethodHandlers.put("broadcastIntent", new broadcastIntent(this.mHostContext));
        this.sHookedMethodHandlers.put("unbroadcastIntent", new unbroadcastIntent(this.mHostContext));
        this.sHookedMethodHandlers.put("getCallingPackage", new getCallingPackage(this.mHostContext));
        this.sHookedMethodHandlers.put("getCallingActivity", new getCallingActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("getAppTasks", new getAppTasks(this.mHostContext));
        this.sHookedMethodHandlers.put("addAppTask", new addAppTask(this.mHostContext));
        this.sHookedMethodHandlers.put("getTasks", new getTasks(this.mHostContext));
        this.sHookedMethodHandlers.put("getServices", new getServices(this.mHostContext));
        this.sHookedMethodHandlers.put("getProcessesInErrorState", new getProcessesInErrorState(this.mHostContext));
        this.sHookedMethodHandlers.put("getContentProvider", new getContentProvider(this.mHostContext));
        this.sHookedMethodHandlers.put("getContentProviderExternal", new getContentProviderExternal(this.mHostContext));
        this.sHookedMethodHandlers.put("removeContentProviderExternal", new removeContentProviderExternal(this.mHostContext));
        this.sHookedMethodHandlers.put("publishContentProviders", new publishContentProviders(this.mHostContext));
        this.sHookedMethodHandlers.put("getRunningServiceControlPanel", new getRunningServiceControlPanel(this.mHostContext));
        this.sHookedMethodHandlers.put("startService", new startService(this.mHostContext));
        this.sHookedMethodHandlers.put("stopService", new stopService(this.mHostContext));
        this.sHookedMethodHandlers.put("stopServiceToken", new stopServiceToken(this.mHostContext));
        this.sHookedMethodHandlers.put("setServiceForeground", new setServiceForeground(this.mHostContext));
        this.sHookedMethodHandlers.put("bindService", new bindService(this.mHostContext));
        this.sHookedMethodHandlers.put("publishService", new publishService(this.mHostContext));
        this.sHookedMethodHandlers.put("unbindFinished", new unbindFinished(this.mHostContext));
        this.sHookedMethodHandlers.put("peekService", new peekService(this.mHostContext));
        this.sHookedMethodHandlers.put("bindBackupAgent", new bindBackupAgent(this.mHostContext));
        this.sHookedMethodHandlers.put("backupAgentCreated", new backupAgentCreated(this.mHostContext));
        this.sHookedMethodHandlers.put("unbindBackupAgent", new unbindBackupAgent(this.mHostContext));
        this.sHookedMethodHandlers.put("killApplicationProcess", new killApplicationProcess(this.mHostContext));
        this.sHookedMethodHandlers.put("startInstrumentation", new startInstrumentation(this.mHostContext));
        this.sHookedMethodHandlers.put("getActivityClassForToken", new getActivityClassForToken(this.mHostContext));
        this.sHookedMethodHandlers.put("getPackageForToken", new getPackageForToken(this.mHostContext));
        this.sHookedMethodHandlers.put("getIntentSender", new getIntentSender(this.mHostContext));
        this.sHookedMethodHandlers.put("clearApplicationUserData", new clearApplicationUserData(this.mHostContext));
        this.sHookedMethodHandlers.put("handleIncomingUser", new handleIncomingUser(this.mHostContext));
        this.sHookedMethodHandlers.put("grantUriPermission", new grantUriPermission(this.mHostContext));
        this.sHookedMethodHandlers.put("getPersistedUriPermissions", new getPersistedUriPermissions(this.mHostContext));
        this.sHookedMethodHandlers.put("killBackgroundProcesses", new killBackgroundProcesses(this.mHostContext));
        this.sHookedMethodHandlers.put("forceStopPackage", new forceStopPackage(this.mHostContext));
        this.sHookedMethodHandlers.put("getRunningAppProcesses", new getRunningAppProcesses(this.mHostContext));
        this.sHookedMethodHandlers.put("getRunningExternalApplications", new getRunningExternalApplications(this.mHostContext));
        this.sHookedMethodHandlers.put("getMyMemoryState", new getMyMemoryState(this.mHostContext));
        this.sHookedMethodHandlers.put("crashApplication", new crashApplication(this.mHostContext));
        this.sHookedMethodHandlers.put("grantUriPermissionFromOwner", new grantUriPermissionFromOwner(this.mHostContext));
        this.sHookedMethodHandlers.put("checkGrantUriPermission", new checkGrantUriPermission(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivities", new startActivities(this.mHostContext));
        this.sHookedMethodHandlers.put("getPackageScreenCompatMode", new getPackageScreenCompatMode(this.mHostContext));
        this.sHookedMethodHandlers.put("setPackageScreenCompatMode", new setPackageScreenCompatMode(this.mHostContext));
        this.sHookedMethodHandlers.put("getPackageAskScreenCompat", new getPackageAskScreenCompat(this.mHostContext));
        this.sHookedMethodHandlers.put("setPackageAskScreenCompat", new setPackageAskScreenCompat(this.mHostContext));
        this.sHookedMethodHandlers.put("navigateUpTo", new navigateUpTo(this.mHostContext));
        this.sHookedMethodHandlers.put("serviceDoneExecuting", new serviceDoneExecuting(this, this.mHostContext));
    }

    private static ServiceInfo replaceFirstServiceIntentOfArgs(Object[] args) throws RemoteException {
        int intentOfArgIndex = findFirstIntentIndexInArgs(args);
        if (args != null && args.length > 1 && intentOfArgIndex >= 0) {
            Intent intent = args[intentOfArgIndex];
            ServiceInfo serviceInfo = resolveService(intent);
            if (serviceInfo != null && isPackagePlugin(serviceInfo.packageName)) {
                ServiceInfo proxyService = selectProxyService(intent);
                if (proxyService != null) {
                    Intent newIntent = new Intent();
                    newIntent.setClassName(proxyService.packageName, proxyService.name);
                    newIntent.putExtra(ApkConstant.EXTRA_TARGET_INTENT, intent);
                    args[intentOfArgIndex] = newIntent;
                    return serviceInfo;
                }
            }
        }
        return null;
    }

    private static int findFirstIntentIndexInArgs(Object[] args) {
        if (args != null && args.length > 0) {
            int i = 0;
            for (Object arg : args) {
                if (arg != null && (arg instanceof Intent)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private static ComponentName selectProxyActivity(Intent intent) {
        if (intent != null) {
            try {
                ActivityInfo proxyInfo = ApkManager.getInstance().selectStubActivityInfo(intent);
                if (proxyInfo != null) {
                    return new ComponentName(proxyInfo.packageName, proxyInfo.name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ServiceInfo selectProxyService(Intent intent) {
        if (intent != null) {
            try {
                ServiceInfo proxyInfo = ApkManager.getInstance().selectStubServiceInfo(intent);
                if (proxyInfo != null) {
                    return proxyInfo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ComponentName selectProxyService(ComponentName componentName) {
        if (componentName != null) {
            try {
                ApkManager instance = ApkManager.getInstance();
                ServiceInfo info = instance.getServiceInfo(componentName, 0);
                if (info != null) {
                    ServiceInfo proxyInfo = instance.selectStubServiceInfo(info);
                    if (proxyInfo != null) {
                        return new ComponentName(proxyInfo.packageName, proxyInfo.name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static ActivityInfo resolveActivity(Intent intent) throws RemoteException {
        return ApkManager.getInstance().resolveActivityInfo(intent, 0);
    }

    private static ServiceInfo resolveService(Intent intent) throws RemoteException {
        return ApkManager.getInstance().resolveServiceInfo(intent, 0);
    }

    private static boolean isPackagePlugin(String packageName) throws RemoteException {
        return ApkManager.getInstance().isPluginPackage(packageName);
    }

    private static boolean isComponentNamePlugin(ComponentName className) throws RemoteException {
        return ApkManager.getInstance().isPluginPackage(className);
    }

    private static ApplicationInfo queryPluginApplicationInfo(String packageName) throws RemoteException {
        return ApkManager.getInstance().getApplicationInfo(packageName, 0);
    }

    private static boolean clearPluginApplicationUserData(String packageName, Object observer) throws RemoteException {
        if (observer == null) {
            ApkManager.getInstance().clearApplicationUserData(packageName, null);
        } else {
            ApkManager.getInstance().clearApplicationUserData(packageName, observer);
        }
        return true;
    }

    private static void tryfixServiceInfo(RunningServiceInfo serviceInfo) {
    }
}
