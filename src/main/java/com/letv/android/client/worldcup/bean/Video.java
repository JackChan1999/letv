package com.letv.android.client.worldcup.bean;

import com.letv.core.bean.LetvBaseBean;

public class Video implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private String AlbumType;
    private String area;
    private int at;
    private String brList;
    private long btime;
    private int cid;
    private String controlAreas;
    private String description;
    private int disableType;
    private int download;
    private long duration;
    private String episode;
    private long etime;
    private long id;
    private int jump;
    private String mid;
    private String nameCn;
    private int pay;
    private String pic;
    private String pic300;
    private long pid;
    private int play;
    private String releaseDate;
    private String singer;
    private int style;
    private String subCategory;
    private String subTitle;
    private String tag;
    private int type;

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAlbumType() {
        return this.AlbumType;
    }

    public void setAlbumType(String albumType) {
        this.AlbumType = albumType;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic300() {
        return this.pic300;
    }

    public void setPic300(String pic300) {
        this.pic300 = pic300;
    }

    public long getBtime() {
        return this.btime;
    }

    public void setBtime(long btime) {
        this.btime = btime;
    }

    public long getEtime() {
        return this.etime;
    }

    public void setEtime(long etime) {
        this.etime = etime;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getPid() {
        return this.pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
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

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getPlay() {
        return this.play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public boolean canPlay() {
        return this.play == 1;
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

    public int getDownload() {
        return this.download;
    }

    public boolean canDownload() {
        return this.download == 1;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getControlAreas() {
        return this.controlAreas;
    }

    public void setControlAreas(String controlAreas) {
        this.controlAreas = controlAreas;
    }

    public int getDisableType() {
        return this.disableType;
    }

    public void setDisableType(int disableType) {
        this.disableType = disableType;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getBrList() {
        return this.brList;
    }

    public void setBrList(String brlist) {
        this.brList = brlist;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public boolean needJump() {
        return this.jump == 1;
    }

    public boolean needPay() {
        return this.pay == 1;
    }
}
