package com.letv.android.client.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

public class letvTencentShare {
    private static Activity mActivity;
    public static letvTencentShare mInstance = null;
    private static QQShare mQQShare = null;
    private static Tencent mTencent = null;
    private ShareAlbumBean album;
    private VideoShotShareInfoBean mVideoShotShareInfoBean;

    public letvTencentShare() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static synchronized letvTencentShare getInstance(Context context) {
        letvTencentShare com_letv_android_client_share_letvTencentShare;
        synchronized (letvTencentShare.class) {
            if (mInstance == null) {
                mInstance = new letvTencentShare();
                mTencent = TencentInstance.getInstance(context);
            }
            com_letv_android_client_share_letvTencentShare = mInstance;
        }
        return com_letv_android_client_share_letvTencentShare;
    }

    public void onTencentShareComplete(int requestCode, int resultCode, Intent data) {
        LogInfo.log("fornia", "SharePageActivity onTencentShareComplete resultCode" + resultCode);
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    public void gotoSharePage(Activity activity, ShareAlbumBean album, VideoShotShareInfoBean vs, int order, int vid, int mode, String content, String staticsId, String fragId) {
        this.album = album;
        mActivity = null;
        this.mVideoShotShareInfoBean = vs;
        if (mode == 4) {
            SharePageActivity.launch(activity, 6, this.mVideoShotShareInfoBean.mVideoName, this.mVideoShotShareInfoBean.mPhotoPath, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 11, content, staticsId, fragId);
        } else if (mode == 5) {
            SharePageActivity.launch(activity, 6, album.Share_AlbumName, album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 12, content, staticsId, fragId);
        } else if (mode == 6) {
            SharePageActivity.launch(activity, 6, "", album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 13, content, staticsId, fragId);
        } else {
            SharePageActivity.launch(activity, 6, ShareUtils.getLinkcardTitleText(album.cid, 3, album.isFeature, album.playCount, album.Share_PidName, album.position, content), album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, 2, this.album.isFeature ? content : "", staticsId, fragId);
        }
    }

    public void gotoSharePage(Activity activity, String title, String webUrl, String imgUrl, int launchMode, String content, String staticsId, String fragId) {
        mActivity = null;
        SharePageActivity.launchToLive(activity, 6, title, webUrl, imgUrl, launchMode, content, staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, String title, String webUrl, String imgUrl, String awardUrl, int launchMode, String content, String staticsId, String fragId) {
        mActivity = activity;
        SharePageActivity.launch(activity, 6, title, content, webUrl, imgUrl, awardUrl, launchMode, staticsId, fragId);
    }

    public void gotoSharePage(Activity activity, String title, String content, String imageUrl, String webUrl, int launchMode, String staticsId, String fragId) {
        mActivity = null;
        SharePageActivity.launch((Context) activity, 6, title, content, imageUrl, webUrl, launchMode, staticsId, fragId);
    }

    public void gotoSignSharePage(Activity activity, String title, String desc, String imageUrl, String webUrl, String staticsId, String fragId) {
        mActivity = null;
        SharePageActivity.launch((Context) activity, 6, title, imageUrl, webUrl, -1, staticsId, fragId, desc);
    }

    public void shareToTencent(Activity activity, String title, String summary, String staticsId, String fragId) {
        if (LetvShareControl.getInstance().getShare() != null && LetvShareControl.getInstance().getShare().video_url != null) {
            mActivity = null;
            String replace_url = LetvShareControl.getInstance().getShare().video_url.replace("{aid}", this.album.Share_id + "").replace("{index}", "1").replace("{vid}", this.album.Share_vid + "");
            Bundle params = new Bundle();
            params.putInt("req_type", 1);
            String str = "title";
            if (TextUtils.isEmpty(title)) {
                title = LetvUtils.getString(2131099758);
            }
            params.putString(str, title);
            params.putString("summary", summary);
            params.putString("targetUrl", replace_url);
            params.putString("imageUrl", this.album == null ? "" : this.album.icon);
            params.putString("appName", LetvUtils.getString(2131099758));
            params.putInt("cflag", 2);
            IUiListener listener = new 1(this, fragId, staticsId, activity);
            if (mTencent == null) {
                mTencent = TencentInstance.getInstance(activity);
            }
            mTencent.shareToQQ(activity, params, listener);
        }
    }

    public void shareToTencent(Activity activity, String title, String summary, int mode, String staticsId, String fragId) {
        if (LetvShareControl.getInstance().getShare() != null && LetvShareControl.getInstance().getShare().video_url != null) {
            mActivity = null;
            String replace_url = LetvShareControl.getInstance().getShare().video_url.replace("{aid}", this.album.Share_id + "").replace("{index}", "1").replace("{vid}", this.album.Share_vid + "");
            Bundle params = new Bundle();
            params.putInt("req_type", 1);
            String str = "title";
            if (TextUtils.isEmpty(title)) {
                title = LetvUtils.getString(2131099758);
            }
            params.putString(str, title);
            params.putString("summary", summary);
            params.putString("targetUrl", replace_url);
            params.putString("imageUrl", this.album == null ? "" : this.album.icon);
            params.putString("appName", LetvUtils.getString(2131099758));
            params.putInt("cflag", 2);
            IUiListener listener = new 2(this, fragId, staticsId, activity);
            if (mTencent == null) {
                mTencent = TencentInstance.getInstance(activity);
            }
            mTencent.shareToQQ(activity, params, listener);
        }
    }

    public void shareLiveToTencent(Activity activity, String title, String summary, String imageUrl, String targetUrl, String staticsId, String fragId) {
        LogInfo.log("fornia", "shareLiveToTencent nomode！！！！");
        Bundle params = new Bundle();
        String str = "title";
        if (TextUtils.isEmpty(title)) {
            title = LetvUtils.getString(2131099758);
        }
        params.putString(str, title);
        params.putString("appName", LetvUtils.getString(2131099758));
        params.putString("summary", summary);
        if (!TextUtils.isEmpty(imageUrl)) {
            params.putString("imageUrl", imageUrl);
        }
        params.putString("targetUrl", targetUrl);
        params.putInt("req_type", 1);
        params.putInt("cflag", 2);
        IUiListener listener = new 3(this, fragId, staticsId, activity);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        mTencent.shareToQQ(activity, params, listener);
    }

    public void shareLiveToTencent(Activity activity, String title, String summary, String targetUrl, String imageUrl, String webUrl, String staticsId, String fragId) {
        mActivity = null;
        Bundle params = new Bundle();
        params.putInt("req_type", 1);
        String str = "title";
        if (TextUtils.isEmpty(title)) {
            title = LetvUtils.getString(2131099758);
        }
        params.putString(str, title);
        params.putString("summary", summary);
        params.putString("targetUrl", webUrl);
        params.putString("imageUrl", imageUrl);
        params.putString("appName", LetvUtils.getString(2131099758));
        params.putInt("cflag", 2);
        IUiListener listener = new 4(this, fragId, staticsId, activity);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        mTencent.shareToQQ(activity, params, listener);
    }

    public void shareLiveToTencent(Activity activity, String title, String summary, String webUrl, String imageUrl, String targetUrl, int launchMode, String staticsId, String fragId) {
        LogInfo.log("fornia", "7777shareLiveToTencent launchMode！！！！" + launchMode + "title:" + title + "summary:" + summary + "webUrl:" + webUrl + "imageUrl:" + imageUrl + "targetUrl:" + targetUrl);
        mActivity = null;
        Bundle params = new Bundle();
        String str;
        if (launchMode == 11) {
            params.putInt("req_type", 5);
            str = "title";
            if (TextUtils.isEmpty(title)) {
                title = activity.getResources().getString(2131099758);
            }
            params.putString(str, title);
            params.putString("summary", summary);
            params.putString("targetUrl", targetUrl);
            params.putString("imageLocalUrl", imageUrl);
            params.putString("appName", activity.getResources().getString(2131099758));
        } else {
            params.putInt("req_type", 1);
            str = "title";
            if (TextUtils.isEmpty(title)) {
                title = activity.getResources().getString(2131100830);
            }
            params.putString(str, title);
            params.putString("summary", summary);
            params.putString("targetUrl", targetUrl);
            params.putString("imageUrl", imageUrl);
            params.putString("appName", activity.getResources().getString(2131100830));
        }
        params.putInt("cflag", 2);
        IUiListener listener = new 5(this, fragId, staticsId, launchMode, activity);
        if (mTencent == null) {
            mTencent = TencentInstance.getInstance(activity);
        }
        mTencent.shareToQQ(activity, params, listener);
    }
}
