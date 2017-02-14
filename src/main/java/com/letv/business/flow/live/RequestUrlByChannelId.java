package com.letv.business.flow.live;

import android.content.Context;
import android.text.TextUtils;
import com.letv.business.flow.live.LiveFlowCallback.RequestUrlByChannelIdCallback;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LiveApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LiveUrlInfo;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LiveUrlParser;
import com.letv.core.utils.StringUtils;
import com.letv.datastatistics.DataStatistics;

public class RequestUrlByChannelId {
    private RequestUrlByChannelIdCallback mCallbck;
    private String mChannelId;
    private Context mContext;
    private boolean mIsPay;
    private boolean mIsToPlay;
    private String mLiveId;
    private String mUUid;
    private String mUrl;
    private String st;

    public RequestUrlByChannelId(Context context, String channelId, boolean isPay, boolean isToPlay, String uuid, String st, String liveId, RequestUrlByChannelIdCallback callback) {
        this.mContext = context;
        this.mIsPay = isPay;
        this.mIsToPlay = isToPlay;
        this.mChannelId = channelId;
        this.mUUid = uuid;
        this.st = st;
        this.mLiveId = liveId;
        this.mCallbck = callback;
        this.mUrl = LiveApi.getInstance().getLiveUrlByChannelId(channelId, isPay);
        PlayLiveFlow.LogAddInfo("请求直播码流", "mUrl=" + this.mUrl);
    }

    public void start() {
        Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_URL_BY_CHANNELID);
        new LetvRequest().setUrl(this.mUrl).setTag(BasePlayLiveFlow.REQUEST_URL_BY_CHANNELID).setParser(new LiveUrlParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveUrlInfo>() {
            public void onNetworkResponse(VolleyRequest<LiveUrlInfo> volleyRequest, LiveUrlInfo result, DataHull hull, NetworkResponseState state) {
                PlayLiveFlow.LogAddInfo("请求直播码流，返回结果", "mIsPay=" + RequestUrlByChannelId.this.mIsPay + ",state=" + state);
                if (RequestUrlByChannelId.this.mCallbck != null) {
                    RequestUrlByChannelId.this.addUnlink(result, RequestUrlByChannelId.this.mIsPay);
                    RequestUrlByChannelId.this.mCallbck.onNetworkResponse(state, result, RequestUrlByChannelId.this.mIsPay, RequestUrlByChannelId.this.mIsToPlay);
                }
            }

            public void onErrorReport(VolleyRequest<LiveUrlInfo> volleyRequest, String errorInfo) {
                PlayLiveFlow.LogAddInfo("请求直播码流，请求错误", "errorInfo=" + errorInfo);
                String str = errorInfo;
                String str2 = null;
                String str3 = null;
                String str4 = null;
                String str5 = null;
                DataStatistics.getInstance().sendErrorInfo(RequestUrlByChannelId.this.mContext, "0", "0", "1403", null, str, str2, str3, str4, str5, "pl", RequestUrlByChannelId.this.mUUid);
            }
        }).add();
    }

    private LiveUrlInfo addUnlink(LiveUrlInfo info, boolean p) {
        PlayLiveFlow.LogAddInfo("请求直播码流，添加防盗链", "mChannelId=" + this.mChannelId + ",info=" + info);
        if (info == null) {
            return null;
        }
        String liveId = this.mChannelId;
        if (!TextUtils.isEmpty(this.mLiveId)) {
            liveId = this.mLiveId;
        }
        if (!TextUtils.isEmpty(info.liveUrl_350)) {
            info.liveUrl_350 = StringUtils.addUnlinkParams(info.liveUrl_350, p, liveId, this.mUUid, this.st);
        }
        if (!TextUtils.isEmpty(info.liveUrl_720p)) {
            info.liveUrl_720p = StringUtils.addUnlinkParams(info.liveUrl_720p, p, liveId, this.mUUid, this.st);
        }
        if (!TextUtils.isEmpty(info.liveUrl_1000)) {
            info.liveUrl_1000 = StringUtils.addUnlinkParams(info.liveUrl_1000, p, liveId, this.mUUid, this.st);
        }
        if (TextUtils.isEmpty(info.liveUrl_1300)) {
            return info;
        }
        info.liveUrl_1300 = StringUtils.addUnlinkParams(info.liveUrl_1300, p, liveId, this.mUUid, this.st);
        return info;
    }
}
