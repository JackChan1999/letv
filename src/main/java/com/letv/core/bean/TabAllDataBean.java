package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class TabAllDataBean implements LetvBaseBean {
    private static final long serialVersionUID = -4481797972458091917L;
    @JSONField(name = "albumInfo")
    public AlbumInfo albumInfo = new AlbumInfo();
    public boolean isNormalVideo = false;
    @JSONField(name = "OuterVideoInfo")
    public TabOuterVideoInfoBean outerVideoInfo = new TabOuterVideoInfoBean();
    @JSONField(name = "tabRelate")
    public TabIndicatorBean tabRelate = new TabIndicatorBean();
    @JSONField(name = "albumInfo")
    public TabIndicatorBean tabVideoList = new TabIndicatorBean();
    @JSONField(name = "videoInfo")
    public VideoBean videoInfo = new VideoBean();
    public VideoListBean videoListPlayerLibs;
}
