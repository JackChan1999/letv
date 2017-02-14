package com.letv.redpacketsdk.bean;

public class GiftBean implements LetvBaseBean {
    private static final long serialVersionUID = 2;
    public int big_type = -1;
    public String company_name;
    public boolean is_first;
    public String price_image;
    public String title;

    public String toString() {
        return "GiftBean : is_first=" + this.is_first + "\n big_type=" + this.big_type + "\n title=" + this.title + "\n price_image=" + this.price_image + "\n company_name=" + this.company_name;
    }
}
