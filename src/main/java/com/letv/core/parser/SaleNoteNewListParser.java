package com.letv.core.parser;

import com.letv.core.bean.SaleNoteBean;
import com.letv.core.bean.SaleNoteListBean;
import org.json.JSONArray;
import org.json.JSONObject;

public class SaleNoteNewListParser extends LetvMobileParser<SaleNoteListBean> {
    public SaleNoteListBean parse(JSONObject data) throws Exception {
        SaleNoteListBean list = new SaleNoteListBean();
        JSONObject result = getJSONObject(data, "result");
        if (result != null) {
            list.code = getString(result, "code");
            list.msg = getString(result, "msg");
            JSONObject values = getJSONObject(result, "values");
            if (values != null) {
                list.totalCount = getString(values, "totalCount");
                JSONArray orderList = getJSONArray(values, "orderList");
                if (orderList != null && orderList.length() > 0) {
                    for (int i = 0; i < orderList.length(); i++) {
                        JSONObject object = orderList.getJSONObject(i);
                        SaleNoteBean sale = new SaleNoteBean();
                        sale.id = getString(object, "id");
                        sale.status = getString(object, "status");
                        sale.cancelTime = getString(object, "cancelTime");
                        sale.payType = getString(object, "payType");
                        sale.money = getString(object, "money");
                        sale.orderName = getString(object, "orderName");
                        sale.addTime = getString(object, "addTime");
                        sale.moneyDes = getString(object, "moneyDes");
                        list.add(sale);
                    }
                }
            }
        }
        return list;
    }
}
