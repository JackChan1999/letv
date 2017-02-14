package com.letv.core.bean;

import java.util.ArrayList;

public class VipSimpleProductBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public ArrayList<SimpleProductBean> mSimpleProductList = new ArrayList();

    public static class SimpleProductBean implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        private String activityPakageId;
        public boolean allowPayLepoint;
        public float currentPrice;
        public int days;
        public float discount;
        public int end;
        private String lable;
        private int leftQuota;
        public int monthType;
        public String name;
        public float originPrice;
        private String packageText;
        public int preSortValue;
        public String productid;
        public int sort;
        public int subscript;
        public String subscriptText;
        public boolean useCurrentPrice;
        public String vipDesc;

        public float getDiscount() {
            return this.discount;
        }

        public void setDiscount(float discount) {
            this.discount = discount;
        }

        public boolean isAllowPayLepoint() {
            return this.allowPayLepoint;
        }

        public void setAllowPayLepoint(boolean allowPayLepoint) {
            this.allowPayLepoint = allowPayLepoint;
        }

        public String getLable() {
            return this.lable;
        }

        public void setLable(String lable) {
            this.lable = lable;
        }

        public String getPackageText() {
            return this.packageText;
        }

        public void setPackageText(String packageText) {
            this.packageText = packageText;
        }

        public int getLeftQuota() {
            return this.leftQuota;
        }

        public void setLeftQuota(int leftQuota) {
            this.leftQuota = leftQuota;
        }

        public float getCurrentPrice() {
            return this.currentPrice - this.discount;
        }

        public void setCurrentPrice(float currentPrice) {
            this.currentPrice = currentPrice;
        }

        public int getDays() {
            return this.days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getEnd() {
            return this.end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getMonthType() {
            return this.monthType;
        }

        public void setMonthType(int monthType) {
            this.monthType = monthType;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getOriginPrice() {
            return this.originPrice;
        }

        public void setOriginPrice(float originPrice) {
            this.originPrice = originPrice;
        }

        public int getPreSortValue() {
            return this.preSortValue;
        }

        public void setPreSortValue(int preSortValue) {
            this.preSortValue = preSortValue;
        }

        public int getSort() {
            return this.sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getSubscriptText() {
            return this.subscriptText;
        }

        public void setSubscriptText(String subscriptText) {
            this.subscriptText = subscriptText;
        }

        public boolean isUseCurrentPrice() {
            return this.useCurrentPrice;
        }

        public void setUseCurrentPrice(boolean useCurrentPrice) {
            this.useCurrentPrice = useCurrentPrice;
        }

        public String getVipDesc() {
            return this.vipDesc;
        }

        public void setVipDesc(String vipDesc) {
            this.vipDesc = vipDesc;
        }

        public String getProductid() {
            return this.productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public int getSubscript() {
            return this.subscript;
        }

        public void setSubscript(int subscript) {
            this.subscript = subscript;
        }

        public String getActivityPakageId() {
            return this.activityPakageId;
        }

        public void setActivityPakageId(String activityPakageId) {
            this.activityPakageId = activityPakageId;
        }
    }

    public ArrayList<SimpleProductBean> getSimpleProductList() {
        return this.mSimpleProductList;
    }

    public void setSimpleProductList(ArrayList<SimpleProductBean> mSimpleProductList) {
        this.mSimpleProductList = mSimpleProductList;
    }
}
