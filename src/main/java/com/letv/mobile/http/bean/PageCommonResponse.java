package com.letv.mobile.http.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class PageCommonResponse<T> extends LetvBaseBean<T> {
    private static final long serialVersionUID = -2773800725879848401L;
    private Integer currentIndex;
    private List<T> data;
    private Integer nextIndex;
    private Integer totalCount;

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Integer getNextIndex() {
        return this.nextIndex;
    }

    public void setNextIndex(Integer nextIndex) {
        this.nextIndex = nextIndex;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @JSONField(serialize = false)
    public boolean isDataValid() {
        return (this.totalCount == null || this.currentIndex == null || this.nextIndex == null) ? false : true;
    }
}
