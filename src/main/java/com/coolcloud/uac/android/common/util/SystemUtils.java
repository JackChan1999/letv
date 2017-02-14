package com.coolcloud.uac.android.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SystemUtils {
    private static final String TAG = "SystemUtils";

    public static String getDeviceId(Context context) {
        return Device.getDeviceId(context);
    }

    public static String getSystemDeviceId(Context context) {
        return Device.getNativeDeviceId(context);
    }

    public static boolean replaceSettingsDeviceId(Context context, String nativeDeviceId, String settingsDeviceId) {
        return Device.replaceSettingsDeviceId(context, nativeDeviceId, settingsDeviceId);
    }

    public static String getSettingsDeviceId(Context context) {
        return Device.getSettingsDeviceId(context);
    }

    public static String getMacId(Context context) {
        return Device.getMACAddress(context);
    }

    public static String getDeviceType() {
        return Device.getDeviceType();
    }

    public static String getDeviceModel() {
        return Device.getDeviceModel();
    }

    public static boolean isConnectNet(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    public static String getNetworkType(Context context) {
        String networkType = EnvironmentCompat.MEDIA_UNKNOWN;
        if (context == null) {
            LOG.w(TAG, "get network type failed(null context)");
            return networkType;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
        if (connMgr == null) {
            LOG.e(TAG, "get connectivity manager failed");
            return networkType;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return networkType;
        }
        String typeName = networkInfo.getTypeName();
        String subTypeName = networkInfo.getSubtypeName();
        String extraInfo = networkInfo.getExtraInfo();
        if (TextUtils.isEmpty(subTypeName)) {
            networkType = typeName + "/" + extraInfo;
        } else {
            networkType = typeName + "(" + subTypeName + ")/" + extraInfo;
        }
        return networkType;
    }

    public static SimInfo getDefaultSimInfo(Context context) {
        SimInfo simInfo = new SimInfo();
        simInfo.setDeviceId(Device.getDeviceId(context));
        simInfo.setDeviceModel(getDeviceModel());
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService("phone");
        if (telMgr != null) {
            simInfo.setIMSI(telMgr.getSubscriberId());
            simInfo.setCCID(telMgr.getSimSerialNumber());
            if (SimInfo.valid(simInfo)) {
                return simInfo;
            }
            LOG.e(TAG, "get telephony manager ok but sim info invalid");
            return null;
        }
        LOG.e(TAG, "get telephony manager failed while getting default sim info");
        return null;
    }

    public static String getExtCacheDirectory() {
        String uacCachepath = TextUtils.isEmpty(getExtCacheDirectory("uac")) ? getExternalImageCacheDir(ContextUtils.getContext()) : getExtCacheDirectory("uac");
        File photoFile = new File(uacCachepath);
        if (!photoFile.exists()) {
            photoFile.mkdir();
        }
        File nomediaFile = new File(uacCachepath + File.separator + ".nomedia");
        if (!nomediaFile.exists()) {
            nomediaFile.mkdir();
        }
        return uacCachepath;
    }

    public static String getExtCacheDirectory(String path) {
        String directory = null;
        List<String> mPathList = getExtSDCardPaths();
        if (mPathList == null || mPathList.size() < 1) {
            LOG.w(TAG, "[mPathList.size:" + mPathList.size() + "]  storage unavailable");
        } else {
            File extFile = new File((String) mPathList.get(0));
            if (extFile == null || !extFile.exists()) {
                LOG.w(TAG, "[path:" + path + "] external storage absent");
            } else if (isSpaceAvailable(extFile)) {
                directory = extFile.getAbsolutePath();
            } else {
                LOG.w(TAG, "[path:" + path + "] external storage not enough");
            }
        }
        if (TextUtils.isEmpty(directory) || TextUtils.isEmpty(path)) {
            return directory;
        }
        return directory + File.separator + path;
    }

    private static List<String> getExtSDCardPaths() {
        List<String> paths = new ArrayList();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        if (extFileStatus.equals("mounted") && extFile.exists() && extFile.isDirectory() && extFile.canWrite()) {
            paths.add(extFile.getAbsolutePath());
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                } else if (!((!line.contains("fat") && !line.contains("fuse") && !line.contains("storage")) || line.contains("secure") || line.contains("asec") || line.contains("firmware") || line.contains("shell") || line.contains("obb") || line.contains("legacy") || line.contains(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA))) {
                    String[] parts = line.split(" ");
                    if (1 < parts.length) {
                        String mountPath = parts[1];
                        if (!(!mountPath.contains("/") || mountPath.contains(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA) || mountPath.contains("Data"))) {
                            File mountRoot = new File(mountPath);
                            if (mountRoot.exists() && mountRoot.isDirectory() && mountRoot.canWrite() && !mountPath.equals(extFile.getAbsolutePath())) {
                                paths.add(mountPath);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    private static boolean isSpaceAvailable(File file) {
        try {
            return SDKUtils.getFreeSpaceSize(new StatFs(file.getAbsolutePath())).longValue() > 20971520;
        } catch (Throwable e) {
            LOG.e(TAG, "get free space size failed(Throwable)", e);
            return false;
        }
    }

    public static String getExternalImageCacheDir(Context context) {
        String coolcloudDir = System.getProperty("file.separator") + context.getPackageName() + System.getProperty("file.separator");
        String androidDataDir = System.getProperty("file.separator") + "Android" + System.getProperty("file.separator") + ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA;
        String externalCacheDir = null;
        String[] paths = getVolumePaths(context);
        if (paths != null && paths.length >= 2) {
            if (paths[1].contains("udisk")) {
                if (1 == isSdcardMounted(paths[0], context)) {
                    externalCacheDir = paths[0] + androidDataDir + coolcloudDir;
                }
            } else if (1 == isSdcardMounted(paths[1], context)) {
                externalCacheDir = paths[1] + androidDataDir + coolcloudDir;
            }
        }
        if (externalCacheDir == null) {
            if (Environment.getExternalStorageState().equals("mounted")) {
                externalCacheDir = Environment.getExternalStorageDirectory().getPath() + androidDataDir + coolcloudDir;
            } else {
                File file = context.getFilesDir();
                String udiskDir = file.getAbsolutePath();
                if (VERSION.SDK_INT > 8) {
                    if (file != null && file.exists() && file.getTotalSpace() > 0) {
                        externalCacheDir = udiskDir + androidDataDir + coolcloudDir;
                    }
                } else if (file != null && file.isDirectory()) {
                    externalCacheDir = udiskDir + androidDataDir + coolcloudDir;
                }
            }
        }
        if (externalCacheDir == null) {
            return "";
        }
        new File(externalCacheDir).mkdirs();
        return externalCacheDir;
    }

    private static String[] getVolumePaths(Context context) {
        String[] volumes = null;
        StorageManager managerStorage = (StorageManager) context.getSystemService("storage");
        if (managerStorage == null) {
            return volumes;
        }
        try {
            return (String[]) managerStorage.getClass().getMethod("getVolumePaths", new Class[0]).invoke(managerStorage, new Object[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return volumes;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return volumes;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return volumes;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return volumes;
        }
    }

    private static int isSdcardMounted(String path, Context context) {
        StorageManager managerStorage = (StorageManager) context.getSystemService("storage");
        if (managerStorage == null) {
            return -1;
        }
        try {
            Method method = managerStorage.getClass().getDeclaredMethod("getVolumeState", new Class[]{String.class});
            method.setAccessible(true);
            if ("mounted".equalsIgnoreCase((String) method.invoke(managerStorage, new Object[]{path}))) {
                return 1;
            }
            return 0;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return -1;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return -1;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return -1;
        }
    }

    public static String getSnId(Context context) {
        String sn = "";
        try {
            sn = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
        } catch (Throwable e) {
            LOG.e(TAG, "getSnId error : " + e);
        }
        return sn;
    }

    public static String getMeidId(Context context) {
        return Device.getMeidId(context);
    }

    public static String getFreeCallDeviceId(Context context) {
        return Device.getFreeCallDeviceId(context);
    }
}
