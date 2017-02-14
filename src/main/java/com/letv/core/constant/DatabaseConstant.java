package com.letv.core.constant;

public class DatabaseConstant {

    public static class ChannelHisListTrace {
        public static final String TABLE_NAME = "channelhislisttable";

        public interface ChannelType {
            public static final String TYPE_HK_MOVIE = "hk_movie";
            public static final String TYPE_HK_MUSIC = "hk_music";
            public static final String TYPE_HK_SPORTS = "hk_sports";
            public static final String TYPE_HK_TVSERIES = "hk_tvseries";
            public static final String TYPE_HK_VARIETY = "hk_variety";
            public static final String TYPE_LUNBO = "lunbo";
            public static final String TYPE_WEISHI = "weishi";
        }

        public static class Field {
            public static final String BEGINTIME = "beginTime";
            public static final String BELONGBRAND = "belongBrand";
            public static final String CH = "ch";
            public static final String CHANNELCLASS = "channelClass";
            public static final String CHANNELICON = "channelIcon";
            public static final String CHANNELID = "channelid";
            public static final String CHANNELSTATUS = "channelstatus";
            public static final String CHANNEL_TYPE = "channel_type";
            public static final String ENAME = "ename";
            public static final String ENDTIME = "endTime";
            public static final String IS3D = "is3D";
            public static final String IS4K = "is4K";
            public static final String ISCOMMEND = "isRecommend";
            public static final String ISRECORD = "isRecord";
            public static final String LOGO120X160 = "logo120x160";
            public static final String LOGO120X90 = "logo120x90";
            public static final String LOGO150X150 = "logo150x150";
            public static final String LOGO150X200 = "logo150x200";
            public static final String LOGO30X30 = "logo30x30";
            public static final String LOGO96X128 = "logo96x128";
            public static final String LOGOORIGIN = "logoOrigin";
            public static final String NAME = "name";
            public static final String NUMERICKEYS = "numericKeys";
            public static final String ORDERNO = "orderNo";
            public static final String SIGNAL = "signal";
            public static final String SOURCEID = "sourceId";
            public static final String SYSTEMILLISECOND = "systemmillisecond";
        }
    }

    public static class ChannelListTrace {
        public static final String TABLE_NAME = "channellisttable";

        public static class ChannelStatus {
            public static final String DELETE = "delete";
            public static final String NORMAL = "normal";
        }

        public static class Field {
            public static final String BEGINTIME = "beginTime";
            public static final String BELONGBRAND = "belongBrand";
            public static final String CH = "ch";
            public static final String CHANNELCLASS = "channelClass";
            public static final String CHANNELICON = "channelIcon";
            public static final String CHANNELID = "channelid";
            public static final String CHANNELSTATUS = "channelstatus";
            public static final String CHANNEL_TYPE = "channel_type";
            public static final String ENAME = "ename";
            public static final String ENDTIME = "endTime";
            public static final String HASSAVE = "hassave";
            public static final String IS3D = "is3D";
            public static final String IS4K = "is4K";
            public static final String ISCOMMEND = "isRecommend";
            public static final String LOGO120X160 = "logo120x160";
            public static final String LOGO120X90 = "logo120x90";
            public static final String LOGO150X150 = "logo150x150";
            public static final String LOGO150X200 = "logo150x200";
            public static final String LOGO30X30 = "logo30x30";
            public static final String LOGO96X128 = "logo96x128";
            public static final String LOGOORIGIN = "logoOrigin";
            public static final String NAME = "name";
            public static final String NUMERICKEYS = "numericKeys";
            public static final String ORDERNO = "orderNo";
            public static final String SIGNAL = "signal";
            public static final String SOURCEID = "sourceId";
        }
    }

    public static class DialogMsgTrace {
        public static final String TABLE_NAME = "DialogMsgTrace";
    }

    public static class DownloadTrace {
        public static final String TABLE_NAME = "downlaodTrace";
    }

    public static class Emoji {
        public static final String TABLE_NAME = "emoji";
    }

    public static class FavoriteRecord {
        public static final String TABLE_NAME = "Favoritetable";
    }

    public static class FestivalImageTrace {
        public static final String TABLE_NAME = "festivalimagetrace";
    }

    public static class HotTopTrace {
        public static final String TABLE_NAME = "HotTopTrace";
    }

    public static class LanguageSettingsTrace {
        public static final String TABLE_NAME = "language_settings";
    }

    public static class LiveBookTrace {
        public static final String TABLE_NAME = "LiveBookTrace";

        public static class Field {
            public static final String CHANNELNAME = "channelName";
            public static final String CODE = "code";
            public static final String IS_NOTIFY = "is_notify";
            public static final String LAUNCH_MODE = "launch_mode";
            public static final String LIVE_ID = "live_id";
            public static final String MD5_ID = "md5";
            public static final String PLAY_TIME = "play_time";
            public static final String PLAY_TIME_LONG = "play_time_long";
            public static final String PROGRAMNAME = "programName";
        }
    }

    public static class LocalCacheTrace {
        public static final String TABLE_NAME = "LocalCacherace";
    }

    public static class LocalVideoTrace {
        public static final String TABLE_NAME = "LocalVideoTable";
    }

    public static class PlayRecord {
        public static final String TABLE_NAME = "playtable";
    }

    public static class PlayRecordWatched {
        public static final String TABLE_NAME = "playtablewatched";
    }

    public static class SearchTrace {
        public static final String TABLE_NAME = "searchtable";

        public static class Field {
            public static final String NAME = "name";
            public static final String TIMESTAMP = "timestamp";
        }
    }

    public static class TopChannelsTrace {
        public static final String TABLE_NAME = "TopChannelsTrace";
    }

    public static class WorldCupTrace {
        public static final String TABLE_NAME = "worldCupTrace";
    }
}
