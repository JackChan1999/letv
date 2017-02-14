package com.letv.core.bean;

public class ProgramEntity implements LetvBaseBean {
    private static final long serialVersionUID = -2742002225109744137L;
    public String duration;
    public String endTime;
    public String id;
    public int liveChannelId;
    public String playTime;
    public int programType;
    public TheaterIcoEntity theaterIco;
    public String title;
    public String vid;
    public String viewPic;
    public WaterMarkEntity waterMark;

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((((this.duration == null ? 0 : this.duration.hashCode()) + 31) * 31) + (this.endTime == null ? 0 : this.endTime.hashCode())) * 31) + (this.id == null ? 0 : this.id.hashCode())) * 31) + this.liveChannelId) * 31) + (this.playTime == null ? 0 : this.playTime.hashCode())) * 31) + this.programType) * 31) + (this.theaterIco == null ? 0 : this.theaterIco.hashCode())) * 31) + (this.title == null ? 0 : this.title.hashCode())) * 31) + (this.vid == null ? 0 : this.vid.hashCode())) * 31) + (this.viewPic == null ? 0 : this.viewPic.hashCode())) * 31;
        if (this.waterMark != null) {
            i = this.waterMark.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        ProgramEntity other = (ProgramEntity) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
            return true;
        } else if (this.id.equals(other.id)) {
            return true;
        } else {
            return false;
        }
    }
}
