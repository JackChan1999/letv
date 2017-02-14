package com.letv.core.parser;

import com.letv.core.bean.VipProductBean;
import com.letv.core.bean.VipProductBean.ProductBean;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class VipProductParser extends LetvMobileParser<VipProductBean> {
    private VipProductBean mVipProductBean;

    protected VipProductBean parse(JSONObject data) throws Exception {
        this.mVipProductBean = new VipProductBean();
        parseProductList(getJSONArray(data, "productList"));
        this.mVipProductBean.mMobilePic = getString(getJSONObject(data, "focusPic"), "mobilePic");
        return this.mVipProductBean;
    }

    private void parseProductList(JSONArray productList) {
        if (productList != null && productList.length() > 0) {
            ArrayList<ProductBean> mProductList = new ArrayList();
            for (int i = 0; i <= productList.length(); i++) {
                JSONObject object = getJSONObject(productList, i);
                if (object != null) {
                    ProductBean mProductBean = new ProductBean();
                    mProductBean.mName = getString(object, "name");
                    mProductBean.mMobileIma = getString(object, "mobileImg");
                    mProductBean.mVipDesc = getString(object, "vipDesc");
                    mProductBean.currentPrice = getFloat(object, "currentPrice");
                    mProductBean.originPrice = getFloat(object, "originPrice");
                    mProductBean.mUseCurrentPrice = getBoolean(object, "useCurrentPrice");
                    mProductBean.mDays = getInt(object, "days");
                    mProductBean.mLable = getString(object, "label");
                    mProductBean.mPackageText = getString(object, "packageText");
                    mProductBean.mExpire = getString(object, "expire");
                    mProductBean.mMonthType = getInt(object, "monthType");
                    mProductBean.mActivityPackageId = getString(object, "activityPackageId");
                    mProductBean.leftQuota = getInt(object, "leftQuota");
                    mProductBean.discountPrice = getFloat(object, "discount");
                    mProductBean.mJoinActivity = getBoolean(object, "allowPayLepoint");
                    mProductList.add(mProductBean);
                }
            }
            this.mVipProductBean.mProductList = mProductList;
        }
    }
}
