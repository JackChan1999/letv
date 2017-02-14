package com.letv.lepaysdk.model;

import com.letv.core.constant.PlayConstant;
import com.tencent.open.SocialConstants;
import java.io.Serializable;
import org.json.JSONObject;

public class Paymodes implements Serializable {
    private String bind_id;
    private String card_no;
    private String channel_id;
    private String desc;
    private String display;
    private String hasChecked;
    private String icon_url;
    private String name;
    private String pay_type;
    private String phone_no;
    private String send_sms_flag;

    public String getChannel_id() {
        return this.channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBind_id() {
        return this.bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }

    public String getPay_type() {
        return this.pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return this.icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getSend_sms_flag() {
        return this.send_sms_flag;
    }

    public void setSend_sms_flag(String send_sms_flag) {
        this.send_sms_flag = send_sms_flag;
    }

    public String getCard_no() {
        return this.card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPhone_no() {
        return this.phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getHasChecked() {
        return this.hasChecked;
    }

    public void setHasChecked(String hasChecked) {
        this.hasChecked = hasChecked;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String toString() {
        return super.toString();
    }

    public static Paymodes fromJsonObject(JSONObject jsonObject) {
        Paymodes paymodes = new Paymodes();
        paymodes.desc = jsonObject.optString(SocialConstants.PARAM_APP_DESC);
        paymodes.bind_id = jsonObject.optString("bind_id");
        paymodes.pay_type = jsonObject.optString("pay_type");
        paymodes.name = jsonObject.optString("name");
        paymodes.icon_url = jsonObject.optString("icon_url");
        paymodes.send_sms_flag = jsonObject.optString("send_sms_flag");
        paymodes.card_no = jsonObject.optString("card_no");
        paymodes.channel_id = jsonObject.optString(PlayConstant.LIVE_CHANNEL_ID);
        paymodes.phone_no = jsonObject.optString("phone_no");
        paymodes.display = jsonObject.optString("display");
        return paymodes;
    }
}
