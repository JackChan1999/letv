package com.letv.android.client.worldcup.bean;

import android.content.Context;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration;
import com.letv.download.db.Download.DownloadAlbumTable;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadDBBean {
    private long albumId;
    private String albumtitle;
    private long btime;
    private int cid;
    private String episodeIcon;
    private int episodeid;
    private String episodetitle;
    private long etime;
    private File file;
    private String filePath;
    private int finish = 0;
    private String icon;
    private int isHd;
    private long length;
    private String mmsid;
    private long timestamp;
    private long totalsize;
    private int type;

    public DownloadDBBean(DownloadDBBean bean) {
        this.episodeid = bean.episodeid;
        this.mmsid = bean.mmsid;
        this.albumId = bean.albumId;
        this.icon = bean.icon;
        this.type = bean.type;
        this.cid = bean.cid;
        this.episodetitle = bean.episodetitle;
        this.albumtitle = bean.albumtitle;
        this.totalsize = bean.totalsize;
        this.finish = bean.finish;
        this.timestamp = bean.timestamp;
        this.length = bean.length;
        this.filePath = bean.filePath;
        this.isHd = bean.isHd;
        this.file = bean.file;
        this.btime = bean.btime;
        this.etime = bean.etime;
    }

    public static DownloadDBBean getInstance(Context context, Video video, long pid, int isHd, String albumtitle) {
        DownloadDBBean mDownloadDBBean = new DownloadDBBean();
        mDownloadDBBean.setAlbumId(pid);
        mDownloadDBBean.setAlbumtitle(albumtitle);
        mDownloadDBBean.setCid(video.getCid());
        mDownloadDBBean.setType(video.getType());
        mDownloadDBBean.setIcon(video.getPic());
        mDownloadDBBean.setEpisodeIcon(video.getPic());
        mDownloadDBBean.setEpisodeid((int) video.getId());
        mDownloadDBBean.setEpisodetitle(video.getNameCn());
        mDownloadDBBean.setFinish(0);
        mDownloadDBBean.setFilePath(LetvServiceConfiguration.getDownload_path(context));
        mDownloadDBBean.setMmsid(video.getMid());
        mDownloadDBBean.setIsHd(isHd);
        mDownloadDBBean.setBtime(video.getBtime());
        mDownloadDBBean.setEtime(video.getEtime());
        return mDownloadDBBean;
    }

    public DownloadDBBean(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {
            this.episodeid = jsonObject.getInt("episodeid");
            this.albumId = (long) jsonObject.getInt(PageJumpUtil.IN_TO_ALBUM_PID);
            this.icon = jsonObject.getString(SettingsJsonConstants.APP_ICON_KEY);
            this.type = jsonObject.getInt("type");
            this.cid = jsonObject.getInt("cid");
            this.episodetitle = jsonObject.getString("episodetitle");
            this.albumtitle = jsonObject.getString(DownloadAlbumTable.COLUMN_ALBUMTITLE);
            this.totalsize = (long) jsonObject.getInt(DownloadVideoTable.COLUMN_TOTALSIZE);
            this.finish = jsonObject.getInt("finish");
            this.timestamp = (long) jsonObject.getInt("timestamp");
            this.length = (long) jsonObject.getInt(DownloadVideoTable.COLUMN_LENGTH);
            if (jsonObject.has(DownloadVideoTable.COLUMN_FILEPATH)) {
                this.filePath = jsonObject.getString(DownloadVideoTable.COLUMN_FILEPATH);
            }
            this.isHd = jsonObject.getInt("isHd");
            this.btime = jsonObject.getLong(DownloadVideoTable.COLUMN_BTIME);
            this.etime = jsonObject.getLong(DownloadVideoTable.COLUMN_ETIME);
        }
    }

    public int getEpisodeid() {
        return this.episodeid;
    }

    public void setEpisodeid(int episodeid) {
        this.episodeid = episodeid;
    }

    public String getMmsid() {
        return this.mmsid;
    }

    public void setMmsid(String mmsid) {
        this.mmsid = mmsid;
    }

    public long getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEpisodetitle() {
        return this.episodetitle;
    }

    public void setEpisodetitle(String episodetitle) {
        this.episodetitle = episodetitle;
    }

    public String getEpisodeIcon() {
        return this.episodeIcon;
    }

    public void setEpisodeIcon(String episodeIcon) {
        this.episodeIcon = episodeIcon;
    }

    public String getAlbumtitle() {
        return this.albumtitle;
    }

    public void setAlbumtitle(String albumtitle) {
        this.albumtitle = albumtitle;
    }

    public long getTotalsize() {
        return this.totalsize;
    }

    public void setTotalsize(long totalsize) {
        this.totalsize = totalsize;
    }

    public int getFinish() {
        return this.finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getIsHd() {
        return this.isHd;
    }

    public void setIsHd(int isHd) {
        this.isHd = isHd;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("episodeid", this.episodeid);
        jsonObject.put(PageJumpUtil.IN_TO_ALBUM_PID, this.albumId);
        jsonObject.put(SettingsJsonConstants.APP_ICON_KEY, this.icon);
        jsonObject.put("type", this.type);
        jsonObject.put("cid", this.cid);
        jsonObject.put("episodetitle", this.episodetitle);
        jsonObject.put("episodeicon", this.episodeIcon);
        jsonObject.put(DownloadAlbumTable.COLUMN_ALBUMTITLE, this.albumtitle);
        jsonObject.put(DownloadVideoTable.COLUMN_TOTALSIZE, this.totalsize);
        jsonObject.put("finish", this.finish);
        jsonObject.put("timestamp", this.timestamp);
        jsonObject.put(DownloadVideoTable.COLUMN_LENGTH, this.length);
        jsonObject.put("file_path", this.filePath);
        jsonObject.put("isHd", this.isHd);
        jsonObject.put(DownloadVideoTable.COLUMN_BTIME, this.btime);
        jsonObject.put(DownloadVideoTable.COLUMN_ETIME, this.etime);
        return jsonObject;
    }

    public String toString() {
        return "DownloadDBBean [episodeid=" + this.episodeid + ", mmsid=" + this.mmsid + ", albumId=" + this.albumId + ", icon=" + this.icon + ", type=" + this.type + ", cid=" + this.cid + ", episodetitle=" + this.episodetitle + ", albumtitle=" + this.albumtitle + ", totalsize=" + this.totalsize + ", finish=" + this.finish + ", timestamp=" + this.timestamp + ", length=" + this.length + ", filePath=" + this.filePath + ", isHd=" + this.isHd + ",btime=" + this.btime + ",etime=" + this.etime + "]";
    }
}
