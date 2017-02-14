package com.letv.core.bean;

public class MobilePayBean implements LetvBaseBean {
    private static final long serialVersionUID = 2617749042582138999L;
    public String code = "";
    public String command = "";
    public String corderid = "";
    public String msg = "";
    public String posturl = "";
    public String servicecode = "";
    public int type = -1;

    public String toString() {
        return "[type = " + this.type + " , code = " + this.code + " , posturl = " + this.posturl + " , command = " + this.command + " , servicecode = " + this.servicecode + " , corderid = " + this.corderid + " , msg = " + this.msg + "]";
    }
}
