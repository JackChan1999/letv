package com.letv.core.api;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.authjs.a;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.core.BaseApplication;
import com.letv.core.api.PlayRecordApi.MODIFYPWD_PARAMETERS;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.PlayRecord;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.PlayConstant;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.toolbox.ParameterBuilder;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.datastatistics.util.DataConstant.PAGE;
import com.letv.datastatistics.util.DataUtils;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.lemallsdk.util.Constants;
import com.letv.mobile.lebox.http.lebox.request.TaskAddHttpRequest;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;

public class LetvUrlMaker {
    public static boolean isTest() {
        return PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest();
    }

    private static String getStaticHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android";
        }
        return "http://static.app.m.letv.com/android";
    }

    private static String getDynamicHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.app.m.letv.com/android/dynamic.php";
    }

    private static String getDynamicHead(boolean isHongKong) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return isHongKong ? "http://hk.test2.m.letv.com/android/dynamic.php" : "http://test2.m.letv.com/android/dynamic.php";
        } else {
            return "http://dynamic.app.m.letv.com/android/dynamic.php";
        }
    }

    private static String getDynamicUrl() {
        return getDynamicUrl(false);
    }

    private static String getDynamicUrl(boolean isHongKong) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return isHongKong ? "http://hk.test2.m.letv.com/android/dynamic.php" : "http://test2.m.letv.com/android/dynamic.php";
        } else {
            return "http://dynamic.pay.app.m.letv.com/android/dynamic.php";
        }
    }

    private static String getDynamicUrlForHomePage(boolean isInHongKong) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return isInHongKong ? "http://hk.test2.m.letv.com/android/dynamic.php" : "http://test2.m.letv.com/android/dynamic.php";
        } else {
            return "http://dynamic.recommend.app.m.letv.com/android/dynamic.php";
        }
    }

    private static String getDynamicUrlForHomePage() {
        return getDynamicUrlForHomePage(false);
    }

    protected static String getWoStaticHead() {
        return "http://other.api.mob.app.letv.com/unicomcheck/";
    }

    private static String getLiveDynamicUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.live.app.m.letv.com/android/dynamic.php";
    }

    private static String getLiveHKDynamicUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return LetvUtils.isInHongKong() ? "http://hk.test2.m.letv.com/android/dynamic.php" : "http://test2.m.letv.com/android/dynamic.php";
        } else {
            return "http://dynamic.live.app.m.letv.com/android/dynamic.php";
        }
    }

    private static String getPushBaseUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://10.204.28.31:9030/android/index.php";
        }
        return "http://msg.m.letv.com/android/index.php";
    }

    private static String getHotAddUpDownUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/hotpoint/addupdown?";
        }
        return "http://api.mob.app.letv.com/hotpoint/addupdown?";
    }

    private static String getStarInfoHost() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/star";
        }
        return "http://api.mob.app.letv.com/star";
    }

    private static String getStarRankHost() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/star/starrank?";
        }
        return "http://api.mob.app.letv.com/star/starrank?";
    }

    private static String getStarRankingHost() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/star/starranking?";
        }
        return "http://api.mob.app.letv.com/star/starranking?";
    }

    private static String getHotTypeListUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/hotpoint/listhotpoint?";
        }
        return "http://api.mob.app.letv.com/hotpoint/listhotpoint?";
    }

    private static String getEmojiListUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/emoji?";
        }
        return "http://api.mob.app.letv.com/emoji?";
    }

    private static String getRedPacketListUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return " http://t.api.mob.app.letv.com/redpackage/package?";
        }
        return "http://api.mob.app.letv.com/redpackage/package?";
    }

    private static String getLiveWatchNumHost() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/live/livenum";
        }
        return "http://api.mob.app.letv.com/live/livenum";
    }

    public static String getTopVideoHomeUrl() {
        String belongArea = LetvUtils.isInHongKong() ? "101" : "100";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "sortHotLive"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("belongArea", belongArea));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getLiveUrlByChannelType(String channelType) {
        return LetvUtils.genLangResRequestUrl(getLiveHKDynamicUrl() + String.format("?luamod=main&mod=live&ctl=liveRoom&act=index&ct=%s&clientId=%s&pcode=%s&version=%s", new Object[]{channelType, LetvConfig.getHKClientID(), LetvConfig.getPcode(), LetvUtils.getClientVersionName()}), 0);
    }

    public static String getLiveAllPlayingUrl() {
        String belongArea = LetvUtils.isInHongKong() ? "101" : "100";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "getAllLiveRoom"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("action", "live"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("belongArea", belongArea));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getLiveNearPrograms(String channelIds) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", "live"));
        params.add(new BasicNameValuePair("ctl", "currentPlayBill"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        params.add(new BasicNameValuePair("channelIds", channelIds));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getLiveHKDynamicUrl());
    }

    public static String getLiveProgramsInc(int direction, String programId) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", "live"));
        params.add(new BasicNameValuePair("ctl", "incrementalPlayBill"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        params.add(new BasicNameValuePair("programId", programId));
        params.add(new BasicNameValuePair("direction", String.valueOf(direction)));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getLiveHKDynamicUrl());
    }

    public static String getLunboListBeanUrl() {
        List<BasicNameValuePair> list = buildLunboParameter();
        list.add(new BasicNameValuePair("signal", "5,7"));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getHKLunboListBeanUrl(String subtype) {
        List<BasicNameValuePair> list = buildHKLunboParameter();
        list.add(new BasicNameValuePair("signal", "5,7"));
        list.add(new BasicNameValuePair("subtype", subtype));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getWeishilListBeanUrl() {
        List<BasicNameValuePair> list = buildLunboParameter();
        list.add(new BasicNameValuePair("signal", "2,9"));
        list.add(new BasicNameValuePair("withUnclass", "1"));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getLunboListUrl(int channelType, String channelIds) {
        return LetvUtils.genLangResRequestUrl(getLiveHKDynamicUrl() + String.format("?luamod=main&mod=live&ctl=preCurNextPlayBill&act=index&channelType=%s&channelIds=%s&clientId=%s&pcode=%s&version=%s", new Object[]{Integer.valueOf(channelType), channelIds, LetvConfig.getHKClientID(), LetvConfig.getPcode(), LetvUtils.getClientVersionName()}), 0);
    }

    private static List<BasicNameValuePair> buildLunboParameter() {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "channel"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("belongArea", "100"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return list;
    }

    private static List<BasicNameValuePair> buildHKLunboParameter() {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "channel"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("belongArea", "101"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return list;
    }

    public static String getLiveWatchNumUrl(String id) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("id", id));
        list.add(new BasicNameValuePair("group", "zhibo"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveWatchNumHost());
    }

    public static String getDialogMsgInfoUrl(String markId) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "minfo"));
        list.add(new BasicNameValuePair("ctl", "message"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("markid", markId));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(list, getStaticHead(), UrlConstdata.getStaticEnd());
    }

    public static String getLiveUrl(String channelId, boolean isPlay) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", TaskAddHttpRequest.stream));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("isPay", isPlay ? "1" : "0"));
        list.add(new BasicNameValuePair("withAllStreams", "1"));
        list.add(new BasicNameValuePair("channelId", channelId));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getMiGuLiveUrl(String channelId, String rateLevel) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "getMiguUrl"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("channelId", channelId));
        list.add(new BasicNameValuePair("rateLevel", rateLevel));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveDynamicUrl());
    }

    public static String getLiveVideoDataUrl(String channelType, String startDate, String endDate, String status, String hasPay, String order, String belongArea) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "getChannelliveBystatus"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("beginDate", startDate));
        list.add(new BasicNameValuePair("ct", channelType));
        list.add(new BasicNameValuePair("endDate", endDate));
        list.add(new BasicNameValuePair("status", status));
        list.add(new BasicNameValuePair("belongArea", belongArea));
        list.add(new BasicNameValuePair("order", order));
        list.add(new BasicNameValuePair("hasPay", hasPay));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getLivePageDataUrl() {
        List<BasicNameValuePair> list = new ArrayList();
        String belongArea = LetvUtils.isInHongKong() ? "101" : "100";
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "liveHome"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("belongArea", belongArea));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getLiveLunboWeishiDataUrl(String channelIds, String channelType) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("ctl", "preCurNextPlayBill"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("channelType", channelType));
        list.add(new BasicNameValuePair("channelIds", channelIds));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveHKDynamicUrl());
    }

    public static String getCanPlayUrl(String streamId) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "live"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "canplay"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair(PlayConstant.LIVE_STREAMID, streamId));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveDynamicUrl());
    }

    public static String getExpireTimeUrl() {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "timeexpirestamp"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "timeExpireStamp"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getLiveDynamicUrl());
    }

    public static String getHomeBeanUrl(Context context) {
        StringBuilder mHistoryIds = new StringBuilder();
        String mConnector_symbol = NetworkUtils.DELIMITER_LINE;
        String mHomePageMarkId = new DataHull().markId;
        int newUser = LetvUtils.isNewUser() ? 1 : 0;
        String mRecommendMarkId = "0";
        Iterator it = DBManager.getInstance().getPlayTrace().getLastPlayTrace(10).iterator();
        while (it.hasNext()) {
            mHistoryIds.append(((PlayRecord) it.next()).videoId).append(NetworkUtils.DELIMITER_LINE);
        }
        if (mHistoryIds.length() > 0) {
            mHistoryIds.delete(mHistoryIds.length() - 1, mHistoryIds.length());
        }
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "home55"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("markid", mHomePageMarkId));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtil.generateDeviceId(context)));
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.HISTORY_KEY, mHistoryIds.toString()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.IS_NEW, String.valueOf(newUser)));
        list.add(new BasicNameValuePair("allow_risk_album", PreferencesManager.getInstance().getCopyright() == 1 ? "true" : "false"));
        return ParameterBuilder.getQueryUrl(list, getDynamicUrlForHomePage(LetvUtils.isInHongKong()));
    }

    public static String getHotSquareUpUrl(String voteids) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "vote"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "num"));
        params.add(new BasicNameValuePair("id", voteids));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getDynamicHead());
    }

    public static String getGeoUrl(String longitude, String latitude) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "geo"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getDynamicHead());
    }

    public static String getHotAddUpListUrl(String vid, String act) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, act));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getHotAddUpDownUrl());
    }

    public static String getHotTypeList() {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getHotTypeListUrl());
    }

    public static String getHomeBottomRecommendUrl(Context context) {
        String mHead = getStaticHead();
        String mEnd = LetvHttpApiConfig.getStaticEnd();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "exchange"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "bottom"));
        list.add(new BasicNameValuePair("markid", "0"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(list, mHead, mEnd);
    }

    public static String getAlbumByTimeUrl(String year, String month, String id, String videoType) {
        String head = getStaticHead() + "/mod/mob/ctl/videolistbydate/act/detail";
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("pcode", LetvUtils.getPcode()));
        if (!TextUtils.isEmpty(year)) {
            params.add(new BasicNameValuePair("year", year));
        }
        if (!TextUtils.isEmpty(month)) {
            params.add(new BasicNameValuePair("month", month));
        }
        params.add(new BasicNameValuePair("id", id));
        if (!TextUtils.isEmpty(videoType)) {
            params.add(new BasicNameValuePair(PlayConstant.VIDEO_TYPE, videoType));
        }
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String getAlbumVideoInfoUrl(String aid) {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "album"));
        params.add(new BasicNameValuePair("id", aid));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair("pcode", LetvUtils.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String getVideolistUrl(String aid, String vid, String page, String count, String o, String merge) {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "videolist"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair("id", aid));
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair(PAGE.MYSHARE, page));
        params.add(new BasicNameValuePair("s", count));
        params.add(new BasicNameValuePair("o", o));
        params.add(new BasicNameValuePair(PAGE.MYLETV, merge));
        params.add(new BasicNameValuePair("pcode", LetvUtils.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String getSpreadUrl(Context context) {
        String mHead = getStaticHead();
        String mEnd = LetvHttpApiConfig.getStaticEnd();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "spread"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("markid", ""));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(list, mHead, mEnd);
    }

    public static String getLiveDataUrl(boolean isForChannel, int cmsid) {
        String mBaseUrl = getDynamicUrl(LetvUtils.isInHongKong());
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        if (isForChannel) {
            list.add(new BasicNameValuePair("mod", "live"));
            list.add(new BasicNameValuePair("luamod", "main"));
            list.add(new BasicNameValuePair("ctl", "liveRoomByChannel"));
            list.add(new BasicNameValuePair("num", "3"));
            String value = "";
            switch (cmsid) {
                case 4:
                    value = "sports";
                    break;
                case 9:
                    value = "music";
                    break;
                case 104:
                    value = "game";
                    break;
                case 1009:
                    value = "information";
                    break;
                default:
                    value = "";
                    break;
            }
            list.add(new BasicNameValuePair("ct", value));
        } else {
            list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
            list.add(new BasicNameValuePair("ctl", "liveNew"));
            list.add(new BasicNameValuePair("num", "2"));
        }
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, mBaseUrl);
    }

    public static String getLiveBookUrl(String value) {
        String mBaseUrl = getDynamicUrl(LetvUtils.isInHongKong());
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "live"));
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("ctl", "getAllBookChannel"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("action", "book"));
        list.add(new BasicNameValuePair(a.e, LetvConfig.getHKClientID()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("ch", value));
        list.add(new BasicNameValuePair("belongArea", LetvUtils.getBelongArea()));
        return ParameterBuilder.getQueryUrl(list, mBaseUrl);
    }

    public static String getWoFlowOpenUrl(String isp, String ip) {
        return getWoStaticHead() + "ipcheck?ip=" + ip + "&isp=" + isp + "&pcode=" + LetvConfig.getPcode() + "&devid=" + LetvUtils.generateDeviceId(BaseApplication.getInstance());
    }

    public static String getCanPlayUrl(String pid, String end, String uname, String uid, String storePath) {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getService"));
        list.add(new BasicNameValuePair("pid", pid));
        list.add(new BasicNameValuePair("end", end));
        list.add(new BasicNameValuePair("uname", uname));
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair("storepath", storePath));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getAlbumPayUrl(String pid) {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "albumpay"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        list.add(new BasicNameValuePair("id", pid));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getDataStatusInfoUrl() {
        StringBuffer sb = new StringBuffer();
        sb.append("osversion=" + DataUtils.getDataEmpty(DataUtils.getOSVersionName()) + "&");
        sb.append("accesstype=" + DataUtils.getDataEmpty(DataUtils.getNetType(BaseApplication.getInstance())) + "&");
        sb.append("resolution=" + DataUtils.getDataEmpty(DataUtils.getResolution(BaseApplication.getInstance())) + "&");
        sb.append("brand=" + DataUtils.getDataEmpty(DataUtils.getBrandName()) + "&");
        sb.append("model=" + DataUtils.getDataEmpty(DataUtils.getDeviceName()) + "&");
        sb.append("devid=" + DataUtils.getDataEmpty(DataUtils.generateDeviceId(BaseApplication.getInstance())) + "&");
        sb.append("pcode=" + DataUtils.getDataEmpty(LetvConfig.getPcode()) + "&");
        sb.append("version=" + DataUtils.getClientVersionName(BaseApplication.getInstance()) + "&");
        sb.append("uid=" + PreferencesManager.getInstance().getUserId() + "&");
        sb.append("imei=" + DataUtils.getDataEmpty(DataUtils.getIMEI(BaseApplication.getInstance())));
        return LetvUtils.genLangResRequestUrl(getDynamicHead(LetvUtils.isInHongKong()) + "?mod=minfo&ctl=apistatus&act=index&" + sb.toString(), 0);
    }

    public static String getUseTicketUrl(String name, String pid, String type, String id) {
        String baseUrl = isTest() ? "http://t.api.mob.app.letv.com/yuanxian/updTicket" : "http://api.mob.app.letv.com/yuanxian/updTicket";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("name", name));
        list.add(new BasicNameValuePair("pid", pid));
        list.add(new BasicNameValuePair("type", type));
        list.add(new BasicNameValuePair("id", id));
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().getUserId()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getMyInfoFocusAdUrl() {
        String home_live_cms_id = (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) ? "2904" : "2903";
        return "http://static.app.m.letv.com/android/mod/mob/ctl/block/act/index/id/" + home_live_cms_id + "/pcode/010110000/version/5.9.mindex.html";
    }

    public static String getCustomerLevelUrl(Context context) {
        String mHead = isTest() ? "http://t.api.mob.app.letv.com/grade/add" : "http://api.mob.app.letv.com/grade/add";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtil.generateDeviceId(context)));
        list.add(new BasicNameValuePair("type", "1"));
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, mHead);
    }

    public static String getLabelUrl() {
        String mHomeLabelMarkId = new DataHull().markId;
        String mHead = getStaticHead();
        String mEnd = LetvHttpApiConfig.getStaticEnd();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "mainstatic"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "dict"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("markid", mHomeLabelMarkId));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(list, mHead, mEnd);
    }

    public static String getSubmitQRCodeUrl(int updataId, String guid, String sso_tk, String key) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "submitQRCode"));
        params.add(new BasicNameValuePair("guid", guid));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, sso_tk));
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(params, getDynamicHead());
    }

    public static String getExchangePopUrl() {
        List<BasicNameValuePair> params = new ArrayList();
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "exchangepop"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "pop"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String getPushUrl(String id, String msgId, String dev_id, String city) {
        String baseUrl = getPushBaseUrl();
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "minfo"));
        params.add(new BasicNameValuePair("ctl", "pushmsg"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("msgid", msgId));
        params.add(new BasicNameValuePair("dev_id", dev_id));
        params.add(new BasicNameValuePair("city", city));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public static String getDataUrl() {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "booklive"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getDate"));
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public static String clientCheckTicket(String tk) {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "clientCheckTicket"));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, tk));
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public static String getShakeSubmitUrl(String aid, String vid, String uuid, String playtime, String vtype, String longitude, String latitude) {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "minfo"));
        params.add(new BasicNameValuePair("ctl", "shake"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "add"));
        params.add(new BasicNameValuePair("aid", aid));
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair(Constants.UUID, uuid));
        params.add(new BasicNameValuePair("playtime", playtime));
        params.add(new BasicNameValuePair("vtype", vtype));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public static String getShakeCommitUrl(String uuid, String longitude, String latitude) {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "minfo"));
        params.add(new BasicNameValuePair("ctl", "shake"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "get"));
        params.add(new BasicNameValuePair(Constants.UUID, uuid));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public static String getUserInfoRequestUrl() {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "thirdUserLogin"));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getCombinesUrl() {
        return isTest() ? "http://115.182.63.61/m3u8api/" : "http://n.mark.letv.com/m3u8api/";
    }

    public static String getSmiliesUrl(int type) {
        String baseUrl = getEmojiListUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("type", String.valueOf(type)));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getVoteListUrl(String vid) {
        String host = isTest() ? "http://t.api.mob.app.letv.com/vote/demandvotevid?" : "http://api.mob.app.letv.com/vote/demandvotevid?";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("vid", vid));
        return ParameterBuilder.getQueryUrl(list, host);
    }

    public static String getVoteUrl(String voteId) {
        String host = isTest() ? "http://t.api.mob.app.letv.com/vote/vote?" : "http://api.mob.app.letv.com/vote/vote?";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("id", voteId));
        list.add(new BasicNameValuePair("ip", com.letv.core.utils.NetworkUtils.getIp()));
        return ParameterBuilder.getQueryUrl(list, host);
    }

    public static String getPageCard(String pcversion, String type) {
        String host = isTest() ? "http://test2.m.letv.com/android/dynamic.php?" : "http://dynamic.app.m.letv.com/android/dynamic.php?";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "pagecard"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("type", type));
        list.add(new BasicNameValuePair("pcversion", pcversion + ""));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, host);
    }

    public static String getFavoriteUrl() {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "block"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("id", "3505"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(list, "http://static.app.m.letv.com/android/", "mindex.html");
    }

    public static String getStarRankUrl(String id, String num) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        if (!TextUtils.isEmpty(id)) {
            list.add(new BasicNameValuePair("id", id));
        }
        list.add(new BasicNameValuePair("n", num));
        list.add(new BasicNameValuePair("protobuf", String.valueOf(PreferencesManager.getInstance().getProtoBuf())));
        return ParameterBuilder.getQueryUrl(list, getStarRankHost());
    }

    public static String getStarRankingUrl(String num) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("nums", num));
        return ParameterBuilder.getQueryUrl(list, getStarRankingHost());
    }

    public static String getPlayCardsUrl(String cid, String pid, String vid, String zid, String uid) {
        String start;
        List<BasicNameValuePair> list = new ArrayList();
        if (!(TextUtils.isEmpty(cid) || TextUtils.equals(cid, "0"))) {
            list.add(new BasicNameValuePair("cid", cid));
        }
        if (!(TextUtils.isEmpty(pid) || TextUtils.equals(pid, "0"))) {
            list.add(new BasicNameValuePair("pid", pid));
        }
        if (!(TextUtils.isEmpty(vid) || TextUtils.equals(vid, "0"))) {
            list.add(new BasicNameValuePair("vid", vid));
        }
        if (!(TextUtils.isEmpty(zid) || TextUtils.equals(zid, "0"))) {
            list.add(new BasicNameValuePair(PlayConstant.ZID, zid));
        }
        if (!TextUtils.isEmpty(uid)) {
            list.add(new BasicNameValuePair("uid", uid));
        }
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance())));
        list.add(new BasicNameValuePair("allow_risk_album", PreferencesManager.getInstance().getCopyright() == 1 ? "true" : "false"));
        if (!PreferencesManager.getInstance().isTestApi() || !LetvConfig.isForTest()) {
            start = "http://api.mob.app.letv.com/play/cards?";
        } else if (LetvUtils.isInHongKong()) {
            start = "http://hk.t.api.mob.app.letv.com/play/cards?";
        } else {
            start = "http://t.api.mob.app.letv.com/play/cards?";
        }
        return ParameterBuilder.getQueryUrl(list, start);
    }

    public static String getStarInfoUrl(String starId) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().getUserId()));
        list.add(new BasicNameValuePair("id", starId));
        list.add(new BasicNameValuePair("allow_risk_album", String.valueOf(PreferencesManager.getInstance().getCopyright())));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance())));
        LogInfo.log("clf", "获取明星信息接口 getStarInfoUrl=" + ParameterBuilder.getQueryUrl(list, getStarInfoHost()));
        return ParameterBuilder.getQueryUrl(list, getStarInfoHost());
    }

    public static String getRedPacketUrl(Context context) {
        List<BasicNameValuePair> list = new ArrayList();
        String head = getRedPacketListUrl();
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtil.generateDeviceId(context)));
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, head);
    }

    public static String getUpdown(String vid, int act) {
        String head = isTest() ? "http://t.api.mob.app.letv.com/play/updown" : "http://api.mob.app.letv.com/play/updown";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, act + ""));
        list.add(new BasicNameValuePair("did", LetvUtil.generateDeviceId(BaseApplication.getInstance())));
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, head);
    }

    public static String getUnreadMessageCount() {
        String head = isTest() ? "http://t.api.mob.app.letv.com/listmessage/unreadcount" : "http://api.mob.app.letv.com/listmessage/unreadcount";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair(com.tencent.connect.common.Constants.PARAM_PLATFORM, "1,3"));
        list.add(new BasicNameValuePair("from", "0,1,3,4,5,6,7,9,10"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, head);
    }

    public static String getTimestamp() {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "timestamp"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "timestamp"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getDynamicHead());
    }

    public static String getWatchAndBuyGoodsUrl(String streamId) {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "live"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "buybystreamid"));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getWatchAndBuyAddToCartUrl(String goodsType, String goodsId, String streamId, boolean needCartDetail) {
        String baseUrl = getDynamicUrlForHomePage();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("purType", goodsType));
        list.add(new BasicNameValuePair("pids", goodsId));
        list.add(new BasicNameValuePair(PlayConstant.LIVE_STREAMID, streamId));
        list.add(new BasicNameValuePair("needCartDetail", needCartDetail ? "1" : "0"));
        list.add(new BasicNameValuePair("user_id", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : "0"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "live"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "addinbuycart"));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getWatchAndBuyCartNumUrl() {
        String baseUrl = getDynamicUrlForHomePage();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("user_id", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : "0"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "live"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getbuycartnum"));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public static String getWatchAndBuyAttionNumUrl(String streamId, String startTime, String duration) {
        String baseUrl = getDynamicUrlForHomePage();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair(PlayConstant.LIVE_STREAMID, streamId));
        list.add(new BasicNameValuePair("startingtime", startTime));
        list.add(new BasicNameValuePair(DownloadVideoTable.COLUMN_DURATION, duration));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "live"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "totalcount"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String toString() {
        return super.toString();
    }

    private static String getPaySucceedRedPackageUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/";
        }
        return "http://api.mob.app.letv.com/";
    }

    public static String getPopRedPackageUrl(String orderId) {
        String requestUrl = getPaySucceedRedPackageUrl() + "redpackage/order";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().getUserId()));
        list.add(new BasicNameValuePair("type", "2"));
        list.add(new BasicNameValuePair("userType", "1"));
        list.add(new BasicNameValuePair("orderId", orderId));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("_debug", "1"));
        LogInfo.log("YDD", "getPopRedPackageUrl url == " + ParameterBuilder.getQueryUrl(list, requestUrl));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public static String getSharedSucceedUrl(String channelId, String orderId) {
        String requestUrl = getPaySucceedRedPackageUrl() + "redpackage/callback";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().getUserId()));
        list.add(new BasicNameValuePair("channelId", channelId));
        list.add(new BasicNameValuePair("orderId", orderId));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("_debug", "1"));
        LogInfo.log("YDD", "SharedSucceedUrl url == " + ParameterBuilder.getQueryUrl(list, requestUrl));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public static String getHotPatchUrl() {
        String requestUrl = getDynamicHead();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "androidhotpatch"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("patchNo", String.valueOf(PreferencesManager.getInstance().getHotPatchNo())));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public static String getDanmakuSwitch(String vid) {
        String host = isTest() ? "http://t.api.mob.app.letv.com/play/isDanmaku?" : "http://api.mob.app.letv.com/play/isDanmaku?";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance())));
        return ParameterBuilder.getQueryUrl(list, host);
    }

    public static String getNetIPAddress() {
        String requestUrl = getDynamicHead();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "ip"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public static String getDexPatchUrl() {
        String requestUrl = getDynamicHead();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "androidhotpatch"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("patchNo", String.valueOf(PreferencesManager.getInstance().getDexPatchNo())));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair("dexpatch", "1"));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public static String getDrmSoUrl() {
        String host;
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            host = "http://t.api.mob.app.letv.com/libso?";
        } else {
            host = "http://api.mob.app.letv.com/libso?";
        }
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, host);
    }
}
