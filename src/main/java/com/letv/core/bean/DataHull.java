package com.letv.core.bean;

public class DataHull {
    public int apiState;
    public int dataType;
    public int errMsg;
    public final String[] errorInfo = new String[3];
    public int httpResponseCode = 200;
    public String mServerTime = "";
    public String markId = "";
    public String message = "";
    public String reportErrorString = "";
    public String sourceData = "";

    public final String[] getErrorInfo() {
        return this.errorInfo;
    }

    public void addErrorInfo(int index, String info) {
        if (index < 3 && index >= 0) {
            this.errorInfo[index] = info;
        }
    }
}
