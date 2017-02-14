package com.letv.datastatistics.bean;

public class StatisCacheBean {
    private String cacheData;
    private String cacheId;
    private long cacheTime;

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public String getCacheData() {
        return this.cacheData;
    }

    public void setCacheData(String cacheData) {
        this.cacheData = cacheData;
    }

    public long getCacheTime() {
        return this.cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public StatisCacheBean(String cacheId, String cacheData, long cacheTime) {
        this.cacheId = cacheId;
        this.cacheData = cacheData;
        this.cacheTime = cacheTime;
    }

    public String toString() {
        return "LocalCacheBean [cacheId=" + this.cacheId + ", cacheData=" + this.cacheData.length() + ", cacheTime=" + this.cacheTime + "]";
    }
}
