package com.letv.plugin.pluginloader.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.utils.Utils;
import com.letv.plugin.pluginloader.application.JarApplication;
import com.letv.plugin.pluginloader.common.Constant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.util.JarUtil;
import com.letv.plugin.pluginloader.util.JarXmlParser;
import com.letv.plugin.pluginloader.util.JarXmlParser.JarEntity;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class DownLoadService extends Service {
    private boolean mIsUpdating = false;
    protected BroadcastReceiver mNetworkChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (!Utils.isNetworkAvailable(context)) {
                Log.i("plugin", "监听到网络状态变化 >>> 网络断开了");
                DownLoadService.this.mIsUpdating = false;
            }
        }
    };

    public int onStartCommand(Intent intent, int flags, int startId) {
        ArrayList<JarEntity> jars = getDownJars();
        if (!this.mIsUpdating) {
            Iterator it = jars.iterator();
            while (it.hasNext()) {
                final JarEntity entity = (JarEntity) it.next();
                if (entity != null && shouldUpdate(entity)) {
                    if (Utils.isWifiConnected(getApplication()) || isForceDownload(entity.getName())) {
                        Log.i("plugin", "WIFI连接，或者是Lite由第三方拉起，下载插件...");
                        new Thread(new Runnable() {
                            public void run() {
                                DownLoadService.this.mIsUpdating = true;
                                Log.i("plugin", "插件即将更新: " + entity.toString());
                                DownLoadService.this.updateJar(entity);
                            }
                        }).start();
                    }
                }
            }
            this.mIsUpdating = false;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate() {
        registerNetworkChangedReceiver();
        super.onCreate();
    }

    public void onDestroy() {
        unregisterNetworkChangedReceiver();
        super.onDestroy();
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean isForceDownload(String pluginName) {
        if (!TextUtils.isEmpty(pluginName) && pluginName.equals(Constant.PLUGIN_LITE) && ApkManager.getInstance().getLiteAppCallState()) {
            return true;
        }
        return false;
    }

    private ArrayList<JarEntity> getDownJars() {
        ArrayList<JarEntity> jars = new JarXmlParser(getApplication()).parseJars();
        ArrayList<JarEntity> djars = new ArrayList();
        if (jars != null) {
            Iterator it = jars.iterator();
            while (it.hasNext()) {
                JarEntity entity = (JarEntity) it.next();
                if (entity.getStatus() == 0) {
                    djars.add(entity);
                }
            }
        }
        return djars;
    }

    private boolean shouldUpdate(JarEntity entity) {
        boolean result = true;
        File filePath = new File(JarUtil.getPluginDownloadPath(getApplication()), entity.getName());
        Log.i("plugin", "shouldUpdate()  filePath =" + filePath);
        if (filePath.exists()) {
            filePath.delete();
        }
        int state = ApkManager.getInstance().getPluginInstallState(entity.getPackagename());
        if (state == 1) {
            result = false;
            if (entity.getPackagename().equals("com.letv.android.lite")) {
                int oldVersion = getSharedPreferences(Constant.SHARED_SP, 0).getInt(entity.getPackagename(), 0);
                int curVersion = entity.getVersion();
                Log.i("plugin", "Lite播放器，已安装版本 = " + oldVersion + "当前版本 = " + curVersion);
                if (curVersion > oldVersion) {
                    result = true;
                }
            }
        }
        Log.i("plugin", "shouldUpdate()  getPackagename = " + entity.getPackagename() + " 安装状态 = " + state);
        return result;
    }

    private String getDownloadUrlByPluginName(String name) {
        String url = "";
        if (TextUtils.isEmpty(name)) {
            return url;
        }
        Object obj = -1;
        switch (name.hashCode()) {
            case -1880579778:
                if (name.equals("LetvLeso.apk")) {
                    obj = null;
                    break;
                }
                break;
            case 1688828887:
                if (name.equals(Constant.PLUGIN_LITE)) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                return Constant.PLUGIN_URL_LESO;
            case 1:
                return Constant.PLUGIN_URL_LITE;
            default:
                return url;
        }
    }

    private void updateJar(JarEntity entity) {
        OutputStream dexWriter;
        Exception e;
        Throwable th;
        File dexInternalStoragePath = new File(JarUtil.getPluginDownloadPath(getApplication()), entity.getName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "temp");
        Log.i("plugin", "插件apk临时存储路径 =" + dexInternalStoragePath);
        BufferedInputStream bis = null;
        OutputStream dexWriter2 = null;
        if (dexInternalStoragePath.exists()) {
            dexInternalStoragePath.delete();
        }
        Log.i("plugin", "WIFI已连接，下载插件...");
        String url = getDownloadUrlByPluginName(entity.getName());
        Log.i("plugin", "updateJar...url = " + url);
        try {
            BufferedInputStream bis2 = new BufferedInputStream(((HttpURLConnection) new URL(url).openConnection()).getInputStream());
            try {
                dexWriter = new BufferedOutputStream(new FileOutputStream(dexInternalStoragePath));
            } catch (Exception e2) {
                e = e2;
                bis = bis2;
                try {
                    Log.i("plugin", "updateJar...Exception =" + e.getMessage());
                    if (dexInternalStoragePath.exists()) {
                        dexInternalStoragePath.delete();
                    }
                    if (dexWriter2 != null) {
                        try {
                            dexWriter2.close();
                        } catch (IOException e3) {
                            return;
                        }
                    }
                    if (bis == null) {
                        bis.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (dexWriter2 != null) {
                        try {
                            dexWriter2.close();
                        } catch (IOException e4) {
                            throw th;
                        }
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bis = bis2;
                if (dexWriter2 != null) {
                    dexWriter2.close();
                }
                if (bis != null) {
                    bis.close();
                }
                throw th;
            }
            try {
                byte[] buf = new byte[10240];
                int totalSize = 0;
                while (true) {
                    int len = bis2.read(buf, 0, 10240);
                    if (len <= 0) {
                        break;
                    }
                    totalSize += len;
                    dexWriter.write(buf, 0, len);
                }
                Log.i("plugin", dexInternalStoragePath + "共写入文件 " + (totalSize >> 20) + " M");
                dexWriter.flush();
                File dexInternalStoragePath_new = new File(JarUtil.getPluginDownloadPath(getApplication()), entity.getName());
                Log.i("plugin", " 从源目录: " + dexInternalStoragePath.getAbsolutePath());
                Log.i("plugin", " 复制到目标目录: " + dexInternalStoragePath_new.getAbsolutePath());
                copyFile(dexInternalStoragePath.getAbsolutePath(), dexInternalStoragePath_new.getAbsolutePath());
                initPluginConfig(entity);
                dexInternalStoragePath.delete();
                if (dexWriter != null) {
                    try {
                        dexWriter.close();
                    } catch (IOException e5) {
                        dexWriter2 = dexWriter;
                        bis = bis2;
                        return;
                    }
                }
                if (bis2 != null) {
                    bis2.close();
                }
                dexWriter2 = dexWriter;
                bis = bis2;
            } catch (Exception e6) {
                e = e6;
                dexWriter2 = dexWriter;
                bis = bis2;
                Log.i("plugin", "updateJar...Exception =" + e.getMessage());
                if (dexInternalStoragePath.exists()) {
                    dexInternalStoragePath.delete();
                }
                if (dexWriter2 != null) {
                    dexWriter2.close();
                }
                if (bis == null) {
                    bis.close();
                }
            } catch (Throwable th4) {
                th = th4;
                dexWriter2 = dexWriter;
                bis = bis2;
                if (dexWriter2 != null) {
                    dexWriter2.close();
                }
                if (bis != null) {
                    bis.close();
                }
                throw th;
            }
        } catch (Exception e7) {
            e = e7;
            Log.i("plugin", "updateJar...Exception =" + e.getMessage());
            if (dexInternalStoragePath.exists()) {
                dexInternalStoragePath.delete();
            }
            if (dexWriter2 != null) {
                dexWriter2.close();
            }
            if (bis == null) {
                bis.close();
            }
        }
    }

    private void initPluginConfig(JarEntity entity) {
        if ("LetvLeso.apk".equals(entity.getName())) {
            Class<?> cls = JarLoader.loadClass(JarApplication.getInstance().getApplicationContext(), entity.getName(), entity.getPackagename(), "utils.LesoInitData");
            if (cls != null) {
                Log.i("plugin", "Leso插件初始化成功");
                JarLoader.invokeStaticMethod(cls, "initData", new Class[]{Context.class}, new Object[]{JarApplication.getInstance().getApplicationContext()});
            }
        }
        if (Constant.PLUGIN_LITE.equals(entity.getName())) {
            Log.i("plugin", "是LiteApp插件，先复制到目录: ");
            JarUtil.copyJar(JarApplication.getInstance().getApplicationContext(), entity);
            File file = new File(JarUtil.getPluginDownloadPath(JarApplication.getInstance().getApplicationContext()), entity.getName());
            Log.i("plugin", "即将安装的apk路径 =" + file);
            ApkManager.getInstance().installPackage(file.getAbsolutePath(), 0);
        }
    }

    private void copyFile(String source, String target) {
        try {
            File oldFile = new File(source);
            if (oldFile.exists()) {
                InputStream is = new FileInputStream(source);
                FileOutputStream os = new FileOutputStream(target);
                byte[] buffer = new byte[8192];
                while (true) {
                    int byteread = is.read(buffer);
                    if (byteread != -1) {
                        os.write(buffer, 0, byteread);
                    } else {
                        is.close();
                        os.close();
                        oldFile.delete();
                        Log.i("plugin", "拷贝文件成功");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            Log.i("plugin", "拷贝文件异常 " + e.getMessage());
        }
    }

    private void registerNetworkChangedReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.mNetworkChangedReceiver, filter);
    }

    private void unregisterNetworkChangedReceiver() {
        unregisterReceiver(this.mNetworkChangedReceiver);
    }
}
