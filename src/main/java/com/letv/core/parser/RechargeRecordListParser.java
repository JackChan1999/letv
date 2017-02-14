package com.letv.core.parser;

import com.letv.core.bean.RechargeRecordListBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RechargeRecordListParser extends LetvMasterParser<RechargeRecordListBean> {
    private static final String AMOUNT = "amount";
    private static final String STATUS = "status";
    private static final String USER_NAME = "username";
    private static final String ZRECORD = "zrecord";

    public RechargeRecordListBean parse(JSONObject data) throws JSONException {
        RechargeRecordListBean rechargeRecordList = new RechargeRecordListBean();
        rechargeRecordList.amount = getInt(data, AMOUNT);
        rechargeRecordList.userName = getString(data, USER_NAME);
        JSONArray jsonArray = getJSONArray(data, ZRECORD);
        for (int i = 0; i < jsonArray.length(); i++) {
            rechargeRecordList.add(new RechargeRecordParser().parse(getJSONObject(jsonArray, i)));
        }
        return rechargeRecordList;
    }

    protected boolean canParse(String data) {
        boolean z = false;
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("status")) {
                z = getString(object, "status").equals("00");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return z;
    }

    protected JSONObject getData(String data) throws JSONException {
        return new JSONObject(data);
    }
}
