package cn.com.iresearch.vvtracker.dao;

import cn.com.iresearch.vvtracker.db.annotation.sqlite.Table;

@Table(name = "VideoPlayInfo")
public class VideoPlayInfo extends a {
    private int _id;
    private String action = "";
    private String customVal = "";
    private int heartTime = 0;
    private String lastActionTime = "";
    private int pauseCount = 0;
    private long playTime = 0;
    private String videoID;
    private long videoLength = 0;

    public int get_id() {
        return this._id;
    }

    public void set_id(int i) {
        this._id = i;
    }

    public String getVideoID() {
        return this.videoID;
    }

    public void setVideoID(String str) {
        this.videoID = str;
    }

    public long getVideoLength() {
        return this.videoLength;
    }

    public void setVideoLength(long j) {
        this.videoLength = j;
    }

    public String getCustomVal() {
        return this.customVal;
    }

    public void setCustomVal(String str) {
        this.customVal = str;
    }

    public long getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(long j) {
        this.playTime = j;
    }

    public int getPauseCount() {
        return this.pauseCount;
    }

    public void setPauseCount(int i) {
        this.pauseCount = i;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public String getLastActionTime() {
        return this.lastActionTime;
    }

    public void setLastActionTime(String str) {
        this.lastActionTime = str;
    }

    public int getHeartTime() {
        return this.heartTime;
    }

    public void setHeartTime(int i) {
        this.heartTime = i;
    }
}
