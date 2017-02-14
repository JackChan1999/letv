package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

public class VoteBean implements LetvBaseBean {
    @JSONField(name = "lifeend")
    public long lifeEnd;
    @JSONField(name = "lifestart")
    public long lifeStart;
    public List<VoteOptionBean> options;
    public String pid;
    public String rule;
    public long second;
    @JSONField(name = "subtitle")
    public String subTitle;
    public String title;
    public String vid;
    @JSONField(name = "vote_id")
    public String voteId;
}
