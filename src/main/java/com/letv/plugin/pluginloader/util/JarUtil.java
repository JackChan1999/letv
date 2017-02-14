package com.letv.plugin.pluginloader.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.utils.PluginDirHelper;
import com.letv.plugin.pluginloader.apk.utils.PluginHelper;
import com.letv.plugin.pluginloader.application.JarApplication;
import com.letv.plugin.pluginloader.common.Constant;
import com.letv.plugin.pluginloader.service.DownLoadService;
import com.letv.plugin.pluginloader.util.JarXmlParser.JarEntity;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JarUtil {
    public static final int TYPE_ALL = -1;
    public static final int TYPE_APK = 1;
    public static final int TYPE_JAR = 0;
    public static final int TYPE_SO = 2;
    public static boolean sHasApplyPermissionsSuccess = false;

    public interface OnPluginInitedListener {
        void onInited();
    }

    public static void initPlugin(Context context, OnPluginInitedListener listener) {
        if (isMainProcess(context)) {
            if (JarApplication.getInstance() == null) {
                JarApplication.setInstance(context.getApplicationContext());
            }
            updatePlugin(context, 0, false);
            PluginHelper.getInstance().initApk(context, new 1(context, listener));
        }
    }

    public static synchronized ArrayList<String> updatePlugin(Context context, int type, boolean needDownload) {
        ArrayList<String> error_jar;
        synchronized (JarUtil.class) {
            error_jar = new ArrayList();
            ArrayList<JarEntity> jars = new JarXmlParser(context).parseJars();
            if (jars == null) {
                error_jar = null;
            } else {
                if (needDownload) {
                    if (sHasApplyPermissionsSuccess) {
                        context.startService(new Intent(context, DownLoadService.class));
                    }
                }
                SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_SP, 0);
                Iterator it = jars.iterator();
                while (it.hasNext()) {
                    JarEntity jarEntity = (JarEntity) it.next();
                    int curVersion = sp.getInt(jarEntity.getPackagename(), 0);
                    if (jarEntity.getStatus() == 1 && jarEntity.getVersion() > curVersion) {
                        if (JarConstant.LETV_ADS_PACKAGENAME.equalsIgnoreCase(jarEntity.getPackagename())) {
                            deleteObsoleteAdsPlugin(context);
                        }
                        String path = null;
                        if (type == -1) {
                            try {
                                path = copyJar(context, jarEntity);
                            } catch (Exception e) {
                                error_jar.add(jarEntity.getPackagename());
                            }
                        } else if (jarEntity.getType() == type) {
                            path = copyJar(context, jarEntity);
                        }
                        if (path == null) {
                            error_jar.add(jarEntity.getPackagename());
                        } else {
                            sp.edit().putInt(jarEntity.getPackagename(), jarEntity.getVersion()).commit();
                        }
                    }
                }
                updateAdsPlugin(context);
            }
        }
        return error_jar;
    }

    public static synchronized void installPlugins(Context context) {
        synchronized (JarUtil.class) {
            if (ApkManager.getInstance().isConnected()) {
                JLog.i("plugin", "JarUtil.updatePlugin");
                updatePlugin(context, 1, true);
                ArrayList<JarEntity> jars = new JarXmlParser(context).parseJars();
                if (jars != null) {
                    Iterator it = jars.iterator();
                    while (it.hasNext()) {
                        JarEntity entity = (JarEntity) it.next();
                        if (entity.getType() == 1) {
                            ApkManager.getInstance().installPackage(PluginDirHelper.getPluginFilePath(context, entity.getPackagename()), 0);
                        }
                    }
                }
            }
        }
    }

    public static String copyJar(Context context, JarEntity jarEntity) {
        Throwable th;
        String outPath = null;
        if (jarEntity.getType() == 1) {
            outPath = PluginDirHelper.getPluginFilePath(context, jarEntity.getPackagename());
        } else if (jarEntity.getType() == 0) {
            outPath = context.getDir(Constant.JAR_IN_FOLDER_NAME, 0) + "/" + jarEntity.getName();
        }
        if (TextUtils.isEmpty(outPath)) {
            return null;
        }
        Log.i("plugin", "复制插件 " + jarEntity.getName() + " 到安装目录: " + outPath);
        File outputFile = new File(outPath);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            InputStream in;
            if (jarEntity.getName().equals(Constant.PLUGIN_LITE)) {
                String filePath = getPluginDownloadPath(context) + "/" + jarEntity.getName();
                Log.i("plugin", "LiteApp filePath =" + filePath);
                in = new FileInputStream(filePath);
            } else {
                in = context.getAssets().open("jars/" + jarEntity.getName());
            }
            BufferedInputStream bis2 = new BufferedInputStream(in);
            try {
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(outputFile));
                try {
                    Log.i("plugin", "复制到文件 = " + outputFile);
                    byte[] buf = new byte[8192];
                    while (true) {
                        int len = bis2.read(buf, 0, 8192);
                        if (len <= 0) {
                            break;
                        }
                        bos2.write(buf, 0, len);
                    }
                    if (bos2 != null) {
                        try {
                            bos2.close();
                        } catch (IOException e) {
                        }
                    }
                    if (bis2 != null) {
                        try {
                            bis2.close();
                        } catch (IOException e2) {
                        }
                    }
                    return outputFile.getAbsolutePath();
                } catch (Exception e3) {
                    bos = bos2;
                    bis = bis2;
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e4) {
                        }
                    }
                    if (bis != null) {
                        return null;
                    }
                    try {
                        bis.close();
                        return null;
                    } catch (IOException e5) {
                        return null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bos = bos2;
                    bis = bis2;
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e6) {
                        }
                    }
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e7) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e8) {
                bis = bis2;
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    return null;
                }
                bis.close();
                return null;
            } catch (Throwable th3) {
                th = th3;
                bis = bis2;
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                throw th;
            }
        } catch (Exception e9) {
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                return null;
            }
            bis.close();
            return null;
        } catch (Throwable th4) {
            th = th4;
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
            throw th;
        }
    }

    public static boolean isMainProcess(Context context) {
        return TextUtils.equals(getProcessName(context, Process.myPid()), "com.letv.android.client");
    }

    public static String getProcessName(Context cxt, int pid) {
        List<RunningAppProcessInfo> runningApps = ((ActivityManager) cxt.getSystemService("activity")).getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static long getAvailableStorage() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().toString());
            return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
        } catch (RuntimeException e) {
            return 0;
        }
    }

    public static String getJarInFolderName(Context context, String jarName) {
        String path = "";
        if (Constant.PLUGIN_LITE.equals(jarName)) {
            File dexInternalStoragePath = new File(getPluginDownloadPath(context), jarName);
            path = dexInternalStoragePath.exists() ? dexInternalStoragePath.getAbsolutePath() : "";
        }
        if (!TextUtils.isEmpty(path)) {
            return path;
        }
        dexInternalStoragePath = new File(context.getDir(Constant.JAR_IN_FOLDER_NAME, 0), jarName);
        return dexInternalStoragePath.exists() ? dexInternalStoragePath.getAbsolutePath() : "";
    }

    public static String getJarOutFolderName(Context context) {
        File optimizedDexOutputPath = context.getDir(Constant.JAR_OUT_FOLDER_NAME, 0);
        return optimizedDexOutputPath.exists() ? optimizedDexOutputPath.getAbsolutePath() : "";
    }

    public static String getPluginDownloadPath(Context context) {
        File dir = new File(getInnerSDCardDir().toString() + "/Letv");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    public static File getInnerSDCardDir() {
        return Environment.getExternalStorageDirectory();
    }

    private static void deleteObsoleteAdsPlugin(Context context) {
        File updateFile = new File(context.getDir(Constant.JAR_IN_FOLDER_NAME, 0), Constant.ADS_APK_UPDATE_NAME);
        File soFile = new File(context.getDir(Constant.ADS_SO_FILE_UPDATE_FOLDER, 0), Constant.ADS_SO_FILE_NAME);
        if (updateFile.exists()) {
            JLog.log("plugin", "删除已废弃的广告插件更新文件..." + updateFile.getAbsolutePath());
            updateFile.delete();
        }
        if (soFile.exists()) {
            JLog.log("plugin", "删除已废弃的广告插件so文件..." + soFile);
            soFile.delete();
        }
    }

    private static void updateAdsPlugin(Context context) {
        File updateFile = new File(context.getDir(Constant.JAR_IN_FOLDER_NAME, 0), Constant.ADS_APK_UPDATE_NAME);
        boolean updateFileExist = updateFile.exists();
        JLog.log("chengjian", "updateAdsPlugin updateFileExist=" + updateFileExist);
        if (updateFileExist) {
            PackageInfo packageInfo = getPackageInfo(context, updateFile.getPath());
            JLog.log("chengjian", "updateAdsPlugin packageInfo=" + packageInfo);
            if (packageInfo != null) {
                File apkFile = new File(context.getDir(Constant.JAR_IN_FOLDER_NAME, 0), "Letv_Ads.apk");
                SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_SP, 0);
                int apkVersionCode = packageInfo.versionCode;
                int curVersion = sp.getInt(JarConstant.LETV_ADS_PACKAGENAME, 0);
                JLog.log("chengjian", "updateAdsPlugin apkVerscode=" + apkVersionCode + "   " + "curVersion" + curVersion + "  packageInfo=" + packageInfo);
                if (curVersion < apkVersionCode) {
                    move(updateFile.getPath(), apkFile.getPath());
                    sp.edit().putInt(JarConstant.LETV_ADS_PACKAGENAME, apkVersionCode).commit();
                    return;
                }
                deleteObsoleteAdsPlugin(context);
            }
        }
    }

    private static void move(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while (true) {
                    int byteRead = inStream.read(buffer);
                    if (byteRead != -1) {
                        fs.write(buffer, 0, byteRead);
                    } else {
                        inStream.close();
                        oldFile.delete();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PackageInfo getPackageInfo(Context context, String apkFilepath) {
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = context.getPackageManager().getPackageArchiveInfo(apkFilepath, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pkgInfo;
    }
}
