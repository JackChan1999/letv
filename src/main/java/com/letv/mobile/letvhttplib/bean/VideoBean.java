package com.letv.mobile.letvhttplib.bean;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.parser.PushDataParser;
import com.letv.download.db.Download.DownloadBaseColumns;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class VideoBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public int albumPay;
    public int allowVote;
    public String area = "";
    public int at;
    public String brList = "";
    public long btime;
    public String bucket = "";
    public int cid;
    public String controlAreas = "";
    public String cornerMark = "";
    public String createMonth = "";
    public String createYear = "";
    public String dataArea = "";
    public String director = "";
    public int disableType;
    public int download = 1;
    public long duration;
    public String episode = "";
    public long etime;
    public String guest = "";
    public int isDanmaku;
    public int isEnd;
    public boolean isHaveSurrVideo = false;
    public boolean isRec = false;
    public int itemType;
    public int jump;
    public String jumpLink = "";
    public String jumptype = "";
    public String mid;
    public String nameCn = "";
    public String nowEpisodes = "";
    public int openby;
    public int page;
    public int pay;
    public String pic120_90 = "";
    public String pic200_150 = "";
    public String pic300_300 = "";
    public String pic320_200 = "";
    public String picHT;
    public String picST;
    public long pid;
    public String pidname = "";
    public String pidsubtitle = "";
    public int play = 1;
    public long playCount;
    public String playMark = "";
    public String playTv = "";
    public String porder = "";
    public String reid = "";
    public String releaseDate = "";
    public String score = "";
    public String singer = "";
    public String starring = "";
    public String style = "";
    public String subCategory = "";
    public String subTitle = "";
    public String subname = "";
    public String title = "";
    public int type;
    public long vid;
    @JSONField(name = "picAll")
    public PicAllBean videoPicAll;
    public String videoType = "";
    public String videoTypeKey = "";
    public String videoTypeName = "";
    public String votePoptext = "";
    public String vtypeFlag = "";
    @JSONField(name = "watchingFocus")
    public ArrayList<WatchingFocusItem> watchingFocusList = new ArrayList();
    public String zid;

    public static class PicAllBean implements LetvHttpBaseModel {
        private static final long serialVersionUID = 1;
        @JSONField(name = "120*90")
        public String pic120;
        @JSONField(name = "200*150")
        public String pic200;
        @JSONField(name = "320*200")
        public String pic320;
    }

    public boolean canPlay() {
        return this.play == 1;
    }

    public boolean canDownload() {
        return this.download == 1;
    }

    public String getEpisode() {
        return this.episode != null ? this.episode : "";
    }

    public boolean needJump() {
        return this.jump == 1;
    }

    public boolean needPay() {
        return this.pay == 1;
    }

    public boolean isFeature() {
        return TextUtils.equals(this.videoTypeKey, LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN);
    }

    public boolean isPreview() {
        return TextUtils.equals(this.videoTypeKey, LetvConstant.VIDEO_TYPE_KEY_PREVIEW);
    }

    public static VideoBean parse(JSONObject obj) {
        VideoBean video = new VideoBean();
        video.vid = obj.optLong("vid");
        video.pid = obj.optLong("pid");
        video.cid = obj.optInt("cid");
        video.zid = obj.optString(PlayConstant.ZID);
        video.nameCn = obj.optString("nameCn");
        video.subTitle = obj.optString("subTitle");
        video.singer = obj.optString("singer");
        video.releaseDate = obj.optString("releaseDate");
        video.style = obj.optString("style");
        video.playMark = obj.optString("playMark");
        video.vtypeFlag = obj.optString("vtypeFlag");
        video.guest = obj.optString("guest");
        video.type = obj.optInt("type");
        video.btime = obj.optLong(DownloadVideoTable.COLUMN_BTIME);
        video.etime = obj.optLong(DownloadVideoTable.COLUMN_ETIME);
        video.duration = obj.optLong(DownloadVideoTable.COLUMN_DURATION);
        video.mid = obj.optString("mid");
        video.episode = obj.optString("episode");
        video.porder = obj.optString("porder");
        video.pay = obj.optInt("pay");
        video.albumPay = obj.optInt("album_pay");
        video.download = obj.optInt("download");
        JSONObject picAll = obj.optJSONObject("picAll");
        if (picAll != null) {
            video.pic320_200 = picAll.optString("320*200");
            video.pic120_90 = picAll.optString("120*90");
        }
        if (!TextUtils.isEmpty(obj.optString("mobilePic"))) {
            video.pic320_200 = obj.optString("mobilePic");
        }
        video.play = obj.optInt("play");
        video.openby = obj.optInt("openby");
        video.jump = obj.optInt("jump");
        video.jumptype = obj.optString("jumptype");
        video.jumpLink = obj.optString("jumplink");
        video.isDanmaku = obj.optInt("isDanmaku");
        video.brList = obj.optString("brList");
        video.videoTypeKey = obj.optString(DownloadVideoTable.COLUMN_VIDEOTYPEKEY);
        video.videoType = obj.optString(PlayConstant.VIDEO_TYPE);
        video.videoTypeName = obj.optString("videoTypeName");
        video.controlAreas = obj.optString("controlAreas");
        video.disableType = obj.optInt("disableType");
        video.cornerMark = obj.optString("cornerMark");
        video.playCount = obj.optLong("playCount");
        video.score = obj.optString("score");
        video.director = obj.optString("director");
        video.starring = obj.optString("starring");
        video.reid = obj.optString("reid");
        video.bucket = obj.optString("bucket");
        video.area = obj.optString("area");
        video.isRec = obj.optBoolean("is_rec", false);
        video.dataArea = obj.optString("dataArea");
        video.subCategory = obj.optString("subCategory");
        video.title = obj.optString("title");
        video.pidname = obj.optString("pidname");
        video.subname = obj.optString("subname");
        video.pidsubtitle = obj.optString("pidsubtitle");
        video.picHT = obj.optString("picHT");
        video.picST = obj.optString("picST");
        video.at = obj.optInt(PushDataParser.AT);
        video.createYear = obj.optString("createYear");
        video.createMonth = obj.optString("createMonth");
        JSONArray jsonFocusrray = obj.optJSONArray("watchingFocus");
        if (jsonFocusrray != null && jsonFocusrray.length() > 0) {
            int len = jsonFocusrray.length();
            for (int j = 0; j < len; j++) {
                JSONObject jsonFocusObj = jsonFocusrray.optJSONObject(j);
                WatchingFocusItem mWatchingFocusItem = new WatchingFocusItem();
                mWatchingFocusItem.desc = jsonFocusObj.optString(SocialConstants.PARAM_APP_DESC);
                mWatchingFocusItem.id = jsonFocusObj.optInt("id");
                mWatchingFocusItem.picUrl = jsonFocusObj.optString(DownloadBaseColumns.COLUMN_PIC);
                mWatchingFocusItem.timeDot = jsonFocusObj.optString("time");
                video.watchingFocusList.add(mWatchingFocusItem);
            }
        }
        return video;
    }

    public String getUrl() {
        String url = "";
        if (!TextUtils.isEmpty(this.pic320_200)) {
            return this.pic320_200;
        }
        if (!TextUtils.isEmpty(this.pic200_150)) {
            return this.pic200_150;
        }
        if (!TextUtils.isEmpty(this.pic120_90)) {
            return this.pic120_90;
        }
        if (TextUtils.isEmpty(this.pic300_300)) {
            return url;
        }
        return this.pic300_300;
    }
}
