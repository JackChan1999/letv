package com.letv.core.messagebus.message;

import android.content.Context;

public class LeMessage {
    private Context mContext;
    private Object mData;
    private final int mId;

    public LeMessage(int id) {
        this(id, null);
    }

    public LeMessage(int id, Object data) {
        this.mId = id;
        this.mData = data;
    }

    public int getId() {
        return this.mId;
    }

    public Object getData() {
        return this.mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public static boolean checkMessageValidity(LeMessage message, Class<?> clz) {
        return (message == null || message.getData() == null || !clz.isInstance(message.getData())) ? false : true;
    }
}
