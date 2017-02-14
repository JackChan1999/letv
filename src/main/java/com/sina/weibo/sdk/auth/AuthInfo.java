package com.sina.weibo.sdk.auth;

import android.content.Context;
import android.os.Bundle;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.utils.Utility;

public class AuthInfo {
    private String mAppKey = "";
    private String mKeyHash = "";
    private String mPackageName = "";
    private String mRedirectUrl = "";
    private String mScope = "";

    public AuthInfo(Context context, String appKey, String redirectUrl, String scope) {
        this.mAppKey = appKey;
        this.mRedirectUrl = redirectUrl;
        this.mScope = scope;
        this.mPackageName = context.getPackageName();
        this.mKeyHash = Utility.getSign(context, this.mPackageName);
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public String getScope() {
        return this.mScope;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getKeyHash() {
        return this.mKeyHash;
    }

    public Bundle getAuthBundle() {
        Bundle mBundle = new Bundle();
        mBundle.putString("appKey", this.mAppKey);
        mBundle.putString("redirectUri", this.mRedirectUrl);
        mBundle.putString("scope", this.mScope);
        mBundle.putString(ShareRequestParam.REQ_PARAM_PACKAGENAME, this.mPackageName);
        mBundle.putString(ShareRequestParam.REQ_PARAM_KEY_HASH, this.mKeyHash);
        return mBundle;
    }

    public static AuthInfo parseBundleData(Context context, Bundle data) {
        return new AuthInfo(context, data.getString("appKey"), data.getString("redirectUri"), data.getString("scope"));
    }
}
