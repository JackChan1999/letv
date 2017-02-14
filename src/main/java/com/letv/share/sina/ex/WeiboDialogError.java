package com.letv.share.sina.ex;

public class WeiboDialogError extends Throwable {
    private static final long serialVersionUID = 1;
    private int mErrorCode;
    private String mFailingUrl;

    public WeiboDialogError(String message, int errorCode, String failingUrl) {
        super(message);
        this.mErrorCode = errorCode;
        this.mFailingUrl = failingUrl;
    }

    int getErrorCode() {
        return this.mErrorCode;
    }

    String getFailingUrl() {
        return this.mFailingUrl;
    }
}
