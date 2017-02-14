package com.tencent.tmsecurelite.filesafe;

import android.os.IInterface;
import android.os.RemoteException;

public interface IEncryptListener extends IInterface {
    public static final int T_onEncryptFinished = 6;
    public static final int T_onEncryptFinished2 = 7;
    public static final int T_onEncryptProgress = 3;
    public static final int T_onEncryptProgressError = 5;
    public static final int T_onEncryptProgressOk = 4;
    public static final int T_onEncryptStartedError = 2;
    public static final int T_onEncryptStartedOk = 1;

    @Deprecated
    void onEncryptFinished(int i, int i2, int i3) throws RemoteException;

    void onEncryptFinished(int i, int i2, int i3, int i4) throws RemoteException;

    void onEncryptProgress(int i) throws RemoteException;

    void onEncryptProgressError(int i, int i2, String str) throws RemoteException;

    void onEncryptProgressOk(int i, String str) throws RemoteException;

    void onEncryptStartedError(int i) throws RemoteException;

    void onEncryptStartedOk(int i) throws RemoteException;
}
