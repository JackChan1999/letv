package com.letv.pp.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICdeBinder extends IInterface {

    public static abstract class Stub extends Binder implements ICdeBinder {
        private static final String DESCRIPTOR = "com.letv.pp.service.ICdeBinder";
        static final int TRANSACTION_getCacheUrlWithData = 4;
        static final int TRANSACTION_getCdePort = 3;
        static final int TRANSACTION_getCdeVersion = 2;
        static final int TRANSACTION_getStateDownloadedDuration = 8;
        static final int TRANSACTION_getStateDownloadedPercent = 9;
        static final int TRANSACTION_getStateLastReceiveSpeed = 5;
        static final int TRANSACTION_getStateTotalDuration = 7;
        static final int TRANSACTION_getStateUrgentReceiveSpeed = 6;
        static final int TRANSACTION_getUpgradePercentage = 11;
        static final int TRANSACTION_isCdeReady = 1;
        static final int TRANSACTION_notifyNetworkChanged = 13;
        static final int TRANSACTION_setChannelSeekPosition = 10;
        static final int TRANSACTION_startUpgrade = 12;

        private static class Proxy implements ICdeBinder {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public boolean isCdeReady() throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdeVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCdePort() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCacheUrlWithData(String data, String ext, String g3Url, String other) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(data);
                    _data.writeString(ext);
                    _data.writeString(g3Url);
                    _data.writeString(other);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getStateLastReceiveSpeed(String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getStateUrgentReceiveSpeed(String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getStateTotalDuration(String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getStateDownloadedDuration(String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public double getStateDownloadedPercent(String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    double _result = _reply.readDouble();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setChannelSeekPosition(String url, double pos) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(url);
                    _data.writeDouble(pos);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUpgradePercentage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startUpgrade() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyNetworkChanged(int networkType, String networkName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(networkType);
                    _data.writeString(networkName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICdeBinder asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICdeBinder)) {
                return new Proxy(obj);
            }
            return (ICdeBinder) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String _result;
            long _result2;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result3 = isCdeReady();
                    reply.writeNoException();
                    reply.writeInt(_result3 ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getCdeVersion();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getCdePort();
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getCacheUrlWithData(data.readString(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getStateLastReceiveSpeed(data.readString());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getStateUrgentReceiveSpeed(data.readString());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getStateTotalDuration(data.readString());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getStateDownloadedDuration(data.readString());
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    double _result4 = getStateDownloadedPercent(data.readString());
                    reply.writeNoException();
                    reply.writeDouble(_result4);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    setChannelSeekPosition(data.readString(), data.readDouble());
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _result5 = getUpgradePercentage();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    startUpgrade();
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    notifyNetworkChanged(data.readInt(), data.readString());
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

    String getCacheUrlWithData(String str, String str2, String str3, String str4) throws RemoteException;

    long getCdePort() throws RemoteException;

    String getCdeVersion() throws RemoteException;

    long getStateDownloadedDuration(String str) throws RemoteException;

    double getStateDownloadedPercent(String str) throws RemoteException;

    long getStateLastReceiveSpeed(String str) throws RemoteException;

    long getStateTotalDuration(String str) throws RemoteException;

    long getStateUrgentReceiveSpeed(String str) throws RemoteException;

    int getUpgradePercentage() throws RemoteException;

    boolean isCdeReady() throws RemoteException;

    void notifyNetworkChanged(int i, String str) throws RemoteException;

    void setChannelSeekPosition(String str, double d) throws RemoteException;

    void startUpgrade() throws RemoteException;
}
