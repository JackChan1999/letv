package com.letv.android.client.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.config.MainActivityConfig;
import com.letv.android.client.parser.ChannelsParser;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.cybergarage.upnp.std.av.server.ContentDirectory;

public class GotoPageUtils {
    public static String GOTO_CHANNEL_CID_KEY = "cid";
    public static String GOTO_CHANNEL_PAGE_ID_KEY = PlayConstant.PAGE_ID;
    public static String GOTO_CHILD_LIVE_PAGE_KAY = MainActivityConfig.CHILD_LIVE_ID;
    public static String GOTO_PAGE_TAG_KAY = "tag";
    private static String TAG = "GotoPageUtils";
    private Handler mHandler;

    /* synthetic */ GotoPageUtils(1 x0) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this();
    }

    private GotoPageUtils() {
    }

    public static GotoPageUtils getSingleton() {
        return SingletonHolder.mGotoPageUtils;
    }

    public boolean gotoPageFromWebview(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        LogInfo.log(TAG + "||wlx", "webview 跳转客户端 url=" + url);
        if (this.mHandler == null) {
            this.mHandler = new Handler(context.getMainLooper());
        }
        if ("Home".equals(url)) {
            return jumpToHome(context);
        }
        if (url.startsWith("1711_")) {
            return jumpToChannel(context, url);
        }
        if (url.startsWith("Me_")) {
            return jumpToMe(context, url);
        }
        if (url.startsWith("Live_")) {
            return jumpToLive(context, url);
        }
        if (url.startsWith("Hot")) {
            return jumpToHot(context);
        }
        if (url.startsWith("Topics")) {
            return jumpToTopics(context);
        }
        if (url.startsWith(ContentDirectory.SEARCH)) {
            return jumpToSearch(context);
        }
        if (url.startsWith("NiceApp")) {
            return jumpToNiceApp(context);
        }
        ToastUtils.showToast(context, context.getString(2131100509));
        return false;
    }

    private boolean jumpToNiceApp(Context context) {
        this.mHandler.post(new 1(this, context));
        return true;
    }

    private boolean jumpToSearch(Context context) {
        this.mHandler.post(new 2(this, context));
        return true;
    }

    private boolean jumpToTopics(Context context) {
        this.mHandler.post(new 3(this, context));
        return true;
    }

    private boolean jumpToHot(Context context) {
        this.mHandler.post(new 4(this, context));
        return true;
    }

    private boolean jumpToLive(Context context, String url) {
        try {
            String[] strings = url.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            if (strings.length != 2) {
                return true;
            }
            this.mHandler.post(new 5(this, context, strings[1]));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLiveChildId(String pageId) {
        LogInfo.log("live_", "pageId = " + pageId);
        if (pageId.equals(PageIdConstant.onLiveremenCtegoryPage) || pageId.equals(PlayConstant.CHANNEL_TYPE_VALUE_REMEN)) {
            return PlayConstant.CHANNEL_TYPE_VALUE_REMEN;
        }
        if (pageId.equals(PageIdConstant.onLiveLunboCtegoryPage) || pageId.equals("lunbo")) {
            return "lunbo";
        }
        if (pageId.equals(PageIdConstant.onLiveWeishiCtegoryPage) || pageId.equals("weishi")) {
            return "weishi";
        }
        if (pageId.equals(PageIdConstant.onLiveSportCtegoryPage) || pageId.equals("sports")) {
            return "sports";
        }
        if (pageId.equals(PageIdConstant.onLiveMusicCtegoryPage) || pageId.equals("music")) {
            return "music";
        }
        if (pageId.equals(PageIdConstant.onLiveEntertainmentCtegoryPage) || pageId.equals("ent")) {
            return "ent";
        }
        return null;
    }

    private boolean jumpToMe(Context context, String url) {
        if ("Me_Record".equals(url)) {
            this.mHandler.post(new 6(this, context));
            return true;
        } else if ("Me_Favorites".equals(url)) {
            this.mHandler.post(new 7(this, context));
            return true;
        } else if ("Me_Download".equals(url)) {
            this.mHandler.post(new 8(this, context));
            return true;
        } else if (url.startsWith("Me_Checkout")) {
            this.mHandler.post(new 9(this, url, context));
            return true;
        } else if ("Me_Points".equals(url)) {
            if (NetworkUtils.isNetworkAvailable()) {
                this.mHandler.post(new 10(this, context));
                return true;
            }
            ToastUtils.showToast(context, 2131100493);
            return false;
        } else if ("Me_Login".equals(url)) {
            this.mHandler.post(new 11(this, context));
            return true;
        } else if (!"Me_Setting".equals(url)) {
            return false;
        } else {
            this.mHandler.post(new 12(this, context));
            return true;
        }
    }

    private boolean jumpToChannel(Context context, String url) {
        if (NetworkUtils.isNetworkAvailable()) {
            String[] strings = url.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            String cid = "";
            String pageid = "";
            if (strings.length == 3) {
                cid = strings[1];
                pageid = strings[2];
            } else if (strings.length == 2) {
                cid = strings[1];
            }
            LogInfo.log(TAG + "||wlx", "strings: " + strings + ", cid: " + cid + ", pageid: " + pageid);
            this.mHandler.post(new 13(this, context, cid, pageid));
        } else {
            ToastUtils.showToast(context, 2131100332);
        }
        return false;
    }

    private void gotoChannel(Context context, String cid, String pageId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(GOTO_PAGE_TAG_KAY, FragmentConstant.TAG_FRAGMENT_CHANNEL);
        intent.putExtra(GOTO_CHANNEL_CID_KEY, cid);
        intent.putExtra(GOTO_CHANNEL_PAGE_ID_KEY, pageId);
        context.startActivity(intent);
    }

    private void getChannelList(Context context, String cid, String pageId) {
        new LetvRequest(ChannelListBean.class).setUrl(MediaAssetApi.getInstance().getChannelListUrl(null)).setParser(new ChannelsParser()).setCallback(new 14(this, context, cid, pageId)).add();
    }

    private boolean jumpToHome(Context context) {
        this.mHandler.post(new 15(this, context));
        return true;
    }
}
