package com.letv.mobile.letvhttplib.api;

import android.text.TextUtils;
import com.letv.core.parser.LetvMasterParser;
import com.letv.mobile.letvhttplib.bean.LetvBaseBean;
import com.letv.mobile.letvhttplib.parser.LetvMobileParser;
import com.letv.mobile.letvhttplib.parser.LetvNormalParser;
import com.letv.mobile.letvhttplib.volley.Volley;
import com.letv.mobile.letvhttplib.volley.VolleyRequest;
import com.letv.mobile.letvhttplib.volley.VolleyResponse;
import com.letv.mobile.letvhttplib.volley.VolleyResponse.ResponseSupplier;
import com.letv.mobile.letvhttplib.volley.VolleyResult;
import com.letv.mobile.letvhttplib.volley.exception.DataIsErrException;
import com.letv.mobile.letvhttplib.volley.exception.DataIsNullException;
import com.letv.mobile.letvhttplib.volley.exception.DataNoUpdateException;
import com.letv.mobile.letvhttplib.volley.exception.JsonCanNotParseException;
import com.letv.mobile.letvhttplib.volley.exception.ParseException;
import com.letv.mobile.letvhttplib.volley.exception.TokenLoseException;
import org.json.JSONException;
import org.json.JSONObject;

public class LetvRequest<T extends LetvBaseBean> extends VolleyRequest<T> {
    private Class<T> mClass;
    private int mFrom;

    public LetvRequest() {
        this(null);
    }

    public LetvRequest(Class<T> cls) {
        this(cls, 0);
    }

    public LetvRequest(Class<T> cls, int from) {
        this.mClass = cls;
        this.mFrom = from;
    }

    public final VolleyRequest<T> add() {
        Volley.getQueue().add(this);
        return this;
    }

    public VolleyResult<T> syncFetch() {
        return Volley.getQueue().syncFetch(this);
    }

    protected void parse(VolleyResponse response, ResponseSupplier supplier) throws JsonCanNotParseException, ParseException, DataIsNullException, DataIsErrException, DataNoUpdateException, TokenLoseException {
        if (response == null || TextUtils.isEmpty(response.data)) {
            throw new DataIsNullException();
        }
        String data = response.data;
        try {
            if (this.mParser == null) {
                JSONObject object = new JSONObject(data);
                if (object.has("body")) {
                    setParser(new LetvMobileParser(this.mFrom));
                } else if (object.has(LetvMasterParser.BEAN)) {
                    setParser(new com.letv.mobile.letvhttplib.parser.LetvMasterParser(this.mFrom));
                } else {
                    setParser(new LetvNormalParser(this.mFrom));
                }
            }
            if (this.mParser != null) {
                this.mParser.setClz(this.mClass);
                if (supplier == ResponseSupplier.NETWORK) {
                    this.mNetworkEntry = this.mParser.doParse(data);
                } else {
                    this.mCacheEntry = this.mParser.doParse(data);
                }
            }
        } catch (JSONException e) {
            throw new DataIsErrException();
        }
    }
}
