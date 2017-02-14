package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class TabIndicatorDataBean implements LetvBaseBean {
    private static final long serialVersionUID = -3801776126192525626L;
    @JSONField(name = "style")
    public String style;
    @JSONField(name = "textLink")
    public TabTextLinkBean textLink = new TabTextLinkBean();
    @JSONField(name = "videoList")
    public TabVideoListBean videoList = new TabVideoListBean();
}
