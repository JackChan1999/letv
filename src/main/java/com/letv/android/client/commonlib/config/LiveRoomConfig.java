package com.letv.android.client.commonlib.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.core.constant.PlayConstant;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.pp.utils.NetworkUtils;

public class LiveRoomConfig {
    public static String BASE_PLAY_ACTIVITY_ACTION = "android.intent.action.BasePlayActivity";

    public static void launchLives(Context context, String code, String streamId, String url, boolean full, int from, String programName) {
        LogInfo.log("live_", "---------launchLives");
        Intent intent = new Intent();
        intent.setAction(BASE_PLAY_ACTIVITY_ACTION);
        intent.putExtra("launchMode", LetvUtils.getLaunchMode(code));
        LogInfo.log("live", "launchLives code = " + code + " , streamId = " + streamId + " , url = " + url + " , full = " + full);
        intent.putExtra("from", from);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
        intent.putExtra(PlayConstant.FLOATINDEX, code);
        intent.putExtra("code", code);
        intent.putExtra(PlayConstant.LIVE_STREAMID, streamId);
        intent.putExtra("url", url);
        intent.putExtra(PlayConstant.LIVE_FULL_ONLY, full);
        if (!TextUtils.isEmpty(programName)) {
            intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, programName);
        }
        intent.addFlags(536870912);
        LetvLogApiTool.createPlayLogInfo("LiveVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "streamId=" + streamId + " full=" + full + " url=" + url);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLivePushWeishi(Context context, String ename, String channelId, boolean isLow, int from) {
        Intent intent = new Intent();
        intent.setAction(BASE_PLAY_ACTIVITY_ACTION);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", 102);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, channelId);
        intent.putExtra("code", ename);
        intent.putExtra(PlayConstant.LIVE_IS_LOW, isLow);
        intent.putExtra("from", from);
        intent.addFlags(536870912);
        LetvLogApiTool.createPlayLogInfo("LiveWeishiVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "ename=" + ename + " isLow=" + isLow);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }
}
