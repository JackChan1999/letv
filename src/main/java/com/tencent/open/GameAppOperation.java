package com.tencent.open;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.open.a.f;
import com.tencent.open.b.c;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.TemporaryStorage;
import com.tencent.open.utils.ThreadManager;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class GameAppOperation extends BaseApi {
    public static final String GAME_FRIEND_ADD_MESSAGE = "add_msg";
    public static final String GAME_FRIEND_LABEL = "friend_label";
    public static final String GAME_FRIEND_OPENID = "fopen_id";
    public static final String GAME_SIGNATURE = "signature";
    public static final String GAME_UNION_ID = "unionid";
    public static final String GAME_UNION_NAME = "union_name";
    public static final String GAME_ZONE_ID = "zoneid";
    public static final char PIC_SYMBOLE = '\u0014';
    public static final String QQFAV_DATALINE_APPNAME = "app_name";
    public static final String QQFAV_DATALINE_AUDIOURL = "audioUrl";
    public static final String QQFAV_DATALINE_DESCRIPTION = "description";
    public static final String QQFAV_DATALINE_FILEDATA = "file_data";
    public static final String QQFAV_DATALINE_IMAGEURL = "image_url";
    public static final String QQFAV_DATALINE_OPENID = "open_id";
    public static final String QQFAV_DATALINE_REQTYPE = "req_type";
    public static final String QQFAV_DATALINE_SHAREID = "share_id";
    public static final String QQFAV_DATALINE_SRCTYPE = "src_type";
    public static final String QQFAV_DATALINE_TITLE = "title";
    public static final int QQFAV_DATALINE_TYPE_AUDIO = 2;
    public static final int QQFAV_DATALINE_TYPE_DEFAULT = 1;
    public static final int QQFAV_DATALINE_TYPE_IMAGE_TEXT = 5;
    public static final int QQFAV_DATALINE_TYPE_TEXT = 6;
    public static final String QQFAV_DATALINE_URL = "url";
    public static final String QQFAV_DATALINE_VERSION = "version";
    public static final String SHARE_PRIZE_ACTIVITY_ID = "activityid";
    public static final String SHARE_PRIZE_IMAGE_URL = "imageUrl";
    public static final String SHARE_PRIZE_SHARE_ID = "shareid";
    public static final String SHARE_PRIZE_SHARE_ID_LIST = "shareid_list";
    public static final String SHARE_PRIZE_SUMMARY = "summary";
    public static final int SHARE_PRIZE_SUMMARY_MAX_LENGTH = 60;
    public static final String SHARE_PRIZE_TARGET_URL = "targetUrl";
    public static final String SHARE_PRIZE_TITLE = "title";
    public static final int SHARE_PRIZE_TITLE_MAX_LENGTH = 45;
    public static final String TROOPBAR_ID = "troopbar_id";
    private static final String a = (f.d + ".GameAppOper");

    public GameAppOperation(QQToken qQToken) {
        super(qQToken);
    }

    public void makeFriend(Activity activity, Bundle bundle) {
        f.c(f.d, "-->makeFriend()  -- start");
        if (bundle == null) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_MAKE_FRIEND, "14", "18", "1");
            return;
        }
        String string = bundle.getString(GAME_FRIEND_OPENID);
        if (TextUtils.isEmpty(string)) {
            f.e(f.d, "-->make friend, fOpenid is empty.");
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_MAKE_FRIEND, "14", "18", "1");
            return;
        }
        Object string2 = bundle.getString(GAME_FRIEND_LABEL);
        Object string3 = bundle.getString(GAME_FRIEND_ADD_MESSAGE);
        Object applicationLable = Util.getApplicationLable(activity);
        Object openId = this.mToken.getOpenId();
        Object appId = this.mToken.getAppId();
        f.b(f.d, "-->make friend, fOpenid: " + string + " | label: " + string2 + " | message: " + string3 + " | openid: " + openId + " | appid:" + appId);
        StringBuffer stringBuffer = new StringBuffer("mqqapi://gamesdk/add_friend?src_type=app&version=1");
        stringBuffer.append("&fopen_id=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
        if (!TextUtils.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&app_id=" + appId);
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&friend_label=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
        }
        if (!TextUtils.isEmpty(string3)) {
            stringBuffer.append("&add_msg=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
        }
        if (!TextUtils.isEmpty(applicationLable)) {
            stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
        }
        f.b(f.d, "-->make friend, url: " + stringBuffer.toString());
        this.mActivityIntent = new Intent("android.intent.action.VIEW");
        this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
        if (!hasActivityForIntent() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_1_0) < 0) {
            f.d(f.d, "-->make friend, there is no activity.");
            a(activity);
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_MAKE_FRIEND, "14", "18", "1");
        } else {
            try {
                activity.startActivityForResult(this.mActivityIntent, 0);
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_MAKE_FRIEND, "14", "18", "0");
            } catch (Throwable e) {
                f.b(f.d, "-->make friend, start activity exception.", e);
                a(activity);
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_MAKE_FRIEND, "14", "18", "1");
            }
        }
        f.c(f.d, "-->makeFriend()  -- end");
    }

    public void bindQQGroup(Activity activity, Bundle bundle) {
        f.c(f.d, "-->bindQQGroup()  -- start");
        if (activity == null) {
            Toast.makeText(activity, "Activity参数为空", 0).show();
            f.e(f.d, "-->bindQQGroup, activity is empty.");
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
        } else if (bundle == null) {
            Toast.makeText(activity, "Bundle参数为空", 0).show();
            f.e(f.d, "-->bindQQGroup, params is empty.");
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
        } else {
            Object applicationLable = Util.getApplicationLable(activity);
            StringBuffer stringBuffer = new StringBuffer("mqqapi://gamesdk/bind_group?src_type=app&version=1");
            if (!TextUtils.isEmpty(applicationLable)) {
                stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            }
            applicationLable = bundle.getString(GAME_UNION_ID);
            if (TextUtils.isEmpty(applicationLable)) {
                Toast.makeText(activity, "游戏公会ID为空", 0).show();
                f.e(f.d, "-->bindQQGroup, game union id is empty.");
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                return;
            }
            stringBuffer.append("&unionid=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            applicationLable = bundle.getString(GAME_UNION_NAME);
            if (TextUtils.isEmpty(applicationLable)) {
                Toast.makeText(activity, "游戏公会名称为空", 0).show();
                f.e(f.d, "-->bindQQGroup, game union name is empty.");
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                return;
            }
            stringBuffer.append("&union_name=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            applicationLable = bundle.getString(GAME_ZONE_ID);
            if (TextUtils.isEmpty(applicationLable)) {
                Toast.makeText(activity, "游戏区域ID为空", 0).show();
                f.e(f.d, "-->bindQQGroup, game zone id  is empty.");
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                return;
            }
            stringBuffer.append("&zoneid=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            applicationLable = bundle.getString(GAME_SIGNATURE);
            if (TextUtils.isEmpty(applicationLable)) {
                Toast.makeText(activity, "游戏签名为空", 0).show();
                f.e(f.d, "-->bindQQGroup, game signature is empty.");
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                return;
            }
            stringBuffer.append("&signature=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            applicationLable = this.mToken.getOpenId();
            if (TextUtils.isEmpty(applicationLable)) {
                Toast.makeText(activity, "Openid为空", 0).show();
                f.e(f.d, "-->bindQQGroup, openid is empty.");
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                return;
            }
            stringBuffer.append("&openid=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
            Bundle composeActivityParams = composeActivityParams();
            for (String str : composeActivityParams.keySet()) {
                composeActivityParams.putString(str, Base64.encodeToString(Util.getBytesUTF8(composeActivityParams.getString(str)), 2));
            }
            stringBuffer.append("&" + Util.encodeUrl(composeActivityParams));
            f.b(f.d, "-->bindQQGroup, url: " + stringBuffer.toString());
            this.mActivityIntent = new Intent("android.intent.action.VIEW");
            this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
            if (!hasActivityForIntent() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_1_0) < 0) {
                f.d(f.d, "-->bind group, there is no activity, show download page.");
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                a(activity);
            } else {
                try {
                    activity.startActivityForResult(this.mActivityIntent, 0);
                    d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "0");
                } catch (Throwable e) {
                    f.b(f.d, "-->bind group, start activity exception.", e);
                    d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_BIND_GROUP, "18", "18", "1");
                    a(activity);
                }
            }
            f.c(f.d, "-->bindQQGroup()  -- end");
        }
    }

    public void addToQQFavorites(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "addToQQFavorites() -- start");
        int i = bundle.getInt("req_type", 1);
        if (a(activity, bundle, iUiListener)) {
            String string;
            StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_qqfav?src_type=app&version=1&file_type=news");
            Object string2 = bundle.getString(QQFAV_DATALINE_IMAGEURL);
            Object string3 = bundle.getString("title");
            Object string4 = bundle.getString("description");
            Object string5 = bundle.getString("url");
            Object string6 = bundle.getString(QQFAV_DATALINE_AUDIOURL);
            String applicationLable = Util.getApplicationLable(activity);
            if (applicationLable == null) {
                string = bundle.getString("app_name");
            } else {
                string = applicationLable;
            }
            ArrayList stringArrayList = bundle.getStringArrayList(QQFAV_DATALINE_FILEDATA);
            Object appId = this.mToken.getAppId();
            Object openId = this.mToken.getOpenId();
            f.b(SystemUtils.QQFAVORITES_CALLBACK_ACTION, "openId:" + openId);
            if (!TextUtils.isEmpty(string2)) {
                stringBuffer.append("&image_url=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
            }
            if (stringArrayList != null) {
                StringBuffer stringBuffer2 = new StringBuffer();
                int size = stringArrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    try {
                        stringBuffer2.append(URLEncoder.encode(((String) stringArrayList.get(i2)).trim(), "UTF-8"));
                    } catch (Throwable e) {
                        e.printStackTrace();
                        f.b(f.d, "UnsupportedEncodingException", e);
                        stringBuffer2.append(URLEncoder.encode(((String) stringArrayList.get(i2)).trim()));
                    }
                    if (i2 != size - 1) {
                        stringBuffer2.append(";");
                    }
                }
                stringBuffer.append("&file_data=" + Base64.encodeToString(Util.getBytesUTF8(stringBuffer2.toString()), 2));
            }
            if (!TextUtils.isEmpty(string3)) {
                stringBuffer.append("&title=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
            }
            if (!TextUtils.isEmpty(string4)) {
                stringBuffer.append("&description=" + Base64.encodeToString(Util.getBytesUTF8(string4), 2));
            }
            if (!TextUtils.isEmpty(appId)) {
                stringBuffer.append("&share_id=" + appId);
            }
            if (!TextUtils.isEmpty(string5)) {
                stringBuffer.append("&url=" + Base64.encodeToString(Util.getBytesUTF8(string5), 2));
            }
            if (!TextUtils.isEmpty(string)) {
                if (string.length() > 20) {
                    string = string.substring(0, 20) + "...";
                }
                stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
            }
            if (!TextUtils.isEmpty(openId)) {
                stringBuffer.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
            }
            if (!TextUtils.isEmpty(string6)) {
                stringBuffer.append("&audioUrl=" + Base64.encodeToString(Util.getBytesUTF8(string6), 2));
            }
            stringBuffer.append("&req_type=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i)), 2));
            f.b("addToQQFavorites url: ", stringBuffer.toString());
            a.a(Global.getContext(), this.mToken, "requireApi", SystemUtils.QQFAVORITES_CALLBACK_ACTION);
            this.mActivityIntent = new Intent("android.intent.action.VIEW");
            this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
            this.mActivityIntent.putExtra("pkg_name", activity.getPackageName());
            Object obj = TemporaryStorage.set(SystemUtils.QQFAVORITES_CALLBACK_ACTION, iUiListener);
            if (obj != null) {
                ((IUiListener) obj).onCancel();
            }
            if (hasActivityForIntent()) {
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_2_0) >= 0) {
                    try {
                        activity.startActivityForResult(this.mActivityIntent, 0);
                        a("21", i, "0");
                    } catch (Throwable e2) {
                        f.b(f.d, "-->addToQQFavorites, start activity exception.", e2);
                        a("21", i, "1");
                        a(activity);
                    }
                    f.c(f.d, "addToQQFavorites() --end");
                    return;
                }
            }
            f.d(f.d, "-->addToQQFavorites, there is no activity, show download page.");
            a("21", i, "1");
            a(activity);
            f.c(f.d, "addToQQFavorites() --end");
            return;
        }
        a("21", i, "1");
    }

    public void sendToMyComputer(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "sendToMyComputer() --start");
        int i = bundle.getInt("req_type", 1);
        if (a(activity, bundle, iUiListener)) {
            String string;
            StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_qqdataline?src_type=app&version=1&file_type=news");
            Object string2 = bundle.getString(QQFAV_DATALINE_IMAGEURL);
            Object string3 = bundle.getString("title");
            Object string4 = bundle.getString("description");
            Object string5 = bundle.getString("url");
            Object string6 = bundle.getString(QQFAV_DATALINE_AUDIOURL);
            String applicationLable = Util.getApplicationLable(activity);
            if (applicationLable == null) {
                string = bundle.getString("app_name");
            } else {
                string = applicationLable;
            }
            ArrayList stringArrayList = bundle.getStringArrayList(QQFAV_DATALINE_FILEDATA);
            Object appId = this.mToken.getAppId();
            Object openId = this.mToken.getOpenId();
            f.b(SystemUtils.QQDATALINE_CALLBACK_ACTION, "openId:" + openId);
            if (!TextUtils.isEmpty(string2)) {
                stringBuffer.append("&image_url=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
            }
            if (stringArrayList != null) {
                StringBuffer stringBuffer2 = new StringBuffer();
                int size = stringArrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    try {
                        stringBuffer2.append(URLEncoder.encode(((String) stringArrayList.get(i2)).trim(), "UTF-8"));
                    } catch (Throwable e) {
                        e.printStackTrace();
                        f.b(f.d, "UnsupportedEncodingException", e);
                        stringBuffer2.append(URLEncoder.encode(((String) stringArrayList.get(i2)).trim()));
                    }
                    if (i2 != size - 1) {
                        stringBuffer2.append(";");
                    }
                }
                stringBuffer.append("&file_data=" + Base64.encodeToString(Util.getBytesUTF8(stringBuffer2.toString()), 2));
            }
            if (!TextUtils.isEmpty(string3)) {
                stringBuffer.append("&title=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
            }
            if (!TextUtils.isEmpty(string4)) {
                stringBuffer.append("&description=" + Base64.encodeToString(Util.getBytesUTF8(string4), 2));
            }
            if (!TextUtils.isEmpty(appId)) {
                stringBuffer.append("&share_id=" + appId);
            }
            if (!TextUtils.isEmpty(string5)) {
                stringBuffer.append("&url=" + Base64.encodeToString(Util.getBytesUTF8(string5), 2));
            }
            if (!TextUtils.isEmpty(string)) {
                if (string.length() > 20) {
                    string = string.substring(0, 20) + "...";
                }
                stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
            }
            if (!TextUtils.isEmpty(openId)) {
                stringBuffer.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
            }
            if (!TextUtils.isEmpty(string6)) {
                stringBuffer.append("&audioUrl=" + Base64.encodeToString(Util.getBytesUTF8(string6), 2));
            }
            stringBuffer.append("&req_type=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i)), 2));
            f.b("sendToMyComputer url: ", stringBuffer.toString());
            a.a(Global.getContext(), this.mToken, "requireApi", SystemUtils.QQDATALINE_CALLBACK_ACTION);
            this.mActivityIntent = new Intent("android.intent.action.VIEW");
            this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
            this.mActivityIntent.putExtra("pkg_name", activity.getPackageName());
            Object obj = TemporaryStorage.set(SystemUtils.QQDATALINE_CALLBACK_ACTION, iUiListener);
            if (obj != null) {
                ((IUiListener) obj).onCancel();
            }
            if (hasActivityForIntent()) {
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_2_0) >= 0) {
                    try {
                        startAssistActivity(activity, Constants.REQUEST_SEND_TO_MY_COMPUTER);
                        a("22", i, "0");
                    } catch (Throwable e2) {
                        f.b(f.d, "-->addToQQFavorites, start activity exception.", e2);
                        a("22", i, "1");
                        a(activity);
                    }
                    f.c(f.d, "sendToMyComputer() --end");
                    return;
                }
            }
            f.d(f.d, "-->addToQQFavorites, there is no activity, show download page.");
            a("22", i, "1");
            a(activity);
            f.c(f.d, "sendToMyComputer() --end");
            return;
        }
        a("22", i, "1");
    }

    public void shareToTroopBar(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "shareToTroopBar() -- start");
        if (activity == null || bundle == null || iUiListener == null) {
            f.e(f.d, "activity or params or listener is null!");
            return;
        }
        Object string = bundle.getString("title");
        if (TextUtils.isEmpty(string)) {
            iUiListener.onError(new UiError(-5, "传入参数不可以为空: title is null", null));
            f.e(f.d, "shareToTroopBar() -- title is null");
        } else if (string.length() < 4 || string.length() > 25) {
            iUiListener.onError(new UiError(-5, "传入参数有误!: title size: 4 ~ 25", null));
            f.e(f.d, "shareToTroopBar() -- title size: 4 ~ 25");
        } else {
            Object string2 = bundle.getString("description");
            if (TextUtils.isEmpty(string2)) {
                iUiListener.onError(new UiError(-5, "传入参数不可以为空: description is null", null));
                f.e(f.d, "shareToTroopBar() -- description is null");
            } else if (string2.length() < 10 || string2.length() > 700) {
                iUiListener.onError(new UiError(-5, "传入参数有误!: description size: 10 ~ 700", null));
                f.e(f.d, "shareToTroopBar() -- description size: 10 ~ 700");
            } else {
                String str;
                ArrayList stringArrayList = bundle.getStringArrayList(QQFAV_DATALINE_FILEDATA);
                Object stringBuffer = new StringBuffer();
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    str = "";
                    int size = stringArrayList.size();
                    if (size > 9) {
                        iUiListener.onError(new UiError(-5, "传入参数有误!: file_data size: 1 ~ 9", null));
                        f.e(f.d, "shareToTroopBar() -- file_data size: 1 ~ 9");
                        return;
                    }
                    int i = 0;
                    while (i < size) {
                        str = ((String) stringArrayList.get(i)).trim();
                        if (!str.startsWith("/")) {
                            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_ERROR, "file_data应该为本地图片"));
                            f.e(f.d, "shareToTroopBar(): file_data应该为本地图片");
                            return;
                        } else if (!str.startsWith("/") || new File(str).exists()) {
                            i++;
                        } else {
                            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_ERROR, "图片文件不存在"));
                            f.e(f.d, "shareToTroopBar(): 图片文件不存在");
                            return;
                        }
                    }
                    for (i = 0; i < size; i++) {
                        try {
                            stringBuffer.append(URLEncoder.encode(((String) stringArrayList.get(i)).trim(), "UTF-8"));
                        } catch (Throwable e) {
                            e.printStackTrace();
                            f.b(f.d, "UnsupportedEncodingException: ", e);
                            stringBuffer.append(URLEncoder.encode(((String) stringArrayList.get(i)).trim()));
                        }
                        if (i != size - 1) {
                            stringBuffer.append(";");
                        }
                    }
                }
                Object string3 = bundle.getString(TROOPBAR_ID);
                if (TextUtils.isEmpty(string3) || Util.isNumeric(string3)) {
                    StringBuffer stringBuffer2 = new StringBuffer("mqqapi://share/to_troopbar?src_type=app&version=1&file_type=news");
                    Object appId = this.mToken.getAppId();
                    Object openId = this.mToken.getOpenId();
                    f.b(f.d, "shareToTroopBar() -- openId: " + openId);
                    str = Util.getApplicationLable(activity);
                    if (!TextUtils.isEmpty(appId)) {
                        stringBuffer2.append("&share_id=" + appId);
                    }
                    if (!TextUtils.isEmpty(openId)) {
                        stringBuffer2.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
                    }
                    if (!TextUtils.isEmpty(str)) {
                        if (str.length() > 20) {
                            str = str.substring(0, 20) + "...";
                        }
                        stringBuffer2.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(str), 2));
                    }
                    if (!TextUtils.isEmpty(string)) {
                        stringBuffer2.append("&title=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
                    }
                    if (!TextUtils.isEmpty(string2)) {
                        stringBuffer2.append("&description=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
                    }
                    if (!TextUtils.isEmpty(string3)) {
                        stringBuffer2.append("&troopbar_id=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
                    }
                    if (!TextUtils.isEmpty(stringBuffer)) {
                        stringBuffer2.append("&file_data=" + Base64.encodeToString(Util.getBytesUTF8(stringBuffer.toString()), 2));
                    }
                    f.b("shareToTroopBar, url: ", stringBuffer2.toString());
                    a.a(Global.getContext(), this.mToken, "requireApi", SystemUtils.TROOPBAR_CALLBACK_ACTION);
                    this.mActivityIntent = new Intent("android.intent.action.VIEW");
                    this.mActivityIntent.setData(Uri.parse(stringBuffer2.toString()));
                    Object packageName = activity.getPackageName();
                    if (!TextUtils.isEmpty(packageName)) {
                        this.mActivityIntent.putExtra("pkg_name", packageName);
                    }
                    packageName = TemporaryStorage.set(SystemUtils.TROOPBAR_CALLBACK_ACTION, iUiListener);
                    if (packageName != null) {
                        ((IUiListener) packageName).onCancel();
                    }
                    if (!hasActivityForIntent() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_3_0) < 0) {
                        f.d(f.d, "-->shareToTroopBar, there is no activity, show download page.");
                        a(activity, SystemUtils.QQ_VERSION_NAME_5_3_0);
                        d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_TROOPBAR, "23", "18", "1");
                    } else {
                        try {
                            startAssistActivity(activity, Constants.REQUEST_SHARE_TO_TROOP_BAR);
                            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_TROOPBAR, "23", "18", "0");
                        } catch (Throwable e2) {
                            f.b(f.d, "-->shareToTroopBar, start activity exception.", e2);
                            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_TROOPBAR, "23", "18", "1");
                            a(activity, SystemUtils.QQ_VERSION_NAME_5_3_0);
                        }
                    }
                    f.c(f.d, "shareToTroopBar() -- end");
                    return;
                }
                iUiListener.onError(new UiError(-6, "传入参数有误! troopbar_id 必须为数字", null));
                f.e(f.d, "shareToTroopBar(): troopbar_id 必须为数字");
            }
        }
    }

    public void sharePrizeToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        String str = Constants.MSG_PARAM_ERROR;
        if (activity == null || bundle == null || iUiListener == null) {
            String str2 = "activity or params or listener is null!";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
            return;
        }
        Object string = bundle.getString("title");
        if (TextUtils.isEmpty(string)) {
            str2 = "sharePrizeToQQ failed, title is empty.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
            return;
        }
        Object string2 = bundle.getString("summary");
        if (TextUtils.isEmpty(string2)) {
            str2 = "sharePrizeToQQ failed, sumary is empty.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
            return;
        }
        String string3 = bundle.getString("imageUrl");
        if (TextUtils.isEmpty(string3) || !(string3.startsWith("http://") || string3.startsWith("https://"))) {
            str2 = "sharePrizeToQQ failed, imageUrl is empty or illegal.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
            return;
        }
        final Object string4 = bundle.getString(SHARE_PRIZE_ACTIVITY_ID);
        if (TextUtils.isEmpty(string4)) {
            str2 = "sharePrizeToQQ failed, activityId is empty.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
            return;
        }
        final Bundle bundle2 = new Bundle();
        bundle2.putString("title", string);
        bundle2.putString("summary", string2);
        bundle2.putString("imageUrl", string3);
        bundle2.putInt("req_type", 1);
        final IUiListener iUiListener2 = iUiListener;
        final Activity activity2 = activity;
        ThreadManager.executeOnSubThread(new Runnable(this) {
            final /* synthetic */ GameAppOperation e;

            public void run() {
                Bundle a = this.e.b();
                if (a == null) {
                    String str = "accesstoken or openid or appid is null, please login first!";
                    f.e(GameAppOperation.a, str);
                    iUiListener2.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, str));
                    return;
                }
                a.putString(GameAppOperation.SHARE_PRIZE_ACTIVITY_ID, string4);
                try {
                    JSONObject request = HttpUtils.request(this.e.mToken, activity2.getApplicationContext(), ServerSetting.URL_PRIZE_MAKE_SHARE_URL, a, "GET");
                    try {
                        int i = request.getInt("ret");
                        int i2 = request.getInt("subCode");
                        if (i == 0 && i2 == 0) {
                            bundle2.putString("targetUrl", request.getString("share_url"));
                            new QQShare(activity2.getApplicationContext(), this.e.mToken).shareToQQ(activity2, bundle2, iUiListener2);
                            return;
                        }
                        iUiListener2.onError(new UiError(i, "make_share_url error.", request.getString("msg")));
                    } catch (JSONException e) {
                        f.e(GameAppOperation.a, "JSONException occur in make_share_url, errorMsg: " + e.getMessage());
                        iUiListener2.onError(new UiError(-4, Constants.MSG_JSON_ERROR, ""));
                    }
                } catch (Throwable e2) {
                    f.b(GameAppOperation.a, "Exception occur in make_share_url", e2);
                    iUiListener2.onError(new UiError(-2, Constants.MSG_IO_ERROR, e2.getMessage()));
                }
            }
        });
    }

    public void queryUnexchangePrize(Context context, final Bundle bundle, final IUiListener iUiListener) {
        String str = Constants.MSG_PARAM_ERROR;
        String str2;
        if (this.mToken == null || !this.mToken.isSessionValid()) {
            str2 = "queryUnexchangePrize failed, auth token is illegal.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
        } else if (context == null && Global.getContext() == null) {
            str2 = "queryUnexchangePrize failed, context is null.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
        } else if (TextUtils.isEmpty(bundle.getString(SHARE_PRIZE_ACTIVITY_ID))) {
            str2 = "queryUnexchangePrize failed, activityId is empty.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
        } else {
            if (context == null) {
                context = Global.getContext();
            }
            ThreadManager.executeOnSubThread(new Runnable(this) {
                final /* synthetic */ GameAppOperation d;

                public void run() {
                    Bundle a = this.d.b();
                    if (a == null) {
                        String str = "accesstoken or openid or appid is null, please login first!";
                        f.e(GameAppOperation.a, str);
                        iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, str));
                        return;
                    }
                    a.putAll(bundle);
                    try {
                        iUiListener.onComplete(HttpUtils.request(this.d.mToken, context, ServerSetting.URL_PRIZE_QUERY_UNEXCHANGE, a, "GET"));
                    } catch (Throwable e) {
                        f.b(GameAppOperation.a, "Exception occur in queryUnexchangePrize", e);
                        iUiListener.onError(new UiError(-2, Constants.MSG_IO_ERROR, e.getMessage()));
                    }
                }
            });
        }
    }

    public void exchangePrize(Context context, Bundle bundle, final IUiListener iUiListener) {
        String str = Constants.MSG_PARAM_ERROR;
        String str2;
        if (bundle == null) {
            str2 = "exchangePrize failed, params is null.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
        } else if (this.mToken == null || !this.mToken.isSessionValid()) {
            str2 = "exchangePrize failed, auth token is illegal.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
        } else if (context == null && Global.getContext() == null) {
            str2 = "exchangePrize failed, context is null.";
            f.e(a, str2);
            iUiListener.onError(new UiError(-5, str, str2));
        } else {
            ArrayList stringArrayList = bundle.getStringArrayList(SHARE_PRIZE_SHARE_ID_LIST);
            if (stringArrayList == null) {
                str2 = "exchangePrize failed, shareid_list is empty.";
                f.e(a, str2);
                iUiListener.onError(new UiError(-5, str, str2));
                return;
            }
            final StringBuffer stringBuffer = new StringBuffer("");
            int size = stringArrayList.size();
            String str3 = "";
            for (int i = 0; i < size; i++) {
                str3 = (String) stringArrayList.get(i);
                if (!TextUtils.isEmpty(str3)) {
                    stringBuffer.append(str3);
                    if (i < size - 1) {
                        stringBuffer.append(",");
                    }
                }
            }
            if (context == null) {
                context = Global.getContext();
            }
            ThreadManager.executeOnSubThread(new Runnable(this) {
                final /* synthetic */ GameAppOperation d;

                public void run() {
                    Bundle a = this.d.b();
                    if (a == null) {
                        String str = "accesstoken or openid or appid is null, please login first!";
                        f.e(GameAppOperation.a, str);
                        iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, str));
                        return;
                    }
                    a.putString(GameAppOperation.SHARE_PRIZE_SHARE_ID, stringBuffer.toString());
                    a.putString("imei", c.b(Global.getContext()));
                    try {
                        iUiListener.onComplete(HttpUtils.request(this.d.mToken, context, ServerSetting.URL_PRIZE_EXCHANGE, a, "GET"));
                    } catch (Throwable e) {
                        f.b(GameAppOperation.a, "Exception occur in exchangePrize", e);
                        iUiListener.onError(new UiError(-2, Constants.MSG_IO_ERROR, e.getMessage()));
                    }
                }
            });
        }
    }

    private Bundle b() {
        if (this.mToken == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        CharSequence appId = this.mToken.getAppId();
        CharSequence openId = this.mToken.getOpenId();
        CharSequence accessToken = this.mToken.getAccessToken();
        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(openId) || TextUtils.isEmpty(accessToken)) {
            f.e(a, "composeLoginStateParams fail, accesstoken or openid or appid is null");
            return null;
        }
        bundle.putString("appid", this.mToken.getAppId());
        bundle.putString("openid", this.mToken.getOpenId());
        bundle.putString("accesstoken", this.mToken.getAccessToken());
        return bundle;
    }

    public void isActivityAvailable(final Activity activity, final String str, final IUiListener iUiListener) {
        String str2 = Constants.MSG_PARAM_ERROR;
        String str3;
        if (TextUtils.isEmpty(str)) {
            str3 = "isActivityAvailable failed, activityId is null.";
            f.e(a, str3);
            iUiListener.onError(new UiError(-5, str2, str3));
        } else if (this.mToken == null || !this.mToken.isSessionValid()) {
            str3 = "exchangePrize failed, auth token is illegal.";
            f.e(a, str3);
            iUiListener.onError(new UiError(-5, str2, str3));
        } else {
            ThreadManager.executeOnSubThread(new Runnable(this) {
                final /* synthetic */ GameAppOperation d;

                public void run() {
                    Bundle a = this.d.b();
                    if (a == null) {
                        String str = "accesstoken or openid or appid is null, please login first!";
                        f.e(GameAppOperation.a, str);
                        iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, str));
                        return;
                    }
                    a.putString(GameAppOperation.SHARE_PRIZE_ACTIVITY_ID, str);
                    try {
                        iUiListener.onComplete(HttpUtils.request(this.d.mToken, activity.getApplicationContext(), ServerSetting.URL_PRIZE_GET_ACTIVITY_STATE, a, "GET"));
                    } catch (Throwable e) {
                        f.a(GameAppOperation.a, "Exception occur in make_share_url", e);
                        iUiListener.onError(new UiError(-6, "Exception occur in make_share_url", e.getMessage()));
                    }
                }
            });
        }
    }

    private boolean a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (activity == null || bundle == null || iUiListener == null) {
            f.e(f.d, "activity or params or listener is null!");
            return false;
        }
        int i = bundle.getInt("req_type", 1);
        if (TextUtils.isEmpty(bundle.getString("app_name"))) {
            iUiListener.onError(new UiError(-5, "传入参数不可以为空: app_name", null));
            return false;
        }
        CharSequence string = bundle.getString("description");
        CharSequence string2 = bundle.getString("url");
        CharSequence string3 = bundle.getString(QQFAV_DATALINE_AUDIOURL);
        CharSequence string4 = bundle.getString(QQFAV_DATALINE_IMAGEURL);
        ArrayList stringArrayList = bundle.getStringArrayList(QQFAV_DATALINE_FILEDATA);
        switch (i) {
            case 1:
                if (TextUtils.isEmpty(string2) || TextUtils.isEmpty(string4)) {
                    iUiListener.onError(new UiError(-5, "传入参数不可以为空: image_url or url is null", null));
                    return false;
                }
            case 2:
                if (TextUtils.isEmpty(string2) || TextUtils.isEmpty(string4) || TextUtils.isEmpty(string3)) {
                    iUiListener.onError(new UiError(-5, "传入参数不可以为空: image_url or url or audioUrl is null", null));
                    return false;
                }
            case 5:
                if (stringArrayList != null && stringArrayList.size() != 0) {
                    String str = "";
                    int size = stringArrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        str = ((String) stringArrayList.get(i2)).trim();
                        if (!str.startsWith("/") || new File(str).exists()) {
                            i2++;
                        } else {
                            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                            return false;
                        }
                    }
                    break;
                }
                iUiListener.onError(new UiError(-5, "传入参数不可以为空: fill_data is null", null));
                return false;
                break;
            case 6:
                if (TextUtils.isEmpty(string)) {
                    iUiListener.onError(new UiError(-5, "传入参数不可以为空: description is null", null));
                    return false;
                }
                break;
            default:
                iUiListener.onError(new UiError(-5, "传入参数有误!: unknow req_type", null));
                return false;
        }
        return true;
    }

    public void releaseResource() {
        f.c(f.d, "releaseResource() -- start");
        TemporaryStorage.remove(SystemUtils.QQDATALINE_CALLBACK_ACTION);
        TemporaryStorage.remove(SystemUtils.QQFAVORITES_CALLBACK_ACTION);
        TemporaryStorage.remove(SystemUtils.TROOPBAR_CALLBACK_ACTION);
        f.c(f.d, "releaseResource() -- end");
    }

    private void a(Activity activity) {
        a(activity, "");
    }

    private void a(Activity activity, String str) {
        new TDialog(activity, "", getCommonDownloadQQUrl(str), null, this.mToken).show();
    }

    private void a(String str, int i, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            f.a(f.d, "reportForVia() error: reportType or result is null");
            return;
        }
        String str3;
        String str4 = "";
        switch (i) {
            case 1:
                str3 = "6";
                break;
            case 2:
                str3 = "3";
                break;
            case 5:
                str3 = "1";
                break;
            case 6:
                str3 = "5";
                break;
            default:
                f.e(f.d, "GameAppOperation -- reportForVia() error: unknow type " + String.valueOf(i));
                return;
        }
        d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", str, "28", str2, str3, "0", "", "");
    }
}
