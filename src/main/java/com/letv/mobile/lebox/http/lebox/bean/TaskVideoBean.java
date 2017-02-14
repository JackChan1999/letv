package com.letv.mobile.lebox.http.lebox.bean;

import android.text.TextUtils;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.http.utils.StringUtils;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;

public class TaskVideoBean implements LetvHttpBaseModel {
    private String completeTime;
    private Info info;
    private String pid;
    private String pr;
    private String progress;
    private String speed;
    private String status;
    private TaskTagBean tag;
    private String totalSize;
    private String vid;

    public TaskVideoBean(String vid, String pid, TaskTagBean tag) {
        this.vid = vid;
        this.pid = pid;
        this.tag = tag;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Info getInfo() {
        return this.info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getCompleteTime() {
        return this.completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public TaskTagBean getTag() {
        return this.tag;
    }

    public String getTagTitle() {
        if (this.tag == null || StringUtils.equalsNull(this.tag.getTitle())) {
            return "";
        }
        return this.tag.getTitle();
    }

    public String getTagCover() {
        if (this.tag == null || StringUtils.equalsNull(this.tag.getCover())) {
            return "";
        }
        return this.tag.getCover();
    }

    public String getTagAlbumCover() {
        if (this.tag == null || StringUtils.equalsNull(this.tag.getAlbumCover())) {
            return "";
        }
        return this.tag.getAlbumCover();
    }

    public String getTagAlbumTitle() {
        if (this.tag == null || StringUtils.equalsNull(this.tag.getAlbumTitle())) {
            return "";
        }
        return this.tag.getAlbumTitle();
    }

    public String getTagIsEnd() {
        if (this.tag == null || StringUtils.equalsNull(this.tag.getIsEnd())) {
            return "";
        }
        return this.tag.getIsEnd();
    }

    public String getTagIsWatch() {
        if (this.tag == null || StringUtils.equalsNull(this.tag.getIsWatch())) {
            return "";
        }
        return this.tag.getIsWatch();
    }

    public void setTagIsWatch(boolean isSeen) {
        if (this.tag != null) {
            if (isSeen) {
                this.tag.setIsWatch("1");
            } else {
                this.tag.setIsWatch("0");
            }
        }
    }

    public void setTag(TaskTagBean tag) {
        this.tag = tag;
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

    public String getInfoVideoName() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getNameCn() == null) {
            return null;
        }
        return this.info.getVideoInfo().getNameCn();
    }

    public String getInfoAlbumName() {
        if (this.info == null || this.info.getAlbumInfo() == null || this.info.getAlbumInfo().getNameCn() == null) {
            return null;
        }
        return this.info.getAlbumInfo().getNameCn();
    }

    public String getInfoVideoEpisode() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getEpisode() == null) {
            return null;
        }
        return this.info.getVideoInfo().getEpisode();
    }

    public String getInfoVideoUpdateTime() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getUpdateTime() == null) {
            return null;
        }
        return this.info.getVideoInfo().getUpdateTime();
    }

    public String getInfoVideoStream() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getStream() == null) {
            return null;
        }
        return this.info.getVideoInfo().getStream();
    }

    public String getInfoVideoBtime() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getBtime() == null) {
            return null;
        }
        return this.info.getVideoInfo().getBtime();
    }

    public String getInfoVideoEtime() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getEtime() == null) {
            return null;
        }
        return this.info.getVideoInfo().getEtime();
    }

    public String getInfoVideoDuration() {
        if (this.info == null || this.info.getVideoInfo() == null || this.info.getVideoInfo().getDuration() == null) {
            return null;
        }
        return this.info.getVideoInfo().getDuration();
    }

    public String getInfoAlbumType() {
        if (this.info == null || this.info.getAlbumInfo() == null || this.info.getAlbumInfo().getCid() == null) {
            return null;
        }
        return this.info.getAlbumInfo().getCid();
    }

    public String getInfoAlbumIsEnd() {
        if (this.info == null || this.info.getAlbumInfo() == null || this.info.getAlbumInfo().getIsEnd() == null) {
            return null;
        }
        return this.info.getAlbumInfo().getIsEnd();
    }

    public String getVideoName() {
        if (TextUtils.isEmpty(getInfoVideoName())) {
            return getTagTitle();
        }
        return getInfoVideoName();
    }

    public String getLeboxVideoFilePath(String type) {
        return HttpRequesetManager.getLeboxVideoFilePath(type, this.vid);
    }

    public String toString() {
        return "TaskVideoBean [vid=" + this.vid + ", status=" + this.status + ", pr=" + this.pr + ", progress=" + this.progress + ", pid=" + this.pid + ", info=" + this.info + ", completeTime=" + this.completeTime + ", tag=" + this.tag + ", totalSize=" + this.totalSize + "]";
    }
}
