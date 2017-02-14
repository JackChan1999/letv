package com.sina.weibo.sdk.component;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;

public class WidgetRequestParam extends BrowserRequestParamBase {
    public static final String EXTRA_KEY_WIDGET_CALLBACK = "key_widget_callback";
    public static final String REQ_PARAM_ATTENTION_FUID = "fuid";
    public static final String REQ_PARAM_COMMENT_CATEGORY = "category";
    public static final String REQ_PARAM_COMMENT_CONTENT = "content";
    public static final String REQ_PARAM_COMMENT_TOPIC = "q";
    private String mAppKey;
    private String mAppPackage;
    private String mAttentionFuid;
    private WeiboAuthListener mAuthListener;
    private String mAuthListenerKey;
    private String mCommentCategory;
    private String mCommentContent;
    private String mCommentTopic;
    private String mHashKey;
    private String mToken;
    private WidgetRequestCallback mWidgetRequestCallback;
    private String mWidgetRequestCallbackKey;

    public WidgetRequestParam(Context context) {
        super(context);
        this.mLaucher = BrowserLauncher.WIDGET;
    }

    protected void onSetupRequestParam(Bundle data) {
        this.mAppKey = data.getString("source");
        this.mAppPackage = data.getString(ShareRequestParam.REQ_PARAM_PACKAGENAME);
        this.mHashKey = data.getString(ShareRequestParam.REQ_PARAM_KEY_HASH);
        this.mToken = data.getString("access_token");
        this.mAttentionFuid = data.getString(REQ_PARAM_ATTENTION_FUID);
        this.mCommentTopic = data.getString(REQ_PARAM_COMMENT_TOPIC);
        this.mCommentContent = data.getString(REQ_PARAM_COMMENT_CONTENT);
        this.mCommentCategory = data.getString(REQ_PARAM_COMMENT_CATEGORY);
        this.mAuthListenerKey = data.getString(AuthRequestParam.EXTRA_KEY_LISTENER);
        if (!TextUtils.isEmpty(this.mAuthListenerKey)) {
            this.mAuthListener = WeiboCallbackManager.getInstance(this.mContext).getWeiboAuthListener(this.mAuthListenerKey);
        }
        this.mWidgetRequestCallbackKey = data.getString(EXTRA_KEY_WIDGET_CALLBACK);
        if (!TextUtils.isEmpty(this.mWidgetRequestCallbackKey)) {
            this.mWidgetRequestCallback = WeiboCallbackManager.getInstance(this.mContext).getWidgetRequestCallback(this.mWidgetRequestCallbackKey);
        }
        this.mUrl = buildUrl(this.mUrl);
    }

    public void onCreateRequestParamBundle(Bundle data) {
        this.mAppPackage = this.mContext.getPackageName();
        if (!TextUtils.isEmpty(this.mAppPackage)) {
            this.mHashKey = MD5.hexdigest(Utility.getSign(this.mContext, this.mAppPackage));
        }
        data.putString("access_token", this.mToken);
        data.putString("source", this.mAppKey);
        data.putString(ShareRequestParam.REQ_PARAM_PACKAGENAME, this.mAppPackage);
        data.putString(ShareRequestParam.REQ_PARAM_KEY_HASH, this.mHashKey);
        data.putString(REQ_PARAM_ATTENTION_FUID, this.mAttentionFuid);
        data.putString(REQ_PARAM_COMMENT_TOPIC, this.mCommentTopic);
        data.putString(REQ_PARAM_COMMENT_CONTENT, this.mCommentContent);
        data.putString(REQ_PARAM_COMMENT_CATEGORY, this.mCommentCategory);
        WeiboCallbackManager manager = WeiboCallbackManager.getInstance(this.mContext);
        if (this.mAuthListener != null) {
            this.mAuthListenerKey = manager.genCallbackKey();
            manager.setWeiboAuthListener(this.mAuthListenerKey, this.mAuthListener);
            data.putString(AuthRequestParam.EXTRA_KEY_LISTENER, this.mAuthListenerKey);
        }
        if (this.mWidgetRequestCallback != null) {
            this.mWidgetRequestCallbackKey = manager.genCallbackKey();
            manager.setWidgetRequestCallback(this.mWidgetRequestCallbackKey, this.mWidgetRequestCallback);
            data.putString(EXTRA_KEY_WIDGET_CALLBACK, this.mWidgetRequestCallbackKey);
        }
    }

    private String buildUrl(String baseUrl) {
        Builder builder = Uri.parse(baseUrl).buildUpon();
        builder.appendQueryParameter("version", "0031105000");
        if (!TextUtils.isEmpty(this.mAppKey)) {
            builder.appendQueryParameter("source", this.mAppKey);
        }
        if (!TextUtils.isEmpty(this.mToken)) {
            builder.appendQueryParameter("access_token", this.mToken);
        }
        String aid = Utility.getAid(this.mContext, this.mAppKey);
        if (!TextUtils.isEmpty(aid)) {
            builder.appendQueryParameter("aid", aid);
        }
        if (!TextUtils.isEmpty(this.mAppPackage)) {
            builder.appendQueryParameter(ShareRequestParam.REQ_PARAM_PACKAGENAME, this.mAppPackage);
        }
        if (!TextUtils.isEmpty(this.mHashKey)) {
            builder.appendQueryParameter(ShareRequestParam.REQ_PARAM_KEY_HASH, this.mHashKey);
        }
        if (!TextUtils.isEmpty(this.mAttentionFuid)) {
            builder.appendQueryParameter(REQ_PARAM_ATTENTION_FUID, this.mAttentionFuid);
        }
        if (!TextUtils.isEmpty(this.mCommentTopic)) {
            builder.appendQueryParameter(REQ_PARAM_COMMENT_TOPIC, this.mCommentTopic);
        }
        if (!TextUtils.isEmpty(this.mCommentContent)) {
            builder.appendQueryParameter(REQ_PARAM_COMMENT_CONTENT, this.mCommentContent);
        }
        if (!TextUtils.isEmpty(this.mCommentCategory)) {
            builder.appendQueryParameter(REQ_PARAM_COMMENT_CATEGORY, this.mCommentCategory);
        }
        return builder.build().toString();
    }

    public String getAttentionFuid() {
        return this.mAttentionFuid;
    }

    public void setAttentionFuid(String fuid) {
        this.mAttentionFuid = fuid;
    }

    public String getCommentContent() {
        return this.mCommentContent;
    }

    public void setCommentContent(String content) {
        this.mCommentContent = content;
    }

    public String getCommentTopic() {
        return this.mCommentTopic;
    }

    public void setCommentTopic(String topic) {
        this.mCommentTopic = topic;
    }

    public String getCommentCategory() {
        return this.mCommentCategory;
    }

    public void setCommentCategory(String category) {
        this.mCommentCategory = category;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public void setAppKey(String mAppKey) {
        this.mAppKey = mAppKey;
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

    public WidgetRequestCallback getWidgetRequestCallback() {
        return this.mWidgetRequestCallback;
    }

    public String getWidgetRequestCallbackKey() {
        return this.mWidgetRequestCallbackKey;
    }

    public void setWidgetRequestCallback(WidgetRequestCallback l) {
        this.mWidgetRequestCallback = l;
    }

    public void execRequest(Activity act, int action) {
        if (action == 3) {
            WeiboSdkBrowser.closeBrowser(act, this.mAuthListenerKey, this.mWidgetRequestCallbackKey);
        }
    }
}
