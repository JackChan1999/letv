package com.letv.mobile.async;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.mobile.http.HttpGlobalConfig;
import com.letv.mobile.http.LetvHttpLog;
import com.letv.mobile.http.bean.LetvBaseBean;
import com.letv.mobile.http.bean.LetvDataHull;
import com.letv.mobile.http.builder.LetvHttpBaseUrlBuilder;
import com.letv.mobile.http.cache.CacheThreadPool;
import com.letv.mobile.http.cache.HttpCache;
import com.letv.mobile.http.cache.HttpCacheTask;
import com.letv.mobile.http.client.LetvHttpClient;
import com.letv.mobile.http.parameter.LetvBaseParameter;
import com.letv.mobile.http.utils.ApnChecker;
import com.letv.mobile.http.utils.NetworkUtil;
import com.letv.mobile.http.utils.StringUtils;
import com.media.ffmpeg.FFMpegPlayer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({"NewApi"})
public abstract class LetvHttpAsyncRequest {
    private static final long DEFAULT_CACHE_TIME = 1800000;
    protected final Context context;
    private boolean mAlwaysCache;
    private final String mCachePath;
    private long mCacheTime = DEFAULT_CACHE_TIME;
    private final TaskCallBack mCallBack;
    private boolean mIsNeedCache;
    protected int mRetryCount = 0;

    public abstract LetvHttpBaseUrlBuilder getRequestUrl(LetvBaseParameter letvBaseParameter);

    protected abstract LetvBaseBean parseData(String str) throws Exception;

    public LetvHttpAsyncRequest(Context context, TaskCallBack callback) {
        this.context = context;
        this.mCallBack = callback;
        this.mCachePath = new StringBuilder(String.valueOf(context.getFilesDir().toString())).append("/httpcache/").toString();
        this.mIsNeedCache = HttpGlobalConfig.isNeedCache();
    }

    public final void execute(LetvBaseParameter params) {
        execute(params, false);
    }

    public final void execute(LetvBaseParameter params, boolean isNeedCache) {
        execute(params, isNeedCache, DEFAULT_CACHE_TIME);
    }

    public final void execute(LetvBaseParameter params, boolean isNeedCache, long cacheTime) {
        execute(params, isNeedCache, cacheTime, true, false);
    }

    public final void execute(LetvBaseParameter params, boolean isNeedCache, long cacheTime, boolean isAlwaysCache) {
        execute(params, isNeedCache, cacheTime, true, isAlwaysCache);
    }

    public final void execute(LetvBaseParameter params, boolean isNeedCache, long cacheTime, boolean usingThreadPool, boolean isAlwaysCache) {
        this.mIsNeedCache = isNeedCache;
        this.mAlwaysCache = isAlwaysCache;
        if (cacheTime < 0) {
            this.mCacheTime = DEFAULT_CACHE_TIME;
        } else {
            this.mCacheTime = cacheTime;
        }
        this.mRetryCount = 0;
        if (isSync()) {
            onDataResponse(requestData(params));
        } else if (usingThreadPool) {
            new MyAsyncTask(this).executeOnExecutor(HttpAsyncThreadPool.getThreadPoolInstance(), new LetvBaseParameter[]{params});
        } else {
            Log.i("LetvHttpAsyncRequest", "execute httprequest without usingThreadPool");
            new MyAsyncTask(this).execute(new LetvBaseParameter[]{params});
        }
    }

    protected void onDataResponse(LetvDataHull result) {
        if (this.mCallBack == null) {
            return;
        }
        if (result.dataType != FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR || result.dataEntity == null) {
            if (result.dataType == 258) {
                this.mCallBack.callback(2, "", "", null);
            } else if (result.dataType == 272) {
                this.mCallBack.callback(3, "", "", null);
            } else {
                this.mCallBack.callback(4, result.message, "", null);
            }
        } else if (String.valueOf(1).equals(result.dataEntity.getStatus()) && (result.dataEntity instanceof LetvBaseBean)) {
            this.mCallBack.callback(0, result.dataEntity.getErrorMessage(), result.dataEntity.getErrorCode(), result.dataEntity);
            cacheHttpRequest(result.requestUrl, result.sourceData, result.requestType);
        } else if (String.valueOf(0).equals(result.dataEntity.getStatus())) {
            this.mCallBack.callback(1, result.dataEntity.getErrorMessage(), result.dataEntity.getErrorCode(), result.dataEntity);
        } else {
            this.mCallBack.callback(4, result.dataEntity.getErrorMessage(), result.dataEntity.getErrorCode(), result.dataEntity);
        }
    }

    private LetvDataHull requestData(LetvBaseParameter params) {
        if ((NetworkUtil.getNetworkType() != 0) || this.mAlwaysCache) {
            return requestData(getRequestUrl(params));
        }
        LetvDataHull dataHull = new LetvDataHull();
        dataHull.dataType = 272;
        return dataHull;
    }

    protected LetvDataHull requestData(LetvHttpBaseUrlBuilder httpParameter) {
        LetvDataHull dataHull;
        if (isCacheRequest(httpParameter.type)) {
            dataHull = getDataFromCache(httpParameter);
            if (dataHull != null) {
                return dataHull;
            }
        }
        LetvHttpClient client = new LetvHttpClient();
        client.setRedirectCount(getRedirectCount());
        dataHull = client.requestData(httpParameter, ApnChecker.getProxy(this.context), getReadTimeOut(), getConnectTimeOut(), isSupportGzip(), getHeader(), getCookies());
        if (dataHull.sourceData != null) {
            try {
                dataHull.dataEntity = parseData(dataHull.sourceData);
                dataHull.dataType = FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR;
                if (httpParameter.isChangeDomainRequest()) {
                    onChangeDomainRequestSuccess(httpParameter);
                }
            } catch (Exception e) {
                e.printStackTrace();
                dataHull.dataType = FFMpegPlayer.PREPARE_AUDIO_NODECODER_ERROR;
            }
            if (dataHull.dataEntity == null || !StringUtils.equalsNull(dataHull.dataEntity.getErrorCode())) {
                LetvHttpLog.Log(dataHull.sourceData);
            }
        }
        if (dataHull.dataType == FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR) {
            return dataHull;
        }
        if (httpParameter.isChangeDomainRequest()) {
            onChangeDomainRequestFail(httpParameter);
        }
        return retryRequest(dataHull, client.getResponseHeader(), httpParameter);
    }

    protected LetvDataHull retryRequest(LetvDataHull dataHull, Map<String, List<String>> map, LetvHttpBaseUrlBuilder httpParameter) {
        if (this.mRetryCount >= getTotalRetryCount()) {
            return dataHull;
        }
        this.mRetryCount++;
        return requestData(httpParameter);
    }

    private LetvDataHull getDataFromCache(LetvHttpBaseUrlBuilder httpParameter) {
        try {
            if (httpParameter.type == LiveRoomConstant.EVENT_FLOW_ON_PLAY) {
                return null;
            }
            LetvDataHull dataHull = new LetvDataHull();
            dataHull.requestUrl = httpParameter.buildUrl();
            HttpCache httpCache = new HttpCache(this.mCachePath, getCacheTime());
            httpCache.setAlwaysCache(this.mAlwaysCache);
            if (httpCache.getCacheIsOverdue(dataHull.requestUrl) && !this.mAlwaysCache) {
                return null;
            }
            dataHull.sourceData = httpCache.readCache(dataHull.requestUrl);
            dataHull.dataEntity = parseData(dataHull.sourceData);
            dataHull.dataType = FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR;
            dataHull.dataEntity.setFromCache(true);
            return dataHull;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cacheHttpRequest(String url, String sourceData, int requestType) {
        if (!TextUtils.isEmpty(url) && sourceData != null && isCacheRequest(requestType)) {
            CacheThreadPool.getThreadPoolInstance().execute(new HttpCacheTask(url, sourceData, this.mCachePath));
        }
    }

    private boolean isCacheRequest(int type) {
        return isNeedCache() && type == 8194;
    }

    protected int getTotalRetryCount() {
        return 3;
    }

    protected int getReadTimeOut() {
        return 10000;
    }

    protected int getConnectTimeOut() {
        return 10000;
    }

    protected boolean isSync() {
        return false;
    }

    protected boolean isSupportGzip() {
        return true;
    }

    protected HashMap<String, String> getHeader() {
        return null;
    }

    protected String getCookies() {
        return null;
    }

    protected boolean isNeedCache() {
        return this.mIsNeedCache;
    }

    protected long getCacheTime() {
        return this.mCacheTime;
    }

    protected int getRedirectCount() {
        return 3;
    }

    protected void onChangeDomainRequestSuccess(LetvHttpBaseUrlBuilder params) {
    }

    protected void onChangeDomainRequestFail(LetvHttpBaseUrlBuilder params) {
    }
}
