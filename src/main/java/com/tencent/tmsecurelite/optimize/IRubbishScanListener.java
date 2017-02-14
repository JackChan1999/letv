package com.tencent.tmsecurelite.optimize;

import android.os.IInterface;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.DataEntity;

public interface IRubbishScanListener extends IInterface {
    public static final int T_onRubbishFound = 2;
    public static final int T_onScanCanceled = 4;
    public static final int T_onScanFinished = 10;
    public static final int T_onScanProgressChanged = 3;
    public static final int T_onScanStarted = 1;

    void onRubbishFound(int i, DataEntity dataEntity) throws RemoteException;

    void onScanCanceled() throws RemoteException;

    void onScanFinished() throws RemoteException;

    void onScanProgressChanged(int i) throws RemoteException;

    void onScanStarted() throws RemoteException;
}
