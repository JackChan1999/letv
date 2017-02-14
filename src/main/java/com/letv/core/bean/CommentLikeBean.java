package com.letv.core.bean;

public class CommentLikeBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public String errorMsg;
    public int like;
    public String status;

    public String toString() {
        return "LetvUser [status=" + this.status + ", like=" + this.like + "]";
    }
}
