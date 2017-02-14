package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ModifyPwdResultBean implements LetvBaseBean {
    private static final long serialVersionUID = 3035923174533024037L;
    @JSONField(name = "result")
    public ResultBean resultBean;
}
