package com.letv.datastatistics;

import android.content.Context;
import android.text.TextUtils;
import com.letv.datastatistics.bean.ActionStatisticsData;
import com.letv.datastatistics.bean.StatisCacheBean;
import com.letv.datastatistics.bean.StatisticsPlayInfo;
import com.letv.datastatistics.db.StatisDBHandler;
import com.letv.datastatistics.http.HttpEngine;
import com.letv.datastatistics.http.ThreadPoolManager;
import com.letv.datastatistics.util.DataUtils;
import com.letv.datastatistics.util.StatisticsUrlHelper;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataStatistics {
    public static final String TAG = "DataStatistics";
    private static DataStatistics mInstance = null;
    private static final Object mInstanceSync = new Object();
    private static String mUuid = "";
    private String ip;
    private boolean isUseTest;
    private DataManager mDataManager;

    private DataStatistics() {
        this.isUseTest = false;
        this.mDataManager = null;
        this.ip = "";
        this.mDataManager = new DataManager();
    }

    public static DataStatistics getInstance() {
        synchronized (mInstanceSync) {
            if (mInstance != null) {
                DataStatistics dataStatistics = mInstance;
                return dataStatistics;
            }
            mInstance = new DataStatistics();
            return mInstance;
        }
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isDebug() {
        return this.isUseTest;
    }

    public void initStatisticsInfo(boolean isTest, String dir) {
        this.isUseTest = isTest;
        StatisticsUrlHelper.isTest = isTest;
        if (!TextUtils.isEmpty(dir)) {
            StatisticsUrlHelper.mDir = dir;
        }
    }

    public void initDoubleChannelInfo(boolean isDoubleChannel, String originPcode, String version, String activeState) {
        DataManager.isDoubleChannel = isDoubleChannel;
        DataManager.originalPcode = originPcode;
        DataManager.originalVersion = version;
        DataManager.activeState = activeState;
    }

    public static String getUuid(Context context, boolean isGetLocal) {
        return isGetLocal ? DataUtils.getLocalUuid(context) : mUuid;
    }

    public static void setUuid(Context context, String uuid) {
        mUuid = uuid;
        DataUtils.setUuidToLocal(context, uuid);
    }

    public void sendPlayInfo(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, -10000, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPlayInfo24New(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String zid, StatisticsPlayInfo info) {
        sendPlayInfo24New(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, zid, DataManager.NOT_UPLOAD_INT, info);
    }

    public void sendPlayInfo24New(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String zid, int ap, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, ap, zid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info.getcTime(), DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPlayInfoInit(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String zid, String cdev, String caid, StatisticsPlayInfo info) {
        sendPlayInfoInit(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, zid, DataManager.NOT_UPLOAD_INT, cdev, caid, info);
    }

    public void sendPlayInfoPlay(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String zid, String atc, String stc, int prl, StatisticsPlayInfo info) {
        sendPlayInfoPlay(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, zid, DataManager.NOT_UPLOAD_INT, atc, stc, prl, info);
    }

    public void sendPlayInfoPlay(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String zid, int ap, String atc, String stc, int prl, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, ap, zid, DataManager.NOT_UPLOAD, atc, stc, prl, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info.getcTime(), info.getPay(), info.getJoint(), info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPlayInfoInit(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String zid, int ap, String cdev, String caid, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, ap, zid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, cdev, caid, info.getcTime(), DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPlayInfoDelCh(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String lc, String zid, String lid, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, zid, lid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info.getcTime(), DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPlayInfoDelChPlay(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String lc, String zid, String lid, String atc, String stc, int prl, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, zid, lid, atc, stc, prl, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info.getcTime(), info.getPay(), info.getJoint(), info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPlayInfoDelChInit(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String lc, String zid, String lid, String cdev, String caid, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, zid, lid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, cdev, caid, info.getcTime(), DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendLivePlayInfo(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String lc, StatisticsPlayInfo info) {
        sendLivePlayInfo25New(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, lc, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info);
    }

    public void sendLivePlayInfo25New(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String lc, String zid, StatisticsPlayInfo info) {
        sendLivePlayInfo25New(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, lc, zid, DataManager.NOT_UPLOAD, info);
    }

    public void sendLivePlayInfo25New(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String lc, String zid, String lid, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, DataManager.NOT_UPLOAD_INT, zid, lid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info.getcTime(), DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendLivePlayInfo25NewDeCH(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String lc, String zid, String lid, StatisticsPlayInfo info) {
        sendLivePlayInfo25New(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, DataManager.NOT_UPLOAD, lc, zid, lid, info);
    }

    public void sendLivePlayInfo25NewPlay(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String lc, String zid, String lid, String atc, String stc, int prl, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, DataManager.NOT_UPLOAD_INT, zid, lid, atc, stc, prl, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, info.getcTime(), info.getPay(), info.getJoint(), info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendLivePlayInfo25NewInit(Context context, String p1, String p2, String ac, String err, String pt, String ut, String uid, String uuidTimeStamp, String cid, String pid, String vid, String vlen, String retryCount, String type, String vt, String url, String ref, String py, String st, String weid, String pcode, int ilu, String ch, String lc, String zid, String lid, String cdev, String caid, StatisticsPlayInfo info) {
        this.mDataManager.sendPlayInfo(context, p1, p2, ac, err, pt, ut, uid, uuidTimeStamp, cid, pid, vid, vlen, retryCount, type, vt, url, ref, py, st, weid, pcode, ilu, ch, DataManager.NOT_UPLOAD_INT, zid, lid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD_INT, cdev, caid, info.getcTime(), DataManager.NOT_UPLOAD_INT, DataManager.NOT_UPLOAD_INT, info.getIpt(), DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu) {
        sendActionInfo(context, p1, p2, pcode, acode, ap, ar, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, DataManager.NOT_UPLOAD);
    }

    public void sendActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String zid) {
        this.mDataManager.sendActionInfo(context, p1, p2, pcode, acode, ap, area, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, zid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String zid, String lid) {
        this.mDataManager.sendActionInfo(context, p1, p2, pcode, acode, ap, area, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, zid, lid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String zid, String lid, String targeturl) {
        this.mDataManager.sendActionInfo(context, p1, p2, pcode, acode, ap, ar, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, zid, lid, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, targeturl);
    }

    public void sendActionInfotime(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String zid, String time) {
        this.mDataManager.sendActionInfo(context, p1, p2, pcode, acode, ap, area, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, zid, DataManager.NOT_UPLOAD, time, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendActionInfoBigData(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String reid, String area, String bucket, String rank, String cms_num, int ilu) {
        this.mDataManager.sendActionInfo(context, p1, p2, pcode, acode, ap, area, cid, pid, vid, uid, DataManager.NOT_UPLOAD, area, bucket, rank, ilu, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD, reid, cms_num, DataManager.NOT_UPLOAD);
    }

    public void sendPushActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu) {
        sendPushActionInfo(context, p1, p2, pcode, acode, ap, ar, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, DataManager.NOT_UPLOAD, DataManager.NOT_UPLOAD);
    }

    public void sendPushActionInfo(Context context, String p1, String p2, String pcode, String acode, String ap, String ar, String cid, String pid, String vid, String uid, String cur_url, String area, String bucket, String rank, int ilu, String lid, String zid) {
        this.mDataManager.sendPushActionInfo(context, p1, p2, pcode, acode, ap, ar, cid, pid, vid, uid, cur_url, area, bucket, rank, ilu, lid, zid);
    }

    public void sendPVInfo(Context context, String p1, String p2, String pcode, String cid, String pid, String vid, String uid, String ref, String ct, String rcid, String kw, String cur_url, String area, String weid, int ilu) {
        sendPVInfo(context, p1, p2, pcode, cid, pid, vid, uid, ref, ct, rcid, kw, cur_url, area, weid, ilu, DataManager.NOT_UPLOAD);
    }

    public void sendPVInfo(Context context, String p1, String p2, String pcode, String cid, String pid, String vid, String uid, String ref, String ct, String rcid, String kw, String cur_url, String area, String weid, int ilu, String zid) {
        this.mDataManager.sendPVInfo(context, p1, p2, pcode, cid, pid, vid, uid, ref, ct, rcid, kw, cur_url, area, weid, ilu, zid);
    }

    public void sendLoginAndEnvInfo(Context context, String uid, String lp, String ref, String ts, String pcode, int st, String zid, String xh, String lo, String la, String location, boolean isLogin) {
        this.mDataManager.sendLoginAndEnv(context, uid, lp, ref, ts, pcode, st, zid, xh, lo, la, location, isLogin);
    }

    public void sendQueryInfo(Context context, String p1, String p2, String sid, String ty, String pos, String pid, String vid, String cid, String uid, int ilu, String query, String page, String rt) {
        sendQueryInfo(context, p1, p2, sid, ty, pos, pid, vid, cid, uid, ilu, query, page, rt, DataManager.NOT_UPLOAD);
    }

    public void sendQueryInfo(Context context, String p1, String p2, String sid, String ty, String pos, String pid, String vid, String cid, String uid, int ilu, String query, String page, String rt, String zid) {
        this.mDataManager.sendQueryInfo(context, p1, p2, sid, ty, pos, pid, vid, cid, uid, ilu, query, page, rt, zid);
    }

    public void sendErrorInfo(Context context, String p1, String p2, String error, String src, String ep, String cid, String pid, String vid, String zid, String et, String uuid) {
        this.mDataManager.sendErrorInfo(context, p1, p2, error, src, ep, cid, pid, vid, zid, this.ip, et, uuid);
    }

    public void sendErrorInfo(Context context, String p1, String p2, String error, String src, String ep, String cid, String pid, String vid, String zid) {
        sendErrorInfo(context, p1, p2, error, src, ep, cid, pid, vid, zid, null, DataManager.NOT_UPLOAD);
    }

    public void submitErrorInfo(Context context) {
        ArrayList<StatisCacheBean> allCacheTrace = StatisDBHandler.getAllCacheTrace(context);
        if (allCacheTrace != null && allCacheTrace.size() > 0) {
            Iterator it = allCacheTrace.iterator();
            while (it.hasNext()) {
                this.mDataManager.submitErrorInfo(context, (StatisCacheBean) it.next());
            }
        }
    }

    public void close() {
        HttpEngine.getInstance().closeHttpEngine();
        ThreadPoolManager.getInstance().closeThreadPool();
    }

    public void sendActionInfo(Context context, ActionStatisticsData data) {
        this.mDataManager.sendActionInfo(context, data);
    }
}
