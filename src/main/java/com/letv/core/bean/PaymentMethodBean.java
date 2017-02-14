package com.letv.core.bean;

import java.util.ArrayList;

public class PaymentMethodBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public ArrayList<PaymentMethod> paymentMethodList = new ArrayList();

    public static class PaymentMethod {
        public String activityPackageId;
        public String activityStatus;
        public String id;
        public String logoUrl;
        public String payType;
        public String subTitle;
        public String title;
    }
}
