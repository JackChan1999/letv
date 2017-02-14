package com.sina.weibo.sdk.component;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

public abstract class BrowserRequestParamBase {
    public static final int EXEC_REQUEST_ACTION_CANCEL = 3;
    public static final int EXEC_REQUEST_ACTION_ERROR = 2;
    public static final int EXEC_REQUEST_ACTION_OK = 1;
    public static final String EXTRA_KEY_LAUNCHER = "key_launcher";
    protected static final String EXTRA_KEY_SPECIFY_TITLE = "key_specify_title";
    protected static final String EXTRA_KEY_URL = "key_url";
    protected Context mContext;
    protected BrowserLauncher mLaucher;
    protected String mSpecifyTitle;
    protected String mUrl;

    public abstract void execRequest(Activity activity, int i);

    protected abstract void onCreateRequestParamBundle(Bundle bundle);

    protected abstract void onSetupRequestParam(Bundle bundle);

    public BrowserRequestParamBase(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void setupRequestParam(Bundle data) {
        this.mUrl = data.getString(EXTRA_KEY_URL);
        this.mLaucher = (BrowserLauncher) data.getSerializable(EXTRA_KEY_LAUNCHER);
        this.mSpecifyTitle = data.getString(EXTRA_KEY_SPECIFY_TITLE);
        onSetupRequestParam(data);
    }

    public Bundle createRequestParamBundle() {
        Bundle data = new Bundle();
        if (!TextUtils.isEmpty(this.mUrl)) {
            data.putString(EXTRA_KEY_URL, this.mUrl);
        }
        if (this.mLaucher != null) {
            data.putSerializable(EXTRA_KEY_LAUNCHER, this.mLaucher);
        }
        if (!TextUtils.isEmpty(this.mSpecifyTitle)) {
            data.putString(EXTRA_KEY_SPECIFY_TITLE, this.mSpecifyTitle);
        }
        onCreateRequestParamBundle(data);
        return data;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setLauncher(BrowserLauncher launcher) {
        this.mLaucher = launcher;
    }

    public BrowserLauncher getLauncher() {
        return this.mLaucher;
    }

    public void setSpecifyTitle(String title) {
        this.mSpecifyTitle = title;
    }

    public String getSpecifyTitle() {
        return this.mSpecifyTitle;
    }
}
