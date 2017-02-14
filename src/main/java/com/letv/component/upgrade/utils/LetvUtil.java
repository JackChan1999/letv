package com.letv.component.upgrade.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.letv.component.upgrade.core.service.DownLoadFunction;
import com.letv.component.upgrade.core.service.RemoteDownloadTaskService;
import com.letv.component.upgrade.core.upgrade.UpgradeCallBack;
import com.letv.component.upgrade.core.upgrade.UpgradeSilenceCallBack;
import com.letv.component.utils.ApnChecker;
import com.letv.component.utils.DebugLog;
import com.letv.download.manager.StoreManager;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LetvUtil {
    public static final int NETTYPE_2G = 2;
    public static final int NETTYPE_3G = 3;
    public static final int NETTYPE_NO = 0;
    public static final int NETTYPE_WIFI = 1;
    private static final String TAG = "LetvUtil";
    private static String deviceID;
    private static Executor executor;
    static Context mContext;
    static File mFile;
    String isHd;
    String mmsid;
    String pcode;
    String version;

    public LetvUtil(String mmsid, String isHd, String pcode, String version, Context mContext) {
        this.mmsid = mmsid;
        this.isHd = isHd;
        this.pcode = pcode;
        this.version = version;
        mContext = mContext;
    }

    public LetvUtil(Context mContext) {
        mContext = mContext;
    }

    public static URLConnection initConnection(URL url, Context mContext) throws IOException {
        Proxy mProxy = ApnChecker.getProxy(mContext);
        if (mProxy != null) {
            return (HttpURLConnection) url.openConnection(mProxy);
        }
        return (HttpURLConnection) url.openConnection();
    }

    public static boolean isSdcardAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static void deleteFile(File file) {
        new Thread(new 1(file)).start();
    }

    public static long getSdcardAvailableSpace(Context context) {
        if (!isSdcardAvailable()) {
            return -1;
        }
        StatFs statFs = new StatFs(getDownloadDir(context).getAbsolutePath());
        return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
    }

    public static File getDownloadDir(Context context) {
        File downloadDir = new File(DownLoadFunction.getInstance(context).fileDir);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }

    public static boolean isDownloadConditionOk(Context context) {
        if (getAvailableNetWorkInfo(context) == null) {
            Toast.makeText(context, "无网络", 0).show();
            return false;
        } else if (StoreManager.DEFAULT_SDCARD_SIZE < 0) {
            return false;
        } else {
            if (getSdcardAvailableSpace(context) >= StoreManager.DEFAULT_SDCARD_SIZE) {
                return true;
            }
            Toast.makeText(context, ResourceUtil.getStringId(context, "upgrade_toast_sdcard_lower"), 1).show();
            return false;
        }
    }

    public static NetworkInfo getAvailableNetWorkInfo(Context context) {
        try {
            NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
                return null;
            }
            return activeNetInfo;
        } catch (Exception e) {
            e.printStackTrace();
            DebugLog.log(TAG, e.getMessage());
            return null;
        }
    }

    public static boolean isWifi(Context context) {
        NetworkInfo networkInfo = getAvailableNetWorkInfo(context);
        if (networkInfo == null || networkInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static boolean isConnect(Context mContext) {
        NetworkInfo ni = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static Executor getExecutor() {
        Executor executor;
        synchronized (LetvUtil.class) {
            if (executor == null) {
                executor = Executors.newSingleThreadExecutor();
            }
            executor = executor;
        }
        return executor;
    }

    public static String getDataEmpty(String data) {
        if (data == null || data.length() <= 0) {
            return "";
        }
        return data.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    public static String getOSVersionName() {
        return VERSION.RELEASE;
    }

    public static String getNetType(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityMgr == null) {
            return null;
        }
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }
        if (1 == networkInfo.getType()) {
            return "wifi";
        }
        if (networkInfo.getType() == 0) {
            return "3G";
        }
        return "wifi";
    }

    public static int getNetWorkType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return 0;
        }
        if (1 == networkInfo.getType()) {
            return 1;
        }
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 1:
            case 2:
            case 4:
                return 2;
            default:
                return 3;
        }
    }

    private static String generate_DeviceId(Context context) {
        return MD5Helper(getIMEI(context) + getDeviceName() + getBrandName() + getMacAddress(context));
    }

    public static String generateDeviceId(Context context) {
        return MD5Helper(getIMEI(context) + getIMSI(context) + getDeviceName() + getBrandName() + getMacAddress(context));
    }

    public static String getIMEI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (deviceId == null || deviceId.length() <= 0) {
                return "";
            }
            return deviceId.replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIMSI(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            if (subscriberId == null || subscriberId.length() <= 0) {
                return generate_DeviceId(context);
            }
            subscriberId.replace(" ", "");
            if (TextUtils.isEmpty(subscriberId)) {
                return generate_DeviceId(context);
            }
            return subscriberId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMacAddress(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null || macAddress.length() <= 0) {
                return "";
            }
            return macAddress;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String MD5Helper(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(byteArray[i] & 255).length() == 1) {
                    sb.append("0").append(Integer.toHexString(byteArray[i] & 255));
                } else {
                    sb.append(Integer.toHexString(byteArray[i] & 255));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("no device Id");
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            throw new RuntimeException("no device Id");
        }
    }

    public static String getDeviceName() {
        String model = Build.MODEL;
        if (model == null || model.length() <= 0) {
            return "";
        }
        return model;
    }

    public static String getBrandName() {
        String brand = Build.BRAND;
        if (brand == null || brand.length() <= 0) {
            return "";
        }
        return brand;
    }

    public static String getClientVersionName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getResolution(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels + "*" + dm.heightPixels;
    }

    public static String getDeviceID(Context context) {
        if (TextUtils.isEmpty(deviceID)) {
            deviceID = generateDeviceId(context);
        }
        return deviceID;
    }

    public static void installApk(Context context, String filePath, int isSilentInstall, UpgradeCallBack callBack) {
        installApk(context, filePath, isSilentInstall, callBack, null);
    }

    public static void installApk(Context context, String filePath, int isSilentInstall, UpgradeCallBack callBack, UpgradeSilenceCallBack silenceCallBack) {
        if (!false) {
            installApkBySystem(context, filePath);
        } else if (!RootUtil.isRoot()) {
            installApkBySystem(context, filePath);
        } else if (callBack != null) {
        } else {
            if (RootUtil.hasRoot(context)) {
                if (silenceCallBack != null) {
                    silenceCallBack.updateNotification();
                }
                installApkBySystem(context, filePath);
                return;
            }
            RootUtil.preparezlsu(context, new 2(context, silenceCallBack, filePath));
        }
    }

    public static void installApkBySystem(Context context, String filePath) {
        try {
            new ProcessBuilder(new String[]{"chmod", "777", filePath}).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static boolean checkPackageAndVersion(Context context, String packageName, String version) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, 8192);
            if (version.trim().contains(getClientVersionName(context))) {
                return false;
            }
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean checkAppIsInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, 8192);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static File getFile(String dir, String name) {
        return new File(new StringBuilder(String.valueOf(dir)).append(File.separator).append(name).toString());
    }

    public static void deleteDownLoadApkFile(File file, boolean hostFileIsIntegrated, File hostFile) {
        if (file != null) {
            try {
                if (!file.exists()) {
                    return;
                }
                if (file.isDirectory()) {
                    File[] file2s = file.listFiles();
                    if (file2s != null) {
                        for (File file3 : file2s) {
                            deleteDownLoadApkFile(file3, hostFileIsIntegrated, hostFile);
                        }
                    }
                } else if (!RemoteDownloadTaskService.appName.equals(file.getName())) {
                    Log.i("RemoteDownloadTaskService", "deleteDownLoadApkFile file=" + file.getName());
                    file.delete();
                }
            } catch (Exception e) {
                DebugLog.log(TAG, "delete file error");
                Log.i("UpgradeManager", "downloadLocation=" + e.getMessage());
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            LayoutParams params = listView.getLayoutParams();
            params.height = (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalHeight;
            listView.setLayoutParams(params);
        }
    }

    public static boolean checkHasInstallByFileName(Context context, String fileName) {
        if (fileName.contains(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
            return checkAppIsInstalled(context, fileName.substring(0, fileName.indexOf(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)));
        }
        return false;
    }

    public static boolean checkFileIsIntegrated(String servFileMd5, File localFile) {
        Log.i("checkFileIsIntegrated", localFile.getName());
        Log.i("checkFileIsIntegrated", "servFileMd5=" + servFileMd5);
        boolean fileIsIntegrated = false;
        String localFileMd5 = MD5Util.fileMd5(localFile);
        Log.i("checkFileIsIntegrated", "localFileMd5=" + localFileMd5);
        if (localFileMd5 != null && localFileMd5.equalsIgnoreCase(servFileMd5)) {
            fileIsIntegrated = true;
        }
        Log.i("checkFileIsIntegrated", "fileIsIntegrated=" + fileIsIntegrated);
        return fileIsIntegrated;
    }

    public static void changeFileMode(String filePath) {
        try {
            new ProcessBuilder(new String[]{"chmod", "777", filePath}).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLocalIpAddress(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo wifi = connMgr.getNetworkInfo(1);
        NetworkInfo mobile = connMgr.getNetworkInfo(0);
        if (wifi.isAvailable()) {
            WifiManager wifimanage = (WifiManager) context.getSystemService("wifi");
            wifimanage.isWifiEnabled();
            int i = wifimanage.getConnectionInfo().getIpAddress();
            return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
        }
        if (mobile.isAvailable()) {
            try {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                while (en.hasMoreElements()) {
                    Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                    while (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isRunningForeground(Context context) {
        String currentPackageName = ((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
        String appPackageName = context.getPackageName();
        if (TextUtils.isEmpty(currentPackageName) || !currentPackageName.equals(appPackageName)) {
            return false;
        }
        return true;
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String unixTimeToDate(long unixLong) {
        String date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(unixLong));
        } catch (Exception e) {
            System.out.println("String转换Date错误，请确认数据可以转换！");
        }
        return date;
    }
}
