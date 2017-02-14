package com.letv.core.bean;

import com.letv.core.utils.LogInfo;

public class RedPacketBean implements LetvBaseBean {
    public String activeID;
    public int channelId;
    public String code;
    public String leftButton;
    public String limitNum;
    public String mobilePic;
    public String rightButton;
    public String shareDesc;
    public String sharePic;
    public String shareTitle;
    public String shorDesc;
    public String skipUrl;
    public String subtitle;
    public String title;
    public int uid;
    public String url;

    public String toString() {
        LogInfo.log("fornia", "uid" + this.uid + "|mobilePic" + this.mobilePic + "|title" + this.title + "|shorDesc" + this.shorDesc + "|url" + this.url + "|skipUrl" + this.skipUrl + "|activeID" + this.activeID + "|limitNum" + this.limitNum + "|leftButton" + this.leftButton + "|rightButton" + this.rightButton + "|shareTitle" + this.shareTitle + "|shareDesc" + this.shareDesc + "|sharePic" + this.sharePic + "|subtitle" + this.subtitle);
        return super.toString();
    }
}
