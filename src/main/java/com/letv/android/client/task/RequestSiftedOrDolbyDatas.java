package com.letv.android.client.task;

import android.content.Context;
import android.text.TextUtils;
import com.letv.android.client.parser.AlbumNewListParser;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.channel.AlbumNewList;
import com.letv.core.constant.ChannelConstant;
import com.letv.core.constant.LetvConstant;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.utils.BaseTypeUtils;
import com.media.ffmpeg.FFMpegPlayer;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import java.util.Iterator;

public class RequestSiftedOrDolbyDatas {
    private final String TAG = RequestSiftedOrDolbyDatas.class.getName();
    private String cacheFilterId;
    private boolean isDolby = false;
    private boolean isLoadMore = false;
    private boolean isOpposite = false;
    private RequestSiftedOrDolbyDatasCallBack mCallBack;
    private boolean mCanCallBack;
    private Channel mChannel;
    private ArrayList<SiftKVP> mCurrentSiftKVPs = new ArrayList();
    private String mStringSift;
    private String markId = "";
    private int maxCount = 0;
    public int num = 30;
    private String ph;
    private String pt;
    private int startPos = 0;

    public interface RequestSiftedOrDolbyDatasCallBack {
        void showErrorPage(boolean z, boolean z2);

        void updateUI(AlbumNewList albumNewList, boolean z);
    }

    public RequestSiftedOrDolbyDatas(Context context, Channel channel) {
        boolean z = false;
        this.mChannel = channel;
        if (this.mChannel.id == 1001) {
            z = true;
        }
        this.isDolby = z;
    }

    public void setRequestCallBack(RequestSiftedOrDolbyDatasCallBack callBack) {
        this.mCallBack = callBack;
    }

    public void setSiftKvps(ArrayList<SiftKVP> siftKVPs) {
        if (!BaseTypeUtils.isListEmpty(siftKVPs)) {
            this.mCurrentSiftKVPs.clear();
            this.mCurrentSiftKVPs.addAll(siftKVPs);
        }
        this.startPos = 0;
    }

    public void setStartPosition(int position) {
        this.startPos = position;
        this.isLoadMore = this.startPos >= this.num;
    }

    public int getCurrentMaxCount() {
        return this.maxCount;
    }

    public boolean isFirstGet() {
        return this.startPos == 0;
    }

    public boolean isDolby() {
        return this.isDolby;
    }

    public boolean isLoadingMore() {
        return this.isLoadMore;
    }

    public void getChannelListAfterSift(boolean isLoadingMore) {
        if (!isLoadingMore) {
            this.startPos = 0;
        }
        this.mCanCallBack = true;
        this.isOpposite = false;
        this.pt = "";
        String url = getChannelListAfterSiftUrl();
        int from = this.isDolby ? 272 : 258;
        if (getRealChannelType() == 2) {
            from = this.isDolby ? 273 : FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR;
        }
        this.cacheFilterId = this.isDolby ? "dolby_" + this.startPos + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.num : getFilterIdStr();
        Volley.getQueue().cancelWithTag(ChannelConstant.REQUEST_CHANNEL_DETAIL_AFTER_SIFT);
        getVolleyRequest(isLoadingMore, from, new 1(this, isLoadingMore, url)).add();
    }

    private VolleyRequest getVolleyRequest(boolean isLoadingMore, int from, SimpleResponse<AlbumNewList> simpleResponse) {
        Volley.getQueue().cancelWithTag(this.TAG);
        VolleyRequest request = new LetvRequest(AlbumNewList.class).setAlwaysCallbackNetworkResponse(true).setRequestType(isLoadingMore ? RequestManner.NETWORK_ONLY : RequestManner.CACHE_THEN_NETROWK).setCache(new VolleyDiskCache(getCacheFileName())).setParser(new AlbumNewListParser(from)).setCallback(simpleResponse).setTag(this.TAG).setCacheValidateListener(new 2(this, isLoadingMore));
        if (isLoadingMore) {
            request.setUrl(getChannelListAfterSiftUrl());
        }
        return request;
    }

    private String getChannelListAfterSiftUrl() {
        if (!BaseTypeUtils.isListEmpty(this.mCurrentSiftKVPs)) {
            createFilterNameValuePair(this.mCurrentSiftKVPs);
        }
        if (this.mChannel.id == 1000) {
            this.ph = LetvConstant.MOBILE_SYSTEM_PHONE;
            this.pt = LetvConstant.MOBILE_SYSTEM_PHONE_PAY;
        } else {
            this.ph = "420003,420004";
        }
        return MediaAssetApi.getInstance().getChannelListAfterSiftUrl(0, getRealChannelType() != 2, "1", this.mChannel.id + "", this.ph, this.pt, ((this.startPos / this.num) + 1) + "", this.num + "", this.mStringSift, this.markId);
    }

    private String getFilterIdStr() {
        if (BaseTypeUtils.isListEmpty(this.mCurrentSiftKVPs)) {
            return "&nothing";
        }
        StringBuffer sb = new StringBuffer();
        Iterator it = this.mCurrentSiftKVPs.iterator();
        while (it.hasNext()) {
            sb.append(((SiftKVP) it.next()).filterKey);
        }
        return sb.toString();
    }

    private int getRealChannelType() {
        int type;
        switch (this.mChannel.type) {
            case 1:
                if (this.isOpposite) {
                    type = 2;
                } else {
                    type = 1;
                }
                return type;
            case 2:
                if (this.isOpposite) {
                    type = 1;
                } else {
                    type = 2;
                }
                return type;
            default:
                return 2;
        }
    }

    private String getCacheFileName() {
        return "channel_detail_" + this.mChannel.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mChannel.pageid + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.cacheFilterId;
    }

    private void createFilterNameValuePair(ArrayList<SiftKVP> siftKVPs) {
        this.mStringSift = "";
        StringBuffer mStringBuffer = new StringBuffer();
        if (siftKVPs != null && siftKVPs.size() > 0) {
            mStringBuffer.append("/");
            Iterator it = siftKVPs.iterator();
            while (it.hasNext()) {
                SiftKVP siftKVP = (SiftKVP) it.next();
                if (!TextUtils.isEmpty(siftKVP.opposite) && TextUtils.equals(siftKVP.opposite, "1")) {
                    this.isOpposite = true;
                }
                if (TextUtils.equals(siftKVP.filterKey, "ispay/1")) {
                    this.pt = LetvConstant.MOBILE_SYSTEM_PHONE_PAY;
                }
                if (!(TextUtils.isEmpty(siftKVP.filterKey) || TextUtils.equals(siftKVP.filterKey, "null"))) {
                    mStringBuffer.append(siftKVP.filterKey).append("/");
                }
            }
        }
        this.mStringSift = mStringBuffer.toString();
        if (this.mStringSift.endsWith("/")) {
            this.mStringSift = this.mStringSift.substring(0, this.mStringSift.length() - 1);
        }
    }

    public void clear() {
        if (this.mCurrentSiftKVPs != null) {
            this.mCurrentSiftKVPs.clear();
        }
        Volley.getQueue().cancelAll(new 3(this));
        this.cacheFilterId = "";
        this.isLoadMore = false;
        this.startPos = 0;
    }
}
