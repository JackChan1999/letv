package com.letv.core.bean;

import java.util.ArrayList;
import java.util.List;

public class HotTypeListBean implements LetvBaseBean {
    private List<HotTypeItemBean> hotpoint_channel;

    public List<HotTypeItemBean> getHotpointChannel() {
        if (this.hotpoint_channel == null) {
            this.hotpoint_channel = new ArrayList();
        }
        return this.hotpoint_channel;
    }

    public void setHotpoint_Channel(List<HotTypeItemBean> hotpointChannel) {
        this.hotpoint_channel = hotpointChannel;
    }
}
