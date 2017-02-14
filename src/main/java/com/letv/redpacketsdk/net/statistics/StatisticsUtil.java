package com.letv.redpacketsdk.net.statistics;

import android.text.TextUtils;
import com.letv.pp.utils.NetworkUtils;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.RedPacketSharePreferences;
import com.letv.redpacketsdk.bean.GiftBean;
import com.letv.redpacketsdk.bean.RedPacketBean;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class StatisticsUtil {
    public static final int TYPE_ENTRY = 0;
    public static final int TYPE_FORECAST_ENTRY = 11;
    public static final int TYPE_FORECAST_FLOAT = 12;
    public static final int TYPE_GIFT_ACTIVIY_DESCRIBE = 18;
    public static final int TYPE_GIFT_ACTIVIY_DESCRIBE_CLICK = 19;
    public static final int TYPE_GIFT_CLOSE_TOAST = 10;
    public static final int TYPE_GIFT_DETAIL = 8;
    public static final int TYPE_GIFT_GET_GIFT = 16;
    public static final int TYPE_GIFT_NEXT_TIME = 17;
    public static final int TYPE_GIFT_SHARE = 9;
    public static final int TYPE_GIFT_SHARE_BUTTON_SHOW = 20;
    public static final int TYPE_Gift = 6;
    public static final int TYPE_Gift_CLOSE = 7;
    public static final int TYPE_SHAKE_CLOSE_BUTTON = 3;
    public static final int TYPE_SHAKE_FLOAT = 1;
    public static final int TYPE_SHAKE_FLOAT_ACTIVITY_DESCRIBE = 14;
    public static final int TYPE_SHAKE_FLOAT_ACTIVITY_DESCRIBE_CLICK = 15;
    public static final int TYPE_SHAKE_FLOAT_BUTTON = 2;
    public static final int TYPE_SHAKE_FLOAT_PIC = 13;
    public static final int TYPE_SHAKE_LOADING = 5;
    public static final int TYPE_SHAKE_PHONE_FUNC = 4;

    public static void statistics(int type) {
        boolean has = RedPacketSdkManager.getInstance().hasRedPacket();
        RedPacketBean rpBean = RedPacketSdkManager.getInstance().getRedPacketBean();
        GiftBean giftBean = RedPacketSharePreferences.getInstance().getGiftBean();
        String actionCode = "0";
        String redPacketId = has ? rpBean.id : "";
        String redPacketTitle = has ? rpBean.title : "";
        String giftMsg = "";
        String classify = "";
        int position = 0;
        String rpType = NetworkUtils.DELIMITER_LINE;
        String rpurl = NetworkUtils.DELIMITER_LINE;
        String companyName = "";
        if (giftBean != null) {
            companyName = TextUtils.isEmpty(giftBean.company_name) ? "乐视集团" : giftBean.company_name;
        }
        switch (type) {
            case 0:
                classify = "rpid01";
                if (has) {
                    rpType = TextUtils.isEmpty(rpBean.pic1) ? "0" : "1";
                } else {
                    rpType = NetworkUtils.DELIMITER_LINE;
                }
                redPacketTitle = "";
                break;
            case 1:
                actionCode = "19";
                classify = "rpid02";
                if (!has) {
                    rpType = NetworkUtils.DELIMITER_LINE;
                    break;
                } else {
                    rpType = TextUtils.isEmpty(rpBean.pic1) ? "0" : "1";
                    break;
                }
            case 2:
                classify = "rpid02";
                position = 1;
                redPacketTitle = "";
                break;
            case 3:
                classify = "rpid02";
                position = 2;
                redPacketTitle = "";
                break;
            case 4:
                classify = "rpid02";
                position = 3;
                redPacketTitle = "";
                break;
            case 5:
                actionCode = "19";
                classify = "rpid03";
                redPacketTitle = "";
                break;
            case 6:
                actionCode = "19";
                if (giftBean == null || giftBean.big_type == 0 || giftBean.big_type == -1) {
                    giftMsg = "未中奖";
                } else {
                    giftMsg = companyName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.title + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.big_type;
                }
                if (giftBean != null) {
                    rpurl = giftBean.price_image;
                }
                classify = "rpid04";
                break;
            case 7:
                if (giftBean == null || giftBean.big_type == 0 || giftBean.big_type == -1) {
                    giftMsg = "未中奖";
                } else {
                    giftMsg = companyName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.title + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.big_type;
                }
                classify = "rpid04";
                position = 1;
                break;
            case 8:
                if (giftBean == null || giftBean.big_type == 0 || giftBean.big_type == -1) {
                    giftMsg = "未中奖";
                } else {
                    giftMsg = companyName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.title + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.big_type;
                }
                classify = "rpid04";
                position = 2;
                break;
            case 9:
                if (giftBean == null || giftBean.big_type == 0 || giftBean.big_type == -1) {
                    giftMsg = "未中奖";
                } else {
                    giftMsg = companyName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.title + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.big_type;
                }
                classify = "rpid04";
                position = 4;
                break;
            case 10:
                actionCode = "19";
                classify = "rpid05";
                giftMsg = "可在我的红包中查看相关红包奖品";
                redPacketTitle = "";
                break;
            case 11:
                classify = "rpid01";
                rpType = "2";
                redPacketId = RedPacketSdkManager.getInstance().getForecastBean().id;
                redPacketTitle = "";
                break;
            case 12:
                actionCode = "19";
                classify = "rpid02";
                rpType = "2";
                redPacketId = RedPacketSdkManager.getInstance().getForecastBean().id;
                redPacketTitle = "";
                break;
            case 13:
                actionCode = "19";
                if (has) {
                    giftMsg = TextUtils.isEmpty(rpBean.mobilePic) ? "0" : "1";
                } else {
                    giftMsg = NetworkUtils.DELIMITER_LINE;
                }
                classify = "rpid02";
                position = 4;
                redPacketTitle = "";
                break;
            case 14:
                actionCode = "19";
                classify = "rpid02";
                position = 5;
                redPacketTitle = "";
                break;
            case 15:
                classify = "rpid02";
                position = 5;
                redPacketTitle = "";
                break;
            case 16:
                if (giftBean == null || giftBean.big_type == 0 || giftBean.big_type == -1) {
                    giftMsg = "未中奖";
                } else {
                    giftMsg = companyName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.title + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.big_type;
                }
                classify = "rpid04";
                position = 3;
                rpurl = has ? rpBean.getGiftButtonString : "";
                break;
            case 17:
                actionCode = "19";
                if (giftBean != null) {
                    giftMsg = rpBean.nextRedPacketTime;
                }
                classify = "rpid04";
                position = 5;
                break;
            case 18:
                actionCode = "19";
                classify = "rpid04";
                position = 6;
                break;
            case 19:
                classify = "rpid04";
                position = 6;
                break;
            case 20:
                actionCode = "19";
                if (giftBean == null || giftBean.big_type == 0 || giftBean.big_type == -1) {
                    giftMsg = "未中奖";
                } else {
                    giftMsg = companyName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.title + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + giftBean.big_type;
                }
                classify = "rpid04";
                position = 4;
                break;
        }
        LetvRedPacketStatistics.statisticsActionInfo(RedPacketSdkManager.getInstance().getApplicationContext(), actionCode, redPacketId, redPacketTitle, giftMsg, rpurl, classify, position, rpType);
    }
}
