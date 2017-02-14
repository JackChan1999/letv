package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ResultBean implements LetvBaseBean {
    private static final long serialVersionUID = -7156591636447541762L;
    public int code;
    public String data;
    @JSONField(name = "bean")
    public DataBean dataBean;
    public String errorCode;
    public String message;
    public String responseType;
    public int status;
}
