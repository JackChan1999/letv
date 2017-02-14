package com.letv.android.client.share;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.letv.android.client.activity.SplashActivity;
import com.letv.cache.LetvCacheTools.StringTool;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.bean.VideoShotShareInfoBean;
import com.letv.core.utils.LogInfo;
import com.letv.download.db.Download.DownloadBaseColumns;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.share.sina.ex.BOauth2AccessToken;
import com.letv.share.sina.ex.BSsoHandler;
import com.letv.share.sina.ex.BWeibo;
import com.letv.share.sina.ex.RequestListener;
import com.letv.share.sina.ex.WeiboException;
import com.letv.share.sina.ex.WeiboParameters;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONException;

public class LetvSinaShareSSO {
    public static BOauth2AccessToken accessToken;
    public static Oauth2AccessToken accessToken2;
    public static boolean isReady2Share = false;
    private IWeiboShareAPI mWeiboShareAPI;

    public LetvSinaShareSSO() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mWeiboShareAPI = null;
    }

    public static int isLogin(Context context) {
        if (accessToken == null) {
            accessToken = AccessTokenKeeper.readAccessToken(context);
        }
        return accessToken.isSessionValid();
    }

    public static boolean isLogin2(Context context) {
        if (accessToken2 == null) {
            accessToken2 = AccessTokenKeeper.read2ndAccessToken(context);
        }
        return accessToken2.isSessionValid();
    }

    public static BSsoHandler login(Activity context, ShareAlbumBean album, VideoShotShareInfoBean vs, int order, int vid, String playMark, int mode, String staticsId, String fragId) {
        isReady2Share = true;
        if ((isLogin(context) == 1 || isLogin2(context)) && (context instanceof Activity)) {
            if (mode == 4) {
                LogInfo.log("fornia", "HalfPlaySharePopwindow.SHARE_FROM_VIDEOSHOT:");
                SharePageEditActivity.launch(context, 1, album.Share_AlbumName, vs.mPhotoPath, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, album.sharedPid, vs.mRandText, "", mode, staticsId, fragId);
            } else {
                SharePageEditActivity.launch(context, 1, album.Share_AlbumName, album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, album.sharedPid, "", playMark, mode, staticsId, fragId);
            }
            return null;
        }
        Class weibo_clazz = JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo");
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, weibo_clazz" + weibo_clazz);
        Class cls = weibo_clazz;
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, mWeibo" + JarLoader.invokeStaticMethod(cls, "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"}));
        Class ssohandler_clazz = JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.sso.SsoHandler");
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, ssohandler_clazz" + ssohandler_clazz);
        BSsoHandler mSsoHandler = (BSsoHandler) JarLoader.newInstance(ssohandler_clazz, new Class[]{Context.class, Object.class}, new Object[]{context, mWeibo});
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, mSsoHandler" + mSsoHandler);
        mSsoHandler.authorize(new 1(context, mode, album, vs, order, vid, staticsId, fragId, playMark));
        return mSsoHandler;
    }

    public static BSsoHandler login(Activity context, ShareAlbumBean album, VideoShotShareInfoBean vs, int order, int vid, int mode, String comment, String staticsId, String fragId) {
        isReady2Share = true;
        if ((isLogin(context) == 1 || isLogin2(context)) && (context instanceof Activity)) {
            LogInfo.log("fornia", "登录了 album.Share_AlbumName：" + album.Share_AlbumName + "|评论或者角色 comment:" + comment);
            SharePageEditActivity.launch(context, 1, album.Share_AlbumName, album.icon, album.Share_id, album.type, album.cid, album.year, album.director, album.actor, (long) album.timeLength, order, vid, mode, comment, staticsId, fragId);
            return null;
        }
        Class weibo_clazz = JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo");
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, weibo_clazz" + weibo_clazz);
        Class cls = weibo_clazz;
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, mWeibo" + JarLoader.invokeStaticMethod(cls, "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"}));
        Class ssohandler_clazz = JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.sso.SsoHandler");
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, ssohandler_clazz" + ssohandler_clazz);
        BSsoHandler mSsoHandler = (BSsoHandler) JarLoader.newInstance(ssohandler_clazz, new Class[]{Context.class, Object.class}, new Object[]{context, mWeibo});
        LogInfo.log("ljnalex", "-------Weibo, ssoHandler, mSsoHandler" + mSsoHandler);
        mSsoHandler.authorize(new 2(context, album, order, vid, mode, comment, staticsId, fragId));
        return mSsoHandler;
    }

    public static BSsoHandler login(Activity context, String title, String liveUrl, String liveType, String liveId, int launchMode, String staticsId, String fragId) {
        isReady2Share = true;
        if (isLogin(context) == 1 || isLogin2(context)) {
            LogInfo.log("fornia", "登录了~");
            SharePageEditActivity.launch(context, 1, title, liveUrl, liveType, liveId, launchMode, staticsId, fragId);
            return null;
        }
        LogInfo.log("fornia", "没有登录~");
        Object mWeibo = JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo"), "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"});
        BSsoHandler mSsoHandler = (BSsoHandler) JarLoader.newInstance(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.sso.SsoHandler"), new Class[]{Context.class, Object.class}, new Object[]{context, mWeibo});
        mSsoHandler.authorize(new 3(context, title, liveUrl, liveType, liveId, launchMode, staticsId, fragId));
        return mSsoHandler;
    }

    public static BSsoHandler login(Activity context, String title, String liveUrl, int launchMode, String role, String staticsId, String fragId) {
        isReady2Share = true;
        if (isLogin(context) == 1 || isLogin2(context)) {
            LogInfo.log("fornia", "登录了~");
            SharePageEditActivity.launch((Context) context, 1, title, liveUrl, launchMode, role, staticsId, fragId);
            return null;
        }
        LogInfo.log("fornia", "没有登录~");
        Object mWeibo = JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo"), "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"});
        BSsoHandler mSsoHandler = (BSsoHandler) JarLoader.newInstance(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.sso.SsoHandler"), new Class[]{Context.class, Object.class}, new Object[]{context, mWeibo});
        mSsoHandler.authorize(new 4(context, title, liveUrl, launchMode, role, staticsId, fragId));
        return mSsoHandler;
    }

    public static SsoHandler login(Activity context, String appName, String title, String content, String imgUrl, String playUrl, String from, int mode, String staticsId, String fragId) {
        if (isLogin(context) == 1 || isLogin2(context)) {
            SharePageEditActivity.launch((Context) context, 1, content, playUrl, "", mode, staticsId, fragId);
            if (context != null) {
                context.finish();
            }
            return null;
        }
        SsoHandler mSsoHandler = new SsoHandler(context, new AuthInfo(context, "3830215581", "http://m.letv.com", "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write"));
        mSsoHandler.authorize(new 5(context, content, playUrl, mode, staticsId, fragId));
        return mSsoHandler;
    }

    public static BSsoHandler login(Activity context, String title, String content, String imgUrl, String shareUrl, String awardUrl, int mode, String staticsId, String fragId) {
        isReady2Share = true;
        if (isLogin(context) == 1 || isLogin2(context)) {
            LogInfo.log("fornia", "登录了~");
            SharePageEditActivity.launch(context, 1, title, content, shareUrl, imgUrl, awardUrl, mode, staticsId, fragId);
            return null;
        }
        LogInfo.log("fornia", "没有登录~");
        Object mWeibo = JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo"), "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"});
        BSsoHandler mSsoHandler = (BSsoHandler) JarLoader.newInstance(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.sso.SsoHandler"), new Class[]{Context.class, Object.class}, new Object[]{context, mWeibo});
        mSsoHandler.authorize(new 6(context, title, content, shareUrl, imgUrl, awardUrl, mode, staticsId, fragId));
        return mSsoHandler;
    }

    public static void share(Context context, String caption, String imaUrl, int mode, RequestListener listener) {
        String path;
        BWeibo mWeibo = (BWeibo) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo"), "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"});
        if (mode == 4) {
            path = imaUrl;
        } else {
            path = StringTool.createFilePath(imaUrl);
        }
        try {
            if ((accessToken != null && !TextUtils.isEmpty(accessToken.getToken())) || (accessToken2 != null && !TextUtils.isEmpty(accessToken2.getToken()))) {
                if (TextUtils.isEmpty(path)) {
                    update(context, mWeibo, "3830215581", caption, "", "", listener);
                } else if (new File(path).exists()) {
                    upload(context, mWeibo, "3830215581", path, caption, "", "", listener);
                } else {
                    update(context, mWeibo, "3830215581", caption, "", "", listener);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (WeiboException e3) {
            e3.printStackTrace();
        }
    }

    private static String upload(Context context, BWeibo weibo, String source, String file, String status, String lon, String lat, RequestListener listener) throws WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add("source", source);
        bundle.add(DownloadBaseColumns.COLUMN_PIC, file);
        bundle.add("status", status);
        String str = "access_token";
        String token = (accessToken == null || TextUtils.isEmpty(accessToken.getToken())) ? accessToken2 == null ? "" : accessToken2.getToken() : accessToken.getToken();
        bundle.add(str, token);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add("lat", lat);
        }
        String rlt = "";
        String url = BWeibo.SERVER + "statuses/upload.json";
        JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.net.AsyncWeiboRunner"), SocialConstants.TYPE_REQUEST, new Class[]{String.class, WeiboParameters.class, String.class, RequestListener.class}, new Object[]{url, bundle, "POST", listener});
        return rlt;
    }

    private static String update(Context context, BWeibo weibo, String source, String status, String lon, String lat, RequestListener listener) throws IOException, WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add("source", source);
        bundle.add("status", status);
        String str = "access_token";
        String token = (accessToken == null || TextUtils.isEmpty(accessToken.getToken())) ? accessToken2 == null ? "" : accessToken2.getToken() : accessToken.getToken();
        bundle.add(str, token);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add("lat", lat);
        }
        String rlt = "";
        String url = BWeibo.SERVER + "statuses/update.json";
        JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.net.AsyncWeiboRunner"), SocialConstants.TYPE_REQUEST, new Class[]{String.class, WeiboParameters.class, String.class, RequestListener.class}, new Object[]{url, bundle, "POST", listener});
        return rlt;
    }

    public static void logout(Activity context) {
        accessToken = null;
        accessToken2 = null;
        AccessTokenKeeper.clear(context);
        JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.util.Utility"), "clearCookies", new Class[]{Context.class}, new Object[]{context});
    }

    private static void log(String log) {
        LogInfo.log("attendance", log);
    }

    public static BSsoHandler loginToSinaInvite(Activity context, String title, String webUrl, String webImage, String type, String staticsId, String fragId) throws JSONException {
        isReady2Share = true;
        if (isLogin(context) == 1 || isLogin2(context)) {
            LogInfo.log("fornia", "loginToSinaInvite title + \" \" + webUrl:" + title + " " + webUrl);
            SignSharePageEditActivity.launch(context, 1, title + " " + webUrl, "", webImage, webUrl, type, true, staticsId, fragId);
            return null;
        }
        Object mWeibo = JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.Weibo"), "getInstance", new Class[]{String.class, String.class}, new Object[]{"3830215581", "http://m.letv.com"});
        BSsoHandler mSsoHandler = (BSsoHandler) JarLoader.newInstance(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.sso.SsoHandler"), new Class[]{Context.class, Object.class}, new Object[]{context, mWeibo});
        mSsoHandler.authorize(new 7(context, title, webUrl, webImage, type, staticsId, fragId));
        return mSsoHandler;
    }

    public static void shareSinaToInvite(Context context, String title, String webImage, String shareType, RequestListener listener) {
        String path = StringTool.createFilePath(webImage);
        if ((accessToken != null && !TextUtils.isEmpty(accessToken.getToken())) || (accessToken2 != null && !TextUtils.isEmpty(accessToken2.getToken()))) {
            WeiboParameters bundle;
            String str;
            String token;
            String url;
            if (TextUtils.isEmpty(path)) {
                bundle = new WeiboParameters();
                bundle.add("source", "3830215581");
                bundle.add("status", title);
                str = "access_token";
                token = (accessToken == null || TextUtils.isEmpty(accessToken.getToken())) ? accessToken2 == null ? "" : accessToken2.getToken() : accessToken.getToken();
                bundle.add(str, token);
                url = BWeibo.SERVER + "statuses/update.json";
                JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.net.AsyncWeiboRunner"), SocialConstants.TYPE_REQUEST, new Class[]{String.class, WeiboParameters.class, String.class, RequestListener.class}, new Object[]{url, bundle, "POST", listener});
            } else if (new File(path).exists()) {
                bundle = new WeiboParameters();
                bundle.add("source", "3830215581");
                bundle.add("status", title);
                bundle.add(DownloadBaseColumns.COLUMN_PIC, path);
                str = "access_token";
                token = (accessToken == null || TextUtils.isEmpty(accessToken.getToken())) ? accessToken2 == null ? "" : accessToken2.getToken() : accessToken.getToken();
                bundle.add(str, token);
                url = BWeibo.SERVER + "statuses/upload.json";
                JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.net.AsyncWeiboRunner"), SocialConstants.TYPE_REQUEST, new Class[]{String.class, WeiboParameters.class, String.class, RequestListener.class}, new Object[]{url, bundle, "POST", listener});
            } else if (TextUtils.isEmpty(webImage)) {
                bundle = new WeiboParameters();
                bundle.add("source", "3830215581");
                bundle.add("status", title);
                bundle.add(DownloadBaseColumns.COLUMN_PIC, SplashActivity.path + "/letv/share/letv_icon.png");
                str = "access_token";
                token = (accessToken == null || TextUtils.isEmpty(accessToken.getToken())) ? accessToken2 == null ? "" : accessToken2.getToken() : accessToken.getToken();
                bundle.add(str, token);
                url = BWeibo.SERVER + "statuses/upload.json";
                JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.net.AsyncWeiboRunner"), SocialConstants.TYPE_REQUEST, new Class[]{String.class, WeiboParameters.class, String.class, RequestListener.class}, new Object[]{url, bundle, "POST", listener});
            } else {
                bundle = new WeiboParameters();
                bundle.add("source", "3830215581");
                bundle.add("status", title);
                str = "access_token";
                token = (accessToken == null || TextUtils.isEmpty(accessToken.getToken())) ? accessToken2 == null ? "" : accessToken2.getToken() : accessToken.getToken();
                bundle.add(str, token);
                url = BWeibo.SERVER + "statuses/update.json";
                JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "sina.net.AsyncWeiboRunner"), SocialConstants.TYPE_REQUEST, new Class[]{String.class, WeiboParameters.class, String.class, RequestListener.class}, new Object[]{url, bundle, "POST", listener});
            }
        }
    }

    private void sendMultiMessage(Activity context, boolean hasText, boolean hasImage, boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        AuthInfo authInfo = new AuthInfo(context, "3830215581", "http://m.letv.com", "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
        Oauth2AccessToken accessToken = AccessTokenKeeper.read2ndAccessToken(context);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        this.mWeiboShareAPI.sendRequest(context, request, authInfo, token, new 8(this, context));
    }
}
