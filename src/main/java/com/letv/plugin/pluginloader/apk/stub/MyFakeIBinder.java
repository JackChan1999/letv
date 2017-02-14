package com.letv.plugin.pluginloader.apk.stub;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

public class MyFakeIBinder implements IBinder {
    public String getInterfaceDescriptor() throws RemoteException {
        return null;
    }

    public boolean pingBinder() {
        return false;
    }

    public boolean isBinderAlive() {
        return false;
    }

    public IInterface queryLocalInterface(String s) {
        return null;
    }

    public void dump(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {
    }

    public void dumpAsync(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {
    }

    public boolean transact(int i, Parcel parcel, Parcel parcel1, int i1) throws RemoteException {
        return false;
    }

    public void linkToDeath(DeathRecipient deathRecipient, int i) throws RemoteException {
    }

    public boolean unlinkToDeath(DeathRecipient deathRecipient, int i) {
        return false;
    }
}
