package com.letv.core.constant;

public class ShareConstant {
    public static final String SHARE_CUSTOM_TEXT_DIVIDER = "##@@";
    public static final String SHARE_CUSTOM_TEXT_DIVIDER_OLD = "##";
    public static final int SHARE_FACEBOOK = 7;
    public static final int SHARE_FROM_ALBUM_PANO = 12;
    public static final int SHARE_FROM_CLICK_PLAY = 1;
    public static final int SHARE_FROM_CLICK_PLAY_VOTE = 6;
    public static final int SHARE_FROM_COMMENT = 5;
    public static final int SHARE_FROM_COMP_OLD_VALUE = -1;
    public static final int SHARE_FROM_GIFT_MONEY_HALFPAGE = 10;
    public static final int SHARE_FROM_GIFT_MONEY_MAINPAGE = 8;
    public static final int SHARE_FROM_HOT = 9;
    public static final int SHARE_FROM_LIVE = 3;
    public static final int SHARE_FROM_LIVE_PANO = 13;
    public static final int SHARE_FROM_LIVE_VOTE = 7;
    public static final int SHARE_FROM_RED_PACKET_SPRING = 11;
    public static final int SHARE_FROM_TOPIC = 2;
    public static final int SHARE_FROM_VIDEOSHOT = 4;
    public static final int SHARE_FROM_VIDEOSHOT_TYPE_1 = 1;
    public static final int SHARE_FROM_VIDEOSHOT_TYPE_2 = 2;
    public static final int SHARE_FROM_VIDEOSHOT_TYPE_3 = 3;
    public static final int SHARE_QQ = 6;
    public static final int SHARE_QQ_ZONE = 3;
    public static final int SHARE_SINA_WEIBO = 1;
    public static final int SHARE_TENCENT_WEIBO = 2;
    public static final int SHARE_TYPE_CONVERT_CHANNEL_COMMENT = 1;
    public static final int SHARE_TYPE_CONVERT_CHANNEL_MSTATION = 2;
    public static final int SHARE_TYPE_CONVERT_CHANNEL_PCSTATION = 3;
    public static final int SHARE_TYPE_CONVERT_CHANNEL_SHARE = 0;
    public static final int SHARE_TYPE_CONVERT_FACEBOOK = 6;
    public static final int SHARE_TYPE_CONVERT_QQ = 2;
    public static final int SHARE_TYPE_CONVERT_QQ_ZONE = 3;
    public static final int SHARE_TYPE_CONVERT_SINA_WEIBO = 4;
    public static final int SHARE_TYPE_CONVERT_TENCENT_WEIBO = 5;
    public static final int SHARE_TYPE_CONVERT_WEIXIN = 1;
    public static final int SHARE_TYPE_CONVERT_WEIXIN_FRIEND = 0;
    public static final int SHARE_TYPE_FACEBOOK = 6;
    public static final int SHARE_TYPE_QQ = 4;
    public static final int SHARE_TYPE_QQ_ZONE = 3;
    public static final int SHARE_TYPE_SINA_WEIBO = 2;
    public static final int SHARE_TYPE_TENCENT_WEIBO = 5;
    public static final int SHARE_TYPE_WEIXIN = 1;
    public static final int SHARE_TYPE_WEIXIN_FRIEND = 0;
    public static final String SHARE_URL_TYPE_CLICK_PLAY = "https://fb.me/1676545839273838?actionType={actionType}&vid={vid}&aid={aid}&cid={cid}&isPanorama={isPanorama}&from={from}";
    public static final String SHARE_URL_TYPE_LIVE_CHANNEL_LUNBO = "http://m.le.com/live/play?channel={id}&ename=lunbo";
    public static final String SHARE_URL_TYPE_LIVE_CHANNEL_NORMAL = "http://m.le.com/live/play_{channel}.html?id={id}";
    public static final String SHARE_URL_TYPE_LIVE_CHANNEL_OTHER = "http://m.le.com/live/play_other.html?id={id}";
    public static final String SHARE_URL_TYPE_LIVE_CHANNEL_WEISHI = "http://m.le.com/live/play?channel={id}&ename=weishi";
    public static final String SHARE_URL_TYPE_Live_PLAY = "https://fb.me/1676545839273838?actionType={actionType}&liveType={liveType}&liveId={liveId}&from={from}";
    public static final int SHARE_VIDEO_TYPE_COPYRIGHT = 1;
    public static final int SHARE_VIDEO_TYPE_MOVIE_TV = 0;
    public static final int SHARE_VIDEO_TYPE_OTHER = 2;
    public static final int SHARE_WEIXIN = 5;
    public static final int SHARE_WEIXIN_FRIEND = 4;

    public interface BindState {
        public static final int BIND = 1;
        public static final int BINDPASS = 2;
        public static final int UNBIND = 0;
    }

    public interface Weixin {
        public static final String APP_ID = "wx40f1ed0460d8cbf4";
        public static final String APP_KEY = "705036f39d8f3fd91421e6e8da268610";
    }
}
