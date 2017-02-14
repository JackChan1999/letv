package com.letv.android.client.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.view.HalfPlaySharePopwindow;
import com.letv.core.constant.LoginConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.EntryLocation;
import com.letv.redpacketsdk.ui.RedPacketDialog;
import java.util.ArrayList;

public class RedPacketSdkController {
    public RedPacketSdkController() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    private static EntryLocation getLocation() {
        EntryLocation location = new EntryLocation();
        location.contentType = 0;
        location.content = "";
        location.extendRange = "";
        location.extendCid = "";
        location.extendZid = "";
        location.extendPid = "";
        return location;
    }

    public static boolean isAllLocationShow() {
        if (!RedPacketSdkManager.getInstance().hasRedPacket()) {
            return false;
        }
        if ("1".equals(RedPacketSdkManager.getInstance().getEntryLocation().extendRange)) {
            return true;
        }
        return false;
    }

    public static boolean checkRedPackLocation(RedPacketFrom redPackFrom) {
        if (redPackFrom.from == 0) {
            return false;
        }
        if (isAllLocationShow()) {
            return true;
        }
        if (redPackFrom == null || !RedPacketSdkManager.getInstance().hasRedPacket()) {
            return false;
        }
        EntryLocation location = RedPacketSdkManager.getInstance().getEntryLocation();
        LogInfo.log("RedPacket", "RedPacket+位置询问" + redPackFrom.toString());
        LogInfo.log("RedPacket", "RedPacket+红包位置" + location.toString());
        switch (redPackFrom.from) {
            case 2:
                if (location.contentType == 3 && !TextUtils.isEmpty(redPackFrom.content) && locationStringToList(location.content).contains(redPackFrom.content)) {
                    return true;
                }
                return false;
            case 3:
            case 4:
                if (location.contentType == 3) {
                    return false;
                }
                if (!"2".equals(location.extendRange) || TextUtils.isEmpty(redPackFrom.cid)) {
                    if (!"3".equals(location.extendRange) || TextUtils.isEmpty(redPackFrom.pid)) {
                        if ("4".equals(location.extendRange) && !TextUtils.isEmpty(redPackFrom.zid) && locationStringToList(location.extendZid).contains(redPackFrom.zid)) {
                            return true;
                        }
                        return false;
                    } else if (locationStringToList(location.extendPid).contains(redPackFrom.pid)) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (locationStringToList(location.extendCid).contains(redPackFrom.cid)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public static void shareGift(Activity activity, Dialog dialog, String url, String picUrl, String title) {
        LogInfo.log("fornia", "redpackey 红包分享");
        if (dialog != null) {
            ShareUtils.RequestShareLink(activity);
            new HalfPlaySharePopwindow(activity, 11, true, title, url, "", picUrl, "", null).showPopupWindow(dialog.getWindow().getDecorView());
        }
    }

    public static void shareSuccess(Activity activity) {
        RedPacketSdkManager.getInstance().setHasShared();
        ((RedPacketDialog) ((LetvBaseActivity) activity).getBaseRedPacket().getResultDialog()).show();
    }

    public static void gotoGiftPage(Activity activity) {
        if (PreferencesManager.getInstance().isLogin()) {
            jumpRedPacketList(activity);
        } else {
            LetvLoginActivity.launch(activity, (int) LoginConstant.LOGIN_FROM_RED_PACKET);
        }
    }

    public static void gotoWeb(Context context, String url) {
        new LetvWebViewActivityConfig(context).launch(url, "");
    }

    public static void showToast() {
        ToastUtils.showToast(LetvApplication.getInstance().getResources().getString(2131100754));
    }

    private static ArrayList<String> locationStringToList(String string) {
        ArrayList<String> list = new ArrayList();
        if (!TextUtils.isEmpty(string)) {
            String[] locations = string.split(",");
            for (Object add : locations) {
                list.add(add);
            }
        }
        return list;
    }

    public static void jumpRedPacketList(Context context) {
        new LetvWebViewActivityConfig(context).launch(RedPacketSdkManager.getInstance().getGiftListBaseUrl(), context.getString(2131101602), false, true);
    }
}
