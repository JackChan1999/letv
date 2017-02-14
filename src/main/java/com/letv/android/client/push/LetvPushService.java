package com.letv.android.client.push;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;
import cn.jpush.android.api.JPushInterface;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.push.DialogService.DialogType;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.android.client.utils.UIs;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.PushData;
import com.letv.core.bean.PushData.Activatemsg;
import com.letv.core.bean.PushData.SMSMessage;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloadStateListener;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.VolleyResult;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.PushDataParser;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.datastatistics.util.DataUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.open.SocialConstants;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.json.JSONException;
import org.json.JSONObject;

public class LetvPushService extends Service {
    public static final String INTENT_KEY_FORCE_PUSH = "isForcePush";
    public static final String INTENT_KEY_PUSH_HAS_PIC = "hasPic";
    public static final int PUSH_FROM_JIGUANG = 1;
    public static final int PUSH_FROM_NORMAL = 0;
    private static final String TAG = LetvPushService.class.getSimpleName();
    private boolean mDataIsFromNormal;
    private int mPushFrom;
    private String mPushMsg;
    private String mThirdPushData;
    private WakeLock mWakeLock;
    private Bitmap pushBitmap;
    private Bitmap pushBitmapBig;

    public LetvPushService() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.pushBitmap = null;
        this.pushBitmapBig = null;
        this.mPushFrom = 0;
        this.mDataIsFromNormal = true;
        this.mPushMsg = "";
    }

    public void onCreate() {
        super.onCreate();
        this.mWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(1, TAG);
        if (!this.mWakeLock.isHeld()) {
            this.mWakeLock.acquire();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        LogInfo.log("push", "start query push");
        if (intent == null || !(intent.getStringExtra(JPushInterface.EXTRA_MESSAGE) instanceof String)) {
            this.mPushFrom = 0;
            this.mDataIsFromNormal = true;
        } else {
            this.mPushFrom = 1;
            this.mThirdPushData = intent.getStringExtra(JPushInterface.EXTRA_MESSAGE);
        }
        new Thread(this) {
            final /* synthetic */ LetvPushService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                this.this$0.fetchData();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchData() {
        if (!LetvUtils.is60() || PreferencesManager.getInstance().isApplyPermissionsSuccess()) {
            LogInfo.log("push", "fetchData");
            forceAlert();
            long curTime = System.currentTimeMillis();
            long historyId = PreferencesManager.getInstance().getPushId();
            String msgId = PreferencesManager.getInstance().getMsgId();
            PreferencesManager.getInstance().savePushTime(curTime);
            LogInfo.log("zhuqiao", "save pushtime:" + curTime);
            PushData pushData = null;
            if (this.mThirdPushData != null) {
                LogInfo.log("JPush", "LetvApplication.sThirdPushData != null");
                try {
                    pushData = new PushDataParser().parse(new JSONObject(this.mThirdPushData));
                    this.mDataIsFromNormal = pushData == null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pushData == null) {
                LogInfo.log("zhaoxiang", LetvUrlMaker.getPushUrl(historyId + "", msgId, LetvUtils.generateDeviceId(this), PreferencesManager.getInstance().getLocationCity()));
                VolleyResult<PushData> volleyResult = new LetvRequest().setParser(new PushDataParser()).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setUrl(LetvUrlMaker.getPushUrl(historyId + "", msgId, LetvUtils.generateDeviceId(this), PreferencesManager.getInstance().getLocationCity())).syncFetch();
                if (volleyResult.networkState == NetworkResponseState.SUCCESS) {
                    pushData = volleyResult.result;
                    if (!(pushData == null || !pushData.mHasPushMsg || volleyResult.dataHull == null || TextUtils.isEmpty(volleyResult.dataHull.sourceData))) {
                        this.mPushMsg = volleyResult.dataHull.sourceData;
                        LogInfo.log("jc666", "push data:" + this.mPushMsg);
                        StatisticsUtils.statisticsActionInfo(this, null, VType.FLV_1080P_3D, null, null, -1, "push_su=0&&&pushmsg=" + volleyResult.dataHull.sourceData);
                    }
                }
            } else {
                this.mPushMsg = pushData.toString();
            }
            if (pushData == null || pushData.isNull()) {
                LogInfo.log("zhuqiao", "schedule with push == null");
                PreferencesManager.getInstance().savePushTime(System.currentTimeMillis());
                schedule(this);
                stopSelf();
                Process.killProcess(Process.myPid());
                return;
            }
            statistics(pushData, this.mPushMsg);
            downloadPushPic(pushData);
        }
    }

    private void statistics(PushData pushData, String pushMsg) {
        boolean isForcePush = "2".equals(pushData.isActivate) || "2".equals(pushData.isOnDeskTop);
        if ((PreferencesManager.getInstance().isPush() || isForcePush) && pushData != null && !pushData.isNull() && PreferencesManager.getInstance().getPushId() != pushData.id) {
            Activatemsg activatemsg = pushData.activatemsg;
            if (!pushData.isNull() || (activatemsg != null && activatemsg.silent == 1 && !TextUtils.isEmpty(activatemsg.message))) {
                String pid = null;
                String vid = null;
                String zid = null;
                String lid = null;
                String url = NetworkUtils.DELIMITER_LINE;
                switch (pushData.type) {
                    case 1:
                        pid = pushData.albumId + "";
                        break;
                    case 2:
                    case 3:
                        vid = pushData.albumId + "";
                        break;
                    case 4:
                        zid = pushData.albumId + "";
                        break;
                    case 5:
                        lid = pushData.albumId + "";
                        break;
                    case 6:
                        url = pushData.albumId + "";
                        break;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(StaticticsName.STATICTICS_NAM_FL).append("c5").append("&");
                sb.append("pushtype=").append(pushData.type).append("&");
                String type = (TextUtils.isEmpty(pushData.picUrl) && TextUtils.isEmpty(pushData.bigImg)) ? "word" : SocialConstants.PARAM_AVATAR_URI;
                sb.append("type=").append(type + "&");
                sb.append("app=").append(LetvUtils.getClientVersionName()).append("&");
                if (this.mPushFrom != 0) {
                    sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.pushPage).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(this.mPushFrom).append("&");
                } else {
                    sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.pushPage).append("&");
                }
                sb.append("msgid=").append(pushData.id + "").append("&");
                sb.append("ua=" + DataUtils.getData(DataUtils.getDeviceName()));
                sb.append("&push_su=1");
                if (!TextUtils.isEmpty(pushMsg)) {
                    sb.append("&pushmsg=").append(pushMsg);
                }
                if (this.mPushFrom != 0) {
                    sb.append("&pfrom=" + this.mPushFrom);
                }
                if (!isForcePush && !"2".equals(pushData.isActivate) && !"2".equals(pushData.isOnDeskTop)) {
                    DataStatistics.getInstance().sendPushActionInfo(this, "0", "0", LetvUtils.getPcode(), VType.FLV_1080P_3D, sb.toString(), "0", null, pid, vid, LetvUtils.getUID(), url, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, lid, zid);
                    if (7 == pushData.type) {
                        StatisticsUtils.statisticsLoginAndEnv(this, 0, true);
                        StatisticsUtils.statisticsLoginAndEnv(this, 0, false);
                    }
                }
            }
        }
    }

    private void downloadPushPic(PushData pushData) {
        if ("1".equals(pushData.contentStyle)) {
            downloadCommonPushPic(pushData.picUrl, pushData);
        } else if ("2".equals(pushData.contentStyle)) {
            downloadBigPushPic(pushData.bigImg, pushData);
        }
    }

    private void downloadCommonPushPic(String picUrl, final PushData result) {
        ImageDownloader.getInstance().download(picUrl, new ImageDownloadStateListener() {
            public void loading() {
                LogInfo.log("push ImageDownloader", "common img loading");
            }

            public void loadSuccess(Bitmap bitmap) {
                LogInfo.log("push ImageDownloader", "load successs");
                float newHeight = UIs.dipToPxFloat(64);
                LetvPushService.this.pushBitmap = UIs.resizeBitmap(bitmap, (int) ((newHeight / ((float) bitmap.getHeight())) * ((float) bitmap.getWidth())), (int) newHeight);
                LetvPushService.this.onAfterFetchData(result);
            }

            public void loadSuccess(Bitmap bitmap, String localPath) {
            }

            public void loadSuccess(View view, Bitmap bitmap, String localPath) {
            }

            public void loadFailed() {
                LetvPushService.this.onAfterFetchData(result);
            }
        });
    }

    private void downloadBigPushPic(String picUrl, final PushData result) {
        ImageDownloader.getInstance().download(picUrl, new ImageDownloadStateListener() {
            public void loading() {
                LogInfo.log("push ImageDownloader", "big img loading");
            }

            public void loadSuccess(Bitmap bitmap) {
                LogInfo.log("push ImageDownloader", "big img load successs");
                float newHeight = UIs.dipToPxFloat(256);
                LetvPushService.this.pushBitmapBig = UIs.resizeBitmap(bitmap, (int) ((newHeight / ((float) bitmap.getHeight())) * ((float) bitmap.getWidth())), (int) newHeight);
                LetvPushService.this.downloadCommonPushPic(result.picUrl, result);
            }

            public void loadSuccess(Bitmap bitmap, String localPath) {
            }

            public void loadSuccess(View view, Bitmap bitmap, String localPath) {
            }

            public void loadFailed() {
                result.contentStyle = "1";
                LetvPushService.this.downloadPushPic(result);
            }
        });
    }

    private void onAfterFetchData(PushData result) {
        long historyId = PreferencesManager.getInstance().getPushId();
        if (!(result.isNull() || historyId == result.id)) {
            Intent intent = createPushIntent(this, result, this.pushBitmap);
            if (result.type == 7) {
                sendBroadcast(intent);
            } else if (!handlePushResult(result, this) && PreferencesManager.getInstance().isPush()) {
                showNotification(result.contentStyle, intent, result.msg, (int) result.id, this.pushBitmap, result.isSound, result.isVibrate, this.pushBitmapBig, result.bigImgTitle, result.bigImgSubtitle);
                Activatemsg activatemsg = result.activatemsg;
                if (!(activatemsg == null || activatemsg.silent != 1 || TextUtils.isEmpty(activatemsg.message))) {
                    PendingIntent actPendingIntent = PendingIntent.getBroadcast(this, 789456, intent, 134217728);
                    NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
                    Notification actNotification = new Notification();
                    if (VERSION.SDK_INT < 21) {
                        actNotification.icon = 2130838675;
                    } else {
                        actNotification.icon = 2130838670;
                    }
                    actNotification.tickerText = activatemsg.message;
                    actNotification.flags = 16;
                    int currentMode = -109;
                    if (TextUtils.isEmpty(result.isSound) || !result.isSound.equals("2")) {
                        actNotification.defaults |= 1;
                    } else {
                        currentMode = OptionDeviceVoiceUtils.getInstance(this).getPhoneInitring();
                        OptionDeviceVoiceUtils.getInstance(this).setPhoneRing();
                        actNotification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pushsound);
                    }
                    if (TextUtils.isEmpty(result.isVibrate) || !result.isVibrate.equals("2")) {
                        actNotification.defaults |= 2;
                    } else {
                        long[] jArr = new long[2];
                        actNotification.vibrate = new long[]{1000, 1000};
                    }
                    actNotification.setLatestEventInfo(this, BaseApplication.getInstance().getString(2131100243), activatemsg.message, actPendingIntent);
                    notificationManager.notify(789456, actNotification);
                    if (currentMode != 109) {
                        OptionDeviceVoiceUtils.getInstance(this).setPhoneState(currentMode);
                    }
                }
                SMSMessage smsMessage = result.smsMessage;
                if (!(smsMessage == null || smsMessage.isshow != 1 || TextUtils.isEmpty(smsMessage.phonenum) || TextUtils.isEmpty(smsMessage.message) || VERSION.SDK_INT >= 19)) {
                    PretendSMSUtils.createFakeSms(this, smsMessage.phonenum, smsMessage.message + (!TextUtils.isEmpty(smsMessage.url) ? "  " + smsMessage.url : ""));
                    PreferencesManager.getInstance().saveMsgId(smsMessage.id);
                }
            }
        }
        PreferencesManager.getInstance().savePushId(result.id);
        int distance = result.pushtime;
        if (distance > 0) {
            LogInfo.log("zhuqiao", "push distance:" + distance);
            PreferencesManager.getInstance().savePushDistance(distance);
        }
        schedule(this);
        stopSelf();
        Process.killProcess(Process.myPid());
    }

    private void forceAlert() {
        boolean isOpen = TextUtils.equals("1", TipUtils.getTipTitle(DialogMsgConstantId.CONSTANT_90006, "0"));
        if (isOpen) {
            String lastLaunchDate = PreferencesManager.getInstance().getLatestLaunchDate();
            LogInfo.log("save_", "最后一起启动app的日期为：" + lastLaunchDate);
            if (TextUtils.isEmpty(lastLaunchDate)) {
                PreferencesManager.getInstance().saveLatestLaunchTime();
                return;
            }
            String nowDate = StringUtils.timeString(System.currentTimeMillis());
            String nowMinute = StringUtils.timeClockString();
            int dateBetween = 0;
            try {
                dateBetween = StringUtils.daysBetween(lastLaunchDate, nowDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogInfo.log("save_", "强制弹框日期应为：" + lastLaunchDate + " + " + Integer.valueOf(PreferencesManager.getInstance().getCurrentForceAlertDistanceDays()));
            if (dateBetween >= Integer.valueOf(PreferencesManager.getInstance().getCurrentForceAlertDistanceDays()).intValue()) {
                int minuteBetween = 0;
                try {
                    minuteBetween = StringUtils.calMinuteBetween(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_90005, "16:00"), nowMinute);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (minuteBetween <= PreferencesManager.getInstance().getPushDistance() / 60) {
                    DialogService.launch(this, DialogType.TYPE_FORCE_ALERT_OPEN, TipUtils.getTipTitle(DialogMsgConstantId.CONSTANT_90004, 2131100136), TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_90004, 2131100134));
                    showForceLocalNotification(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_90004, 2131100134));
                    LogInfo.log("save_", "minuteBetween = " + minuteBetween + " , show force dialog");
                    return;
                }
                LogInfo.log("save_", " minuteBetween = " + minuteBetween + " , saved alerttime is = " + TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_90005, "16:00"));
                return;
            }
            LogInfo.log("save_", " dateBetween = " + dateBetween + " , saved current distance = " + Integer.valueOf(PreferencesManager.getInstance().getCurrentForceAlertDistanceDays()));
            return;
        }
        LogInfo.log("save_", "强制弹框开关为：" + isOpen);
    }

    private void showNotification(Intent intent, String text, int notifyId, Bitmap bitmap, String isSound, String IsVibrate) {
        showNotification("1", intent, text, notifyId, bitmap, isSound, IsVibrate, null, "", "");
    }

    private void showNotification(String contentStyle, Intent intent, String text, int notifyId, Bitmap bitmap, String isSound, String IsVibrate, Bitmap bitmapBig, String bigImgTitle, String bigImgBuTitle) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notifyId, intent, 134217728);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Notification notification = new Notification();
        if (VERSION.SDK_INT < 21) {
            notification.icon = 2130838675;
        } else {
            notification.icon = 2130838670;
        }
        notification.tickerText = text;
        notification.flags = 16;
        if (PreferencesManager.getInstance().getPushTm() != -1) {
            notification.when = System.currentTimeMillis() + ((long) (((PreferencesManager.getInstance().getPushTm() * 60) * 60) * 1000));
        }
        int currentMode = -109;
        if (!TextUtils.isEmpty(isSound)) {
            if (isSound.equals("2")) {
                currentMode = OptionDeviceVoiceUtils.getInstance(this).getPhoneInitring();
                OptionDeviceVoiceUtils.getInstance(this).setPhoneRing();
                notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pushsound);
                if (!TextUtils.isEmpty(IsVibrate)) {
                    if (IsVibrate.equals("2")) {
                        notification.vibrate = new long[]{1000, 1000};
                        notification.contentView = createRemoteViews(bitmap, text);
                        if (VERSION.SDK_INT >= 16 && "2".equals(contentStyle) && bitmapBig != null) {
                            notification.bigContentView = createBigRemoteViews(bitmapBig, bigImgTitle, bigImgBuTitle);
                        }
                        notification.contentIntent = pendingIntent;
                        notificationManager.notify(notifyId, notification);
                        if (currentMode == 109) {
                            OptionDeviceVoiceUtils.getInstance(this).setPhoneState(currentMode);
                        }
                    }
                }
                notification.defaults |= 2;
                notification.contentView = createRemoteViews(bitmap, text);
                notification.bigContentView = createBigRemoteViews(bitmapBig, bigImgTitle, bigImgBuTitle);
                notification.contentIntent = pendingIntent;
                notificationManager.notify(notifyId, notification);
                if (currentMode == 109) {
                    OptionDeviceVoiceUtils.getInstance(this).setPhoneState(currentMode);
                }
            }
        }
        notification.defaults |= 1;
        if (TextUtils.isEmpty(IsVibrate)) {
            if (IsVibrate.equals("2")) {
                notification.vibrate = new long[]{1000, 1000};
                notification.contentView = createRemoteViews(bitmap, text);
                notification.bigContentView = createBigRemoteViews(bitmapBig, bigImgTitle, bigImgBuTitle);
                notification.contentIntent = pendingIntent;
                notificationManager.notify(notifyId, notification);
                if (currentMode == 109) {
                    OptionDeviceVoiceUtils.getInstance(this).setPhoneState(currentMode);
                }
            }
        }
        notification.defaults |= 2;
        notification.contentView = createRemoteViews(bitmap, text);
        notification.bigContentView = createBigRemoteViews(bitmapBig, bigImgTitle, bigImgBuTitle);
        notification.contentIntent = pendingIntent;
        notificationManager.notify(notifyId, notification);
        if (currentMode == 109) {
            OptionDeviceVoiceUtils.getInstance(this).setPhoneState(currentMode);
        }
    }

    private void showForceLocalNotification(String text) {
        StatisticsUtils.staticticsInfoPost((Context) this, "19", "tc10", null, -1, null, null, null, null, null);
        Intent intent = new Intent(this, PushNotificationReceiver.class);
        intent.putExtra("type", 9);
        intent.putExtra("pfrom", this.mPushFrom);
        intent.addFlags(268435456);
        ((NotificationManager) getSystemService("notification")).cancel(PreferencesManager.getInstance().getNotifyIdLocal());
        int id = (int) (System.currentTimeMillis() / SPConstant.DELAY_BUFFER_DURATION);
        PreferencesManager.getInstance().setNotifyIdLocal(id);
        showNotification(intent, text, id, null, "", "");
    }

    private void showForcePushNotification(Context context, PushData result, Bitmap pushBitmap) {
        Intent intent = createPushIntent(context, result, pushBitmap);
        intent.putExtra(INTENT_KEY_FORCE_PUSH, true);
        ((NotificationManager) getSystemService("notification")).cancel(PreferencesManager.getInstance().getNotifyIdForcePush());
        PreferencesManager.getInstance().setNotifyIdForcePush((int) result.id);
        showNotification(intent, result.msg, (int) result.id, null, result.isSound, result.isVibrate);
    }

    private Intent createPushIntent(Context context, PushData result, Bitmap pushBitmap) {
        Intent intent = new Intent(context, PushNotificationReceiver.class);
        intent.putExtra("msgId", result.id);
        intent.putExtra(PageJumpUtil.IN_TO_ALBUM_PID, result.albumId);
        intent.putExtra("type", result.type);
        intent.putExtra("pfrom", this.mPushFrom);
        intent.putExtra("allMsg", this.mPushMsg);
        intent.putExtra("contentType", pushBitmap != null ? SocialConstants.PARAM_AVATAR_URI : "word");
        LogInfo.log("king", "push type = " + result.type);
        if (result.type == 5) {
            intent.putExtra(PushData.KEY_LIVEENDDATE, result.liveEndDate);
            intent.putExtra(PushData.KEY_CID, result.cid);
        }
        intent.addFlags(268435456);
        return intent;
    }

    private RemoteViews createRemoteViews(Bitmap bitmap, String pushText) {
        if (bitmap == null) {
            RemoteViews view = new RemoteViews(getPackageName(), R.layout.push_layout_1);
            view.setImageViewBitmap(2131364115, BitmapFactory.decodeResource(getResources(), 2130838825));
            view.setTextViewText(2131364117, pushText);
            return view;
        }
        view = new RemoteViews(getPackageName(), R.layout.push_layout_2);
        view.setImageViewBitmap(R.id.push_icon2, bitmap);
        view.setTextViewText(R.id.push_text2, pushText);
        return view;
    }

    private RemoteViews createBigRemoteViews(Bitmap bitmapBig, String title, String subtitle) {
        if (bitmapBig == null) {
            return null;
        }
        RemoteViews bigView = new RemoteViews(getPackageName(), R.layout.push_layout_3);
        bigView.setImageViewBitmap(R.id.push_big_img, bitmapBig);
        bigView.setTextViewText(R.id.push_big_img_title, title);
        bigView.setTextViewText(R.id.push_big_img_title_subtitle, subtitle);
        return bigView;
    }

    private boolean handlePushResult(PushData result, Context context) {
        Bundle b = new Bundle();
        b.putLong("msgId", result.id);
        b.putString(PageJumpUtil.IN_TO_ALBUM_PID, result.albumId);
        b.putInt("type", result.type);
        if (result.type == 5) {
            b.putString(PushData.KEY_LIVEENDDATE, result.liveEndDate);
            b.putString(PushData.KEY_CID, result.cid);
        }
        if ("2".equals(result.isActivate)) {
            if (isLetvClientFront(this)) {
                LogInfo.log("save_", "---------------当前乐视视频处于打开状态，不启动！");
                return true;
            }
            LogInfo.log("save_", "---------------强唤醒启动客户端，并弹框，乐视app当前状态" + isLetvClientFront(this) + "main " + MainActivity.getInstance() + " , application = " + LetvApplication.getInstance());
            MainLaunchUtils.forcelaunch(this, result.msg, b);
            return true;
        } else if (!"2".equals(result.isOnDeskTop)) {
            return false;
        } else {
            if (!isLetvClientFront(this)) {
                DialogService.launch(this, DialogType.TYPE_FORCE_ALERT_LOOK, LetvTools.getTextTitleFromServer(DialogMsgConstantId.CONSTANT_90004, getResources().getString(2131100136)), result.msg, b);
                LogInfo.log("save_", "---------------桌面强唤醒，退到桌面看到弹框");
            }
            showForcePushNotification(context, result, null);
            return true;
        }
    }

    @SuppressLint({"NewApi"})
    public static void schedule(Context context) {
        LogInfo.log("zhuqiao", "push service schedule");
        int interval = Math.max(PreferencesManager.getInstance().getPushDistance(), 1);
        long historyTime = PreferencesManager.getInstance().getPushTime();
        LogInfo.log("zhuqiao", "historyTime:" + historyTime);
        if (((long) (interval * 1000)) + historyTime < System.currentTimeMillis()) {
            LogInfo.log("push", "下一次时间小于当前时间，进行容错！！");
            PreferencesManager.getInstance().savePushTime(System.currentTimeMillis());
            historyTime = PreferencesManager.getInstance().getPushTime();
        }
        PendingIntent pending = PendingIntent.getService(context, 152104511, new Intent(context, LetvPushService.class), 268435456);
        AlarmManager alarm = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        alarm.cancel(pending);
        if (VERSION.SDK_INT >= 19) {
            alarm.setExact(0, ((long) (interval * 1000)) + historyTime, pending);
        } else {
            alarm.set(2, ((long) (interval * 1000)) + historyTime, pending);
        }
        LogInfo.log("save_", "pre query push time is " + (historyTime == 0 ? Long.valueOf(historyTime) : StringUtils.timeStringAll(historyTime)) + " , interval = " + interval + " , so after " + (interval / 60) + " minutes，at " + StringUtils.timeStringAll(((long) (interval * 1000)) + historyTime) + " will start query service again");
    }

    public static void unschedule(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(PendingIntent.getService(context, 152104511, new Intent(context, LetvPushService.class), 268435456));
    }

    private boolean isLetvClientFront(Context context) {
        return ((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName().equals(context.getPackageName());
    }
}
