package com.letv.core.utils;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.letv.base.R;
import com.letv.component.player.LetvVideoViewBuilder.Type;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.core.PlayUrl;
import com.letv.component.player.core.PlayUrl.StreamType;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumPayInfoBean;
import com.letv.core.bean.DDUrlsResultBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.bean.VideoFileBean.SchedulingAddressLevel;
import com.letv.core.bean.VideoFileBean.VideoSchedulingAddress;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.datastatistics.util.DataConstant.ACTION.LE123.CHANNEL;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.datastatistics.util.LetvBase64;

public class PlayUtils {
    public static final String BRLIST_1000 = "mp4_1000";
    public static final String BRLIST_1300 = "mp4_1300";
    public static final String BRLIST_350 = "mp4_350";
    public static final String BRLIST_FLV_1080p = "flv_1080p";
    public static final String BRLIST_FLV_1300 = "flv_1300";
    public static final String BRLIST_FLV_720p = "flv_720p";
    public static final String BRLIST_FLV_800 = "flv_800";

    public interface PLAY_LEVEL {
        public static final int[] ARR = new int[]{0, 1, 2, 3, 4};
        public static final int HIGH = 2;
        public static final int HIGH_1080 = 5;
        public static final int HIGH_720 = 4;
        public static final int LOW = 0;
        public static final int STANDARD = 1;
        public static final int SUPERHIGH = 3;
    }

    public static boolean canPlayStream1080p(boolean shouldPay) {
        return shouldPay || PreferencesManager.getInstance().isVip();
    }

    public static DDUrlsResultBean getDDUrls(VideoFileBean videoFile, int playLevel, boolean shouldPay, VideoType videoType) {
        if (videoType == VideoType.Dolby) {
            return getDolbyDDUrls(videoFile, playLevel, shouldPay);
        }
        if (videoType == VideoType.Panorama) {
            return getPanoramaDDUrls(videoFile, playLevel, shouldPay);
        }
        if (videoType == VideoType.Drm) {
            return getDrmDDUrls(videoFile, playLevel, shouldPay);
        }
        return getDDUrls(videoFile, playLevel, shouldPay);
    }

    private static DDUrlsResultBean getDDUrls(VideoFileBean videoFile, int playLevel, boolean shouldPay) {
        boolean z = true;
        VideoSchedulingAddress address = null;
        int realPlayLevel = playLevel;
        DDUrlsResultBean result = new DDUrlsResultBean();
        SchedulingAddressLevel addressLevel = videoFile.getVideoSchedulingAddress(videoFile.normalAddressArr, playLevel, result);
        if (addressLevel != null) {
            address = addressLevel.address;
            realPlayLevel = addressLevel.level;
        }
        if (address == null) {
            return null;
        }
        result.playLevel = realPlayLevel;
        result.videoType = VideoType.Normal;
        if (address == null) {
            return result;
        }
        boolean z2;
        result.storepath = address.getStorepath();
        result.token = address.token;
        result.streamLevel = address.vtype;
        if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 4) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.has720p = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 3) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasSuperHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 2) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 1) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasStandard = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.normalAddressArr, 0) == null) {
            z = false;
        }
        result.hasLow = z;
        return result;
    }

    private static DDUrlsResultBean getDolbyDDUrls(VideoFileBean videoFile, int playLevel, boolean shouldPay) {
        boolean z = true;
        VideoSchedulingAddress address = null;
        DDUrlsResultBean result = new DDUrlsResultBean();
        int realPlayLevel = playLevel;
        SchedulingAddressLevel addressLevel = videoFile.getVideoSchedulingAddress(videoFile.dolbyAddressArr, playLevel, result);
        if (addressLevel != null) {
            address = addressLevel.address;
            realPlayLevel = addressLevel.level;
        }
        if (address == null) {
            return getDDUrls(videoFile, playLevel, shouldPay);
        }
        boolean z2;
        result.videoType = VideoType.Dolby;
        result.playLevel = realPlayLevel;
        result.storepath = address.getStorepath();
        result.streamLevel = address.vtype;
        if (BaseTypeUtils.getElementFromArray(videoFile.dolbyAddressArr, 4) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.has720p = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.dolbyAddressArr, 3) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasSuperHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.dolbyAddressArr, 2) == null) {
            z = false;
        }
        result.hasHigh = z;
        return result;
    }

    private static DDUrlsResultBean getPanoramaDDUrls(VideoFileBean videoFile, int playLevel, boolean shouldPay) {
        boolean z = true;
        VideoSchedulingAddress address = null;
        DDUrlsResultBean result = new DDUrlsResultBean();
        int realPlayLevel = playLevel;
        SchedulingAddressLevel addressLevel = videoFile.getVideoSchedulingAddress(videoFile.panoramaAddressArr, playLevel, result);
        if (addressLevel != null) {
            address = addressLevel.address;
            realPlayLevel = addressLevel.level;
        }
        if (address == null) {
            return getDDUrls(videoFile, playLevel, shouldPay);
        }
        boolean z2;
        result.videoType = VideoType.Panorama;
        result.playLevel = realPlayLevel;
        result.storepath = address.getStorepath();
        result.streamLevel = address.vtype;
        if (BaseTypeUtils.getElementFromArray(videoFile.panoramaAddressArr, 3) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasSuperHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.panoramaAddressArr, 2) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.panoramaAddressArr, 1) == null) {
            z = false;
        }
        result.hasStandard = z;
        return result;
    }

    private static DDUrlsResultBean getDrmDDUrls(VideoFileBean videoFile, int playLevel, boolean shouldPay) {
        boolean z = true;
        VideoSchedulingAddress address = null;
        int realPlayLevel = playLevel;
        DDUrlsResultBean result = new DDUrlsResultBean();
        SchedulingAddressLevel addressLevel = videoFile.getVideoSchedulingAddress(videoFile.drmAddressArr, playLevel, result);
        if (addressLevel != null) {
            address = addressLevel.address;
            realPlayLevel = addressLevel.level;
        }
        if (address == null) {
            return null;
        }
        result.playLevel = realPlayLevel;
        result.videoType = VideoType.Normal;
        if (address == null) {
            return result;
        }
        boolean z2;
        result.storepath = address.getStorepath();
        result.token = address.token;
        result.streamLevel = address.vtype;
        result.drmToken = address.drmToken;
        if (BaseTypeUtils.getElementFromArray(videoFile.drmAddressArr, 4) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.has720p = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.drmAddressArr, 3) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasSuperHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.drmAddressArr, 2) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasHigh = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.drmAddressArr, 1) != null) {
            z2 = true;
        } else {
            z2 = false;
        }
        result.hasStandard = z2;
        if (BaseTypeUtils.getElementFromArray(videoFile.drmAddressArr, 0) == null) {
            z = false;
        }
        result.hasLow = z;
        return result;
    }

    public static String getPlayToken(DDUrlsResultBean ddUrl, AlbumPayInfoBean payInfo) {
        String token = ddUrl != null ? ddUrl.token : "";
        if (TextUtils.isEmpty(token)) {
            return payInfo != null ? payInfo.token : "";
        } else {
            return token;
        }
    }

    public static String getDdUrl(String dispatchUrl, String token, String uid, String vid, String uuid, String audioTrackId) {
        return getDdUrl(dispatchUrl, token, uid, vid, uuid, audioTrackId, false);
    }

    public static String getDdUrl(String dispatchUrl, String token, String uid, String vid, String uuid, String audioTrackId, boolean isWoUser) {
        if (TextUtils.isEmpty(dispatchUrl)) {
            return null;
        }
        dispatchUrl = (dispatchUrl + "&pcode=" + LetvConfig.getPcode()) + "&version=" + LetvUtils.getClientVersionName();
        if (PreferencesManager.getInstance().isLogin()) {
            dispatchUrl = dispatchUrl + "&iscpn=f9050";
            if (!isWoUser) {
                dispatchUrl = dispatchUrl + "&uinfo=" + PreferencesManager.getInstance().getUInfo();
            }
        }
        if (!TextUtils.isEmpty(token)) {
            dispatchUrl = dispatchUrl + "&token=" + token;
        }
        if (!TextUtils.isEmpty(uid)) {
            dispatchUrl = dispatchUrl + "&uid=" + uid;
        }
        if (isWoUser) {
            dispatchUrl = dispatchUrl + "&freecnc=1";
        }
        String ddUrl = replaceM3v((dispatchUrl + "&p1=0&p2=00&uuid=" + uuid) + "&vid=" + vid);
        if (!TextUtils.isEmpty(audioTrackId)) {
            ddUrl = ddUrl + "&a_idx=" + audioTrackId;
        }
        LogInfo.log("wuxinrong", "调度地址AAABBBCCC = " + ddUrl);
        return ddUrl;
    }

    public static String addStatisticsInfoToUrl(String url, String uuid, String vid) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return (url.contains("uuid=") || url.contains("p1=")) ? url : url + "&p1=0&p2=00&uuid=" + uuid + "&vid=" + vid;
    }

    public static String replaceM3v(String url) {
        String defaultM3v = PreferencesManager.getInstance().getM3v();
        if (!url.contains("&m3v=")) {
            return url + "&m3v=" + defaultM3v;
        }
        String replace = "&m3v=" + defaultM3v;
        for (int i = 0; i < 10; i++) {
            String old = "&m3v=" + i;
            if (url.contains(old)) {
                return url.replace(old, replace);
            }
        }
        return url;
    }

    public static String getLinkShell(String dispatchUrl, String token, String uid, String vid, String uuid, String audioTrackCode) {
        if (TextUtils.isEmpty(dispatchUrl)) {
            return null;
        }
        try {
            String baseUrl = getDdUrl(dispatchUrl, token, uid, vid, uuid, audioTrackCode);
            if (BaseApplication.getInstance().getCdeHelper() == null) {
                return baseUrl;
            }
            String linkShellUrl = BaseApplication.getInstance().getCdeHelper().getLinkshellUrl(baseUrl);
            if (TextUtils.isEmpty(linkShellUrl)) {
                return baseUrl;
            }
            return linkShellUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLinkShell2(String dispatchUrl, String token, String uid, String vid, String uuid, String audioTrackCode) {
        if (TextUtils.isEmpty(dispatchUrl)) {
            return null;
        }
        try {
            String baseUrl = getDdUrl(dispatchUrl, token, uid, vid, uuid, audioTrackCode);
            if (BaseApplication.getInstance().getCdeHelper() == null) {
                return baseUrl;
            }
            String linkShellUrl = BaseApplication.getInstance().getCdeHelper().getLinkshellUrl2(baseUrl);
            if (TextUtils.isEmpty(linkShellUrl)) {
                return baseUrl;
            }
            return linkShellUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSupportHD(String brList) {
        if (!TextUtils.isEmpty(brList) && brList.contains(BRLIST_1300) && H265Utils.isSupport1300() && !"lenovo k910".equalsIgnoreCase(Build.MODEL)) {
            return true;
        }
        return false;
    }

    public static boolean isSupportStandard(String brList) {
        if (TextUtils.isEmpty(brList)) {
            return false;
        }
        return brList.contains(BRLIST_1000);
    }

    public static boolean isSupportLow(String brList) {
        if (TextUtils.isEmpty(brList)) {
            return false;
        }
        return brList.contains(BRLIST_350);
    }

    public static boolean isSupportFlv800(String brList) {
        if (TextUtils.isEmpty(brList)) {
            return false;
        }
        return brList.contains(BRLIST_FLV_800);
    }

    public static boolean isSupportFlv1300(String brList) {
        if (TextUtils.isEmpty(brList)) {
            return false;
        }
        return brList.contains(BRLIST_FLV_1300);
    }

    public static boolean isSupportFlv720p(String brList) {
        if (TextUtils.isEmpty(brList)) {
            return false;
        }
        return brList.contains(BRLIST_FLV_720p);
    }

    public static boolean isSupportFlv1080p(String brList) {
        if (TextUtils.isEmpty(brList)) {
            return false;
        }
        return brList.contains(BRLIST_FLV_1080p);
    }

    public static String getTss() {
        return "ios";
    }

    public static String splitJointPlayUrl(String playUrl, String adUrl) {
        try {
            String baseUrl = "http://" + Uri.parse(playUrl).getHost() + "/gate_way.m3u8?ID=id9,id10&id9.proxy_url=";
            adUrl = LetvBase64.encode(adUrl.getBytes("UTF-8"));
            playUrl = LetvBase64.encode(playUrl.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            sb.append(baseUrl);
            sb.append(adUrl);
            sb.append("&id10.proxy_url=");
            sb.append(playUrl);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCode(int playLevel) {
        String code = "";
        switch (playLevel) {
            case 0:
                return "160";
            case 1:
                return CHANNEL.SERIAL_SPECIAL;
            case 2:
                return "800";
            default:
                return code;
        }
    }

    public static String getStreamLevel(int type) {
        String streamLevel = VType.H265_FLV_800;
        switch (type) {
            case 0:
                return VType.H265_FLV_800;
            case 1:
                return VType.H265_FLV_1300;
            case 2:
                return VType.H265_FLV_720P;
            case 3:
                return VType.H265_FLV_1080P;
            default:
                return streamLevel;
        }
    }

    public static int getMaxStreamLevel() {
        if (LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel() == 5) {
            return 4;
        }
        if (LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel() == 4) {
            return 3;
        }
        if (LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel() == 2) {
            return 2;
        }
        if (LetvMediaPlayerManager.getInstance().getHardDecodeSupportLevel() == 1) {
            return 1;
        }
        return -1;
    }

    public static String getURLFromLinkShell(String input, String uuid) {
        if (BaseApplication.getInstance().getCdeHelper() != null) {
            return BaseApplication.getInstance().getCdeHelper().getLinkshellUrl(replaceUuid(input, uuid));
        }
        return input;
    }

    public static String getURLFromLinkShell2(String input, String uuid) {
        if (BaseApplication.getInstance().getCdeHelper() != null) {
            return BaseApplication.getInstance().getCdeHelper().getLinkshellUrl2(replaceUuid(input, uuid));
        }
        return input;
    }

    private static String replaceUuid(String url, String uuid) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(uuid)) {
            return url;
        }
        String newUrl = url;
        if (!url.contains("uuid=")) {
            return url + "&uuid =" + uuid;
        }
        int start = url.indexOf("uuid=");
        int end = url.indexOf("&", start);
        return url.replace(end < 0 ? url.substring(start) : url.substring(start, end), "uuid=" + uuid);
    }

    public static PlayUrl getPlayUrl(String url, Type type, int playLevel) {
        PlayUrl playUrl = new PlayUrl();
        playUrl.setVid(101);
        try {
            if (type == Type.MOBILE_H264_M3U8_HW) {
                playUrl.setStreamType(getPlayLevel2StreamType(playLevel));
            } else {
                playUrl.setStreamType(getPlayLevel2StreamType(-1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        playUrl.setUrl(url);
        return playUrl;
    }

    public static StreamType getPlayLevel2StreamType(int playLevel) {
        switch (playLevel) {
            case 0:
                return StreamType.STREAM_TYPE_180K;
            case 1:
                return StreamType.STREAM_TYPE_350K;
            case 2:
                return StreamType.STREAM_TYPE_1000K;
            case 3:
                return StreamType.STREAM_TYPE_1300K;
            case 4:
                return StreamType.STREAM_TYPE_720P;
            default:
                return StreamType.STREAM_TYPE_UNKNOWN;
        }
    }

    public static String[] getStreamLevelName() {
        return new String[]{TipUtils.getTipMessage("100035", R.string.stream_low), TipUtils.getTipMessage("100036", R.string.stream_smooth), TipUtils.getTipMessage("100037", R.string.stream_standard), TipUtils.getTipMessage("100038", R.string.stream_hd), BaseApplication.getInstance().getString(R.string.three_screen_SD), BaseApplication.getInstance().getString(R.string.stream_1080p)};
    }

    public static String getPlayLevelZh(String superName, String hdName, String standardName, String lowName, int playLevel) {
        if (playLevel == 2) {
            return hdName;
        }
        if (playLevel == 1) {
            return standardName;
        }
        if (playLevel == 0) {
            return lowName;
        }
        return superName;
    }
}
