package com.letv.datastatistics.exception;

public class HttpDataConnectionException extends Exception {
    private static final long serialVersionUID = -1989985506281961078L;

    public HttpDataConnectionException(Exception e) {
        super(e);
    }

    public HttpDataConnectionException(String e) {
        super(e);
    }
}
