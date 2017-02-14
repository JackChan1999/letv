package com.letv.core.utils.external.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import com.letv.core.bean.LetvBaseBean;

public class AlipayData implements LetvBaseBean {
    @JSONField(name = "corderid")
    private String corderid;
    @JSONField(name = "errormsg")
    private String erromsg;
    @JSONField(name = "info")
    private String info;
    @JSONField(name = "partnerId")
    private String partnerId;
    @JSONField(name = "status")
    private String status;

    public String getErromsg() {
        return this.erromsg;
    }

    public void setErromsg(String erromsg) {
        this.erromsg = erromsg;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPartnerId() {
        return this.partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
