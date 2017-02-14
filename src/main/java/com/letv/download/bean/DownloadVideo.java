package com.letv.download.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.DownloadConstant;
import com.letv.core.utils.LetvUtils;
import com.letv.download.db.DownloadDBDao;
import com.letv.download.manager.VideoFileManager;
import com.letv.download.service.FileDownloader;
import com.letv.download.util.DownloadUtil;
import com.letv.download.util.L;
import java.io.File;

public class DownloadVideo implements Parcelable {
    public static final Creator<DownloadVideo> CREATOR = new 1();
    public long aid;
    public long btime;
    public long cid;
    public String downloadUrl;
    public long downloaded;
    public long duration;
    public long etime;
    public String filePath;
    public boolean hasSubtitle;
    public boolean isMultipleAudio;
    public boolean isNew;
    public boolean isVideoNormal;
    public boolean isVipDownload;
    public boolean isWatch;
    public long length;
    public DownloadAlbum mDownloadAlbum;
    public FileDownloader mDownloader;
    public PartInfoBean[] mParts;
    public String mmsid;
    public String multipleAudioCode;
    public String name;
    public int ord;
    public String pcode;
    public String picUrl;
    public long pid;
    public long rowID;
    public long serverTotalSize;
    public String speed;
    public int state;
    public String storePath;
    public int streamType;
    public String subtitleCode;
    public String subtitleUrl;
    public int threadNum;
    public int threads;
    public long timestamp;
    public long totalsize;
    public int type;
    public String version;
    public long vid;
    public String videoTypeKey;

    public static class DownloadState {
        public static final int FINISHED_STATE = 4;
        public static final int NETWORK_ERROR_STATE = 6;
        public static final int PAUSE_STATE = 3;
        public static final int RUNNING_STATE = 1;
        public static final int SERVER_ERROR_STATE = 7;
        public static final int STORE_ERROR_STATE = 8;
        public static final int UNKNOW_ERROR_STATE = 5;
        public static final int WAIT_STATE = 0;
    }

    public boolean isErrorState() {
        if (this.state == 5 || this.state == 6 || this.state == 7 || this.state == 8) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.vid != 0) {
            return (int) this.vid;
        }
        return super.hashCode();
    }

    public boolean equals(Object o) {
        L.v("", "DownloadVideo equals ");
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof DownloadVideo)) {
            return false;
        }
        if (this.vid != ((DownloadVideo) o).vid) {
            return false;
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeLong(this.aid);
        dest.writeLong(this.cid);
        dest.writeInt(this.ord);
        dest.writeInt(this.type);
        dest.writeLong(this.vid);
        dest.writeString(this.name);
        dest.writeString(this.picUrl);
        dest.writeLong(this.totalsize);
        dest.writeLong(this.length);
        dest.writeString(this.videoTypeKey);
        dest.writeString(this.filePath);
        dest.writeInt(this.streamType);
        dest.writeByte((byte) (this.isNew ? 1 : 0));
        dest.writeLong(this.btime);
        dest.writeLong(this.etime);
        if (this.isWatch) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeLong(this.duration);
        dest.writeString(this.downloadUrl);
        dest.writeLong(this.downloaded);
        dest.writeInt(this.threadNum);
        dest.writeInt(this.state);
        dest.writeString(this.mmsid);
        dest.writeString(this.pcode);
        dest.writeString(this.version);
        dest.writeParcelable(this.mDownloadAlbum, 1);
        dest.writeLong(this.rowID);
        dest.writeLong(this.timestamp);
        dest.writeString(this.speed);
        dest.writeLong(this.pid);
        dest.writeByte((byte) (this.isVideoNormal ? 1 : 0));
        if (this.isVipDownload) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (!this.hasSubtitle) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        dest.writeString(this.subtitleUrl);
    }

    public DownloadVideo() {
        this.vid = 0;
        this.isNew = true;
        this.isWatch = false;
        this.state = 0;
        this.mDownloader = null;
        this.isVideoNormal = true;
        this.isVipDownload = false;
        this.hasSubtitle = false;
        this.isMultipleAudio = false;
    }

    private DownloadVideo(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.vid = 0;
        this.isNew = true;
        this.isWatch = false;
        this.state = 0;
        this.mDownloader = null;
        this.isVideoNormal = true;
        this.isVipDownload = false;
        this.hasSubtitle = false;
        this.isMultipleAudio = false;
        this.aid = in.readLong();
        this.cid = in.readLong();
        this.ord = in.readInt();
        this.type = in.readInt();
        this.vid = in.readLong();
        this.name = in.readString();
        this.picUrl = in.readString();
        this.totalsize = in.readLong();
        this.length = in.readLong();
        this.videoTypeKey = in.readString();
        this.filePath = in.readString();
        this.streamType = in.readInt();
        this.isNew = in.readByte() != (byte) 0;
        this.btime = in.readLong();
        this.etime = in.readLong();
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isWatch = z;
        this.duration = in.readLong();
        this.downloadUrl = in.readString();
        this.downloaded = in.readLong();
        this.threadNum = in.readInt();
        this.state = in.readInt();
        this.mmsid = in.readString();
        this.pcode = in.readString();
        this.version = in.readString();
        this.mDownloadAlbum = (DownloadAlbum) in.readParcelable(DownloadAlbum.class.getClassLoader());
        this.rowID = in.readLong();
        this.timestamp = in.readLong();
        this.speed = in.readString();
        this.pid = in.readLong();
        this.isVideoNormal = in.readByte() != (byte) 0;
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isVipDownload = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.hasSubtitle = z2;
        this.subtitleUrl = in.readString();
    }

    private static void assignDownloadAlbum(DownloadVideo downloadVideo, DownloadAlbum downloadAlbum, AlbumInfo album, VideoBean video, boolean isFromRecomm) {
        if (album != null) {
            downloadVideo.pid = video.pid;
            downloadVideo.type = DownloadUtil.stringToInteger(album.style);
            if (video.pid != 0) {
                downloadVideo.aid = video.pid;
                downloadAlbum.aid = video.pid;
            } else if (album.pid != 0) {
                downloadVideo.aid = album.pid;
                downloadAlbum.aid = album.pid;
            } else {
                downloadVideo.aid = video.vid;
                downloadAlbum.aid = video.vid;
            }
            if (!TextUtils.isEmpty(video.pidname)) {
                downloadAlbum.albumTitle = video.pidname;
            } else if (!TextUtils.isEmpty(album.nameCn)) {
                downloadAlbum.albumTitle = album.nameCn;
            } else if (TextUtils.isEmpty(video.nameCn)) {
                downloadAlbum.albumTitle = video.title;
            } else {
                downloadAlbum.albumTitle = video.nameCn;
            }
            if (isFromRecomm) {
                if (album.pid == video.pid) {
                    downloadAlbum.picUrl = album.pic320_200;
                    return;
                } else {
                    downloadAlbum.picUrl = video.pic320_200;
                    return;
                }
            } else if (!TextUtils.isEmpty(album.pic320_200)) {
                downloadAlbum.picUrl = album.pic320_200;
                return;
            } else if (!TextUtils.isEmpty(video.pic120_90)) {
                downloadAlbum.picUrl = video.pic120_90;
                return;
            } else if (!TextUtils.isEmpty(video.pic320_200)) {
                downloadAlbum.picUrl = video.pic320_200;
                return;
            } else {
                return;
            }
        }
        if (video.pid != 0) {
            downloadAlbum.aid = video.pid;
            downloadVideo.aid = video.pid;
        } else {
            downloadAlbum.aid = video.vid;
            downloadVideo.aid = video.vid;
        }
        if (!TextUtils.isEmpty(video.pidname)) {
            downloadAlbum.albumTitle = video.pidname;
        } else if (TextUtils.isEmpty(video.nameCn)) {
            downloadAlbum.albumTitle = video.title;
        } else {
            downloadAlbum.albumTitle = video.nameCn;
        }
        if (!TextUtils.isEmpty(video.pic120_90)) {
            downloadAlbum.picUrl = video.pic120_90;
        } else if (!TextUtils.isEmpty(video.pic320_200)) {
            downloadAlbum.picUrl = video.pic320_200;
        }
        downloadVideo.pid = video.pid;
        downloadVideo.cid = (long) video.cid;
    }

    public static DownloadVideo createNewDownloadVideo(AlbumInfo album, VideoBean video, int streamType, boolean isDolby, boolean isFromRecomm) {
        if (video == null) {
            return null;
        }
        DownloadVideo downloadVideo = new DownloadVideo();
        DownloadAlbum downloadAlbum = new DownloadAlbum();
        boolean isVideoNormal = video.pid != 0;
        downloadAlbum.isVideoNormal = isVideoNormal;
        downloadAlbum.setAlbumVersion(63);
        DownloadAlbum downloadAlbumFromDB = DownloadDBDao.getInstance(BaseApplication.getInstance()).getDownloadAlbumByAid(video.pid);
        L.v("DownloadVideo", "createNewDownloadVideo isVideoNormal : " + isVideoNormal + " album : " + album + " downloadAlbumFromDB : " + downloadAlbumFromDB + " isFromRecomm " + isFromRecomm);
        if (downloadAlbumFromDB == null) {
            assignDownloadAlbum(downloadVideo, downloadAlbum, album, video, isFromRecomm);
        } else {
            downloadAlbum = downloadAlbumFromDB;
            downloadVideo.aid = downloadAlbum.aid;
            downloadVideo.pid = video.pid;
        }
        downloadAlbum.isFrommRecom = isFromRecomm;
        if (video.cid != 0) {
            downloadVideo.cid = (long) video.cid;
        } else if (!(album == null || album.cid == 0)) {
            downloadVideo.cid = (long) album.cid;
        }
        if (!TextUtils.isEmpty(video.style)) {
            downloadVideo.type = DownloadUtil.stringToInteger(video.style);
        } else if (!(album == null || TextUtils.isEmpty(album.style))) {
            downloadVideo.type = DownloadUtil.stringToInteger(album.style);
        }
        downloadVideo.isVideoNormal = isVideoNormal;
        downloadVideo.pcode = LetvUtils.getPcode();
        downloadVideo.version = LetvUtils.getClientVersionName();
        downloadVideo.ord = DownloadUtil.stringToInteger(video.episode);
        downloadVideo.videoTypeKey = video.videoTypeKey;
        downloadVideo.mDownloadAlbum = downloadAlbum;
        downloadVideo.duration = video.duration;
        downloadVideo.isNew = true;
        downloadVideo.vid = video.vid;
        if (TextUtils.isEmpty(video.nameCn)) {
            downloadVideo.name = video.title;
        } else {
            downloadVideo.name = video.nameCn;
        }
        downloadVideo.state = 0;
        downloadVideo.mmsid = video.mid;
        downloadVideo.ord = DownloadUtil.stringToInteger(video.episode);
        if (isDolby) {
            streamType = 3;
        }
        downloadVideo.streamType = streamType;
        downloadVideo.btime = video.btime;
        downloadVideo.etime = video.etime;
        downloadVideo.isWatch = false;
        if (TextUtils.isEmpty(video.pic120_90)) {
            downloadVideo.picUrl = video.pic320_200;
        } else {
            downloadVideo.picUrl = video.pic120_90;
        }
        downloadVideo.threadNum = DownloadConstant.DOWNLOAD_JOB_THREAD_LIMIT;
        downloadVideo.isVipDownload = video.isVipDownload;
        return downloadVideo;
    }

    public synchronized void incrementBytes(int bytes) {
        this.downloaded += (long) bytes;
    }

    public DownloadDBBean convertToDownloadDbBean() {
        int i = 1;
        DownloadDBBean bean = new DownloadDBBean();
        if (this.pid == 0) {
            bean.aid = (int) this.aid;
        } else {
            bean.aid = (int) this.pid;
        }
        bean.isHd = this.streamType;
        bean.filePath = this.filePath + File.separator + VideoFileManager.createFileName(this.vid);
        bean.vid = (int) this.vid;
        bean.episodetitle = this.name;
        bean.cid = (int) this.cid;
        bean.icon = this.picUrl;
        bean.btime = this.btime;
        bean.etime = this.etime;
        bean.mmsid = this.mmsid;
        bean.duration = this.duration;
        bean.videoTypeKey = this.videoTypeKey;
        bean.isNew = this.isNew ? 1 : 0;
        bean.finish = this.state;
        if (!this.isWatch) {
            i = 0;
        }
        bean.isWatch = i;
        bean.length = this.length;
        bean.type = this.type;
        bean.hasSubtitle = this.hasSubtitle;
        bean.subtitleUrl = this.subtitleUrl;
        bean.subtitleCode = this.subtitleCode;
        bean.isMultipleAudio = this.isMultipleAudio;
        bean.multipleAudioCode = this.multipleAudioCode;
        return bean;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append("name : ").append(this.name).append(" ").append("  status : ").append(this.state).append("]").append(" mDownloader : ").append(this.mDownloader).append("  vid : ").append(this.vid).append(" mParts : ").append(this.mParts);
        return sb.toString();
    }
}
