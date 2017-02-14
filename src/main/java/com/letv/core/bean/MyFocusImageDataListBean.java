package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;

public class MyFocusImageDataListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    @JSONField(name = "blockContent")
    public List<HomeMetaData> mFocusImageDataList = new ArrayList();

    public void add(HomeMetaData bean) {
        this.mFocusImageDataList.add(bean);
    }
}
