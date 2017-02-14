package com.tencent.tmsecurelite.optimize;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.DataEntity;
import org.json.JSONException;

public abstract class RubbishScanListenerStub extends Binder implements IRubbishScanListener {
    private static final String DESCRIPTOR = "com.tencent.tmsecurelite.IRubbishScanListener";

    public RubbishScanListenerStub() {
        attachInterface(this, DESCRIPTOR);
    }

    public IBinder asBinder() {
        return this;
    }

    public static IRubbishScanListener asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
        if (iInterface == null || !(iInterface instanceof IRubbishScanListener)) {
            return new RubbishScanListenerProxy(binder);
        }
        return (IRubbishScanListener) iInterface;
    }

    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case 1:
                onScanStarted();
                reply.writeNoException();
                break;
            case 2:
                int type = data.readInt();
                DataEntity result = null;
                try {
                    result = new DataEntity(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onRubbishFound(type, result);
                reply.writeNoException();
                break;
            case 3:
                onScanProgressChanged(data.readInt());
                reply.writeNoException();
                break;
            case 4:
                onScanCanceled();
                reply.writeNoException();
                break;
            case 10:
                onScanFinished();
                reply.writeNoException();
                break;
        }
        return true;
    }
}
