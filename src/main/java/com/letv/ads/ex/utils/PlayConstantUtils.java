package com.letv.ads.ex.utils;

public class PlayConstantUtils {

    public interface CPConstant {
        public static final int MSG_BASE = 200;
    }

    public interface ClientCPConstant {
        public static final String KEY_AD_TYPE = "ad_type";
        public static final String KEY_MIDROLL_DURATION = "midRoll_duration";
        public static final String KEY_MIDROLL_POINT = "midRoll_point";
        public static final String KEY_MIDROLL_TIP_TIME = "midRoll_tip_time";
        public static final int MSG_BASE = 400;
        public static final int MSG_MIDROLL_TIP = 401;
        public static final int TYPE_MIDROLL = 2;
        public static final int TYPE_PAUSE = 3;
        public static final int TYPE_PREROLL = 1;
    }

    public interface PFConstant {
        public static final int ERROR_BLOCK_TIME_OUT = 1;
        public static final int ERROR_LOADING_TIME_OUT = 2;
        public static final int EVENT_AD_NUMBER = 4;
        public static final int EVENT_AD_START_PLAY = 10;
        public static final int EVENT_BLOCK = 6;
        public static final int EVENT_COMBINE_COMPLETE = 5;
        public static final int EVENT_PLAY_COMPLETE = 3;
        public static final int EVENT_PLAY_ERROR = 2;
        public static final int EVENT_PLAY_PREPARE = 1;
        public static final int EVENT_PROXY_AD_DATA_COMPLETE = 8;
        public static final int EVENT_PROXY_AD_TIMEOUT = 9;
        public static final int EVENT_REFRESH_AD_PROGRESS = 7;
        public static final String KEY_AD = "ad";
        public static final String KEY_AD_COMBINE_RESULT = "ad_combine_result";
        public static final String KEY_AD_DATA = "ad_data";
        public static final String KEY_AD_PLAY_POSITION = "ad_play_position";
        public static final String KEY_BLOCK_VALUE = "block_value";
        public static final String KEY_CT = "cuepoint";
        public static final String KEY_INDEX_VALUE = "index";
        public static final String KEY_IS_FINISH_BY_HAND = "isFinishByHand";
        public static final String KEY_PLAY_ERROR_EXTRA = "play_error_extra";
        public static final String KEY_PLAY_ERROR_TYPE = "play_error_type";
        public static final String KEY_PROLL_TYPE = "proll_type";
        public static final String KEY_SAVE_POSITION = "savePos";
        public static final int MSG_AD_INDEX_CHANGE = 105;
        public static final int MSG_BASE = 100;
        public static final int MSG_BLOCK_TIMEOUT = 102;
        public static final int MSG_CLINET_COMBINE = 106;
        public static final int MSG_FINISH_FRONTAD = 107;
        public static final int MSG_LOADING_TIMEOUT = 101;
        public static final int MSG_PLAY_STATE_CHANGE = 104;
        public static final int MSG_REFRESH_TIME = 103;
        public static final int MSG_SAVE_AD_POSITION = 108;
        public static final long REFRESH_TIME_INTERVAL = 500;
        public static final int TIMEOUT_BLOCK_DURATION = 5000;
        public static final long TIMEOUT_DURATION = 10000;
    }

    public interface SPConstant {
        public static final long CHECK_BLOCK_INTERVAL = 1000;
        public static final long DELAY_BUFFER_DURATION = 2000;
        public static final String KEY_IS_FIRSTVIDEO = "is_first_video";
        public static final String KEY_IS_ONLY_BUFFER = "only_buffer";
        public static final int MSG_BASE = 300;
        public static final int MSG_CHECK_BLOCK = 302;
        public static final int MSG_HANDLE_PIC_AD = 303;
        public static final int MSG_PLAY_OR_BUFFER = 304;
        public static final int MSG_PREPARE_BUFFER_VIDEO = 301;
    }
}
