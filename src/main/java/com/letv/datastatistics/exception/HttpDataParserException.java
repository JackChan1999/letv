package com.letv.datastatistics.exception;

public class HttpDataParserException extends Exception {
    private static final long serialVersionUID = 4982948452919633570L;

    public HttpDataParserException(Exception e) {
        super(e);
    }

    public HttpDataParserException(String e) {
        super(e);
    }
}
