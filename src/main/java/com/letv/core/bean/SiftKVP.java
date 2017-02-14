package com.letv.core.bean;

public class SiftKVP implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public String filterKey;
    public String id;
    public String key;
    public String keyReplace;
    public String opposite;

    public SiftKVP(String key, String id, String filterKey) {
        this.key = key;
        this.id = id;
        this.filterKey = filterKey;
    }
}
