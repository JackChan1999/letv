package com.letv.datastatistics;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.letv.datastatistics.bean.ActionProperty;
import com.letv.datastatistics.bean.ActionStatisticsData;
import com.letv.datastatistics.bean.ErrorStatisticsData;
import com.letv.datastatistics.bean.StatisCacheBean;
import com.letv.datastatistics.constant.StatisticsConstant;
import com.letv.datastatistics.http.HttpEngine;
import com.letv.datastatistics.http.ThreadPoolManager;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.datastatistics.util.DataUtils;
import com.letv.datastatistics.util.StatisticsUrlHelper;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.net.URLEncoder;

public class DataManager {
    public static final String NOT_UPLOAD = "not_upload";
    public static final int NOT_UPLOAD_INT = -10000;
    private static final String TAG = "DataManager";
    public static String activeState = "0";
    public static boolean isDoubleChannel = false;
    public static String originalPcode = "";
    public static String originalVersion = IdManager.DEFAULT_VERSION_NAME;

    public void sendPlayInfo(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, int ap, String zid, String lid, String atc, String stc, int prl, String cdev, String caid, long ctime, int pay, int joint, int ipt, String custid, String apprunid) {
        final String str = p1;
        final String str2 = p2;
        final String str3 = ac;
        final String str4 = err;
        final String str5 = pt;
        final String str6 = ut;
        final String str7 = uid;
        final Context context2 = context;
        final String str8 = uuidTimeStamp;
        final String str9 = cid;
        final String str10 = pid;
        final String str11 = vid;
        final String str12 = vlen;
        final String str13 = ch;
        final String str14 = retryCount;
        final String str15 = type;
        final String str16 = vt;
        final String str17 = url;
        final String str18 = ref;
        final String str19 = py;
        final String str20 = st;
        final int i = ilu;
        final String str21 = pcode;
        final String str22 = weid;
        final int i2 = ap;
        final String str23 = zid;
        final String str24 = lid;
        final String str25 = atc;
        final String str26 = stc;
        final int i3 = prl;
        final String str27 = cdev;
        final String str28 = caid;
        final long j = ctime;
        final int i4 = pay;
        final int i5 = joint;
        final int i6 = ipt;
        final String str29 = custid;
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                sb.append("ver=3.0&");
                sb.append("p1=" + DataUtils.getData(DataUtils.getTrimData(str)) + "&");
                sb.append("p2=" + DataUtils.getData(DataUtils.getTrimData(str + str2)) + "&");
                sb.append("p3=001&");
                sb.append("ac=" + DataUtils.getData(str3) + "&");
                sb.append("err=" + DataUtils.getData(str4) + "&");
                sb.append("pt=" + DataUtils.getData(str5) + "&");
                sb.append("ut=" + DataUtils.getData(str6) + "&");
                sb.append("uid=" + DataUtils.getData(str7) + "&");
                sb.append("lc=-&");
                sb.append("auid=" + DataUtils.getData(DataUtils.generateDeviceId(context2)) + "&");
                sb.append("uuid=" + DataUtils.getData(str8) + "&");
                sb.append("nt=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getNetType(context2))) + "&");
                sb.append(StaticticsName.STATICTICS_NAM_CID + DataManager.this.replaceStr(DataUtils.getData(str9)) + "&");
                sb.append("pid=" + DataManager.this.replaceStr(DataUtils.getData(str10)) + "&");
                sb.append("vid=" + DataManager.this.replaceStr(DataUtils.getData(str11)) + "&");
                sb.append("vlen=" + DataUtils.getData(str12) + "&");
                if (!DataManager.NOT_UPLOAD.equals(str13)) {
                    sb.append("ch=" + DataUtils.getData(str13) + "&");
                }
                sb.append("ry=" + DataUtils.getData(str14) + "&");
                sb.append("ty=" + DataUtils.getData(str15) + "&");
                sb.append("vt=" + DataUtils.getData(str16) + "&");
                sb.append("url=" + URLEncoder.encode(DataUtils.getData(str17)) + "&");
                sb.append("ref=" + URLEncoder.encode(DataUtils.getData(str18)) + "&");
                sb.append("pv=2.0&");
                if (str19 != null) {
                    StringBuffer sb1 = new StringBuffer(str19);
                    sb1.append("&app=" + DataUtils.getData(DataUtils.getClientVersionName(context2)));
                    sb1.append("&bsi_msg=" + DataManager.this.getBsiMsg(context2));
                    sb1.append("&active=" + DataManager.activeState);
                    if (DataManager.isDoubleChannel) {
                        sb1.append("&ini_ver=" + DataManager.originalVersion);
                        sb1.append("&pcode=" + DataManager.originalPcode);
                    }
                    sb.append("py=" + URLEncoder.encode(DataUtils.getData(DataUtils.getTrimData(sb1.toString()))) + "&");
                }
                sb.append("st=" + URLEncoder.encode(DataUtils.getData(str20)) + "&");
                sb.append("ilu=" + (i > 0 ? 1 : 0) + "&");
                sb.append("pcode=" + DataUtils.getData(str21) + "&");
                sb.append("weid=" + DataUtils.getData(str22) + "&");
                sb.append("r=" + System.currentTimeMillis() + "&");
                if (DataManager.NOT_UPLOAD_INT != i2) {
                    sb.append("ap=" + i2 + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str23)) {
                    sb.append("zid=" + DataManager.this.replaceStr(DataUtils.getData(DataUtils.getTrimData(str23))) + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str24)) {
                    sb.append("lid=" + DataManager.this.replaceStr(DataUtils.getData(str24)) + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str25)) {
                    sb.append("atc=" + DataUtils.getData(str25) + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str26)) {
                    sb.append("stc=" + DataUtils.getData(str26) + "&");
                }
                if (DataManager.NOT_UPLOAD_INT != i3) {
                    sb.append("prl=" + i3 + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str27)) {
                    sb.append("cdev=" + DataUtils.getData(str27) + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str28)) {
                    sb.append("caid=" + DataUtils.getData(str28) + "&");
                }
                if (-10000 != j) {
                    sb.append("ctime=" + j + "&");
                }
                if (DataManager.NOT_UPLOAD_INT != i4) {
                    sb.append("pay=" + i4 + "&");
                }
                if (DataManager.NOT_UPLOAD_INT != i5) {
                    sb.append("joint=" + i5 + "&");
                }
                if (DataManager.NOT_UPLOAD_INT != i6) {
                    sb.append("ipt=" + i6 + "&");
                }
                if (!DataManager.NOT_UPLOAD.equals(str29)) {
                    sb.append("custid=" + DataUtils.getData(str29) + "&");
                }
                sb.append("apprunid=" + DataUtils.getData(DataManager.this.getUuid(context2, false)));
                sb.append("&mac=-");
                sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context2)));
                sb.append("&im=" + DataUtils.getEncryptIMEI(context2, StatisticsConstant.DES_KEY));
                sb.append("&imsi=" + DataUtils.getEncryptIMSI(context2, StatisticsConstant.DES_KEY));
                if (DataStatistics.getInstance().isDebug()) {
                    Log.d(DataManager.TAG, "sendPlayInfo:" + sb.toString());
                }
                try {
                    String cacheData = StatisticsUrlHelper.getStatisticsPlayUrl() + sb.toString();
                    HttpEngine.getInstance().doHttpGet(context2, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendPlayInfo Exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    public void sendActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String zid, String lid, String time, String reid, String cms_num, String targetUrl) {
        if (DataStatistics.getInstance().isDebug()) {
            Log.d(TAG, "acode = " + acode + " ap =" + ap + " ,vid=" + vid + " ,cid=" + cid + " ,lid=" + lid + " ,nt=" + DataUtils.getNetType(context));
        }
        final String uuid = DataUtils.getData(getUuid(context, false));
        final Context context2 = context;
        final String str = p1;
        final String str2 = p2;
        final String str3 = pcode;
        final String str4 = acode;
        final String str5 = ap;
        final String str6 = ar;
        final String str7 = cid;
        final String str8 = pid;
        final String str9 = vid;
        final String str10 = uid;
        final String str11 = cur_url;
        final String str12 = area;
        final String str13 = bucket;
        final String str14 = rank;
        final int i = ilu;
        final String str15 = zid;
        final String str16 = lid;
        final String str17 = time;
        final String str18 = reid;
        final String str19 = cms_num;
        final String str20 = targetUrl;
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                DataManager.this.sendActionInfoSub(context2, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, i, str15, str16, str17, str18, str19, str20, uuid, false);
            }
        });
    }

    public void sendPushActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String lid, String zid) {
        Context context2 = context;
        String str = p1;
        String str2 = p2;
        String str3 = pcode;
        String str4 = acode;
        String str5 = ap;
        String str6 = area;
        String str7 = cid;
        String str8 = pid;
        String str9 = vid;
        String str10 = uid;
        String str11 = cur_url;
        String str12 = area;
        String str13 = bucket;
        String str14 = rank;
        int i = ilu;
        String str15 = zid;
        String str16 = lid;
        sendActionInfoSub(context2, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, i, str15, str16, NOT_UPLOAD, NOT_UPLOAD, NOT_UPLOAD, NOT_UPLOAD, DataUtils.getData(getUuid(context, false)), true);
    }

    public void sendActionInfoSub(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String zid, String lid, String time, String reid, String cms_num, String targetUrl, String uuid, boolean isPush) {
        StringBuffer sb = new StringBuffer();
        sb.append("ver=3.0&");
        sb.append("p1=" + DataUtils.getData(DataUtils.getTrimData(p1)) + "&");
        sb.append("p2=" + DataUtils.getData(DataUtils.getTrimData(p1 + p2)) + "&");
        sb.append("p3=001&");
        sb.append("acode=" + acode + "&");
        StringBuffer sb1 = new StringBuffer(ap);
        sb1.append("&app=" + DataUtils.getData(DataUtils.getClientVersionName(context)));
        sb1.append("&bsi_msg=" + getBsiMsg(context));
        sb1.append("&active=" + activeState);
        if (isDoubleChannel) {
            sb1.append("&ini_ver=" + originalVersion);
            sb1.append("&pcode=" + originalPcode);
        }
        sb.append("ap=" + URLEncoder.encode(DataUtils.getData(DataUtils.getTrimData(sb1.toString()))) + "&");
        sb.append("ar=" + DataUtils.getData(DataUtils.getTrimData(ar)) + "&");
        sb.append(StaticticsName.STATICTICS_NAM_CID + replaceStr(DataUtils.getData(cid)) + "&");
        sb.append("pid=" + replaceStr(DataUtils.getData(pid)) + "&");
        sb.append("vid=" + replaceStr(DataUtils.getData(vid)) + "&");
        sb.append("uid=" + DataUtils.getData(uid) + "&");
        sb.append("uuid=" + uuid + "&");
        sb.append("nt=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getNetType(context))) + "&");
        sb.append("lc=-&");
        if (!NOT_UPLOAD.equals(cur_url)) {
            sb.append("cur_url=" + URLEncoder.encode(DataUtils.getData(cur_url)) + "&");
        }
        sb.append("ch=-&");
        sb.append("pcode=" + DataUtils.getData(pcode) + "&");
        sb.append("auid=" + DataUtils.getData(DataUtils.generateDeviceId(context)) + "&");
        if (ilu != -1 || isPush) {
            sb.append("ilu=" + (ilu > 0 ? 1 : 0) + "&");
        } else {
            sb.append("ilu=-&");
        }
        if (NOT_UPLOAD.equals(reid)) {
            sb.append("reid=" + System.currentTimeMillis() + "&");
        } else {
            sb.append("reid=" + DataUtils.getData(reid) + "&");
        }
        sb.append("area=" + DataUtils.getData(area) + "&");
        sb.append("bucket=" + DataUtils.getData(bucket) + "&");
        sb.append("rank=" + DataUtils.getData(rank) + "&");
        if (!NOT_UPLOAD.equals(zid)) {
            sb.append("zid=" + replaceStr(DataUtils.getData(DataUtils.getTrimData(zid))) + "&");
        }
        if (!NOT_UPLOAD.equals(lid)) {
            sb.append("lid=" + DataUtils.getData(DataUtils.getTrimData(lid)) + "&");
        }
        if (!NOT_UPLOAD.equals(time)) {
            sb.append("time" + DataUtils.timeClockString("yyyyMMdd_hh:mm:ss") + "&");
        }
        if (!NOT_UPLOAD.equals(cms_num)) {
            sb.append("cms_num=" + DataUtils.getData(cms_num) + "&");
        }
        if (!NOT_UPLOAD.equals(targetUrl)) {
            sb.append("targeturl=" + DataUtils.getData(DataUtils.getTrimData(targetUrl)) + "&");
        }
        sb.append("r=" + System.currentTimeMillis());
        sb.append("&apprunid=" + uuid);
        sb.append("&mac=-");
        sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context)));
        sb.append("&im=" + DataUtils.getEncryptIMEI(context, StatisticsConstant.DES_KEY));
        sb.append("&imsi=" + DataUtils.getEncryptIMSI(context, StatisticsConstant.DES_KEY));
        sb.append("&ctime=" + System.currentTimeMillis());
        if (DataStatistics.getInstance().isDebug()) {
            Log.d(TAG, "sendActionInfoSub:" + sb.toString());
        }
        try {
            String cacheData = StatisticsUrlHelper.getStatisticsActionUrl() + sb.toString();
            HttpEngine.getInstance().doHttpGet(context, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            if (DataStatistics.getInstance().isDebug()) {
                Log.d(TAG, "sendActionInfoSub Exception:" + e.getMessage());
            }
        }
    }

    public void sendActionInfo(Context context, ActionStatisticsData data) {
        if (context != null && data != null) {
            final boolean isStatisticsTimeToPlay = TextUtils.equals("22", data.mActionCode) && !TextUtils.isEmpty(data.mUuid);
            final boolean isGetLocal = TextUtils.equals("2", data.mActionCode);
            final String uuid = isStatisticsTimeToPlay ? data.mUuid : DataUtils.getData(getUuid(context, isGetLocal));
            final Context context2 = context;
            final ActionStatisticsData actionStatisticsData = data;
            ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
                public void run() {
                    StringBuffer sb = new StringBuffer();
                    sb.append(DataManager.this.getCommonParams(context2));
                    sb.append("&acode=" + actionStatisticsData.mActionCode);
                    sb.append("&ap=" + DataManager.this.getActionPropertyParams(context2, actionStatisticsData.mActionProperty));
                    sb.append("&ar=" + DataUtils.getData(DataUtils.getTrimData(actionStatisticsData.mActionResult)));
                    sb.append("&cid=" + DataManager.this.replaceStr(DataUtils.getData(actionStatisticsData.mCid)));
                    sb.append("&pid=" + DataManager.this.replaceStr(DataUtils.getData(actionStatisticsData.mPid)));
                    sb.append("&vid=" + DataManager.this.replaceStr(DataUtils.getData(actionStatisticsData.mVid)));
                    sb.append("&uid=" + DataUtils.getData(actionStatisticsData.mUid));
                    sb.append("&uuid=" + uuid);
                    sb.append("&pcode=" + DataUtils.getData(actionStatisticsData.mPcode));
                    sb.append("&ilu=" + (actionStatisticsData.mIsLogined > 0 ? 1 : 0));
                    if (!DataManager.NOT_UPLOAD.equals(actionStatisticsData.mCurUrl)) {
                        sb.append("&cur_url=" + URLEncoder.encode(DataUtils.getData(actionStatisticsData.mCurUrl)));
                    }
                    if (DataManager.NOT_UPLOAD.equals(actionStatisticsData.mReid)) {
                        sb.append("&reid=" + System.currentTimeMillis());
                    } else {
                        sb.append("&reid=" + DataUtils.getData(actionStatisticsData.mReid));
                    }
                    sb.append("&area=" + DataUtils.getData(actionStatisticsData.mArea));
                    sb.append("&bucket=" + DataUtils.getData(actionStatisticsData.mBucket));
                    sb.append("&rank=" + DataUtils.getData((long) actionStatisticsData.mRank));
                    sb.append("&apprunid=" + (isStatisticsTimeToPlay ? DataUtils.getData(DataManager.this.getUuid(context2, isGetLocal)) : uuid));
                    sb.append("&ctime=" + System.currentTimeMillis());
                    if (!DataManager.NOT_UPLOAD.equals(actionStatisticsData.mZid)) {
                        sb.append("&zid=" + DataManager.this.replaceStr(DataUtils.getData(DataUtils.getTrimData(actionStatisticsData.mZid))));
                    }
                    if (!DataManager.NOT_UPLOAD.equals(actionStatisticsData.mLid)) {
                        sb.append("&lid=" + DataUtils.getData(DataUtils.getTrimData(actionStatisticsData.mLid)));
                    }
                    if (!DataManager.NOT_UPLOAD.equals(actionStatisticsData.mTargeturl)) {
                        sb.append("&targeturl=" + DataUtils.getData(DataUtils.getTrimData(actionStatisticsData.mTargeturl)));
                    }
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendActionInfoSub:" + sb.toString());
                    }
                    try {
                        String cacheData = StatisticsUrlHelper.getStatisticsActionUrl() + sb.toString();
                        HttpEngine.getInstance().doHttpGet(context2, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (DataStatistics.getInstance().isDebug()) {
                            Log.d(DataManager.TAG, "sendActionInfoSub Exception:" + e.getMessage());
                        }
                    }
                }
            });
        }
    }

    public void sendErrorInfo(final Context context, final ErrorStatisticsData data) {
        if (context != null && data != null) {
            ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
                public void run() {
                    StringBuffer sb = new StringBuffer();
                    sb.append(DataManager.this.getCommonParams(context));
                    sb.append("&err=" + data.mErrorCode);
                    if (!TextUtils.isEmpty(data.mErrorType)) {
                        sb.append("&et=" + data.mErrorType);
                    }
                    sb.append(DataManager.this.getDeviceInfo(context));
                    sb.append("&src=" + data.mSrc);
                    String ep = "&time=" + DataUtils.timeClockString("yyyyMMdd_hh:mm:ss");
                    StringBuilder append = new StringBuilder().append("&ep=");
                    if (!TextUtils.isEmpty(data.mErrorProperty)) {
                        ep = data.mErrorCode + ep;
                    }
                    sb.append(append.append(URLEncoder.encode(ep)).toString());
                    sb.append("&cid=" + DataManager.this.replaceStr(DataUtils.getData(data.mCid)));
                    sb.append("&pid=" + DataManager.this.replaceStr(DataUtils.getData(data.mPid)));
                    sb.append("&vid=" + DataManager.this.replaceStr(DataUtils.getData(data.mVid)));
                    sb.append("&zid=" + DataManager.this.replaceStr(DataUtils.getData(data.mZid)));
                    String apprunid = DataUtils.getData(DataManager.this.getUuid(context, false));
                    if (!TextUtils.equals(data.mUuid, DataManager.NOT_UPLOAD)) {
                        String str;
                        StringBuilder append2 = new StringBuilder().append("&uuid=");
                        if (TextUtils.isEmpty(data.mUuid)) {
                            str = apprunid;
                        } else {
                            str = data.mUuid;
                        }
                        sb.append(append2.append(str).toString());
                    }
                    sb.append("&apprunid=" + apprunid);
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendErrorInfo:" + sb.toString());
                    }
                    try {
                        String cacheData = StatisticsUrlHelper.getStatisticsErrorUrl() + sb.toString();
                        HttpEngine.getInstance().doHttpGet(context, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (DataStatistics.getInstance().isDebug()) {
                            Log.d(DataManager.TAG, "sendErrorInfo Exception:" + e.getMessage());
                        }
                    }
                }
            });
        }
    }

    private String getCommonParams(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("ver=3.0");
        sb.append("&p1=0");
        sb.append("&p2=00");
        sb.append("&p3=001");
        sb.append("&nt=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getNetType(context))));
        sb.append("&lc=-");
        sb.append("&ch=-");
        sb.append("&r=" + System.currentTimeMillis());
        sb.append("&auid=" + DataUtils.getData(DataUtils.generateDeviceId(context)));
        sb.append("&mac=-");
        sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context)));
        sb.append("&im=" + DataUtils.getEncryptIMEI(context, StatisticsConstant.DES_KEY));
        sb.append("&imsi=" + DataUtils.getEncryptIMSI(context, StatisticsConstant.DES_KEY));
        return sb.toString();
    }

    private String getActionPropertyParams(Context context, ActionProperty actionProperty) {
        StringBuffer sb = new StringBuffer();
        if (!TextUtils.isEmpty(actionProperty.fl)) {
            sb.append(StaticticsName.STATICTICS_NAM_FL + actionProperty.fl);
        }
        if (!TextUtils.isEmpty(actionProperty.name)) {
            sb.append("&name=" + actionProperty.name);
        }
        if (actionProperty.wz >= 0) {
            sb.append("&wz=" + actionProperty.wz);
        }
        if (!TextUtils.isEmpty(actionProperty.pageId)) {
            sb.append("&pageid=" + actionProperty.pageId);
        }
        if (!TextUtils.isEmpty(actionProperty.fragId)) {
            sb.append("&fragid=" + actionProperty.fragId);
        }
        if (!TextUtils.isEmpty(actionProperty.scid)) {
            sb.append("&scid=" + actionProperty.scid);
        }
        if (!TextUtils.isEmpty(actionProperty.ep)) {
            if (!actionProperty.ep.startsWith("&")) {
                sb.append("&");
            }
            sb.append(actionProperty.ep);
        }
        sb.append("&app=" + DataUtils.getData(DataUtils.getClientVersionName(context)));
        sb.append("&bsi_msg=" + getBsiMsg(context));
        sb.append("&active=" + activeState);
        if (isDoubleChannel) {
            sb.append("&ini_ver=" + originalVersion);
            sb.append("&pcode=" + originalPcode);
        }
        return URLEncoder.encode(DataUtils.getData(DataUtils.getTrimData(sb.toString())));
    }

    private String getDeviceInfo(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("&os=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getSystemName())));
        sb.append("&osv=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getOSVersionName())));
        sb.append("&app=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getClientVersionName(context))));
        sb.append("&bd=" + URLEncoder.encode(DataUtils.getData(DataUtils.getTrimData(DataUtils.getBrandName()))));
        sb.append("&xh=phone");
        sb.append("&model=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getDeviceName())));
        sb.append("&ro=" + DataUtils.getNewResolution(context));
        return sb.toString();
    }

    public void sendLoginAndEnv(Context context, String uid, String lp, String ref, String ts, String pcode, int st, String zid, String xh, String lo, String la, String location, boolean isLogin) {
        final String uuid = DataUtils.getData(getUuid(context, false));
        final String str = uid;
        final Context context2 = context;
        final String str2 = lp;
        final String str3 = pcode;
        final boolean z = isLogin;
        final String str4 = ref;
        final String str5 = ts;
        final int i = st;
        final String str6 = zid;
        final String str7 = xh;
        final String str8 = lo;
        final String str9 = la;
        final String str10 = location;
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                sb.append("ver=3.0");
                sb.append("&p1=0");
                sb.append("&p2=00");
                sb.append("&p3=001");
                sb.append("&uid=" + DataUtils.getData(str));
                sb.append("&lc=-");
                sb.append("&auid=" + DataUtils.getData(DataUtils.generateDeviceId(context2)));
                sb.append("&uuid=" + uuid);
                StringBuffer sb1 = new StringBuffer(str2);
                sb1.append("&app=" + DataUtils.getData(DataUtils.getClientVersionName(context2)));
                sb1.append("&os=" + DataUtils.getData(DataUtils.getSystemName()));
                sb1.append("&model=" + DataUtils.getUnEmptyData(DataUtils.getDeviceName()));
                sb1.append("&mem=" + DataUtils.getMemoryTotalSize());
                sb1.append("&bsi_msg=" + DataManager.this.getBsiMsg(context2));
                sb1.append("&active=" + DataManager.activeState);
                if (DataManager.isDoubleChannel) {
                    sb1.append("&ini_ver=" + DataManager.originalVersion);
                    sb1.append("&pcode=" + DataManager.originalPcode);
                }
                if ("010110696".equals(str3)) {
                    sb1.append("&aliuuid=" + DataUtils.getUnEmptyData(DataUtils.getCloudUUID()));
                }
                sb.append((z ? "&lp=" : "&ep=") + URLEncoder.encode(DataUtils.getUnEmptyData(sb1.toString())));
                sb.append("&ch=-");
                sb.append("&ref=" + DataUtils.getData(str4));
                sb.append("&ts=" + DataUtils.getData(str5));
                sb.append("&pcode=" + DataUtils.getData(str3));
                sb.append("&st=" + i);
                sb.append("&zid=" + DataManager.this.replaceStr(DataUtils.getData(DataUtils.getTrimData(str6))));
                sb.append("&r=" + System.currentTimeMillis());
                sb.append("&apprunid=" + uuid);
                sb.append("&mac=-");
                sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context2)));
                sb.append("&im=" + DataUtils.getEncryptIMEI(context2, StatisticsConstant.DES_KEY));
                sb.append("&imsi=" + DataUtils.getEncryptIMSI(context2, StatisticsConstant.DES_KEY));
                sb.append("&nt=" + DataUtils.getData(DataUtils.getNetType(context2)));
                sb.append("&os=" + DataUtils.getData(DataUtils.getSystemName()));
                sb.append("&osv=" + DataUtils.getData(DataUtils.getOSVersionName()));
                sb.append("&app=" + DataUtils.getData(DataUtils.getClientVersionName(context2)));
                sb.append("&bd=" + URLEncoder.encode(DataUtils.getUnEmptyData(DataUtils.getBrandName())));
                sb.append("&xh=" + URLEncoder.encode(DataUtils.getData(str7)));
                sb.append("&ro=" + DataUtils.getDataEmpty(DataUtils.getNewResolution(context2)));
                sb.append("&br=chrome");
                sb.append("&cs=" + DataUtils.getCpuFrequency(1) + "*" + DataUtils.getCpuNumCores());
                sb.append("&ssid=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getConnectWifiSsid(context2))));
                sb.append("&lo=" + DataUtils.getData(DataUtils.getTrimData(str8)));
                sb.append("&la=" + DataUtils.getData(DataUtils.getTrimData(str9)));
                sb.append("&location=" + URLEncoder.encode(DataUtils.getUnEmptyData(str10)));
                sb.append("&ctime=" + System.currentTimeMillis());
                if (DataStatistics.getInstance().isDebug()) {
                    Log.d(DataManager.TAG, "sendLoginAndEnv:" + sb.toString());
                }
                try {
                    String cacheData = (z ? StatisticsUrlHelper.getStatisticsLoginUrl() : StatisticsUrlHelper.getStatisticsEnvUrl()) + sb.toString();
                    HttpEngine.getInstance().doHttpGet(context2, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendLoginAndEnv Exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    public void sendErrorInfo(Context context, String p1, String p2, String error, String src, String ep, String cid, String pid, String vid, String zid, String ip, String et, String uuid) {
        final String str = p1;
        final String str2 = p2;
        final String str3 = et;
        final String str4 = error;
        final Context context2 = context;
        final String str5 = ip;
        final String str6 = src;
        final String str7 = ep;
        final String str8 = cid;
        final String str9 = pid;
        final String str10 = vid;
        final String str11 = zid;
        final String str12 = uuid;
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                sb.append("ver=3.0&");
                sb.append("p1=" + DataUtils.getData(DataUtils.getTrimData(str)) + "&");
                sb.append("p2=" + DataUtils.getData(DataUtils.getTrimData(str + str2)) + "&");
                sb.append("p3=001&");
                if (!TextUtils.isEmpty(str3)) {
                    sb.append("et=" + DataUtils.getData(str3) + "&");
                }
                sb.append("err=" + DataUtils.getData(DataUtils.getTrimData(str4)) + "&");
                sb.append("lc=-&");
                sb.append("auid=" + DataUtils.getData(DataUtils.generateDeviceId(context2)) + "&");
                sb.append("ip=" + DataUtils.getData(DataUtils.getTrimData(str5)) + "&");
                sb.append("nt=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getNetType(context2))) + "&");
                sb.append("os=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getSystemName())) + "&");
                sb.append("osv=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getOSVersionName())) + "&");
                sb.append("app=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getClientVersionName(context2))) + "&");
                sb.append("bd=" + URLEncoder.encode(DataUtils.getData(DataUtils.getTrimData(DataUtils.getBrandName()))) + "&");
                sb.append("xh=phone&");
                sb.append("model=" + DataUtils.getData(DataUtils.getTrimData(DataUtils.getDeviceName())) + "&");
                sb.append("ro=" + DataUtils.getNewResolution(context2) + "&");
                sb.append("br=other&");
                sb.append("src=" + DataUtils.getData(DataUtils.getTrimData(str6)) + "&");
                String eps = "";
                if (TextUtils.isEmpty(str7)) {
                    eps = "-&time=" + DataUtils.timeClockString("yyyyMMdd_hh:mm:ss");
                } else {
                    eps = str7 + "&time=" + DataUtils.timeClockString("yyyyMMdd_hh:mm:ss");
                }
                sb.append("ep=" + URLEncoder.encode(DataUtils.getData(DataUtils.getTrimData(eps + "&bsi_msg=" + DataManager.this.getBsiMsg(context2)))) + "&");
                sb.append(StaticticsName.STATICTICS_NAM_CID + DataManager.this.replaceStr(DataUtils.getData(str8)) + "&");
                sb.append("pid=" + DataManager.this.replaceStr(DataUtils.getData(str9)) + "&");
                sb.append("vid=" + DataManager.this.replaceStr(DataUtils.getData(str10)) + "&");
                sb.append("zid=" + DataManager.this.replaceStr(DataUtils.getData(DataUtils.getTrimData(str11))) + "&");
                String apprunid = DataUtils.getData(DataManager.this.getUuid(context2, false));
                if (!TextUtils.equals(str12, DataManager.NOT_UPLOAD)) {
                    sb.append("uuid=" + (TextUtils.isEmpty(str12) ? apprunid : str12));
                }
                sb.append("&r=" + System.currentTimeMillis());
                sb.append("&apprunid=" + apprunid);
                sb.append("&mac=-");
                sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context2)));
                sb.append("&im=" + DataUtils.getEncryptIMEI(context2, StatisticsConstant.DES_KEY));
                sb.append("&imsi=" + DataUtils.getEncryptIMSI(context2, StatisticsConstant.DES_KEY));
                if (DataStatistics.getInstance().isDebug()) {
                    Log.d(DataManager.TAG, "sendErrorInfo:" + sb.toString());
                }
                try {
                    String cacheData = StatisticsUrlHelper.getStatisticsErrorUrl() + sb.toString();
                    HttpEngine.getInstance().doHttpGet(context2, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendErrorInfo Exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    public void submitErrorInfo(final Context context, final StatisCacheBean mStatisCacheBean) {
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                try {
                    HttpEngine.getInstance().doHttpGet(context, mStatisCacheBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "submitErrorInfo Exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    public void sendQueryInfo(Context context, String p1, String p2, String sid, String ty, String pos, String pid, String vid, String cid, String uid, int ilu, String query, String page, String rt, String zid) {
        final String str = p1;
        final String str2 = p2;
        final String str3 = sid;
        final String str4 = ty;
        final String str5 = pos;
        final String str6 = pid;
        final String str7 = vid;
        final String str8 = cid;
        final String str9 = uid;
        final Context context2 = context;
        final int i = ilu;
        final String str10 = query;
        final String str11 = page;
        final String str12 = rt;
        final String str13 = zid;
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                int i = 0;
                StringBuffer sb = new StringBuffer();
                sb.append("ver=3.0&");
                sb.append("p1=" + DataUtils.getData(DataUtils.getTrimData(str)) + "&");
                sb.append("p2=" + DataUtils.getData(DataUtils.getTrimData(str + str2)) + "&");
                sb.append("sid=" + DataUtils.getData(str3) + "&");
                sb.append("ty=" + DataUtils.getData(str4) + "&");
                sb.append("pos" + DataUtils.getData(str5) + "&");
                sb.append("clk=" + DataUtils.getData(str6) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + DataUtils.getData(str7) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + DataUtils.getData(str8) + "&");
                sb.append("uid=" + DataUtils.getData(str9) + "&");
                String uuid = DataUtils.getData(DataManager.this.getUuid(context2, false));
                sb.append("uuid=" + uuid + "&");
                sb.append("lc=-&");
                sb.append("auid=" + DataUtils.getData(DataUtils.generateDeviceId(context2)) + "&");
                sb.append("ch=-&");
                StringBuilder append = new StringBuilder().append("ilu=");
                if (i > 0) {
                    i = 1;
                }
                sb.append(append.append(i).append("&").toString());
                sb.append("q=" + URLEncoder.encode(DataUtils.getData(str10)) + "&");
                sb.append("p=" + DataUtils.getData(str11) + "&");
                sb.append("rt=" + DataUtils.getData(str12) + "&");
                if (!DataManager.NOT_UPLOAD.equals(str13)) {
                    sb.append("zid=" + DataManager.this.replaceStr(DataUtils.getData(DataUtils.getTrimData(str13))) + "&");
                }
                sb.append("r=" + System.currentTimeMillis());
                sb.append("&apprunid=" + uuid);
                sb.append("&mac=-");
                sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context2)));
                sb.append("&im=" + DataUtils.getEncryptIMEI(context2, StatisticsConstant.DES_KEY));
                sb.append("&imsi=" + DataUtils.getEncryptIMSI(context2, StatisticsConstant.DES_KEY));
                if (DataStatistics.getInstance().isDebug()) {
                    Log.d(DataManager.TAG, "sendQueryInfo:" + sb.toString());
                }
                try {
                    String cacheData = StatisticsUrlHelper.getStatisticsQueryUrl() + sb.toString();
                    HttpEngine.getInstance().doHttpGet(context2, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendQueryInfo Exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    public void sendPVInfo(Context context, String p1, String p2, String pcode, String cid, String pid, String vid, String uid, String ref, String ct, String rcid, String kw, String cur_url, String area, String weid, int ilu, String zid) {
        final String str = p1;
        final String str2 = p2;
        final String str3 = cid;
        final String str4 = pid;
        final String str5 = vid;
        final String str6 = uid;
        final Context context2 = context;
        final String str7 = ref;
        final String str8 = ct;
        final String str9 = rcid;
        final String str10 = kw;
        final String str11 = cur_url;
        final String str12 = area;
        final String str13 = pcode;
        final int i = ilu;
        final String str14 = weid;
        final String str15 = zid;
        ThreadPoolManager.getInstance().executeThreadWithPool(new Runnable() {
            public void run() {
                int i = 0;
                StringBuffer sb = new StringBuffer();
                sb.append("ver=3.0&");
                sb.append("p1=" + DataUtils.getData(DataUtils.getTrimData(str)) + "&");
                sb.append("p2=" + DataUtils.getData(DataUtils.getTrimData(str + str2)) + "&");
                sb.append("p3=001&");
                sb.append(StaticticsName.STATICTICS_NAM_CID + DataManager.this.replaceStr(DataUtils.getData(str3)) + "&");
                sb.append("pid=" + DataManager.this.replaceStr(DataUtils.getData(str4)) + "&");
                sb.append("vid=" + DataManager.this.replaceStr(DataUtils.getData(str5)) + "&");
                sb.append("uid=" + DataUtils.getData(str6) + "&");
                String uuid = DataUtils.getData(DataManager.this.getUuid(context2, false));
                sb.append("uuid=" + uuid + "&");
                sb.append("lc=-&");
                sb.append("ref=" + URLEncoder.encode(DataUtils.getData(str7)) + "&");
                sb.append("ct=" + DataUtils.getData(str8) + "&");
                sb.append("rcid=" + DataUtils.getData(str9) + "&");
                sb.append("kw=" + URLEncoder.encode(DataUtils.getData(str10)) + "&");
                sb.append("cur_url=" + URLEncoder.encode(DataUtils.getData(str11)) + "&");
                sb.append("ch=-&");
                sb.append("area=" + DataUtils.getData(str12) + "&");
                sb.append("pcode=" + DataUtils.getData(str13) + "&");
                sb.append("auid=" + DataUtils.getData(DataUtils.generateDeviceId(context2)) + "&");
                StringBuilder append = new StringBuilder().append("ilu=");
                if (i > 0) {
                    i = 1;
                }
                sb.append(append.append(i).append("&").toString());
                sb.append("weid=" + DataUtils.getData(str14) + "&");
                if (!DataManager.NOT_UPLOAD.equals(str15)) {
                    sb.append("zid=" + DataManager.this.replaceStr(DataUtils.getData(DataUtils.getTrimData(str15))) + "&");
                }
                sb.append("r=" + System.currentTimeMillis());
                sb.append("&apprunid=" + uuid);
                sb.append("&mac=-");
                sb.append("&wmac=" + DataUtils.getData(DataUtils.getNoColonMacAddress(context2)));
                sb.append("&im=" + DataUtils.getEncryptIMEI(context2, StatisticsConstant.DES_KEY));
                sb.append("&imsi=" + DataUtils.getEncryptIMSI(context2, StatisticsConstant.DES_KEY));
                if (DataStatistics.getInstance().isDebug()) {
                    Log.d(DataManager.TAG, "sendPVInfo:" + sb.toString());
                }
                try {
                    String cacheData = StatisticsUrlHelper.getStatisticsPVUrl() + sb.toString();
                    HttpEngine.getInstance().doHttpGet(context2, new StatisCacheBean(cacheData, cacheData, System.currentTimeMillis()));
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DataStatistics.getInstance().isDebug()) {
                        Log.d(DataManager.TAG, "sendPVInfo Exception:" + e.getMessage());
                    }
                }
            }
        });
    }

    private String getBsiMsg(Context context) {
        return StatisticsHelperNative.getInstance().encrypt_deviceinfo();
    }

    private String replaceStr(String str) {
        String value = NetworkUtils.DELIMITER_LINE;
        if (str == null || str.equals("") || str.equals("0") || str.equals("-1")) {
            return value;
        }
        return str;
    }

    private String getUuid(Context context, boolean isGetLocal) {
        String uuid = DataStatistics.getUuid(context, isGetLocal);
        if (!TextUtils.isEmpty(uuid)) {
            return uuid;
        }
        uuid = DataUtils.getUUID(context);
        DataStatistics.setUuid(context, uuid);
        return uuid;
    }
}
