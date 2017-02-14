package com.tencent.tmsecurelite.filesafe;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class EncryptListenerProxy implements IEncryptListener {
    private IBinder mRemote;

    public EncryptListenerProxy(IBinder binder) {
        this.mRemote = binder;
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    public void onEncryptStartedOk(int totalCount) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(totalCount);
            this.mRemote.transact(1, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onEncryptStartedError(int errorCode) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(errorCode);
            this.mRemote.transact(2, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onEncryptProgress(int progress) throws RemoteException {
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

    public void onEncryptProgressOk(int progress, String filePath) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(progress);
            data.writeString(filePath);
            this.mRemote.transact(4, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onEncryptProgressError(int progress, int errorCode, String filePath) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(progress);
            data.writeInt(errorCode);
            data.writeString(filePath);
            this.mRemote.transact(5, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Deprecated
    public void onEncryptFinished(int failCount, int successCount, int totalCount) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(failCount);
            data.writeInt(successCount);
            data.writeInt(totalCount);
            this.mRemote.transact(6, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void onEncryptFinished(int failCount, int successCount, int totalCount, int finishStatus) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInt(failCount);
            data.writeInt(successCount);
            data.writeInt(totalCount);
            data.writeInt(finishStatus);
            this.mRemote.transact(7, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }
}
