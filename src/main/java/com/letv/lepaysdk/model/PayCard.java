package com.letv.lepaysdk.model;

import android.text.TextUtils;
import java.io.Serializable;

public class PayCard implements Serializable {
    private String bankcode;
    private String bankname;
    private String bind_id;
    private String cardno;
    private String change;
    private String channel_id;
    private String cvv2;
    private String idcard;
    private String lepay_order_no;
    private String merchant_business_id;
    private String owner;
    private String phone;
    private String sendby;
    private String validdate;
    private String verifycode;

    public String getBankcode() {
        return this.bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getCardno() {
        return this.cardno;
    }

    public void setCardno(String cardno) {
        if (TextUtils.isEmpty(cardno)) {
            this.cardno = cardno;
        } else {
            this.cardno = cardno.replace(" ", "");
        }
    }

    public String getCvv2() {
        return this.cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getLepay_order_no() {
        return this.lepay_order_no;
    }

    public void setLepay_order_no(String lepay_order_no) {
        this.lepay_order_no = lepay_order_no;
    }

    public String getMerchant_business_id() {
        return this.merchant_business_id;
    }

    public void setMerchant_business_id(String merchant_business_id) {
        this.merchant_business_id = merchant_business_id;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            this.phone = phone;
        } else {
            this.phone = phone.replace(" ", "");
        }
    }

    public String getValiddate() {
        return this.validdate;
    }

    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }

    public String getVerifycode() {
        return this.verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBind_id() {
        return this.bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }

    public String getChannel_id() {
        return this.channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getSendby() {
        return this.sendby;
    }

    public void setSendby(String sendby) {
        this.sendby = sendby;
    }

    public String getChange() {
        return this.change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String toString() {
        return "[ PayCard bankcode=" + this.bankcode + ", bankname=" + this.bankname + " ,cardno=" + this.cardno + " ,cvv2=" + this.cvv2 + " " + ",lepay_order_no=" + this.lepay_order_no + " ,merchant_business_id=" + this.merchant_business_id + ",phone=" + this.phone + " , validdate=" + this.validdate + " ,verifycode=" + this.verifycode + " " + ",idcard=" + this.idcard + " ,owner=" + this.owner + " ,bind_id=" + this.bind_id + ", channel_id=" + this.channel_id + ", sendby= " + this.sendby + ",change=" + this.change + "]";
    }
}
