package com.letv.download.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.download.R;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.download.db.DownloadDBDao;

public class DownloadNotification {
    private static final int INITIAL_REGISTER_OBSERVER_DELAY = 1020;
    private static DownloadVideo mDownloadVideo;
    private static long preDownloadedSize = 0;
    private boolean isNeed2DealChange = false;
    private boolean isfristdown = false;
    private long lastRefreshVid = 0;
    private Context mContext;
    private DownloadProgressObserver mDownloadProgressObserver;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int progress = 0;
            super.handleMessage(msg);
            switch (msg.what) {
                case 1010:
                    DownloadNotification.this.initNotification();
                    DownloadNotification.this.newVid = DownloadService.getVid4Notification();
                    if (DownloadNotification.this.oldVid != 0 || DownloadNotification.this.newVid != 0) {
                        if (DownloadNotification.this.newVid != DownloadNotification.this.oldVid && DownloadNotification.this.oldVid == 0 && !DownloadNotification.this.isNeed2DealChange) {
                            DownloadNotification.this.oldVid = DownloadNotification.this.newVid;
                        } else if (DownloadNotification.this.newVid != 0 || DownloadNotification.this.oldVid == 0 || DownloadNotification.this.isNeed2DealChange) {
                            if (DownloadNotification.this.newVid != DownloadNotification.this.oldVid && DownloadNotification.this.newVid != 0 && DownloadNotification.this.oldVid != 0 && !DownloadNotification.this.isNeed2DealChange) {
                                DownloadNotification.this.isNeed2DealChange = true;
                            } else if (DownloadNotification.this.newVid == DownloadNotification.this.oldVid || DownloadNotification.this.oldVid == 0 || !DownloadNotification.this.isNeed2DealChange) {
                                DownloadNotification.this.isNeed2DealChange = false;
                            } else {
                                DownloadNotification.this.isNeed2DealChange = false;
                                DownloadNotification.this.oldVid = DownloadNotification.this.newVid;
                            }
                        }
                        DownloadNotification.mDownloadVideo = DownloadDBDao.getInstance(DownloadNotification.this.mContext).getDownloadVideoByVid(DownloadNotification.this.oldVid);
                        if (DownloadNotification.mDownloadVideo == null) {
                            DownloadNotification.this.type = 4;
                            try {
                                DownloadNotification.this.refreshNotification(DownloadNotification.this.mContext, 0, DownloadNotification.this.type, 0, 0);
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        switch (DownloadNotification.mDownloadVideo.state) {
                            case 0:
                                DownloadNotification.this.type = 8;
                                break;
                            case 1:
                                DownloadNotification.this.type = 0;
                                break;
                            case 3:
                                DownloadNotification.this.type = 3;
                                break;
                            case 4:
                                LogInfo.log("DownloadState.FINISHED_STATE!!!!!");
                                DownloadNotification.this.type = 1;
                                break;
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                DownloadNotification.this.type = 9;
                                break;
                        }
                        long downloadedSize = DownloadNotification.mDownloadVideo.downloaded;
                        long totalSize = DownloadNotification.mDownloadVideo.totalsize;
                        if (totalSize != 0) {
                            progress = (int) ((100 * downloadedSize) / totalSize);
                        }
                        try {
                            DownloadNotification.this.refreshNotification(DownloadNotification.this.mContext, DownloadNotification.this.oldVid, DownloadNotification.this.type, progress, downloadedSize);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        if (DownloadNotification.this.newVid == 0 && DownloadNotification.this.oldVid != 0 && !DownloadNotification.this.isNeed2DealChange) {
                            DownloadNotification.this.oldVid = DownloadNotification.this.newVid;
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private long mLastDownloadMisecTime = System.currentTimeMillis();
    private Notification mNotification;
    public Class<?> mTargetClass = null;
    private long newVid = 0;
    private NotificationManager nm;
    private long oldVid = 0;
    private int type = -1;

    public DownloadNotification(Context context, Class<?> target) {
        this.mContext = context;
        this.mTargetClass = target;
        this.mDownloadProgressObserver = new DownloadProgressObserver(this.mContext, this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(DownloadVideoTable.CONTENT_URI, false, this.mDownloadProgressObserver);
    }

    public void unregisterNotifyObserver() {
        LogInfo.log("fornia", "MainAcitivityNotification unregisterNotifyObserver()()()  !!!");
        if (this.mDownloadProgressObserver != null) {
            LogInfo.log("fornia", "MainAcitivityNotification unregisterNotifyObserver  !!!");
            this.mContext.getContentResolver().unregisterContentObserver(this.mDownloadProgressObserver);
            this.mDownloadProgressObserver = null;
        }
        if (this.nm != null && this.type != 1) {
            this.nm.cancel(1000);
            mDownloadVideo = null;
        }
    }

    private void initNotification() {
        if (this.mNotification == null || this.nm == null) {
            this.mNotification = new Notification(R.drawable.notification_icon, this.mContext.getString(R.string.notification_download_ing), System.currentTimeMillis());
            this.mNotification.contentView = new RemoteViews(this.mContext.getPackageName(), R.layout.notification_download);
            this.mNotification.contentView.setProgressBar(R.id.notify_progressbar, 100, 0, false);
            this.mNotification.flags = 32;
            this.nm = (NotificationManager) this.mContext.getSystemService("notification");
        }
    }

    private void refreshNotification(Context context, long vid, int type, int progress, long downloadedSize) {
        if (this.mNotification != null && this.nm != null) {
            String title;
            Intent down_it;
            switch (type) {
                case 0:
                    this.mNotification.tickerText = null;
                    this.mNotification.flags = 32;
                    if (vid != this.lastRefreshVid) {
                        LogInfo.log("MainAcitivityNotification 初始化notify lastRefreshVid:" + this.lastRefreshVid + "|vid:" + vid);
                        this.lastRefreshVid = vid;
                        if (TextUtils.isEmpty(mDownloadVideo.name)) {
                            title = "";
                        } else {
                            title = mDownloadVideo.name;
                        }
                        this.mNotification.contentView.setTextViewText(R.id.notify_title, context.getString(R.string.notification_download_ing) + " " + title);
                        this.mNotification.contentView.setViewVisibility(R.id.notify_error, 4);
                        this.mNotification.contentView.setViewVisibility(R.id.notify_progress_lt, 0);
                        this.mNotification.contentView.setViewVisibility(R.id.notify_progressbar, 0);
                        this.mNotification.contentView.setViewVisibility(R.id.notify_content, 4);
                        if (this.isfristdown) {
                            this.mNotification.tickerText = null;
                        } else {
                            if (progress == 0) {
                                this.mNotification.tickerText = context.getString(R.string.notification_download_ing) + " " + title;
                            } else {
                                this.mNotification.tickerText = context.getString(R.string.notification_download_continue) + " " + title;
                            }
                            this.isfristdown = true;
                        }
                        this.mNotification.flags = 32;
                        if (this.mTargetClass != null) {
                            down_it = new Intent(context, this.mTargetClass);
                            down_it.putExtra(MyDownloadActivityConfig.PAGE, 1);
                            this.mNotification.contentIntent = PendingIntent.getActivity(context, 0, down_it, 268435456);
                        }
                    }
                    String speed = "";
                    long pastTimeSec = (System.currentTimeMillis() - this.mLastDownloadMisecTime) / 1000;
                    if (downloadedSize == 0 || preDownloadedSize == 0 || pastTimeSec == 0 || preDownloadedSize > downloadedSize) {
                        speed = "";
                    } else {
                        String downloadSpeed = LetvTools.calculateDownloadSpeed(this.mLastDownloadMisecTime, System.currentTimeMillis(), downloadedSize - preDownloadedSize);
                        if (!TextUtils.isEmpty(downloadSpeed)) {
                            try {
                                speed = LetvTools.formatSpeed(Long.valueOf(downloadSpeed).longValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    this.mLastDownloadMisecTime = System.currentTimeMillis();
                    preDownloadedSize = downloadedSize;
                    this.mNotification.contentView.setViewVisibility(R.id.notify_error, 4);
                    this.mNotification.contentView.setViewVisibility(R.id.notify_speed, 0);
                    this.mNotification.contentView.setProgressBar(R.id.notify_progressbar, 100, progress, false);
                    this.mNotification.contentView.setTextViewText(R.id.notify_speed, speed);
                    this.mNotification.contentView.setTextViewText(R.id.notify_progress, String.valueOf(progress) + "%");
                    break;
                case 1:
                    this.nm.cancel(1000);
                    title = "";
                    if (mDownloadVideo != null) {
                        title = TextUtils.isEmpty(mDownloadVideo.name) ? "" : mDownloadVideo.name;
                    } else {
                        mDownloadVideo = DownloadDBDao.getInstance(this.mContext).getDownloadVideoByVid(vid);
                        if (mDownloadVideo != null) {
                            title = TextUtils.isEmpty(mDownloadVideo.name) ? "" : mDownloadVideo.name;
                        }
                    }
                    this.mNotification.tickerText = title + " " + context.getString(R.string.notification_download_done);
                    this.mNotification.contentView.setTextViewText(R.id.notify_title, context.getString(R.string.notification_download_complete));
                    this.mNotification.contentView.setTextViewText(R.id.notify_content, context.getString(R.string.notification_doanload_click) + " " + title);
                    this.mNotification.contentView.setViewVisibility(R.id.notify_progress_lt, 4);
                    this.mNotification.contentView.setViewVisibility(R.id.notify_progressbar, 4);
                    this.mNotification.contentView.setViewVisibility(R.id.notify_content, 0);
                    this.mNotification.flags = 16;
                    down_it = new Intent();
                    down_it.setAction("android.client.receiver.DownloadCompeleReceiver");
                    this.mNotification.contentIntent = PendingIntent.getBroadcast(context, 0, down_it, 268435456);
                    mDownloadVideo = null;
                    break;
                case 3:
                    if (mDownloadVideo != null) {
                        this.nm.cancel(1000);
                        mDownloadVideo = null;
                        return;
                    }
                    return;
                case 4:
                case 9:
                    this.nm.cancel(1000);
                    mDownloadVideo = null;
                    return;
                case 10:
                    if (mDownloadVideo != null && mDownloadVideo.vid == vid) {
                        this.nm.cancel(1000);
                        mDownloadVideo = null;
                        return;
                    }
                    return;
            }
            this.nm.notify(1000, this.mNotification);
        }
    }
}
