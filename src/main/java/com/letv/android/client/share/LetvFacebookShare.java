package com.letv.android.client.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareLinkContent.Builder;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.widget.ShareDialog.Mode;
import com.letv.android.client.view.HalfPlaySharePopwindow;
import com.letv.core.constant.ShareConstant;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LetvFacebookShare {
    private static LetvFacebookShare facebookShare;
    public static String mFragId;
    public static String mStaticsId;

    public LetvFacebookShare() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static LetvFacebookShare getInstance() {
        if (facebookShare == null) {
            facebookShare = new LetvFacebookShare();
        }
        return facebookShare;
    }

    public void shareToFacebook(Activity context, String title, String desc, String imgUrl, String url, String staticsId, String fragId) {
        mStaticsId = staticsId;
        mFragId = fragId;
        LogInfo.log("sx", "imgUrl==" + imgUrl);
        LogInfo.log("sx", "share desc===" + desc + "==title==" + title + "===replace_url==" + url);
        ShareLinkContent content = ((Builder) new Builder().setImageUrl(Uri.parse(imgUrl)).setContentTitle(title).setContentDescription(desc).setContentUrl(Uri.parse(url))).build();
        ShareDialog mShareDialog = new ShareDialog(context);
        if (HalfPlaySharePopwindow.mFacebookCallbackManager != null) {
            LogInfo.log("sx", "mFacebookCallbackManager  != null");
            mShareDialog.registerCallback(HalfPlaySharePopwindow.mFacebookCallbackManager, new 1(this, context));
        }
        mShareDialog.show(content, Mode.AUTOMATIC);
    }

    public static String getFacebookClickShareUrl(int actionType, int vid, int aid, int cid, int isPanorama, String from) {
        return ShareConstant.SHARE_URL_TYPE_CLICK_PLAY.replace("{actionType}", actionType + "").replace("{vid}", vid + "").replace("{aid}", TextUtils.isEmpty(new StringBuilder().append(aid).append("").toString()) ? "null" : aid + "").replace("{cid}", TextUtils.isEmpty(new StringBuilder().append(cid).append("").toString()) ? "null" : cid + "").replace("{isPanorama}", isPanorama + "").replace("{from}", from);
    }

    public static String getFacebookLiveShareUrl(int actionType, String liveType, String liveId, String from) {
        String replace = ShareConstant.SHARE_URL_TYPE_Live_PLAY.replace("{actionType}", actionType + "");
        CharSequence charSequence = "{liveType}";
        if (TextUtils.isEmpty(liveType)) {
            liveType = "null";
        }
        replace = replace.replace(charSequence, liveType);
        charSequence = "{liveId}";
        if (TextUtils.isEmpty(liveId)) {
            liveId = "null";
        }
        return replace.replace(charSequence, liveId).replace("{from}", from);
    }

    public void shareToFacebookLive(Activity context, String title, String desc, String imgUrl, String liveUrl, String staticsId, String fragId) {
        mStaticsId = staticsId;
        mFragId = fragId;
        LogInfo.log("fornia", "share desc===" + desc + "===title" + title + "===liveUrl" + liveUrl + "===imgUrl" + imgUrl);
        ShareLinkContent content = ((Builder) new Builder().setContentTitle(title).setContentDescription(desc).setContentUrl(Uri.parse(liveUrl))).build();
        ShareDialog mShareDialog = new ShareDialog(context);
        if (HalfPlaySharePopwindow.mFacebookCallbackManager != null) {
            mShareDialog.registerCallback(HalfPlaySharePopwindow.mFacebookCallbackManager, new 2(this));
        }
        mShareDialog.show(content, Mode.AUTOMATIC);
    }

    public void shareToFacebookPic(Activity context, String caption, String imgLocalPath, String staticsId, String fragId) {
        mStaticsId = staticsId;
        mFragId = fragId;
        LogInfo.log("fornia", "share imgLocalPath===" + imgLocalPath + "===caption" + caption);
        Bitmap bmp = BitmapFactory.decodeFile(imgLocalPath);
        if (bmp != null) {
            SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(new SharePhoto.Builder().setBitmap(bmp).setCaption(caption).build()).build();
            ShareDialog mShareDialog = new ShareDialog(context);
            if (HalfPlaySharePopwindow.mFacebookCallbackManager != null) {
                LogInfo.log("sx", "mFacebookCallbackManager  != null");
                mShareDialog.registerCallback(HalfPlaySharePopwindow.mFacebookCallbackManager, new 3(this, context));
            }
            mShareDialog.show(content, Mode.AUTOMATIC);
        }
    }
}
