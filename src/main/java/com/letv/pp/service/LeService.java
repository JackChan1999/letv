package com.letv.pp.service;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Proxy;
import android.text.TextUtils;
import com.letv.lemallsdk.util.Constants;
import com.letv.pp.common.TaskEngine;
import com.letv.pp.task.BackgroundTask;
import com.letv.pp.task.ErrorReportEngine;
import com.letv.pp.utils.LogTool;
import com.letv.pp.utils.NetworkUtils;
import java.net.URLEncoder;

public class LeService {
    private static final String FORMAT_CONTROL_URL = "http://127.0.0.1:%s/control/%s";
    private static final String FORMAT_PROXY_URL = "http://%s:%s";
    private static final String FORMAT_START_PARAM = "&proxy=%s&network_type=%s";
    private static final String TAG = "LeService";
    private boolean mCdeStarted;
    private final Context mContext;
    private ErrorReportEngine mErrorReportEngine;
    private ProxyBroadcastReceive mProxyBroadcastReceive;
    private long mServiceHandle;
    private final String mStartParams;
    private String mUseingVersion;

    private native long accaGetServicePort(long j);

    private native long accaGetStateDownloadedDuration(String str);

    private native double accaGetStateDownloadedPercent(String str);

    private native long accaGetStateLastReceiveSpeed(String str);

    private native long accaGetStateTotalDuration(String str);

    private native long accaGetStateUrgentReceiveSpeed(String str);

    private native long accaGetVersionNumber();

    private native String accaGetVersionString();

    private native void accaSetChannelSeekPosition(String str, double d);

    private native long accaSetKeyDataCache(String str, String str2);

    private native long accaStartServiceWithParams(String str);

    private native long accaStopService(long j);

    public LeService(Context context, String params) {
        if (context == null) {
            throw new IllegalArgumentException("Illegal Context argument");
        }
        this.mContext = context;
        this.mStartParams = params;
    }

    public boolean start() {
        try {
            this.mUseingVersion = accaGetVersionString();
        } catch (Throwable t) {
            LogTool.e(TAG, "start. get cde version failed, " + t.toString());
        }
        try {
            this.mServiceHandle = accaStartServiceWithParams(this.mStartParams + String.format(FORMAT_START_PARAM, new Object[]{detectProxy(), Integer.valueOf(getCdeNetworkType())}));
        } catch (Throwable t2) {
            LogTool.e(TAG, "start. CDE start abnormal, " + t2.toString());
        }
        long port = getCdePort();
        if (port <= 0) {
            try {
                LogTool.i(TAG, "start. port invalid(%s), try to restart service", Long.valueOf(port));
                accaStopService(this.mServiceHandle);
                this.mServiceHandle = accaStartServiceWithParams(this.mStartParams + String.format(FORMAT_START_PARAM, new Object[]{detectProxy(), Integer.valueOf(getCdeNetworkType())}));
                port = getCdePort();
            } catch (Throwable t22) {
                LogTool.e(TAG, "start. CDE restart abnormal, " + t22.toString());
            }
            if (port <= 0) {
                LogTool.i(TAG, "start. CDE start failed, service handle(%s), CDE version(%s), CDE port(%s)", Long.valueOf(this.mServiceHandle), this.mUseingVersion, Long.valueOf(port));
                this.mCdeStarted = true;
                return false;
            }
        }
        LogTool.i(TAG, "start. CDE start successfully, service handle(%s), CDE version(%s), CDE port(%s)", Long.valueOf(this.mServiceHandle), this.mUseingVersion, Long.valueOf(port));
        this.mCdeStarted = true;
        notifyStorageSize(port);
        this.mProxyBroadcastReceive = new ProxyBroadcastReceive(this, null);
        this.mContext.registerReceiver(this.mProxyBroadcastReceive, new IntentFilter("android.intent.action.PROXY_CHANGE"));
        return true;
    }

    public void stop() {
        long result = 0;
        try {
            if (this.mErrorReportEngine != null) {
                this.mErrorReportEngine.close();
                this.mErrorReportEngine = null;
            }
            if (this.mProxyBroadcastReceive != null) {
                this.mContext.unregisterReceiver(this.mProxyBroadcastReceive);
                this.mProxyBroadcastReceive = null;
            }
            if (this.mServiceHandle > 0) {
                result = accaStopService(this.mServiceHandle);
            }
        } catch (Throwable t) {
            LogTool.e(TAG, "stopService. " + t.toString());
        }
        this.mServiceHandle = 0;
        String str = TAG;
        String str2 = "stopService. stop CDE service completed, result(%s)";
        Object[] objArr = new Object[1];
        objArr[0] = result == 0 ? "successfully" : Constants.CALLBACK_FAILD;
        LogTool.i(str, str2, objArr);
    }

    private int getCdeNetworkType() {
        int networkType = NetworkUtils.getNetworkType();
        if (NetworkUtils.unknownNetwork() || NetworkUtils.noPermissionNetwork()) {
            return 2;
        }
        return networkType;
    }

    private void notifyStorageSize(long port) {
        new Thread(new 1(this, port)).start();
    }

    public boolean setKeyDataCache(String key, String data) {
        try {
            if (accaSetKeyDataCache(key, data) == 0) {
                return true;
            }
            LogTool.e(TAG, "setKeyDataCache. the failed, result value(%s)", Long.valueOf(accaSetKeyDataCache(key, data)));
            return false;
        } catch (Throwable t) {
            LogTool.e(TAG, "setKeyDataCache. " + t.toString());
            return false;
        }
    }

    public long getStateTotalDuration(String url) {
        try {
            return accaGetStateTotalDuration(url);
        } catch (Throwable t) {
            LogTool.e(TAG, "getStateTotalDuration. " + t.toString());
            return -3;
        }
    }

    public long getStateDownloadedDuration(String url) {
        try {
            return accaGetStateDownloadedDuration(url);
        } catch (Throwable t) {
            LogTool.e(TAG, "getStateDownloadedDuration. " + t.toString());
            return -3;
        }
    }

    public double getStateDownloadedPercent(String url) {
        try {
            return accaGetStateDownloadedPercent(url);
        } catch (Throwable t) {
            LogTool.e(TAG, "getStateDownloadedPercent. " + t.toString());
            return -3.0d;
        }
    }

    public long getStateUrgentReceiveSpeed(String url) {
        try {
            return accaGetStateUrgentReceiveSpeed(url);
        } catch (Throwable t) {
            LogTool.e(TAG, "getStateUrgentReceiveSpeed. " + t.toString());
            return -3;
        }
    }

    public long getStateLastReceiveSpeed(String url) {
        try {
            return accaGetStateLastReceiveSpeed(url);
        } catch (Throwable t) {
            LogTool.e(TAG, "getStateLastReceiveSpeed. " + t.toString());
            return -3;
        }
    }

    public void setChannelSeekPosition(String url, double pos) {
        try {
            accaSetChannelSeekPosition(url, pos);
        } catch (Throwable t) {
            LogTool.e(TAG, "setChannelSeekPosition. " + t.toString());
        }
    }

    public String getCdeVersion() {
        return this.mUseingVersion;
    }

    public long getCdePort() {
        long j = 0;
        try {
            if (this.mServiceHandle > 0) {
                j = accaGetServicePort(this.mServiceHandle);
            }
        } catch (Throwable t) {
            LogTool.e(TAG, "getCdePort. " + t.toString());
        }
        return j;
    }

    public void memoryRecovery() {
        notifyCde("removeall");
    }

    public void notifyNetworkChanged() {
        if (this.mCdeStarted) {
            long port = getCdePort();
            if (port <= 0) {
                try {
                    LogTool.i(TAG, "notifyNetworkChanged. port invalid(%s), try to restart service", Long.valueOf(port));
                    accaStopService(this.mServiceHandle);
                    this.mServiceHandle = accaStartServiceWithParams(this.mStartParams + String.format(FORMAT_START_PARAM, new Object[]{detectProxy(), Integer.valueOf(getCdeNetworkType())}));
                    port = getCdePort();
                    LogTool.i(TAG, "notifyNetworkChanged. restart port(%s), service handle(%s)", Long.valueOf(port), Long.valueOf(this.mServiceHandle));
                } catch (Throwable t) {
                    LogTool.e(TAG, "notifyNetworkChanged. " + t.toString());
                }
                if (port <= 0) {
                    return;
                }
            }
            notifyCde("params?set_net_type=" + getCdeNetworkType());
        }
    }

    private void notifyCde(String params) {
        if (getCdePort() > 0) {
            TaskEngine.getInstance().submit(new BackgroundTask(String.format(FORMAT_CONTROL_URL, new Object[]{Long.valueOf(port), params})));
        }
    }

    private String detectProxy() {
        String proxyUrl = null;
        if (!TextUtils.isEmpty(Proxy.getDefaultHost())) {
            proxyUrl = String.format(FORMAT_PROXY_URL, new Object[]{Proxy.getDefaultHost(), Integer.valueOf(Proxy.getDefaultPort())});
        }
        LogTool.i(TAG, "detectProxy. detected the proxy url(%s)", proxyUrl);
        return TextUtils.isEmpty(proxyUrl) ? "" : URLEncoder.encode(proxyUrl);
    }
}
