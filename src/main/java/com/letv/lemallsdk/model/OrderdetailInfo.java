package com.letv.lemallsdk.model;

import java.util.ArrayList;
import java.util.List;

public class OrderdetailInfo extends BaseBean {
    private static final long serialVersionUID = -2521564981239818874L;
    private int IS_MAIN;
    private String ON_AMOUNT;
    private String PRODUCT_NAME;
    private String imageSrc;
    private List<OrderdetailInfo> oderItemList = new ArrayList();

    public int getIS_MAIN() {
        return this.IS_MAIN;
    }

    public void setIS_MAIN(int iS_MAIN) {
        this.IS_MAIN = iS_MAIN;
    }

    public String getPRODUCT_NAME() {
        return this.PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String pRODUCT_NAME) {
        this.PRODUCT_NAME = pRODUCT_NAME;
    }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public List<OrderdetailInfo> getOderItemList() {
        return this.oderItemList;
    }

    public void setOderItemList(List<OrderdetailInfo> oderItemList) {
        this.oderItemList = oderItemList;
    }

    public String getON_AMOUNT() {
        return this.ON_AMOUNT;
    }

    public void setON_AMOUNT(String oN_AMOUNT) {
        this.ON_AMOUNT = oN_AMOUNT;
    }
}
