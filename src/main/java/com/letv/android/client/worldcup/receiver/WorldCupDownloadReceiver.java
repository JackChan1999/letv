package com.letv.android.client.worldcup.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.letv.android.client.worldcup.LetvAlarmService;
import com.letv.android.client.worldcup.LetvDownloadService;
import com.letv.android.client.worldcup.R;
import com.letv.android.client.worldcup.bean.DownloadDBBean;
import com.letv.android.client.worldcup.bean.DownloadDBBeanList;
import com.letv.android.client.worldcup.bean.DownloadStatus;
import com.letv.android.client.worldcup.dao.PreferencesManager;
import com.letv.android.client.worldcup.db.WorldCupTraceHandler;
import com.letv.android.client.worldcup.util.Constants;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration;
import com.letv.android.client.worldcup.util.LetvUtil;
import com.letv.core.constant.DownloadConstant.BroadcaseIntentParams;
import com.letv.core.utils.LogInfo;
import com.letv.datastatistics.DataManager;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;

public class WorldCupDownloadReceiver extends BroadcastReceiver {
    private final int PUSH_ID = DataManager.NOT_UPLOAD_INT;
    private boolean isPush = false;
    private Context mContext;
    private WorldCupTraceHandler worldCupTraceHandler;

    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        this.worldCupTraceHandler = new WorldCupTraceHandler(this.mContext);
        handleIntent(context, intent);
    }

    private void handleIntent(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals("com.letv.android.client.worldcup.download")) {
            int type = intent.getIntExtra("type", -1);
            int progress = intent.getIntExtra("progress", 0);
            String episodeId = intent.getStringExtra("id");
            int status = intent.getIntExtra("status", 0);
            long totalSize = intent.getLongExtra(BroadcaseIntentParams.KEY_TOTAL, 0);
            this.worldCupTraceHandler.updateSize(episodeId, totalSize, intent.getLongExtra("downloaded", 0), status);
            LogInfo.log("WorldCupDownloadReceiver", "WorldCupDownloadReceiverhandleIntent>>>");
            if (type == 1) {
                this.worldCupTraceHandler.finish(Integer.parseInt(episodeId), totalSize);
                try {
                    staticticsInfoPost(this.worldCupTraceHandler.getInFinish((long) Integer.parseInt(episodeId)));
                    setPushData(context);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                boolean isAllDownloadFinish = true;
                DownloadDBBeanList dbBeanList = this.worldCupTraceHandler.getAllTrace();
                if (dbBeanList != null) {
                    for (int i = 0; i < dbBeanList.size(); i++) {
                        Constants.debug("===========dbBeanList.size()" + dbBeanList.size() + ", pos = " + i + ", dbBeanList.get(" + i + ").getFinish()" + ((DownloadDBBean) dbBeanList.get(i)).getFinish());
                        if (((DownloadDBBean) dbBeanList.get(i)).getFinish() != DownloadStatus.FINISHED.toInt()) {
                            isAllDownloadFinish = false;
                            break;
                        }
                    }
                    if (isAllDownloadFinish && LetvAlarmService.checkServiceIsRunning(context)) {
                        ((LetvDownloadService) context).stopSelf();
                    }
                }
            }
            sendIntent(episodeId, progress, status, type);
        }
    }

    private void setPushData(Context context) {
        if (!this.isPush) {
            Constants.debug("======Push Id:-10000");
            String pushText = PreferencesManager.getInstance().getPushText(context);
            Intent intent = new Intent(context, PushNotificationReceiver.class);
            intent.putExtra("msgId", DataManager.NOT_UPLOAD_INT);
            intent.putExtra("type", 1000);
            intent.addFlags(268435456);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            Notification notification = new Notification();
            notification.icon = R.drawable.notify_icon;
            notification.tickerText = pushText;
            notification.flags = 16;
            notification.defaults |= 4;
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.push_layout);
            remoteViews.setImageViewBitmap(R.id.push_icon, BitmapFactory.decodeResource(context.getResources(), R.drawable.push_icon));
            remoteViews.setTextViewText(R.id.push_text, LetvUtil.ToDBC(pushText));
            notification.contentView = remoteViews;
            notification.contentIntent = PendingIntent.getBroadcast(context, DataManager.NOT_UPLOAD_INT, intent, 268435456);
            notificationManager.notify(DataManager.NOT_UPLOAD_INT, notification);
            this.isPush = true;
        }
    }

    private void sendIntent(String episodeId, int progress, int status, int type) {
        Intent notifyIntent = new Intent("com.letv.android.client.worldcup.download.action_update");
        notifyIntent.putExtra("episodeId", episodeId);
        notifyIntent.putExtra("progress", progress);
        notifyIntent.putExtra("status", status);
        notifyIntent.putExtra("type", type);
        this.mContext.sendBroadcast(notifyIntent);
    }

    private void staticticsInfoPost(DownloadDBBean bean) {
        if (bean != null) {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(bean.getAlbumtitle())) {
                sb.append("&name=" + bean.getAlbumtitle());
            }
            if (!TextUtils.isEmpty(PageIdConstant.worldCupPage)) {
                sb.append("&pageid=" + PageIdConstant.worldCupPage);
            }
            sb.append("&download=1");
            DataStatistics.getInstance().sendActionInfo(this.mContext, "0", "0", LetvServiceConfiguration.getPcode(this.mContext), "2", sb.toString(), "0", new StringBuilder(String.valueOf(bean.getCid())).toString(), new StringBuilder(String.valueOf(bean.getAlbumId())).toString(), new StringBuilder(String.valueOf(bean.getEpisodeid())).toString(), com.letv.core.db.PreferencesManager.getInstance().getUserId(), null, null, null, null, -1, null);
        }
    }
}
