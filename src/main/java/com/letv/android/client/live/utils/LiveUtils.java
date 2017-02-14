package com.letv.android.client.live.utils;

import android.content.Context;
import android.text.TextUtils;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LiveUtils {
    public static int[] hk_icons = new int[]{2130838231, 2130838234, 2130838232, 2130838233, 2130838235};
    public static int[] hk_names = new int[]{2131100266, 2131100267, 2131100271, 2131100272, 2131100268};
    public static int[] hk_pageIndexs = new int[]{13, 14, 16, 17, 15};
    public static int[] icons = new int[]{2130838224, 2130838233, 2130838236, 2130838226, 2130838230, 2130838228, 2130838232, 2130838225, 2130838235, 2130838229, 2130838227};
    public static int[] names = new int[]{2131100261, 2131100272, 2131100274, 2131100263, 2131100270, 2131100265, 2131100271, 2131100262, 2131100273, 2131100269, 2131100264};
    public static int[] pageIndexs = new int[]{0, 3, 2, 5, 1, 7, 4, 6, 11, 8, 9};
    private static SimpleDateFormat sdf_all = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf_date = new SimpleDateFormat("MM-dd");
    private static SimpleDateFormat sdf_fdate = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");

    public LiveUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static String getLiveChannelType(int pageIndex) {
        switch (pageIndex) {
            case 3:
                return "sports";
            case 4:
                return "music";
            case 5:
                return LiveRoomConstant.CHANNEL_TYPE_ENTERTAINMENT;
            case 6:
                return "brand";
            case 7:
                return "game";
            case 8:
                return "information";
            case 9:
                return "finance";
            case 10:
                return "other";
            case 11:
                return "variety";
            default:
                return null;
        }
    }

    public static int getIndexByChannelType(String type) {
        int index = -1;
        if (TextUtils.isEmpty(type)) {
            return -1;
        }
        if (type.equals("sports")) {
            index = 3;
        } else if (type.equals("ent") || type.equals(LiveRoomConstant.CHANNEL_TYPE_ENTERTAINMENT)) {
            index = 5;
        } else if (type.equals("music")) {
            index = 4;
        } else if (type.equals("variety")) {
            index = 11;
        } else if (type.equals("game")) {
            index = 7;
        } else if (type.equals("information")) {
            index = 8;
        } else if (type.equals("finance")) {
            index = 9;
        } else if (type.equals("brand")) {
            index = 6;
        } else if (type.equals("other")) {
            index = 10;
        } else if (type.equals("lunbo")) {
            index = 1;
        } else if (type.equals("weishi")) {
            index = 2;
        } else if (type.equals("hk_all")) {
            index = 12;
        } else if (type.equals("hk_movie")) {
            index = 13;
        } else if (type.equals("hk_tvseries")) {
            index = 14;
        } else if (type.equals("hk_variety")) {
            index = 15;
        } else if (type.equals("hk_music")) {
            index = 16;
        } else if (type.equals("hk_sports")) {
            index = 17;
        }
        return index;
    }

    public static int getIconByChannelType(String type) {
        int index = 2130838244;
        if (TextUtils.isEmpty(type)) {
            return 2130838244;
        }
        if (type.equals(LiveRoomConstant.LIVE_TYPE_SPORT)) {
            index = 2130838245;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_ENT)) {
            index = 2130838239;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_VARIETY)) {
            index = 2130838246;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_MUSIC)) {
            index = 2130838243;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_GAME)) {
            index = 2130838241;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_INFORMATION)) {
            index = 2130838242;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_FINANCE)) {
            index = 2130838240;
        } else if (type.equals(LiveRoomConstant.LIVE_TYPE_BRAND)) {
            index = 2130838238;
        }
        return index;
    }

    public static String getLiveChannelName(Context context, int pageIndex) {
        int i;
        if (LetvUtils.isInHongKong()) {
            for (i = 0; i < hk_pageIndexs.length; i++) {
                if (pageIndex == hk_pageIndexs[i]) {
                    return context.getResources().getString(hk_names[i]);
                }
            }
        } else {
            for (i = 0; i < pageIndexs.length; i++) {
                if (pageIndex == pageIndexs[i]) {
                    return context.getResources().getString(names[i]);
                }
            }
        }
        return "";
    }

    public static String getPeopleNum(Context context, int count) {
        String result = "";
        if (count < 10000) {
            return String.valueOf(count);
        }
        Object[] objArr = new Object[1];
        objArr[0] = String.format("%.1f", new Object[]{Float.valueOf(((float) count) / 10000.0f)});
        return context.getString(2131100935, objArr);
    }

    public static String parseDate(String date_all) {
        try {
            date_all = sdf_date.format(sdf_all.parse(date_all));
        } catch (ParseException e) {
            LogInfo.log("wxy", "error when parse date:" + date_all);
        }
        return date_all;
    }

    public static String parseFullDate(String date_all) {
        try {
            date_all = sdf_fdate.format(sdf_all.parse(date_all));
        } catch (ParseException e) {
            LogInfo.log("wxy", "error when parse date:" + date_all);
        }
        return date_all;
    }

    public static String parseTime(String date_all) {
        try {
            date_all = sdf_time.format(sdf_all.parse(date_all));
        } catch (ParseException e) {
            LogInfo.log("wxy", "error when parse time:" + date_all);
        }
        return date_all;
    }

    public static String parseYear(String date_all) {
        try {
            date_all = sdf_time.format(sdf_year.parse(date_all));
        } catch (ParseException e) {
            LogInfo.log("wxy", "error when parse time:" + date_all);
        }
        return date_all;
    }
}
