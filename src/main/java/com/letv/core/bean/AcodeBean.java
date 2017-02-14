package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class AcodeBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    @JSONField(name = "apiurl")
    public String mApiUrl;
}
