package com.letv.android.client.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.commonlib.share.ShareResultObserver;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LetvQZoneShare {
    private static File file;
    private static Activity mActivity;
    private static LetvQZoneShare mLetvQZoneShare = null;
    private static Tencent mTencent;
    public static List<ShareResultObserver> observers = new ArrayList();
    private ShareAlbumBean album;
    private VideoShotShareInfoBean mVideoShotShareInfoBean;

    public static LetvQZoneShare getInstance(Context context) {
        if (mLetvQZoneShare == null) {
            mLetvQZoneShare = new LetvQZoneShare();
            mTencent = TencentInstance.getInstance(context);
        }
        return mLetvQZoneShare;
    }

    private LetvQZoneShare() {
    }

    public void gotoSharePage(Activity activity, ShareAlbumBean album, int order, int vid, String staticsId, String fragId) {
        this.album = album;
        mActivity = null;
        SharePageActivity.launch(activity, 3, album.Share_AlbumName, album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, ShareAlbumBean album, int order, int vid, String content, String staticsId, String fragId) {
        this.album = album;
        mActivity = null;
        SharePageActivity.launch(activity, 3, ShareUtils.getLinkcardTitleText(album.cid, 3, album.isFeature, album.playCount, album.Share_PidName, album.position, content), album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, this.album.isFeature ? content : "", staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, ShareAlbumBean album, int order, int vid, int mode, String content, String staticsId, String fragId) {
        this.album = album;
        mActivity = null;
        if (mode == 5) {
            SharePageActivity.launch(activity, 3, album.Share_AlbumName, album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 12, content, staticsId, fragId);
        } else if (mode == 6) {
            SharePageActivity.launch(activity, 3, album.Share_AlbumName, album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 13, content, staticsId, fragId);
        }
    }

    public void gotoSharePage(Activity activity, ShareAlbumBean album, VideoShotShareInfoBean vs, int order, int vid, int mode, String staticsId, String fragId) {
        this.album = album;
        mActivity = null;
        this.mVideoShotShareInfoBean = vs;
        if (mode == 4) {
            SharePageActivity.launch(activity, 3, album.Share_AlbumName, this.mVideoShotShareInfoBean.mPhotoPath, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 11, this.mVideoShotShareInfoBean.mRandText, staticsId, fragId);
        }
    }

    public void gotoSharePage(Activity activity, String title, String webUrl, String imgUrl, int launchMode, String content, String staticsId, String fragId) {
        mActivity = null;
        LogInfo.log("fornia", "share---gotoSharePage");
        SharePageActivity.launchToLive(activity, 3, title, webUrl, imgUrl, launchMode, content, staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, String title, String webUrl, String imgUrl, String awardUrl, int launchMode, String content, String staticsId, String fragId) {
        mActivity = activity;
        SharePageActivity.launch(activity, 3, title, content, webUrl, imgUrl, awardUrl, launchMode, staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, String title, String imageUrl, String webUrl, int launchMode, String staticsId, String fragId) {
        mActivity = null;
        SharePageActivity.launch(activity, 3, title, imageUrl, webUrl, launchMode, staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, String title, String content, String imageUrl, String webUrl, int launchMode, String staticsId, String fragId) {
        mActivity = null;
        SharePageActivity.launch((Context) activity, 3, title, content, imageUrl, webUrl, launchMode, staticsId, fragId);
    }

    public void gotoSignSharePage(Activity activity, String title, String webUrl, String shareType, boolean isInvite, String staticsId, String fragId) {
        mActivity = null;
        SignSharePageEditActivity.launch(activity, 3, title, "", "", webUrl, shareType, isInvite, staticsId, fragId);
    }

    public void gotoSignSharePage(Activity activity, String title, String desc, String imageUrl, String webUrl, String shareType, boolean isInvite, String staticsId, String fragId) {
        mActivity = null;
        SignSharePageEditActivity.launch(activity, 3, title, desc, imageUrl, webUrl, shareType, isInvite, staticsId, fragId);
    }

    public void shareToQzone(Activity activity, String title, String content, int mode, String staticsId, String fragId) {
        LogInfo.log("fornia", "share---1开始 shareToQzone" + activity + content);
        if (LetvShareControl.getInstance().getShare() != null && LetvShareControl.getInstance().getShare().video_url != null) {
            mActivity = null;
            String replace_url = LetvShareControl.getInstance().getShare().video_url.replace("{aid}", this.album.Share_id + "").replace("{index}", "1").replace("{vid}", this.album.Share_vid + "");
            Bundle params = new Bundle();
            ArrayList<String> icon = new ArrayList();
            if (mode != 11 || this.mVideoShotShareInfoBean == null) {
                icon.add(this.album.icon);
            } else {
                icon.add(this.mVideoShotShareInfoBean.mPhotoPath);
            }
            if (mode == 12) {
                params.putInt("req_type", 1);
                params.putString("targetUrl", replace_url);
                params.putStringArrayList("imageUrl", icon);
            } else {
                params.putInt("req_type", 1);
                params.putString("targetUrl", replace_url);
                params.putStringArrayList("imageUrl", icon);
            }
            String str = "title";
            if (TextUtils.isEmpty(title)) {
                title = LetvUtils.getString(2131099758);
            }
            params.putString(str, title);
            params.putString("summary", content);
            IUiListener listener = new 1(this, activity, fragId, staticsId);
            if (mTencent == null) {
                mTencent = TencentInstance.getInstance(activity);
            }
            new Thread(new 2(this, activity, params, listener)).start();
        }
    }

    private void callShareResult(boolean isSucceed) {
        Iterator it = observers.iterator();
        if (it.hasNext()) {
            ShareResultObserver observer = (ShareResultObserver) it.next();
            if (isSucceed) {
                observer.onShareSucceed();
            } else {
                observer.onShareFail();
            }
        }
    }

    public void shareLiveToQzone(String imgIcon, Activity activity, String title, String content, String targetUrl, String staticsId, String fragId) {
        LogInfo.log("fornia", "share---2开始 2shareLiveToQzone" + activity + content);
        LogInfo.log("fornia", "share---2开始 imgIcon" + imgIcon);
        mActivity = null;
        Bundle params = new Bundle();
        ArrayList<String> icon = new ArrayList();
        if (!TextUtils.isEmpty(imgIcon)) {
            icon.add(imgIcon);
        }
        params.putInt("req_type", 1);
        String str = "title";
        if (TextUtils.isEmpty(title)) {
            title = LetvUtils.getString(2131099758);
        }
        params.putString(str, title);
        params.putString("summary", content);
        params.putString("targetUrl", targetUrl);
        params.putStringArrayList("imageUrl", icon);
        IUiListener listener = new 3(this, fragId, staticsId, activity);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        new Thread(new 4(this, activity, params, listener)).start();
    }

    public void shareLiveToQzone(Activity activity, String title, String content, String targetUrl, String imgUrl, String staticsId, String fragId) {
        LogInfo.log("fornia", "share---2开始 2shareLiveToQzone" + activity + content + "|" + targetUrl + "|" + imgUrl);
        Bundle params = new Bundle();
        ArrayList<String> icon = new ArrayList();
        if (!TextUtils.isEmpty(imgUrl)) {
            icon.add(imgUrl);
        }
        params.putInt("req_type", 1);
        String str = "title";
        if (TextUtils.isEmpty(title)) {
            title = LetvUtils.getString(2131099758);
        }
        params.putString(str, title);
        params.putString("summary", content);
        params.putString("targetUrl", targetUrl);
        params.putStringArrayList("imageUrl", icon);
        IUiListener listener = new 5(this, fragId, staticsId, activity);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        new Thread(new 6(this, activity, params, listener)).start();
    }

    public void logout(Context context) {
        if (mTencent != null) {
            mTencent.logout(context);
        }
    }

    public void shareLiveToQzone(Activity activity, String title, String content, String targetUrl, String imgUrl, String webUrl, String staticsId, String fragId) {
        mActivity = null;
        LogInfo.log("fornia", "share---3开始 3shareLiveToQzone" + activity + content);
        Bundle params = new Bundle();
        ArrayList<String> icon = new ArrayList();
        icon.add(imgUrl);
        params.putInt("req_type", 1);
        String str = "title";
        if (TextUtils.isEmpty(title)) {
            title = LetvUtils.getString(2131099758);
        }
        params.putString(str, title);
        params.putString("summary", content);
        params.putString("targetUrl", webUrl);
        params.putStringArrayList("imageUrl", icon);
        IUiListener listener = new 7(this, activity, fragId, staticsId);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        new Thread(new 8(this, activity, params, listener)).start();
    }

    public void shareLiveToQzone(Activity activity, String title, String content, String targetUrl, String imgUrl, String webUrl, int launchMode, String staticsId, String fragId) {
        mActivity = null;
        LogInfo.log("fornia", "share---3开始 3shareLiveToQzone" + activity + content);
        Bundle params = new Bundle();
        ArrayList<String> icon = new ArrayList();
        icon.add(imgUrl);
        params.putInt("req_type", 1);
        String str = "title";
        if (TextUtils.isEmpty(title)) {
            title = LetvUtils.getString(2131099758);
        }
        params.putString(str, title);
        params.putString("summary", content);
        params.putString("targetUrl", webUrl);
        params.putString("appName", LetvUtils.getString(2131100830));
        params.putStringArrayList("imageUrl", icon);
        IUiListener listener = new 9(this, activity, launchMode, fragId, staticsId);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        new Thread(new 10(this, activity, params, listener)).start();
    }

    public void shareQzoneToInvite(Activity activity, String title, String content, String targetUrl, String webUrl, String imageUrl, String shareType, String staticsId, String fragId) {
        mActivity = null;
        if (shareType.equals("text")) {
            LogInfo.log("fornia", "share---2shareQzoneToInvite" + activity + content);
            shareLiveToQzone(imageUrl, activity, title, content, targetUrl, staticsId, fragId);
            return;
        }
        if (shareType.equals("image")) {
            shareLiveToQzone(activity, title, "", targetUrl, imageUrl, webUrl, staticsId, fragId);
            return;
        }
        shareLiveToQzone(activity, title, content, targetUrl, imageUrl, webUrl, staticsId, fragId);
    }
}
