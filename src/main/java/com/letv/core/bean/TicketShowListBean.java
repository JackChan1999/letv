package com.letv.core.bean;

import java.util.ArrayList;

public class TicketShowListBean extends ArrayList<TicketShow> implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public String code = "";
    public ArrayList<String> content = null;
    public String mobilePic = "";
    public String totalSize = "";

    public static class TicketShow implements LetvBaseBean {
        private static final long serialVersionUID = -8338521964857923776L;
        public String beginTime = "";
        public String endTime = "";
        public String isExpired = "";
        public String pid = "";
        public String ticketCode = "";
        public String ticketName = "";
        public String ticketSource = "";
        public String totalNumber = "";
        public String usedNumber = "";
    }
}
