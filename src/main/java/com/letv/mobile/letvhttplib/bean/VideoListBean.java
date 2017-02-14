package com.letv.mobile.letvhttplib.bean;

import java.util.ArrayList;

public class VideoListBean extends ArrayList<VideoBean> implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public int currPage;
    public int episodeNum;
    public int showOuterVideolist;
    public int style;
    public int totalNum;
    public int varietyShow;
    public int videoPosition;
}
