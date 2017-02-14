package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class VoteOptionBean implements LetvBaseBean {
    public static final int IS_VOTED = 1;
    public String img;
    @JSONField(name = "is_vote")
    public int isVote;
    public long num;
    @JSONField(name = "option_id")
    public String optionId = "";
    public String remarks;
    @JSONField(name = "subtitle")
    public String subTitle;
    public String title = "";
}
