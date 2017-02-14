package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;

public class TabVideoListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    @JSONField(name = "albumInfo")
    public AlbumNewListByDateBean albumNewListByDatePlayerLibs = new AlbumNewListByDateBean();
    @JSONField(name = "cmsdata")
    public ArrayList<CmsBean> cmsDataList = new ArrayList();
    @JSONField(name = "episodeNum")
    public String episodeNum;
    @JSONField(name = "pagenum")
    public String pagenum;
    @JSONField(name = "previewList")
    public ArrayList<VideoBean> previewList = new ArrayList();
    @JSONField(name = "recAlbumInfo")
    public RecAlbumBean recAlbumInfo;
    @JSONField(name = "recData")
    public ArrayList<VideoBean> recDataList = new ArrayList();
    @JSONField(name = "relateAlbums")
    public ArrayList<AlbumInfo> relateAlbumList = new ArrayList();
    @JSONField(name = "selfVideo")
    public VideoBean selfVideo;
    @JSONField(name = "showOuterVideolist")
    public String showOuterVideolist;
    @JSONField(name = "style")
    public String style;
    @JSONField(name = "totalNum")
    public String totalNum;
    @JSONField(name = "varietyShow")
    public String varietyShow;
    @JSONField(name = "videoInfo")
    public ArrayList<VideoBean> videoInfoList = new ArrayList();

    public ArrayList<VideoBean> getPreviewList() {
        int i = 0;
        while (i < this.previewList.size()) {
            if (!(this.previewList.get(i) == null || ((VideoBean) this.previewList.get(i)).play == 1)) {
                this.previewList.remove(i);
            }
            i++;
        }
        return this.previewList;
    }

    public void setPreviewList(ArrayList<VideoBean> previewList) {
        this.previewList = previewList;
    }
}
