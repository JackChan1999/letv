package com.alipay.sdk.app;

public enum m {
    SUCCEEDED(9000, "处理成功"),
    FAILED(4000, "系统繁忙，请稍后再试"),
    CANCELED(6001, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    PARAMS_ERROR(4001, "参数错误"),
    PAY_WAITTING(8000, "支付结果确认中");
    
    private int g;
    private String h;

    private m(int i, String str) {
        this.g = i;
        this.h = str;
    }

    public static m a(int i) {
        switch (i) {
            case 4001:
                return PARAMS_ERROR;
            case 6001:
                return CANCELED;
            case 6002:
                return NETWORK_ERROR;
            case 8000:
                return PAY_WAITTING;
            case 9000:
                return SUCCEEDED;
            default:
                return FAILED;
        }
    }

    private void a(String str) {
        this.h = str;
    }

    private void b(int i) {
        this.g = i;
    }

    public final int a() {
        return this.g;
    }

    public final String b() {
        return this.h;
    }
}
