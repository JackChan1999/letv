package com.letv.redpacketsdk;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.letv.redpacketsdk.bean.GiftBean;
import com.letv.redpacketsdk.parser.GiftBeanParser;

public class RedPacketSharePreferences {
    private static RedPacketSharePreferences instance = new RedPacketSharePreferences();
    private final String SPNAME = "com.letv.redpacketsdk.sp";
    private final String SP_GIFT_JSON = "sp_gift_json";
    private final String SP_HAS_OPEN_RED_PACKET_ID = "sp_has_open_red_packet_id";
    private final String SP_HAS_SHARED = "sp_has_shared";
    private final String SP_PASS = "sp_pass";
    private SharedPreferences mPrefs = null;

    private RedPacketSharePreferences() {
    }

    public static RedPacketSharePreferences getInstance() {
        return instance;
    }

    private SharedPreferences getSharedPreferences() {
        if (this.mPrefs == null) {
            this.mPrefs = RedPacketSdkManager.getInstance().getApplicationContext().getSharedPreferences("com.letv.redpacketsdk.sp", 0);
        }
        return this.mPrefs;
    }

    public void setGiftJson(String json) {
        if (json == null) {
            json = "";
        }
        if (!json.equals(getGiftJson())) {
            Editor e = getSharedPreferences().edit();
            e.putString("sp_gift_json", json);
            e.commit();
        }
    }

    public String getGiftJson() {
        return getSharedPreferences().getString("sp_gift_json", "");
    }

    public void setHasOpenRedPacketId() {
        Editor e = getSharedPreferences().edit();
        e.putString("sp_has_open_red_packet_id", RedPacketSdkManager.getInstance().getRedPacketBean().id);
        e.commit();
    }

    public String getHasOpenRedPacketId() {
        return getSharedPreferences().getString("sp_has_open_red_packet_id", "");
    }

    protected void setHasShared() {
        Editor e = getSharedPreferences().edit();
        e.putString("sp_has_shared", RedPacketSdkManager.getInstance().getRedPacketBean().id);
        e.commit();
    }

    public boolean getHasShared() {
        return compareRedPacketId(getSharedPreferences().getString("sp_has_shared", ""));
    }

    public void setPassRedPacket() {
        Editor e = getSharedPreferences().edit();
        e.putString("sp_has_shared", "");
        e.putString("sp_pass", RedPacketSdkManager.getInstance().getRedPacketBean().id);
        e.commit();
    }

    public boolean isPassRedPacket() {
        return compareRedPacketId(getSharedPreferences().getString("sp_pass", ""));
    }

    public GiftBean getGiftBean() {
        return new GiftBeanParser().parser(getGiftJson());
    }

    public boolean hasOpenedThisRedPacket() {
        return compareRedPacketId(getHasOpenRedPacketId());
    }

    private boolean compareRedPacketId(String id) {
        if (RedPacketSdkManager.getInstance().hasRedPacket()) {
            return RedPacketSdkManager.getInstance().getRedPacketBean().id.equals(id);
        }
        return false;
    }
}
