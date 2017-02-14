package com.letv.mobile.lebox.httpmanager;

import android.text.TextUtils;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumBean;
import com.letv.mobile.lebox.http.lebox.bean.Info;
import com.letv.mobile.lebox.http.lebox.bean.TaskPro;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class HttpCacheAssistant {
    private static final String TAG = HttpCacheAssistant.class.getSimpleName();
    public static final int TYPE_ALL = 0;
    public static final int TYPE_COMPLETED = 1;
    public static final int TYPE_NOT_COMPLETED = 2;
    public static final int USER_IDENTITY_ADMIN = 1;
    public static final int USER_IDENTITY_GUEST = 2;
    public static final int USER_IDENTITY_UNKNOW = 0;
    private static HttpCacheAssistant mAssistant;
    private String deviceName;
    private List<TaskVideoBean> mCompleteList = new ArrayList();
    private String mCompleteVersion;
    private List<FollowAlbumBean> mFollowList;
    private List<TaskVideoBean> mUnFinishList = new ArrayList();
    private String mUnFinishVersion;
    private int userIdentity;

    private HttpCacheAssistant() {
    }

    public static synchronized HttpCacheAssistant getInstanced() {
        HttpCacheAssistant httpCacheAssistant;
        synchronized (HttpCacheAssistant.class) {
            if (mAssistant == null) {
                mAssistant = new HttpCacheAssistant();
            }
            httpCacheAssistant = mAssistant;
        }
        return httpCacheAssistant;
    }

    public String getCompleteVersion() {
        return this.mCompleteVersion;
    }

    void setCompleteVersion(String completeVersion) {
        this.mCompleteVersion = completeVersion;
    }

    public String getUnFinishVersion() {
        return this.mUnFinishVersion;
    }

    void setUnFinishVersion(String unFinishVersion) {
        this.mUnFinishVersion = unFinishVersion;
    }

    public List<FollowAlbumBean> getFollowList() {
        return this.mFollowList;
    }

    public void setFollowList(List<FollowAlbumBean> followList) {
        this.mFollowList = followList;
    }

    public void deleteFollowAlbumInCache(String pid) {
        if (!TextUtils.isEmpty(pid) && this.mFollowList != null && this.mFollowList.size() != 0) {
            for (FollowAlbumBean album : this.mFollowList) {
                if (pid.equals(album.getPid())) {
                    this.mFollowList.remove(album);
                    return;
                }
            }
        }
    }

    public boolean isCompleteTaskEmpty() {
        if (this.mCompleteList == null || this.mCompleteList.size() <= 0) {
            return true;
        }
        return false;
    }

    public boolean isUnFinishTaskEmpty() {
        if (this.mUnFinishList == null || this.mUnFinishList.size() <= 0) {
            return true;
        }
        return false;
    }

    public boolean isFollowEmpty() {
        if (this.mFollowList == null || this.mFollowList.size() <= 0) {
            return true;
        }
        return false;
    }

    public List<TaskVideoBean> getCompleteList() {
        return this.mCompleteList;
    }

    public List<TaskVideoBean> getUnFinishList() {
        return this.mUnFinishList;
    }

    public void setCompleteList(List<TaskVideoBean> completeList) {
        this.mCompleteList = completeList;
    }

    public void setUnFinishListList(List<TaskVideoBean> unFinishList) {
        this.mUnFinishList = unFinishList;
    }

    public void setAllList(List<TaskVideoBean> allList) {
        if (allList != null && allList.size() != 0) {
            this.mCompleteList = new ArrayList();
            this.mUnFinishList = new ArrayList();
            for (TaskVideoBean video : allList) {
                if ("2".equals(video.getStatus())) {
                    this.mCompleteList.add(video);
                } else {
                    this.mUnFinishList.add(video);
                }
            }
        }
    }

    public boolean setDownloadingUpdate(TaskPro taskPro) {
        if (taskPro == null || TextUtils.isEmpty(taskPro.getVid())) {
            return false;
        }
        for (TaskVideoBean video : this.mUnFinishList) {
            if (taskPro.getVid().equals(video.getVid())) {
                video.setStatus("1");
                video.setPr(taskPro.getPr());
                video.setProgress(taskPro.getProgress());
                video.setSpeed(taskPro.getSpeed());
                video.setTotalSize(taskPro.getTotalSize());
                return true;
            }
        }
        return false;
    }

    public boolean checkNotCompletedListInfoHaveNull() {
        if (this.mUnFinishList == null || this.mUnFinishList.size() == 0) {
            return false;
        }
        for (TaskVideoBean video : this.mUnFinishList) {
            if (video.getInfo() == null) {
                return true;
            }
        }
        return false;
    }

    public List<TaskVideoBean> getAllVideoByPid(String pid, int type) {
        List<TaskVideoBean> videoList = new ArrayList();
        switch (type) {
            case 0:
                getVideoByPid(pid, this.mCompleteList, videoList);
                getVideoByPid(pid, this.mUnFinishList, videoList);
                break;
            case 1:
                getVideoByPid(pid, this.mCompleteList, videoList);
                break;
            case 2:
                getVideoByPid(pid, this.mUnFinishList, videoList);
                break;
        }
        return videoList;
    }

    public Info getInfo(String pid, List<TaskVideoBean> totalVideoList) {
        if (TextUtils.isEmpty(pid)) {
            return null;
        }
        Logger.d(TAG, "getInfo totalVideoList.size():" + totalVideoList.size());
        for (TaskVideoBean video : totalVideoList) {
            if (pid.equals(video.getPid())) {
                Logger.d(TAG, "-getInfo---pid=" + pid + "--video.getPid()=" + video.getPid());
                if (!(video.getInfo() == null || video.getInfo().getAlbumInfo() == null)) {
                    return video.getInfo();
                }
            }
        }
        return null;
    }

    private void getVideoByPid(String pid, List<TaskVideoBean> totalVideoList, List<TaskVideoBean> saveVideoList) {
        if (saveVideoList == null) {
            saveVideoList = new ArrayList();
        }
        if (totalVideoList != null && totalVideoList.size() != 0 && !TextUtils.isEmpty(pid)) {
            for (TaskVideoBean video : totalVideoList) {
                if (pid.equals(video.getPid())) {
                    saveVideoList.add(video);
                }
            }
        }
    }

    public String getFollowAlbumPro(String pid, int type) {
        String episode = "";
        switch (type) {
            case 0:
                return getFollowDetails(getAllVideoByPid(pid, 0));
            case 1:
                return getFollowDetails(getAllVideoByPid(pid, 1));
            default:
                return episode;
        }
    }

    public String getFollowDetails(List<TaskVideoBean> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        Logger.d(TAG, "---------getFollowDetails list size=" + list.size());
        if (list.size() != 1 || ((TaskVideoBean) list.get(0)).getInfo() == null) {
            int index = -1;
            int number = 0;
            for (int i = 0; i < list.size(); i++) {
                if (((TaskVideoBean) list.get(i)).getInfo() != null) {
                    String episode = ((TaskVideoBean) list.get(i)).getInfoVideoEpisode();
                    Logger.d(TAG, "--episode=" + episode + "--vid=" + ((TaskVideoBean) list.get(i)).getVid());
                    int t = Integer.valueOf(episode).intValue();
                    Logger.d(TAG, "--episode=" + episode + "--t=" + t);
                    if (t > number) {
                        index = i;
                        number = t;
                    }
                }
            }
            Logger.d(TAG, "--index=" + index + "--number=" + number);
            if (index >= 0) {
                return ((TaskVideoBean) list.get(index)).getInfoVideoEpisode();
            }
            return "";
        }
        episode = ((TaskVideoBean) list.get(0)).getInfo().getVideoInfo().getEpisode();
        Logger.d(TAG, "---------1 size episode= " + episode);
        return episode;
    }

    public boolean isAdmini() {
        String ssid = LeboxQrCodeBean.getSsid();
        if (TextUtils.isEmpty(ssid) || !ssid.equals(this.deviceName)) {
            return false;
        }
        if (this.userIdentity == 1) {
            return true;
        }
        return false;
    }

    public boolean isGuest() {
        String ssid = LeboxQrCodeBean.getSsid();
        if (!TextUtils.isEmpty(ssid) && ssid.equals(this.deviceName) && this.userIdentity == 2) {
            return true;
        }
        return false;
    }

    public boolean isLogin() {
        String ssid = LeboxQrCodeBean.getSsid();
        if (TextUtils.isEmpty(ssid) || !ssid.equals(this.deviceName)) {
            return false;
        }
        if (this.userIdentity == 1 || this.userIdentity == 2) {
            return true;
        }
        return false;
    }

    public void setUserIdentity(String deviceName, int userIdentity) {
        this.deviceName = deviceName;
        this.userIdentity = userIdentity;
    }

    public int getUserIdentity() {
        String ssid = LeboxQrCodeBean.getSsid();
        if (TextUtils.isEmpty(ssid) || !ssid.equals(this.deviceName)) {
            return 0;
        }
        return this.userIdentity;
    }

    public boolean isIdentityDeviceChange() {
        String ssid = LeboxQrCodeBean.getSsid();
        if (TextUtils.isEmpty(ssid) || ssid.equals(this.deviceName)) {
            return false;
        }
        return true;
    }
}
