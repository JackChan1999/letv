package com.letv.mobile.lebox.ui.album;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.letv.core.parser.PushDataParser;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.letvhttplib.bean.LetvBaseBean;
import com.letv.mobile.letvhttplib.bean.VideoBean;
import com.letv.mobile.letvhttplib.utils.BaseTypeUtils;
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
    public int filmstyle;
    private String fitAge;
    public String icon_200_150;
    public String icon_400_300;
    public String instructor;
    public boolean isDolby;
    public int isEnd;
    private int isHomemade;
    public int jump;
    public String language;
    public String nameCn;
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
    private String supervise;
    public String tag;
    public String title;
    public String totalNum = "0";
    public String travelType;
    public int type;
    public List<String> vTypeFlagList = new ArrayList();
    public int varietyShow;
    public long vid;

    public static class PicCollectionsBean implements LetvHttpBaseModel {
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
        JSONObject picAll = object.optJSONObject("picCollections");
        if (picAll == null || JSONObject.NULL.equals(picAll)) {
            picAll = object.optJSONObject("picAll");
        }
        if (!(picAll == null || JSONObject.NULL.equals(picAll))) {
            album.pic150_200 = picAll.optString("150*200");
            album.pic300_300 = picAll.optString("300*300");
            album.pic320_200 = picAll.optString("320*200");
            album.pic400_300 = picAll.optString("400*300");
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
        getClass();
        return getChannelTypeTitle((byte) 1);
    }

    public String getChannelTypeTitle(String episodeNum) {
        this.nowEpisodes = episodeNum;
        getClass();
        return getChannelTypeTitle((byte) 1);
    }

    public String getMultiPageChannelTypeTitle(String episodeNum) {
        this.nowEpisodes = episodeNum;
        getClass();
        return getChannelTypeTitle((byte) 2);
    }

    public String getMultiPageChannelTypeTitle() {
        getClass();
        return getChannelTypeTitle((byte) 2);
    }

    public String getChannelTypeTitle(byte formatType) {
        String ret = "";
        switch (this.cid) {
            case 1:
            case 1000:
            case 1001:
            case 2001:
                return Util.getString(R.string.lebox_collect_c_total_count_2, this.episode);
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
                    return Util.getString(R.string.lebox_collect_c_total_count, this.nowEpisodes);
                }
                if (this.episode.equals("0")) {
                    return Util.getString(R.string.lebox_collect_c_cur_episode, this.nowEpisodes);
                }
                return Util.getString(R.string.lebox_collect_c_cur_episode_totle, this.nowEpisodes, this.episode);
            case (byte) 2:
                if (this.isEnd == 0) {
                    return "(" + Util.getString(R.string.lebox_collect_c_cur_episode, this.nowEpisodes) + ")";
                }
                return Util.getString(R.string.lebox_collect_c_total_count_1, this.episode);
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
