package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class TabTopicVideoListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    @JSONField(name = "style")
    public String style;
    @JSONField(name = "textLink")
    public TabTextLinkBean tabTextLinkBean;
    @JSONField(name = "videoList")
    public TabVideoListBean tabVideoListBean;
}
