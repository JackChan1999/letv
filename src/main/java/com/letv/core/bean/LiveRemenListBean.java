package com.letv.core.bean;

import android.text.TextUtils;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.utils.LogInfo;
import com.letv.pp.utils.NetworkUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LiveRemenListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public List<LiveRemenBaseBean> mRemenList = new ArrayList();

    public static class LiveRemenBaseBean implements LetvBaseBean {
        public static final String IS_BOOKED = "1";
        public static final String IS_PAY = "1";
        public static final String PLAY_NO_START = "1";
        public static final String PLAY_OVER = "3";
        public static final String PLAY_PLAYING = "2";
        public static final String STATUS_AUTHORED = "1";
        private static SimpleDateFormat sdf_all = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private static SimpleDateFormat sdf_date = new SimpleDateFormat("MM-dd");
        private static SimpleDateFormat sdf_fdate = new SimpleDateFormat("yyyy-MM-dd");
        private static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
        private static SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
        private static final long serialVersionUID = 1;
        public String address;
        public int allowVote;
        public String at;
        public boolean authored;
        public String beginTime;
        public int branchType;
        public ArrayList<MultiProgramBranchBean> branches;
        public String buyFlag;
        public String ch;
        public String channelId;
        public String channelName;
        public String channelTvName;
        public String chatRoomNum;
        public String cmsid;
        public String commentaryLanguage = null;
        public String description;
        public String endTime;
        public String extendSubscript;
        public String extends_extendRange;
        public String focusPic;
        public String guest = null;
        public String guestImgUrl = null;
        public String guestscore = null;
        public String home = null;
        public String homeImgUrl = null;
        public String homescore = null;
        public String id;
        public int isBranch;
        public int isChat;
        public int isDanmaku;
        public int isPanoramicView;
        public String isPay;
        public String isPlayback;
        public String isVS;
        public String is_rec;
        public String isbooked = null;
        public String level1;
        public String level2;
        public String liveType;
        public String match = null;
        public String mobilePic;
        public String nameCn;
        public String partId;
        public String pid;
        public String play_date = null;
        public String play_time = null;
        public String preVID;
        public String rateType;
        public String recordingId;
        public String screenings;
        public String seanson;
        public String selectId;
        public String specialPageUrl;
        public String status;
        public String streamId;
        public String subTitle;
        public String subsciptColor;
        public String title;
        public String type;
        public String typeICON;
        public String typeValue;
        public int userOnlineNum;
        public String vid;
        public String vipFree;
        public String webUrl;
        public String webViewUrl;
        public String weight;
        public String year;

        public String getBeginTime() {
            return this.beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
            this.play_date = parseDate(beginTime);
            this.play_time = parseTime(beginTime);
            this.year = parseYear(beginTime);
        }

        public String getEndTime() {
            return this.endTime;
        }

        public String getName1() {
            if (LiveRoomConstant.LIVE_TYPE_SPORT.equals(this.ch)) {
                return getPlayDate() + " " + getPlayTime() + " " + (TextUtils.isEmpty(this.level2) ? "" : this.level2) + " " + (TextUtils.isEmpty(this.commentaryLanguage) ? "" : "[" + this.commentaryLanguage + "]");
            } else if (LiveRoomConstant.LIVE_TYPE_MUSIC.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_GAME.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_ENT.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_INFORMATION.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_FINANCE.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_VARIETY.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_BRAND.equals(this.ch)) {
                return this.play_date + " " + this.play_time + " " + this.typeValue;
            } else {
                return "";
            }
        }

        public String getName2() {
            if (LiveRoomConstant.LIVE_TYPE_SPORT.equals(this.ch)) {
                if (!"1".equals(this.isVS)) {
                    return this.title;
                }
                if ("1".equals(this.status) || "2".equals(this.status)) {
                    return "VS";
                }
                return "" + (TextUtils.isEmpty(this.homescore) ? Integer.valueOf(0) : this.homescore) + NetworkUtils.DELIMITER_LINE + (TextUtils.isEmpty(this.homescore) ? Integer.valueOf(0) : this.guestscore);
            } else if (LiveRoomConstant.LIVE_TYPE_MUSIC.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_GAME.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_ENT.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_INFORMATION.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_FINANCE.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_VARIETY.equals(this.ch) || LiveRoomConstant.LIVE_TYPE_BRAND.equals(this.ch)) {
                return this.title;
            } else {
                return "";
            }
        }

        public String getPlayDate() {
            if (TextUtils.isEmpty(this.beginTime) || this.beginTime.length() != 19) {
                return parseDate(this.beginTime);
            }
            return this.beginTime.substring(5, 10);
        }

        public String getFullPlayDate() {
            return parseFullDate(this.beginTime);
        }

        public String getPlayTime() {
            if (TextUtils.isEmpty(this.beginTime) || this.beginTime.length() != 19) {
                return parseTime(this.beginTime);
            }
            return this.beginTime.substring(11, 16);
        }

        public String getPlayYear() {
            return parseTime(this.beginTime);
        }

        public String getChannelName(String channel) {
            String name = this.channelName;
            if (TextUtils.equals(this.ch, LiveRoomConstant.LIVE_TYPE_SPORT)) {
                return this.level1;
            }
            if (TextUtils.equals(this.ch, LiveRoomConstant.LIVE_TYPE_ENT) || TextUtils.equals(this.ch, LiveRoomConstant.LIVE_TYPE_MUSIC)) {
                return this.typeValue;
            }
            if (TextUtils.equals(this.ch, LiveRoomConstant.LIVE_TYPE_OTHER)) {
                return name;
            }
            return name + channel;
        }

        public String toString() {
            return "LiveRemenBase [id=" + this.id + ", type=" + this.type + ", beginTime=" + this.beginTime + ", endTime=" + this.endTime + ", selectId=" + this.selectId + ", ch=" + this.ch + ", isPay=" + this.isPay + ", title=" + this.title + ", liveType=" + this.liveType + ", description=" + this.description + ", status=" + this.status + ", recordingId=" + this.recordingId + ", vid=" + this.vid + ", pid=" + this.pid + ", weight=" + this.weight + ", typeICON=" + this.typeICON + ", isVS=" + this.isVS + ", level2=" + this.level2 + ", level1=" + this.level1 + ", typeValue=" + this.typeValue + ", channelName=" + this.channelName + ", chatRoomNum=" + this.chatRoomNum + ", authored=" + this.authored + "]";
        }

        public static String parseDate(String date_all) {
            try {
                date_all = sdf_date.format(sdf_all.parse(date_all));
            } catch (ParseException e) {
                LogInfo.log("wxy", "error when parse date:" + date_all);
            }
            return date_all;
        }

        public static String parseFullDate(String date_all) {
            try {
                date_all = sdf_fdate.format(sdf_all.parse(date_all));
            } catch (ParseException e) {
                LogInfo.log("wxy", "error when parse date:" + date_all);
            }
            return date_all;
        }

        public static String parseTime(String date_all) {
            try {
                date_all = sdf_time.format(sdf_all.parse(date_all));
            } catch (ParseException e) {
                LogInfo.log("wxy", "error when parse time:" + date_all);
            }
            return date_all;
        }

        public static String parseYear(String date_all) {
            try {
                date_all = sdf_time.format(sdf_year.parse(date_all));
            } catch (ParseException e) {
                LogInfo.log("wxy", "error when parse time:" + date_all);
            }
            return date_all;
        }
    }

    public void add(LiveRemenBaseBean bean) {
        this.mRemenList.add(bean);
    }
}
