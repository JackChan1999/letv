package com.coolcloud.uac.android.common.provider;

import android.os.Bundle;
import com.coolcloud.uac.android.common.util.LOG;
import java.util.List;
import java.util.Set;

public class BasicProvider implements Provider {
    private static final String TAG = "BasicProvider";

    public RTKTEntity getDefaultRTKT() {
        return getRTKT();
    }

    public RTKTEntity syncRTKT() {
        LOG.w(TAG, "[method:syncRTKT] unsupported, nothing to do");
        return null;
    }

    public RTKTEntity getRTKT() {
        LOG.w(TAG, "[method:getRTKT] unsupported, nothing to do");
        return null;
    }

    public boolean putRTKT(RTKTEntity entity) {
        LOG.w(TAG, "[method:putRTKT] unsupported, nothing to do");
        return true;
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
        LOG.w(TAG, "[method:updateUser] unsupported, nothing to do");
        return true;
    }

    public TKTEntity getTKT(String appId) {
        LOG.w(TAG, "[method:getTKT] unsupported, nothing to do");
        return null;
    }

    public boolean putTKT(String appId, TKTEntity tkt) {
        LOG.w(TAG, "[method:putTKT] unsupported, nothing to do");
        return true;
    }

    public boolean removeTKT(String appId) {
        LOG.w(TAG, "[method:removeTKT] unsupported, nothing to do");
        return true;
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
        LOG.w(TAG, "[method:getUserInfo] unsupported, nothing to do");
        return null;
    }

    public boolean putUserInfo(String uid, Bundle userInfo) {
        LOG.w(TAG, "[method:putUserInfo] unsupported, nothing to do");
        return true;
    }

    public boolean removeUserInfo(String uid) {
        LOG.w(TAG, "[method:removeUserInfo] unsupported, nothing to do");
        return true;
    }

    public boolean putAccountHistory(String inputAccount, Bundle userInfo) {
        LOG.w(TAG, "[method:putAccountHistory] unsupported, nothing to do");
        return true;
    }

    public List<Bundle> getAccountHistory() {
        LOG.w(TAG, "[method:getAccountHistory] unsupported, nothing to do");
        return null;
    }

    public Set<String> getHistoryInputAccount() {
        LOG.w(TAG, "[method:getHistoryInputAccount] unsupported, nothing to do");
        return null;
    }
}
