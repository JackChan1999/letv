package com.letv.itv.threenscreen.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface AIDLActivity extends IInterface {

    public static abstract class Stub extends Binder implements AIDLActivity {
        private static final String DESCRIPTOR = "com.letv.itv.threenscreen.service.AIDLActivity";
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onResDevices = 4;
        static final int TRANSACTION_onResInitDevice = 3;
        static final int TRANSACTION_onResLoadBalance = 2;
        static final int TRANSACTION_onResPush = 5;
        static final int TRANSACTION_onResult = 1;
        static final int TRANSACTION_quit = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static AIDLActivity asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof AIDLActivity)) {
                return new Proxy(obj);
            }
            return (AIDLActivity) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    onResult(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onResLoadBalance(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onResInitDevice(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    onResDevices(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    onResPush(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    onError(data.readString());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    quit();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onError(String str) throws RemoteException;

    void onResDevices(int i, String str) throws RemoteException;

    void onResInitDevice(int i, String str) throws RemoteException;

    void onResLoadBalance(int i, String str) throws RemoteException;

    void onResPush(int i, String str) throws RemoteException;

    void onResult(int i, String str) throws RemoteException;

    void quit() throws RemoteException;
}
