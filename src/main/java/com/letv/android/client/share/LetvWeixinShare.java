package com.letv.android.client.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.commonlib.listener.GiftShareAwardCallback;
import com.letv.android.client.utils.UIs;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.ShareConstant.Weixin;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.utils.FileUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class LetvWeixinShare {
    public static boolean isShareFriendZone = false;
    public static Activity mActivity;
    public static String mAwardUrl = "";
    public static String mFragId;
    public static GiftShareAwardCallback mGiftShareAwardCallback;
    public static int mLaunchMode = -1;
    private static String mShareCaption = "";
    private static String mShareCaptionLepai = "";
    private static String mSharePicCaption = "";
    private static String mSharePicTitle = "";
    private static String mShareTitle = "";
    private static String mShareTitleLepai = "";
    public static String mStaticsId;
    private static int shareMode;

    public LetvWeixinShare() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void share(Activity context, String title, String caption, String imaUrl, String playUrl, int cid, boolean timeline, String staticsId, String fragId) {
        mActivity = null;
        new SendMessageToWx(context, title, caption, imaUrl, playUrl, cid, timeline, staticsId, fragId).execute(new Void[0]);
    }

    public static void share(Activity context, String title, String caption, String imaUrl, String playUrl, int cid, boolean isPlainText, boolean timeline, String staticsId, String fragId) {
        mActivity = null;
        new SendMessageToWx(context, title, caption, imaUrl, playUrl, cid, isPlainText, timeline, staticsId, fragId).execute(new Void[0]);
    }

    public static void share(Activity context, String title, String caption, String imaUrl, String playUrl, String awardUrl, GiftShareAwardCallback giftShareAwardCallback, boolean timeline, int mode, String staticsId, String fragId) {
        mActivity = context;
        new SendMessageToWx(context, title, caption, imaUrl, playUrl, awardUrl, giftShareAwardCallback, timeline, mode, staticsId, fragId).execute(new Void[0]);
    }

    public static void share(Activity context, String title, String caption, String imaUrl, String playUrl, int cid, int mode, boolean timeline, String staticsId, String fragId) {
        mActivity = null;
        new SendMessageToWx(context, title, caption, imaUrl, playUrl, cid, mode, timeline, staticsId, fragId).execute(new Void[0]);
    }

    public static void share(Activity context, String title, String caption, String liveUrl, boolean timeline, String staticsId, String fragId) {
        mSharePicTitle = title;
        mSharePicCaption = caption;
        isShareFriendZone = timeline;
        mStaticsId = staticsId;
        mFragId = fragId;
        mLaunchMode = -1;
        mActivity = null;
        try {
            LetvApplication mLetvApplication = (LetvApplication) context.getApplicationContext();
            IWXAPI api = WXAPIFactory.createWXAPI(context, Weixin.APP_ID, true);
            api.registerApp(Weixin.APP_ID);
            if (timeline) {
                int wxSdkVersion = api.getWXAppSupportAPI();
                LogInfo.log("fornia", "wxSdkVersion1:" + wxSdkVersion + "|TIMELINE_SUPPORTED_SDK_INT:" + 553779201);
                if (wxSdkVersion == 0) {
                    if (api.openWXApp()) {
                        new Handler().postDelayed(new 1(context, liveUrl, timeline), 3000);
                        return;
                    }
                } else if (wxSdkVersion < 553779201) {
                    ToastUtils.showToast((Context) context, 2131101164);
                    mLetvApplication.setWxisShare(false);
                    if (context != null && (context instanceof ShareAllChannelActivity)) {
                        context.finish();
                        return;
                    }
                    return;
                }
            }
            if (!TextUtils.isEmpty(title) && title.getBytes().length > 512) {
                title = title.substring(0, 512);
            }
            if (!TextUtils.isEmpty(caption) && caption.getBytes().length > 1024) {
                caption = caption.substring(0, 1024);
            }
            WXMediaMessage msg = new WXMediaMessage();
            WXWebpageObject webpageObject = new WXWebpageObject();
            webpageObject.webpageUrl = liveUrl;
            msg.mediaObject = webpageObject;
            msg.title = title;
            msg.description = caption;
            Bitmap bmp = BitmapFactory.decodeResource(mLetvApplication.getResources(), 2130838248);
            if (bmp != null) {
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT, 90, true);
                bmp.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
            }
            Req req = new Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            if (timeline) {
                LogInfo.log("wx", "---wx pengyouquan----timeline---" + timeline + "----req.transaction:" + req.transaction);
                req.scene = 1;
            }
            api.sendReq(req);
            if (context != null && (context instanceof ShareAllChannelActivity)) {
                context.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ((LetvApplication) context.getApplicationContext()).setWxisShare(false);
        }
    }

    public static void share(Activity context, String title, String caption, String liveUrl, boolean timeline, int mode, String staticsId, String fragId) {
        mShareTitleLepai = title;
        mShareCaptionLepai = caption;
        isShareFriendZone = timeline;
        mStaticsId = staticsId;
        mFragId = fragId;
        mGiftShareAwardCallback = null;
        mAwardUrl = "";
        mLaunchMode = -1;
        mActivity = null;
        try {
            LetvApplication mLetvApplication = (LetvApplication) context.getApplicationContext();
            IWXAPI api = WXAPIFactory.createWXAPI(context, Weixin.APP_ID, true);
            api.registerApp(Weixin.APP_ID);
            if (timeline) {
                int wxSdkVersion = api.getWXAppSupportAPI();
                LogInfo.log("fornia", "wxSdkVersion1:" + wxSdkVersion + "|TIMELINE_SUPPORTED_SDK_INT:" + 553779201);
                if (wxSdkVersion == 0) {
                    if (api.openWXApp()) {
                        new Handler().postDelayed(new 2(context, liveUrl, timeline), 3000);
                        return;
                    }
                } else if (wxSdkVersion < 553779201) {
                    ToastUtils.showToast((Context) context, 2131101164);
                    mLetvApplication.setWxisShare(false);
                    if (context != null && (context instanceof ShareAllChannelActivity)) {
                        context.finish();
                        return;
                    }
                    return;
                }
            }
            if (!TextUtils.isEmpty(title) && title.getBytes().length > 512) {
                title = title.substring(0, 512);
            }
            if (!TextUtils.isEmpty(caption) && caption.getBytes().length > 1024) {
                caption = caption.substring(0, 1024);
            }
            WXMediaMessage msg = new WXMediaMessage();
            WXVideoObject videoObject = new WXVideoObject();
            LogInfo.log("wx", "---wx pengyouquan----videoUrl---" + liveUrl + "----title---" + title + "----description---" + caption);
            videoObject.videoUrl = liveUrl;
            msg.mediaObject = videoObject;
            if (mode == 10) {
                if (timeline) {
                    msg.title = caption;
                } else {
                    msg.title = title;
                }
                msg.description = caption;
            } else {
                msg.description = "";
            }
            Bitmap bmp = BitmapFactory.decodeResource(mLetvApplication.getResources(), 2130838248);
            if (bmp != null) {
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT, 90, true);
                bmp.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
            }
            Req req = new Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            if (timeline) {
                LogInfo.log("wx", "---wx pengyouquan----timeline---" + timeline + "----req.transaction:" + req.transaction);
                req.scene = 1;
            }
            api.sendReq(req);
            if (context != null && (context instanceof ShareAllChannelActivity)) {
                context.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ((LetvApplication) context.getApplicationContext()).setWxisShare(false);
        }
    }

    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String buildTransaction(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static void sharePic(Activity context, String title, String caption, String imgNetUrl, String imgLocalPath, boolean timeline, String staticsId, String fragId) {
        LogInfo.log("fornia", "title:" + title + "caption:" + caption + "imgNetUrl:" + imgNetUrl + "imgLocalPath:" + imgLocalPath + "timeline:" + timeline);
        mSharePicTitle = title;
        mSharePicCaption = caption;
        isShareFriendZone = timeline;
        mStaticsId = staticsId;
        mFragId = fragId;
        mGiftShareAwardCallback = null;
        mAwardUrl = "";
        mLaunchMode = -1;
        mActivity = null;
        try {
            LetvApplication mLetvApplication = (LetvApplication) context.getApplicationContext();
            IWXAPI api = WXAPIFactory.createWXAPI(context, Weixin.APP_ID, true);
            api.registerApp(Weixin.APP_ID);
            if (timeline) {
                int wxSdkVersion = api.getWXAppSupportAPI();
                LogInfo.log("fornia", "wxSdkVersion2:" + wxSdkVersion + "|TIMELINE_SUPPORTED_SDK_INT:" + 553779201);
                if (wxSdkVersion == 0) {
                    if (api.openWXApp()) {
                        new Handler().postDelayed(new 3(context, imgNetUrl, imgLocalPath, timeline), 3000);
                        return;
                    }
                } else if (wxSdkVersion < 553779201) {
                    ToastUtils.showToast((Context) context, 2131101164);
                    mLetvApplication.setWxisShare(false);
                    return;
                }
            }
            if (!TextUtils.isEmpty(title) && title.getBytes().length > 512) {
                title = title.substring(0, 512);
            }
            if (!TextUtils.isEmpty(caption) && caption.getBytes().length > 1024) {
                caption = caption.substring(0, 1024);
            }
            WXImageObject imgObj = new WXImageObject();
            imgObj.setImagePath(imgLocalPath);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            msg.title = title;
            msg.description = caption;
            Bitmap bmp = BitmapFactory.decodeFile(imgLocalPath);
            if (bmp != null) {
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT, 90, true);
                bmp.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp, true);
            }
            if (msg.thumbData != null) {
                LogInfo.log("fornia", "title:" + msg.thumbData.length);
            }
            Req req = new Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            if (timeline) {
                req.scene = 1;
            }
            api.sendReq(req);
        } catch (Exception e) {
            LogInfo.log("fornia", "Exception e:" + e);
            e.printStackTrace();
            ((LetvApplication) context.getApplicationContext()).setWxisShare(false);
        }
    }

    public static Bitmap returnBitMap(String url) {
        URL url2;
        MalformedURLException e;
        IOException e2;
        Bitmap bitmap = null;
        InputStream is = null;
        if (shareMode == 4) {
            shareMode = -1;
            if (!(TextUtils.isEmpty(url) || url.contains(IDataSource.SCHEME_HTTP_TAG))) {
                try {
                    bitmap = FileUtils.getBitmapByPath(url, LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT, LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT);
                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                }
            }
        } else {
            try {
                URL myFileUrl = new URL(url);
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    url2 = myFileUrl;
                } catch (MalformedURLException e4) {
                    e = e4;
                    url2 = myFileUrl;
                    e.printStackTrace();
                    LogInfo.log("fornia", "addparam bitmap:" + bitmap);
                    return bitmap;
                } catch (IOException e5) {
                    e2 = e5;
                    url2 = myFileUrl;
                    e2.printStackTrace();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    LogInfo.log("fornia", "addparam bitmap:" + bitmap);
                    return bitmap;
                }
            } catch (MalformedURLException e6) {
                e = e6;
                e.printStackTrace();
                LogInfo.log("fornia", "addparam bitmap:" + bitmap);
                return bitmap;
            } catch (IOException e7) {
                e2 = e7;
                e2.printStackTrace();
                if (is != null) {
                    is.close();
                }
                LogInfo.log("fornia", "addparam bitmap:" + bitmap);
                return bitmap;
            }
        }
        LogInfo.log("fornia", "addparam bitmap:" + bitmap);
        return bitmap;
    }

    public static void shareWXToInvite(Activity context, String title, String desc, String webUrl, String webImage, String shareType, boolean isTimeLine, String staticsId, String fragId) {
        LetvApplication mLetvApplication = (LetvApplication) context.getApplicationContext();
        if (!ShareUtils.checkPackageInstalled(context, "com.tencent.mm")) {
            UIs.callDialogMsgPositiveButton(context, DialogMsgConstantId.SEVEN_ZERO_SEVEN_CONSTANT, null);
        } else if (!mLetvApplication.isWxisShare()) {
            mLetvApplication.setWxisShare(true);
            new SendImageToInvite(context, title, desc, webUrl, webImage, shareType, isTimeLine, staticsId, fragId).execute(new Void[0]);
        }
    }
}
