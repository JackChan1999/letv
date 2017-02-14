package com.tencent.tmsecurelite.commom;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TmsCallbackProxy implements ITmsCallback {
    private IBinder mRemote;

    public TmsCallbackProxy(IBinder binder) {
        this.mRemote = binder;
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    public void onResultGot(int err, DataEntity result) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(err);
            result.writeToParcel(data, 0);
            this.mRemote.transact(1, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onArrayResultGot(int err, ArrayList<DataEntity> result) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(err);
            DataEntity.writeToParcel((List) result, data);
            this.mRemote.transact(2, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }
}
