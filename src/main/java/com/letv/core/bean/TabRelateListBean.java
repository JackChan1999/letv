package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;

public class TabRelateListBean implements LetvBaseBean {
    private static final long serialVersionUID = -128157262647153803L;
    @JSONField(name = "cmsdata")
    public ArrayList<CmsBean> cmsDataList = new ArrayList();
    @JSONField(name = "recData")
    public ArrayList<VideoBean> recDataList = new ArrayList();
    @JSONField(name = "relateAlbums")
    public ArrayList<AlbumInfo> relateAlbumList = new ArrayList();
    @JSONField(name = "selfVideo")
    public VideoBean selfVideo = new VideoBean();
    @JSONField(name = "showOuterVideolist")
    public String showOuterVideolist;
    @JSONField(name = "varietyShow")
    public String varietyShow;
}
