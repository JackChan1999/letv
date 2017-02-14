package com.letv.mobile.lebox;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.http.lebox.bean.FollowAddExtBean;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumBean;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumTagBean;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.ui.album.DownloadVideoAlbumPageActivity;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeboxApiManager {
    private static final String TAG = LeboxApiManager.class.getSimpleName();
    public static final int TYPE_VIDEO_ALL = 0;
    public static final int TYPE_VIDEO_COMPLETED = 1;
    public static final int TYPE_VIDEO_NOT_COMPLETED = 2;
    private static LeboxApiManager mApiManager;
    private static LetvMediaPlayer mLetvMediaPlayer;
    private boolean isConnected;
    private LeboxConnectReceiver mLeboxConnectReceiver;

    public interface LetvMediaPlayer {
        void doPlay(Context context, LeboxVideoBean leboxVideoBean);
    }

    private LeboxApiManager() {
    }

    public static LeboxApiManager getInstance() {
        if (mApiManager != null) {
            return mApiManager;
        }
        synchronized (LeboxApiManager.class) {
            mApiManager = new LeboxApiManager();
        }
        return mApiManager;
    }

    public boolean isLeboxConnection() {
        return LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable();
    }

    public boolean isLeboxLogin() {
        return HttpCacheAssistant.getInstanced().isLogin();
    }

    public boolean isAdministrator() {
        return HttpCacheAssistant.getInstanced().isAdmini();
    }

    public List<LeboxVideoBean> getVideoByPid(String pid, int type) {
        if (TextUtils.isEmpty(pid)) {
            return null;
        }
        List<LeboxVideoBean> videoList = new ArrayList();
        if ((type == 0 || type == 1) && !HttpCacheAssistant.getInstanced().isCompleteTaskEmpty()) {
            for (TaskVideoBean v : HttpCacheAssistant.getInstanced().getCompleteList()) {
                if (pid.equals(v.getPid())) {
                    videoList.add(replaceCarapace(v, true));
                }
            }
        }
        if ((type == 0 || type == 2) && !HttpCacheAssistant.getInstanced().isUnFinishTaskEmpty()) {
            for (TaskVideoBean v2 : HttpCacheAssistant.getInstanced().getUnFinishList()) {
                if (pid.equals(v2.getPid())) {
                    videoList.add(replaceCarapace(v2, false));
                }
            }
        }
        if (videoList.size() <= 1) {
            return videoList;
        }
        Collections.sort(videoList, new 1(this));
        return videoList;
    }

    public LeboxVideoBean replaceCarapace(TaskVideoBean video, boolean isDowdloadCompleted) {
        LeboxVideoBean bean = new LeboxVideoBean();
        if (video != null) {
            bean.pid = video.getPid();
            bean.vid = video.getVid();
            bean.isDowdloadCompleted = isDowdloadCompleted;
            bean.albumName = video.getInfoAlbumName();
            bean.videoName = video.getInfoVideoName();
            bean.cid = video.getInfoAlbumType();
            bean.episode = video.getInfoVideoEpisode();
            bean.videoURL = getVideoUrl(bean.vid);
            bean.stream = video.getInfoVideoStream();
            bean.btime = video.getInfoVideoBtime();
            bean.etime = video.getInfoVideoEtime();
            bean.duration = video.getInfoVideoDuration();
        }
        return bean;
    }

    @Deprecated
    public boolean isHasVideo(String vid) {
        return false;
    }

    @Deprecated
    public boolean isHasVideoCanPlay(String vid) {
        return false;
    }

    public String getVideoUrl(String vid) {
        return HttpRequesetManager.getLeboxVideoFilePath("0", vid);
    }

    public boolean isFollowed(String pid) {
        if (TextUtils.isEmpty(pid) || HttpCacheAssistant.getInstanced().isFollowEmpty()) {
            return false;
        }
        for (FollowAlbumBean f : HttpCacheAssistant.getInstanced().getFollowList()) {
            if (pid.equals(f.getPid())) {
                return true;
            }
        }
        return false;
    }

    public void setFollow(ResultCallback<Boolean> resultCallback, String pid, int stream, String albumTitle) {
        FollowAddExtBean followAddExtBean = new FollowAddExtBean();
        followAddExtBean.setStream(String.valueOf(stream));
        FollowAlbumTagBean albumTagBean = new FollowAlbumTagBean();
        albumTagBean.setAlbumCover("");
        albumTagBean.setAlbumTitle(albumTitle);
        HttpRequesetManager.getInstance().addFollowAlbum(new 2(this, resultCallback), pid, followAddExtBean, albumTagBean);
    }

    @Deprecated
    public List<FollowBean> getAllFollow() {
        return null;
    }

    public static void gotoLeboxMainPage(Activity activity) {
        if (!LetvUtils.isGooglePlay()) {
            if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid()) || TextUtils.isEmpty(LeboxQrCodeBean.getPassword())) {
                gotoLeboxIntroducePage(activity);
                return;
            }
            try {
                PageJumpUtil.jumpLeBoxMainActivity(activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void gotoLeboxIntroducePage(Activity activity) {
        try {
            PageJumpUtil.jumpToIntroduce(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void gotoLeboxAlbumPage(Activity activity, long pid, int page, int from) {
        DownloadVideoAlbumPageActivity.launch(activity, pid, page, from);
    }

    public void setLetvMediaPlayer(LetvMediaPlayer letvMediaPlayer) {
        mLetvMediaPlayer = letvMediaPlayer;
    }

    public boolean onPlayLeboxVideo(Context context, TaskVideoBean video) {
        if (video == null) {
            return false;
        }
        if (mLetvMediaPlayer != null) {
            mLetvMediaPlayer.doPlay(context, replaceCarapace(video, true));
            HttpRequesetManager.getInstance().setVideoWatch(video);
            return true;
        }
        Util.showToast(R.string.toast_msg_medioplayer_not_find);
        return false;
    }

    public void setVideoWatch(String vid) {
        if (!TextUtils.isEmpty(vid) && !HttpCacheAssistant.getInstanced().isCompleteTaskEmpty()) {
            for (TaskVideoBean v : HttpCacheAssistant.getInstanced().getCompleteList()) {
                if (vid.equals(v.getVid())) {
                    HttpRequesetManager.getInstance().setVideoWatch(v);
                    return;
                }
            }
        }
    }

    public void onScanQRCode(Context context, String result) {
        if (!LeboxQrCodeBean.isWifiText(result)) {
            return;
        }
        if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable() && HttpCacheAssistant.getInstanced().isLogin()) {
            PageJumpUtil.jumpLeBoxMainActivity(context);
        } else {
            PageJumpUtil.jumpLeboxRipple(context);
        }
    }

    public void setLeboxConnectReceiver(LeboxConnectReceiver receiver) {
        this.isConnected = LeBoxNetworkManager.getInstance().isLeboxConnected();
        this.mLeboxConnectReceiver = receiver;
    }

    public void releaseLeboxReceiver() {
        this.mLeboxConnectReceiver = null;
    }

    public void setLeboxConnectChanged() {
        if (this.mLeboxConnectReceiver != null) {
            boolean connectState = LeBoxNetworkManager.getInstance().isLeboxConnected();
            if (this.isConnected != connectState && this.mLeboxConnectReceiver != null) {
                this.isConnected = connectState;
                this.mLeboxConnectReceiver.callBack(connectState);
            }
        }
    }

    public boolean isLeboxHasInternet() {
        return HeartbeatManager.getInstance().getHasInternet();
    }
}
