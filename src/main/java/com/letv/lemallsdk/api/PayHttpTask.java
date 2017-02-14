package com.letv.lemallsdk.api;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.lemallsdk.model.CashierInfo;
import com.letv.lemallsdk.model.ILetvBridge;
import com.letv.lemallsdk.util.EALogger;
import com.letv.lepaysdk.ELePayState;
import com.letv.lepaysdk.LePay.ILePayCallback;
import com.letv.lepaysdk.LePayApi;
import com.letv.lepaysdk.LePayConfig;
import com.letv.lepaysdk.model.TradeInfo;
import java.io.UnsupportedEncodingException;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class PayHttpTask {
    public static Context context;
    private static String currency;
    private static String hb_fq;
    private static String input_charset;
    private static String ip;
    private static String key_index;
    private static String merchant_business_id;
    private static String merchant_no;
    private static String notify_url;
    private static String out_trade_no;
    private static String pay_expire;
    private static String price;
    private static String product_desc;
    private static String product_id;
    private static String product_name;
    private static String service;
    private static String sign;
    private static String sign_type;
    public static int times = 0;
    private static String timestamp;
    private static String tradeinfo;
    private static String user_id;
    private static String version;

    class AnonymousClass1 extends ILetvBridge {
        private final /* synthetic */ String val$orderId;

        AnonymousClass1(String str) {
            this.val$orderId = str;
        }

        public void goldData(Object successObj) {
            if (successObj != null) {
                CashierInfo cashierInfo = (CashierInfo) successObj;
                if (cashierInfo == null) {
                    return;
                }
                if ("200".equals(cashierInfo.getStatus())) {
                    PayHttpTask.version = cashierInfo.getVersion();
                    PayHttpTask.service = cashierInfo.getService();
                    PayHttpTask.merchant_business_id = cashierInfo.getMerchant_business_id();
                    PayHttpTask.user_id = cashierInfo.getUser_id();
                    PayHttpTask.notify_url = cashierInfo.getNotify_url();
                    PayHttpTask.merchant_no = cashierInfo.getMerchant_no();
                    PayHttpTask.out_trade_no = cashierInfo.getOut_trade_no();
                    PayHttpTask.price = cashierInfo.getPrice();
                    PayHttpTask.currency = cashierInfo.getCurrency();
                    PayHttpTask.pay_expire = cashierInfo.getPay_expire();
                    PayHttpTask.product_id = cashierInfo.getProduct_id();
                    PayHttpTask.product_name = cashierInfo.getProduct_name();
                    PayHttpTask.product_desc = cashierInfo.getProduct_desc();
                    PayHttpTask.timestamp = cashierInfo.getTimestamp();
                    PayHttpTask.key_index = cashierInfo.getKey_index();
                    PayHttpTask.input_charset = cashierInfo.getInput_charset();
                    PayHttpTask.ip = cashierInfo.getIp();
                    PayHttpTask.sign_type = cashierInfo.getSign_type();
                    PayHttpTask.sign = cashierInfo.getSign();
                    PayHttpTask.hb_fq = cashierInfo.getHb_fq();
                    if (PayHttpTask.isNUll()) {
                        LemallPlatform.getInstance().payOrderId = this.val$orderId;
                        LemallPlatform.getInstance().payOrderPrice = PayHttpTask.price;
                        PayHttpTask.goTopaySdk();
                        return;
                    }
                    return;
                }
                Toast.makeText(PayHttpTask.context, cashierInfo.getMessage(), 1).show();
            }
        }
    }

    public static void getOrderDetail(String orderId, Context con) {
        context = con;
        getCashierInfo(orderId);
    }

    public static void getCashierInfo(String orderId) {
        HttpTask.getCashier(orderId, new AnonymousClass1(orderId));
    }

    public static void goTopaySdk() {
        if (setParms()) {
            LePayConfig lePayConfig = new LePayConfig();
            lePayConfig.hasShowTimer = true;
            lePayConfig.hasShowPaySuccess = false;
            LePayApi.initConfig(context, lePayConfig);
            Log.i("tradeinfo", tradeinfo);
            LePayApi.doPay(context, tradeinfo, new ILePayCallback() {
                public void payResult(ELePayState status, String message) {
                    if (ELePayState.CANCEL == status) {
                        Toast.makeText(PayHttpTask.context, message, 1).show();
                    } else if (ELePayState.FAILT == status) {
                        Toast.makeText(PayHttpTask.context, message, 1).show();
                    } else if (ELePayState.OK == status) {
                        EALogger.i("CALLBACK_SUCCESS", "CALLBACK_SUCCESSCALLBACK_SUCCESSCALLBACK_SUCCESSCALLBACK_SUCCESS");
                        LemallPlatform.getInstance().paySuccess = Boolean.valueOf(true);
                    } else {
                        ELePayState eLePayState = ELePayState.WAITTING;
                    }
                }
            });
        }
    }

    public static boolean isNUll() {
        if (!TextUtils.isEmpty(version) || !TextUtils.isEmpty(user_id) || !TextUtils.isEmpty(notify_url) || !TextUtils.isEmpty(out_trade_no) || !TextUtils.isEmpty(price) || !TextUtils.isEmpty(pay_expire) || !TextUtils.isEmpty(product_id) || !TextUtils.isEmpty(product_name) || !TextUtils.isEmpty(product_desc) || !TextUtils.isEmpty(key_index) || !TextUtils.isEmpty(sign) || !TextUtils.isEmpty(sign_type)) {
            return true;
        }
        Toast.makeText(context, "参数填写不完整,请填写完整", 1).show();
        return false;
    }

    public static boolean setParms() {
        if (isNUll()) {
            if (priceIsOK()) {
                EALogger.e("tag", "测试是否进入");
                try {
                    tradeinfo = setTradeInfo();
                    return true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "总金额应在0~999999.99之间 ", 1).show();
                return false;
            }
        }
        return false;
    }

    public static boolean priceIsOK() {
        double money = Double.parseDouble(price);
        if (money < 0.0d || money > 999999.99d) {
            return false;
        }
        return true;
    }

    public static String setTradeInfo() throws UnsupportedEncodingException {
        StringBuffer params = new StringBuffer("");
        params.append("version").append(SearchCriteria.EQ).append(version);
        params.append("&").append(NotificationCompat.CATEGORY_SERVICE).append(SearchCriteria.EQ).append(service);
        params.append("&").append("merchant_business_id").append(SearchCriteria.EQ).append(merchant_business_id);
        params.append("&").append("user_id").append(SearchCriteria.EQ).append(user_id);
        params.append("&").append("notify_url").append(SearchCriteria.EQ).append(notify_url);
        params.append("&").append(TradeInfo.merchant_no).append(SearchCriteria.EQ).append(merchant_no);
        params.append("&").append(TradeInfo.TRADE_NO).append(SearchCriteria.EQ).append(out_trade_no);
        params.append("&").append("price").append(SearchCriteria.EQ).append(price);
        params.append("&").append("currency").append(SearchCriteria.EQ).append(currency);
        params.append("&").append("pay_expire").append(SearchCriteria.EQ).append(pay_expire);
        params.append("&").append("product_id").append(SearchCriteria.EQ).append(product_id);
        params.append("&").append("product_name").append(SearchCriteria.EQ).append(product_name);
        params.append("&").append("product_desc").append(SearchCriteria.EQ).append(product_desc);
        params.append("&").append("timestamp").append(SearchCriteria.EQ).append(timestamp);
        params.append("&").append("key_index").append(SearchCriteria.EQ).append(key_index);
        params.append("&").append("input_charset").append(SearchCriteria.EQ).append(input_charset);
        params.append("&").append("ip").append(SearchCriteria.EQ).append(ip);
        params.append("&").append(TradeInfo.SIGN).append(SearchCriteria.EQ).append(sign);
        params.append("&").append("sign_type").append(SearchCriteria.EQ).append(sign_type);
        params.append("&").append("hb_fq").append(SearchCriteria.EQ).append(hb_fq);
        return params.toString();
    }
}
