package com.letv.lemallsdk.model;

public class CashierInfo extends BaseBean {
    private static final long serialVersionUID = -5850497723149760277L;
    private String appId;
    private String currency;
    private String hb_fq;
    private String input_charset;
    private String ip;
    private String key_index;
    private String merchant_business_id;
    private String merchant_no;
    private String message;
    private String notify_url;
    private String out_trade_no;
    private String pay_expire;
    private String price;
    private String product_desc;
    private String product_id;
    private String product_name;
    private String service;
    private String sign;
    private String sign_type;
    private int status;
    private String timestamp;
    private String user_id;
    private String user_name;
    private String version;

    public String getInput_charset() {
        return this.input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getKey_index() {
        return this.key_index;
    }

    public void setKey_index(String key_index) {
        this.key_index = key_index;
    }

    public String getMerchant_business_id() {
        return this.merchant_business_id;
    }

    public void setMerchant_business_id(String merchant_business_id) {
        this.merchant_business_id = merchant_business_id;
    }

    public String getMerchant_no() {
        return this.merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getNotify_url() {
        return this.notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return this.out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPay_expire() {
        return this.pay_expire;
    }

    public void setPay_expire(String pay_expire) {
        this.pay_expire = pay_expire;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_desc() {
        return this.product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSign_type() {
        return this.sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHb_fq() {
        return this.hb_fq;
    }

    public void setHb_fq(String hb_fq) {
        this.hb_fq = hb_fq;
    }

    public String toString() {
        return "CashierInfo [input_charset=" + this.input_charset + ", key_index=" + this.key_index + ", merchant_business_id=" + this.merchant_business_id + ", merchant_no=" + this.merchant_no + ", notify_url=" + this.notify_url + ", out_trade_no=" + this.out_trade_no + ", pay_expire=" + this.pay_expire + ", price=" + this.price + ", product_desc=" + this.product_desc + ", product_id=" + this.product_id + ", product_name=" + this.product_name + ", sign_type=" + this.sign_type + ", timestamp=" + this.timestamp + ", user_id=" + this.user_id + ", user_name=" + this.user_name + ", version=" + this.version + ", status=" + this.status + ", message=" + this.message + "]";
    }
}
