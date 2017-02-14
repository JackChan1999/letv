package com.letv.core.parser;

import com.letv.core.bean.TokenResultBean;
import org.json.JSONObject;

public class TokenResultParser extends LetvMasterParser<TokenResultBean> {
    protected TokenResultBean parse(JSONObject data) throws Exception {
        TokenResultBean result = new TokenResultBean();
        result.isPass = false;
        return result;
    }
}
