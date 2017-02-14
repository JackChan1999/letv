package com.letv.core.utils;

import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.constant.PlayConstant.LiveType;
import java.util.concurrent.ArrayBlockingQueue;

public class LiveLunboUtils {
    public static ArrayBlockingQueue<LiveBeanLeChannel> mChannelQueue = new ArrayBlockingQueue(10);

    public static String getChannelDBType(int index) {
        String channelDbType = "lunbo";
        switch (index) {
            case 1:
                return "lunbo";
            case 2:
                return "weishi";
            case 13:
                return "hk_movie";
            case 14:
                return "hk_tvseries";
            case 15:
                return "hk_variety";
            case 16:
                return "hk_music";
            case 17:
                return "hk_sports";
            default:
                return channelDbType;
        }
    }

    public static boolean isLunBoWeiShiType(int index) {
        switch (index) {
            case 1:
            case 2:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                return true;
            default:
                return false;
        }
    }

    public static String getChannelDBTypeFromLaunchMode(int index) {
        String channelDbType = "lunbo";
        switch (index) {
            case 101:
                return "lunbo";
            case 102:
                return "weishi";
            case 115:
                return "hk_movie";
            case 116:
                return "hk_tvseries";
            case 117:
                return "hk_variety";
            case LiveType.PLAY_LIVE_HK_MUSIC /*118*/:
                return "hk_music";
            case 119:
                return "hk_sports";
            default:
                return channelDbType;
        }
    }

    public static int getPageIndexFromLaunchMode(int launchMode) {
        switch (launchMode) {
            case 101:
                return 1;
            case 102:
                return 2;
            case 103:
                return 3;
            case 104:
                return 5;
            case 105:
                return 4;
            case 106:
                return 7;
            case 107:
                return 8;
            case 108:
                return 9;
            case 109:
                return 6;
            case LiveType.PLAY_LIVE_OTHER /*110*/:
                return 10;
            case 111:
                return 11;
            case 114:
                return 12;
            case 115:
                return 13;
            case 116:
                return 14;
            case 117:
                return 15;
            case LiveType.PLAY_LIVE_HK_MUSIC /*118*/:
                return 16;
            case 119:
                return 17;
            default:
                return 1;
        }
    }

    public static String getChannelType(int index) {
        String channelType = "1";
        switch (index) {
            case 1:
                return String.valueOf(1);
            case 2:
                return String.valueOf(2);
            case 13:
                return String.valueOf(13);
            case 14:
                return String.valueOf(14);
            case 15:
                return String.valueOf(15);
            case 16:
                return String.valueOf(16);
            case 17:
                return String.valueOf(17);
            default:
                return channelType;
        }
    }

    public static int getLaunchMode(int index) {
        if (index == 1) {
            return 101;
        }
        if (index == 13) {
            return 115;
        }
        if (index == 14) {
            return 116;
        }
        if (index == 15) {
            return 117;
        }
        if (index == 16) {
            return LiveType.PLAY_LIVE_HK_MUSIC;
        }
        if (index == 17) {
            return 119;
        }
        return 101;
    }

    public static boolean isLunboType(int launchMode) {
        switch (launchMode) {
            case 101:
            case 115:
            case 116:
            case 117:
            case LiveType.PLAY_LIVE_HK_MUSIC /*118*/:
            case 119:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLunboIndex(int index) {
        switch (index) {
            case 1:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                return true;
            default:
                return false;
        }
    }

    public static int getRealChannelType(int channelType) {
        if (LetvUtils.isInHongKong()) {
            return 1;
        }
        return channelType;
    }
}
