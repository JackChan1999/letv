package com.tencent.tmsecurelite.filesafe;

import android.os.IInterface;
import android.os.RemoteException;
import com.tencent.tmsecurelite.commom.DataEntity;
import com.tencent.tmsecurelite.commom.ITmsCallback;
import java.util.ArrayList;

public interface IFileSafeEncrypt extends IInterface {
    public static final String INTERFACE = "com.tencent.tmsecurelite.IFileSafeEncrypt";
    public static final int T_checkVersion = 4;
    public static final int T_checkVersionAsync = 23;
    public static final int T_decryptFiles = 8;
    public static final int T_decryptFilesAsync = 27;
    public static final int T_decryptInTemp = 18;
    public static final int T_decryptInTempAsync = 37;
    public static final int T_deleteFiles = 9;
    public static final int T_deleteFilesAsync = 28;
    public static final int T_deleteOldData = 12;
    public static final int T_deleteOldDataAsync = 31;
    public static final int T_encryptFiles = 3;
    public static final int T_encryptFiles2Async = 38;
    public static final int T_encryptFilesAsync = 22;
    public static final int T_encryptForTempDecrypt = 17;
    public static final int T_encryptForTempDecryptAsync = 36;
    public static final int T_enterPrivacySpace = 2;
    public static final int T_enterPrivacySpaceAsync = 21;
    public static final int T_getCameraSwitchStatus = 11;
    public static final int T_getCameraSwitchStatusAsync = 30;
    public static final int T_getEncryptFileCount = 14;
    public static final int T_getEncryptFileCountAsync = 33;
    public static final int T_getEncryptFileSet = 5;
    public static final int T_getEncryptFileSet2 = 19;
    public static final int T_getEncryptFileSet2Async = 39;
    public static final int T_getEncryptFileSetAsync = 24;
    public static final int T_getLatestEncryptFileDestPath = 15;
    public static final int T_getLatestEncryptFileDestPathAsync = 34;
    public static final int T_getPrivacySpaceStatus = 1;
    public static final int T_getPrivacySpaceStatusAsync = 20;
    public static final int T_partialDecrypt = 16;
    public static final int T_partialDecryptAsync = 35;
    public static final int T_registerDataChangeListener = 6;
    public static final int T_registerDataChangeListenerAsync = 25;
    public static final int T_restoreOldData = 13;
    public static final int T_restoreOldDataAsync = 32;
    public static final int T_setCameraSwitchStatus = 10;
    public static final int T_setCameraSwitchStatusAsync = 29;
    public static final int T_unregisterDataChangeListener = 7;
    public static final int T_unregisterDataChangeListenerAsync = 26;
    public static final int VERSION = 1;

    boolean checkVersion(int i) throws RemoteException;

    @Deprecated
    void decryptFiles(IDecryptListener iDecryptListener, ArrayList<String> arrayList) throws RemoteException;

    int decryptFilesAsync(IDecryptListener iDecryptListener, ArrayList<String> arrayList) throws RemoteException;

    @Deprecated
    int decryptInTemp(String str, String str2, int i) throws RemoteException;

    void decryptInTempAsync(String str, String str2, int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void deleteFiles(IDeleteListener iDeleteListener, ArrayList<String> arrayList) throws RemoteException;

    int deleteFilesAsync(IDeleteListener iDeleteListener, ArrayList<String> arrayList) throws RemoteException;

    @Deprecated
    boolean deleteOldData() throws RemoteException;

    void deleteOldDataAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void encryptFiles(IEncryptListener iEncryptListener, ArrayList<String> arrayList) throws RemoteException;

    int encryptFilesAsync(IEncryptListener iEncryptListener, ArrayList<String> arrayList) throws RemoteException;

    @Deprecated
    int encryptForTempDecrypt(String str, String str2, int i) throws RemoteException;

    void encryptForTempDecryptAsync(String str, String str2, int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void enterPrivacySpace(int i) throws RemoteException;

    void enterPrivacySpaceAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean getCameraAssistantSwitchStatus() throws RemoteException;

    void getCameraAssistantSwitchStatusAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getEncrptFileCount(int i) throws RemoteException;

    void getEncrptFileCountAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<DataEntity> getEncryptFileSet(int i) throws RemoteException;

    void getEncryptFileSet(int i, IGetEncryptFileSetListener iGetEncryptFileSetListener) throws RemoteException;

    void getEncryptFileSetAsync(int i, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    ArrayList<String> getLatestEncryptFileDestPathSet(int i, int i2) throws RemoteException;

    void getLatestEncryptFileDestPathSetAsync(int i, int i2, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    int getPrivacySpaceStatus() throws RemoteException;

    void getPrivacySpaceStatusAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    DataEntity partialDecrypt(String str) throws RemoteException;

    void partialDecryptAsync(String str, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean registerEncryptDataChangeListener(IEncryptDataChangeListener iEncryptDataChangeListener) throws RemoteException;

    int registerEncryptDataChangeListenerAsync(IEncryptDataChangeListener iEncryptDataChangeListener) throws RemoteException;

    @Deprecated
    boolean restoreOldData() throws RemoteException;

    void restoreOldDataAsync(ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    void setCameraAssistantSwitch(boolean z) throws RemoteException;

    void setCameraAssistantSwitchAsync(boolean z, ITmsCallback iTmsCallback) throws RemoteException;

    @Deprecated
    boolean unregisterEncryptDataChangeListener(IEncryptDataChangeListener iEncryptDataChangeListener) throws RemoteException;

    int unregisterEncryptDataChangeListenerAsync(IEncryptDataChangeListener iEncryptDataChangeListener) throws RemoteException;
}
