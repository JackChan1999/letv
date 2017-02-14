package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.Iterator;

public class TabTopicAllDataBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public int columnNum;
    public boolean isNormalVideo = false;
    @JSONField(name = "tabDesc")
    public TabTopicDesc tabTopicDesc = new TabTopicDesc();
    @JSONField(name = "tabZtList")
    public TabTopicZtList tabTopicZtList = new TabTopicZtList();

    public static class TabTopicZtList implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        @JSONField(name = "subject")
        public TabSubject subject = new TabSubject();
        @JSONField(name = "tabAlbumList")
        public ArrayList<AlbumInfo> tabAlbumList = new ArrayList();
        @JSONField(name = "tabVideoList")
        public TabVideoListBean tabVideoList = new TabVideoListBean();
    }

    public AlbumInfo getAlumNewByPid(long pid) {
        if (this.tabTopicZtList == null) {
            return null;
        }
        ArrayList<AlbumInfo> albumInfoArrayList = this.tabTopicZtList.tabAlbumList;
        if (albumInfoArrayList == null) {
            return null;
        }
        Iterator it = albumInfoArrayList.iterator();
        while (it.hasNext()) {
            AlbumInfo albumInfo = (AlbumInfo) it.next();
            if (albumInfo.pid == pid) {
                return albumInfo;
            }
        }
        return null;
    }
}
