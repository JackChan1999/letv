package com.letv.redpacketsdk.parser;

import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.PollingResult;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.callback.RedPacketPollingCallback;
import com.letv.redpacketsdk.utils.LogInfo;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RedPacketParser extends CommonParser {
    private final String ANDROIDURL = "androidUrl";
    private final String CONTENT = WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT;
    private final String CONTENTTYPE = "type";
    private final String ENDTIME = "endTime";
    private final String EXTENDCID = "extendCid";
    private final String EXTENDJSON = "extendJson";
    private final String EXTENDPID = "extendPid";
    private final String EXTENDRANGE = "extendRange";
    private final String EXTENDZID = "extendZid";
    private final String GIFTID = "subTitle";
    private final String ID = "id";
    private final String IOSURL = "iosUrl";
    private final String MOBCHANNEL = "mobChannel";
    private final String MOBILEPIC = "mobilePic";
    private final String PIC1 = "pic1";
    private final String PUSHFLAG = "pushflag";
    private final String REMARK = "remark";
    private final String SHAREPIC = "padPic";
    private final String SHARETITLE = "shorDesc";
    private final String SHAREURL = "url";
    private final String STARTTIME = "startTime";
    private final String TITLE = "title";
    private RedPacketPollingCallback mCallback;
    private PollingResult mPollingResult = new PollingResult();
    private String sAndroidPlatformCode = "420003_1";

    public RedPacketBean parser(String data, RedPacketPollingCallback callback) throws JSONException, Exception {
        this.mCallback = callback;
        return parser(new JSONObject(data));
    }

    public RedPacketBean parser(JSONObject data) throws Exception {
        parsePollingController(data);
        RedPacketBean bean = new RedPacketBean();
        if (has(data, "blockContent")) {
            JSONArray jsonArray = data.getJSONArray("blockContent");
            if (!(jsonArray == null || jsonArray.length() == 0)) {
                RedPacketSdkManager.getInstance().setRedPacketList(jsonArray);
            }
            bean = parserRedPacketList(jsonArray);
            bean.type = RedPacketBean.TYPE_REDPACKET;
        }
        this.mPollingResult.hasRedPacket = RedPacketSdkManager.getInstance().hasRedPacket(bean);
        if (this.mCallback != null) {
            this.mCallback.onReceivePollingResult(this.mPollingResult);
            this.mCallback = null;
        }
        LogInfo.log("RedPacketParser", "parser + Network is ok,parse polling result is finish");
        return bean;
    }

    public RedPacketBean parserRedPacketList(JSONArray jsonArray) {
        RedPacketBean bean = new RedPacketBean();
        if (jsonArray == null || jsonArray.length() == 0) {
            return bean;
        }
        JSONObject json = getCurrentlyRedPacketJson(jsonArray);
        if (json != null) {
            return parseRedPacketBean(json);
        }
        return bean;
    }

    private JSONObject getCurrentlyRedPacketJson(JSONArray jsonArray) {
        int i = 0;
        while (i < jsonArray.length()) {
            try {
                JSONObject json = jsonArray.getJSONObject(i);
                if (isCurrentlyRedPacket(json)) {
                    return json;
                }
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean isCurrentlyRedPacket(JSONObject json) {
        if (json == null) {
            return false;
        }
        try {
            if (!has(json, "startTime") || !has(json, "endTime")) {
                return false;
            }
            String startTimeString = json.getString("startTime");
            String endTimeString = json.getString("endTime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long startTime = simpleDateFormat.parse(startTimeString).getTime();
            long endTime = simpleDateFormat.parse(endTimeString).getTime();
            long time = new Date().getTime();
            if (time < startTime || time > endTime) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private RedPacketBean parseRedPacketBean(JSONObject json) {
        RedPacketBean redPacketBean = new RedPacketBean();
        if (!json.optString("pushflag").contains(this.sAndroidPlatformCode)) {
            LogInfo.log("RedPacketParser", "parserRedPacketBean + not this platform rp");
        } else if (json.optString("mobChannel").contains(RedPacketSdkManager.getInstance().getAppId())) {
            redPacketBean.title = json.optString("title");
            redPacketBean.id = json.optString("id");
            redPacketBean.giftId = json.optString("subTitle");
            redPacketBean.pic1 = json.optString("pic1");
            redPacketBean.mobilePic = json.optString("mobilePic");
            redPacketBean.startTime = json.optString("startTime");
            redPacketBean.endTime = json.optString("endTime");
            redPacketBean.shareBean.shareUrl = json.optString("url");
            redPacketBean.shareBean.sharePic = json.optString("padPic");
            redPacketBean.shareBean.shareTitle = json.optString("shorDesc");
            redPacketBean.actionUrl = json.optString("iosUrl");
            redPacketBean.nextRedPacketTime = json.optString("androidUrl");
            redPacketBean.getGiftButtonString = json.optString("remark");
            if (has(json, "extendJson")) {
                try {
                    JSONObject extendJson = json.getJSONObject("extendJson");
                    redPacketBean.entryLocation.extendRange = extendJson.optString("extendRange");
                    redPacketBean.entryLocation.extendCid = extendJson.optString("extendCid");
                    redPacketBean.entryLocation.extendPid = extendJson.optString("extendPid");
                    redPacketBean.entryLocation.extendZid = extendJson.optString("extendZid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            redPacketBean.entryLocation.contentType = json.optInt("type");
            redPacketBean.entryLocation.content = json.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
        } else {
            LogInfo.log("RedPacketParser", "parserRedPacketBean + not this app rp");
        }
        return redPacketBean;
    }

    private void parsePollingController(JSONObject data) {
        if (has(data, "startTime")) {
            this.mPollingResult.rollSwitch = data.optInt("startTime", -1);
        }
        if (has(data, "endTime")) {
            this.mPollingResult.rollRate = data.optInt("endTime", -1);
        }
    }

    public RedPacketBean parserPreview(String jsonString) throws JSONException {
        JSONObject data = new JSONObject(jsonString);
        RedPacketBean bean = new RedPacketBean();
        if (has(data, "blockContent")) {
            bean = parserRedPacketList(data.getJSONArray("blockContent"));
            bean.type = RedPacketBean.TYPE_FORCAST;
        }
        LogInfo.log("RedPacketParser", "parserPreview + bean = " + bean.toString());
        return bean;
    }
}
