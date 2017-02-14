package com.letv.android.client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.LiveRoomConfig;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.hot.LetvHotActivity;
import com.letv.android.client.zxing.result.ZxingUtil;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.LiveType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MainLaunchUtils {
    public static final String FORCELAUNCH = "forceLaunch";
    public static final String FORCEMSG = "forceMsg";
    public static final int LAUNCH_TO_HOT = 22;
    public static final int LAUNCH_TO_LIVE = 17;
    public static final int LAUNCH_TO_LIVE_CHANNEL = 20;
    public static final int LAUNCH_TO_LIVE_PUSH = 19;
    public static final int LAUNCH_TO_PLAY = 16;
    public static final int LAUNCH_TO_TOPIC_PLAY = 18;
    public static final int LAUNCH_TO_WB_VIEW = 21;

    public MainLaunchUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch4M(Activity activity, String tag) {
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra("tag", tag);
        i.putExtra("from_M", true);
        i.setFlags(268435456);
        activity.startActivityForResult(i, 10001);
        activity.finish();
    }

    public static void forcelaunch(Context context, String forceMsg, Bundle bundle) {
        Intent i = new Intent();
        if (MainActivity.getInstance() != null) {
            i.addFlags(67108864);
            i.addFlags(536870912);
        }
        i.setClass(context, MainActivity.class);
        if (!(context instanceof Activity)) {
            i.addFlags(268435456);
        }
        i.putExtra(MyDownloadActivityConfig.TO_HOME_PAGE, true);
        i.putExtra("forceLaunch", true);
        i.putExtra("forceMsg", forceMsg);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    public static void launch(Context context, boolean fromPush) {
        Intent i = new Intent();
        if (MainActivity.getInstance() != null) {
            i.addFlags(67108864);
            i.addFlags(536870912);
        }
        i.setClass(context, MainActivity.class);
        if (!(context instanceof Activity)) {
            i.addFlags(268435456);
        }
        i.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        if (fromPush) {
            i.putExtra(MyDownloadActivityConfig.TO_HOME_PAGE, true);
        }
        context.startActivity(i);
    }

    public static void launchToDownload(Context context) {
        Intent i = new Intent();
        if (MainActivity.getInstance() != null) {
            i.addFlags(67108864);
            i.addFlags(536870912);
        }
        i.setClass(context, MainActivity.class);
        if (!(context instanceof Activity)) {
            i.addFlags(268435456);
        }
        i.putExtra(MyDownloadActivityConfig.FROM_PUSH, true);
        i.putExtra(MyDownloadActivityConfig.TO_HOME_PAGE, true);
        i.putExtra(MyDownloadActivityConfig.TO_DOWNLOAD, true);
        context.startActivity(i);
    }

    public static void launchToPlay(Context context, long aid, long vid, boolean fromPush) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LaunchMode", 16);
        intent.putExtra("aid", aid);
        intent.putExtra("vid", vid);
        intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        if (MainActivity.getInstance() != null) {
            intent.addFlags(67108864);
            intent.addFlags(536870912);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchToTopicPlay(Context context, long zid, boolean fromPush) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LaunchMode", 18);
        intent.putExtra("pageId", zid);
        intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        if (MainActivity.getInstance() != null) {
            intent.addFlags(67108864);
            intent.addFlags(536870912);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchToHot(Context context, String pageId, int vid, boolean fromPush) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LaunchMode", 22);
        intent.putExtra("pageId", pageId);
        intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        intent.putExtra("vid", vid);
        if (MainActivity.getInstance() != null) {
            intent.addFlags(67108864);
            intent.addFlags(536870912);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchToLive(Context context, String code, String url, String liveMode, String channelId, boolean fromPush) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LaunchMode", 17);
        intent.putExtra("code", code);
        intent.putExtra("id", channelId);
        intent.putExtra("url", url);
        intent.putExtra("live_mode", liveMode);
        intent.putExtra(PlayConstant.LIVE_CHANNEL_ID, channelId);
        intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        if (MainActivity.getInstance() != null) {
            intent.addFlags(67108864);
            intent.addFlags(536870912);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchToLivePush(Context context, String zhiboId, boolean fromPush) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LaunchMode", 19);
        intent.putExtra(PlayConstant.LIVE_LAUNCH_ID, zhiboId);
        intent.addFlags(536870912);
        intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        if (MainActivity.getInstance() != null) {
            intent.addFlags(67108864);
            intent.addFlags(536870912);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchToWebView(Context context, String url, boolean fromPush) {
        if (!LetvUtils.isGooglePlay()) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("LaunchMode", 21);
            intent.putExtra("url", url);
            intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
            if (MainActivity.getInstance() != null) {
                intent.addFlags(67108864);
                intent.addFlags(536870912);
            }
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            context.startActivity(intent);
        }
    }

    public static void launchGotoLive(Context context, String channelType, String liveId, boolean isShowToast, boolean fromPush) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("LaunchMode", 20);
        intent.putExtra("channel_type", channelType);
        intent.putExtra("liveId", liveId);
        intent.putExtra("isShowToast", isShowToast);
        intent.putExtra(MyDownloadActivityConfig.FROM_PUSH, fromPush);
        intent.putExtra("isLivePush", true);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    public static void launchQRCodeScanActivity(Activity activity) {
        ZxingUtil.startCapture(activity, activity.getString(2131100440), activity.getString(2131100441));
    }

    public static void launch2Channel(Activity activity, int cid, int pageId, String cname, int type) {
        Channel channel = new Channel();
        channel.id = cid;
        channel.pageid = pageId + "";
        channel.type = type;
        channel.name = cname;
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra("tag", FragmentConstant.TAG_FRAGMENT_CHANNEL);
        i.putExtra("channel", channel);
        i.putExtra("from_M", true);
        i.setFlags(268435456);
        activity.startActivityForResult(i, 10001);
        activity.finish();
    }

    public static void jump2Pay(Activity activity) {
        if (activity != null) {
            if (PreferencesManager.getInstance().isLogin()) {
                LetvVipActivity.launch(activity, LetvApplication.getInstance().getResources().getString(2131100646));
            } else {
                LetvLoginActivity.launch(activity, false);
            }
            activity.finish();
        }
    }

    public static void jump2Live(Activity activity, String liveType, boolean isPay, String liveId, boolean isHalf) {
        LiveLaunchUtils.launchLives4M(activity, liveType, isPay, liveId, isHalf);
    }

    public static void jump2H5(Activity activity, String url) {
        if (!LetvUtils.isGooglePlay()) {
            if (activity == null || TextUtils.isEmpty(url)) {
                UIsUtils.showToast(2131101159);
            } else {
                new LetvWebViewActivityConfig(activity).launch(url, false, true, -1);
            }
        }
    }

    public static void jump2H5(Activity activity, String url, boolean isFinishSelf) {
        if (!LetvUtils.isGooglePlay()) {
            if (activity == null || TextUtils.isEmpty(url)) {
                UIsUtils.showToast(2131101159);
            } else {
                new LetvWebViewActivityConfig(activity).launch(url, false, isFinishSelf, 28);
            }
        }
    }

    public static void jump2Subject(Activity activity) {
        if (activity != null) {
            launch4M(activity, FragmentConstant.TAG_FRAGMENT_FIND);
        }
    }

    public static void jump2Channel(Activity activity, int cid, int pageId, String cname, int type) {
        if (activity != null) {
            launch2Channel(activity, cid, pageId, cname, type);
        }
    }

    public static void jump2My(Activity activity) {
        if (activity != null) {
            launch4M(activity, FragmentConstant.TAG_FRAGMENT_MINE);
        }
    }

    public static void jump2Hot(Activity activity) {
        if (activity != null) {
            activity.startActivity(new Intent(activity, LetvHotActivity.class));
            activity.finish();
        }
    }

    public static void jump2Home(Activity activity) {
        if (activity != null) {
            launch4M(activity, FragmentConstant.TAG_FRAGMENT_HOME);
        }
    }

    public static void launchDemand(Activity activity, Intent intent) {
        long aid = intent.getLongExtra("aid", 0);
        long vid = intent.getLongExtra("vid", 0);
        boolean isFromPush = intent.getBooleanExtra(MyDownloadActivityConfig.FROM_PUSH, false);
        if (aid > 0 || vid > 0) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(activity).create(aid, vid, isFromPush ? 13 : 2)));
        }
    }

    public static void launchLive(Context context, Intent intent) {
        String code = intent.getStringExtra("code");
        String url = intent.getStringExtra("url");
        String mode = intent.getStringExtra("live_mode");
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra(Field.PROGRAMNAME);
        String streamId = intent.getStringExtra(PlayConstant.LIVE_STREAMID);
        if (!TextUtils.isEmpty(mode)) {
            switch (Integer.valueOf(mode).intValue()) {
                case 101:
                    LiveLaunchUtils.launchLiveLunbo(context, code, false, name, "", id, "", false);
                    return;
                case 102:
                    LiveLaunchUtils.launchLiveWeishi(context, code, false, name, "", id, false);
                    return;
                case 103:
                    LiveLaunchUtils.launchLiveSports(context, name, id);
                    return;
                case 104:
                    LiveLaunchUtils.launchLiveEntertainment(context, name, id);
                    return;
                case 105:
                    LiveLaunchUtils.launchLiveMusic(context, name, id);
                    return;
                case LiveType.PLAY_LIVE_OTHER /*110*/:
                    LiveLaunchUtils.launchLiveOther(context, "");
                    return;
                default:
                    return;
            }
        } else if (!TextUtils.isEmpty(code) || !TextUtils.isEmpty(url)) {
            LiveRoomConfig.launchLives(context, code, streamId, url, true, 2, "");
        }
    }
}
