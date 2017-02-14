package com.letv.android.wo.ex;

import com.letv.core.bean.LetvBaseBean;

public class LetvOrderInfo implements LetvBaseBean {
    public String code;
    public Object data;
    public String message;

    public static class Data {
        public String canorder;
        public String desc;
        public String username;

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCanorder() {
            return this.canorder;
        }

        public void setCanorder(String canorder) {
            this.canorder = canorder;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
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

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
