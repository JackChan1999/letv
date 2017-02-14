package com.coolcloud.uac.android.api.provider;

import android.content.Context;
import android.os.Bundle;
import com.coolcloud.uac.android.common.Config;
import com.coolcloud.uac.android.common.provider.BasicProvider;
import com.coolcloud.uac.android.common.provider.Provider;
import com.coolcloud.uac.android.common.provider.RTKTEntity;
import com.coolcloud.uac.android.common.provider.SettingsProvider;
import com.coolcloud.uac.android.common.provider.TKTEntity;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.TextUtils;

public class LocalComplexProvider extends BasicProvider {
    private static final String TAG = "LocalComplexProvider";
    private static Provider provider = null;
    private Context mContext = null;
    private Provider mSystemAccountProvider;
    private Provider sqlite3 = null;

    private LocalComplexProvider(Context context) {
        this.mContext = context;
        this.sqlite3 = LocalSqlite3Provider.get(context);
        if (TextUtils.equal(Config.getUacBrand(), "qiku")) {
            LOG.d(TAG, "LocalComplexProvider init qiku");
            this.mSystemAccountProvider = QikuSystemProvider.get(this.mContext);
        } else if (TextUtils.equal(Config.getUacBrand(), "coolpad")) {
            LOG.d(TAG, "LocalComplexProvider init coolcloud");
            this.mSystemAccountProvider = SettingsProvider.get(this.mContext);
        } else {
            LOG.e(TAG, "LocalComplexProvider init error unkonwn brand use default QIKUSystemProvider");
            this.mSystemAccountProvider = QikuSystemProvider.get(this.mContext);
        }
    }

    public static synchronized Provider get(Context context) {
        Provider provider;
        synchronized (LocalComplexProvider.class) {
            if (provider == null) {
                provider = new LocalComplexProvider(context.getApplicationContext());
            }
            provider = provider;
        }
        return provider;
    }

    public RTKTEntity getDefaultRTKT() {
        return getRTKT();
    }

    public RTKTEntity getRTKT() {
        return this.mSystemAccountProvider.getRTKT();
    }

    public boolean putRTKT(RTKTEntity entity) {
        boolean putRTKT;
        synchronized (this) {
            RTKTEntity persist = this.mSystemAccountProvider.getRTKT();
            if (!RTKTEntity.valid(persist)) {
                putRTKT = this.mSystemAccountProvider.putRTKT(entity);
            } else if (TextUtils.equal(persist.getUID(), entity.getUID())) {
                putRTKT = this.mSystemAccountProvider.putRTKT(entity);
            } else {
                putRTKT = true;
            }
        }
        return putRTKT;
    }

    public TKTEntity getTKT(String appId) {
        return this.sqlite3.getTKT(appId);
    }

    public boolean putTKT(String appId, TKTEntity entity) {
        return this.sqlite3.putTKT(appId, entity);
    }

    public boolean removeTKT(String appId) {
        return this.sqlite3.removeTKT(appId);
    }

    public boolean clearRTKT() {
        LOG.w(TAG, "[method:clearRTKT] unsupported, nothing to do");
        return true;
    }

    public boolean updatePwd(String user, String pwd) {
        LOG.w(TAG, "[method:updatePwd] unsupported, nothing to do");
        return true;
    }

    public boolean updateUser(String oldUser, String newUser) {
        return this.mSystemAccountProvider.updateUser(oldUser, newUser);
    }

    public boolean getMetaBoolean(String name, boolean defaultValue) {
        LOG.w(TAG, "[method:getMetaBoolean] unsupported, nothing to do");
        return true;
    }

    public boolean putMetaBoolean(String name, boolean value) {
        LOG.w(TAG, "[method:putMetaBoolean] unsupported, nothing to do");
        return true;
    }

    public boolean removeMetaBoolean(String name) {
        LOG.w(TAG, "[method:removeMetaBoolean] unsupported, nothing to do");
        return true;
    }

    public String getUserItem(String uid, String name) {
        LOG.w(TAG, "[method:getUserItem] unsupported, nothing to do");
        return null;
    }

    public boolean putUserItem(String uid, String name, String value) {
        LOG.w(TAG, "[method:putUserItem] unsupported, nothing to do");
        return true;
    }

    public boolean removeUserItem(String uid, String name) {
        LOG.w(TAG, "[method:removeUserItem] unsupported, nothing to do");
        return true;
    }

    public Bundle getUserInfo(String uid) {
        return this.mSystemAccountProvider.getUserInfo(uid);
    }

    public boolean putUserInfo(String uid, Bundle userInfo) {
        return this.mSystemAccountProvider.putUserInfo(uid, userInfo);
    }

    public boolean removeUserInfo(String uid) {
        LOG.w(TAG, "[method:removeUserInfo] unsupported, nothing to do");
        return true;
    }
}
