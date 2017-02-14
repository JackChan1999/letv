package com.letv.mobile.letvhttplib.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.letv.core.constant.DownloadConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.mobile.letvhttplib.HttpLibApp;
import com.letv.mobile.letvhttplib.config.LetvConfig;
import com.letv.mobile.letvhttplib.utils.LetvUtils;
import com.letv.mobile.letvhttplib.utils.Logger;
import com.letv.mobile.letvhttplib.utils.SharedPreferenceUtils;
import com.letv.mobile.letvhttplib.utils.StringUtils;
import com.letv.mobile.letvhttplib.utils.UIsUtils;
import com.tencent.open.SocialConstants;
import java.io.File;
import org.cybergarage.upnp.std.av.renderer.RenderingControl;
import org.json.JSONException;
import org.json.JSONObject;

public class PreferencesManager {
    private static final String ACCUMULATED_CLEAN_SIZE = "accumulated_clean_size";
    private static final String AD_COOKIES = "ad_cookies";
    private static final String ALIPAY_AUTO_MOBILE_ALL_SCREEN_OPEN_STATUS = "mobile_allscreen_open_status";
    private static final String ALIPAY_AUTO_PAY_CORDERID = "alipay_auto_pay_corderid";
    private static final String ALIPAY_AUTO_PAY_FLAG = "alipay_auto_pay_flag";
    private static final String ALIPAY_AUTO_PAY_INFO = "alipay_auto_pay_info";
    private static final String ALIPAY_AUTO_PAY_PARTNERID = "alipay_auto_pay_partnerid";
    private static final String ALIPAY_AUTO_PRODUCT_EXPIRE = "product_expire";
    private static final String ALIPAY_AUTO_PRODUCT_NAME = "product_name";
    private static final String ALIPAY_AUTO_PRODUCT_PAYTYPE = "product_paytype";
    private static final String ALIPAY_AUTO_PRODUCT_PRICE = "product_price";
    private static final String API = "API";
    private static final String AREA_FIND_KEY = "area_find_key";
    private static final String ATTENDANCE = "attendance";
    private static final String BARRAGE_SWITCH = "barrage_switch";
    private static final String BD_LOCATION = "bd_location";
    private static final String BR_CONTROL = "br_Control";
    private static final String CACHE_TITLE = "cache_title";
    private static final String CHANNEL_PAGE = "channel_page";
    private static final String COMMENT_LIKE_COUNT = "comment_like_count";
    private static final String COMMENT_LIKE_DIALOG = "comment_like_dialog";
    private static final int COMMENT_LOGOUT_LIKE_COUNT = 50;
    private static final String CONTINUE_DISCOUNT = "";
    private static final String CRASH_COUNT = "crash_count";
    private static final String DIALOG_3G = "dialog_3g";
    private static final String DIALOG_MSG = "dialog_msg";
    private static final String DOWNLOAD_FILE_STREAM_LEVEL = "download_file_stream_level";
    private static final String DOWNLOAD_LOCATION = "download_location";
    private static final String DOWNLOAD_LOCATION_DIR = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append("Letv/storage/download").toString();
    private static final String DOWNLOAD_LOCATION_ISMEMORY_KEY = "download_location_ismem";
    private static final String DOWNLOAD_LOCATION_KEY = "download_location";
    private static final String FIRST_VOTE_KEY = "first_vote_key";
    private static final String FLOATBALL_ACTIVE_FLAG = "floatball_active_recommend_flag";
    private static final String FORCE_ALERT = "force_alert";
    private static final String GET_FLAOT_DATA_TIME = "FlaotDataTime";
    private static final String HAS_SHOWN_VIDEOSHOT_BREATH = "videoshot_breath";
    private static final String HAS_SHOWN_VIDEOSHOT_GUIDE = "videoshot_guided";
    private static final String HAVELOGINPAGEKEY = "haveLoginKey";
    private static final String HOME_PAGE = "home_page";
    private static final String HOME_RECOMMEND = "";
    private static final String INVITE_FLAG = "invite_flag";
    private static final String INVITE_VISIBLE_FLAG = "false";
    private static final String LAST_EXCHANGE_POP_TIME = "LastExchangePopTime";
    private static final String LAST_HOT_TIME = "last_hot_time";
    private static final String LAST_REFRESH_TIME = "last_refresh_time";
    private static final String LESO_NOTIFICATION = "leso_notification";
    private static final String NOTIFY_ID_LOCAL_FORCE = "notify_id_local_force";
    private static final String PATH = "Letv/storage/";
    private static final String PATH_DOWNLOAD = "Letv/storage/download";
    private static final String PERSONAL_CENTER_LOGIN_NAME = "personal_login";
    private static final String PERSONAL_CENTER_SP_NAME = "personal_center";
    private static final String PHONE_PAY = "phone_pay";
    private static final String PIP_FROM = "pipFrom";
    private static final String PLAYER_PARAMER = "player_parameter";
    private static final String POINTS = "points";
    private static final String PRAISE_USER = "praise_user";
    private static final String PREF_ALERT_DAYS = "pref_alert_days";
    private static final String PREF_CURRENTTIMEMILLIS = "currentTimeMillis";
    private static final String PREF_LAUNCH_DATE = "pref_launch_date";
    private static final String PREF_LAUNCH_MINUTE = "pref_launch_minute";
    private static final String PUSH = "push";
    public static final String PUSH_MSG = "push_msg";
    private static final String QZONE = "qzone";
    private static final String RECOMMEND = "recommend";
    private static final String SETTINGS = "settings";
    private static final String SHARE = "share";
    private static final String SOFT_KETBOARD_HEIGHT = "soft_ketboard_height";
    private static final String TAG = PreferencesManager.class.getSimpleName();
    private static final String USER_PHONE_NUMBER_BIND_STATE = "user_phone_number_bind_state";
    private static final String USER_WO_ORDER = "user_wo_order";
    private static final String VERSIONCODE = "versioncode";
    private static final String WORLD_CUP_FUNCTION = "world_cup";
    private static final String WO_FLOW_ALERT = "wo_flow_alert";
    private static final String WO_FLOW_ALERT_TIME = "wo_flow_alert_time";
    private static final String _HOME_RECORD = "home_record";
    private static final String _POINTS = "_points";
    private static Context context;
    private static PreferencesManager instance;

    private PreferencesManager(Context context) {
        context = context;
    }

    public static PreferencesManager getInstance() {
        if (context == null) {
            instance = new PreferencesManager(HttpLibApp.getInstance());
        }
        return instance;
    }

    public boolean getBarrageSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, BARRAGE_SWITCH, Boolean.valueOf(true))).booleanValue();
    }

    public void setBarrageSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, SETTINGS, BARRAGE_SWITCH, Boolean.valueOf(isOpen));
    }

    public int getMaxLoading() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, "maxLoading", Integer.valueOf(3))).intValue();
    }

    public void setMaxLoading(int maxLoading) {
        SharedPreferenceUtils.put(context, SETTINGS, "maxLoading", Integer.valueOf(maxLoading));
    }

    public void saveFindTimeStamp(JSONObject times) {
        SharedPreferenceUtils.putSyn(context, AREA_FIND_KEY, times.toString());
    }

    public JSONObject getFindTimeStamp() {
        try {
            return new JSONObject((String) SharedPreferenceUtils.get(context, AREA_FIND_KEY, ""));
        } catch (JSONException e) {
            Logger.d("songhang", "getFindTimeStamp json prase error");
            e.printStackTrace();
            return null;
        }
    }

    public void setSkip(boolean isSkip) {
        SharedPreferenceUtils.put(context, SETTINGS, "isSkip", Boolean.valueOf(isSkip));
    }

    public boolean getUserPhoneNumberBindState() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, USER_PHONE_NUMBER_BIND_STATE, Boolean.valueOf(false))).booleanValue();
    }

    public void setUserPhoneNumberBindState(boolean hasBinded) {
        SharedPreferenceUtils.put(context, SETTINGS, USER_PHONE_NUMBER_BIND_STATE, Boolean.valueOf(hasBinded));
    }

    public boolean isSkip() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isSkip", Boolean.valueOf(true))).booleanValue();
    }

    public void setAllowMobileNetwork(boolean isAllow) {
        SharedPreferenceUtils.put(context, SETTINGS, "isAllow", Boolean.valueOf(isAllow));
    }

    public boolean isAllowMobileNetwork() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isAllow", Boolean.valueOf(false))).booleanValue();
    }

    public void setListModel(boolean isList) {
        SharedPreferenceUtils.put(context, SETTINGS, "isList", Boolean.valueOf(isList));
    }

    public boolean getListModel() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isList", Boolean.valueOf(true))).booleanValue();
    }

    public void setUpdataRate(int rate) {
        SharedPreferenceUtils.put(context, SETTINGS, "updataRate", Integer.valueOf(rate));
    }

    public int getUpdataRate() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, "updataRate", Integer.valueOf(-1))).intValue();
    }

    public void setAutoShare(boolean isAuto) {
        SharedPreferenceUtils.put(context, SETTINGS, "isAuto", Boolean.valueOf(isAuto));
    }

    public boolean getAutoShare() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isAuto", Boolean.valueOf(false))).booleanValue();
    }

    public void setShareWords(String shareWords) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "sharewords", shareWords);
    }

    public String getShareWords() {
        return (String) SharedPreferenceUtils.get(context, HOME_PAGE, "sharewords", "1");
    }

    public void notShowNewFeaturesDialog() {
        SharedPreferenceUtils.put(context, SETTINGS, "isShowNewFeatures", Integer.valueOf(LetvUtils.getClientVersionCode()));
    }

    public boolean isShowNewFeaturesDialog() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, "isShowNewFeatures", Integer.valueOf(-1))).intValue() < LetvUtils.getClientVersionCode();
    }

    public int getSettingVersionCode() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, "isShowNewFeatures", Integer.valueOf(-1))).intValue();
    }

    public void setShortcut(boolean shortcut) {
        SharedPreferenceUtils.put(context, SETTINGS, "shortcut", Boolean.valueOf(shortcut));
    }

    public boolean getShortcut() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "shortcut", Boolean.valueOf(true))).booleanValue();
    }

    public void setSearchShortcut(boolean shortcut) {
        SharedPreferenceUtils.put(context, SETTINGS, "searchShortcut", Boolean.valueOf(shortcut));
    }

    public boolean getSearchShortcut() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "searchShortcut", Boolean.valueOf(true))).booleanValue();
    }

    public int getSoftKeyboardHeight() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, SOFT_KETBOARD_HEIGHT, Integer.valueOf(UIsUtils.dipToPx(240)))).intValue();
    }

    public void saveSoftKeyboardHeight(int newHeight) {
        SharedPreferenceUtils.put(context, SETTINGS, SOFT_KETBOARD_HEIGHT, Integer.valueOf(newHeight));
    }

    public void setShortCutIcon(boolean isHaveShortIcon) {
        SharedPreferenceUtils.put(context, SETTINGS, "appShortcut", Boolean.valueOf(isHaveShortIcon));
    }

    public boolean getShortCutIcon() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "appShortcut", Boolean.valueOf(false))).booleanValue();
    }

    public void setFirstEnter(boolean isFirstEnter) {
        SharedPreferenceUtils.put(context, SETTINGS, "firstEnter", Boolean.valueOf(isFirstEnter));
    }

    public boolean isFirstEnter() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "firstEnter", Boolean.valueOf(true))).booleanValue();
    }

    public void setFirstPlay(boolean isFirstEnter) {
        SharedPreferenceUtils.put(context, SETTINGS, "firstPlay", Boolean.valueOf(isFirstEnter));
    }

    public boolean isFirstPlay() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "firstPlay", Boolean.valueOf(true))).booleanValue();
    }

    public void setIsNeedUpdate(boolean isNeedUpdate) {
        SharedPreferenceUtils.put(context, SETTINGS, "isNeedUpdate", Boolean.valueOf(isNeedUpdate));
    }

    public boolean isNeedUpdate() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isNeedUpdate", Boolean.valueOf(false))).booleanValue();
    }

    public void setPlayLevel(int isPlayHd) {
        Logger.d("zhuqiao", "setPlayLevel" + isPlayHd);
        SharedPreferenceUtils.put(context, SETTINGS, "PlayLevel", Integer.valueOf(isPlayHd));
    }

    public void setIsDownloadHd(boolean isPlayHd) {
        SharedPreferenceUtils.put(context, SETTINGS, "isDownloadHd", Boolean.valueOf(isPlayHd));
    }

    public boolean isDownloadHd() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isDownloadHd", Boolean.valueOf(false))).booleanValue();
    }

    public boolean isRecover() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isRecoverNew", Boolean.valueOf(false))).booleanValue();
    }

    public boolean isOperate() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "isOperate", Boolean.valueOf(false))).booleanValue();
    }

    public void setIsOperate(boolean isOperate) {
        SharedPreferenceUtils.put(context, SETTINGS, "isOperate", Boolean.valueOf(isOperate));
    }

    public void setRecover() {
        SharedPreferenceUtils.put(context, SETTINGS, "isRecoverNew", Boolean.valueOf(true));
    }

    public boolean isShack() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "shack", Boolean.valueOf(true))).booleanValue();
    }

    public void setIsShack(boolean isShack) {
        Logger.d(TAG, "setShack == " + isShack);
        SharedPreferenceUtils.put(context, SETTINGS, "shack", Boolean.valueOf(isShack));
    }

    public boolean isPlayCloud() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "playcloud", Boolean.valueOf(false))).booleanValue();
    }

    public void setisPlayCloud(boolean isShack) {
        SharedPreferenceUtils.put(context, SETTINGS, "playcloud", Boolean.valueOf(isShack));
    }

    public String getTVSpreadData() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, "TVSpreadData", null);
    }

    public void setTVSpreadData(String TVSpreadData) {
        SharedPreferenceUtils.put(context, SETTINGS, "TVSpreadData", TVSpreadData);
    }

    public String getTVSpreadMark() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, "TVSpreadMark", null);
    }

    public void setTVSpreadMark(String TVSpreadMark) {
        SharedPreferenceUtils.put(context, SETTINGS, "TVSpreadMark", TVSpreadMark);
    }

    public void saveDialogMsgMarkid(String markid) {
        SharedPreferenceUtils.put(context, SETTINGS, "dialogMsgMarkid", markid);
    }

    public String getDialogMsgMarkid() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, "dialogMsgMarkid", null);
    }

    public void setPlayBrightness(float brightness) {
        SharedPreferenceUtils.put(context, SETTINGS, RenderingControl.BRIGHTNESS, Float.valueOf(brightness));
    }

    public float getPlayBrightness() {
        return ((Float) SharedPreferenceUtils.get(context, SETTINGS, RenderingControl.BRIGHTNESS, Float.valueOf(0.5f))).floatValue();
    }

    public void setVipTrailTime(int trailTime) {
        SharedPreferenceUtils.put(context, SETTINGS, "trailTime", Integer.valueOf(trailTime));
    }

    public int getVipTrailTime() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, "trailTime", Integer.valueOf(6))).intValue();
    }

    public void setFirstEnterWorldCup(boolean isFirst) {
        SharedPreferenceUtils.put(context, SETTINGS, "firstEnterWorld", Boolean.valueOf(isFirst));
    }

    public boolean isFirstEnterWorldCup() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "firstEnterWorld", Boolean.valueOf(true))).booleanValue();
    }

    public String getClickAppRechangeTime() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, "clickAppRechangeTime", "");
    }

    public void setClickAppRechangeTime(String time) {
        SharedPreferenceUtils.put(context, SETTINGS, "clickAppRechangeTime", time);
    }

    public boolean getCommentLikeDialog() {
        return context.getSharedPreferences(SETTINGS, 0).getBoolean(COMMENT_LIKE_DIALOG, true);
    }

    public boolean getCommentLikeDialogShow() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, COMMENT_LIKE_DIALOG, Boolean.valueOf(true))).booleanValue();
    }

    public void setCommentLikeDialog() {
        boolean z;
        boolean isTip = ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, COMMENT_LIKE_DIALOG, Boolean.valueOf(true))).booleanValue();
        Context context = context;
        String str = SETTINGS;
        String str2 = COMMENT_LIKE_DIALOG;
        if (isTip) {
            z = false;
        } else {
            z = true;
        }
        SharedPreferenceUtils.put(context, str, str2, Boolean.valueOf(z));
    }

    public boolean isLogoutCommentLikeDialogVisible() {
        return ((Integer) SharedPreferenceUtils.get(context, SETTINGS, COMMENT_LIKE_COUNT, Integer.valueOf(0))).intValue() < 50 ? false : getCommentLikeDialog();
    }

    public void setLogoutCommentLikeCount() {
        SharedPreferenceUtils.put(context, SETTINGS, COMMENT_LIKE_COUNT, Integer.valueOf((((Integer) SharedPreferenceUtils.get(context, SETTINGS, COMMENT_LIKE_COUNT, Integer.valueOf(0))).intValue() % 50) + 1));
    }

    public void clearLogoutCommentLikeCount() {
        SharedPreferenceUtils.put(context, SETTINGS, COMMENT_LIKE_COUNT, Integer.valueOf(0));
    }

    public int getPushDistance() {
        return context.getSharedPreferences(PUSH, 4).getInt("distance", 600);
    }

    public void savePushDistance(int time) {
        context.getSharedPreferences(PUSH, 4).edit().putInt("distance", time).commit();
    }

    public long getPushTime() {
        return context.getSharedPreferences(PUSH, 4).getLong("time", 0);
    }

    public void savePushTime(long time) {
        context.getSharedPreferences(PUSH, 4).edit().putLong("time", time).commit();
    }

    public long getPushId() {
        return context.getSharedPreferences(PUSH, 4).getLong("id", 0);
    }

    public void savePushId(long id) {
        context.getSharedPreferences(PUSH, 4).edit().putLong("id", id).commit();
    }

    public String getMsgId() {
        return context.getSharedPreferences(PUSH, 4).getString("msgId", "");
    }

    public void saveMsgId(String id) {
        context.getSharedPreferences(PUSH, 4).edit().putString("msgId", id).commit();
    }

    public String getPushLive() {
        return context.getSharedPreferences(PUSH, 4).getString("live", "");
    }

    public void savePushLive(String key) {
        context.getSharedPreferences(PUSH, 4).edit().putString("live", key).commit();
    }

    public void setUserName(String username) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "username", username);
    }

    public void setNickName(String nickname) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "nickname", nickname);
    }

    public String getUserMobile() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "mobile", "");
    }

    public void setUserMobile(String mobile) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "mobile", mobile);
    }

    public String getUserName() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "username", "");
    }

    public String getNickName() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "nickname", "");
    }

    public void setLoginName(String username) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_LOGIN_NAME, "loginname", username);
    }

    public String getLoginName() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_LOGIN_NAME, "loginname", "");
    }

    public void setLoginPassword(String password) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "loginpassword", password);
    }

    public String getLoginPassword() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "loginpassword", "");
    }

    public void setUserId(String userId) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, UserInfoDb.USER_ID, userId);
    }

    public String getUserIcon() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "userIcon", "");
    }

    public void setUserIcon(String userId) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "userIcon", userId);
    }

    public String getUserId() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, UserInfoDb.USER_ID, "");
    }

    public void setSso_tk(String sso_tk) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "sso_tk", sso_tk);
    }

    public String getSso_tk() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "sso_tk", "");
    }

    public void setShareUserId(String userId) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, UserInfoDb.SHARE_USER_ID, userId);
    }

    public String getShareUserId() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, UserInfoDb.SHARE_USER_ID, "");
    }

    public void setShareToken(String sso_tk) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "share_tk", sso_tk);
    }

    public String getShareToken() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "share_tk", "");
    }

    public void setRemember_pwd(boolean isRemember_pwd) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "isRemember_pwd", Boolean.valueOf(isRemember_pwd));
    }

    public boolean isRemember_pwd() {
        return ((Boolean) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "isRemember_pwd", Boolean.valueOf(false))).booleanValue();
    }

    public boolean isLogin() {
        return (TextUtils.isEmpty(getUserName()) || TextUtils.isEmpty(getUserId())) ? false : true;
    }

    public void logoutUser() {
        SharedPreferenceUtils.clear(context, PERSONAL_CENTER_SP_NAME);
    }

    public void setColletionPop(boolean pop) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "show_collection_pop", Boolean.valueOf(pop));
    }

    public boolean getColletionPop() {
        return ((Boolean) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "show_collection_pop", Boolean.valueOf(true))).booleanValue();
    }

    public void setFirstInTopMyFragment(boolean first) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "first_in_topmyfragment", Boolean.valueOf(first));
    }

    public boolean getFirstInTopMyFragment() {
        return ((Boolean) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "first_in_topmyfragment", Boolean.valueOf(true))).booleanValue();
    }

    public void setVip(boolean isVip) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "vip", Boolean.valueOf(isVip));
    }

    public boolean isVip() {
        return ((Boolean) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "vip", Boolean.valueOf(false))).booleanValue();
    }

    public void setVipCancelTime(long cancelTime) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "cancelTime", Long.valueOf(cancelTime));
    }

    public long getVipCancelTime() {
        return ((Long) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "cancelTime", Long.valueOf(0))).longValue();
    }

    public void setSeniorVipCancelTime(long cancelTime) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "senior_cancelTime", Long.valueOf(cancelTime));
    }

    public long getSeniorVipCancelTime() {
        return ((Long) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "senior_cancelTime", Long.valueOf(0))).longValue();
    }

    public int getVipLevel() {
        return ((Integer) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "vipLevel", Integer.valueOf(0))).intValue();
    }

    public void setVipLevel(int vipLevel) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "vipLevel", Integer.valueOf(vipLevel));
    }

    public void setChkvipday(long chkvipday) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "chkvipday", Long.valueOf(chkvipday));
    }

    public long getChkvipday() {
        return ((Long) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "chkvipday", Long.valueOf(864000))).longValue();
    }

    public void setLastdays(long lastdays) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "lastdays", Long.valueOf(lastdays));
    }

    public long getLastdays() {
        return ((Long) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "lastdays", Long.valueOf(864086400))).longValue();
    }

    public void setUInfo(String uinfo) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "uinfo", uinfo);
    }

    public String getUInfo() {
        return (String) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "uinfo", "");
    }

    public void setCacheCancelTime(long cacheCancelTime) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "cacheCancelTime", Long.valueOf(cacheCancelTime));
    }

    public long getCacheCancelTime() {
        return ((Long) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "cacheCancelTime", Long.valueOf(0))).longValue();
    }

    public void setCurrdays(long currdays) {
        SharedPreferenceUtils.put(context, PERSONAL_CENTER_SP_NAME, "currdays", Long.valueOf(currdays));
    }

    public long getCurrdays() {
        return ((Long) SharedPreferenceUtils.get(context, PERSONAL_CENTER_SP_NAME, "currdays", Long.valueOf(1001))).longValue();
    }

    public void setUserOrderWo(boolean isOrder) {
        SharedPreferenceUtils.put(context, USER_WO_ORDER, "user_wo", Boolean.valueOf(isOrder));
    }

    public boolean getUserOrderWo() {
        return ((Boolean) SharedPreferenceUtils.get(context, USER_WO_ORDER, "user_wo", Boolean.valueOf(false))).booleanValue();
    }

    public void saveChannelsData(String data) {
        SharedPreferenceUtils.put(context, CHANNEL_PAGE, "channels", data);
    }

    public void saveSpecialsData(String data) {
        SharedPreferenceUtils.put(context, CHANNEL_PAGE, "specials", data);
    }

    public String getChannelsData() {
        return (String) SharedPreferenceUtils.get(context, CHANNEL_PAGE, "channels", null);
    }

    public String getSpecialsData() {
        return (String) SharedPreferenceUtils.get(context, CHANNEL_PAGE, "specials", null);
    }

    public boolean sinaIsShare() {
        return ((Boolean) SharedPreferenceUtils.get(context, SHARE, "sinaIsShare", Boolean.valueOf(true))).booleanValue();
    }

    public boolean tencentIsShare() {
        return ((Boolean) SharedPreferenceUtils.get(context, SHARE, "tencentIsShare", Boolean.valueOf(true))).booleanValue();
    }

    public boolean qzoneIsShare() {
        return ((Boolean) SharedPreferenceUtils.get(context, SHARE, "qzoneIsShare", Boolean.valueOf(true))).booleanValue();
    }

    public boolean lestarIsShare() {
        return ((Boolean) SharedPreferenceUtils.get(context, SHARE, "lestarIsShare", Boolean.valueOf(true))).booleanValue();
    }

    public void setSinaIsShare(boolean isShare) {
        SharedPreferenceUtils.put(context, SHARE, "sinaIsShare", Boolean.valueOf(isShare));
    }

    public void setTencentIsShare(boolean isShare) {
        SharedPreferenceUtils.put(context, SHARE, "tencentIsShare", Boolean.valueOf(isShare));
    }

    public void setQzoneIsShare(boolean isShare) {
        SharedPreferenceUtils.put(context, SHARE, "qzoneIsShare", Boolean.valueOf(isShare));
    }

    public void setLestarIsShare(boolean isShare) {
        SharedPreferenceUtils.put(context, SHARE, "lestarIsShare", Boolean.valueOf(isShare));
    }

    public void setQQIsShare(boolean isShare) {
        context.getSharedPreferences(SHARE, 0).edit().putBoolean("qqIsShare", isShare).commit();
    }

    public boolean qqIsShare() {
        return context.getSharedPreferences(SHARE, 0).getBoolean("qqIsShare", true);
    }

    public void saveHomePageMarkid(String markid) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "homepageMarkid", markid);
    }

    public String getHomePageDataMarkid() {
        return (String) SharedPreferenceUtils.get(context, HOME_PAGE, "homepageMarkid", null);
    }

    public void saveHomePageRecommendMarkid(String markid) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "homepageRecommendMarkid", markid);
    }

    public String getHomePageDataRecommendMarkid() {
        return (String) SharedPreferenceUtils.get(context, HOME_PAGE, "homepageRecommendMarkid", null);
    }

    public void saveDialogMsgInfo(String info) {
        SharedPreferenceUtils.put(context, DIALOG_MSG, "dialogMsgInfo", info);
    }

    public String getDialogMsgInfo() {
        return (String) SharedPreferenceUtils.get(context, DIALOG_MSG, "dialogMsgInfo", null);
    }

    public void saveDialogMsgIsSuc(boolean isSuc) {
        SharedPreferenceUtils.put(context, DIALOG_MSG, "dialogMsgInit", Boolean.valueOf(isSuc));
    }

    public boolean getDialogMsgIsSuc() {
        return ((Boolean) SharedPreferenceUtils.get(context, DIALOG_MSG, "dialogMsgInit", Boolean.valueOf(false))).booleanValue();
    }

    public void saveHasVideoShotGuide(Boolean isGuide) {
        context.getSharedPreferences(SHARE, 0).edit().putBoolean(HAS_SHOWN_VIDEOSHOT_GUIDE, isGuide.booleanValue()).commit();
    }

    public boolean getHasVideoShotGuide() {
        return context.getSharedPreferences(SHARE, 0).getBoolean(HAS_SHOWN_VIDEOSHOT_GUIDE, false);
    }

    public void saveHasVideoShotBreath(Boolean isBreath) {
        context.getSharedPreferences(SHARE, 0).edit().putBoolean(HAS_SHOWN_VIDEOSHOT_BREATH, isBreath.booleanValue()).commit();
    }

    public boolean getHasVideoShotBreath() {
        return context.getSharedPreferences(SHARE, 0).getBoolean(HAS_SHOWN_VIDEOSHOT_BREATH, false);
    }

    public void saveHomePageData(String data) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "homepage", data);
    }

    public String getHomePageData() {
        return (String) SharedPreferenceUtils.get(context, HOME_PAGE, "homepage", null);
    }

    public void saveHomePageRecommendData(String data) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "homepageRecommend", data);
    }

    public String getHomePageRecommendData() {
        return (String) SharedPreferenceUtils.get(context, HOME_PAGE, "homepageRecommend", null);
    }

    public void saveChannelSiftMarkid(String markid) {
        SharedPreferenceUtils.put(context, CHANNEL_PAGE, "channelSiftMarkid", markid);
    }

    public String getChannelSiftMarkid() {
        return (String) SharedPreferenceUtils.get(context, CHANNEL_PAGE, "channelSiftMarkid", null);
    }

    public void saveChannelSiftData(String markid) {
        SharedPreferenceUtils.put(context, CHANNEL_PAGE, "channelSift", markid);
    }

    public String getChannelSiftData() {
        return (String) SharedPreferenceUtils.get(context, CHANNEL_PAGE, "channelSift", null);
    }

    public boolean isPush() {
        return ((Boolean) SharedPreferenceUtils.get(context, SHARE, "isPush", Boolean.valueOf(true))).booleanValue();
    }

    public void setIsPush(boolean isPush) {
        SharedPreferenceUtils.put(context, SHARE, "isPush", Boolean.valueOf(isPush));
    }

    public void setTransferCookies(String transfer_cookies) {
        SharedPreferenceUtils.put(context, AD_COOKIES, "transfer_cookies", transfer_cookies);
    }

    public String getTransferCookies() {
        return (String) SharedPreferenceUtils.get(context, AD_COOKIES, "transfer_cookies", "");
    }

    public void setApiLevel(int state) {
        SharedPreferenceUtils.put(context, API, "api_level", Integer.valueOf(state));
    }

    public Integer getApiLevel() {
        return (Integer) SharedPreferenceUtils.get(context, API, "api_level", Integer.valueOf(2));
    }

    public boolean isTestApi() {
        boolean isTest = false;
        switch (getApiLevel().intValue()) {
            case 0:
                isTest = true;
                break;
            case 1:
                isTest = false;
                break;
            case 2:
                isTest = ((Boolean) SharedPreferenceUtils.get(context, API, "test", Boolean.valueOf(false))).booleanValue();
                break;
        }
        if (LetvConfig.isDebug()) {
            return isTest;
        }
        return ((Boolean) SharedPreferenceUtils.get(context, API, "test", Boolean.valueOf(false))).booleanValue();
    }

    public void setTestApi(boolean isTest) {
        SharedPreferenceUtils.put(context, API, "test", Boolean.valueOf(isTest));
    }

    public boolean hasUpdateMulti() {
        return ((Boolean) SharedPreferenceUtils.get(context, API, "update_to_multi", Boolean.valueOf(false))).booleanValue();
    }

    public void updateMulti() {
        SharedPreferenceUtils.put(context, API, "update_to_multi", Boolean.valueOf(true));
    }

    public boolean isUpdateLiveBook() {
        return ((Boolean) SharedPreferenceUtils.get(context, PUSH, "isUpdateLiveBook", Boolean.valueOf(true))).booleanValue();
    }

    public void setIsUpdateLiveBook(boolean isUpdateLiveBook) {
        SharedPreferenceUtils.put(context, PUSH, "isUpdateLiveBook", Boolean.valueOf(isUpdateLiveBook));
    }

    public boolean isLiveRemind() {
        return ((Boolean) SharedPreferenceUtils.get(context, PUSH, "isLiveRemind", Boolean.valueOf(true))).booleanValue();
    }

    public void setIsLiveRemind(boolean isLiveRemind) {
        SharedPreferenceUtils.put(context, PUSH, "isLiveRemind", Boolean.valueOf(isLiveRemind));
    }

    public boolean isFirstVote() {
        return ((Boolean) SharedPreferenceUtils.get(context, FIRST_VOTE_KEY, Boolean.valueOf(true))).booleanValue();
    }

    public void setIsFirstVote(boolean b) {
        SharedPreferenceUtils.put(context, FIRST_VOTE_KEY, Boolean.valueOf(b));
    }

    public String getDownloadLocation() {
        return (String) SharedPreferenceUtils.get(context, PUSH, DownloadConstant.DOWNLOAD_LOCATION_KEY, DOWNLOAD_LOCATION_DIR);
    }

    public void setDownloadLocation(String path) {
        SharedPreferenceUtils.put(context, DownloadConstant.DOWNLOAD_LOCATION_KEY, DownloadConstant.DOWNLOAD_LOCATION_KEY, path);
    }

    public void setRecommendNum(int num) {
        SharedPreferenceUtils.put(context, RECOMMEND, "num", Integer.valueOf(num));
    }

    public int getRecommendNum() {
        return ((Integer) SharedPreferenceUtils.get(context, RECOMMEND, "num", Integer.valueOf(0))).intValue();
    }

    public boolean isPopTencentDialog() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "isPop", Boolean.valueOf(false))).booleanValue();
    }

    public void setisPopTencentdialog(boolean isPop) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "isPop", Boolean.valueOf(isPop));
    }

    public int getCurBootingOrder() {
        return ((Integer) SharedPreferenceUtils.get(context, HOME_PAGE, "bootingOrder", Integer.valueOf(0))).intValue();
    }

    public void setCurBootingOrder(int order) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "bootingOrder", Integer.valueOf(order));
    }

    public void setBottomRecommendSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "bottomrecommendswitch", Boolean.valueOf(isOpen));
        if (LetvUtils.isSpecialChannel()) {
            SharedPreferenceUtils.put(context, HOME_PAGE, "bottomrecommendswitch", Boolean.valueOf(false));
        }
    }

    public boolean getBottomRecommendSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "bottomrecommendswitch", Boolean.valueOf(false))).booleanValue();
    }

    public void setChannelRecommendSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "channelrecommendswitch", Boolean.valueOf(isOpen));
        if (LetvUtils.isSpecialChannel()) {
            SharedPreferenceUtils.put(context, HOME_PAGE, "channelrecommendswitch", Boolean.valueOf(false));
        }
    }

    public boolean getChannelRecommendSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "channelrecommendswitch", Boolean.valueOf(false))).booleanValue();
    }

    public void setPopRecommendSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "poprecommendswitch", Boolean.valueOf(isOpen));
    }

    public boolean isPopRecommendSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "poprecommendswitch", Boolean.valueOf(false))).booleanValue();
    }

    public void setChannelWorldCupSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "channelWorldCupswitch", Boolean.valueOf(isOpen));
    }

    public boolean getChannelWorldCupSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "channelWorldCupswitch", Boolean.valueOf(false))).booleanValue();
    }

    public void setDoublySwitch(boolean isFirst) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "doublyswitch", Boolean.valueOf(isFirst));
    }

    public boolean getDoublySwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "doublyswitch", Boolean.valueOf(false))).booleanValue();
    }

    public void setLogoInfo(boolean status) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "logoInfo", Boolean.valueOf(status));
    }

    public boolean getLogoInfo() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "logoInfo", Boolean.valueOf(false))).booleanValue();
    }

    public void setJpushInfo(boolean status) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "JpushInfo", Boolean.valueOf(status));
    }

    public boolean getJpushInfo() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "JpushInfo", Boolean.valueOf(false))).booleanValue();
    }

    public void setUtp(boolean status) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "utp", Boolean.valueOf(status));
    }

    public boolean getUtp() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "utp", Boolean.valueOf(false))).booleanValue();
    }

    public void setChinaUnicomSwitch(boolean isOpen) {
        Logger.d(TAG, "联通流量包是否开启 = " + isOpen);
        SharedPreferenceUtils.put(context, HOME_PAGE, "china_unicom", Boolean.valueOf(isOpen));
    }

    public boolean isChinaUnicomSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "china_unicom", Boolean.valueOf(true))).booleanValue();
    }

    public void setLinkShellSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "link_shell", Boolean.valueOf(isOpen));
    }

    public boolean isLinkShellSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "link_shell", Boolean.valueOf(true))).booleanValue();
    }

    public void setMP4UtpSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "mp4_utp", Boolean.valueOf(isOpen));
    }

    public boolean isMP4UtpSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "mp4_utp", Boolean.valueOf(false))).booleanValue();
    }

    public void closePlayBrControl() {
        SharedPreferenceUtils.put(context, BR_CONTROL, "play_control", Integer.valueOf(LetvUtils.getClientVersionCode()));
    }

    public boolean playBrControlIsClose() {
        return ((Integer) SharedPreferenceUtils.get(context, BR_CONTROL, "play_control", Integer.valueOf(0))).intValue() == LetvUtils.getClientVersionCode();
    }

    public void closeDownloadBrControl() {
        SharedPreferenceUtils.put(context, BR_CONTROL, "download_control", Integer.valueOf(LetvUtils.getClientVersionCode()));
    }

    public boolean downloadBrControlIsClose() {
        return ((Integer) SharedPreferenceUtils.get(context, BR_CONTROL, "download_control", Integer.valueOf(0))).intValue() == LetvUtils.getClientVersionCode();
    }

    public void setDownloadLow_zh(String name) {
        SharedPreferenceUtils.put(context, BR_CONTROL, "low_download_zh", name);
    }

    public void setDownloadNormal_zh(String name) {
        SharedPreferenceUtils.get(context, BR_CONTROL, "normal_download_zh", name);
    }

    public void setDownloadHigh_zh(String name) {
        SharedPreferenceUtils.put(context, BR_CONTROL, "high_download_zh", name);
    }

    public void setPlaySuperSpeed_zh(String name) {
        SharedPreferenceUtils.put(context, BR_CONTROL, "superSpeed_play_zh", name);
    }

    public void setPlayLow_zh(String name) {
        SharedPreferenceUtils.put(context, BR_CONTROL, "low_play_zh", name);
    }

    public void setPlayNormal_zh(String name) {
        SharedPreferenceUtils.put(context, BR_CONTROL, "normal_play_zh", name);
    }

    public void setPlayHigh_zh(String name) {
        SharedPreferenceUtils.put(context, BR_CONTROL, "high_play_zh", name);
    }

    public void setM3v(String m3v) {
        if (!TextUtils.isEmpty(m3v)) {
            SharedPreferenceUtils.put(context, BR_CONTROL, "m3v", m3v);
        }
    }

    public String getM3v() {
        return (String) SharedPreferenceUtils.get(context, BR_CONTROL, "m3v", "3");
    }

    public void setVersionCode(int VersionCodeId) {
        SharedPreferenceUtils.put(context, VERSIONCODE, "VersionCodeId", Integer.valueOf(VersionCodeId));
    }

    public int getVersionCode() {
        return ((Integer) SharedPreferenceUtils.get(context, VERSIONCODE, "VersionCodeId", Integer.valueOf(0))).intValue();
    }

    public void saveQZoneLogin(String openid, String access_token, String expires_in) {
        Editor editor = context.getSharedPreferences("qzone", 0).edit();
        editor.putString("openid", openid);
        editor.putString("access_token", access_token);
        editor.putString("expires_in", expires_in);
        editor.commit();
    }

    public String[] getQZoneLogin() {
        SharedPreferences preferences = context.getSharedPreferences("qzone", 0);
        return new String[]{preferences.getString("openid", ""), preferences.getString("access_token", ""), preferences.getString("expires_in", "")};
    }

    public void clearQZoneLogin() {
        context.getSharedPreferences("qzone", 0).edit().clear().commit();
    }

    public void setLastExchangePopTime(long currdays) {
        SharedPreferenceUtils.put(context, LAST_EXCHANGE_POP_TIME, LAST_EXCHANGE_POP_TIME, Long.valueOf(currdays));
    }

    public long getLastExchangePopTime() {
        return ((Long) SharedPreferenceUtils.get(context, LAST_EXCHANGE_POP_TIME, LAST_EXCHANGE_POP_TIME, Long.valueOf(0))).longValue();
    }

    public void setPointsLoginGainDate(String date) {
        SharedPreferenceUtils.put(context, POINTS, "login_date", date);
    }

    public String getPointsLoginGainDate() {
        return (String) SharedPreferenceUtils.get(context, POINTS, "login_date", "");
    }

    public void setPointsVideoGainDate(String date) {
        SharedPreferenceUtils.put(context, POINTS, "video_date", date);
    }

    public String getPointsVideoGainDate() {
        return (String) SharedPreferenceUtils.get(context, POINTS, "video_date", "");
    }

    public void setPointsShareGainDate(String date) {
        SharedPreferenceUtils.put(context, POINTS, "share_date", date);
    }

    public String getPointsShareGainDate() {
        return (String) SharedPreferenceUtils.get(context, POINTS, "share_date", "");
    }

    public void setPointsVideoPoints(int points) {
        SharedPreferenceUtils.put(context, _POINTS, "video_points", Integer.valueOf(points));
    }

    public int getPointsVideoPoints() {
        return ((Integer) SharedPreferenceUtils.get(context, _POINTS, "video_points", Integer.valueOf(0))).intValue();
    }

    public void setPointsSharePoints(int points) {
        SharedPreferenceUtils.put(context, _POINTS, "share_points", Integer.valueOf(points));
    }

    public int getPointsSharePoints() {
        return ((Integer) SharedPreferenceUtils.get(context, _POINTS, "share_points", Integer.valueOf(0))).intValue();
    }

    public void setShowPhonePay(boolean show) {
        SharedPreferenceUtils.put(context, PHONE_PAY, "show_phone_pay", Boolean.valueOf(show));
    }

    public boolean isShowPhonePay() {
        return ((Boolean) SharedPreferenceUtils.get(context, PHONE_PAY, "show_phone_pay", Boolean.valueOf(getInstance().isTestApi()))).booleanValue();
    }

    public void setContinueDiscount(String userId, String price) {
        if (!TextUtils.isEmpty(userId)) {
            SharedPreferenceUtils.put(context, "", new StringBuilder(String.valueOf(userId)).append("_continue_discount").toString(), price);
        }
    }

    public String getContinueDiscount(String userId) {
        if (TextUtils.isEmpty(userId)) {
            return "0";
        }
        return StringUtils.subZeroAndDot((String) SharedPreferenceUtils.get(context, "", new StringBuilder(String.valueOf(userId)).append("_continue_discount").toString(), "0"));
    }

    private void cleanContinueDiscount(String userId) {
        SharedPreferenceUtils.remove(context, "", new StringBuilder(String.valueOf(userId)).append("_continue_discount").toString());
    }

    public void saveRecommend(String recommend) {
        if (!TextUtils.isEmpty(recommend)) {
            SharedPreferenceUtils.put(context, "", RECOMMEND, recommend);
        }
    }

    public String getRecommend() {
        return (String) SharedPreferenceUtils.get(context, "", RECOMMEND, null);
    }

    public void setCrashCount(int count) {
        SharedPreferenceUtils.put(context, CRASH_COUNT, "crash", Integer.valueOf(count));
    }

    public int getCrashCount() {
        return ((Integer) SharedPreferenceUtils.get(context, CRASH_COUNT, "crash", Integer.valueOf(0))).intValue();
    }

    public void savePraise(boolean praise) {
        SharedPreferenceUtils.put(context, PRAISE_USER, "praise_kit", Boolean.valueOf(praise));
    }

    public boolean getPraise() {
        return ((Boolean) SharedPreferenceUtils.get(context, PRAISE_USER, "praise_kit", Boolean.valueOf(false))).booleanValue();
    }

    public void saveHomeRecord(String name) {
        SharedPreferenceUtils.put(context, _HOME_RECORD, "my_home_record", name);
    }

    public String getHomeRecord() {
        return (String) SharedPreferenceUtils.get(context, _HOME_RECORD, "my_home_record", "");
    }

    public void saveLastRefreshTime(String title, String time) {
        SharedPreferenceUtils.put(context, LAST_REFRESH_TIME, title, time);
    }

    public String getLastRefreshTime(String title) {
        return (String) SharedPreferenceUtils.get(context, LAST_REFRESH_TIME, title, "1");
    }

    public void saveHotLastTime(String hot) {
        SharedPreferenceUtils.put(context, LAST_HOT_TIME, "last_time_refresh_hot", hot);
    }

    public String getHotLastTime() {
        return (String) SharedPreferenceUtils.get(context, LAST_HOT_TIME, "last_time_refresh_hot", "");
    }

    public void setWorldCupStartTime(String startTime) {
        SharedPreferenceUtils.put(context, WORLD_CUP_FUNCTION, "world_cup_start_time", startTime);
    }

    public void setWorldCupEndTime(String endTime) {
        SharedPreferenceUtils.put(context, WORLD_CUP_FUNCTION, "world_cup_end_time", endTime);
    }

    public void setWorldCupPushText(String pushText) {
        SharedPreferenceUtils.put(context, WORLD_CUP_FUNCTION, "pushText", pushText);
    }

    public void setDownloadLocation(String path, boolean isMemory) {
        if (!TextUtils.isEmpty(path)) {
            SharedPreferences sp = context.getSharedPreferences(PUSH, 0);
            sp.edit().putString(DownloadConstant.DOWNLOAD_LOCATION_KEY, path).commit();
            sp.edit().putBoolean(DOWNLOAD_LOCATION_ISMEMORY_KEY, isMemory).commit();
        }
    }

    public void setDownloadLocation(String key, int value) {
        SharedPreferenceUtils.put(context, PUSH, key, Integer.valueOf(value));
    }

    public void setAttendanceCacheData(String jsonData, long time) {
        Editor editor = context.getSharedPreferences(ATTENDANCE, 0).edit();
        editor.putString("jsonData", jsonData);
        editor.putLong("time", time);
        editor.commit();
    }

    public String getAttendanceCacheData() {
        return (String) SharedPreferenceUtils.get(context, ATTENDANCE, "jsonData", "");
    }

    public long getAttendanceCacheTime() {
        return ((Long) SharedPreferenceUtils.get(context, ATTENDANCE, "time", Long.valueOf(0))).longValue();
    }

    public void clearAttendanceCache() {
        SharedPreferenceUtils.clear(context, ATTENDANCE);
    }

    public boolean getInviteFlag() {
        return ((Boolean) SharedPreferenceUtils.get(context, INVITE_FLAG, "my_invite_flag", Boolean.valueOf(true))).booleanValue();
    }

    public void setInviteFlag(boolean flag) {
        SharedPreferenceUtils.put(context, INVITE_FLAG, "my_invite_flag", Boolean.valueOf(flag));
    }

    public boolean getWoFlowAlert() {
        return ((Boolean) SharedPreferenceUtils.get(context, WO_FLOW_ALERT, "woflow", Boolean.valueOf(false))).booleanValue();
    }

    public void setWoFlowAlert(boolean isAlert) {
        SharedPreferenceUtils.put(context, WO_FLOW_ALERT, "woflow", Boolean.valueOf(isAlert));
    }

    public String getWoFlowShowTime() {
        return (String) SharedPreferenceUtils.get(context, WO_FLOW_ALERT_TIME, "woflowtime", "");
    }

    public void setWoFlowShowTime(String time) {
        SharedPreferenceUtils.put(context, WO_FLOW_ALERT_TIME, "woflowtime", time);
    }

    public boolean saveLatestLaunchTime() {
        long t = System.currentTimeMillis();
        String date = StringUtils.timeString(t);
        String minutes = StringUtils.timeStringByMinutes(t);
        SharedPreferences sp = context.getSharedPreferences(FORCE_ALERT, 0);
        if (sp.getLong(PREF_CURRENTTIMEMILLIS, 0) != 0 && t - sp.getLong(PREF_CURRENTTIMEMILLIS, 0) < 300000) {
            return false;
        }
        Editor editor = sp.edit();
        editor.putLong(PREF_CURRENTTIMEMILLIS, t);
        editor.putString(PREF_LAUNCH_DATE, date);
        editor.putString(PREF_LAUNCH_MINUTE, minutes);
        editor.commit();
        return true;
    }

    public String getLatestLaunchDate() {
        return (String) SharedPreferenceUtils.get(context, FORCE_ALERT, PREF_LAUNCH_DATE, "");
    }

    public String getLatestLaunchMinuite() {
        return (String) SharedPreferenceUtils.get(context, FORCE_ALERT, PREF_LAUNCH_MINUTE, "");
    }

    public void setForceAlertDistanceDays(String day) {
        if (!TextUtils.isEmpty(day) && day.trim().length() != 0) {
            SharedPreferenceUtils.put(context, FORCE_ALERT, PREF_ALERT_DAYS, day);
        }
    }

    public String getCurrentForceAlertDistanceDays() {
        return (String) SharedPreferenceUtils.get(context, FORCE_ALERT, PREF_ALERT_DAYS, DialogMsgConstantId.CONSTANT_10000);
    }

    public void setPipFlag(boolean isFromPip) {
        SharedPreferenceUtils.put(context, PIP_FROM, "isFromPip", Boolean.valueOf(isFromPip));
    }

    public boolean isPipFlag() {
        return ((Boolean) SharedPreferenceUtils.get(context, PIP_FROM, "isFromPip", Boolean.valueOf(false))).booleanValue();
    }

    public void setBritness(float bri) {
        SharedPreferenceUtils.put(context, PLAYER_PARAMER, "britness", Float.valueOf(bri));
    }

    public float getBritness() {
        return ((Float) SharedPreferenceUtils.get(context, PLAYER_PARAMER, "britness", Float.valueOf(0.0f))).floatValue();
    }

    public boolean isShow3gDialog() {
        return ((Boolean) SharedPreferenceUtils.get(context, DIALOG_3G, "show", Boolean.valueOf(true))).booleanValue();
    }

    public void setShow3gDialog(boolean show) {
        SharedPreferenceUtils.put(context, DIALOG_3G, "show", Boolean.valueOf(show));
    }

    public void setWorldCupTime(String time) {
        context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).edit().putString("world_cup_start_time", time).commit();
    }

    public void setWorldCupFunc(boolean b) {
        context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).edit().putBoolean("world_cup_func", b).commit();
    }

    public boolean getWorldCupFunc() {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getBoolean("world_cup_func", false);
    }

    public String getDeviceId(Context context) {
        return context.getSharedPreferences(SETTINGS, 4).getString("letv_deviceId", null);
    }

    public void setDeviceId(Context context, String deviceId) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putString("letv_deviceId", deviceId);
        editor.commit();
    }

    public void setPicture(String picture) {
        context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).edit().putString(SocialConstants.PARAM_AVATAR_URI, picture).commit();
    }

    public String getPicture() {
        return context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).getString(SocialConstants.PARAM_AVATAR_URI, "");
    }

    public File getDownloadDir() {
        File file = new File(getDownloadLocation());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public boolean getHaveLoginPage() {
        return context.getSharedPreferences(HAVELOGINPAGEKEY, 0).getBoolean("have_login_page", false);
    }

    public int getCurrentDownloadStream() {
        return context.getSharedPreferences(SETTINGS, 4).getInt("downloadStream", 2);
    }

    public void setCurrentDownloadStream(int stream) {
        context.getSharedPreferences(SETTINGS, 4).edit().putInt("downloadStream", stream).commit();
    }

    public void setDownloadPath(Context context, String path, String defaultPath) {
        SharedPreferences sp = context.getSharedPreferences(WORLD_CUP_FUNCTION, 4);
        sp.edit().putString("download_path", path).commit();
        sp.edit().putString("download_default_path", defaultPath).commit();
    }

    public String getDownloadPath(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("download_path", "");
    }

    public void setHaveLoginPage(boolean flag) {
        Editor editor = context.getSharedPreferences(HAVELOGINPAGEKEY, 0).edit();
        editor.putBoolean("have_login_page", flag);
        editor.commit();
    }

    public void setScore(String score) {
        context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).edit().putString("score", score).commit();
    }

    public String getScore() {
        return context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).getString("score", "");
    }

    public void setEmail(String email) {
        context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).edit().putString(NotificationCompat.CATEGORY_EMAIL, email).commit();
    }

    public String getEmail() {
        return context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).getString(NotificationCompat.CATEGORY_EMAIL, "");
    }

    public void setSsouid(String ssouid) {
        context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).edit().putString("ssouid", ssouid).commit();
    }

    public String getSsouid() {
        return context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).getString("ssouid", "");
    }

    public boolean getClickLetvLogin() {
        return context.getSharedPreferences(HAVELOGINPAGEKEY, 0).getBoolean("click_letv_login", false);
    }

    public void setClickLetvLogin(boolean flag) {
        Editor editor = context.getSharedPreferences(HAVELOGINPAGEKEY, 0).edit();
        editor.putBoolean("click_letv_login", flag);
        editor.commit();
    }

    public void saveTimeOfFloatInfoSuccess(String isSuccessTime) {
        Editor editor = context.getSharedPreferences(GET_FLAOT_DATA_TIME, 0).edit();
        editor.putString("isSuccessTime", isSuccessTime);
        editor.commit();
    }

    public String getTimeOfFloatInfoSuccess() {
        return context.getSharedPreferences(GET_FLAOT_DATA_TIME, 0).getString("isSuccessTime", "invalid");
    }

    public void setAdOfflineSwitch(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "ad_Offline", Boolean.valueOf(isOpen));
    }

    public boolean isAdOfflineSwitch() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "ad_Offline", Boolean.valueOf(true))).booleanValue();
    }

    public String getContinuePayDateTime() {
        return context.getSharedPreferences(SETTINGS, 4).getString("continue_pay_datetime", "");
    }

    public void setGameShow(boolean isShow) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putBoolean("isGameShow", isShow);
        if (LetvUtils.isSpecialChannel()) {
            editor.putBoolean("isGameShow", false);
        }
        editor.commit();
    }

    public void setDataNum(String num) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putString("dataNum", num);
        editor.commit();
    }

    public void setPushTm(int pushTime) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putInt("pushTime", pushTime);
        editor.commit();
    }

    public int getPushTm() {
        return context.getSharedPreferences(SETTINGS, 4).getInt("pushTime", -1);
    }

    public void setChannelShow(boolean isShow) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putBoolean("isChannelShow", isShow);
        editor.commit();
    }

    public boolean getChannelShow() {
        return context.getSharedPreferences(SETTINGS, 4).getBoolean("isChannelShow", false);
    }

    public void setLeMallShow(boolean isShow) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putBoolean("isLeMallShow", isShow);
        editor.commit();
    }

    public boolean isLeMallShow() {
        return context.getSharedPreferences(SETTINGS, 4).getBoolean("isLeMallShow", true);
    }

    public boolean getGameShow() {
        return context.getSharedPreferences(SETTINGS, 4).getBoolean("isGameShow", false);
    }

    public String getDataNum() {
        String deafultNum = "2";
        return context.getSharedPreferences(SETTINGS, 4).getString("dataNum", "2");
    }

    public int getDownloadFlag() {
        return context.getSharedPreferences(PUSH, 0).getInt("now_type", -1);
    }

    public void setFristApp() {
        context.getSharedPreferences("fristapp", 4).edit().putBoolean("frist", false).commit();
    }

    public boolean getFristApp() {
        return context.getSharedPreferences("fristapp", 4).getBoolean("frist", true);
    }

    public void setCacheTitle(long id, String title) {
        context.getSharedPreferences(CACHE_TITLE, 4).edit().putString(String.valueOf(id), title).commit();
    }

    public String getCacheTitle(long id) {
        return context.getSharedPreferences(CACHE_TITLE, 4).getString(String.valueOf(id), "");
    }

    public void setLesoNotification(boolean isOpen) {
        SharedPreferenceUtils.put(context, "false", "isSkip", Boolean.valueOf(isOpen));
    }

    public boolean getLesoNotification() {
        return context.getSharedPreferences("false", 0).getBoolean(LESO_NOTIFICATION, true);
    }

    public void setVersionCode4Leso(int VersionCodeId) {
        context.getSharedPreferences(VERSIONCODE, 0).edit().putInt("VersionCodeId4Leso", VersionCodeId).commit();
    }

    public int getVersionCode4Leso() {
        return context.getSharedPreferences(VERSIONCODE, 0).getInt("VersionCodeId4Leso", 0);
    }

    public void setLastAttendanceTime(long time) {
        Editor editor = context.getSharedPreferences(ATTENDANCE, 0).edit();
        editor.putLong("time", time);
        editor.commit();
    }

    public void setInviteVisibleFlag(boolean flag) {
        context.getSharedPreferences("false", 0).edit().putBoolean("invite_visible_flag", flag).commit();
    }

    public boolean getInviteVisibleFlag() {
        return context.getSharedPreferences("false", 0).getBoolean("invite_visible_flag", false);
    }

    public void setContinuePayDateTime(String datetime, int expireDays) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putString("continue_pay_datetime", datetime);
        editor.putInt("expire_days", expireDays);
        editor.commit();
    }

    public void setContinuePayDateTime(String datetime) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putString("continue_pay_datetime", datetime);
        editor.putInt("expire_days", -101);
        editor.commit();
    }

    public void setExpireDays(int expireDays) {
        Editor editor = context.getSharedPreferences(SETTINGS, 4).edit();
        editor.putInt("expire_days", expireDays);
        editor.commit();
    }

    public int getExpireDays() {
        return context.getSharedPreferences(SETTINGS, 4).getInt("expire_days", -101);
    }

    public boolean isSViP() {
        return getVipLevel() == 2;
    }

    public void setNotifyIdLocal(int id) {
        context.getSharedPreferences(NOTIFY_ID_LOCAL_FORCE, 0).edit().putInt("local_id", id).commit();
    }

    public int getNotifyIdLocal() {
        return context.getSharedPreferences(NOTIFY_ID_LOCAL_FORCE, 0).getInt("local_id", 0);
    }

    public void setNotifyIdForcePush(int id) {
        context.getSharedPreferences(NOTIFY_ID_LOCAL_FORCE, 0).edit().putInt("push_id", id).commit();
    }

    public int getNotifyIdForcePush() {
        return context.getSharedPreferences(NOTIFY_ID_LOCAL_FORCE, 0).getInt("push_id", 0);
    }

    public String getTVSpreadMarkForV551() {
        return context.getSharedPreferences(SETTINGS, 4).getString("TVSpreadMark551", "0");
    }

    public void setTVSpreadMarkForV551(String TVSpreadMark) {
        context.getSharedPreferences(SETTINGS, 4).edit().putString("TVSpreadMark551", TVSpreadMark).commit();
    }

    public String getUA() {
        return context.getSharedPreferences(SETTINGS, 4).getString("User-agent", "");
    }

    public void setUA(String ua) {
        context.getSharedPreferences(SETTINGS, 4).edit().putString("User-agent", ua).commit();
    }

    public String getLocationLatitude() {
        return (String) SharedPreferenceUtils.get(context, BD_LOCATION, "mlocationLatitude", "");
    }

    public void setLocationLatitude(String latitude) {
        SharedPreferenceUtils.put(context, BD_LOCATION, "mlocationLatitude", latitude);
    }

    public String getLocationLongitude() {
        return (String) SharedPreferenceUtils.get(context, BD_LOCATION, "mlocationLongitude", "");
    }

    public void setLocationLongitude(String Longitude) {
        SharedPreferenceUtils.put(context, BD_LOCATION, "mlocationLongitude", Longitude);
    }

    public void setGeoCode(String code) {
        SharedPreferenceUtils.put(context, BD_LOCATION, "geoCode", code);
    }

    public String getGeoCode() {
        return (String) SharedPreferenceUtils.get(context, BD_LOCATION, "geoCode", "");
    }

    public void setLocationCode(String code) {
        SharedPreferenceUtils.put(context, BD_LOCATION, "locationCode", code);
    }

    public String getLocationCode() {
        return (String) SharedPreferenceUtils.get(context, BD_LOCATION, "locationCode", "");
    }

    public String getLocationCity() {
        return (String) SharedPreferenceUtils.get(context, BD_LOCATION, "locationCity", "");
    }

    public void setLocationCity(String city) {
        SharedPreferenceUtils.put(context, BD_LOCATION, "locationCity", city);
    }

    public String getCountryCode() {
        return (String) SharedPreferenceUtils.get(context, BD_LOCATION, "countryCode", "");
    }

    public void setCountryCode(String code) {
        SharedPreferenceUtils.put(context, BD_LOCATION, "countryCode", code);
    }

    public boolean getAlipayAutoPayFlag() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PAY_FLAG, Boolean.valueOf(false))).booleanValue();
    }

    public void setAlipayAutoPayFlag(boolean isAgreeFlag) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PAY_FLAG, Boolean.valueOf(isAgreeFlag));
    }

    public void setAlipayAutoPayPartnerId(String partnerid) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PAY_PARTNERID, partnerid);
    }

    public String getAlipayAutoPayPartnerId() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PAY_FLAG, "");
    }

    public String getAlipayAutoPayCorderId() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PAY_CORDERID, "");
    }

    public void setAlipayAutoPayCorderId(String corderid) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PAY_CORDERID, corderid);
    }

    public String getAlipayAutoPayInfo() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PAY_INFO, "");
    }

    public void setAlipayAutoPayInfo(String info) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PAY_INFO, info);
    }

    public String getAlipayAutoProductName() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PRODUCT_NAME, "");
    }

    public void setAlipayAutoProductName(String name) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PRODUCT_NAME, name);
    }

    public String getAlipayAutoProductExpire() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PRODUCT_EXPIRE, "");
    }

    public void setAlipayAutoProductExpire(String expire) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PRODUCT_EXPIRE, expire);
    }

    public String getAlipayAutoProductPrice() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PRODUCT_PRICE, "");
    }

    public void setAlipayAutoProductPrice(String price) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PRODUCT_PRICE, price);
    }

    public String getAlipayAutoProductPayType() {
        return (String) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_PRODUCT_PAYTYPE, "");
    }

    public void setAlipayAutoProductPayType(String payType) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_PRODUCT_PAYTYPE, payType);
    }

    public boolean getAlipayAutoOpenStatus() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, ALIPAY_AUTO_MOBILE_ALL_SCREEN_OPEN_STATUS, Boolean.valueOf(false))).booleanValue();
    }

    public void setAlipayAutoOpenStatus(boolean autoPayOpenFlag) {
        SharedPreferenceUtils.put(context, SETTINGS, ALIPAY_AUTO_MOBILE_ALL_SCREEN_OPEN_STATUS, Boolean.valueOf(autoPayOpenFlag));
    }

    public long getAccumulatedCleanSize() {
        return ((Long) SharedPreferenceUtils.get(context, SETTINGS, ACCUMULATED_CLEAN_SIZE, Long.valueOf(0))).longValue();
    }

    public void setAccumulatedCleanSize(long size) {
        SharedPreferenceUtils.put(context, SETTINGS, ACCUMULATED_CLEAN_SIZE, Long.valueOf(size));
    }

    public int getPageCardVersion() {
        return ((Integer) SharedPreferenceUtils.get(context, HOME_PAGE, "page_card_" + LetvUtils.getClientVersionName(), Integer.valueOf(0))).intValue();
    }

    public void setPageCardVersion(int pageCardVersion) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "page_card_" + LetvUtils.getClientVersionName(), Integer.valueOf(pageCardVersion));
    }

    public int getAlbumPageCardVersion() {
        return ((Integer) SharedPreferenceUtils.get(context, HOME_PAGE, "album_page_card_" + LetvUtils.getClientVersionName(), Integer.valueOf(0))).intValue();
    }

    public void setAlbumPageCardVersion(int pageCardVersion) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "album_page_card_" + LetvUtils.getClientVersionName(), Integer.valueOf(pageCardVersion));
    }

    public void setPanoramaPlayGuideVisible(boolean visible) {
        SharedPreferenceUtils.put(context, SETTINGS, "panoramaPlayGuide", Boolean.valueOf(visible));
    }

    public boolean isPanoramaPlayGuideVisible() {
        return ((Boolean) SharedPreferenceUtils.get(context, SETTINGS, "panoramaPlayGuide", Boolean.valueOf(true))).booleanValue();
    }

    public void setDownloadFileStreamLevel(String vid, String vtype) {
        if (!TextUtils.isEmpty(vid)) {
            SharedPreferenceUtils.put(context, DOWNLOAD_FILE_STREAM_LEVEL, vid, vtype);
        }
    }

    public String getDownloadFileStreamLevel(String vid) {
        return (String) SharedPreferenceUtils.get(context, DOWNLOAD_FILE_STREAM_LEVEL, vid, "13");
    }

    public boolean getSupportCombine() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "supportcombine", Boolean.valueOf(true))).booleanValue();
    }

    public void setSupportCombine(boolean isOpen) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "supportcombine", Boolean.valueOf(isOpen));
    }

    public boolean getUseCombineM3u8() {
        return ((Boolean) SharedPreferenceUtils.get(context, HOME_PAGE, "usecombinem3u8", Boolean.valueOf(false))).booleanValue();
    }

    public void setUseCombineM3u8(boolean use) {
        SharedPreferenceUtils.put(context, HOME_PAGE, "usecombinem3u8", Boolean.valueOf(use));
    }

    public void setPushMsg(String pushMsg) {
        SharedPreferenceUtils.put(context, PUSH_MSG, "msg", pushMsg);
    }

    public String getPushMsg() {
        return (String) SharedPreferenceUtils.get(context, PUSH_MSG, "msg", "");
    }
}
