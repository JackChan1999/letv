package com.letv.core.bean;

import com.letv.base.R;
import com.letv.core.BaseApplication;
import java.util.ArrayList;

public class MyElectronicTicketBean implements LetvBaseBean {
    public int resultCode;
    public ArrayList<ElectronicTicket> ticketsList = new ArrayList();

    public static class ElectronicTicket implements LetvBaseBean {
        public long cancelTime;
        public int currentNum;
        public String desc;
        public String from;
        public String id;
        public String name;
        public int subType;
        public int totalNum;
        public int type;
    }

    public ArrayList<ElectronicTicket> getDefaultTicketList() {
        this.ticketsList.clear();
        this.ticketsList.add(getDefaultElectronicTicket());
        return this.ticketsList;
    }

    public ElectronicTicket getDefaultElectronicTicket() {
        ElectronicTicket electronicTicket = new ElectronicTicket();
        electronicTicket.name = BaseApplication.instance.getString(R.string.vip_ticket_name);
        electronicTicket.type = 0;
        electronicTicket.totalNum = -1;
        electronicTicket.currentNum = 20;
        electronicTicket.from = BaseApplication.instance.getString(R.string.vip_ticket_from);
        electronicTicket.desc = BaseApplication.instance.getString(R.string.vip_ticket_desc);
        return electronicTicket;
    }
}
