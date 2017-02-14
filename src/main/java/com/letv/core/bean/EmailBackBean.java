package com.letv.core.bean;

public class EmailBackBean implements LetvBaseBean {
    public String errorCode = "";
    public String message = "";
    public String responseType = "";
    public String status = "";

    public String toString() {
        return this.errorCode + "message:" + this.message + "responseType;" + this.responseType + "status;" + this.status;
    }
}
