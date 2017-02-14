package com.letv.mobile.http.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;

public abstract class LetvBaseBean<T> implements Serializable {
    private static final long serialVersionUID = 1;
    private String errorCode;
    private String errorMessage;
    private boolean isFromCache = false;
    private String message;
    private String status;

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return String.valueOf(0).equals(getStatus());
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JSONField(serialize = false)
    public boolean isDataValid() {
        return true;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isFromCache() {
        return this.isFromCache;
    }

    public void setFromCache(boolean isFromCache) {
        this.isFromCache = isFromCache;
    }
}
