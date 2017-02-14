package com.letv.redpacketsdk.net.statistics;

import android.content.Context;
import android.text.TextUtils;
import com.letv.datastatistics.DataManager;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.pp.utils.NetworkUtils;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.net.AsyncTaskHttp;
import com.letv.redpacketsdk.net.HTTPURL;
import com.letv.redpacketsdk.utils.MobileUtil;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.net.URLEncoder;

public class LetvRedPacketStatistics {
    protected static void statisticsActionInfo(Context context, String aCode, String contentID, String name, String rpmsg, String rpurl, String fl, int wz, String rptype) {
        statisticsActionInfo(context, aCode, fl, name, wz, "rptype=" + rptype + "&rpid=" + getData(contentID) + "&rpurl=" + getData(rpurl), null, null, null, null, null, null, -1, null, null, null, null, null, rpmsg);
    }

    private static void statisticsActionInfo(Context context, String actionCode, String fl, String name, int wz, String expandProperty, String cid, String pid, String vid, String zid, String lid, String reid, int rank, String bucket, String area, String targetUrl, String curUrl, String uuid, String rpmsg) {
        RedPacketSdkManager instance = RedPacketSdkManager.getInstance();
        ActionStatisticsData data = new ActionStatisticsData();
        data.mActionCode = actionCode;
        data.mLid = lid;
        data.mReid = reid;
        data.mRank = rank;
        data.mBucket = bucket;
        data.mArea = area;
        data.mTargeturl = targetUrl;
        data.mCurUrl = curUrl;
        data.mUuid = getData(instance.getAppRunId());
        data.mUid = getData(instance.getUid());
        data.mActionProperty.fl = fl;
        data.mActionProperty.name = name;
        data.mActionProperty.wz = wz;
        data.mActionProperty.rpmsg = rpmsg;
        data.mActionProperty.ep = expandProperty + "&appid=" + getData(instance.getAppId()) + "&sdk_v=" + getData(instance.getSdkVersion());
        sendActionInfo(context, data);
    }

    private static void sendActionInfo(Context context, ActionStatisticsData data) {
        if (context != null && data != null) {
            String NOT_UPLOAD = DataManager.NOT_UPLOAD;
            String uuid = data.mUuid;
            StringBuffer sb = new StringBuffer();
            sb.append(getCommonParams(context));
            sb.append("&acode=" + data.mActionCode);
            sb.append("&ap=" + getActionPropertyParams(context, data.mActionProperty));
            sb.append("&ar=" + getData(getTrimData(data.mActionResult)));
            sb.append("&cid=" + replaceStr(getData(data.mCid)));
            sb.append("&pid=" + replaceStr(getData(data.mPid)));
            sb.append("&vid=" + replaceStr(getData(data.mVid)));
            sb.append("&uid=" + getData(RedPacketSdkManager.getInstance().getUid()));
            sb.append("&uuid=" + uuid);
            sb.append("&pcode=" + getData(data.mPcode));
            sb.append("&ilu=" + (TextUtils.isEmpty(RedPacketSdkManager.getInstance().getUid()) ? 1 : 0));
            sb.append("&cur_url=-");
            if (NOT_UPLOAD.equals(data.mReid)) {
                sb.append("&reid=" + System.currentTimeMillis());
            } else {
                sb.append("&reid=" + getData(data.mReid));
            }
            sb.append("&area=" + getData(data.mArea));
            sb.append("&bucket=" + getData(data.mBucket));
            sb.append("&rank=" + getData((long) data.mRank));
            sb.append("&apprunid=" + getData(RedPacketSdkManager.getInstance().getAppRunId()));
            if (!NOT_UPLOAD.equals(data.mZid)) {
                sb.append("&zid=" + replaceStr(getData(getTrimData(data.mZid))));
            }
            if (!NOT_UPLOAD.equals(data.mLid)) {
                sb.append("&lid=" + getData(getTrimData(data.mLid)));
            }
            if (!NOT_UPLOAD.equals(data.mTargeturl)) {
                sb.append("&targeturl=" + getData(getTrimData(data.mTargeturl)));
            }
            AsyncTaskHttp asyncTaskHttp = new AsyncTaskHttp(HTTPURL.getStatisticsBase() + sb.toString(), null);
        }
    }

    private static String getCommonParams(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("ver=3.0");
        sb.append("&p1=0&p2=0m&p3=0m12");
        sb.append("&nt=" + getTrimData(MobileUtil.getNetType(context)));
        sb.append("&lc=-");
        sb.append("&ch=-");
        sb.append("&r=" + System.currentTimeMillis());
        sb.append("&auid=" + MobileUtil.generateDeviceId(context));
        sb.append("&mac=-");
        sb.append("&im=" + MobileUtil.getIMEI(context));
        return sb.toString();
    }

    private static String getActionPropertyParams(Context context, ActionProperty actionProperty) {
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(actionProperty.fl)) {
            sb.append(StaticticsName.STATICTICS_NAM_FL + actionProperty.fl);
        }
        if (TextUtils.isEmpty(actionProperty.name)) {
            sb.append("&name=-");
        } else {
            sb.append("&name=" + actionProperty.name);
        }
        if (actionProperty.wz > 0) {
            sb.append("&wz=" + actionProperty.wz);
        } else {
            sb.append("&wz=-");
        }
        if (!TextUtils.isEmpty(actionProperty.fragId)) {
            sb.append("&fragid=" + actionProperty.fragId);
        }
        if (!TextUtils.isEmpty(actionProperty.scid)) {
            sb.append("&scid=" + actionProperty.scid);
        }
        sb.append("&rpmsg=" + getData(actionProperty.rpmsg));
        if (!TextUtils.isEmpty(actionProperty.ep)) {
            if (!actionProperty.ep.startsWith("&")) {
                sb.append("&");
            }
            sb.append(actionProperty.ep);
        }
        return URLEncoder.encode(getTrimData(sb.toString()));
    }

    private static String getData(String data) {
        if (data == null || data.length() <= 0) {
            return NetworkUtils.DELIMITER_LINE;
        }
        return removeQuote(data.replace(" ", EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR));
    }

    private static String removeQuote(String data) {
        if (TextUtils.isEmpty(data) || !data.contains("\"")) {
            return data;
        }
        return data.replace("\"", "");
    }

    private static String getData(long data) {
        return data <= 0 ? NetworkUtils.DELIMITER_LINE : String.valueOf(data);
    }

    private static String getTrimData(String data) {
        return TextUtils.isEmpty(data) ? NetworkUtils.DELIMITER_LINE : data.trim();
    }

    private static String replaceStr(String str) {
        String value = NetworkUtils.DELIMITER_LINE;
        if (str == null || str.equals("") || str.equals("0") || str.equals("-1")) {
            return value;
        }
        return str;
    }
}
