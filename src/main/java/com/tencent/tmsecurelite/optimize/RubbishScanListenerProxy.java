package com.tencent.tmsecurelite.optimize;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.DataEntity;

public final class RubbishScanListenerProxy implements IRubbishScanListener {
    private IBinder mRemote;

    public RubbishScanListenerProxy(IBinder binder) {
        this.mRemote = binder;
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    public void onScanStarted() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            this.mRemote.transact(1, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onScanCanceled() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            this.mRemote.transact(4, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onScanProgressChanged(int progress) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(progress);
            this.mRemote.transact(3, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onRubbishFound(int type, DataEntity result) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(type);
            result.writeToParcel(data, 0);
            this.mRemote.transact(2, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onScanFinished() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            this.mRemote.transact(10, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }
}
