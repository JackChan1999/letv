package com.letv.core.bean;

import java.util.ArrayList;
import java.util.List;

public class MyFollowBodyBean implements LetvBaseBean {
    public int count = 0;
    private List<MyFollowItemBean> list;
    public int page = 0;
    public int pagecount = 0;

    public List<MyFollowItemBean> getList() {
        if (this.list == null) {
            return new ArrayList();
        }
        return this.list;
    }

    public void setList(List<MyFollowItemBean> list) {
        this.list = list;
    }
}
