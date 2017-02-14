package com.letv.core.bean;

import java.util.ArrayList;
import java.util.List;

public class TopicDetailInfoListBean implements LetvBaseBean {
    public static final String CACHE_KEY_TOPIC_ALBUM_LIST = TopicDetailInfoListBean.class.getSimpleName();
    private static final long serialVersionUID = 1;
    public List<TopicDetailItemBean> list = new ArrayList();
    public TopicSubject subject;
    public List<TopicDetailItemBean> videoList = new ArrayList();

    public void add(TopicDetailItemBean bean) {
        this.list.add(bean);
    }
}
