package com.letv.android.client.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.letv.android.client.receiver.LetvLiveReceiver;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LiveApi;
import com.letv.core.bean.LiveBookProgramList;
import com.letv.core.bean.LiveBookProgramList.LiveBookProgram;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.PushBookLive;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.core.utils.StringUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class LetvLiveBookUtil {
    public LetvLiveBookUtil() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static boolean createClock(Context context) {
        long time = DBManager.getInstance().getLiveBookTrace().getNearestTrace();
        if (time == -1 || time <= System.currentTimeMillis()) {
            return false;
        }
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(0, time - 300000, PendingIntent.getBroadcast(context, LetvConstant.LETV_LIVEBOOK_CODE, new Intent(context, LetvLiveReceiver.class), 0));
        return true;
    }

    public static void closeClock(Context context) {
        LogInfo.log("clf", "closeClock....");
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(PendingIntent.getBroadcast(context, LetvConstant.LETV_LIVEBOOK_CODE, new Intent(context, LetvLiveReceiver.class), 0));
    }

    public static void updateLiveBook(Context context) {
        if (PreferencesManager.getInstance().isUpdateLiveBook()) {
            new LetvRequest().setUrl(LiveApi.getInstance().getBookLiveList(Global.DEVICEID)).setCache(new VolleyNoCache()).setCallback(new 1(context));
        }
    }

    public static void comparisonLiveBook(Context context, LiveBookProgramList liveBookPrograms) {
        if (liveBookPrograms != null) {
            int i;
            closeClock(context);
            ArrayList<String> tempMd5s = new ArrayList();
            for (i = 0; i < liveBookPrograms.size(); i++) {
                LiveBookProgram mLiveBookProgram = (LiveBookProgram) liveBookPrograms.get(i);
                tempMd5s.add(MD5.toMd5(mLiveBookProgram.programName + mLiveBookProgram.code + mLiveBookProgram.play_time));
                LogInfo.log("clf", "尝试同步预定节目 " + mLiveBookProgram.programName + " , 默认卫视台id " + 102);
                DBManager.getInstance().getLiveBookTrace().saveLiveBookTrace(mLiveBookProgram.programName, mLiveBookProgram.channelName, mLiveBookProgram.code, mLiveBookProgram.play_time, StringUtils.getTimeLong(mLiveBookProgram.play_time), mLiveBookProgram.id, 102);
            }
            ArrayList<PushBookLive> pushBookLives = DBManager.getInstance().getLiveBookTrace().getAllTrace();
            if (pushBookLives != null && pushBookLives.size() > 0) {
                for (i = 0; i < pushBookLives.size(); i++) {
                    if (!tempMd5s.contains(((PushBookLive) pushBookLives.get(i)).md5_id)) {
                        DBManager.getInstance().getLiveBookTrace().remove(((PushBookLive) pushBookLives.get(i)).md5_id);
                    }
                }
            }
            createClock(context);
            PreferencesManager.getInstance().setIsUpdateLiveBook(false);
        }
    }

    public static String getBookId(LiveRemenBaseBean bean) {
        if (bean == null) {
            return "";
        }
        String programName = bean.title;
        String liveType = bean.liveType;
        return com.letv.component.utils.MD5.toMd5(programName + liveType + StringUtils.formatBookTime(bean.getFullPlayDate() + " " + bean.getPlayTime()));
    }

    public static void bookLiveProgram(Context context, String programName, String channelName, String code, String play_time, String id, int launchMode) {
        DBManager.getInstance().getLiveBookTrace().saveLiveBookTrace(programName, channelName, code, play_time, StringUtils.getTimeLong(play_time), id, launchMode);
        createClock(context);
    }

    public static void bookLiveProgram(Context context, String programName, String channelName, String code, String play_time, String id) {
        String str = programName;
        String str2 = channelName;
        String str3 = code;
        String str4 = play_time;
        DBManager.getInstance().getLiveBookTrace().saveLiveBookTrace(str, str2, str3, str4, StringUtils.getTimeLong(play_time), id, LetvUtils.getLaunchMode(code));
        createClock(context);
    }

    public static void cancelBookLiveProgram(Context context, String programName, String channelName, String code, String play_time) {
        DBManager.getInstance().getLiveBookTrace().remove(programName, channelName, code, play_time);
        createClock(context);
    }

    public static boolean hasBookLiveProgram(String programName, String channelName, String code, String play_time) {
        return DBManager.getInstance().getLiveBookTrace().hasLiveBookTrace(MD5.toMd5(programName + code + play_time));
    }
}
