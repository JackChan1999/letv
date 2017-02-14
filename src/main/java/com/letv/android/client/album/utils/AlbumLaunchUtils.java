package com.letv.android.client.album.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.letv.android.client.album.R;
import com.letv.android.client.commonlib.config.BasePlayActivityConfig;
import com.letv.android.client.commonlib.config.ConsumeRecordActivityConfig;
import com.letv.android.client.commonlib.config.LetvHotActivityConfig;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.commonlib.config.LetvTopicActivityConfig;
import com.letv.android.client.commonlib.config.LetvVipActivityConfig;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.config.MainActivityConfig;
import com.letv.android.client.commonlib.config.MyCollectActivityConfig;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.commonlib.config.MyPlayRecordActivityConfig;
import com.letv.android.client.commonlib.config.SettingsMainActivityConfig;
import com.letv.core.BaseApplication;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LoginConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.manager.DownloadManager;
import com.letv.http.LetvLogApiTool;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class AlbumLaunchUtils {
    public static String BASE_PLAY_ACTIVITY_ACTION = "android.intent.action.BasePlayActivity";
    public static final String FLOAT_TOPIC_HALF_NINE = "9";
    private static final String LETVCLIENT = "letvclient";
    public static final String LITESCHEME = "liteclient";
    public static String from = "";

    public static void initMParams(Activity activity, Intent intent) {
        if (intent != null && intent.getData() != null) {
            BaseApplication.setAppStartTime(System.currentTimeMillis());
            Uri data = intent.getData();
            LogInfo.log("zhuqiao", "M data" + data);
            String scheme = intent.getScheme();
            if (scheme != null && "letvclient".equalsIgnoreCase(scheme)) {
                DownloadManager.pauseAllDownload();
            }
            if (TextUtils.equals(data.getQueryParameter("version"), "2.0")) {
                mLetvFunctionVersion2(activity, data, intent);
            } else {
                mLetvFunctionVersion1(activity, data, intent);
            }
        }
    }

    public static void initLite(Context context, Intent intent) {
        if (intent != null) {
            String scheme = intent.getScheme();
            Uri data = intent.getData();
            if (scheme != null && LITESCHEME.equalsIgnoreCase(scheme) && data != null) {
                String albumId = data.getQueryParameter("aid");
                String videoId = data.getQueryParameter("vid");
                String packageName = data.getQueryParameter("packageName");
                String jsonData = intent.getStringExtra("jsonData");
                String lMode = data.getQueryParameter("launchMode");
                int aid = 0;
                int vid = 0;
                int launchMode = 0;
                if (!TextUtils.isEmpty(albumId)) {
                    aid = Integer.parseInt(albumId);
                }
                if (!TextUtils.isEmpty(videoId)) {
                    vid = Integer.parseInt(videoId);
                }
                if (!TextUtils.isEmpty(lMode)) {
                    launchMode = Integer.parseInt(lMode);
                }
                intent.putExtra("packageName", packageName);
                if (launchMode == 2) {
                    intent.putExtra("launchMode", launchMode);
                    intent.putExtra("aid", aid);
                    intent.putExtra("vid", vid);
                } else if (launchMode == 30) {
                    intent.putExtra("launchMode", launchMode);
                    intent.putExtra("jsonData", jsonData);
                } else if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (activity != null) {
                        activity.finish();
                    }
                }
            }
        }
    }

    private static void mLetvFunctionVersion1(Activity activity, Uri data, Intent intent) {
        LogInfo.log("zhuqiao", "M站跳转的init:" + data.toString());
        String actionType = data.getQueryParameter("actionType");
        String aid = data.getQueryParameter("aid");
        if (TextUtils.isEmpty(aid)) {
            aid = data.getQueryParameter("pid");
        }
        String vid = data.getQueryParameter("vid");
        String weburl = data.getQueryParameter("weburl");
        String zid = data.getQueryParameter(PlayConstant.ZID);
        String mtype = data.getQueryParameter(PlayConstant.BACK);
        String cidString = data.getQueryParameter("cid");
        String cPageIdString = data.getQueryParameter(PlayConstant.PAGE_ID);
        String cTypeString = data.getQueryParameter("ctype");
        String cName = data.getQueryParameter("cname");
        String livehalf = data.getQueryParameter("ishalf");
        String liveType = data.getQueryParameter("livetype");
        String liveIsPay = data.getQueryParameter(PlayConstant.IS_PAY);
        String liveId = data.getQueryParameter("liveid");
        String localUrl = data.getQueryParameter("localurl");
        boolean isBack2Source = true;
        if (mtype != null) {
            try {
                isBack2Source = BaseTypeUtils.stoi(mtype, 1) == 1;
            } catch (Exception e) {
            }
        }
        LogInfo.log("zhuqiao", "isBack2Source:" + isBack2Source);
        setStatistisInfo(data);
        int type = BaseTypeUtils.stoi(actionType, -1);
        long albumId = BaseTypeUtils.stol(aid);
        long videoId = BaseTypeUtils.stol(vid);
        long zhuantiId = BaseTypeUtils.stol(zid);
        long cid = BaseTypeUtils.stol(cidString);
        int cPageId = BaseTypeUtils.stoi(cPageIdString);
        int cType = BaseTypeUtils.stoi(cTypeString);
        boolean isHalf = BaseTypeUtils.stoi(livehalf) == 1;
        boolean isPay = BaseTypeUtils.stoi(liveIsPay) == 1;
        boolean isPanorama = TextUtils.equals(data.getQueryParameter("isfullscene"), "true");
        StatisticsUtils.mClickImageForPlayTime = System.currentTimeMillis();
        intent.putExtra("from", 20);
        intent.putExtra(PlayConstant.BACK, isBack2Source);
        if (type == 0) {
            if (zhuantiId > 0) {
                intent.putExtra("launchMode", 6);
                intent.putExtra(PlayConstant.ZID, zhuantiId);
                if (videoId > 0) {
                    intent.putExtra("vid", videoId);
                }
            } else if (albumId > 0 || videoId > 0) {
                intent.putExtra("launchMode", 3);
                intent.putExtra("aid", albumId);
                intent.putExtra("vid", videoId);
            }
            intent.putExtra(PlayConstant.FORCE_FULL, true);
            if (isPanorama) {
                intent.putExtra(PlayConstant.VIDEO_TYPE, VideoType.Panorama);
            }
        } else if (type == 1) {
            if (PreferencesManager.getInstance().isLogin()) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvVipActivityConfig(activity).createForResult(activity.getString(R.string.pim_vip_recharge))));
            } else {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(activity).createFromHome()));
            }
            activity.finish();
        } else if (type != 2) {
            if (type == 3) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new BasePlayActivityConfig(activity).createFromThird(liveType, isPay, liveId, isHalf)));
                activity.finish();
            } else if (type == 4) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvWebViewActivityConfig(activity).create(weburl, -1)));
                activity.finish();
            } else if (type == 5) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_FIND)));
                activity.finish();
            } else if (type == 6) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForChannel((int) cid, cPageId, cName, cType)));
                activity.finish();
            } else if (type == 7) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_MINE)));
                activity.finish();
            } else if (type == 8) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvHotActivityConfig(activity)));
                activity.finish();
            } else if (type == 9) {
                if (zhuantiId > 0) {
                    intent.putExtra("launchMode", 6);
                    intent.putExtra(PlayConstant.ZID, zhuantiId);
                    if (videoId > 0) {
                        intent.putExtra("vid", videoId);
                    }
                } else if (albumId > 0 || videoId > 0) {
                    intent.putExtra("launchMode", 2);
                    intent.putExtra("aid", albumId);
                    intent.putExtra("vid", videoId);
                }
                if (isPanorama) {
                    intent.putExtra(PlayConstant.VIDEO_TYPE, VideoType.Panorama);
                }
            } else if (type == 10) {
                if (!TextUtils.isEmpty(localUrl)) {
                    intent.putExtra("launchMode", 1);
                    intent.putExtra(PlayConstant.URI, localUrl);
                    intent.putExtra(PlayConstant.PLAY_MODE, 1);
                    intent.putExtra(PlayConstant.PAGE_ID, PageIdConstant.localPage);
                }
            } else if (type == 11) {
                jump2ConsumeRecord(activity);
            } else if (type == 1001) {
                jump2Donwloaded(activity);
            } else if (type == 12) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_HOME)));
                activity.finish();
            } else if (type == 13) {
                jump2PlayRecord(activity);
            } else if (type == 14) {
                jump2PlayFavorite(activity);
            } else {
                if (!data.toString().contains("letvclient://msiteAction")) {
                    if (!data.toString().contains(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT) && !data.toString().contains("media")) {
                        if (data.toString().contains(IDataSource.SCHEME_FILE_TAG)) {
                            return;
                        }
                    }
                    return;
                }
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_HOME)));
                activity.finish();
                return;
            }
        }
        intent.setData(null);
        LetvLogApiTool.createPlayLogInfo("VideoClickPlayStart", vid + " aid =" + aid, NetworkUtils.DELIMITER_LINE);
    }

    private static void mLetvFunctionVersion2(Activity activity, Uri data, Intent intent) {
        LogInfo.log("zhuqiao", "M站跳转的init:" + data.toString());
        String actionType = data.getQueryParameter("actionType");
        String aid = data.getQueryParameter("pid");
        String vid = data.getQueryParameter("vid");
        String weburl = data.getQueryParameter("weburl");
        String zid = data.getQueryParameter(PlayConstant.ZID);
        String mtype = data.getQueryParameter(PlayConstant.BACK);
        String livehalf = data.getQueryParameter("ishalf");
        String liveType = data.getQueryParameter("livetype");
        String liveIsPay = data.getQueryParameter(PlayConstant.IS_PAY);
        String liveId = data.getQueryParameter("liveid");
        String localurl = BaseTypeUtils.ensureStringValidate(data.getQueryParameter("localurl"));
        if (TextUtils.isEmpty(liveId)) {
            liveId = data.getQueryParameter("streamid");
        }
        setStatistisInfo(data);
        boolean isBack2Source = BaseTypeUtils.stoi(mtype, 1) == 1;
        LogInfo.log("zhuqiao", "isBack2Source:" + isBack2Source);
        int type = BaseTypeUtils.stoi(actionType, -1);
        long albumId = (long) BaseTypeUtils.stoi(aid);
        long videoId = (long) BaseTypeUtils.stoi(vid);
        long zhuantiId = (long) BaseTypeUtils.stoi(zid);
        boolean isHalf = BaseTypeUtils.stoi(livehalf) == 1;
        boolean isPay = BaseTypeUtils.stoi(liveIsPay) == 1;
        boolean isPanorama = TextUtils.equals(data.getQueryParameter("isfullscene"), "true");
        intent.putExtra("from", 20);
        intent.putExtra(PlayConstant.BACK, isBack2Source);
        if (type == 0) {
            if (zhuantiId > 0) {
                intent.putExtra("launchMode", 6);
                intent.putExtra(PlayConstant.ZID, zhuantiId);
                if (videoId > 0) {
                    intent.putExtra("vid", videoId);
                }
            } else if (albumId > 0 || videoId > 0) {
                intent.putExtra("launchMode", 3);
                intent.putExtra("aid", albumId);
                intent.putExtra("vid", videoId);
            }
            intent.putExtra(PlayConstant.FORCE_FULL, true);
            if (isPanorama) {
                intent.putExtra(PlayConstant.VIDEO_TYPE, VideoType.Panorama);
            }
        } else if (type == 3) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new BasePlayActivityConfig(activity).createFromThird(liveType, isPay, liveId, isHalf)));
            activity.finish();
            activity.finish();
        } else if (type == 4) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvWebViewActivityConfig(activity).create(weburl, -1)));
            activity.finish();
        } else if (type == 10 || type == 9) {
            if (zhuantiId > 0) {
                intent.putExtra("launchMode", 6);
                intent.putExtra(PlayConstant.ZID, zhuantiId);
                if (videoId > 0) {
                    intent.putExtra("vid", videoId);
                }
            } else if (albumId > 0 || videoId > 0) {
                intent.putExtra("launchMode", 2);
                intent.putExtra("aid", albumId);
                intent.putExtra("vid", videoId);
            }
            if (isPanorama) {
                intent.putExtra(PlayConstant.VIDEO_TYPE, VideoType.Panorama);
            }
        } else if (type == 16) {
            if (localurl.equalsIgnoreCase("Home")) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_HOME)));
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Topics")) {
                jump2TopicActivity(activity);
            } else if (localurl.equalsIgnoreCase("Hot")) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvHotActivityConfig(activity)));
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Me_Login")) {
                if (PreferencesManager.getInstance().isLogin()) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_MINE)));
                } else {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvLoginActivityConfig(activity).create(3, 10000)));
                }
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Me_Record")) {
                jump2PlayRecord(activity);
            } else if (localurl.equalsIgnoreCase("Me_Download")) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(activity).create(0)));
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Me_Points")) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvWebViewActivityConfig(activity).create(LetvTools.getTextFromServer("90043", LoginConstant.LETV_MALL_JIFEN_WEBSITE), -1)));
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Me_Setting")) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new SettingsMainActivityConfig(activity)));
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Me_Checkout")) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvVipActivityConfig(activity).create("")));
                activity.finish();
            } else if (localurl.equalsIgnoreCase("Me_Favorites")) {
                jump2PlayFavorite(activity);
            }
        } else if (type == 8) {
            Intent intent1 = new Intent(JarConstant.LEZXING_ACTION_CAPTUREACTIVITY);
            intent1.putExtra("extra.jarname", JarConstant.LETV_ZXING_NAME);
            intent1.putExtra("extra.packagename", JarConstant.LETV_ZXING_PACKAGENAME);
            intent1.putExtra("extra.class", "CaptureActivity");
            intent1.putExtra("top", activity.getString(R.string.more_setting_scan_qr_code));
            intent1.putExtra("bottom", activity.getString(R.string.more_setting_scan_qr_code_bottom));
            activity.startActivity(intent1);
            activity.finish();
        } else {
            if (!data.toString().contains("letvclient://msiteAction")) {
                if (!data.toString().contains(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT) && !data.toString().contains("media")) {
                    if (data.toString().contains(IDataSource.SCHEME_FILE_TAG)) {
                        return;
                    }
                }
                return;
            }
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(activity).createForTab(FragmentConstant.TAG_FRAGMENT_HOME)));
            activity.finish();
            return;
        }
        intent.setData(null);
        com.letv.core.utils.LetvLogApiTool.getInstance().saveExceptionInfo("点播scheme :" + data.toString());
    }

    private static void jump2ConsumeRecord(Activity activity) {
        if (activity != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new ConsumeRecordActivityConfig(activity)));
            activity.finish();
        }
    }

    private static void jump2Donwloaded(Activity activity) {
        if (activity != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(activity).create(true, true, true)));
            activity.finish();
        }
    }

    private static void jump2PlayRecord(Activity activity) {
        if (activity != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyPlayRecordActivityConfig(activity)));
            activity.finish();
        }
    }

    private static void jump2PlayFavorite(Activity activity) {
        if (activity != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyCollectActivityConfig(activity)));
            activity.finish();
        }
    }

    private static void jump2TopicActivity(Activity activity) {
        if (activity != null) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvTopicActivityConfig(activity)));
            activity.finish();
        }
    }

    private static void setStatistisInfo(Uri data) {
        String utype = data.getQueryParameter("utype");
        String from = data.getQueryParameter("from");
        String ref = data.getQueryParameter(PlayConstant.REF);
        if (TextUtils.isEmpty(utype)) {
            utype = NetworkUtils.DELIMITER_LINE;
        }
        if (TextUtils.isEmpty(from)) {
            from = NetworkUtils.DELIMITER_LINE;
        }
        StatisticsUtils.sMStartType = utype;
        StatisticsUtils.sFrom = from;
        if (!TextUtils.isEmpty(ref)) {
            StatisticsUtils.sLoginRef = ref;
        } else if (!TextUtils.equals(from, NetworkUtils.DELIMITER_LINE)) {
            StatisticsUtils.sLoginRef = from;
        }
        StatisticsUtils.mClickImageForPlayTime = System.currentTimeMillis();
        LogInfo.log("jc666", "启动：from=" + from + ",utype=" + utype + ", ref=" + ref);
    }
}
