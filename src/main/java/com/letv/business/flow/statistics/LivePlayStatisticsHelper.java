package com.letv.business.flow.statistics;

import android.content.Context;
import com.letv.core.BaseApplication;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.util.DataUtils;

public class LivePlayStatisticsHelper {
    private Context mContext = BaseApplication.getInstance();
    private LivePlayStatisticsHelperCallBack mHelperCallBack = new LivePlayStatisticsHelperCallBack() {
        public void statisticsTimeToPlay() {
            LivePlayStatisticsHelper.this.statisticsTimeToPlays();
        }

        public void statisticsTimeAction(int time) {
            LivePlayStatisticsHelper.this.statisticsPlayActions("time", (long) time);
        }

        public void statisticsEndAction() {
            LivePlayStatisticsHelper.this.statisticsPlayActions("end");
        }
    };
    public StatisticsInfo mStatisticsInfo = new StatisticsInfo();
    private LivePlayingHandlerThread mThread = new LivePlayingHandlerThread(this.mHelperCallBack);

    public interface LivePlayStatisticsHelperCallBack {
        void statisticsEndAction();

        void statisticsTimeAction(int i);

        void statisticsTimeToPlay();
    }

    public void startTimeStatistics() {
        this.mThread.start();
    }

    public void pauseTimeStatistics() {
        this.mThread.pause();
    }

    public void stopTimeStatistics() {
        this.mThread.stopThread(this.mStatisticsInfo.mStatus != 2);
        if (this.mStatisticsInfo.mIsPlayingAds) {
            statisticsTimeToPlays();
        }
    }

    public void clear() {
        this.mStatisticsInfo = new StatisticsInfo();
    }

    public void resetUuid() {
        this.mStatisticsInfo.mUuid = "";
        this.mStatisticsInfo.mInterruptNum = 0;
    }

    public void destroy() {
        this.mStatisticsInfo = new StatisticsInfo();
        this.mThread.stopThread(false);
        this.mThread = null;
        this.mHelperCallBack = null;
    }

    public void setIsBlocking(boolean isBlocking) {
        this.mThread.setIsBlocking(isBlocking);
    }

    public void statisticsPlayActions(String action) {
        statisticsPlayActions(action, 0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void statisticsPlayActions(java.lang.String r103, long r104) {
        /*
        r102 = this;
        r17 = "1";
        r26 = "-";
        r101 = "-";
        r100 = "-";
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mLiveId;	 Catch:{ Exception -> 0x0309 }
        r29 = r0;
        r98 = "-";
        r97 = "-";
        r2 = r102.isInLunBoOrWeiShi();	 Catch:{ Exception -> 0x0309 }
        if (r2 != 0) goto L_0x02ef;
    L_0x001a:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLiveRenmenBase;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0050;
    L_0x0022:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLiveRenmenBase;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.ch;	 Catch:{ Exception -> 0x0309 }
        r26 = r0;
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLiveRenmenBase;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.level2;	 Catch:{ Exception -> 0x0309 }
        r97 = com.letv.datastatistics.util.DataUtils.getTrimData(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLiveRenmenBase;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.level1;	 Catch:{ Exception -> 0x0309 }
        r98 = com.letv.datastatistics.util.DataUtils.getTrimData(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLiveRenmenBase;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.title;	 Catch:{ Exception -> 0x0309 }
        r100 = com.letv.datastatistics.util.DataUtils.getTrimData(r2);	 Catch:{ Exception -> 0x0309 }
    L_0x0050:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mIsLunboWeiData;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x02eb;
    L_0x0058:
        r17 = "2";
    L_0x005a:
        r99 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r99.<init>();	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "gslb=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mIsGslb;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x02f3;
    L_0x0072:
        r2 = 1;
    L_0x0073:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&cload=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mIsCload;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x02f6;
    L_0x0093:
        r2 = 1;
    L_0x0094:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.isPush();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x02f9;
    L_0x00ab:
        r2 = "&push=1";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&type=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.core.utils.StatisticsUtils.sStatisticsPushData;	 Catch:{ Exception -> 0x0309 }
        r3 = r3.mContentType;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&pushtype=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.core.utils.StatisticsUtils.sStatisticsPushData;	 Catch:{ Exception -> 0x0309 }
        r3 = r3.mType;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&pushmsg=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.core.utils.StatisticsUtils.sStatisticsPushData;	 Catch:{ Exception -> 0x0309 }
        r3 = r3.mAllMsg;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
    L_0x0106:
        r2 = "&videoSend=CDN";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = "&vformat=m3u8";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&level1=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r0 = r97;
        r2 = r2.append(r0);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&level2=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r0 = r98;
        r2 = r2.append(r0);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&title=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r0 = r100;
        r2 = r2.append(r0);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&speed=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.core.utils.StatisticsUtils.getSpeed();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&sdk_ver=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.component.player.core.LetvMediaPlayerManager.getInstance();	 Catch:{ Exception -> 0x0309 }
        r3 = r3.getSdkVersion();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&cpu=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.component.player.utils.NativeInfos.getCPUClock();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = "ios";
        r3 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0309 }
        r3 = r3.getVideoFormat();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x030e;
    L_0x01ca:
        r2 = "&cs=m3u8";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
    L_0x01d1:
        r2 = "&su=1";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = "&time=";
        r0 = r99;
        r2 = r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r3 = "yyyyMMdd_HH:mm:ss";
        r3 = com.letv.core.utils.StringUtils.timeClockString(r3);	 Catch:{ Exception -> 0x0309 }
        r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&vip=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.isVip();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0327;
    L_0x01ff:
        r2 = 1;
    L_0x0200:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = "&replaytype=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r3 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r3 = r3.mReplayType;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = "&vip=";
        r0 = r99;
        r3 = r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.isVip();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x032a;
    L_0x023d:
        r2 = 1;
    L_0x023e:
        r3.append(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mPlayRef;	 Catch:{ Exception -> 0x0309 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0258;
    L_0x024d:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r3 = -1;
        r3 = com.letv.core.utils.StatisticsUtils.getPlayInfoRef(r3);	 Catch:{ Exception -> 0x0309 }
        r2.mPlayRef = r3;	 Catch:{ Exception -> 0x0309 }
    L_0x0258:
        r94 = 0;
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mPlayAdFragment;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x026c;
    L_0x0262:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mPlayAdFragment;	 Catch:{ Exception -> 0x0309 }
        r94 = r2.getAdsPlayFirstFrameTime();	 Catch:{ Exception -> 0x0309 }
    L_0x026c:
        r61 = 0;
        r2 = 0;
        r2 = (r94 > r2 ? 1 : (r94 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x028b;
    L_0x0274:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0309 }
        r2.<init>();	 Catch:{ Exception -> 0x0309 }
        r3 = com.letv.core.utils.StringUtils.staticticsLoadTimeInfoFormat(r94);	 Catch:{ Exception -> 0x0309 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0309 }
        r61 = r2.toString();	 Catch:{ Exception -> 0x0309 }
    L_0x028b:
        r63 = 0;
        r30 = "";
        r2 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x02ab;
    L_0x0295:
        r2 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.getCdeHelper();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x02ab;
    L_0x029f:
        r2 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.getCdeHelper();	 Catch:{ Exception -> 0x0309 }
        r30 = r2.getServiceVersion();	 Catch:{ Exception -> 0x0309 }
    L_0x02ab:
        r31 = "3000";
        r32 = new com.letv.datastatistics.bean.StatisticsPlayInfo;	 Catch:{ Exception -> 0x0309 }
        r32.<init>();	 Catch:{ Exception -> 0x0309 }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0309 }
        r0 = r32;
        r0.setcTime(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mIpt;	 Catch:{ Exception -> 0x0309 }
        r0 = r32;
        r0.setIpt(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r3 = r0.mContext;	 Catch:{ Exception -> 0x0309 }
        r11 = r2.getUuidTime(r3);	 Catch:{ Exception -> 0x0309 }
        r2 = "init";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x03a1;
    L_0x02dc:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLastAction;	 Catch:{ Exception -> 0x0309 }
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x032d;
    L_0x02ea:
        return;
    L_0x02eb:
        r17 = "1";
        goto L_0x005a;
    L_0x02ef:
        r17 = "2";
        goto L_0x005a;
    L_0x02f3:
        r2 = 0;
        goto L_0x0073;
    L_0x02f6:
        r2 = 0;
        goto L_0x0094;
    L_0x02f9:
        r2 = "&push=0";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        r2 = "&pushtype=-";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        goto L_0x0106;
    L_0x0309:
        r93 = move-exception;
        r93.printStackTrace();
        goto L_0x02ea;
    L_0x030e:
        r2 = "no";
        r3 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0309 }
        r3 = r3.getVideoFormat();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.equals(r3);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x01d1;
    L_0x031e:
        r2 = "&cs=mp4";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        goto L_0x01d1;
    L_0x0327:
        r2 = 0;
        goto L_0x0200;
    L_0x032a:
        r2 = 0;
        goto L_0x023e;
    L_0x032d:
        r2 = com.letv.datastatistics.DataStatistics.getInstance();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r3 = r0.mContext;	 Catch:{ Exception -> 0x0309 }
        r4 = "0";
        r5 = "0";
        r7 = "0";
        r8 = "0";
        r9 = "-";
        r10 = com.letv.core.utils.LetvUtils.getUID();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r6 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r12 = r6.mCid;	 Catch:{ Exception -> 0x0309 }
        r13 = "-";
        r14 = java.net.URLEncoder.encode(r101);	 Catch:{ Exception -> 0x0309 }
        r15 = "-";
        r16 = "-";
        r0 = r102;
        r6 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r6 = r6.mVt;	 Catch:{ Exception -> 0x0309 }
        r18 = com.letv.datastatistics.util.DataUtils.getTrimData(r6);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r6 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r6.mRealUrl;	 Catch:{ Exception -> 0x0309 }
        r19 = r0;
        r0 = r102;
        r6 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r6.mPlayRef;	 Catch:{ Exception -> 0x0309 }
        r20 = r0;
        r21 = r99.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r6 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r6.mStation;	 Catch:{ Exception -> 0x0309 }
        r22 = r0;
        r23 = "-";
        r24 = com.letv.core.utils.LetvUtils.getPcode();	 Catch:{ Exception -> 0x0309 }
        r6 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0309 }
        r6 = r6.isLogin();	 Catch:{ Exception -> 0x0309 }
        if (r6 == 0) goto L_0x039e;
    L_0x0389:
        r25 = 0;
    L_0x038b:
        r27 = "-";
        r28 = 0;
        r6 = r103;
        r2.sendLivePlayInfo25NewInit(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32);	 Catch:{ Exception -> 0x0309 }
    L_0x0394:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r103;
        r2.mLastAction = r0;	 Catch:{ Exception -> 0x0309 }
        goto L_0x02ea;
    L_0x039e:
        r25 = 1;
        goto L_0x038b;
    L_0x03a1:
        r2 = "play";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0477;
    L_0x03ac:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLastAction;	 Catch:{ Exception -> 0x0309 }
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 != 0) goto L_0x02ea;
    L_0x03ba:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mStatus;	 Catch:{ Exception -> 0x0309 }
        r3 = 1;
        if (r2 == r3) goto L_0x02ea;
    L_0x03c3:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mIsPay;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x046d;
    L_0x03cb:
        r2 = 2;
    L_0x03cc:
        r0 = r32;
        r0.setPay(r2);	 Catch:{ Exception -> 0x0309 }
        r96 = 0;
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mAdCount;	 Catch:{ Exception -> 0x0309 }
        if (r2 <= 0) goto L_0x03e6;
    L_0x03db:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mAdCount;	 Catch:{ Exception -> 0x0309 }
        r3 = 1;
        if (r2 != r3) goto L_0x0470;
    L_0x03e4:
        r96 = 2;
    L_0x03e6:
        r0 = r32;
        r1 = r96;
        r0.setJoint(r1);	 Catch:{ Exception -> 0x0309 }
        r33 = com.letv.datastatistics.DataStatistics.getInstance();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r0 = r0.mContext;	 Catch:{ Exception -> 0x0309 }
        r34 = r0;
        r35 = "0";
        r36 = "0";
        r38 = "0";
        r39 = "0";
        r40 = "-";
        r41 = com.letv.core.utils.LetvUtils.getUID();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mCid;	 Catch:{ Exception -> 0x0309 }
        r43 = r0;
        r44 = "-";
        r45 = java.net.URLEncoder.encode(r101);	 Catch:{ Exception -> 0x0309 }
        r46 = "-";
        r47 = "-";
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mVt;	 Catch:{ Exception -> 0x0309 }
        r49 = com.letv.datastatistics.util.DataUtils.getTrimData(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mRealUrl;	 Catch:{ Exception -> 0x0309 }
        r50 = r0;
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mPlayRef;	 Catch:{ Exception -> 0x0309 }
        r51 = r0;
        r52 = r99.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mStation;	 Catch:{ Exception -> 0x0309 }
        r53 = r0;
        r54 = "-";
        r55 = com.letv.core.utils.LetvUtils.getPcode();	 Catch:{ Exception -> 0x0309 }
        r2 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.isLogin();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0474;
    L_0x044d:
        r56 = 0;
    L_0x044f:
        r58 = "-";
        r59 = 0;
        r62 = "-";
        r37 = r103;
        r42 = r11;
        r48 = r17;
        r57 = r26;
        r60 = r29;
        r64 = r32;
        r33.sendLivePlayInfo25NewPlay(r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55, r56, r57, r58, r59, r60, r61, r62, r63, r64);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r3 = 1;
        r2.mStatus = r3;	 Catch:{ Exception -> 0x0309 }
        goto L_0x0394;
    L_0x046d:
        r2 = 0;
        goto L_0x03cc;
    L_0x0470:
        r96 = 1;
        goto L_0x03e6;
    L_0x0474:
        r56 = 1;
        goto L_0x044f;
    L_0x0477:
        r2 = "launch";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0509;
    L_0x0481:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r3 = 0;
        r2.mStatus = r3;	 Catch:{ Exception -> 0x0309 }
    L_0x0488:
        r64 = com.letv.datastatistics.DataStatistics.getInstance();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r0 = r0.mContext;	 Catch:{ Exception -> 0x0309 }
        r65 = r0;
        r66 = "0";
        r67 = "0";
        r69 = "0";
        r2 = 0;
        r0 = r104;
        r2 = java.lang.Math.max(r2, r0);	 Catch:{ Exception -> 0x0309 }
        r70 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x0309 }
        r71 = "-";
        r72 = com.letv.core.utils.LetvUtils.getUID();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mCid;	 Catch:{ Exception -> 0x0309 }
        r74 = r0;
        r75 = "-";
        r76 = java.net.URLEncoder.encode(r101);	 Catch:{ Exception -> 0x0309 }
        r77 = "-";
        r78 = "-";
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mVt;	 Catch:{ Exception -> 0x0309 }
        r80 = com.letv.datastatistics.util.DataUtils.getTrimData(r2);	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mRealUrl;	 Catch:{ Exception -> 0x0309 }
        r81 = r0;
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mPlayRef;	 Catch:{ Exception -> 0x0309 }
        r82 = r0;
        r83 = r99.toString();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r0 = r2.mStation;	 Catch:{ Exception -> 0x0309 }
        r84 = r0;
        r85 = "-";
        r86 = com.letv.core.utils.LetvUtils.getPcode();	 Catch:{ Exception -> 0x0309 }
        r2 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0309 }
        r2 = r2.isLogin();	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0568;
    L_0x04f2:
        r87 = 0;
    L_0x04f4:
        r89 = "-";
        r90 = 0;
        r68 = r103;
        r73 = r11;
        r79 = r17;
        r88 = r26;
        r91 = r29;
        r92 = r32;
        r64.sendLivePlayInfo25New(r65, r66, r67, r68, r69, r70, r71, r72, r73, r74, r75, r76, r77, r78, r79, r80, r81, r82, r83, r84, r85, r86, r87, r88, r89, r90, r91, r92);	 Catch:{ Exception -> 0x0309 }
        goto L_0x0394;
    L_0x0509:
        r2 = "time";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0527;
    L_0x0514:
        r2 = com.letv.core.utils.StatisticsUtils.mIsHomeClicked;	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0488;
    L_0x0518:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r3 = 2;
        r2.mStatus = r3;	 Catch:{ Exception -> 0x0309 }
        r2 = 0;
        com.letv.core.utils.StatisticsUtils.mIsHomeClicked = r2;	 Catch:{ Exception -> 0x0309 }
        r102.resetInfos();	 Catch:{ Exception -> 0x0309 }
        goto L_0x0488;
    L_0x0527:
        r2 = "end";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x054b;
    L_0x0531:
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r3 = 2;
        r2.mStatus = r3;	 Catch:{ Exception -> 0x0309 }
        r102.resetInfos();	 Catch:{ Exception -> 0x0309 }
        r0 = r102;
        r2 = r0.mStatisticsInfo;	 Catch:{ Exception -> 0x0309 }
        r2 = r2.mLastAction;	 Catch:{ Exception -> 0x0309 }
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0488;
    L_0x0549:
        goto L_0x02ea;
    L_0x054b:
        r2 = "block";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 != 0) goto L_0x055f;
    L_0x0555:
        r2 = "eblock";
        r0 = r103;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0309 }
        if (r2 == 0) goto L_0x0488;
    L_0x055f:
        r2 = "isplayer=1&&bype=-";
        r0 = r99;
        r0.append(r2);	 Catch:{ Exception -> 0x0309 }
        goto L_0x0488;
    L_0x0568:
        r87 = 1;
        goto L_0x04f4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.business.flow.statistics.LivePlayStatisticsHelper.statisticsPlayActions(java.lang.String, long):void");
    }

    public void statisticsTimeToPlays() {
        if (!this.mStatisticsInfo.mHasStatistics) {
            LogInfo.LogStatistics("statistics time to play(code=22)");
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("type1=" + DataUtils.getNetType(this.mContext));
                sb.append("&type2=0");
                if (this.mStatisticsInfo.mPlayAdFragment != null) {
                    sb.append("&type7=0&type8=0&type9=0&type10=0&type11=0&type12=0&type13=0&type14=0");
                    sb.append("&pageid=" + this.mStatisticsInfo.mPageId);
                    StatisticsUtils.statisticsActionInfo(this.mContext, null, "22", null, null, -1, sb.toString(), null, null, null, null, this.mStatisticsInfo.mLiveId, null, -1, null, null, null, null, this.mStatisticsInfo.getUuidTime(this.mContext));
                    this.mStatisticsInfo.mHasStatistics = true;
                } else {
                    sb.append("&type7=0&type8=0&type9=0&type10=0&type11=0&type12=0&type13=0&type14=0");
                    sb.append("&pageid=" + this.mStatisticsInfo.mPageId);
                    StatisticsUtils.statisticsActionInfo(this.mContext, null, "22", null, null, -1, sb.toString(), null, null, null, null, this.mStatisticsInfo.mLiveId, null, -1, null, null, null, null, this.mStatisticsInfo.getUuidTime(this.mContext));
                    this.mStatisticsInfo.mHasStatistics = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StatisticsUtils.mClickImageForPlayTime = 0;
                this.mStatisticsInfo.resetTimeInfos();
            }
        }
    }

    private boolean isInLunBoOrWeiShi() {
        if (this.mStatisticsInfo.mIsInLiveHall) {
            return LiveLunboUtils.isLunBoWeiShiType(this.mStatisticsInfo.mPageIndex);
        }
        boolean result = LiveLunboUtils.isLunboType(this.mStatisticsInfo.mLaunchMode) || this.mStatisticsInfo.mLaunchMode == 102;
        return result;
    }

    private void resetInfos() {
    }
}
