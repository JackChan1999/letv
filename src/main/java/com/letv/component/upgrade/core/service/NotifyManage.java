package com.letv.component.upgrade.core.service;

import android.content.Context;
import android.content.Intent;
import com.letv.component.upgrade.bean.DownloadInfo;
import com.letv.component.upgrade.core.AppDownloadConfiguration.DataCallbackCategory;
import com.letv.component.upgrade.utils.AppUpgradeConstants;
import com.letv.component.utils.DebugLog;
import java.util.ArrayList;

public class NotifyManage {
    private static final String TAG = "NotifyManage";
    public static DataCallbackCategory callbackCategory;
    public static boolean canCallBack = true;
    private static ArrayList<DownloadListener> observers = new ArrayList();

    public static void registerLocalListener(DownloadListener listener) {
        if (!observers.contains(listener)) {
            observers.add(listener);
            DebugLog.log(TAG, "添加成功" + listener.toString());
        }
    }

    public static void unRegisterLocalListener(DownloadListener listener) {
        observers.remove(listener);
    }

    private static Intent baseIntent(Context mContext, DownloadInfo info) {
        Intent notifyIntent = new Intent(AppUpgradeConstants.NOTIFY_INTENT_ACTION);
        notifyIntent.putExtra("info", info);
        return notifyIntent;
    }

    public static void notifyAdd(Context mContext, DownloadInfo info) {
        if (callbackCategory == DataCallbackCategory.BROADCAST) {
            Intent notifyIntent = baseIntent(mContext, info);
            notifyIntent.putExtra("type", 6);
            mContext.sendBroadcast(notifyIntent);
            return;
        }
        callbackClient(6, info);
    }

    public static void notifyProgress(Context mContext, DownloadInfo info) {
        if (callbackCategory == DataCallbackCategory.BROADCAST) {
            Intent notifyIntent = baseIntent(mContext, info);
            notifyIntent.putExtra("type", 0);
            mContext.sendBroadcast(notifyIntent);
            return;
        }
        callbackClient(0, info);
    }

    public static void notifyPause(Context mContext, DownloadInfo info) {
        if (callbackCategory == DataCallbackCategory.BROADCAST) {
            Intent notifyIntent = baseIntent(mContext, info);
            notifyIntent.putExtra("type", 3);
            mContext.sendBroadcast(notifyIntent);
            return;
        }
        callbackClient(3, info);
    }

    public static void notifyFinish(Context mContext, DownloadInfo info) {
        if (callbackCategory == DataCallbackCategory.BROADCAST) {
            Intent notifyIntent = baseIntent(mContext, info);
            notifyIntent.putExtra("type", 1);
            mContext.sendBroadcast(notifyIntent);
            return;
        }
        callbackClient(1, info);
    }

    public static void notifyStart(Context mContext, DownloadInfo info) {
        if (callbackCategory == DataCallbackCategory.BROADCAST) {
            Intent notifyIntent = baseIntent(mContext, info);
            notifyIntent.putExtra("type", 2);
            mContext.sendBroadcast(notifyIntent);
            return;
        }
        callbackClient(2, info);
    }

    public static void notifyPending(Context mContext, DownloadInfo info) {
        if (callbackCategory == DataCallbackCategory.BROADCAST) {
            Intent notifyIntent = baseIntent(mContext, info);
            notifyIntent.putExtra("type", 8);
            mContext.sendBroadcast(notifyIntent);
            return;
        }
        callbackClient(8, info);
    }

    private static void callbackClient(int state, DownloadInfo info) {
        if (!canCallBack) {
            DebugLog.log(TAG, "不能回调");
        } else if (observers == null || observers.size() <= 0) {
            DebugLog.log(TAG, "回调失败");
        } else {
            DebugLog.log(TAG, "local回调成功");
            DownloadListener listener = (DownloadListener) observers.get(0);
            switch (state) {
                case 0:
                    listener.updateProgress(info);
                    return;
                case 1:
                    listener.finishDownload(info);
                    return;
                case 2:
                    listener.startDownload(info);
                    return;
                case 3:
                    listener.stopDownload(info, true);
                    return;
                case 6:
                    listener.waitingDownload(info);
                    return;
                case 8:
                    listener.waitingDownload(info);
                    return;
                default:
                    return;
            }
        }
    }
}
