package com.letv.android.client.album.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DrmSoUrlBean;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.TimestampBean.FetchServerTimeListener;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.VolleyResult;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.DrmSoUrlParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.plugin.pluginloader.apk.compat.PackageManagerCompat;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.common.Constant;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class SimplePluginDownloadService extends Service {
    private static final String DOWNLOAD_PLUGIN = "download_plugin";
    private static final String DOWNLOAD_PLUGIN_PAGE = "download_plugin_page";
    public static final String FLODER_NAME = "libs";
    private static final int MAX_RETRY_COUNT = 2;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action) && TextUtils.equals("android.net.conn.CONNECTIVITY_CHANGE", action) && !NetworkUtils.isNetworkAvailable() && SimplePluginDownloadService.this.mIsDownloading) {
                SimplePluginDownloadService.this.stop();
            }
        }
    };
    private int mCurrentRetryCount = 0;
    private String mFilePath;
    private boolean mIsDownloading;
    private boolean mIsRunning;
    private String mPageId;

    public static class PluginInstalled {
        public boolean success;

        public PluginInstalled(boolean success) {
            this.success = success;
        }
    }

    public static void launchSimplePluginDownloadService(Context context, String pageId) {
        Intent intent = new Intent(context, SimplePluginDownloadService.class);
        intent.putExtra(DOWNLOAD_PLUGIN, true);
        intent.putExtra(DOWNLOAD_PLUGIN_PAGE, pageId);
        context.startService(intent);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean shouldRunProcess = false;
        if (this.mIsRunning) {
            return super.onStartCommand(intent, flags, startId);
        }
        if (intent == null || !intent.getBooleanExtra(DOWNLOAD_PLUGIN, false)) {
            return super.onStartCommand(intent, flags, startId);
        }
        this.mPageId = intent.getStringExtra(DOWNLOAD_PLUGIN_PAGE);
        prepareDownload();
        if (!(BaseApplication.getInstance().mHasLoadDrmSo && ApkManager.getInstance().getPluginInstallState(Constant.DRM_LIBWASABIJNI) == 1)) {
            shouldRunProcess = true;
        }
        if (shouldRunProcess) {
            registerNetChange();
            new Thread() {
                public void run() {
                    SimplePluginDownloadService.this.checkTimestamp();
                }
            }.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerNetChange() {
        unRegisterNetChange();
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(this.mBroadcastReceiver, filter);
        } catch (Exception e) {
        }
    }

    private void unRegisterNetChange() {
        if (this.mBroadcastReceiver != null) {
            try {
                unregisterReceiver(this.mBroadcastReceiver);
            } catch (Exception e) {
            }
        }
    }

    private void stop() {
        this.mCurrentRetryCount++;
        if (this.mCurrentRetryCount < 2) {
            checkTimestamp();
            return;
        }
        this.mIsRunning = false;
        stopSelf();
        RxBus.getInstance().send(new PluginInstalled(false));
        unRegisterNetChange();
        LogInfo.log("play_auto_test_DRM", "####PLAY_DRM####LOAD ERROR");
    }

    private String getFilePath() {
        if (!TextUtils.isEmpty(this.mFilePath)) {
            return this.mFilePath;
        }
        File folder = getDir(FLODER_NAME, 0);
        if (folder == null || !folder.exists()) {
            return null;
        }
        this.mFilePath = folder.getAbsolutePath() + File.separator + Constant.DRM_LIBWASABIJNI;
        return this.mFilePath;
    }

    private void prepareDownload() {
        String path = getFilePath();
        if (TextUtils.isEmpty(path)) {
            ApkManager.getInstance().setPluginInstallState(Constant.DRM_LIBWASABIJNI, PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR);
        } else if (!new File(path).exists()) {
            ApkManager.getInstance().setPluginInstallState(Constant.DRM_LIBWASABIJNI, PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR);
        }
    }

    private void checkTimestamp() {
        this.mIsRunning = true;
        if (TimestampBean.getTm().mHasRecodeServerTime) {
            startDownload();
        } else {
            TimestampBean.getTm().getServerTimestamp(new FetchServerTimeListener() {
                public void afterFetch() {
                    SimplePluginDownloadService.this.startDownload();
                }
            });
        }
    }

    private void startDownload() {
        LogInfo.log("zhuqiao", "start request drm info");
        ApkManager.getInstance().setPluginInstallState(Constant.DRM_LIBWASABIJNI, PackageManagerCompat.INSTALL_FAILED_INTERNAL_ERROR);
        VolleyResult<DrmSoUrlBean> result = new LetvRequest().setUrl(LetvUrlMaker.getDrmSoUrl()).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setParser(new DrmSoUrlParser()).syncFetch();
        StatisticsUtils.statisticsActionInfo(this, this.mPageId, "19", "drm01", "drm_download", 1, null);
        if (result.networkState != NetworkResponseState.SUCCESS || !(result.result instanceof DrmSoUrlBean)) {
            LogInfo.log("zhuqiao", "request drm url error!!");
            stop();
        } else if (checkShouldUpdatePlugin((DrmSoUrlBean) result.result)) {
            String path = getFilePath();
            if (TextUtils.isEmpty(path)) {
                LogInfo.log("zhuqiao", "app dir floder is null!!");
                stop();
                return;
            }
            File file = new File(path);
            if (file.exists()) {
                LogInfo.log("zhuqiao", "deletv origin drm file");
                file.delete();
            }
            String url = ((DrmSoUrlBean) result.result).url;
            if (TextUtils.isEmpty(url)) {
                LogInfo.log("zhuqiao", "request drm url is null!!");
                stop();
                return;
            }
            this.mIsDownloading = true;
            byte[] bArr = downloadFile(url);
            this.mIsDownloading = false;
            if (bArr == null) {
                LogInfo.log("zhuqiao", "download drm file error!!");
                stop();
                return;
            }
            try {
                copyFile(bArr, getDir(FLODER_NAME, 0).getAbsolutePath(), Constant.DRM_LIBWASABIJNI);
                PreferencesManager.getInstance().setPluginVersion(Constant.DRM_LIBWASABIJNI, ((DrmSoUrlBean) result.result).version);
                loadLib(path);
            } catch (Exception e) {
                LogInfo.log("zhuqiao", "copy drm file error!!");
                e.printStackTrace();
                stop();
            }
        } else {
            LogInfo.log("zhuqiao", "drm is newest!!");
            loadLib(getFilePath());
        }
    }

    private boolean checkShouldUpdatePlugin(DrmSoUrlBean bean) {
        File file = new File(getFilePath());
        if (file.exists()) {
            String version = PreferencesManager.getInstance().getPluginVersion(Constant.DRM_LIBWASABIJNI);
            if (TextUtils.equals(bean.version, version)) {
                String md5 = getFileMD5(file);
                if (TextUtils.equals(md5, bean.md5)) {
                    return false;
                }
                LogInfo.log("zhuqiao", "drm md5，需要重新下载：curr md5=" + md5 + ";server md5=" + bean.md5);
                return true;
            }
            LogInfo.log("zhuqiao", "drm版本号不一致，需要重新下载：curr version=" + version + ";server version=" + bean.version);
            return true;
        }
        LogInfo.log("zhuqiao", "drm文件不存在，需要重新下载");
        return true;
    }

    private void loadLib(String path) {
        LogInfo.log("zhuqiao", "load drm so:" + path);
        System.load(path);
        this.mIsRunning = false;
        BaseApplication.getInstance().mHasLoadDrmSo = true;
        ApkManager.getInstance().setPluginInstallState(Constant.DRM_LIBWASABIJNI, 1);
        RxBus.getInstance().send(new PluginInstalled(true));
        LogInfo.log("play_auto_test_DRM", "####PLAY_DRM####LOAD SUCCESS");
    }

    private byte[] downloadFile(String urlStr) {
        Exception e;
        Throwable th;
        if (!NetworkUtils.isNetworkAvailable()) {
            return null;
        }
        LogInfo.log("zhuqiao", "start download so...");
        HttpURLConnection urlConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            urlConnection = (HttpURLConnection) new URL(urlStr).openConnection();
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int totalSize = 0;
                byte[] buf = new byte[10240];
                while (true) {
                    int len = in.read(buf, 0, 10240);
                    if (len == -1) {
                        break;
                    }
                    totalSize += len;
                    baos.write(buf, 0, len);
                }
                LogInfo.log("zhuqiao", "drm so size is :" + (totalSize >> 20) + " M");
                byte[] toByteArray = baos.toByteArray();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (in == null) {
                    return toByteArray;
                }
                try {
                    in.close();
                    return toByteArray;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return toByteArray;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedInputStream = in;
                try {
                    LogInfo.log("zhuqiao", "drm so download error：" + e.getMessage());
                    e.printStackTrace();
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = in;
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            LogInfo.log("zhuqiao", "drm so download error：" + e.getMessage());
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            return null;
        }
    }

    private void copyFile(byte[] buf, String filePath, String fileName) throws Exception {
        Throwable th;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            FileOutputStream fos2 = new FileOutputStream(new File(filePath + File.separator + fileName));
            try {
                BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
                try {
                    bos2.write(buf);
                    if (bos2 != null) {
                        try {
                            bos2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos2 != null) {
                        try {
                            fos2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fos = fos2;
                    bos = bos2;
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
            throw th;
        }
    }

    private String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        byte[] buffer = new byte[1024];
        try {
            MessageDigest digest = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            FileInputStream in = new FileInputStream(file);
            while (true) {
                int len = in.read(buffer, 0, 1024);
                if (len != -1) {
                    digest.update(buffer, 0, len);
                } else {
                    in.close();
                    return new BigInteger(1, digest.digest()).toString(16);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
