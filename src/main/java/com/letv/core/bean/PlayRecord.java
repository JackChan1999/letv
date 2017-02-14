package com.letv.core.bean;

public class PlayRecord implements LetvBaseBean {
    private static final long serialVersionUID = -5563191253372442515L;
    public int albumId;
    public int channelId;
    public float curEpsoid;
    public int from;
    public String img;
    public String img300;
    public long playedDuration;
    public String title;
    public long totalDuration;
    public int type;
    public long updateTime;
    public String userId;
    public int videoId;
    public int videoNextId;
    public int videoType;
    public String videoTypeKey;

    public PlayDeviceFrom getFrom() {
        return PlayDeviceFrom.getDeviceFromById(this.from);
    }

    public String toString() {
        return "PlayRecord [channelId=" + this.channelId + ", albumId=" + this.albumId + ", videoId=" + this.videoId + ", videoNextId=" + this.videoNextId + ", userId=" + this.userId + ", from=" + this.from + ", videoType=" + this.videoType + ", totalDuration=" + this.totalDuration + ", playedDuration=" + this.playedDuration + ", updateTime=" + this.updateTime + ", title=" + this.title + ", img=" + this.img + ", type=" + this.type + ", curEpsoid=" + this.curEpsoid + "]";
    }

    public boolean equals(Object o) {
        if (((PlayRecord) o).albumId != this.albumId || ((PlayRecord) o).videoId != this.videoId || ((PlayRecord) o).channelId != this.channelId || ((PlayRecord) o).curEpsoid != this.curEpsoid || ((PlayRecord) o).videoNextId != this.videoNextId || ((PlayRecord) o).videoType != this.videoType || ((PlayRecord) o).from != this.from || ((PlayRecord) o).totalDuration != this.totalDuration || ((PlayRecord) o).playedDuration != this.playedDuration || ((PlayRecord) o).updateTime != this.updateTime) {
            return false;
        }
        if (this.img300 != null && ((PlayRecord) o).img300 != null && !this.img300.equals(((PlayRecord) o).img300)) {
            return false;
        }
        if (this.videoTypeKey != null && ((PlayRecord) o).videoTypeKey != null && !((PlayRecord) o).videoTypeKey.equals(this.videoTypeKey)) {
            return false;
        }
        if (this.userId != null && ((PlayRecord) o).userId != null && !((PlayRecord) o).userId.equals(this.userId)) {
            return false;
        }
        if (this.title != null && ((PlayRecord) o).title != null && !((PlayRecord) o).title.equals(this.title)) {
            return false;
        }
        if (this.img == null || ((PlayRecord) o).img == null || ((PlayRecord) o).img.equals(this.img)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 1;
    }
}
