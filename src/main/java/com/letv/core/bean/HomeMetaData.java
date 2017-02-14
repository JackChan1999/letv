package com.letv.core.bean;

import android.text.TextUtils;
import com.letv.core.constant.PlayConstant;
import com.letv.core.parser.PushDataParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.lemallsdk.util.Constants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class HomeMetaData implements LetvBaseBean {
    public static final int BRAND = 30;
    public static final int FINANCE = 33;
    public static final int GAME = 31;
    public static final int GAME_CENTER = 34;
    public static final int GOTO_LIVE_ENTERTAINMENT = 23;
    public static final int GOTO_LIVE_MUSIC = 22;
    public static final int GOTO_LIVE_REMEN = 20;
    public static final int GOTO_LIVE_SPORT = 21;
    public static final int GOTO_PAGE_CHANNEL = 7;
    public static final int GOTO_PAGE_LOGIN = 19;
    public static final int GOTO_PAGE_PAY = 12;
    public static final int GOTO_PAGE_RECOMMEND = 6;
    public static final int GOTO_PAGE_SCORE = 14;
    public static final int GOTO_PAGE_VIP = 11;
    public static final int GOTO_PAGE_WO_UNION = 13;
    public static final int GOTO_PLAYER_FULL = 2;
    public static final int GOTO_PLAYER_HALF = 1;
    public static final int GOTO_PLAYER_LIVE = 3;
    public static final int GOTO_REMEN = 25;
    public static final int GOTO_WEB_INNER = 5;
    public static final int GOTO_WEB_OUTER = 4;
    public static final int GOTO_ZHUANTI = 26;
    public static final int INFO = 32;
    public static final int LEMALL_SDK = 35;
    public static final int STAR = 28;
    public static final int STAR_RANK = 29;
    private static final long serialVersionUID = 1;
    public String albumType;
    public int at;
    public int cid;
    public String cmsid;
    public String cornerMark;
    public String director;
    public String duration;
    public String episode;
    public String extendSubscript;
    public String externalUrl;
    public List<Integer> gotoList = new ArrayList();
    public String guestImgUrl = null;
    public String homeImgUrl = null;
    public String id = null;
    public int index;
    public String isAlbum;
    public int isEnd;
    public boolean isFristShow;
    public String is_rec;
    public int jump;
    public String leId;
    public String leftSubscipt;
    public String leftSubsciptColor;
    public String liveid = null;
    public String mFragId;
    public String mobilePic;
    public String nameCn;
    public boolean noCopyright;
    public String nowEpisodes;
    public String padPic;
    public String pageid;
    public String partId;
    public int pay;
    public String pic1;
    public String pic169 = "";
    public String pic300_300 = "";
    public String pic400_225 = "";
    public String pic400_250 = "";
    public String pic400_300 = "";
    public int pid;
    public int play;
    public long playCount;
    public String playbill;
    public String score;
    public String shorDesc;
    public ArrayList<SiftKVP> showTagList;
    public String singer;
    public String starring;
    public String streamCode;
    public String streamUrl;
    public int style = 1;
    public String subTitle;
    public String sub_category;
    public String subsciptColor;
    public String tag;
    public String tm;
    public int type;
    public List<String> vTypeFlagList = new ArrayList();
    public boolean varietyShow;
    public int vid;
    public String videoType;
    public String webUrl;
    public String webViewUrl;
    public String zid;

    public HomeMetaData() {
        this.gotoList.add(Integer.valueOf(7));
        this.gotoList.add(Integer.valueOf(19));
        this.gotoList.add(Integer.valueOf(12));
        this.gotoList.add(Integer.valueOf(6));
        this.gotoList.add(Integer.valueOf(14));
        this.gotoList.add(Integer.valueOf(11));
        this.gotoList.add(Integer.valueOf(13));
        this.gotoList.add(Integer.valueOf(2));
        this.gotoList.add(Integer.valueOf(1));
        this.gotoList.add(Integer.valueOf(3));
        this.gotoList.add(Integer.valueOf(5));
        this.gotoList.add(Integer.valueOf(4));
        this.gotoList.add(Integer.valueOf(20));
        this.gotoList.add(Integer.valueOf(21));
        this.gotoList.add(Integer.valueOf(22));
        this.gotoList.add(Integer.valueOf(23));
        this.gotoList.add(Integer.valueOf(25));
        this.gotoList.add(Integer.valueOf(26));
        this.gotoList.add(Integer.valueOf(28));
        this.gotoList.add(Integer.valueOf(29));
        this.gotoList.add(Integer.valueOf(31));
        this.gotoList.add(Integer.valueOf(33));
        this.gotoList.add(Integer.valueOf(32));
        this.gotoList.add(Integer.valueOf(30));
        this.gotoList.add(Integer.valueOf(34));
        this.gotoList.add(Integer.valueOf(35));
    }

    public boolean isRec() {
        return "true".equals(this.is_rec);
    }

    public static HomeMetaData parse(JSONObject objs, boolean isFromFocus) {
        boolean z = true;
        int i = 0;
        HomeMetaData metadata = new HomeMetaData();
        metadata.shorDesc = objs.optString("shorDesc");
        metadata.at = objs.optInt(PushDataParser.AT);
        metadata.cmsid = objs.optString("cmsid");
        metadata.pid = objs.optInt("pid");
        metadata.vid = objs.optInt("vid");
        metadata.zid = objs.optString(PlayConstant.ZID);
        if (!isFromFocus) {
            metadata.nameCn = objs.optString("nameCn");
            metadata.subTitle = objs.optString("subTitle");
        }
        metadata.cid = objs.optInt("cid");
        metadata.type = objs.optInt("type");
        metadata.albumType = objs.optString("albumType");
        metadata.videoType = objs.optString(PlayConstant.VIDEO_TYPE);
        if (objs.optInt("varietyShow") != 1) {
            z = false;
        }
        metadata.varietyShow = z;
        metadata.episode = objs.optString("episode");
        metadata.nowEpisodes = objs.optString("nowEpisodes");
        metadata.isEnd = objs.optInt("isEnd");
        metadata.pay = objs.optInt("pay");
        metadata.tag = objs.optString("tag");
        metadata.streamCode = objs.optString("streamCode");
        metadata.webUrl = objs.optString("webUrl");
        metadata.webViewUrl = objs.optString("webViewUrl");
        metadata.streamUrl = objs.optString("streamUrl");
        metadata.tm = objs.optString("tm");
        metadata.duration = objs.optString(DownloadVideoTable.COLUMN_DURATION);
        metadata.mobilePic = objs.optString("mobilePic");
        metadata.is_rec = objs.optString("is_rec");
        metadata.pageid = objs.optString(PlayConstant.PAGE_ID);
        metadata.noCopyright = TextUtils.equals(objs.optString(PlayConstant.NO_COPYRIGHT), "1");
        metadata.externalUrl = objs.optString("external_url");
        metadata.liveid = objs.optString("liveid");
        metadata.homeImgUrl = objs.optString("homeImgUrl");
        metadata.guestImgUrl = objs.optString("guestImgUrl");
        metadata.id = objs.optString("id");
        metadata.singer = objs.optString("singer");
        metadata.isAlbum = objs.optString("isalbum");
        metadata.leId = objs.optString("leId");
        metadata.style = objs.optInt("style");
        if (objs.has("extendSubscript")) {
            metadata.extendSubscript = objs.optString("extendSubscript");
        }
        if (objs.has("leftSubscipt")) {
            metadata.leftSubscipt = objs.optString("leftSubscipt");
        }
        if (objs.has("leftSubsciptColor")) {
            metadata.leftSubsciptColor = objs.optString("leftSubsciptColor");
        }
        if (objs.has("subsciptColor")) {
            metadata.subsciptColor = objs.optString("subsciptColor");
        }
        String vtypeFlag = objs.optString("vtypeFlag");
        if (!TextUtils.isEmpty(vtypeFlag)) {
            String[] flagArr = vtypeFlag.split(",");
            if (!BaseTypeUtils.isArrayEmpty(flagArr)) {
                int length = flagArr.length;
                while (i < length) {
                    metadata.vTypeFlagList.add(flagArr[i].trim());
                    i++;
                }
            }
        }
        JSONArray jo = objs.optJSONArray("showTagList");
        if (jo != null && jo.length() > 0) {
            ArrayList<SiftKVP> siftKVPs = new ArrayList();
            for (int i2 = 0; i2 < jo.length(); i2++) {
                JSONObject job = jo.optJSONObject(i2);
                if (job != null) {
                    SiftKVP showTagList = new SiftKVP();
                    showTagList.id = job.optString("id");
                    showTagList.key = job.optString(Constants.VALUE_ID);
                    showTagList.filterKey = job.optString("key");
                    siftKVPs.add(showTagList);
                }
            }
            metadata.showTagList = siftKVPs;
        }
        JSONObject picListObj = objs.optJSONObject("picList");
        if (!(picListObj == null || JSONObject.NULL.equals(picListObj))) {
            metadata.pic400_250 = picListObj.optString("400x250");
            metadata.pic400_225 = picListObj.optString("400x225");
            metadata.pic400_300 = picListObj.optString("400x300");
            metadata.pic300_300 = picListObj.optString("300x300");
            metadata.pic169 = picListObj.optString("pic169");
        }
        return metadata;
    }

    public VideoBean convertToVideoBean() {
        VideoBean video = new VideoBean();
        video.vid = (long) this.vid;
        video.pid = (long) this.pid;
        video.cid = this.cid;
        video.pid = (long) this.pid;
        video.nameCn = this.nameCn;
        video.subname = this.subTitle;
        video.subTitle = this.subTitle;
        video.singer = this.singer;
        video.type = this.type;
        video.duration = BaseTypeUtils.stol(this.duration);
        video.episode = this.episode;
        video.pay = this.pay;
        video.pic320_200 = this.mobilePic;
        video.play = this.play;
        video.jump = this.jump;
        video.videoType = this.videoType;
        video.style = this.style + "";
        return video;
    }

    public boolean isPanorama() {
        return !BaseTypeUtils.isListEmpty(this.vTypeFlagList) && this.vTypeFlagList.contains("2");
    }

    public String getPic(Object object) {
        String pic = this.pic169;
        if (object instanceof String) {
            String name = object.toString();
            if (name.contains("mobilePic")) {
                pic = this.mobilePic;
            } else if (name.contains("pic169")) {
                pic = this.pic169;
            } else if (name.contains("pic400x300")) {
                pic = this.pic400_300;
            } else if (name.contains("pic400x250")) {
                pic = this.pic400_250;
            } else if (name.contains("pic400x225")) {
                pic = this.pic400_225;
            } else if (name.contains("pic300x300")) {
                pic = this.pic300_300;
            }
        }
        return TextUtils.isEmpty(pic) ? this.mobilePic : pic;
    }
}
