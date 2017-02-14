package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;

public class MyPointsBeanList implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    @JSONField(name = "data")
    public ArrayList<MyPointsBean> myPointsBeansList = new ArrayList();

    public static long getSerialversionuid() {
        return 1;
    }
}
