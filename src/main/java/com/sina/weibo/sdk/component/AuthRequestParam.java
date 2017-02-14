package com.sina.weibo.sdk.component;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;

public class AuthRequestParam extends BrowserRequestParamBase {
    public static final String EXTRA_KEY_AUTHINFO = "key_authinfo";
    public static final String EXTRA_KEY_LISTENER = "key_listener";
    private AuthInfo mAuthInfo;
    private WeiboAuthListener mAuthListener;
    private String mAuthListenerKey;

    public AuthRequestParam(Context context) {
        super(context);
        this.mLaucher = BrowserLauncher.AUTH;
    }

    protected void onSetupRequestParam(Bundle data) {
        Bundle authInfoBundle = data.getBundle(EXTRA_KEY_AUTHINFO);
        if (authInfoBundle != null) {
            this.mAuthInfo = AuthInfo.parseBundleData(this.mContext, authInfoBundle);
        }
        this.mAuthListenerKey = data.getString(EXTRA_KEY_LISTENER);
        if (!TextUtils.isEmpty(this.mAuthListenerKey)) {
            this.mAuthListener = WeiboCallbackManager.getInstance(this.mContext).getWeiboAuthListener(this.mAuthListenerKey);
        }
    }

    public void onCreateRequestParamBundle(Bundle data) {
        if (this.mAuthInfo != null) {
            data.putBundle(EXTRA_KEY_AUTHINFO, this.mAuthInfo.getAuthBundle());
        }
        if (this.mAuthListener != null) {
            WeiboCallbackManager manager = WeiboCallbackManager.getInstance(this.mContext);
            this.mAuthListenerKey = manager.genCallbackKey();
            manager.setWeiboAuthListener(this.mAuthListenerKey, this.mAuthListener);
            data.putString(EXTRA_KEY_LISTENER, this.mAuthListenerKey);
        }
    }

    public void execRequest(Activity act, int action) {
        if (action == 3) {
            if (this.mAuthListener != null) {
                this.mAuthListener.onCancel();
            }
            WeiboSdkBrowser.closeBrowser(act, this.mAuthListenerKey, null);
        }
    }

    public AuthInfo getAuthInfo() {
        return this.mAuthInfo;
    }

    public void setAuthInfo(AuthInfo mAuthInfo) {
        this.mAuthInfo = mAuthInfo;
    }

    public WeiboAuthListener getAuthListener() {
        return this.mAuthListener;
    }

    public String getAuthListenerKey() {
        return this.mAuthListenerKey;
    }

    public void setAuthListener(WeiboAuthListener mAuthListener) {
        this.mAuthListener = mAuthListener;
    }
}
