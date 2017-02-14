package com.letv.core.bean;

import java.util.ArrayList;

public class SaleNoteListBean extends ArrayList<SaleNoteBean> implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public String code;
    public String msg;
    public String totalCount = "0";
}
