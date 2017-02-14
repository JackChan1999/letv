package com.letv.core.bean;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.letv.base.R;
import com.letv.core.constant.PlayConstant;
import com.letv.core.parser.PushDataParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.download.db.Download.DownloadVideoTable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class AlbumInfo implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    final byte MULTI_PAGE = (byte) 2;
    public long Plist_play_count;
    final byte SINGLE_PAGE = (byte) 1;
    public String albumType;
    public String albumTypeKey;
    private String alias;
    public String area;
    public int at;
    private String cast;
    public int cid;
    public String compere;
    public String controlAreas;
    public String cornerMark;
    public String description;
    public String directory;
    public int disableType;
    public int download;
    private String dub;
    public long duration;
    public String episode;
    public String extendSubscript;
    public String externalUrl;
    public int filmstyle;
    private String fitAge;
    public String icon_16_9;
    public String icon_200_150;
    public String icon_400_300;
    public String instructor;
    public boolean isDolby;
    public int isEnd;
    private int isHomemade;
    public int jump;
    public String language;
    public String leftSubscipt;
    public String leftSubsciptColor;
    public String nameCn;
    public boolean noCopyright;
    public String nowEpisodes;
    private String originator;
    public int pay;
    public String pic;
    public String pic150_200;
    public String pic300_300;
    @JSONField(name = "pic320*200")
    public String pic320_200;
    public String pic400_300;
    @JSONField(name = "picCollections")
    public PicCollectionsBean picCollections = new PicCollectionsBean();
    public String pic_300_400;
    public long pid;
    public String pidname;
    public String pidsubtitle;
    public int platformVideoInfo;
    public int platformVideoNum;
    public int play;
    public long playCount;
    private String playStatus;
    public String playTv;
    private String rCompany;
    public String relationAlbumId;
    public String relationId;
    public String releaseDate;
    public String school;
    public String score;
    public String starring;
    public String style;
    public String subCategory;
    public String subTitle;
    public String subcid;
    public String subname;
    public String subsciptColor;
    private String supervise;
    public String tag;
    public String title;
    public String totalNum = "0";
    public String travelType;
    public int type;
    public List<String> vTypeFlagList = new ArrayList();
    public int varietyShow;
    public long vid;

    public static class Channel {
        public static final int TYPE_CAR = 14;
        public static final int TYPE_CARTOON = 5;
        public static final int TYPE_DOCUMENT_FILM = 16;
        public static final int TYPE_DOLBY = 1001;
        public static final int TYPE_EDUCATION = 1021;
        public static final int TYPE_FASHION = 20;
        public static final int TYPE_FINANCIAL = 22;
        public static final int TYPE_FUNNY = 10;
        public static final int TYPE_GAME = 104;
        public static final int TYPE_H265 = 2001;
        public static final int TYPE_HOT = 30;
        public static final int TYPE_HUMOUR = 1010;
        public static final int TYPE_INFORMATION = 6;
        public static final int TYPE_JOY = 3;
        public static final int TYPE_LETV_MAKE = 19;
        public static final int TYPE_LETV_PRODUCT = 202;
        public static final int TYPE_LIFE = 13;
        public static final int TYPE_MOVIE = 1;
        public static final int TYPE_MUSIC = 9;
        public static final int TYPE_NBA = 1004;
        public static final int TYPE_NEWS = 1009;
        public static final int TYPE_OPEN_CLASS = 17;
        public static final int TYPE_ORIGINAL = 7;
        public static final int TYPE_OTHERS = 8;
        public static final int TYPE_PATERNITY = 34;
        public static final int TYPE_PE = 4;
        public static final int TYPE_SCIENCE = 12;
        public static final int TYPE_SEARCH = 2002;
        public static final int TYPE_TOURISM = 23;
        public static final int TYPE_TV = 2;
        public static final int TYPE_TVPROGRAM = 15;
        public static final int TYPE_TVSHOW = 11;
        public static final int TYPE_TV_AMERICAN = 1017;
        public static final int TYPE_VIP = 1000;
        public static final int TYPE_WEBVIEW = 2003;
        public static final int TYPE_WORLDCUP = 123456;
        public static String VIP_PAGEID = "1003322235";
        public static String VIP_PAGEID_HONGKONG = "1003351312";
        public static String VIP_PAGEID_TEST = "1003322237";
    }

    public static class PicCollectionsBean implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        @JSONField(name = "150*200")
        public String pic150;
        @JSONField(name = "300*300")
        public String pic300;
        @JSONField(name = "320*200")
        public String pic320;
        @JSONField(name = "400*300")
        public String pic400;

        public void setPic150(String pic150) {
            this.pic150 = pic150;
        }

        public void setPic300(String pic300) {
            this.pic300 = pic300;
        }

        public void setPic400(String pic400) {
            this.pic400 = pic400;
        }

        public void setPic320(String pic320) {
            this.pic320 = pic320;
        }
    }

    public static class Type {
        public static final int PTV = 2;
        public static final int TOPIC_MANG = 4;
        public static final int VRS_MANG = 1;
        public static final int VRS_ONE = 3;
    }

    public boolean canPlay() {
        return this.play == 1;
    }

    public boolean needJump() {
        return this.jump == 1;
    }

    public boolean needPay() {
        return this.pay == 1;
    }

    public boolean canDownload() {
        return this.download == 1;
    }

    public static AlbumInfo parse(JSONObject object) {
        AlbumInfo album = new AlbumInfo();
        if (TextUtils.isEmpty(object.optString("id"))) {
            album.pid = object.optLong("pid");
        } else {
            album.pid = object.optLong("id");
        }
        album.nameCn = object.optString("nameCn");
        album.albumType = object.optString("albumType");
        album.subTitle = object.optString("subTitle");
        album.score = object.optString("score");
        album.cid = object.optInt("cid");
        album.type = object.optInt("type");
        album.at = object.optInt(PushDataParser.AT);
        album.releaseDate = object.optString("releaseDate");
        album.platformVideoNum = object.optInt("platformVideoNum");
        album.platformVideoInfo = object.optInt("platformVideoInfo");
        album.episode = object.optString("episode");
        album.nowEpisodes = object.optString("nowEpisodes");
        album.isEnd = object.optInt("isEnd");
        album.duration = object.optLong(DownloadVideoTable.COLUMN_DURATION);
        album.directory = object.optString("directory");
        album.starring = object.optString("starring");
        album.description = object.optString("description");
        album.area = object.optString("area");
        album.language = object.optString("language");
        album.instructor = object.optString("instructor");
        album.subCategory = object.optString("subCategory");
        album.style = object.optString("style");
        album.playTv = object.optString("playTv");
        album.school = object.optString("school");
        album.controlAreas = object.optString("controlAreas");
        album.disableType = object.optInt("disableType");
        album.play = object.optInt("play");
        album.jump = object.optInt("jump");
        album.pay = object.optInt("pay");
        album.download = object.optInt("download");
        album.tag = object.optString("tag");
        album.travelType = object.optString("travelType");
        album.playCount = object.optLong("playCount");
        album.varietyShow = object.optInt("varietyShow");
        album.cornerMark = object.optString("cornerMark");
        album.noCopyright = TextUtils.equals(object.optString(PlayConstant.NO_COPYRIGHT), "1");
        album.externalUrl = object.optString("external_url");
        JSONObject picAll = object.optJSONObject("picCollections");
        if (picAll == null || JSONObject.NULL.equals(picAll)) {
            picAll = object.optJSONObject("picAll");
        }
        if (!(picAll == null || JSONObject.NULL.equals(picAll))) {
            album.pic150_200 = picAll.optString("150*200");
            album.pic300_300 = picAll.optString("300*300");
            album.pic320_200 = picAll.optString("320*200");
            album.pic400_300 = picAll.optString("400*300");
            album.pic_300_400 = picAll.optString("300*400");
            if (!TextUtils.isEmpty(picAll.optString("120*90"))) {
                album.pic320_200 = picAll.optString("120*90");
            }
            if (!TextUtils.isEmpty(picAll.optString("200*150"))) {
                album.pic320_200 = picAll.optString("200*150");
            }
        }
        return album;
    }

    public VideoBean convertToVideoBean() {
        VideoBean video = new VideoBean();
        video.vid = this.vid;
        video.pid = this.pid;
        video.cid = this.cid;
        video.nameCn = this.nameCn;
        video.subTitle = this.subTitle;
        video.type = this.type;
        video.duration = this.duration;
        video.episode = this.episode;
        video.albumPay = this.pay;
        video.download = this.download;
        video.pic320_200 = this.pic320_200;
        video.pic300_300 = this.pic300_300;
        video.pic300_400 = this.pic_300_400;
        video.play = this.play;
        video.jump = this.jump;
        video.controlAreas = this.controlAreas;
        video.disableType = this.disableType;
        video.playCount = this.playCount;
        video.at = this.at;
        video.title = this.title;
        video.pidname = this.pidname;
        video.subname = this.subname;
        video.nowEpisodes = this.nowEpisodes;
        video.director = this.directory;
        video.starring = this.starring;
        video.score = this.score;
        video.area = this.area;
        video.releaseDate = this.releaseDate;
        video.style = this.style;
        video.subCategory = this.subCategory;
        video.cornerMark = this.cornerMark;
        video.isEnd = this.isEnd;
        video.playTv = this.playTv;
        video.pidsubtitle = this.pidsubtitle;
        return video;
    }

    public String getChannelTypeTitle() {
        return getChannelTypeTitle((byte) 1);
    }

    public String getChannelTypeTitle(String episodeNum) {
        this.nowEpisodes = episodeNum;
        return getChannelTypeTitle((byte) 1);
    }

    public String getMultiPageChannelTypeTitle(String episodeNum) {
        this.nowEpisodes = episodeNum;
        return getChannelTypeTitle((byte) 2);
    }

    public String getMultiPageChannelTypeTitle() {
        return getChannelTypeTitle((byte) 2);
    }

    public String getChannelTypeTitle(byte formatType) {
        String ret = "";
        switch (this.cid) {
            case 1:
            case 1000:
            case 1001:
            case 2001:
                return LetvUtils.getString(R.string.my_collect_c_total_count_2, this.episode);
            case 2:
            case 5:
                return getTipStr(formatType);
            case 16:
                if (this.varietyShow == 0) {
                    return getTipStr(formatType);
                }
                return ret;
            default:
                return ret;
        }
    }

    String getTipStr(byte formatType) {
        switch (formatType) {
            case (byte) 1:
                if (this.isEnd != 0) {
                    return LetvUtils.getString(R.string.my_collect_c_total_count, this.nowEpisodes);
                }
                if (this.episode.equals("0")) {
                    return LetvUtils.getString(R.string.my_collect_c_cur_episode, this.nowEpisodes);
                }
                return LetvUtils.getString(R.string.my_collect_c_cur_episode_totle, this.nowEpisodes, this.episode);
            case (byte) 2:
                if (this.isEnd == 0) {
                    return "(" + LetvUtils.getString(R.string.my_collect_c_cur_episode, this.nowEpisodes) + ")";
                }
                return LetvUtils.getString(R.string.my_collect_c_total_count_1, this.episode);
            default:
                return "";
        }
    }

    public String getUrl() {
        String url = "";
        if (!TextUtils.isEmpty(this.pic320_200)) {
            return this.pic320_200;
        }
        if (!TextUtils.isEmpty(this.pic150_200)) {
            return this.pic150_200;
        }
        if (!TextUtils.isEmpty(this.pic400_300)) {
            return this.pic400_300;
        }
        if (TextUtils.isEmpty(this.pic300_300)) {
            return url;
        }
        return this.pic300_300;
    }

    public boolean isPanorama() {
        return !BaseTypeUtils.isListEmpty(this.vTypeFlagList) && this.vTypeFlagList.contains("2");
    }

    public boolean isMovieOrTvOrCartoon() {
        return isMovieOrTvOrCartoon(this.cid);
    }

    public static boolean isMovieOrTvOrCartoon(int cid) {
        return cid == 1 || cid == 2 || cid == 5;
    }
}
