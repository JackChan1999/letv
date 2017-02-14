package com.letv.core.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class VideoListBean extends ArrayList<VideoBean> implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public int currPage;
    public int episodeNum;
    public LinkedHashMap<String, VideoListBean> periodHashMap;
    public int showOuterVideolist;
    public int style;
    public int totalNum;
    public int varietyShow;
    public int videoPosition;
}
