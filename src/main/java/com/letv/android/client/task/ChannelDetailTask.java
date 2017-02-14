package com.letv.android.client.task;

import android.content.Context;
import com.letv.android.client.listener.ChannelDetailCallback;
import com.letv.android.client.parser.ChannelFilterTypesParse;
import com.letv.android.client.parser.ChannelHomeBeanParser;
import com.letv.android.client.parser.ChannelLiveSportParse;
import com.letv.android.client.parser.TopsParser;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.PageCardListBean;
import com.letv.core.bean.channel.ChannelFilterTypes;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.bean.channel.TopList;
import com.letv.core.constant.ChannelConstant;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.StringUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class ChannelDetailTask {
    private String TAG;
    private String mArea;
    private int mChanneId;
    private ChannelDetailCallback mChannelcallBack;
    private Context mContext;
    private int mDataSize;
    private String mPageId;
    private String mType;

    public ChannelDetailTask(Context context, int chanelId, String pageId) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.TAG = "channelDetailItem";
        this.mContext = context;
        this.mChanneId = chanelId;
        this.mPageId = pageId;
        this.TAG += this.mChanneId;
    }

    public void initCallBack(ChannelDetailCallback callback) {
        this.mChannelcallBack = callback;
    }

    public void reSetPageId(String pageId) {
        this.mPageId = pageId;
    }

    public void requestChannelDetailList(boolean isReference, boolean isLoadingMore, int pageNum, PageCardListBean pageCardList) {
        if (this.mChannelcallBack != null) {
            Volley.getQueue().cancelWithTag(getRequestTagPrefix() + ChannelConstant.REQUEST_CHANNEL_DETAIL);
            getVolleyRequest(isLoadingMore, pageNum, pageCardList, new 1(this, isLoadingMore, isReference)).add();
        }
    }

    private void setLoadMessage(ChannelHomeBean result, boolean isLoadingMore) {
        if (result != null && !BaseTypeUtils.isListEmpty(result.block) && !isLoadingMore && BaseTypeUtils.getElementFromList(result.block, 0) != null && result.block.size() == 1) {
            this.mArea = ((HomeBlock) result.block.get(0)).area;
            this.mType = ((HomeBlock) result.block.get(0)).type;
            this.mDataSize = ((HomeBlock) result.block.get(0)).num;
        }
    }

    public int getFirstPageDataSize() {
        return this.mDataSize == 0 ? 30 : this.mDataSize;
    }

    public void requestLiveList(RequestManner requestType) {
        if (this.mChannelcallBack != null) {
            Volley.getQueue().cancelWithTag(this.TAG + ChannelConstant.REQUEST_CHANNEL_LIVE_LIST);
            new LetvRequest(LiveRemenListBean.class).setRequestType(requestType).setParser(new ChannelLiveSportParse()).setTag(this.TAG + ChannelConstant.REQUEST_CHANNEL_LIVE_LIST).setCache(new VolleyDiskCache("channel_livedata_" + this.mChanneId + StringUtils.getLocalWeekDay())).setUrl(LetvUrlMaker.getLiveDataUrl(true, this.mChanneId)).setCallback(new 2(this, requestType)).add();
        }
    }

    public void requestTopList() {
        if (this.mChannelcallBack != null) {
            Volley.getQueue().cancelWithTag(this.TAG + ChannelConstant.REQUEST_TOP_LIST);
            new LetvRequest(TopList.class).setCache(new VolleyDiskCache(getCacheFileName(false))).setTag(this.TAG + ChannelConstant.REQUEST_TOP_LIST).setCacheValidateListener(new 4(this)).setParser(new TopsParser()).setCallback(new 3(this)).add();
        }
    }

    public void requestSiftList(boolean isFilterButtonSelect) {
        if (this.mChannelcallBack != null) {
            Volley.getQueue().cancelWithTag(this.TAG + ChannelConstant.REQUEST_CHANNEL_SIFT_LIST);
            new LetvRequest(ChannelFilterTypes.class).setCache(new VolleyDiskCache(getCacheFileName(true) + "_sifts")).setTag(this.TAG + ChannelConstant.REQUEST_CHANNEL_SIFT_LIST).setUrl(MediaAssetApi.getInstance().getChannelSiftListUrl()).setCacheValidateListener(new 6(this)).setParser(new ChannelFilterTypesParse()).setCallback(new 5(this, isFilterButtonSelect)).add();
        }
    }

    public void onDestroy() {
        Volley.getQueue().cancelAll(new 7(this));
    }

    private VolleyRequest getVolleyRequest(boolean isLoadingMore, int pageNum, PageCardListBean pageCardList, SimpleResponse<ChannelHomeBean> simpleResponse) {
        VolleyRequest request = new LetvRequest(ChannelHomeBean.class).setCache(new VolleyDiskCache(getCacheFileName(true))).setRequestType(isLoadingMore ? RequestManner.NETWORK_ONLY : RequestManner.CACHE_THEN_NETROWK).setTag(getRequestTagPrefix() + ChannelConstant.REQUEST_CHANNEL_DETAIL).setAlwaysCallbackNetworkResponse(true).setCacheValidateListener(new 8(this)).setParser(new ChannelHomeBeanParser(pageCardList)).setCallback(simpleResponse);
        if (!isLoadingMore) {
            return request;
        }
        return request.setUrl(isLoadingMore ? MediaAssetApi.getInstance().getChannelDetailListUrl(this.mChanneId, 0, "", this.mPageId + "", "", true, this.mArea, String.valueOf(pageNum), this.mType, getFirstPageDataSize() + "") : "");
    }

    private String getRequestTagPrefix() {
        return this.TAG + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mChanneId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mPageId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR;
    }

    private String getCacheFileName(boolean isNotTopList) {
        return isNotTopList ? "channel_detail_" + this.mChanneId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mPageId + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR : "tops_" + this.mChanneId;
    }
}
