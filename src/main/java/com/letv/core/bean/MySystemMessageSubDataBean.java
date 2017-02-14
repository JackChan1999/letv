package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class MySystemMessageSubDataBean implements LetvBaseBean {
    public long cid;
    public String content;
    @JSONField(name = "native")
    public String gotoType;
    public String image_url;
    public long pid;
    public String title;
    public String unread;
    public String url;
    public long vid;
}
