package com.letv.share.sina.ex;

public class WeiboException extends Exception {
    private static final long serialVersionUID = 475022994858770424L;
    private int statusCode = -1;

    public WeiboException(String msg) {
        super(msg);
    }

    public WeiboException(Exception cause) {
        super(cause);
    }

    public WeiboException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public WeiboException(String msg, Exception cause) {
        super(msg, cause);
    }

    public WeiboException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public WeiboException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public WeiboException(Throwable throwable) {
        super(throwable);
    }

    public WeiboException(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
