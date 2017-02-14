package com.letv.core.utils.external.alipay;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.letv.base.R;

public class AlipayUtils {

    public static class PayChannel {
        public static final String CLIENT = "client";
        public static final String WAP = "wap";
    }

    public static class PayFlag {
        public static final String CHARGE = "1";
        public static final String CONSUME = "2";
    }

    public static class TermType {
        public static final String MOBILE = "2";
        public static final String TV = "1";
    }

    public static boolean checkMobileSecurePayHelper(Context context) {
        return new MobileSecurePayHelper(context).detectMobile_sp();
    }

    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public static RequestValue getRequestValue(String data, String productid, String productname, String payflag, String price, String desc, String payChannel, String termType, String userid, String username, String pid, String svip, String activityId, String payWay, String renewFlag) {
        RequestValue rValue = new RequestValue();
        rValue.setOrderId(data);
        rValue.setProductid(productid);
        rValue.setPid(pid);
        rValue.setPayflag(payflag);
        rValue.setPrice(price);
        rValue.setProductname(productname);
        rValue.setDesc(desc);
        rValue.setPayChannel(payChannel);
        rValue.setTermType(termType);
        rValue.setUserid(userid);
        rValue.setUsername(username);
        rValue.setSvip(svip);
        rValue.setActivityId(activityId);
        rValue.setPayWay(payWay);
        rValue.setRenewFlag(renewFlag);
        return rValue;
    }

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, 0).show();
    }

    public static void showToast(Activity activity, int msg) {
        Toast.makeText(activity, msg, 0).show();
    }

    public static String aliPayErrorMsg(String errorcode, Context context) {
        if (errorcode.equals(String.valueOf(4000))) {
            return context.getResources().getString(R.string.SYSTEMEXCEPTION);
        }
        if (errorcode.equals(String.valueOf(4001))) {
            return context.getResources().getString(R.string.DATA_FORMAT_ERROR);
        }
        if (errorcode.equals(String.valueOf(4003))) {
            return context.getResources().getString(R.string.ACCOUNT_FREEZ);
        }
        if (errorcode.equals(String.valueOf(4004))) {
            return context.getResources().getString(R.string.USER_UNBIND);
        }
        if (errorcode.equals(String.valueOf(4005))) {
            return context.getResources().getString(R.string.UNBIND_FAILE);
        }
        if (errorcode.equals(String.valueOf(4006))) {
            return context.getResources().getString(R.string.ORDER_PAY_FAILE);
        }
        if (errorcode.equals(String.valueOf(4010))) {
            return context.getResources().getString(R.string.REBUNBIND);
        }
        if (errorcode.equals(String.valueOf(6000))) {
            return context.getResources().getString(R.string.PAY_SEERVER_UPGRADE);
        }
        if (errorcode.equals(String.valueOf(6001))) {
            return context.getResources().getString(R.string.PAY_CANCEL);
        }
        if (errorcode.equals(String.valueOf(6002))) {
            return context.getResources().getString(R.string.NET_ERROR);
        }
        return context.getResources().getString(R.string.PAY_ERROR);
    }
}
