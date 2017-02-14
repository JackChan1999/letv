package com.letv.itv.threenscreen.utils;

import android.os.Bundle;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.itv.threenscreen.service.AIDLActivity;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreenScreenManager {
    static Client client;
    static ExecutorService executorService = Executors.newFixedThreadPool(100000);

    static {
        Map<String, String> users = UserGenerator.generat(0.1f);
        for (String uName : users.keySet()) {
            String clientType = (String) users.get(uName);
            client = new Client(uName, UserGenerator.getClientNameByType(clientType), clientType);
        }
    }

    public static void queryDeviceList(int requestId, Bundle bundle, AIDLActivity cb) {
        client.setCallback(cb);
        client.setRequestId(requestId);
        client.setUserId(bundle.getString(UserInfoDb.USER_ID));
        client.setVer(bundle.getString("version"));
        client.setDeviceName(bundle.getString("deviceName"));
        client.setBrandCode(bundle.getString("brandCode"));
        client.setSeriesCode(bundle.getString("seriesCode"));
        client.setMac(bundle.getString("mac"));
        client.setVideoId(bundle.getString("videoId"));
        client.setVideoTitle(bundle.getString("title"));
        client.setVideoTime(bundle.getString("time"));
        submitTast(new UserBehaviorB(client, "duoping.go.letv.com", Integer.valueOf(8080)));
    }

    public static void sendPush(int requestId, Bundle bundle, String deid, AIDLActivity cb) {
        client.setCallback(cb);
        client.setRequestId(requestId);
        client.setUserId(bundle.getString(UserInfoDb.USER_ID));
        client.setVer(bundle.getString("version"));
        client.setDeviceName(bundle.getString("deviceName"));
        client.setBrandCode(bundle.getString("brandCode"));
        client.setSeriesCode(bundle.getString("seriesCode"));
        client.setMac(bundle.getString("mac"));
        client.setVideoId(bundle.getString("videoId"));
        client.setAlbumId(bundle.getString(PageJumpUtil.IN_TO_ALBUM_PID));
        client.setVideoTitle(bundle.getString("title"));
        client.setVideoTime(bundle.getString("time"));
        submitTast(new UserBehaviorC(client, "duoping.go.letv.com", Integer.valueOf(8080), deid));
    }

    public static void cancel() {
        client.quit();
    }

    private static void submitTast(UserBehavior userBehavior) {
        executorService.submit(new ThreadTask(userBehavior));
    }
}
