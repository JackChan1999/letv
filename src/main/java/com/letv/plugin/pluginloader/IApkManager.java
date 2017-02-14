package com.letv.plugin.pluginloader;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IApkManager extends IInterface {

    public static abstract class Stub extends Binder implements IApkManager {
        private static final String DESCRIPTOR = "com.letv.plugin.pluginloader.IApkManager";
        static final int TRANSACTION_checkSignatures = 28;
        static final int TRANSACTION_clearApplicationUserData = 22;
        static final int TRANSACTION_deleteApplicationCacheFiles = 21;
        static final int TRANSACTION_deletePackage = 25;
        static final int TRANSACTION_forceStopPackage = 39;
        static final int TRANSACTION_getActivityInfo = 4;
        static final int TRANSACTION_getAllPermissionGroups = 19;
        static final int TRANSACTION_getApplicationInfo = 23;
        static final int TRANSACTION_getInstalledApplications = 15;
        static final int TRANSACTION_getInstalledPackages = 14;
        static final int TRANSACTION_getMyPid = 49;
        static final int TRANSACTION_getPackageInfo = 2;
        static final int TRANSACTION_getPackageNameByPid = 35;
        static final int TRANSACTION_getPermissionGroupInfo = 18;
        static final int TRANSACTION_getPermissionInfo = 16;
        static final int TRANSACTION_getProcessNameByPid = 36;
        static final int TRANSACTION_getProviderInfo = 7;
        static final int TRANSACTION_getReceiverInfo = 5;
        static final int TRANSACTION_getReceiverIntentFilter = 27;
        static final int TRANSACTION_getReceivers = 26;
        static final int TRANSACTION_getServiceInfo = 6;
        static final int TRANSACTION_getTargetServiceInfo = 33;
        static final int TRANSACTION_installPackage = 24;
        static final int TRANSACTION_isPluginPackage = 3;
        static final int TRANSACTION_killApplicationProcess = 38;
        static final int TRANSACTION_killBackgroundProcesses = 37;
        static final int TRANSACTION_onActivityCreated = 42;
        static final int TRANSACTION_onActivityDestory = 43;
        static final int TRANSACTION_onActivtyOnNewIntent = 48;
        static final int TRANSACTION_onProviderCreated = 46;
        static final int TRANSACTION_onServiceCreated = 44;
        static final int TRANSACTION_onServiceDestory = 45;
        static final int TRANSACTION_queryIntentActivities = 9;
        static final int TRANSACTION_queryIntentContentProviders = 13;
        static final int TRANSACTION_queryIntentReceivers = 10;
        static final int TRANSACTION_queryIntentServices = 12;
        static final int TRANSACTION_queryPermissionsByGroup = 17;
        static final int TRANSACTION_registerApplicationCallback = 40;
        static final int TRANSACTION_reportMyProcessName = 47;
        static final int TRANSACTION_resolveContentProvider = 20;
        static final int TRANSACTION_resolveIntent = 8;
        static final int TRANSACTION_resolveService = 11;
        static final int TRANSACTION_selectStubActivityInfo = 29;
        static final int TRANSACTION_selectStubActivityInfoByIntent = 30;
        static final int TRANSACTION_selectStubProviderInfo = 34;
        static final int TRANSACTION_selectStubServiceInfo = 31;
        static final int TRANSACTION_selectStubServiceInfoByIntent = 32;
        static final int TRANSACTION_unregisterApplicationCallback = 41;
        static final int TRANSACTION_waitForReady = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IApkManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IApkManager)) {
                return new Proxy(obj);
            }
            return (IApkManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _result;
            ComponentName _arg0;
            ActivityInfo _result2;
            ServiceInfo _result3;
            ProviderInfo _result4;
            Intent _arg02;
            ResolveInfo _result5;
            List<ResolveInfo> _result6;
            int _result7;
            ActivityInfo _arg03;
            ServiceInfo _arg04;
            ActivityInfo _arg1;
            ServiceInfo _arg12;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _result = waitForReady();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    PackageInfo _result8 = getPackageInfo(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isPluginPackage(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = (ComponentName) ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = getActivityInfo(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = (ComponentName) ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result2 = getReceiverInfo(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = (ComponentName) ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result3 = getServiceInfo(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = (ComponentName) ComponentName.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _result4 = getProviderInfo(_arg0, data.readInt());
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result5 = resolveIntent(_arg02, data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result6 = queryIntentActivities(_arg02, data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result6);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result6 = queryIntentReceivers(_arg02, data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result6);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result5 = resolveService(_arg02, data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result6 = queryIntentServices(_arg02, data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result6);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result6 = queryIntentContentProviders(_arg02, data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result6);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    List<PackageInfo> _result9 = getInstalledPackages(data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result9);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    List<ApplicationInfo> _result10 = getInstalledApplications(data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result10);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    PermissionInfo _result11 = getPermissionInfo(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result11 != null) {
                        reply.writeInt(1);
                        _result11.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    List<PermissionInfo> _result12 = queryPermissionsByGroup(data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result12);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    PermissionGroupInfo _result13 = getPermissionGroupInfo(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result13 != null) {
                        reply.writeInt(1);
                        _result13.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    List<PermissionGroupInfo> _result14 = getAllPermissionGroups(data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result14);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = resolveContentProvider(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    deleteApplicationCacheFiles(data.readString(), com.letv.plugin.pluginloader.IPackageDataObserver.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    clearApplicationUserData(data.readString(), com.letv.plugin.pluginloader.IPackageDataObserver.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    ApplicationInfo _result15 = getApplicationInfo(data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result15 != null) {
                        reply.writeInt(1);
                        _result15.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    _result7 = installPackage(data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    _result7 = deletePackage(data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    List<ActivityInfo> _result16 = getReceivers(data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result16);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    List<IntentFilter> _result17 = getReceiverIntentFilter(_arg03);
                    reply.writeNoException();
                    reply.writeTypedList(_result17);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    _result7 = checkSignatures(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    _result2 = selectStubActivityInfo(_arg03);
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result2 = selectStubActivityInfoByIntent(_arg02);
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = (ServiceInfo) ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    _result3 = selectStubServiceInfo(_arg04);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    _result3 = selectStubServiceInfoByIntent(_arg02);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = (ServiceInfo) ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    _result3 = getTargetServiceInfo(_arg04);
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = selectStubProviderInfo(data.readString());
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    List<String> _result18 = getPackageNameByPid(data.readInt());
                    reply.writeNoException();
                    reply.writeStringList(_result18);
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    String _result19 = getProcessNameByPid(data.readInt());
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    _result = killBackgroundProcesses(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    _result = killApplicationProcess(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    _result = forceStopPackage(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    _result = registerApplicationCallback(com.letv.plugin.pluginloader.IApplicationCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    _result = unregisterApplicationCallback(com.letv.plugin.pluginloader.IApplicationCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    onActivityCreated(_arg03, _arg1);
                    reply.writeNoException();
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    onActivityDestory(_arg03, _arg1);
                    reply.writeNoException();
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = (ServiceInfo) ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg12 = (ServiceInfo) ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    onServiceCreated(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = (ServiceInfo) ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg12 = (ServiceInfo) ServiceInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    onServiceDestory(_arg04, _arg12);
                    reply.writeNoException();
                    return true;
                case 46:
                    ProviderInfo _arg05;
                    ProviderInfo _arg13;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg05 = (ProviderInfo) ProviderInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg05 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg13 = (ProviderInfo) ProviderInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    onProviderCreated(_arg05, _arg13);
                    reply.writeNoException();
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    reportMyProcessName(data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onActivtyOnNewIntent /*48*/:
                    Intent _arg2;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg1 = (ActivityInfo) ActivityInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    if (data.readInt() != 0) {
                        _arg2 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    onActivtyOnNewIntent(_arg03, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getMyPid /*49*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result7 = getMyPid();
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int checkSignatures(String str, String str2) throws RemoteException;

    void clearApplicationUserData(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    void deleteApplicationCacheFiles(String str, IPackageDataObserver iPackageDataObserver) throws RemoteException;

    int deletePackage(String str, int i) throws RemoteException;

    boolean forceStopPackage(String str) throws RemoteException;

    ActivityInfo getActivityInfo(ComponentName componentName, int i) throws RemoteException;

    List<PermissionGroupInfo> getAllPermissionGroups(int i) throws RemoteException;

    ApplicationInfo getApplicationInfo(String str, int i) throws RemoteException;

    List<ApplicationInfo> getInstalledApplications(int i) throws RemoteException;

    List<PackageInfo> getInstalledPackages(int i) throws RemoteException;

    int getMyPid() throws RemoteException;

    PackageInfo getPackageInfo(String str, int i) throws RemoteException;

    List<String> getPackageNameByPid(int i) throws RemoteException;

    PermissionGroupInfo getPermissionGroupInfo(String str, int i) throws RemoteException;

    PermissionInfo getPermissionInfo(String str, int i) throws RemoteException;

    String getProcessNameByPid(int i) throws RemoteException;

    ProviderInfo getProviderInfo(ComponentName componentName, int i) throws RemoteException;

    ActivityInfo getReceiverInfo(ComponentName componentName, int i) throws RemoteException;

    List<IntentFilter> getReceiverIntentFilter(ActivityInfo activityInfo) throws RemoteException;

    List<ActivityInfo> getReceivers(String str, int i) throws RemoteException;

    ServiceInfo getServiceInfo(ComponentName componentName, int i) throws RemoteException;

    ServiceInfo getTargetServiceInfo(ServiceInfo serviceInfo) throws RemoteException;

    int installPackage(String str, int i) throws RemoteException;

    boolean isPluginPackage(String str) throws RemoteException;

    boolean killApplicationProcess(String str) throws RemoteException;

    boolean killBackgroundProcesses(String str) throws RemoteException;

    void onActivityCreated(ActivityInfo activityInfo, ActivityInfo activityInfo2) throws RemoteException;

    void onActivityDestory(ActivityInfo activityInfo, ActivityInfo activityInfo2) throws RemoteException;

    void onActivtyOnNewIntent(ActivityInfo activityInfo, ActivityInfo activityInfo2, Intent intent) throws RemoteException;

    void onProviderCreated(ProviderInfo providerInfo, ProviderInfo providerInfo2) throws RemoteException;

    void onServiceCreated(ServiceInfo serviceInfo, ServiceInfo serviceInfo2) throws RemoteException;

    void onServiceDestory(ServiceInfo serviceInfo, ServiceInfo serviceInfo2) throws RemoteException;

    List<ResolveInfo> queryIntentActivities(Intent intent, String str, int i) throws RemoteException;

    List<ResolveInfo> queryIntentContentProviders(Intent intent, String str, int i) throws RemoteException;

    List<ResolveInfo> queryIntentReceivers(Intent intent, String str, int i) throws RemoteException;

    List<ResolveInfo> queryIntentServices(Intent intent, String str, int i) throws RemoteException;

    List<PermissionInfo> queryPermissionsByGroup(String str, int i) throws RemoteException;

    boolean registerApplicationCallback(IApplicationCallback iApplicationCallback) throws RemoteException;

    void reportMyProcessName(String str, String str2, String str3) throws RemoteException;

    ProviderInfo resolveContentProvider(String str, int i) throws RemoteException;

    ResolveInfo resolveIntent(Intent intent, String str, int i) throws RemoteException;

    ResolveInfo resolveService(Intent intent, String str, int i) throws RemoteException;

    ActivityInfo selectStubActivityInfo(ActivityInfo activityInfo) throws RemoteException;

    ActivityInfo selectStubActivityInfoByIntent(Intent intent) throws RemoteException;

    ProviderInfo selectStubProviderInfo(String str) throws RemoteException;

    ServiceInfo selectStubServiceInfo(ServiceInfo serviceInfo) throws RemoteException;

    ServiceInfo selectStubServiceInfoByIntent(Intent intent) throws RemoteException;

    boolean unregisterApplicationCallback(IApplicationCallback iApplicationCallback) throws RemoteException;

    boolean waitForReady() throws RemoteException;
}
