package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class RecAlbumBean implements LetvBaseBean {
    private static final long serialVersionUID = -1949128158691084298L;
    public String albumType;
    public String albumTypeKey;
    public String alias;
    public String area;
    public String at;
    public String cast;
    public String cid;
    public String compere;
    public String controlAreas;
    public String cornerMark;
    public String description;
    public String directory;
    public String disableType;
    public String download;
    public String dub;
    public String duration;
    public String episode;
    public String fitAge;
    public String instructor;
    public String isEnd;
    public String isHomemade;
    public String jump;
    public String language;
    public String nameCn;
    public String nowEpisodes;
    public String originator;
    public String pay;
    public String pic150;
    public String pic300;
    public String pic320;
    public String pic400;
    @JSONField(name = "picCollections")
    public PicCollections picCollections = new PicCollections();
    public String pid;
    public String platformVideoInfo;
    public String platformVideoNum;
    public String play;
    public String playCount;
    public String playStatus;
    public String playTv;
    public String rCompany;
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
    public String supervise;
    public String tag;
    public String type;
    public String varietyShow;

    public static class PicCollections implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        @JSONField(name = "150*200")
        public String pic150;
        @JSONField(name = "300*300")
        public String pic300;
        @JSONField(name = "320*200")
        public String pic320;
        @JSONField(name = "400*300")
        public String pic400;
    }

    public String getPic150() {
        this.pic150 = this.picCollections.pic150;
        return this.pic150;
    }

    public void setPic150(String pic150) {
        this.picCollections.pic150 = pic150;
        this.pic150 = pic150;
    }

    public String getPic300() {
        this.pic300 = this.picCollections.pic300;
        return this.pic300;
    }

    public void setPic300(String pic300) {
        this.picCollections.pic300 = pic300;
        this.pic300 = pic300;
    }

    public String getPic400() {
        this.pic400 = this.picCollections.pic400;
        return this.pic400;
    }

    public void setPic400(String pic400) {
        this.picCollections.pic400 = pic400;
        this.pic400 = pic400;
    }

    public String getPic320() {
        this.pic320 = this.picCollections.pic320;
        return this.pic320;
    }

    public void setPic320(String pic320) {
        this.picCollections.pic320 = pic320;
        this.pic320 = pic320;
    }
}
