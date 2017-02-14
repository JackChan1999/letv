package com.coolcloud.uac.android.common.ws;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.coolcloud.uac.android.common.http.GetAgent;
import com.coolcloud.uac.android.common.http.HTTPAgent;
import com.coolcloud.uac.android.common.http.Host.Passport;
import com.coolcloud.uac.android.common.http.ImageAgent;
import com.coolcloud.uac.android.common.http.PostAgent;
import com.coolcloud.uac.android.common.http.ProtocolBuilder;
import com.coolcloud.uac.android.common.util.EncryptUtils;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.download.db.Download.DownloadVideoTable;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import org.json.JSONException;

public class RequestBuilder extends ProtocolBuilder {
    private static final String PATH_PARENT = "uac/m/";
    private static final String TAG = "RequestBuilder";

    private RequestBuilder(Context context) {
        super(context);
    }

    public static RequestBuilder create(Context context) {
        return new RequestBuilder(context);
    }

    public HTTPAgent buildGetSMSChannels(String ccid, String imsi, String appid) {
        Builder ub = getBuilder("uac/m/get_smschannel");
        append(ub, "ccid", ccid);
        append(ub, "imsi", imsi);
        append(ub, "appid", appid);
        return new GetAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildLogin(String account, String password, String appId) {
        Builder ub = getBuilder("uac/m/login");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "account", account);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildRelogin(String uid, String password, String appId) {
        Builder ub = getBuilder("uac/m/relogin");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildLogin4app(String uid, String rtkt, String appId) {
        Builder ub = getBuilder("uac/m/login4app");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "rtkt", rtkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildLoginThird(String account, String password, String appId) {
        Builder ub = getBuilder("uac/m/login");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "account", account);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public PostAgent buildCheckPassword(String uid, String account, String password, String appId) {
        Builder ub = getBuilder("uac/m/check_pwd");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "account", account);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public PostAgent buildCheckTKT(String uid, String account, String tkt, String appId) {
        Builder ub = getBuilder("uac/m/check_tkt");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "account", account);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public PostAgent buildCheckTKT(String openid, String access_token, String appId) {
        Builder ub = getBuilder("uac/m/oauth2/authenticate");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "openid", openid);
        append(hb, "access_token", access_token);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public PostAgent buildLogout(String uid, String tkt, String password, String appId) {
        Builder ub = getBuilder("uac/m/logout");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildRegisterPhoneGetActivateCode(String phone, String appId) {
        Builder ub = getBuilder("uac/m/register/phone/get_activate_code");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildRegisterPhone(String phone, String code, String password, String nickname, String appId) {
        Builder ub = getBuilder("uac/m/register/phone/register");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "activate_code", code);
        append(hb, "phone", phone);
        append(hb, "pwd", password);
        append(hb, "nickname", nickname);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildCheckEmailPresent(String email, String appId) {
        Builder ub = getBuilder("uac/m/register/email/check_present");
        append(ub, NotificationCompat.CATEGORY_EMAIL, email);
        append(ub, "appid", appId);
        return new GetAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildRegisterEmail(String email, String password, String nickname, String appId) {
        Builder ub = getBuilder("uac/m/register/email/get_link");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, NotificationCompat.CATEGORY_EMAIL, email);
        append(hb, "pwd", password);
        append(hb, "nickname", nickname);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildQuickRegister(String appId) {
        Builder ub = getBuilder("uac/m/register/quick_register");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildCheckPresentOnActivate(String ccid, String imsi, String appId) {
        Builder ub = getBuilder("uac/m/activate/check_present");
        append(ub, "ccid", ccid);
        append(ub, "imsi", imsi);
        append(ub, "appid", appId);
        return new GetAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildPollingOnActivate(String simId, String appId) {
        Builder ub = getBuilder("uac/m/activate/polling");
        append(ub, "simid", simId);
        append(ub, "appid", appId);
        return new GetAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildForwardPhoneGetActivateCode(String phone, String appId) {
        Builder ub = getBuilder("uac/m/forward/phone/get_activate_code");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildForwardPhone(String phone, String activateCode, String uid, String password, String appId) {
        Builder ub = getBuilder("uac/m/forward/phone/forward");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "activate_code", activateCode);
        append(hb, "uid", uid);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildForwardThird(String thirdId, String account, String password, String appId) {
        Builder ub = getBuilder("uac/m/forward/thirld/forward");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "thirdid", thirdId);
        append(hb, "account", account);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildBindPhoneGetActivateCode(String phone, String appId) {
        Builder ub = getBuilder("uac/m/bind/phone/get_activate_code");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildBindPhone(String phone, String tkt, String activateCode, String uid, String password, String appId) {
        Builder ub = getBuilder("uac/m/bind/phone/bind");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "activate_code", activateCode);
        append(hb, "uid", uid);
        append(hb, "pwd", password);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildBindEmail(String email, String tkt, String uid, String password, String appId) {
        Builder ub = getBuilder("uac/m/bind/email/get_link");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, NotificationCompat.CATEGORY_EMAIL, email);
        append(hb, "uid", uid);
        append(hb, "pwd", password);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildFindpwdPhoneGetActivateCode(String phone, String appId) {
        Builder ub = getBuilder("uac/m/findpwd/phone/get_activate_code");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildFindpwdPhoneSetPwd(String phone, String activateCode, String password, String appId) {
        Builder ub = getBuilder("uac/m/findpwd/phone/set_pwd");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "activate_code", activateCode);
        append(hb, "pwd", password);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildFindpwdEmail(String email, String appId) {
        Builder ub = getBuilder("uac/m/findpwd/email/get_link");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, NotificationCompat.CATEGORY_EMAIL, email);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildSetPwd(String uid, String tkt, String oldPwd, String newPwd, String appId) {
        Builder ub = getBuilder("uac/m/change/pwd");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "old_pwd", oldPwd);
        append(hb, "new_pwd", newPwd);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildSetUserInfo(String uid, String tkt, String appId, Bundle userInfo) {
        Builder ub = getBuilder("uac/m/user/set_user_info");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        HTTPAgent.Builder userb = new HTTPAgent.Builder();
        append(userb, userInfo);
        append(ub, UserInfoDb.TABLE_NAME, userb.buildJSON());
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetBasicUserInfo(String uid, String tkt, String appId) {
        Builder ub = getBuilder("uac/m/user/get_basic_user_info");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetDetailUserInfo(String uid, String tkt, String appId) {
        Builder ub = getBuilder("uac/m/user/get_detail_user_info");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildUpdateHeadImage(String uid, String tkt, String key, String value, String appId) {
        Builder ub = getBuilder("uac/m/change/headimage");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, key, value);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildUpdateUserItem(String uid, String tkt, String key, String value, String appId) {
        Builder ub = getBuilder("uac/m/change/user_info");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, key, value);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetAppInfo(String appId) {
        Builder ub = getBuilder("uac/m/oauth2/get_app_info");
        append(ub, "redirect_uri", "http://auther.coolyun.com");
        append(ub, "appid", appId);
        return new GetAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildCheckAuthorized(String uid, String appId, String scope) {
        Builder ub = getBuilder("uac/m/oauth2/check_authorize");
        append(ub, "uid", uid);
        append(ub, "appid", appId);
        append(ub, "scope", scope);
        return new GetAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildGetAuthCode(String uid, String tkt, String appId, String scope) {
        Builder ub = getBuilder("uac/m/oauth2/authorize");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "response_type", "code");
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        append(hb, "redirect_uri", "http://auther.coolyun.com");
        append(hb, "scope", scope);
        append(hb, "display", "mobile");
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetTokenImplicit(String uid, String tkt, String appId, String scope) {
        Builder ub = getBuilder("uac/m/oauth2/authorize");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "response_type", UserInfoDb.TOKEN);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        append(hb, "redirect_uri", "http://auther.coolyun.com");
        append(hb, "scope", scope);
        append(hb, "display", "mobile");
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildRefreshToken(String appId, String refreshToken) {
        Builder ub = getBuilder("uac/m/oauth2/token");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "grant_type", Oauth2AccessToken.KEY_REFRESH_TOKEN);
        append(hb, "appid", appId);
        append(hb, Oauth2AccessToken.KEY_REFRESH_TOKEN, refreshToken);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetTotalScore(String uid, String tkt, String appId) {
        Builder ub = getBuilder("uac/m/score/get_total_score");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetDetailScoreInfo(String uid, String tkt, String appId) {
        Builder ub = getBuilder("uac/m/score/get_detail_score_info");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetExchangeRate(String uid, String tkt, String appId) {
        Builder ub = getBuilder("uac/m/score/get_exchange_rate");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetExchangeRecords(String uid, String tkt, String appId, String startDate, int pageOffset, int pageSize, int pageNum) {
        Builder ub = getBuilder("uac/m/score/get_exchange_records");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        append(hb, "start_date", startDate);
        append(hb, "page_offset", "" + pageOffset);
        append(hb, "page_size", "" + pageSize);
        append(hb, "page_number", "" + pageNum);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetConsumeRecords(String uid, String tkt, String appId, String startDate, int pageOffset, int pageSize, int pageNum) {
        Builder ub = getBuilder("uac/m/score/get_consume_records");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "appid", appId);
        append(hb, "start_date", startDate);
        append(hb, "page_offset", "" + pageOffset);
        append(hb, "page_size", "" + pageSize);
        append(hb, "page_number", "" + pageNum);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetFreeCall(String appid, String uid, String tkt, String type, String phone, String loginSource) {
        Builder ub = getBuilder("uac/m/freecall/get_freecall360_info");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "type", type);
        append(hb, "phone", phone);
        append(hb, "loginSource", loginSource);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetBindDevice(String appid, String uid, String tkt, String version) {
        Builder ub = getBuilder("uac/m/device/get_device_list");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "version", version);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildUnBindDevice(String appid, String uid, String tkt, String devlist, String version) {
        Builder ub = getBuilder("uac/m/device/unbind_device");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "devlist", devlist);
        append(hb, "version", version);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetLoginApp(String appid, String uid, String tkt) {
        Builder ub = getBuilder("uac/m/device/get_login_apps");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildChangeNickname(String appid, String uid, String tkt, String nickname) {
        Builder ub = getBuilder("uac/m/change/nickname");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "nickname", nickname);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildThidOauth(String appId, String tappname, String tappid, String ttoken) {
        Builder ub = getBuilder("uac/m/third", "method", "loginbytoken");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "tappid", tappid);
        append(hb, "tappname", tappname);
        append(hb, "appid", appId);
        append(hb, "ttoken", ttoken);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildThirdLoginAndCreateTemp(String appId, String tappname, String tappid, String tuid, String tnickname, String ttoken) {
        Builder ub = getBuilder("uac/m/third", "method", "loginAnonymous");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "tappid", tappid);
        append(hb, "appid", appId);
        append(hb, "tappname", tappname);
        append(hb, "tuid", tuid);
        append(hb, "tnickname", tnickname);
        append(hb, "ttoken", ttoken);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildThidLoginAndBind(String appId, String account, String pwd, String tappname, String tappid, String tuid, String tnickname, String ttoken) {
        Builder ub = getBuilder("uac/m/third", "method", "bind4login");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appId);
        append(hb, "account", account);
        append(hb, "pwd", pwd);
        append(hb, "tappname", tappname);
        append(hb, "tappid", tappid);
        append(hb, "tnickname", tnickname);
        append(hb, "tuid", tuid);
        append(hb, "ttoken", ttoken);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildThidBind(String appid, String uid, String tkt, String tappname, String tappid, String ttoken) {
        Builder ub = getBuilder("uac/m/third", "method", "bindthird");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "tappname", tappname);
        append(hb, "tappid", tappid);
        append(hb, "ttoken", ttoken);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetBindInfo(String appid, String uid, String tkt) {
        Builder ub = getBuilder("uac/m/third", "method", "getbindinfo");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", "");
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildQihooLogin(String appid, String tappname, String tappid, String tuseinfo, String isSkip) {
        Builder ub = getBuilder("uac/m/third", "method", "loginbyqihoo");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "tappname", tappname);
        append(hb, "tappid", tappid);
        append(hb, "tuserinfo", tuseinfo);
        append(hb, "isSkip", isSkip);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildQihooBind(String appid, String uid, String tkt, String tappname, String tappid, String tuseinfo) {
        Builder ub = getBuilder("uac/m/third", "method", "bindqihoo");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "tappname", tappname);
        append(hb, "tappid", tappid);
        append(hb, "tuserinfo", tuseinfo);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildloginAndBindQihoo(String appid, String account, String pwd, String tappname, String tappid, String tuserinfo) {
        Builder ub = getBuilder("uac/m/third", "method", "bindandlogin4qihoo");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "account", account);
        append(hb, "pwd", pwd);
        append(hb, "tuserinfo", tuserinfo);
        append(hb, "tappname", tappname);
        append(hb, "tappid", tappid);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildUnBindThird(String appid, String uid, String tkt, String tappname, String tappid) {
        Builder ub = getBuilder("uac/m/third", "method", "unbindthird");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "tappname", tappname);
        append(hb, "tappid", tappid);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildGetQihooToken(String appid, String uid, String tkt, String token, String tappname) {
        Builder ub = getBuilder("uac/m/third", "method", "getqihootoken");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "access_token", token);
        append(hb, "tappname", tappname);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildRefreshQihooToken(String appid, String uid, String tkt, String token, String tappname) {
        Builder ub = getBuilder("uac/m/third", "method", "refreshqihootoken");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "appid", appid);
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "access_token", token);
        append(hb, "tappname", tappname);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildDoPostHttp(String method, Bundle input) {
        Builder ub = getBuilder(PATH_PARENT + method);
        String reqb = "";
        try {
            reqb = KVUtils.bundle2JSON(input).toLowerCase();
        } catch (JSONException e) {
            LOG.e(TAG, "buildDoGetHttp [method : " + method + "][input" + input + "] JSONException ", e);
        } catch (Throwable e2) {
            LOG.e(TAG, "buildDoGetHttp [method : " + method + "][input" + input + "] Throwable ", e2);
        }
        return new PostAgent(ub.build(), Passport.getHosts(), reqb);
    }

    public HTTPAgent buildDoThirdPostHttp(String method, Bundle input) {
        Builder ub = getBuilder("uac/m/third", "method", method);
        String reqb = "";
        try {
            reqb = KVUtils.bundle2JSON(input);
        } catch (JSONException e) {
            LOG.e(TAG, "buildDoThirdGetHttp [method : " + method + "][input" + input + "] JSONException ", e);
        } catch (Throwable e2) {
            LOG.e(TAG, "buildDoThirdGetHttp [method : " + method + "][input" + input + "] Throwable ", e2);
        }
        return new PostAgent(ub.build(), Passport.getHosts(), reqb);
    }

    public HTTPAgent buildGetCaptchaImg(String appid, String captchakey, String width, String height, String length) {
        Builder ub = getBuilder("uac/m/authcode/getcaptcha");
        append(ub, "appid", appid);
        append(ub, "captchakey", captchakey);
        append(ub, SettingsJsonConstants.ICON_WIDTH_KEY, width);
        append(ub, SettingsJsonConstants.ICON_HEIGHT_KEY, height);
        append(ub, DownloadVideoTable.COLUMN_LENGTH, length);
        append(ub, "checkkey", EncryptUtils.getMD5String("YL31815!@#" + captchakey));
        return new ImageAgent(ub.build(), Passport.getHosts());
    }

    public HTTPAgent buildRegisterPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode) {
        Builder ub = getBuilder("uac/m/register/phone/get_activate_code_safe");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        append(hb, "captchakey", captchakey);
        append(hb, "captchacode", captchacode);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildupdateDeviceId(String uid, String tkt, String newdevid, String olddevid, String appid) {
        Builder ub = getBuilder("uac/m/update_devid");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "uid", uid);
        append(hb, "tkt", tkt);
        append(hb, "newdevid", newdevid);
        append(hb, "olddevid", olddevid);
        append(hb, "appid", appid);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildForwardPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode) {
        Builder ub = getBuilder("uac/m/forward/phone/get_activate_code_safe");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        append(hb, "captchakey", captchakey);
        append(hb, "captchacode", captchacode);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildFindpwdPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode) {
        Builder ub = getBuilder("uac/m/findpwd/phone/get_activate_code_safe");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        append(hb, "captchakey", captchakey);
        append(hb, "captchacode", captchacode);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }

    public HTTPAgent buildBindPhoneGetActivateCodeSafe(String phone, String appId, String captchakey, String captchacode) {
        Builder ub = getBuilder("uac/m/bind/phone/get_activate_code_safe");
        HTTPAgent.Builder hb = new HTTPAgent.Builder();
        append(hb, "phone", phone);
        append(hb, "appid", appId);
        append(hb, "captchakey", captchakey);
        append(hb, "captchacode", captchacode);
        return new PostAgent(ub.build(), Passport.getHosts(), hb.buildJSON());
    }
}
