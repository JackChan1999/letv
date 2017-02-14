package com.letv.core.network.volley;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.listener.Network;
import com.letv.core.network.volley.listener.ResponseDelivery;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class VolleyRequestQueue {
    private static final int DEFAULT_CACHE_THREAD_POOL_SIZE = 2;
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 5;
    private CacheDispatcher[] mCacheDispatchers;
    private final PriorityBlockingQueue<VolleyRequest<?>> mCacheQueue;
    private final Set<VolleyRequest<?>> mCurrentRequests;
    private final ResponseDelivery mDelivery;
    private NetworkDispatcher[] mDispatchers;
    private NetworkDispatcher mFileDispatcher;
    private final PriorityBlockingQueue<VolleyRequest<?>> mFileNetworkQueue;
    private final PriorityBlockingQueue<VolleyRequest<?>> mNetworkQueue;
    private AtomicInteger mSequenceGenerator;

    public interface RequestFilter {
        boolean apply(VolleyRequest<?> volleyRequest);
    }

    public VolleyRequestQueue(Network network, Network fileNetwork, int threadPoolSize, int cacheThreadPoolSize, ResponseDelivery delivery) {
        this.mSequenceGenerator = new AtomicInteger();
        this.mCurrentRequests = new HashSet();
        this.mCacheQueue = new PriorityBlockingQueue();
        this.mNetworkQueue = new PriorityBlockingQueue();
        this.mFileNetworkQueue = new PriorityBlockingQueue();
        this.mDispatchers = new NetworkDispatcher[threadPoolSize];
        this.mCacheDispatchers = new CacheDispatcher[cacheThreadPoolSize];
        this.mDelivery = delivery;
    }

    public VolleyRequestQueue(Network network, Network fileNetwork, int threadPoolSize, int cacheThreadPoolSize) {
        this(network, fileNetwork, threadPoolSize, cacheThreadPoolSize, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    public VolleyRequestQueue(Network network, Network fileNetwork) {
        this(network, fileNetwork, 5, 2);
    }

    public void start() {
        int i;
        stop();
        for (i = 0; i < this.mCacheDispatchers.length; i++) {
            CacheDispatcher dispatcher = new CacheDispatcher(this.mCacheQueue, this.mNetworkQueue, this.mDelivery);
            this.mCacheDispatchers[i] = dispatcher;
            dispatcher.start();
        }
        for (i = 0; i < this.mDispatchers.length; i++) {
            NetworkDispatcher dispatcher2 = new NetworkDispatcher(this.mNetworkQueue, this.mCacheQueue, false, this.mDelivery);
            this.mDispatchers[i] = dispatcher2;
            dispatcher2.start();
        }
        this.mFileDispatcher = new NetworkDispatcher(this.mFileNetworkQueue, this.mCacheQueue, true, this.mDelivery);
        this.mFileDispatcher.start();
    }

    public void stop() {
        int i;
        for (i = 0; i < this.mCacheDispatchers.length; i++) {
            if (this.mCacheDispatchers[i] != null) {
                this.mCacheDispatchers[i].quit();
            }
        }
        for (i = 0; i < this.mDispatchers.length; i++) {
            if (this.mDispatchers[i] != null) {
                this.mDispatchers[i].quit();
            }
        }
        if (this.mFileDispatcher != null) {
            this.mFileDispatcher.quit();
        }
    }

    public VolleyRequest<?> add(VolleyRequest<?> request) {
        request.mAddToQueueTime = System.currentTimeMillis();
        if (!request.onPreExecute()) {
            request.validateFail();
        }
        request.setRequestQueue(this);
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.add(request);
        }
        request.setSequence(getSequenceNumber());
        if (containPostFile(request)) {
            request.setRequestType(RequestManner.NETWORK_ONLY);
            request.setCache(new VolleyNoCache());
            this.mFileNetworkQueue.add(request);
        } else {
            RequestManner type = request.mRequestType;
            if (type == RequestManner.NETWORK_ONLY || type == RequestManner.NETWORK_THEN_CACHE) {
                this.mNetworkQueue.add(request);
            } else {
                this.mCacheQueue.add(request);
            }
        }
        if (request.isShowTag()) {
            LogInfo.log("request_time", request.getTag() + " 添加进队列!");
        }
        return request;
    }

    public <T> VolleyResult<T> syncFetch(VolleyRequest<T> request) {
        VolleyResult<T> result;
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.add(request);
        }
        request.mAddToQueueTime = System.currentTimeMillis();
        if (containPostFile(request)) {
            request.setRequestType(RequestManner.NETWORK_ONLY);
            request.setCache(new VolleyNoCache());
            result = new NetworkRequestData(this.mDelivery, new BasicNetwork(new HurlFileStack()), true).start(request);
        } else {
            RequestManner type = request.mRequestType;
            if (type == RequestManner.NETWORK_ONLY || type == RequestManner.NETWORK_THEN_CACHE) {
                result = new NetworkRequestData(this.mDelivery, new BasicNetwork(new HurlStack()), true).start(request);
            } else {
                result = new CacheRequestData(this.mDelivery, true).start(request);
            }
        }
        if (result == null || request.isCanceled()) {
            return new VolleyResult(null, request.mNetWorkDataHull, NetworkResponseState.IGNORE, CacheResponseState.IGNORE, "");
        }
        request.setClientConsumeTime(System.currentTimeMillis() - request.mAddToQueueTime);
        return result;
    }

    private boolean containPostFile(VolleyRequest<?> request) {
        return !BaseTypeUtils.isArrayEmpty(request.getFileBody());
    }

    private int getSequenceNumber() {
        return this.mSequenceGenerator.incrementAndGet();
    }

    public void cancelAll(RequestFilter filter) {
        synchronized (this.mCurrentRequests) {
            for (VolleyRequest<?> request : this.mCurrentRequests) {
                if (filter == null) {
                    request.cancel();
                } else if (filter.apply(request)) {
                    request.cancel();
                }
            }
        }
    }

    public void cancelWithTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            cancelAll(new 1(this, tag));
        }
    }

    public void finish(VolleyRequest<?> request) {
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.remove(request);
        }
    }

    public void finishAll() {
        synchronized (this.mCurrentRequests) {
            this.mCurrentRequests.clear();
        }
    }

    public void onDestory() {
        cancelAll(null);
        finishAll();
    }
}
