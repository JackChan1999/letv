package com.tencent.open.wpa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.UnsupportedEncodingException;

/* compiled from: ProGuard */
public class WPA extends BaseApi {
    public WPA(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public WPA(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void getWPAUserOnlineState(String str, IUiListener iUiListener) {
        if (str == null) {
            try {
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_WAP_STATE, "15", "18", "1");
                throw new Exception("uin null");
            } catch (Exception e) {
                if (iUiListener != null) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_ERROR, null));
                }
                d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_WAP_STATE, "15", "18", "1");
            }
        } else if (str.length() < 5) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_WAP_STATE, "15", "18", "1");
            throw new Exception("uin length < 5");
        } else {
            int i = 0;
            while (i < str.length()) {
                if (Character.isDigit(str.charAt(i))) {
                    i++;
                } else {
                    d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_WAP_STATE, "15", "18", "1");
                    throw new Exception("uin not digit");
                }
            }
            Bundle bundle = null;
            HttpUtils.requestAsync(this.mToken, Global.getContext(), "http://webpresence.qq.com/getonline?Type=1&" + str + NetworkUtils.DELIMITER_COLON, bundle, "GET", new TempRequestListener(iUiListener));
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_WAP_STATE, "15", "18", "0");
        }
    }

    public int startWPAConversation(Activity activity, String str, String str2) {
        String str3 = "mqqwpa://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=app&attach_content=%2$s";
        Intent intent = new Intent("android.intent.action.VIEW");
        if (TextUtils.isEmpty(str)) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_START_WAP, "16", "18", "1");
            return -1;
        } else if (str.length() < 5) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_START_WAP, "16", "18", "1");
            return -3;
        } else {
            int i;
            int i2 = 0;
            while (i2 < str.length()) {
                if (Character.isDigit(str.charAt(i2))) {
                    i2++;
                } else {
                    d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_START_WAP, "16", "18", "1");
                    return -4;
                }
            }
            str3 = "";
            if (!TextUtils.isEmpty(str2)) {
                try {
                    str3 = Base64.encodeToString(str2.getBytes("UTF-8"), 2);
                } catch (UnsupportedEncodingException e) {
                }
            }
            intent.setData(Uri.parse(String.format("mqqwpa://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=app&attach_content=%2$s", new Object[]{str, str3})));
            PackageManager packageManager = Global.getContext().getPackageManager();
            if (packageManager.queryIntentActivities(intent, 65536).size() > 0) {
                activity.startActivity(intent);
                i = 0;
            } else {
                intent.setData(Uri.parse("http://www.myapp.com/forward/a/45592?g_f=990935"));
                if (packageManager.queryIntentActivities(intent, 65536).size() > 0) {
                    activity.startActivity(intent);
                    i = 0;
                } else {
                    d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_START_WAP, "16", "18", "1");
                    i = -2;
                }
            }
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_START_WAP, "16", "18", "0");
            return i;
        }
    }
}
