package com.tencent.tmsecurelite.optimize;

import android.os.IInterface;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.DataEntity;
import com.tencent.tmsecurelite.commom.ITmsCallback;
import java.util.ArrayList;
import java.util.List;

public interface ISystemOptimize extends IInterface {
    public static final String INTERFACE = "com.tencent.tmsecurelite.ISecureService";
    public static final int T_askForRoot = 11;
    public static final int T_askForRootAsync = 26;
    public static final int T_cancelScanRubbish = 33;
    public static final int T_checkVersion = 9;
    public static final int T_checkVersionAsync = 24;
    public static final int T_cleanRubbishAsync = 34;
    public static final int T_cleanSysRubbish = 15;
    public static final int T_cleanSysRubbishAsync = 30;
    public static final int T_clearAppsCache = 2;
    public static final int T_clearAppsCacheAsync = 17;
    public static final int T_findAppsWithAutoboot = 3;
    public static final int T_findAppsWithAutobootAsync = 18;
    public static final int T_findAppsWithCache = 1;
    public static final int T_findAppsWithCacheAsync = 16;
    public static final int T_getAllRuningTask = 6;
    public static final int T_getAllRuningTaskAsync = 21;
    public static final int T_getMemoryUsage = 12;
    public static final int T_getMemoryUsageAsync = 27;
    public static final int T_getSysRubbish = 14;
    public static final int T_getSysRubbishAsync = 29;
    public static final int T_hasRoot = 10;
    public static final int T_hasRootAsync = 25;
    public static final int T_isMemoryReachWarning = 13;
    public static final int T_isMemoryReachWarningAsync = 28;
    public static final int T_killTask = 7;
    public static final int T_killTaskAsync = 22;
    public static final int T_killTasks = 8;
    public static final int T_killTasksAsync = 23;
    public static final int T_setAutobootState = 4;
    public static final int T_setAutobootStateAsync = 19;
    public static final int T_setAutobootStates = 5;
    public static final int T_setAutobootStatesAsync = 20;
    public static final int T_startScanRubbish = 32;
    public static final int T_updateTmsConfigAsync = 31;
    public static final int VERSION = 2;

    @Deprecated
    boolean askForRoot() throws RemoteException;

    void askForRootAsync(ITmsCallback iTmsCallback) throws RemoteException;

    int cancelScanRubbish() throws RemoteException;

    @Deprecated
    boolean checkVersion(int i) throws RemoteException;

    void cleanRubbishAsync(ITmsCallback iTmsCallback, ArrayList<String> arrayList) throws RemoteException;

    @Deprecated
    void cleanSysRubbish() throws RemoteException;

    void cleanSysRubbishAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean clearAppsCache() throws RemoteException;

    void clearAppsCacheAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<DataEntity> findAppsWithAutoboot(boolean z) throws RemoteException;

    void findAppsWithAutobootAsync(boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<DataEntity> findAppsWithCache() throws RemoteException;

    void findAppsWithCacheAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<DataEntity> getAllRuningTask(boolean z) throws RemoteException;

    void getAllRuningTaskAsync(boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getMemoryUsage() throws RemoteException;

    void getMemoryUsageAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void getSysRubbishSize(IMemoryListener iMemoryListener) throws RemoteException;

    int getSysRubbishSizeAsync(IMemoryListener iMemoryListener) throws RemoteException;

    @Deprecated
    boolean hasRoot() throws RemoteException;

    void hasRootAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean isMemoryReachWarning() throws RemoteException;

    void isMemoryReachWarningAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean killTask(String str) throws RemoteException;

    void killTaskAsync(String str, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean killTasks(List<String> list) throws RemoteException;

    void killTasksAsync(List<String> list, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean setAutobootState(String str, boolean z) throws RemoteException;

    void setAutobootStateAsync(String str, boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean setAutobootStates(List<String> list, boolean z) throws RemoteException;

    void setAutobootStatesAsync(List<String> list, boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    int startScanRubbish(IRubbishScanListener iRubbishScanListener) throws RemoteException;

    void updateTmsConfigAsync(ITmsCallback iTmsCallback) throws RemoteException;
}
