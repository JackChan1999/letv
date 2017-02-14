package com.letv.core.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import android.text.TextUtils;
import cn.com.iresearch.mapptracker.IRMonitor;
import cn.com.iresearch.vvtracker.IRVideo;
import com.amap.api.location.AMapLocation;
import com.letv.android.client.activity.MainActivity;
import com.letv.component.player.LetvVideoViewBuilder.Type;
import com.letv.core.BaseApplication;
import com.letv.core.bean.DataHull;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.utils.external.gaode.AMapLocationTool;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.bean.ActionStatisticsData;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.datastatistics.util.DataUtils;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.open.SocialConstants;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class StatisticsUtils {
    public static boolean isFirstPlay = false;
    private static ActionProperty mActionProperty = new ActionProperty();
    public static long mClickImageForPlayTime = 0;
    public static boolean mIsHomeClicked = false;
    private static long mPreviousBytes = 0;
    public static Type mType = null;
    public static int sCont = -1;
    public static String sFrom = "";
    public static boolean sHasStatisticsLaunch = false;
    public static String sLastPlayRef;
    public static String sLoginRef = MainActivity.THIRD_PARTY_LETV;
    public static String sMStartType = "";
    public static boolean sPlayFromCard = false;
    public static PlayStatisticsRelateInfo sPlayStatisticsRelateInfo = new PlayStatisticsRelateInfo();
    public static StatisticsPushData sStatisticsPushData;

    public static class StatisticsPushData {
        public String mAllMsg = "";
        public String mContentType = SocialConstants.PARAM_AVATAR_URI;
        public String mType = "";
    }

    public static void init() {
        DataStatistics.getInstance().initStatisticsInfo(LetvConfig.isDebug(), "0ie6");
    }

    public static void initDoubleChannel() {
        DataStatistics.getInstance().initDoubleChannelInfo(PreferencesManager.getInstance().isDoubleChannel(), PreferencesManager.getInstance().getOriginalPcode(), PreferencesManager.getInstance().getOriginalVersion(), PreferencesManager.getInstance().getActivieState());
    }

    public static void setActionProperty(String fl, int wz, String pageId) {
        setActionProperty(fl, wz, pageId, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public static void setActionProperty(String fl, int wz, String pageId, String fragId, String scid) {
        if (!TextUtils.isEmpty(fl)) {
            mActionProperty.fl = fl;
        }
        if (wz >= -1) {
            mActionProperty.wz = wz;
        }
        if (!TextUtils.isEmpty(pageId)) {
            mActionProperty.pageId = pageId;
        }
        if (!TextUtils.isEmpty(fragId)) {
            mActionProperty.fragId = fragId;
        }
        if (!TextUtils.isEmpty(scid)) {
            mActionProperty.scid = scid;
        }
    }

    public static String getPageId() {
        return mActionProperty.pageId;
    }

    public static void setPageId(String pageId) {
        if (!TextUtils.isEmpty(pageId)) {
            mActionProperty.pageId = pageId;
        }
    }

    public static String getFl() {
        return mActionProperty.fl;
    }

    public static int getWz() {
        return mActionProperty.wz;
    }

    public static String getPlayInfoRef(int type) {
        String ref = NetworkUtils.DELIMITER_LINE;
        switch (type) {
            case 0:
                return sFrom;
            case 1:
                return PageIdConstant.halpPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "h214" + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "4";
            case 2:
                return PageIdConstant.halpPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "h212" + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "3";
            case 3:
                return PageIdConstant.halpPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "h214" + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "1";
            case 4:
                return PageIdConstant.halpPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "h214" + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "3";
            case 5:
                return PageIdConstant.halpPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "c683" + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "1";
            case 6:
                return PageIdConstant.fullPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "c683" + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + "1";
            case 7:
                return PageIdConstant.halpPlayPage + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + NetworkUtils.DELIMITER_LINE + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + NetworkUtils.DELIMITER_LINE;
            case 8:
                return "001_1c_1";
            default:
                if (!TextUtils.isEmpty(mActionProperty.fl) && !mActionProperty.fl.equals(NetworkUtils.DELIMITER_LINE) && mActionProperty.fl.startsWith(IDataSource.SCHEME_HTTP_TAG)) {
                    return mActionProperty.fl;
                }
                if (TextUtils.isEmpty(mActionProperty.pageId)) {
                    return ref;
                }
                return mActionProperty.pageId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + mActionProperty.fl + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + mActionProperty.wz + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + DataUtils.getUnEmptyData(mActionProperty.scid) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + DataUtils.getUnEmptyData(mActionProperty.fragId);
        }
    }

    public static String getSpeed() {
        long totalBytes = TrafficStats.getTotalRxBytes();
        if (totalBytes != -1) {
            if (mPreviousBytes == 0) {
                mPreviousBytes = totalBytes;
            } else {
                long networkSpeed = TrafficStats.getTotalRxBytes() - mPreviousBytes;
                mPreviousBytes = totalBytes;
                return getMbString(networkSpeed);
            }
        }
        return "0";
    }

    private static String getMbString(long bytesize) {
        DecimalFormat df = new DecimalFormat("0.##");
        Double value = Double.valueOf(Long.valueOf(bytesize).doubleValue());
        Double size = Double.valueOf(0.0d);
        String suffix = "";
        if (bytesize == 0) {
            size = Double.valueOf(0.0d);
            suffix = "KB";
        } else if (bytesize < 1024) {
            size = value;
            suffix = "bytes";
        } else if (bytesize >= 1024 && bytesize < 1048576) {
            size = Double.valueOf(value.doubleValue() / 1024.0d);
            suffix = "KB";
        } else if (bytesize >= 1048576 && bytesize < 1073741824) {
            size = Double.valueOf(value.doubleValue() / 1048576.0d);
            suffix = "MB";
        } else if (bytesize >= 1073741824) {
            size = Double.valueOf(value.doubleValue() / 1.073741824E9d);
            suffix = "GB";
        }
        return df.format(size) + suffix + "/S";
    }

    public static void staticticsInfoPostAddTargetUrl(Context mContext, String acode, String fl, String name, int wz, String type, String pushtype, String messagetype, String msgid, String bid, String pageid, String cid, String pid, String vid, String curUrl, String zid, String lid, String targeturl) {
        staticticsInfoPostAddTargetUrl(mContext, acode, fl, name, wz, type, pushtype, messagetype, msgid, bid, pageid, cid, pid, vid, curUrl, zid, lid, targeturl, 0);
    }

    public static void staticticsInfoPostAddTargetUrl(Context mContext, String acode, String fl, String name, int wz, String type, String pushtype, String messagetype, String msgid, String bid, String pageid, String cid, String pid, String vid, String curUrl, String zid, String lid, String targeturl, int pfrom) {
        try {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(fl)) {
                sb.append(StaticticsName.STATICTICS_NAM_FL + fl);
            }
            if (wz >= 0) {
                sb.append("&wz=" + wz);
            }
            if (!TextUtils.isEmpty(type)) {
                sb.append("&type=" + type);
            }
            if (!TextUtils.isEmpty(pushtype)) {
                sb.append("&pushtype=" + pushtype);
            }
            if (!TextUtils.isEmpty(messagetype)) {
                sb.append("&messagetype=" + messagetype);
            }
            if (!TextUtils.isEmpty(msgid)) {
                sb.append("&msgid=" + msgid);
            }
            if (!TextUtils.isEmpty(name)) {
                sb.append("&name=" + URLEncoder.encode(name));
            }
            if (!TextUtils.isEmpty(bid)) {
                sb.append("&bid=" + bid);
            }
            if (!TextUtils.isEmpty(pageid)) {
                sb.append("&pageid=" + pageid);
            }
            sb.append("&device=" + DataUtils.getDataEmpty(DataUtils.getDeviceName()));
            if (pfrom != 0) {
                sb.append("&pfrom=" + pfrom);
            }
            if (!(sStatisticsPushData == null || TextUtils.isEmpty(sStatisticsPushData.mAllMsg))) {
                sb.append("&pushmsg=" + sStatisticsPushData.mAllMsg);
            }
            DataStatistics.getInstance().sendActionInfo(mContext.getApplicationContext(), "0", "0", LetvUtils.getPcode(), acode, sb.toString(), "0", cid, pid, vid, LetvUtils.getUID(), curUrl, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, zid, lid, targeturl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void staticticsInfoPost(Context mContext, String acode, String fl, String name, int wz, String cid, String pid, String vid, String curUrl, String zid) {
        statisticsInfoPost(mContext, acode, fl, name, wz, null, null, cid, pid, vid, curUrl, zid, null, null, null, -1, false);
    }

    public static void staticticsInfoPost(Context context, String acode, String fl, String name, int wz, String bid, String pageid, String cid, String pid, String vid, String curUrl, String zid) {
        statisticsInfoPost(context, acode, fl, name, wz, bid, pageid, cid, pid, vid, curUrl, zid, null, null, null, -1, false);
    }

    private static void statisticsInfoPost(Context context, String acode, String fl, String name, int wz, String bid, String pageid, String cid, String pid, String vid, String curUrl, String zid, String scid, String fragid, String time, int download, boolean isAddRef) {
        try {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(fl)) {
                sb.append(StaticticsName.STATICTICS_NAM_FL + fl);
            }
            if (wz >= 0) {
                sb.append("&wz=" + wz);
            }
            if (!TextUtils.isEmpty(name)) {
                sb.append("&name=" + name);
            }
            if (!TextUtils.isEmpty(bid)) {
                sb.append("&bid=" + bid);
            }
            if (!TextUtils.isEmpty(pageid)) {
                sb.append("&pageid=" + pageid);
            }
            if (!TextUtils.isEmpty(scid)) {
                sb.append("&scid=" + scid);
            }
            if (!TextUtils.isEmpty(fragid)) {
                sb.append("&fragid=" + fragid);
            }
            if (!TextUtils.isEmpty(time)) {
                sb.append("&time=" + time);
            }
            if (download > 0) {
                sb.append("&download=" + download);
            }
            if (isAddRef) {
                StringBuilder sb1 = new StringBuilder();
                if (!TextUtils.isEmpty(pageid)) {
                    sb1.append(pageid);
                }
                if (!TextUtils.isEmpty(fl)) {
                    sb1.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + fl);
                }
                if (wz >= 0) {
                    sb1.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + wz);
                }
                if (!TextUtils.isEmpty(sb1)) {
                    sb.append("&ref=" + sb1.toString());
                }
            }
            LogInfo.LogStatistics("StatisticsUtils-action:" + sb.toString());
            DataStatistics.getInstance().sendActionInfo(context.getApplicationContext(), "0", "0", LetvUtils.getPcode(), acode, sb.toString(), "0", cid, pid, vid, LetvUtils.getUID(), curUrl, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, zid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void staticticsInfoPost(Context mContext, String fl, String name, int wz, int id, String cid, String pid, String vid, String curUrl, String zid) {
        try {
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(fl)) {
                sb.append(StaticticsName.STATICTICS_NAM_FL + fl);
            }
            if (wz >= 0) {
                sb.append("&wz=" + (wz + 1));
            }
            if (!TextUtils.isEmpty(name)) {
                sb.append("&name=" + name);
            }
            if (id > 0) {
                sb.append("&cid=" + id);
            }
            LogInfo.LogStatistics("StatisticsUtils-action:" + sb.toString());
            DataStatistics.getInstance().sendActionInfo(mContext.getApplicationContext(), "0", "0", LetvUtils.getPcode(), "0", sb.toString(), "0", cid, pid, vid, LetvUtils.getUID(), curUrl, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, zid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void statisticsIRDeviceId(Context context) {
        LogInfo.LogStatistics("艾瑞-api调用");
        try {
            IRMonitor.getInstance().init(context, LetvConstant.MAPPTRACKERKEY, Global.DEVICEID, LetvConfig.isDebug(), new 1(context));
        } catch (Exception e) {
            LogInfo.LogStatistics("艾瑞-api调用异常！！！");
            e.printStackTrace();
        }
    }

    public static void initIRVideo(Context context) {
        try {
            if (VERSION.SDK_INT > 8) {
                IRVideo.getInstance().init(context.getApplicationContext(), LetvConstant.IRVIDEOUAID);
            }
        } catch (Exception e) {
            LogInfo.LogStatistics("IR init exception");
            e.printStackTrace();
        }
    }

    public static void statisticsAppLaunch(Context context, String type1, String type2, String time, boolean isFromHomeBack, String pageId) {
        StringBuilder sb = new StringBuilder();
        sb.append("type1=").append(type1);
        sb.append("&type2=").append(type2);
        sb.append("&time=").append(time);
        sb.append("&pageid=").append(pageId);
        int startType = 1;
        if (BaseApplication.getInstance().isPush()) {
            startType = 2;
        } else if (!TextUtils.equals(MainActivity.THIRD_PARTY_LETV, sLoginRef) && !TextUtils.isEmpty(sLoginRef)) {
            startType = sLoginRef.contains("mletv") ? 6 : 7;
        } else if (isFromHomeBack) {
            startType = 3;
        }
        sb.append("&starttype=").append(startType);
        DataStatistics.getInstance().sendActionInfo(context, "0", "0", LetvUtils.getPcode(), "11", sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
        sHasStatisticsLaunch = true;
    }

    public static void statisticsLoginAndEnv(Context context, int st, boolean isLogin) {
        String loginProperty = "plat=" + LetvConfig.getSource();
        AMapLocation location = AMapLocationTool.getInstance().location();
        String lo = "";
        String la = "";
        if (location != null) {
            lo = String.valueOf(location.getLongitude());
            la = String.valueOf(location.getLatitude());
        }
        if (TextUtils.isEmpty(lo) && TextUtils.isEmpty(la)) {
            lo = PreferencesManager.getInstance().getLocationLongitude() + "";
            la = PreferencesManager.getInstance().getLocationLatitude() + "";
        }
        int i = st;
        DataStatistics.getInstance().sendLoginAndEnvInfo(context.getApplicationContext(), LetvUtils.getUID(), loginProperty, sLoginRef, String.valueOf(System.currentTimeMillis() / 1000), LetvUtils.getPcode(), i, null, LetvConfig.getSource(), lo, la, PreferencesManager.getInstance().getStatisticsLocation(), isLogin);
    }

    public static String getApprunId(Context context) {
        String id = DataStatistics.getUuid(context, false);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }
        id = DataUtils.getUUID(context);
        DataStatistics.setUuid(context, id);
        return id;
    }

    public static void submitLocalErrors(Context context) {
        if (NetworkUtils.isNetworkAvailable()) {
            new Thread(new 2(context)).start();
        }
    }

    public static void statisticsSettings(Context context) {
        int i;
        int i2 = 1;
        StringBuffer sb = new StringBuffer();
        StringBuilder append = new StringBuilder().append("sk=");
        if (PreferencesManager.getInstance().isSkip()) {
            i = 1;
        } else {
            i = 0;
        }
        sb.append(append.append(i).toString());
        append = new StringBuilder().append("nt=");
        if (PreferencesManager.getInstance().isAllowMobileNetwork()) {
            i = 1;
        } else {
            i = 0;
        }
        sb.append(append.append(i).toString());
        append = new StringBuilder().append("ps=");
        if (PreferencesManager.getInstance().isPush()) {
            i = 1;
        } else {
            i = 0;
        }
        sb.append(append.append(i).toString());
        StringBuilder append2 = new StringBuilder().append("sh=");
        if (!PreferencesManager.getInstance().isShack()) {
            i2 = 0;
        }
        sb.append(append2.append(i2).toString());
        String cacheSize = "0.00M";
        try {
            cacheSize = LetvUtils.getCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("&cl=" + cacheSize);
        statisticsActionInfo(context.getApplicationContext(), PageIdConstant.searchResultPage, "19", "91", null, -1, sb.toString());
    }

    public static void statisticsActionInfo(Context context, String pageId, String actionCode, String fl, String name, int wz, String expandProperty) {
        statisticsActionInfo(context, pageId, actionCode, fl, name, wz, expandProperty, null, null, null, null, null, null, -1, null, null, null, null, null);
    }

    public static void statisticsActionInfo(Context context, String pageId, String actionCode, String fl, String name, int wz, String expandProperty, String cid, String pid, String vid, String zid, String lid) {
        statisticsActionInfo(context, pageId, actionCode, fl, name, wz, expandProperty, cid, pid, vid, zid, lid, null, -1, null, null, null, null, null);
    }

    public static void statisticsActionInfo(Context context, String pageId, String actionCode, String fl, String name, int wz, String expandProperty, String cid, String pid, String vid, String zid, String lid, String reid, int rank, String bucket, String area, String targetUrl, String curUrl, String uuid) {
        ActionStatisticsData data = new ActionStatisticsData();
        data.mActionCode = actionCode;
        data.mPcode = LetvUtils.getPcode();
        data.mIsLogined = PreferencesManager.getInstance().isLogin() ? 0 : 1;
        data.mUid = LetvUtils.getUID();
        data.mCid = cid;
        data.mPid = pid;
        data.mVid = vid;
        data.mZid = zid;
        data.mLid = lid;
        data.mReid = reid;
        data.mRank = rank;
        data.mBucket = bucket;
        data.mArea = area;
        data.mTargeturl = targetUrl;
        if (TextUtils.isEmpty(curUrl)) {
            curUrl = pageId;
        }
        data.mCurUrl = curUrl;
        data.mUuid = uuid;
        data.mActionProperty.pageId = pageId;
        data.mActionProperty.fl = fl;
        data.mActionProperty.name = name;
        data.mActionProperty.wz = wz;
        data.mActionProperty.ep = expandProperty;
        DataStatistics.getInstance().sendActionInfo(context.getApplicationContext(), data);
    }

    public static String getLivePageId(int tab) {
        String pageId = PageIdConstant.onLiveremenCtegoryPage;
        switch (tab) {
            case -1:
            case 0:
            case 12:
                return PageIdConstant.onLiveremenCtegoryPage;
            case 1:
                return PageIdConstant.onLiveLunboCtegoryPage;
            case 2:
                return PageIdConstant.onLiveWeishiCtegoryPage;
            case 3:
            case 17:
                return PageIdConstant.onLiveSportCtegoryPage;
            case 4:
            case 16:
                return PageIdConstant.onLiveMusicCtegoryPage;
            case 5:
                return PageIdConstant.onLiveEntertainmentCtegoryPage;
            case 6:
                return PageIdConstant.onLiveBrandCtegoryPage;
            case 7:
                return PageIdConstant.onLiveGameCtegoryPage;
            case 8:
                return PageIdConstant.onLiveNewsCtegoryPage;
            case 9:
                return PageIdConstant.onLiveFinanceCtegoryPage;
            case 10:
                return PageIdConstant.onLiveOtherCtegoryPage;
            case 11:
                return PageIdConstant.liveVariety;
            case 13:
                return PageIdConstant.hkLiveMovie;
            case 14:
                return PageIdConstant.hkLiveTv;
            case 15:
                return PageIdConstant.hkLiveComposite;
            default:
                return pageId;
        }
    }

    public static String getSubErroCode(NetworkResponseState state, DataHull hull) {
        StringBuilder sb = new StringBuilder("ecode=");
        if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
            sb.append(LetvErrorCode.SUB_ERROR_CODE_NO_NET);
        } else if (hull.httpResponseCode < 200 || hull.httpResponseCode > 299) {
            sb.append(LetvErrorCode.SUB_ERROR_CODE_SERVER_ERROR).append("&sub_ecode=").append("0003-" + hull.httpResponseCode);
        } else {
            sb.append(LetvErrorCode.SUB_ERROR_CODE_DATA_ERROR).append("&sub_ecode=").append("0002-" + hull.apiState);
        }
        return sb.toString();
    }

    public static void clearStatisticsInfo(Context context) {
        mActionProperty = new ActionProperty();
        sPlayStatisticsRelateInfo = new PlayStatisticsRelateInfo();
        mPreviousBytes = 0;
        mType = null;
        sMStartType = "";
        sFrom = "";
        mClickImageForPlayTime = 0;
        isFirstPlay = false;
        mIsHomeClicked = false;
        sHasStatisticsLaunch = false;
        DataStatistics.setUuid(context, "");
        sCont = -1;
        sPlayFromCard = false;
        sLoginRef = MainActivity.THIRD_PARTY_LETV;
        sLastPlayRef = "";
        sStatisticsPushData = null;
    }
}
