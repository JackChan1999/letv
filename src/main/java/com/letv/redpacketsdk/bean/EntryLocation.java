package com.letv.redpacketsdk.bean;

public class EntryLocation implements LetvBaseBean {
    private static final long serialVersionUID = 3;
    public String content;
    public int contentType = -1;
    public String extendCid;
    public String extendPid;
    public String extendRange;
    public String extendZid;

    public String toString() {
        return "EntryLocation: contentType =" + this.contentType + "\n content=" + this.content + "\n extendRange=" + this.extendRange + "\n extendCid=" + this.extendCid + "\n extendZid=" + this.extendZid + "\n extendPid=" + this.extendPid;
    }
}
