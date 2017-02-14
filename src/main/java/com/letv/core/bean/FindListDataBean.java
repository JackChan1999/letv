package com.letv.core.bean;

import java.util.ArrayList;
import java.util.List;

public class FindListDataBean implements LetvBaseBean {
    public static String CACHE_KEY = "FindListDataBean";
    private List<FindDataBean> data;
    private List<FindDataBean> filterData;
    private FindDataBean spreadAreaBean;

    public List<FindDataBean> getData() {
        return this.filterData;
    }

    private void filter() {
        if (this.filterData == null) {
            this.filterData = new ArrayList();
            for (FindDataBean bean : this.data) {
                if (!bean.getData().isEmpty()) {
                    this.filterData.add(bean);
                    initAreaBean(bean);
                }
            }
        }
    }

    public void setData(List<FindDataBean> data) {
        this.data = data;
        filter();
    }

    private void initAreaBean(FindDataBean bean) {
        if ("3".equals(bean.area)) {
            this.spreadAreaBean = bean;
        }
    }

    public boolean hasNewSpread() {
        if (this.spreadAreaBean == null || this.spreadAreaBean.getData() == null) {
            return false;
        }
        return this.spreadAreaBean.isNewData();
    }

    public void saveSpreadBeanTimeStamp() {
        if (this.spreadAreaBean != null) {
            this.spreadAreaBean.saveTimeStamp();
        }
    }
}
