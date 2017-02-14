package com.letv.plugin.pluginloader.apk.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.letv.plugin.pluginloader.IApkManager;
import com.letv.plugin.pluginloader.IApkManager.Stub;
import com.letv.plugin.pluginloader.apk.compat.PackageManagerCompat;
import com.letv.plugin.pluginloader.common.Constant;
import com.letv.plugin.pluginloader.util.JLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ApkManager implements ServiceConnection {
    public static final String ACTION_PACKAGE_ADDED = "com.letv.plugin.pluginloader.PACKAGE_ADDED";
    public static final String ACTION_PACKAGE_REMOVED = "com.letv.plugin.pluginloader.PACKAGE_REMOVED";
    public static final String EXTRA_APP_PERSISTENT = "com.letv.plugin.pluginloader.EXTRA_APP_PERSISTENT";
    public static final String EXTRA_PACKAGENAME = "com.letv.plugin.pluginloader.EXTRA_EXTRA_PACKAGENAME";
    public static final String EXTRA_PID = "com.letv.plugin.pluginloader.EXTRA_PID";
    public static final int INSTALL_FAILED_NO_REQUESTEDPERMISSION = -100001;
    public static final String STUB_AUTHORITY_NAME = "com.letv.plugin.pluginloader_stub";
    public static final int STUB_NO_ACTIVITY_MAX_NUM = 4;
    private static final String TAG = ApkManager.class.getSimpleName();
    private static ApkManager mInstance = null;
    private IApkManager mApkManager;
    private Context mHostContext;
    private Object mWaitLock = new Object();
    private List<WeakReference<ServiceConnection>> sServiceConnection = Collections.synchronizedList(new ArrayList(1));

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.i("wuxinrong", "启动插件管理Service成功");
        this.mApkManager = Stub.asInterface(iBinder);
        new 1(this, componentName, iBinder).start();
    }

    public void onServiceDisconnected(ComponentName componentName) {
        JLog.log("wuxinrong", "断开插件服务");
        this.mApkManager = null;
        Iterator<WeakReference<ServiceConnection>> iterator = this.sServiceConnection.iterator();
        while (iterator.hasNext()) {
            ServiceConnection sc;
            WeakReference<ServiceConnection> wsc = (WeakReference) iterator.next();
            if (wsc != null) {
                sc = (ServiceConnection) wsc.get();
            } else {
                sc = null;
            }
            if (sc != null) {
                sc.onServiceDisconnected(componentName);
            } else {
                iterator.remove();
            }
        }
        connectToService();
    }

    public void waitForConnected() {
        if (!isConnected()) {
            try {
                synchronized (this.mWaitLock) {
                    this.mWaitLock.wait();
                }
            } catch (InterruptedException e) {
                JLog.log("wuxinrong", "线程等待插件服务连接失败 e=" + e.getMessage());
            }
            JLog.log("wuxinrong", "结束线程插件服务连接结束");
        }
    }

    private void connectToService() {
        if (this.mApkManager == null) {
            try {
                Intent intent = new Intent(this.mHostContext, PluginManagerService.class);
                intent.setPackage(this.mHostContext.getPackageName());
                this.mHostContext.startService(intent);
                this.mHostContext.bindService(intent, this, 1);
            } catch (Exception e) {
                JLog.log("wuxinrong", "启动插件服务失败 e=" + e.getMessage());
            }
        }
    }

    public void addServiceConnection(ServiceConnection sc) {
        this.sServiceConnection.add(new WeakReference(sc));
    }

    public void removeServiceConnection(ServiceConnection sc) {
        Iterator<WeakReference<ServiceConnection>> iterator = this.sServiceConnection.iterator();
        while (iterator.hasNext()) {
            if (((WeakReference) iterator.next()).get() == sc) {
                iterator.remove();
            }
        }
    }

    public void init(Context hostContext) {
        this.mHostContext = hostContext;
        connectToService();
    }

    public boolean isConnected() {
        return (this.mHostContext == null || this.mApkManager == null) ? false : true;
    }

    public static ApkManager getInstance() {
        if (mInstance == null) {
            mInstance = new ApkManager();
        }
        return mInstance;
    }

    public PackageInfo getPackageInfo(String packageName, int flags) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getPackageInfo(packageName, flags);
            }
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取插件包信息失败 e=" + e2.getMessage());
        }
        return null;
    }

    public boolean isPluginPackage(String packageName) throws RemoteException {
        boolean z = false;
        try {
            if (!(this.mHostContext == null || TextUtils.equals(this.mHostContext.getPackageName(), packageName) || this.mApkManager == null || packageName == null)) {
                z = this.mApkManager.isPluginPackage(packageName);
            }
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "判断是否是插件失败 e=" + e2.getMessage());
        }
        return z;
    }

    public boolean isPluginPackage(ComponentName className) throws RemoteException {
        if (className == null) {
            return false;
        }
        return isPluginPackage(className.getPackageName());
    }

    public ActivityInfo getActivityInfo(ComponentName className, int flags) throws NameNotFoundException, RemoteException {
        ActivityInfo activityInfo = null;
        if (className != null) {
            try {
                if (!(this.mApkManager == null || className == null)) {
                    activityInfo = this.mApkManager.getActivityInfo(className, flags);
                }
            } catch (RemoteException e) {
                JLog.log("wuxinrong", "获取activity信息 失败 e=" + e.getMessage());
            } catch (Exception e2) {
                JLog.log("wuxinrong", "获取activity信息 失败 e=" + e2.getMessage());
            }
        }
        return activityInfo;
    }

    public ActivityInfo getReceiverInfo(ComponentName className, int flags) throws NameNotFoundException, RemoteException {
        ActivityInfo activityInfo = null;
        if (className != null) {
            try {
                if (!(this.mApkManager == null || className == null)) {
                    activityInfo = this.mApkManager.getReceiverInfo(className, flags);
                }
            } catch (RemoteException e) {
                JLog.log("wuxinrong", "获取activity信息 失败 e=" + e.getMessage());
                throw e;
            } catch (Exception e2) {
                JLog.log("wuxinrong", "获取activity信息 失败 e=" + e2.getMessage());
            }
        }
        return activityInfo;
    }

    public ServiceInfo getServiceInfo(ComponentName className, int flags) throws NameNotFoundException, RemoteException {
        ServiceInfo serviceInfo = null;
        if (className != null) {
            try {
                if (!(this.mApkManager == null || className == null)) {
                    serviceInfo = this.mApkManager.getServiceInfo(className, flags);
                }
            } catch (RemoteException e) {
                JLog.log("wuxinrong", "获取svervice信息 失败 e=" + e.getMessage());
                throw e;
            } catch (Exception e2) {
                JLog.log("wuxinrong", "获取svervice信息 失败 e=" + e2.getMessage());
            }
        }
        return serviceInfo;
    }

    public ProviderInfo getProviderInfo(ComponentName className, int flags) throws NameNotFoundException, RemoteException {
        ProviderInfo providerInfo = null;
        if (className != null) {
            try {
                if (!(this.mApkManager == null || className == null)) {
                    providerInfo = this.mApkManager.getProviderInfo(className, flags);
                }
            } catch (RemoteException e) {
                JLog.log("wuxinrong", "获取Provider信息 失败 e=" + e.getMessage());
                throw e;
            } catch (Exception e2) {
                JLog.log("wuxinrong", "获取Provider信息 失败 e=" + e2.getMessage());
            }
        }
        return providerInfo;
    }

    public ResolveInfo resolveIntent(Intent intent, String resolvedType, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || intent == null)) {
                return this.mApkManager.resolveIntent(intent, resolvedType, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取Resolve信息 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取Resolve信息 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ResolveInfo resolveService(Intent intent, String resolvedType, Integer flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || intent == null)) {
                return this.mApkManager.resolveService(intent, resolvedType, flags.intValue());
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取Resolve信息 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取Resolve信息 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<ResolveInfo> queryIntentActivities(Intent intent, String resolvedType, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || intent == null)) {
                return this.mApkManager.queryIntentActivities(intent, resolvedType, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取Resolve信息列表 失败 e=" + e.getMessage());
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取Resolve信息列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<ResolveInfo> queryIntentReceivers(Intent intent, String resolvedType, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || intent == null)) {
                return this.mApkManager.queryIntentReceivers(intent, resolvedType, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取Receivers的ResolveInfo列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取Receivers的ResolveInfo列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<ResolveInfo> queryIntentServices(Intent intent, String resolvedType, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || intent == null)) {
                return this.mApkManager.queryIntentServices(intent, resolvedType, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取Receivers的ResolveInfo列表 失败 e=" + e.getMessage());
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取Receivers的ResolveInfo列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<ResolveInfo> queryIntentContentProviders(Intent intent, String resolvedType, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || intent == null)) {
                return this.mApkManager.queryIntentContentProviders(intent, resolvedType, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ContentProviders 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ContentProviders 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<PackageInfo> getInstalledPackages(int flags) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getInstalledPackages(flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取已安装插件包信息 失败 e=" + e.getMessage());
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取已安装插件包信息 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<ApplicationInfo> getInstalledApplications(int flags) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getInstalledApplications(flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取已安装插件ApplicationInfo列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取已安装插件ApplicationInfo列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public PermissionInfo getPermissionInfo(String name, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || name == null)) {
                return this.mApkManager.getPermissionInfo(name, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取权限信息 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取权限信息 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || group == null)) {
                return this.mApkManager.queryPermissionsByGroup(group, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取权限信息列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取权限信息列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || name == null)) {
                return this.mApkManager.getPermissionGroupInfo(name, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取PermissionGroupInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取PermissionGroupInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getAllPermissionGroups(flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取PermissionGroupInfo列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取PermissionGroupInfo列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ProviderInfo resolveContentProvider(String name, Integer flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || name == null)) {
                return this.mApkManager.resolveContentProvider(name, flags.intValue());
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ContentProvider 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ContentProvider 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public void deleteApplicationCacheFiles(String packageName, Object observer) throws RemoteException {
        try {
            if (this.mApkManager != null && packageName != null) {
                this.mApkManager.deleteApplicationCacheFiles(packageName, new 2(this, observer));
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "删除ApplicationCacheFiles 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "删除ApplicationCacheFiles 失败 e=" + e2.getMessage());
        }
    }

    public void clearApplicationUserData(String packageName, Object observer) throws RemoteException {
        try {
            if (this.mApkManager != null && packageName != null) {
                this.mApkManager.clearApplicationUserData(packageName, new 3(this, observer));
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "清除ApplicationUserData 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "清除ApplicationUserData 失败 e=" + e2.getMessage());
        }
    }

    public ApplicationInfo getApplicationInfo(String packageName, int flags) throws RemoteException {
        try {
            if (!(this.mApkManager == null || packageName == null)) {
                return this.mApkManager.getApplicationInfo(packageName, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ApplicationInfo 失败 e=" + e.getMessage());
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ApplicationInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ActivityInfo selectStubActivityInfo(ActivityInfo pluginInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.selectStubActivityInfo(pluginInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ApplicationInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ApplicationInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ActivityInfo selectStubActivityInfo(Intent pluginInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.selectStubActivityInfoByIntent(pluginInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ActivityInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ActivityInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ServiceInfo selectStubServiceInfo(ServiceInfo pluginInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.selectStubServiceInfo(pluginInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ServiceInfo selectStubServiceInfo(Intent pluginInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.selectStubServiceInfoByIntent(pluginInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ProviderInfo selectStubProviderInfo(String name) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.selectStubProviderInfo(name);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ProviderInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ProviderInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ActivityInfo resolveActivityInfo(Intent intent, int flags) throws RemoteException {
        try {
            if (this.mApkManager == null) {
                return null;
            }
            if (intent.getComponent() != null) {
                return this.mApkManager.getActivityInfo(intent.getComponent(), flags);
            }
            ResolveInfo resolveInfo = this.mApkManager.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mHostContext.getContentResolver()), flags);
            if (resolveInfo == null || resolveInfo.activityInfo == null) {
                return null;
            }
            return resolveInfo.activityInfo;
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ActivityInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ActivityInfo 失败 e=" + e2.getMessage());
            return null;
        }
    }

    public ServiceInfo resolveServiceInfo(Intent intent, int flags) throws RemoteException {
        try {
            if (this.mApkManager == null) {
                return null;
            }
            if (intent.getComponent() != null) {
                return this.mApkManager.getServiceInfo(intent.getComponent(), flags);
            }
            ResolveInfo resolveInfo = this.mApkManager.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mHostContext.getContentResolver()), flags);
            if (resolveInfo == null || resolveInfo.serviceInfo == null) {
                return null;
            }
            return resolveInfo.serviceInfo;
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e2.getMessage());
            return null;
        }
    }

    public void killBackgroundProcesses(String packageName) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.killBackgroundProcesses(packageName);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "杀死后台进程 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "杀死后台进程 失败 e=" + e2.getMessage());
        }
    }

    public void forceStopPackage(String packageName) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.forceStopPackage(packageName);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "强制暂停插件 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "强制暂停插件 失败 e=" + e2.getMessage());
        }
    }

    public boolean killApplicationProcess(String packageName) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.killApplicationProcess(packageName);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "杀死插件进程 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "杀死插件进程 失败 e=" + e2.getMessage());
        }
        return false;
    }

    public List<ActivityInfo> getReceivers(String packageName, int flags) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getReceivers(packageName, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ActivityInfo列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ActivityInfo列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public List<IntentFilter> getReceiverIntentFilter(ActivityInfo info) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getReceiverIntentFilter(info);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取IntentFilter列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取IntentFilter列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public ServiceInfo getTargetServiceInfo(ServiceInfo info) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getTargetServiceInfo(info);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取ServiceInfo 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public void setPluginInstallState(String pluginName, int result) {
        if (this.mHostContext != null) {
            JLog.log("wuxinrong", "保存插件 " + pluginName + " 的安装状态 ：" + result);
            this.mHostContext.getSharedPreferences(Constant.SHARED_SP, 0).edit().putInt(pluginName, result).commit();
        }
    }

    public int getPluginInstallState(String pluginName) {
        if (this.mHostContext == null) {
            return PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR;
        }
        return this.mHostContext.getSharedPreferences(Constant.SHARED_SP, 0).getInt(pluginName, PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR);
    }

    public void setLiteAppCallState(boolean is3rdPart) {
        if (this.mHostContext != null) {
            JLog.log("wuxinrong", "保存LiteApp调起状态（是否第三方） " + is3rdPart);
            this.mHostContext.getSharedPreferences(Constant.SHARED_SP, 0).edit().putBoolean("LiteAppCallState", is3rdPart).commit();
        }
    }

    public boolean getLiteAppCallState() {
        if (this.mHostContext == null) {
            return false;
        }
        return this.mHostContext.getSharedPreferences(Constant.SHARED_SP, 0).getBoolean("LiteAppCallState", false);
    }

    public int installPackage(String filePath, int flags) {
        int result = -1;
        if (this.mApkManager == null) {
            return -1;
        }
        try {
            Log.i("wuxinrong", "安装插件..." + filePath);
            result = this.mApkManager.installPackage(filePath, flags);
            Log.i("wuxinrong", "安装插件结果 = " + result);
            return result;
        } catch (RemoteException e) {
            Log.i("wuxinrong", "安装插件失败 RemoteException = " + e.toString());
            return result;
        } catch (Exception e2) {
            Log.i("wuxinrong", "安装插件失败 Exception = " + e2.getMessage());
            return result;
        }
    }

    public List<String> getPackageNameByPid(int pid) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getPackageNameByPid(pid);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "通过pid获取包名列表 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "通过pid获取包名列表 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public String getProcessNameByPid(int pid) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getProcessNameByPid(pid);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "通过pid获取进程名 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "通过pid获取进程名 失败 e=" + e2.getMessage());
        }
        return null;
    }

    public void onActivityCreated(ActivityInfo stubInfo, ActivityInfo targetInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.onActivityCreated(stubInfo, targetInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "activity启动回调ActivityManagerService.onActivityCreated 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "activity启动回调ActivityManagerService.onActivityCreated 失败 e=" + e2.getMessage());
        }
    }

    public void onActivityDestory(ActivityInfo stubInfo, ActivityInfo targetInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.onActivityDestory(stubInfo, targetInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "activity销毁回调activity启动回调ActivityManagerService 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "activity销毁回调activity启动回调ActivityManagerService 失败 e=" + e2.getMessage());
        }
    }

    public void onServiceCreated(ServiceInfo stubInfo, ServiceInfo targetInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.onServiceCreated(stubInfo, targetInfo);
            } else {
                Log.w(TAG, "Plugin Package Manager Service not be connect");
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "Service启动回调ActivityManagerService.onServiceCreated 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "Service启动回调ActivityManagerService.onServiceCreated 失败 e=" + e2.getMessage());
        }
    }

    public void onServiceDestory(ServiceInfo stubInfo, ServiceInfo targetInfo) {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.onServiceDestory(stubInfo, targetInfo);
            }
        } catch (Exception e) {
            JLog.log("wuxinrong", "Service销毁回调ActivityManagerService.onServiceDestory 失败 e=" + e.getMessage());
        }
    }

    public void onProviderCreated(ProviderInfo stubInfo, ProviderInfo targetInfo) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.onProviderCreated(stubInfo, targetInfo);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "Provider启动回调ActivityManagerService.onProviderCreated 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "Provider启动回调ActivityManagerService.onProviderCreated 失败 e=" + e2.getMessage());
        }
    }

    public void onActivtyOnNewIntent(ActivityInfo stubInfo, ActivityInfo targetInfo, Intent intent) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.onActivtyOnNewIntent(stubInfo, targetInfo, intent);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "回调ActivityManagerService.onActivtyOnNewIntent 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "回调ActivityManagerService.onActivtyOnNewIntent 失败 e=" + e2.getMessage());
        }
    }

    public void reportMyProcessName(String stubProcessName, String targetProcessName, String targetPkg) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.reportMyProcessName(stubProcessName, targetProcessName, targetPkg);
            }
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e2) {
        }
    }

    public void deletePackage(String packageName, int flags) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                this.mApkManager.deletePackage(packageName, flags);
            }
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "卸载插件失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "卸载插件失败 e=" + e2.getMessage());
        }
    }

    public int checkSignatures(String pkg0, String pkg1) throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.checkSignatures(pkg0, pkg1);
            }
            JLog.log("wuxinrong", "检查签名：插件服务未连接");
            return -3;
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "检查签名失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "检查签名失败 e=" + e2.getMessage());
            return -3;
        }
    }

    public int getMyPid() throws RemoteException {
        try {
            if (this.mApkManager != null) {
                return this.mApkManager.getMyPid();
            }
            JLog.log("wuxinrong", "获取当前进程pid：插件服务未连接");
            return -1;
        } catch (RemoteException e) {
            JLog.log("wuxinrong", "获取当前进程pid 失败 e=" + e.getMessage());
            throw e;
        } catch (Exception e2) {
            JLog.log("wuxinrong", "获取当前进程pid 失败 e=" + e2.getMessage());
            return -1;
        }
    }
}
