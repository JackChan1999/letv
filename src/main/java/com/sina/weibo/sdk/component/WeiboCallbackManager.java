package com.sina.weibo.sdk.component;

import android.content.Context;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.component.WidgetRequestParam.WidgetRequestCallback;
import java.util.HashMap;
import java.util.Map;

public class WeiboCallbackManager {
    private static WeiboCallbackManager sInstance;
    private Context mContext;
    private Map<String, WeiboAuthListener> mWeiboAuthListenerMap = new HashMap();
    private Map<String, WidgetRequestCallback> mWidgetRequestCallbackMap = new HashMap();

    private WeiboCallbackManager(Context context) {
        this.mContext = context;
    }

    public static synchronized WeiboCallbackManager getInstance(Context context) {
        WeiboCallbackManager weiboCallbackManager;
        synchronized (WeiboCallbackManager.class) {
            if (sInstance == null) {
                sInstance = new WeiboCallbackManager(context);
            }
            weiboCallbackManager = sInstance;
        }
        return weiboCallbackManager;
    }

    public synchronized WeiboAuthListener getWeiboAuthListener(String callbackId) {
        WeiboAuthListener weiboAuthListener;
        if (TextUtils.isEmpty(callbackId)) {
            weiboAuthListener = null;
        } else {
            weiboAuthListener = (WeiboAuthListener) this.mWeiboAuthListenerMap.get(callbackId);
        }
        return weiboAuthListener;
    }

    public synchronized void setWeiboAuthListener(String callbackId, WeiboAuthListener authListener) {
        if (!(TextUtils.isEmpty(callbackId) || authListener == null)) {
            this.mWeiboAuthListenerMap.put(callbackId, authListener);
        }
    }

    public synchronized void removeWeiboAuthListener(String callbackId) {
        if (!TextUtils.isEmpty(callbackId)) {
            this.mWeiboAuthListenerMap.remove(callbackId);
        }
    }

    public synchronized WidgetRequestCallback getWidgetRequestCallback(String callbackId) {
        WidgetRequestCallback widgetRequestCallback;
        if (TextUtils.isEmpty(callbackId)) {
            widgetRequestCallback = null;
        } else {
            widgetRequestCallback = (WidgetRequestCallback) this.mWidgetRequestCallbackMap.get(callbackId);
        }
        return widgetRequestCallback;
    }

    public synchronized void setWidgetRequestCallback(String callbackId, WidgetRequestCallback l) {
        if (!(TextUtils.isEmpty(callbackId) || l == null)) {
            this.mWidgetRequestCallbackMap.put(callbackId, l);
        }
    }

    public synchronized void removeWidgetRequestCallback(String callbackId) {
        if (!TextUtils.isEmpty(callbackId)) {
            this.mWidgetRequestCallbackMap.remove(callbackId);
        }
    }

    public String genCallbackKey() {
        return String.valueOf(System.currentTimeMillis());
    }
}
