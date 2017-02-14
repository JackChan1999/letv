package com.letv.core.api;

import android.text.TextUtils;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse;
import com.letv.core.network.volley.VolleyResponse.ResponseSupplier;
import com.letv.core.network.volley.VolleyResult;
import com.letv.core.network.volley.exception.DataIsErrException;
import com.letv.core.network.volley.exception.DataIsNullException;
import com.letv.core.network.volley.exception.DataNoUpdateException;
import com.letv.core.network.volley.exception.JsonCanNotParseException;
import com.letv.core.network.volley.exception.ParseException;
import com.letv.core.network.volley.exception.TokenLoseException;
import com.letv.core.parser.LetvMasterParser;
import com.letv.core.parser.LetvMobileParser;
import com.letv.core.parser.LetvNormalParser;
import org.json.JSONException;
import org.json.JSONObject;

public class LetvRequest<T> extends VolleyRequest<T> {
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
        if (!isPb()) {
            try {
                if (this.mParser == null) {
                    JSONObject object = new JSONObject(data);
                    if (object.has("body")) {
                        setParser(new LetvMobileParser(this.mFrom));
                    } else if (object.has(LetvMasterParser.BEAN)) {
                        setParser(new LetvMasterParser(this.mFrom));
                    } else {
                        setParser(new LetvNormalParser(this.mFrom));
                    }
                }
            } catch (JSONException e) {
                throw new DataIsErrException();
            }
        } else if (this.mParser == null) {
            setParser(new 1(this));
        }
        if (this.mParser != null) {
            this.mParser.setClz(this.mClass);
            if (supplier == ResponseSupplier.NETWORK) {
                this.mNetworkEntry = this.mParser.doParse(data);
            } else {
                this.mCacheEntry = this.mParser.doParse(data);
            }
        }
    }
}
