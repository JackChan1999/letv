package com.letv.lepaysdk.model;

import android.os.Process;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class TradeInfo implements Serializable {
    public static final String LEPAY_CONFIG = "lepay_config";
    public static final String LEPAY_TRADEINFO_PARAM = "lepay_tradeinfo_param";
    public static final String SIGN = "sign";
    public static final String TRADE_KEY = "trade_key";
    public static final String TRADE_NO = "out_trade_no";
    public static final String merchant_no = "merchant_no";
    private static final long serialVersionUID = 1;
    private String lepay_order_no;
    private String merchant_business_id;
    private int orderstatus;
    private List<Paymodes> paylist = new ArrayList();
    private String price;
    private String product_urls;
    private String sign;
    private String tradeKey = (Process.myPid() + "_lepay");

    public String getKey() {
        return this.tradeKey;
    }

    public static long getSerialVersionUID() {
        return 1;
    }

    public static String getTradeKey() {
        return TRADE_KEY;
    }

    public static String getMerchatid() {
        return merchant_no;
    }

    public static String getTradeid() {
        return TRADE_NO;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getOrderstatus() {
        return this.orderstatus;
    }

    public void setOrderstatus(int orderstatus) {
        this.orderstatus = orderstatus;
    }

    public List<Paymodes> getPaylist() {
        return this.paylist;
    }

    public void setPaylist(List<Paymodes> paylist) {
        this.paylist = paylist;
    }

    public String getProduct_urls() {
        return this.product_urls;
    }

    public void setProduct_urls(String product_urls) {
        this.product_urls = product_urls;
    }

    public String toString() {
        return super.toString();
    }

    public static TradeInfo fromJsonObject(JSONObject jsonObject) {
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.price = jsonObject.optString("price");
        tradeInfo.lepay_order_no = jsonObject.optString("lepay_order_no");
        tradeInfo.merchant_business_id = jsonObject.optString("merchant_business_id");
        tradeInfo.sign = jsonObject.optString(SIGN);
        tradeInfo.orderstatus = jsonObject.optInt("orderstatus");
        tradeInfo.product_urls = jsonObject.optString("product_urls");
        JSONArray jsonArray = jsonObject.optJSONArray("paylist");
        for (int i = 0; i < jsonArray.length(); i++) {
            tradeInfo.paylist.add(Paymodes.fromJsonObject(jsonArray.optJSONObject(i)));
        }
        return tradeInfo;
    }
}
