package com.letv.core.bean;

import com.letv.core.utils.LogInfo;
import java.util.ArrayList;

public class VipProductBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public String mMobilePic;
    public ArrayList<ProductBean> mProductList = new ArrayList();

    public static class ProductBean implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        public float currentPrice;
        public float discountPrice;
        public int leftQuota;
        public String mActivityPackageId;
        public int mDays;
        public String mExpire;
        public boolean mJoinActivity;
        public String mLable;
        public String mMobileIma;
        public int mMonthType;
        public String mName;
        public String mPackageText;
        public boolean mUseCurrentPrice;
        public String mVipDesc;
        public float originPrice;

        public float getLedianPrice() {
            if (this.mJoinActivity && this.mUseCurrentPrice) {
                return this.currentPrice - this.discountPrice;
            }
            if (this.mJoinActivity && !this.mUseCurrentPrice) {
                return this.originPrice - this.discountPrice;
            }
            if (!this.mJoinActivity && this.mUseCurrentPrice) {
                return this.currentPrice;
            }
            if (this.mJoinActivity || this.mUseCurrentPrice) {
                return 0.0f;
            }
            return this.originPrice;
        }

        public float getNormalPrice() {
            LogInfo.log(" getNormalPrice  currentPrice == " + this.currentPrice + " discountPrice ==  " + this.discountPrice);
            return this.currentPrice - this.discountPrice;
        }
    }
}
