package com.letv.component.upgrade.core.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.component.utils.DebugLog;

public class DownloadService extends LetvIntentService {
    private static final String ACTION_ADD = "action_add";
    private static final String ACTION_COMPLETE = "action_complete";
    private static final String ACTION_PAUSE = "action_pause";
    private static final String ACTION_PAUSE_ALL = "action_pause_all";
    private static final String ACTION_PENDING = "action_pending";
    private static final String ACTION_REFETCH = "action_refetch";
    private static final String ACTION_REMOVE = "action_remove";
    private static final String ACTION_REMOVE_ALL = "action_remove_all";
    private static final String ACTION_RESUME = "action_resume";
    private static final String ACTION_SAVE_ALL = "action_save_all";
    private static final String ACTION_START_ALL = "action_start_all";
    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(TAG);
    }

    public void onCreate() {
        super.onCreate();
        DebugLog.log(TAG, "Download Service onCreate");
        initDownLoadConfig();
    }

    public static void addDownload(Context mContext, String id, String url, String dir, String name, int threads, boolean isCurrentApp, int installType) {
        if (!TextUtils.isEmpty(id)) {
            Intent i = new Intent(mContext, DownloadService.class);
            i.setAction(ACTION_ADD);
            i.putExtra("id", id);
            i.putExtra("url", url);
            i.putExtra("directory", dir);
            i.putExtra("file_name", name);
            i.putExtra("threads", threads);
            i.putExtra("isCurrentApp", isCurrentApp);
            i.putExtra("installType", installType);
            mContext.startService(i);
        }
    }

    private void add(String id, String url, String dir, String name, int threads, boolean isCurrentApp, int installType) {
        DownloadManager.getInstance(this).addDownload(id, url, dir, name, threads, isCurrentApp, installType);
    }

    public static void refreshDownloads(Context mContext) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction(ACTION_REFETCH);
        mContext.startService(i);
    }

    private void refresh() {
        DownloadManager.getInstance(this).refresh();
    }

    public static void pauseDowload(Context mContext, String id) {
        if (!TextUtils.isEmpty(id)) {
            Intent i = new Intent(mContext, DownloadService.class);
            i.setAction("action_pause");
            i.putExtra("id", id);
            mContext.startService(i);
        }
    }

    private void pause(String url) {
        DownloadManager.getInstance(this).pauseDownload(url);
        if (DownloadManager.getInstance(this).getDownloadingNum() == 0) {
            stopSelf();
        }
    }

    public static void resumeDownload(Context mContext, String id) {
        if (!TextUtils.isEmpty(id)) {
            Intent i = new Intent(mContext, DownloadService.class);
            i.setAction("action_resume");
            i.putExtra("id", id);
            mContext.startService(i);
        }
    }

    private void resume(String id) {
        DownloadManager.getInstance(this).resumeDownload(id);
    }

    public static void saveAllDownload(Context mContext) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction(ACTION_SAVE_ALL);
        mContext.startService(i);
    }

    private void saveAll() {
        DownloadManager.getInstance(this).saveDownloads();
    }

    public static void removeDownload(Context mContext, String id) {
        if (!TextUtils.isEmpty(id)) {
            Intent i = new Intent(mContext, DownloadService.class);
            i.setAction("action_remove");
            i.putExtra("id", id);
            mContext.startService(i);
        }
    }

    private void remove(String id) {
        DownloadManager.getInstance(this).removeDownload(id);
    }

    public static void removeAllDownload(Context mContext) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction("action_remove_all");
        mContext.startService(i);
    }

    private void removeAll() {
        DownloadManager.getInstance(this).removeAllDownload();
        stopSelf();
    }

    public static void startAllDownload(Context mContext) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction("action_start_all");
        mContext.startService(i);
    }

    private void startAll() {
        DownloadManager.getInstance(this).startAll();
    }

    public static void pauseAllDownload(Context mContext, boolean isError) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction("action_pause_all");
        i.putExtra("isError", isError);
        mContext.startService(i);
        DebugLog.log(TAG, "pauseAllDownload");
    }

    private void pauseAll(boolean isError) {
        if (isError) {
            DownloadManager.getInstance(this).errorPauseAll();
        } else {
            DownloadManager.getInstance(this).pauseAll();
        }
        stopSelf();
    }

    public static void comleteDownload(Context mContext, String id) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction(ACTION_COMPLETE);
        i.putExtra("id", id);
        mContext.startService(i);
    }

    private void complete(String id) {
        DownloadManager.getInstance(this).completeDownloadInfo(id);
    }

    public static void pendingDownload(Context mContext) {
        Intent i = new Intent(mContext, DownloadService.class);
        i.setAction("action_pending");
        mContext.startService(i);
    }

    private void pending() {
        DownloadManager.getInstance(this).startPendingDownload();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equals("action_remove") || action.equals("action_remove_all") || action.equals("action_pending") || LetvUtil.isConnect(this)) {
                String id = intent.getStringExtra("id");
                DebugLog.log(TAG, "DownloadService:" + action);
                if (action.equals(ACTION_ADD)) {
                    add(id, intent.getStringExtra("url"), intent.getStringExtra("directory"), intent.getStringExtra("file_name"), intent.getIntExtra("threads", 1), intent.getBooleanExtra("isCurrentApp", false), intent.getIntExtra("installType", 0));
                } else if (action.equals(ACTION_REFETCH)) {
                    refresh();
                } else if (action.equals("action_pause")) {
                    pause(id);
                } else if (action.equals("action_resume")) {
                    resume(id);
                } else if (action.equals(ACTION_SAVE_ALL)) {
                    saveAll();
                } else if (action.equals("action_remove")) {
                    remove(id);
                } else if (action.equals("action_remove_all")) {
                    removeAll();
                } else if (action.equals("action_start_all")) {
                    startAll();
                } else if (action.equals("action_pause_all")) {
                    pauseAll(intent.getBooleanExtra("isError", false));
                } else if (action.equals(ACTION_COMPLETE)) {
                    complete(id);
                } else if (action.equals("action_pending")) {
                    pending();
                }
            }
        }
    }

    private void initDownLoadConfig() {
        DownLoadFunction downLoadFunction = DownLoadFunction.getInstance(getApplicationContext());
        DownloadManager.getInstance(getApplicationContext()).downTaskNum = downLoadFunction.downLoadTaskCount;
        NotifyManage.callbackCategory = downLoadFunction.callbackCategory;
        DownloadManager.getInstance(getApplicationContext()).startAddToDb = downLoadFunction.startAddTaskToDB;
        DownloadManager.getInstance(getApplicationContext()).finishAddToDb = downLoadFunction.finishAddTaskToDB;
    }
}
