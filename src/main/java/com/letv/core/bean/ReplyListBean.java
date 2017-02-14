package com.letv.core.bean;

import java.util.LinkedList;

public class ReplyListBean implements LetvBaseBean {
    public LinkedList<ReplyBean> data;
    public String errorMsg;
    public int rule;
    public String status;
    public int total;

    public String toString() {
        return "LetvUser [status=" + this.status + ", total=" + this.total + ", rule=" + this.rule + "]";
    }
}
