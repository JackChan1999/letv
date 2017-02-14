package com.letv.plugin.pluginloader.apk.pm;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import com.letv.plugin.pluginloader.apk.compat.ActivityThreadCompat;
import com.letv.plugin.pluginloader.apk.compat.CompatibilityInfoCompat;
import com.letv.plugin.pluginloader.apk.compat.ProcessCompat;
import com.letv.plugin.pluginloader.apk.hook.HookFactory;
import com.letv.plugin.pluginloader.apk.loader.PluginClassLoader;
import com.letv.plugin.pluginloader.apk.stub.ActivityStub;
import com.letv.plugin.pluginloader.apk.stub.ServiceStub;
import com.letv.plugin.pluginloader.apk.utils.FieldUtils;
import com.letv.plugin.pluginloader.apk.utils.MethodUtils;
import com.letv.plugin.pluginloader.apk.utils.PluginDirHelper;
import com.letv.plugin.pluginloader.util.JLog;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class PluginProcessManager {
    private static String mCurrentProcessName;
    private static AtomicBoolean mExec = new AtomicBoolean(false);
    private static WeakHashMap<Integer, Context> mFakedContext = new WeakHashMap(1);
    private static Handler mHandle = new Handler(Looper.getMainLooper());
    private static Object mServiceCache = null;
    private static HashMap<String, Application> sApplicationsCache = new HashMap(2);
    private static Object sGetCurrentProcessNameLock = new Object();
    private static Map<String, ClassLoader> sPluginClassLoaderCache = new WeakHashMap(1);
    private static Map<String, Object> sPluginLoadedApkCache = new WeakHashMap(1);
    private static List<String> sProcessList = new ArrayList();
    private static List<String> sSkipService = new ArrayList();

    static {
        sSkipService.add("layout_inflater");
        sSkipService.add("notification");
        sSkipService.add("storage");
        sSkipService.add("accessibility");
        sSkipService.add("audio");
        sSkipService.add("clipboard");
        sSkipService.add("media_router");
        sSkipService.add("wifi");
        sSkipService.add("captioning");
        sSkipService.add("account");
        sSkipService.add("activity");
        sSkipService.add("wifiscanner");
        sSkipService.add("rttmanager");
        sSkipService.add("tv_input");
        sSkipService.add("jobscheduler");
        sSkipService.add("sensorhub");
        sSkipService.add("servicediscovery");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCurrentProcessName(android.content.Context r7) {
        /*
        r4 = sGetCurrentProcessNameLock;
        monitor-enter(r4);
        r3 = mCurrentProcessName;	 Catch:{ all -> 0x0037 }
        if (r3 != 0) goto L_0x0033;
    L_0x0007:
        r3 = "activity";
        r0 = r7.getSystemService(r3);	 Catch:{ all -> 0x0037 }
        r0 = (android.app.ActivityManager) r0;	 Catch:{ all -> 0x0037 }
        r2 = r0.getRunningAppProcesses();	 Catch:{ all -> 0x0037 }
        r3 = r2.iterator();	 Catch:{ all -> 0x0037 }
    L_0x0017:
        r5 = r3.hasNext();	 Catch:{ all -> 0x0037 }
        if (r5 == 0) goto L_0x0033;
    L_0x001d:
        r1 = r3.next();	 Catch:{ all -> 0x0037 }
        r1 = (android.app.ActivityManager.RunningAppProcessInfo) r1;	 Catch:{ all -> 0x0037 }
        r5 = r1.pid;	 Catch:{ all -> 0x0037 }
        r6 = android.os.Process.myPid();	 Catch:{ all -> 0x0037 }
        if (r5 != r6) goto L_0x0017;
    L_0x002b:
        r3 = r1.processName;	 Catch:{ all -> 0x0037 }
        mCurrentProcessName = r3;	 Catch:{ all -> 0x0037 }
        r3 = mCurrentProcessName;	 Catch:{ all -> 0x0037 }
        monitor-exit(r4);	 Catch:{ all -> 0x0037 }
    L_0x0032:
        return r3;
    L_0x0033:
        monitor-exit(r4);	 Catch:{ all -> 0x0037 }
        r3 = mCurrentProcessName;
        goto L_0x0032;
    L_0x0037:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0037 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.plugin.pluginloader.apk.pm.PluginProcessManager.getCurrentProcessName(android.content.Context):java.lang.String");
    }

    private static void initProcessList(Context context) {
        int i = 0;
        try {
            if (sProcessList.size() <= 0) {
                ActivityInfo info;
                sProcessList.add(context.getPackageName());
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 11);
                if (packageInfo.receivers != null) {
                    for (ActivityInfo info2 : packageInfo.receivers) {
                        if (!sProcessList.contains(info2.processName)) {
                            sProcessList.add(info2.processName);
                        }
                    }
                }
                if (packageInfo.providers != null) {
                    for (ProviderInfo info3 : packageInfo.providers) {
                        if (!(sProcessList.contains(info3.processName) || info3.processName == null || info3.authority == null || info3.authority.indexOf(ApkManager.STUB_AUTHORITY_NAME) >= 0)) {
                            sProcessList.add(info3.processName);
                        }
                    }
                }
                if (packageInfo.services != null) {
                    for (ServiceInfo info4 : packageInfo.services) {
                        if (!(sProcessList.contains(info4.processName) || info4.processName == null || info4.name == null || info4.name.indexOf(ServiceStub.class.getSimpleName()) >= 0)) {
                            sProcessList.add(info4.processName);
                        }
                    }
                }
                if (packageInfo.activities != null) {
                    ActivityInfo[] activityInfoArr = packageInfo.activities;
                    int length = activityInfoArr.length;
                    while (i < length) {
                        info2 = activityInfoArr[i];
                        if (!(sProcessList.contains(info2.processName) || info2.processName == null || info2.name == null || info2.name.indexOf(ActivityStub.class.getSimpleName()) >= 0)) {
                            sProcessList.add(info2.processName);
                        }
                        i++;
                    }
                }
            }
        } catch (NameNotFoundException e) {
            JLog.logApk("初始化进程列表出错 e=" + e.getMessage());
        }
    }

    public static final boolean isPluginProcess(Context context) {
        initProcessList(context);
        String currentProcessName = getCurrentProcessName(context);
        return (TextUtils.equals(currentProcessName, context.getPackageName()) || sProcessList.contains(currentProcessName)) ? false : true;
    }

    public static ClassLoader getPluginClassLoader(String pkg) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        JLog.logApk("获取插件classLoader pkg=" + pkg);
        ClassLoader classLoader = (ClassLoader) sPluginClassLoaderCache.get(pkg);
        JLog.logApk("获取插件classLoader 缓存classLoader=" + classLoader);
        if (classLoader == null) {
            Application app = getPluginContext(pkg);
            JLog.logApk("获取插件classLoader app=" + app);
            if (app != null) {
                JLog.logApk("获取插件classLoader app.getPackageName()=" + app.getPackageName() + ",app.getClassLoader()=" + app.getClassLoader());
                sPluginClassLoaderCache.put(app.getPackageName(), app.getClassLoader());
            }
        }
        return (ClassLoader) sPluginClassLoaderCache.get(pkg);
    }

    public static void preLoadApk(Context hostContext, ComponentInfo pluginInfo) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NameNotFoundException, ClassNotFoundException {
        if (pluginInfo != null || hostContext != null) {
            if (pluginInfo == null || getPluginContext(pluginInfo.packageName) == null) {
                boolean found = false;
                synchronized (sPluginLoadedApkCache) {
                    Object object = ActivityThreadCompat.currentActivityThread();
                    if (object != null) {
                        Object containsKeyObj = MethodUtils.invokeMethod(FieldUtils.readField(object, "mPackages"), "containsKey", pluginInfo.packageName);
                        if ((containsKeyObj instanceof Boolean) && !((Boolean) containsKeyObj).booleanValue()) {
                            Object loadedApk;
                            if (VERSION.SDK_INT >= 11) {
                                loadedApk = MethodUtils.invokeMethod(object, "getPackageInfoNoCheck", pluginInfo.applicationInfo, CompatibilityInfoCompat.DEFAULT_COMPATIBILITY_INFO());
                            } else {
                                loadedApk = MethodUtils.invokeMethod(object, "getPackageInfoNoCheck", pluginInfo.applicationInfo);
                            }
                            sPluginLoadedApkCache.put(pluginInfo.packageName, loadedApk);
                            String optimizedDirectory = PluginDirHelper.getPluginDalvikCacheDir(hostContext, pluginInfo.packageName);
                            String libraryPath = PluginDirHelper.getPluginNativeLibraryDir(hostContext, pluginInfo.packageName);
                            String apk = pluginInfo.applicationInfo.publicSourceDir;
                            if (TextUtils.isEmpty(apk)) {
                                pluginInfo.applicationInfo.publicSourceDir = PluginDirHelper.getPluginFilePath(hostContext, pluginInfo.packageName);
                                apk = pluginInfo.applicationInfo.publicSourceDir;
                            }
                            if (apk != null) {
                                ClassLoader classloader = new PluginClassLoader(apk, optimizedDirectory, libraryPath, ClassLoader.getSystemClassLoader());
                                synchronized (loadedApk) {
                                    FieldUtils.writeDeclaredField(loadedApk, "mClassLoader", classloader);
                                }
                                sPluginClassLoaderCache.put(pluginInfo.packageName, classloader);
                                Thread.currentThread().setContextClassLoader(classloader);
                                found = true;
                            }
                            ProcessCompat.setArgV0(pluginInfo.processName);
                        }
                    }
                }
                if (found) {
                    preMakeApplication(hostContext, pluginInfo);
                }
            }
        }
    }

    private static void preMakeApplication(Context hostContext, ComponentInfo pluginInfo) {
        try {
            Object loadedApk = sPluginLoadedApkCache.get(pluginInfo.packageName);
            if (loadedApk != null && FieldUtils.readField(loadedApk, "mApplication") == null) {
                if (Looper.getMainLooper() != Looper.myLooper()) {
                    Object lock = new Object();
                    mExec.set(false);
                    mHandle.post(new 1(loadedApk, pluginInfo, lock));
                    if (!mExec.get()) {
                        synchronized (lock) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                        return;
                    }
                    return;
                }
                MethodUtils.invokeMethod(loadedApk, "makeApplication", Boolean.valueOf(false), ActivityThreadCompat.getInstrumentation());
            }
        } catch (Exception e2) {
            JLog.logApk("插件preMakeApplication 失败 e=" + e2.getMessage());
        }
    }

    public static void registerStaticReceiver(Context context, ApplicationInfo pluginApplicationInfo, ClassLoader cl) throws Exception {
        List<ActivityInfo> infos = ApkManager.getInstance().getReceivers(pluginApplicationInfo.packageName, 0);
        if (infos != null && infos.size() > 0) {
            CharSequence myPname = null;
            try {
                myPname = ApkManager.getInstance().getProcessNameByPid(Process.myPid());
            } catch (Exception e) {
            }
            for (ActivityInfo info : infos) {
                if (TextUtils.equals(info.processName, myPname)) {
                    try {
                        for (IntentFilter filter : ApkManager.getInstance().getReceiverIntentFilter(info)) {
                            context.registerReceiver((BroadcastReceiver) cl.loadClass(info.name).newInstance(), filter);
                        }
                    } catch (Exception e2) {
                        JLog.logApk("注册静态Receiver失败 Receiver名=" + info.name + ",e=" + e2.getMessage());
                    }
                }
            }
        }
    }

    public static void setHookEnable(boolean enable) {
        HookFactory.getInstance().setHookEnable(enable);
    }

    public static void setHookEnable(boolean enable, boolean reinstallHook) {
        HookFactory.getInstance().setHookEnable(enable, reinstallHook);
    }

    public static void installHook(Context hostContext) throws Throwable {
        HookFactory.getInstance().installHook(hostContext, null);
    }

    public static Application getPluginContext(String packageName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        JLog.logApk("获取插件application packageName=" + packageName);
        if (!sApplicationsCache.containsKey(packageName)) {
            Object at = ActivityThreadCompat.currentActivityThread();
            JLog.logApk("获取插件application 当前ActivityThread=" + at);
            List mAllApplications = FieldUtils.readField(at, "mAllApplications");
            if (mAllApplications instanceof List) {
                for (Application o : mAllApplications) {
                    if (o instanceof Application) {
                        Application app = o;
                        JLog.logApk("获取插件application Application=" + app);
                        if (!sApplicationsCache.containsKey(app.getPackageName())) {
                            sApplicationsCache.put(app.getPackageName(), app);
                        }
                    }
                }
            }
        }
        return (Application) sApplicationsCache.get(packageName);
    }

    private static Context getBaseContext(Context c) {
        if (c instanceof ContextWrapper) {
            return ((ContextWrapper) c).getBaseContext();
        }
        return c;
    }

    private static void fakeSystemServiceInner(Context hostContext, Context targetContext) {
        try {
            Object baseContext = getBaseContext(targetContext);
            if (!mFakedContext.containsValue(baseContext)) {
                Object cr;
                Object crctx;
                if (mServiceCache != null) {
                    FieldUtils.writeField(baseContext, "mServiceCache", mServiceCache);
                    cr = baseContext.getContentResolver();
                    if (cr != null) {
                        crctx = FieldUtils.readField(cr, "mContext");
                        if (crctx != null) {
                            FieldUtils.writeField(crctx, "mServiceCache", mServiceCache);
                        }
                    }
                    if (!mFakedContext.containsValue(baseContext)) {
                        mFakedContext.put(Integer.valueOf(baseContext.hashCode()), baseContext);
                        return;
                    }
                    return;
                }
                Map<?, ?> sSYSTEM_SERVICE_MAP;
                Object originContext;
                Object mServiceCache;
                Object serviceFetcher;
                Map<?, ?> SYSTEM_SERVICE_MAP = null;
                try {
                    SYSTEM_SERVICE_MAP = FieldUtils.readStaticField(baseContext.getClass(), "SYSTEM_SERVICE_MAP");
                } catch (Exception e) {
                    JLog.eApk("读取系统Service map失败 e=" + e);
                }
                if (SYSTEM_SERVICE_MAP == null) {
                    try {
                        sSYSTEM_SERVICE_MAP = FieldUtils.readStaticField(Class.forName("android.app.SystemServiceRegistry"), "SYSTEM_SERVICE_FETCHERS");
                    } catch (InvocationTargetException e2) {
                        JLog.eApk("读取系统Service map失败 e=" + e2);
                    }
                    if (sSYSTEM_SERVICE_MAP != null) {
                        if (sSYSTEM_SERVICE_MAP instanceof Map) {
                            sSYSTEM_SERVICE_MAP = sSYSTEM_SERVICE_MAP;
                            originContext = getBaseContext(hostContext);
                            mServiceCache = FieldUtils.readField(originContext, "mServiceCache");
                            if (mServiceCache instanceof List) {
                                ((List) mServiceCache).clear();
                            }
                            for (Object key : sSYSTEM_SERVICE_MAP.keySet()) {
                                if (!sSkipService.contains(key)) {
                                    serviceFetcher = sSYSTEM_SERVICE_MAP.get(key);
                                    try {
                                        serviceFetcher.getClass().getMethod("getService", new Class[]{baseContext.getClass()}).invoke(serviceFetcher, new Object[]{originContext});
                                    } catch (InvocationTargetException e22) {
                                        if (e22.getCause() != null) {
                                            JLog.eApk("Fake插件Service失败 e=" + e22);
                                        } else {
                                            JLog.eApk("Fake插件Service失败 e=" + e22);
                                        }
                                    } catch (Exception e3) {
                                        JLog.eApk("Fake插件Service失败 e=" + e3);
                                    }
                                }
                            }
                            mServiceCache = FieldUtils.readField(originContext, "mServiceCache");
                            FieldUtils.writeField(baseContext, "mServiceCache", mServiceCache);
                            cr = baseContext.getContentResolver();
                            if (cr != null) {
                                crctx = FieldUtils.readField(cr, "mContext");
                                if (crctx != null) {
                                    FieldUtils.writeField(crctx, "mServiceCache", mServiceCache);
                                }
                            }
                        }
                    }
                    if (!mFakedContext.containsValue(baseContext)) {
                        mFakedContext.put(Integer.valueOf(baseContext.hashCode()), baseContext);
                    }
                }
                sSYSTEM_SERVICE_MAP = SYSTEM_SERVICE_MAP;
                if (sSYSTEM_SERVICE_MAP != null) {
                    if (sSYSTEM_SERVICE_MAP instanceof Map) {
                        sSYSTEM_SERVICE_MAP = sSYSTEM_SERVICE_MAP;
                        originContext = getBaseContext(hostContext);
                        mServiceCache = FieldUtils.readField(originContext, "mServiceCache");
                        if (mServiceCache instanceof List) {
                            ((List) mServiceCache).clear();
                        }
                        for (Object key2 : sSYSTEM_SERVICE_MAP.keySet()) {
                            if (!sSkipService.contains(key2)) {
                                serviceFetcher = sSYSTEM_SERVICE_MAP.get(key2);
                                serviceFetcher.getClass().getMethod("getService", new Class[]{baseContext.getClass()}).invoke(serviceFetcher, new Object[]{originContext});
                            }
                        }
                        mServiceCache = FieldUtils.readField(originContext, "mServiceCache");
                        FieldUtils.writeField(baseContext, "mServiceCache", mServiceCache);
                        cr = baseContext.getContentResolver();
                        if (cr != null) {
                            crctx = FieldUtils.readField(cr, "mContext");
                            if (crctx != null) {
                                FieldUtils.writeField(crctx, "mServiceCache", mServiceCache);
                            }
                        }
                    }
                }
                if (!mFakedContext.containsValue(baseContext)) {
                    mFakedContext.put(Integer.valueOf(baseContext.hashCode()), baseContext);
                }
            }
        } catch (Exception e32) {
            JLog.eApk("fakeSystemServiceInner出错 e=" + e32.getMessage());
        }
    }

    public static void fakeSystemService(Context hostContext, Context targetContext) {
        if (VERSION.SDK_INT >= 15 && !TextUtils.equals(hostContext.getPackageName(), targetContext.getPackageName())) {
            long b = System.currentTimeMillis();
            fakeSystemServiceInner(hostContext, targetContext);
            JLog.logApk("插件=" + targetContext.getPackageName() + "fakeSystemServiceInner消耗时间=" + (System.currentTimeMillis() - b));
        }
    }
}
