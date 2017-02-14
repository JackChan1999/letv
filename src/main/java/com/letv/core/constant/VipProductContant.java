package com.letv.core.constant;

import com.letv.core.bean.PaymentMethodBean.PaymentMethod;
import com.letv.core.bean.VipSimpleProductBean.SimpleProductBean;

public class VipProductContant {
    public static final String ACTION_HONGKONG_VIP_PAY = "letvclient://hongkongPayAction";
    public static final String ACTION_VIP_AUTH_PASS = "VIP_AUTH_PASS";
    public static final String NORMAL_VIP = "1";
    public static final int PAY = 17;
    public static final int PAY_FAILURE = 258;
    public static final int PAY_SUCCESS = 257;
    public static final int PAY_SUCCESS_SEE_MOVIE = 3;
    public static final int PAY_WITH_LOGINED = 18;
    public static final int PAY_WITH_UNLOGIN = 19;
    public static final String SENIOR_VIP = "9";
    private static PaymentMethod mDefaultPayMethodBean;
    private static SimpleProductBean mDefaultSimpleProductBean;
    public static PaySuccessType mPaySuccessType = PaySuccessType.NORMAL;
    private static String mProductDes;
    private static String mProductName;
    private static String mTitle;
    private static String mUrl;
    private static String mVideoTitle;

    public enum PaySuccessType {
        NORMAL,
        PLAYER,
        H5ACTIVITY
    }

    public static void setPaySuccessType(PaySuccessType paySuccessType) {
        mPaySuccessType = paySuccessType;
    }

    public static PaySuccessType getPaySuccessType() {
        return mPaySuccessType;
    }

    public static void payFromH5(String title, String url) {
        mTitle = title;
        mUrl = url;
    }

    public static String getH5ActivityTitle() {
        return mTitle;
    }

    public static String getH5ActivityUrl() {
        return mUrl;
    }

    public static void setVideoTitle(String videoTitle) {
        mVideoTitle = videoTitle;
    }

    public static String getVideoTitle() {
        return mVideoTitle;
    }

    public static void setDialogDefaultProductBean(SimpleProductBean mSimpleProductBean) {
        mDefaultSimpleProductBean = mSimpleProductBean;
    }

    public static SimpleProductBean getDefaultSimpleProductBean() {
        return mDefaultSimpleProductBean;
    }

    public static void setPayMethodBean(PaymentMethod mPaymentMethod) {
        mDefaultPayMethodBean = mPaymentMethod;
    }

    public static PaymentMethod getDefaultPayMethod() {
        return mDefaultPayMethodBean;
    }

    public static void setPayProduct(String productName, String productDes) {
        mProductName = productName;
        mProductDes = productDes;
    }

    public static String getmProductName() {
        return mProductName;
    }

    public static String getmProductDes() {
        return mProductDes;
    }
}
