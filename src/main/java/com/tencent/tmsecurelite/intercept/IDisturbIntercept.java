package com.tencent.tmsecurelite.intercept;

import android.os.IInterface;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.DataEntity;
import com.tencent.tmsecurelite.commom.ITmsCallback;
import java.util.ArrayList;

public interface IDisturbIntercept extends IInterface {
    public static final String INTERFACE = "com.tencent.tmsecurelite.IDisturbIntercept";
    public static final int NAMELIST_BLACK = 0;
    public static final int NAMELIST_WHITE = 1;
    public static final int TYPE_CALL = 1;
    public static final int TYPE_SMS = 0;
    public static final int T_addToNamelist = 7;
    public static final int T_addToNamelistAsync = 21;
    public static final int T_checkVersion = 14;
    public static final int T_checkVersionAsync = 28;
    public static final int T_delete = 3;
    public static final int T_deleteAsync = 17;
    public static final int T_get = 1;
    public static final int T_getAsync = 15;
    public static final int T_getCustomModeConfs = 10;
    public static final int T_getCustomModeConfsAsync = 24;
    public static final int T_getHoldoffMode = 12;
    public static final int T_getHoldoffModeAsync = 26;
    public static final int T_getInterceptMode = 9;
    public static final int T_getInterceptModeAsync = 23;
    public static final int T_getNamelist = 6;
    public static final int T_getNamelistAsync = 20;
    public static final int T_removeNamelist = 8;
    public static final int T_removeNamelistAsync = 22;
    public static final int T_report = 2;
    public static final int T_reportAsync = 16;
    public static final int T_restore = 4;
    public static final int T_restoreAsync = 18;
    public static final int T_setHoldoffMode = 13;
    public static final int T_setHoldoffModeAsync = 27;
    public static final int T_setInterceptMode = 11;
    public static final int T_setInterceptModeAsync = 25;
    public static final int T_setSmsRead = 5;
    public static final int T_setSmsReadAsync = 19;
    public static final int T_updateTmsConfigAsync = 29;
    public static final int VERSION = 1;

    @Deprecated
    boolean addToNamelist(String str, String str2, int i) throws RemoteException;

    void addToNamelistAsync(String str, String str2, int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean checkVersion(int i) throws RemoteException;

    @Deprecated
    void delete(int i, int i2, boolean z) throws RemoteException;

    void deleteAsync(int i, int i2, boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<DataEntity> get(int i) throws RemoteException;

    void getAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getCustomModeConfs() throws RemoteException;

    void getCustomModeConfsAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getHoldoffMode() throws RemoteException;

    void getHoldoffModeAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getInterceptMode() throws RemoteException;

    void getInterceptModeAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<DataEntity> getNamelist(int i) throws RemoteException;

    void getNamelistAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void removeNamelist(int i, int i2) throws RemoteException;

    void removeNamelistAsync(int i, int i2, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean report(int i, int i2) throws RemoteException;

    void reportAsync(int i, int i2, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void restore(int i, int i2, boolean z) throws RemoteException;

    void restoreAsync(int i, int i2, boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void setHoldoffMode(int i) throws RemoteException;

    void setHoldoffModeAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void setInterceptMode(int i, int i2) throws RemoteException;

    void setInterceptModeAsync(int i, int i2, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void setSmsRead(int i) throws RemoteException;

    void setSmsReadAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    void updateTmsConfigAsync(ITmsCallback iTmsCallback) throws RemoteException;
}
