package com.letv.core.bean;

public class TopicSubject implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private int cid;
    private String ctime;
    private String description;
    private String name;
    private String pubName;
    private String tag;
    private int type;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubName() {
        return this.pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCtime() {
        return this.ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
