package com.letv.android.client.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.letv.component.core.http.LetvHttpLog;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvHttpApiConfig;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.UserCenterApi;
import com.letv.core.bean.Share;
import com.letv.core.bean.ShareDialogCustomBean;
import com.letv.core.bean.ShareDialogCustomBean.VideoCid;
import com.letv.core.bean.ShareDialogCustomBean.VideoPid;
import com.letv.core.bean.ShareDialogCustomBean.VideoVid;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.ShareConstant;
import com.letv.core.parser.CommonParser;
import com.letv.core.parser.ShareLinkParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ShareUtils {
    public static final String APP_KEY = "3830215581";
    public static final String REDIRECT_URL = "http://m.letv.com";
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";
    private static String mShareLink = "";

    public ShareUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static String getQQLoginUrl() {
        return "http://dynamic.app.m.letv.com/android/dynamic.php?mod=passport&ctl=index&act=appqq&pcode=" + LetvHttpApiConfig.PCODE + "&version=" + LetvHttpApiConfig.VERSION;
    }

    public static String getSinaLoginUrl() {
        return "http://dynamic.app.m.letv.com/android/dynamic.php?mod=passport&ctl=index&act=appsina&pcode=" + LetvHttpApiConfig.PCODE + "&version=" + LetvHttpApiConfig.VERSION;
    }

    public static String getString(int id) {
        return BaseApplication.getInstance().getString(id);
    }

    public static String getString(int id, Object... objects) {
        return BaseApplication.getInstance().getString(id, objects);
    }

    public static boolean checkBrowser(Context context, String packageName) {
        boolean isInstalled;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, 8192);
            isInstalled = appInfo != null && appInfo.enabled;
        } catch (NameNotFoundException e) {
            isInstalled = false;
        }
        LogInfo.log("ZSM", "weixin   " + isInstalled);
        return isInstalled;
    }

    public static boolean checkPackageInstalled(Context context, String packageName) {
        for (ApplicationInfo info : context.getPackageManager().getInstalledApplications(1)) {
            if (info.packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpecificActivityExsit(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        PackageManager pm = context.getPackageManager();
        if (pm.queryIntentActivities(intent, 0).size() > 0) {
            LogInfo.log("ZSM", "weixin " + pm + ": is exsit");
            return true;
        }
        LogInfo.log("ZSM", "weixin " + pm + ": is not exsit");
        return false;
    }

    public static String getShareHint(String name, int type, int id, int order, int vid, long pid, long cid, int videoType, int channel, int station) {
        int universalShareRes;
        Object[] objArr;
        LogInfo.log("fornia", "name =" + name + "  type =" + type + "  id=" + id + "  order =" + order + "  vid=" + vid + "  pid =" + pid + "  cid=" + cid);
        Share share = LetvShareControl.getInstance().getShare();
        String orderStr = LetvNumberFormat.format(order + "");
        String replace_url = "";
        LetvHttpLog.Err("type =" + type + "  id =" + id + "  vid=" + vid);
        String serverHint = "";
        TipBean dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_100105);
        LogInfo.log("fornia", "dialogMsgByMsg:" + dialogMsgByMsg + "share:" + share);
        if (share != null) {
            if (type == 1) {
                replace_url = share.video_url.replace("{vid}", vid + "").replace("{index}", "1").replace("{aid}", id + "");
            } else if (type == 2) {
                replace_url = share.video_url.replace("{aid}", id + "").replace("{index}", "1").replace("{vid}", vid + "");
            }
            if (dialogMsgByMsg == null) {
                universalShareRes = getUniversalShareRes(videoType);
                objArr = new Object[2];
                if (TextUtils.isEmpty(name)) {
                    name = "";
                }
                objArr[0] = name;
                objArr[1] = addAnalysisParam(replace_url, channel, station);
                return getString(universalShareRes, objArr);
            }
            serverHint = dialogMsgByMsg.message;
            LogInfo.log("fornia", "dialogMsgByMsg 000serverHint:" + serverHint);
            if (!TextUtils.isEmpty(serverHint)) {
                serverHint = serverHint.replaceAll(ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER, "\"");
            }
            LogInfo.log("fornia", "dialogMsgByMsg 222serverHint:" + serverHint);
            ShareDialogCustomBean shareDialogCustomBean = (ShareDialogCustomBean) CommonParser.getJsonObj(ShareDialogCustomBean.class, serverHint);
            LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean:" + shareDialogCustomBean);
            String str;
            String str2;
            String str3;
            String str4;
            if (shareDialogCustomBean != null) {
                str = pid + "";
                str2 = vid + "";
                str3 = cid + "";
                if (TextUtils.isEmpty(name)) {
                    str4 = "";
                } else {
                    str4 = name;
                }
                serverHint = getCustomMsg(shareDialogCustomBean, str, str2, str3, str4, addAnalysisParam(replace_url, channel, station));
                LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean serverHint:" + serverHint);
                if (!TextUtils.isEmpty(serverHint)) {
                    return serverHint;
                }
                universalShareRes = getUniversalShareRes(videoType);
                objArr = new Object[2];
                if (TextUtils.isEmpty(name)) {
                    name = "";
                }
                objArr[0] = name;
                objArr[1] = addAnalysisParam(replace_url, channel, station);
                return getString(universalShareRes, objArr);
            }
            serverHint = dialogMsgByMsg.message;
            if (!TextUtils.isEmpty(serverHint)) {
                serverHint = serverHint.replaceAll(ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER_OLD, "\"");
            }
            LogInfo.log("fornia", "dialogMsgByMsg 222serverHint:" + serverHint);
            shareDialogCustomBean = (ShareDialogCustomBean) CommonParser.getJsonObj(ShareDialogCustomBean.class, serverHint);
            LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean:" + shareDialogCustomBean);
            if (shareDialogCustomBean != null) {
                str = pid + "";
                str2 = vid + "";
                str3 = cid + "";
                if (TextUtils.isEmpty(name)) {
                    str4 = "";
                } else {
                    str4 = name;
                }
                serverHint = getCustomMsg(shareDialogCustomBean, str, str2, str3, str4, addAnalysisParam(replace_url, channel, station));
                LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean serverHint:" + serverHint);
                if (!TextUtils.isEmpty(serverHint)) {
                    return serverHint;
                }
                universalShareRes = getUniversalShareRes(videoType);
                objArr = new Object[2];
                if (TextUtils.isEmpty(name)) {
                    name = "";
                }
                objArr[0] = name;
                objArr[1] = addAnalysisParam(replace_url, channel, station);
                return getString(universalShareRes, objArr);
            }
        }
        universalShareRes = getUniversalShareRes(videoType);
        objArr = new Object[2];
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        objArr[0] = name;
        objArr[1] = addAnalysisParam(replace_url, channel, station);
        return getString(universalShareRes, objArr);
    }

    public static String getHotShareHint(String name, int type, int id, int order, int vid, long pid, long cid, int videoType, int channel, int station) {
        LogInfo.log("fornia", "name =" + name + "  type =" + type + "  id=" + id + "  order =" + order + "  vid=" + vid + "  pid =" + pid + "  cid=" + cid);
        Share share = LetvShareControl.getInstance().getShare();
        String replace_url = "";
        LetvHttpLog.Err("type =" + type + "  id =" + id + "  vid=" + vid);
        if (share != null) {
            if (type == 1) {
                replace_url = share.video_url.replace("{vid}", vid + "").replace("{index}", "1").replace("{aid}", id + "");
            } else if (type == 2) {
                replace_url = share.video_url.replace("{aid}", id + "").replace("{index}", "1").replace("{vid}", vid + "");
            }
        }
        int universalShareRes = getUniversalShareRes(videoType);
        Object[] objArr = new Object[2];
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        objArr[0] = name;
        objArr[1] = addAnalysisParam(replace_url, channel, station);
        return getString(universalShareRes, objArr);
    }

    private static int getUniversalShareRes(int videoType) {
        switch (videoType) {
            case 0:
                return 2131100847;
            case 1:
                return 2131100845;
            case 2:
                return 2131100844;
            default:
                return 2131100844;
        }
    }

    public static String getVoteShareHint(String name, int type, long aid, int vid, String role) {
        Share share = LetvShareControl.getInstance().getShare();
        String replace_url = "";
        TipBean dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_211015);
        LogInfo.log("fornia", "dialogMsgByMsg:" + dialogMsgByMsg);
        if (share != null) {
            if (type == 1) {
                replace_url = share.video_url.replace("{vid}", vid + "").replace("{index}", "1").replace("{aid}", aid + "");
            } else if (type == 2) {
                replace_url = share.video_url.replace("{aid}", aid + "").replace("{index}", "1").replace("{vid}", vid + "");
            }
        }
        if (dialogMsgByMsg == null || TextUtils.isEmpty(dialogMsgByMsg.message)) {
            Object[] objArr = new Object[4];
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            objArr[0] = name;
            if (TextUtils.isEmpty(role)) {
                role = "";
            }
            objArr[1] = role;
            objArr[2] = BaseApplication.getInstance().getString(2131099927);
            if (TextUtils.isEmpty(replace_url)) {
                replace_url = "";
            }
            objArr[3] = replace_url;
            return getString(2131100829, objArr);
        }
        LogInfo.log("fornia", "dialogMsgByMsg.message:" + dialogMsgByMsg.message);
        objArr = new Object[4];
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        objArr[0] = name;
        if (TextUtils.isEmpty(role)) {
            role = "";
        }
        objArr[1] = role;
        objArr[2] = dialogMsgByMsg.message;
        if (TextUtils.isEmpty(replace_url)) {
            replace_url = "";
        }
        objArr[3] = replace_url;
        return getString(2131100829, objArr);
    }

    public static String getLiveVoteShareHint(String name, String url, String role) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_211015);
        LogInfo.log("fornia", "dialogMsgByMsg:" + dialogMsgByMsg);
        if (dialogMsgByMsg == null || TextUtils.isEmpty(dialogMsgByMsg.message)) {
            Object[] objArr = new Object[4];
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            objArr[0] = name;
            if (TextUtils.isEmpty(role)) {
                role = "";
            }
            objArr[1] = role;
            objArr[2] = BaseApplication.getInstance().getString(2131099927);
            if (TextUtils.isEmpty(url)) {
                url = "";
            }
            objArr[3] = url;
            return getString(2131100829, objArr);
        }
        LogInfo.log("fornia", "dialogMsgByMsg.message:" + dialogMsgByMsg.message);
        objArr = new Object[4];
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        objArr[0] = name;
        if (TextUtils.isEmpty(role)) {
            role = "";
        }
        objArr[1] = role;
        objArr[2] = dialogMsgByMsg.message;
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        objArr[3] = url;
        return getString(2131100829, objArr);
    }

    public static String getVideoShareHint(String name, int type, int id, int order, int vid, long pid, long cid, String time, String voice, int shareType, int channel, int station) {
        Share share = LetvShareControl.getInstance().getShare();
        String orderStr = LetvNumberFormat.format(order + "");
        String replace_url = "";
        LogInfo.log("fornia", "type =" + type + "  id =" + id + "  vid=" + vid + "  pid =" + pid + "  cid=" + cid + "voice:" + voice + "sharetype:" + shareType);
        String serverHint = "";
        TipBean dialogMsgByMsg = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_211012);
        TipBean dialogMsgByMsgCustom = TipUtils.getTipBean(DialogMsgConstantId.CONSTANT_200035);
        LogInfo.log("fornia", "dialogMsgByMsg:" + dialogMsgByMsg);
        LogInfo.log("fornia", "dialogMsgByMsgCustom:" + dialogMsgByMsgCustom);
        if (share != null) {
            if (type == 1) {
                replace_url = share.video_url.replace("{vid}", vid + "").replace("{index}", "1").replace("{aid}", id + "");
            } else if (type == 2) {
                replace_url = share.video_url.replace("{aid}", id + "").replace("{index}", "1").replace("{vid}", vid + "");
            }
        }
        Object[] objArr;
        if (dialogMsgByMsgCustom == null || !(dialogMsgByMsgCustom == null || shareType == 2)) {
            LogInfo.log("fornia", "dialogMsgByMsgCustom:" + dialogMsgByMsgCustom);
            if (dialogMsgByMsg == null || TextUtils.isEmpty(dialogMsgByMsg.message)) {
                objArr = new Object[5];
                if (TextUtils.isEmpty(voice)) {
                    voice = "";
                }
                objArr[0] = voice;
                if (TextUtils.isEmpty(name)) {
                    name = "";
                }
                objArr[1] = name;
                if (TextUtils.isEmpty(time)) {
                    time = "";
                }
                objArr[2] = time;
                objArr[3] = BaseApplication.getInstance().getString(2131099926);
                objArr[4] = addAnalysisParam(replace_url, channel, station);
                return getString(2131100828, objArr);
            }
            objArr = new Object[5];
            if (TextUtils.isEmpty(voice)) {
                voice = "";
            }
            objArr[0] = voice;
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            objArr[1] = name;
            if (TextUtils.isEmpty(time)) {
                time = "";
            }
            objArr[2] = time;
            objArr[3] = dialogMsgByMsg.message;
            objArr[4] = addAnalysisParam(replace_url, channel, station);
            return getString(2131100828, objArr);
        }
        LogInfo.log("fornia", "dialogMsgByMsgCustom:" + dialogMsgByMsgCustom);
        serverHint = dialogMsgByMsgCustom.message;
        LogInfo.log("fornia", "dialogMsgByMsg 000serverHint:" + serverHint);
        if (!TextUtils.isEmpty(serverHint)) {
            serverHint = serverHint.replaceAll(ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER, "\"");
        }
        LogInfo.log("fornia", "dialogMsgByMsg 222serverHint:" + serverHint);
        ShareDialogCustomBean shareDialogCustomBean = (ShareDialogCustomBean) CommonParser.getJsonObj(ShareDialogCustomBean.class, serverHint);
        LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean:" + shareDialogCustomBean);
        if (shareDialogCustomBean != null) {
            serverHint = getCustomMsg(shareDialogCustomBean, pid + "", vid + "", cid + "", voice, addAnalysisParam(replace_url, channel, station));
            LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean serverHint:" + serverHint);
        } else {
            serverHint = dialogMsgByMsgCustom.message;
            LogInfo.log("fornia", "dialogMsgByMsg 000serverHint:" + serverHint);
            if (!TextUtils.isEmpty(serverHint)) {
                serverHint = serverHint.replaceAll(ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER_OLD, "\"");
            }
            LogInfo.log("fornia", "dialogMsgByMsg 222serverHint:" + serverHint);
            shareDialogCustomBean = (ShareDialogCustomBean) CommonParser.getJsonObj(ShareDialogCustomBean.class, serverHint);
            LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean:" + shareDialogCustomBean);
            if (shareDialogCustomBean != null) {
                serverHint = getCustomMsg(shareDialogCustomBean, pid + "", vid + "", cid + "", voice, addAnalysisParam(replace_url, channel, station));
                LogInfo.log("fornia", "dialogMsgByMsg shareDialogCustomBean serverHint:" + serverHint);
            }
        }
        if (!TextUtils.isEmpty(serverHint)) {
            return serverHint;
        }
        if (dialogMsgByMsg == null || TextUtils.isEmpty(dialogMsgByMsg.message)) {
            objArr = new Object[5];
            if (TextUtils.isEmpty(voice)) {
                voice = "";
            }
            objArr[0] = voice;
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            objArr[1] = name;
            if (TextUtils.isEmpty(time)) {
                time = "";
            }
            objArr[2] = time;
            objArr[3] = BaseApplication.getInstance().getString(2131099926);
            objArr[4] = addAnalysisParam(replace_url, channel, station);
            return getString(2131100828, objArr);
        }
        objArr = new Object[5];
        if (TextUtils.isEmpty(voice)) {
            voice = "";
        }
        objArr[0] = voice;
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        objArr[1] = name;
        if (TextUtils.isEmpty(time)) {
            time = "";
        }
        objArr[2] = time;
        objArr[3] = dialogMsgByMsg.message;
        objArr[4] = addAnalysisParam(replace_url, channel, station);
        return getString(2131100828, objArr);
    }

    private static String getCustomMsg(ShareDialogCustomBean sCustomBean, String pid, String vid, String cid, String name, String url) {
        if (!(sCustomBean.pids == null || sCustomBean.pids.size() <= 0 || TextUtils.isEmpty(pid))) {
            for (VideoPid videoPid : sCustomBean.pids) {
                if (videoPid.pid.equals(pid)) {
                    Object[] objArr = new Object[5];
                    objArr[0] = videoPid.title;
                    objArr[1] = name;
                    objArr[2] = videoPid.subtitle1;
                    objArr[3] = videoPid.subtitle2;
                    if (TextUtils.isEmpty(url)) {
                        url = "";
                    }
                    objArr[4] = url;
                    return getString(2131100846, objArr);
                }
            }
        }
        if (!(sCustomBean.vids == null || sCustomBean.vids.size() <= 0 || TextUtils.isEmpty(vid))) {
            for (VideoVid videoVid : sCustomBean.vids) {
                if (videoVid.vid.equals(vid)) {
                    objArr = new Object[5];
                    objArr[0] = videoVid.title;
                    objArr[1] = name;
                    objArr[2] = videoVid.subtitle1;
                    objArr[3] = videoVid.subtitle2;
                    if (TextUtils.isEmpty(url)) {
                        url = "";
                    }
                    objArr[4] = url;
                    return getString(2131100846, objArr);
                }
            }
        }
        if (!(sCustomBean.cids == null || sCustomBean.cids.size() <= 0 || TextUtils.isEmpty(cid))) {
            for (VideoCid videoCid : sCustomBean.cids) {
                if (videoCid.cid.equals(cid)) {
                    objArr = new Object[5];
                    objArr[0] = videoCid.title;
                    objArr[1] = name;
                    objArr[2] = videoCid.subtitle1;
                    objArr[3] = videoCid.subtitle2;
                    if (TextUtils.isEmpty(url)) {
                        url = "";
                    }
                    objArr[4] = url;
                    return getString(2131100846, objArr);
                }
            }
        }
        return null;
    }

    public static String getShareHitFroLive(String name, String liveUrl) {
        Object[] objArr = new Object[2];
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        objArr[0] = name;
        objArr[1] = liveUrl;
        return getString(2131100844, objArr);
    }

    public static String getAnalysisLiveShareUrl(int liveChannel, String liveType, String liveId, int channel, int station) {
        LogInfo.log("fornia", "getAnalysisLiveShareUrl liveChannel =" + liveChannel + "  liveType =" + liveType + "  liveId=" + liveId + "  channel =" + channel + "  station=" + station);
        if (TextUtils.isEmpty(liveId)) {
            return "";
        }
        String shareUrl = ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_OTHER.replace("{id}", liveId);
        switch (liveChannel) {
            case 1:
                shareUrl = ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_LUNBO.replace("{id}", liveId);
                break;
            case 3:
            case 4:
            case 5:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                shareUrl = ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_NORMAL.replace("{channel}", liveType).replace("{id}", liveId);
                break;
            case 15:
                shareUrl = ShareConstant.SHARE_URL_TYPE_LIVE_CHANNEL_WEISHI.replace("{id}", liveId);
                break;
        }
        return addAnalysisParam(shareUrl, channel, station);
    }

    public static String addAnalysisParam(String url, int channel, int station) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        int sf = 0;
        switch (channel) {
            case 0:
                sf = 0;
                break;
            case 1:
                sf = 1;
                break;
            case 2:
                sf = 4;
                break;
            case 3:
                sf = 3;
                break;
            case 4:
                sf = 2;
                break;
            case 5:
                sf = 5;
                break;
        }
        if (url.contains("?") && url.endsWith("?")) {
            url = url + "sf=" + sf + "&sc=" + 0;
        } else if (url.contains("?")) {
            url = url + "&sf=" + sf + "&sc=" + 0;
        } else {
            url = url + "?sf=" + sf + "&sc=" + 0;
        }
        LogInfo.log("fornia", "看这个分享地址××××××url ===" + url);
        return url;
    }

    public static String getSharePlayUrl(int type, int id, int order, int vid) {
        Share share = LetvShareControl.getInstance().getShare();
        String orderStr = LetvNumberFormat.format(order + "");
        LogInfo.log("getSharePlayUrl", "type ===" + type + "  id =" + id + "  vid=" + vid);
        LetvHttpLog.Err("type =" + type + "  id =" + id + "  vid=" + vid);
        if (share != null) {
            return share.video_url.replace("{vid}", vid + "").replace("{index}", "1").replace("{aid}", id + "");
        }
        return null;
    }

    public static int getShareVideoType(int cid, String tag) {
        int shareVideoType = 2;
        if (!TextUtils.isEmpty(tag) && tag.equals("1")) {
            return 1;
        }
        if (cid == 1 || cid == 2) {
            shareVideoType = 0;
        }
        return shareVideoType;
    }

    public static String getLinkcardTitleText(int type, int shareType, boolean isFeature, long count, String pidTitle, String order, String vidTitle) {
        Object[] objArr;
        LogInfo.log("fornia", "6.4文案调整 type:" + type + "shareType:" + shareType + "count:" + count + "pidTitle:" + pidTitle + "order:" + order + "vidTitle:" + vidTitle);
        String title = "";
        if (type == 9) {
            if (shareType == 0 || shareType == 6) {
                if (count >= 10000) {
                    objArr = new Object[4];
                    objArr[0] = StringUtils.getPlayCountsToStr(count);
                    if (TextUtils.isEmpty(pidTitle)) {
                        pidTitle = "";
                    }
                    objArr[1] = pidTitle;
                    objArr[2] = "";
                    objArr[3] = "";
                    return getString(2131100826, objArr);
                }
                objArr = new Object[3];
                if (TextUtils.isEmpty(pidTitle)) {
                    pidTitle = "";
                }
                objArr[0] = pidTitle;
                objArr[1] = "";
                objArr[2] = "";
                return getString(2131100825, objArr);
            } else if (shareType == 1 || shareType == 4 || shareType == 3) {
                if (count >= 10000) {
                    objArr = new Object[3];
                    objArr[0] = StringUtils.getPlayCountsToStr(count);
                    if (TextUtils.isEmpty(pidTitle)) {
                        pidTitle = "";
                    }
                    objArr[1] = pidTitle;
                    objArr[2] = "";
                    return getString(2131100824, objArr);
                }
                objArr = new Object[2];
                if (TextUtils.isEmpty(pidTitle)) {
                    pidTitle = "";
                }
                objArr[0] = pidTitle;
                objArr[1] = "";
                return getString(2131100823, objArr);
            }
        }
        if (!isFeature) {
            Object[] objArr2;
            if (count >= 10000) {
                String str;
                objArr2 = new Object[4];
                objArr2[0] = StringUtils.getPlayCountsToStr(count);
                objArr2[1] = "";
                objArr2[2] = "";
                if (TextUtils.isEmpty(vidTitle)) {
                    str = "";
                } else {
                    str = vidTitle;
                }
                objArr2[3] = str;
                title = getString(2131100826, objArr2);
            } else {
                objArr2 = new Object[3];
                objArr2[0] = "";
                objArr2[1] = "";
                objArr2[2] = TextUtils.isEmpty(vidTitle) ? "" : vidTitle;
                title = getString(2131100825, objArr2);
            }
        }
        if (type == 2 || type == 5 || type == 11) {
            if (shareType == 0 || shareType == 6) {
                if (count >= 10000) {
                    objArr = new Object[4];
                    objArr[0] = StringUtils.getPlayCountsToStr(count);
                    if (TextUtils.isEmpty(pidTitle)) {
                        pidTitle = "";
                    }
                    objArr[1] = pidTitle;
                    objArr[2] = getOrderString(type, order);
                    if (TextUtils.isEmpty(vidTitle)) {
                        vidTitle = "";
                    }
                    objArr[3] = vidTitle;
                    title = getString(2131100826, objArr);
                } else {
                    objArr = new Object[3];
                    if (TextUtils.isEmpty(pidTitle)) {
                        pidTitle = "";
                    }
                    objArr[0] = pidTitle;
                    objArr[1] = getOrderString(type, order);
                    if (TextUtils.isEmpty(vidTitle)) {
                        vidTitle = "";
                    }
                    objArr[2] = vidTitle;
                    title = getString(2131100825, objArr);
                }
            } else if (shareType == 1 || shareType == 4 || shareType == 3) {
                if (count >= 10000) {
                    objArr = new Object[3];
                    objArr[0] = StringUtils.getPlayCountsToStr(count);
                    if (TextUtils.isEmpty(pidTitle)) {
                        pidTitle = "";
                    }
                    objArr[1] = pidTitle;
                    objArr[2] = getOrderString(type, order);
                    title = getString(2131100824, objArr);
                } else {
                    objArr = new Object[2];
                    if (TextUtils.isEmpty(pidTitle)) {
                        pidTitle = "";
                    }
                    objArr[0] = pidTitle;
                    objArr[1] = getOrderString(type, order);
                    title = getString(2131100823, objArr);
                }
            }
        } else if (shareType == 0 || shareType == 6) {
            if (count >= 10000) {
                objArr = new Object[4];
                objArr[0] = StringUtils.getPlayCountsToStr(count);
                if (TextUtils.isEmpty(pidTitle)) {
                    pidTitle = "";
                }
                objArr[1] = pidTitle;
                objArr[2] = "";
                if (TextUtils.isEmpty(vidTitle)) {
                    vidTitle = "";
                }
                objArr[3] = vidTitle;
                title = getString(2131100826, objArr);
            } else {
                objArr = new Object[3];
                if (TextUtils.isEmpty(pidTitle)) {
                    pidTitle = "";
                }
                objArr[0] = pidTitle;
                objArr[1] = "";
                if (TextUtils.isEmpty(vidTitle)) {
                    vidTitle = "";
                }
                objArr[2] = vidTitle;
                title = getString(2131100825, objArr);
            }
        } else if (shareType == 1 || shareType == 4 || shareType == 3) {
            if (count >= 10000) {
                objArr = new Object[3];
                objArr[0] = StringUtils.getPlayCountsToStr(count);
                if (TextUtils.isEmpty(pidTitle)) {
                    pidTitle = "";
                }
                objArr[1] = pidTitle;
                objArr[2] = "";
                title = getString(2131100824, objArr);
            } else {
                objArr = new Object[2];
                if (TextUtils.isEmpty(pidTitle)) {
                    pidTitle = "";
                }
                objArr[0] = pidTitle;
                objArr[1] = "";
                title = getString(2131100823, objArr);
            }
        }
        LogInfo.log("fornia", "6.4文案调整 title:" + title);
        if (TextUtils.isEmpty(title)) {
            return "";
        }
        return title;
    }

    private static String getOrderString(int type, String order) {
        if (TextUtils.isEmpty(order)) {
            return "";
        }
        Object[] objArr;
        if (type == 2 || type == 5) {
            objArr = new Object[1];
            if (TextUtils.isEmpty(order)) {
                order = "";
            }
            objArr[0] = order;
            return getString(2131100821, objArr);
        } else if (type != 11) {
            return "";
        } else {
            objArr = new Object[1];
            if (TextUtils.isEmpty(order)) {
                order = "";
            }
            objArr[0] = order;
            return getString(2131100822, objArr);
        }
    }

    public static void RequestShareLink(Context context) {
        if (TextUtils.isEmpty(mShareLink) || LetvShareControl.getInstance().getShare() == null) {
            mShareLink = UserCenterApi.requestShareLink();
            new LetvRequest(Share.class).setUrl(mShareLink).setParser(new ShareLinkParser()).setCallback(new 1()).add();
        }
    }
}
