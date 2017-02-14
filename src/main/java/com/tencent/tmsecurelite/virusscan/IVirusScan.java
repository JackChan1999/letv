package com.tencent.tmsecurelite.virusscan;

import android.os.IInterface;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.ITmsCallback;
import java.util.List;

public interface IVirusScan extends IInterface {
    public static final String INTERFACE = "com.tencent.tmsecurelite.IVirusScan";
    public static final int T_cancelScan = 8;
    public static final int T_cancelScanAsync = 18;
    public static final int T_checkVersion = 10;
    public static final int T_checkVersionAsync = 20;
    public static final int T_continueScan = 7;
    public static final int T_continueScanAsync = 17;
    public static final int T_freeScanner = 9;
    public static final int T_freeScannerAsync = 19;
    public static final int T_pauseScan = 6;
    public static final int T_pauseScanAsync = 16;
    public static final int T_scanApks = 5;
    public static final int T_scanApksAsync = 15;
    public static final int T_scanGlobal = 3;
    public static final int T_scanGlobalAsync = 13;
    public static final int T_scanInstalledPackages = 1;
    public static final int T_scanInstalledPackagesAsync = 11;
    public static final int T_scanPackages = 4;
    public static final int T_scanPackagesAsync = 14;
    public static final int T_scanSdcardApks = 2;
    public static final int T_scanSdcardApksAsync = 12;
    public static final int T_updateTmsConfigAsync = 21;
    public static final int VERSION = 2;

    @Deprecated
    void cancelScan() throws RemoteException;

    int cancelScanAsync() throws RemoteException;

    boolean checkVersion(int i) throws RemoteException;

    @Deprecated
    void continueScan() throws RemoteException;

    int continueScanAsync() throws RemoteException;

    @Deprecated
    void freeScanner() throws RemoteException;

    int freeScannerAsync() throws RemoteException;

    @Deprecated
    void pauseScan() throws RemoteException;

    int pauseScanAsync() throws RemoteException;

    @Deprecated
    void scanApks(List<String> list, IScanListener iScanListener, boolean z) throws RemoteException;

    int scanApksAsync(List<String> list, IScanListener iScanListener, boolean z) throws RemoteException;

    @Deprecated
    void scanGlobal(IScanListener iScanListener, boolean z) throws RemoteException;

    int scanGlobalAsync(IScanListener iScanListener, boolean z) throws RemoteException;

    @Deprecated
    void scanInstalledPackages(IScanListener iScanListener, boolean z) throws RemoteException;

    int scanInstalledPackagesAsync(IScanListener iScanListener, boolean z) throws RemoteException;

    @Deprecated
    void scanPackages(List<String> list, IScanListener iScanListener, boolean z) throws RemoteException;

    int scanPackagesAsync(List<String> list, IScanListener iScanListener, boolean z) throws RemoteException;

    @Deprecated
    void scanSdcardApks(IScanListener iScanListener, boolean z) throws RemoteException;

    int scanSdcardApksAsync(IScanListener iScanListener, boolean z) throws RemoteException;

    void updateTmsConfigAsync(ITmsCallback iTmsCallback) throws RemoteException;
}
