package com.letv.core.messagebus.message;

public class LeResponseMessage {
    private Object mData;
    private final int mId;

    public LeResponseMessage(int id) {
        this(id, null);
    }

    public LeResponseMessage(int id, Object data) {
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

    public static boolean checkResponseMessageValidity(LeResponseMessage responseMessage, Class<?> clz) {
        return (responseMessage == null || responseMessage.getData() == null || !clz.isInstance(responseMessage.getData())) ? false : true;
    }
}
