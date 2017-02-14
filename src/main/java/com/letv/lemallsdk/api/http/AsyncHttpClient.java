package com.letv.lemallsdk.api.http;

import android.content.Context;
import android.text.TextUtils;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.util.EALogger;
import com.letv.lemallsdk.util.OtherUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class AsyncHttpClient {
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVETIME = 0;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 10;
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static final int DEFAULT_MAX_RETRIES = 5;
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String VERSION = "1.1";
    private static int maxConnections = 10;
    private static int socketTimeout = 10000;
    private final Map<String, String> clientHeaderMap;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext = new SyncBasicHttpContext(new BasicHttpContext());
    private final Map<Context, List<WeakReference<Future<?>>>> requestMap;
    private ThreadPoolExecutor threadPool;

    private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1;
        }
    }

    public AsyncHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, (long) socketTimeout);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);
        HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, socketTimeout);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(httpParams, String.format("letvshop/%s (http://www.letv.com/)", new Object[]{VERSION}));
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTP_TAG, PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        this.httpClient = new DefaultHttpClient(cm, httpParams);
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
                for (String header : AsyncHttpClient.this.clientHeaderMap.keySet()) {
                    request.addHeader(header, (String) AsyncHttpClient.this.clientHeaderMap.get(header));
                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Header encoding = entity.getContentEncoding();
                    if (encoding != null) {
                        for (HeaderElement element : encoding.getElements()) {
                            if (element.getName().equalsIgnoreCase("gzip")) {
                                response.setEntity(new InflatingEntity(response.getEntity()));
                                return;
                            }
                        }
                    }
                }
            }
        });
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(5));
        this.threadPool = new ThreadPoolExecutor(5, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue(3), new CallerRunsPolicy());
        this.requestMap = new WeakHashMap();
        this.clientHeaderMap = new HashMap();
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute("http.cookie-store", cookieStore);
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public void setUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
    }

    public void setTimeout(int timeout) {
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout(httpParams, (long) timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
    }

    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, sslSocketFactory, 443));
    }

    public void addHeader(String header, String value) {
        this.clientHeaderMap.put(header, value);
    }

    public void setBasicAuth(String user, String pass) {
        setBasicAuth(user, pass, AuthScope.ANY);
    }

    public void setBasicAuth(String user, String pass, AuthScope scope) {
        this.httpClient.getCredentialsProvider().setCredentials(scope, new UsernamePasswordCredentials(user, pass));
    }

    public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        List<WeakReference<Future<?>>> requestList = (List) this.requestMap.get(context);
        if (requestList != null) {
            for (WeakReference<Future<?>> requestRef : requestList) {
                Future<?> request = (Future) requestRef.get();
                if (request != null) {
                    request.cancel(mayInterruptIfRunning);
                }
            }
        }
        this.requestMap.remove(context);
    }

    public void get(String url, AsyncHttpResponseHandler responseHandler) {
        get(null, url, null, responseHandler);
    }

    public void get(boolean isCookieStroe, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (isCookieStroe) {
            setCookieStore(getuCookie());
        }
        get(null, url, params, responseHandler);
    }

    public void get(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        get(context, url, null, responseHandler);
    }

    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(url, params)), null, responseHandler, context);
    }

    public void download(String url, AsyncHttpResponseHandler responseHandler) {
        download(null, url, null, responseHandler);
    }

    public void download(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        download(null, url, params, responseHandler);
    }

    public void download(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        download(context, url, null, responseHandler);
    }

    public void download(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(url, params)), null, responseHandler, context);
    }

    public void get(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        HttpUriRequest request = new HttpGet(getUrlWithQueryString(url, params));
        if (headers != null) {
            request.setHeaders(headers);
        }
        sendRequest(this.httpClient, this.httpContext, request, null, responseHandler, context);
    }

    public void post(String url, AsyncHttpResponseHandler responseHandler) {
        post(null, url, null, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(null, url, params, responseHandler);
    }

    public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(context, url, paramsToEntity(params), null, responseHandler);
    }

    public void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPost(url), entity), contentType, responseHandler, context);
    }

    public void post(Context context, String url, Header[] headers, RequestParams params, String contentType, AsyncHttpResponseHandler responseHandler) {
        HttpEntityEnclosingRequestBase request = new HttpPost(url);
        if (params != null) {
            request.setEntity(paramsToEntity(params));
        }
        if (headers != null) {
            request.setHeaders(headers);
        }
        sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public void post(Context context, String url, Header[] headers, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        HttpEntityEnclosingRequestBase request = addEntityToRequestBase(new HttpPost(url), entity);
        if (headers != null) {
            request.setHeaders(headers);
        }
        sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public void put(String url, AsyncHttpResponseHandler responseHandler) {
        put(null, url, null, responseHandler);
    }

    public void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        put(null, url, params, responseHandler);
    }

    public void put(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        put(context, url, paramsToEntity(params), null, responseHandler);
    }

    public void put(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPut(url), entity), contentType, responseHandler, context);
    }

    public void put(Context context, String url, Header[] headers, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        HttpEntityEnclosingRequestBase request = addEntityToRequestBase(new HttpPut(url), entity);
        if (headers != null) {
            request.setHeaders(headers);
        }
        sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public void delete(String url, AsyncHttpResponseHandler responseHandler) {
        delete(null, url, responseHandler);
    }

    public void delete(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        sendRequest(this.httpClient, this.httpContext, new HttpDelete(url), null, responseHandler, context);
    }

    public void delete(Context context, String url, Header[] headers, AsyncHttpResponseHandler responseHandler) {
        HttpDelete delete = new HttpDelete(url);
        if (headers != null) {
            delete.setHeaders(headers);
        }
        sendRequest(this.httpClient, this.httpContext, delete, null, responseHandler, context);
    }

    protected void sendRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, AsyncHttpResponseHandler responseHandler, Context context) {
        if (contentType != null) {
            uriRequest.addHeader(io.fabric.sdk.android.services.network.HttpRequest.HEADER_CONTENT_TYPE, contentType);
        }
        Future<?> request = this.threadPool.submit(new AsyncHttpRequest(client, httpContext, uriRequest, responseHandler));
        if (context != null) {
            List<WeakReference<Future<?>>> requestList = (List) this.requestMap.get(context);
            if (requestList == null) {
                requestList = new LinkedList();
                this.requestMap.put(context, requestList);
            }
            requestList.add(new WeakReference(request));
        }
    }

    public static String getUrlWithQueryString(String url, RequestParams params) {
        if (params != null) {
            String paramString = params.getParamString();
            if (url.indexOf("?") == -1) {
                url = new StringBuilder(String.valueOf(url)).append("?").append(paramString).toString();
            } else {
                url = new StringBuilder(String.valueOf(url)).append("&").append(paramString).toString();
            }
        }
        EALogger.i(IDataSource.SCHEME_HTTP_TAG, url);
        return url;
    }

    private HttpEntity paramsToEntity(RequestParams params) {
        if (params != null) {
            return params.getEntity();
        }
        return null;
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase requestBase, HttpEntity entity) {
        if (entity != null) {
            requestBase.setEntity(entity);
        }
        return requestBase;
    }

    public static CookieStore getuCookie() {
        CookieStore uCookie = new BasicCookieStore();
        try {
            String COOKIE_S_LINKDATA = LemallPlatform.getInstance().getCookieLinkdata();
            if (!TextUtils.isEmpty(COOKIE_S_LINKDATA)) {
                String[] cookies = COOKIE_S_LINKDATA.split("&");
                for (String item : cookies) {
                    String[] keyValue = item.split(SearchCriteria.EQ);
                    if (keyValue.length == 2) {
                        if (OtherUtil.isContainsChinese(keyValue[1])) {
                            keyValue[1] = URLEncoder.encode(keyValue[1], "UTF-8");
                        }
                        BasicClientCookie cookie = new BasicClientCookie(keyValue[0], keyValue[1]);
                        cookie.setVersion(0);
                        cookie.setDomain(".lemall.com");
                        cookie.setPath("/");
                        uCookie.addCookie(cookie);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uCookie;
    }
}
