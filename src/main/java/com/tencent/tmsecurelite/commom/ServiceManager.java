package com.tencent.tmsecurelite.commom;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.tencent.tmsecurelite.accountsc.AccountSecureStub;
import com.tencent.tmsecurelite.filesafe.FileSafeEncryptStub;
import com.tencent.tmsecurelite.intercept.DisturbInterceptStub;
import com.tencent.tmsecurelite.networkmgr.NetworkMgrServiceStub;
import com.tencent.tmsecurelite.optimize.SystemOptimizeStub;
import com.tencent.tmsecurelite.password.PassWordSystemStub;
import com.tencent.tmsecurelite.paysecure.PaySecureStub;
import com.tencent.tmsecurelite.root.RootServiceStub;
import com.tencent.tmsecurelite.softwaremove.SoftMoveServiceStub;
import com.tencent.tmsecurelite.virusscan.VirusScanStub;

public class ServiceManager {
    static final String SERVICE_ACTION = "com.tencent.qqpimsecure.TMS_LITE_SERVICE";
    static final String SERVICE_ACTION_ACCOUNTSECURE = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_ACCOUNTSECURE";
    static final String SERVICE_ACTION_FILESAFE = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_FILESAFE";
    static final String SERVICE_ACTION_INTERCEPT = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_INTERCEPT";
    static final String SERVICE_ACTION_NETWORKMGR = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_NETWORKMGR";
    static final String SERVICE_ACTION_OPTIMIZE = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_OPTIMIZE";
    static final String SERVICE_ACTION_PASSWORD = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_PASSWORD";
    static final String SERVICE_ACTION_PAYSECURE = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_PAYSECURE";
    static final String SERVICE_ACTION_ROOT = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_ROOT";
    static final String SERVICE_ACTION_SOFTMOVE = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_SOFTMOVE";
    static final String SERVICE_ACTION_VIRUS = "com.tecnent.qqpimsecure.TMS_LITE_SERVICE_VIRUS";
    static final String SERVICE_PACAKGE = "com.tencent.qqpimsecure";
    public static final String SERVICE_TYPE = "service_type";
    public static final int TYPE_ACCOUNT_SECURE = 9;
    public static final int TYPE_DISTURB_INTERCEPT = 2;
    public static final int TYPE_FILE_SAFE = 3;
    public static final int TYPE_NETWORK_MGR = 8;
    public static final int TYPE_PASSWORD_SYSTEM = 4;
    public static final int TYPE_PAY_SECURE = 6;
    public static final int TYPE_ROOT_SERVICE = 5;
    public static final int TYPE_SOFT_MOVE = 7;
    public static final int TYPE_SYSTEM_OPTIMIZE = 0;
    public static final int TYPE_VIRUS_SCAN = 1;

    public static final Intent getIntent(int type) {
        Intent intent = new Intent();
        intent.setPackage(SERVICE_PACAKGE);
        intent.putExtra(SERVICE_TYPE, type);
        switch (type) {
            case 0:
                intent.setAction(SERVICE_ACTION_OPTIMIZE);
                break;
            case 1:
                intent.setAction(SERVICE_ACTION_VIRUS);
                break;
            case 2:
                intent.setAction(SERVICE_ACTION_INTERCEPT);
                break;
            case 3:
                intent.setAction(SERVICE_ACTION_FILESAFE);
                break;
            case 4:
                intent.setAction(SERVICE_ACTION_PASSWORD);
                break;
            case 5:
                intent.setAction(SERVICE_ACTION_ROOT);
                break;
            case 6:
                intent.setAction(SERVICE_ACTION_PAYSECURE);
                break;
            case 7:
                intent.setAction(SERVICE_ACTION_SOFTMOVE);
                break;
            case 8:
                intent.setAction(SERVICE_ACTION_NETWORKMGR);
                break;
            case 9:
                intent.setAction(SERVICE_ACTION_ACCOUNTSECURE);
                break;
        }
        return intent;
    }

    public static final IInterface getInterface(int type, IBinder binder) {
        switch (type) {
            case 0:
                return SystemOptimizeStub.asInterface(binder);
            case 1:
                return VirusScanStub.asInterface(binder);
            case 2:
                return DisturbInterceptStub.asInterface(binder);
            case 3:
                return FileSafeEncryptStub.asInterface(binder);
            case 4:
                return PassWordSystemStub.asInterface(binder);
            case 5:
                return RootServiceStub.asInterface(binder);
            case 6:
                return PaySecureStub.asInterface(binder);
            case 7:
                return SoftMoveServiceStub.asInterface(binder);
            case 8:
                return NetworkMgrServiceStub.asInterface(binder);
            case 9:
                return AccountSecureStub.asInterface(binder);
            default:
                return null;
        }
    }
}
