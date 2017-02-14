package com.letv.core.api;

import android.text.TextUtils;
import com.letv.core.api.PlayRecordApi.MODIFYPWD_PARAMETERS;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.toolbox.ParameterBuilder;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

public class UserCenterApi {
    private static volatile UserCenterApi instance;
    private final String USER_DYNAMIC_APP_URL = "http://dynamic.user.app.m.letv.com/android/dynamic.php";

    protected UserCenterApi() {
    }

    public static UserCenterApi getInstance() {
        if (instance == null) {
            synchronized (UserCenterApi.class) {
                if (instance == null) {
                    instance = new UserCenterApi();
                }
            }
        }
        return instance;
    }

    protected String getDynamicUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.user.app.m.letv.com/android/dynamic.php";
    }

    protected static String getStaticHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android";
        }
        return "http://static.user.app.m.letv.com/android";
    }

    public String getChatRecords(String roomId, boolean server, String tm, int mode) {
        String keyb = roomId + "," + tm + "," + LetvConstant.CHAT_KEY;
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "chatGethistory"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("roomId", roomId));
        params.add(new BasicNameValuePair("server", String.valueOf(server)));
        params.add(new BasicNameValuePair("tm", tm));
        params.add(new BasicNameValuePair("key", MD5.toMd5(keyb)));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        if (mode == 0) {
            params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        } else {
            params.add(new BasicNameValuePair("version", "6.0"));
        }
        LogInfo.log("clf", "获取历史聊天记录requestChatRecords..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String getSendChatMessage(String roomId, String message, String tk, boolean forhost, String tm, String vtkey) {
        String keyb = roomId + "," + tm + "," + LetvConstant.CHAT_KEY;
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "chatSendmessage"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("roomId", roomId));
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("tm", tm));
        params.add(new BasicNameValuePair("from", "6"));
        if (!TextUtils.isEmpty(vtkey)) {
            params.add(new BasicNameValuePair("vtkey", vtkey));
        }
        params.add(new BasicNameValuePair("key", MD5.toMd5(keyb)));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, tk));
        params.add(new BasicNameValuePair("forhost", String.valueOf(forhost)));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "发送聊天消息getSendChatMessage..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String getSendChatBarrageMessage(String roomId, String message, String tk, boolean forhost, String tm, String color, String font, String position, String from) {
        String keyb = roomId + "," + tm + "," + LetvConstant.CHAT_KEY;
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("luamod", "main"));
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "chatSendmessage"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("color", color));
        params.add(new BasicNameValuePair("font", font));
        params.add(new BasicNameValuePair("position", position));
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("roomId", roomId));
        params.add(new BasicNameValuePair("message", message));
        params.add(new BasicNameValuePair("tm", tm));
        params.add(new BasicNameValuePair("key", MD5.toMd5(keyb)));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, tk));
        params.add(new BasicNameValuePair("forhost", String.valueOf(forhost)));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "发送聊天消息getSendChatMessage..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String addPoints(int updataId, String uid, String sso_tk, String desc, POINT_ADD_ACTION action) {
        String baseUrl = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "credit"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "add"));
        if (action == POINT_ADD_ACTION.SHAREVIDEO) {
            params.add(new BasicNameValuePair("action", "sharevideo"));
        } else if (action == POINT_ADD_ACTION.STARTMAPP) {
            params.add(new BasicNameValuePair("action", "startmapp"));
        }
        params.add(new BasicNameValuePair(SocialConstants.PARAM_APP_DESC, desc));
        params.add(new BasicNameValuePair("sso_tk", sso_tk));
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("ZSM", "addPoints url=" + ParameterBuilder.getQueryUrl(params, baseUrl));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public static String encodeUrl() {
        String webSsoUrl = "";
        if (!PreferencesManager.getInstance().isLogin()) {
            return "http://sso.letv.com/user/setUserStatus?tk=&from=mobile_tv&region=" + LetvUtils.getCountryCode();
        }
        String ssoTk = PreferencesManager.getInstance().getSso_tk();
        if (TextUtils.isEmpty(ssoTk)) {
            return webSsoUrl;
        }
        return "http://sso.letv.com/user/setUserStatus?tk=" + ssoTk + "&from=mobile_tv" + "&region=" + LetvUtils.getCountryCode();
    }

    public String getMyPointInfo(int id, String sso_tk) {
        String baseUrl = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "credit"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getActionProgress"));
        params.add(new BasicNameValuePair("action", "video,startmapp,sharevideo"));
        params.add(new BasicNameValuePair("count", "1"));
        params.add(new BasicNameValuePair("sso_tk", sso_tk));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("ZSM", "getMyPointInfo url=" + ParameterBuilder.getQueryUrl(params, baseUrl));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public String getPointInfo(int id, String sso_tk) {
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "credit"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getactioninfo"));
        params.add(new BasicNameValuePair("sso_tk", sso_tk));
        params.add(new BasicNameValuePair("action", "video,startmapp,sharevideo"));
        return ParameterBuilder.getQueryUrl(params, "http://dynamic.user.app.m.letv.com/android/dynamic.php");
    }

    public static String requestShareLink() {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "minfo"));
        params.add(new BasicNameValuePair("ctl", "linkshare"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        params.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String requestLetvOrderWoInfo(int updateId, String usermob, String userid, String isp, String ip) {
        return LetvUrlMaker.getWoStaticHead() + "orderquery?ip=" + ip + "&isp=" + isp + "&pcode=" + LetvHttpApiConfig.PCODE + "&devid=" + Global.DEVICEID + "&userid=" + userid + "&usermob=" + usermob + "&region=" + LetvUtils.getCountryCode();
    }

    public static String requestLetvOrderInfo(int updateId, String usermob, String userid, String username, String type, String isp, String ip) {
        String requestUrl = LetvUrlMaker.getWoStaticHead() + "usersave?ip=" + ip + "&isp=" + isp + "&pcode=" + LetvHttpApiConfig.PCODE + "&devid=" + Global.DEVICEID + "&userid=" + userid + "&usermob=" + usermob + "&type=" + type + "&region=" + LetvUtils.getCountryCode();
        if (username != null) {
            return requestUrl + "&username=" + username;
        }
        return requestUrl;
    }

    public static String getLetvVipServiceProtocolUrl() {
        return "http://minisite.letv.com/zt2015/servicenew/index.shtml";
    }
}
