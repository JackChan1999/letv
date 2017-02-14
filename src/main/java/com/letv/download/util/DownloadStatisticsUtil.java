package com.letv.download.util;

import android.content.Context;
import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.bean.DownloadVideo;
import java.util.regex.Pattern;

public class DownloadStatisticsUtil {
    private static final String TAG = DownloadStatisticsUtil.class.getSimpleName();
    private static Context sContext = BaseApplication.getInstance();
    private long mBeforeTime;
    private long mLastDownloadSize;

    public static class DownloadPauseStatistics {
        public static final int FINISH_PAUSE = 5;
        public static final int NET_CHANGE_PAUSE = 3;
        public static final int NET_ERROR_PAUSE = 4;
        public static final int NET_STOP_PAUSE = 2;
        public static final int NORMAL_PAUSE = 1;

        private static String createAp(String speed, int type) {
            StringBuffer sb = new StringBuffer();
            sb.append("speed=").append(speed).append("&").append("type=").append(type);
            LogInfo.LogStatistics("createAp sb : " + sb);
            return sb.toString();
        }

        public static void downloadPauseReport(String speed, int type) {
            LogInfo.LogStatistics("downloadPauseReport speed " + speed);
            try {
                if (TextUtils.isEmpty(speed)) {
                    speed = "";
                } else {
                    speed = Pattern.compile("[^(.0-9)]").matcher(speed).replaceAll("");
                }
                DataStatistics.getInstance().sendActionInfo(DownloadStatisticsUtil.sContext, "0", "0", LetvUtils.getPcode(), "2", createAp(speed, type), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public DownloadStatisticsUtil(long beforeTime, long lastDownloadSize) {
        this.mBeforeTime = beforeTime;
        this.mLastDownloadSize = lastDownloadSize;
    }

    public void statisticDownloadSpeed(DownloadVideo downloadVideo) {
        if (downloadVideo != null) {
            long downloadSize = downloadVideo.downloaded;
            if (System.currentTimeMillis() - this.mBeforeTime > 30000) {
                String speed = DownloadUtil.calculateDownloadSpeed(this.mBeforeTime, System.currentTimeMillis(), downloadSize - this.mLastDownloadSize);
                LogInfo.log(TAG, "statisticDownloadSpeed speed " + speed);
                StatisticsUtils.statisticsActionInfo(sContext, PageIdConstant.downloadingPage, "2", null, downloadVideo.name, -1, "avg_speed=" + speed, String.valueOf(downloadVideo.cid), String.valueOf(downloadVideo.pid), String.valueOf(downloadVideo.vid), null, null);
                this.mBeforeTime = System.currentTimeMillis();
                this.mLastDownloadSize = downloadSize;
            }
        }
    }
}
