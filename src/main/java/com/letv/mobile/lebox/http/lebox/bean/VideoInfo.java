package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class VideoInfo implements LetvHttpBaseModel {
    private String btime;
    private String duration;
    private String episode;
    private String etime;
    private String id;
    private String nameCn;
    private String porder;
    private String stream;
    private String updateTime;

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getPorder() {
        return this.porder;
    }

    public void setPorder(String porder) {
        this.porder = porder;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStream() {
        return this.stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getBtime() {
        return this.btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getEtime() {
        return this.etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String toString() {
        return "VideoInfo [nameCn=" + this.nameCn + ", id=" + this.id + ", episode=" + this.episode + ", porder=" + this.porder + ", updateTime=" + this.updateTime + ", stream=" + this.stream + ", btime=" + this.btime + ", etime=" + this.etime + ", duration=" + this.duration + "]";
    }
}
