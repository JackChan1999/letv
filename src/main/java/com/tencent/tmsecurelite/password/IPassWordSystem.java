package com.tencent.tmsecurelite.password;

import android.os.IInterface;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.ITmsCallback;

public interface IPassWordSystem extends IInterface {
    public static final String INTERFACE = "com.tencent.tmsecurelite.IPassWordSystem";
    public static final int T_checkVersion = 9;
    public static final int T_checkVersionAsync = 21;
    public static final int T_getFileSafePassword = 1;
    public static final int T_getFileSafePasswordAsync = 13;
    public static final int T_getPrivacyEntranceStatus = 11;
    public static final int T_getPrivacyEntranceStatusAsync = 23;
    public static final int T_getPrivacySafeQQ = 5;
    public static final int T_getPrivacySafeQQAsync = 17;
    public static final int T_getPrivacySpaceStatus = 10;
    public static final int T_getPrivacySpaceStatusAsync = 22;
    public static final int T_getPrivacyUnifiedPasswordType = 3;
    public static final int T_getPrivacyUnifiedPasswordTypeAsync = 15;
    public static final int T_getSecureSpacePassWord = 7;
    public static final int T_getSecureSpacePassWordAsync = 19;
    public static final int T_setFileSafePassword = 2;
    public static final int T_setFileSafePasswordAsync = 14;
    public static final int T_setPrivacyEntranceStatus = 12;
    public static final int T_setPrivacyEntranceStatusAsync = 24;
    public static final int T_setPrivacySafeQQ = 6;
    public static final int T_setPrivacySafeQQAsync = 18;
    public static final int T_setPrivacyUnifiedPasswordType = 4;
    public static final int T_setPrivacyUnifiedPasswordTypeAsync = 16;
    public static final int T_setSecureSpacePassWord = 8;
    public static final int T_setSecureSpacePassWordAsync = 20;
    public static final int VERSION = 1;

    boolean checkVersion(int i) throws RemoteException;

    @Deprecated
    String getFileSafePassword() throws RemoteException;

    void getFileSafePasswordAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean getPrivacyEntranceStatus() throws RemoteException;

    void getPrivacyEntranceStatusAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    String getPrivacySafeQQ() throws RemoteException;

    void getPrivacySafeQQAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getPrivacySpaceStatus() throws RemoteException;

    void getPrivacySpaceStatusAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getPrivacyUnifiedPasswordType() throws RemoteException;

    void getPrivacyUnifiedPasswordTypeAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    String getSecureSpacePassWord() throws RemoteException;

    void getSecureSpacePassWordAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void setFileSafePassword(String str) throws RemoteException;

    int setFileSafePasswordAsync(String str) throws RemoteException;

    @Deprecated
    void setPrivacyEntranceStatus(boolean z) throws RemoteException;

    int setPrivacyEntranceStatusAsync(boolean z) throws RemoteException;

    @Deprecated
    void setPrivacySafeQQ(String str) throws RemoteException;

    int setPrivacySafeQQAsync(String str) throws RemoteException;

    @Deprecated
    void setPrivacyUnifiedPasswordType(int i) throws RemoteException;

    int setPrivacyUnifiedPasswordTypeAsync(int i) throws RemoteException;

    @Deprecated
    void setSecureSpacePassWord(String str) throws RemoteException;

    int setSecureSpacePassWordAsync(String str) throws RemoteException;
}
