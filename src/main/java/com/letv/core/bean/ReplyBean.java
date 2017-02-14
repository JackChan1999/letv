package com.letv.core.bean;

import com.letv.core.bean.CommentBean.Source;
import com.letv.core.bean.CommentBean.User;

public class ReplyBean implements LetvBaseBean {
    public String _id;
    public String city;
    public String commentid;
    public String content;
    public long ctime;
    public int flag;
    public boolean isLike;
    public boolean isOpen = true;
    public int like;
    public ReplyBean reply;
    public Source source;
    public User user;
    public String vtime;

    public String toString() {
        return "LetvUser [_id=" + this._id + ", commentid=" + this.commentid + ", content=" + this.content + ", vtime=" + this.vtime + ", ctime=" + this.ctime + ", city=" + this.city + ", like=" + this.like + ", isLike=" + this.isLike + "]";
    }
}
