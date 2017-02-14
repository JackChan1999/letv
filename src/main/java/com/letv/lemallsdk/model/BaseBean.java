package com.letv.lemallsdk.model;

import java.io.Serializable;
import java.util.List;

public class BaseBean implements Serializable {
    private static final long serialVersionUID = 1;
    private List<BaseBean> beanList;
    private String message;
    private String status;

    public List<BaseBean> getBeanList() {
        return this.beanList;
    }

    public void setBeanList(List<BaseBean> beanList) {
        this.beanList = beanList;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
