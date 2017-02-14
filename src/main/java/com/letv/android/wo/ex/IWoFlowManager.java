package com.letv.android.wo.ex;

import android.content.Context;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;

public interface IWoFlowManager {

    public enum ORDER_STATE {
        NOT_ORDER,
        ORDER,
        UNORDER
    }

    void clear(Context context);

    String getDeadline();

    String getMessages(Context context);

    String getMobilePhoneNumber(Context context);

    void getMonthlyPaymentOrderInfo(Context context);

    String getNotice(Context context);

    String getPhoneNum(Context context);

    void getSyncOrderShipInfo(Context context, Object... objArr);

    void getSysBusirange(Context context);

    void getSysTermsOfService(Context context);

    ORDER_STATE getUserOrderInfo(Context context);

    void identifyWoVideoSDK(Context context, String str, int i, LetvWoFlowListener letvWoFlowListener);

    void initSDK(Context context, Object... objArr);

    boolean isGetNumSuccess();

    boolean isSmsGetNumSuccess();

    void isUserOrder(Context context, LetvWoFlowListener letvWoFlowListener, boolean z, boolean z2);

    boolean isUserOrder();

    boolean isUserUnOrder();

    void queryOrders(Context context);

    void sendSms(Context context, LetvWoFlowListener letvWoFlowListener);

    void setGetNumSuccess(boolean z);

    void setSmsGetNumSuccess(boolean z);

    void setUserOrder(boolean z);

    void setUserUnOrder(boolean z);

    void showOrderSureDialog(Context context, LetvWoFlowListener letvWoFlowListener);

    void showSMSVerificationDialog(Context context, Object... objArr);

    void showUnOrderSureDialog(Context context, LetvWoFlowListener letvWoFlowListener);
}
