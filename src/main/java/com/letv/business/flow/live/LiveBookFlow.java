package com.letv.business.flow.live;

import android.content.Context;
import com.letv.android.client.business.R;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LiveApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveResultInfo;
import com.letv.core.bean.LiveResultParser;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;

public class LiveBookFlow {
    private static final String TAG = "LiveBookFlow";
    private LiveBookCallback mCallback;
    private Context mContext;

    public LiveBookFlow(Context context) {
        this.mContext = context;
    }

    public void setCallback(LiveBookCallback callback) {
        this.mCallback = callback;
    }

    public void cancelBooking(LiveRemenBaseBean bean) {
        String playTime = bean.getFullPlayDate() + " " + bean.getPlayTime();
        String pName = bean.title;
        String channelCode = bean.liveType;
        cancelBooking(playTime, pName, channelCode, bean.channelName, playTime + "|" + channelCode + "|" + pName);
    }

    public void cancelBooking(String playTime, String pName, String channelCode, String channelName, String id) {
        final String str = playTime;
        final String str2 = pName;
        final String str3 = channelCode;
        final String str4 = channelName;
        final String str5 = id;
        new LetvRequest(LiveResultInfo.class).setUrl(LiveApi.getDelBookLive(LetvUtils.generateDeviceId(this.mContext), playTime + "|" + channelCode + "|" + pName)).setParser(new LiveResultParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveResultInfo>() {
            public void onNetworkResponse(VolleyRequest<LiveResultInfo> volleyRequest, LiveResultInfo result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS && result != null && "1".equals(result.result)) {
                    LogInfo.log(LiveBookFlow.TAG, "onResponse: " + result);
                    if (LiveBookFlow.this.mCallback != null) {
                        LiveBookFlow.this.mCallback.onBooked(str, str2, str3, str4, str5, false, true);
                        return;
                    }
                    return;
                }
                if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE || state == NetworkResponseState.NETWORK_ERROR) {
                    ToastUtils.showToast(LiveBookFlow.this.mContext, R.string.network_cannot_use_try_later);
                }
                if (LiveBookFlow.this.mCallback != null) {
                    LiveBookFlow.this.mCallback.onBooked(str, str2, str3, str4, str5, false, false);
                }
                LogInfo.log(LiveBookFlow.TAG, "Request from network LiveRemenListBean failed: " + state);
            }

            public void onCacheResponse(VolleyRequest<LiveResultInfo> volleyRequest, LiveResultInfo result, DataHull hull, CacheResponseState state) {
                if (state != CacheResponseState.SUCCESS) {
                }
            }

            public void onErrorReport(VolleyRequest<LiveResultInfo> volleyRequest, String errorInfo) {
            }
        }).add();
    }

    public void book(LiveRemenBaseBean bean) {
        book(bean.getFullPlayDate() + " " + bean.getPlayTime(), bean.title, bean.liveType, bean.channelName, bean.id);
    }

    public void book(String playTime, String pName, String channelCode, String channelName, String id) {
        String url = LiveApi.requestAddBookLive(0, LetvUtils.generateDeviceId(this.mContext), playTime, pName, channelCode, channelName, id);
        LogInfo.log(TAG, "request LiveRemenListBean: " + url);
        final String str = playTime;
        final String str2 = pName;
        final String str3 = channelCode;
        final String str4 = channelName;
        final String str5 = id;
        new LetvRequest(LiveResultInfo.class).setUrl(url).setParser(new LiveResultParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<LiveResultInfo>() {
            public void onNetworkResponse(VolleyRequest<LiveResultInfo> volleyRequest, LiveResultInfo result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS && result != null && "1".equals(result.result)) {
                    LogInfo.log(LiveBookFlow.TAG, "onResponse: " + result);
                    if (LiveBookFlow.this.mCallback != null) {
                        LiveBookFlow.this.mCallback.onBooked(str, str2, str3, str4, str5, true, true);
                        return;
                    }
                    return;
                }
                LogInfo.log(LiveBookFlow.TAG, "Request from network LiveRemenListBean failed: " + state);
                if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE || state == NetworkResponseState.NETWORK_ERROR) {
                    ToastUtils.showToast(LiveBookFlow.this.mContext, R.string.network_cannot_use_try_later);
                }
                if (LiveBookFlow.this.mCallback != null) {
                    LiveBookFlow.this.mCallback.onBooked(str, str2, str3, str4, str5, true, false);
                }
            }

            public void onCacheResponse(VolleyRequest<LiveResultInfo> volleyRequest, LiveResultInfo result, DataHull hull, CacheResponseState state) {
                if (state != CacheResponseState.SUCCESS) {
                }
            }

            public void onErrorReport(VolleyRequest<LiveResultInfo> volleyRequest, String errorInfo) {
            }
        }).add();
    }
}
