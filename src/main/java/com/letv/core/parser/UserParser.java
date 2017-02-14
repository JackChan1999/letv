package com.letv.core.parser;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.letv.core.bean.UserBean;
import com.letv.core.bean.UserBean.VipInfo;
import com.letv.core.constant.LetvConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.tencent.open.SocialConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserParser extends LetvMasterParser<UserBean> {
    private final String ADDRESS = "address";
    protected final String BEAN = LetvMasterParser.BEAN;
    private final String BIRTHDAY = "birthday";
    private final String CHKVIPDAY = "chkvipday";
    private final String CITY = "city";
    private final String CONTACTEMAIL = "contactEmail";
    private final String DELIVERY = "delivery";
    private final String EDUCATION = "education";
    private final String EMAIL = NotificationCompat.CATEGORY_EMAIL;
    protected final String ERRORCODE = "errorCode";
    private final String GENDER = "gender";
    private final String INCOME = "income";
    private final String INDUSTRY = "industry";
    private final String ISIDENTIFY = "isIdentify";
    private final String ISVIP = "isvip";
    private final String JOB = "job";
    private final String LASTLOGINIP = "lastLoginIp";
    private final String LASTLOGINTIME = "lastLoginTime";
    private final String LASTMODIFYTIME = "lastModifyTime";
    private final String LEVEL_ID = "level_id";
    private final String MAC = "mac";
    protected final String MESSAGE = "message";
    private final String MOBILE = "mobile";
    private final String MSN = "msn";
    private final String NAME = "name";
    private final String NICKNAME = "nickname";
    private final String PICTURE = SocialConstants.PARAM_AVATAR_URI;
    private final String POINT = "point";
    private final String POSTCODE = "postCode";
    private final String PROVINCE = "province";
    private final String QQ = "qq";
    private final String REGISTIP = "registIp";
    private final String REGISTSERVICE = "registService";
    private final String REGISTTIME = "registTime";
    private final String SCORE = "score";
    private final String SSOUID = "ssouid";
    private final String STATUS = "status";
    private final String TV_TOKEN = "tv_token";
    private final String UID = "uid";
    private final String USERNAME = "username";
    private final String VIPDAY = "vipday";
    private final String VIPINFO = "vipinfo";
    public int activityStatus = -1;
    public String activityUrl = "";
    private String tv_token;

    public UserBean parse(JSONObject data) throws JSONException {
        UserBean letvUser = new UserBean();
        letvUser.uid = getString(data, "uid");
        letvUser.username = getString(data, "username");
        letvUser.status = getString(data, "status");
        letvUser.gender = getString(data, "gender");
        letvUser.qq = getString(data, "qq");
        letvUser.registIp = getString(data, "registIp");
        letvUser.registTime = getString(data, "registTime");
        letvUser.lastModifyTime = getString(data, "lastModifyTime");
        letvUser.birthday = getString(data, "birthday");
        letvUser.nickname = getString(data, "nickname");
        letvUser.msn = getString(data, "msn");
        letvUser.registService = getString(data, "registService");
        letvUser.email = getString(data, NotificationCompat.CATEGORY_EMAIL);
        letvUser.mobile = getString(data, "mobile");
        letvUser.province = getString(data, "province");
        letvUser.city = getString(data, "city");
        letvUser.postCode = getString(data, "postCode");
        letvUser.address = getString(data, "address");
        letvUser.mac = getString(data, "mac");
        letvUser.name = getString(data, "name");
        letvUser.contactEmail = getString(data, "contactEmail");
        letvUser.delivery = getString(data, "delivery");
        letvUser.point = getString(data, "point");
        letvUser.score = getString(data, "score");
        letvUser.level_id = getString(data, "level_id");
        letvUser.isvip = getString(data, "isvip");
        letvUser.chkvipday = getString(data, "chkvipday");
        letvUser.isIdentify = getString(data, "isIdentify");
        if (has(data, "ssouid")) {
            letvUser.ssouid = getString(data, "ssouid");
        }
        if (has(data, "vipday")) {
            letvUser.vipday = getString(data, "vipday");
        }
        if (TextUtils.isEmpty(letvUser.chkvipday)) {
            PreferencesManager.getInstance().setChkvipday(864000);
        } else {
            PreferencesManager.getInstance().setChkvipday(Long.parseLong(letvUser.chkvipday) * LetvConstant.VIP_OVERDUE_TIME);
        }
        letvUser.education = getString(data, "education");
        letvUser.industry = getString(data, "industry");
        letvUser.job = getString(data, "job");
        letvUser.income = getString(data, "income");
        letvUser.lastLoginTime = getString(data, "lastLoginTime");
        letvUser.lastLoginIp = getString(data, "lastLoginIp");
        if (!TextUtils.isEmpty(this.tv_token)) {
            letvUser.tv_token = this.tv_token;
        }
        if (has(data, "vipinfo")) {
            JSONArray array = getJSONArray(data, "vipinfo");
            if (array == null || array.length() <= 0) {
                PreferencesManager.getInstance().setLastdays(864086400);
            } else {
                JSONObject object = getJSONObject(array, 0);
                VipInfo info = new VipInfo();
                info.id = getString(object, "id");
                info.username = getString(object, "username");
                info.canceltime = getLong(object, "canceltime");
                info.orderFrom = getInt(object, "orderFrom");
                info.productid = getInt(object, "productid");
                info.uinfo = getString(object, "uinfo");
                info.vipType = getInt(object, "vipType");
                info.seniorcanceltime = getLong(object, "seniorcanceltime");
                String valueString = getString(object, "lastdays");
                if (TextUtils.isEmpty(valueString)) {
                    info.lastdays = 864086400;
                } else {
                    info.lastdays = Long.parseLong(valueString);
                }
                letvUser.vipInfo = info;
                PreferencesManager.getInstance().setLastdays(info.lastdays);
                PreferencesManager.getInstance().setUInfo(info.uinfo);
                saveCacheCancelTime(info.canceltime, info.seniorcanceltime);
            }
        }
        if ("1".equals(letvUser.isvip)) {
            PreferencesManager.getInstance().setVip(true);
            if (letvUser.vipInfo != null) {
                PreferencesManager.getInstance().setVipCancelTime(letvUser.vipInfo.canceltime);
                PreferencesManager.getInstance().setSeniorVipCancelTime(letvUser.vipInfo.seniorcanceltime);
                PreferencesManager.getInstance().setVipLevel(letvUser.vipInfo.vipType);
            }
        } else {
            PreferencesManager.getInstance().setVip(false);
            PreferencesManager.getInstance().setVipCancelTime(0);
            PreferencesManager.getInstance().setSeniorVipCancelTime(0);
            PreferencesManager.getInstance().setVipLevel(0);
        }
        String pictures = getString(data, SocialConstants.PARAM_AVATAR_URI);
        if (!TextUtils.isEmpty(pictures)) {
            String[] splits = pictures.split(",");
            letvUser.picture = splits[1];
            PreferencesManager.getInstance().setUserIcon(splits[1]);
        }
        return letvUser;
    }

    private void saveCacheCancelTime(long cancelTime, long seniorCancelTime) {
        if (cancelTime > 0 || seniorCancelTime > 0) {
            long cacheCancelTime;
            if (cancelTime < seniorCancelTime) {
                cacheCancelTime = seniorCancelTime;
            } else {
                cacheCancelTime = cancelTime;
            }
            PreferencesManager.getInstance().setCacheCancelTime(cacheCancelTime);
        }
    }

    protected boolean canParse(String data) {
        LogInfo.log("RequestUserByTokenTask data == " + data);
        try {
            JSONObject object = new JSONObject(data);
            if (!object.has("status")) {
                return false;
            }
            int status = object.getInt("status");
            int errorCode = object.optInt("errorCode");
            setMessage(object.optString("message"));
            if (status == 1 && errorCode == 0) {
                if (has(object, "tv_token")) {
                    this.tv_token = getString(object, "tv_token");
                }
                return true;
            }
            setErrCode(errorCode);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected JSONObject getData(String data) throws Exception {
        LogInfo.log("RequestUserByTokenTask  getData data == " + data);
        JSONObject object = new JSONObject(data);
        JSONObject activity = getJSONObject(object, "activity");
        if (activity != null) {
            this.activityStatus = getInt(activity, "status");
            this.activityUrl = getString(activity, "url");
            PreferencesManager.getInstance().setReceiveVipActivityStatus(this.activityStatus);
            PreferencesManager.getInstance().setReceiveVipActivityUrl(this.activityUrl);
            LogInfo.log("LoginBack activityStatus== " + this.activityStatus + "  activityUrl ==" + this.activityUrl);
        } else {
            LogInfo.log("LoginBack activity is null Status== " + this.activityStatus + "  activityUrl ==" + this.activityUrl);
        }
        return getJSONObject(object, LetvMasterParser.BEAN);
    }
}
