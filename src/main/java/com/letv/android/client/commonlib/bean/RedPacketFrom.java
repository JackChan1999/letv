package com.letv.android.client.commonlib.bean;

public class RedPacketFrom {
    public String cid;
    public String content;
    public int from = 1;
    public String pageid;
    public String pid;
    public String zid;

    public RedPacketFrom(int from) {
        this.from = from;
    }

    public RedPacketFrom(int from, String cid) {
        this.from = from;
        this.cid = cid;
    }

    public RedPacketFrom(int from, String cid, String pageid) {
        this.from = from;
        this.cid = cid;
        this.pageid = pageid;
    }

    public String toString() {
        return "RedPacketFrom: from=" + this.from + ";content=" + this.content + ";cid=" + this.cid + ";pid=" + this.pid + ";zid=" + this.zid;
    }
}
