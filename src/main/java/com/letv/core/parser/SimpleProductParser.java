package com.letv.core.parser;

import com.letv.core.bean.VipSimpleProductBean;
import com.letv.core.bean.VipSimpleProductBean.SimpleProductBean;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SimpleProductParser extends LetvMobileParser<VipSimpleProductBean> {
    private VipSimpleProductBean mVipSimpleProductBean;

    protected VipSimpleProductBean parse(JSONObject data) throws Exception {
        this.mVipSimpleProductBean = new VipSimpleProductBean();
        JSONArray productList = getJSONArray(getJSONObject(data, "result"), "packageList");
        if (productList != null && productList.length() > 0) {
            ArrayList<SimpleProductBean> mSimpleProductList = new ArrayList();
            for (int i = 0; i <= productList.length(); i++) {
                JSONObject object = getJSONObject(productList, i);
                if (object != null) {
                    SimpleProductBean mSimpleProductBean = new SimpleProductBean();
                    mSimpleProductBean.currentPrice = getFloat(object, "currentPrice");
                    mSimpleProductBean.days = getInt(object, "days");
                    mSimpleProductBean.end = getInt(object, "end");
                    mSimpleProductBean.monthType = getInt(object, "monthType");
                    mSimpleProductBean.name = getString(object, "name");
                    mSimpleProductBean.originPrice = getFloat(object, "originPrice");
                    mSimpleProductBean.preSortValue = getInt(object, "preSortValue");
                    mSimpleProductBean.sort = getInt(object, "sort");
                    mSimpleProductBean.subscript = getInt(object, "subscript");
                    mSimpleProductBean.subscriptText = getString(object, "subscriptText");
                    mSimpleProductBean.useCurrentPrice = getBoolean(object, "useCurrentPrice");
                    mSimpleProductBean.vipDesc = getString(object, "vipDesc");
                    mSimpleProductBean.productid = getString(object, "productid");
                    mSimpleProductBean.setLable(getString(object, "label"));
                    mSimpleProductBean.setPackageText(getString(object, "packageText"));
                    mSimpleProductBean.setActivityPakageId(getString(object, "activityPackageId"));
                    mSimpleProductBean.setLeftQuota(getInt(object, "leftQuota"));
                    mSimpleProductBean.setDiscount(getFloat(object, "discount"));
                    mSimpleProductBean.setAllowPayLepoint(getBoolean(object, "allowPayLepoint"));
                    mSimpleProductList.add(mSimpleProductBean);
                }
            }
            this.mVipSimpleProductBean.mSimpleProductList = mSimpleProductList;
        }
        return this.mVipSimpleProductBean;
    }
}
