package com.letv.core.parser;

import com.letv.core.bean.MyElectronicTicketBean;
import com.letv.core.bean.MyElectronicTicketBean.ElectronicTicket;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyElectronicTicketParser extends LetvMobileParser<MyElectronicTicketBean> {
    public MyElectronicTicketBean parse(JSONObject data) throws Exception {
        MyElectronicTicketBean myElectronicTicketBean = new MyElectronicTicketBean();
        ArrayList<ElectronicTicket> ticketsList = new ArrayList();
        if (data.has("code")) {
            myElectronicTicketBean.resultCode = getInt(data, "code");
        }
        if (data.has("values")) {
            JSONArray array = data.getJSONObject("values").getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (i == 0 && getInt(object, "type") != 0) {
                    ticketsList.add(myElectronicTicketBean.getDefaultElectronicTicket());
                }
                ElectronicTicket electronicTicket = new ElectronicTicket();
                electronicTicket.name = getString(object, "name");
                electronicTicket.id = getString(object, "id");
                electronicTicket.type = getInt(object, "type");
                electronicTicket.totalNum = getInt(object, "totalNum");
                electronicTicket.currentNum = getInt(object, "currentNum");
                electronicTicket.cancelTime = getLong(object, "cancelTime");
                electronicTicket.subType = getInt(object, "subType");
                electronicTicket.from = getString(object, "from");
                electronicTicket.desc = getString(object, SocialConstants.PARAM_APP_DESC);
                ticketsList.add(electronicTicket);
            }
        }
        myElectronicTicketBean.ticketsList = ticketsList;
        return myElectronicTicketBean;
    }
}
