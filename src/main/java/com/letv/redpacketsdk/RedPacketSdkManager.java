package com.letv.redpacketsdk;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.letv.redpacketsdk.bean.EntryLocation;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.bean.ShareBean;
import com.letv.redpacketsdk.net.HTTPURL;
import com.letv.redpacketsdk.service.RedPacketListPollingService;
import com.letv.redpacketsdk.utils.LogInfo;
import org.json.JSONArray;

public class RedPacketSdkManager {
    private static RedPacketSdkManager instance = new RedPacketSdkManager();
    private final String SDK_VERSION = "2.0";
    private String mAppId;
    private Application mApplication;
    private String mApprunid;
    private String mDeviceId;
    private RedPacketBean mForecastBean = new RedPacketBean();
    private boolean mIsOnline = false;
    private RedPacketBean mRedPacketBean = new RedPacketBean();
    private JSONArray mRedPacketList;
    private String mToken;
    private String mUid;

    private RedPacketSdkManager() {
    }

    public static RedPacketSdkManager getInstance() {
        return instance;
    }

    public void init(Application application, String deviceId, String appId, String uid, String token) {
        LogInfo.log("RedPacketSdkManager", "init");
        this.mApplication = application;
        this.mDeviceId = deviceId;
        this.mAppId = appId;
        this.mUid = uid;
        this.mToken = token;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public void setAppRunid(String apprunid) {
        this.mApprunid = apprunid;
    }

    public String getUid() {
        return this.mUid;
    }

    public String getToken() {
        return this.mToken;
    }

    public String getAppRunId() {
        return this.mApprunid;
    }

    public RedPacketBean getRedPacketBean() {
        return this.mRedPacketBean;
    }

    public String getAppId() {
        return this.mAppId;
    }

    public String getDeviceId() {
        return this.mDeviceId;
    }

    public boolean getIsOnline() {
        return this.mIsOnline;
    }

    public void setIsOnline(boolean is) {
        this.mIsOnline = is;
    }

    public String getSdkVersion() {
        return "2.0";
    }

    public void setRedPacketList(JSONArray redPacketList) {
        LogInfo.log("RedPacketSdkManager", "setRedPacketList");
        this.mRedPacketList = redPacketList;
        if (redPacketList != null) {
            RedPacketListPollingService.start(this.mApplication);
        }
    }

    public JSONArray getRedPacketList() {
        return this.mRedPacketList;
    }

    public void setForecastBean(RedPacketBean bean) {
        this.mForecastBean = bean;
    }

    public RedPacketBean getForecastBean() {
        return this.mForecastBean;
    }

    public void setRedPacketBean(RedPacketBean redPacketBean) {
        if (isNeedToNotifyClient(this.mRedPacketBean, redPacketBean)) {
            this.mRedPacketBean = redPacketBean;
            if (hasRedPacket()) {
                RedPacketSdk.getInstance().notifyListener(true);
                return;
            } else {
                RedPacketSdk.getInstance().notifyListener(false);
                return;
            }
        }
        this.mRedPacketBean = redPacketBean;
    }

    public boolean isNeedToNotifyClient(RedPacketBean oldBean, RedPacketBean newBean) {
        boolean isNeed = false;
        if (hasRedPacket(oldBean) != hasRedPacket(newBean)) {
            isNeed = true;
        } else if (hasRedPacket(newBean) && !newBean.id.equals(oldBean.id)) {
            isNeed = true;
        }
        LogInfo.log("RedPacketSdkManager", "hasRedPacket = " + hasRedPacket(newBean) + " ; isNeedToNotifyClient = " + isNeed);
        return isNeed;
    }

    public boolean hasRedPacket() {
        LogInfo.log("RedPacketSdkManager", "hasRedPacket=" + hasRedPacket(this.mRedPacketBean));
        return hasRedPacket(this.mRedPacketBean);
    }

    public boolean hasRedPacket(RedPacketBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.id) || TextUtils.isEmpty(bean.giftId) || TextUtils.isEmpty(bean.startTime) || TextUtils.isEmpty(bean.endTime)) {
            return false;
        }
        return true;
    }

    public void setHasShared() {
        RedPacketSharePreferences.getInstance().setHasShared();
    }

    public String getGiftListBaseUrl() {
        return HTTPURL.REDPACKET_LIST_BASE;
    }

    public EntryLocation getEntryLocation() {
        if (hasRedPacket()) {
            return this.mRedPacketBean.entryLocation;
        }
        return null;
    }

    public Context getApplicationContext() {
        return this.mApplication;
    }

    public String getActivityDescribeUrl() {
        String url = "";
        if (hasRedPacket()) {
            return this.mRedPacketBean.actionUrl;
        }
        return url;
    }

    public String getNextRedPacketTime() {
        String time = "";
        if (hasRedPacket()) {
            return this.mRedPacketBean.nextRedPacketTime;
        }
        return time;
    }

    public ShareBean getShareInfo() {
        ShareBean shareBean = new ShareBean();
        if (hasRedPacket()) {
            return this.mRedPacketBean.shareBean;
        }
        return shareBean;
    }

    public void clean() {
        RedPacketListPollingService.stop(this.mApplication);
        this.mRedPacketBean = null;
    }
}
