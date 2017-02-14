package com.letv.core.bean;

import java.util.ArrayList;
import java.util.List;

public class RecommData implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private List<RecommenApp> appList = new ArrayList();
    private List<RecommendColumn> columns = new ArrayList();
    private List<RecommenApp> focusApps = new ArrayList();
    private String markid;
    private String status;
    private int totalNum;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarkid() {
        return this.markid;
    }

    public void setMarkid(String markid) {
        this.markid = markid;
    }

    public int getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<RecommendColumn> getColumns() {
        return this.columns;
    }

    public void setColumns(List<RecommendColumn> columns) {
        this.columns = columns;
    }

    public List<RecommenApp> getFocusApps() {
        return this.focusApps;
    }

    public void setFocusApps(List<RecommenApp> focusApps) {
        this.focusApps = focusApps;
    }

    public List<RecommenApp> getAppList() {
        return this.appList;
    }

    public void setAppList(List<RecommenApp> appList) {
        this.appList = appList;
    }
}
