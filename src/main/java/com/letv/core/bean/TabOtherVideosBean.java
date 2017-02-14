package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;

public class TabOtherVideosBean implements LetvBaseBean {
    private static final long serialVersionUID = 2892331125581105759L;
    @JSONField(name = "pagenum")
    public String pagenum;
    @JSONField(name = "totalNum")
    public String totalNum;
    @JSONField(name = "videoInfo")
    public ArrayList<VideoBean> videoInfoList;
    @JSONField(name = "videoPosition")
    public String videoPosition;
}
