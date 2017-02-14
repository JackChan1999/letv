package com.tencent.tmsecurelite.commom;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.ArrayList;

public interface ITmsCallback extends IInterface {
    public static final String DESCRIPTOR = "com.tencent.tmsecurelite.ITmsCallback";
    public static final int T_onArrayResultGot = 2;
    public static final int T_onResultGot = 1;

    void onArrayResultGot(int i, ArrayList<DataEntity> arrayList) throws RemoteException;

    void onResultGot(int i, DataEntity dataEntity) throws RemoteException;
}
