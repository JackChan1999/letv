package com.sina.weibo.sdk.auth;

import android.os.Bundle;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Oauth2AccessToken {
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_EXPIRES_IN = "expires_in";
    public static final String KEY_PHONE_NUM = "phone_num";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_UID = "uid";
    private String mAccessToken = "";
    private long mExpiresTime = 0;
    private String mPhoneNum = "";
    private String mRefreshToken = "";
    private String mUid = "";

    @Deprecated
    public Oauth2AccessToken(String responseText) {
        if (responseText != null && responseText.indexOf("{") >= 0) {
            try {
                JSONObject json = new JSONObject(responseText);
                setUid(json.optString("uid"));
                setToken(json.optString("access_token"));
                setExpiresIn(json.optString("expires_in"));
                setRefreshToken(json.optString(KEY_REFRESH_TOKEN));
                setPhoneNum(json.optString(KEY_PHONE_NUM));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Oauth2AccessToken(String accessToken, String expiresIn) {
        this.mAccessToken = accessToken;
        this.mExpiresTime = System.currentTimeMillis();
        if (expiresIn != null) {
            this.mExpiresTime += Long.parseLong(expiresIn) * 1000;
        }
    }

    public static Oauth2AccessToken parseAccessToken(String responseJsonText) {
        if (!TextUtils.isEmpty(responseJsonText) && responseJsonText.indexOf("{") >= 0) {
            try {
                JSONObject json = new JSONObject(responseJsonText);
                Oauth2AccessToken token = new Oauth2AccessToken();
                token.setUid(json.optString("uid"));
                token.setToken(json.optString("access_token"));
                token.setExpiresIn(json.optString("expires_in"));
                token.setRefreshToken(json.optString(KEY_REFRESH_TOKEN));
                token.setPhoneNum(json.optString(KEY_PHONE_NUM));
                return token;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Oauth2AccessToken parseAccessToken(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Oauth2AccessToken accessToken = new Oauth2AccessToken();
        accessToken.setUid(getString(bundle, "uid", ""));
        accessToken.setToken(getString(bundle, "access_token", ""));
        accessToken.setExpiresIn(getString(bundle, "expires_in", ""));
        accessToken.setRefreshToken(getString(bundle, KEY_REFRESH_TOKEN, ""));
        accessToken.setPhoneNum(getString(bundle, KEY_PHONE_NUM, ""));
        return accessToken;
    }

    public boolean isSessionValid() {
        return !TextUtils.isEmpty(this.mAccessToken);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("uid", this.mUid);
        bundle.putString("access_token", this.mAccessToken);
        bundle.putString(KEY_REFRESH_TOKEN, this.mRefreshToken);
        bundle.putString("expires_in", Long.toString(this.mExpiresTime));
        bundle.putString(KEY_PHONE_NUM, this.mPhoneNum);
        return bundle;
    }

    public String toString() {
        return "uid: " + this.mUid + ", " + "access_token" + ": " + this.mAccessToken + ", " + KEY_REFRESH_TOKEN + ": " + this.mRefreshToken + ", " + KEY_PHONE_NUM + ": " + this.mPhoneNum + ", " + "expires_in" + ": " + Long.toString(this.mExpiresTime);
    }

    public String getUid() {
        return this.mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getToken() {
        return this.mAccessToken;
    }

    public void setToken(String mToken) {
        this.mAccessToken = mToken;
    }

    public String getRefreshToken() {
        return this.mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.mRefreshToken = refreshToken;
    }

    public long getExpiresTime() {
        return this.mExpiresTime;
    }

    public void setExpiresTime(long mExpiresTime) {
        this.mExpiresTime = mExpiresTime;
    }

    public void setExpiresIn(String expiresIn) {
        if (!TextUtils.isEmpty(expiresIn) && !expiresIn.equals("0")) {
            setExpiresTime(System.currentTimeMillis() + (Long.parseLong(expiresIn) * 1000));
        }
    }

    private static String getString(Bundle bundle, String key, String defaultValue) {
        if (bundle == null) {
            return defaultValue;
        }
        String value = bundle.getString(key);
        return value != null ? value : defaultValue;
    }

    public String getPhoneNum() {
        return this.mPhoneNum;
    }

    private void setPhoneNum(String mPhoneNum) {
        this.mPhoneNum = mPhoneNum;
    }
}
