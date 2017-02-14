package com.letv.android.client.push;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.android.client.push.LetvWindowDialog.LetvWindowDialogListener;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.android.client.utils.UIs;
import com.letv.core.bean.AlbumInfo.Channel;
import com.letv.core.bean.PushData;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogService extends Service {
    public static final String FORCE_ALERT_MESSAGE = "message";
    public static final String FORCE_ALERT_TITLE = "title";
    public static final String FORCE_ALERT_TYPE = "dialog_type";
    public static int MOVE_LENGTH = 15;
    public static final int NOTIFICATION_ID = 109001;
    private static final Class<?>[] mSetForegroundSignature = new Class[]{Boolean.TYPE};
    private static final Class<?>[] mStartForegroundSignature = new Class[]{Integer.TYPE, Notification.class};
    private static final Class<?>[] mStopForegroundSignature = new Class[]{Boolean.TYPE};
    private float StartX;
    private float StartY;
    private boolean isAdded;
    private Bundle mBundle;
    private DialogType mDialogType;
    private Handler mHandler;
    int mHeight;
    private LetvWindowDialogListener mListener;
    private NotificationManager mNM;
    private Method mSetForeground;
    private Object[] mSetForegroundArgs;
    private Method mStartForeground;
    private Object[] mStartForegroundArgs;
    private Method mStopForeground;
    private Object[] mStopForegroundArgs;
    private float mTouchStartX;
    private float mTouchStartY;
    private int state;
    private LetvWindowDialog view;
    private WindowManager wm;
    private LayoutParams wmParams;
    private float x;
    private float y;

    public enum DialogType {
        TYPE_FORCE_ALERT_OPEN,
        TYPE_FORCE_ALERT_LOOK
    }

    public DialogService() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.wm = null;
        this.wmParams = null;
        this.mHeight = 0;
        this.mSetForegroundArgs = new Object[1];
        this.mStartForegroundArgs = new Object[2];
        this.mStopForegroundArgs = new Object[1];
        this.mListener = new LetvWindowDialogListener(this) {
            final /* synthetic */ DialogService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void okCallBack(Context context) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                switch (this.this$0.mDialogType) {
                    case TYPE_FORCE_ALERT_OPEN:
                        LogInfo.log("save_", "打开客户端");
                        MainLaunchUtils.launch(context, true);
                        notificationManager.cancel(PreferencesManager.getInstance().getNotifyIdLocal());
                        StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h61", this.this$0.getResources().getString(2131100135), 1, null, null, null, null, null);
                        break;
                    case TYPE_FORCE_ALERT_LOOK:
                        StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h62", this.this$0.getResources().getString(2131100135), 1, null, null, null, null, null);
                        Intent intent = new Intent(context, PushNotificationReceiver.class);
                        intent.putExtra("msgId", this.this$0.mBundle.getLong("msgId"));
                        intent.putExtra(PageJumpUtil.IN_TO_ALBUM_PID, this.this$0.mBundle.getString(PageJumpUtil.IN_TO_ALBUM_PID));
                        intent.putExtra("type", this.this$0.mBundle.getInt("type"));
                        intent.putExtra(LetvPushService.INTENT_KEY_FORCE_PUSH, true);
                        if (this.this$0.mBundle.getInt("type") == 5) {
                            intent.putExtra(PushData.KEY_LIVEENDDATE, this.this$0.mBundle.getString(PushData.KEY_LIVEENDDATE));
                            intent.putExtra(PushData.KEY_CID, this.this$0.mBundle.getString(PushData.KEY_CID));
                        }
                        if (this.this$0.mBundle.getInt("type") != 6) {
                            intent.putExtra("force", true);
                            intent.putExtra("fl", "h62");
                            intent.putExtra("wz", 2);
                        }
                        intent.addFlags(268435456);
                        context.sendBroadcast(intent);
                        notificationManager.cancel(PreferencesManager.getInstance().getNotifyIdForcePush());
                        break;
                }
                DialogService.closePipView(context);
            }

            public void cancelCallBack(Context context) {
                switch (this.this$0.mDialogType) {
                    case TYPE_FORCE_ALERT_OPEN:
                        StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h61", this.this$0.getResources().getString(2131099798), 0, null, null, null, null, null);
                        break;
                    case TYPE_FORCE_ALERT_LOOK:
                        StatisticsUtils.staticticsInfoPost(this.this$0, "0", "h62", this.this$0.getResources().getString(2131099798), 0, null, null, null, null, null);
                        break;
                }
                DialogService.closePipView(context);
            }
        };
        this.isAdded = false;
        this.mHandler = new Handler(this) {
            final /* synthetic */ DialogService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if (this.this$0.isHome()) {
                            if (!this.this$0.isAdded) {
                                this.this$0.wm.addView(this.this$0.view, this.this$0.wmParams);
                                this.this$0.isAdded = true;
                            }
                        } else if (this.this$0.isAdded) {
                            this.this$0.wm.removeView(this.this$0.view);
                            this.this$0.isAdded = false;
                        }
                        this.this$0.mHandler.sendEmptyMessageDelayed(1, 1000);
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public static void closePipView(Context context) {
        if (context != null) {
            Intent serviceStop = new Intent();
            serviceStop.setClass(context, DialogService.class);
            context.stopService(serviceStop);
        }
    }

    public static void launch(Context context, DialogType type, String title, String message) {
        Intent intent = new Intent(context, DialogService.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra(FORCE_ALERT_TYPE, type.ordinal());
        LogInfo.log("save_", "FORCE_ALERT_TYPE, type.ordinal() " + type.ordinal());
        context.startService(intent);
    }

    public static void launch(Context context, DialogType type, String title, String message, Bundle bundle) {
        Intent intent = new Intent(context, DialogService.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra(FORCE_ALERT_TYPE, type.ordinal());
        intent.putExtras(bundle);
        context.startService(intent);
    }

    public void onCreate() {
        super.onCreate();
        this.mNM = (NotificationManager) getSystemService("notification");
        try {
            this.mStartForeground = getClass().getMethod("startForeground", mStartForegroundSignature);
            this.mStopForeground = getClass().getMethod("stopForeground", mStopForegroundSignature);
        } catch (NoSuchMethodException e) {
            this.mStopForeground = null;
            this.mStartForeground = null;
            try {
                this.mSetForeground = getClass().getMethod("setForeground", mSetForegroundSignature);
            } catch (NoSuchMethodException e2) {
                throw new IllegalStateException("OS doesn't have Service.startForeground OR Service.setForeground!");
            }
        }
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent == null) {
            stopSelf();
            return;
        }
        if (!(this.wm == null || this.view == null)) {
            try {
                this.wm.removeView(this.view);
                this.isAdded = false;
            } catch (Exception e) {
            }
            this.view = null;
        }
        this.mDialogType = intent.getIntExtra(FORCE_ALERT_TYPE, 0) == 0 ? DialogType.TYPE_FORCE_ALERT_OPEN : DialogType.TYPE_FORCE_ALERT_LOOK;
        if (this.mDialogType == DialogType.TYPE_FORCE_ALERT_OPEN) {
            LogInfo.log("save_", "弹框类型为：本地提醒");
            StatisticsUtils.staticticsInfoPost((Context) this, "19", "h61", null, -1, null, null, null, null, null);
            refreshForceAlertDistance();
        } else if (this.mDialogType == DialogType.TYPE_FORCE_ALERT_LOOK) {
            LogInfo.log("save_", "弹框类型为：强制推送提醒");
            StatisticsUtils.staticticsInfoPost((Context) this, "19", "h62", null, -1, null, null, null, null, null);
            this.mBundle = intent.getExtras();
        }
        createView(intent.getStringExtra("title"), intent.getStringExtra("message"));
    }

    void invokeMethod(Method method, Object[] args) {
        try {
            method.invoke(this, args);
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e2) {
        }
    }

    void stopForegroundCompat(int id) {
        if (this.mStopForeground != null) {
            this.mStopForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(this.mStopForeground, this.mStopForegroundArgs);
            return;
        }
        this.mNM.cancel(id);
        this.mSetForegroundArgs[0] = Boolean.FALSE;
        invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
    }

    private void createView(String title, String msg) {
        this.view = new LetvWindowDialog(this, this.mListener);
        this.view.setTitleContent(title, msg);
        this.wm = (WindowManager) getApplicationContext().getSystemService("window");
        if (this.wmParams == null) {
            this.wmParams = new LayoutParams();
            this.wmParams.type = Channel.TYPE_WEBVIEW;
            this.wmParams.flags = 40;
            this.wmParams.alpha = 1.0f;
            this.wmParams.gravity = 17;
            this.wmParams.x = 0;
            this.wmParams.y = 0;
            int width = UIsUtils.getDisplayWidth();
            int height = UIs.zoomHeight(244);
            this.mHeight = height;
            this.wmParams.width = (width * 4) / 5;
            this.wmParams.height = height;
            this.wmParams.format = 1;
        }
        this.view.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ DialogService this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.this$0.x = event.getRawX();
                this.this$0.y = event.getRawY() - 25.0f;
                switch (event.getAction()) {
                    case 0:
                        this.this$0.state = 0;
                        this.this$0.StartX = this.this$0.x;
                        this.this$0.StartY = this.this$0.y;
                        this.this$0.mTouchStartX = event.getX();
                        this.this$0.mTouchStartY = event.getY();
                        break;
                    case 1:
                        this.this$0.state = 1;
                        this.this$0.mTouchStartX = this.this$0.mTouchStartY = 0.0f;
                        break;
                    case 2:
                        this.this$0.state = 2;
                        break;
                }
                return false;
            }
        });
        this.mHandler.sendEmptyMessageDelayed(1, SPConstant.DELAY_BUFFER_DURATION);
    }

    public void onDestroy() {
        super.onDestroy();
        if (!(this.wm == null || this.view == null || !this.isAdded)) {
            this.wm.removeView(this.view);
        }
        this.mHandler.removeMessages(1);
        this.isAdded = false;
        stopForegroundCompat(109001);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private List<String> getHomes() {
        List<String> names = new ArrayList();
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        for (ResolveInfo ri : packageManager.queryIntentActivities(intent, 65536)) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    public boolean isHome() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService("activity");
        List<String> homeList = getHomes();
        List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        if (homeList != null) {
            return homeList.contains(((RunningTaskInfo) rti.get(0)).topActivity.getPackageName());
        }
        return false;
    }

    private void refreshForceAlertDistance() {
        String days = TipUtils.getTipTitle(DialogMsgConstantId.CONSTANT_90005, "");
        if (!TextUtils.isEmpty(days) && days.trim().length() >= 1) {
            String currentDistance = PreferencesManager.getInstance().getCurrentForceAlertDistanceDays();
            String[] arrayDay = days.split(",");
            LogInfo.log("save_", "refreshForceAlertDistance arrayDay = " + Arrays.toString(arrayDay));
            for (int i = 0; i < arrayDay.length; i++) {
                if (arrayDay[i].equals(currentDistance)) {
                    if (i == arrayDay.length - 1) {
                        PreferencesManager.getInstance().setForceAlertDistanceDays(DialogMsgConstantId.CONSTANT_10000);
                        LogInfo.log("save_", "间隔了" + arrayDay[i] + "天才提醒，已经是最后一个提醒了，以后不再提醒");
                    } else {
                        try {
                            LogInfo.log("save_", "从间隔" + arrayDay[i] + "天，更新到间隔" + arrayDay[i + 1] + " 天！下次提醒时间为：" + StringUtils.timeString(System.currentTimeMillis() + ((long) (86400000 * Integer.valueOf(arrayDay[i + 1]).intValue()))) + " " + TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_90005, "16:00"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        PreferencesManager.getInstance().setForceAlertDistanceDays(arrayDay[i + 1]);
                    }
                }
            }
        }
    }
}
