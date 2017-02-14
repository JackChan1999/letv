package com.letv.adlib.sdk.types;

public class AdElement {
    public int adReqType;
    public int adTag;
    public int adZoneType;
    public String clickThrough;
    public int commonType;
    public int cuePointType;
    public int dspType;
    public int genericType;
    public int hasProgressTracking = 0;
    public int vastTag;

    public enum AdClickShowType {
        SHOW_NoAction(0),
        SHOW_ExternalWebBrowser(1),
        SHOW_InternalWebView(2),
        SHOW_EnterVideoPlayer(3),
        SHOW_EnterLivePlayer(4),
        SHOW_InstallAPK(5),
        SHOW_EnterShop(6);
        
        public static final int SHOW_ENTERLIVEPLAYER = 4;
        public static final int SHOW_ENTERSHOP = 6;
        public static final int SHOW_ENTERVIDEOPLAYER = 3;
        public static final int SHOW_EXTERNALWEBBROWSER = 1;
        public static final int SHOW_INSTALLAPK = 5;
        public static final int SHOW_INTERNALWEBVIEW = 2;
        public static final int SHOW_NOACTION = 0;
        private int _value;

        private AdClickShowType(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public enum AdCloseType {
        DEFAULT(0),
        SHAKE(1);
        
        public static final int DEFAULT_VALUE = 0;
        public static final int SHAKE_VALUE = 1;
        private int _value;

        private AdCloseType(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public enum AdCommonType {
        COMMON_DEFAULT(0),
        COMMON_SearchType(1),
        COMMON_FORCEFOCUS(2),
        COMMON_TEXTLINK(3),
        COMMON_PortraitPic(4),
        COMMON_CORNER(5),
        COMMON_BEGIN(6),
        COMMON_PLAYPAGE_BANNER(7),
        COMMON_HOMEPAGE_BANNER(20);
        
        private int _value;

        private AdCommonType(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public interface AdContentType {
        public static final int DEFAULT = 0;
        public static final int GAME_COMMON = 3;
        public static final int GAME_DOWNLOAD = 2;
        public static final int LIVE_INFO = 7;
        public static final int NORMAL_COMMON = 5;
        public static final int NORMAL_DOWNLOAD = 4;
        public static final int PRODUCT = 1;
        public static final int VOD_INFO = 6;
    }

    public enum AdGenericType {
        GENERIC_DEFAULT(0),
        GENERIC_INTERACTIVE_CREATIVE(1);
        
        private int _value;

        private AdGenericType(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public enum AdMediaType {
        MEDIA_Bitmap(0),
        MEDIA_Video(1),
        MEDIA_Plain(2),
        MEDIA_HTML(3),
        MEDIA_DYNAMIC(4),
        MEDIA_HALF_PIC(5);
        
        public static final int BITMAP = 0;
        public static final int DYNAMIC = 4;
        public static final int HALF_PIC = 5;
        public static final int HTML = 3;
        public static final int PLAIN = 2;
        public static final int VIDEO = 1;
        private int _value;

        private AdMediaType(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public enum AdPostion {
        DEFAULT(0),
        LEFT_BOTTOM(1),
        RIGHT_BOTTOM(2);
        
        public static final int DEFAULT_VALUE = 0;
        public static final int LEFT_BOTTOM_VALUE = 1;
        public static final int RIGHT_BOTTOM_VALUE = 2;
        private int _value;

        private AdPostion(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public interface AdStyleType {
        public static final int DEFAULT = 0;
        public static final int PICTURE = 2;
        public static final int PICTURE_TEXT = 1;
    }

    public interface AdType {
        public static final int VIDEO_PLUS = 1;
    }

    public interface CuePointType {
        public static final int DRAWCURTAIN = 1;
        public static final int MIDROLL = 4;
        public static final int OVERLAY = 7;
        public static final int PAGE = 0;
        public static final int PAUSE = 6;
        public static final int POSTROLL = 5;
        public static final int PREROLL = 2;
        public static final int STANDARD = 3;
    }

    public enum DSPType {
        DSP_DUMMY(0),
        DSP_ARK(1),
        DSP_ALLYES(2),
        DSP_MIAOZHEN(3);
        
        private int _value;

        private DSPType(int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public interface ThirdPartyCreativeType {
        public static final String MOVIEBOOK = "moviebook";
        public static final String VIDEO_PLUS = "videoplus";
    }

    public String getClickThrough() {
        try {
            return this.clickThrough;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isInPlayerAd(int ct) {
        return ct == 2 || ct == 6 || ct == 7 || ct == 3 || ct == 4 || ct == 5;
    }
}
