package com.letv.core.parser;

import android.os.Build.VERSION;
import com.letv.core.bean.PushBookLive;
import com.letv.core.bean.PushData;
import com.letv.core.bean.PushData.Activatemsg;
import com.letv.core.bean.PushData.SMSMessage;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.PlayConstant;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PushDataParser extends LetvMobileParser<PushData> {
    public static final String AT = "at";
    public static final String BIGIMG = "bigImg";
    public static final String BIGIMGSUBTITLE = "bigImgSubtitle";
    public static final String BIGIMGTITLE = "bigImgTitle";
    public static final String CID = "cid";
    public static final String CONTENTSTYLE = "contentStyle";
    public static final String ID = "id";
    public static final String ISACTIVATE = "isActivate";
    public static final String ISONDESKTOP = "isOnDeskTop";
    public static final String ISSOUND = "isSound";
    public static final String ISVIBRATE = "isVibrate";
    public static final String LIVEENDDATE = "liveEndDate";
    public static final String MSG = "msg";
    public static final String PICURL = "picUrl";
    public static final String RESID = "resid";
    public static final String TYPE = "type";

    public PushData parse(JSONObject data) throws JSONException {
        JSONObject object;
        PushData pushData = new PushData();
        if (has(data, "pushmsg")) {
            object = getJSONObject(data, "pushmsg");
            pushData.id = getLong(object, "id");
            pushData.msg = getString(object, "msg");
            pushData.albumId = getString(object, RESID);
            pushData.type = getInt(object, "type");
            pushData.at = getInt(object, AT);
            pushData.liveEndDate = getString(object, "liveEndDate");
            pushData.cid = getString(object, "cid");
            pushData.picUrl = getString(object, PICURL);
            pushData.isActivate = getString(object, ISACTIVATE);
            pushData.isOnDeskTop = getString(object, ISONDESKTOP);
            pushData.isSound = getString(object, ISSOUND);
            pushData.isVibrate = getString(object, ISVIBRATE);
            if (VERSION.SDK_INT >= 16) {
                pushData.contentStyle = getString(object, CONTENTSTYLE);
                pushData.bigImg = getString(object, BIGIMG);
                pushData.bigImgTitle = getString(object, BIGIMGTITLE);
                pushData.bigImgSubtitle = getString(object, BIGIMGSUBTITLE);
            } else {
                pushData.contentStyle = "1";
            }
            if (object.has("id")) {
                pushData.mHasPushMsg = true;
            }
        } else {
            pushData.mHasPushMsg = false;
        }
        if (has(data, "bookliveMsg")) {
            JSONArray jsonArray = getJSONArray(data, "bookliveMsg");
            if (jsonArray != null && jsonArray.length() > 0) {
                pushData.pushBookLives = new ArrayList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    pushData.pushBookLives.add(parseBookLive(getJSONObject(jsonArray, i)));
                }
            }
        }
        if (has(data, "pushtime")) {
            object = getJSONObject(data, "pushtime");
            if (has(object, "times")) {
                pushData.pushtime = getInt(object, "times");
            }
        }
        if (has(data, "activatemsg")) {
            object = getJSONObject(data, "activatemsg");
            if (object != null) {
                Activatemsg activatemsg = new Activatemsg();
                if (has(object, "message")) {
                    activatemsg.message = getString(object, "message");
                    activatemsg.silent = getInt(object, "silent");
                    pushData.activatemsg = activatemsg;
                }
            }
        }
        if (has(data, "message")) {
            object = getJSONObject(data, "message");
            if (object != null) {
                SMSMessage message = new SMSMessage();
                if (has(object, "message")) {
                    message.id = getString(object, "id");
                    message.message = getString(object, "message");
                    message.phonenum = getString(object, "phonenum");
                    message.image = getString(object, "image");
                    message.url = getString(object, "url");
                    message.isshow = getInt(object, "isshow");
                    pushData.smsMessage = message;
                }
            }
        }
        return pushData;
    }

    private PushBookLive parseBookLive(JSONObject data) throws JSONException {
        PushBookLive pushBookLive = new PushBookLive();
        pushBookLive.channelName = getString(data, Field.CHANNELNAME);
        pushBookLive.url = getString(data, "url");
        pushBookLive.code = getString(data, "code");
        pushBookLive.programName = getString(data, Field.PROGRAMNAME);
        pushBookLive.play_time = getString(data, "play_time");
        pushBookLive.url_350 = getString(data, PlayConstant.LIVE_URL_350);
        pushBookLive.id = getString(data, "id");
        return pushBookLive;
    }
}
