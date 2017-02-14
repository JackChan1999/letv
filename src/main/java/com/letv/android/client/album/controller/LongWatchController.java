package com.letv.android.client.album.controller;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.commonlib.view.LongWatchNoticeDialog;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import java.util.Timer;

public class LongWatchController {
    public static final long DEFAULT_LONG_WATCH_PERIOD = 10800000;
    private static final int TIMER_PERIOD = 5000;
    private long longWatchPeriod = 10800000;
    private Context mContext;
    private Handler mHandler = new Handler();
    private int mTime;
    private Timer mWatchTimer = null;

    public LongWatchController(Context context) {
        this.mContext = context;
        if (this.longWatchPeriod == 10800000) {
            TipBean tipBean = TipUtils.getTipBean(DialogMsgConstantId.LONGTIME_TIP);
            if (!(tipBean == null || TextUtils.isEmpty(tipBean.message))) {
                LogInfo.log("zhuqiao", "long watch period:" + tipBean.message);
                this.longWatchPeriod = (long) (((BaseTypeUtils.stof(tipBean.message, 3.0f) * 60.0f) * 60.0f) * 1000.0f);
            }
        }
        startTimer();
    }

    private void startTimer() {
        this.mWatchTimer = new Timer();
        this.mWatchTimer.schedule(new 1(this), 0, 5000);
    }

    public void reCalcTime() {
        this.mTime = 0;
        if (AlbumPlayActivity.sIsShowingLongwatch) {
            LongWatchNoticeDialog.dismissDialog();
            AlbumPlayActivity.sIsShowingLongwatch = false;
        }
    }

    private void onLongWatch() {
        if (this.mContext instanceof AlbumPlayActivity) {
            AlbumPlayActivity activity = this.mContext;
            if (!activity.mIsPlayingDlna) {
                if (activity.getFlow() == null || activity.getFlow().mIsDownloadFile || NetworkUtils.isNetworkAvailable()) {
                    LogInfo.log("zhuqiao", "onLongWatch");
                    AlbumPlayActivity.sIsShowingLongwatch = true;
                    if (activity.getController() != null) {
                        activity.getController().pause(false);
                    }
                    LongWatchNoticeDialog.show(new 2(this, activity), this.mContext);
                    LogInfo.LogStatistics("long watch");
                    StatisticsUtils.staticticsInfoPost(this.mContext, "19", "c68", null, 6, null, PageIdConstant.fullPlayPage, null, null, null, null, null);
                }
            }
        }
    }

    public void onDestory() {
        if (this.mWatchTimer != null) {
            this.mWatchTimer.cancel();
            this.mWatchTimer.purge();
            this.mWatchTimer = null;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        LongWatchNoticeDialog.onDestory();
    }
}
