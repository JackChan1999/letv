package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class OtaVersionBean implements LetvHttpBaseModel {
    private String curVersion;
    private String hasNew;
    private String isForce;
    private String nextFeature;
    private String nextVersion;

    public String getCurVersion() {
        return this.curVersion;
    }

    public void setCurVersion(String curVersion) {
        this.curVersion = curVersion;
    }

    public String getHasNew() {
        return this.hasNew;
    }

    public void setHasNew(String hasNew) {
        this.hasNew = hasNew;
    }

    public String getNextVersion() {
        return this.nextVersion;
    }

    public void setNextVersion(String nextVersion) {
        this.nextVersion = nextVersion;
    }

    public String getNextFeature() {
        return this.nextFeature;
    }

    public void setNextFeature(String nextFeature) {
        this.nextFeature = nextFeature;
    }

    public String getIsForce() {
        return this.isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String toString() {
        return "OtaVersionBean [curVersion=" + this.curVersion + ", hasNew=" + this.hasNew + ", nextVersion=" + this.nextVersion + ", nextFeature=" + this.nextFeature + ", isForce=" + this.isForce + "]";
    }
}
