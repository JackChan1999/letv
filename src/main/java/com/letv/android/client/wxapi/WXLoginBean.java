package com.letv.android.client.wxapi;

import com.alibaba.fastjson.annotation.JSONField;
import com.letv.core.bean.LetvBaseBean;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class WXLoginBean implements LetvBaseBean {
    private static final long serialVersionUID = 4318750899032039049L;
    @JSONField(name = "access_token")
    public String access_token_wx;
    @JSONField(name = "expires_in")
    public String expires_in_wx;
    @JSONField(name = "openid")
    public String openid_wx;
    @JSONField(name = "refresh_token")
    public String refresh_token_wx;
    @JSONField(name = "scope")
    public String scope_wx;

    public WXLoginBean() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public String getmWxAccessToken() {
        return this.access_token_wx;
    }

    public String getmWxExpiresIn() {
        return this.expires_in_wx;
    }

    public String getmRefreshToken() {
        return this.refresh_token_wx;
    }

    public String getmOpenId() {
        return this.openid_wx;
    }

    public String getmScope() {
        return this.scope_wx;
    }

    public String toString() {
        return "WXLoginBean [access_token_wx=" + this.access_token_wx + ", expires_in_wx=" + this.expires_in_wx + ", refresh_token_wx=" + this.refresh_token_wx + ", openid_wx=" + this.openid_wx + ", scope_wx=" + this.scope_wx + "]";
    }
}
