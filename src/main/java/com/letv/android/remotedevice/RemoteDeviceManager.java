package com.letv.android.remotedevice;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface RemoteDeviceManager extends IInterface {

    public static abstract class Stub extends Binder implements RemoteDeviceManager {
        private static final String DESCRIPTOR = "com.letv.android.remotedevice.RemoteDeviceManager";
        static final int TRANSACTION_captureScreen = 10;
        static final int TRANSACTION_deletePackage = 17;
        static final int TRANSACTION_getCurrentPlayState = 14;
        static final int TRANSACTION_getDeviceInfo = 3;
        static final int TRANSACTION_getDeviceList = 2;
        static final int TRANSACTION_getInstalledPackages = 16;
        static final int TRANSACTION_getLastUsedDevice = 4;
        static final int TRANSACTION_playMedia = 13;
        static final int TRANSACTION_queryLauncherActivities = 15;
        static final int TRANSACTION_registerDeviceCallback = 5;
        static final int TRANSACTION_scanDevices = 1;
        static final int TRANSACTION_sendBroadcast = 12;
        static final int TRANSACTION_sendControlAction = 7;
        static final int TRANSACTION_sendFileToDevice = 9;
        static final int TRANSACTION_sendInputText = 8;
        static final int TRANSACTION_startActivity = 11;
        static final int TRANSACTION_unregisterDeviceCallback = 6;

        private static class Proxy implements RemoteDeviceManager {
            private final IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void scanDevices(PendingIntent resultIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (resultIntent != null) {
                        _data.writeInt(1);
                        resultIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<DeviceInfo> getDeviceList(int deviceType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceType);
                    this.mRemote.transact(2, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    List<DeviceInfo> _result = _reply.createTypedArrayList(DeviceInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DeviceInfo getDeviceInfo(String deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    DeviceInfo _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    if (_reply.readInt() != 0) {
                        _result = (DeviceInfo) DeviceInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DeviceInfo getLastUsedDevice(int deviceType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    DeviceInfo _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(deviceType);
                    this.mRemote.transact(4, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    if (_reply.readInt() != 0) {
                        _result = (DeviceInfo) DeviceInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerDeviceCallback(String deviceId, DeviceCallback callback, int events) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(events);
                    this.mRemote.transact(5, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterDeviceCallback(String deviceId, DeviceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(6, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendControlAction(String deviceId, String action, Bundle extra) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeString(action);
                    if (extra != null) {
                        _data.writeInt(1);
                        extra.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(7, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendInputText(String deviceId, int editTextId, String text) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeInt(editTextId);
                    _data.writeString(text);
                    this.mRemote.transact(8, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendFileToDevice(String deviceId, String mimeType, Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeString(mimeType);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(9, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void captureScreen(String deviceId, PendingIntent resultIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    if (resultIntent != null) {
                        _data.writeInt(1);
                        resultIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(10, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startActivity(String deviceId, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(11, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendBroadcast(String deviceId, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(12, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int playMedia(String deviceId, Uri uri, int start) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                int _result = 1;
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(start);
                    this.mRemote.transact(13, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getCurrentPlayState(String deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    Bundle _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    if (_reply.readInt() != 0) {
                        _result = (Bundle) Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<Bundle> queryLauncherActivities(String deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    List<Bundle> _result = _reply.createTypedArrayList(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PackageInfo> getInstalledPackages(String deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                    List<PackageInfo> _result = _reply.createTypedArrayList(PackageInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deletePackage(String deviceId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deviceId);
                    _data.writeString(packageName);
                    this.mRemote.transact(17, _data, _reply, 0);
                    ExceptionUtils.readExceptionFromParcel(_reply);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static RemoteDeviceManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof RemoteDeviceManager)) {
                return new Proxy(obj);
            }
            return (RemoteDeviceManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            DeviceInfo _result;
            String _arg0;
            String _arg1;
            Intent _arg12;
            switch (code) {
                case 1:
                    PendingIntent _arg02;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (PendingIntent) PendingIntent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    scanDevices(_arg02);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    List<DeviceInfo> _result2 = getDeviceList(data.readInt());
                    reply.writeNoException();
                    reply.writeTypedList(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getDeviceInfo(data.readString());
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getLastUsedDevice(data.readInt());
                    reply.writeNoException();
                    if (_result != null) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    registerDeviceCallback(data.readString(), com.letv.android.remotedevice.DeviceCallback.Stub.asInterface(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterDeviceCallback(data.readString(), com.letv.android.remotedevice.DeviceCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 7:
                    Bundle _arg2;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readString();
                    _arg1 = data.readString();
                    if (data.readInt() != 0) {
                        _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    sendControlAction(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    sendInputText(data.readString(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 9:
                    Uri _arg22;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readString();
                    _arg1 = data.readString();
                    if (data.readInt() != 0) {
                        _arg22 = (Uri) Uri.CREATOR.createFromParcel(data);
                    } else {
                        _arg22 = null;
                    }
                    sendFileToDevice(_arg0, _arg1, _arg22);
                    reply.writeNoException();
                    return true;
                case 10:
                    PendingIntent _arg13;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readString();
                    if (data.readInt() != 0) {
                        _arg13 = (PendingIntent) PendingIntent.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    captureScreen(_arg0, _arg13);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readString();
                    if (data.readInt() != 0) {
                        _arg12 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    startActivity(_arg0, _arg12);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readString();
                    if (data.readInt() != 0) {
                        _arg12 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    sendBroadcast(_arg0, _arg12);
                    reply.writeNoException();
                    return true;
                case 13:
                    Uri _arg14;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readString();
                    if (data.readInt() != 0) {
                        _arg14 = (Uri) Uri.CREATOR.createFromParcel(data);
                    } else {
                        _arg14 = null;
                    }
                    playMedia(_arg0, _arg14, data.readInt());
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _result3 = getCurrentPlayState(data.readString());
                    reply.writeNoException();
                    if (_result3 != null) {
                        reply.writeInt(1);
                        _result3.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    List<Bundle> _result4 = queryLauncherActivities(data.readString());
                    reply.writeNoException();
                    reply.writeTypedList(_result4);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    List<PackageInfo> _result5 = getInstalledPackages(data.readString());
                    reply.writeNoException();
                    reply.writeTypedList(_result5);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    deletePackage(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    try {
                        reply.writeString(DESCRIPTOR);
                        return true;
                    } catch (Exception e) {
                        ExceptionUtils.writeExceptionToParcel(reply, e);
                        return true;
                    }
                default:
                    return super.onTransact(code, data, reply, flags);
            }
            ExceptionUtils.writeExceptionToParcel(reply, e);
            return true;
        }
    }

    void captureScreen(String str, PendingIntent pendingIntent) throws RemoteException;

    void deletePackage(String str, String str2) throws RemoteException;

    Bundle getCurrentPlayState(String str) throws RemoteException;

    DeviceInfo getDeviceInfo(String str) throws RemoteException;

    List<DeviceInfo> getDeviceList(int i) throws RemoteException;

    List<PackageInfo> getInstalledPackages(String str) throws RemoteException;

    DeviceInfo getLastUsedDevice(int i) throws RemoteException;

    int playMedia(String str, Uri uri, int i) throws RemoteException;

    List<Bundle> queryLauncherActivities(String str) throws RemoteException;

    void registerDeviceCallback(String str, DeviceCallback deviceCallback, int i) throws RemoteException;

    void scanDevices(PendingIntent pendingIntent) throws RemoteException;

    void sendBroadcast(String str, Intent intent) throws RemoteException;

    void sendControlAction(String str, String str2, Bundle bundle) throws RemoteException;

    void sendFileToDevice(String str, String str2, Uri uri) throws RemoteException;

    void sendInputText(String str, int i, String str2) throws RemoteException;

    void startActivity(String str, Intent intent) throws RemoteException;

    void unregisterDeviceCallback(String str, DeviceCallback deviceCallback) throws RemoteException;
}
