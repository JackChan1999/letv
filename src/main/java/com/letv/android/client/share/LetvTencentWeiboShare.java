package com.letv.android.client.share;

import android.app.Activity;
import android.content.Context;
import com.letv.cache.LetvCacheTools.StringTool;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.share.tencent.weibo.ex.ITWeiboNew;
import com.letv.share.tencent.weibo.ex.ITWeiboNew.TWeiboListener;

public class LetvTencentWeiboShare {
    public LetvTencentWeiboShare() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static int isLogin(Context context) {
        return ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).isLogin(context);
    }

    public static void login(Activity context, ShareAlbumBean album, int witch, int order, int vid, String playMark, int mode, String staticsId, String fragId) {
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 1(context, witch, album, order, vid, playMark, mode, staticsId, fragId));
    }

    public static void login(Activity context, ShareAlbumBean album, int witch, int order, int vid, int mode, String comment, String staticsId, String fragId) {
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 2(context, witch, album, order, vid, mode, comment, staticsId, fragId));
    }

    public static void login(Activity context, String title, int witch, String liveUrl, String liveId, int launchMode, String staticsId, String fragId) {
        if (context != null && (context instanceof ShareAllChannelActivity)) {
            context.finish();
        }
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 3(context, witch, title, liveUrl, liveId, launchMode, staticsId, fragId));
    }

    public static void login(Activity context, String title, int witch, String liveUrl, String liveType, String liveId, int launchMode, String staticsId, String fragId) {
        if (context != null && (context instanceof ShareAllChannelActivity)) {
            context.finish();
        }
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 4(context, witch, title, liveUrl, liveType, liveId, launchMode, staticsId, fragId));
    }

    public static void login(Activity context, String title, int witch, String liveUrl, int launchMode, String role, String staticsId, String fragId) {
        if (context != null && (context instanceof ShareAllChannelActivity)) {
            context.finish();
        }
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 5(context, witch, title, liveUrl, launchMode, role, staticsId, fragId));
    }

    public static void share(Activity context, String caption, String imgUrl, boolean isQZoom, TWeiboListener listener) {
        try {
            ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).share(context, listener, caption, imgUrl, isQZoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sharePic(Activity context, String caption, String imgUrl, boolean isQZoom, TWeiboListener listener) {
        try {
            ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).sharePic(context, listener, caption, StringTool.createFilePath(imgUrl), isQZoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout(Activity context) {
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).logout(context);
    }

    public static void loginPic(Activity context, String title, String imgUrl, String webUrl, String staticsId, String fragId) {
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 6());
    }

    public static void loginTencentWeibo(Activity context, String title, String desc, String imgUrl, String webUrl, String shareType, boolean isInvite, String staticsId, String fragId) {
        ((ITWeiboNew) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_SHARE_NAME, JarConstant.LETV_SHARE_PACKAGENAME, "tencent.weibo.TWeiboNew"), "getInstance", null, null)).login(context, new 7(context, title, desc, imgUrl, webUrl, shareType, staticsId, fragId));
    }

    public static void shareTencentToInvite(Activity context, String content, String webImage, String shareType, TWeiboListener listener) {
        if (shareType.equals("text")) {
            sharePic(context, content, "", false, listener);
        } else if (shareType.equals("image")) {
            sharePic(context, content, webImage, false, listener);
        } else {
            share(context, content, webImage, false, listener);
        }
    }
}
