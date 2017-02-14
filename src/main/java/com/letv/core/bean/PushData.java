package com.letv.core.bean;

import java.util.ArrayList;

public class PushData implements LetvBaseBean {
    public static String KEY_CID = "cid";
    public static String KEY_LIVEENDDATE = "liveEndDate";
    private static final long serialVersionUID = 2147020033763766953L;
    public Activatemsg activatemsg;
    public String albumId;
    public int at;
    public String bigImg;
    public String bigImgSubtitle;
    public String bigImgTitle;
    public String cid;
    public String contentStyle;
    public long id;
    public String isActivate;
    public String isOnDeskTop;
    public String isSound;
    public String isVibrate;
    public String liveEndDate;
    public boolean mHasPushMsg = false;
    public String msg;
    public String picUrl;
    public ArrayList<PushBookLive> pushBookLives;
    public int pushtime;
    public SMSMessage smsMessage;
    public int type;

    public static class Activatemsg {
        public String message;
        public int silent;

        public String toString() {
            return "Activatemsg [message=" + this.message + ", silent=" + this.silent + "]";
        }
    }

    public static class SMSMessage {
        public String id;
        public String image;
        public int isshow;
        public String message;
        public String phonenum;
        public String url;

        public String toString() {
            return "SMSMessage [id=" + this.id + ", message=" + this.message + ", phonenum=" + this.phonenum + ", image=" + this.image + ", url=" + this.url + ", isshow=" + this.isshow + "]";
        }
    }

    public boolean isNull() {
        return this.id == -1 || this.id == 0;
    }

    public String toString() {
        return "PushData [id=" + this.id + ", msg=" + this.msg + ", albumId=" + this.albumId + ", type=" + this.type + ", at=" + this.at + ", pushtime=" + this.pushtime + ", pushBookLives=" + this.pushBookLives + ", activatemsg=" + this.activatemsg.toString() + ", smsMessage=" + this.smsMessage.toString() + ", liveEndDate=" + this.liveEndDate + ", cid=" + this.cid + ", picUrl=" + this.picUrl + ", isActivate=" + this.isActivate + ", isOnDeskTop=" + this.isOnDeskTop + ", bigImg=" + this.bigImg + ", bigImgTitle=" + this.bigImgTitle + ", bigImgBubtitle=" + this.bigImgSubtitle + ", contentStyle=" + this.contentStyle + "]";
    }
}
