package com.letv.datastatistics.bean;

public class StatisticsPlayInfo {
    private String ac;
    private long cTime = 0;
    private String cid;
    private int ipt = 0;
    private int joint = 0;
    private int pay = 0;
    private String pid;
    private int playTime;
    private String ref;
    private int retry;
    private String ty;
    private int uTime;
    private String url;
    private String uuid;
    private int vLen;
    private String vType;
    private String vid;

    public long getcTime() {
        return this.cTime;
    }

    public void setcTime(long cTime) {
        this.cTime = cTime;
    }

    public int getPay() {
        return this.pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getJoint() {
        return this.joint;
    }

    public void setJoint(int joint) {
        this.joint = joint;
    }

    public int getIpt() {
        return this.ipt;
    }

    public void setIpt(int ipt) {
        this.ipt = ipt;
    }
}
