package com.letv.business.flow.live;

import android.content.Context;
import com.letv.business.flow.live.LiveFlowCallback.AsyncCallback;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LiveApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LiveCanPlay;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.LiveCanPlayParser;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;

public class RequestCanPlay {
    long eTime;
    private AsyncCallback mCallback;
    private Context mContext;
    private String mUrl;
    private String mUuid;
    long sTime = System.currentTimeMillis();

    public RequestCanPlay(Context context, String streamId, String uuid, AsyncCallback callback) {
        this.mContext = context;
        this.mUrl = LiveApi.getInstance().getLiveCanplay(streamId);
        this.mCallback = callback;
        this.mUuid = uuid;
        PlayLiveFlow.LogAddInfo("开始请求是否可以播放", "mUrl=" + this.mUrl);
    }

    public void start() {
        Volley.getQueue().cancelWithTag(BasePlayLiveFlow.REQUEST_CAN_PLAY);
        new LetvRequest().setCache(new VolleyNoCache()).setUrl(this.mUrl).setTag(BasePlayLiveFlow.REQUEST_CAN_PLAY).setParser(new LiveCanPlayParser()).setCallback(new SimpleResponse<LiveCanPlay>() {
            public void onNetworkResponse(VolleyRequest<LiveCanPlay> volleyRequest, LiveCanPlay result, DataHull hull, NetworkResponseState state) {
                if (RequestCanPlay.this.mCallback != null) {
                    RequestCanPlay.this.eTime = System.currentTimeMillis();
                    RequestCanPlay.this.mCallback.onNetworkResponse(state, result, RequestCanPlay.this.eTime - RequestCanPlay.this.sTime);
                }
            }

            public void onErrorReport(VolleyRequest<LiveCanPlay> volleyRequest, String errorInfo) {
                PlayLiveFlow.LogAddInfo("开始请求是否可以播放 请求失败", "errorInfo=" + errorInfo);
                String str = errorInfo;
                String str2 = null;
                String str3 = null;
                String str4 = null;
                String str5 = null;
                DataStatistics.getInstance().sendErrorInfo(RequestCanPlay.this.mContext, "0", "0", LetvErrorCode.REQUEST_CANPLAY_ERROR, null, str, str2, str3, str4, str5, "pl", RequestCanPlay.this.mUuid);
            }
        }).add();
    }
}
