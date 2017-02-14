package com.letv.core.bean;

import java.util.LinkedList;
import java.util.List;

public class CommentBean implements LetvBaseBean {
    public static final int VOTE_STATE_LEFT = 1;
    public static final int VOTE_STATE_RIGHT = 2;
    public static final int VOTE_STATE_UNSELECT = 0;
    private static final long serialVersionUID = 1;
    public String _id;
    public String cid;
    public String city;
    public String content;
    public long ctime;
    public int flag;
    public int htime;
    public String img;
    public List<String> imgPack;
    public boolean isLike;
    public int level;
    public int like;
    public String pid;
    public int replyPage = 1;
    public int replynum;
    public LinkedList<ReplyBean> replys;
    public int share;
    public Source source;
    public String title;
    public User user;
    public int voteFlag;
    public String vtime;
    public String xid;

    public static class COMMENT_LEVEL {
        public static final int JINHUA = 1;
        public static final int NORMAL = 0;
        public static final int SHENPING = 2;
    }

    public static class User {
        public boolean isStar;
        public int isvip;
        public String photo;
        public String ssouid;
        public String uid;
        public String username;
    }

    public String toString() {
        return "LetvUser [_id=" + this._id + ", content=" + this.content + ", vtime=" + this.vtime + ", ctime=" + this.ctime + ", city=" + this.city + ", replynum=" + this.replynum + ", share=" + this.share + ", like=" + this.like + ", img=" + this.img + ", htime=" + this.htime + ", pid=" + this.pid + ", xid=" + this.xid + ", cid=" + this.cid + ", title=" + this.title + "]";
    }
}
