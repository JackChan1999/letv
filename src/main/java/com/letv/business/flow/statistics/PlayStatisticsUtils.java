package com.letv.business.flow.statistics;

import android.content.Context;
import android.text.TextUtils;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.business.flow.album.AlbumPlayBaseFlow;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.bean.StatisticsPlayInfo;
import com.letv.datastatistics.util.DataUtils;
import com.letv.pp.utils.NetworkUtils;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayStatisticsUtils {
    public static void staticsLauncher(Context context, long aid, long vid, long cid, long zid, int launchMode, String uuid, int ipt) {
        String sCid = cid <= 0 ? NetworkUtils.DELIMITER_LINE : cid + "";
        String sVid = vid <= 0 ? NetworkUtils.DELIMITER_LINE : vid + "";
        String sAid = aid <= 0 ? NetworkUtils.DELIMITER_LINE : aid + "";
        String sZid = zid <= 0 ? NetworkUtils.DELIMITER_LINE : zid + "";
        StatisticsPlayInfo playInfo = new StatisticsPlayInfo();
        playInfo.setcTime(System.currentTimeMillis());
        playInfo.setIpt(ipt);
        String type = "0";
        if (launchMode == 4) {
            type = "3";
        } else if (launchMode == 1) {
            type = "4";
        }
        DataStatistics.getInstance().sendPlayInfo24New(context, "0", "0", "launch", "0", "0", NetworkUtils.DELIMITER_LINE, LetvUtils.getUID(), uuid, sCid, sAid, sVid, null, null, type, null, null, null, null, null, null, LetvUtils.getPcode(), PreferencesManager.getInstance().isLogin() ? 0 : 1, null, sZid, playInfo);
        LogInfo.log("jc666", "album play launch start  uuid=" + uuid);
    }

    public static void staticticsLoadTimeInfo(Context context, AlbumPlayBaseFlow flow, AdPlayFragmentProxy adFragment) {
        AlbumPlayInfo playInfo = flow.mPlayInfo;
        StringBuilder sb = new StringBuilder();
        sb.append("type1=" + DataUtils.getNetType(context));
        sb.append("&type2=0");
        if (adFragment == null || playInfo.mIsAfterHomeClicked || playInfo.mIsCombineAd) {
            try {
                if (playInfo.mIsAfterHomeClicked) {
                    playInfo.mIsAfterHomeClicked = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StatisticsUtils.mClickImageForPlayTime = 0;
                playInfo.mTotalConsumeTime = 0;
                playInfo.mAdsRequestTime = 0;
                playInfo.mAdsToalTime = 0;
                playInfo.type8 = 0;
                playInfo.type8_1 = 0;
                playInfo.mAdsLoadConsumeTime = 0;
                playInfo.mVideoLoadConsumeTime = 0;
                playInfo.type14 = 0;
                playInfo.type7 = 0;
                playInfo.type7_1 = 0;
                playInfo.type9 = 0;
                playInfo.mType3 = 0;
                playInfo.mType15 = 0;
                playInfo.mType6_1 = 0;
                playInfo.mType17 = 0;
                playInfo.mType18 = 0;
                playInfo.mType19 = 0;
                playInfo.mType20 = 0;
                playInfo.mType21 = 0;
                playInfo.mAdsPlayFirstFrameTime = 0;
                playInfo.mTimeForRequestRealUrlFromUnion = 0;
                playInfo.mIsStatisticsLoadTime = true;
                playInfo.mHasCollectTimeToPlay = false;
            }
        }
        sb.append("&type3=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mType3));
        sb.append("&type4=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mTimeForRequestRealUrlFromCde));
        sb.append("&type5=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mType5));
        sb.append("&type5_1=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mType5_1));
        sb.append("&type6=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mTimeForRequestRealUrlFromUnion));
        sb.append("&type6_1=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mType6_1));
        sb.append("&type7=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.type7));
        sb.append("&type7_1=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.type7_1));
        sb.append("&type8=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.type8));
        sb.append("&type8_1=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.type8_1));
        sb.append("&type9=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.type9));
        if (!playInfo.mHasCollectTimeToPlay && playInfo.mTotalConsumeTime != 0) {
            playInfo.mTotalConsumeTime = Math.max(System.currentTimeMillis() - playInfo.mTotalConsumeTime, 0);
        } else if (playInfo.mTotalConsumeTime < 0) {
            playInfo.mTotalConsumeTime = 0;
        }
        sb.append("&type10=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mTotalConsumeTime));
        if (flow.mIsDownloadFile && playInfo.mType15 > 0) {
            playInfo.mAdsRequestTime = playInfo.mType15;
        }
        sb.append("&type11=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mAdsRequestTime));
        sb.append("&type12=" + ((playInfo.mIsCombineAd ? playInfo.frontAdDuration : playInfo.mAdsToalTime) / 1000));
        long j = (playInfo.mAdCount <= 0 || playInfo.mIsCombineAd) ? playInfo.mVideoLoadConsumeTime : playInfo.mAdsLoadConsumeTime;
        float loadingConsumeTimeTmp = StringUtils.staticticsLoadTimeInfoFormat(j);
        sb.append("&type13=" + loadingConsumeTimeTmp);
        sb.append("&type14=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.type14));
        sb.append("&type15=" + StringUtils.staticticsLoadTimeInfoFormat(playInfo.mType15));
        sb.append("&adtype=" + (playInfo.mIsCombineAd ? 0 : 1));
        sb.append("&iscde=" + (flow.mIsUseCde ? 0 : 1));
        sb.append("&time=").append(StringUtils.timeClockString("yyyyMMdd_HH:mm:ss"));
        sb.append("&pageid=" + StatisticsUtils.getPageId());
        String playUrl = NetworkUtils.DELIMITER_LINE;
        if (!(flow.mAlbumUrl == null || TextUtils.isEmpty(flow.mAlbumUrl.realUrl))) {
            playUrl = URLEncoder.encode(flow.mAlbumUrl.realUrl);
        }
        sb.append("&playurl=" + playUrl);
        sb.append("&adurl=" + (TextUtils.isEmpty(playInfo.mAdUrl) ? NetworkUtils.DELIMITER_LINE : URLEncoder.encode(playInfo.mAdUrl)));
        LogInfo.log("jc666", "loadtime type7=" + playInfo.type7 + ",type8=" + playInfo.type8 + ",type9=" + playInfo.type9 + ",type10=" + playInfo.mTotalConsumeTime + ",type13=" + loadingConsumeTimeTmp + ",type14=" + playInfo.type14 + ",type15=" + playInfo.mType15 + ",type3=" + playInfo.mType3);
        LogInfo.log("play_auto_test", new SimpleDateFormat("yyyymmdd hh:mm:ss").format(new Date()) + "####PLAY#### type10:" + playInfo.mTotalConsumeTime + ", type3:" + playInfo.mType3 + ", type7:" + playInfo.type7 + ", type7_1:" + playInfo.type7_1 + ", type8:" + playInfo.type8 + ", type8_1:" + playInfo.type8_1 + ", type9:" + playInfo.type9 + ", type11:" + playInfo.mAdsRequestTime + ", type13:" + ((int) (1000.0f * loadingConsumeTimeTmp)) + ", type14:" + playInfo.type14 + ", type15:" + playInfo.mType15 + ", type17:" + playInfo.mType17 + ", type18:" + playInfo.mType18 + ", type19:" + playInfo.mType19 + ", type20:" + playInfo.mType20 + ", type21:" + playInfo.mType21);
        StatisticsUtils.statisticsActionInfo(context, null, "22", null, null, -1, sb.toString(), getCorrectData(flow.mCid), getCorrectData(flow.mAid), getCorrectData(flow.mVid), getCorrectData(flow.mZid), null, null, -1, null, null, null, null, playInfo.mUuidTimp);
    }

    private static String getCorrectData(long data) {
        return data <= 0 ? NetworkUtils.DELIMITER_LINE : String.valueOf(data);
    }
}
