package com.letv.android.wo.ex;

import com.letv.core.bean.LetvBaseBean;

public class LetvWoOrderInfo implements LetvBaseBean {
    public String code;
    public Object data;
    public String message;
    public String timestamp;

    public static class Data {
        public String canceltime;
        public String endtime;
        public String ordertime;
        public String type;

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrdertime() {
            return this.ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getEndtime() {
            return this.endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getCanceltime() {
            return this.canceltime;
        }

        public void setCanceltime(String canceltime) {
            this.canceltime = canceltime;
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
