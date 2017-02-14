package com.letv.android.client.worldcup.bean;

import com.letv.core.bean.LetvBaseBean;
import java.util.ArrayList;

public class WorldCupMetaData implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private int at;
    private int cid;
    private String duration;
    private String episode;
    private int isEnd;
    private int jump;
    private String mobilePic;
    private String nameCn;
    private String nowEpisodes;
    private int pay;
    private int pid;
    private int play;
    private ArrayList<SiftKVP> showTagList;
    private String singer;
    private String streamCode;
    private String streamUrl;
    private String subTitle;
    private String tag;
    private String tm;
    private int type;
    private int vid;
    private String webUrl;
    private String webViewUrl;
    private String zid;

    public String getZid() {
        return this.zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getStreamUrl() {
        return this.streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getVid() {
        return this.vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAt() {
        return this.at;
    }

    public void setAt(int at) {
        this.at = at;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getNowEpisodes() {
        return this.nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public int getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public int getPlay() {
        return this.play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getJump() {
        return this.jump;
    }

    public void setJump(int jump) {
        this.jump = jump;
    }

    public int getPay() {
        return this.pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMobilePic() {
        return this.mobilePic;
    }

    public void setMobilePic(String mobilePic) {
        this.mobilePic = mobilePic;
    }

    public String getStreamCode() {
        return this.streamCode;
    }

    public void setStreamCode(String streamCode) {
        this.streamCode = streamCode;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebViewUrl() {
        return this.webViewUrl;
    }

    public void setWebViewUrl(String webViewUrl) {
        this.webViewUrl = webViewUrl;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public ArrayList<SiftKVP> getShowTagList() {
        return this.showTagList;
    }

    public void setShowTagList(ArrayList<SiftKVP> showTagList) {
        this.showTagList = showTagList;
    }

    public String getTm() {
        return this.tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
