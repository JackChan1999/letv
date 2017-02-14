package com.letv.core.bean;

import com.letv.core.constant.LiveRoomConstant;

public class DrmSoUrlBean implements LetvBaseBean {
    public String md5 = "";
    public String pcode = LiveRoomConstant.CHANNEL_TYPE_ALL;
    public int size;
    public String url = "";
    public String version = "";
}
