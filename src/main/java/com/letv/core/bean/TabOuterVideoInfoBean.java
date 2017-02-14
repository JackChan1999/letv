package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;

public class TabOuterVideoInfoBean implements LetvBaseBean {
    private static final long serialVersionUID = 4689469609006306825L;
    @JSONField(name = "cmsdata")
    public ArrayList<TabTextLinkBean> cmsdata = new ArrayList();
    @JSONField(name = "otherVideos")
    public TabOtherVideosBean otherVideos = new TabOtherVideosBean();
}
