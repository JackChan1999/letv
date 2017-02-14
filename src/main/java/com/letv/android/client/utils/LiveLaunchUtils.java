package com.letv.android.client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.BasePlayActivity;
import com.letv.android.client.controller.FloatingWindowPlayerController;
import com.letv.business.flow.live.PlayLiveFlow;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.http.LetvHttpConstant;
import com.letv.pp.utils.NetworkUtils;

public class LiveLaunchUtils {
    public LiveLaunchUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    private static void setClickForPlayPageTime() {
    }

    public static void launch(Context context, long aid, long vid, int from) {
        int launchMode;
        setClickForPlayPageTime();
        if (aid > 0) {
            launchMode = 2;
        } else {
            launchMode = 3;
        }
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", (int) aid);
        intent.putExtra("vid", (int) vid);
        intent.putExtra("from", from);
        intent.addFlags(536870912);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, 100);
        } else {
            context.startActivity(intent);
        }
    }

    public static void launch(Context context, long aid, long vid, long htime, String ref, String type, int from) {
        int launchMode;
        setClickForPlayPageTime();
        if (aid > 0) {
            launchMode = 2;
        } else {
            launchMode = 3;
        }
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", (int) aid);
        intent.putExtra("vid", (int) vid);
        intent.putExtra(PlayConstant.HTIME, htime);
        intent.putExtra(PlayConstant.REF, ref);
        intent.putExtra("type", type);
        intent.putExtra("from", from);
        intent.addFlags(536870912);
        LetvLogApiTool.createPlayLogInfo("VideoClickPlayStart", vid + " aid =" + aid, NetworkUtils.DELIMITER_LINE);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, 100);
        } else {
            context.startActivity(intent);
        }
    }

    public static void launch(Context context, long aid, long vid, int from, long seek) {
        int launchMode;
        setClickForPlayPageTime();
        if (aid > 0) {
            launchMode = 2;
        } else {
            launchMode = 3;
        }
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", (int) aid);
        intent.putExtra("vid", (int) vid);
        intent.putExtra("from", from);
        intent.putExtra("seek", seek);
        intent.addFlags(536870912);
        LetvLogApiTool.createPlayLogInfo("VideoClickPlayStart", vid + " aid =" + aid, NetworkUtils.DELIMITER_LINE);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launch(Context context, long aid, long vid, boolean isDolby, int from) {
        int launchMode;
        setClickForPlayPageTime();
        if (aid <= 0 || isDolby) {
            launchMode = 3;
        } else {
            launchMode = 2;
        }
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("aid", (int) aid);
        intent.putExtra("vid", (int) vid);
        intent.putExtra(FloatingWindowPlayerController.BUNDLE_PARAM_IS_DOLBY, isDolby);
        intent.putExtra("from", from);
        intent.addFlags(536870912);
        LetvLogApiTool.createPlayLogInfo("DolbyVideoClickPlayStart", vid + " aid =" + aid, NetworkUtils.DELIMITER_LINE);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchFocusPicLive(Context context, int from, String channelType, String streamId, String url, boolean isPay, String zhiboId, int allowVote, String partId) {
        setClickForPlayPageTime();
        LogInfo.log("clf", "channelType = " + channelType + " , streamId = " + streamId + " , url = " + url + " , isPay = " + isPay + " , zhiboId = " + zhiboId);
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            Intent intent = new Intent(context, BasePlayActivity.class);
            intent.putExtra(PlayConstant.PLAY_TYPE, 1);
            intent.putExtra("launchMode", LetvUtils.getLaunchMode(channelType));
            intent.putExtra("from", from);
            intent.putExtra(PlayConstant.FLOATINDEX, channelType);
            intent.putExtra("code", channelType);
            intent.putExtra(PlayConstant.LIVE_STREAMID, streamId);
            intent.putExtra("url", url);
            intent.putExtra(PlayConstant.LIVE_IS_PAY, isPay);
            intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, zhiboId);
            intent.putExtra(PlayConstant.LIVE_ALLOW_VOTE, allowVote);
            intent.putExtra(PlayConstant.LIVE_WATCHANDBUY_PARTID, partId);
            intent.addFlags(536870912);
            Bundle bundle = new Bundle();
            bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchFocusPicLive");
            bundle.putInt("from", from);
            bundle.putString("channelType", channelType);
            bundle.putString(PlayConstant.LIVE_STREAMID, streamId);
            bundle.putString("url", url);
            bundle.putBoolean("isPay", isPay);
            bundle.putString("zhiboId", zhiboId);
            bundle.putString(PlayConstant.LIVE_WATCHANDBUY_PARTID, partId);
            bundle.putInt(PlayConstant.LIVE_ALLOW_VOTE, allowVote);
            LetvApplication.getInstance().setLiveLunboBundle(bundle);
            LetvLogApiTool.createPlayLogInfo("LiveVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "streamId=" + streamId + " url=" + url);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
            return;
        }
        ToastUtils.showToast(context, 2131100495);
    }

    public static void launchLives4M(Context context, String liveType, boolean isPay, String zhiboId, boolean isHalf) {
        PlayLiveFlow.LogAddInfo("三方调用直播的入口方法", "liveType=" + liveType + ",isPay=" + isPay + ",zhiboId=" + zhiboId + ",isHalf=" + isHalf);
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            Intent intent = new Intent(context, BasePlayActivity.class);
            intent.putExtra(PlayConstant.PLAY_TYPE, 1);
            intent.putExtra("from", 20);
            intent.putExtra(PlayConstant.BACK, true);
            if (liveType == null || !liveType.startsWith("lb_")) {
                if ("lunbo".equals(liveType) || "weishi".equals(liveType)) {
                    intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, zhiboId);
                } else {
                    intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, zhiboId);
                }
                intent.putExtra("launchMode", LetvUtils.getLaunchMode(liveType));
            } else {
                intent.putExtra("launchMode", 101);
                intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, zhiboId);
            }
            intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
            intent.putExtra(PlayConstant.FLOATINDEX, liveType);
            intent.putExtra("code", liveType);
            intent.putExtra(PlayConstant.LIVE_IS_PAY, isPay);
            if (!isHalf) {
                intent.putExtra(PlayConstant.LIVE_FULL_ONLY, true);
            }
            intent.addFlags(536870912);
            Bundle bundle = new Bundle();
            bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLives4M");
            bundle.putString("liveType", liveType);
            bundle.putBoolean("isPay", isPay);
            bundle.putString("zhiboId", zhiboId);
            bundle.putBoolean("isHalf", isHalf);
            LetvApplication.getInstance().setLiveLunboBundle(bundle);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
            return;
        }
        ToastUtils.showToast(context, 2131100495);
    }

    public static void launchLiveLeso(Context context, String liveType, boolean isPay, String zhiboId, boolean isHalf) {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            Intent intent = new Intent(context, BasePlayActivity.class);
            intent.putExtra(PlayConstant.PLAY_TYPE, 1);
            intent.putExtra("launchMode", LetvUtils.getLaunchMode(liveType));
            intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
            intent.putExtra(PlayConstant.FLOATINDEX, liveType);
            intent.putExtra("code", liveType);
            intent.putExtra(PlayConstant.LIVE_IS_PAY, isPay);
            intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, zhiboId);
            if (!isHalf) {
                intent.putExtra(PlayConstant.LIVE_FULL_ONLY, true);
            }
            intent.addFlags(536870912);
            Bundle bundle = new Bundle();
            bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveLeso");
            bundle.putString("liveType", liveType);
            bundle.putBoolean("isPay", isPay);
            bundle.putString("zhiboId", zhiboId);
            bundle.putBoolean("isHalf", isHalf);
            LetvApplication.getInstance().setLiveLunboBundle(bundle);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
            return;
        }
        ToastUtils.showToast(context, 2131100495);
    }

    public static void launchLiveLesoLunbo(Context context, String programName, String channelId, String sourceId, boolean isFull, String cName, String eName) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        if (sourceId.equals("2") || sourceId.equals("9")) {
            intent.putExtra("launchMode", 102);
            intent.putExtra(PlayConstant.LIVE_CHANNEL_SIGNAL, sourceId);
        } else if (sourceId.equals("5") || sourceId.equals("7")) {
            intent.putExtra("launchMode", 101);
        }
        intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, programName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, channelId);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_NAME, cName);
        intent.putExtra("code", eName);
        intent.putExtra(PlayConstant.LIVE_FULL_ONLY, isFull);
        intent.putExtra("from", 18);
        intent.addFlags(536870912);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveLesoLunbo");
        bundle.putString(PlayConstant.LIVE_PROGRAM_NAME, programName);
        bundle.putString(PlayConstant.LIVE_CHANNEL_ID, channelId);
        bundle.putString(PlayConstant.LIVE_CHANNEL_NAME, cName);
        bundle.putString("code", eName);
        bundle.putString(PlayConstant.LIVE_SOURCE_ID, sourceId);
        bundle.putBoolean(PlayConstant.LIVE_FULL_ONLY, isFull);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveLunbo(Context context, String eName, boolean isLow, String programName, String cName, String channelId, String channelNum, boolean isFull) {
        int i;
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        Bundle bundle = LetvApplication.getInstance().getLiveLunboBundle();
        int launchMode = bundle == null ? -1 : bundle.getInt("launchMode", -1);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        String str = "launchMode";
        if (launchMode == -1) {
            i = 101;
        } else {
            i = launchMode;
        }
        intent.putExtra(str, i);
        intent.putExtra("code", eName);
        intent.putExtra(PlayConstant.LIVE_IS_LOW, isLow);
        intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, programName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_NAME, cName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, channelId);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER, channelNum);
        intent.putExtra(PlayConstant.LIVE_FULL_ONLY, isFull);
        intent.putExtra("from", 18);
        intent.addFlags(536870912);
        bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveLunbo");
        bundle.putString("code", eName);
        bundle.putString(PlayConstant.LIVE_PROGRAM_NAME, programName);
        bundle.putString(PlayConstant.LIVE_CHANNEL_NAME, cName);
        bundle.putString(PlayConstant.LIVE_CHANNEL_ID, channelId);
        bundle.putString(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER, channelNum);
        bundle.putBoolean(PlayConstant.LIVE_FULL_ONLY, isFull);
        bundle.putInt("launchMode", launchMode);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        LetvLogApiTool.createPlayLogInfo("LiveLunboVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "ename=" + eName + " programName=" + programName + " isLow=" + isLow);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveLunbo(Context context, int launchMode, String eName, boolean isLow, String programName, String cName, String channelId, String channelNum, boolean isFull) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", launchMode);
        intent.putExtra("code", eName);
        intent.putExtra(PlayConstant.LIVE_IS_LOW, isLow);
        intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, programName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_NAME, cName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, channelId);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER, channelNum);
        intent.putExtra(PlayConstant.LIVE_FULL_ONLY, isFull);
        intent.putExtra("from", 18);
        intent.addFlags(536870912);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveLunbo");
        bundle.putString("code", eName);
        bundle.putString(PlayConstant.LIVE_PROGRAM_NAME, programName);
        bundle.putString(PlayConstant.LIVE_CHANNEL_NAME, cName);
        bundle.putString(PlayConstant.LIVE_CHANNEL_ID, channelId);
        bundle.putString(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER, channelNum);
        bundle.putBoolean(PlayConstant.LIVE_FULL_ONLY, isFull);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        LetvLogApiTool.createPlayLogInfo("LiveLunboVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "ename=" + eName + " programName=" + programName + " isLow=" + isLow);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveWeishi(Context context, String eName, boolean isLow, String programName, String cName, String channelId, boolean isFull) {
        launchLiveWeishi(context, eName, isLow, programName, cName, channelId, "2", isFull);
    }

    public static void launchLiveWeishi(Context context, String eName, boolean isLow, String programName, String cName, String channelId, String signal, boolean isFull) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", 102);
        intent.putExtra("code", eName);
        intent.putExtra(PlayConstant.LIVE_IS_LOW, isLow);
        intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, programName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_NAME, cName);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, channelId);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_SIGNAL, signal);
        intent.putExtra(PlayConstant.LIVE_FULL_ONLY, isFull);
        intent.addFlags(536870912);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveWeishi");
        bundle.putString("eName", eName);
        bundle.putBoolean("isLow", isLow);
        bundle.putString(Field.PROGRAMNAME, programName);
        bundle.putString("cName", cName);
        bundle.putString("channelId", channelId);
        bundle.putBoolean("isFull", isFull);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        LetvLogApiTool.createPlayLogInfo("LiveLunboVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "ename=" + eName + " programName=" + programName + " isLow=" + isLow);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveSports(Context context, String pName, String id) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", 103);
        intent.putExtra("code", "sports");
        intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, id);
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
        intent.putExtra(PlayConstant.FLOATINDEX, "sports");
        if (!TextUtils.isEmpty(pName)) {
            intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, pName);
        }
        intent.addFlags(536870912);
        LetvLogApiTool.createPlayLogInfo("LiveSportsVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveSports");
        bundle.putString("pName", pName);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveEntertainment(Context context, String pName, String id) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", 104);
        intent.putExtra("code", "ent");
        if (!TextUtils.isEmpty(pName)) {
            intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, pName);
        }
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
        intent.putExtra(PlayConstant.FLOATINDEX, "ent");
        intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, id);
        intent.addFlags(536870912);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveEntertainment");
        bundle.putString("pName", pName);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        LetvLogApiTool.createPlayLogInfo("LiveEntertainmentVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveMusic(Context context, String pName, String id) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", 105);
        intent.putExtra("code", "music");
        if (!TextUtils.isEmpty(pName)) {
            intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, pName);
        }
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
        intent.putExtra(PlayConstant.FLOATINDEX, "music");
        intent.addFlags(536870912);
        intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, id);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveMusic");
        bundle.putString("pName", pName);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        LetvLogApiTool.createPlayLogInfo("LiveMusicVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchLiveOther(Context context, String pName) {
        setClickForPlayPageTime();
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", LiveType.PLAY_LIVE_OTHER);
        intent.putExtra("code", "other");
        if (!TextUtils.isEmpty(pName)) {
            intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, pName);
        }
        intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
        intent.putExtra(PlayConstant.FLOATINDEX, "other");
        intent.addFlags(536870912);
        Bundle bundle = new Bundle();
        bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveOther");
        bundle.putString("pName", pName);
        LetvApplication.getInstance().setLiveLunboBundle(bundle);
        LetvLogApiTool.createPlayLogInfo("LiveOtherVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    private static Intent createZhibotingIntent(Context context, String channelType, String streamId_350, String url_350, String streamId_1000, String url_1000, String programName, int from, String zhiboId) {
        Intent intent = new Intent(context, BasePlayActivity.class);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", LetvUtils.getLaunchMode(channelType));
        intent.putExtra("code", channelType);
        intent.putExtra(PlayConstant.LIVE_STREAMID_350, streamId_350);
        intent.putExtra(PlayConstant.LIVE_URL_350, url_350);
        intent.putExtra(PlayConstant.LIVE_STREAMID_1000, streamId_1000);
        intent.putExtra(PlayConstant.LIVE_URL_1000, url_1000);
        intent.putExtra(PlayConstant.LIVE_ID, zhiboId);
        intent.putExtra(PlayConstant.LIVE_PROGRAM_NAME, programName);
        intent.putExtra("from", from);
        return intent;
    }

    public static void launchLiveZhiboting(Context context, String channelType, String streamId_350, String url_350, String streamId_1000, String url_1000, String programName, int from, String zhiboId, Bundle payBundle) {
        setClickForPlayPageTime();
        LogInfo.log("pay_", "launchLiveZhiboting channelType = " + channelType + " , streamId_350 = " + streamId_350 + " , url_350 = " + url_350 + " , streamId_1000 = " + streamId_1000 + " , url_1000 = " + url_1000 + " , programName = " + programName + " , zhiboId = " + zhiboId);
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            Intent intent = createZhibotingIntent(context, channelType, streamId_350, url_350, streamId_1000, url_1000, programName, from, zhiboId);
            if (payBundle != null) {
                intent.putExtra(PlayConstant.BUNDLE_KEY_YC_PARAM, payBundle);
            }
            intent.putExtra(PlayConstant.FLOATPAGEINDEX, "8");
            intent.putExtra(PlayConstant.FLOATINDEX, channelType);
            intent.addFlags(536870912);
            LetvLogApiTool.createPlayLogInfo("LiveZhibotingVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "channelType=" + channelType + " streamId_350=" + streamId_350 + " url_350=" + url_350 + " streamId_1000=" + streamId_1000 + " url_1000=" + url_1000 + " programName=" + programName);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            Bundle bundle = new Bundle();
            bundle.putString(PlayConstant.LIVE_LAUCH_METHOD, "launchLiveZhiboting");
            bundle.putString("channelType", channelType);
            bundle.putString(PlayConstant.LIVE_STREAMID_350, streamId_350);
            bundle.putString(PlayConstant.LIVE_URL_350, url_350);
            bundle.putString(PlayConstant.LIVE_STREAMID_1000, streamId_1000);
            bundle.putString(PlayConstant.LIVE_URL_1000, url_1000);
            bundle.putString(Field.PROGRAMNAME, programName);
            bundle.putInt("from", from);
            bundle.putString("zhiboId", zhiboId);
            bundle.putBundle("payBundle", payBundle);
            LetvApplication.getInstance().setLiveLunboBundle(bundle);
            context.startActivity(intent);
            return;
        }
        ToastUtils.showToast(context, 2131100495);
    }

    public static void launchLivePush(Context context, String channel_ename, String cid, String endTime) {
        setClickForPlayPageTime();
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            Intent intent = new Intent(context, BasePlayActivity.class);
            intent.putExtra(PlayConstant.PLAY_TYPE, 1);
            intent.putExtra("launchMode", 112);
            intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, channel_ename);
            intent.putExtra("cid", cid);
            intent.putExtra("liveEndDate", endTime);
            intent.putExtra(PlayConstant.LIVE_FULL_ONLY, true);
            intent.addFlags(536870912);
            LetvLogApiTool.createPlayLogInfo("LivePushVideoClickPlayStart", NetworkUtils.DELIMITER_LINE, "channel_ename=" + channel_ename + " cid=" + cid + " endTime=" + endTime);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
            return;
        }
        ToastUtils.showToast(context, 2131100495);
    }

    public static void launchLiveById(Context context, String zhiboId) {
        LogInfo.log(LetvHttpConstant.LOG, "Baseplayactivity launchLiveById zhiboId = " + zhiboId);
        launchLiveById(context, zhiboId, false, false, -1);
    }

    public static void launchLiveById(Context context, String zhiboId, boolean selectLowHigh, boolean isLow, int from) {
        if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            Intent intent = new Intent(context, BasePlayActivity.class);
            intent.putExtra(PlayConstant.PLAY_TYPE, 1);
            intent.putExtra("launchMode", 112);
            intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, zhiboId);
            intent.putExtra(PlayConstant.LIVE_FULL_ONLY, true);
            intent.putExtra("from", from);
            if (selectLowHigh) {
                intent.putExtra(PlayConstant.LIVE_IS_LOW, isLow);
            }
            intent.addFlags(536870912);
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
            return;
        }
        ToastUtils.showToast(context.getString(2131100495));
    }

    public static void lauchPanoramaVideo(Context context, String id, boolean isLow, VideoType videoType) {
        Intent intent = new Intent(context, BasePlayActivity.class);
        LogInfo.log("clf", "lauchPanoramaVideo...id=" + id);
        intent.putExtra(PlayConstant.PLAY_TYPE, 1);
        intent.putExtra("launchMode", 112);
        intent.putExtra(PlayConstant.LIVE_FULL_ONLY, true);
        intent.putExtra(PlayConstant.VIDEO_TYPE, videoType);
        intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, id);
        intent.putExtra(PlayConstant.LIVE_IS_LOW, isLow);
        intent.addFlags(536870912);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static boolean checkJumpLive(Activity activity, boolean isJumpToPip) {
        Bundle bundle = LetvApplication.getInstance().getLiveLunboBundle();
        if (bundle == null || isJumpToPip) {
            return false;
        }
        String live_launch = bundle.getString(PlayConstant.LIVE_LAUCH_METHOD);
        if (!TextUtils.isEmpty(live_launch)) {
            if (live_launch.equals("launchFocusPicLive")) {
                launchFocusPicLive(activity, bundle.getInt("from"), bundle.getString("channelType"), bundle.getString(PlayConstant.LIVE_STREAMID), bundle.getString("url"), bundle.getBoolean("isPay"), bundle.getString("zhiboId"), bundle.getInt(PlayConstant.LIVE_ALLOW_VOTE, 0), bundle.getString(PlayConstant.LIVE_WATCHANDBUY_PARTID));
            } else if (live_launch.equals("launchLives4M")) {
                launchLives4M(activity, bundle.getString("liveType"), bundle.getBoolean("isPay"), bundle.getString("zhiboId"), bundle.getBoolean("isHalf"));
            } else if (live_launch.equals("launchLiveLeso")) {
                launchLiveLeso(activity, bundle.getString("liveType"), bundle.getBoolean("isPay"), bundle.getString("zhiboId"), bundle.getBoolean("isHalf"));
            } else if (live_launch.equals("launchLiveLesoLunbo")) {
                launchLiveLesoLunbo(activity, bundle.getString(PlayConstant.LIVE_PROGRAM_NAME), bundle.getString(PlayConstant.LIVE_CHANNEL_ID), bundle.getString(PlayConstant.LIVE_SOURCE_ID), bundle.getBoolean("isHalf"), bundle.getString(PlayConstant.LIVE_CHANNEL_NAME), bundle.getString("code"));
            } else if (live_launch.equals("launchLiveLunbo")) {
                launchLiveLunbo(activity, bundle.getString("code"), false, bundle.getString(PlayConstant.LIVE_PROGRAM_NAME), bundle.getString(PlayConstant.LIVE_CHANNEL_NAME), bundle.getString(PlayConstant.LIVE_CHANNEL_ID), bundle.getString(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER), bundle.getBoolean(PlayConstant.LIVE_FULL_ONLY));
            } else if (live_launch.equals("launchLiveWeishi")) {
                launchLiveWeishi(activity, bundle.getString("eName"), bundle.getBoolean("isLow"), bundle.getString(Field.PROGRAMNAME), bundle.getString("cName"), bundle.getString("channelId"), bundle.getBoolean("isFull"));
            } else if (live_launch.equals("launchLiveSports")) {
                launchLiveSports(activity, bundle.getString("pName"), "");
            } else if (live_launch.equals("launchLiveEntertainment")) {
                launchLiveEntertainment(activity, bundle.getString("pName"), "");
            } else if (live_launch.equals("launchLiveMusic")) {
                launchLiveMusic(activity, bundle.getString("pName"), "");
            } else if (live_launch.equals("launchLiveMusic")) {
                launchLiveMusic(activity, bundle.getString("pName"), "");
            } else if (live_launch.equals("launchLiveOther")) {
                launchLiveOther(activity, bundle.getString("pName"));
            } else if (live_launch.equals("launchLiveZhiboting")) {
                launchLiveZhiboting(activity, bundle.getString("channelType"), bundle.getString(PlayConstant.LIVE_STREAMID_350), bundle.getString(PlayConstant.LIVE_URL_350), bundle.getString(PlayConstant.LIVE_STREAMID_1000), bundle.getString(PlayConstant.LIVE_URL_1000), bundle.getString(Field.PROGRAMNAME), bundle.getInt("from"), bundle.getString("zhiboId"), bundle.getBundle("payBundle"));
            }
        }
        activity.finish();
        return true;
    }
}
