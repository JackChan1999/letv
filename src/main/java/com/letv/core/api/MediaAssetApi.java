package com.letv.core.api;

import android.text.TextUtils;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.core.BaseApplication;
import com.letv.core.api.PlayRecordApi.MODIFYPWD_PARAMETERS;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.bean.BarrageBean;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.PlayConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.toolbox.ParameterBuilder;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.core.utils.NetworkUtils;
import com.letv.datastatistics.util.DataConstant.PAGE;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;

public class MediaAssetApi {
    private static final String STATIC_APP_BASE_END = ".mindex.html";
    private static volatile MediaAssetApi instance;

    protected MediaAssetApi() {
    }

    public static MediaAssetApi getInstance() {
        if (instance == null) {
            synchronized (MediaAssetApi.class) {
                if (instance == null) {
                    instance = new MediaAssetApi();
                }
            }
        }
        return instance;
    }

    private String getDynamicUrl(boolean isInHongkong) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return isInHongkong ? "http://hk.test2.m.letv.com/android/dynamic.php" : "http://test2.m.letv.com/android/dynamic.php";
        } else {
            return "http://dynamic.meizi.app.m.letv.com/android/dynamic.php";
        }
    }

    private String getDynamicUrl() {
        return getDynamicUrl(false);
    }

    private String getStaticHead(boolean isInHongkong) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return isInHongkong ? "http://hk.test2.m.letv.com/android" : "http://test2.m.letv.com/android";
        } else {
            return "http://static.meizi.app.m.letv.com/android";
        }
    }

    private String getStaticHead() {
        return getStaticHead(false);
    }

    private String getLiveUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.live.app.m.letv.com/android/dynamic.php";
    }

    private String getHotListUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/hotpoint/hotpoint?";
        }
        return "http://api.mob.app.letv.com/hotpoint/hotpoint?";
    }

    private String getMyMessageUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/listmessage/";
        }
        return "http://api.mob.app.letv.com/listmessage/";
    }

    private String getMyFollowUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/follow/followlist";
        }
        return "http://api.mob.app.letv.com/follow/followlist";
    }

    public String getPlayUrl() {
        if (!PreferencesManager.getInstance().isTestApi() || !LetvConfig.isForTest()) {
            return "http://api.mob.app.letv.com/play?";
        }
        if (LetvUtils.isInHongKong()) {
            return "http://hk.t.api.mob.app.letv.com/play?";
        }
        return "http://t.api.mob.app.letv.com/play?";
    }

    private String getFilterHeader(boolean isHongKong) {
        if (!PreferencesManager.getInstance().isTestApi() || !LetvConfig.isForTest()) {
            return "http://api.mob.app.letv.com/filter/list?";
        }
        return (isHongKong ? "http://hk.t.api.mob.app.letv.com/" : "http://t.api.mob.app.letv.com/") + "filter/list?";
    }

    private String getChannelWall() {
        if (!PreferencesManager.getInstance().isTestApi() || !LetvConfig.isForTest()) {
            return "http://api.mob.app.letv.com/channel?";
        }
        if (TextUtils.equals(LetvUtils.getCountryCode(), LetvUtils.COUNTRY_HONGKONG)) {
            return "http://hk.t.api.mob.app.letv.com/channel?";
        }
        return "http://t.api.mob.app.letv.com/channel?";
    }

    public static String getStaticEnd() {
        return STATIC_APP_BASE_END;
    }

    public String getFindUrl(String markId) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "discover"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("markid", markId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(LetvUtils.isInHongKong()), UrlConstdata.getStaticEnd());
    }

    public String getTopicList(boolean isTopicChannel, String markId) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "subjectlist"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, isTopicChannel ? "index" : "hot"));
        params.add(new BasicNameValuePair("markid", markId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(LetvUtils.isInHongKong()), UrlConstdata.getStaticEnd());
    }

    public String getTopicDeatil(String zid, String markId) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "subject"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair(PlayConstant.ZID, zid));
        params.add(new BasicNameValuePair("markid", markId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(LetvUtils.isInHongKong()), UrlConstdata.getStaticEnd());
    }

    public String getDateUrl() {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "booklive"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getDate"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getLiveUrl());
    }

    public String getVideoPlayUrl(String cid, String aid, String zid, String vid, String uid, String tss, String playid, String tm, String uuid, Map<String, String> adMap) {
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("tm", tm);
        map.put("playid", playid);
        if (TextUtils.equals(tss, "ios")) {
            map.put("tss", tss);
        } else {
            map.put("tss", "no");
        }
        map.put("pcode", LetvConfig.getPcode());
        map.put("version", LetvUtils.getClientVersionName());
        if (!(TextUtils.isEmpty(cid) || TextUtils.equals("0", cid))) {
            map.put("cid", cid);
        }
        if (!(TextUtils.isEmpty(zid) || TextUtils.equals("0", zid))) {
            map.put(PlayConstant.ZID, zid);
        }
        if (!(TextUtils.isEmpty(aid) || TextUtils.equals("0", aid))) {
            map.put("pid", aid);
        }
        if (!(TextUtils.isEmpty(vid) || TextUtils.equals("0", vid))) {
            map.put("vid", vid);
        }
        if (!(TextUtils.isEmpty(uid) || TextUtils.equals("0", uid))) {
            map.put("uid", uid);
        }
        if (!TextUtils.isEmpty(uuid)) {
            map.put("vvid", uuid);
        }
        map.put(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance()));
        if (!BaseTypeUtils.isMapEmpty(adMap)) {
            map.putAll(adMap);
        }
        return ParameterBuilder.getQueryUrl(map, getPlayUrl());
    }

    public String getChannelListUrl(String s) {
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, getChannelWall());
    }

    public String getChannelDetailListUrl() {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("ctl", "h265"));
        return ParameterBuilder.getPathUrl(params, getStaticHead(), UrlConstdata.getStaticEnd());
    }

    public String getChannelDetailListUrl(int cid, int updataId, String markid, String pageid, String historyIds, boolean isLoadingMore, String area, String page_num, String type, String pageSize) {
        String url = "";
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("allow_risk_album", PreferencesManager.getInstance().getCopyright() == 1 ? "true" : "false"));
        if (isLoadingMore) {
            params.add(new BasicNameValuePair("num", pageSize));
            params.add(new BasicNameValuePair("page_num", page_num));
            params.add(new BasicNameValuePair("area", area));
            params.add(new BasicNameValuePair("type", type));
        }
        if (cid == 2001) {
            params.add(new BasicNameValuePair("ctl", "h265"));
            return ParameterBuilder.getPathUrl(params, getStaticHead(), UrlConstdata.getStaticEnd());
        }
        params.add(new BasicNameValuePair("ctl", "channelindex55"));
        params.add(new BasicNameValuePair("markid", markid));
        params.add(new BasicNameValuePair(PlayConstant.PAGE_ID, pageid));
        params.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        params.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        params.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.HISTORY_KEY, historyIds));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl(LetvUtils.isInHongKong()));
    }

    public String getVipChannelDetailListUrl(int updataId, String markid, String historyIds, boolean isLoadingMore, String area, String page_num, String type) {
        String url = "";
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("allow_risk_album", PreferencesManager.getInstance().getCopyright() == 1 ? "true" : "false"));
        if (isLoadingMore) {
            params.add(new BasicNameValuePair("num", String.valueOf(30)));
            params.add(new BasicNameValuePair("page_num", page_num));
            params.add(new BasicNameValuePair("area", area));
            params.add(new BasicNameValuePair("type", type));
        }
        params.add(new BasicNameValuePair("ctl", "channelindex55"));
        params.add(new BasicNameValuePair("markid", markid));
        params.add(new BasicNameValuePair("vip", "1"));
        params.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        params.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        params.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.HISTORY_KEY, historyIds));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl(LetvUtils.isInHongKong()));
    }

    public String getChannelSiftListUrl() {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        params.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance())));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getFilterHeader(LetvUtils.isInHongKong()));
    }

    public String getChannelListAfterSiftUrl(int updataId, boolean isAlbum, String src, String cd, String ph, String pt, String pn, String ps, String filterParams, String markId) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        if (isAlbum) {
            params.add(new BasicNameValuePair("ctl", "listalbum60"));
        } else {
            params.add(new BasicNameValuePair("ctl", "listvideo60"));
        }
        params.add(new BasicNameValuePair("allow_risk_album", PreferencesManager.getInstance().getCopyright() == 1 ? "true" : "false"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("src", src));
        params.add(new BasicNameValuePair("cg", cd));
        params.add(new BasicNameValuePair("ph", ph));
        params.add(new BasicNameValuePair("pt", pt));
        params.add(new BasicNameValuePair("pn", pn));
        params.add(new BasicNameValuePair("ps", ps));
        params.add(new BasicNameValuePair("markid", markId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(LetvUtils.isInHongKong()), filterParams + UrlConstdata.getStaticEnd());
    }

    public String getChannelTopListUrl(int updataId, String cid, String markId) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "dayplaytop"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("cid", cid));
        params.add(new BasicNameValuePair("markid", markId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(), UrlConstdata.getStaticEnd());
    }

    public String getWaterMarkUrl(int cid, long pid) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "waterMark"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("cid", cid + ""));
        params.add(new BasicNameValuePair("pid", pid + ""));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String getHotSquareVideoUrl(String pageId, int page) {
        List<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("mpt", "420003_1"));
        params.add(new BasicNameValuePair("page_id", pageId));
        params.add(new BasicNameValuePair("pages", "" + page));
        params.add(new BasicNameValuePair("num", "20"));
        LogInfo.log("wlx", "热点列表 url= " + ParameterBuilder.getQueryUrl(params, getHotListUrl()));
        return ParameterBuilder.getQueryUrl(params, getHotListUrl());
    }

    protected String getCombineHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://t.api.mob.app.letv.com/";
        }
        return "http://api.mob.app.letv.com/";
    }

    public String getLikeCommentUrl(boolean isLike) {
        String baseUrl = "";
        if (isLike) {
            baseUrl = getCombineHead() + "comment/like/region/" + LetvUtils.getCountryCode();
        } else {
            baseUrl = getCombineHead() + "comment/unlike/region/" + LetvUtils.getCountryCode();
        }
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public HashMap<String, String> getLikeCommentUrlParams(int updataId, String commentId, boolean isLike, boolean isComment) {
        List<BasicNameValuePair> list = new ArrayList();
        HashMap<String, String> map = new HashMap();
        setPostCommonParams(map);
        map.put("commentid", commentId);
        map.put("attr", isComment ? "cmt" : "reply");
        return map;
    }

    private void setCommonParams(List<BasicNameValuePair> list) {
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance())));
    }

    private HashMap<String, String> setPostCommonParams(HashMap<String, String> map) {
        map.put("pcode", LetvConfig.getPcode());
        map.put("version", LetvUtils.getClientVersionName());
        map.put(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance()));
        map.put("source", "3");
        return map;
    }

    private void setCommonCombineParams(List<BasicNameValuePair> list, String cid, String zid, String vid, String pid) {
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, LetvUtils.generateDeviceId(BaseApplication.getInstance())));
        if (!(TextUtils.isEmpty(zid) || zid.equals("0"))) {
            list.add(new BasicNameValuePair(PlayConstant.ZID, zid));
        }
        if (!(TextUtils.isEmpty(vid) || vid.equals("0"))) {
            list.add(new BasicNameValuePair("vid", vid));
        }
        if (!(TextUtils.isEmpty(pid) || pid.equals("0"))) {
            list.add(new BasicNameValuePair("pid", pid));
        }
        if (!TextUtils.isEmpty(cid) && !cid.equals("0")) {
            list.add(new BasicNameValuePair("cid", cid));
        }
    }

    public String addCommentUrl() {
        String baseUrl = getCombineHead() + "comment/add";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public HashMap<String, String> addCommentUrlParam(String pid, String vid, String cid, String ctype, String content, String voteState, String imgName, String time) {
        HashMap<String, String> map = new HashMap();
        map.put("pid", pid);
        map.put("xid", vid);
        map.put("cid", cid);
        map.put("ctype", ctype);
        map.put(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        map.put("voteFlag", voteState);
        map.put(PlayConstant.HTIME, time);
        map.put("type", "video");
        return map;
    }

    public String getFavouriteDumpUrl() {
        String baseUrl = getCombineHead() + "favorite/dump";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("source", "3"));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public Map<String, String> getFavouriteDumpParams(String dumpData) {
        HashMap<String, String> map = new HashMap();
        map.put("from_type", "3");
        map.put(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, dumpData);
        map.put("sso_tk", PreferencesManager.getInstance().getSso_tk());
        return map;
    }

    public String getFavouriteListUrl(int page, int pageSize) {
        String baseUrl = getCombineHead() + "favorite/listfavorite";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("source", "3"));
        list.add(new BasicNameValuePair(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, "0"));
        list.add(new BasicNameValuePair("favorite_type", "1"));
        list.add(new BasicNameValuePair("from_type", "3"));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, String.valueOf(page)));
        list.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
        list.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getMultideleteFavouritetUrl(String fIds) {
        String baseUrl = getCombineHead() + "favorite/multidelete";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("source", "3"));
        list.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair("favorite_id", fIds));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getDeleteFavouriteUrl(String pid, String vid, String favoriteType) {
        String baseUrl = getCombineHead() + "favorite/delete";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("source", "3"));
        list.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair("favorite_type", favoriteType));
        list.add(new BasicNameValuePair("play_id", pid));
        list.add(new BasicNameValuePair("video_id", vid));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getAddFavouriteUrl(String pid, String vid, String favoriteType, String fromType) {
        String baseUrl = getCombineHead() + "favorite/add";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("source", "3"));
        list.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair("play_id", pid));
        list.add(new BasicNameValuePair("video_id", vid));
        list.add(new BasicNameValuePair("from_type", fromType));
        list.add(new BasicNameValuePair("favorite_type", favoriteType));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getIsFavouriteUrl(String pid, String vid, String favoriteType) {
        String baseUrl = getCombineHead() + "favorite/isfavorite";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("source", "3"));
        list.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair("play_id", pid));
        list.add(new BasicNameValuePair("video_id", vid));
        list.add(new BasicNameValuePair("favorite_type", favoriteType));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getPatchVoteUrl(String optionId, String num) {
        String baseUrl = getCombineHead() + "vote/vote";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair("id", optionId));
        list.add(new BasicNameValuePair("ip", NetworkUtils.getIp()));
        list.add(new BasicNameValuePair("num", num));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getPatchVoteByStarIdUrl(String starId) {
        String baseUrl = getCombineHead() + "vote/vote";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, PreferencesManager.getInstance().getSso_tk()));
        list.add(new BasicNameValuePair("id", starId));
        list.add(new BasicNameValuePair("type", "1"));
        list.add(new BasicNameValuePair("ip", NetworkUtils.getIp()));
        list.add(new BasicNameValuePair("device_id", LetvUtils.generateDeviceId(BaseApplication.getInstance())));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getStarFollowStatusUrl(String starIds) {
        String baseUrl = getCombineHead() + "follow/followchecklist";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("followid", starIds));
        list.add(new BasicNameValuePair("type", "star"));
        LogInfo.log("clf", "获取是否关注明星 url=" + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getStarFollowUrl(String starId) {
        String baseUrl = getCombineHead() + "follow/follow";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("followid", starId));
        list.add(new BasicNameValuePair("type", "star"));
        LogInfo.log("clf", "明星关注 url=" + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getStarUnFollowUrl(String starId) {
        String baseUrl = getCombineHead() + "follow/cancel";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("followid", starId));
        list.add(new BasicNameValuePair("type", "star"));
        LogInfo.log("clf", "取消明星关注 url=" + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getCommentVoteListUrl(String vid) {
        return getVoteListUrl(vid, 2);
    }

    private String getVoteListUrl(String vid, int type) {
        String baseUrl = getCombineHead() + "vote/votelist";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair("type", String.valueOf(type)));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String replyCommentUrl() {
        String baseUrl = getCombineHead() + "comment/reply";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public HashMap<String, String> replyCommentUrlParams(String pid, String vid, String cid, String commentId, String replyId, String content) {
        HashMap<String, String> map = new HashMap();
        map.put("pid", pid);
        map.put("xid", vid);
        map.put("cid", cid);
        map.put("commentid", commentId);
        map.put("replyid", replyId);
        map.put(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT, content);
        return map;
    }

    public String requestCommentListUrl(String pid, String vid, int page, String cid) {
        String baseUrl = getCombineHead() + "comment/list";
        List<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("pid", pid));
        list.add(new BasicNameValuePair("xid", vid));
        list.add(new BasicNameValuePair("cid", cid));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, String.valueOf(page)));
        list.add(new BasicNameValuePair("rows", "20"));
        list.add(new BasicNameValuePair("source", "3"));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String requestReplyListUrl(String commentId, int replyPage) {
        String baseUrl = getCombineHead() + "comment/replylist";
        ArrayList<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("commentid", commentId));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, String.valueOf(replyPage)));
        list.add(new BasicNameValuePair("rows", "20"));
        list.add(new BasicNameValuePair("source", "3"));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String requestCommmentNumberUrl(String vid, String pid) {
        String baseUrl = getCombineHead() + "comment/commnum";
        ArrayList<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair("pid", pid));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String requestCommmentDetailUrl(String commentId, int page) {
        String baseUrl = getCombineHead() + "comment/replylist";
        ArrayList<BasicNameValuePair> list = new ArrayList();
        setCommonParams(list);
        list.add(new BasicNameValuePair("commentid", commentId));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page + ""));
        list.add(new BasicNameValuePair("rows", "20"));
        list.add(new BasicNameValuePair("source", "3"));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String requestFloatBallUrl() {
        String baseUrl = getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "floatball"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getRecommDataUrl(String exchid, int page, int pagesize, String markid) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "exchange"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        if (!(exchid == null || "".equals(exchid))) {
            params.add(new BasicNameValuePair("exchid", exchid));
        }
        params.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, String.valueOf(page)));
        params.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        params.add(new BasicNameValuePair("markid", markid));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(), getStaticEnd());
    }

    public String getCombineTabDataUrl(String markId, String cid, String zid, String vid, String pid) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        setCommonParams(params);
        if (!(TextUtils.isEmpty(zid) || zid.equals("0"))) {
            params.add(new BasicNameValuePair(PlayConstant.ZID, zid));
        }
        if (!(TextUtils.isEmpty(vid) || vid.equals("0"))) {
            params.add(new BasicNameValuePair("vid", vid));
        }
        if (!(TextUtils.isEmpty(pid) || pid.equals("0"))) {
            params.add(new BasicNameValuePair("pid", pid));
        }
        if (!(TextUtils.isEmpty(cid) || cid.equals("0"))) {
            params.add(new BasicNameValuePair("cid", cid));
        }
        return ParameterBuilder.getQueryUrl(params, getCombineHead() + "play/tabs");
    }

    public String getIntroduceDataUrl(String markId, String cid, String zid, String vid, String pid) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        setCommonCombineParams(params, cid, zid, vid, pid);
        return ParameterBuilder.getQueryUrl(params, getCombineHead() + "play/desc");
    }

    public String getTopicAlbumDataUrl(String markId, String zid) {
        String head = getStaticHead();
        String end = getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "subject"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair(PlayConstant.ZID, zid));
        params.add(new BasicNameValuePair("markid", markId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public String getRelateUrl(String vid, String pid, String cid) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        setCommonCombineParams(params, cid, "", vid, pid);
        return ParameterBuilder.getQueryUrl(params, getCombineHead() + "play/relate");
    }

    public String getEpisodeVListUrl(String pid, String pagenum, String pagesize) {
        return getVListUrl("", "", "", "", pid, pagenum, pagesize, "", "");
    }

    public String getPeriodsVListUrl(String pid, String year, String month) {
        return getVListUrl("", "", "", "", pid, "", "", year, month);
    }

    public String getVListUrl(String updataId, String cid, String zid, String vid, String pid, String pagenum, String pagesize, String year, String month) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        setCommonCombineParams(params, cid, zid, vid, pid);
        if (!TextUtils.isEmpty(pagenum)) {
            params.add(new BasicNameValuePair("pagenum", pagenum));
        }
        if (!TextUtils.isEmpty(pagesize)) {
            params.add(new BasicNameValuePair("pagesize", pagesize));
        }
        if (!TextUtils.isEmpty(year)) {
            params.add(new BasicNameValuePair("year", year));
        }
        if (!TextUtils.isEmpty(month)) {
            params.add(new BasicNameValuePair("month", month));
        }
        return ParameterBuilder.getQueryUrl(params, getCombineHead() + "play/vlist");
    }

    public String getVideoList(String id, String vid, String page, String count, String o, String merge, String markId) {
        String head = getStaticHead();
        String end = UrlConstdata.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "videolist"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair(PAGE.MYSHARE, page));
        params.add(new BasicNameValuePair("s", count));
        params.add(new BasicNameValuePair("o", o));
        params.add(new BasicNameValuePair(PAGE.MYLETV, merge));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("markid", markId));
        LogInfo.log("clf", "直播相关tab页获取视频列表getVideoList..url=" + ParameterBuilder.getPathUrl(params, head, end));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public String getPAlbumVideoPlayCount(String ids, String type) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("ctl", "getPidsInfo"));
        params.add(new BasicNameValuePair("type", type));
        params.add(new BasicNameValuePair("ids", ids));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "批量取得专辑、视频播放数getPAlbumVideoPlayCount..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String requestGetAlbumByIdUrl(String id) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "getalbumbyid"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, getStaticHead(), UrlConstdata.getStaticEnd());
    }

    public String getRequestPlayRecommendurl(String markId, String cid, String pid, String vid) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        setCommonParams(params);
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "playrecommend"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : ""));
        params.add(new BasicNameValuePair("cid", cid));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("vid", vid));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String requestVideoShotText() {
        String head = getStaticHead() + "/mod/mob/ctl/sharewords/act/index/";
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        LogInfo.log("fornia", "requestVideoShotText ParameterBuilder.getPathUrl(params, head, end):" + ParameterBuilder.getPathUrl(params, head, end));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public String requestVideoShotHead() {
        String head = getCombineHead() + "shareimg";
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        LogInfo.log("fornia", "requestVideoShotText ParameterBuilder.getQueryUrl(params, head):" + ParameterBuilder.getQueryUrl(params, head));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String getBarrageListByTime(String cid, String pid, String vid, String amount, String start) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "danmu_list"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        params.add(new BasicNameValuePair(JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START, start));
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("cid", cid));
        params.add(new BasicNameValuePair("key", MD5.MD5Encode(vid + "," + "4Est4DesKSt8s2")));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String createPublishBarrageParams(String vid, String pid, String cid, String color, BarrageBean barrageBean) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "danmu_add"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair(JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START, barrageBean.start));
        params.add(new BasicNameValuePair("txt", barrageBean.txt));
        params.add(new BasicNameValuePair("color", color));
        params.add(new BasicNameValuePair("type", "txt"));
        params.add(new BasicNameValuePair("font", barrageBean.font));
        params.add(new BasicNameValuePair("position", String.valueOf(barrageBean.position)));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("key", MD5.MD5Encode(vid + "," + "4Est4DesKSt8s2")));
        params.add(new BasicNameValuePair("from", "6"));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String createPublishLiveBarrageParams(String color, BarrageBean barrageBean) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "danmu_add"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("sso_tk", PreferencesManager.getInstance().getSso_tk()));
        params.add(new BasicNameValuePair("txt", barrageBean.txt));
        params.add(new BasicNameValuePair("color", color));
        params.add(new BasicNameValuePair("type", "txt"));
        params.add(new BasicNameValuePair("font", barrageBean.font));
        params.add(new BasicNameValuePair("position", String.valueOf(barrageBean.position)));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String getMySystemMessage(int page) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page + ""));
        params.add(new BasicNameValuePair("is_read", "1"));
        String url = getMyMessageUrl();
        if (PreferencesManager.getInstance().isLogin()) {
            url = url + "loginsysmes?";
        } else {
            url = url + "unlogin?";
        }
        return ParameterBuilder.getQueryUrl(params, url);
    }

    public String getMyFollow(int page) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("type", "star"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page + ""));
        params.add(new BasicNameValuePair("pagesize", "20"));
        return ParameterBuilder.getQueryUrl(params, getMyFollowUrl());
    }

    public String getMyMessageRead(int messageId) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("msg_id", messageId + ""));
        return ParameterBuilder.getQueryUrl(params, getMyMessageUrl() + "readMessage?");
    }

    public String getMyMessageReadAll(int type) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        if (type == 1) {
            params.add(new BasicNameValuePair("from", "0,1,2,3,4,5,6,7,8,10"));
        } else {
            params.add(new BasicNameValuePair("from", "9"));
        }
        return ParameterBuilder.getQueryUrl(params, getMyMessageUrl() + "readall?");
    }

    public String getMyMessageDelete(List<String> ids) {
        String idParam = "";
        for (String id : ids) {
            idParam = (idParam + id) + ",";
        }
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("id", idParam));
        return ParameterBuilder.getQueryUrl(params, getMyMessageUrl() + "deletemessage?");
    }

    public String getMyReplyCommentsMessage(int page) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page + ""));
        params.add(new BasicNameValuePair("is_read", "1"));
        String url = getMyMessageUrl();
        if (PreferencesManager.getInstance().isLogin()) {
            url = url + "loginreplymes?";
        }
        return ParameterBuilder.getQueryUrl(params, url);
    }
}
