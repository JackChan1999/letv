package com.letv.core.parser;

import android.text.TextUtils;
import com.letv.core.network.volley.exception.DataIsErrException;
import com.letv.core.network.volley.exception.DataIsNullException;
import com.letv.core.network.volley.exception.DataNoUpdateException;
import com.letv.core.network.volley.exception.JsonCanNotParseException;
import com.letv.core.network.volley.exception.ParseException;
import com.letv.core.network.volley.exception.TokenLoseException;
import com.letv.ltpbdata.LTHeaderModelPBOuterClass.LTHeaderModelPB;

public abstract class LetvPBParser<T> extends LetvBaseParser<T, byte[]> {
    public String markid;

    protected abstract LTHeaderModelPB getHeader(T t);

    public LetvPBParser() {
        this(0);
    }

    public LetvPBParser(int from) {
        super(from);
    }

    public T doParse(String data) throws DataIsNullException, DataIsErrException, TokenLoseException, ParseException, JsonCanNotParseException, DataNoUpdateException {
        if (TextUtils.isEmpty(data)) {
            throw new DataIsNullException();
        }
        try {
            byte[] bytes = getData(data);
            if (bytes == null) {
                throw new DataIsErrException();
            }
            try {
                T result = parse(bytes);
                if (result == null) {
                    throw new ParseException();
                } else if (!hasUpdate(result)) {
                    throw new DataNoUpdateException();
                } else if (checkHeader(result)) {
                    return result;
                } else {
                    throw new JsonCanNotParseException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ParseException();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new DataIsErrException();
        }
    }

    protected boolean canParse(String data) {
        return true;
    }

    private boolean checkHeader(T t) {
        LTHeaderModelPB header = getHeader(t);
        if (header == null) {
            return true;
        }
        int status = header.status;
        setErrCode(status);
        switch (status) {
            case 1:
                this.markid = header.markid;
                return true;
            case 2:
                return false;
            case 4:
                this.markid = header.markid;
                return false;
            case 6:
                return true;
            default:
                return false;
        }
    }

    public boolean hasUpdate() {
        return true;
    }

    private boolean hasUpdate(T t) {
        LTHeaderModelPB header = getHeader(t);
        if (header != null && header.status == 4) {
            return false;
        }
        return true;
    }

    protected byte[] getData(String data) throws Exception {
        return data.getBytes("UTF-8");
    }
}
