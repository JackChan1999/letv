package com.letv.mobile.lebox.http.lebox.bean;

public class TaskPro {
    private String pr;
    private String progress;
    private String speed;
    private String totalSize;
    private String vid;

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPr() {
        return this.pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    public String getProgress() {
        return this.progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getSpeed() {
        return this.speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String toString() {
        return "TaskPro [vid=" + this.vid + ", pr=" + this.pr + ", progress=" + this.progress + ", totalSize=" + this.totalSize + ", speed=" + this.speed + "]";
    }
}
