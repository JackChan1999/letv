package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;

public class MyProfileListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    @JSONField(name = "profile")
    public List<MyProfileBean> list = new ArrayList();

    public void addMyProfileBean(MyProfileBean myProfileBean) {
        this.list.add(myProfileBean);
    }
}
