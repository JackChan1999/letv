package com.letv.android.client.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.letv.android.client.commonlib.R;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.BasePlayActivityConfig;
import com.letv.android.client.commonlib.config.ChannelDetailItemActivityConfig;
import com.letv.android.client.commonlib.config.LetvHotActivityConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvTopicActivityConfig;
import com.letv.android.client.commonlib.config.LetvVipActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.LiveRoomConfig;
import com.letv.android.client.commonlib.config.MainActivityConfig;
import com.letv.android.client.commonlib.config.MainActivityConfig.GoToChannelByCid;
import com.letv.android.client.commonlib.config.PointsActivtiyConfig;
import com.letv.android.client.commonlib.config.RecommendFragmentActivityConfig;
import com.letv.android.client.commonlib.config.SettingsMainActivityConfig;
import com.letv.android.client.commonlib.config.StarActivityConfig;
import com.letv.android.client.commonlib.config.StarRankActivityConfig;
import com.letv.android.client.commonlib.messagemodel.ChannelDetailConfig.ChannelTabData;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.RedirectData;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.UserBean;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.util.Constants;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginconfig.utils.JarLaunchUtils;
import com.letv.plugin.pluginloader.loader.JarLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class UIControllerUtils {
    public static final String ENTERTAINMENT_CHANNEL_ID = "03";
    public static final String MUSIC_CHANNEL_ID = "09";
    private static final boolean NEED_JUMP_LIVE_PANOTAMA = false;
    public static final String OTHER_CHANNEL_ID = "11";
    private static final String PAGE_ID_4D = "1002984593";
    public static final String SPORTS_CHANNEL_ID = "04";
    private static String mPageId = "";
    private static UserBean mUser = null;

    @Deprecated
    public static void gotoWeb(Context context, String url) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        intent.setFlags(1610612740);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtils.showToast(context, context.getString(R.string.jump_failed_toast));
        }
    }

    public static void setUser(UserBean user) {
        mUser = user;
    }

    public static String channelIdTochannelType(String channelID) {
        LogInfo.log("UIControllerUtils", "channelIdTochannelType channelID : " + channelID);
        String channelType = "sports";
        if (channelID.equals(ENTERTAINMENT_CHANNEL_ID)) {
            channelType = "ent";
        } else if (channelID.equals("11")) {
            channelType = "other";
        } else if (channelID.equals(MUSIC_CHANNEL_ID)) {
            channelType = "music";
        } else if (channelID.equals(SPORTS_CHANNEL_ID)) {
            channelType = "sports";
        }
        LogInfo.log("UIControllerUtils", "channelIdTochannelType channelType : " + channelType);
        return channelType;
    }

    public static boolean isFeeLive(HomeMetaData block) {
        String liveid = block.liveid;
        String zhiboid = block.id;
        if (TextUtils.isEmpty(liveid) || liveid.length() < 2 || TextUtils.isEmpty(zhiboid)) {
            return false;
        }
        return true;
    }

    public static void gotoActivity(Context context, HomeMetaData block, int index, int from) {
        VideoType videoType = VideoType.Normal;
        if (block != null) {
            if (block.isPanorama()) {
                videoType = VideoType.Panorama;
            }
            gotoActivity(context, block, from, videoType, true);
        }
    }

    static void handleFocusLiveLaunch(Context context, String tvCode, String liveUrl, String zhiboId, String name, String liveid, boolean pay, String partId, boolean isPanorama) {
        if (TextUtils.isEmpty(zhiboId) || isPanorama) {
        }
        if (TextUtils.isEmpty(liveid) || liveid.length() <= 1) {
            if (pay && PreferencesManager.getInstance().isTestApi()) {
                UIsUtils.showToast("test msg : liveid is null！");
            }
            if (!TextUtils.isEmpty(zhiboId)) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new BasePlayActivityConfig(context).create(zhiboId)));
                return;
            } else if (!TextUtils.isEmpty(liveUrl)) {
                LiveRoomConfig.launchLives(context, null, tvCode, liveUrl, true, 1, name);
                return;
            } else {
                return;
            }
        }
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new BasePlayActivityConfig(context).create(0, channelIdTochannelType(liveid.substring(0, 2)), tvCode, liveUrl, pay, zhiboId, 0, partId)));
    }

    public static void gotoActivity(Context context, HomeMetaData block) {
        gotoActivity(context, block, -1, -1);
    }

    public static void gotoActivity(Context context, HomeMetaData block, VideoType type, String pageId) {
        mPageId = pageId;
        gotoActivity(context, block, 1, type, false);
    }

    public static void gotoActivity(Context context, HomeMetaData block, VideoType type) {
        gotoActivity(context, block, 1, type, false);
    }

    private static void gotoActivity(Context context, HomeMetaData block, int from, VideoType type, boolean isHome) {
        if (block != null) {
            if (!(NetworkUtils.isNetworkAvailable() || block.at == 12)) {
                ToastUtils.showToast(context, R.string.load_data_no_net);
            }
            boolean noCopyright = block.noCopyright;
            String externalUrl = block.externalUrl;
            if (TextUtils.equals(block.isAlbum, "1")) {
                block.vid = 0;
            }
            LeResponseMessage response;
            switch (block.at) {
                case 1:
                    if (block.type == 4) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).createTopic(BaseTypeUtils.stol(block.zid), from, noCopyright, externalUrl)));
                        return;
                    } else {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).create((long) block.pid, (long) block.vid, type, 1, noCopyright, externalUrl)));
                        return;
                    }
                case 2:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).create(0, (long) block.vid, type, from, noCopyright, externalUrl)));
                    return;
                case 3:
                    if (PAGE_ID_4D.equals(mPageId) || "1003041295".equals(mPageId)) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(context).create4D(block.padPic, block.streamUrl, 2)));
                        mPageId = "";
                        return;
                    }
                    handleFocusLiveLaunch(context, block.streamCode, block.streamUrl, block.id, block.nameCn, block.liveid, "1".equals(String.valueOf(block.pay)), block.partId, type == VideoType.Panorama);
                    return;
                case 4:
                    if (!LetvUtils.isGooglePlay()) {
                        String webUrl = block.webUrl;
                        if (webUrl != null) {
                            gotoWeb(context, LetvUtils.checkUrl(webUrl));
                            return;
                        }
                        return;
                    }
                    return;
                case 5:
                    String webViewUrl = block.webViewUrl;
                    if (webViewUrl != null) {
                        new LetvWebViewActivityConfig(context).launch(LetvUtils.checkUrl(webViewUrl), block.nameCn, 1);
                        return;
                    }
                    return;
                case 6:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new RecommendFragmentActivityConfig(context).create()));
                    return;
                case 7:
                    if (from == 26) {
                        response = LeMessageManager.getInstance().dispatchMessage(context, new LeMessage(LeMessageIds.MSG_MAIN_CHECK_CONTEXT));
                        if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
                            gotoChannelByCid(context, String.valueOf(block.cid), block.pageid, block.nameCn);
                            return;
                        }
                        response = LeMessageManager.getInstance().dispatchMessage(context, new LeMessage(1001));
                        if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
                            goChannelDetailActivity(context, block);
                            return;
                        }
                        return;
                    }
                    response = LeMessageManager.getInstance().dispatchMessage(context, new LeMessage(LeMessageIds.MSG_MAIN_CHECK_CONTEXT));
                    if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
                        if (!isHome) {
                            from = 6;
                        } else if (2 == from) {
                            from = 1;
                        }
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(202, new GoToChannelByCid(String.valueOf(block.cid), block.pageid, from, block.nameCn)));
                        return;
                    }
                    return;
                case 11:
                    if (isHome) {
                        if (2 == from) {
                            from = 1;
                        }
                        String CHANNEL_MEMBER_PAGE_ID = "1002697479";
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(201, new GoToChannelByCid(String.valueOf(1000), "1002697479", from, "")));
                        return;
                    }
                    response = LeMessageManager.getInstance().dispatchMessage(context, new LeMessage(LeMessageIds.MSG_MAIN_CHECK_CONTEXT));
                    if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(201, new GoToChannelByCid(String.valueOf(1000), block.id, 6, "")));
                        return;
                    }
                    return;
                case 12:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvVipActivityConfig(context).create(context.getString(R.string.pim_vip_good_title))));
                    return;
                case 13:
                    if (!PreferencesManager.getInstance().isLogin()) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(context).create()));
                        StatisticsUtils.staticticsInfoPost(context, "71", null, 1, -1, null, null, null, null, null);
                        return;
                    } else if (!PreferencesManager.getInstance().getUserOrderWo()) {
                        return;
                    } else {
                        if (NetworkUtils.getNetworkType() == 0) {
                            ToastUtils.showToast(context, R.string.wo_flow_flow_no_net_toast);
                            return;
                        } else {
                            ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).showSMSVerificationDialog(context, new 1(context));
                            return;
                        }
                    }
                case 14:
                    if (!NetworkUtils.isNetworkAvailable()) {
                        UIsUtils.showToast(R.string.net_error);
                        return;
                    } else if (PreferencesManager.getInstance().isLogin()) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new PointsActivtiyConfig(context).create(context.getString(R.string.pim_vip_good_title), mUser)));
                        return;
                    } else {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(context).createForWhat(3)));
                        StatisticsUtils.staticticsInfoPost(context, "71", null, 1, -1, null, null, null, null, null);
                        return;
                    }
                case 19:
                    if (PreferencesManager.getInstance().isLogin()) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new SettingsMainActivityConfig(context)));
                        return;
                    } else {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(context).create(0)));
                        return;
                    }
                case 20:
                    jumpToLive(context, PlayConstant.CHANNEL_TYPE_VALUE_REMEN);
                    return;
                case 21:
                    jumpToLive(context, "sports");
                    return;
                case 22:
                    jumpToLive(context, "music");
                    return;
                case 23:
                    jumpToLive(context, "ent");
                    return;
                case 25:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvHotActivityConfig(context).create(block.pageid, block.vid)));
                    return;
                case 26:
                    if (!LetvUtils.isGooglePlay()) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvTopicActivityConfig(context)));
                        return;
                    }
                    return;
                case 28:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new StarActivityConfig(context).create(block.leId, block.nameCn, PageIdConstant.index)));
                    return;
                case 29:
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new StarRankActivityConfig(context)));
                    return;
                case 30:
                    jumpToLive(context, "brand");
                    return;
                case 31:
                    jumpToLive(context, "game");
                    return;
                case 32:
                    jumpToLive(context, "information");
                    return;
                case 33:
                    jumpToLive(context, "finance");
                    return;
                case 34:
                    JarLaunchUtils.launchSportGameDefault(context);
                    return;
                case 35:
                    LogInfo.log("zhaoxiang", "-------LEMALL_SDK");
                    try {
                        LemallPlatform.getInstance().openSdkPage(Constants.PAGE_FLAG_RECOMMENDED, PreferencesManager.getInstance().getSso_tk());
                        return;
                    } catch (Exception e) {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    private static void jumpToLive(Context context, String value) {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(context, new LeMessage(LeMessageIds.MSG_MAIN_CHECK_CONTEXT));
        if (LeResponseMessage.checkResponseMessageValidity(response, Boolean.class) && ((Boolean) response.getData()).booleanValue()) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_MAIN_GO_TO_LIVE_ROOM, value));
        } else {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(context).create(FragmentConstant.TAG_FRAGMENT_LIVE, value)));
        }
    }

    public static Channel getVipChannel(Context context) {
        if (context == null) {
            return null;
        }
        Channel channel = new Channel();
        channel.name = context.getResources().getString(R.string.vip_tag);
        channel.type = 1;
        channel.id = 1000;
        if (LetvUtils.isInHongKong()) {
            channel.pageid = AlbumInfo.Channel.VIP_PAGEID_HONGKONG;
            return channel;
        } else if (PreferencesManager.getInstance().isTestApi()) {
            channel.pageid = AlbumInfo.Channel.VIP_PAGEID_TEST;
            return channel;
        } else {
            channel.pageid = AlbumInfo.Channel.VIP_PAGEID;
            return channel;
        }
    }

    public static void gotoChannelDetailItemActivity(Context mContext, Channel mChannel, boolean isFilter, ChannelNavigation mChannelNavigation, ArrayList<SiftKVP> siftKVPTmps, String title) {
        if (mContext != null && mChannel != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new ChannelDetailItemActivityConfig(mContext).create(mChannel, isFilter, mChannelNavigation, siftKVPTmps, title)));
        }
    }

    private static void goChannelDetailActivity(Context context, HomeMetaData block) {
        if (block != null && context != null) {
            Channel channel = new Channel();
            channel.id = block.cid;
            channel.name = block.nameCn;
            channel.pageid = block.pageid;
            channel.type = block.type;
            ChannelNavigation mChannelNavigation = new ChannelNavigation();
            mChannelNavigation.pageid = block.pageid;
            mChannelNavigation.nameCn = block.nameCn;
            LeMessageManager.getInstance().dispatchMessage(context, new LeMessage(1000, new ChannelTabData(channel, mChannelNavigation)));
        }
    }

    public static boolean gotoChannel(Context context, String cid, RedirectData redirect, String bolockName, int type) {
        if (TextUtils.isEmpty(cid) || redirect == null || context == null) {
            return false;
        }
        Channel channel = getChannelJumpToPage(cid);
        ArrayList<SiftKVP> list = null;
        if (type != 2) {
            list = getSiftList(redirect.redField);
        }
        if (channel != null && channel.top == 0 && type == 2) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_MAIN_UPDATA_INDICATOR, cid));
            return true;
        } else if (channel == null || BaseTypeUtils.isListEmpty(list)) {
            jumpDetailChannelActivity(context, cid, redirect.redirectPageId, list, bolockName);
            return true;
        } else {
            gotoChannelDetailItemActivity(context, channel, true, null, list, channel.name);
            return true;
        }
    }

    public static boolean gotoChannelByCid(Context context, String cid, String pageid, String blockName) {
        if (TextUtils.isEmpty(cid)) {
            return false;
        }
        if ((TextUtils.isEmpty(pageid) && TextUtils.equals(cid, "1001")) || BaseTypeUtils.stoi(cid) <= 0) {
            return false;
        }
        String name = "";
        Channel channel = getChannelJumpToPage(cid);
        if (channel == null || channel.top != 0) {
            name = blockName;
        } else if (TextUtils.equals(channel.pageid, pageid)) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_MAIN_UPDATA_INDICATOR, cid));
            return true;
        } else {
            name = TextUtils.isEmpty(blockName) ? channel.name : blockName;
        }
        jumpDetailChannelActivity(context, cid, pageid, null, name);
        return true;
    }

    public static void jumpDetailChannelActivity(Context context, String cid, String pageId, ArrayList<SiftKVP> siftKVPTmps, String bolockName) {
        ChannelNavigation navigation = new ChannelNavigation();
        Channel channel = getChannelJumpToPage(cid);
        if (channel == null) {
            String string;
            channel = new Channel();
            channel.id = BaseTypeUtils.stoi(cid);
            if (TextUtils.isEmpty(bolockName)) {
                string = context.getString(R.string.channel_name_other);
            } else {
                string = bolockName;
            }
            channel.name = string;
            channel.pageid = pageId;
        }
        if (TextUtils.equals(channel.pageid, pageId)) {
            navigation.pageid = channel.pageid;
            navigation.nameCn = channel.name;
        } else {
            navigation.pageid = pageId;
            navigation.nameCn = bolockName;
        }
        gotoChannelDetailItemActivity(context, channel, siftKVPTmps != null, navigation, siftKVPTmps, channel.name);
    }

    private static Channel getChannelJumpToPage(String cid) {
        ChannelListBean channelNavigation = BaseApplication.getInstance().getChannelList();
        if (channelNavigation == null || !BaseTypeUtils.isMapContainsKey(channelNavigation.getChannelMap(), cid)) {
            return null;
        }
        return (Channel) channelNavigation.getChannelMap().get(cid);
    }

    private static Channel getChannelJumpToHome(String cid) {
        ChannelListBean channelNavigation = BaseApplication.getInstance().getChannelList();
        if (channelNavigation == null || !BaseTypeUtils.isMapContainsKey(channelNavigation.getChannelMap(), cid)) {
            return null;
        }
        return (Channel) channelNavigation.getChannelMap().get(cid);
    }

    private static Channel getChannelJumpToMore(String cid) {
        ChannelListBean channelMore = BaseApplication.getInstance().getChannelMoreList();
        if (channelMore == null || !BaseTypeUtils.isMapContainsKey(channelMore.getChannelMap(), cid)) {
            return null;
        }
        return (Channel) channelMore.getChannelMap().get(cid);
    }

    private static ArrayList<SiftKVP> getSiftList(HashMap<String, String> siftKVPMap) {
        if (siftKVPMap == null) {
            return null;
        }
        ArrayList<SiftKVP> siftKVPs = new ArrayList();
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : siftKVPMap.entrySet()) {
            SiftKVP siftKVP = new SiftKVP();
            if (TextUtils.equals("pt", (CharSequence) entry.getKey()) && TextUtils.equals(LetvConstant.MOBILE_SYSTEM_PHONE_PAY, (CharSequence) entry.getValue())) {
                sb.append("ispay/1");
            } else {
                sb.append((String) entry.getKey()).append("/");
                sb.append((String) entry.getValue());
            }
            siftKVP.filterKey = sb.toString();
            siftKVPs.add(siftKVP);
        }
        return siftKVPs;
    }
}
