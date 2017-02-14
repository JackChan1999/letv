package com.letv.redpacketsdk.bean;

public class RedPacketBean implements LetvBaseBean {
    public static final String TYPE_FORCAST = "type_forecast";
    public static final String TYPE_REDPACKET = "type_redpacket";
    private static final long serialVersionUID = 1;
    public String actionUrl;
    public String endTime;
    public EntryLocation entryLocation = new EntryLocation();
    public String getGiftButtonString;
    public String giftId;
    public String id;
    public String mobilePic;
    public String nextRedPacketTime;
    public String pic1;
    public ShareBean shareBean = new ShareBean();
    public String startTime;
    public String title;
    public String type;

    public String toString() {
        String string = "";
        if (TYPE_FORCAST.equals(this.type)) {
            string = "RedPacketBean: \n type=" + this.type + ";\n id=" + this.id + ";\n pic1=" + this.pic1 + ";\n mobilePic=" + this.mobilePic;
        }
        if (TYPE_REDPACKET.equals(this.type)) {
            return "RedPacketBean: \n type=" + this.type + ";\n id=" + this.id + ";\n giftId=" + this.giftId + ";\n title=" + this.title + ";\n pic1=" + this.pic1 + ";\n mobilePic=" + this.mobilePic + ";\n shareUrl=" + this.shareBean.shareUrl + ";\n sharepic=" + this.shareBean.sharePic + ";\n shareTile=" + this.shareBean.shareTitle + ";\n startTime=" + this.startTime + ";\n endTime=" + this.endTime + ";\n actionUrl=" + this.actionUrl + ";\n nextRedPacketTime=" + this.nextRedPacketTime + ";\n getGiftButtonString=" + this.getGiftButtonString + ";\n entryLocation=" + this.entryLocation.toString();
        }
        return string;
    }
}
