package com.letv.core.api;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.dlna.service.DLNAService;
import com.letv.android.client.utils.IniFile;
import com.letv.core.BaseApplication;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.bean.GSMInfo;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.PlayConstant;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.toolbox.ParameterBuilder;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.lemallsdk.util.Constants;
import com.letv.lepaysdk.model.TradeInfo;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class PlayRecordApi {
    private static volatile PlayRecordApi instance;
    private final String USER_STATIC_APP_HEAD = "http://static.user.app.m.letv.com/android";

    public interface MODIFYPWD_PARAMETERS {
        public static final String ACT_VALUE = "index";
        public static final String APISIGN_KEY = "apisign";
        public static final String CTL_VALUE = "modifyPwd";
        public static final String MOD_VALUE = "sso";
        public static final String NEED_TK_KEY = "need_tk";
        public static final String NEWPWD_KEY = "newpwd";
        public static final String OLDPWD_KEY = "oldpwd";
        public static final String PLAT_KEY = "plat";
        public static final String TK_KEY = "tk";
    }

    protected PlayRecordApi() {
    }

    public static PlayRecordApi getInstance() {
        if (instance == null) {
            synchronized (PlayRecordApi.class) {
                if (instance == null) {
                    instance = new PlayRecordApi();
                }
            }
        }
        return instance;
    }

    public String submitPlayTrace(int updataId, String cid, String pid, String vid, String nvid, String uid, String vtype, String from, String htime, String sso_tk, GSMInfo gsmInfo) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "minfo"));
        list.add(new BasicNameValuePair("ctl", "cloud"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "add"));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddress()));
        list.add(new BasicNameValuePair("cid", cid));
        list.add(new BasicNameValuePair("pid", pid));
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair("nvid", nvid));
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair("vtype", vtype));
        list.add(new BasicNameValuePair("from", from));
        list.add(new BasicNameValuePair(PlayConstant.HTIME, htime));
        list.add(new BasicNameValuePair("sso_tk", sso_tk));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        list.add(new BasicNameValuePair("longitude", gsmInfo != null ? String.valueOf(gsmInfo.longitude) : ""));
        list.add(new BasicNameValuePair("latitude", gsmInfo != null ? String.valueOf(gsmInfo.latitude) : ""));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String submitPlayTraces(int updataId, String uid, String data, String sso_tk) {
        String baseUrl = UrlConstdata.getDynamicUrl() + "?" + "mod" + SearchCriteria.EQ + "minfo" + "&" + "ctl" + SearchCriteria.EQ + "cloud" + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "import" + "&" + "pcode" + SearchCriteria.EQ + LetvHttpApiConfig.PCODE + "&" + "version" + SearchCriteria.EQ + LetvHttpApiConfig.VERSION;
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA, data));
        list.add(new BasicNameValuePair("sso_tk", sso_tk));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String deletePlayTraces(int updataId, String pid, String vid, String uid, String idstr, String flush, String backdata, String sso_tk) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "minfo"));
        list.add(new BasicNameValuePair("ctl", "cloud"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "del"));
        list.add(new BasicNameValuePair("pid", pid));
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair("idstr", idstr));
        list.add(new BasicNameValuePair("flush", flush));
        list.add(new BasicNameValuePair("backdata", backdata));
        list.add(new BasicNameValuePair("sso_tk", sso_tk));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getPlayTraces(int updataId, String uid, String page, String pagesize, String sso_tk) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "minfo"));
        list.add(new BasicNameValuePair("ctl", "cloud"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "get"));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddress()));
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page));
        list.add(new BasicNameValuePair("pagesize", pagesize));
        list.add(new BasicNameValuePair("sso_tk", sso_tk));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getPlayTrace(int updataId, String uid, String vid, String sso_tk) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "minfo"));
        list.add(new BasicNameValuePair("ctl", "cloud"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getPoint"));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddress()));
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair("vid", vid));
        list.add(new BasicNameValuePair("sso_tk", sso_tk));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String searchPlayTraces(int updataId, String pids, String vids, String sso_tk) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "minfo"));
        list.add(new BasicNameValuePair("ctl", "cloud"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, DLNAService.SEARCH_KEY));
        list.add(new BasicNameValuePair("pids", pids));
        list.add(new BasicNameValuePair("vids", vids));
        list.add(new BasicNameValuePair("sso_tk", sso_tk));
        list.add(new BasicNameValuePair("pcode", LetvHttpApiConfig.PCODE));
        list.add(new BasicNameValuePair("version", LetvHttpApiConfig.VERSION));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String openIDOAuthLoginUrl(int updataId, String tk) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "thirdUserLogin"));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, tk));
        list.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddressForLogin()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String loginWeixin(int updataId, String accessToken, String openId, String plat, String equipType, String equipID, String softID, String pcode, String version) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "appssoweixin"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("access_token", accessToken));
        list.add(new BasicNameValuePair("openid", openId));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.PLAT_KEY, plat));
        list.add(new BasicNameValuePair("equipID", equipID));
        list.add(new BasicNameValuePair("softID", softID));
        list.add(new BasicNameValuePair("pcode", pcode));
        list.add(new BasicNameValuePair("version", version));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddressForLogin()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String loginSina(int updataId, String accessToken, String openId, String plat, String equipType, String equipID, String softID, String pcode, String version) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "appssosina"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("access_token", accessToken));
        list.add(new BasicNameValuePair("uid", openId));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.PLAT_KEY, plat));
        list.add(new BasicNameValuePair("equipType", equipType));
        list.add(new BasicNameValuePair("equipID", equipID));
        list.add(new BasicNameValuePair("pcode", pcode));
        list.add(new BasicNameValuePair("softID", softID));
        list.add(new BasicNameValuePair("version", version));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddressForLogin()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String loginQQ(int updataId, String accessToken, String openId, String plat, String equipType, String equipID, String softID, String pcode, String version, String appkey) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "appssoqq"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("access_token", accessToken));
        list.add(new BasicNameValuePair("openid", openId));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.PLAT_KEY, plat));
        list.add(new BasicNameValuePair(IniFile.APPKEY, appkey));
        list.add(new BasicNameValuePair("equipType", equipType));
        list.add(new BasicNameValuePair("equipID", equipID));
        list.add(new BasicNameValuePair("softID", softID));
        list.add(new BasicNameValuePair("pcode", pcode));
        list.add(new BasicNameValuePair("version", version));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddressForLogin()));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getCoolpadTokenByCode(String code, GSMInfo gsmInfo) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "appssocoolpad"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("code", code));
        list.add(new BasicNameValuePair("equipType", "androidphone"));
        list.add(new BasicNameValuePair("equipID", Global.DEVICEID));
        list.add(new BasicNameValuePair("softID", Global.VERSION));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.PLAT_KEY, "mobile_tv"));
        list.add(new BasicNameValuePair("imei", LetvUtils.getIMEI()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddressForLogin()));
        list.add(new BasicNameValuePair("longitude", gsmInfo != null ? String.valueOf(gsmInfo.longitude) : ""));
        list.add(new BasicNameValuePair("latitude", gsmInfo != null ? String.valueOf(gsmInfo.latitude) : ""));
        list.add(new BasicNameValuePair("cid", gsmInfo != null ? String.valueOf(gsmInfo.cid) : ""));
        list.add(new BasicNameValuePair("lac", gsmInfo != null ? String.valueOf(gsmInfo.lac) : ""));
        list.add(new BasicNameValuePair("pcode", Global.PCODE));
        list.add(new BasicNameValuePair("version", Global.VERSION_CODE + ""));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String loginBaseUrl() {
        String baseUrl = UrlConstdata.getDynamicUrl() + "?" + "mod" + SearchCriteria.EQ + "passport" + "&" + "ctl" + SearchCriteria.EQ + "index" + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "newLogin" + "&" + "mac" + SearchCriteria.EQ + LetvUtils.getMacAddressForLogin() + "&" + "pcode" + SearchCriteria.EQ + Global.PCODE + "&" + LetvUtils.COUNTRY_CODE_KEY + SearchCriteria.EQ + LetvUtils.getCountryCode() + "&" + "version" + SearchCriteria.EQ + Global.VERSION;
        LogInfo.log("loginBaseUrl == " + baseUrl);
        return baseUrl;
    }

    public HashMap<String, String> login(int updataId, String loginname, String password, String registService, String profile, GSMInfo gsmInfo) {
        HashMap<String, String> postParams = new HashMap();
        postParams.put("loginname", loginname);
        postParams.put("password", password);
        postParams.put("registService", registService);
        postParams.put("profile", profile);
        postParams.put(MODIFYPWD_PARAMETERS.PLAT_KEY, "mobile_tv");
        postParams.put(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, Global.DEVICEID);
        postParams.put(TradeInfo.SIGN, LetvTools.generateLoginSignKey(loginname, password));
        postParams.put("longitude", gsmInfo != null ? String.valueOf(gsmInfo.longitude) : "");
        postParams.put("latitude", gsmInfo != null ? String.valueOf(gsmInfo.latitude) : "");
        postParams.put("lac", gsmInfo != null ? String.valueOf(gsmInfo.lac) : "");
        postParams.put("cid", gsmInfo != null ? String.valueOf(gsmInfo.cid) : "");
        postParams.put("imei", LetvUtils.getIMEI());
        postParams.put("mac", LetvUtils.getMacAddressForLogin());
        return postParams;
    }

    public String requestUserInfoByTk(String updataId, String tk) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "getUserByTk"));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.TK_KEY, tk));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddress()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "RequestUserByTokenTask url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String sendBackPwdEmail(int updataId, String email) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "sendBackPwdEmail"));
        list.add(new BasicNameValuePair(NotificationCompat.CATEGORY_EMAIL, email));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "sendBackPwdEmail url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String saleNotes(int updataId, String userId, String status, String day, String page, String pageSize) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put(UserInfoDb.USER_ID, userId);
        signMap.put("status", status);
        signMap.put("day", day);
        signMap.put("version", LetvUtils.getClientVersionName());
        String signValue = LetvTools.generSignedKey53(signMap);
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "saleNew"));
        list.add(new BasicNameValuePair(UserInfoDb.USER_ID, userId));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page));
        list.add(new BasicNameValuePair("pagesize", pageSize));
        list.add(new BasicNameValuePair("status", status));
        list.add(new BasicNameValuePair("day", day));
        list.add(new BasicNameValuePair(TradeInfo.SIGN, signValue));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "saleNotes url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String requestTicketShowList(int updataId, String userId, String page, String size) {
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "queryServletList"));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        list.add(new BasicNameValuePair(UserInfoDb.USER_ID, userId));
        list.add(new BasicNameValuePair(MyDownloadActivityConfig.PAGE, page));
        list.add(new BasicNameValuePair("size", size));
        Log.e("ZSM", "requestTicketShowList url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String requestTicketUrl(int updataId, String userId, String days) {
        String baseUrl;
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            baseUrl = "http://t.api.mob.app.letv.com/yuanxian/myTickets?";
        } else {
            baseUrl = "http://api.mob.app.letv.com/yuanxian/myTickets?";
        }
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("uid", userId));
        list.add(new BasicNameValuePair("days", days));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddress()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "requestTicketUrl url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String queryRecord(int updataId, String uid, String username, String starttime, String endtime, String query, String day, String deptid, String pid, String productid) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("uid", uid);
        signMap.put("username", username);
        signMap.put("query", query);
        signMap.put("version", LetvUtils.getClientVersionName());
        String signValue = LetvTools.generSignedKey53(signMap);
        String baseUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", "passport"));
        list.add(new BasicNameValuePair("ctl", "index"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "queryrecord"));
        list.add(new BasicNameValuePair("uid", uid));
        list.add(new BasicNameValuePair("username", username));
        list.add(new BasicNameValuePair("starttime", starttime));
        list.add(new BasicNameValuePair("endtime", endtime));
        list.add(new BasicNameValuePair("query", query));
        list.add(new BasicNameValuePair("day", day));
        list.add(new BasicNameValuePair("deptid", deptid));
        list.add(new BasicNameValuePair("pid", pid));
        list.add(new BasicNameValuePair(TradeInfo.SIGN, signValue));
        list.add(new BasicNameValuePair("productid", productid));
        list.add(new BasicNameValuePair("mac", LetvUtils.getMacAddress()));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "queryRecord url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String modifyPwdBaseUrl(int updataId) {
        return UrlConstdata.getDynamicUrl() + "?" + "mod" + SearchCriteria.EQ + MODIFYPWD_PARAMETERS.MOD_VALUE + "&" + "ctl" + SearchCriteria.EQ + MODIFYPWD_PARAMETERS.CTL_VALUE + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "index" + "&" + "pcode" + SearchCriteria.EQ + LetvConfig.getPcode() + "&" + "version" + SearchCriteria.EQ + LetvUtils.getClientVersionName() + "&" + LetvUtils.COUNTRY_CODE_KEY + SearchCriteria.EQ + LetvUtils.getCountryCode();
    }

    public String getWXTokenByCode(int updataId, String code, String appid, String secret, String grandType) {
        String baseUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("code", code));
        list.add(new BasicNameValuePair("appid", appid));
        list.add(new BasicNameValuePair("secret", secret));
        list.add(new BasicNameValuePair("grant_type", grandType));
        Log.e("ZSM", "getWXTokenByCode url == " + ParameterBuilder.getQueryUrl(list, baseUrl));
        return ParameterBuilder.getQueryUrl(list, baseUrl);
    }

    public String getSinaLoginUrl() {
        return "http://dynamic.app.m.letv.com/android/dynamic.php?mod=passport&ctl=index&act=appsina&pcode=" + LetvConfig.getPcode() + "&version=" + LetvUtils.getClientVersionName() + "&" + LetvUtils.COUNTRY_CODE_KEY + SearchCriteria.EQ + LetvUtils.getCountryCode();
    }

    public String getQQLoginUrl() {
        return "http://dynamic.app.m.letv.com/android/dynamic.php?mod=passport&ctl=index&act=appqq&pcode=" + LetvConfig.getPcode() + "&version=" + LetvUtils.getClientVersionName() + "&" + LetvUtils.COUNTRY_CODE_KEY + SearchCriteria.EQ + LetvUtils.getCountryCode();
    }

    public String requestGetverificationCode(int updataId, String key, String tm) {
        String requestUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "getCaptcha"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("key", key));
        list.add(new BasicNameValuePair("tm", tm));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "requestGetverificationCode url == " + ParameterBuilder.getQueryUrl(list, requestUrl));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public String requestCheckMob(int updataId, String mobile, String key) {
        String requestUrl = UrlConstdata.getDynamicUrl();
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("luamod", "main"));
        list.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "mobilecheck"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("mobile", mobile));
        list.add(new BasicNameValuePair("key", key));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "requestCheckMob url == " + ParameterBuilder.getQueryUrl(list, requestUrl));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    @Deprecated
    public String s_sendMobile(int updataId, String mobile, String ver, String Cookid) {
        String requestUrl;
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            requestUrl = "http://test2.m.letv.com/android/dynamic.php";
        } else {
            requestUrl = "http://dynamic.user.app.m.letv.com/android/dynamic.php";
        }
        String str = MD5.toMd5("action=reg&mobile=" + mobile + "&pcode=" + LetvConfig.getPcode() + "&plat=mobile_tv" + "&version=" + LetvUtils.getClientVersionName() + "&poi345");
        List<BasicNameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("mod", MODIFYPWD_PARAMETERS.MOD_VALUE));
        list.add(new BasicNameValuePair("ctl", "clientSendMsg"));
        list.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        list.add(new BasicNameValuePair("mobile", mobile));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.PLAT_KEY, "mobile_tv"));
        list.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, str));
        list.add(new BasicNameValuePair("action", "reg"));
        list.add(new BasicNameValuePair("captchaValue", ver));
        list.add(new BasicNameValuePair("captchaId", Cookid));
        list.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        list.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        Log.e("ZSM", "s_sendMobile url == " + ParameterBuilder.getQueryUrl(list, requestUrl));
        return ParameterBuilder.getQueryUrl(list, requestUrl);
    }

    public String registerBaseUrl() {
        return getRegisterUrl() + "?" + "mod" + SearchCriteria.EQ + MODIFYPWD_PARAMETERS.MOD_VALUE + "&" + "ctl" + SearchCriteria.EQ + "addUser" + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "index" + "&" + "pcode" + SearchCriteria.EQ + LetvConfig.getPcode() + "&" + "version" + SearchCriteria.EQ + LetvUtils.getClientVersionName();
    }

    public HashMap<String, String> registerParameter(int updataId, String email, String mobile, String password, String nickname, String gender, String registService, String vcode) {
        HashMap<String, String> postParams = new HashMap();
        postParams.put(NotificationCompat.CATEGORY_EMAIL, getRegString(email));
        postParams.put("mobile", getRegString(mobile));
        postParams.put("password", getRegString(password));
        postParams.put(MODIFYPWD_PARAMETERS.PLAT_KEY, "mobile_tv");
        postParams.put("code", getRegString(vcode));
        postParams.put("nickname", getRegString(nickname));
        postParams.put("gender", getRegString(gender));
        postParams.put("registService", "");
        postParams.put("deviceid", "");
        postParams.put("vcode", getRegString(vcode));
        if (TextUtils.isEmpty(email)) {
            postParams.put("sendmail", "0");
        } else {
            postParams.put("sendmail", "1");
        }
        postParams.put("next_action", "");
        postParams.put("equipType", "");
        postParams.put("equipID", "");
        postParams.put("softID", "");
        postParams.put("dev_id", Global.DEVICEID);
        return postParams;
    }

    public String getMineFocusImageUrl(String cmsId) {
        return getStaticHead() + "/mod/mob/ctl/block/act/index/id/" + cmsId + "/isnew/" + (LetvUtils.isNewUser() ? 1 : 0) + "/pcode/" + LetvTools.getPcode() + "/version/" + LetvTools.getClientVersionName() + "/region/" + LetvUtils.getCountryCode() + ".mindex.html";
    }

    public String getMineListUrl(String markId, String userId, String token) {
        String realUrl = LetvUtils.genLangResRequestUrl(UrlConstdata.getDynamicUrl(LetvUtils.isInHongKong()) + "?mod=mob&ctl=profile&act=index&pcode=" + LetvTools.getPcode() + "&version=" + LetvTools.getClientVersionName() + "&userid=" + userId + "&sso_tk=" + token + "&markid=" + markId, 0);
        LogInfo.log("ZSM", "requestUrl getMineListUrl == " + realUrl);
        return realUrl;
    }

    public static String postExceptionInfo(String uuid, String mobile, String feedback) {
        String uploadUrl = getUploadStaticHead();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "uploaderpic"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "uploadedfile"));
        params.add(new BasicNameValuePair(Constants.UUID, uuid));
        params.add(new BasicNameValuePair("mobile", mobile));
        params.add(new BasicNameValuePair("key", LetvTools.generateExceptionFilesKey(uuid)));
        params.add(new BasicNameValuePair("pcode", LetvTools.getPcode()));
        params.add(new BasicNameValuePair("version", LetvTools.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, uploadUrl, LetvHttpApiConfig.getStaticEnd(), false);
    }

    public String requestSpread(int updataId, String markid) {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "spread"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("markid", markid));
        params.add(new BasicNameValuePair("pcode", LetvTools.getPcode()));
        params.add(new BasicNameValuePair("version", LetvTools.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String requestPraiseInfo(int updataId) {
        String baseUrl = getUserDynamicUrl();
        String uid = LetvUtils.getUID();
        String userName = LetvUtils.getLoginUserName();
        String deviceId = LetvUtils.generateDeviceId(BaseApplication.getInstance());
        StringBuilder md5Sb = new StringBuilder();
        md5Sb.append("uid=" + uid + "&");
        md5Sb.append("username=" + userName + "&");
        md5Sb.append("devid=" + deviceId + "&");
        md5Sb.append("pcode=" + LetvConfig.getPcode() + "&");
        md5Sb.append("version=" + LetvUtils.getClientVersionName() + "&");
        md5Sb.append("letvpraise2014");
        String md5Sign = MD5.toMd5(md5Sb.toString());
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "praiseactivity"));
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("username", userName));
        params.add(new BasicNameValuePair(HOME_RECOMMEND_PARAMETERS.DEVICEID_KEY, deviceId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair(TradeInfo.SIGN, md5Sign));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    private static String getUploadStaticHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android";
        }
        return "http://upload.app.m.letv.com/android";
    }

    private static String getStaticHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android";
        }
        return "http://static.app.m.letv.com/android";
    }

    private static String getUserDynamicUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.user.app.m.letv.com/android/dynamic.php";
    }

    private String getRegString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }

    protected String getRegisterUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.user.app.m.letv.com/android/dynamic.php";
    }
}
