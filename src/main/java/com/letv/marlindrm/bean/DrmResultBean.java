package com.letv.marlindrm.bean;

public class DrmResultBean {
    private String drmMediaUrl;
    private boolean isLastProSuccess = true;
    private int resultCode;
    private String tokenXmlContent;

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getDrmMediaUrl() {
        return this.drmMediaUrl;
    }

    public void setDrmMediaUrl(String drmMediaUrl) {
        this.drmMediaUrl = drmMediaUrl;
    }

    public boolean isPreProSuccess() {
        return this.isLastProSuccess;
    }

    public void setLastProSuccess(boolean isLastProSuccess) {
        this.isLastProSuccess = isLastProSuccess;
    }

    public String getTokenXmlContent() {
        return this.tokenXmlContent;
    }

    public void setTokenXmlContent(String tokenXmlContent) {
        this.tokenXmlContent = tokenXmlContent;
    }

    public String toString() {
        return "DrmResultBean [resultCode=" + this.resultCode + ", drmMediaUrl=" + this.drmMediaUrl + "]";
    }
}
