package com.letv.android.client.album.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.service.ScreenProjectionService;
import com.letv.android.client.album.utils.ScreenProjectionDialogManager;
import com.letv.android.client.album.view.ScreenProjectionLoadingDialog;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.ScreenProjectionCacheFileResultBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.HttpRequestMethod;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.parser.ScreenProjectionCacheFileParser;
import com.letv.core.parser.ScreenProjectionCacheFileResultParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.itv.threenscreen.service.AIDLActivity.Stub;
import com.letv.itv.threenscreen.service.AIDLService;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.tencent.open.SocialConstants;
import java.net.URLEncoder;
import java.util.Timer;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class ScreenProjectionController {
    private static final String BASE_URL = "http://duoping.go.letv.com/";
    public static final int PROJECTION_STATE_CACHE = 2;
    public static final int PROJECTION_STATE_NO = 0;
    public static final int PROJECTION_STATE_PUSH = 1;
    private static final int SCR_PROJ_TIME_OUT = 15000;
    public static final int SCR_PROJ_TYPE_CACHE = 1;
    public static final int SCR_PROJ_TYPE_DLNA = 2;
    public static final int SCR_PROJ_TYPE_PUSH = 0;
    private static AIDLService mAIDLService;
    private Stub mAIDLCallback = new 6(this);
    private AlbumPlayActivity mAlbumPlayActivity;
    private AlbumPlayFlow mAlbumPlayFlow;
    private DialogCallback mDialogCallback;
    private boolean mHasBinded = false;
    private Intent mIntent;
    private boolean mIsLocked = false;
    private Runnable mLoadCacheFileCallback = new 3(this);
    private Runnable mLoadDevicesCallback = new 2(this);
    private ScreenProjectionLoadingDialog mLoadingDialog;
    public int mProjectionState;
    private OnPushToTvResponseCallback mPushToTVResultCallback;
    private int mScreenProjectionMode = 0;
    private ScreenProjectionServiceConnection mServiceConnection;
    private String mStreamCode1080P = null;
    private String mStreamCode720P = null;
    private Timer mTimeOutTimer;

    public interface OnPushToTvResponseCallback {
        void onPushResponse(SCREEN_PROJECTION_RESPONSE screen_projection_response);
    }

    public ScreenProjectionController(AlbumPlayActivity activity) {
        this.mAlbumPlayActivity = activity;
        this.mAlbumPlayFlow = activity.getFlow();
        this.mIntent = new Intent(this.mAlbumPlayActivity, ScreenProjectionService.class);
        this.mLoadingDialog = new ScreenProjectionLoadingDialog(this.mAlbumPlayActivity, R.string.device_list_loading, true);
        this.mDialogCallback = createDialogCallback();
    }

    public void setOnPushToTvResponseCallback(OnPushToTvResponseCallback callback) {
        this.mPushToTVResultCallback = callback;
    }

    private DialogCallback createDialogCallback() {
        return new 1(this);
    }

    public void performScreenProject(int type) {
        this.mScreenProjectionMode = type;
        switch (type) {
            case 0:
                pushToSuperTV();
                return;
            case 1:
                cacheToSuperTV();
                return;
            case 2:
                return;
            default:
                this.mScreenProjectionMode = -1;
                return;
        }
    }

    private void pushToSuperTV() {
        this.mProjectionState = 1;
        if (!NetworkUtils.isNetworkAvailable()) {
            showToast(this.mAlbumPlayActivity, this.mAlbumPlayActivity.getString(R.string.three_screen_no_net));
        } else if (getAlbum() == null || getVideo() == null) {
            showToast(this.mAlbumPlayActivity, this.mAlbumPlayActivity.getString(R.string.three_screen_currvideo_unsupport_push));
        } else {
            this.mDialogCallback.lockDirection();
            if (!isLogin() || bindScreenProjectionService(this.mLoadDevicesCallback)) {
            }
        }
    }

    private void cacheToSuperTV() {
        LogInfo.log("wuxinrong", "缓存到超级电视");
        this.mProjectionState = 2;
        if (!NetworkUtils.isNetworkAvailable()) {
            showToast(this.mAlbumPlayActivity, this.mAlbumPlayActivity.getString(R.string.three_screen_no_net));
        } else if (getAlbum() == null || getVideo() == null) {
            showToast(this.mAlbumPlayActivity, this.mAlbumPlayActivity.getString(R.string.three_screen_currvideo_unsupport_down));
        } else {
            this.mDialogCallback.lockDirection();
            if (!isLogin()) {
                LogInfo.log("wuxinrong", "未登陆会员，直接退出");
            } else if (!bindScreenProjectionService(this.mLoadCacheFileCallback)) {
                LogInfo.log("wuxinrong", "未绑定服务，直接退出");
            }
        }
    }

    private AlbumInfo getAlbum() {
        return this.mAlbumPlayFlow.mVideoBelongedAlbum;
    }

    private VideoBean getVideo() {
        return this.mAlbumPlayFlow.mCurrentPlayingVideo;
    }

    private Bundle genScreenProjectionBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(UserInfoDb.USER_ID, LetvUtils.getLoginUserName());
        bundle.putString("version", Global.VERSION);
        bundle.putString("deviceName", LetvUtils.getLoginUserName() + "2");
        bundle.putString("brandCode", LetvUtils.getBrandName());
        bundle.putString("seriesCode", LetvUtils.getDeviceName());
        bundle.putString("mac", LetvUtils.getIMSI());
        bundle.putString("videoId", getVideo() == null ? "" : getVideo().vid + "");
        bundle.putString(PageJumpUtil.IN_TO_ALBUM_PID, getAlbum() == null ? "" : getAlbum().pid + "");
        bundle.putString("title", getVideo() == null ? "" : getVideo().nameCn);
        bundle.putString("time", getPlayTime() + "");
        return bundle;
    }

    private long getPlayTime() {
        if (this.mAlbumPlayActivity.getFlow() != null) {
            return this.mAlbumPlayActivity.getFlow().mPlayInfo.currTime;
        }
        return 0;
    }

    private void searchDevices() {
        if (mAIDLService == null) {
            LogInfo.log("wuxinrong", "AIDL 服务为空！！！");
            return;
        }
        if (NetworkUtils.isWifi()) {
            this.mLoadingDialog.show();
            this.mTimeOutTimer = new Timer();
            this.mTimeOutTimer.schedule(new 4(this), 15000);
        } else {
            if (this.mDialogCallback != null) {
                this.mDialogCallback.unlockDirection();
            }
            this.mLoadingDialog.dismiss();
        }
        try {
            mAIDLService.queryDiviceList(genScreenProjectionBundle(), this.mScreenProjectionMode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean isLogin() {
        boolean isLogin = PreferencesManager.getInstance().isLogin();
        if (!isLogin) {
            if (this.mScreenProjectionMode == 0) {
                ScreenProjectionDialogManager.showPushLogin(this.mAlbumPlayActivity, this.mDialogCallback, this.mPushToTVResultCallback);
            } else {
                ScreenProjectionDialogManager.showLoginDialog(this.mAlbumPlayActivity, this.mDialogCallback, this.mPushToTVResultCallback);
            }
        }
        return isLogin;
    }

    private synchronized boolean bindScreenProjectionService(Runnable callback) {
        boolean result;
        result = false;
        try {
            this.mServiceConnection = new ScreenProjectionServiceConnection(this, new 5(this, callback));
            result = this.mAlbumPlayActivity.bindService(this.mIntent, this.mServiceConnection, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized void unbindScreenProjectionService() {
        if (this.mHasBinded) {
            this.mHasBinded = false;
            try {
                LogInfo.log("wuxinrong", "解除绑定服务");
                this.mAlbumPlayActivity.unbindService(this.mServiceConnection);
            } catch (Exception e) {
            }
            mAIDLService = null;
        }
    }

    private void showDevices(String data, int requestId) {
        this.mAlbumPlayActivity.runOnUiThread(new 7(this, data, requestId));
    }

    private void cancelToastTimer() {
        if (this.mTimeOutTimer != null) {
            this.mTimeOutTimer.cancel();
        }
    }

    private void performPushPlay(String deviceId) {
        try {
            mAIDLService.sendPush(genScreenProjectionBundle(), deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToast(Context context, String msg) {
        ((Activity) context).runOnUiThread(new 8(this, msg));
    }

    private VolleyRequest<ScreenProjectionCacheFileResultBean> createCacheFileRequest(Activity activity, String url) {
        return new LetvRequest().setUrl(url).setRequestType(RequestManner.NETWORK_ONLY).setParser(new ScreenProjectionCacheFileResultParser()).setCallback(new 9(this, activity));
    }

    private void performCacheFile(String deviceId) {
        Runnable runnable = null;
        if (this.mStreamCode720P != null) {
            String url_720p = genCacheFileUrl(deviceId, this.mStreamCode720P);
            if (url_720p != null) {
                runnable = new 10(this, url_720p);
            }
        }
        if (this.mStreamCode1080P != null) {
            String url_1080p = genCacheFileUrl(deviceId, this.mStreamCode1080P);
            if (url_1080p != null) {
                runnable = new 11(this, url_1080p);
            }
        }
        ScreenProjectionDialogManager.showStreamCodeListDialog(this.mAlbumPlayActivity, runnable, null, this.mDialogCallback);
    }

    private void requestVideoCacheFileInfo() {
        long id = -1;
        LogInfo.log("wuxinrong", "请求缓存文件信息");
        VideoBean vb = this.mAlbumPlayFlow.mCurrentPlayingVideo;
        if (vb != null) {
            id = vb.vid;
        }
        LogInfo.log("wuxinrong", "生成缓存文件url");
        String requestUrl = genCacheFileInfoUrl(id);
        LogInfo.log("wuxinrong", "通过url请求缓存文件 = " + requestUrl);
        requestVideoCacheFileInfoByUrl(this.mAlbumPlayActivity, requestUrl);
    }

    private String genCacheFileInfoUrl(long vid) {
        return assembleRequestUrl(genCacheFileInfoParams(vid), HttpRequestMethod.GET);
    }

    private Bundle genCacheFileInfoParams(long videoId) {
        Bundle params = new Bundle();
        String uid = PreferencesManager.getInstance().getUserName();
        String vid = videoId > 0 ? String.valueOf(videoId) : "";
        params.putString(SocialConstants.PARAM_ACT, "download_query_video_list");
        params.putString(UserInfoDb.USER_ID, uid);
        params.putString("videoId", vid);
        return params;
    }

    private String genCacheFileUrl(String deviceId, String streamCode) {
        String url = assembleRequestUrl(genCacheFileParams(deviceId, streamCode), HttpRequestMethod.GET);
        LogInfo.log("wuxinrong", "缓存文件的url = " + url);
        return url;
    }

    private Bundle genCacheFileParams(String targetDeviceId, String streamCode) {
        Bundle bundle = new Bundle();
        String data = "[{'videoInfoId':" + (getVideo() == null ? "" : Long.valueOf(getVideo().vid)) + ",'title':" + (getVideo() == null ? "" : getVideo().nameCn) + ",'stream':" + streamCode + "}]";
        bundle.putString(UserInfoDb.USER_ID, PreferencesManager.getInstance().getUserId());
        bundle.putString("version", Global.VERSION);
        bundle.putString(SocialConstants.PARAM_ACT, "download_push");
        bundle.putString("toDeviceId", targetDeviceId + "");
        bundle.putString("deviceType", "2");
        bundle.putString("seq", ((int) (Math.random() * 10000.0d)) + "");
        bundle.putString("dt", data);
        return bundle;
    }

    private static String assembleRequestUrl(Bundle bundle, HttpRequestMethod type) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL);
        boolean isFirstKey = true;
        for (String key : bundle.keySet()) {
            if (isFirstKey) {
                if (type == HttpRequestMethod.GET || type == HttpRequestMethod.AUTO) {
                    sb.append("?");
                }
                isFirstKey = false;
            } else {
                sb.append("&");
            }
            String value = bundle.getString(key);
            if (value != null) {
                sb.append(key + SearchCriteria.EQ + URLEncoder.encode(value));
            } else {
                sb.append(key + SearchCriteria.EQ);
            }
        }
        return LetvUtils.genLangResRequestUrl(sb.toString(), 0);
    }

    private void requestVideoCacheFileInfoByUrl(Context context, String url) {
        new LetvRequest().setUrl(url).setRequestType(RequestManner.NETWORK_ONLY).setParser(new ScreenProjectionCacheFileParser()).setCallback(new 12(this, context)).add();
    }
}
