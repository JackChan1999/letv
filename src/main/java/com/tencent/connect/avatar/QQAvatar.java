package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class QQAvatar extends BaseApi {
    private IUiListener a;

    public QQAvatar(QQToken qQToken) {
        super(qQToken);
    }

    private Intent a(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, ImageActivity.class);
        return intent;
    }

    public void setAvatar(Activity activity, Uri uri, IUiListener iUiListener, int i) {
        if (this.a != null) {
            this.a.onCancel();
        }
        this.a = iUiListener;
        Bundle bundle = new Bundle();
        bundle.putString(SocialConstants.PARAM_AVATAR_URI, uri.toString());
        bundle.putInt("exitAnim", i);
        bundle.putString("appid", this.mToken.getAppId());
        bundle.putString("access_token", this.mToken.getAccessToken());
        bundle.putLong("expires_in", this.mToken.getExpireTimeInSecond());
        bundle.putString("openid", this.mToken.getOpenId());
        this.mActivityIntent = a(activity);
        if (hasActivityForIntent()) {
            a(activity, bundle);
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SET_AVATAR, "12", "18", "0");
            return;
        }
        d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SET_AVATAR, "12", "18", "1");
    }

    private void a(Activity activity, Bundle bundle) {
        a(bundle);
        this.mActivityIntent.putExtra(Constants.KEY_ACTION, "action_avatar");
        this.mActivityIntent.putExtra(Constants.KEY_PARAMS, bundle);
        startAssitActivity(activity, this.a);
    }

    private void a(Bundle bundle) {
        if (this.mToken != null) {
            bundle.putString("appid", this.mToken.getAppId());
            if (this.mToken.isSessionValid()) {
                bundle.putString(Constants.PARAM_KEY_STR, this.mToken.getAccessToken());
                bundle.putString(Constants.PARAM_KEY_TYPE, "0x80");
            }
            String openId = this.mToken.getOpenId();
            if (openId != null) {
                bundle.putString("hopenid", openId);
            }
            bundle.putString(Constants.PARAM_PLATFORM, "androidqz");
            try {
                bundle.putString(Constants.PARAM_PLATFORM_ID, Global.getContext().getSharedPreferences(Constants.PREFERENCE_PF, 0).getString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF));
            } catch (Exception e) {
                e.printStackTrace();
                bundle.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
            }
        }
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        if (i2 == -1) {
            int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
            if (intExtra == 0) {
                String stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
                if (stringExtra != null) {
                    try {
                        this.a.onComplete(Util.parseJson(stringExtra));
                        return;
                    } catch (JSONException e) {
                        this.a.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra));
                        return;
                    }
                }
                this.a.onComplete(new JSONObject());
                return;
            }
            this.a.onError(new UiError(intExtra, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
            return;
        }
        this.a.onCancel();
    }
}
