package com.letv.core.constant;

import com.letv.core.utils.LetvUtils;

public class LoginConstant {
    public static final int ALBUM_REQUEST_CODE_IGNORE = 10000;
    public static final int ALBUM_REQUEST_CODE_PLAY_COMMENT = 10002;
    public static final int ALBUM_REQUEST_CODE_PLAY_LOGIN = 10001;
    public static final String COOLPAD_APP_ID = "2000003007";
    public static final int FORPLAY = 1;
    public static final int FORTS = 2;
    public static final String LETV_MALL_JIFEN_WEBSITE = "http://my.letv.com/jifen/app";
    public static final int LOGIN = 16;
    public static final int LOGIN_FAILURE = 0;
    public static final int LOGIN_FROM_COMMENT_STANDPOINT = 19;
    public static final int LOGIN_FROM_FAVORITE = 8;
    public static final int LOGIN_FROM_HOME = 17;
    public static final int LOGIN_FROM_RED_PACKET = 1886;
    public static final int LOGIN_FROM_VIP = 20;
    public static final int LOGIN_FROM_WO = 18;
    public static final int LOGIN_MY_INVISTORS = 5;
    public static final int LOGIN_MY_INVITATIONS = 4;
    public static final int LOGIN_MY_POINTS = 3;
    public static final int LOGIN_MY_REGISTRATION = 7;
    public static final int LOGIN_MY_UNICOM = 6;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_SUCCESS_FOR_TS = 2;
    public static final String MINE_AD_CMS_ID_ONLINE = "1632";
    public static final String MINE_AD_CMS_ID_TEST = "2959";

    public static boolean isHongKong() {
        return LetvUtils.isInHongKong();
    }
}
