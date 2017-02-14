package com.letv.plugin.pluginloader.apk.pm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import com.letv.plugin.pluginloader.IApkManager.Stub;
import com.letv.plugin.pluginloader.IApplicationCallback;
import com.letv.plugin.pluginloader.apk.compat.PackageManagerCompat;
import com.letv.plugin.pluginloader.apk.loader.PluginClassLoader;
import com.letv.plugin.pluginloader.apk.parser.IntentMatcher;
import com.letv.plugin.pluginloader.apk.parser.PluginPackageParser;
import com.letv.plugin.pluginloader.apk.utils.Log;
import com.letv.plugin.pluginloader.apk.utils.PluginDirHelper;
import com.letv.plugin.pluginloader.apk.utils.Utils;
import com.letv.plugin.pluginloader.util.JLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IApkManagerImpl extends Stub {
    private static final String TAG = IApkManagerImpl.class.getSimpleName();
    private BaseActivityManagerService mActivityManagerService;
    private Context mContext;
    private AtomicBoolean mHasLoadedOk = new AtomicBoolean(false);
    private Set<String> mHostRequestedPermission = new HashSet(10);
    private final Object mLock = new Object();
    private Map<String, PluginPackageParser> mPluginCache = Collections.synchronizedMap(new HashMap(20));
    private Map<String, Signature[]> mSignatureCache = new HashMap();

    public void clearApplicationUserData(java.lang.String r6, com.letv.plugin.pluginloader.IPackageDataObserver r7) throws android.os.RemoteException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r5 = this;
        r3 = 0;
        r4 = android.text.TextUtils.isEmpty(r6);	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        if (r4 == 0) goto L_0x000d;
    L_0x0007:
        if (r7 == 0) goto L_0x000c;
    L_0x0009:
        r7.onRemoveCompleted(r6, r3);
    L_0x000c:
        return;
    L_0x000d:
        r4 = r5.mPluginCache;	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        r2 = r4.get(r6);	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        r2 = (com.letv.plugin.pluginloader.apk.parser.PluginPackageParser) r2;	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        if (r2 != 0) goto L_0x001d;
    L_0x0017:
        if (r7 == 0) goto L_0x000c;
    L_0x0019:
        r7.onRemoveCompleted(r6, r3);
        goto L_0x000c;
    L_0x001d:
        r4 = 0;
        r0 = r2.getApplicationInfo(r4);	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        r4 = r0.dataDir;	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        com.letv.plugin.pluginloader.apk.utils.Utils.deleteDir(r4);	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        r3 = 1;
        if (r7 == 0) goto L_0x000c;
    L_0x002a:
        r7.onRemoveCompleted(r6, r3);
        goto L_0x000c;
    L_0x002e:
        r1 = move-exception;
        r5.handleException(r1);	 Catch:{ Exception -> 0x002e, all -> 0x0038 }
        if (r7 == 0) goto L_0x000c;
    L_0x0034:
        r7.onRemoveCompleted(r6, r3);
        goto L_0x000c;
    L_0x0038:
        r4 = move-exception;
        if (r7 == 0) goto L_0x003e;
    L_0x003b:
        r7.onRemoveCompleted(r6, r3);
    L_0x003e:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.plugin.pluginloader.apk.pm.IApkManagerImpl.clearApplicationUserData(java.lang.String, com.letv.plugin.pluginloader.IPackageDataObserver):void");
    }

    public void deleteApplicationCacheFiles(java.lang.String r8, com.letv.plugin.pluginloader.IPackageDataObserver r9) throws android.os.RemoteException {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r7 = this;
        r3 = 0;
        r4 = android.text.TextUtils.isEmpty(r8);	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        if (r4 == 0) goto L_0x000d;
    L_0x0007:
        if (r9 == 0) goto L_0x000c;
    L_0x0009:
        r9.onRemoveCompleted(r8, r3);
    L_0x000c:
        return;
    L_0x000d:
        r4 = r7.mPluginCache;	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r2 = r4.get(r8);	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r2 = (com.letv.plugin.pluginloader.apk.parser.PluginPackageParser) r2;	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        if (r2 != 0) goto L_0x001d;
    L_0x0017:
        if (r9 == 0) goto L_0x000c;
    L_0x0019:
        r9.onRemoveCompleted(r8, r3);
        goto L_0x000c;
    L_0x001d:
        r4 = 0;
        r0 = r2.getApplicationInfo(r4);	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r4 = new java.io.File;	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r5 = r0.dataDir;	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r6 = "caches";	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r4.<init>(r5, r6);	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r4 = r4.getName();	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        com.letv.plugin.pluginloader.apk.utils.Utils.deleteDir(r4);	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        r3 = 1;
        if (r9 == 0) goto L_0x000c;
    L_0x0035:
        r9.onRemoveCompleted(r8, r3);
        goto L_0x000c;
    L_0x0039:
        r1 = move-exception;
        r7.handleException(r1);	 Catch:{ Exception -> 0x0039, all -> 0x0043 }
        if (r9 == 0) goto L_0x000c;
    L_0x003f:
        r9.onRemoveCompleted(r8, r3);
        goto L_0x000c;
    L_0x0043:
        r4 = move-exception;
        if (r9 == 0) goto L_0x0049;
    L_0x0046:
        r9.onRemoveCompleted(r8, r3);
    L_0x0049:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.plugin.pluginloader.apk.pm.IApkManagerImpl.deleteApplicationCacheFiles(java.lang.String, com.letv.plugin.pluginloader.IPackageDataObserver):void");
    }

    public IApkManagerImpl(Context context) {
        this.mContext = context;
        this.mActivityManagerService = new MyActivityManagerService(this.mContext);
    }

    public void onCreate() {
        new 1(this).start();
    }

    private void onCreateInner() {
        loadAllPlugin(this.mContext);
        loadHostRequestedPermission();
        try {
            this.mHasLoadedOk.set(true);
            synchronized (this.mLock) {
                this.mLock.notifyAll();
            }
        } catch (Exception e) {
        }
    }

    private void loadHostRequestedPermission() {
        try {
            this.mHostRequestedPermission.clear();
            PackageInfo pms = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 4096);
            if (pms != null && pms.requestedPermissions != null && pms.requestedPermissions.length > 0) {
                for (String requestedPermission : pms.requestedPermissions) {
                    this.mHostRequestedPermission.add(requestedPermission);
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void loadAllPlugin(android.content.Context r27) {
        /*
        r26 = this;
        r6 = java.lang.System.currentTimeMillis();
        r4 = 0;
        r5 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0117 }
        r5.<init>();	 Catch:{ Exception -> 0x0117 }
        r10 = new java.io.File;	 Catch:{ Exception -> 0x0247 }
        r19 = com.letv.plugin.pluginloader.apk.utils.PluginDirHelper.getBaseDir(r27);	 Catch:{ Exception -> 0x0247 }
        r0 = r19;
        r10.<init>(r0);	 Catch:{ Exception -> 0x0247 }
        r12 = r10.listFiles();	 Catch:{ Exception -> 0x0247 }
        r0 = r12.length;	 Catch:{ Exception -> 0x0247 }
        r20 = r0;
        r19 = 0;
    L_0x001e:
        r0 = r19;
        r1 = r20;
        if (r0 >= r1) goto L_0x0041;
    L_0x0024:
        r11 = r12[r19];	 Catch:{ Exception -> 0x0247 }
        r21 = r11.isDirectory();	 Catch:{ Exception -> 0x0247 }
        if (r21 == 0) goto L_0x003e;
    L_0x002c:
        r14 = new java.io.File;	 Catch:{ Exception -> 0x0247 }
        r21 = "apk/base-1.apk";
        r0 = r21;
        r14.<init>(r11, r0);	 Catch:{ Exception -> 0x0247 }
        r21 = r14.exists();	 Catch:{ Exception -> 0x0247 }
        if (r21 == 0) goto L_0x003e;
    L_0x003b:
        r5.add(r14);	 Catch:{ Exception -> 0x0247 }
    L_0x003e:
        r19 = r19 + 1;
        goto L_0x001e;
    L_0x0041:
        r4 = r5;
    L_0x0042:
        r19 = "plugin";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "遍历插件apk文件耗时 t=";
        r20 = r20.append(r21);
        r22 = java.lang.System.currentTimeMillis();
        r22 = r22 - r6;
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);
        r20 = r20.toString();
        com.letv.plugin.pluginloader.util.JLog.log(r19, r20);
        r6 = java.lang.System.currentTimeMillis();
        if (r4 == 0) goto L_0x01dd;
    L_0x006c:
        r19 = r4.size();
        if (r19 <= 0) goto L_0x01dd;
    L_0x0072:
        r19 = r4.iterator();
    L_0x0076:
        r20 = r19.hasNext();
        if (r20 == 0) goto L_0x01dd;
    L_0x007c:
        r16 = r19.next();
        r16 = (java.io.File) r16;
        r8 = java.lang.System.currentTimeMillis();
        r17 = new com.letv.plugin.pluginloader.apk.parser.PluginPackageParser;	 Catch:{ Throwable -> 0x0150 }
        r0 = r26;
        r0 = r0.mContext;	 Catch:{ Throwable -> 0x0150 }
        r20 = r0;
        r0 = r17;
        r1 = r20;
        r2 = r16;
        r0.<init>(r1, r2);	 Catch:{ Throwable -> 0x0150 }
        r20 = r17.getPackageName();	 Catch:{ Throwable -> 0x0150 }
        r0 = r26;
        r1 = r20;
        r18 = r0.readSignatures(r1);	 Catch:{ Throwable -> 0x0150 }
        if (r18 == 0) goto L_0x00ac;
    L_0x00a5:
        r0 = r18;
        r0 = r0.length;	 Catch:{ Throwable -> 0x0150 }
        r20 = r0;
        if (r20 > 0) goto L_0x0138;
    L_0x00ac:
        r20 = 0;
        r0 = r17;
        r1 = r20;
        r0.collectCertificates(r1);	 Catch:{ Throwable -> 0x0150 }
        r20 = 64;
        r0 = r17;
        r1 = r20;
        r15 = r0.getPackageInfo(r1);	 Catch:{ Throwable -> 0x0150 }
        r0 = r26;
        r0.saveSignatures(r15);	 Catch:{ Throwable -> 0x0150 }
    L_0x00c4:
        r0 = r26;
        r0 = r0.mPluginCache;	 Catch:{ Throwable -> 0x0150 }
        r20 = r0;
        r21 = r17.getPackageName();	 Catch:{ Throwable -> 0x0150 }
        r20 = r20.containsKey(r21);	 Catch:{ Throwable -> 0x0150 }
        if (r20 != 0) goto L_0x00e7;
    L_0x00d4:
        r0 = r26;
        r0 = r0.mPluginCache;	 Catch:{ Throwable -> 0x0150 }
        r20 = r0;
        r21 = r17.getPackageName();	 Catch:{ Throwable -> 0x0150 }
        r0 = r20;
        r1 = r21;
        r2 = r17;
        r0.put(r1, r2);	 Catch:{ Throwable -> 0x0150 }
    L_0x00e7:
        r20 = "plugin";
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "解析插件apk花费时间=";
        r21 = r21.append(r22);
        r22 = java.lang.System.currentTimeMillis();
        r22 = r22 - r8;
        r21 = r21.append(r22);
        r22 = ",插件路径=";
        r21 = r21.append(r22);
        r22 = r16.getPath();
        r21 = r21.append(r22);
        r21 = r21.toString();
        com.letv.plugin.pluginloader.util.JLog.log(r20, r21);
        goto L_0x0076;
    L_0x0117:
        r13 = move-exception;
    L_0x0118:
        r19 = "plugin";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "遍历插件apk文件出错 e=";
        r20 = r20.append(r21);
        r21 = r13.getMessage();
        r20 = r20.append(r21);
        r20 = r20.toString();
        com.letv.plugin.pluginloader.util.JLog.log(r19, r20);
        goto L_0x0042;
    L_0x0138:
        r0 = r26;
        r0 = r0.mSignatureCache;	 Catch:{ Throwable -> 0x0150 }
        r20 = r0;
        r21 = r17.getPackageName();	 Catch:{ Throwable -> 0x0150 }
        r0 = r20;
        r1 = r21;
        r2 = r18;
        r0.put(r1, r2);	 Catch:{ Throwable -> 0x0150 }
        r17.writeSignature(r18);	 Catch:{ Throwable -> 0x0150 }
        goto L_0x00c4;
    L_0x0150:
        r13 = move-exception;
        r20 = "plugin";
        r21 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01ad }
        r21.<init>();	 Catch:{ all -> 0x01ad }
        r22 = "解析插件apk文件出错 插件路径=";
        r21 = r21.append(r22);	 Catch:{ all -> 0x01ad }
        r22 = r16.getPath();	 Catch:{ all -> 0x01ad }
        r21 = r21.append(r22);	 Catch:{ all -> 0x01ad }
        r22 = ",e=";
        r21 = r21.append(r22);	 Catch:{ all -> 0x01ad }
        r22 = r13.getMessage();	 Catch:{ all -> 0x01ad }
        r21 = r21.append(r22);	 Catch:{ all -> 0x01ad }
        r21 = r21.toString();	 Catch:{ all -> 0x01ad }
        com.letv.plugin.pluginloader.util.JLog.log(r20, r21);	 Catch:{ all -> 0x01ad }
        r20 = "plugin";
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "解析插件apk花费时间=";
        r21 = r21.append(r22);
        r22 = java.lang.System.currentTimeMillis();
        r22 = r22 - r8;
        r21 = r21.append(r22);
        r22 = ",插件路径=";
        r21 = r21.append(r22);
        r22 = r16.getPath();
        r21 = r21.append(r22);
        r21 = r21.toString();
        com.letv.plugin.pluginloader.util.JLog.log(r20, r21);
        goto L_0x0076;
    L_0x01ad:
        r19 = move-exception;
        r20 = "plugin";
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "解析插件apk花费时间=";
        r21 = r21.append(r22);
        r22 = java.lang.System.currentTimeMillis();
        r22 = r22 - r8;
        r21 = r21.append(r22);
        r22 = ",插件路径=";
        r21 = r21.append(r22);
        r22 = r16.getPath();
        r21 = r21.append(r22);
        r21 = r21.toString();
        com.letv.plugin.pluginloader.util.JLog.log(r20, r21);
        throw r19;
    L_0x01dd:
        r19 = "plugin";
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "加载解析所有插件apk花费时间=";
        r20 = r20.append(r21);
        r22 = java.lang.System.currentTimeMillis();
        r22 = r22 - r6;
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);
        r20 = r20.toString();
        com.letv.plugin.pluginloader.util.JLog.log(r19, r20);
        r6 = java.lang.System.currentTimeMillis();
        r0 = r26;
        r0 = r0.mActivityManagerService;	 Catch:{ Throwable -> 0x0230 }
        r19 = r0;
        r0 = r19;
        r1 = r26;
        r0.onCreate(r1);	 Catch:{ Throwable -> 0x0230 }
    L_0x0212:
        r19 = TAG;
        r20 = "ActivityManagerService.onCreate %s ms";
        r21 = 1;
        r0 = r21;
        r0 = new java.lang.Object[r0];
        r21 = r0;
        r22 = 0;
        r24 = java.lang.System.currentTimeMillis();
        r24 = r24 - r6;
        r23 = java.lang.Long.valueOf(r24);
        r21[r22] = r23;
        com.letv.plugin.pluginloader.apk.utils.Log.i(r19, r20, r21);
        return;
    L_0x0230:
        r13 = move-exception;
        r19 = TAG;
        r20 = "mActivityManagerService.onCreate";
        r21 = 0;
        r0 = r21;
        r0 = new java.lang.Object[r0];
        r21 = r0;
        r0 = r19;
        r1 = r20;
        r2 = r21;
        com.letv.plugin.pluginloader.apk.utils.Log.e(r0, r1, r13, r2);
        goto L_0x0212;
    L_0x0247:
        r13 = move-exception;
        r4 = r5;
        goto L_0x0118;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.plugin.pluginloader.apk.pm.IApkManagerImpl.loadAllPlugin(android.content.Context):void");
    }

    private void enforcePluginFileExists() throws RemoteException {
        List<String> removedPkg = new ArrayList();
        for (String pkg : this.mPluginCache.keySet()) {
            File pluginFile = ((PluginPackageParser) this.mPluginCache.get(pkg)).getPluginFile();
            if (pluginFile == null || !pluginFile.exists()) {
                removedPkg.add(pkg);
            }
        }
        for (String pkg2 : removedPkg) {
            deletePackage(pkg2, 0);
        }
    }

    public boolean waitForReady() {
        waitForReadyInner();
        return true;
    }

    private void waitForReadyInner() {
        if (!this.mHasLoadedOk.get()) {
            synchronized (this.mLock) {
                try {
                    this.mLock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private void handleException(Exception e) throws RemoteException {
        RemoteException remoteException;
        if (VERSION.SDK_INT >= 15) {
            remoteException = new RemoteException(e.getMessage());
            remoteException.initCause(e);
            remoteException.setStackTrace(e.getStackTrace());
        } else {
            remoteException = new RemoteException();
            remoteException.initCause(e);
            remoteException.setStackTrace(e.getStackTrace());
        }
        throw remoteException;
    }

    public PackageInfo getPackageInfo(String packageName, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            String pkg = getAndCheckCallingPkg(packageName);
            if (pkg != null) {
                enforcePluginFileExists();
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(pkg);
                if (parser != null) {
                    PackageInfo packageInfo = parser.getPackageInfo(flags);
                    if (packageInfo == null || (flags & 64) == 0 || packageInfo.signatures != null) {
                        return packageInfo;
                    }
                    packageInfo.signatures = (Signature[]) this.mSignatureCache.get(packageName);
                    return packageInfo;
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public boolean isPluginPackage(String packageName) throws RemoteException {
        waitForReadyInner();
        enforcePluginFileExists();
        return this.mPluginCache.containsKey(packageName);
    }

    public ActivityInfo getActivityInfo(ComponentName className, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            if (getAndCheckCallingPkg(className.getPackageName()) != null) {
                enforcePluginFileExists();
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(className.getPackageName());
                if (parser != null) {
                    return parser.getActivityInfo(className, flags);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public ActivityInfo getReceiverInfo(ComponentName className, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            if (getAndCheckCallingPkg(className.getPackageName()) != null) {
                enforcePluginFileExists();
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(className.getPackageName());
                if (parser != null) {
                    return parser.getReceiverInfo(className, flags);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public ServiceInfo getServiceInfo(ComponentName className, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            if (getAndCheckCallingPkg(className.getPackageName()) != null) {
                enforcePluginFileExists();
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(className.getPackageName());
                if (parser != null) {
                    return parser.getServiceInfo(className, flags);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public ProviderInfo getProviderInfo(ComponentName className, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            if (getAndCheckCallingPkg(className.getPackageName()) != null) {
                enforcePluginFileExists();
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(className.getPackageName());
                if (parser != null) {
                    return parser.getProviderInfo(className, flags);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    private boolean shouldNotBlockOtherInfo() {
        return true;
    }

    private String getAndCheckCallingPkg(String pkg) {
        return pkg;
    }

    private boolean pkgInPid(int pid, String pkg) {
        List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(pid);
        if (pkgs == null || pkgs.size() <= 0) {
            return true;
        }
        return pkgs.contains(pkg);
    }

    public ResolveInfo resolveIntent(Intent intent, String resolvedType, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            List<ResolveInfo> infos;
            if (shouldNotBlockOtherInfo()) {
                infos = IntentMatcher.resolveIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags);
                if (infos != null && infos.size() > 0) {
                    return IntentMatcher.findBest(infos);
                }
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            infos = new ArrayList();
            for (String pkg : pkgs) {
                intent.setPackage(pkg);
                infos.addAll(IntentMatcher.resolveIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags));
            }
            if (infos != null) {
                if (infos.size() > 0) {
                    return IntentMatcher.findBest(infos);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public List<ResolveInfo> queryIntentActivities(Intent intent, String resolvedType, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                return IntentMatcher.resolveActivityIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags);
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            List<ResolveInfo> infos = new ArrayList();
            for (String pkg : pkgs) {
                intent.setPackage(pkg);
                infos.addAll(IntentMatcher.resolveActivityIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags));
            }
            if (infos != null) {
                if (infos.size() > 0) {
                    return infos;
                }
            }
            return null;
        } catch (Exception e) {
            handleException(e);
        }
    }

    public List<ResolveInfo> queryIntentReceivers(Intent intent, String resolvedType, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                return IntentMatcher.resolveReceiverIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags);
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            List<ResolveInfo> infos = new ArrayList();
            for (String pkg : pkgs) {
                intent.setPackage(pkg);
                infos.addAll(IntentMatcher.resolveReceiverIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags));
            }
            if (infos != null) {
                if (infos.size() > 0) {
                    return infos;
                }
            }
            return null;
        } catch (Exception e) {
            handleException(e);
        }
    }

    public ResolveInfo resolveService(Intent intent, String resolvedType, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            List<ResolveInfo> infos;
            if (shouldNotBlockOtherInfo()) {
                infos = IntentMatcher.resolveServiceIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags);
                if (infos != null && infos.size() > 0) {
                    return IntentMatcher.findBest(infos);
                }
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            infos = new ArrayList();
            for (String pkg : pkgs) {
                intent.setPackage(pkg);
                infos.addAll(IntentMatcher.resolveServiceIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags));
            }
            if (infos != null) {
                if (infos.size() > 0) {
                    return IntentMatcher.findBest(infos);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public List<ResolveInfo> queryIntentServices(Intent intent, String resolvedType, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                IntentMatcher.resolveServiceIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags);
            } else {
                List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
                List<ResolveInfo> infos = new ArrayList();
                for (String pkg : pkgs) {
                    intent.setPackage(pkg);
                    infos.addAll(IntentMatcher.resolveServiceIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags));
                }
                if (infos != null) {
                    if (infos.size() > 0) {
                        return infos;
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public List<ResolveInfo> queryIntentContentProviders(Intent intent, String resolvedType, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                return IntentMatcher.resolveProviderIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags);
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            List<ResolveInfo> infos = new ArrayList();
            for (String pkg : pkgs) {
                intent.setPackage(pkg);
                infos.addAll(IntentMatcher.resolveProviderIntent(this.mContext, this.mPluginCache, intent, resolvedType, flags));
            }
            if (infos != null) {
                if (infos.size() > 0) {
                    return infos;
                }
            }
            return null;
        } catch (Exception e) {
            handleException(e);
        }
    }

    public List<PackageInfo> getInstalledPackages(int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            List<PackageInfo> infos = new ArrayList(this.mPluginCache.size());
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    infos.add(pluginPackageParser.getPackageInfo(flags));
                }
                return infos;
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                if (pkgs.contains(pluginPackageParser2.getPackageName())) {
                    infos.add(pluginPackageParser2.getPackageInfo(flags));
                }
            }
            return infos;
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public List<ApplicationInfo> getInstalledApplications(int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            List<ApplicationInfo> infos = new ArrayList(this.mPluginCache.size());
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    infos.add(pluginPackageParser.getApplicationInfo(flags));
                }
                return infos;
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                if (pkgs.contains(pluginPackageParser2.getPackageName())) {
                    infos.add(pluginPackageParser2.getApplicationInfo(flags));
                }
            }
            return infos;
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public PermissionInfo getPermissionInfo(String name, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    for (PermissionInfo permissionInfo : pluginPackageParser.getPermissions()) {
                        if (TextUtils.equals(permissionInfo.name, name)) {
                            return permissionInfo;
                        }
                    }
                }
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                for (PermissionInfo permissionInfo2 : pluginPackageParser2.getPermissions()) {
                    if (TextUtils.equals(permissionInfo2.name, name) && pkgs.contains(permissionInfo2.packageName)) {
                        return permissionInfo2;
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            List<PermissionInfo> list = new ArrayList();
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    for (PermissionInfo permissionInfo : pluginPackageParser.getPermissions()) {
                        if (TextUtils.equals(permissionInfo.group, group) && !list.contains(permissionInfo)) {
                            list.add(permissionInfo);
                        }
                    }
                }
                return list;
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                for (PermissionInfo permissionInfo2 : pluginPackageParser2.getPermissions()) {
                    if (pkgs.contains(permissionInfo2.packageName) && TextUtils.equals(permissionInfo2.group, group) && !list.contains(permissionInfo2)) {
                        list.add(permissionInfo2);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    for (PermissionGroupInfo permissionGroupInfo : pluginPackageParser.getPermissionGroups()) {
                        if (TextUtils.equals(permissionGroupInfo.name, name)) {
                            return permissionGroupInfo;
                        }
                    }
                }
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                for (PermissionGroupInfo permissionGroupInfo2 : pluginPackageParser2.getPermissionGroups()) {
                    if (TextUtils.equals(permissionGroupInfo2.name, name) && pkgs.contains(permissionGroupInfo2.packageName)) {
                        return permissionGroupInfo2;
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            List<PermissionGroupInfo> list = new ArrayList();
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    for (PermissionGroupInfo permissionGroupInfo : pluginPackageParser.getPermissionGroups()) {
                        if (!list.contains(permissionGroupInfo)) {
                            list.add(permissionGroupInfo);
                        }
                    }
                }
                return list;
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                for (PermissionGroupInfo permissionGroupInfo2 : pluginPackageParser2.getPermissionGroups()) {
                    if (!list.contains(permissionGroupInfo2) && pkgs.contains(permissionGroupInfo2.packageName)) {
                        list.add(permissionGroupInfo2);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public ProviderInfo resolveContentProvider(String name, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            enforcePluginFileExists();
            if (shouldNotBlockOtherInfo()) {
                for (PluginPackageParser pluginPackageParser : this.mPluginCache.values()) {
                    for (ProviderInfo providerInfo : pluginPackageParser.getProviders()) {
                        if (TextUtils.equals(providerInfo.authority, name)) {
                            return providerInfo;
                        }
                    }
                }
            }
            List<String> pkgs = this.mActivityManagerService.getPackageNamesByPid(Binder.getCallingPid());
            for (PluginPackageParser pluginPackageParser2 : this.mPluginCache.values()) {
                for (ProviderInfo providerInfo2 : pluginPackageParser2.getProviders()) {
                    if (TextUtils.equals(providerInfo2.authority, name) && pkgs.contains(providerInfo2.packageName)) {
                        return providerInfo2;
                    }
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public ApplicationInfo getApplicationInfo(String packageName, int flags) throws RemoteException {
        waitForReadyInner();
        try {
            PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(packageName);
            if (parser != null) {
                return parser.getApplicationInfo(flags);
            }
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public int installPackage(String filepath, int flags) throws RemoteException {
        String apkfile = null;
        try {
            PackageManager pm = this.mContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(filepath, 0);
            if (info == null) {
                return -2;
            }
            JLog.log("plugin", "安装插件 info.packageName =" + info.packageName);
            apkfile = PluginDirHelper.getPluginFilePath(this.mContext, info.packageName);
            JLog.log("plugin", "安装插件 apkfile=" + apkfile + ",(flags & PackageManagerCompat.INSTALL_REPLACE_EXISTING)=" + (flags & 2));
            PluginPackageParser parser;
            PackageInfo pkgInfo;
            String[] strArr;
            int length;
            int i;
            String requestedPermission;
            boolean b;
            if ((flags & 2) != 0) {
                forceStopPackage(info.packageName);
                if (this.mPluginCache.containsKey(info.packageName)) {
                    deleteApplicationCacheFiles(info.packageName, null);
                }
                parser = new PluginPackageParser(this.mContext, new File(apkfile));
                parser.collectCertificates(0);
                pkgInfo = parser.getPackageInfo(4160);
                if (!(pkgInfo == null || pkgInfo.requestedPermissions == null || pkgInfo.requestedPermissions.length <= 0)) {
                    strArr = pkgInfo.requestedPermissions;
                    length = strArr.length;
                    i = 0;
                    while (i < length) {
                        requestedPermission = strArr[i];
                        b = false;
                        try {
                            b = pm.getPermissionInfo(requestedPermission, 0) != null;
                        } catch (NameNotFoundException e) {
                        }
                        if (this.mHostRequestedPermission.contains(requestedPermission) || !b) {
                            i++;
                        } else {
                            JLog.log("plugin", "插件安装失败 宿主应用未预申请权限Permission=" + requestedPermission + ",filepath=" + filepath);
                            new File(apkfile).delete();
                            return ApkManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION;
                        }
                    }
                }
                saveSignatures(pkgInfo);
                copyNativeLibs(this.mContext, apkfile, parser.getApplicationInfo(0));
                dexOpt(this.mContext, apkfile, parser);
                this.mPluginCache.put(parser.getPackageName(), parser);
                this.mActivityManagerService.onPkgInstalled(this.mPluginCache, parser, parser.getPackageName());
                sendInstalledBroadcast(info.packageName);
                return 1;
            }
            if (info.packageName.equals("com.letv.android.lite") && ApkManager.getInstance().getPluginInstallState("com.letv.android.lite") != 1) {
                this.mPluginCache.remove(info.packageName);
            }
            if (this.mPluginCache.containsKey(info.packageName)) {
                return -1;
            }
            forceStopPackage(info.packageName);
            parser = new PluginPackageParser(this.mContext, new File(apkfile));
            parser.collectCertificates(0);
            pkgInfo = parser.getPackageInfo(4160);
            if (!(pkgInfo == null || pkgInfo.requestedPermissions == null || pkgInfo.requestedPermissions.length <= 0)) {
                strArr = pkgInfo.requestedPermissions;
                length = strArr.length;
                i = 0;
                while (i < length) {
                    requestedPermission = strArr[i];
                    b = false;
                    try {
                        b = pm.getPermissionInfo(requestedPermission, 0) != null;
                    } catch (NameNotFoundException e2) {
                    }
                    if (this.mHostRequestedPermission.contains(requestedPermission) || !b) {
                        i++;
                    } else {
                        JLog.log("plugin", "插件安装失败，宿主应用未事先申请权限: " + requestedPermission + ", filepath = " + filepath);
                        new File(apkfile).delete();
                        return ApkManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION;
                    }
                }
            }
            saveSignatures(pkgInfo);
            copyNativeLibs(this.mContext, apkfile, parser.getApplicationInfo(0));
            dexOpt(this.mContext, apkfile, parser);
            this.mPluginCache.put(parser.getPackageName(), parser);
            this.mActivityManagerService.onPkgInstalled(this.mPluginCache, parser, parser.getPackageName());
            sendInstalledBroadcast(info.packageName);
            JLog.log("plugin", "写入 " + info.packageName + " 的插件安装状态 " + 1);
            ApkManager.getInstance().setPluginInstallState(info.packageName, 1);
            return 1;
        } catch (Exception e3) {
            JLog.log("plugin", "插件安装失败，文件路径=" + filepath + ",e=" + e3.getMessage());
            if (apkfile != null) {
                new File(apkfile).delete();
            }
            handleException(e3);
            return PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR;
        }
    }

    private void dexOpt(Context hostContext, String apkfile, PluginPackageParser parser) throws Exception {
        String packageName = parser.getPackageName();
        ClassLoader classloader = new PluginClassLoader(apkfile, PluginDirHelper.getPluginDalvikCacheDir(hostContext, packageName), PluginDirHelper.getPluginNativeLibraryDir(hostContext, packageName), ClassLoader.getSystemClassLoader());
    }

    private void saveSignatures(PackageInfo pkgInfo) {
        JLog.log("plugin", "保存签名信息: " + pkgInfo.signatures);
        if (pkgInfo != null && pkgInfo.signatures != null) {
            int i = 0;
            Signature[] signatureArr = pkgInfo.signatures;
            int length = signatureArr.length;
            int i2 = 0;
            while (i2 < length) {
                Signature signature = signatureArr[i2];
                File file = new File(PluginDirHelper.getPluginSignatureFile(this.mContext, pkgInfo.packageName, i));
                try {
                    Utils.writeToFile(file, signature.toByteArray());
                    JLog.log("plugin", "保存签名信息 包名=" + pkgInfo.packageName + ",i=" + i + ", 签名md5=" + Utils.md5(signature.toByteArray()));
                    i++;
                    i2++;
                } catch (Exception e) {
                    JLog.log("plugin", "保存签名信息失败 e=" + e.getMessage());
                    file.delete();
                    Utils.deleteDir(PluginDirHelper.getPluginSignatureDir(this.mContext, pkgInfo.packageName));
                    return;
                }
            }
        }
    }

    private Signature[] readSignatures(String packageName) {
        List<String> fils = PluginDirHelper.getPluginSignatureFiles(this.mContext, packageName);
        List<Signature> signatures = new ArrayList(fils.size());
        int i = 0;
        for (String file : fils) {
            try {
                byte[] data = Utils.readFromFile(new File(file));
                if (data != null) {
                    Signature sin = new Signature(data);
                    signatures.add(sin);
                    JLog.log("plugin", "读取签名信息 包名=" + packageName + ",i=" + i + ",签名md5=" + Utils.md5(sin.toByteArray()));
                    i++;
                } else {
                    JLog.log("plugin", "读取签名信息失败 i=" + i);
                    return null;
                }
            } catch (Exception e) {
                JLog.log("plugin", "读取签名信息失败 e=" + e.getMessage());
                return null;
            }
        }
        return (Signature[]) signatures.toArray(new Signature[signatures.size()]);
    }

    private void sendInstalledBroadcast(String packageName) {
        Intent intent = new Intent(ApkManager.ACTION_PACKAGE_ADDED);
        intent.setData(Uri.parse("package://" + packageName));
        JLog.log("plugin", "发送插件安装成功广播消息 = " + packageName);
        this.mContext.sendBroadcast(intent);
    }

    private void sendUninstalledBroadcast(String packageName) {
        Intent intent = new Intent(ApkManager.ACTION_PACKAGE_REMOVED);
        intent.setData(Uri.parse("package://" + packageName));
        this.mContext.sendBroadcast(intent);
    }

    private void copyNativeLibs(Context context, String apkfile, ApplicationInfo applicationInfo) throws Exception {
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        Throwable th;
        String nativeLibraryDir = PluginDirHelper.getPluginNativeLibraryDir(context, applicationInfo.packageName);
        ZipFile zipFile = null;
        try {
            ZipFile zipFile2 = new ZipFile(apkfile);
            try {
                String soName;
                File file;
                Exception e;
                Enumeration<? extends ZipEntry> entries = zipFile2.entries();
                Map<String, ZipEntry> libZipEntries = new HashMap();
                Map<String, Set<String>> soList = new HashMap(1);
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String name = entry.getName();
                    if (!(name.contains("../") || !name.startsWith("lib/") || entry.isDirectory())) {
                        libZipEntries.put(name, entry);
                        soName = new File(name).getName();
                        Set<String> fs = (Set) soList.get(soName);
                        if (fs == null) {
                            fs = new TreeSet();
                            soList.put(soName, fs);
                        }
                        fs.add(name);
                    }
                }
                for (String soName2 : soList.keySet()) {
                    String soPath = findSoPath((Set) soList.get(soName2));
                    if (soPath != null) {
                        file = new File(nativeLibraryDir, soName2);
                        if (file.exists()) {
                            file.delete();
                        }
                        inputStream = null;
                        fileOutputStream = null;
                        try {
                            inputStream = zipFile2.getInputStream((ZipEntry) libZipEntries.get(soPath));
                            FileOutputStream ou = new FileOutputStream(file);
                            try {
                                byte[] buf = new byte[8192];
                                while (true) {
                                    int read = inputStream.read(buf);
                                    if (read == -1) {
                                        break;
                                    }
                                    ou.write(buf, 0, read);
                                }
                                ou.flush();
                                ou.getFD().sync();
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Exception e2) {
                                    }
                                }
                                if (ou != null) {
                                    try {
                                        ou.close();
                                    } catch (Exception e3) {
                                    }
                                }
                            } catch (Exception e4) {
                                e = e4;
                                fileOutputStream = ou;
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream = ou;
                            }
                        } catch (Exception e5) {
                            e = e5;
                        }
                    }
                }
                if (zipFile2 != null) {
                    try {
                        zipFile2.close();
                        return;
                    } catch (Exception e6) {
                        return;
                    }
                }
                return;
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    throw e;
                } catch (Throwable th3) {
                    th = th3;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e7) {
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e8) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                zipFile = zipFile2;
            }
        } catch (Throwable th5) {
            th = th5;
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (Exception e9) {
                }
            }
            throw th;
        }
    }

    private String findSoPath(Set<String> soPaths) {
        if (soPaths != null && soPaths.size() > 0) {
            for (String soPath : soPaths) {
                if (!TextUtils.isEmpty(Build.CPU_ABI) && soPath.contains(Build.CPU_ABI)) {
                    return soPath;
                }
            }
            for (String soPath2 : soPaths) {
                if (!TextUtils.isEmpty(Build.CPU_ABI2) && soPath2.contains(Build.CPU_ABI2)) {
                    return soPath2;
                }
            }
        }
        return null;
    }

    public int deletePackage(String packageName, int flags) throws RemoteException {
        try {
            if (this.mPluginCache.containsKey(packageName)) {
                PluginPackageParser parser;
                forceStopPackage(packageName);
                synchronized (this.mPluginCache) {
                    parser = (PluginPackageParser) this.mPluginCache.remove(packageName);
                }
                Utils.deleteDir(PluginDirHelper.makePluginBaseDir(this.mContext, packageName));
                this.mActivityManagerService.onPkgDeleted(this.mPluginCache, parser, packageName);
                this.mSignatureCache.remove(packageName);
                sendUninstalledBroadcast(packageName);
                return 1;
            }
        } catch (Exception e) {
            handleException(e);
        }
        return -1;
    }

    public List<ActivityInfo> getReceivers(String packageName, int flags) throws RemoteException {
        try {
            if (getAndCheckCallingPkg(packageName) != null) {
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(packageName);
                if (parser != null) {
                    return new ArrayList(parser.getReceivers());
                }
            }
            return new ArrayList(0);
        } catch (Exception e) {
            RemoteException remoteException = new RemoteException();
            remoteException.setStackTrace(e.getStackTrace());
            throw remoteException;
        }
    }

    public List<IntentFilter> getReceiverIntentFilter(ActivityInfo info) throws RemoteException {
        try {
            if (getAndCheckCallingPkg(info.packageName) != null) {
                PluginPackageParser parser = (PluginPackageParser) this.mPluginCache.get(info.packageName);
                if (parser != null) {
                    List<IntentFilter> filters = parser.getReceiverIntentFilter(info);
                    if (filters != null && filters.size() > 0) {
                        return new ArrayList(filters);
                    }
                }
            }
            return new ArrayList(0);
        } catch (Exception e) {
            RemoteException remoteException = new RemoteException();
            remoteException.setStackTrace(e.getStackTrace());
            throw remoteException;
        }
    }

    public int checkSignatures(String pkg1, String pkg2) throws RemoteException {
        PackageManager pm = this.mContext.getPackageManager();
        Signature[] signatureArr = new Signature[0];
        try {
            signatureArr = getSignature(pkg1, pm);
            Signature[] signatureArr2 = new Signature[0];
            try {
                signatureArr2 = getSignature(pkg2, pm);
                boolean pkg1Signed = signatureArr != null && signatureArr.length > 0;
                boolean pkg2Signed = signatureArr2 != null && signatureArr2.length > 0;
                if (!pkg1Signed && !pkg2Signed) {
                    return 1;
                }
                if (!pkg1Signed && pkg2Signed) {
                    return -1;
                }
                if (pkg1Signed && !pkg2Signed) {
                    return -2;
                }
                if (signatureArr.length != signatureArr2.length) {
                    return -3;
                }
                for (int i = 0; i < signatureArr.length; i++) {
                    if (!Arrays.equals(signatureArr[i].toByteArray(), signatureArr2[i].toByteArray())) {
                        return -3;
                    }
                }
                return 0;
            } catch (NameNotFoundException e) {
                return -4;
            }
        } catch (NameNotFoundException e2) {
            return -4;
        }
    }

    private Signature[] getSignature(String pkg, PackageManager pm) throws RemoteException, NameNotFoundException {
        PackageInfo info = getPackageInfo(pkg, 64);
        if (info == null) {
            info = pm.getPackageInfo(pkg, 64);
        }
        if (info != null) {
            return info.signatures;
        }
        throw new NameNotFoundException();
    }

    public ActivityInfo selectStubActivityInfo(ActivityInfo pluginInfo) throws RemoteException {
        return this.mActivityManagerService.selectStubActivityInfo(Binder.getCallingPid(), Binder.getCallingUid(), pluginInfo);
    }

    public ActivityInfo selectStubActivityInfoByIntent(Intent intent) throws RemoteException {
        ActivityInfo ai = null;
        if (intent.getComponent() != null) {
            ai = getActivityInfo(intent.getComponent(), 0);
        } else {
            ResolveInfo resolveInfo = resolveIntent(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 0);
            if (!(resolveInfo == null || resolveInfo.activityInfo == null)) {
                ai = resolveInfo.activityInfo;
            }
        }
        if (ai != null) {
            return selectStubActivityInfo(ai);
        }
        return null;
    }

    public ServiceInfo selectStubServiceInfo(ServiceInfo targetInfo) throws RemoteException {
        return this.mActivityManagerService.selectStubServiceInfo(Binder.getCallingPid(), Binder.getCallingUid(), targetInfo);
    }

    public ServiceInfo selectStubServiceInfoByIntent(Intent intent) throws RemoteException {
        ServiceInfo ai = null;
        if (intent.getComponent() != null) {
            ai = getServiceInfo(intent.getComponent(), 0);
        } else {
            ResolveInfo resolveInfo = resolveIntent(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 0);
            if (resolveInfo.serviceInfo != null) {
                ai = resolveInfo.serviceInfo;
            }
        }
        if (ai != null) {
            return selectStubServiceInfo(ai);
        }
        return null;
    }

    public ServiceInfo getTargetServiceInfo(ServiceInfo targetInfo) throws RemoteException {
        return this.mActivityManagerService.getTargetServiceInfo(Binder.getCallingPid(), Binder.getCallingUid(), targetInfo);
    }

    public ProviderInfo selectStubProviderInfo(String name) throws RemoteException {
        return this.mActivityManagerService.selectStubProviderInfo(Binder.getCallingPid(), Binder.getCallingUid(), resolveContentProvider(name, 0));
    }

    public List<String> getPackageNameByPid(int pid) throws RemoteException {
        List<String> packageNameByProcessName = this.mActivityManagerService.getPackageNamesByPid(pid);
        if (packageNameByProcessName != null) {
            return new ArrayList(packageNameByProcessName);
        }
        return null;
    }

    public String getProcessNameByPid(int pid) throws RemoteException {
        return this.mActivityManagerService.getProcessNameByPid(pid);
    }

    public boolean killBackgroundProcesses(String pluginPackageName) throws RemoteException {
        boolean success = false;
        for (RunningAppProcessInfo info : ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses()) {
            if (info.pkgList != null) {
                String[] pkgListCopy = (String[]) Arrays.copyOf(info.pkgList, info.pkgList.length);
                Arrays.sort(pkgListCopy);
                if (Arrays.binarySearch(pkgListCopy, pluginPackageName) >= 0 && info.pid != Process.myPid()) {
                    Log.i(TAG, "killBackgroundProcesses(%s),pkgList=%s,pid=%s", pluginPackageName, Arrays.toString(info.pkgList), Integer.valueOf(info.pid));
                    Process.killProcess(info.pid);
                    success = true;
                }
            }
        }
        return success;
    }

    public boolean killApplicationProcess(String pluginPackageName) throws RemoteException {
        return killBackgroundProcesses(pluginPackageName);
    }

    public boolean forceStopPackage(String pluginPackageName) throws RemoteException {
        JLog.log("plugin", "强制插件进程停止 pluginPackageName=" + pluginPackageName);
        return killBackgroundProcesses(pluginPackageName);
    }

    public boolean registerApplicationCallback(IApplicationCallback callback) throws RemoteException {
        return this.mActivityManagerService.registerApplicationCallback(Binder.getCallingPid(), Binder.getCallingUid(), callback);
    }

    public boolean unregisterApplicationCallback(IApplicationCallback callback) throws RemoteException {
        return this.mActivityManagerService.unregisterApplicationCallback(Binder.getCallingPid(), Binder.getCallingUid(), callback);
    }

    public void onActivityCreated(ActivityInfo stubInfo, ActivityInfo targetInfo) throws RemoteException {
        this.mActivityManagerService.onActivityCreated(Binder.getCallingPid(), Binder.getCallingUid(), stubInfo, targetInfo);
    }

    public void onActivityDestory(ActivityInfo stubInfo, ActivityInfo targetInfo) throws RemoteException {
        this.mActivityManagerService.onActivityDestory(Binder.getCallingPid(), Binder.getCallingUid(), stubInfo, targetInfo);
    }

    public void onServiceCreated(ServiceInfo stubInfo, ServiceInfo targetInfo) throws RemoteException {
        this.mActivityManagerService.onServiceCreated(Binder.getCallingPid(), Binder.getCallingUid(), stubInfo, targetInfo);
    }

    public void onServiceDestory(ServiceInfo stubInfo, ServiceInfo targetInfo) throws RemoteException {
        this.mActivityManagerService.onServiceDestory(Binder.getCallingPid(), Binder.getCallingUid(), stubInfo, targetInfo);
    }

    public void onProviderCreated(ProviderInfo stubInfo, ProviderInfo targetInfo) throws RemoteException {
        this.mActivityManagerService.onProviderCreated(Binder.getCallingPid(), Binder.getCallingUid(), stubInfo, targetInfo);
    }

    public void reportMyProcessName(String stubProcessName, String targetProcessName, String targetPkg) throws RemoteException {
        this.mActivityManagerService.onReportMyProcessName(Binder.getCallingPid(), Binder.getCallingUid(), stubProcessName, targetProcessName, targetPkg);
    }

    public void onDestroy() {
        this.mActivityManagerService.onDestory();
    }

    public void onActivtyOnNewIntent(ActivityInfo stubInfo, ActivityInfo targetInfo, Intent intent) throws RemoteException {
        this.mActivityManagerService.onActivtyOnNewIntent(Binder.getCallingPid(), Binder.getCallingUid(), stubInfo, targetInfo, intent);
    }

    public int getMyPid() {
        return Process.myPid();
    }
}
