package com.letv.core.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayVoteListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    public List<PlayVoteItemBean> voteList = new ArrayList();

    public static class PlayVoteResultBean implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        public int code;
        public Map<String, Integer> map = new HashMap();
    }
}
