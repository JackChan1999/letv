package com.letv.redpacketsdk.net;

import android.content.Context;
import android.text.TextUtils;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.RedPacketSharePreferences;
import com.letv.redpacketsdk.utils.LogInfo;
import com.letv.redpacketsdk.utils.MD5;
import com.letv.redpacketsdk.utils.MobileUtil;

public class HTTPURL {
    private static String OPEN_RED_PACKET = "http://package.my.letv.com/spring/package/?";
    private static String OPEN_URL_ONLINE = "http://www.letv.com/cmsdata/block/5636.json";
    private static String OPEN_URL_TEST = "http://www.letv.com/cmsdata/block/5635.json";
    private static String PREVIEW_URL_ONLINE = "http://www.letv.com/cmsdata/block/5645.json";
    private static String PREVIEW_URL_TEST = "http://www.letv.com/cmsdata/block/5644.json";
    public static String REDPACKET_LIST_BASE = "http://package.my.letv.com/spring/bind/";
    private static String STATISTICS_BASE_ONLINE = "http://apple.www.letv.com/op/?";
    private static String STATISTICS_BASE_TEST = "http://develop.bigdata.letv.com/0gg6/op/?";

    public static String getPreviewUrl() {
        return RedPacketSdkManager.getInstance().getIsOnline() ? PREVIEW_URL_ONLINE : PREVIEW_URL_TEST;
    }

    public static String getOpenUrl() {
        return RedPacketSdkManager.getInstance().getIsOnline() ? OPEN_URL_ONLINE : OPEN_URL_TEST;
    }

    public static String getStatisticsBase() {
        return RedPacketSdkManager.getInstance().getIsOnline() ? STATISTICS_BASE_ONLINE : STATISTICS_BASE_TEST;
    }

    public static String getOpenRedPacketUrl(Context context) {
        String url = OPEN_RED_PACKET;
        String activeId = "";
        if (RedPacketSdkManager.getInstance().hasRedPacket()) {
            activeId = RedPacketSdkManager.getInstance().getRedPacketBean().giftId;
        }
        String deviceId = MobileUtil.generateDeviceId(context);
        String appId = RedPacketSdkManager.getInstance().getAppId();
        String time = System.currentTimeMillis() + "";
        String ap = "";
        RedPacketSharePreferences sp = RedPacketSharePreferences.getInstance();
        if (!sp.hasOpenedThisRedPacket()) {
            sp.setHasOpenRedPacketId();
        } else if (sp.getHasShared()) {
            ap = "2";
            sp.setPassRedPacket();
        }
        url = url + "activeId=" + activeId + "&deviceId=" + deviceId + "&appId=" + appId + "&time=" + time + "&auth=" + MD5.toMd5(activeId + appId + deviceId + time + ap + "springPackage");
        if (!TextUtils.isEmpty(ap)) {
            url = url + "&ap=" + ap;
        }
        LogInfo.log("HTTPURL", "getOpenRedPacketUrl=" + url);
        return url;
    }
}
