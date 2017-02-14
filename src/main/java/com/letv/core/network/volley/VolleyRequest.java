package com.letv.core.network.volley;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.UploadFileBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.VolleyResponse.ResponseSupplier;
import com.letv.core.network.volley.exception.DataIsErrException;
import com.letv.core.network.volley.exception.DataIsNullException;
import com.letv.core.network.volley.exception.DataNoUpdateException;
import com.letv.core.network.volley.exception.JsonCanNotParseException;
import com.letv.core.network.volley.exception.ParseException;
import com.letv.core.network.volley.exception.TokenLoseException;
import com.letv.core.network.volley.listener.OnEntryResponse;
import com.letv.core.network.volley.listener.OnPreExecuteListener;
import com.letv.core.network.volley.listener.VolleyCache;
import com.letv.core.network.volley.toolbox.ParameterBuilder;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.LetvBaseParser;
import com.letv.core.parser.LetvMobileParser;
import com.letv.core.parser.LetvPBParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.EncryptUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.HashMap;
import java.util.Map;

public abstract class VolleyRequest<T> implements Comparable<VolleyRequest<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final int DEFAULT_TIMEOUT = 20000;
    private static final String DEVID = "devid";
    private static final String MAC = "mac";
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
    public int mConnectTimeOut = 20000;
    public Context mContext = BaseApplication.getInstance();
    private boolean mEnablePostEmpty = false;
    protected OnEntryResponse<T> mEntryResponse;
    private UploadFileBean[] mFilePostParams;
    public Map<String, String> mHeadMap = new HashMap();
    public HttpRequestMethod mHttpRequestMethod = HttpRequestMethod.AUTO;
    private boolean mIsPB = false;
    private boolean mNeedCheckToken;
    private boolean mNeedLogDataError = true;
    public final DataHull mNetWorkDataHull = new DataHull();
    protected T mNetworkEntry;
    private OnPreExecuteListener mOnPreExecuteListener;
    protected LetvBaseParser<T, ?> mParser;
    private boolean mPostErrorReport = false;
    private Map<String, String> mPostParams = new HashMap();
    private RequestPriority mPriority = RequestPriority.NORMAL;
    public int mReadTimeOut = 20000;
    private long mRequestNetConsumeTime;
    private VolleyRequestQueue mRequestQueue;
    public RequestManner mRequestType = RequestManner.CACHE_THEN_NETROWK;
    private final RetryPolicy mRetryPolicy = new RetryPolicy();
    private Integer mSequence;
    private boolean mShowTag;
    private String mTag;
    private String mUrl = "";
    private boolean mValidateSuccess = true;
    private VolleyCache<?> mVolleyCache = new VolleyDiskCache();

    public enum HttpRequestMethod {
        AUTO,
        GET,
        POST
    }

    public enum RequestManner {
        CACHE_THEN_NETROWK,
        NETWORK_THEN_CACHE,
        NETWORK_ONLY,
        CACHE_ONLY,
        CACHE_FAIL_THEN_NETWORK
    }

    public abstract VolleyRequest<T> add();

    protected abstract void parse(VolleyResponse volleyResponse, ResponseSupplier responseSupplier) throws JsonCanNotParseException, ParseException, DataIsNullException, DataIsErrException, DataNoUpdateException, TokenLoseException;

    public abstract VolleyResult<T> syncFetch();

    public VolleyRequest() {
        this.mHeadMap.put(SSOTK, PreferencesManager.getInstance().getSso_tk());
        this.mHeadMap.put("devid", LetvUtils.generateDeviceId(this.mContext));
        this.mHeadMap.put(MAC, LetvUtils.getMacAddressForLogin());
    }

    public final VolleyRequest<T> setCallback(OnEntryResponse<T> response) {
        this.mEntryResponse = response;
        return this;
    }

    public final VolleyRequest<T> setUrl(String url) {
        this.mUrl = url;
        if (!(!LetvUrlMaker.isTest() || !LetvUtils.isInHongKong() || TextUtils.isEmpty(this.mUrl) || this.mUrl.startsWith("http://hk.") || this.mUrl.contains(".g3") || this.mUrl.contains("http://10.204.28.31:9030/android/index.php"))) {
            this.mUrl = this.mUrl.replace("http://", "http://hk.");
        }
        if (!TextUtils.isEmpty(url) && url.contains("api.mob.app.letv.com")) {
            this.mHeadMap.put(TK, EncryptUtils.letvEncrypt(((long) TimestampBean.getTm().getCurServerTime()) * 1, url));
        }
        LogInfo.log("volley", url);
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
            LogInfo.log("volley", "add post:key=" + key + "&value=" + value);
        }
        return this;
    }

    public final VolleyRequest<T> addPostParams(Map<String, String> postParams) {
        if (!BaseTypeUtils.isMapEmpty(postParams)) {
            this.mPostParams.putAll(postParams);
            for (String key : postParams.keySet()) {
                LogInfo.log("volley", "add post:key=" + key + "&value=" + ((String) postParams.get(key)));
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

    public final VolleyRequest<T> setConnectTimeOut(int timeOut) {
        this.mConnectTimeOut = timeOut;
        return this;
    }

    public final VolleyRequest<T> setReadTimeOut(int timeOut) {
        this.mReadTimeOut = timeOut;
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

    public final VolleyRequest<T> setNeedLogDataError(boolean needLogDataError) {
        this.mNeedLogDataError = needLogDataError;
        return this;
    }

    public final VolleyRequest<T> setIsPB(boolean isPB) {
        this.mIsPB = isPB;
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

    void setCacheEntry(Object entry) {
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

    public boolean isCanceled() {
        if (this.mCanceled && !TextUtils.isEmpty(this.mTag)) {
            LogInfo.log("volley", this.mTag + " is canceled");
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

    protected boolean isPb() {
        return this.mIsPB;
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

    public boolean isNeedLogDataError() {
        return this.mNeedLogDataError;
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
        this.mNetWorkDataHull.apiState = this.mParser == null ? 0 : this.mParser.getErrCode();
        if (responseState == NetworkResponseState.RESULT_ERROR) {
            this.mNetWorkDataHull.apiState = 7;
        }
        if (!(this.mEntryResponse == null || this.mContext == null)) {
            if (!TextUtils.isEmpty(this.mTag)) {
                LogInfo.log("request_time", this.mTag + " 网络真正回调!");
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
                LogInfo.log("request_time", this.mTag + " 缓存真正回调!");
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
        if (this.mParser instanceof LetvMobileParser) {
            return ((LetvMobileParser) this.mParser).getMarkId();
        }
        if (this.mParser instanceof LetvPBParser) {
            return ((LetvPBParser) this.mParser).markid;
        }
        return "";
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
            status = status + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mNetWorkDataHull.errMsg;
        } else {
            status = status + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + NetworkUtils.DELIMITER_LINE;
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
