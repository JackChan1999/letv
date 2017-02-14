package com.letv.mobile.letvhttplib.volley;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.mobile.letvhttplib.HttpLibApp;
import com.letv.mobile.letvhttplib.bean.DataHull;
import com.letv.mobile.letvhttplib.bean.LetvBaseBean;
import com.letv.mobile.letvhttplib.bean.UploadFileBean;
import com.letv.mobile.letvhttplib.db.PreferencesManager;
import com.letv.mobile.letvhttplib.parser.LetvBaseParser;
import com.letv.mobile.letvhttplib.parser.LetvMobileParser;
import com.letv.mobile.letvhttplib.utils.BaseTypeUtils;
import com.letv.mobile.letvhttplib.utils.Logger;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.CacheResponseState;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.NetworkResponseState;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.ResponseSupplier;
import com.letv.mobile.letvhttplib.volley.exception.DataIsErrException;
import com.letv.mobile.letvhttplib.volley.exception.DataIsNullException;
import com.letv.mobile.letvhttplib.volley.exception.DataNoUpdateException;
import com.letv.mobile.letvhttplib.volley.exception.JsonCanNotParseException;
import com.letv.mobile.letvhttplib.volley.exception.ParseException;
import com.letv.mobile.letvhttplib.volley.exception.TokenLoseException;
import com.letv.mobile.letvhttplib.volley.listener.OnEntryResponse;
import com.letv.mobile.letvhttplib.volley.listener.OnPreExecuteListener;
import com.letv.mobile.letvhttplib.volley.listener.VolleyCache;
import com.letv.mobile.letvhttplib.volley.toolbox.ParameterBuilder;
import com.letv.mobile.letvhttplib.volley.toolbox.VolleyDiskCache;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashMap;
import java.util.Map;

public abstract class VolleyRequest<T extends LetvBaseBean> implements Comparable<VolleyRequest<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final int DEFAULT_TIMEOUT = 20000;
    private static final String SSOTK = "SSOTK";
    private static final String TK = "TK";
    private OnPreAddCacheValidateListener mAddCacheValidateListener;
    public long mAddToQueueTime;
    private boolean mAlwaysCallbackNetworkResponse = false;
    public final DataHull mCacheDataHull = new DataHull();
    protected T mCacheEntry;
    private boolean mCacheSuccess = false;
    private boolean mCanceled = false;
    private long mClientConsumeTime;
    public Context mContext = HttpLibApp.getInstance();
    private boolean mEnablePostEmpty = false;
    protected OnEntryResponse<T> mEntryResponse;
    private UploadFileBean[] mFilePostParams;
    public Map<String, String> mHeadMap = new HashMap();
    public HttpRequestMethod mHttpRequestMethod = HttpRequestMethod.AUTO;
    private boolean mNeedCheckToken;
    public final DataHull mNetWorkDataHull = new DataHull();
    protected T mNetworkEntry;
    private OnPreExecuteListener mOnPreExecuteListener;
    protected LetvBaseParser<T, ?> mParser;
    private boolean mPostErrorReport = false;
    private final Map<String, String> mPostParams = new HashMap();
    private RequestPriority mPriority = RequestPriority.NORMAL;
    private long mRequestNetConsumeTime;
    private VolleyRequestQueue mRequestQueue;
    public RequestManner mRequestType = RequestManner.CACHE_THEN_NETROWK;
    private final RetryPolicy mRetryPolicy = new RetryPolicy();
    private Integer mSequence;
    private boolean mShowTag;
    private String mTag;
    public int mTimeOut = 20000;
    private String mUrl = "";
    private boolean mValidateSuccess = true;
    private VolleyCache<?> mVolleyCache = new VolleyDiskCache();

    public abstract VolleyRequest<T> add();

    protected abstract void parse(VolleyResponse volleyResponse, ResponseSupplier responseSupplier) throws JsonCanNotParseException, ParseException, DataIsNullException, DataIsErrException, DataNoUpdateException, TokenLoseException;

    public abstract VolleyResult<T> syncFetch();

    public VolleyRequest() {
        this.mHeadMap.put(SSOTK, PreferencesManager.getInstance().getSso_tk());
    }

    public final VolleyRequest<T> setCallback(OnEntryResponse<T> response) {
        this.mEntryResponse = response;
        return this;
    }

    public final VolleyRequest<T> setUrl(String url) {
        this.mUrl = url;
        Logger.d("volley", url);
        return this;
    }

    public final VolleyRequest<T> setParser(LetvBaseParser<T, ?> parser) {
        this.mParser = parser;
        if (this.mParser != null) {
            this.mParser.setShouldCheckToken(this.mNeedCheckToken);
        }
        return this;
    }

    public final VolleyRequest<T> addPostParam(String key, String value) {
        if (!(TextUtils.isEmpty(key) || TextUtils.isEmpty(value))) {
            this.mPostParams.put(key, value);
            Logger.d("volley", "add post:key=" + key + "&value=" + value);
        }
        return this;
    }

    public final VolleyRequest<T> addPostParams(Map<String, String> postParams) {
        if (!BaseTypeUtils.isMapEmpty(postParams)) {
            this.mPostParams.putAll(postParams);
            for (String key : postParams.keySet()) {
                Logger.d("volley", "add post:key=" + key + "&value=" + ((String) postParams.get(key)));
            }
        }
        return this;
    }

    public final VolleyRequest<T> setFilePostParam(UploadFileBean[] formFiles) {
        this.mFilePostParams = formFiles;
        return this;
    }

    public final VolleyRequest<T> addHead(String key, String value) {
        this.mHeadMap.put(key, value);
        return this;
    }

    public final VolleyRequest<T> addHeads(Map<String, String> map) {
        this.mHeadMap.putAll(map);
        return this;
    }

    public final VolleyRequest<T> setHttpMethod(HttpRequestMethod method) {
        this.mHttpRequestMethod = method;
        return this;
    }

    public final VolleyRequest<T> setRequestType(RequestManner type) {
        this.mRequestType = type;
        return this;
    }

    public final VolleyRequest<T> setTag(String tag) {
        this.mTag = tag;
        return this;
    }

    public final VolleyRequest<T> setMaxRetries(int maxNumRetries) {
        this.mRetryPolicy.setMaxRetries(maxNumRetries);
        return this;
    }

    public final VolleyRequest<T> setPriority(RequestPriority priority) {
        this.mPriority = priority;
        return this;
    }

    public final VolleyRequest<T> setTimeOut(int timeOut) {
        this.mTimeOut = timeOut;
        return this;
    }

    public final VolleyRequest<T> setCache(VolleyCache<?> volleyCache) {
        this.mVolleyCache = volleyCache;
        return this;
    }

    public final VolleyRequest<T> setOnPreExecuteListener(OnPreExecuteListener listener) {
        this.mOnPreExecuteListener = listener;
        return this;
    }

    public final VolleyRequest<T> setAlwaysCallbackNetworkResponse(boolean alwaysCallbackNetworkResponse) {
        this.mAlwaysCallbackNetworkResponse = alwaysCallbackNetworkResponse;
        return this;
    }

    public final VolleyRequest<T> enablePostEmpty(boolean alwaysCallbackNetworkResponse) {
        this.mEnablePostEmpty = true;
        return this;
    }

    public final VolleyRequest<T> setCacheValidateListener(OnPreAddCacheValidateListener<T> listener) {
        this.mAddCacheValidateListener = listener;
        return this;
    }

    public final VolleyRequest<T> setNeedCheckToken(boolean needCheckToken) {
        this.mNeedCheckToken = needCheckToken;
        if (this.mParser != null) {
            this.mParser.setShouldCheckToken(this.mNeedCheckToken);
        }
        return this;
    }

    public final VolleyRequest<T> setShowTag(boolean showTag) {
        this.mShowTag = showTag;
        return this;
    }

    final void setSequence(int sequence) {
        this.mSequence = Integer.valueOf(sequence);
    }

    final void setRequestQueue(VolleyRequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    final void setCacheSuccess() {
        this.mCacheSuccess = true;
    }

    public final void setCacheSuccess(boolean success) {
        this.mCacheSuccess = success;
    }

    void setCacheEntry(LetvBaseBean entry) {
        this.mCacheEntry = entry;
    }

    public void setRequestNetConsumeTime(long time) {
        this.mRequestNetConsumeTime = time;
    }

    public void setClientConsumeTime(long time) {
        this.mClientConsumeTime = time;
    }

    public String getUrl() {
        return this.mUrl;
    }

    String getParamsEncoding() {
        return "UTF-8";
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy;
    }

    String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    byte[] getPostBody() {
        return ParameterBuilder.getByteArrayParams(this.mPostParams);
    }

    Map<String, String> getPostFileBody() {
        return this.mPostParams;
    }

    UploadFileBean[] getFileBody() {
        return this.mFilePostParams;
    }

    public String getTag() {
        return this.mTag;
    }

    final int getSequence() {
        if (this.mSequence != null) {
            return this.mSequence.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    boolean isCanceled() {
        if (this.mCanceled && !TextUtils.isEmpty(this.mTag)) {
            Logger.d("volley", this.mTag + " is canceled");
        }
        return this.mCanceled;
    }

    boolean isValidateSuccess() {
        return this.mValidateSuccess;
    }

    boolean shouldPostErrorReport() {
        return this.mPostErrorReport;
    }

    VolleyCache<?> getVolleyCache() {
        return this.mVolleyCache;
    }

    RequestPriority getPriority() {
        return this.mPriority;
    }

    public boolean isCacheSuccess() {
        return this.mCacheSuccess;
    }

    boolean isAlwaysCallbackNetworkResponse() {
        return this.mAlwaysCallbackNetworkResponse;
    }

    boolean enablePostEmpty() {
        return this.mEnablePostEmpty;
    }

    boolean isShowTag() {
        return this.mShowTag && !TextUtils.isEmpty(this.mTag);
    }

    public OnPreAddCacheValidateListener<T> getAddCacheValidateListener() {
        return this.mAddCacheValidateListener;
    }

    public T getCacheEntry() {
        return this.mCacheEntry;
    }

    public T getNetworkEntry() {
        return this.mNetworkEntry;
    }

    public long getRequestNetConsumeTime() {
        if (this.mRequestNetConsumeTime > 60000) {
            return 0;
        }
        return this.mRequestNetConsumeTime;
    }

    public long getClientConsumeTime() {
        if (this.mClientConsumeTime > 60000) {
            return 0;
        }
        return Math.max(0, this.mClientConsumeTime - this.mRequestNetConsumeTime);
    }

    protected boolean onPreExecute() {
        if (this.mOnPreExecuteListener != null) {
            return this.mOnPreExecuteListener.onPreExecute();
        }
        return true;
    }

    public void cancel() {
        this.mCanceled = true;
    }

    void validateFail() {
        this.mValidateSuccess = false;
    }

    void setNeedPostErrorReport() {
        this.mPostErrorReport = true;
    }

    void finish() {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.finish(this);
        }
    }

    protected void deliverNetworkResponse(NetworkResponseState responseState) {
        this.mNetWorkDataHull.reportErrorString = getErrorInfo();
        if (!(this.mEntryResponse == null || this.mContext == null)) {
            if (!TextUtils.isEmpty(this.mTag)) {
                Logger.d("request_time", this.mTag + " 网络真正回调!");
            }
            this.mEntryResponse.onNetworkResponse(this, this.mNetworkEntry, this.mNetWorkDataHull, responseState);
        }
        if (this.mNetWorkDataHull.dataType == 272) {
            sendTokenLoseBroadcast();
        }
    }

    protected void deliverCacheResponse(CacheResponseState responseState) {
        if (this.mEntryResponse != null) {
            if (responseState != CacheResponseState.SUCCESS) {
                this.mCacheDataHull.markId = "";
            }
            if (!TextUtils.isEmpty(this.mTag)) {
                Logger.d("request_time", this.mTag + " 缓存真正回调!");
            }
            this.mEntryResponse.onCacheResponse(this, this.mCacheEntry, this.mCacheDataHull, responseState);
        }
    }

    protected void deliverErrorReport() {
        if (this.mEntryResponse != null) {
            this.mEntryResponse.onErrorReport(this, getErrorInfo());
        }
    }

    private void sendTokenLoseBroadcast() {
        Intent intent;
        if (isContainsCredit(this.mUrl)) {
            intent = new Intent("TokenLoseReceiver1");
        } else {
            intent = new Intent("TokenLoseReceiver2");
        }
        if (this.mContext != null && intent != null) {
            this.mContext.sendBroadcast(intent);
        }
    }

    private boolean isContainsCredit(String url) {
        return url.contains("&ctl=credit") && (url.contains("&act=status") || url.contains("&act=add") || url.contains("&act=list") || url.contains("&act=getactioninfo") || url.contains("&act=getActionProgress"));
    }

    int getErrCode() {
        return this.mParser == null ? 0 : this.mParser.getErrCode();
    }

    String getMessage() {
        return this.mParser == null ? "" : this.mParser.getMessage();
    }

    String getMarkId() {
        return this.mParser instanceof LetvMobileParser ? ((LetvMobileParser) this.mParser).getMarkId() : "";
    }

    String getErrorInfo() {
        String[] errorInfo = this.mNetWorkDataHull.getErrorInfo();
        String url = errorInfo[0];
        String status = errorInfo[1];
        String ut = errorInfo[2];
        if (TextUtils.isEmpty(status) || status.equals("null")) {
            status = NetworkUtils.DELIMITER_LINE;
        }
        if (TextUtils.isEmpty(ut) || ut.equals("null")) {
            ut = NetworkUtils.DELIMITER_LINE;
        }
        if (this.mNetWorkDataHull.errMsg != -1) {
            status = new StringBuilder(String.valueOf(status)).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(this.mNetWorkDataHull.errMsg).toString();
        } else {
            status = new StringBuilder(String.valueOf(status)).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).append(NetworkUtils.DELIMITER_LINE).toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("url=").append(url).append("&");
        sb.append("status=").append(status).append("&");
        sb.append("ut=").append(ut);
        return sb.toString();
    }

    public int compareTo(VolleyRequest<T> other) {
        RequestPriority left = getPriority();
        RequestPriority right = other.getPriority();
        return left == right ? this.mSequence.intValue() - other.mSequence.intValue() : right.ordinal() - left.ordinal();
    }
}
