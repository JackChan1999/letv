package com.letv.core.bean;

public class RechargeRecordBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public String chargetime = "";
    public String chargetype = "lk";
    public String desc = "";
    public String money = "";
    public String orderid = "";
    public String point = "";

    public String toString() {
        return "RechargeRecord [chargetime=" + this.chargetime + ", chargetype=" + this.chargetype + ", desc=" + this.desc + ", money=" + this.money + ", orderid=" + this.orderid + ", point=" + this.point + "]";
    }
}
