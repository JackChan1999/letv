package com.letv.core.constant;

import android.util.DisplayMetrics;
import com.letv.core.BaseApplication;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;

public class LetvConstant {
    public static final String ABLUT_US_URL = "http://m.letv.com/";
    public static final String AD_PublishId = "e084144fd78c42c5878e3277606f8b6e";
    public static final int CHANNEL_LIST_PAGE_SIZE = 30;
    public static final String CHAT_KEY = "4D2sP9BV7vCs8AL";
    public static final int DATA_NULL = 2;
    public static final String IRVIDEOUAID = "UA-letv-140001";
    public static final int LETV_LIVEBOOK_CODE = 250250250;
    public static final int LETV_QDACTIVITY_CODE = 250250251;
    public static String LETV_SHOP_URL = "http://shop.letv.com/";
    public static String LETV_UNINSTALL_URL = "http://upload.app.m.letv.com/android/static/uninstall_question.html?";
    public static final String MAPPTRACKERKEY = "440e9707b1c3669a";
    public static final int MAX_VIDEOSHOT_PIC_COUNT = 99;
    public static final String MIYUE = "a2915e518ba60169f77";
    public static final String MIYUE53 = "poi345";
    public static final String MIYUE_ATTENDANCE = "9cce0cb830bbd61b8b7408599034051a";
    public static final String MOBILE_SYSTEM_PHONE = "420003";
    public static final String MOBILE_SYSTEM_PHONE_JUMP = "420004";
    public static final String MOBILE_SYSTEM_PHONE_PAY = "141003";
    public static final int NET_ERROR = 1;
    public static final int NET_NO = 0;
    public static final long OVERDUE_TIME_IN_SECS = 86400000;
    public static final int PAGE_SIZE = 20;
    public static final int PLAY_FINISH = -1000;
    public static final int RECOMMEND_LIST_PAGE_SIZE = 30;
    public static final long REFRESH_TIME = 120000;
    public static final String STATUS_OK = "1";
    public static final int THREADPOOL_SIZE = 3;
    public static final String USER_PROTOCOL_URL = "http://sso.letv.com/user/protocol";
    public static final boolean USE_MEMORY = true;
    public static final boolean USE_SDCARD = true;
    public static final String VIDEO_TYPE_KEY_PREVIEW = "180002";
    public static final String VIDEO_TYPE_KEY_TIDBITSTS = "180003";
    public static final String VIDEO_TYPE_KEY_ZHENG_PIAN = "180001";
    public static final long VIP_OVERDUE_TIME = 86400;
    public static final int WIDGET_UPDATE_UI_TIME = 15000;
    private static boolean isForcePlay = false;
    public static final String retrievePwdPhoneNum = "10690228102921";
    public static final String retrieve_pwd_byemail_url = "http://sso.letv.com/user/backpwdemail";

    public class DialogMsgConstantId {
        public static final String BLOCK_SUGGEST_SWITCH_LOW = "100002";
        public static final String COMMENT_CSRF_ERROR = "100210";
        public static final String COMMENT_ERROR = "100201";
        public static final String COMMENT_FAIL = "100204";
        public static final String COMMENT_FAVOUR_LOGIN_GUIDE = "100212";
        public static final String COMMENT_FAVOUR_REPEAT = "700004";
        public static final String COMMENT_FORBIDIP = "100206";
        public static final String COMMENT_FORBIDUSER = "100207";
        public static final String COMMENT_LONG = "100203";
        public static final String COMMENT_MORE = "100208";
        public static final String COMMENT_NONE_VIDEOSHOT_NOTICE = "700082";
        public static final String COMMENT_NOTLOGGED = "100200";
        public static final String COMMENT_NULL_COMMENT = "700003";
        public static final String COMMENT_PREFER_VIDEOSHOT_NOTICE = "700083";
        public static final String COMMENT_REPEAT = "100209";
        public static final String COMMENT_SHORT = "100202";
        public static final String COMMENT_SUCCESS = "700002";
        public static final String COMMENT_TIME = "100205";
        public static final String CONSTANT_10000 = "10000";
        public static final String CONSTANT_10001 = "10001";
        public static final String CONSTANT_100016 = "100016";
        public static final String CONSTANT_10002 = "10002";
        public static final String CONSTANT_100022 = "100022";
        public static final String CONSTANT_100023 = "100023";
        public static final String CONSTANT_100026 = "100026";
        public static final String CONSTANT_100027 = "100027";
        public static final String CONSTANT_100035 = "100035";
        public static final String CONSTANT_100036 = "100036";
        public static final String CONSTANT_100037 = "100037";
        public static final String CONSTANT_100038 = "100038";
        public static final String CONSTANT_100058 = "100058";
        public static final String CONSTANT_100059 = "100059";
        public static final String CONSTANT_100061 = "100061";
        public static final String CONSTANT_100093 = "100093";
        public static final String CONSTANT_100094 = "100094";
        public static final String CONSTANT_100095 = "100095";
        public static final String CONSTANT_100096 = "100096";
        public static final String CONSTANT_100104 = "100104";
        public static final String CONSTANT_100105 = "100105";
        public static final String CONSTANT_100212 = "100212";
        public static final String CONSTANT_111101 = "111101";
        public static final String CONSTANT_111102 = "111102";
        public static final String CONSTANT_20001 = "20001";
        public static final String CONSTANT_20002 = "20002";
        public static final String CONSTANT_20003 = "20003";
        public static final String CONSTANT_200035 = "200035";
        public static final String CONSTANT_20004 = "20004";
        public static final String CONSTANT_20005 = "20005";
        public static final String CONSTANT_20006 = "20006";
        public static final String CONSTANT_20007 = "20007";
        public static final String CONSTANT_20008 = "20008";
        public static final String CONSTANT_20009 = "20009";
        public static final String CONSTANT_20024 = "20024";
        public static final String CONSTANT_20025 = "20025";
        public static final String CONSTANT_20026 = "20026";
        public static final String CONSTANT_20091 = "20091";
        public static final String CONSTANT_211012 = "211012";
        public static final String CONSTANT_211014 = "211014";
        public static final String CONSTANT_211015 = "211015";
        public static final String CONSTANT_2601 = "2601";
        public static final String CONSTANT_2701 = "2701";
        public static final String CONSTANT_2702 = "2702";
        public static final String CONSTANT_2703 = "2703";
        public static final String CONSTANT_2704 = "2704";
        public static final String CONSTANT_2705 = "2705";
        public static final String CONSTANT_30001 = "30001";
        public static final String CONSTANT_30002 = "30002";
        public static final String CONSTANT_30003 = "30003";
        public static final String CONSTANT_30004 = "30004";
        public static final String CONSTANT_30006 = "30006";
        public static final String CONSTANT_50001 = "50001";
        public static final String CONSTANT_50002 = "50002";
        public static final String CONSTANT_50102 = "50102";
        public static final String CONSTANT_50208 = "50208";
        public static final String CONSTANT_50301 = "50301";
        public static final String CONSTANT_50601 = "50601";
        public static final String CONSTANT_50602 = "50602";
        public static final String CONSTANT_50902 = "50902";
        public static final String CONSTANT_700044 = "700044";
        public static final String CONSTANT_700048 = "700048";
        public static final String CONSTANT_700049 = "700049";
        public static final String CONSTANT_70005 = "70005";
        public static final String CONSTANT_700050 = "700050";
        public static final String CONSTANT_700051 = "700051";
        public static final String CONSTANT_700064 = "700064";
        public static final String CONSTANT_700065 = "700065";
        public static final String CONSTANT_700066 = "700066";
        public static final String CONSTANT_700067 = "700067";
        public static final String CONSTANT_700068 = "700068";
        public static final String CONSTANT_70007 = "70007";
        public static final String CONSTANT_700070 = "700070";
        public static final String CONSTANT_700071 = "700071";
        public static final String CONSTANT_700073 = "700073";
        public static final String CONSTANT_700074 = "700074";
        public static final String CONSTANT_700075 = "700075";
        public static final String CONSTANT_700076 = "700076";
        public static final String CONSTANT_70008 = "70008";
        public static final String CONSTANT_700085 = "700085";
        public static final String CONSTANT_70009 = "70009";
        public static final String CONSTANT_700091 = "700091";
        public static final String CONSTANT_700092 = "700092";
        public static final String CONSTANT_700093 = "700093";
        public static final String CONSTANT_700094 = "700094";
        public static final String CONSTANT_700095 = "700095";
        public static final String CONSTANT_70010 = "70010";
        public static final String CONSTANT_70011 = "70011";
        public static final String CONSTANT_70012 = "70012";
        public static final String CONSTANT_70013 = "70013";
        public static final String CONSTANT_70014 = "70014";
        public static final String CONSTANT_70015 = "70015";
        public static final String CONSTANT_70016 = "70016";
        public static final String CONSTANT_80003 = "80003";
        public static final String CONSTANT_90001 = "90001";
        public static final String CONSTANT_90002 = "90002";
        public static final String CONSTANT_90003 = "90003";
        public static final String CONSTANT_90004 = "90004";
        public static final String CONSTANT_90005 = "90005";
        public static final String CONSTANT_90006 = "90006";
        public static final String CONSTANT_FAVORITE_BOTTOM_LOGIN_GUIDE_TITLE = "100211";
        public static final String CONSTANT_FAVORITE_CANCEL_SUCCESS = "700007";
        public static final String CONSTANT_FAVORITE_CONTENT_NULL_SUBTITLE = "700006";
        public static final String CONSTANT_FAVORITE_CONTENT_NULL_TITLE = "700005";
        public static final String CONSTANT_FAVORITE_NO_NET = "700008";
        public static final String CONSTANT_FAVORITE_SUCCESS = "100062";
        public static final String CONSTANT_LEBOX_DATA_ERROR = "100113";
        public static final String ELEVEN_ZERO_ONE_CONSTANT = "1101";
        public static final String EMOJI_COLOR_FONT_TITLE = "700069";
        public static final String FEEDBACKURI = "80001";
        public static final String FIFTEEN_ELEVEN_CONSTANT = "1511";
        public static final String FIFTEEN_ZERO_FIVE_CONSTANT = "1505";
        public static final String FIFTEEN_ZERO_ONE_CONSTANT = "1501";
        public static final String FIFTEEN_ZERO_SIX_CONSTANT = "1506";
        public static final String FIFTEEN_ZERO_TWO_CONSTANT = "1502";
        public static final String FIVE_ZERO_ONE_CONSTANT = "501";
        public static final String FOURTEEN_FIFTEEN_CONSTANT = "1415";
        public static final String FOURTEEN_FOURTEEN_CONSTANT = "1414";
        public static final String FOURTEEN_SIXTEEN_CONSTANT = "1416";
        public static final String FOURTEEN_TEN_CONSTANT = "1410";
        public static final String FOURTEEN_ZERO_EIGHT_CONSTANT = "1408";
        public static final String FOURTEEN_ZERO_FIVE_CONSTANT = "1405";
        public static final String FOURTEEN_ZERO_FOUR_CONSTANT = "1404";
        public static final String FOURTEEN_ZERO_NINE_CONSTANT = "1409";
        public static final String FOURTEEN_ZERO_ONE_CONSTANT = "1401";
        public static final String FOURTEEN_ZERO_SEVEN_CONSTANT = "1407";
        public static final String FOURTEEN_ZERO_SIX_CONSTANT = "1406";
        public static final String FOURTEEN_ZERO_THREE_CONSTANT = "1403";
        public static final String FOURTEEN_ZERO_TWO_CONSTANT = "1402";
        public static final String FOUR_ZERO_ONE_CONSTANT = "401";
        public static final String INVITE_BOTTM_BUTTON_TEXT = "90019";
        public static final String INVITE_CHECK_BUTTON_TEXT = "90018";
        public static final String INVITE_LEFT_BUTTON_TEXT = "90020";
        public static final String INVITE_SWITCH = "90021";
        public static final String JUMP_PIP_3G_PROMPT = "100028";
        public static final String LIVE_STREAM_HD = "100046";
        public static final String LIVE_STREAM_HIGH = "100047";
        public static final String LIVE_STREAM_ST = "100045";
        public static final String LIVE_STREAM_SUPERHD = "100048";
        public static final String LONGTIME_TIP = "100029";
        public static final String NINE_ZERO_TWO_CONSTANT = "902";
        public static final String NO_PLAY_ONLY_CHINA = "110020";
        public static final String ON_LOADING = "100001";
        public static final String OVERLOAD_CHANGE_STREAM = "100083";
        public static final String OVERLOAD_CUTOUT = "100084";
        public static final String PIP_LOCK = "100034";
        public static final String PLAY_OPERATION_LOCK = "100033";
        public static final String PLAY_OPERATION_UNLOCK = "100032";
        public static final String RECOMMEND = "70006";
        public static final String SEEVENTEEN_ZERO_THREE_CONSTANT = "1703";
        public static final String SEVEN_ZERO_FIVE_CONSTANT = "705";
        public static final String SEVEN_ZERO_FOUR_CONSTANT = "704";
        public static final String SEVEN_ZERO_SEVEN_CONSTANT = "707";
        public static final String SEVEN_ZERO_TWO_CONSTANT = "702";
        public static final String SIXTEEN_ZERO_FOUR_CONSTANT = "1604";
        public static final String SIXTEEN_ZERO_ONE_CONSTANT = "1601";
        public static final String SIXTEEN_ZERO_TWO_CONSTANT = "1602";
        public static final String SIX_ZERO_TWO_CONSTANT = "602";
        public static final String STREAM_HD = "100037";
        public static final String STREAM_LOW = "100035";
        public static final String STREAM_STANDARD = "100036";
        public static final String STREAM_SUPERHD = "100038";
        public static final String SWITCH_STREAM = "100015";
        public static final String TEN_ZERO_ONE_CONSTANT = "1001";
        public static final String TEN_ZERO_TWO_CONSTANT = "1002";
        public static final String THIRTEEN_ZERO_ONE_CONSTANT = "1301";
        public static final String TWELVE_ZERO_ONE_CONSTANT = "1201";
        public static final String TWENTYFIVE_ZERO_ONE_CONSTANT = "2501";
        public static final String TWENTYFOUR_ZERO_FIVE_CONSTANT = "2405";
        public static final String TWENTYFOUR_ZERO_ONE_CONSTANT = "2401";
        public static final String TWENTYFOUR_ZERO_TWO_CONSTANT = "2402";
        public static final String TWENTYONE_ZERO_ONE_CONSTANT = "2101";
        public static final String TWENTYONE_ZERO_TWO_CONSTANT = "2102";
        public static final String TWENTYTHREE_ZERO_THREE_CONSTANT = "2303";
        public static final String TWENTYTWO_ZERO_ONE_CONSTANT = "2201";
        public static final String TWO_ZERO_FIVE_CONSTANT = "205";
        public static final String TWO_ZERO_ONE_CONSTANT = "201";
        public static final String TWO_ZERO_SEVEN_CONSTANT = "207";
        public static final String TWO_ZERO_SIX_CONSTANT = "206";
        public static final String TWO_ZERO_THREE_CONSTANT = "203";
        public static final String TWO_ZERO_TWO_CONSTANT = "202";
    }

    public static class Global {
        public static final String DEVICEID = LetvUtils.generateDeviceId(BaseApplication.getInstance());
        public static final String PCODE = LetvTools.getPcode();
        public static final String VERSION = LetvTools.getClientVersionName();
        public static final int VERSION_CODE = LetvTools.getClientVersionCode();
        public static final DisplayMetrics displayMetrics = BaseApplication.getInstance().getResources().getDisplayMetrics();
    }

    public static class Intent {

        public static class Bundle {
            public static final String DEVICEID = "deviceId";
            public static final String LAUNCH_MODE = "launch_mode";
            public static final String PCODE = "pcode";
            public static final String PLAY = "play_parameter";
            public static final String VERSION = "version";
            public static final String VIDEO_FORMAT = "video_format";
        }
    }

    public interface NotificationID {
        public static final int NOTIFY_DOWNLOAD_VIDEO = 1000;
    }

    public interface STREAM_LEVEL {
        public static final int HD_STREAM = 1;
        public static final int LOW_STREAM = 0;
        public static final int STANDARD_STREAM = 2;
    }

    public enum SortType {
        SORT_BYNO,
        SORT_BYNEWTIME
    }

    public static boolean isForcePlay() {
        return isForcePlay;
    }

    public static void setForcePlay(boolean force) {
        isForcePlay = force;
        LogInfo.log("king", "force = " + force);
    }

    private static String getString(int id) {
        return BaseApplication.getInstance().getApplicationContext().getString(id);
    }
}
