package com.letv.component.player.http.bean;

import com.letv.component.core.http.bean.LetvBaseBean;
import com.letv.component.player.http.bean.Order.Tag;
import java.util.List;

public class Cmdinfo implements LetvBaseBean {
    private static final long serialVersionUID = -8468410886834671381L;
    private List<Tag> cmdAry;
    private Long endTime;
    private String fbCode;
    private Long fbId;
    private int upPeriod;

    public List<Tag> getCmdAry() {
        return this.cmdAry;
    }

    public void setCmdAry(List<Tag> cmdAry) {
        this.cmdAry = cmdAry;
    }

    public String getFbCode() {
        return this.fbCode;
    }

    public void setFbCode(String fbCode) {
        this.fbCode = fbCode;
    }

    public Long getFbId() {
        return this.fbId;
    }

    public void setFbId(Long fbId) {
        this.fbId = fbId;
    }

    public int getUpPeriod() {
        return this.upPeriod;
    }

    public void setUpPeriod(int upPeriod) {
        this.upPeriod = upPeriod;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
