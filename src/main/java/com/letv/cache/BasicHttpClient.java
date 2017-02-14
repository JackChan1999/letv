package com.letv.cache;

public interface BasicHttpClient {
    public static final String CHARSET = "UTF-8";
    public static final int CONNECTION_TIMEOUT = 2000;
    public static final int CONN_PER_ROUTE_MAX = 30;
    public static final int MAX_CONNECTIONS = 30;
    public static final int RETRY_COUNT = 3;
    public static final int SOCKET_BUFFER_SIZE = 8192;
    public static final int SOCKET_TIMEOUT = 2000;
    public static final int TIMEOUT = 3000;
    public static final String USERAGENT = "Android client";
}
