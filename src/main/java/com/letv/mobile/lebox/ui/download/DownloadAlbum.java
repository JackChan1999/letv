package com.letv.mobile.lebox.ui.download;

import android.text.TextUtils;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import java.util.ArrayList;
import java.util.List;

public class DownloadAlbum implements LetvHttpBaseModel {
    private static final String TAG = DownloadAlbum.class.getSimpleName();
    public String albumTitle;
    public long albumTotalSize = 0;
    public int albumVideoNum = 0;
    public boolean isWatch = true;
    public String mAlbumType;
    public String picUrl;
    public long pid = 0;
    private final List<TaskVideoBean> videoList = new ArrayList();

    public void addVideo(TaskVideoBean videoBean) {
        String albumType = videoBean.getInfoAlbumType();
        if (TextUtils.isEmpty(albumType) || !("2".equals(albumType) || "5".equals(albumType))) {
            addVideoForEpisodeInDescendingOrder(videoBean);
        } else {
            addVideoForEpisode(videoBean);
        }
        if (TextUtils.isEmpty(this.mAlbumType)) {
            this.mAlbumType = albumType;
        }
        if (this.pid == 0 && !TextUtils.isEmpty(videoBean.getPid())) {
            this.pid = Long.valueOf(videoBean.getPid()).longValue();
        }
        if (TextUtils.isEmpty(this.picUrl)) {
            this.picUrl = videoBean.getLeboxVideoFilePath("2");
        }
        if (TextUtils.isEmpty(this.albumTitle)) {
            if (TextUtils.isEmpty(videoBean.getInfoAlbumName())) {
                this.albumTitle = videoBean.getTagAlbumTitle();
            } else {
                this.albumTitle = videoBean.getInfoAlbumName();
            }
        }
        long size = 0;
        if (!TextUtils.isEmpty(videoBean.getTotalSize())) {
            size = Long.valueOf(videoBean.getTotalSize()).longValue();
        }
        this.albumTotalSize += size;
        this.albumVideoNum++;
        if (!"1".equals(videoBean.getTagIsWatch())) {
            this.isWatch = false;
        }
    }

    private void addVideoForOrder(TaskVideoBean videoBean) {
        if (this.videoList.size() == 0) {
            this.videoList.add(videoBean);
            return;
        }
        int index = 0;
        for (int i = 0; i < this.videoList.size(); i++) {
            if (TextUtils.isEmpty(videoBean.getInfoVideoUpdateTime())) {
                index = this.videoList.size();
            } else {
                long time = Util.stringToDate(videoBean.getInfoVideoUpdateTime()).getTime();
                TaskVideoBean video = (TaskVideoBean) this.videoList.get(i);
                if (TextUtils.isEmpty(video.getInfoVideoUpdateTime())) {
                    index = i;
                    break;
                } else if (time < Util.stringToDate(video.getInfoVideoUpdateTime()).getTime()) {
                    index = i;
                    break;
                } else if (i == this.videoList.size() - 1) {
                    index = this.videoList.size();
                }
            }
        }
        this.videoList.add(index, videoBean);
    }

    public void addVideoForUpdate(TaskVideoBean videoBean) {
        if (this.videoList.size() == 0) {
            this.videoList.add(videoBean);
            return;
        }
        int index = 0;
        for (int i = 0; i < this.videoList.size(); i++) {
            if (TextUtils.isEmpty(videoBean.getInfoVideoUpdateTime())) {
                index = this.videoList.size();
            } else {
                long time = Util.stringToDate(videoBean.getInfoVideoUpdateTime()).getTime();
                TaskVideoBean video = (TaskVideoBean) this.videoList.get(i);
                if (TextUtils.isEmpty(video.getInfoVideoUpdateTime())) {
                    index = i;
                    break;
                } else if (time > Util.stringToDate(video.getInfoVideoUpdateTime()).getTime()) {
                    index = i;
                    break;
                } else if (i == this.videoList.size() - 1) {
                    index = this.videoList.size();
                }
            }
        }
        this.videoList.add(index, videoBean);
    }

    private void addVideoForEpisode(TaskVideoBean videoBean) {
        if (this.videoList.size() == 0) {
            this.videoList.add(videoBean);
            return;
        }
        int index = 0;
        for (int i = 0; i < this.videoList.size(); i++) {
            if (TextUtils.isEmpty(videoBean.getInfoVideoEpisode()) || Util.getInt(videoBean.getInfoVideoEpisode(), 0) == 0) {
                index = this.videoList.size();
            } else {
                int episode = Util.getInt(videoBean.getInfoVideoEpisode(), 0);
                TaskVideoBean video = (TaskVideoBean) this.videoList.get(i);
                if (TextUtils.isEmpty(video.getInfoVideoEpisode()) || Util.getInt(video.getInfoVideoEpisode(), 0) == 0) {
                    index = i;
                    break;
                } else if (episode < Util.getInt(video.getInfoVideoEpisode(), 0)) {
                    index = i;
                    break;
                } else if (i == this.videoList.size() - 1) {
                    index = this.videoList.size();
                }
            }
        }
        this.videoList.add(index, videoBean);
    }

    public void addVideoForEpisodeInDescendingOrder(TaskVideoBean videoBean) {
        if (this.videoList.size() == 0) {
            this.videoList.add(videoBean);
            return;
        }
        int index = 0;
        for (int i = 0; i < this.videoList.size(); i++) {
            if (TextUtils.isEmpty(videoBean.getInfoVideoEpisode()) || Util.getInt(videoBean.getInfoVideoEpisode(), 0) == 0) {
                index = this.videoList.size();
            } else {
                int episode = Util.getInt(videoBean.getInfoVideoEpisode(), 0);
                TaskVideoBean video = (TaskVideoBean) this.videoList.get(i);
                if (TextUtils.isEmpty(video.getInfoVideoEpisode()) || Util.getInt(video.getInfoVideoEpisode(), 0) == 0) {
                    index = i;
                    break;
                } else if (episode > Util.getInt(video.getInfoVideoEpisode(), 0)) {
                    index = i;
                    break;
                } else if (i == this.videoList.size() - 1) {
                    index = this.videoList.size();
                }
            }
        }
        this.videoList.add(index, videoBean);
    }

    public void cleanVideoList() {
        this.videoList.clear();
        this.albumVideoNum = 0;
        this.albumTotalSize = 0;
    }

    public List<TaskVideoBean> getVideoAlbum() {
        return this.videoList;
    }

    public long getJustOneVid() {
        if (this.videoList.size() > 0) {
            return Long.valueOf(((TaskVideoBean) this.videoList.get(0)).getVid(), -1).longValue();
        }
        return -1;
    }

    public String getJustOneVideoPicUrl() {
        if (this.videoList.size() > 0) {
            return ((TaskVideoBean) this.videoList.get(0)).getLeboxVideoFilePath("1");
        }
        return null;
    }

    public String getJustOneVideoPlayUrl() {
        if (this.videoList.size() > 0) {
            return ((TaskVideoBean) this.videoList.get(0)).getLeboxVideoFilePath("0");
        }
        return null;
    }

    public String getJustOneVideoName() {
        if (this.videoList.size() > 0) {
            return ((TaskVideoBean) this.videoList.get(0)).getVideoName();
        }
        return null;
    }

    public boolean getIsEnd() {
        String isEnd = "";
        if ("2".equals(this.mAlbumType)) {
            isEnd = getIsEndLocal((TaskVideoBean) this.videoList.get(this.videoList.size() - 1));
        } else {
            isEnd = getIsEndLocal((TaskVideoBean) this.videoList.get(0));
        }
        if ("1".equals(isEnd)) {
            return true;
        }
        return false;
    }

    private String getIsEndLocal(TaskVideoBean videoBean) {
        String isEndFromTag = videoBean.getTagIsEnd();
        String isEndFromInfo = videoBean.getInfoAlbumIsEnd();
        Logger.d(TAG, "--getIsEndLocal----isEndFromTag" + isEndFromTag + "--isEndFromInfo=" + isEndFromInfo);
        if (TextUtils.isEmpty(isEndFromTag) && TextUtils.isEmpty(isEndFromInfo)) {
            return "";
        }
        if ("1".equals(isEndFromInfo) || "1".equals(isEndFromTag)) {
            return "1";
        }
        return "0";
    }

    public String toString() {
        return "DownloadAlbum [pid=" + this.pid + ", picUrl=" + this.picUrl + ", albumTitle=" + this.albumTitle + ", albumTotalSize=" + this.albumTotalSize + ", albumVideoNum=" + this.albumVideoNum + ", isWatch=" + this.isWatch + ", videoList=" + this.videoList + "]";
    }
}
