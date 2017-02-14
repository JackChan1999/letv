package com.letv.datastatistics.http;

import android.content.Context;
import android.util.Log;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.bean.StatisCacheBean;
import com.letv.datastatistics.db.StatisDBHandler;
import com.letv.datastatistics.exception.HttpDataConnectionException;
import com.letv.datastatistics.exception.HttpDataParserException;
import com.letv.datastatistics.util.DataUtils;
import com.letv.pp.utils.NetworkUtils;
import java.io.IOException;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpEngine {
    public static final int CON_TIME_OUT_MS = 30000;
    public static final int GET_CONNECTION_TIMEOUT = 1000;
    public static final int MAX_CONNECTIONS_PER_HOST = 3;
    public static final int MAX_TOTAL_CONNECTIONS = 3;
    public static final int SOCKET_BUFFER_SIZE = 8192;
    public static final int SO_TIME_OUT_MS = 30000;
    private static HttpEngine mInstance = null;
    private static final Object mInstanceSync = new Object();
    private DefaultHttpClient mDefaultHttpClient;

    private HttpEngine() {
        this.mDefaultHttpClient = null;
        this.mDefaultHttpClient = createHttpClient();
        this.mDefaultHttpClient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (DataStatistics.getInstance().isDebug()) {
                    Log.d(DataStatistics.TAG, exception.getClass() + NetworkUtils.DELIMITER_COLON + exception.getMessage() + ",executionCount:" + executionCount);
                }
                if (executionCount >= 3) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                if (exception instanceof ClientProtocolException) {
                    return true;
                }
                return false;
            }
        });
    }

    public static HttpEngine getInstance() {
        synchronized (mInstanceSync) {
            if (mInstance != null) {
                HttpEngine httpEngine = mInstance;
                return httpEngine;
            }
            mInstance = new HttpEngine();
            return mInstance;
        }
    }

    public DefaultHttpClient createHttpClient() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme(IDataSource.SCHEME_HTTP_TAG, PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme(IDataSource.SCHEME_HTTPS_TAG, SSLSocketFactory.getSocketFactory(), 443));
        HttpParams params = createHttpParams();
        return new DefaultHttpClient(new ThreadSafeClientConnManager(params, registry), params);
    }

    private HttpParams createHttpParams() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(params, false);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(3));
        ConnManagerParams.setMaxTotalConnections(params, 3);
        ConnManagerParams.setTimeout(params, 1000);
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpClientParams.setRedirecting(params, false);
        return params;
    }

    public String doHttpGet(Context context, StatisCacheBean mStatisCacheBean) throws HttpDataConnectionException, HttpDataParserException {
        String str = null;
        if (context != null) {
            if (DataStatistics.getInstance().isDebug()) {
                Log.d(DataStatistics.TAG, "url:" + mStatisCacheBean.getCacheData());
            }
            if (mStatisCacheBean != null) {
                StatisDBHandler.saveLocalCache(context, mStatisCacheBean);
                if (DataUtils.getAvailableNetWorkInfo(context) != null) {
                    HttpGet httpGet = new HttpGet(mStatisCacheBean.getCacheData());
                    try {
                        HttpResponse httpResponse = this.mDefaultHttpClient.execute(httpGet);
                        if (httpResponse == null) {
                            try {
                                httpGet.abort();
                                this.mDefaultHttpClient.getConnectionManager().closeExpiredConnections();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            int responseCode = httpResponse.getStatusLine().getStatusCode();
                            if (DataStatistics.getInstance().isDebug()) {
                                Log.d(DataStatistics.TAG, "responseCode:" + responseCode);
                            }
                            if (responseCode < 200 || responseCode >= 300) {
                                if (!DataStatistics.getInstance().isDebug() && (System.currentTimeMillis() - mStatisCacheBean.getCacheTime()) / 1000 >= 432000) {
                                    StatisDBHandler.deleteByCacheId(context, mStatisCacheBean.getCacheId());
                                }
                                throw new HttpDataConnectionException(httpResponse.getStatusLine().toString());
                            } else if (httpResponse.getEntity() == null) {
                                try {
                                    httpGet.abort();
                                    this.mDefaultHttpClient.getConnectionManager().closeExpiredConnections();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            } else {
                                str = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                                if (DataStatistics.getInstance().isDebug()) {
                                    Log.d(DataStatistics.TAG, "result:" + str);
                                }
                                StatisDBHandler.deleteByCacheId(context, mStatisCacheBean.getCacheId());
                                try {
                                    httpGet.abort();
                                    this.mDefaultHttpClient.getConnectionManager().closeExpiredConnections();
                                } catch (Exception e22) {
                                    e22.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e222) {
                        e222.printStackTrace();
                        throw new HttpDataParserException(e222);
                    } catch (Exception e2222) {
                        e2222.printStackTrace();
                        throw new HttpDataConnectionException(e2222);
                    } catch (Exception e22222) {
                        e22222.printStackTrace();
                        throw new HttpDataConnectionException(e22222);
                    } catch (Exception e222222) {
                        e222222.printStackTrace();
                        throw new HttpDataConnectionException(e222222);
                    } catch (Throwable th) {
                        try {
                            httpGet.abort();
                            this.mDefaultHttpClient.getConnectionManager().closeExpiredConnections();
                        } catch (Exception e2222222) {
                            e2222222.printStackTrace();
                        }
                    }
                }
            }
        }
        return str;
    }

    public void closeHttpEngine() {
        if (!(this.mDefaultHttpClient == null || this.mDefaultHttpClient.getConnectionManager() == null)) {
            try {
                this.mDefaultHttpClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mInstance != null) {
            mInstance = null;
        }
    }
}
