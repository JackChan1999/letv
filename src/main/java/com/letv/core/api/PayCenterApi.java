package com.letv.core.api;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.core.api.PlayRecordApi.MODIFYPWD_PARAMETERS;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.AlipayConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.PlayConstant;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.toolbox.ParameterBuilder;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.core.utils.external.alipay.RequestValue;
import com.letv.lepaysdk.model.TradeInfo;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.connect.common.Constants;
import com.tencent.open.GameAppOperation;
import com.tencent.open.SocialConstants;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.message.BasicNameValuePair;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class PayCenterApi {
    private static final String ALIPAY_ONE_KEY_BACK_URL = "http://yuanxian.letv.com/letv/newPay.ldo";
    private static final String ALIPAY_ONE_KEY_BUY_TYPE = "2";
    private static final String ALIPAY_ONE_KEY_FRONT_URL = "letvclient://viporderdetail";
    private static final String ALIPAY_ONE_KEY_PID = "0";
    private static volatile PayCenterApi instance;
    private final String PAY_DYNAMIC_APP_URL = "http://dynamic.pay.app.m.letv.com/android/dynamic.php";
    private final String PAY_LETV_PAY_HEAD = "http://api.zhifu.letv.com/";
    private final String PAY_STATIC_APP_HEAD = "http://static.pay.app.m.letv.com/android";

    protected PayCenterApi() {
    }

    public static PayCenterApi getInstance() {
        if (instance == null) {
            synchronized (PayCenterApi.class) {
                if (instance == null) {
                    instance = new PayCenterApi();
                }
            }
        }
        return instance;
    }

    private String getDynamicUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.pay.app.m.letv.com/android/dynamic.php";
    }

    private String getNormalDynamicUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android/dynamic.php";
        }
        return "http://dynamic.app.m.letv.com/android/dynamic.php";
    }

    private String getStaticHead() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return "http://test2.m.letv.com/android";
        }
        return "http://static.pay.app.m.letv.com/android";
    }

    public String requestVIPProduct(String updataId, String svip, String uid) {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "minfo"));
        params.add(new BasicNameValuePair("ctl", "vipproduct"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair(ControlAction.ACTION_KEY_UP, "1"));
        params.add(new BasicNameValuePair("svip", svip));
        params.add(new BasicNameValuePair(UserInfoDb.USER_ID, uid));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("markid", updataId));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public String requestVipProduct(String svip, String updataId) {
        String head = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "productList"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("end", svip));
        params.add(new BasicNameValuePair("markid", updataId));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestHongkongOrder(String productid, String pid, String price, String product_name, String product_desc, String merchant_business_id, String callBackUrl) {
        String head = getNormalDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "iappay"));
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "orderId"));
        params.add(new BasicNameValuePair("productid", productid));
        params.add(new BasicNameValuePair("username", PreferencesManager.getInstance().getUserName()));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("uid", PreferencesManager.getInstance().getUserId()));
        params.add(new BasicNameValuePair("price", price));
        params.add(new BasicNameValuePair("itemamt", price));
        params.add(new BasicNameValuePair("product_name", product_name));
        params.add(new BasicNameValuePair("product_desc", product_desc));
        params.add(new BasicNameValuePair("merchant_business_id", merchant_business_id));
        params.add(new BasicNameValuePair("my_order_type", "WAP"));
        params.add(new BasicNameValuePair("call_back_url", callBackUrl));
        params.add(new BasicNameValuePair("channel_ids", ""));
        params.add(new BasicNameValuePair("companyurl", ""));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("markid", ""));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestPayKind(String monthType, String vipType, String updataId) {
        String head = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "payType"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("monthType", monthType));
        params.add(new BasicNameValuePair("end", vipType));
        params.add(new BasicNameValuePair("markid", updataId));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestSimpleVipPackage(String updataId) {
        String head = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "payPackagePre"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("markid", updataId));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestAutoPayWithOneKey(String activityIds, String corderid, String productid, String price, String svip, String autoRenewFlag, String payType) {
        String head = getDynamicUrl();
        String backUrl = ALIPAY_ONE_KEY_BACK_URL;
        String fronturl = ALIPAY_ONE_KEY_FRONT_URL;
        String buyType = "2";
        String pid = "0";
        String apisign = MD5.toMd5("corderid=" + corderid + "&pcode=" + LetvConfig.getPcode() + "&productid=" + productid + "&svip=" + svip + "&userid=" + PreferencesManager.getInstance().getUserId() + "&version=" + LetvUtils.getClientVersionName() + "&poi345");
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "payAutorenew"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("userid", PreferencesManager.getInstance().getUserId()));
        params.add(new BasicNameValuePair("productid", productid));
        String str = "corderid";
        if (TextUtils.isEmpty(corderid)) {
            corderid = "0";
        }
        params.add(new BasicNameValuePair(str, corderid));
        params.add(new BasicNameValuePair("price", price));
        params.add(new BasicNameValuePair("svip", svip));
        params.add(new BasicNameValuePair("buyType", buyType));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("activityIds", activityIds));
        params.add(new BasicNameValuePair("backurl", backUrl));
        params.add(new BasicNameValuePair("fronturl", fronturl));
        params.add(new BasicNameValuePair("autoRenewFlag", autoRenewFlag));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, apisign));
        params.add(new BasicNameValuePair(AlipayConstant.ALIPAY_PAY_TYPE_MOBILE_ALL_SCREEN_FLAG, payType));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestAutoSignWithOneKey(String activityIds, String corderid, String productid, String price, String svip, String autoRenewFlag) {
        String head = getDynamicUrl();
        String backUrl = ALIPAY_ONE_KEY_BACK_URL;
        String fronturl = ALIPAY_ONE_KEY_FRONT_URL;
        String buyType = "2";
        String pid = "0";
        String apisign = MD5.toMd5("corderid=" + corderid + "&pcode=" + LetvConfig.getPcode() + "&productid=" + productid + "&svip=" + svip + "&userid=" + PreferencesManager.getInstance().getUserId() + "&version=" + LetvUtils.getClientVersionName() + "&poi345");
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "payMobile15"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("userid", PreferencesManager.getInstance().getUserId()));
        params.add(new BasicNameValuePair("productid", productid));
        String str = "corderid";
        if (TextUtils.isEmpty(corderid)) {
            corderid = "0";
        }
        params.add(new BasicNameValuePair(str, corderid));
        params.add(new BasicNameValuePair("price", price));
        params.add(new BasicNameValuePair("svip", svip));
        params.add(new BasicNameValuePair("buyType", buyType));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("activityIds", activityIds));
        params.add(new BasicNameValuePair("fronturl", fronturl));
        params.add(new BasicNameValuePair("autoRenewFlag", autoRenewFlag));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, apisign));
        params.add(new BasicNameValuePair("backurl", backUrl));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestAutoSignPayStatus(String svip) {
        String head = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "checkOneKey"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("userid", PreferencesManager.getInstance().getUserId()));
        params.add(new BasicNameValuePair("svip", svip));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestAutoSignUserStatus(String svip) {
        String head = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "autorenew"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("userid", PreferencesManager.getInstance().getUserId()));
        params.add(new BasicNameValuePair("svip", svip));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String requestAutoSignMaintain(String autorenew_type) {
        String head = getDynamicUrl();
        String apiSign = MD5.toMd5("autorenew_type=" + autorenew_type + "&pcode=" + LetvConfig.getPcode() + "&userid=" + PreferencesManager.getInstance().getUserId() + "&version=" + LetvUtils.getClientVersionName() + "&poi345");
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair("ctl", "turnAutorenew"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("userid", PreferencesManager.getInstance().getUserId()));
        params.add(new BasicNameValuePair("autorenew_type", autorenew_type));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, apiSign));
        return ParameterBuilder.getQueryUrl(params, head);
    }

    public String pay(int updataId, String deptno, String username, String commodity, String price, String merOrder, String payType, String service, String pid, String productId, String svip, String activityId) {
        Bundle params = new Bundle();
        params.putString("mod", "passport");
        params.putString("ctl", "index");
        params.putString(SocialConstants.PARAM_ACT, "offline");
        params.putString("deptno", deptno);
        params.putString("username", username);
        params.putString("commodity", commodity);
        params.putString("price", price);
        params.putString("merOrder", merOrder);
        params.putString("payType", payType);
        params.putString(NotificationCompat.CATEGORY_SERVICE, service);
        params.putString("pid", pid);
        params.putString("productid", productId);
        params.putString("svip", svip);
        params.putString("activityId", activityId);
        params.putString("pcode", LetvConfig.getPcode());
        params.putString("version", LetvUtils.getClientVersionName());
        params.putString("deviceid", Global.DEVICEID);
        ArrayList<String> list = new ArrayList();
        list.add("mod");
        list.add("ctl");
        list.add(SocialConstants.PARAM_ACT);
        list.add("deptno");
        list.add("username");
        list.add("commodity");
        list.add("price");
        list.add("merOrder");
        list.add("payType");
        list.add(NotificationCompat.CATEGORY_SERVICE);
        list.add("pid");
        list.add("productid");
        list.add("svip");
        list.add("activityId");
        list.add("pcode");
        list.add("version");
        list.add("deviceid");
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        sb.append("letv&");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();
            sb.append(s);
            sb.append(SearchCriteria.EQ);
            sb.append(params.get(s));
            sb.append("&");
        }
        sb.append(MainActivity.THIRD_PARTY_LETV);
        String baseUrl = null;
        try {
            baseUrl = getDynamicUrl() + "?" + "mod" + SearchCriteria.EQ + "passport" + "&" + "ctl" + SearchCriteria.EQ + "index" + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "offline" + "&" + "deptno" + SearchCriteria.EQ + deptno + "&" + "username" + SearchCriteria.EQ + username + "&" + "commodity" + SearchCriteria.EQ + URLEncoder.encode(commodity, "UTF-8") + "&" + "price" + SearchCriteria.EQ + price + "&" + "merOrder" + SearchCriteria.EQ + merOrder + "&" + "payType" + SearchCriteria.EQ + payType + "&" + NotificationCompat.CATEGORY_SERVICE + SearchCriteria.EQ + service + "&" + "pid" + SearchCriteria.EQ + pid + "&" + "productid" + SearchCriteria.EQ + productId + "&" + "svip" + SearchCriteria.EQ + svip + "&" + "activityId" + SearchCriteria.EQ + activityId + "&" + "pcode" + SearchCriteria.EQ + LetvConfig.getPcode() + "&" + "version" + SearchCriteria.EQ + LetvUtils.getClientVersionName() + "&" + "deviceid" + SearchCriteria.EQ + Global.DEVICEID + "&" + GameAppOperation.GAME_SIGNATURE + SearchCriteria.EQ + MD5.toMd5(sb.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return baseUrl;
    }

    public String requestAlipayData(int updataId, RequestValue requestValue) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("username", requestValue.getUsername());
        signMap.put("productid", requestValue.getProductid());
        signMap.put("userid", requestValue.getUserid());
        signMap.put("version", LetvUtils.getClientVersionName());
        String signValue = LetvTools.generSignedKey53(signMap);
        String baseUrl = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "simpleBank"));
        params.add(new BasicNameValuePair("userid", requestValue.getUserid()));
        params.add(new BasicNameValuePair("username", requestValue.getUsername()));
        params.add(new BasicNameValuePair("price", requestValue.getPrice()));
        params.add(new BasicNameValuePair("productid", requestValue.getProductid()));
        params.add(new BasicNameValuePair("svip", requestValue.getSvip()));
        params.add(new BasicNameValuePair("activityId", requestValue.getActivityId()));
        params.add(new BasicNameValuePair("renewFlag", requestValue.getRenewFlag()));
        params.add(new BasicNameValuePair(TradeInfo.SIGN, signValue));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public String requestYingchaoJianquan(String pid, String liveid, String streamId, String userId) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("pid", pid);
        signMap.put("liveid", liveid);
        signMap.put("from", "mobile");
        signMap.put(PlayConstant.LIVE_STREAMID, streamId);
        signMap.put("splatId", "1003");
        signMap.put(UserInfoDb.USER_ID, userId);
        signMap.put("version", LetvUtils.getClientVersionName());
        signMap.put("pcode", LetvConfig.getPcode());
        String signValue = LetvTools.generSignedKey53(signMap);
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("ctl", "livevalidate"));
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, signValue));
        params.add(new BasicNameValuePair("pid", pid));
        params.add(new BasicNameValuePair("liveid", liveid));
        params.add(new BasicNameValuePair("from", "mobile"));
        params.add(new BasicNameValuePair(PlayConstant.LIVE_STREAMID, streamId));
        params.add(new BasicNameValuePair("splatId", "1003"));
        params.add(new BasicNameValuePair(UserInfoDb.USER_ID, userId));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "请求英超鉴权requestYingchaoJianquan..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String requestYingchaoTicketConsume(String liveid, String userid) {
        if (TextUtils.isEmpty(liveid) || liveid.trim().length() != 16) {
            throw new RuntimeException("param error ! liveid must have 16 numbers ! by zlb");
        }
        String tickettype = "1";
        String channel = LetvUtils.getChannelFromLiveid(liveid);
        String category = LetvUtils.getCategoryFromLiveid(liveid);
        String season = LetvUtils.getSeasonFromLiveid(liveid);
        String turn = LetvUtils.getTurnFromLiveid(liveid);
        String game = LetvUtils.getGameFromLiveid(liveid);
        HashMap<String, String> signMap = new HashMap();
        signMap.put("userid", userid);
        signMap.put("tickettype", tickettype);
        signMap.put("channel", channel);
        signMap.put(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, category);
        signMap.put("season", season);
        signMap.put("turn", turn);
        signMap.put("game", game);
        signMap.put("version", LetvUtils.getClientVersionName());
        signMap.put("pcode", LetvConfig.getPcode());
        String signValue = LetvTools.generSignedKey53(signMap);
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("ctl", "liveuseticket"));
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, signValue));
        params.add(new BasicNameValuePair("tickettype", tickettype));
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("channel", channel));
        params.add(new BasicNameValuePair(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, category));
        params.add(new BasicNameValuePair("season", season));
        params.add(new BasicNameValuePair("turn", turn));
        params.add(new BasicNameValuePair("game", game));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "请求使用英超直播券接口requestYingchaoTicketConsume..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String requestYingchaoTicketInfo(String liveid, String userid) {
        String channel = LetvUtils.getChannelFromLiveid(liveid);
        String category = LetvUtils.getCategoryFromLiveid(liveid);
        String season = LetvUtils.getSeasonFromLiveid(liveid);
        String turn = LetvUtils.getTurnFromLiveid(liveid);
        String game = LetvUtils.getGameFromLiveid(liveid);
        HashMap<String, String> signMap = new HashMap();
        signMap.put("userid", userid);
        signMap.put("channel", channel);
        signMap.put(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, category);
        signMap.put("season", season);
        signMap.put("version", LetvUtils.getClientVersionName());
        signMap.put("pcode", LetvConfig.getPcode());
        String signValue = LetvTools.generSignedKey53(signMap);
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("ctl", "livegetticket"));
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("userid", userid));
        params.add(new BasicNameValuePair("channel", channel));
        params.add(new BasicNameValuePair(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, category));
        params.add(new BasicNameValuePair("season", season));
        params.add(new BasicNameValuePair("turn", turn));
        params.add(new BasicNameValuePair("game", game));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, signValue));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "查询英超直播券接口getDate..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }

    public String requestWxpayData(int updataId, RequestValue requestValue) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("username", requestValue.getUsername());
        signMap.put("productid", requestValue.getProductid());
        signMap.put("productname", requestValue.getProductname());
        signMap.put("version", LetvUtils.getClientVersionName());
        String signValue = LetvTools.generSignedKey53(signMap);
        String baseUrl = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair("corderid", "0"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "wx"));
        params.add(new BasicNameValuePair("username", requestValue.getUsername()));
        params.add(new BasicNameValuePair("price", requestValue.getPrice()));
        params.add(new BasicNameValuePair(Constants.PARAM_PLATFORM, AbstractSpiCall.ANDROID_CLIENT_TYPE));
        params.add(new BasicNameValuePair("deptid", "130"));
        params.add(new BasicNameValuePair("pid", requestValue.getPid()));
        params.add(new BasicNameValuePair("productid", requestValue.getProductid()));
        params.add(new BasicNameValuePair("productname", requestValue.getProductname()));
        params.add(new BasicNameValuePair("productdesc", requestValue.getDesc()));
        params.add(new BasicNameValuePair("svip", requestValue.getSvip()));
        params.add(new BasicNameValuePair("activityId", requestValue.getActivityId()));
        params.add(new BasicNameValuePair("renewFlag", requestValue.getRenewFlag()));
        params.add(new BasicNameValuePair("deviceid", Global.DEVICEID));
        params.add(new BasicNameValuePair(TradeInfo.SIGN, signValue));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public String requestMobilePayData(int updataId, String price, String mobileNum, String userName, String userId, String pay_type_id, String svip) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("pay_type_id", pay_type_id);
        signMap.put("userid", userId);
        signMap.put("username", userName);
        signMap.put("version", LetvUtils.getClientVersionName());
        String signValue = LetvTools.generSignedKey53(signMap);
        String baseUrl = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "phonePay"));
        params.add(new BasicNameValuePair(TradeInfo.SIGN, signValue));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public HashMap<String, String> requestMobilePay(int updataId, String price, String mobileNum, String userName, String userId, String pay_type_id, String svip) {
        HashMap<String, String> postParams = new HashMap();
        postParams.put("price", price);
        postParams.put("ext", mobileNum);
        postParams.put("username", userName);
        postParams.put("userid", userId);
        postParams.put("pay_type_id", pay_type_id);
        postParams.put("svip", svip);
        postParams.put("deviceid", Global.DEVICEID);
        return postParams;
    }

    public String requestPhoneNumCheck(int updataId, String phoneNum) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("phone", phoneNum);
        signMap.put("version", LetvUtils.getClientVersionName());
        return getDynamicUrl() + "?" + "mod" + SearchCriteria.EQ + "passport" + "&" + "ctl" + SearchCriteria.EQ + "index" + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "phonePayCheck" + "&" + "phone" + SearchCriteria.EQ + phoneNum + "&" + TradeInfo.SIGN + SearchCriteria.EQ + LetvTools.generSignedKey53(signMap) + "&" + "pcode" + SearchCriteria.EQ + LetvConfig.getPcode() + "&" + "deviceid" + SearchCriteria.EQ + Global.DEVICEID + "&" + "version" + SearchCriteria.EQ + LetvUtils.getClientVersionName();
    }

    public String queryPayResult(int updataId, String userId, String phoneNum) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put("userid", userId);
        signMap.put("phone", phoneNum);
        signMap.put("version", LetvUtils.getClientVersionName());
        return getDynamicUrl() + "?" + "mod" + SearchCriteria.EQ + "passport" + "&" + "ctl" + SearchCriteria.EQ + "index" + "&" + "userid" + SearchCriteria.EQ + userId + "&" + SocialConstants.PARAM_ACT + SearchCriteria.EQ + "phonePayResult" + "&" + "phone" + SearchCriteria.EQ + phoneNum + "&" + TradeInfo.SIGN + SearchCriteria.EQ + LetvTools.generSignedKey53(signMap) + "&" + "pcode" + SearchCriteria.EQ + LetvConfig.getPcode() + "&" + "version" + SearchCriteria.EQ + LetvUtils.getClientVersionName();
    }

    public String requestContinueDiscount(int updataId, String userId) {
        HashMap<String, String> signMap = new HashMap();
        signMap.put(UserInfoDb.USER_ID, userId);
        signMap.put("version", LetvUtils.getClientVersionName());
        String signValue = LetvTools.generSignedKey53(signMap);
        String baseUrl = getDynamicUrl();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", "passport"));
        params.add(new BasicNameValuePair("ctl", "index"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "renewDiscount"));
        params.add(new BasicNameValuePair(UserInfoDb.USER_ID, userId));
        params.add(new BasicNameValuePair(TradeInfo.SIGN, signValue));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getQueryUrl(params, baseUrl);
    }

    public String requestYingchaoQueryPrice(String liveid) {
        String channel = LetvUtils.getChannelFromLiveid(liveid);
        String category = LetvUtils.getCategoryFromLiveid(liveid);
        String season = LetvUtils.getSeasonFromLiveid(liveid);
        String turn = LetvUtils.getTurnFromLiveid(liveid);
        String game = LetvUtils.getGameFromLiveid(liveid);
        HashMap<String, String> signMap = new HashMap();
        signMap.put("channel", channel);
        signMap.put(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, category);
        signMap.put("season", season);
        signMap.put("isteam", "0");
        signMap.put("version", LetvUtils.getClientVersionName());
        signMap.put("pcode", LetvConfig.getPcode());
        String signValue = LetvTools.generSignedKey53(signMap);
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("ctl", "livepackage"));
        params.add(new BasicNameValuePair("mod", "pay"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "index"));
        params.add(new BasicNameValuePair("channel", channel));
        params.add(new BasicNameValuePair(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY, category));
        params.add(new BasicNameValuePair("season", season));
        params.add(new BasicNameValuePair("isteam", "0"));
        params.add(new BasicNameValuePair("turn", turn));
        params.add(new BasicNameValuePair("game", game));
        params.add(new BasicNameValuePair(MODIFYPWD_PARAMETERS.APISIGN_KEY, signValue));
        params.add(new BasicNameValuePair("pcode", LetvConfig.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        LogInfo.log("clf", "查询直播价格接口requestYingchaoQueryPrice..url=" + ParameterBuilder.getQueryUrl(params, getDynamicUrl()));
        return ParameterBuilder.getQueryUrl(params, getDynamicUrl());
    }
}
