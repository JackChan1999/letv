package com.tencent.tmsecurelite.commom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import org.json.JSONException;

public abstract class TmsCallbackStub extends Binder implements ITmsCallback {
    public TmsCallbackStub() {
        attachInterface(this, ITmsCallback.DESCRIPTOR);
    }

    public IBinder asBinder() {
        return null;
    }

    public static ITmsCallback asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        IInterface iInterface = binder.queryLocalInterface(ITmsCallback.DESCRIPTOR);
        if (iInterface == null || !(iInterface instanceof ITmsCallback)) {
            return new TmsCallbackProxy(binder);
        }
        return (ITmsCallback) iInterface;
    }

    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case 1:
                int err = data.readInt();
                DataEntity result = null;
                try {
                    result = new DataEntity(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onResultGot(err, result);
                reply.writeNoException();
                break;
            case 2:
                onArrayResultGot(data.readInt(), DataEntity.readFromParcel(data));
                reply.writeNoException();
                break;
        }
        return true;
    }
}
