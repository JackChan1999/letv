package com.letv.lemallsdk.command;

import android.support.v4.app.NotificationCompat;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.CashierInfo;
import com.letv.lemallsdk.util.EALogger;
import com.letv.lepaysdk.model.TradeInfo;
import master.flame.danmaku.danmaku.parser.IDataSource;
import org.json.JSONObject;

public class ParserCashier extends BaseParse {
    private CashierInfo cashierInfo;

    public BaseBean Json2Entity(String content) {
        this.cashierInfo = new CashierInfo();
        EALogger.i(IDataSource.SCHEME_HTTP_TAG, "收银台接口数据：" + content);
        try {
            JSONObject jo = new JSONObject(content);
            this.cashierInfo.setStatus(jo.optString("status"));
            this.cashierInfo.setMessage(jo.optString("message"));
            JSONObject result = jo.optJSONObject("result");
            if (result == null) {
                return this.cashierInfo;
            }
            JSONObject object = result.optJSONObject("payMentResult");
            if (object != null) {
                this.cashierInfo.setVersion(object.optString("version"));
                this.cashierInfo.setService(object.optString(NotificationCompat.CATEGORY_SERVICE));
                this.cashierInfo.setMerchant_business_id(object.optString("merchantBusinessId"));
                this.cashierInfo.setUser_id(object.optString(UserInfoDb.USER_ID));
                this.cashierInfo.setNotify_url(object.optString("notifyUrl"));
                this.cashierInfo.setMerchant_no(object.optString("merchantNo"));
                this.cashierInfo.setOut_trade_no(object.optString("outTradeNo"));
                this.cashierInfo.setPrice(object.optString("price"));
                this.cashierInfo.setCurrency(object.optString("currency"));
                this.cashierInfo.setPay_expire(object.optString("payExpire"));
                this.cashierInfo.setProduct_id(object.optString("productId"));
                this.cashierInfo.setProduct_name(object.optString("productName"));
                this.cashierInfo.setProduct_desc(object.optString("productDesc"));
                this.cashierInfo.setTimestamp(object.optString("timestamp"));
                this.cashierInfo.setKey_index(object.optString("keyIndex"));
                this.cashierInfo.setInput_charset(object.optString("inputCharset"));
                this.cashierInfo.setIp(object.optString("ip"));
                this.cashierInfo.setSign_type(object.optString("signType"));
                this.cashierInfo.setSign(object.optString(TradeInfo.SIGN));
                this.cashierInfo.setHb_fq(object.optString("hbFq"));
            }
            return this.cashierInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isNotNull(JSONObject list) {
        if (list == null || list.length() <= 0) {
            return false;
        }
        return true;
    }
}
