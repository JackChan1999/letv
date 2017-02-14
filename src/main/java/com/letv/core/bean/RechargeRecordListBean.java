package com.letv.core.bean;

import java.util.ArrayList;

public class RechargeRecordListBean extends ArrayList<RechargeRecordBean> implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public int amount;
    public String status;
    public String userName;
}
