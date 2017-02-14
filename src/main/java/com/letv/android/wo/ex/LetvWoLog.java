package com.letv.android.wo.ex;

import com.letv.core.bean.LetvBaseBean;

public class LetvWoLog implements LetvBaseBean {
    private String code;
    private String message;

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
}
