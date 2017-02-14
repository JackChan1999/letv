package com.letv.core.bean;

import java.util.ArrayList;

public class FindDataAreaBean extends FindBaseBean implements LetvBaseBean {
    public ArrayList<FindChildDataAreaBean> data;
    public HomeMetaData findAppBean;
    public String mobilePic;
    public String mtime;
    public String name;
    public String title;
    public String type;

    public void setData(ArrayList<FindChildDataAreaBean> data) {
        this.data = data;
    }

    public void setFindAppBean(HomeMetaData findAppBean) {
        this.findAppBean = findAppBean;
    }

    public String getTimeStampKey() {
        return this.name;
    }

    public String getTimeStamp() {
        return this.mtime;
    }
}
