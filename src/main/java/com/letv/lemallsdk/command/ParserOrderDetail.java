package com.letv.lemallsdk.command;

import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.OrderdetailInfo;
import com.letv.lemallsdk.util.EALogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParserOrderDetail extends BaseParse {
    private List<OrderdetailInfo> oderItemList;

    public BaseBean Json2Entity(String content) {
        OrderdetailInfo orderDetailInfo = new OrderdetailInfo();
        try {
            JSONObject jo = new JSONObject(content);
            EALogger.i("js", "订单详情接口：：" + content);
            this.oderItemList = new ArrayList();
            orderDetailInfo.setStatus(jo.optString("status"));
            orderDetailInfo.setMessage(jo.optString("message"));
            JSONArray order_result = jo.optJSONArray("result");
            if (order_result != null) {
                for (int i = 0; i < order_result.length(); i++) {
                    JSONObject order = order_result.optJSONObject(i).optJSONObject("ORDER");
                    if (order != null) {
                        orderDetailInfo.setON_AMOUNT(order.optString("ON_AMOUNT"));
                    }
                    JSONArray sinleproduct = order_result.optJSONObject(i).optJSONObject("ORDERITEMS").optJSONArray("SINGLEPRODUCT");
                    if (sinleproduct != null) {
                        for (int m = 0; m < sinleproduct.length(); m++) {
                            OrderdetailInfo bean = new OrderdetailInfo();
                            JSONObject index = sinleproduct.optJSONObject(m);
                            bean.setIS_MAIN(index.optInt("IS_MAIN"));
                            bean.setPRODUCT_NAME(index.optString("PRODUCT_NAME"));
                            JSONObject product = index.optJSONObject("PRODUCTS");
                            if (product != null) {
                                bean.setImageSrc(product.optString("imageSrc"));
                            } else {
                                bean.setImageSrc("");
                            }
                            this.oderItemList.add(bean);
                        }
                    }
                }
                orderDetailInfo.setOderItemList(this.oderItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetailInfo;
    }
}
